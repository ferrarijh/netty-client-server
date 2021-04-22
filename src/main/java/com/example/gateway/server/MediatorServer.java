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

import static com.example.gateway.GatewayMessage.ID_LEN;

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
                                    new MediatorDownstreamHandler()
                            );
                        }
                    }).childOption(
                            ChannelOption.SO_KEEPALIVE, true
            );

            for(int i=8081; i<=8081; i++)
                connectAsClient("E"+(i-8080), "localhost", i);

            sb.bind(8080).sync()        //binds server asynchronously and wait till binding completes.
            .channel().closeFuture().sync();    //block till closing completes

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
            workerGroup.shutdownGracefully().sync();
            bossGroup.shutdownGracefully().sync();
        }

    }

    public void connectAsClient(String destId, String host, int port){
        if(destId.length() != ID_LEN)
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
                                            new LoggingHandler(LogLevel.INFO),
                                            new MediatorUpstreamHandler(destId)
                                    );
                                }
                            }
                    );

            b.connect(host, port).sync()
//                    .channel().closeFuture().sync()
                    ;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
