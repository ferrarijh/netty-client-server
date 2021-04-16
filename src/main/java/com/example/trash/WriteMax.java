package com.example.trash;

import com.example.speedtest.GlobalTimer;
import jdk.nashorn.internal.objects.Global;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class WriteMax {
    public static void main(String[] args) {
        File f = new File("C:\\Users\\ferra\\Downloads\\develop\\data.txt");
        try{
            PrintWriter pw = new PrintWriter(new FileWriter(f, true));
            for(int i = 0; i< GlobalTimer.MAX; i++)
                pw.append("1234ABCDABCDABCDABCDABCDABCDABCD");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
