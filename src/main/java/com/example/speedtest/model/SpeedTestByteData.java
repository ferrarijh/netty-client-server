package com.example.speedtest.model;

public class SpeedTestByteData{

    public static final int HEADER_LEN = 4;
    public static final int BODY_LEN = 28;

    private final byte[] header;
    private final byte[] body;

    public SpeedTestByteData(byte[] header, byte[] body) throws Exception{
        if (header.length != HEADER_LEN || body.length != BODY_LEN)
            throw new Exception();

        this.header = header;
        this.body = body;
    }

    public void show(){
        for (byte c: header){
            System.out.print(c);
        }
        for(byte c: body){
            System.out.print(c);
        }
        System.out.println();
    }

    public byte[] getBody() {
        return body;
    }

    public byte[] getHeader() {
        return header;
    }
}
