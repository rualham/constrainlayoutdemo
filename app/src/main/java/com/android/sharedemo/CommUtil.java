package com.android.sharedemo;

import android.content.Context;

public class CommUtil {
    private static CommUtil instance;
    private Context context;

    private CommUtil(Context context) {
        this.context = context.getApplicationContext();
    }

    public static CommUtil getInstance(Context context) {
        if (null == instance) {
            instance = new CommUtil(context);
        }
        return instance;
    }
}
