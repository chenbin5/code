package com.serial;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author chenbin
 * @ClassName Server
 * @Description TODO
 * @date 2019/12/22 10:54
 * @Vsersion
 */
public class Server {

    public static void main(String[] args) throws Exception {

        EventLoopGroup pGroup = new NioEventLoopGroup();
        EventLoopGroup cGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(pGroup,cGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                //设置打印日志
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                        socketChannel.pipeline().addLast(new ServerHandler());
                    }
                });

        ChannelFuture channelFuture = bootstrap.bind(8765).sync();
        channelFuture.channel().closeFuture().sync();
        pGroup.shutdownGracefully();
        cGroup.shutdownGracefully();
    }
}
