package com.example.time.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import sun.rmi.runtime.Log;

import java.util.List;

public class TimeDecoder extends ByteToMessageDecoder {
    private static String TAG = "[TimeDecoder]";

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        System.out.println(TAG+"ctx.channel="+ctx.channel().hashCode());

        System.out.println("decode() called..");
//        while(in.readableBytes()>=4)
        if(in.readableBytes()>=4){
            System.out.println(TAG+"out.add() with "+in.readableBytes()+" readable bytes");
            out.add(in.readBytes(4));
        }
    }
}
