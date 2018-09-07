package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.YhglobalReceivedCouponAccount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface YhglobalReceivedCouponAccountMapper extends BaseMapper {

    @Insert({
            "insert into ${prefix}_yhglobal_received_coupon_account (rowId, projectId, ",
            "projectName, supplierId, ",
            "supplierName, status, ",
            "totalAmount, dataVersion, ",
            "createTime, lastUpdateTime, ",
            "tracelog)",
            "values (#{record.rowId,jdbcType=BIGINT}, #{record.projectId,jdbcType=BIGINT}, ",
            "#{record.projectName,jdbcType=VARCHAR}, #{record.supplierId,jdbcType=BIGINT}, ",
            "#{record.supplierName,jdbcType=VARCHAR}, #{record.status,jdbcType=TINYINT}, ",
            "#{record.totalAmount,jdbcType=BIGINT}, #{record.dataVersion,jdbcType=BIGINT}, ",
            "#{record.createTime,jdbcType=TIMESTAMP}, #{record.lastUpdateTime,jdbcType=TIMESTAMP}, ",
            "#{record.tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(@Param("prefix") String prefix,
               @Param("record") YhglobalReceivedCouponAccount record);

    @Select({
            "select",
            "rowId, projectId, projectName, supplierId, supplierName, status, totalAmount, ",
            "dataVersion, createTime, lastUpdateTime, tracelog",
            "from ${prefix}_yhglobal_received_coupon_account",
            "where projectId = #{projectId}"
    })
    YhglobalReceivedCouponAccount getAccount(@Param("prefix") String prefix,
                                             @Param("projectId") long projectId);

    @Update({
            "update ${prefix}_yhglobal_received_coupon_account",
            "set projectId = #{record.projectId,jdbcType=BIGINT},",
            "projectName = #{record.projectName,jdbcType=VARCHAR},",
            "supplierId = #{record.supplierId,jdbcType=BIGINT},",
            "supplierName = #{record.supplierName,jdbcType=VARCHAR},",
            "status = #{record.status,jdbcType=TINYINT},",
            "totalAmount = #{record.totalAmount,jdbcType=BIGINT},",
            "dataVersion = dataVersion+1,",
            "createTime = #{record.createTime,jdbcType=TIMESTAMP},",
            "tracelog = #{record.tracelog,jdbcType=LONGVARCHAR}",
            "where rowId = #{record.rowId,jdbcType=BIGINT} AND dataVersion=#{record.dataVersion}"
    })
    int updateByPrimaryKeyWithBLOBs(@Param("prefix") String prefix,
                                    @Param("record") YhglobalReceivedCouponAccount record);
}