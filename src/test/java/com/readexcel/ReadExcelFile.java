package com.readexcel;


/*import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;*/

/**
 * 多个线程读取Excel
 */
public class ReadExcelFile {

    /*private volatile List<String> list = new ArrayList<String>();
    *//**
     * 解析excel
     * @param path
     * @return
     *//*
    public synchronized List<String> readExcel(String path) {

        String fileType = path.substring(path.lastIndexOf(".") + 1);

        //读取excel文件
        InputStream is = null;
        try {
            is = new FileInputStream(path);
            //获取工作薄
            Workbook wb = null;
            if (fileType.equals("xls")) {
                wb = new HSSFWorkbook(is);
            } else if (fileType.equals("xlsx")) {
                wb = new XSSFWorkbook(is);
            } else {
                return null;
            }
            //读取第一个工作页sheet
            Sheet sheet = wb.getSheetAt(0);
            int firstRowIndex = sheet.getFirstRowNum() + 1;
            int lastRowIndex = sheet.getLastRowNum();
            for (int i = firstRowIndex;i<=lastRowIndex;i++) {
                Row row = sheet.getRow(i);
                if (null != row) {
                    int firstCellIndex = row.getFirstCellNum();
                    int lastCellIndex = row.getLastCellNum();
                    for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {   //遍历列
                        Cell cell = row.getCell(cIndex);
                        if (cell != null) {
                            list.add(cell.toString());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public int getSize() {
        return this.list.size();
    }

    public static void main(String[] args) {

        String path = "C:\\Users\\jiuqi\\Desktop\\template.xls";
        System.out.println("开始解析excel...");
        long beginTime = System.currentTimeMillis();
        final ReadExcelFile readExcelFile = new ReadExcelFile();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        List<String> list = new ArrayList<String>();

        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();

        concurrentHashMap.put("","");


        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                if (readExcelFile.list.size() != 500) {
                    readExcelFile.readExcel(path);
                    System.out.println("通知t2线程来解析...");
                    System.out.println("当前线程t1解析了：" + readExcelFile.readExcel(path).size() + "条数据。");
                    countDownLatch.countDown();
                }
            }
        },"t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                if (readExcelFile.list.size() == 500) {
                    //readExcelFile.readExcel(path);
                    //System.out.println("当前线程t2解析了：" + readExcelFile.readExcel(path).size() + "条数据。");
                    try {
                        countDownLatch.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        },"t2");

        t1.start();
        t2.start();

        long endTime = System.currentTimeMillis();
        System.out.println("结果为：" + list.size());
        System.out.println("解析excel结束...耗时：" + (endTime - beginTime) + "毫秒。");
    }*/
}
