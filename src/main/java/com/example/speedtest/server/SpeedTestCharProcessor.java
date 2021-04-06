package com.example.speedtest.server;

import com.example.speedtest.model.SpeedTestCharData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SpeedTestCharProcessor extends ChannelInboundHandlerAdapter{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SpeedTestCharData data = (SpeedTestCharData) msg;
        ctx.writeAndFlush(msg);
    }
}
