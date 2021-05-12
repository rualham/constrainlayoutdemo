package com.android.javapoetdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Enumeration;

import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClassLoader classLoader = getClassLoader();

        //classLoader： element=》[classes.dex，fix.dex]
        //BootClassLoader
        ClassLoader classLoader1 = Activity.class.getClassLoader();

        System.out.println("getClassLoader:" + classLoader);
        System.out.println("getClassLoader 的父亲 :" + classLoader.getParent());
        System.out.println("Activity.class :" + classLoader1);
        File file = new File(Environment.getExternalStorageDirectory().toString() + File.separator + "lvmama/classes5.dex");
//        File file = new File("sdcard/myclasses.dex");
        if (file.exists()) {
            System.out.println("file = " + file);
        }
        try {
//            DexFile dexFile = new DexFile(Environment.getExternalStorageDirectory().toString() + File.separator + "lvmama/classes5.dex");
            DexFile dexFile = new DexFile("/sdcard/classes5.dex");
            Enumeration<String> entries = dexFile.entries();
            //遍历  dex中所有的Class
            while (entries.hasMoreElements()) {
                String clsName = entries.nextElement();
                System.out.println("clsName = " + clsName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        // 演示classloader
//
        try {
            Class<?> aClass = classLoader.loadClass("com.lvmama.account.login.ILoginProcessor");
            System.out.println(aClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
//
//        try {
//            classLoader.loadClass("android.app.Activity");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        /**
//         * 1、dex路径
//         */
//        PathClassLoader pathClassLoader = new PathClassLoader("/sdcard/myclasses.dex", getClassLoader());
        PathClassLoader pathClassLoader = new PathClassLoader(Environment.getExternalStorageDirectory().toString() + File.separator + "lvmama/classes5.dex", getClassLoader());
        System.out.println("pathClassLoader = " + pathClassLoader);
//
//        // /data/data/packagename : 私有目录
//        // 2: dex优化为odex之后保存的目录，必须是私有目录，不能是sd卡的目录
//        DexClassLoader dexClassLoader = new DexClassLoader("/sdcard/fix.dex", getCodeCacheDir().getAbsolutePath(), null, getClassLoader());
//
//
        try {
            Class<?> aClass = pathClassLoader.loadClass("com.lvmama.account.login.ILoginProcessor");
            System.out.println(aClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
//
//        try {
//            Class<?> aClass = dexClassLoader.loadClass("com.enjoy.enjoyfix.BugPatch");
//            System.out.println(aClass);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }


    }

    public void test(View view) {
//        Bug.test();
    }

    public void fix(View view) {
//        PatchExecutor.patch(this, "/sdcard/fix.dex", new PatchCallBack() {
//            @Override
//            public void onPatchResult(boolean result, String patch) {
//                System.out.println("修复结果:"+result);
//            }
//        });
    }
      /*  MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .build();*/

//        ClassName activity = ClassName.get("android.app", "Activity");
       /* TypeSpec.Builder mainActivityBuilder = TypeSpec.classBuilder("MainActivity")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .superclass(activity);*/
}
