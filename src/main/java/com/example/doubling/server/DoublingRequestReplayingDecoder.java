package com.example.doubling.server;

import com.example.doubling.model.RequestData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

//limited operations are allowed for ReplayingDecoder (vs ByteToMessageDecoder)
public class DoublingRequestReplayingDecoder extends ReplayingDecoder<Void> {

    private final Charset charset = StandardCharsets.UTF_8;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        int intVal = in.readInt();
        int strLen = in.readInt();
        String cs = in.readCharSequence(strLen, charset).toString();
        System.out.println("[RequestDecoder] intVal="+intVal+", strLen="+strLen);
        RequestData data = new RequestData(intVal, cs);
        out.add(data);
    }
}
