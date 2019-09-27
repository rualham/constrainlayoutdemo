package com.android.constrainlayoutdemo;

import android.app.Activity;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;

public class LottieActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);
        LottieAnimationView lottieAnimationView = (LottieAnimationView) findViewById(R.id.lottieAnimationView);
        lottieAnimationView.setImageAssetsFolder("splash/img_0.png");
//        lottieAnimationView.setAnimation("splash/animation_data_0.json");
        lottieAnimationView.setAnimation("data.json");
        lottieAnimationView.loop(true);
        lottieAnimationView.playAnimation();

    }
}
