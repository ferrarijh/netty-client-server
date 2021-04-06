package com.example.doubling.client;

import com.example.doubling.model.ResponseData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import javax.xml.ws.Response;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DoublingResponseBTMDecoder extends ByteToMessageDecoder {
    private static String TAG = "[ResponseDecoder2]";
    private static Charset charset = StandardCharsets.UTF_8;

    private Integer readInt = null;
    private Integer strLen = null;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("(decode())readable bytes="+in.readableBytes());

//        while(in.readableBytes()>=4){
//            System.out.println(TAG+" readable bytes: "+in.readableBytes());
//            int intIn = in.readInt();
//            ResponseData data = new ResponseData(intIn);
//            out.add(data);
//            System.out.print(TAG);
//            data.show();
//        }

        if(readInt == null && in.readableBytes() >= 4)
            readInt = in.readInt();
        else if (strLen == null && in.readableBytes() >= 4)
            strLen = in.readInt();
        else if (in.readableBytes() >= strLen){
            String readStr = in.readCharSequence(strLen, charset).toString();
            out.add(new ResponseData(readInt, readStr));
            readInt = null;
            strLen = null;
        }
    }
}
