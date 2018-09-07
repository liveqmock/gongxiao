package com.yhglobal.gongxiao.type;

/**
 * WMS客户渠道
 */
public enum WmsSourceChannel {
    CHANNEL_SHAVER("1", "深圳越海全球供应链有限公司"),
    CHANNEL_TMALL("2", "深圳越海全球供应链有限公司"),
    CHANNEL_JMGO("3", "深圳越海全球供应链有限公司"),
    CHANNEL_TUANGOU("4", "深圳越海全球供应链有限公司"),
    CHANNEL_PHJD("5", "深圳越海全球供应链有限公司");


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
