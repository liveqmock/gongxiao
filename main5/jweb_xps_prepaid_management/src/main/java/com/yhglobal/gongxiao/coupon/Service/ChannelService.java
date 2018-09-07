package com.yhglobal.gongxiao.coupon.Service;

import com.yhglobal.gongxiao.coupon.model.PrepaidChannel;

/**
 * @author 王帅
 */
public interface ChannelService {

    public PrepaidChannel selectByChannelId(String channelId);
}
