package com.utils;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author chenbin
 * @ClassName GzipUtils
 * @Description TODO
 * @date 2019/12/23 21:02
 * @Vsersion
 */
public class GzipUtils {

    public static byte[] gzip(byte[] data) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(bos);
        gzip.write(data);
        gzip.finish();
        gzip.close();
        byte[] ret = bos.toByteArray();
        bos.close();
        return ret;
    }

    public static byte[] ungzip(byte[] data) throws Exception {

        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        GZIPInputStream gzip = new GZIPInputStream(bis);
        byte[] buf = new byte[1024];
        int num = -1;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((num = gzip.read(buf,0,buf.length)) != -1) {
            bos.write(buf,0,num);
        }
        gzip.close();
        bis.close();
        byte[] ret = bos.toByteArray();
        bos.flush();
        bos.close();
        return ret;
    }

    public static void main(String[] args) throws Exception{

        //user.dir 打印当前程序所在的目录
        String readPath = System.getProperty("user.dir") + File.separatorChar + "sources" + File.separatorChar + "006.jpg";
        //System.out.println(readPath);
        File file = new File(readPath);
        FileInputStream in = new FileInputStream(file);
        byte[] data = new byte[in.available()];
        in.read(data);
        in.close();

        System.out.println("文件大小为：" + data.length);

        //测试压缩
        byte[] ret1 = GzipUtils.gzip(data);
        System.out.println("压缩后的大小为：" + ret1.length);


        //解压文件
        byte[] ret2 = GzipUtils.ungzip(ret1);
        System.out.println("解码后的文件为：" + ret2.length);

        //写出文件
        String writePath = System.getProperty("user.dir") + File.separatorChar + "resource" + File.separatorChar + "006.jpg";
        FileOutputStream fos = new FileOutputStream(writePath);
        fos.write(ret2);
        fos.close();
    }

}
