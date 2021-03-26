package com.example.echo.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class EchoServerHandler2 extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        int size = in.readableBytes();
        System.out.println("[channelRead()-h2]h2 received("+size+"): " + in.toString(CharsetUtil.UTF_8));
//        ctx.write(in);  //does NOT request flush() so be sure to call flush() for actual transport of all pending data
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        long time = System.currentTimeMillis();
        System.out.println("[channelReadComplete()-h2]["+time+"] at "+this.hashCode()+" | "+Thread.currentThread().getId()+"\n");
//        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)    //TODO("??")
//                .addListener(ChannelFutureListener.CLOSE);  //close channel after flush
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
