package com.yhglobal.gongxiao.transport.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.Serializable;
import java.security.MessageDigest;

/**
 * 来源渠道类
 * @author : liukai
 */
public class TransportationChannel implements Serializable{
    private  static Logger  logger = LoggerFactory.getLogger(TransportationChannel.class);

    private int id;         //主键id
    private int channelID;  //渠道id
    private String custCode;        //客户编码
    private String channelSecret;   //渠道密文
    private String channelName;     //渠道名称
    private String channelUrl;      //渠道对应的url
    private int type;               //渠道类型

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
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

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
