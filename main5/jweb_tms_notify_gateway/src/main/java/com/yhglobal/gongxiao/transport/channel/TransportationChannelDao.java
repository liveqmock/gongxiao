package com.yhglobal.gongxiao.transport.channel;

import com.yhglobal.gongxiao.transport.channel.mapping.TransportationChannelMapper;
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
