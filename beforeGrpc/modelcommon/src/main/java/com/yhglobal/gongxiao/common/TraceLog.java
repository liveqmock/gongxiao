package com.yhglobal.gongxiao.common;

import java.io.Serializable;
import java.util.Date;

/**
 * 通用tracelog模型 对应到database中的tracelog字段
 * <p>
 * 注: 若自己的业务模块tracelog有别的需求，可在自己的模块中定义，不使用该通用的tracelog
 */
public class TraceLog implements Serializable {

    public TraceLog() {
        super();
    }

    /**
     * 新建一个操作日志
     *
     * @param opTime  操作时间
     * @param opUid   操作人id
     * @param opName  操作人姓名
     * @param content 操作内容
     * @return
     */
    public TraceLog(long opTime, String opUid, String opName, String content) {
        super();
        this.opTime = opTime;
        this.opUid = opUid;
        this.opName = opName;
        this.content = content;
    }
    public TraceLog(long opTime, String opUid, String opName,Integer value, String content) {
        super();
        this.opTime = opTime;
        this.opUid = opUid;
        this.opName = opName;
        this.content = content;
        this.value = value;
    }

    long opTime;   //操作时间(当前毫秒值)

    String opUid;  //操作人id

    String opName; //操作人名字

    String content;//操作内容

    Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public long getOpTime() {
        return opTime;
    }

    public void setOpTime(long opTime) {
        this.opTime = opTime;
    }

    public String getOpUid() {
        return opUid;
    }

    public void setOpUid(String opUid) {
        this.opUid = opUid;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
