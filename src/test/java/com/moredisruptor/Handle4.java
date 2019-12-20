package com.moredisruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import com.newdisruptor.Trade;

public class Handle4 implements EventHandler<Trade>, WorkHandler<Trade> {

    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        this.onEvent(trade);
    }

    @Override
    public void onEvent(Trade trade) throws Exception {
        System.out.println("handle4 : get name");
        trade.setName(trade.getName() + "h4");
        Thread.sleep(1000);
    }
}
