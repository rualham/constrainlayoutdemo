package com.android.javapoetdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.lang.reflect.Modifier;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      /*  MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .build();*/

        ClassName activity = ClassName.get("android.app", "Activity");
       /* TypeSpec.Builder mainActivityBuilder = TypeSpec.classBuilder("MainActivity")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .superclass(activity);*/
    }
}
