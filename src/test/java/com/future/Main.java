package com.future;

/**
 * @author chenbin
 * @ClassName Main
 * @Description TODO
 * @date 2019/11/25 22:37
 * @Vsersion
 */
public class Main {

    public static void main(String[] args) {
        FutureClient futureClient = new FutureClient();
        //这里会立即返回，因为获取的是FutureData，而非RealData
        Data data = futureClient.request("请求参数");

        System.out.println("请求发送成功...");
        System.out.println("做其他的事情...");

        String result = data.getRequest();
        System.out.println(result);
    }
}
