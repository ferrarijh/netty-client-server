package com.example.gateway.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.StandardCharsets;

public class EndClient {
    private String id;
    public static void main(String[] args) {
        new EndClient().run();
    }

    public void run(){
        this.id = "clientA";

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            Bootstrap b = new Bootstrap();
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new LoggingHandler(LogLevel.INFO),
                                    new SimpleChannelInboundHandler<ByteBuf>() {
                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                            System.out.println("(channelRead0) "+msg.toString(StandardCharsets.UTF_8));
                                        }

                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            ByteBuf bb = ctx.alloc().buffer();
                                            bb.writeCharSequence("Hello from EndClient", StandardCharsets.UTF_8);
                                            ctx.writeAndFlush(bb);
                                        }
                                    }
                            );
                        }
                    });

            b.connect("localhost", 8080).sync()
                    .channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
            workerGroup.shutdownGracefully();
        }
    }
}
