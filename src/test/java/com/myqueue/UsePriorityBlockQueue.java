package myqueue;

import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author chenbin
 * @ClassName UsePriorityBlockQueue
 * @Description TODO
 * @date 2019/11/24 9:29
 * @Vsersion
 */
public class UsePriorityBlockQueue {

    public static void main(String[] args) throws Exception{

        PriorityBlockingQueue<Task> que = new PriorityBlockingQueue<>();
        Task task1 = new Task();
        task1.setId(3);
        task1.setName("任务1");
        Task task2 = new Task();
        task2.setId(7);
        task2.setName("任务2");
        Task task3 = new Task();
        task3.setId(1);
        task3.setName("任务3");
        Task task4 = new Task();
        task4.setId(2);
        task4.setName("任务4");
        Task task5 = new Task();
        task5.setId(6);
        task5.setName("任务5");

        que.add(task1);
        que.add(task2);
        que.add(task3);
        que.add(task4);
        que.add(task5);

        System.out.println(que.take().getId());
        System.out.println(que);


        for (Iterator iterator = que.iterator();iterator.hasNext();) {
            Task task = (Task) iterator.next();
            //System.out.println(task.getName());
        }

//        for (Task task:que) {
//            System.out.println(task.getName());
//        }
    }
}
