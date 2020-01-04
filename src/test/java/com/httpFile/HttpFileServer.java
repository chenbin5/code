package com.httpFile;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpFileServer {

    private static final String DEFAULT_UTL = "/resource/";

    public void run(int port,final String url) throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //加入http的解码器
                            socketChannel.pipeline().addLast("http-decoder",new HttpRequestDecoder());
                            //加入ObjectAggregator解码器，作用是他会把多个消息转换为单一的FullHttpRequest或者FullHttpResponse
                            socketChannel.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
                            //加入http的编码器
                            socketChannel.pipeline().addLast("http-encoder",new HttpRequestEncoder());
                            //加入chucked 主要作用是支持异步发送的码流（大文件传输），但不专用过多的内容，防止java内存溢出
                            socketChannel.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
                            //加入自定义处理文件服务器的业务逻辑Handler
                            socketChannel.pipeline().addLast("fileServerHandler",
                                    new HttpFileServerHandler(url));
                        }
                    });
            ChannelFuture future = b.bind("192.168.0.104",port).sync();
            System.out.println("Http文件目录服务器启动，网址为：" + "http://127.0.0.1:" + port + url);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        int port = 8765;
        String url = DEFAULT_UTL;
        new HttpFileServer().run(port,url);
    }
}
