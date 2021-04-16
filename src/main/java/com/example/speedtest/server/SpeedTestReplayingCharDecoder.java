package com.example.speedtest.server;

import com.example.speedtest.GlobalTimer;
import com.example.speedtest.model.SpeedTestCharData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.CharsetUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.util.List;

public class SpeedTestReplayingCharDecoder extends ReplayingDecoder<Void> {
    public static final Charset charset = CharsetUtil.US_ASCII;
    int cnt = 0;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        char[] header = in.readCharSequence(SpeedTestCharData.HEADER_LEN, charset).toString().toCharArray();
        char[] body = in.readCharSequence(SpeedTestCharData.BODY_LEN, charset).toString().toCharArray();
        out.add(new SpeedTestCharData(header, body));

//        if (GlobalTimer.cnt != GlobalTimer.MAX)
//            GlobalTimer.increm();
//        if (GlobalTimer.cnt == GlobalTimer.MAX) {
//            GlobalTimer.increm();
//
//            long span = System.currentTimeMillis() - GlobalTimer.getTimeActive();
//            System.out.println("(ByteDecoder) " + span/1000+"."+span%1000);
//        }
    }
}
