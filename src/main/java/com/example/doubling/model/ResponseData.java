package com.example.doubling.model;

public class ResponseData {
    private final int intVal;
    private final String stringVal;

    public ResponseData(){
        this.intVal = -1;
        this.stringVal = "";
    }
    public ResponseData(int n, String s){intVal = n; stringVal = s;}

    public int getIntVal(){
        return intVal;
    }
    public String getStringVal(){return stringVal;}

    public void show(){
        System.out.println("intVal="+ intVal+", stringVal="+stringVal);
    }
}
