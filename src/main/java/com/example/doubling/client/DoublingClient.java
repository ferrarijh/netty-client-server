package com.example.doubling.client;

import com.example.doubling.handler.DoublingClientHandler;
import com.example.doubling.handler.DoublingRequestEncoder;
import com.example.doubling.handler.DoublingResponseBTMDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class DoublingClient {


    public static void main(String[] args) throws Exception {

        String host = "localhost";
        int port = 8080;
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        InetSocketAddress addr = new InetSocketAddress(host, port);

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
//                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .remoteAddress(addr)
                    .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(
                            new DoublingRequestEncoder(),
                            new DoublingResponseBTMDecoder(),
                            new DoublingClientHandler()
                    );
                }
            });

            ChannelFuture f = b.connect().sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
