package com.example.speedtest;

import java.util.concurrent.atomic.AtomicLong;

public class GlobalTimer {
    private static long timeActive;
    private static int cnt = 0;
    public static synchronized void setTimeActive(){
        timeActive = System.currentTimeMillis();
    }

    public static synchronized long getTimeActive() {
        return timeActive;
    }
    public static synchronized void increm(){
        cnt++;
    }
    public static synchronized int getCnt(){
        return cnt;
    }
}