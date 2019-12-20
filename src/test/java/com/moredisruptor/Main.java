package com.moredisruptor;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;
import com.newdisruptor.Trade;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws Exception{

        long beginTime = System.currentTimeMillis();
        int bufferSize = 1024;
        ExecutorService executorService =  Executors.newFixedThreadPool(8);
        Disruptor<Trade> disruptor = new Disruptor<Trade>(new EventFactory<Trade>() {
            @Override
            public Trade newInstance() {
                return new Trade();
            }
        },bufferSize,executorService, ProducerType.SINGLE,new BusySpinWaitStrategy());  //指定一个生产者

        //创建消费者组
        //EventHandlerGroup<Trade> handlerGroup = disruptor.handleEventsWith(new Handle1(),new Handle2());

        //负责合并并行执行结果
        //handlerGroup.then(new Handle3());

        /*Handle1 h1 = new Handle1();
        Handle2 h2 = new Handle2();
        Handle3 h3 = new Handle3();
        Handle4 h4 = new Handle4();
        Handle5 h5 = new Handle5();

        disruptor.handleEventsWith(h1,h2);
        disruptor.after(h1).handleEventsWith(h4);
        disruptor.after(h2).handleEventsWith(h5);
        disruptor.after(h4,h5).handleEventsWith(h3);*/

        //顺序操作
        disruptor.handleEventsWith(new Handle1()).handleEventsWith(new Handle2()).handleEventsWith(new Handle3());

        disruptor.start();
        CountDownLatch latch = new CountDownLatch(1);
        executorService.submit(new TradePublisher(latch,disruptor));
        latch.await();

        disruptor.shutdown();
        executorService.shutdown();
        System.out.println("总耗时：" + (System.currentTimeMillis() - beginTime + "毫秒。"));
    }
}
