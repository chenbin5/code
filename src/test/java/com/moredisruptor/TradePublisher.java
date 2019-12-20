package com.moredisruptor;

import com.lmax.disruptor.dsl.Disruptor;
import com.newdisruptor.Trade;

import java.util.concurrent.CountDownLatch;

public class TradePublisher implements Runnable{

    private CountDownLatch countDownLatch;
    private Disruptor<Trade> disruptor;
    private static int LOOP = 1;

    public TradePublisher(CountDownLatch countDownLatch,Disruptor<Trade> disruptor) {
        this.countDownLatch = countDownLatch;
        this.disruptor = disruptor;
    }
    @Override
    public void run() {

        TradeEventTranslator tradeEventTranslator = new TradeEventTranslator();

        for (int i = 0;i<LOOP;i++) {
            disruptor.publishEvent(tradeEventTranslator);
        }
        countDownLatch.countDown();
    }
}

