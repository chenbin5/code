package com.masterAndworker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 总任务Master
 */
public class Master {

    //1.有一个承装元素的集合 ConcurrentLinkedQueue
    private ConcurrentLinkedQueue<Task> workQueue = new ConcurrentLinkedQueue<Task>();
    //2.使用普通的HashMap去承装所有的worker子任务
    private HashMap<String,Thread> workers = new HashMap<String,Thread>();
    //3.使用一个容器承装每一个worker并发执行的结果集
    private ConcurrentHashMap<String,Object> resultMap = new ConcurrentHashMap<String,Object>();
    //4.构造方法
    public Master(Worker worker,int workCount) {
        //每一个worker对象都需要Master的引用，workerQueue用于任务的领取，resultMap用于任务的提交
        worker.setWorkerQueue(this.workQueue);
        worker.setResultMap(this.resultMap);
        for (int i = 0; i<workCount; i++) {
            //key标识每一个worker的名字，value标识线程执行对象
            workers.put("子节点" + Integer.toString(i),new Thread(worker));
        }
    }

    //5.提交方法
    public void submit(Task task) {
        this.workQueue.add(task);
    }

    //6.需要一个执行的方法，用于启动应用程序，让所有的worker工作
    public void exacute() {
        for (Map.Entry<String,Thread> map : workers.entrySet()) {
            map.getValue().start();
        }
    }

    //7.判断线程是否执行完毕
    public boolean isComplate() {
        for (Map.Entry<String,Thread> map : workers.entrySet()) {
            if (map.getValue().getState() != Thread.State.TERMINATED) {
                return false;
            }
        }
        return true;
    }

    //8.返回结果集数据
    public int getResult() {
        int ret = 0;
        //把结果集的结果遍历返回
        for (Map.Entry<String,Object> map : resultMap.entrySet()) {
            //汇总结果
            ret += (Integer)map.getValue();
        }
        return ret;
    }
}
