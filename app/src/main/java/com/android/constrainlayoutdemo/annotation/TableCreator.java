package com.android.constrainlayoutdemo.annotation;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TableCreator {
    public static String createTableSql(String className) throws ClassNotFoundException {
        Class<?> cl = Class.forName(className);
        DBTable dbTable = cl.getAnnotation(DBTable.class);
        //如果没有表注解，直接返回
        if (dbTable == null) {
            System.out.println("No DBTable annotations in class = " + className);
            return null;
        }
        String tableName = dbTable.name();
        // If the name is empty, use the Class name:
        if (tableName.length() < 1) {
            tableName = cl.getName().toUpperCase();
        }
        List<String> columnDefs = new ArrayList<>();
        //通过Class类API获取到所有成员字段
        for (Field field : cl.getDeclaredFields()) {
            String columnName = null;
            //获取字段上的注解
            Annotation[] anns = field.getDeclaredAnnotations();
            if (anns.length < 1) {
                continue;
            }
            if (anns[0] instanceof SQLInteger) {
                SQLInteger sInt = (SQLInteger) anns[0];
                //获取字段对应列名称，如果没有就是使用字段名称替代
                if (sInt.name().length() < 1) {
                    columnName = field.getName().toUpperCase();
                } else {
                    columnName = sInt.name();
                }
                //构建语句
                columnDefs.add(columnName + " INT" + getConstraints(sInt.constraint()));
            }
            if (anns[0] instanceof SQLString) {
                SQLString sString = (SQLString) anns[0];
                if (sString.name().length() < 1) {
                    columnName = field.getName().toUpperCase();
                } else {
                    columnName = sString.name();
                }
                columnDefs.add(columnName + " VARCHAR(" + sString.value() + ")" +
                        getConstraints(sString.constraint()));
            }
        }
        //数据库表构建语句
        StringBuilder createCommand = new StringBuilder("CREATE TABLE " + tableName + "(");
        for (String columnDef : columnDefs) {
            createCommand.append(" \n  " + columnDef + ",");
        }
        String tableCreate = createCommand.substring(0, createCommand.length() - 1) + ")";
        return tableCreate;
    }

    /**
     * 判断该字段是否有其他约束
     *
     * @param con
     * @return
     */
    private static String getConstraints(Constraints con) {
        String constraints = "";
        if (!con.allowNull()) {
            constraints += " NOT　NULL";
        }
        if (con.primaryKey()) {
            constraints += " PRIMARY KEY";
        }
        if (con.unique()) {
            constraints += " UNIQUE";
        }
        return constraints;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        String[] arg = {"com.android.constrainlayoutdemo.annotation.Member"};
        for (String className : arg) {
            System.out.println("Table Creation SQL for = " + className + " is:\n"
                    + createTableSql(className));
        }
        final CountDownLatch latch = new CountDownLatch(2);
        System.out.println(" 主线程开始执行.... .....");
        //第一个子线程执行
        ExecutorService es1 = Executors.newSingleThreadExecutor();
        es1.execute(new Runnable() {
            @Override
            public void run() {
//                try {
//                    Thread.sleep(3000);
//                    System.out.println("子线程:+ " + Thread.currentThread().getName() + "执行");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println("子线程:+ " + Thread.currentThread().getName() + "执行");
                latch.countDown();
            }
        });
        es1.shutdown();

        //第二个子线程执行

        //第二个子线程执行
        ExecutorService es2 = Executors.newSingleThreadExecutor();
        es2.execute(new Runnable() {
            @Override
            public void run() {
               /* try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                System.out.println("子线程："+Thread.currentThread().getName()+"执行");
                latch.countDown();
            }
        });
        es2.shutdown();
        System.out.println("等待两个线程执行完毕…… ……");

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("两个子线程都执行完毕，继续执行主线程");
    }
}
