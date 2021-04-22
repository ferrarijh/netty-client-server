package com.example.gateway.server;

import com.example.gateway.GatewayMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class EndServerHandler extends ReplayingDecoder<Void> {

    private final Charset charset = StandardCharsets.UTF_8;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int msgLen = 2* GatewayMessage.ID_LEN + GatewayMessage.MESSAGE_LEN;
        String msg = in.readCharSequence(msgLen, charset).toString();

        String newMsgShort = msg.trim() + " from EndServer";
        char[] newMsgArr = new char[msgLen];
        for(int i=0; i<msgLen; i++)
            newMsgArr[i] = i < newMsgShort.length()? newMsgShort.charAt(i) : ' ';

        ByteBuf bb = Unpooled.buffer();
        bb.writeCharSequence(new String(newMsgArr), charset);
        ctx.channel().writeAndFlush(bb);
    }
}
