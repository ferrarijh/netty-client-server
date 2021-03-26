package com.example.doubling.handler;

import com.example.doubling.model.ResponseData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DoublingResponseReplayingDecoder extends ReplayingDecoder<Void> {
    private static final Charset charset = StandardCharsets.UTF_8;

    private Integer readInt = null;
    private Integer strLen = null;
//    private String str = null;

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(readInt == null && in.readableBytes() >= 4)
            readInt = in.readInt();
        else if (strLen == null && in.readableBytes() >= 4)
            strLen = in.readInt();
        else if (in.readableBytes() >= strLen){
            String readStr = in.readCharSequence(strLen, charset).toString();
            out.add(new ResponseData(readInt, readStr));
            readInt = null;
            strLen = null;
        }
    }
}
