package com.android.sharedemo;

import android.app.Application;
import android.util.Log;

import java.lang.reflect.Field;

import static android.support.constraint.Constraints.TAG;

class HookUtils {
    public static void hookClassLoader(Application context) {
        try {
            // 获取Application类的mLoadedApk属性值
            Object mLoadedApk = getFieldValue(context.getClass().getSuperclass(), context, "mLoadedApk");
            if (mLoadedApk != null) {
                // 获取其mClassLoader属性值以及属性字段
                final ClassLoader mClassLoader = (ClassLoader) getFieldValue(mLoadedApk.getClass(), mLoadedApk, "mClassLoader");
                if (mClassLoader != null) {
                    Field mClassLoaderField = getField(mLoadedApk.getClass(), "mClassLoader");
                    // 替换成自己的ClassLoader
                    mClassLoaderField.set(mLoadedApk, new ClassLoader() {
                        @Override
                        public Class<?> loadClass(String name) throws ClassNotFoundException {
                            // 替换Activity
                            if (name.endsWith("MainActivity")) {
                                Log.d(TAG, "loadClass: name = " + name);
                                name = name.replace("MainActivity", "MainActivity2");
                                Log.d(TAG, "loadClass: 替换后name = " + name);
                            }
                            return mClassLoader.loadClass(name);
                        }
                    });
                }
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 反射获取属性值
     *
     * @param c         class
     * @param o         对象
     * @param fieldName 属性名称
     * @return 值
     * @throws NoSuchFieldException   e
     * @throws IllegalAccessException e
     */
    public static Object getFieldValue(Class c, Object o, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = getField(c, fieldName);
        if (field != null) {
            return field.get(o);
        } else {
            return null;
        }
    }

    /**
     * 反射获取对象属性
     *
     * @param aClass    c
     * @param fieldName 属性名称
     * @return 属性
     * @throws NoSuchFieldException e
     */
    private static Field getField(Class<?> aClass, String fieldName) throws NoSuchFieldException {
        Field field = aClass.getDeclaredField(fieldName);
        if (field != null) {
            field.setAccessible(true);
        }
        return field;
    }
}
