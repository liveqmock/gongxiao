package com.yhglobal.gongxiao.transport.channel.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.transport.channel.TransportationChannel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TransportationChannelMapper extends BaseMapper {
    @Select({
            "select custCode, channelName, channelUrl, channelSecret, type ",
            "from transportation_source_channel",
            "where channelId = #{channelId}"
    })
    TransportationChannel selectByChannelId(@Param("channelId") int channelId);

}
