package com.android.sharedemo.annotationAndDelegate;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.android.sharedemo.R;

public class AnnotationTestActivity extends Activity {

    @OnMyClickField(value = {R.id.tv_annotation1, R.id.tv_annotation2}, text = {"v_annotation1", "df"})
    String myOnclickField;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        InjectUtils.injectEvent(this);
    }

    //    @Lance(value = 1, id = "2")
    @OnClick(value = {R.id.tv_annotation1, R.id.tv_annotation2}, text = {"v_annotation1", "df"})
    private void thisClick(View view) {
        System.out.println("view = " + view);
        switch (view.getId()) {
            case R.id.tv_annotation1:
                Toast.makeText(AnnotationTestActivity.this, "我是tv_annotation1", Toast.LENGTH_LONG).show();
                break;
            case R.id.tv_annotation2:
                Toast.makeText(AnnotationTestActivity.this, "我是tv_annotation2", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(AnnotationTestActivity.this, "我是tv_annotation3", Toast.LENGTH_LONG).show();
        }
    }
}
