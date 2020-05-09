package com.android.sharedemo.annotationAndDelegate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class ListenerInvocationHandler implements InvocationHandler {
    /*持有的真实对象*/
    private Object factory;
    private Method method;
    private Class listenerType;

    public ListenerInvocationHandler(Object factory, Method method, Class listenerType) {
        this.factory = factory;
        this.method = method;
        this.listenerType = listenerType;
    }

    //    Object object1 = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, handler);
    /*通过Proxy获得动态代理对象*/
    public Object getProxyInstance() {
        return Proxy.newProxyInstance(listenerType.getClassLoader(),
                new Class[]{listenerType}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        doSthBefore();
        Object result = this.method.invoke(factory, args);
        doSthAfter();
        return result;
//        return this.method.invoke(factory, args);
    }

    /*前置处理器*/
    private void doSthAfter() {
        System.out.println("精美包装，快递一条龙服务");
    }
    /*后置处理器*/

    private void doSthBefore() {
        System.out.println("根据需求，进行市场调研和产品分析");
    }
}
