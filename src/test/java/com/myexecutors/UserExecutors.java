package myexecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author chenbin
 * @ClassName UserExecutors
 * @Description TODO
 * @date 2019/11/28 21:38
 * @Vsersion
 */
public class UserExecutors {

    public static void main(String[] args) {

        ExecutorService pool = Executors.newFixedThreadPool(10);
    }
}
