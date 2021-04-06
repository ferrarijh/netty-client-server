package com.example.doubling.server;

import com.example.doubling.model.RequestData;
import com.example.doubling.model.ResponseData;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

//process in inbound handler?
public class DoublingProcessingHandler extends ChannelInboundHandlerAdapter {
    static String TAG = "[ProcessingHandler]";

    //forward to next ChannelInboundHandler
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        RequestData requestData = (RequestData) msg;
        int resInt = requestData.getIntVal()*10;
        String resString = requestData.getStringVal().toLowerCase();

        ResponseData responseData = new ResponseData(resInt, resString);

        System.out.print(TAG+" ctx.writeAndFlush() with ");
        responseData.show();

        ctx.writeAndFlush(responseData);
    }
}
