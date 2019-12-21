package semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author chenbin
 * @ClassName UserSemaphore
 * @Description TODO
 * @date 2019/12/2 21:52
 * @Vsersion
 */
public class UserSemaphore {

    public static void main(String[] args) {

        //线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //只能5个线程信号量
        final Semaphore semaphore = new Semaphore(5);

        for (int i = 0; i < 20; i++) {
            final int NO = i;
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        // 代表业务逻辑
                        // 获取许可
                        semaphore.acquire();
                        System.out.println("Accetping：" + NO);
                        Thread.sleep((long)(Math.random() * 10000));
                        // 执行完毕，释放资源
                        semaphore.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            executorService.execute(run);
        }
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}
