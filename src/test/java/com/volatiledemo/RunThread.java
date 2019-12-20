package com.volatiledemo;

public class RunThread extends Thread {

    private volatile boolean isRunning = true;
    private void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
    public void run() {
        System.out.println("进入run方法...");
        while(isRunning == true) {
            //...
        }
        System.out.println("线程停止");
    }
    public static void main(String[] args) throws Exception{
        RunThread runThread = new RunThread();
        runThread.start();
        Thread.sleep(3000);
        runThread.setRunning(false);
        System.out.println("isRunning的值已经修改为false");
        Thread.sleep(1000);
        System.out.println(runThread.isRunning);
    }
}
