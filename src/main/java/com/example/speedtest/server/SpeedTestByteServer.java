package com.example.speedtest.server;

import com.example.speedtest.common.SpeedTestByteDecoder;
import com.example.speedtest.common.SpeedTestByteEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class SpeedTestByteServer {
    public static int port = 8081;

    public static void main(String[] args) throws Exception{
        SpeedTestByteServer server = new SpeedTestByteServer();
        server.run();
    }

    private void run() throws Exception{
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(workerGroup, bossGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
//                                    new LoggingHandler(LogLevel.INFO),
//                                    new SpeedTestCharEncoder(),
//                                    new SpeedTestCharDecoder(),
//                                    new SpeedTestCharProcessor()
                                    new SpeedTestByteEncoder(),
                                    new SpeedTestByteDecoder(),
                                    new SpeedTestByteProcessor()
                            );
                        }
                    });

            sb.bind(port).sync()
                    .channel().closeFuture().sync();
        }finally{
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
