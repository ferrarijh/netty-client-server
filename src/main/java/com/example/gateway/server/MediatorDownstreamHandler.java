package com.example.gateway.server;

import com.example.gateway.GatewayMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.example.gateway.GatewayMessage.ID_LEN;
import static com.example.gateway.GatewayMessage.MESSAGE_LEN;

public class MediatorDownstreamHandler extends ReplayingDecoder<Void> {

    private final Charset charset = StandardCharsets.UTF_8;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        String msg = "[" + Thread.currentThread().getName() + "]";
        System.out.println(msg);
    }

    //decode incoming data from client
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int msgLen = 2* GatewayMessage.ID_LEN + GatewayMessage.MESSAGE_LEN;
        String msg = in.readCharSequence(msgLen, charset).toString();

        String srcChannelId = msg.substring(0, 2);
//        String dstUpChannelId = msg.substring(2, 4);
        Channel dstUpChannel = ChannelRepository.getUpChannel("E1");

        ChannelRepository.putDownChannel(srcChannelId, ctx.channel());

        System.out.println(ChannelRepository.getUpChannel("E1"));

        ByteBuf bb = Unpooled.buffer();
        bb.writeCharSequence(msg, charset);
        dstUpChannel.writeAndFlush(bb);
    }
}