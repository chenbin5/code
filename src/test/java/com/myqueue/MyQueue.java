package com.myqueue;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 实现队列
 */
public class MyQueue {

    //1.需要一个承装元素的集合
    private final LinkedList<Object> list = new LinkedList<>();
    //2.需要一个计数器，统计加入list集合的个数
    private AtomicInteger count = new AtomicInteger(0);
    //3.需要指定上限和下限
    private final int minSize = 0;
    private final int maxSize;

    //4.构造方法初始化下限
    public MyQueue(int size) {
        this.maxSize = size;
    }
    //5.初始化一个对象，用于加锁
    private final Object lock = new Object();
    //6.实现put()
    public void put(Object object) {
        synchronized (lock) {
            while (count.get() == this.maxSize) {
                try {
                    lock.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //添加元素
            list.add(object);
            count.incrementAndGet();  //计数器+1
            //通知另一个线程，唤醒
            lock.notify();  //考虑容易为空得时候
            System.out.println("新加入的元素为：" + object);
        }
    }
    //7.实现take()
    public Object take() {
        Object object = null;
        synchronized (lock) {
            while (count.get() == minSize) {
                try {
                    lock.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //移除元素
            object = list.removeFirst();
            //计数器递减
            count.decrementAndGet();
            //唤醒另一个线程
            lock.notify();  //考虑容器为满得时候
        }
        return object;
    }
    public int getSize() {
        return this.count.get();
    }
    public static void main(String[] args) {

        final MyQueue myQueue = new MyQueue(5);
        myQueue.put("a");
        myQueue.put("b");
        myQueue.put("c");
        myQueue.put("d");
        myQueue.put("e");

        System.out.println("当前容器得长度为：" + myQueue.getSize());

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                myQueue.put("f");
                myQueue.put("g");
            }
        },"t1");
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Object o1 = myQueue.take();
                System.out.println("移除的元素为：" + o1);
                Object o2 = myQueue.take();
                System.out.println("移除的元素为：" + o2);
            }
        },"t2");
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        t2.start();
    }
}
