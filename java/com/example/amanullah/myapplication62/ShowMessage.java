package com.example.amanullah.myapplication63;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class ShowMessage {
    private AlertDialog.Builder alertDialogBuilder;

    public void showMessage(Context context, String title, String message, DialogInterface.OnClickListener onClickListener){
        alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("OK",onClickListener);

        alertDialogBuilder.show();
    }
}
