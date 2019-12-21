package myqueue;

import java.util.concurrent.DelayQueue;

/**
 * @author chenbin
 * @ClassName WangBa
 * @Description TODO
 * @date 2019/11/24 10:10
 * @Vsersion
 */
public class WangBa implements Runnable {

    private DelayQueue<WangMin> queue = new DelayQueue<>();

    public boolean yingye = true;

    public void shangji(String name,String id,int money) {
        WangMin wangMin = new WangMin(name,id,1000 * money + System.currentTimeMillis());
        System.out.println("网名：" + wangMin.getName() +" 身份证：" + wangMin.getId() + " 交钱：" + money +"块，开始上网...");
        this.queue.add(wangMin);
    }

    public void xiaji(WangMin wangMin) {
        System.out.println("网名：" + wangMin.getName() + " 身份证：" + wangMin.getId() +" 时间到，已下机...");
    }

    @Override
    public void run() {
        while (yingye) {
            try {
                WangMin wangMin = queue.take();
                xiaji(wangMin);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("网吧开始营业...");
            WangBa wangBa = new WangBa();
            Thread thread = new Thread(wangBa);
            thread.start();

            wangBa.shangji("甲","123",1);
            wangBa.shangji("乙","234",10);
            wangBa.shangji("丙","345",5);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
