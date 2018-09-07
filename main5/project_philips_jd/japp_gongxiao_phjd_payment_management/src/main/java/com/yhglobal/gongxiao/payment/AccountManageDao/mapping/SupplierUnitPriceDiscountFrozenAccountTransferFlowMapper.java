package com.yhglobal.gongxiao.payment.AccountManageDao.mapping;


import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.SupplierUnitPriceDiscountFrozenAccountTransferFlow;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

public interface SupplierUnitPriceDiscountFrozenAccountTransferFlowMapper extends BaseMapper{
    @Delete({
        "delete from supplier_unit_price_discount_frozen_account_transfer_flow",
        "where flowId = #{flowId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long flowId);

    @SelectProvider(type = SupplierUnitPriceDiscountFrozenAccountTransferFLowSqlProvider.class, method = "selectByConditions")
    List<SupplierUnitPriceDiscountFrozenAccountTransferFlow> selectByConditions(@Param("moneyFlow") Integer moneyFlow,
                                                                                @Param("dateS") Date dateS,
                                                                                @Param("dateE") Date dateE, @Param("tablePrefix") String tablePrefix);
    @SelectProvider(type = SupplierUnitPriceDiscountFrozenAccountTransferFLowSqlProvider.class, method = "selectTotal")
    List<FlowSubtotal> selectTotal(@Param("moneyFlow") Integer moneyFlow,
                                   @Param("dateS") Date dateS,
                                   @Param("dateE") Date dateE, @Param("tablePrefix") String tablePrefix);
    @Insert({
        "insert into ${tablePrefix}_supplier_unit_price_discount_frozen_account_transfer_flow (flowId, flowNo, ",
        "currencyCode, amountBeforeTransaction, ",
        "transactionAmount, amountAfterTransaction, ",
        "transferTime, supplierId, ",
        "supplierName, projectId, ",
        "projectName, createdById, ",
        "createdByName, purchaseOrderNo, ",
        "flowType, createTime, ",
        "sourceFlowNo, remark)",
        "values (#{flowId,jdbcType=BIGINT}, #{flowNo,jdbcType=VARCHAR}, ",
        "#{currencyCode,jdbcType=VARCHAR}, #{amountBeforeTransaction,jdbcType=DECIMAL}, ",
        "#{transactionAmount,jdbcType=DECIMAL}, #{amountAfterTransaction,jdbcType=DECIMAL}, ",
        "#{transferTime,jdbcType=TIMESTAMP}, #{supplierId,jdbcType=BIGINT}, ",
        "#{supplierName,jdbcType=VARCHAR}, #{projectId,jdbcType=BIGINT}, ",
        "#{projectName,jdbcType=VARCHAR}, #{createdById,jdbcType=BIGINT}, ",
        "#{createdByName,jdbcType=VARCHAR}, #{purchaseOrderNo,jdbcType=VARCHAR}, ",
        "#{flowType,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{sourceFlowNo,jdbcType=VARCHAR}, #{remark,jdbcType=LONGVARCHAR})"
    })
    int insert(SupplierUnitPriceDiscountFrozenAccountTransferFlow record);


    @Select({
        "select",
        "flowId, flowNo, currencyCode, amountBeforeTransaction, transactionAmount, amountAfterTransaction, ",
        "transferTime, supplierId, supplierName, projectId, projectName, createdById, ",
        "createdByName, purchaseOrderNo, flowType, createTime, sourceFlowNo",
        "from supplier_unit_price_discount_frozen_account_transfer_flow",
        "where flowId = #{flowId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="flowId", property="flowId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="flowNo", property="flowNo", jdbcType=JdbcType.VARCHAR),
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
        @Result(column="flowType", property="flowType", jdbcType=JdbcType.INTEGER),
        @Result(column="createTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="sourceFlowNo", property="sourceFlowNo", jdbcType=JdbcType.VARCHAR)
    })
    SupplierUnitPriceDiscountFrozenAccountTransferFlow selectByPrimaryKey(Long flowId);


    @Update({
        "update supplier_unit_price_discount_frozen_account_transfer_flow",
        "set flowNo = #{flowNo,jdbcType=VARCHAR},",
          "currencyCode = #{currencyCode,jdbcType=VARCHAR},",
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
          "flowType = #{flowType,jdbcType=INTEGER},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "sourceFlowNo = #{sourceFlowNo,jdbcType=VARCHAR}",
        "where flowId = #{flowId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(SupplierUnitPriceDiscountFrozenAccountTransferFlow record);
}