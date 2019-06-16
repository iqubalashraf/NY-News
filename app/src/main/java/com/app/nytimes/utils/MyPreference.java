package com.app.nytimes.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreference {

    private static final String KEY_NEWS_ARTICLE = "com.app.nytimes.utils.KEY_NEWS_ARTICLE";

    private static void setPreference(Context context, String name, String value) {
        if (context == null)
            return;
        SharedPreferences oSharedPreference = context.getSharedPreferences(context.getPackageName(),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor oEditor = oSharedPreference.edit();
        oEditor.putString(name, value);
        oEditor.apply();
    }

    private static String getPreference(Context context, String key, String defaultValue) {
        SharedPreferences oSharedPreference = context.getSharedPreferences(context.getPackageName(),
                Context.MODE_PRIVATE);
        return oSharedPreference.getString(key, defaultValue);
    }

    public static void setNewsArticle(Context context, String subject, String value){
        setPreference(context, KEY_NEWS_ARTICLE + subject, value);
    }

    public static String getNewsArticle(Context context, String subject, String defaultValue){
        return getPreference(context, KEY_NEWS_ARTICLE + subject , defaultValue);
    }

}
