package buffer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author chenbin
 * @ClassName Client
 * @Description TODO
 * @date 2019/12/8 18:21
 * @Vsersion
 */
public class Client {

    public static void main(String[] args) {

        InetSocketAddress address = new InetSocketAddress("127.0.0.1",8765);
        SocketChannel sc = null;
        ByteBuffer buf = ByteBuffer.allocate(1024);

        try {
            sc = SocketChannel.open();
            sc.connect(address);
            while(true) {
                byte[] bytes = new byte[1024];
                System.in.read(bytes);

                buf.put(bytes);
                buf.flip();
                sc.write(buf);
                buf.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sc != null) {
                try {
                    sc.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
