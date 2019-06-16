package com.app.nytimes.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.widget.Toast;

import com.app.nytimes.R;

import java.util.Objects;

/**
 * This is a helper class contains all helper method commonly used in app
 * */

public class AppHelper {

    public static void openUrl(Context context, String url){

        if(TextUtils.isEmpty(url)){
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //First looking for chrome
        intent.setPackage("com.android.chrome");

        try {
            if(intent.resolveActivity(context.getPackageManager()) != null){
                context.startActivity(intent);
            }else {
                // Chrome is probably not installed
                // Try with the default browser
                intent.setPackage(null);
                if(intent.resolveActivity(context.getPackageManager()) != null){
                    context.startActivity(intent);
                }else {
                    showMsg(context, R.string.no_browser_installed_message);
                }
            }
        } catch (ActivityNotFoundException e) {
            showMsg(context, R.string.no_browser_installed_message);
        }

    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void showMsg(Context context, @StringRes int msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
    public static void showMsg(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
