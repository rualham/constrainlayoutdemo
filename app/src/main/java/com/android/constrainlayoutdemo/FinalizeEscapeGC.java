package com.android.constrainlayoutdemo;

public class FinalizeEscapeGC {
    public static FinalizeEscapeGC SAVE_HOOK = null;

    public void isAlive() {
        System.out.println("yes, I am still alive :) -- " + SAVE_HOOK);
    }

    //重写finalize方法，该方法只被调用一次，但并不是调用后立刻被回收
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
        FinalizeEscapeGC.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGC();
        /*
        拯救成功
         */
        SAVE_HOOK = null;
        //提醒虚拟机进行垃圾回收，但是虚拟机具体什么时候进行回收就不知道了
        System.gc();

        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("No, I am dead :(");
        }

        /*
        拯救失败
         */
        SAVE_HOOK = null;
        System.gc();
        //finalize方法的优先级比较低所以等待它0.5秒
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("No, I am dead :(");
        }
    }
}

