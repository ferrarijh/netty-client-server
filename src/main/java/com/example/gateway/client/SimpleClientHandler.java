package com.example.gateway.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class SimpleClientHandler extends SimpleChannelInboundHandler<String> {
    private final Charset charset = StandardCharsets.UTF_8;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buf = Unpooled.buffer();
        buf.writeCharSequence("Hello World", charset);
        ctx.writeAndFlush(buf);
    }
}