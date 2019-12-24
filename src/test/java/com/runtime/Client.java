package com.runtime;

import com.serial.MarshallingCodeCFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author chenbin
 * @ClassName Client
 * @Description TODO
 * @date 2019/12/22 10:54
 * @Vsersion
 */
public class Client {

    private static class SingletonHolder {
        static final Client instance  = new Client();
    }

    public static Client getInstance() {
        return SingletonHolder.instance;
    }

    private EventLoopGroup group;
    private Bootstrap bootstrap;
    private ChannelFuture channelFuture;

    public Client() {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                        //5秒后如果没有数据通信，自动断开连接
                        socketChannel.pipeline().addLast(new ReadTimeoutHandler(5));
                        socketChannel.pipeline().addLast(new ClientHandler());
                    }
                });
    }

    public void connect() {
        try {
            this.channelFuture = bootstrap.connect("127.0.0.1",8765).sync();
            System.out.println("远程服务器已经连接，开始数据交互");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ChannelFuture getChannelFuture() {
        if (this.channelFuture == null) {
            this.connect();
        }
        if (!this.channelFuture.channel().isActive()) {
            this.connect();
        }
        return this.channelFuture;
    }

    public static void main(String[] args) throws Exception {
        final Client client = Client.getInstance();
        //client.connect();

        ChannelFuture cf  = client.getChannelFuture();
        for (int i=0;i<=3;i++) {
            Req req = new Req();
            req.setId("" + i);
            req.setName("pro" + i);
            req.setRequestMessge("数据信息" + i);

            /*String readPath = System.getProperty("user.dir") + File.separatorChar + "sources" + File.separatorChar + "001.jpg";
            File file = new File(readPath);
            FileInputStream in = new FileInputStream(file);
            byte[] data = new byte[in.available()];
            in.read(data);
            in.close();
            req.setAttachment(GzipUtils.gzip(data));*/
            cf.channel().writeAndFlush(req);
            TimeUnit.SECONDS.sleep(4);
        }
        cf.channel().closeFuture().sync();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("进入子线程...");
                    ChannelFuture cf = client.getChannelFuture();
                    System.out.println(cf.channel().isActive());
                    System.out.println(cf.channel().isOpen());

                    //再次发送请求
                    Req req = new Req();
                    req.setId("" + 4);
                    req.setName("pro" + 4);
                    req.setRequestMessge("数据信息：" + 4);
                    cf.channel().writeAndFlush(req);
                    cf.channel().closeFuture();
                    System.out.println("子线程结束...");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        System.out.println("断开连接，主线程结束...");
    }
}
