package com.android.sharedemo.utils;

import android.util.Log;

/**
 * Created by J!nl!n on 2016-5-11 11:43:01.
 * Copyright © 1990-2015 J!nl!n™ Inc. All rights reserved.
 * <p/>
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 */
public final class L {
    private static final String TAG = "lvmama";
    private static boolean DEBUG = false;// 是否打印日志

    public static final void setDebug(boolean debug) {
        DEBUG = debug;
        if (DEBUG) {
            Logger.init(TAG).setMethodCount(0).hideThreadInfo()
                    .setLogLevel(L.DEBUG ? Logger.LogLevel.FULL : Logger.LogLevel.NONE);
        }
    }

    public static void v(String msg) {
        if (DEBUG) {
            Logger.v(getTagName(), msg);
        }
    }

    public static void d(String msg) {
        if (DEBUG) {
            Logger.d(getTagName(), msg);
        }
    }

    public static void i(String msg) {
        if (DEBUG) {
            Logger.i(getTagName(), msg);
        }
    }

    public static void w(String msg) {
        if (DEBUG) {
            Logger.w(getTagName(), msg);
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            Logger.e(getTagName(), msg);
        }
    }

    public static void json(String msg) {
        if (DEBUG) {
            Logger.json(msg);
        }
    }

    public static void e(String msg, Exception tr) {
        if (DEBUG) {
            Logger.e(getTagName(), msg, tr);
        }
    }

    private static String getTagName() {
        return TAG;
    }

    public static void i(String tag, String message) {
        if (DEBUG) {
            Logger.i(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (DEBUG) {
            Logger.d(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (DEBUG) {
            Logger.e(tag, message);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            Log.d(tag, msg, tr);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            Log.i(tag, msg, tr);
        }
    }
}