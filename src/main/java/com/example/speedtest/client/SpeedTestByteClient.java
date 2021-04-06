package com.example.speedtest.client;

import com.example.speedtest.common.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SpeedTestByteClient {
    public static String remote = "localhost";
    public static int port = 8081;

    public static void main(String[] args) throws Exception{

        new SpeedTestByteClient().run();
    }

    private void run() throws Exception{
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer< SocketChannel >(){
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
//                                    new LoggingHandler(LogLevel.INFO),
//                                    new SpeedTestCharDecoder(),
//                                    new SpeedTestCharEncoder()
                                    new SpeedTestByteDecoder(),
                                    new SpeedTestByteEncoder()
                            );
                            ch.pipeline().addLast(
                                    "handler", new SpeedTestByteClientHandler(100000)
                            );
                        }
                    });

            b.connect(remote, port).sync()
                    .channel().closeFuture().sync();

//            ChannelFuture cf = b.connect(remote, port).sync();
//            cf.channel().pipeline().replace(
//                    "handler", "handler", new SpeedTestClientHandler(100));
//            cf.channel().closeFuture().sync();
//
//            cf = b.connect(remote, port).sync();
//            cf.channel().pipeline().replace(
//                    "handler", "handler", new SpeedTestClientHandler(1000));
//            cf.channel().closeFuture().sync();
//
//            cf = b.connect(remote, port).sync();
//            cf.channel().pipeline().replace(
//                    "handler", "handler", new SpeedTestClientHandler(100));
//            cf.channel().closeFuture().sync();

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            workerGroup.shutdownGracefully();
        }
    }
}
