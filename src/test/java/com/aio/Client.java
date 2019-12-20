package com.aio;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public class Client implements Runnable {

    private AsynchronousSocketChannel asc;

    public Client() throws Exception{
        asc = AsynchronousSocketChannel.open();
    }

    public void connect() {
        asc.connect(new InetSocketAddress("127.0.0.1",8765));
    }

    public void write(String request) {
        try {
            asc.write(ByteBuffer.wrap(request.getBytes())).get();
            read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void read() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        try {
            asc.read(byteBuffer).get();
            byteBuffer.flip();
            byte[] respByte = new byte[byteBuffer.remaining()];
            byteBuffer.get(respByte);
            System.out.println(new String(respByte,"utf-8").trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {

        }
    }

    public static void main(String[] asrg) throws Exception{

        Client c1 = new Client();
        c1.connect();

        Client c2 = new Client();
        c2.connect();

        Client c3 = new Client();
        c3.connect();

        //Thread.sleep(1000);

        //c1.write("aaaa");
        //c2.write("bbbbb");
        //c3.write("cccccc");

        for (int i = 0; i < 100; i++) {
            Client client = new Client();
            Thread.sleep(2000);
            client.write("aaaa" + i);
        }
    }
}
