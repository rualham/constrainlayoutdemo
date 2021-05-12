package com.android.sharedemo.thread_test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class NewThread extends Thread {

    static class UserThread extends Thread {
        @Override
        public void run() {
            super.run();
            System.out.println("i am a extend thread ");
        }
    }

    static class RunnableThread implements Runnable {
        @Override
        public void run() {
            System.out.println("i am a Runnable thread ");
        }
    }

    static class CallableThread implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("I am implements Callable");
            return "i am a Callable thread";
        }
    }

    /*实现Callable接口，允许有返回值*/
    private static class UseCall implements Callable<String>{

        @Override
        public String call() throws Exception {
            System.out.println("I am implements Callable");
            return "CallResult";
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Thread thread = new UserThread();
        thread.start();
        new Thread(new RunnableThread()).start();
        CallableThread callableThread=new CallableThread();
        FutureTask<String> futureTask=new FutureTask(callableThread);
        new Thread(futureTask).start();
        futureTask.get();

//        UseCall useCall = new UseCall();
//        FutureTask<String> futureTask1 = new FutureTask<>(useCall);
//        new Thread(futureTask1).start();
        //do my work
        //.....
        System.out.println(futureTask.get());
    }
}
