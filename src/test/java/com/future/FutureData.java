package com.future;

/**
 * @author chenbin
 * @ClassName FutureData
 * @Description TODO
 * @date 2019/11/25 22:27
 * @Vsersion
 */
public class FutureData implements Data {

    private RealData realData;  //FutureData是RealData的封装
    private boolean isReady = false;  //是否把真实RealData构造好

    public synchronized void setRealData(RealData realData) {

        if (isReady) {
            return;
        }
        this.realData = realData;
        isReady = true;
        notify(); //RealData已经被注入到FutureData中了，通知getResult()方法
    }

    @Override
    public synchronized String getRequest() {
        while (!isReady) {
            try {
                wait();  //一直等到RealData注入到FutureData中，目前处于阻塞状态
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.realData.getRequest();
    }
}
