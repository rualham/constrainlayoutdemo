package com.android.sharedemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class LeakedActivity extends Activity {
    //    private static final String TAG = "MainActivity";
    private String TAG = "MainActivity";
    private static Context myContext;
    //    ClassDemo demo = new ClassDemo();
    private Handler mHandler;
    boolean mStartThreadFlag = true;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);
        Log.e(TAG, "onCreate: ");
        myContext = this;
        mHandler=new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    i++;
                }
            }
        }).start();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mHandler.postDelayed(this,100);
            }
        }, 100);
    }

    protected void onDestroy() {

        super.onDestroy();
        if(mHandler!=null){
            mHandler.removeCallbacksAndMessages(null);
        }
        Log.e(TAG, "onDestroy: ");
//        mContext = null;

    }
}

class ClassDemo {

    private static final String TAG = "ClassDemo";

    public int[] arr = new int[10000];

    public ClassDemo() {

        Log.d(TAG, "ClassDemo: ");

    }

}

