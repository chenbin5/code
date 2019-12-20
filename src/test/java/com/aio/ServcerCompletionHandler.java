package com.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;

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
    /**
     * 异步操作
     * @param asc
     */
    private void read(final AsynchronousSocketChannel asc) {

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        asc.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                //对缓存区进行复位操作
                attachment.flip();
                System.out.println("Server -> " + "收到客户端的数据长度为：" + result);
                //获取数据
                String resultData = new String(attachment.array()).trim();
                System.out.println("Server -> " + "收到客户端的数据信息为：" + resultData);
                String response = "服务器响应，收到了客户端发来的数据：" + resultData;
                write(asc,response);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
            }
        });
    }

    private void write(AsynchronousSocketChannel asc, String response) {
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put(response.getBytes());
            byteBuffer.flip();
            asc.write(byteBuffer).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
