package com.android.sharedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class DemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // activity_simple.xml中 lottie_fileName="data.json"
        // 所以只需要在 app/src/main/assets 中添加AE 生成的 json文件，重命名为data.json就可以显示动画
        setContentView(R.layout.demo);
        findViewById(R.id.tv_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DemoActivity.this, LeakedActivity.class));
            }
        });
    }
}
