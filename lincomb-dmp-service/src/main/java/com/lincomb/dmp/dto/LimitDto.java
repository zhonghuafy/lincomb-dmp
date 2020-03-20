package com.lincomb.dmp.dto;

/**
 * Created by shiyu.cao on 2018/1/26.
 */
public class LimitDto {

    //从服务器名称
    private String slaveName;

    //设备总条数
    private int macSize;

    //设备开启顺序
    private int slaveOrder;

    //从服务器台数
    private int slaveNum;

    //开始查询条数
    private int limitStart;

    //查询条数
    private int limitSize;

    public String getSlaveName() {
        return slaveName;
    }

    public void setSlaveName(String slaveName) {
        this.slaveName = slaveName;
    }

    public int getMacSize() {
        return macSize;
    }

    public void setMacSize(int macSize) {
        this.macSize = macSize;
    }

    public int getSlaveOrder() {
        return slaveOrder;
    }

    public void setSlaveOrder(int slaveOrder) {
        this.slaveOrder = slaveOrder;
    }

    public int getSlaveNum() {
        return slaveNum;
    }

    public void setSlaveNum(int slaveNum) {
        this.slaveNum = slaveNum;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitSize() {
        return limitSize;
    }

    public void setLimitSize(int limitSize) {
        this.limitSize = limitSize;
    }
}
