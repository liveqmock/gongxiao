package com.yhglobal.gongxiao.transportation.channel;

import com.yhglobal.gongxiao.transportation.channel.mapping.TransportationChannelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TransportationChannelDao {

    @Autowired
    TransportationChannelMapper transportationChannelMapper;

    public TransportationChannel selectByChannelId(int channelId){
        return transportationChannelMapper.selectByChannelId(channelId);
    }

}
