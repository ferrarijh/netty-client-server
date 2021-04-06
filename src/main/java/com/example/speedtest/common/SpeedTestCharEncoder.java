package com.example.speedtest.common;

import com.example.speedtest.model.SpeedTestCharData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class SpeedTestCharEncoder extends MessageToByteEncoder<SpeedTestCharData> {
    @Override
    protected void encode(ChannelHandlerContext ctx, SpeedTestCharData msg, ByteBuf out) throws Exception {
        char[] body = msg.getBody();
        char[] header = msg.getHeader();
        for(char c: body){out.writeChar(c);}
        for(char c: header){out.writeChar(c);}
    }
}
