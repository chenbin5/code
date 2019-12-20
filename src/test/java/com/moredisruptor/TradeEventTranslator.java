package com.moredisruptor;

import com.lmax.disruptor.EventTranslator;
import com.newdisruptor.Trade;

import java.util.Random;

public class TradeEventTranslator implements EventTranslator<Trade> {

    private Random random = new Random();
    @Override
    public void translateTo(Trade trade, long l) {
        this.generateTrade(trade);
    }

    private Trade generateTrade(Trade trade) {
        trade.setPrice(random.nextDouble()*9999);
        return trade;
    }
}
