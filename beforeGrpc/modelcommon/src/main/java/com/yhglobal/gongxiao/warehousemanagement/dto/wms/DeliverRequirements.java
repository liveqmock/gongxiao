package com.yhglobal.gongxiao.warehousemanagement.dto.wms;

import java.io.Serializable;

/**
 * 配送要求信息
 * @author liukai
 */
public class DeliverRequirements implements Serializable {
    private int scheduleType;    //投递时延要求 可选
    private String scheduleDay;  //送达时间 可选
    private String scheduleStart;//送达开始时间 可选
    private String scheduleEnd;  //送达结束时间 可选

    public int getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(int scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getScheduleDay() {
        return scheduleDay;
    }

    public void setScheduleDay(String scheduleDay) {
        this.scheduleDay = scheduleDay;
    }

    public String getScheduleStart() {
        return scheduleStart;
    }

    public void setScheduleStart(String scheduleStart) {
        this.scheduleStart = scheduleStart;
    }

    public String getScheduleEnd() {
        return scheduleEnd;
    }

    public void setScheduleEnd(String scheduleEnd) {
        this.scheduleEnd = scheduleEnd;
    }
}
