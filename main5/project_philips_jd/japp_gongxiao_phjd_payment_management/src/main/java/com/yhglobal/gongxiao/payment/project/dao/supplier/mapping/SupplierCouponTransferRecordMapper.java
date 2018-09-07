package com.yhglobal.gongxiao.payment.project.dao.supplier.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.project.model.SupplierCouponTransferRecord;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface SupplierCouponTransferRecordMapper  extends BaseMapper {
    @Delete({
        "delete from supplier_coupon_transfer_record",
        "where recordId = #{recordId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long recordId);

    @Insert({
            "insert into supplier_coupon_transfer_record (recordId, transferAccountType, ",
            "currencyCode, amountBeforeTransaction, ",
            "transactionAmount, amountAfterTransaction, ",
            "transferTime, supplierId, ",
            "supplierName, projectId, ",
            "projectName, createdById, ",
            "createdByName, purchaseOrderNo, ",
            "flowNo, recordType, ",
            "createTime)",
            "values (#{recordId,jdbcType=BIGINT}, #{transferAccountType,jdbcType=VARCHAR}, ",
            "#{currencyCode,jdbcType=VARCHAR}, #{amountBeforeTransaction,jdbcType=BIGINT}, ",
            "#{transactionAmount,jdbcType=BIGINT}, #{amountAfterTransaction,jdbcType=BIGINT}, ",
            "#{transferTime,jdbcType=TIMESTAMP}, #{supplierId,jdbcType=BIGINT}, ",
            "#{supplierName,jdbcType=VARCHAR}, #{projectId,jdbcType=BIGINT}, ",
            "#{projectName,jdbcType=VARCHAR}, #{createdById,jdbcType=BIGINT}, ",
            "#{createdByName,jdbcType=VARCHAR}, #{purchaseOrderNo,jdbcType=VARCHAR}, ",
            "#{flowNo,jdbcType=VARCHAR}, #{recordType,jdbcType=INTEGER}, ",
            "#{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(SupplierCouponTransferRecord record);

    @Select({
            "select",
            "recordId, transferAccountType, currencyCode, amountBeforeTransaction, transactionAmount, ",
            "amountAfterTransaction, transferTime, supplierId, supplierName, projectId, projectName, ",
            "createdById, createdByName, purchaseOrderNo, flowNo, recordType, createTime",
            "from supplier_coupon_transfer_record",
            "where recordId = #{recordId,jdbcType=BIGINT}"
    })
    SupplierCouponTransferRecord selectByPrimaryKey(Long recordId);



    @Update({
        "update supplier_coupon_transfer_record",
        "set transferAccountType = #{transferAccountType,jdbcType=VARCHAR},",
          "currencyCode = #{currencyCode,jdbcType=VARCHAR},",
          "amountBeforeTransaction = #{amountBeforeTransaction,jdbcType=BIGINT},",
          "transactionAmount = #{transactionAmount,jdbcType=BIGINT},",
          "amountAfterTransaction = #{amountAfterTransaction,jdbcType=BIGINT},",
          "transferTime = #{transferTime,jdbcType=TIMESTAMP},",
          "supplierId = #{supplierId,jdbcType=BIGINT},",
          "supplierName = #{supplierName,jdbcType=VARCHAR},",
          "projectId = #{projectId,jdbcType=BIGINT},",
          "projectName = #{projectName,jdbcType=VARCHAR},",
          "createdById = #{createdById,jdbcType=BIGINT},",
          "createdByName = #{createdByName,jdbcType=VARCHAR},",
          "purchaseOrderNo = #{purchaseOrderNo,jdbcType=VARCHAR}",
        "where recordId = #{recordId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(SupplierCouponTransferRecord record);

    @SelectProvider(type = SupplierCouponTransferRecordSqlProvider.class, method = "selectAllBySupplierId")
    List<SupplierCouponTransferRecord> selectAllBySupplierId(@Param("currencyCode") String currencyCode,
                                                             @Param("supplierId") long supplierId,
                                                             @Param("projectId") long projectId,
                                                             @Param("moneyFlow") Integer moneyFlow,
                                                             @Param("beginDate") Date beginDate,
                                                             @Param("endDate") Date endDate);

    @SelectProvider(type = SupplierCouponTransferRecordSqlProvider.class, method = "selectIncomeCount")
    int selectIncomeCount(@Param("currencyCode") String currencyCode,
                          @Param("supplierId") long supplierId,
                          @Param("projectId") long projectId,
                          @Param("moneyFlow") Integer moneyFlow,
                          @Param("beginDate") Date beginDate,
                          @Param("endDate") Date endDate);

    @SelectProvider(type = SupplierCouponTransferRecordSqlProvider.class, method = "selectIncomeAmount")
    Long selectIncomeAmount(@Param("currencyCode") String currencyCode,
                            @Param("supplierId") long supplierId,
                            @Param("projectId") long projectId,
                            @Param("moneyFlow") Integer moneyFlow,
                            @Param("beginDate") Date beginDate,
                            @Param("endDate") Date endDate);

    @SelectProvider(type = SupplierCouponTransferRecordSqlProvider.class, method = "selectExpenditureCount")
    int selectExpenditureCount(@Param("currencyCode") String currencyCode,
                               @Param("supplierId") long supplierId,
                               @Param("projectId") long projectId,
                               @Param("moneyFlow") Integer moneyFlow,
                               @Param("beginDate") Date beginDate,
                               @Param("endDate") Date endDate);

    @SelectProvider(type = SupplierCouponTransferRecordSqlProvider.class, method = "selectExpenditureAmount")
    Long selectExpenditureAmount(@Param("currencyCode") String currencyCode,
                                 @Param("supplierId") long supplierId,
                                 @Param("projectId") long projectId,
                                 @Param("moneyFlow") Integer moneyFlow,
                                 @Param("beginDate") Date beginDate,
                                 @Param("endDate") Date endDate);

}