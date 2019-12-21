package myexecutors;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenbin
 * @ClassName UserThreadPoolExecutors2
 * @Description TODO
 * @date 2019/11/30 9:59
 * @Vsersion
 */
public class UserThreadPoolExecutors2 implements Runnable {


    private static AtomicInteger count = new AtomicInteger();

    @Override
    public void run() {
        try {
            int temp = count.incrementAndGet();
            System.out.println("任务：" + temp);
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        //如果使用无界队列，根据corePoolSize来执行，maximumPoolSize无任何作用
        //如果使用有界队列，根据corePoolSize来执行对应的线程，根据maximumPoolSize创建执行大小的队列
        //BlockingQueue<Runnable> queue = new LinkedBlockingDeque<>();
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);

        ExecutorService executor = new ThreadPoolExecutor(
                5,        //核心线程数
                10,   //最大线程数
                120L,    //2分钟
                TimeUnit.SECONDS,     //分钟
                queue);
        for (int i = 0; i < 20; i++) {
            executor.execute(new UserThreadPoolExecutors2());
        }
        Thread.sleep(1000);
        System.out.println("queue size：" + queue.size());
        Thread.sleep(2000);
    }
}
