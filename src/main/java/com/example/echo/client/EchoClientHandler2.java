package com.example.echo.client;

import com.example.echo.model.Person;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class EchoClientHandler2 extends SimpleChannelInboundHandler<ByteBuf> {
    //called when channel is connected
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        for(int i=0; i<100; i++) {
//        Person p = new Person("Jonathan", 28);

            long time = System.currentTimeMillis();
            System.out.println("["+time+"]");
//            ctx.writeAndFlush(p);
            ctx.writeAndFlush(Unpooled.copiedBuffer("Jonathan!", CharsetUtil.UTF_8));
//        }
    }

    //called upon receiving message
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("[channelRead0()] client received: "+byteBuf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
