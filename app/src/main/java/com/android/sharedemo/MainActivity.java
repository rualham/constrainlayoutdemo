package com.android.sharedemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.sharedemo.annotationAndDelegate.AnnotationTestActivity;
import com.android.sharedemo.handlethread.HandlerThreadActivity;

public class MainActivity extends AppCompatActivity {

    public static FinalizeEscapeGC SAVE_HOOK = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv1 = (TextView) findViewById(R.id.tv1);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * (1)在manifest配置文件中配置了scheme参数
                 * (2)网络端获取url
                 * (3)跳转
                 */
                String url = "scheme://mtime/goodsDetail?goodsId=10011002";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
//                startActivity(new Intent(MainActivity.this, TestServiceActivity.class));
            }
        });
        findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LottieActivity.class));
            }
        });
        TextView tv4 = (TextView) findViewById(R.id.tv4);
        TextView txt_handler_view = (TextView) findViewById(R.id.txt_handler_view);
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SimpleActivity.class));

                SAVE_HOOK = new FinalizeEscapeGC();

        /*
        拯救成功
         */
                SAVE_HOOK = null;
                //提醒虚拟机进行垃圾回收，但是虚拟机具体什么时候进行回收就不知道了
                System.gc();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (SAVE_HOOK != null) {
                    SAVE_HOOK.isAlive();
                } else {
                    System.out.println("No, I am dead :(");
                }

        /*
        拯救失败
         */
                SAVE_HOOK = null;
                System.gc();
                //finalize方法的优先级比较低所以等待它0.5秒
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (SAVE_HOOK != null) {
                    SAVE_HOOK.isAlive();
                } else {
                    System.out.println("No, I am dead :(");
                }
            }
        });
        findViewById(R.id.txt_object_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ObjectAnimatorActivity.class));
            }
        });

        txt_handler_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HandlerThreadActivity.class));
            }
        });

        findViewById(R.id.txt_annotation_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AnnotationTestActivity.class));

            }
        });
    }
}
