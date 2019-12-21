import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author chenbin
 * @ClassName UserCyclicBarrier
 * @Description TODO
 * @date 2019/12/1 14:41
 * @Vsersion
 */
public class UserCyclicBarrier {

    static class Runner implements Runnable {

        private CyclicBarrier barrier;
        private String name;

        public Runner(CyclicBarrier barrier,String name) {
            this.barrier = barrier;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000*(new Random().nextInt(5)));
                System.out.println(name + "准备就绪...");
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(name + "Go!!");
        }
    }

    public static void main(String[] args) {

        CyclicBarrier barrier = new CyclicBarrier(3);
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.submit(new Thread(new Runner(barrier,"zhangsan")));
        executorService.submit(new Thread(new Runner(barrier,"lisi")));
        executorService.submit(new Thread(new Runner(barrier,"wangwu")));
    }
}
