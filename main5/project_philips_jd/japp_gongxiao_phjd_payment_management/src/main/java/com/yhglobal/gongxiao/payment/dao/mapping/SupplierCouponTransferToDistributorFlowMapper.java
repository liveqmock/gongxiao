package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.SupplierCouponTransferToDistributorFlow;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SupplierCouponTransferToDistributorFlowMapper extends BaseMapper {
    @Delete({
            "delete from supplier_coupon_transfer_to_distributor_flow",
            "where flowId = #{flowId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long flowId);

    @Insert({
            "insert into supplier_coupon_transfer_to_distributor_flow (flowId, flowNo, ",
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
    int insert(SupplierCouponTransferToDistributorFlow record);

    @Select({
            "select",
            "flowId, flowNo, currencyCode, amountBeforeTransaction, transactionAmount, amountAfterTransaction, ",
            "transferTime, supplierId, supplierName, projectId, projectName, createdById, ",
            "createdByName, purchaseOrderNo, flowType, createTime",
            "from supplier_coupon_transfer_to_distributor_flow",
            "where flowId = #{flowId,jdbcType=BIGINT}"
    })
    SupplierCouponTransferToDistributorFlow selectByPrimaryKey(Long flowId);

    @Update({
            "update supplier_coupon_transfer_to_distributor_flow",
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
    int updateByPrimaryKey(SupplierCouponTransferToDistributorFlow record);

    @SelectProvider(type = SupplierCouponTransferToDistributorFlowSqlProvider.class, method = "selectBufferCouponFlowList")
    List<SupplierCouponTransferToDistributorFlow> selectBufferCouponFlowList(@Param("currencyCode") String currencyCode,
                                                                             @Param("projectId") long projectId,
                                                                             @Param("moneyFlow") Integer moneyFlow,
                                                                             @Param("beginDate") String beginDate,
                                                                             @Param("endDate") String endDate);


    @SelectProvider(type = SupplierCouponTransferToDistributorFlowSqlProvider.class, method = "selectIncomeAndExpenditure")
    List<FlowSubtotal> selectIncomeAndExpenditure(@Param("currencyCode") String currencyCode,
                                                  @Param("projectId") long projectId,
                                                  @Param("moneyFlow") Integer moneyFlow,
                                                  @Param("beginDate") String beginDate,
                                                  @Param("endDate") String endDate);

}