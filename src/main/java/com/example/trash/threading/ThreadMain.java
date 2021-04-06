package com.example.trash.threading;

public class ThreadMain {
    public static void main(String[] args) throws InterruptedException {
        ThreadData td = new ThreadData();

        Thread t1 = new Thread(() -> {
            for(int i=0;i<10000; i++){
                td.increm();
            }
        });

        Thread t2 = new Thread(() -> {
            for(int i=0;i<10000; i++){
                td.increm();
            }
        });

        Thread t3 = new Thread(()-> System.out.println(td.cnt));

        t1.start();
        t2.start();
        t2.join();
        t3.start();

    }
}
