package com.yhglobal.gongxiao.payment.AccountManageDao.mapping;


import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.SupplierUnitPriceDiscountFrozenAccountTransferRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

public interface SupplierUnitPriceDiscountFrozenAccountTransferRecordMapper extends BaseMapper{
    @Delete({
        "delete from ${tablePrefix}_supplier_unit_price_discount_frozen_account_transfer_record",
        "where recordId = #{recordId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long recordId, String tablePrefix);

    @Insert({
        "insert into ${tablePrefix}_supplier_unit_price_discount_frozen_account_transfer_record (recordId, currencyCode, ",
        "amountBeforeTransaction, transactionAmount, ",
        "amountAfterTransaction, transferTime, ",
        "supplierId, supplierName, ",
        "projectId, projectName, ",
        "createdById, createdByName, ",
        "purchaseOrderNo, flowNo, ",
        "recordType, createTime, remark)",
        "values (#{recordId,jdbcType=BIGINT}, #{currencyCode,jdbcType=VARCHAR}, ",
        "#{amountBeforeTransaction,jdbcType=DECIMAL}, #{transactionAmount,jdbcType=DECIMAL}, ",
        "#{amountAfterTransaction,jdbcType=DECIMAL}, #{transferTime,jdbcType=TIMESTAMP}, ",
        "#{supplierId,jdbcType=BIGINT}, #{supplierName,jdbcType=VARCHAR}, ",
        "#{projectId,jdbcType=BIGINT}, #{projectName,jdbcType=VARCHAR}, ",
        "#{createdById,jdbcType=BIGINT}, #{createdByName,jdbcType=VARCHAR}, ",
        "#{purchaseOrderNo,jdbcType=VARCHAR}, #{flowNo,jdbcType=VARCHAR}, ",
        "#{recordType,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP},#{remark,jdbcType=LONGVARCHAR})"
    })
    int insert(SupplierUnitPriceDiscountFrozenAccountTransferRecord record);



    @Select({
        "select",
        "recordId, currencyCode, amountBeforeTransaction, transactionAmount, amountAfterTransaction, ",
        "transferTime, supplierId, supplierName, projectId, projectName, createdById, ",
        "createdByName, purchaseOrderNo, flowNo, recordType, createTime, remark",
        "from ${tablePrefix}_supplier_unit_price_discount_frozen_account_transfer_record",
        "where recordId = #{recordId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="recordId", property="recordId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="currencyCode", property="currencyCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="amountBeforeTransaction", property="amountBeforeTransaction", jdbcType=JdbcType.DECIMAL),
        @Result(column="transactionAmount", property="transactionAmount", jdbcType=JdbcType.DECIMAL),
        @Result(column="amountAfterTransaction", property="amountAfterTransaction", jdbcType=JdbcType.DECIMAL),
        @Result(column="transferTime", property="transferTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="supplierId", property="supplierId", jdbcType=JdbcType.BIGINT),
        @Result(column="supplierName", property="supplierName", jdbcType=JdbcType.VARCHAR),
        @Result(column="projectId", property="projectId", jdbcType=JdbcType.BIGINT),
        @Result(column="projectName", property="projectName", jdbcType=JdbcType.VARCHAR),
        @Result(column="createdById", property="createdById", jdbcType=JdbcType.BIGINT),
        @Result(column="createdByName", property="createdByName", jdbcType=JdbcType.VARCHAR),
        @Result(column="purchaseOrderNo", property="purchaseOrderNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="flowNo", property="flowNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="recordType", property="recordType", jdbcType=JdbcType.INTEGER),
        @Result(column="createTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="remark", property="remark", jdbcType=JdbcType.LONGNVARCHAR)
    })
    SupplierUnitPriceDiscountFrozenAccountTransferRecord selectByPrimaryKey(Long recordId, String tablePrefix);

//    @SelectProvider(type = SupplierUnitPriceDiscountFrozenAccountTransferRecordSqlProvider.class, method = "selectByConditions")
//    List<SupplierUnitPriceDiscountFrozenAccountTransferRecord> selectByConditions(@Param("moneyFlow") Integer moneyFlow,
//                                                                                 @Param("dateS") Date dateS,
//                                                                                 @Param("dateE") Date dateE, @Param("tablePrefix") String tablePrefix);
//    @SelectProvider(type = SupplierUnitPriceDiscountFrozenAccountTransferRecordSqlProvider.class, method = "selectTotal")
//    List<FlowSubtotal> selectTotal(@Param("moneyFlow") Integer moneyFlow,
//                                   @Param("dateS") Date dateS,
//                                   @Param("dateE") Date dateE, @Param("tablePrefix") String tablePrefix);



    @Update({
        "update supplier_unit_price_discount_frozen_account_transfer_record",
        "set currencyCode = #{currencyCode,jdbcType=VARCHAR},",
          "amountBeforeTransaction = #{amountBeforeTransaction,jdbcType=DECIMAL},",
          "transactionAmount = #{transactionAmount,jdbcType=DECIMAL},",
          "amountAfterTransaction = #{amountAfterTransaction,jdbcType=DECIMAL},",
          "transferTime = #{transferTime,jdbcType=TIMESTAMP},",
          "supplierId = #{supplierId,jdbcType=BIGINT},",
          "supplierName = #{supplierName,jdbcType=VARCHAR},",
          "projectId = #{projectId,jdbcType=BIGINT},",
          "projectName = #{projectName,jdbcType=VARCHAR},",
          "createdById = #{createdById,jdbcType=BIGINT},",
          "createdByName = #{createdByName,jdbcType=VARCHAR},",
          "purchaseOrderNo = #{purchaseOrderNo,jdbcType=VARCHAR},",
          "flowNo = #{flowNo,jdbcType=VARCHAR},",
          "recordType = #{recordType,jdbcType=INTEGER},",
          "createTime = #{createTime,jdbcType=TIMESTAMP}",
            "remark = #{remark,jdbcType=LONGVARCHAR}",
        "where recordId = #{recordId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(SupplierUnitPriceDiscountFrozenAccountTransferRecord record);
}