package com.android.constrainlayoutdemo;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.airbnb.lottie.LottieAnimationView;
import com.android.constrainlayoutdemo.utils.ChannelUtil;

import java.io.IOException;
import java.util.zip.ZipFile;

public class LottieActivity extends Activity {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);
        try {
            new ZipFile("f").getComment();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LottieAnimationView lottieAnimationView = (LottieAnimationView) findViewById(R.id.lottieAnimationView);
        lottieAnimationView.setImageAssetsFolder("splash/img_0.png");
//        lottieAnimationView.setAnimation("splash/animation_data_0.json");
        lottieAnimationView.setAnimation("data.json");
        lottieAnimationView.loop(true);
        lottieAnimationView.playAnimation();
        ApplicationInfo appInfo = null;
        try {
            appInfo = this.getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String channelId = appInfo.metaData.getString("CHANNEL_NAME");
        String a = ChannelUtil.getChannel(this);
    }
}
