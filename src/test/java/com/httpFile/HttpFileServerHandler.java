package com.httpFile;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;
import org.apache.tomcat.jni.Error;
import sun.net.www.http.ChunkedInputStream;
import sun.text.resources.uk.FormatData_uk;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;
import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static org.springframework.http.HttpHeaders.*;

public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private String url;

    public HttpFileServerHandler(String url) {
        this.url = url;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (!request.decoderResult().isSuccess()) {
            sendError(ctx, BAD_REQUEST);
            return;
        }
        if (request.method() != GET) {
            sendError(ctx,METHOD_NOT_ALLOWED);
            return;
        }
        final String uri = request.uri();
        final String path = sanitizeUri(uri);
        if (null == path) {
            //403
            sendError(ctx,FORBIDDEN);
            return;
        }
        File file = new File(path);
        if (file.isHidden() || !file.exists()) {
            sendError(ctx,NOT_FOUND);
            return;
        }
        if (file.isDirectory()) {
            if (uri.endsWith("/")) {
                sendListing(ctx,file);
            } else {
                setRedirect(ctx,uri + '/');
            }
            return;
        }
        if (!file.isFile()) {
            sendError(ctx,FORBIDDEN);
            return;
        }
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file,"r");
        } catch (Exception e) {
            sendError(ctx,NOT_FOUND);
            return;
        }
        long fileLength = randomAccessFile.length();
        HttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,OK);
        HttpHeaderUtil.setContentLength(response,fileLength);
        setContentTypeHeader(response,file);
        if (HttpHeaderUtil.isKeepAlive(request)) {
            response.headers().set(CONNECTION,HttpHeaderValues.KEEP_ALIVE);
        }
        ctx.write(response);
        //ChannelFuture sendFileFuture;
        ChannelFuture sendFileFuture = ctx.write(new ChunkedFile(randomAccessFile,0,fileLength,8192),ctx.newProgressivePromise());
        sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
            @Override
            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
                if (total<0) {
                    System.err.println("Transfer progress：" + progress);
                } else {
                    System.err.println("Transfer progress：" + progress + "/" + total);
                }
            }

            @Override
            public void operationComplete(ChannelProgressiveFuture channelProgressiveFuture) throws Exception {
                System.out.println("Transfer complate...");
            }
        });
        ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        if (!HttpHeaderUtil.isKeepAlive(request)) {
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) throws Exception {
        if (ctx.channel().isActive()) {
            sendError(ctx,INTERNAL_SERVER_ERROR);
            ctx.close();
        }
    }

    private static void setRedirect(ChannelHandlerContext ctx,String newUrl) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,FOUND);
        response.headers().set(LOCATION,newUrl);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,status, Unpooled.copiedBuffer("Failure：" + status.toString() + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE,"text/plain;charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void setContentTypeHeader(HttpResponse response, File file) {
        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
        response.headers().set(CONTENT_TYPE,mimetypesFileTypeMap.getContentType(file.getPath()));
    }

    private static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");

    private String sanitizeUri(String uri) {
        try {
            uri = URLDecoder.decode(uri,"utf-8");
        } catch (Exception e) {
            try {
                uri = URLDecoder.decode(uri,"ISO-8859-1");
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        }
        if (!uri.startsWith(url)) {
            return null;
        }
        if (!uri.endsWith("/")) {
            return null;
        }
        uri = uri.replace('/',File.separatorChar);
        if (uri.contains(File.separator + '.')
            || uri.contains('.'+File.separator) || uri.startsWith(".")
            || uri.endsWith(".") || INSECURE_URI.matcher(url).matches()) {
            return null;
        }
        return System.getProperty("user.dir") + File.separator + uri;
    }

    private static final Pattern ALLOWED_FILE_NAME = Pattern.compile("[A-Za-z0-9][-_A-Za-z0-9\\.]*");

    private static void sendListing(ChannelHandlerContext ctx,File file) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,OK);
        response.headers().set(CONTENT_TYPE,"text/html;charset=UTF-8");
        //添加文本内容
        StringBuilder ret = new StringBuilder();
        String dirPath = file.getPath();
        ret.append("<!DOCTYPE html>\r\n");
        ret.append("<html><head><title>");
        ret.append(dirPath);
        ret.append(" 目录：");
        ret.append("</title></head></html>");
        ret.append("<h3>");
        ret.append(dirPath).append(" 目录：");
        ret.append("</h3>\r\n");
        ret.append("<ul>");
        ret.append("<li>链接：<a href=\"../\">..</a></li>\r\n");

        for (File f:file.listFiles()) {

            if (f.isHidden() || !f.canRead()) {
                continue;
            }
            String name = f.getName();
            if (!ALLOWED_FILE_NAME.matcher(name).matches()) {
                continue;
            }
            ret.append("<li>链接：<a href=\">");
            ret.append(name);
            ret.append("\">");
            ret.append(name);
            ret.append("</a></li>\r\n");
        }
        ret.append("</ul></body></html>\r\n");
        ByteBuf buffer = Unpooled.copiedBuffer(ret,CharsetUtil.UTF_8);
        response.content().writeBytes(buffer);
        buffer.release();
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
