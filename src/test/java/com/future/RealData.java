package com.future;

/**
 * @author chenbin
 * @ClassName RealData
 * @Description TODO
 * @date 2019/11/25 22:28
 * @Vsersion
 */
public class RealData implements Data {

    private String result;

    public RealData(String queryStr) {
        System.out.println("查询条件为：" + queryStr);
        // 利用sleep方法来表示RealData构造过程是非常缓慢的
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("操作完毕，获取结果");
        result = "查询结果";
    }

    @Override
    public String getRequest() {
        return result;
    }
}
