package com.example.lengthfieldbasedframedcoder.client.handler;

import com.example.utils.TRGenerator;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf bb = UnpooledByteBufAllocator.DEFAULT.buffer(1024);
        String outStr = TRGenerator.generate(500);

        for(int i=0; i<3; i++)
            bb.writeBytes(outStr.getBytes());

        ctx.writeAndFlush(bb);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //
    }
}