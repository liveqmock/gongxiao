package com.yhglobal.gongxiao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作记录
 *
 * @author: 陈浩
 * @create: 2018-03-16 11:10
 **/
public class OperateHistoryRecord implements Serializable{
    /**
     * 状态 0.新建 1.等待退货到仓 2.退货完成
     */
    private int operateStatus;
    /**
     * 操作功能
     */
    private String operateFunction;
    /**
     * 操作时间
     */
    private Date operateTime;

    private String operateTimeString;
    /**
     * 才做人ID
     */
    private String operateId;
    /**
     * 操作人名称
     */
    private String operateName;

    public String getOperateTimeString() {
        return operateTimeString;
    }

    public void setOperateTimeString(String operateTimeString) {
        this.operateTimeString = operateTimeString;
    }

    public String getOperateFunction() {
        return operateFunction;
    }

    public void setOperateFunction(String operateFunction) {
        this.operateFunction = operateFunction;
    }

    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId;
    }

    public int getOperateStatus() {
        return operateStatus;
    }

    public void setOperateStatus(int operateStatus) {
        this.operateStatus = operateStatus;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
}
