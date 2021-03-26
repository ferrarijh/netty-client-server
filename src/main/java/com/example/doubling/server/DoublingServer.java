package com.example.doubling.server;

import com.example.doubling.handler.DoublingProcessingHandler;
import com.example.doubling.handler.DoublingRequestReplayingDecoder;
import com.example.doubling.handler.DoublingResponseEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class DoublingServer {

    private int port;

    public DoublingServer(int port){
        this.port = port;
    }

    public static void main(String[] args) throws Exception {

        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;

        (new DoublingServer(port)).run();
    }

    public void run() throws Exception {
        System.out.println("DoublingServer running...");

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        InetSocketAddress addr = new InetSocketAddress(8080);

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(addr)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new DoublingRequestReplayingDecoder(),
                                    new DoublingResponseEncoder(),
                                    new DoublingProcessingHandler()
                            );
                        }
                    })

//                    .option(ChannelOption.SO_BACKLOG, 128)
//                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    ;


            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
