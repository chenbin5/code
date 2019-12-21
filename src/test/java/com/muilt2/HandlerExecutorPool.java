package muilt2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chenbin
 * @ClassName HandlerExecutorPool
 * @Description TODO
 * @date 2019/12/7 17:41
 * @Vsersion
 */
public class HandlerExecutorPool {

    private ExecutorService executor;

    public HandlerExecutorPool(int maxPoolSize,int queueSize) {
        this.executor = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                maxPoolSize,
                120L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(queueSize)
        );
    }

    public void executor (Runnable task) {
        this.executor.execute(task);
    }
}
