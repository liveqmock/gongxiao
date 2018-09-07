package com.yhglobal.gongxiao.payment.project.dao.supplier.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.project.model.SupplierCouponFlow;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface SupplierCouponFlowMapper extends BaseMapper {


    @Insert({
            "insert into ${prefix}_supplier_coupon_flow (recordId, transferAccountType, ",
            "currencyCode, amountBeforeTransaction, ",
            "transactionAmount, amountAfterTransaction, ",
            "transactionTime, supplierId, ",
            "supplierName, projectId, ",
            "projectName, createdById, ",
            "createdByName, businessOrderNo, ",
            "flowNo, flowType, ",
            "createTime, remark)",
            "values (#{record.recordId,jdbcType=BIGINT}, #{record.transferAccountType,jdbcType=VARCHAR}, ",
            "#{record.currencyCode,jdbcType=VARCHAR}, #{record.amountBeforeTransaction,jdbcType=BIGINT}, ",
            "#{record.transactionAmount,jdbcType=BIGINT}, #{record.amountAfterTransaction,jdbcType=BIGINT}, ",
            "#{record.transactionTime,jdbcType=TIMESTAMP}, #{record.supplierId,jdbcType=BIGINT}, ",
            "#{record.supplierName,jdbcType=VARCHAR}, #{record.projectId,jdbcType=BIGINT}, ",
            "#{record.projectName,jdbcType=VARCHAR}, #{record.createdById,jdbcType=BIGINT}, ",
            "#{record.createdByName,jdbcType=VARCHAR}, #{record.businessOrderNo,jdbcType=VARCHAR}, ",
            "#{record.flowNo,jdbcType=VARCHAR}, #{record.flowType,jdbcType=INTEGER}, ",
            "#{record.createTime,jdbcType=TIMESTAMP}, #{record.remark})"
    })
    int insert(@Param("prefix") String prefix,
               @Param("record") SupplierCouponFlow record);

    @Update({
            "update ${prefix}_supplier_coupon_flow",
            "set transferAccountType = #{record.transferAccountType,jdbcType=VARCHAR},",
            "currencyCode = #{record.currencyCode,jdbcType=VARCHAR},",
            "amountBeforeTransaction = #{record.amountBeforeTransaction,jdbcType=BIGINT},",
            "transactionAmount = #{record.transactionAmount,jdbcType=BIGINT},",
            "amountAfterTransaction = #{record.amountAfterTransaction,jdbcType=BIGINT},",
            "transactionTime = #{record.transactionTime,jdbcType=TIMESTAMP},",
            "supplierId = #{record.supplierId,jdbcType=BIGINT},",
            "supplierName = #{record.supplierName,jdbcType=VARCHAR},",
            "projectId = #{record.projectId,jdbcType=BIGINT},",
            "projectName = #{record.projectName,jdbcType=VARCHAR},",
            "createdById = #{record.createdById,jdbcType=BIGINT},",
            "createdByName = #{record.createdByName,jdbcType=VARCHAR},",
            "businessOrderNo = #{record.businessOrderNo,jdbcType=VARCHAR}",
            "where recordId = #{record.recordId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(@Param("prefix") String prefix,
                           @Param("record") SupplierCouponFlow record);

    @SelectProvider(type = SupplierCouponTransferRecordSqlProvider.class, method = "selectAllBySupplierId")
    List<SupplierCouponFlow> selectAllBySupplierId(@Param("prefix") String prefix,
                                                   @Param("currencyCode") String currencyCode,
                                                   @Param("supplierId") long supplierId,
                                                   @Param("projectId") long projectId,
                                                   @Param("moneyFlow") Integer moneyFlow,
                                                   @Param("beginDate") Date beginDate,
                                                   @Param("endDate") Date endDate);

    @SelectProvider(type = SupplierCouponTransferRecordSqlProvider.class, method = "selectIncomeCount")
    int selectIncomeCount(@Param("prefix") String prefix,
                          @Param("currencyCode") String currencyCode,
                          @Param("supplierId") long supplierId,
                          @Param("projectId") long projectId,
                          @Param("moneyFlow") Integer moneyFlow,
                          @Param("beginDate") Date beginDate,
                          @Param("endDate") Date endDate);

    @SelectProvider(type = SupplierCouponTransferRecordSqlProvider.class, method = "selectIncomeAmount")
    BigDecimal selectIncomeAmount(@Param("prefix") String prefix,
                                  @Param("currencyCode") String currencyCode,
                                  @Param("supplierId") long supplierId,
                                  @Param("projectId") long projectId,
                                  @Param("moneyFlow") Integer moneyFlow,
                                  @Param("beginDate") Date beginDate,
                                  @Param("endDate") Date endDate);

    @SelectProvider(type = SupplierCouponTransferRecordSqlProvider.class, method = "selectExpenditureCount")
    int selectExpenditureCount(@Param("prefix") String prefix,
                               @Param("currencyCode") String currencyCode,
                               @Param("supplierId") long supplierId,
                               @Param("projectId") long projectId,
                               @Param("moneyFlow") Integer moneyFlow,
                               @Param("beginDate") Date beginDate,
                               @Param("endDate") Date endDate);

    @SelectProvider(type = SupplierCouponTransferRecordSqlProvider.class, method = "selectExpenditureAmount")
    BigDecimal selectExpenditureAmount(@Param("prefix") String prefix,
                                 @Param("currencyCode") String currencyCode,
                                 @Param("supplierId") long supplierId,
                                 @Param("projectId") long projectId,
                                 @Param("moneyFlow") Integer moneyFlow,
                                 @Param("beginDate") Date beginDate,
                                 @Param("endDate") Date endDate);

}