package com.disruptor;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

public class LongEventProducer {

    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer){
        this.ringBuffer = ringBuffer;
    }

    /**
     *
     * @param byteBuffer
     */
    public void onData(ByteBuffer byteBuffer) {
        // 1.可以吧ringBuffer看做一个容器队列，那么next()是一下一个事件槽
        long sequence = ringBuffer.next();
        try {
            // 2.用上面的索引取出一个空的事件用于填充
            LongEvent event = ringBuffer.get(sequence);
            // 3.获取要通过事件传递的业务数据
            event.setValue(byteBuffer.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 4.发布事件
            //注意：最后的ringBuffer.publish方法必须包含finally中以确保必须得到调用，如果某个请求的sequence未被提交
            ringBuffer.publish(sequence);
        }
    }
}
