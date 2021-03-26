package com.example.echo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;

import java.net.InetSocketAddress;
import java.util.logging.Logger;

public class EchoServer {

    private final int port;

    public EchoServer(int p){this.port = p;}

    public static void main(String[] args) throws Exception{

        if(args.length != 1){
            System.err.println("argument length should not be 1");
        }
        int port = Integer.parseInt(args[0]);

        (new EchoServer(port)).start(); //TODO("why not static start() without new?")
    }

    public void start() throws Exception{
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        InetSocketAddress socketAddress = new InetSocketAddress(port);

        System.out.println("EchoServer started at ["+socketAddress.toString()+"]");

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(socketAddress)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline p = socketChannel.pipeline();   //returns assigned ChannelPipeline.
                            p.addLast(new EchoServerHandler());
                        }
                    });
            //childHandler(): executed after connection completion
            //handler(): executed on ServerBootstrap initialization

            ChannelFuture f = serverBootstrap.bind().sync();
            //wait for binding operation to complete
            //bind() creates new Channel and bind it.

            f.channel().closeFuture().sync();
            //Channel.closeFuture() returns ChannelFuture which will be notified when the channel is closed.
        }finally{
            eventLoopGroup.shutdownGracefully();
        }
    }
}
