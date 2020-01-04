package com.heatBead;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

public class ServerHeartBeatHandler extends ChannelHandlerAdapter {

    private static HashMap<String,String> AUTH_IP_MAP = new HashMap<String, String>();
    private static final String SUCCESS_KEY = "auth_success_key";

    static {
        AUTH_IP_MAP.put("192.168.0.104","1234");
    }

    private boolean auth(ChannelHandlerContext context,Object msg) {
        String[] ret = ((String) msg).split(",");
        String auth = AUTH_IP_MAP.get(ret[0]);
        if (auth != null && auth.equals(ret[1])) {
            context.writeAndFlush(SUCCESS_KEY);
            return true;
        } else {
            context.writeAndFlush("auth failure!").addListener(ChannelFutureListener.CLOSE);
            return false;
        }
    }

    public void channelRead(ChannelHandlerContext context,Object msg) throws Exception {
        if (msg instanceof String) {
            auth(context,msg);
        } else if (msg instanceof RequestInfo) {
            RequestInfo info = (RequestInfo) msg;
            System.out.println("----------------------------------");
            System.out.println("当前主机IP为：" + info.getIp());
            System.out.println("当前主机cpu情况：");
            HashMap<String,Object> cpu = info.getCpuPerMap();
            System.out.println("总使用率：" + cpu.get("combined"));
            System.out.println("用户使用率：" + cpu.get("user"));
            System.out.println("系统使用率：" + cpu.get("sys"));
            System.out.println("等待率：" + cpu.get("wait"));
            System.out.println("空闲率：" + cpu.get("idle"));

            System.out.println("当前主机memory情况：");
            HashMap<String,Object> memoryMap = info.getMemoryMap();
            System.out.println("内存总量：" + memoryMap.get("total"));
            System.out.println("当前内存使用量为：" + memoryMap.get("used"));
            System.out.println("当前内存剩余量为：" + memoryMap.get("free"));
            System.out.println("----------------------------------");
            context.writeAndFlush("info received!");
        } else {
            context.writeAndFlush("connect failure!").addListener(ChannelFutureListener.CLOSE);
        }
    }


}
