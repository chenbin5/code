package com.serial;

import com.utils.GzipUtils;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author chenbin
 * @ClassName ServerHandler
 * @Description TODO
 * @date 2019/12/23 20:46
 * @Vsersion
 */
public class ServerHandler extends ChannelHandlerAdapter {

    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{

        Req req = (Req) msg;
        System.out.println("Server：" + req.getId() + ", " + req.getName() + ", " + req.getRequestMessge());

        byte[] attachment = GzipUtils.ungzip(req.getAttachment());
        String writePath = System.getProperty("user.dir") + File.separatorChar + "resource" + req.getId() + ".jpg";
        FileOutputStream fos = new FileOutputStream(writePath);
        fos.write(attachment);
        fos.close();
        Resp resp = new Resp();
        resp.setId(req.getId());
        resp.setName(req.getName());
        resp.setResponseMessage("响应内容为：" + req.getId());

        ctx.writeAndFlush(resp);
    }
}
