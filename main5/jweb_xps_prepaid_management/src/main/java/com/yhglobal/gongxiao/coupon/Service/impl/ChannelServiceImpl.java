package com.yhglobal.gongxiao.coupon.Service.impl;

import com.yhglobal.gongxiao.coupon.Service.ChannelService;
import com.yhglobal.gongxiao.coupon.dao.ChannelDao;
import com.yhglobal.gongxiao.coupon.model.PrepaidChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 王帅
 */
@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    ChannelDao channelDao;

    public PrepaidChannel selectByChannelId(String channelId){
        return channelDao.selectByChannelId(channelId);
    }


}
