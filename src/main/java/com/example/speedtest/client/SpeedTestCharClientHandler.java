package com.example.speedtest.client;

import com.example.speedtest.GlobalTimer;
import com.example.speedtest.model.SpeedTestByteData;
import com.example.speedtest.model.SpeedTestCharData;
import com.example.utils.StringGenerator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

public class SpeedTestCharClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        char[] header = StringGenerator.generateAlpha(SpeedTestCharData.HEADER_LEN).toCharArray();
        char[] body = StringGenerator.generateNum(SpeedTestCharData.BODY_LEN).toCharArray();
        SpeedTestCharData data = new SpeedTestCharData(header, body);

        System.out.println("(channelActive)");

        GlobalTimer.setTimeActive();

        int gCnt = GlobalTimer.MAX;
        for(int i=0; i<gCnt; i++){
            ctx.writeAndFlush(data);
            GlobalTimer.increm();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        SpeedTestData data = (SpeedTestData) msg;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        long timeSpan = GlobalTimer.getTimeSpan();
        System.out.println("(channelReadComplete-CharClientHandler-"+Thread.currentThread().getName()+") "+ timeSpan /1000+'.'+ timeSpan %1000+"s ("+GlobalTimer.cnt+")");
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        System.out.println("(channelInactive) "+GlobalTimer.cnt);
    }
}
