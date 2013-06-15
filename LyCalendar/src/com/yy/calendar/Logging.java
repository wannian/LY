package com.yy.calendar;

import android.util.Log;

public class Logging {
    public static final String LOG_TAG = "yy";

    public static final void v(String msg) {
        if (Log.isLoggable(LOG_TAG, Log.VERBOSE)) {
            Log.v(LOG_TAG, msg);
        }
    }

    public static final void d(String msg) {
        if (Log.isLoggable(LOG_TAG, Log.DEBUG)) {
            Log.d(LOG_TAG, msg == null ? "" : msg);
        }
    }

    public static final void i(String msg) {
        if (Log.isLoggable(LOG_TAG, Log.INFO)) {
            Log.i(LOG_TAG, msg == null ? "" : msg);
        }
    }

    public static final void w(String msg) {
        if (Log.isLoggable(LOG_TAG, Log.WARN)) {
            Log.w(LOG_TAG, msg);
        }
    }

    public static final void w(String msg, Throwable e) {
        if (Log.isLoggable(LOG_TAG, Log.WARN)) {
            Log.w(LOG_TAG, msg, e);
        }
    }

    public static final void e(String msg) {
        if (Log.isLoggable(LOG_TAG, Log.ERROR)) {
            Log.e(LOG_TAG, msg);
        }
    }

    public static final void e(String msg, Throwable e) {
        if (Log.isLoggable(LOG_TAG, Log.ERROR)) {
            Log.e(LOG_TAG, msg, e);
        }
    }
}
