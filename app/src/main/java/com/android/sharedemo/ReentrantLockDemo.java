package com.android.sharedemo;

public class ReentrantLockDemo {
    public static void main(String[] args) {
        Runnable t1 = new MyThread();
        new Thread(t1, "t1").start();
        new Thread(t1, "t2").start();
    }

    private static class MyThread implements Runnable {
        @Override
        public void run() {
            synchronized (this) {
                for (int i = 0; i < 10; i++) {
                    System.out.println("ReentrantLockDemo = " + Thread.currentThread().getName() + ":" + i);
//                    Log.e("ReentrantLockDemo",Thread.currentThread().getName() + ":" + i);
                }
            }
        }
    }
}
