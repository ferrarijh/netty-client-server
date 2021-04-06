package com.example.speedtest.client;

import com.example.speedtest.GlobalTimer;
import com.example.speedtest.model.SpeedTestByteData;
import com.example.speedtest.model.SpeedTestCharData;
import com.example.utils.StringGenerator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

public class SpeedTestByteClientHandler extends ChannelInboundHandlerAdapter {
//    private long timeActive;
    private final int cnt;

    public SpeedTestByteClientHandler(int cnt){
        this.cnt = cnt;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        char[] header = StringGenerator.generateAlpha(SpeedTestCharData.HEADER_LEN).toCharArray();
//        char[] body = StringGenerator.generateNum(SpeedTestCharData.BODY_LEN).toCharArray();
        byte[] header =  StringGenerator.generateAlpha(SpeedTestCharData.HEADER_LEN).getBytes(StandardCharsets.UTF_8);
        byte[] body = StringGenerator.generateAlpha(SpeedTestCharData.BODY_LEN).getBytes(StandardCharsets.UTF_8);
        SpeedTestByteData data = new SpeedTestByteData(header, body);

        setCurrentTimeMillis();
        System.out.println("(channelActive)");

        for(int i=0; i<cnt; i++) {
            ctx.writeAndFlush(data);
            GlobalTimer.increm();
        }
    }

    public void setCurrentTimeMillis(){
        GlobalTimer.setTimeActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        SpeedTestData data = (SpeedTestData) msg;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        long timeLast = System.currentTimeMillis();
        long timeSpan = timeLast - GlobalTimer.getTimeActive();
        System.out.println("(channelReadComplete) "+ timeSpan /1000+'.'+ timeSpan %1000+"s with "+cnt+"("+GlobalTimer.getCnt()+")");
//        System.out.println("timeActive="+ GlobalTimer.getTimeActive());
//        System.out.println("timeLast="+timeLast);
        ctx.close();
    }

}
