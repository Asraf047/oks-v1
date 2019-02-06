package com.example.amanullah.myapplication63;

import android.app.AlertDialog;
import android.content.Context;

public class MyProcessingDialog {
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;

    public MyProcessingDialog(Context context, String title, String message){
        alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);
        alertDialog = alertDialogBuilder.create();
    }

    public void show(){
        alertDialog.show();
    }
    public void dismiss(){
        alertDialog.dismiss();
    }


}
