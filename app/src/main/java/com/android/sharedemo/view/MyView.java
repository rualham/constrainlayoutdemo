package com.android.sharedemo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MyView extends android.support.v7.widget.AppCompatTextView {
    public MyView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("--------", "MyView onTouchEvent " + event);
        return super.onTouchEvent(event);
    }
}
