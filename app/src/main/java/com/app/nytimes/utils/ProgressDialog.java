package com.app.nytimes.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;

import com.app.nytimes.R;

import java.util.Objects;

public class ProgressDialog {

    public static Dialog getProgressDialog(Context context, boolean cancelable){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(LayoutInflater.from(context).inflate(R.layout.progress_dialog, null));
        dialog.setCancelable(cancelable);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

}
