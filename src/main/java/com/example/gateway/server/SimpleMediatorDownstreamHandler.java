package com.example.gateway.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SimpleMediatorDownstreamHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        ByteBuf bb = ctx.channel().alloc().buffer();
        bb.writeBytes(msg);
        ctx.writeAndFlush(bb);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelRepository.putUpChannel(ctx.channel().id().toString(), ctx.channel());

        String msg = "[" + Thread.currentThread().getName() + "]";
        System.out.println(msg);
    }
}
