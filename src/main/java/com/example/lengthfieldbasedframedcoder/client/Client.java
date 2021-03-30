package com.example.lengthfieldbasedframedcoder.client;

import com.example.lengthfieldbasedframedcoder.client.handler.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Client {
    private int port = 8080;
    private String host = "localhost";

    public static void main(String[] args) throws Exception{
        new Client().run();
    }

    public void run() throws Exception{
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        try{
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
                                    new LoggingHandler(LogLevel.INFO),
                                    new ClientHandler()
                            );
                        }
                    });

            b.connect(host, port).sync()
                    .channel().closeFuture().sync();
        }finally{
            workerGroup.shutdownGracefully();
        }
    }
}
