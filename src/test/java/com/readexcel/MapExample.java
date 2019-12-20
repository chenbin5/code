package com.readexcel;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class MapExample {

    private Map<Object,Object> map = new HashMap<>();

    private Hashtable hashtable = new Hashtable();

    public void put(Object key,Object value) {
       // final ReentrantLock lock = this.lock;
        map.put(key,value);
        hashtable.put(key,value);
    }

    public static void main(String[] args) {

        final MapExample example = new MapExample();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                example.put("1","aaa");
            }
        },"t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                example.put("1","aaa");
            }
        },"t2");

        t1.start();
        t2.start();
    }
}
