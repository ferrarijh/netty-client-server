package com.example.speedtest;

import java.util.concurrent.atomic.AtomicLong;

public class GlobalTimer {
    public static int MAX = 500000;

    private static volatile boolean isActive = false;
    private static volatile long timeActive;
    public static volatile int cnt = 0;

    public static synchronized void setTimeActive(){
        timeActive = System.currentTimeMillis();
    }

    public static long getTimeActive() { return timeActive; }

    public static synchronized long getTimeSpan(){
        return System.currentTimeMillis() - timeActive;
    }

    public static synchronized void increm(){
        cnt++;
    }

    public static synchronized void init(){
        cnt = 0;
    }
}