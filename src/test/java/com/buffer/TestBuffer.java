package buffer;

import java.nio.IntBuffer;

/**
 * @author chenbin
 * @ClassName TestBuffer
 * @Description TODO
 * @date 2019/12/7 21:53
 * @Vsersion
 */
public class TestBuffer {

    public static void main(String[] args) {

        //基本操作
        /*IntBuffer intBuffer = IntBuffer.allocate(10);
        intBuffer.put(13);
        intBuffer.put(21);
        intBuffer.put(35);
        //一定要使用flip()方法，将上限置成元素个数
        intBuffer.flip();
        System.out.println("使用flip复位：" + intBuffer);
        System.out.println("容量为：" + intBuffer.capacity());
        System.out.println("限制为：" + intBuffer.limit());

        intBuffer.put(1,34);
        for (int i = 0;i<intBuffer.limit();i++) {
            System.out.println(intBuffer.get());
        }*/

        //使用wrap()方法
        /*int[] array = new int[]{1,2,3};
        IntBuffer intBuffer = IntBuffer.wrap(array);
        System.out.println(intBuffer);

        IntBuffer intBuffer1 = IntBuffer.wrap(array,0,2);
        System.out.println(intBuffer1);*/


    }
}
