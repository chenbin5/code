package com.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

    public static  void main(String[] args) throws Exception{
        //1.第一个线程组是用于接收client链接的
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //2.第二个线程组是用于实际业务处理的
        EventLoopGroup workGroup = new NioEventLoopGroup();
        //3.创建一个辅助类，就是对我们的Server端进行一系列的配置
        ServerBootstrap b = new ServerBootstrap();
        //把两个线程组添加进来
        b.group(bossGroup,workGroup)
                //要指定使用的NioServerSocketChannel这种类型的通道
                .channel(NioServerSocketChannel.class)
                //一定要使用ChannleHandler，绑定具体的事件处理器
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new ServerHandler());
                    }
                })
         //设置tcp通信的缓冲区
        .option(ChannelOption.SO_BACKLOG,128)
         //保持连接
        .childOption(ChannelOption.SO_KEEPALIVE,true);
        //绑定指定的端口，进行监听，异步监听
        ChannelFuture future = b.bind(8765).sync();
        //相当于阻塞睡眠
        future.channel().closeFuture().sync();
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }
}
