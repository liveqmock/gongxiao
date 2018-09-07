package com.yhglobal.gongxiao.coupon.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.coupon.model.PrepaidChannel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @author 王帅
 */
public interface ChannelMapper extends BaseMapper {

    @Insert({
            "insert into yhglobal_coupon_ledger_writeoff_flow (rowId ,channelStatus,deleteFlag,channelId,channelSecret,channelName,notifyUrl)",
            "values (#{rowId}, ",
            "#{channelStatus}, #{deleteFlag}, ",
            "#{channelId}, #{channelSecret}, ",
            "#{channelName}, #{notifyUrl})"
    })
    int insert(PrepaidChannel prepaidChannel);


    /**
     *
     */
    @Select({"select",
            "rowId ,channelStatus,deleteFlag,channelId,channelSecret,channelName,notifyUrl",
            "from coupon_source_channel",
            "where channelId = #{channelId, jdbcType=VARCHAR}"
    })
    PrepaidChannel selectByChannelId(String channelId);
}
