package com.example.speedtest.model;

public class SpeedTestCharData {
    public static final int HEADER_LEN = 4;
    public static final int BODY_LEN = 28;

    private final char[] header;
    private final char[] body;

    public SpeedTestCharData(char[] header, char[] body) throws Exception{
        if (header.length != HEADER_LEN || body.length != BODY_LEN)
            throw new Exception();

        this.header = header;
        this.body = body;
    }

    public void show(){
        for (char c: header){
            System.out.print(c);
        }
        for(char c: body){
            System.out.print(c);
        }
        System.out.println();
    }

    public char[] getBody() {
        return body;
    }

    public char[] getHeader() {
        return header;
    }
}
