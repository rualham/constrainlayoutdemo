package com.android.sharedemo;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sharedemo.view.SuffixEditText;

import java.util.concurrent.locks.ReentrantLock;

public class ViewStubActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewStub viewStub;// 占位控件
    private Button bt_show;// 显示按钮
    private Button bt_hide;// 隐藏按钮
    private TextView tv_show_title;// 标题
    private SuffixEditText suffixEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stub);
        viewStub = (ViewStub) findViewById(R.id.viewStub);// 寻找控件
        bt_show = (Button) findViewById(R.id.bt_show);
        bt_hide = (Button) findViewById(R.id.bt_hide);
        suffixEditText = (SuffixEditText) findViewById(R.id.suffixEditText);
        suffixEditText.setSuffix("cm");
        bt_show.setOnClickListener(this);
        bt_hide.setOnClickListener(this);
     /*   bt_hide.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                float a = v.getX();
                float b = event.getX();
                float c = event.getY();
                return false;
            }
        });*/
    }

    private static class MyThread implements Runnable {
        private ReentrantLock lock = new ReentrantLock();

        @Override
        public void run() {
            lock.lock();
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println("ReentrantLockDemo = " + Thread.currentThread().getName() + ":" + i);
                    Log.e("ReentrantLockDemo", Thread.currentThread().getName() + ":" + i);
                }
            } finally {
                lock.unlock();
            }
        /*    synchronized (this) {
                for (int i = 0; i < 10; i++) {
                    System.out.println("ReentrantLockDemo = " + Thread.currentThread().getName() + ":" + i);
                    Log.e("ReentrantLockDemo", Thread.currentThread().getName() + ":" + i);
                }
            }*/
        }
    }

    static class Panzi<T> {
        private T item;

        public Panzi(T item) {
            this.item = item;
        }

        public void set(T t) {
            this.item = t;
        }

        public T get() {
            return item;
        }
    }

    static class Fruit {

    }

    static class Apple extends Fruit {

    }

    static class Banana extends Fruit {

    }

    public static void main(String[] args) {
        Panzi<? extends Fruit> panzi1 = new Panzi<Apple>(new Apple());
        Fruit fruit = panzi1.get();
        Panzi<? super Fruit> panzi2 = new Panzi<Fruit>(new Banana());
        panzi2.set(new Banana());
        Object fruit1 = panzi2.get();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_show:// 显示
                try {
                    View titleBar = viewStub.inflate();// 第二次加载会抛出异常
                    tv_show_title = (TextView) titleBar.findViewById(R.id.tv_show_title);
                    tv_show_title.setText("Title");
                } catch (Exception e) {
                    viewStub.setVisibility(View.VISIBLE);
                }
               /* viewStub.setVisibility(View.VISIBLE);// 方式二
                if (tv_show_title == null) {
                    tv_show_title = (TextView) findViewById(R.id.tv_show_title);
                    tv_show_title.setText("Title");
                }*/
                break;
            case R.id.bt_hide:// 隐藏
                viewStub.setVisibility(View.GONE);
                Runnable t1 = new MyThread();
                new Thread(t1, "t1").start();
                new Thread(t1, "t2").start();
                new Thread(t1, "t3").start();
                new Thread(t1, "t4").start();
                new Thread(t1, "t5").start();
                new Thread(t1, "t6").start();
                new Thread(t1, "t7").start();

                Panzi<? extends Fruit> panzi1 = new Panzi<Apple>(new Apple());
                Fruit fruit = panzi1.get();
                Panzi<? super Apple> panzi2 = new Panzi<Fruit>(new Banana());
                panzi2.set(new Apple());
                Object fruit1 = panzi2.get();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(ViewStubActivity.this, "子线程弹出Toast", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }).start();
        new Thread() {
            @Override
            public void run() {
                super.run();
            }
        }.start();
//        thread.start();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }
}
