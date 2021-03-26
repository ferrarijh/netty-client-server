package com.example.echo.client;

import com.example.echo.model.Person;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.io.Serializable;
import java.util.List;

public class PersonToByteEncoder extends MessageToByteEncoder<Person>{
    protected void encode(ChannelHandlerContext channelHandlerContext, Person person, ByteBuf byteBuf) throws Exception {

    }
}
