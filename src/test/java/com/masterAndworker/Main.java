package com.masterAndworker;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

         System.out.println("我的机器可用Processors数量：" + Runtime.getRuntime().availableProcessors());
         Random random = new Random();
         Master master = new Master(new Worker(),Runtime.getRuntime().availableProcessors());
         for (int i = 1;i<100;i++) {
             Task task = new Task();
             task.setId(i);
             task.setName("任务"+i);
             task.setPrice(random.nextInt(1000));
             master.submit(task);
         }
         master.exacute();

         long begin = System.currentTimeMillis();
         while (true) {
             if (master.isComplate()) {
                 long end = System.currentTimeMillis() - begin;
                 int price = master.getResult();
                 System.out.println("最终结果：" + price + "，执行耗时：" + end);
                 break;
             }
         }

    }
}
