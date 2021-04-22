package com.example.gateway.server;

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

public class MediatorUpstreamHandler extends ReplayingDecoder<Void> {
    private final Charset charset = StandardCharsets.UTF_8;
    private final String id;
    public MediatorUpstreamHandler(String _id){
        if(_id.length() != ID_LEN)
            throw new IllegalArgumentException("id length must be "+ID_LEN);
        this.id = _id;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelRepository.putUpChannel(this.id, ctx.channel());

//        ByteBuf bb = ctx.channel().alloc().buffer();
//        bb.writeCharSequence("Hello from MediatorServer", StandardCharsets.UTF_8);
//        ctx.writeAndFlush(bb);

        System.out.println("["+Thread.currentThread().getName()+"]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("(channelReadComplete)");
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        String msg = in.readCharSequence(2*ID_LEN + MESSAGE_LEN, charset).toString();
//        String srcClient = msg.substring(0, 2);
        String dstClient = msg.substring(2, 4);
        Channel dstClientChannel = ChannelRepository.getDownChannel(dstClient);

        ByteBuf bb = Unpooled.buffer();
        bb.writeCharSequence(msg, charset);
        dstClientChannel.writeAndFlush(bb);
    }
}
