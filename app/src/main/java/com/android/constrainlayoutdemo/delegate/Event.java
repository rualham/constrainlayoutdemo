package com.android.constrainlayoutdemo.delegate;

import java.lang.reflect.Method;

public class Event {
    private Object object;
    private String methodName;
    private Object[] params;
    private Class[] paramTypes;

    public Event() {
    }

    public Event(Object object, String methodName, Object... args) {
        this.object = object;
        this.methodName = methodName;
        this.params = args;
        contractParamTypes(this.params);
    }

    //根据参数数组生成参数类型数组
    private void contractParamTypes(Object[] params) {
        this.paramTypes = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            this.paramTypes[i] = params[i].getClass();
        }
    }

    public Object getObject() {
        return object;
    }

    public void setParamTypes(Class[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public void invoke() throws Exception {
        Method method=object.getClass().getMethod(this.methodName,this.paramTypes);
        if(null==method){
            return;
        }
        method.invoke(this.getObject(),this.params);
    }
}
