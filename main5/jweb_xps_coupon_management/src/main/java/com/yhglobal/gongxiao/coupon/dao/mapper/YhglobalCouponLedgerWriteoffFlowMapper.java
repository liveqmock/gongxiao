package com.yhglobal.gongxiao.coupon.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;

import com.yhglobal.gongxiao.coupon.model.YhglobalCouponWriteoffFlow;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

/**
 * 越海返利流水表Mapper
 *
 * @Author: 王帅
 */
public interface YhglobalCouponLedgerWriteoffFlowMapper extends BaseMapper {
    @Insert({
            "insert into ${tablePrefix}_yhglobal_coupon_ledger_writeoff_flow (flowId, ",
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
            "from ${tablePrefix}_yhglobal_coupon_ledger_writeoff_flow",
            "where flowId = (select MAX(flowId) from yhglobal_inout_flow)"
    })
    Long selectAmountLatestByMaxPrimaryKey(@Param("tablePrefix")String tablePrefix);


    @Select({
            "select",
            "flowId, flowType, projectId, projectName, amountBeforeTransaction, transactionAmount, ",
            "amountAfterTransaction, transactionTime, purchaseOrderId, supplierId, supplierName, ",
            "salesOrderId, distributorId, distributorName, extraInfo, statementCheckingFlag, ",
            "statementCheckingTime, createTime, flowNo, transferPattern, differenceAmountAdjustPattern, ",
            "currencyCode",
            "from ${tablePrefix}_yhglobal_coupon_ledger_writeoff_flow",
            "where flowId = #{flowId,jdbcType=BIGINT}"
    })
    YhglobalCouponWriteoffFlow selectByPrimaryKey(@Param("tablePrefix")String tablePrefix, Long flowId);

  /*  @UpdateProvider(type=YhglobalCouponWriteoffFlowSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(YhglobalCouponLedgerWriteoffFlow record);*/

    @Update({
            "update ${tablePrefix}_yhglobal_coupon_ledger_writeoff_flow",
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
    int updateByPrimaryKey(@Param("tablePrefix")String tablePrefix, YhglobalCouponWriteoffFlow record);

    @Select({
            "select",
            "flowId, flowType, projectId, projectName, amountBeforeTransaction, transactionAmount, ",
            "amountAfterTransaction, transactionTime, purchaseOrderId, supplierId, supplierName, ",
            "salesOrderId, distributorId, distributorName, extraInfo, statementCheckingFlag, ",
            "statementCheckingTime, createTime, flowNo, transferPattern, differenceAmountAdjustPattern, ",
            "currencyCode",
            "from ${tablePrefix}_yhglobal_coupon_ledger_writeoff_flow",
            "where flowNo = #{flowNo,jdbcType=VARCHAR}"
    })
    YhglobalCouponWriteoffFlow getByFlowNo(@Param("tablePrefix")String tablePrefix, String flowNo);




}