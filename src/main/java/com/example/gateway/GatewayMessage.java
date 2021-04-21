package com.example.gateway;

import java.util.ArrayList;

public class GatewayMessage {
    public static final int ID_LEN = 4;
    public static final int CONTENT_LEN = 28;

    String srcId;
    ArrayList<String> destId;
    String content;

    public GatewayMessage(String _src, ArrayList<String> _dst, String _content){
        this.srcId = _src;
        destId = _dst;
        content = _content;
    }
}
