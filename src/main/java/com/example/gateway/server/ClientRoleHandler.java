package com.example.gateway.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientRoleHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private final String host;
    private final int port;

    public ClientRoleHandler(String host, int port){
        this.host = host;
        this.port = port;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Bootstrap b = new Bootstrap();
        b.group(ctx.channel().eventLoop())
                .channel(NioSocketChannel.class)
                .handler(
                        new SimpleChannelInboundHandler<ByteBuf>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                ctx.writeAndFlush(msg);
                            }
                        }
                );
        System.out.println(host+port);
        b.connect(host, port).sync()
                .channel().closeFuture().sync();


    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        ctx.writeAndFlush(msg);
    }
}
