package com.example.speedtest.common;

import com.example.speedtest.model.SpeedTestCharData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.List;

public class SpeedTestCharDecoder extends ByteToMessageDecoder {
    public static final Charset charset = CharsetUtil.UTF_8;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//        System.out.println("(decode) readable bytes="+in.readableBytes());
        char[] header = in.readCharSequence(SpeedTestCharData.HEADER_LEN, charset).toString().toCharArray();
        char[] body = in.readCharSequence(SpeedTestCharData.BODY_LEN, charset).toString().toCharArray();
        out.add(new SpeedTestCharData(header, body));
    }
}