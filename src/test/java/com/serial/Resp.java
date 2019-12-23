package com.serial;

import java.io.Serializable;

/**
 * @author chenbin
 * @ClassName Resp
 * @Description TODO
 * @date 2019/12/22 11:58
 * @Vsersion
 */
public class Resp implements Serializable {

    private String id;
    private String name;
    private String responseMessage;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
