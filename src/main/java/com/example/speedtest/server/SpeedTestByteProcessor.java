package com.example.speedtest.server;

import com.example.speedtest.GlobalTimer;
import com.example.speedtest.model.SpeedTestByteData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SpeedTestByteProcessor extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        GlobalTimer.setTimeActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SpeedTestByteData data = (SpeedTestByteData) msg;

        ctx.writeAndFlush(msg);

        if (GlobalTimer.cnt != GlobalTimer.MAX)
            GlobalTimer.increm();
        if (GlobalTimer.cnt == GlobalTimer.MAX) {
            GlobalTimer.increm();

            long span = System.currentTimeMillis() - GlobalTimer.getTimeActive();
            System.out.println("(ByteProcessor) " + span/1000+"."+span%1000);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {

        long timeLast = System.currentTimeMillis();
        long timeSpan = timeLast - GlobalTimer.getTimeActive();
        System.out.println("(channelReadComplete-ByteProcessor-"+Thread.currentThread().getName()+") "+ timeSpan /1000+'.'+ timeSpan %1000+"s("+GlobalTimer.cnt+")");

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);

        long timeSpan = System.currentTimeMillis() - GlobalTimer.getTimeActive();
        System.out.println("(channelUnregistered-ByteProcessor-"+Thread.currentThread().getName()+") "+ timeSpan /1000+'.'+ timeSpan %1000+"s("+GlobalTimer.cnt+")");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        long timeSpan = System.currentTimeMillis() - GlobalTimer.getTimeActive();
        System.out.println("(channelInactive-ByteProcessor-"+Thread.currentThread().getName()+") "+ timeSpan /1000+'.'+ timeSpan %1000+"s("+GlobalTimer.cnt+")");
    }
}
