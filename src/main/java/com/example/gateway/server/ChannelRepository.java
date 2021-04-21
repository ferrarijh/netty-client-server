package com.example.gateway.server;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

public class ChannelRepository {
    private static volatile ConcurrentHashMap<String, Channel> upChannels;
    private static volatile ConcurrentHashMap<String, Channel> downChannels;

    public static ConcurrentHashMap<String, Channel> getUpChannels(){
        if(upChannels == null)
            synchronized(ChannelRepository.class){
                if(upChannels == null)
                    upChannels = new ConcurrentHashMap<>();
            }
        return upChannels;
    }

    public static ConcurrentHashMap<String, Channel> getDownChannels(){
        if(downChannels == null)
            synchronized(ChannelRepository.class){
                if(downChannels == null)
                    downChannels = new ConcurrentHashMap<>();
            }
        return downChannels;
    }

    public static void putUpChannel(String key, Channel c){
        getUpChannels().put(key, c);
    }

    public static Channel getUpChannel(String key){
        return getUpChannels().get(key);
    }


    public static void putDownChannel(String key, Channel c){
        getDownChannels().put(key, c);
    }

    public static Channel getDownChannel(String key){
        return getDownChannels().get(key);
    }
}
