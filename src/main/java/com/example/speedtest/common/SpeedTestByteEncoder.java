package com.example.speedtest.common;

import com.example.speedtest.model.SpeedTestByteData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class SpeedTestByteEncoder extends MessageToByteEncoder<SpeedTestByteData> {
    @Override
    protected void encode(ChannelHandlerContext ctx, SpeedTestByteData msg, ByteBuf out) throws Exception {
        byte[] header = msg.getHeader();
        byte[] body = msg.getBody();
        out.writeBytes(header);
        out.writeBytes(body);
    }
}
