package com.example.utils;

public class StringGenerator {
    public static String generateAlpha(int len){
        StringBuilder res = new StringBuilder();
        for(int i=0; i<len; i++)
            res.append((char) (65 + i % 5));

        return res.toString();
    }

    public static String generateNum(int len){
        StringBuilder res = new StringBuilder();
        for(int i=0; i<len; i++)
            res.append((char)(48 + i%10));
        return res.toString();
    }
}
