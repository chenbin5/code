package com.waitAndnotify;

import java.util.ArrayList;
import java.util.List;

/**
 * wait和notify关键字的使用
 */
public class ListAdd2 {

    private volatile static List list = new ArrayList();

    public void add() {
        list.add("bjsxt");
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {

        final ListAdd2 listAdd2 = new ListAdd2();
        final Object lock = new Object();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (lock) {
                        for (int i = 0; i < 10; i++) {
                            listAdd2.add();
                            System.out.println("当前线程：" + Thread.currentThread().getName() + "添加了一个元素..");
                            Thread.sleep(500);
                            if (listAdd2.size() == 5) {
                                System.out.println("已经发出通知..");
                                lock.notify();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        if (listAdd2.size() != 5) {
                            lock.wait();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("当前线程：" + Thread.currentThread().getName() + "收到通知，线程停止..");
                    throw new RuntimeException();
                }
            }
        },"t2");

        t2.start();
        t1.start();
    }
}
