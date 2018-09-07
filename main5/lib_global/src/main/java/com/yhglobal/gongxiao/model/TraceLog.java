package com.yhglobal.gongxiao.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用tracelog模型 对应到database中的tracelog字段
 * <p>
 * 注: 若自己的业务模块tracelog有别的需求，可在自己的模块中定义，不使用该通用的tracelog
 */
public class TraceLog implements Serializable {

    long opTime; //操作时间(当前毫秒值)

    String opUid;  //操作人id

    String opName; //操作人名字

    Integer cmdCode; //操作指令code 可为空 需要跟踪部分指令的场景用

    String content;//操作内容

    String extra; //JSON格式(map<String, Object>)的额外信息 比如参数

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

    public TraceLog(long opTime, String opUid, String opName, Integer cmdCode, String content) {
        super();
        this.opTime = opTime;
        this.opUid = opUid;
        this.opName = opName;
        this.cmdCode = cmdCode;
        this.content = content;
    }


    public void putExtraParam(String paramKey, Object paramValue) {
        JSONObject jsonObject;
        if (extra == null) {
            jsonObject = new JSONObject();
        } else {
            jsonObject = JSON.parseObject(extra);
        }
        jsonObject.put(paramKey, paramValue);
        extra = JSON.toJSONString(jsonObject);
    }

    public Object getExtraParam(String paramKey) {
        if (extra == null) return null;
        JSONObject jsonObject = JSON.parseObject(extra);
        return jsonObject.get(paramKey);
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

    public Integer getCmdCode() {
        return cmdCode;
    }

    public void setCmdCode(Integer cmdCode) {
        this.cmdCode = cmdCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

}
