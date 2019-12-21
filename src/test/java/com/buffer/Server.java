package buffer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author chenbin
 * @ClassName Server
 * @Description TODO
 * @date 2019/12/8 17:49
 * @Vsersion
 */
public class Server implements Runnable {

    private Selector selector;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    public Server(int port) {
        try {

            this.selector = Selector.open();
            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            channel.bind(new InetSocketAddress(port));
            channel.register(this.selector,SelectionKey.OP_ACCEPT);

            System.out.println("Server start,port is ：" + port);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while (true) {

            try {

                //1.开启轮询工作
                this.selector.select();
                Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();
                //进行轮询
                while(keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();
                    //如果是有效的
                    if (key.isValid()) {
                        //如果为阻塞状态
                        if (key.isAcceptable()) {
                            this.accpet(key);
                        }
                        //如果为可读状态
                        if (key.isReadable()) {
                            this.read(key);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void read(SelectionKey key) {

        try {

            this.byteBuffer.clear();
            SocketChannel socketChannel = (SocketChannel) key.channel();
            int count = socketChannel.read(this.byteBuffer);
            if (count == -1) {
                key.channel().close();
                key.cancel();
                return;
            }
            this.byteBuffer.flip();
            byte[] bytes = new byte[this.byteBuffer.remaining()];
            this.byteBuffer.get(bytes);
            String body = new String(bytes).trim();
            System.out.println("Server ：" + body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void accpet(SelectionKey key) {
        try {
            ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
            SocketChannel sc = ssc.accept();
            sc.configureBlocking(false);
            sc.register(this.selector,SelectionKey.OP_READ);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(new Server(8765)).start();
    }
}
