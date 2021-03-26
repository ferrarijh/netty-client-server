package com.example.doubling.model;

public class RequestData {
    private int intVal;
    private String stringVal;

    public RequestData(int n) {
        intVal = n;
    }
    public RequestData(int n, String s){
        intVal = n;
        stringVal = s;
    }

    public int getIntVal(){
        return intVal;
    }
    public String getStringVal(){
        return stringVal;
    }
    public void show(){
        System.out.println("intVal: "+intVal+", stringVal: "+stringVal);
    }
}
