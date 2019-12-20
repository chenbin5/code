package com.waitAndnotify;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 实现线程之间实时通信
 */
public class ListAdd3 {

    private volatile static List list = new ArrayList();
    public void add() {
        list.add("bjsxt");
    }
    public int size() {
        return list.size();
    }

    public static void main(String[] args) {

        final ListAdd3 listAdd3 = new ListAdd3();
        //final Object lock = new Object();
        //实现线程之间的实时通信
        final CountDownLatch countDownLatch = new CountDownLatch(1);  //参数代表唤醒的次数

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                   //synchronized (lock) {
                        for (int i = 0; i < 10; i++) {
                            listAdd3.add();
                            System.out.println("当前线程：" + Thread.currentThread().getName() + "添加了一个元素..");
                            Thread.sleep(500);
                            if (listAdd3.size() == 5) {
                                System.out.println("已经发出通知..");
                                countDownLatch.countDown();
                                //lock.notify();
                            }
                        }
                   //}
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                //synchronized (lock) {
                    try {
                        if (listAdd3.size() != 5) {
                            //lock.wait();
                            countDownLatch.await();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("当前线程：" + Thread.currentThread().getName() + "收到通知，线程停止..");
                    throw new RuntimeException();
                //}
            }
        },"t2");

        t2.start();
        t1.start();
    }

}
