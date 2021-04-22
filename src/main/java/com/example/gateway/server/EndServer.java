package com.example.gateway.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.Scanner;

public class EndServer {
//    private String id;
//    private final int port = 8081;

    public static void main(String[] args) {
        for(int i=8081; i<=8081; i++)
            new EndServer().run(i);
    }

    public void run(int p){
//        getIdInput();

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new LoggingHandler(LogLevel.INFO),
                                    new EndServerHandler()
                            );
                        }
                    });

            sb.bind(p)
                    .sync()
                    .channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
//            workerGroup.shutdownGracefully();
//            bossGroup.shutdownGracefully();
        }
    }

    private void getIdInput(){
        Scanner sc = new Scanner(System.in);
        String newId = "";
        System.out.print("client id (length=4): ");
        while(newId.length() != 4) {
            System.out.print("client id (length=4): ");
            newId = sc.nextLine();
        }
    }
}
