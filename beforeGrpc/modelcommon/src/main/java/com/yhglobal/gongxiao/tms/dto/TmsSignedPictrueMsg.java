package com.yhglobal.gongxiao.tms.dto;

/**
 * @author: 葛灿
 */
public class TmsSignedPictrueMsg {
    /**
     * 图片数据
     */
    private String PictureData;
    /**
     * 图片类型
     */
    private String PictureType;
    /**
     * 拍照时间
     */
    private String PictureTime;

    public String getPictureData() {
        return PictureData;
    }

    public void setPictureData(String pictureData) {
        PictureData = pictureData;
    }

    public String getPictureType() {
        return PictureType;
    }

    public void setPictureType(String pictureType) {
        PictureType = pictureType;
    }

    public String getPictureTime() {
        return PictureTime;
    }

    public void setPictureTime(String pictureTime) {
        PictureTime = pictureTime;
    }
}
