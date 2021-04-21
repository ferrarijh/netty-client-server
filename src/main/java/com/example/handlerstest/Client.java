package com.example.handlerstest;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class Client {
    private final String host;
    private final int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) throws Exception{
        (new Client("localhost", 8080)).run();
    }

    public void run() throws Exception{
        System.out.println("Client started...");

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(inetSocketAddress)
                    .handler(
                            new ChannelInitializer<SocketChannel>() {
                                protected void initChannel(SocketChannel socketChannel) throws Exception {
                                    socketChannel.pipeline().addLast(
                                            new LoggingHandler(LogLevel.INFO),

                                            new ChannelInboundHandlerAdapter(){
                                                @Override
                                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                                    System.out.println("1st inbound handler");
                                                    String msg = "[TEST MESSAGE]";
                                                    ctx.writeAndFlush(msg);   //TODO("writeAndFlush() is only effective at the end of pipeline")
//                                                    ctx.fireChannelActive();
//                                                    ctx.fireChannelRead(msg);

                                                }
                                            },

                                            new MessageToByteEncoder<String>(){
                                                @Override
                                                protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
                                                    System.out.println("writing: "+msg);
                                                    out.writeCharSequence(msg, StandardCharsets.UTF_8);
                                                }
                                            },

                                            new ChannelInboundHandlerAdapter(){
                                                @Override
                                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                                    System.out.println("2nd inbound handler");
                                                }

                                                @Override
                                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                                    String msgStr = (String) msg;
                                                    System.out.println("2nd inbound handler received: "+msgStr);

                                                    ctx.writeAndFlush(msg);
                                                }
                                            },

                                            new ChannelInboundHandlerAdapter(){
                                                @Override
                                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                                    System.out.println("3rd inbound handler");
                                                }

                                                @Override
                                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                                    String msgStr = (String) msg;
                                                    System.out.println("3rd inbound handler received: "+msgStr);

//                                                    ctx.writeAndFlush(msg);
                                                }
                                            }

                                    );
                                }
                            }
                    );

            ChannelFuture f = bootstrap.connect().sync();
            f.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }
}
