package com.example.time.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

//simply print out received (Object)message
public class TimeClientHandlerSimple extends ChannelInboundHandlerAdapter {
    private static String TAG = "[ClientHandlerSimple]";
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(TAG+"ctx.channel="+ctx.channel().hashCode());
        ByteBuf msgBb = (ByteBuf) msg;
        try{
            long currentTimeMillis = (msgBb.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println(TAG+new Date(currentTimeMillis));
        }finally{
            msgBb.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
