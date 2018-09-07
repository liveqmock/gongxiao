package com.yhglobal.gongxiao.foundation.channel.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.channel.Channel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ChannelMapper extends BaseMapper {
    @Select({
            "select custCode, channelName, channelUrl, type ",
            "from gongxiao_source_channel",
            "where channelId = #{channelId}"
    })
    Channel selectByChannelId(@Param("channelId") int channelId);

}
