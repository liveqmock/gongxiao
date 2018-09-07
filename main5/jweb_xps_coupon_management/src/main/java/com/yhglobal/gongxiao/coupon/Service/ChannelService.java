package com.yhglobal.gongxiao.coupon.Service;

import com.yhglobal.gongxiao.coupon.model.CouponChannel;

/**
 * @author 王帅
 */
public interface ChannelService {

    public CouponChannel selectByChannelId(String channelId);
}
