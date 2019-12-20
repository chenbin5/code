package com.volatiledemo;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileNoAtomic extends Thread{

    //private static volatile int count;
    //推荐使用 AtomicInteger
    private static AtomicInteger count = new AtomicInteger(0);
    private static void addCount() {
        for (int i = 0;i<1000;i++) {
            //count++;
            count.incrementAndGet();  //返回++后的值  推荐使用
        }
        System.out.println(count);
    }

    public void run() {
        addCount();
    }

    public static void main(String[] args) {
        VolatileNoAtomic[] arr = new VolatileNoAtomic[10];
        for (int i = 0;i<10;i++) {
            arr[i] = new VolatileNoAtomic();
        }
        for (int i = 0;i<10;i++) {
            arr[i].start();
        }
    }
}
