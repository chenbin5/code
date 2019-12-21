package aiosocket;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author chenbin
 * @ClassName ServcerCompletionHandler
 * @Description TODO
 * @date 2019/12/8 19:49
 * @Vsersion
 *
 * zm
 * nu7D  106903290212367
 */
public class ServcerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel,Server> {

    @Override
    public void completed(AsynchronousSocketChannel result, Server attachment) {

        attachment.assc.accept(attachment,this);
        read(result);
    }

    @Override
    public void failed(Throwable exc, Server attachment) {
        exc.printStackTrace();
    }

    public void read(final AsynchronousSocketChannel asc) {


    }
}
