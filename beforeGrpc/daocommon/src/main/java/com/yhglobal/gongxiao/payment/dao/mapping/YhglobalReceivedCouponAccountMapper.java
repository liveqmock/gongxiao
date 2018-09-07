package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.YhglobalReceivedCouponAccount;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface YhglobalReceivedCouponAccountMapper extends BaseMapper {

    @Insert({
            "insert into yhglobal_received_coupon_account (rowId, projectId, ",
            "projectName, supplierId, ",
            "supplierName, status, ",
            "totalAmount, dataVersion, ",
            "createTime, lastUpdateTime, ",
            "tracelog)",
            "values (#{rowId,jdbcType=BIGINT}, #{projectId,jdbcType=BIGINT}, ",
            "#{projectName,jdbcType=VARCHAR}, #{supplierId,jdbcType=BIGINT}, ",
            "#{supplierName,jdbcType=VARCHAR}, #{status,jdbcType=TINYINT}, ",
            "#{totalAmount,jdbcType=BIGINT}, #{dataVersion,jdbcType=BIGINT}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP}, ",
            "#{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(YhglobalReceivedCouponAccount record);

    @Select({
            "select",
            "rowId, projectId, projectName, supplierId, supplierName, status, totalAmount, ",
            "dataVersion, createTime, lastUpdateTime, tracelog",
            "from yhglobal_received_coupon_account",
            "where projectId = #{projectId}"
    })
    YhglobalReceivedCouponAccount getAccount(@Param("projectId") long projectId);

    @Update({
            "update yhglobal_received_coupon_account",
            "set projectId = #{projectId,jdbcType=BIGINT},",
            "projectName = #{projectName,jdbcType=VARCHAR},",
            "supplierId = #{supplierId,jdbcType=BIGINT},",
            "supplierName = #{supplierName,jdbcType=VARCHAR},",
            "status = #{status,jdbcType=TINYINT},",
            "totalAmount = #{totalAmount,jdbcType=BIGINT},",
            "dataVersion = dataVersion+1,",
            "createTime = #{createTime,jdbcType=TIMESTAMP},",
            "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
            "where rowId = #{rowId,jdbcType=BIGINT} AND dataVersion=#{dataVersion}"
    })
    int updateByPrimaryKeyWithBLOBs(YhglobalReceivedCouponAccount record);
}