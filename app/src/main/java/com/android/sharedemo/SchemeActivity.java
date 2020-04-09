package com.android.sharedemo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;

public class SchemeActivity extends Activity {
    private static final String TAG = "SchemeActivity";
    private TextView schemeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme);
        schemeTv = (TextView) findViewById(R.id.scheme_tv);
        Uri data = getIntent().getData();
        Log.i(TAG, "host = " + data.getHost() + " path = " + data.getPath() + " query = " + data.getQuery());
        String param = data.getQueryParameter("goodsId");
        schemeTv.setText("获取的参数为：" + param);
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl("file:///android_asset/shiver_me_timbers.gif");
        imPrettySureSortingIsFree();
        findViewById(R.id.scheme_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SchemeActivity.this, SingleActivity.class));
//                optimizeComputeFibonacci(40);
//               computeFibonacci(40);
            }
        });
    }

    public int computeFibonacci(int positionInFibSequence) {
        //0 1 1 2 3 5 8
        if (positionInFibSequence <= 2) {
            return 1;
        } else {
            return computeFibonacci(positionInFibSequence - 1)
                    + computeFibonacci(positionInFibSequence - 2);
        }
    }

    public int optimizeComputeFibonacci(int positionInFibSequence) {
        int prev = 0;
        int current = 1;
        int newValue;
        for (int i = 1; i < positionInFibSequence; i++) {
            newValue = current + prev;
            prev = current;
            current = newValue;
        }
        return current;
    }

    /**
     * 　排序后打印二维数组，一行行打印
     */
    public void imPrettySureSortingIsFree() {
        int dimension = 300;
        int[][] lotsOfInts = new int[dimension][dimension];
        Random randomGenerator = new Random();
        for (int i = 0; i < lotsOfInts.length; i++) {
            for (int j = 0; j < lotsOfInts[i].length; j++) {
                lotsOfInts[i][j] = randomGenerator.nextInt();
            }
        }

        for (int i = 0; i < lotsOfInts.length; i++) {
            String rowAsStr = "";
            //排序
            int[] sorted = getSorted(lotsOfInts[i]);
            //拼接打印
            for (int j = 0; j < lotsOfInts[i].length; j++) {
                rowAsStr += sorted[j];
                if (j < (lotsOfInts[i].length - 1)) {
                    rowAsStr += ", ";
                }
            }
            Log.i("ricky", "Row " + i + ": " + rowAsStr);
        }

        String result = "";

        int size = 100; //MERGE_SIZE 10000

        for (int i = 0; i < size; i++) {

            result += "This is a test\n"; //42行

        }
    }

    public int[] getSorted(int[] input) {
        int[] clone = input.clone();
        Arrays.sort(clone);
        return clone;
    }
}
