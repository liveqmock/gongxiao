package com.yhglobal.gongxiao.constant;

/**
 *
 * @author 王帅
 */
public enum CouponSourceChannel {

    CHANNEL_PHTXD("1", "飞利浦剃须刀项目"),
    CHANNEL_PHJD("2", "飞利浦京东项目")


    ;
    String channelId;
    String channelName;

    CouponSourceChannel(String channelId, String channelName) {
        this.channelId = channelId;
        this.channelName = channelName;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getChannelName() {
        return channelName;
    }
}
