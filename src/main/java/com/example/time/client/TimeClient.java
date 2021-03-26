package com.example.time.client;

import com.example.time.handler.TimeClientHandlerComplex;
import com.example.time.handler.TimeClientHandlerSimple;
import com.example.time.handler.TimeDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient {

    private int port = 8080;
    private String host = "localhost";

    public static void main(String[] args) throws Exception{

        (new TimeClient()).run();
    }

    private void run() throws Exception{
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            sc.pipeline().addLast(
                                    new TimeDecoder(),
                                    new TimeClientHandlerSimple()
                            );
                        }
                    });

            ChannelFuture f = b.connect(host, port).sync();   //b.bind()... for server
            f.channel().closeFuture().sync();
        }finally{
            workerGroup.shutdownGracefully();
        }
    }
}
