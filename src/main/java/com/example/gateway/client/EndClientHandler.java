package com.example.gateway.client;

import com.example.gateway.GatewayMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class EndClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private final Charset charset = StandardCharsets.UTF_8;
    private Scanner scanner = new Scanner(System.in);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("(channelRead0) " + msg.toString(StandardCharsets.UTF_8));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf bb = ctx.alloc().buffer();

        String msg = getMsgFromInput();
//        String msg = "C1C2 from C1";
//        String msg = "C2C1 from C2";

        int newMsgLen = 2* GatewayMessage.ID_LEN + GatewayMessage.MESSAGE_LEN;
        char[] newMsgArr = new char[newMsgLen];
        for(int i=0; i<newMsgLen; i++)
            newMsgArr[i] = i<msg.length()? msg.charAt(i) : ' ';
        String newMsgStr = new String(newMsgArr);

        System.out.println("["+newMsgStr+"]");

        bb.writeCharSequence(newMsgStr, StandardCharsets.UTF_8);

        ctx.writeAndFlush(bb)
//                .addListener((ChannelFutureListener) future -> {
//                    sendMessage(ctx);
//                })
        ;
    }

    private String getMsgFromInput(){
        System.out.print("msg: ");
        return scanner.nextLine();
    }
}