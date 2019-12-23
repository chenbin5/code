package com.serial;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author chenbin
 * @ClassName ServerHandler
 * @Description TODO
 * @date 2019/12/23 20:46
 * @Vsersion
 */
public class ServerHandler extends ChannelHandlerAdapter {

    public void channelRead(ChannelHandlerContext ctx,Object msg) {

        Req req = (Req) msg;
        System.out.println("Server：" + req.getId() + ", " + req.getName() + ", " + req.getRequestMessge());

        Resp resp = new Resp();
        resp.setId(req.getId());
        resp.setName(req.getName());
        resp.setResponseMessage("响应内容为：" + req.getId());
        ctx.writeAndFlush(resp);
    }
}
