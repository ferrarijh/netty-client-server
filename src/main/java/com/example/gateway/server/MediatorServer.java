package com.example.gateway.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.StandardCharsets;

public class MediatorServer {
    private EventLoopGroup workerGroup;
//    private EventLoopGroup workerGroup2;
    private EventLoopGroup bossGroup;

    private EventLoop testLoop;

    public static void main(String[] args) throws InterruptedException {
        new MediatorServer().run();
    }

    public void run() throws InterruptedException {
        workerGroup = new NioEventLoopGroup();
        bossGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new LoggingHandler(LogLevel.INFO),
                                    new SimpleMediatorDownstreamHandler()
                            );
                        }
                    }).childOption(
                            ChannelOption.SO_KEEPALIVE, true
            );

            for(int i=8081; i<=8082; i++)
                connectAsClient("AAA"+(i-8080), "localhost", i);

            sb.bind(8080).sync()        //binds server asynchronously and wait till binding completes.
            .channel().closeFuture().sync();    //block till closing completes

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
            workerGroup.shutdownGracefully().sync();
            bossGroup.shutdownGracefully().sync();
        }

    }

    public void connectAsClient(String id, String host, int port){
        if(id.length() != 4)
            throw new IllegalArgumentException("length of id must be 4");

        try{
            Bootstrap b = new Bootstrap();
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(
                            new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                    ch.pipeline().addLast(
//                                            new LoggingHandler(LogLevel.INFO),
                                            new SimpleChannelInboundHandler<ByteBuf>() {
                                                @Override
                                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                                    ChannelRepository.putUpChannel(id, ctx.channel());

                                                    ByteBuf bb = ctx.channel().alloc().buffer();
                                                    bb.writeCharSequence("Hello from MediatorServer", StandardCharsets.UTF_8);
                                                    ctx.writeAndFlush(bb);

                                                    System.out.println("["+Thread.currentThread().getName()+"]");
                                                }

                                                @Override
                                                protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                                    System.out.println("(channelRead0) msg: "+msg.toString(StandardCharsets.UTF_8));
                                                }

                                                @Override
                                                public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                                    System.out.println("(channelReadComplete)");
                                                }
                                            }
                                    );
                                }
                            }
                    );

            b.connect(host, port).sync()
                    .channel().closeFuture().sync();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
