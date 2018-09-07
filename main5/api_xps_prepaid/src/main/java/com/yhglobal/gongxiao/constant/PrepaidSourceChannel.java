package com.yhglobal.gongxiao.constant;

public enum PrepaidSourceChannel {

    CHANNEL_PHTXD("1", "飞利浦剃须刀项目"),
    CHANNEL_PHJD("2", "飞利浦京东项目")


    ;
    String channelId;
    String channelName;

    PrepaidSourceChannel(String channelId, String channelName) {
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
