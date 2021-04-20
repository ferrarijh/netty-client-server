package com.example.handlerstest;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Server {

    public static void main(String[] args) throws Exception{
        (new Server()).start(); //TODO("why not static start() without new?")
    }

    public void start() throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        System.out.println("Server started...");

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
                                    new LoggingHandler(LogLevel.INFO)
                            );
                        }
                    });

            ChannelFuture f = serverBootstrap.bind(8080).sync();

            f.channel().closeFuture().sync();
        }finally{
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
