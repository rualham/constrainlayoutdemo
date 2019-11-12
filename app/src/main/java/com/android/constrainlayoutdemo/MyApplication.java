package com.android.constrainlayoutdemo;

import android.app.Application;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HookUtils.hookClassLoader(this);
    }
}
