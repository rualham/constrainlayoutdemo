package com.android.sharedemo;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.sharedemo.utils.ChannelUtil;
import com.bumptech.glide.Glide;
import com.meituan.android.walle.ChannelInfo;
import com.meituan.android.walle.WalleChannelReader;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipFile;

public class LottieActivity extends Activity {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("savedInstanceState = " + savedInstanceState);
                Looper.prepare();
//                Handler handler = new Handler();
                Glide.with(LottieActivity.this).load("http://pics.lvjs.com.cn//uploads/pc/place2/2017-08-09/d63737fa-2d55-412f-85af-ba43aacbe3a2.jpg").into((ImageView) findViewById(R.id.image));
            }
        }).start();

        HandlerThread handlerThread = new HandlerThread("dfj");

        try {
            new ZipFile("f").getComment();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ChannelInfo channelInfo = WalleChannelReader.getChannelInfo(this.getApplicationContext());
        if (channelInfo != null) {
            String channel = channelInfo.getChannel();
            Map<String, String> extraInfo = channelInfo.getExtraInfo();
            System.out.println("channel = " + channel);
            System.out.println("extraInfo = " + extraInfo);
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

    @SafeVarargs
    private static void method(List<String>... strLists) {
//        String path=(@Path String)input;
//        List<@ReadOnly ? extends Person>
        List[] array = strLists;
        List<Integer> tmpList = Arrays.asList(42);
        array[0] = tmpList; //非法操作，但是没有警告
        String s = strLists[0].get(0); //ClassCastException at runtime!
    }

    @Target(ElementType.METHOD)//只能应用于方法上。
    @Retention(RetentionPolicy.RUNTIME)//保存到运行时
    public @interface Test {
    }

    @Target(ElementType.TYPE)//只能应用于类型上，包括类，接口。
    @Retention(RetentionPolicy.RUNTIME)//保存到运行时
    public @interface Table {
        String name() default "";
    }

    @Table(name = "MEMBER")
    public class Member {
        @Test
        public void method() {
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Master {
    }

    @Repeatable(ShuShengs.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface ShuSheng {
        //        String name() ;
        String name() default "bean";

        int age() default 12;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface ShuShengs {
        ShuSheng[] value();
    }

    @Master
    public class AnoBase {

    }

    @ShuSheng(name = "frank", age = 18)
//    @ShuSheng(age = 20)
    @ShuSheng(name = "rual")
    public class AnnotationTest extends AnoBase {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static void getAnnotation() {
        Class<?> cInstance = AnnotationTest.class;
        //获取AnnotationDemo上的重复注解
        ShuSheng[] ssAons = cInstance.getAnnotationsByType(ShuSheng.class);
        System.out.println("重复注解:" + Arrays.asList(ssAons).toString());

        //获取AnnotationDemo上的所有注解，包括从父类继承的
        Annotation[] allAno = cInstance.getAnnotations();
        System.out.println("所有注解:" + Arrays.asList(allAno).toString());

        //判断AnnotationDemo上是否存在Master注解
        boolean isP = cInstance.isAnnotationPresent(Master.class);
        System.out.println("是否存在Master: " + isP);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        getAnnotation();
        return super.onKeyDown(keyCode, event);
    }
}


