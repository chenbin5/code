package myexecutors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chenbin
 * @ClassName UserThreadPoolExecutors1
 * @Description TODO
 * @date 2019/11/30 10:38
 * @Vsersion
 */
public class UserThreadPoolExecutors1 {

    public static void main(String[] args) {

        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                1,
                2,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(3)
        );
        MyTask myTask1 = new MyTask("1","任务1");
        MyTask myTask2 = new MyTask("2","任务2");
        MyTask myTask3 = new MyTask("3","任务3");
        MyTask myTask4 = new MyTask("4","任务4");
        MyTask myTask5 = new MyTask("5","任务5");
        MyTask myTask6 = new MyTask("6","任务6");

        pool.execute(myTask1);
        /*pool.execute(myTask2);
        pool.execute(myTask3);
        pool.execute(myTask4);
        pool.execute(myTask5);
        pool.execute(myTask6);*/

        pool.shutdown();
    }
}
