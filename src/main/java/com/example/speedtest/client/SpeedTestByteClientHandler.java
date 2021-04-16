package com.example.speedtest.client;

import com.example.speedtest.GlobalTimer;
import com.example.speedtest.model.SpeedTestByteData;
import com.example.speedtest.model.SpeedTestCharData;
import com.example.utils.StringGenerator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import jdk.nashorn.internal.objects.Global;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SpeedTestByteClientHandler extends ChannelInboundHandlerAdapter {

    byte[] header;
    byte[] body;
    Scanner scanner = new Scanner(System.in);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.header = StringGenerator.generateAlpha(SpeedTestCharData.HEADER_LEN).getBytes(StandardCharsets.UTF_8);
        this.body = StringGenerator.generateAlpha(SpeedTestCharData.BODY_LEN).getBytes(StandardCharsets.UTF_8);
        SpeedTestByteData data = new SpeedTestByteData(header, body);

        System.out.println("(channelActive)");

        GlobalTimer.setTimeActive();

        //wait for input
//        System.out.println("waiting for input..");
//        scanner.nextLine();

        int gCnt = GlobalTimer.MAX;
        for(int i=0; i<gCnt; i++) {
            ctx.writeAndFlush(data);
            GlobalTimer.increm();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        long timeSpan = System.currentTimeMillis() - GlobalTimer.getTimeActive();
        System.out.println("(channelReadComplete-ByteClientHandler-"+Thread.currentThread().getName()+") "+ timeSpan /1000+'.'+ timeSpan %1000+"s ("+GlobalTimer.cnt+")");

//        GlobalTimer.init();
        ctx.close();
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        System.out.println(GlobalTimer.cnt);
    }
}
