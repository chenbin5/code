package com.heatBead;

import java.io.Serializable;
import java.util.HashMap;

public class RequestInfo implements Serializable {

    private String ip;
    private HashMap<String,Object> cpuPerMap;
    private HashMap<String,Object> memoryMap;

    public String getIp() {
        return ip;
    }

    public HashMap<String, Object> getCpuPerMap() {
        return cpuPerMap;
    }

    public HashMap<String, Object> getMemoryMap() {
        return memoryMap;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setCpuPerMap(HashMap<String, Object> cpuPerMap) {
        this.cpuPerMap = cpuPerMap;
    }

    public void setMemoryMap(HashMap<String, Object> memoryMap) {
        this.memoryMap = memoryMap;
    }
}
