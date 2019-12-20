package com.lock;


import com.alibaba.fastjson.JSONObject;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class UseReentranReadWriteLock {

    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();

    private static final String filePath = "C:\\Users\\jiuqi\\Desktop\\student.xls";

    private List<Map<String, Object>> readExcel(String path) {
        SimpleDateFormat simFormat = new SimpleDateFormat("yyyy年MM月dd日");
        File Inputfile = new File(path);
        FileInputStream fileInputStream = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            fileInputStream = new FileInputStream(Inputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Workbook workBook = null;
            workBook = Workbook.getWorkbook(fileInputStream);
            //获取学员基本信息sheet页
            Sheet sheet = workBook.getSheet(0);
            int row = 1;//开始行号
            int kh = 0;//空行数
            for (row = 1; row < sheet.getRows(); row++) {
                int m = 0;
                for (int k = 0; k < 15; k++) {//遇到空行则忽略
                    if (judageStrIsEmpt(sheet.getCell(k, row).getContents())) {
                        m++;
                    }
                }
                if (m >= 14) {
                    kh++;
                    if (kh >= 1)
                        break;
                    continue;
                }
                //项目名称
                String xmName = sheet.getCell(0, row).getContents();
                //证件类型
                String zjType = sheet.getCell(1, row).getContents();
                //证件号码
                String zjNumber = sheet.getCell(2, row).getContents();
                //学员姓名
                String stuName = sheet.getCell(3, row).getContents();
                //职称
                String zc = sheet.getCell(4, row).getContents();
                //学历
                String xl = sheet.getCell(5, row).getContents();
                //联系电话
                String phone = sheet.getCell(6, row).getContents();
                //入选理由
                String rxly = sheet.getCell(7, row).getContents();
                //出生年月
                //String csny = sheet.getCell(8, row).getContents();
                //政治面貌
                String zzmm = sheet.getCell(8, row).getContents();
                //民族
                String mz = sheet.getCell(9, row).getContents();
                //毕业院校
                String shcool = sheet.getCell(10, row).getContents();
                //所读专业
                String sdzy = sheet.getCell(11, row).getContents();
                //毕业时间
                //String bysj = sheet.getCell(12, row).getContents();
                //从事本专业工作年限
                String gznx = sheet.getCell(12, row).getContents().trim();
                //所在地区
                String szdq = sheet.getCell(13, row).getContents();
                //所在单位及职称
                String szdwJzc = sheet.getCell(14, row).getContents();

                Map<String, Object> paramMap = new HashMap<String, Object>();
                //插入学员简历
                paramMap.put("XMNAME",xmName);
                paramMap.put("XYMC", stuName);
                paramMap.put("ZJTYPE", zjType);
                paramMap.put("ZJNUMBER", zjNumber);
                paramMap.put("ZC", zc);
                paramMap.put("XL", xl);
                paramMap.put("PHONE", phone);
                paramMap.put("RXLY", rxly);
                paramMap.put("ZZMM", zzmm);
                paramMap.put("MZ", mz);
                paramMap.put("BYYX", shcool);
                paramMap.put("SDZY", sdzy);
                paramMap.put("GZNX", gznx);
                paramMap.put("SZDQ", szdq);
                paramMap.put("SZDQJZW", szdwJzc);
                list.add(paramMap);
            }
        } catch (BiffException e) {
            // TODO Auto-generated catch block
            try {
                fileInputStream.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            try {
                fileInputStream.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return list;
    }

    public static boolean judageStrIsEmpt(String oldStr){
        oldStr = oldStr==null||"".equalsIgnoreCase(oldStr)?"":oldStr;
        boolean ret = "".equalsIgnoreCase(oldStr.trim())?true:false;
        return ret;
    }

    /**
     * 通过身份证号码获取出生日期、性别、年龄
     * @param certificateNo
     * @return 返回的出生日期格式：2019年01月01日   性别格式：F-女，M-男
     */
    public static Map<String, String> getBirAgeSex(String certificateNo) {
        String birthday = "";
        String age = "";
        String sexCode = "";
        int year = Calendar.getInstance().get(Calendar.YEAR);
        char[] number = certificateNo.toCharArray();
        boolean flag = true;
        if (number.length == 15) {
            for (int x = 0; x < number.length; x++) {
                if (!flag) return new HashMap<String, String>();
                flag = Character.isDigit(number[x]);
            }
        } else if (number.length == 18) {
            for (int x = 0; x < number.length - 1; x++) {
                if (!flag) return new HashMap<String, String>();
                flag = Character.isDigit(number[x]);
            }
        }
        if (flag && certificateNo.length() == 15) {
            birthday = "19" + certificateNo.substring(6, 8) + "-"
                    + certificateNo.substring(8, 10) + "-"
                    + certificateNo.substring(10, 12);
            sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 3, certificateNo.length())) % 2 == 0 ? "F" : "M";
            age = (year - Integer.parseInt("19" + certificateNo.substring(6, 8))) + "";
        } else if (flag && certificateNo.length() == 18) {
            birthday = certificateNo.substring(6, 10) + "年"
                    + certificateNo.substring(10, 12) + "月"
                    + certificateNo.substring(12, 14) + "日";
            sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 4, certificateNo.length() - 1)) % 2 == 0 ? "F" : "M";
            age = (year - Integer.parseInt(certificateNo.substring(6, 10))) + "";
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("CSRQ", birthday);
        map.put("AGE", age);
        map.put("SEX_CODE", sexCode);
        return map;
    }



    public List<Map<String, Object>> read() {
        List<Map<String, Object>> list = null;
        try {
            readLock.lock();
            long beginTime = System.currentTimeMillis();
            System.out.println("当前线程：" + Thread.currentThread().getName() + "进入读方法...");
            list = readExcel(filePath);
            //System.out.println("当前线程：" + Thread.currentThread().getName() + "退出读方法...");
            System.out.println("当前线程：" + Thread.currentThread().getName() + "耗时：" + (System.currentTimeMillis() - beginTime) + "毫秒...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
        }
        return list;
    }

    public void write() {
        try {
            writeLock.lock();
            System.out.println("当前线程：" + Thread.currentThread().getName() + "进入写方法...");
            Thread.sleep(3000);
            System.out.println("当前线程：" + Thread.currentThread().getName() + "退出写方法...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {

        final UseReentranReadWriteLock reentranReadWriteLock = new UseReentranReadWriteLock();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Map<String, Object>> resultList = reentranReadWriteLock.read();
                System.out.println("解析excele之后，长度为：" + resultList.size());
            }
        },"t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Map<String, Object>> resultList = reentranReadWriteLock.read();
                System.out.println("解析excele之后，长度为：" + resultList.size());
            }
        },"t2");

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                reentranReadWriteLock.write();
            }
        },"t3");

        t1.start();
        t2.start();
        //t3.start();
    }
}
