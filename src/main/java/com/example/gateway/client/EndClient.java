package com.example.gateway.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static com.example.gateway.GatewayMessage.ID_LEN;
import static com.example.gateway.GatewayMessage.MESSAGE_LEN;

public class EndClient {
    private String id;
    private Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        new EndClient().run();
    }

    public void run(){
        getIdInput();

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            Bootstrap b = new Bootstrap();
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new LoggingHandler(LogLevel.INFO),
                                    new EndClientHandler()
                            );
                        }
                    });


            b.connect("localhost", 8080).sync()
                    .channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
            workerGroup.shutdownGracefully();
        }
    }

    private void getIdInput(){
        System.out.print("client id: ");
        String newId = sc.nextLine();
        if(newId.length() != ID_LEN)
            throw new IllegalArgumentException("invalid id length (must be 4");
        this.id = newId;
    }
//
//    private void sendMessage(ChannelHandlerContext ctx){
//
//        System.out.print("destination id(length=4): ");
//        String id = sc.nextLine();
//        while(id.length() != 4){
//            System.out.print("destination id(length=4): ");
//            id = sc.nextLine();
//        }
//
//        System.out.print("message: ");
//        String msgTmp = sc.nextLine();
//        if(msgTmp.equals("exit"))
//            return;
//        String msg = String.format("%1$"+MESSAGE_LEN+"s", msgTmp);
//
//        ctx.writeAndFlush(id+msg);
//
//        sendMessage(ctx);
//    }
}
