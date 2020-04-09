package com.android.sharedemo.threadDemo;

public class SyncThreadTest {

    public static void main(String args[]) {
        final Bank bank = new Bank();
        final MyBank myBank = new MyBank();
        Thread tadd = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    myBank.addMoney(100);
                    myBank.lookMoney();
//                    bank.addMoney(100);
//                    bank.lookMoney();
                    System.out.println("\n");
                }
            }
        });
        Thread tsub = new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (true) {
                    myBank.subMoney(100);
                    myBank.lookMoney();
//                    bank.subMoney(100);
//                    bank.lookMoney();
                    System.out.println("\n");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        tsub.start();
        tadd.start();
    }
}
