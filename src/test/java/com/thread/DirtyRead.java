package com.thread;


/**
 * 避免脏读
 */
public class DirtyRead {

    private String userName = "bjsxt";
    private String passWord = "123";

    public synchronized void  setValue(String userName,String passWord) {

        this.userName = userName;
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.passWord = passWord;
        System.out.println("setValue最终结果为：userName = " + userName + ", passWord = " + passWord);
    }

    public synchronized void getValue() {
        System.out.println("getValue方法得到的结果为：userName = " + userName + ", passWord = " + passWord);
    }

    public static void main(String[] args) throws Exception{

        final DirtyRead dirtyRead = new DirtyRead();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                dirtyRead.setValue("张三","456");
            }
        });

        t1.start();
        Thread.sleep(1000);
        dirtyRead.getValue();
    }
}
