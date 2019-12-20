package com.future;

/**
 * @author chenbin
 * @ClassName FutureClient
 * @Description TODO
 * @date 2019/11/25 22:26
 * @Vsersion
 */
public class FutureClient {

    public Data request(final String queryStr) {

        final FutureData  futureData = new FutureData();

        //RealData的构建很慢，所以放在单独的线程中运行
        new Thread(new Runnable() {
            @Override
            public void run() {
                RealData realData = new RealData(queryStr);
                futureData.setRealData(realData);
            }
        }).start();
        //直接先返回FutureData
        return futureData;
    }
}
