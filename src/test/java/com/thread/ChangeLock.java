package com.thread;

public class ChangeLock {

    private String lock = "lock";
    public void method() {
        //如果直接用字符串常量当做锁，只能保证一个线程执行。
        synchronized (lock) {
            try {
                while(true) {
                    System.out.println("当前线程：" + Thread.currentThread().getName() + "开始。");
                    lock = "change lock";
                    Thread.sleep(1000);
                    System.out.println("当前线程：" + Thread.currentThread().getName() + "结束。");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public static void main(String[] args) {
        final ChangeLock changeLock = new ChangeLock();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                changeLock.method();
            }
        },"t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                changeLock.method();
            }
        },"t2");

        t1.start();
        t2.start();
    }
}
