package com.moredisruptor;

import com.lmax.disruptor.EventHandler;
import com.newdisruptor.Trade;

public class Handle3 implements EventHandler<Trade> {


    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        System.out.println("handle3 name：" + trade.getName() + "，price：" + trade.getPrice() +"，hashCode：" + trade.toString());
    }
}
