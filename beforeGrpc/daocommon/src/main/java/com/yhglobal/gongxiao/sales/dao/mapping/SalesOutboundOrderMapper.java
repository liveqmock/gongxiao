package com.yhglobal.gongxiao.sales.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.sales.model.SalesOutboundOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author 葛灿
 */
public interface SalesOutboundOrderMapper extends BaseMapper {

    @Insert({
            "insert into sales_outbound_order (oid, outboundOrderNo, ",
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
            "values (#{oid,jdbcType=INTEGER}, #{outboundOrderNo,jdbcType=VARCHAR}, ",
            "#{expectedArrivalTime,jdbcType=TIMESTAMP}, #{salesOrderNo,jdbcType=VARCHAR}, ",
            "#{totalQuantity,jdbcType=INTEGER}, #{orderStatus,jdbcType=TINYINT}, ",
            "#{outboundFinishedTime,jdbcType=TIMESTAMP}, #{syncToEas,jdbcType=TINYINT}, ",
            "#{syncToTms,jdbcType=TINYINT}, #{tmsOrdNo,jdbcType=VARCHAR}, ",
            "#{tmsPostedBy,jdbcType=VARCHAR}, #{tmsSignedPhone,jdbcType=VARCHAR}, ",
            "#{tmsSignedBy,jdbcType=VARCHAR}, #{transporter,jdbcType=TIMESTAMP}, ",
            "#{signedRemark,jdbcType=VARCHAR}, #{dataVersion,jdbcType=BIGINT}, ",
            "#{createdById,jdbcType=BIGINT}, #{createdByName,jdbcType=VARCHAR}, ",
            "NOW(), #{lastUpdateTime,jdbcType=TIMESTAMP}, ",
            "#{items,jdbcType=LONGVARCHAR},#{warehouseId})"
    })
    int insert(SalesOutboundOrder order);

    @Select({
            "select",
            "oid, outboundOrderNo, expectedArrivalTime, salesOrderNo, totalQuantity, orderStatus, ",
            "outboundFinishedTime, syncToEas, syncToTms, tmsOrdNo, tmsPostedBy, tmsSignedPhone, ",
            "tmsSignedBy, transporter, signedRemark, dataVersion, createdById, createdByName, ",
            "createTime, lastUpdateTime, items, warehouseId",
            "from sales_outbound_order",
            "where outboundOrderNo = #{outboundOrderNo}"
    })
    SalesOutboundOrder getOrderByOutboundNo(@Param("outboundOrderNo") String outboundNo);


    @Update({
            "update sales_outbound_order",
            "set outboundOrderNo = #{outboundOrderNo,jdbcType=VARCHAR},",
            "expectedArrivalTime = #{expectedArrivalTime,jdbcType=TIMESTAMP},",
            "salesOrderNo = #{salesOrderNo,jdbcType=VARCHAR},",
            "totalQuantity = #{totalQuantity,jdbcType=INTEGER},",
            "orderStatus = #{orderStatus,jdbcType=TINYINT},",
            "outboundFinishedTime = #{outboundFinishedTime,jdbcType=TIMESTAMP},",
            "syncToEas = #{syncToEas,jdbcType=TINYINT},",
            "syncToTms = #{syncToTms,jdbcType=TINYINT},",
            "tmsOrdNo = #{tmsOrdNo,jdbcType=VARCHAR},",
            "tmsPostedBy = #{tmsPostedBy,jdbcType=VARCHAR},",
            "tmsSignedPhone = #{tmsSignedPhone,jdbcType=VARCHAR},",
            "tmsSignedBy = #{tmsSignedBy,jdbcType=VARCHAR},",
            "transporter = #{transporter,jdbcType=TIMESTAMP},",
            "signedRemark = #{signedRemark,jdbcType=VARCHAR},",
            "dataVersion = dataVersion+1,",
            "items = #{items}",
            "where oid = #{oid,jdbcType=INTEGER} and dataVersion = #{dataVersion}"
    })
    int update(SalesOutboundOrder order);

    @Select({
            "select",
            "outboundOrderNo",
            "from sales_outbound_order",
            "where syncToEas = #{syncToEas}"
    })
    List<String> selectListByEasStatus(int syncToEas);

    @Select({
            "select",
            "outboundOrderNo",
            "from sales_outbound_order",
            "where syncToTms = #{syncToTms}"
    })
    List<String> selectListByTmsStatus(@Param("syncToTms") int syncToTms);
}
