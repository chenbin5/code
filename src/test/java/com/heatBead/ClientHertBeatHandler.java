package com.heatBead;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ClientHertBeatHandler extends ChannelHandlerAdapter {

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> scheduledFuture;
    private InetAddress address;
    private static final String SUCCESS_KEY = "auth_success_key";

    public void channelActive(ChannelHandlerContext context) throws Exception {
        address = InetAddress.getLocalHost();
        String ip = address.getHostAddress();
        String key = "1234";
        String auth = ip + "," + key;
        context.writeAndFlush(auth);
    }

    public void channelRead(ChannelHandlerContext context,Object msg) throws Exception {
        try {
            if (msg instanceof String) {
                String ret = (String)msg;
                if (SUCCESS_KEY.equals(ret)) {
                    this.scheduledFuture = this.scheduledExecutorService.scheduleWithFixedDelay(new HeartBeatTask(context),0,10, TimeUnit.SECONDS);
                    System.out.println(msg);
                }
            } else {
                System.out.println(msg);
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    private class HeartBeatTask implements Runnable {

        private final ChannelHandlerContext context;

        private HeartBeatTask(final ChannelHandlerContext context) {
            this.context = context;
        }
        @Override
        public void run() {
            try {
                RequestInfo info = new RequestInfo();
                info.setIp(address.getHostAddress());
                Sigar sigar = new Sigar();
                CpuPerc cpuPerc = sigar.getCpuPerc();
                HashMap<String,Object> cpuPerMap = new HashMap<String, Object>();
                cpuPerMap.put("combined",cpuPerc.getCombined());
                cpuPerMap.put("user",cpuPerc.getUser());
                cpuPerMap.put("sys",cpuPerc.getUser());
                cpuPerMap.put("wait",cpuPerc.getWait());
                cpuPerMap.put("idle",cpuPerc.getIdle());
                Mem mem = sigar.getMem();
                HashMap<String,Object> memoryMap = new HashMap<String, Object>();
                memoryMap.put("total",mem.getTotal() / 1024L);
                memoryMap.put("used",mem.getUsed() / 1024L);
                memoryMap.put("free",mem.getFree() / 1024L);
                info.setCpuPerMap(cpuPerMap);
                info.setMemoryMap(memoryMap);
                context.writeAndFlush(info);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void exceptionCaught(ChannelHandlerContext context,Throwable cause) throws Exception {
            cause.printStackTrace();
            if (scheduledFuture != null) {
                scheduledFuture.cancel(true);
                scheduledFuture = null;
            }
            context.fireExceptionCaught(cause);
        }
    }
}
