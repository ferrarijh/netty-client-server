package com.example.lengthfieldbasedframedcoder.server;

import com.example.lengthfieldbasedframedcoder.client.Client;
import com.example.lengthfieldbasedframedcoder.client.handler.ClientHandler;
import com.example.lengthfieldbasedframedcoder.server.handler.ProcessingHandler;
import com.example.lengthfieldbasedframedcoder.server.handler.RequestDecoder;
import com.example.lengthfieldbasedframedcoder.server.handler.RequestEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Server {

    private int port = 8080;

    public static void main(String[] args) throws Exception{
        new Server().run();
    }

    public void run() throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        try{
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            sc.pipeline().addLast(
                                    new LoggingHandler(LogLevel.INFO),
                                    new RequestDecoder(1024, 0, 4, -4, 0),
                                    new ProcessingHandler(),

                                    new RequestEncoder()
                            );
                        }
                    });

            b.bind(8080).sync()
                    .channel().closeFuture().sync();
        }finally{
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
