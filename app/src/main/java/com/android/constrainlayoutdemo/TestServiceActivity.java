package com.android.constrainlayoutdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class TestServiceActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chain_test);
        //可以启动多次，每启动一次，就会新建一个work thread，但IntentService的实例始终只有一个
        //Operation 1
        Intent startServiceIntent = new Intent("com.test.intentService");
        Bundle bundle = new Bundle();
        bundle.putString("param", "oper1");
        startServiceIntent.putExtras(bundle);
        startServiceIntent.setPackage(getPackageName());
        startService(startServiceIntent);

        //Operation 2
        Intent startServiceIntent2 = new Intent("com.test.intentService");
        Bundle bundle2 = new Bundle();
        bundle2.putString("param", "oper2");
        startServiceIntent2.putExtras(bundle2);
        startServiceIntent2.setPackage(getPackageName());
        startService(startServiceIntent2);
    }
}
