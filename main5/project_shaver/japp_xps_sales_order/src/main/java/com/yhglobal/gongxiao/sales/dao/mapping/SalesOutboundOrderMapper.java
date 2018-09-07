package com.yhglobal.gongxiao.sales.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.sales.model.SalesOutboundOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * @author 葛灿
 */
public interface SalesOutboundOrderMapper extends BaseMapper {

    @Insert({
            "insert into ${prefix}_sales_outbound_order (oid, outboundOrderNo, ",
            "expectedArrivalTime, salesOrderNo, ",
            "totalQuantity, orderStatus, ",
            "outboundFinishedTime, syncToEas, ",
            "syncToTms, tmsOrdNo, ",
            "tmsPostedBy, tmsSignedPhone, ",
            "tmsSignedBy, transporter, ",
            "signedRemark, dataVersion, ",
            "createdById, createdByName, ",
            "createTime, lastUpdateTime, ",
            "items,warehouseId)",
            "values (#{record.oid}, #{record.outboundOrderNo}, ",
            "#{record.expectedArrivalTime}, #{record.salesOrderNo}, ",
            "#{record.totalQuantity}, #{record.orderStatus}, ",
            "#{record.outboundFinishedTime}, #{record.syncToEas}, ",
            "#{record.syncToTms}, #{record.tmsOrdNo}, ",
            "#{record.tmsPostedBy}, #{record.tmsSignedPhone}, ",
            "#{record.tmsSignedBy}, #{record.transporter}, ",
            "#{record.signedRemark}, #{record.dataVersion}, ",
            "#{record.createdById}, #{record.createdByName}, ",
            "NOW(), #{record.lastUpdateTime}, ",
            "#{record.items},#{record.warehouseId})"
    })
    int insert(@Param("prefix") String prefix,
               @Param("record") SalesOutboundOrder order);

    @Select({
            "select",
            "oid, outboundOrderNo, expectedArrivalTime, salesOrderNo, totalQuantity, orderStatus, ",
            "outboundFinishedTime, syncToEas, syncToTms, tmsOrdNo, tmsPostedBy, tmsSignedPhone, ",
            "tmsSignedBy, transporter, signedRemark, dataVersion, createdById, createdByName, ",
            "createTime, lastUpdateTime, items, warehouseId, tmsSignedTime",
            "from ${prefix}_sales_outbound_order",
            "where outboundOrderNo = #{outboundOrderNo}"
    })
    SalesOutboundOrder getOrderByOutboundNo(@Param("prefix") String prefix,
                                            @Param("outboundOrderNo") String outboundNo);


    @Select({
            "select",
            "outboundOrderNo",
            "from ${prefix}_sales_outbound_order",
            "where syncToTms = #{syncToTms}"
    })
    List<String> selectListByTmsStatus(@Param("prefix") String prefix,
                                       @Param("syncToTms") int syncToTms);

    @Update({
            "update ${prefix}_sales_outbound_order",
            "set ",
            "syncToTms = #{syncToTms},",
            "dataVersion = #{dataVersion} + 1",
            "where oid = #{oid} and dataVersion = #{dataVersion}"
    })
    int updateSyncTmsStatus(@Param("prefix") String prefix,
                            @Param("oid") long oid,
                            @Param("dataVersion") long dataVersion,
                            @Param("syncToTms") int syncToTms);


    @Update({
            "update ${prefix}_sales_outbound_order",
            "set ",
            "orderStatus = #{orderStatus},",
            "outboundFinishedTime = #{outboundFinishedTime},",
            "dataVersion = #{dataVersion} + 1",
            "where oid = #{oid} and dataVersion = #{dataVersion}"
    })
    int updateOutboundTime(@Param("prefix") String prefix,
                           @Param("oid") long oid,
                           @Param("dataVersion") long dataVersion,
                           @Param("orderStatus") int orderStatus,
                           @Param("outboundFinishedTime") Date outboundFinishedTime);

    @Update({
            "update ${prefix}_sales_outbound_order",
            "set ",
            "orderStatus = #{orderStatus},",
            "tmsOrdNo = #{tmsOrdNo},",
            "tmsPostedBy = #{tmsPostedBy},",
            "tmsSignedPhone = #{tmsSignedPhone},",
            "tmsSignedBy = #{tmsSignedBy},",
            "transporter = #{transporter},",
            "tmsSignedTime = #{tmsSignedTime},",
            "signedRemark = #{signedRemark},",
            "dataVersion = #{dataVersion} + 1",
            "where oid = #{oid} and dataVersion = #{dataVersion}"
    })
    int updateWhenSigned(@Param("prefix") String prefix,
                         @Param("oid") long oid,
                         @Param("dataVersion") long dataVersion,
                         @Param("orderStatus") int orderStatus,
                         @Param("tmsOrdNo") String tmsOrdNo,
                         @Param("signedRemark") String signedRemark,
                         @Param("tmsSignedBy") String signedBy,
                         @Param("tmsPostedBy") String postedBy,
                         @Param("tmsSignedTime") Date tmsSignedTime,
                         @Param("tmsSignedPhone") String tmsSignedPhone,
                         @Param("transporter") String transporter);
}
