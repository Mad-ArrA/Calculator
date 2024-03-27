package com.example.calculator.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.calculator.R;

public class MyThemeUtils {

    public static void setTheme(Activity activity) {
        SharedPreferences preferences = activity.getPreferences(Context.MODE_PRIVATE);
        boolean isDarkTheme = preferences.getBoolean("isDarkTheme", false);
        if (isDarkTheme) {
            activity.setTheme(R.style.DarkTheme);
        } else {
            activity.setTheme(R.style.LightTheme);
        }
    }
}
