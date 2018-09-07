package com.yhglobal.gongxiao.channel.mapping;

import com.yhglobal.gongxiao.channel.Channel;
import com.yhglobal.gongxiao.common.mapper.BaseMapper;
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
