package com.yhglobal.gongxiao.type;

/**
 * 调拨方式
 * @author liukai
 */
public enum WmsAllocateWay {
    ALLOCATE_DIFFERENT_STOCK(2,"跨仓调拨"),
    ALLOCATE_SAME_STOCK(1,"同仓调拨");

    int allocateWay;
    String allocateWayDesc;

    WmsAllocateWay(int allocateWay, String allocateWayDesc) {
        this.allocateWay = allocateWay;
        this.allocateWayDesc = allocateWayDesc;
    }

    public int getAllocateWay() {
        return allocateWay;
    }

    public void setAllocateWay(int allocateWay) {
        this.allocateWay = allocateWay;
    }

    public String getAllocateWayDesc() {
        return allocateWayDesc;
    }

    public void setAllocateWayDesc(String allocateWayDesc) {
        this.allocateWayDesc = allocateWayDesc;
    }
}
