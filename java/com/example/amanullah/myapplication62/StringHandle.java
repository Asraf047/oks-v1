package com.example.amanullah.myapplication63;

public class StringHandle {
    public static boolean isNotNullAndEmpty(String string){
        if(string == null)  return false;
        if(string.equals("")) return false;
        return true;
    }
}
