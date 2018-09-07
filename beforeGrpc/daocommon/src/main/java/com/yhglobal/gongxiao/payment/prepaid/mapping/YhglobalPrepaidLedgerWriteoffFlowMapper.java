package com.yhglobal.gongxiao.payment.prepaid.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.prepaid.YhglobalPrepaidLedgerWriteoffFlow;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 15:25 2018/4/27
 */
public interface YhglobalPrepaidLedgerWriteoffFlowMapper extends BaseMapper {
    @Insert({
            "insert into yhglobal_prepaid_ledger_writeoff_flow (flowId, flowCode, ",
            "projectId, ",
            "projectName, amountBeforeTransaction, ",
            "transactionAmount, amountAfterTransaction, ",
            "transactionTime, ",
            "supplierId, supplierName, ",
            "salesOrderId, distributorId, ",
            "distributorName, extraInfo, ",
            "statementCheckingFlag, statementCheckingTime, ",
            "createTime, ",
            "transferPattern, differenceAmountAdjustPattern)",
            "values (#{flowId,jdbcType=BIGINT}, #{flowCode,jdbcType=VARCHAR}, ",
            "#{projectId,jdbcType=BIGINT}, ",
            "#{projectName,jdbcType=VARCHAR}, #{amountBeforeTransaction,jdbcType=BIGINT}, ",
            "#{transactionAmount,jdbcType=BIGINT}, #{amountAfterTransaction,jdbcType=BIGINT}, ",
            "#{transactionTime,jdbcType=TIMESTAMP},  ",
            "#{supplierId,jdbcType=INTEGER}, #{supplierName,jdbcType=VARCHAR}, ",
            "#{salesOrderId,jdbcType=VARCHAR}, #{distributorId,jdbcType=BIGINT}, ",
            "#{distributorName,jdbcType=VARCHAR}, #{extraInfo,jdbcType=VARCHAR}, ",
            "#{statementCheckingFlag,jdbcType=TINYINT}, #{statementCheckingTime,jdbcType=TIMESTAMP}, ",
            "#{createTime,jdbcType=TIMESTAMP},  ",
            "#{transferPattern,jdbcType=VARCHAR}, #{differenceAmountAdjustPattern,jdbcType=VARCHAR})"
    })
    int insert(YhglobalPrepaidLedgerWriteoffFlow record);
    @Select({
            "select",
            "flowId, flowCode, projectId, projectName, amountBeforeTransaction, ",
            "transactionAmount, amountAfterTransaction, transactionTime, ",
            "supplierId, supplierName, salesOrderId, distributorId, distributorName, extraInfo, ",
            "statementCheckingFlag, statementCheckingTime, createTime, ",
            "transferPattern, differenceAmountAdjustPattern",
            "from yhglobal_prepaid_ledger_writeoff_flow",
            "where flowId = #{flowId,jdbcType=BIGINT}"
    })
    YhglobalPrepaidLedgerWriteoffFlow selectById(Long flowId);
    @Update({
            "update yhglobal_prepaid_ledger_writeoff_flow",
            "set flowCode = #{flowCode,jdbcType=VARCHAR},",
            "projectId = #{projectId,jdbcType=BIGINT},",
            "projectName = #{projectName,jdbcType=VARCHAR},",
            "amountBeforeTransaction = #{amountBeforeTransaction,jdbcType=BIGINT},",
            "transactionAmount = #{transactionAmount,jdbcType=BIGINT},",
            "amountAfterTransaction = #{amountAfterTransaction,jdbcType=BIGINT},",
            "transactionTime = #{transactionTime,jdbcType=TIMESTAMP},",
            "supplierId = #{supplierId,jdbcType=INTEGER},",
            "supplierName = #{supplierName,jdbcType=VARCHAR},",
            "salesOrderId = #{salesOrderId,jdbcType=VARCHAR},",
            "distributorId = #{distributorId,jdbcType=BIGINT},",
            "distributorName = #{distributorName,jdbcType=VARCHAR},",
            "extraInfo = #{extraInfo,jdbcType=VARCHAR},",
            "statementCheckingFlag = #{statementCheckingFlag,jdbcType=TINYINT},",
            "statementCheckingTime = #{statementCheckingTime,jdbcType=TIMESTAMP},",
            "createTime = #{createTime,jdbcType=TIMESTAMP},",
            "transferPattern = #{transferPattern,jdbcType=VARCHAR},",
            "differenceAmountAdjustPattern = #{differenceAmountAdjustPattern,jdbcType=VARCHAR}",
            "where flowId = #{flowId,jdbcType=BIGINT}"
    })
    int updateById(YhglobalPrepaidLedgerWriteoffFlow record);
}
