package com.example.doubling.client;

import com.example.doubling.model.RequestData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class DoublingRequestEncoder extends MessageToByteEncoder<RequestData> {

    private final Charset charset = StandardCharsets.UTF_8;

    protected void encode(ChannelHandlerContext ctx, RequestData msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getIntVal());
        out.writeInt(msg.getStringVal().length());
        out.writeCharSequence(msg.getStringVal(), charset);

        System.out.print("[RequestEncoder]");
        msg.show();
    }
}
