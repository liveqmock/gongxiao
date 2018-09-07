package com.yhglobal.gongxiao.tms.model;

import java.util.Date;

/**
 * @author: 葛灿
 */
public class SignedPictrueMsg {
    /**
     * 数据库主键id
     */
    private long rowId;
    /**
     * 出库单号
     */
    private String custOrdNo;
    /**
     * 备注
     */
    private String remark;
    /**
     * 图片数据
     */
    private String pictureData;
    /**
     * 图片类型
     */
    private String pictureType;
    /**
     * 拍照时间
     */
    private Date pictureTime;
    private Date createTime;

    public long getRowId() {
        return rowId;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public String getCustOrdNo() {
        return custOrdNo;
    }

    public void setCustOrdNo(String custOrdNo) {
        this.custOrdNo = custOrdNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPictureData() {
        return pictureData;
    }

    public void setPictureData(String pictureData) {
        this.pictureData = pictureData;
    }

    public String getPictureType() {
        return pictureType;
    }

    public void setPictureType(String pictureType) {
        this.pictureType = pictureType;
    }

    public Date getPictureTime() {
        return pictureTime;
    }

    public void setPictureTime(Date pictureTime) {
        this.pictureTime = pictureTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
