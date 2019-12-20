package com.queue;

import java.util.concurrent.LinkedBlockingQueue;

public class UserQueue1 {

    public static void main(String[] args) throws Exception{

        /*ArrayBlockingQueue<String> array = new ArrayBlockingQueue<>(5);
        array.offer("a");
        array.offer("b");
        array.offer("c");
        array.offer("d");
        array.offer("e");
        System.out.println(array.offer("f",2, TimeUnit.SECONDS));
        array.remove();
        System.out.println(array.poll());*/
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
    }
}
