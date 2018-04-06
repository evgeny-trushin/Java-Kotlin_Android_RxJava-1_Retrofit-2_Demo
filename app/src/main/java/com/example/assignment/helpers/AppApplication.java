package com.example.assignment.helpers;

import android.app.Application;

public class AppApplication extends Application {
    private final static String TAG = AppApplication.class.getSimpleName();

    private static AppApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        AppApplication.instance = this;
    }

    public static AppApplication getAppContext() {
        return (AppApplication) instance.getApplicationContext();
    }
}
