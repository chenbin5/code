package com.images;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

public class Test {

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    public static void main ( String[] args ) throws Exception {
        String text = "http://192.168.0.105:8080/upload";
        //String text = "http://10.2.21.162:8080/upload";
        String path = "E:/";
        int width = 1000;
        int height = 1000;
        // 二维码图片格式
        String format = "jpg";
        // 设置编码，防止中文乱码
        Hashtable<EncodeHintType, Object> ht = new Hashtable<EncodeHintType, Object> ();
        ht.put (EncodeHintType.CHARACTER_SET, "UTF-8");
        // 设置二维码参数(编码内容，编码类型，图片宽度，图片高度, 编码格式)
        BitMatrix bitMatrix = new MultiFormatWriter().encode (text, BarcodeFormat.QR_CODE, width, height, ht);
        // 生成二维码(定义二维码输出服务器路径)
        File outputFile = new File (path);
        if (!outputFile.exists ()) {
            //创建文件夹
            outputFile.mkdir ();
        }
        int b_width = bitMatrix.getWidth ();
        int b_height = bitMatrix.getHeight ();
        // 建立图像缓冲器
        BufferedImage image = new BufferedImage(b_width, b_height, BufferedImage.TYPE_3BYTE_BGR);
        for ( int x = 0; x < b_width; x++ ) {
            for ( int y = 0; y < b_height; y++ ) {
                image.setRGB (x, y, bitMatrix.get (x, y) ? BLACK : WHITE);
            }
        }
        // 生成二维码
        ImageIO.write (image, format, new File (path + "/test." + format));
    }
}
