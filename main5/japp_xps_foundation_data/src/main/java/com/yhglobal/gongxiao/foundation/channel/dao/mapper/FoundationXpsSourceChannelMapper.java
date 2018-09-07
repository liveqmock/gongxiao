package com.yhglobal.gongxiao.foundation.channel.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.channel.model.FoundationXpsSourceChannel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface FoundationXpsSourceChannelMapper extends BaseMapper {
    @Delete({
        "delete from foundation_xps_source_channel",
        "where rowId = #{rowId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer rowId);

    @Insert({
        "insert into foundation_xps_source_channel (rowId, channelStatus, ",
        "deleteFlag, xpsProjectId, ",
        "xpsChannelId, xpsChannelSecret, ",
        "xpsChannelName, wmsCustCode, ",
        "wmsOrderSource, xpsWarehouseNotifyUrl, ",
        "tmsCustomerCode, tmsCustomerName, ",
        "tmsProjectName, xpsTransportationNotifyUrl)",
        "values (#{rowId,jdbcType=INTEGER}, #{channelStatus,jdbcType=SMALLINT}, ",
        "#{deleteFlag,jdbcType=SMALLINT}, #{xpsProjectId,jdbcType=BIGINT}, ",
        "#{xpsChannelId,jdbcType=VARCHAR}, #{xpsChannelSecret,jdbcType=VARCHAR}, ",
        "#{xpsChannelName,jdbcType=VARCHAR}, #{wmsCustCode,jdbcType=VARCHAR}, ",
        "#{wmsOrderSource,jdbcType=VARCHAR}, #{xpsWarehouseNotifyUrl,jdbcType=VARCHAR}, ",
        "#{tmsCustomerCode,jdbcType=VARCHAR}, #{tmsCustomerName,jdbcType=VARCHAR}, ",
        "#{tmsProjectName,jdbcType=VARCHAR}, #{xpsTransportationNotifyUrl,jdbcType=VARCHAR})"
    })
    int insert(FoundationXpsSourceChannel record);

    @Select({
        "select",
        "rowId, channelStatus, deleteFlag, xpsProjectId, xpsChannelId, xpsChannelSecret, ",
        "xpsChannelName, wmsCustCode, wmsOrderSource, xpsWarehouseNotifyUrl, tmsCustomerCode, ",
        "tmsCustomerName, tmsProjectName, xpsTransportationNotifyUrl",
        "from foundation_xps_source_channel",
        "where rowId = #{rowId,jdbcType=INTEGER}"
    })
    FoundationXpsSourceChannel selectByPrimaryKey(Integer rowId);

    @Select({
            "select",
            "rowId, channelStatus, deleteFlag, xpsProjectId, xpsChannelId, xpsChannelSecret, ",
            "xpsChannelName, wmsCustCode, wmsOrderSource, xpsWarehouseNotifyUrl, tmsCustomerCode, ",
            "tmsCustomerName, tmsProjectName, xpsTransportationNotifyUrl",
            "from foundation_xps_source_channel",
            "where xpsChannelId = #{xpsChannelId}"
    })
    FoundationXpsSourceChannel getByChannelId(String xpsChannelId);

    @Update({
        "update foundation_xps_source_channel",
        "set channelStatus = #{channelStatus,jdbcType=SMALLINT},",
          "deleteFlag = #{deleteFlag,jdbcType=SMALLINT},",
          "xpsProjectId = #{xpsProjectId,jdbcType=BIGINT},",
          "xpsChannelId = #{xpsChannelId,jdbcType=VARCHAR},",
          "xpsChannelSecret = #{xpsChannelSecret,jdbcType=VARCHAR},",
          "xpsChannelName = #{xpsChannelName,jdbcType=VARCHAR},",
          "wmsCustCode = #{wmsCustCode,jdbcType=VARCHAR},",
          "wmsOrderSource = #{wmsOrderSource,jdbcType=VARCHAR},",
          "xpsWarehouseNotifyUrl = #{xpsWarehouseNotifyUrl,jdbcType=VARCHAR},",
          "tmsCustomerCode = #{tmsCustomerCode,jdbcType=VARCHAR},",
          "tmsCustomerName = #{tmsCustomerName,jdbcType=VARCHAR},",
          "tmsProjectName = #{tmsProjectName,jdbcType=VARCHAR},",
          "xpsTransportationNotifyUrl = #{xpsTransportationNotifyUrl,jdbcType=VARCHAR}",
        "where rowId = #{rowId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(FoundationXpsSourceChannel record);
}