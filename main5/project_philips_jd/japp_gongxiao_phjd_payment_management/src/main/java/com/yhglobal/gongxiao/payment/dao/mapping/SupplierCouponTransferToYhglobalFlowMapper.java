package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.SupplierCouponTransferToYhglobalFlow;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SupplierCouponTransferToYhglobalFlowMapper extends BaseMapper {
    @Delete({
            "delete from supplier_coupon_transfer_to_yhglobal_flow",
            "where flowId = #{flowId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long flowId);

    @Insert({
            "insert into supplier_coupon_transfer_to_yhglobal_flow (flowId, flowNo, ",
            "currencyCode, amountBeforeTransaction, ",
            "transactionAmount, amountAfterTransaction, ",
            "transferTime, supplierId, ",
            "supplierName, projectId, ",
            "projectName, createdById, ",
            "createdByName, purchaseOrderNo, ",
            "flowType, createTime, sourceFlowNo)",
            "values (#{flowId,jdbcType=BIGINT}, #{flowNo,jdbcType=VARCHAR}, ",
            "#{currencyCode,jdbcType=VARCHAR}, #{amountBeforeTransaction,jdbcType=BIGINT}, ",
            "#{transactionAmount,jdbcType=BIGINT}, #{amountAfterTransaction,jdbcType=BIGINT}, ",
            "#{transferTime,jdbcType=TIMESTAMP}, #{supplierId,jdbcType=BIGINT}, ",
            "#{supplierName,jdbcType=VARCHAR}, #{projectId,jdbcType=BIGINT}, ",
            "#{projectName,jdbcType=VARCHAR}, #{createdById,jdbcType=BIGINT}, ",
            "#{createdByName,jdbcType=VARCHAR}, #{purchaseOrderNo,jdbcType=VARCHAR}, ",
            "#{flowType,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, #{sourceFlowNo})"
    })
    int insert(SupplierCouponTransferToYhglobalFlow record);


    @Select({
            "select",
            "flowId, flowNo, currencyCode, amountBeforeTransaction, transactionAmount, amountAfterTransaction, ",
            "transferTime, supplierId, supplierName, projectId, projectName, createdById, ",
            "createdByName, purchaseOrderNo, flowType, createTime",
            "from supplier_coupon_transfer_to_yhglobal_flow",
            "where flowId = #{flowId,jdbcType=BIGINT}"
    })
    SupplierCouponTransferToYhglobalFlow selectByPrimaryKey(Long flowId);

    @Update({
            "update supplier_coupon_transfer_to_yhglobal_flow",
            "set flowNo = #{flowNo,jdbcType=VARCHAR},",
            "currencyCode = #{currencyCode,jdbcType=VARCHAR},",
            "transactionAmount = #{transactionAmount,jdbcType=BIGINT},",
            "transferTime = #{transferTime,jdbcType=TIMESTAMP},",
            "supplierId = #{supplierId,jdbcType=BIGINT},",
            "supplierName = #{supplierName,jdbcType=VARCHAR},",
            "projectId = #{projectId,jdbcType=BIGINT},",
            "projectName = #{projectName,jdbcType=VARCHAR},",
            "createdById = #{createdById,jdbcType=BIGINT},",
            "createdByName = #{createdByName,jdbcType=VARCHAR},",
            "purchaseOrderNo = #{purchaseOrderNo,jdbcType=VARCHAR}",
            "where flowId = #{flowId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(SupplierCouponTransferToYhglobalFlow record);
}