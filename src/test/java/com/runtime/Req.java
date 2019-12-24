package com.runtime;

import java.io.Serializable;

/**
 * @author chenbin
 * @ClassName Req
 * @Description TODO
 * @date 2019/12/22 10:54
 * @Vsersion
 */
public class Req implements Serializable {


    private String id;
    private String name;
    private String requestMessge;
    private byte[] attachment;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRequestMessge() {
        return requestMessge;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRequestMessge(String requestMessge) {
        this.requestMessge = requestMessge;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }
}
