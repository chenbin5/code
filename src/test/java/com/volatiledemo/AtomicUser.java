package com.volatiledemo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicUser extends Thread{

    private static AtomicInteger count = new AtomicInteger(0);

    public synchronized int multiAdd() {
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        count.addAndGet(1);
        count.addAndGet(2);
        count.addAndGet(3);
        count.addAndGet(4);
        return count.get();
    }

    public static void main(String[] args) {

        final AtomicUser user = new AtomicUser();
        List<Thread> list = new ArrayList<Thread>();
        for (int i=0;i<100;i++) {
            list.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(user.multiAdd());
                }
            }));
        }
        for (Thread thread:list) {
            thread.start();
        }
    }
}
