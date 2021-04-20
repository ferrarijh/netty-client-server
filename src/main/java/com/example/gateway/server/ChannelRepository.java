package com.example.gateway.server;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

public class ChannelRepository {
    private static volatile ConcurrentHashMap<String, Channel> channels;

    public static ConcurrentHashMap<String, Channel> getChannels(){
        if(channels == null)
            synchronized(ChannelRepository.class){
                if(channels == null)
                    channels = new ConcurrentHashMap<>();
            }
        return channels;
    }

    private void put(String key, Channel c){
        channels.put(key, c);
    }

    private Channel get(String key){
        return channels.get(key);
    }
}
