package com.example.assignment.helpers;

import android.util.Log;

import com.example.assignment.i002.BuildConfig;

public class CrashlyticsProxy {
    public static <Exception extends Throwable> void eTestFailedReport(String tag, Exception e) {
        try {
            if (BuildConfig.DEBUG) {
                if(null!=e) {
                    Log.e(tag + " | Error", "" + e.getMessage());
                    e.printStackTrace();
                }
            }
            e(tag, e);
        } catch (java.lang.Exception error) {
            if (BuildConfig.DEBUG) {
                Log.e(tag + " | Error", "" + e.getMessage());
                error.printStackTrace();
            }
        }
    }

    public static void eTestFailedReport(String tag, String e) {
        eTestFailedReport(tag,new Exception(e));
    }


    public static <Exception extends Throwable> void e(String tag, Exception e) {
        if (BuildConfig.DEBUG) {
            Log.e(tag + " | Error", "" + e.getMessage());
            try {
                e.printStackTrace();
            } catch (java.lang.Exception ignore) {
            }
        }
    }

    public static <Object> void d(String tag, Object comment) {
        if (BuildConfig.DEBUG) {
            try {
                Log.d(tag, "" + comment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void e(String tag, String comment) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, "" + comment);
        }
    }

}