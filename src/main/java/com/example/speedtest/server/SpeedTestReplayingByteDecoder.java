package com.example.speedtest.server;

import com.example.speedtest.GlobalTimer;
import com.example.speedtest.model.SpeedTestByteData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class SpeedTestReplayingByteDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte[] header = new byte[SpeedTestByteData.HEADER_LEN];
        byte[] body = new byte[SpeedTestByteData.BODY_LEN];
        in.readBytes(header);
        in.readBytes(body);

        out.add(new SpeedTestByteData(header, body));

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
