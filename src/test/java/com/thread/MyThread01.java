package com.thread;

public class MyThread01 extends Thread {

    private int count = 5;

    public synchronized void run() {
        count--;
        System.out.println(this.currentThread().getName() + " count = " + count);
    }

    public static void main(String[] args) {

        MyThread01 myThread01 = new MyThread01();

        Thread t1 = new Thread(myThread01,"t1");
        Thread t2 = new Thread(myThread01,"t2");
        Thread t3 = new Thread(myThread01,"t3");
        Thread t4 = new Thread(myThread01,"t4");
        Thread t5 = new Thread(myThread01,"t5");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}
