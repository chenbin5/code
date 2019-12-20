package com.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class UseMangCondition {

    private ReentrantLock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();

    public void method1() {
        try {
            lock.lock();
            System.out.println("当前线程：" + Thread.currentThread().getName() + "进入method1方法等待...");
            c1.await();
            System.out.println("当前线程：" + Thread.currentThread().getName() + "method1方法继续...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void method2() {
        try {
            lock.lock();
            System.out.println("当前线程：" + Thread.currentThread().getName() + "进入method2方法等待...");
            c1.await();
            System.out.println("当前线程：" + Thread.currentThread().getName() + "method2方法继续...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void method3() {
        try {
            lock.lock();
            System.out.println("当前线程：" + Thread.currentThread().getName() + "进入method3方法等待...");
            c2.await();
            System.out.println("当前线程：" + Thread.currentThread().getName() + "method3方法继续...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void method4() {
        try {
            lock.lock();
            System.out.println("当前线程：" + Thread.currentThread().getName() + "进入method4方法等待...");
            c1.signal();
            System.out.println("当前线程：" + Thread.currentThread().getName() + "method4方法继续...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void method5() {
        try {
            lock.lock();
            System.out.println("当前线程：" + Thread.currentThread().getName() + "进入method5方法等待...");
            c2.signal();
            System.out.println("当前线程：" + Thread.currentThread().getName() + "method5方法继续...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        final UseMangCondition mangCondition = new UseMangCondition();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                mangCondition.method1();
            }
        },"t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                mangCondition.method2();
            }
        },"t2");

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                mangCondition.method3();
            }
        },"t3");

        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                mangCondition.method4();
            }
        },"t4");

        Thread t5 = new Thread(new Runnable() {
            @Override
            public void run() {
                mangCondition.method5();
            }
        },"t5");

        t1.start();
        t2.start();
        t3.start();
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        t4.start();
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        t5.start();
    }

}
