package com.example.speedtest.common;

import com.example.speedtest.model.SpeedTestByteData;
import com.example.speedtest.model.SpeedTestCharData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class SpeedTestByteDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte[] header = new byte[SpeedTestByteData.HEADER_LEN];
        byte[] body = new byte[SpeedTestByteData.BODY_LEN];
        in.readBytes(header);
        in.readBytes(body);
        out.add(new SpeedTestByteData(header, body));
    }
}
