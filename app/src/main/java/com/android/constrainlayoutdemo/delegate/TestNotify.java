package com.android.constrainlayoutdemo.delegate;

import java.util.Date;

public class TestNotify {
    public static void main(String[] args) {
        //创建一个尽职尽责的放哨者
        Notifier notifier = new GoodNotifier();
        //创建一个玩游戏的同学，开始玩游戏
        PlayingGameListener playingGameListener = new PlayingGameListener();
        //创建一个看电视的同学，开始看电视
        WatchingTVListener watchingTVListener = new WatchingTVListener();
        //玩游戏的同学告诉放哨的同学，老师来了告诉一下
        notifier.addListener(playingGameListener, "stopPlayingGame", new Date());
        //看电视的同学告诉放哨的同学，老师来了告诉一下
        notifier.addListener(watchingTVListener, "stopWatchingTV", new Date());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifier.notifyX();
    }
}
