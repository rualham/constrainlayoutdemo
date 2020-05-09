package com.android.sharedemo.annotationAndDelegate;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class InjectUtils {

    public static void injectEvent(Activity activity) {
        Class<? extends Activity> cls = activity.getClass();
        Method[] declaredMethods = cls.getDeclaredMethods();

        for (Method method : declaredMethods) {
            //获得方法上的所有注解
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                //注解类型
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType.isAnnotationPresent(EventType.class)) {
                    EventType eventType = annotationType.getAnnotation(EventType.class);
                    Class listenerType = eventType.listenerType();//interface android.view.View$OnClickListener
                    String listenerSetter = eventType.listenerSetter();//setOnClickListener
                    try {
                        Method valueMethod = annotationType.getDeclaredMethod("value");
                        int[] viewIds = (int[]) valueMethod.invoke(annotation);
                        method.setAccessible(true);
                        ListenerInvocationHandler handler = new ListenerInvocationHandler(activity, method, listenerType);
//                        Object object1 = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, handler);
                        Object object = handler.getProxyInstance();

                         /*  findViewById(R.id.tv_annotation1).setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {

                                  }
        });*/
                        // 遍历注解的值
                        for (int viewId : viewIds) {
                            // 获得当前activity的view（赋值）
                            View view = activity.findViewById(viewId);
                            // 执行方法 listenerSetter=setOnClickListener listenerType=interface android.view.View$OnClickListener
                            //setter=public void android.view.View.setOnClickListener(android.view.View$OnClickListener)
                            Method setter = view.getClass().getMethod(listenerSetter, listenerType);
                            //view对应要执行的哪个类，object对应的是参数
                            setter.invoke(view, object);
//                            setter.invoke(view, activity);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

//    static class ListenerInvocationHandler<T> implements InvocationHandler {
//        /*持有的真实对象*/
//        private T factory;
//        private Method method;
//
//        public ListenerInvocationHandler(T factory, Method method) {
//            this.factory = factory;
//            this.method = method;
//        }
//
////    /*通过Proxy获得动态代理对象*/
////    public  Object getProxyInstance(){
////        return Proxy.newProxyInstance(factory.getClass().getClassLoader(),
////                factory.getClass().getInterfaces(),this);
////    }
//
//        @Override
//        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//            return this.method.invoke(factory, args);
//        }
//    }
}
