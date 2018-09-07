package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.SupplierPrepaidBufferToYhglobalFlow;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface SupplierPrepaidBufferToYhglobalFlowMapper extends BaseMapper {

    @Insert({
            "insert into ${prefix}_supplier_prepaid_buffer_to_yhglobal_flow (flowId, flowNo, ",
            "currencyCode, amountBeforeTransaction, ",
            "transactionAmount, amountAfterTransaction, ",
            "transactionTime, supplierId, ",
            "supplierName, projectId, ",
            "projectName, createdById, ",
            "createdByName, businessOrderNo, ",
            "flowType, createTime, sourceFlowNo, remark)",
            "values (#{record.flowId,jdbcType=BIGINT}, #{record.flowNo,jdbcType=VARCHAR}, ",
            "#{record.currencyCode,jdbcType=VARCHAR}, #{record.amountBeforeTransaction,jdbcType=BIGINT}, ",
            "#{record.transactionAmount,jdbcType=BIGINT}, #{record.amountAfterTransaction,jdbcType=BIGINT}, ",
            "#{record.transactionTime,jdbcType=TIMESTAMP}, #{record.supplierId,jdbcType=BIGINT}, ",
            "#{record.supplierName,jdbcType=VARCHAR}, #{record.projectId,jdbcType=BIGINT}, ",
            "#{record.projectName,jdbcType=VARCHAR}, #{record.createdById,jdbcType=BIGINT}, ",
            "#{record.createdByName,jdbcType=VARCHAR}, #{record.businessOrderNo,jdbcType=VARCHAR}, ",
            "#{record.flowType,jdbcType=INTEGER}, #{record.createTime,jdbcType=TIMESTAMP}, #{record.sourceFlowNo}, #{record.remark})"
    })
    int insert(@Param("prefix") String prefix,
               @Param("record") SupplierPrepaidBufferToYhglobalFlow record);


    @Update({
            "update ${prefix}_supplier_prepaid_buffer_to_yhglobal_flow",
            "set flowNo = #{record.flowNo,jdbcType=VARCHAR},",
            "currencyCode = #{record.currencyCode,jdbcType=VARCHAR},",
            "transactionAmount = #{record.transactionAmount,jdbcType=BIGINT},",
            "transactionTime = #{record.transactionTime,jdbcType=TIMESTAMP},",
            "supplierId = #{record.supplierId,jdbcType=BIGINT},",
            "supplierName = #{record.supplierName,jdbcType=VARCHAR},",
            "projectId = #{record.projectId,jdbcType=BIGINT},",
            "projectName = #{record.projectName,jdbcType=VARCHAR},",
            "createdById = #{record.createdById,jdbcType=BIGINT},",
            "createdByName = #{record.createdByName,jdbcType=VARCHAR},",
            "businessOrderNo = #{record.businessOrderNo,jdbcType=VARCHAR}",
            "where flowId = #{record.flowId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(@Param("prefix") String prefix,
                           @Param("record") SupplierPrepaidBufferToYhglobalFlow record);
}