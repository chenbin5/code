package com.thread;

public class StringLock {

    public void method() {
        //如果直接用字符串常量当做锁，只能保证一个线程执行。
        synchronized (new String("lock")) {
            try {
                while(true) {
                    System.out.println("当前线程：" + Thread.currentThread().getName() + "开始。");
                    Thread.sleep(1000);
                    System.out.println("当前线程：" + Thread.currentThread().getName() + "结束。");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public static void main(String[] args) {
        final StringLock stringLock = new StringLock();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                stringLock.method();
            }
        },"t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                stringLock.method();
            }
        },"t2");

        t1.start();
        t2.start();
    }
}
