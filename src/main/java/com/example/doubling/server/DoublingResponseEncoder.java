package com.example.doubling.server;

import com.example.doubling.model.ResponseData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class DoublingResponseEncoder extends MessageToByteEncoder<ResponseData> {
    private final Charset charset = StandardCharsets.UTF_8;

    protected void encode(ChannelHandlerContext ctx, ResponseData responseData, ByteBuf out) throws Exception {
        System.out.print("[ResponseEncoder] writeInt() with ");
        responseData.show();
        System.out.println();

        int resInt = responseData.getIntVal();
        String resStr = responseData.getStringVal();

        out.writeInt(resInt);
        out.writeInt(resStr.length());
        out.writeCharSequence(resStr, charset);
    }
}
