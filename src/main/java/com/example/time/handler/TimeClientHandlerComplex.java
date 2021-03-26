package com.example.time.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

//expects to receive (int)time in seconds and
public class TimeClientHandlerComplex extends ChannelInboundHandlerAdapter {

    private static final String TAG = "[TimeClientHandler]";
    private ByteBuf tmpBuf;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        tmpBuf = ctx.alloc().buffer(4);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        tmpBuf.release();
        tmpBuf = null;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf bbMsg = (ByteBuf) msg;  //in TCP/IP, Netty reads the data sent from a peer into a ByteBuf.
        tmpBuf.writeBytes(bbMsg);
        bbMsg.release();

        while(tmpBuf.readableBytes()>=4){
            long ctm = (tmpBuf.readUnsignedInt()-2208988800L) * 1000L;
            System.out.println(TAG + new Date(ctm));
//            ctx.close();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
