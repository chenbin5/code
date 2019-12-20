package com.masterAndworker;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * worker子任务
 */
public class Worker implements Runnable{

    private ConcurrentLinkedQueue<Task> workQueue;
    private ConcurrentHashMap<String, Object> resultMap;

    public void setWorkerQueue(ConcurrentLinkedQueue<Task> workQueue) {
        this.workQueue = workQueue;
    }

    public void setResultMap(ConcurrentHashMap<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    @Override
    public void run() {
        while (true) {
            //移除已经执行的元素
            Task input = this.workQueue.poll();
            if (input == null) break;
            //真正的去做业务处理
            Object output = handle(input);
            this.resultMap.put(Integer.toString(input.getId()),output);
        }
    }

    private Object handle(Task input) {
        Object output = null;
        try {
            //对数据源进行处理
            Thread.sleep(500);
            output = input.getPrice();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
}
