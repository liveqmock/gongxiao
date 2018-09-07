package com.yhglobal.gongxiao.transportation.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.transportataion.sendtotransportation.sales.model.CancelDispatchOrderRequest;
import com.yhglobal.gongxiao.transportation.model.TransportationCancelledOutboundOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 取消出库单mapper
 *
 * @author 葛灿
 */
public interface CancelledOutboundOrderMapper extends BaseMapper {

    /**
     * 插入对象
     *
     * @param prefix        前缀
     * @param requestJson   发送给tms的json
     * @param syncTmsStatus 同步tms状态
     * @param dataVersion   数据版本
     * @return 插入成功的记录数
     */
    @Insert({
            "INSERT INTO ${prefix}_transportation_cancelled_outbound_order",
            "(outboundOrderNo, requestJson, ",
            "syncTmsStatus, dataVersion, ",
            "createTime)",
            "VALUES",
            "(#{outboundOrderNo}, #{requestJson}, ",
            "#{syncTmsStatus}, #{dataVersion}, ",
            "NOW() )"
    })
    int insert(@Param("prefix") String prefix,
               @Param("outboundOrderNo") String outboundOrderNo,
               @Param("requestJson") String requestJson,
               @Param("syncTmsStatus") long syncTmsStatus,
               @Param("dataVersion") long dataVersion);

    /**
     * 根据出单号查询
     *
     * @param prefix          表前缀
     * @param outboundOrderNo 出库单号
     * @return 退货出库单信息
     */
    @Select({
            "SELECT oid, outboundOrderNo, requestJson, syncTmsStatus, dataVersion",
            "FROM ${prefix}_transportation_cancelled_outbound_order",
            "WHERE outboundOrderNo = #{outboundOrderNo}"
    })
    TransportationCancelledOutboundOrder getOrderByOutboundOrderNo(@Param("prefix") String prefix,
                                                                   @Param("outboundOrderNo") String outboundOrderNo);


    /**
     * 更新同步TMS状态
     *
     * @param prefix        前缀
     * @param oid           主键id
     * @param dataVersion   数据版本
     * @param syncTmsStatus 同步TMS状态
     * @param tmsResponse   tms响应
     * @return 更新成功记录数
     */
    @Update({
            "UPDATE ${prefix}_transportation_cancelled_outbound_order",
            "SET",
            "oid = #{oid},",
            "syncTmsStatus = #{syncTmsStatus},",
            "tmsResponse = #{tmsResponse},",
            "dataVersion = dataVersion+1",
            "WHERE",
            "oid = #{oid} AND dataVersion = #{dataVersion}"
    })
    int updateSyncTmsStatus(@Param("prefix") String prefix,
                            @Param("oid") long oid,
                            @Param("dataVersion") long dataVersion,
                            @Param("syncTmsStatus") int syncTmsStatus,
                            @Param("tmsResponse") String tmsResponse);


    /**
     * 根据同步TMS状态获取单号清单
     *
     * @param prefix        表前缀
     * @param syncTmsStatus 同步tms状态
     * @return LIST<出库单号>
     */
    @Select({
            "SELECT outboundOrderNo",
            "FROM ${prefix}_transportation_cancelled_outbound_order",
            "WHERE",
            "syncTmsStatus = #{syncTmsStatus}"

    })
    List<String> selectListBySyncTmsStatus(@Param("prefix") String prefix,
                                           @Param("syncTmsStatus") int syncTmsStatus);
}
