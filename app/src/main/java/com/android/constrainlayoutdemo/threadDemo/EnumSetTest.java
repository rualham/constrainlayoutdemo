package com.android.constrainlayoutdemo.threadDemo;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;

public class EnumSetTest {
    public static void main(String[] args) {
        //1.创建一个包含Session（枚举类）里所有枚举值的EnumSet集合
        EnumSet e1 = EnumSet.allOf(Session.class);
        System.out.println("e1 = " + e1);

        //2.创建一个空EnumSet
        EnumSet e2 = EnumSet.noneOf(Session.class);
        System.out.println("e2 = " + e2);

        EnumSet e3 = EnumSet.of(Session.AUTUMN, Session.SPRING, Session.WINTER);
        System.out.println("e3 = " + e3);

        EnumSet e4 = EnumSet.range(Session.SPRING, Session.AUTUMN);
        System.out.println("e4 = " + e4);

        EnumSet e5 = EnumSet.complementOf(e4);
        System.out.println("e5 = " + e5);

        Collection c = new HashSet();
        c.add(Session.AUTUMN);
        c.add(Session.WINTER);
        c.add(Session.FAIL);
        c.add("Java");
        EnumSet e6 = EnumSet.copyOf(c);
        System.out.println("e6 = " + e6);
    }

    enum Session {
        SPRING,
        SUMMER,
        AUTUMN,
        FAIL,
        WINTER
    }
}
