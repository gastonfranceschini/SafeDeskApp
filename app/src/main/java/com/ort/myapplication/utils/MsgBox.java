package com.ort.myapplication.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.ort.myapplication.MainActivity;

public class MsgBox {

    void show(String title, String message)
    {
       /*dialog = new AlertDialog.Builder(getApplicationContext()) // Pass a reference to your main activity here
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialog.cancel();
                    }
                })
                .show();

*/
    }

    private AlertDialog dialog;
}

