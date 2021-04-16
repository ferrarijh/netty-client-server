package com.example.echo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) throws Exception{
//        if (args.length != 2){
//            System.err.println("[EchoClient] args.length should be 2");
//            return;
//        }
//
//        String h = args[0];
//        int p = Integer.parseInt(args[1]);

        (new EchoClient("localhost", 8081)).start();
    }

    public void start() throws Exception{
        System.out.println("EchoClient started...");

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
                                    ChannelPipeline p = socketChannel.pipeline();
                                    p.addLast(new EchoClientHandler());
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
