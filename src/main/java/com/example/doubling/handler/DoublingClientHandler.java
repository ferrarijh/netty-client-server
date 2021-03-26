package com.example.doubling.handler;

import com.example.doubling.model.RequestData;
import com.example.doubling.model.ResponseData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DoublingClientHandler extends ChannelInboundHandlerAdapter {

    //create new message and flush - inbound handler can also flush
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[ClientHandler-req] flushing 1~10");

        for(int i=1; i<10; i++){
            ctx.writeAndFlush(new RequestData(i, "REQ"));
        }
    }

    //read inbound message
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ResponseData res = (ResponseData) msg;

        System.out.print("[ClientHandler-res] ");
        res.show();
    }

    //데이터가 분리된 패킷으로 들어오면 (ex.전송된 4*9가 4*7 + 4*2로 들어오는 경우) 첫 패킷 받고 닫혀버림
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.close();
//    }
}