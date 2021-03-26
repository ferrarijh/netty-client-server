package com.example.echo.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

@ChannelHandler.Sharable    //instance of this class is sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    //called when server receives msg. (when incoming data is detected at inbound buffer)
    //actual business logic?
    //messages are NOT released automatically after channelRead() returns.
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        int size = in.readableBytes();
        System.out.println("[channelRead()]h1 received("+size+"): " + in.toString(CharsetUtil.UTF_8));
        ctx.write(in);  //does NOT request flush() so be sure to call flush() for actual transport of all pending data
    }

    //called when channelRead() is complete.
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        long time = System.currentTimeMillis();
        System.out.println("[channelReadComplete()-h1]["+time+"] at "+this.hashCode()+" | "+Thread.currentThread().getId()+"\n");
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)    //TODO("??")
                .addListener(ChannelFutureListener.CLOSE);  //close channel after flush
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
