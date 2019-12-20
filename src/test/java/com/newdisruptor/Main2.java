package com.newdisruptor;

import com.lmax.disruptor.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main2 {
    public static void main(String[] args) throws Exception{
        int BUFFER_SIZE = 1024;
        int THREAT_NUMBER = 4;

        /**
         *
         */
    /*final RingBuffer<Trade> ringBuffer = RingBuffer.createSingleProducer(new EventFactory<Trade>() {
        @Override
        public Trade newInstance() {
            return new Trade();
        }
    },BUFFER_SIZE,new YieldingWaitStrategy());*/

        EventFactory<Trade> eventFactory = new EventFactory<Trade>() {
            @Override
            public Trade newInstance() {
                return new Trade();
            }
        };

        RingBuffer<Trade> ringBuffer = RingBuffer.createSingleProducer(eventFactory, BUFFER_SIZE);

        SequenceBarrier barrier = ringBuffer.newBarrier();

        ExecutorService executor = Executors.newFixedThreadPool(THREAT_NUMBER);


        WorkHandler<Trade> handler = new TradeHandler();

        WorkerPool<Trade> pool = new WorkerPool<>(ringBuffer, barrier, new IgnoreExceptionHandler(), handler);

        pool.start(executor);

        for (int i = 0;i<8;i++) {
            long seq = ringBuffer.next();
            ringBuffer.get(seq).setPrice(Math.random()*9999);
            ringBuffer.publish(seq);
        }

        Thread.sleep(1000);
        pool.halt();
        executor.shutdown();
    }

}
