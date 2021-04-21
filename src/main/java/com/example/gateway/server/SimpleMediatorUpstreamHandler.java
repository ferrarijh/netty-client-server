package com.example.gateway.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

public class SimpleMediatorUpstreamHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private final String id;
    public SimpleMediatorUpstreamHandler(String _id){
        this.id = _id;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelRepository.putUpChannel(this.id, ctx.channel());

        ByteBuf bb = ctx.channel().alloc().buffer();
        bb.writeCharSequence("Hello from MediatorServer", StandardCharsets.UTF_8);
        ctx.writeAndFlush(bb);

        System.out.println("["+Thread.currentThread().getName()+"]");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("(channelRead0) msg: "+msg.toString(StandardCharsets.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("(channelReadComplete)");
    }
}
