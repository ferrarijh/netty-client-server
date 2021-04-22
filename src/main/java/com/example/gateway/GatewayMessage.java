package com.example.gateway;

import java.util.ArrayList;

public class GatewayMessage {
    public static final int ID_LEN = 2;
    public static final int MESSAGE_LEN = 28;

    final String srcId;
    final ArrayList<String> destId;
    final String content;

    public GatewayMessage(String _src, ArrayList<String> _dst, String _content){
        this.srcId = _src;
        destId = _dst;
        content = _content;
    }
}
