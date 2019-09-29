package com.android.constrainlayoutdemo.threadDemo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyBank {
    private int count = 0;
    private Lock lock = new ReentrantLock();

    //存钱
    public synchronized void addMoney(int money) {
        lock.lock();
        try {
            count += money;
            System.out.println(System.currentTimeMillis() + "存进：" + money);
        } finally {
            lock.unlock();
        }
    }

    //取钱
    public synchronized void subMoney(int money) {
        lock.lock();
        try {
            if (count - money < 0) {
                System.out.println("余额不足");
                return;
            }
            count -= money;
            System.out.println(+System.currentTimeMillis() + "取出：" + money);
        } finally {
            lock.unlock();
        }
    }

    //查询
    public void lookMoney() {
        System.out.println("账户余额：" + count);
    }
}
