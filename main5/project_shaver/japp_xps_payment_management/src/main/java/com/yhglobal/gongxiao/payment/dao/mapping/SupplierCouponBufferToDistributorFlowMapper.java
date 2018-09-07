package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.SupplierCouponBufferToDistributorFlow;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SupplierCouponBufferToDistributorFlowMapper extends BaseMapper {

    @Insert({
            "insert into ${prefix}_supplier_coupon_buffer_to_distributor_flow (flowId, flowNo, ",
            "currencyCode, amountBeforeTransaction, ",
            "transactionAmount, amountAfterTransaction, ",
            "transactionTime, supplierId, ",
            "supplierName, projectId, ",
            "projectName, createdById, ",
            "createdByName, businessOrderNo, ",
            "flowType, createTime, sourceFlowNo, remark)",
            "values (#{record.flowId}, #{record.flowNo}, ",
            "#{record.currencyCode}, #{record.amountBeforeTransaction}, ",
            "#{record.transactionAmount}, #{record.amountAfterTransaction}, ",
            "#{record.transactionTime}, #{record.supplierId}, ",
            "#{record.supplierName}, #{record.projectId}, ",
            "#{record.projectName}, #{record.createdById}, ",
            "#{record.createdByName}, #{record.businessOrderNo}, ",
            "#{record.flowType}, #{record.createTime}, #{record.sourceFlowNo}, #{record.remark})"
    })
    int insert(@Param("prefix") String prefix,
              @Param("record") SupplierCouponBufferToDistributorFlow record);


    @Update({
            "update ${prefix}_supplier_coupon_buffer_to_distributor_flow",
            "set flowNo = #{record.flowNo},",
            "currencyCode = #{record.currencyCode},",
            "transactionAmount = #{record.transactionAmount},",
            "transactionTime = #{record.transactionTime},",
            "supplierId = #{record.supplierId},",
            "supplierName = #{record.supplierName},",
            "projectId = #{record.projectId},",
            "projectName = #{record.projectName},",
            "createdById = #{record.createdById},",
            "createdByName = #{record.createdByName},",
            "businessOrderNo = #{record.businessOrderNo}",
            "where flowId = #{record.flowId}"
    })
    int updateByPrimaryKey(@Param("prefix") String prefix,
                           @Param("record") SupplierCouponBufferToDistributorFlow record);

    @SelectProvider(type = SupplierCouponBufferToDistributorFlowSqlProvider.class, method = "selectBufferCouponFlowList")
    List<SupplierCouponBufferToDistributorFlow> selectBufferCouponFlowList(@Param("prefix") String prefix,
                                                                           @Param("currencyCode") String currencyCode,
                                                                           @Param("projectId") long projectId,
                                                                           @Param("moneyFlow") Integer moneyFlow,
                                                                           @Param("beginDate") String beginDate,
                                                                           @Param("endDate") String endDate);


    @SelectProvider(type = SupplierCouponBufferToDistributorFlowSqlProvider.class, method = "selectIncomeAndExpenditure")
    List<FlowSubtotal> selectIncomeAndExpenditure(@Param("prefix") String prefix,
                                                  @Param("currencyCode") String currencyCode,
                                                  @Param("projectId") long projectId,
                                                  @Param("moneyFlow") Integer moneyFlow,
                                                  @Param("beginDate") String beginDate,
                                                  @Param("endDate") String endDate);

}