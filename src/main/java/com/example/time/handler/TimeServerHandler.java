package com.example.time.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

//server pushes out (int)time in seconds for $outputCnt times
public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    private static String TAG = "[ServerHandler]";

    private int outputCnt = 3;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(TAG+"ctx.channel="+ctx.channel().hashCode());
        ByteBuf bb = ctx.alloc().buffer(4*outputCnt);


        int currentTimeSeconds = (int) ((System.currentTimeMillis()/1000L) + 2208988800L);

        for(int i=0; i<outputCnt; i++)
            bb.writeInt(currentTimeSeconds);

        System.out.println(TAG+currentTimeSeconds);

        //TODO("ctx.writeAndFlush(bb) writes & flushes on separate buffer from bb?")
        ctx.writeAndFlush(bb)
                .addListener(ChannelFutureListener.CLOSE);
    }
}
