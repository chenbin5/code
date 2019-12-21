package myexecutors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author chenbin
 * @ClassName ScheduledJob
 * @Description TODO
 * @date 2019/11/28 22:03
 * @Vsersion
 */
public class ScheduledJob {

    public static void main(String[] args) {

        Temp temp = new Temp();
        ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(1);

        ScheduledFuture<?> future = scheduledService.scheduleWithFixedDelay(temp,1,3,TimeUnit.SECONDS);
    }
}

class Temp extends Thread {

    public void run() {
        System.out.println("run...");
    }
}

