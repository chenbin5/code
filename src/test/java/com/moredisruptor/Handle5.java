package com.moredisruptor;


import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import com.newdisruptor.Trade;

public class Handle5 implements EventHandler<Trade>, WorkHandler<Trade> {

    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        /*System.out.println("handle2 : set price");
        trade.setPrice(17.0);*/
        //Thread.sleep(1000);
        this.onEvent(trade);
    }

    @Override
    public void onEvent(Trade trade) throws Exception {
        System.out.println("handle5 : get price ：" + trade.getPrice());
        trade.setPrice(trade.getPrice() + 3.0);
    }
}
