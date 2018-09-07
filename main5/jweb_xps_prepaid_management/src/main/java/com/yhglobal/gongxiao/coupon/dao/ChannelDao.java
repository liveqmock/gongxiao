package com.yhglobal.gongxiao.coupon.dao;

import com.yhglobal.gongxiao.coupon.dao.mapper.ChannelMapper;
import com.yhglobal.gongxiao.coupon.model.PrepaidChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author 王帅
 */
@Repository
public class ChannelDao {

    @Autowired
    ChannelMapper channelMapper;

    public PrepaidChannel selectByChannelId(String channelId){
        return channelMapper.selectByChannelId(channelId);
    }
}
