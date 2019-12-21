package aiosocket;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author chenbin
 * @ClassName Server
 * @Description TODO
 * @date 2019/12/8 19:43
 * @Vsersion
 */
public class Server {

    private ExecutorService executorService;
    private AsynchronousChannelGroup threadGroup;
    public AsynchronousServerSocketChannel assc;

    public Server(int port) {

        try {
            //创建一个线程池
            executorService = Executors.newCachedThreadPool();
            //创建线程数
            threadGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService,1);
            //创建服务器池组
            assc = AsynchronousServerSocketChannel.open(threadGroup);
            //进行绑定
            assc.bind(new InetSocketAddress(port));

            System.out.println("server start，port is：" + port);
            //进行阻塞
            assc.accept(this,new ServcerCompletionHandler());
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(8765);
    }
}
