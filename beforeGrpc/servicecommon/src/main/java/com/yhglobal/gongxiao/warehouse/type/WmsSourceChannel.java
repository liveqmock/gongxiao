package com.yhglobal.gongxiao.warehouse.type;

/**
 * WMS客户渠道
 */
public enum WmsSourceChannel {
    CHANNEL_YHGLOBAL("1", "深圳越海全球供应链有限公司"),
    CHANNEL_TMS("2", "深圳越海全球供应链有限公司");

    String channelId;
    String channelName;

    WmsSourceChannel(String channelId, String channelName) {
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
