package com.yhglobal.gongxiao.type;

/**
 * WMS客户渠道
 */
public enum TransportationSourceChannel {
    CHANNEL_SHAVER("1", "小家电");

    String channelId;
    String channelName;

    TransportationSourceChannel(String channelId, String channelName) {
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
