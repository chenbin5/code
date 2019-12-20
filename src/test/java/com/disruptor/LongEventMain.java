package com.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LongEventMain {

    public static void main(String[] args) {

        //缓存线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        LongEventFactory factory = new LongEventFactory();
        //缓存区大小一定是2的指数被 2的n次方
        int ringBufferSize = 1024 * 1024;


        // 1.第一个参数为工厂类对象，用于创建一个个的LongEvent，LongEvent是实际的消费数据
        // 2.第二个参数为缓冲区大小
        // 3.第三个参数为线程池，进行disruptor，内的数据接收处理调度
        // 4.第四个参数有两种形式：SINGLE和MULTI，如果指定SINGLE生产者有一个，如果指定MULTI说明生产者有多个
        // 5.第五个参数是一种策略，
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory,ringBufferSize,executorService,
                ProducerType.SINGLE, new YieldingWaitStrategy());
        //链接消费事件的方法
        disruptor.handleEventsWith(new LongEventHandler());
        //启动
        disruptor.start();

        //Disruptor的事件发布过程是一个提交数据的过程
        //使用该方法获取到存放数据的具体容器ringBuffer，是一个环形机构的模型
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        LongEventProducer producer = new LongEventProducer(ringBuffer);
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        for (long i = 0;i<100;i++) {
            byteBuffer.putLong(0,i);
            producer.onData(byteBuffer);
        }

        disruptor.shutdown();
        executorService.shutdown();
    }
}
