package com.android.sharedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.android.sharedemo.view.MyView;

/**
 * @author zkk
 * 简书:    http://www.jianshu.com/u/61f41588151d
 * github: https://github.com/panacena
 */
public class SimpleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // activity_simple.xml中 lottie_fileName="data.json"
        // 所以只需要在 app/src/main/assets 中添加AE 生成的 json文件，重命名为data.json就可以显示动画
        setContentView(R.layout.activity_simple);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("sleep_test", "准备更新text");
                ((TextView) findViewById(R.id.tv_tv)).setText("update btn text");
                Log.d("sleep_test", "更新text完成");
            }
        }, 100);

        Log.d("sleep_test", "准备sleep30秒");
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("sleep_test", "sleep30秒完成");

        Log.d("sleep_test", "first update");
        ((MyView) findViewById(R.id.tv_tv)).setText("This is the first update");

        findViewById(R.id.red).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.i("--------", "touch  yelloe  " + motionEvent);
                return false;
            }
        });
        findViewById(R.id.green).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.i("--------", "touch  green  " + motionEvent);
                return false;
            }
        });
        findViewById(R.id.tv_tv).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.i("--------", "touch  red  " + motionEvent);
                return false;
            }
        });
        findViewById(R.id.tv_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SimpleActivity.this, DemoActivity.class));
                try {
                    Thread.sleep(20_0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        System.out.println("dispatchTouchEvent ============= " + ev);
//        return false;
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        System.out.println("onTouchEvent ============= " + event);
        return super.onTouchEvent(event);
    }
}
