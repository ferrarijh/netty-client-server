package com.example.utils;

public class TRGenerator {
    public static String generate(int len){
        String lenStr = String.valueOf(len);
        int lenStrLen = lenStr.length();

        StringBuilder sb = new StringBuilder();

        for(int i=0; i<4-lenStrLen; i++)
            sb.append("0");
        sb.append(lenStr);

        for(int i=0; i<len-4; i++)
            sb.append((char)(65+i%5));

        return sb.toString();
    }
}
