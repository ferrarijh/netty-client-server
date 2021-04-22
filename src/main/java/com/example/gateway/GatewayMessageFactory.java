package com.example.gateway;

import java.util.ArrayList;

import static com.example.gateway.GatewayMessage.MESSAGE_LEN;
import static com.example.gateway.GatewayMessage.ID_LEN;

public class GatewayMessageFactory {
    public static GatewayMessage create(String src, ArrayList<String> dst, String msg){

        if(src.length() != ID_LEN || msg.length() != MESSAGE_LEN)
            throw new IllegalArgumentException("please check argument length");
        for(String d: dst){
            if(d.length() != ID_LEN)
                throw new IllegalArgumentException("please check argument length");
        }

        return new GatewayMessage(src, dst, msg);
    }
}
