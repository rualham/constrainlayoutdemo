package com.android.constrainlayoutdemo.threadDemo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadUnSecurity {
    static int tickets = 10;

    class SellTickets implements Runnable {
        Lock lock = new ReentrantLock();

        @Override
        public void run() {
            while (tickets > 0) {
                try {
                    lock.lock();
                    if (tickets <= 0) {
                        return;
                    }
                    System.out.println(Thread.currentThread().getName() + "--->售出第：  " + tickets + " 票");
                    tickets--;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    lock.unlock();
                }
            }
            if (tickets <= 0) {
                System.out.println(Thread.currentThread().getName() + "--->售票结束！");
            }
        }
    }

    public static void main(String[] args) {
        SellTickets sell = new ThreadUnSecurity().new SellTickets();
        Thread thread1 = new Thread(sell, "1号窗口");
        Thread thread2 = new Thread(sell, "2号窗口");
        Thread thread3 = new Thread(sell, "3号窗口");
        Thread thread4 = new Thread(sell, "4号窗口");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();


    }
}
