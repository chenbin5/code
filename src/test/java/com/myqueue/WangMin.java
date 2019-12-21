package myqueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author chenbin
 * @ClassName WangMin
 * @Description TODO
 * @date 2019/11/24 9:58
 * @Vsersion
 */
public class WangMin implements Delayed {

    private String name;
    private String id;
    //截止时间
    private long endTime;
    //定义时间工具类
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    public WangMin(String name,String id,long endTime) {
        this.name = name;
        this.id = id;
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    /**
     * 用来判断是否到了截止时间
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return endTime - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed delayed) {
        WangMin wangMin = (WangMin) delayed;
        return this.getDelay(this.timeUnit) - wangMin.getDelay(this.timeUnit) > 0 ? 1 : 0;
    }
}
