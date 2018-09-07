package com.yhglobal.gongxiao.payment.flow.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.dao.mapping.SupplierCouponTransferRecordSqlProvider;
import com.yhglobal.gongxiao.payment.flow.YhglobalCouponWriteoffFlow;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

/**
 * 越海返利流水表Mapper
 *
 * @Author: 王帅
 */
public interface YhglobalCouponLedgerWriteoffFlowMapper extends BaseMapper {
    @Insert({
            "insert into yhglobal_coupon_ledger_writeoff_flow (flowId, ",
            "flowType, projectId, ",
            "projectName, amountBeforeTransaction, ",
            "transactionAmount, amountAfterTransaction, ",
            "transactionTime, purchaseOrderId, ",
            "supplierId, supplierName, ",
            "salesOrderId, distributorId, ",
            "distributorName, extraInfo, ",
            "statementCheckingFlag, statementCheckingTime, ",
            "createTime, flowNo, transferPattern, differenceAmountAdjustPattern, currencyCode)",
            "values (#{flowId}, ",
            "#{flowType}, #{projectId}, ",
            "#{projectName}, #{amountBeforeTransaction}, ",
            "#{transactionAmount}, #{amountAfterTransaction}, ",
            "#{transactionTime}, #{purchaseOrderId}, ",
            "#{supplierId}, #{supplierName}, ",
            "#{salesOrderId}, #{distributorId}, ",
            "#{distributorName}, #{extraInfo}, ",
            "#{statementCheckingFlag}, #{statementCheckingTime}, ",
            "#{createTime}, #{flowNo},",
            "#{transferPattern}, #{differenceAmountAdjustPattern}, #{currencyCode})"
    })
    int insert(YhglobalCouponWriteoffFlow record);


    /**
     * 查找最近的一条流水的事务后账户余额  依据主键最大
     *
     * @return 获取当前账户余额
     * @author 王帅
     */
    @Select({"select",
            "amountAfterTransaction",
            "from yhglobal_coupon_ledger_writeoff_flow",
            "where flowId = (select MAX(flowId) from yhglobal_inout_flow)"
    })
    Long selectAmountLatestByMaxPrimaryKey();


    @Select({
            "select",
            "flowId, flowType, projectId, projectName, amountBeforeTransaction, transactionAmount, ",
            "amountAfterTransaction, transactionTime, purchaseOrderId, supplierId, supplierName, ",
            "salesOrderId, distributorId, distributorName, extraInfo, statementCheckingFlag, ",
            "statementCheckingTime, createTime, flowNo, transferPattern, differenceAmountAdjustPattern, ",
            "currencyCode",
            "from yhglobal_coupon_ledger_writeoff_flow",
            "where flowId = #{flowId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="flowId", property="flowId", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="flowType", property="flowType", jdbcType=JdbcType.SMALLINT),
            @Result(column="projectId", property="projectId", jdbcType=JdbcType.BIGINT),
            @Result(column="projectName", property="projectName", jdbcType=JdbcType.VARCHAR),
            @Result(column="amountBeforeTransaction", property="amountBeforeTransaction", jdbcType=JdbcType.BIGINT),
            @Result(column="transactionAmount", property="transactionAmount", jdbcType=JdbcType.BIGINT),
            @Result(column="amountAfterTransaction", property="amountAfterTransaction", jdbcType=JdbcType.BIGINT),
            @Result(column="transactionTime", property="transactionTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="purchaseOrderId", property="purchaseOrderId", jdbcType=JdbcType.VARCHAR),
            @Result(column="supplierId", property="supplierId", jdbcType=JdbcType.INTEGER),
            @Result(column="supplierName", property="supplierName", jdbcType=JdbcType.VARCHAR),
            @Result(column="salesOrderId", property="salesOrderId", jdbcType=JdbcType.VARCHAR),
            @Result(column="distributorId", property="distributorId", jdbcType=JdbcType.BIGINT),
            @Result(column="distributorName", property="distributorName", jdbcType=JdbcType.VARCHAR),
            @Result(column="extraInfo", property="extraInfo", jdbcType=JdbcType.VARCHAR),
            @Result(column="statementCheckingFlag", property="statementCheckingFlag", jdbcType=JdbcType.TINYINT),
            @Result(column="statementCheckingTime", property="statementCheckingTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="createTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="flowNo", property="flowNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="transferPattern", property="transferPattern", jdbcType=JdbcType.VARCHAR),
            @Result(column="differenceAmountAdjustPattern", property="differenceAmountAdjustPattern", jdbcType=JdbcType.VARCHAR),
            @Result(column="currencyCode", property="currencyCode", jdbcType=JdbcType.VARCHAR)
    })
    YhglobalCouponWriteoffFlow selectByPrimaryKey(Long flowId);

  /*  @UpdateProvider(type=YhglobalCouponWriteoffFlowSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(YhglobalCouponLedgerWriteoffFlow record);*/

    @Update({
            "update yhglobal_coupon_ledger_writeoff_flow",
            "set flowType = #{flowType,jdbcType=SMALLINT},",
            "projectId = #{projectId,jdbcType=BIGINT},",
            "projectName = #{projectName,jdbcType=VARCHAR},",
            "amountBeforeTransaction = #{amountBeforeTransaction,jdbcType=BIGINT},",
            "transactionAmount = #{transactionAmount,jdbcType=BIGINT},",
            "amountAfterTransaction = #{amountAfterTransaction,jdbcType=BIGINT},",
            "transactionTime = #{transactionTime,jdbcType=TIMESTAMP},",
            "purchaseOrderId = #{purchaseOrderId,jdbcType=VARCHAR},",
            "supplierId = #{supplierId,jdbcType=INTEGER},",
            "supplierName = #{supplierName,jdbcType=VARCHAR},",
            "salesOrderId = #{salesOrderId,jdbcType=VARCHAR},",
            "distributorId = #{distributorId,jdbcType=BIGINT},",
            "distributorName = #{distributorName,jdbcType=VARCHAR},",
            "extraInfo = #{extraInfo,jdbcType=VARCHAR},",
            "statementCheckingFlag = #{statementCheckingFlag,jdbcType=TINYINT},",
            "statementCheckingTime = #{statementCheckingTime,jdbcType=TIMESTAMP},",
            "createTime = #{createTime,jdbcType=TIMESTAMP},",
            "flowNo = #{flowNo,jdbcType=VARCHAR},",
            "transferPattern = #{transferPattern,jdbcType=VARCHAR},",
            "differenceAmountAdjustPattern = #{differenceAmountAdjustPattern,jdbcType=VARCHAR},",
            "currencyCode = #{currencyCode,jdbcType=VARCHAR}",
            "where flowId = #{flowId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(YhglobalCouponWriteoffFlow record);

    @Select({
            "select",
            "flowId, flowType, projectId, projectName, amountBeforeTransaction, transactionAmount, ",
            "amountAfterTransaction, transactionTime, purchaseOrderId, supplierId, supplierName, ",
            "salesOrderId, distributorId, distributorName, extraInfo, statementCheckingFlag, ",
            "statementCheckingTime, createTime, flowNo, transferPattern, differenceAmountAdjustPattern, ",
            "currencyCode",
            "from yhglobal_coupon_ledger_writeoff_flow",
            "where flowNo = #{flowNo,jdbcType=VARCHAR}"
    })
    YhglobalCouponWriteoffFlow getByFlowNo(String flowNo);




}