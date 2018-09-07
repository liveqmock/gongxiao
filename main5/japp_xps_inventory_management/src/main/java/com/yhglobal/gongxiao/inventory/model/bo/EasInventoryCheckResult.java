package com.yhglobal.gongxiao.inventory.model.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * 每天核对EAS库存结果
 */
public class EasInventoryCheckResult implements Serializable{
    private long id;             //主键id
    private long projectId;      //项目id
    private Date dateTime;        //日期
    private String projectName;   //项目名称
    private int xpsInventoryQuantity;   //分销系统库存总量
    private int easInventoryQuantity;   //EAS库存总量
    private int differentQuantity;       //差异数量
    private Boolean exception;           //是否异常
    private Date createTime;             //创建时间

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getXpsInventoryQuantity() {
        return xpsInventoryQuantity;
    }

    public void setXpsInventoryQuantity(int xpsInventoryQuantity) {
        this.xpsInventoryQuantity = xpsInventoryQuantity;
    }

    public int getEasInventoryQuantity() {
        return easInventoryQuantity;
    }

    public void setEasInventoryQuantity(int easInventoryQuantity) {
        this.easInventoryQuantity = easInventoryQuantity;
    }

    public int getDifferentQuantity() {
        return differentQuantity;
    }

    public void setDifferentQuantity(int differentQuantity) {
        this.differentQuantity = differentQuantity;
    }

    public Boolean getException() {
        return exception;
    }

    public void setException(Boolean exception) {
        this.exception = exception;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
