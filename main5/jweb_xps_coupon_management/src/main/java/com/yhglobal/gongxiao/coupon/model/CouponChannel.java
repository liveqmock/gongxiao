package com.yhglobal.gongxiao.coupon.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.Serializable;
import java.security.MessageDigest;

/**
 * 来源渠道类
 * @author : liukai
 */
public class CouponChannel implements Serializable{
    private  static Logger  logger = LoggerFactory.getLogger(CouponChannel.class);

//      `rowId` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
//            `channelStatus` smallint(6) NOT NULL COMMENT '渠道状态 1:草稿 2:已提交待审核 6:审核通过 9:冻结',
//            `deleteFlag` smallint(6) NOT NULL DEFAULT '0' COMMENT '渠道是否已删除',
//            `channelId` varchar(20) NOT NULL COMMENT '渠道id',
//            `channelSecret` varchar(256) NOT NULL COMMENT '渠道密文',
//            `channelName` varchar(256) NOT NULL COMMENT '渠道名称',
//            `notifyUrl` varchar(256) NOT NULL COMMENT '该渠道回告的url',
    private int rowId;         //主键id
    private Integer channelStatus; // '渠道状态 1:草稿 2:已提交待审核 6:审核通过 9:冻结',
    private Integer deleteFlag; // '渠道是否已删除' 1 删除 2未删除
    private String channelID;  //渠道id
    private String channelSecret;   //渠道密文
    private String channelName;     //渠道名称
    private String notifyUrl;      //渠道对应的url


    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        CouponChannel.logger = logger;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public Integer getChannelStatus() {
        return channelStatus;
    }

    public void setChannelStatus(Integer channelStatus) {
        this.channelStatus = channelStatus;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getChannelSecret() {
        return channelSecret;
    }

    public void setChannelSecret(String channelSecret) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64Encoder = new BASE64Encoder();
            this.channelSecret = base64Encoder.encode(md5.digest((this.channelName).getBytes("utf8")));
        } catch (Exception e) {
            logger.error("给渠道名称加密的时候出错");
        }
    }



}
