package com.example.gateway.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EndServer {
    private String id;
    private final int port = 8081;

    public static void main(String[] args) {
        for(int i=8081; i<=8100; i++)
            new EndServer().run(i);
    }

    public void run(int p){
        this.id = "serverA";

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new LoggingHandler(LogLevel.INFO),
                                    new SimpleChannelInboundHandler<ByteBuf>() {
                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                            ByteBuf bb = Unpooled.buffer();
                                            bb.writeBytes(msg);
                                            ctx.writeAndFlush(bb);
                                        }

                                        @Override
                                        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                            ctx.close();
                                        }
                                    }
                            );
                        }
                    });

            sb.bind(p)
                    .sync()
                    .channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
//            workerGroup.shutdownGracefully();
//            bossGroup.shutdownGracefully();
        }
    }
}
