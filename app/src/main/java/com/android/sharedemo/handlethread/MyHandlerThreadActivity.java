package com.android.sharedemo.handlethread;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.sharedemo.R;
import com.android.sharedemo.utils.DateUtil;

public class MyHandlerThreadActivity extends AppCompatActivity implements Handler.Callback {

    private int j = 1;
    private String note;
    private TextView note_text;
    private Handler workHandler;
    private Handler uiHandler = new Handler(this);
    ;
    private HandlerThread handlerThread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread);
        initView();
        initHandlerThread();
    }

    private void initHandlerThread() {

        //step1: 创建HandlerThread实例，传入的参数为线程名称
        handlerThread = new HandlerThread("based on yourself");

        //step2: 手动调用start()开启线程
        handlerThread.start();

        //step3:
        //创建Handler,关联HandlerThread的Looper
        //复写handleMessage根据消息更新UI布局，处理消息的线程即是创建的线程handlerThread
        workHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(final Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        break;
                    case 2:
                        for (int i = 1; i < 7; i++) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            note = msg.obj + "卖出" + i + "张票";
//                            Bundle bundle = new Bundle();
//                            bundle.putString("key", note);
//                            msg.setData(bundle);
                            Message startMsg = uiHandler.obtainMessage(1, note);
                            uiHandler.sendMessage(startMsg);
                            Log.d("workHandler----", note);
                          /*  uiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    note_text.setText(note);
                                }
                            });*/
                        }
                        break;
                }
            }
        };
    }

    private void initView() {
        note_text = (TextView) findViewById(R.id.note_text);
        Button startThread = (Button) findViewById(R.id.start_thread);
        startThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //step4: 点击一次Button,工作线程workHandler向工作线程队列发送消息
                Message msg = Message.obtain(); //不用new一个Message,采用obtain
                msg.what = 2;
                msg.obj = "窗口" + j;
                workHandler.sendMessage(msg);
                j++;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //step5: 停止handlerThread
        handlerThread.quit(); //结束线程,效率高
        handlerThread.quitSafely(); //结束线程
    }

    @Override
    public boolean handleMessage(Message msg) {
        note_text.setText(msg.obj + " ");
        return false;
    }
}
