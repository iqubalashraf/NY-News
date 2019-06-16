package com.app.nytimes.utils;

import android.content.res.Resources;

public class Constants {

    public static final String SERVER_BASE_URL = "https://api.nytimes.com/";
    public static final String API_KEY = "eek2SSIFAR4ZY10em0egzcoWw5RVTGtQ";
    public static int screenWidth = getScreenWidth();
    public static int screenHeight = getScreenHeight();

    private static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    private static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
