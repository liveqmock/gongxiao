package com.yhglobal.gongxiao.transport.common.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.transport.model.TransportationOutboundOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author 葛灿
 */
public interface TransportationOutboundOrderMapper extends BaseMapper {

    @Select({
            "select oid, projectId, outboundOrderNo, channelId, channelToken, ",
            "requestJson, dataVersion, syncTmsStatus, tmsResponse, projectId",
            "from ${prefix}_transportation_outbound_order",
            "where",
            "outboundOrderNo = #{outboundOrderNo}"
    })
    TransportationOutboundOrder getOrderByOrderNo(
            @Param("prefix") String prefix,
            @Param("outboundOrderNo") String outboundOrderNo);

    @Insert({
            "insert into ${prefix}_transportation_outbound_order",
            "(outboundOrderNo, projectId, channelId, channelToken, requestJson, syncTmsStatus, createTime)",
            "values",
            "(#{outboundOrderNo}, #{projectId}, #{channelId}, #{channelToken}, #{requestJson}, #{syncTmsStatus}, NOW() )"
    })
    int insert(@Param("prefix") String prefix,
               @Param("projectId") long projectId,
               @Param("outboundOrderNo") String outboundOrderNo,
               @Param("channelId") String channelId,
               @Param("channelToken") String channelToken,
               @Param("requestJson") String requestJson,
               @Param("syncTmsStatus") int syncTmsStatus);

    @Select({
            "select outboundOrderNo",
            "from ${prefix}_transportation_outbound_order",
            "where",
            "syncTmsStatus = #{syncTmsStatus}"
    })
    List<String> selectListBySyncTmsStatus(@Param("prefix") String prefix,
                                           @Param("syncTmsStatus") int syncTmsStatus);

    @Update({
            "UPDATE ${prefix}_transportation_outbound_order",
            "SET",
            "syncTmsStatus = #{syncTmsStatus},",
            "tmsResponse = #{tmsResponse},",
            "dataVersion = dataVersion+1",
            "WHERE",
            "oid = #{oid} AND dataVersion=#{dataVersion}"
    })
    int updateSyncTmsStatus(@Param("prefix") String prefix,
                            @Param("oid") long oid,
                            @Param("dataVersion") long dataVersion,
                            @Param("syncTmsStatus") int syncTmsStatus,
                            @Param("tmsResponse") String tmsResponse);
}
