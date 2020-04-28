package com.android.sharedemo.handlethread;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.sharedemo.MainActivity;
import com.android.sharedemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HandlerThreadActivity extends AppCompatActivity implements Handler.Callback {

    @BindView(R.id.tv_start_msg)
    TextView mTvStartMsg;
    @BindView(R.id.tv_finish_msg)
    TextView mTvFinishMsg;
    @BindView(R.id.btn_start_download)
    Button mBtnStartDownload;

    private Handler mUIHandler;
    private DownloadThread mDownloadThread;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread_test);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mUIHandler = new Handler(this);
        mDownloadThread = new DownloadThread("下载线程");
        mDownloadThread.setUIHandler(mUIHandler);
        mDownloadThread.setDownloadUrls("http://pan.baidu.com/s/1qYc3EDQ", "http://bbs.005.tv/thread-589833-1-1.html", "http://list.youku.com/show/id_zc51e1d547a5b11e2a19e.html?");
        mTvStartMsg = (TextView) findViewById(R.id.tv_start_msg);
        mTvFinishMsg = (TextView) findViewById(R.id.tv_finish_msg);
        mBtnStartDownload = (Button) findViewById(R.id.btn_start_download);
        findViewById(R.id.btn_hand_thread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HandlerThreadActivity.this, MyHandlerThreadActivity.class));
            }
        });
        mBtnStartDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDownloadThread.start();
                mBtnStartDownload.setText("下载中");
                mBtnStartDownload.setEnabled(false);
            }
        });
    }

   /* @OnClick(R.id.btn_start_download)
    public void startDownload() {
        mDownloadThread.start();
        mBtnStartDownload.setText("下载中");
        mBtnStartDownload.setEnabled(false);
    }*/

    @Override
    public boolean handleMessage(final Message msg) {
        switch (msg.what) {
            case DownloadThread.TYPE_FINISHED:
                mTvFinishMsg.setText(mTvFinishMsg.getText().toString() + "\n " + msg.obj);
                break;
            case DownloadThread.TYPE_START:
                mTvStartMsg.setText(mTvStartMsg.getText().toString() + "\n " + msg.obj);
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDownloadThread.quitSafely();
    }
}
