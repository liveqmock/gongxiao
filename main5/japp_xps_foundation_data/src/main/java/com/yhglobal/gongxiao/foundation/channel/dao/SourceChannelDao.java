package com.yhglobal.gongxiao.foundation.channel.dao;

import com.yhglobal.gongxiao.foundation.channel.dao.mapper.FoundationXpsSourceChannelMapper;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.foundation.channel.model.FoundationXpsSourceChannel;
import com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Repository
public class SourceChannelDao {

    @Autowired
    FoundationXpsSourceChannelMapper sourceChannelMapper;

    /**
     *
     * @param xpsChannelId
     * @return
     */
    public FoundationXpsSourceChannel getChannelById(String xpsChannelId ){
        return sourceChannelMapper.getByChannelId(xpsChannelId);
    }

}
