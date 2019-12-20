package com.newdisruptor;

import com.lmax.disruptor.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main1 {

    public static void main(String[] args) throws Exception{

        int BUFFER_SIZE = 1024;
        int THREAT_NUMBER = 4;

        /**
         *
         */
        final RingBuffer<Trade> ringBuffer = RingBuffer.createSingleProducer(new EventFactory<Trade>() {
            @Override
            public Trade newInstance() {
                return new Trade();
            }
        },BUFFER_SIZE,new YieldingWaitStrategy());

        ExecutorService service = Executors.newFixedThreadPool(THREAT_NUMBER);

        SequenceBarrier barrier = ringBuffer.newBarrier();

        BatchEventProcessor<Trade> tradeBatchEvent = new BatchEventProcessor<Trade>(ringBuffer,barrier,new TradeHandler());

        ringBuffer.addGatingSequences(tradeBatchEvent.getSequence());

        service.submit(tradeBatchEvent);

        Future<?> future = service.submit(new Callable<Void>() {

            @Override
            public Void call() throws Exception {
                long seq;
                for (int i = 0;i<10;i++) {
                    seq = ringBuffer.next();
                    ringBuffer.get(seq).setPrice(Math.random()*9999);
                    ringBuffer.publish(seq);
                }
                return null;
            }
        });

        future.get();
        Thread.sleep(1000);
        tradeBatchEvent.halt();
        service.shutdown();
    }
}
