package com.moredisruptor;


import com.lmax.disruptor.EventHandler;
import com.newdisruptor.Trade;

public class Handle2 implements EventHandler<Trade> {

    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        System.out.println("handle2 : set price");
        trade.setPrice(17.0);
        Thread.sleep(1000);
    }
}
