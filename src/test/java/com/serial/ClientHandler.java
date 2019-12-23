package com.serial;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author chenbin
 * @ClassName ClientHandler
 * @Description TODO
 * @date 2019/12/23 20:53
 * @Vsersion
 */
public class ClientHandler extends ChannelHandlerAdapter {

    public void channelRead(ChannelHandlerContext ctx,Object message) {

        try {
            Resp resp = (Resp) message;
            System.out.println("Client：" + resp.getId() + ", " + resp.getName() + ", " + resp.getResponseMessage());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
