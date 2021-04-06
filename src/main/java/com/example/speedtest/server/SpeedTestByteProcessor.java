package com.example.speedtest.server;

import com.example.speedtest.model.SpeedTestByteData;
import com.example.speedtest.model.SpeedTestCharData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SpeedTestByteProcessor extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SpeedTestByteData data = (SpeedTestByteData) msg;
        ctx.writeAndFlush(msg);
    }
}
