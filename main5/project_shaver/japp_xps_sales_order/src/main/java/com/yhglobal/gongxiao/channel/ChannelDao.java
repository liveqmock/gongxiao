package com.yhglobal.gongxiao.channel;

import com.yhglobal.gongxiao.channel.mapping.ChannelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ChannelDao {

    @Autowired
    ChannelMapper channelMapper;

    public Channel selectByChannelId(int channelId){
        return channelMapper.selectByChannelId(channelId);
    }

}
