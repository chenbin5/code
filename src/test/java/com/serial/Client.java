package com.serial;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author chenbin
 * @ClassName Client
 * @Description TODO
 * @date 2019/12/22 10:54
 * @Vsersion
 */
public class Client {

    public static void main(String[] args) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                        socketChannel.pipeline().addLast(new ClientHandler());
                    }
                });

        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",8765).sync();
        for (int i=0;i<5;i++) {
            Req req = new Req();
            req.setId("" + i);
            req.setName("pro" + i);
            req.setRequestMessge("数据信息" + i);

            channelFuture.channel().writeAndFlush(req);
        }

        channelFuture.channel().closeFuture().sync();
        group.shutdownGracefully();
    }
}
