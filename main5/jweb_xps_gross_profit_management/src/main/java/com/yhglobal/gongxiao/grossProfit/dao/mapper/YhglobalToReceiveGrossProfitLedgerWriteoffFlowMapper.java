package com.yhglobal.gongxiao.grossProfit.dao.mapper;


import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.grossProfit.model.YhglobalToReceiveGrossProfitLedgerWriteoffFlow;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface YhglobalToReceiveGrossProfitLedgerWriteoffFlowMapper extends BaseMapper{
    @Delete({
        "delete from yhglobal_to_receive_gross_profit_ledger_writeoff_flow",
        "where flowId = #{flowId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long flowId);

    @Insert({
        "insert into ${tablePrefix}_yhglobal_to_receive_gross_profit_ledger_writeoff_flow " ,
        "(flowId, projectId,projectName, amountBeforeTransaction, ",
        "transactionAmount, amountAfterTransaction, ",
        "transactionTime, purchaseOrderId, ",
        "supplierId, supplierName, ",
        "extraInfo, statementCheckingFlag, ",
        "statementCheckingTime, createTime, ",
        "flowNo, currencyCode,flowType, transferPattern, distributorId,distributorName, differenceAmountAdjustPattern)",
        "values (#{flowId}, #{projectId}, ",
        "#{projectName}, #{amountBeforeTransaction}, ",
        "#{transactionAmount}, #{amountAfterTransaction}, ",
        "#{transactionTime}, #{purchaseOrderId}, ",
        "#{supplierId}, #{supplierName}, ",
        "#{extraInfo}, #{statementCheckingFlag}, ",
        "#{statementCheckingTime}, #{createTime}, ",
        "#{flowNo},  #{currencyCode},#{flowType}, #{transferPattern}, " ,
         "#{distributorId},#{distributorName}, #{differenceAmountAdjustPattern})"
    })
    int insert(YhglobalToReceiveGrossProfitLedgerWriteoffFlow record);



    @Select({
        "select",
        "flowId, projectId, projectName, amountBeforeTransaction, transactionAmount, ",
        "amountAfterTransaction, transactionTime, purchaseOrderId, supplierId, supplierName, ",
        "extraInfo, statementCheckingFlag, statementCheckingTime, createTime, flowNo, ",
        "currencyCode",
        "from yhglobal_to_receive_gross_profit_ledger_writeoff_flow",
        "where flowId = #{flowId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="flowId", property="flowId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="projectId", property="projectId", jdbcType=JdbcType.BIGINT),
        @Result(column="projectName", property="projectName", jdbcType=JdbcType.VARCHAR),
        @Result(column="amountBeforeTransaction", property="amountBeforeTransaction", jdbcType=JdbcType.DECIMAL),
        @Result(column="transactionAmount", property="transactionAmount", jdbcType=JdbcType.DECIMAL),
        @Result(column="amountAfterTransaction", property="amountAfterTransaction", jdbcType=JdbcType.DECIMAL),
        @Result(column="transactionTime", property="transactionTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="purchaseOrderId", property="purchaseOrderId", jdbcType=JdbcType.VARCHAR),
        @Result(column="supplierId", property="supplierId", jdbcType=JdbcType.INTEGER),
        @Result(column="supplierName", property="supplierName", jdbcType=JdbcType.VARCHAR),
        @Result(column="extraInfo", property="extraInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="statementCheckingFlag", property="statementCheckingFlag", jdbcType=JdbcType.TINYINT),
        @Result(column="statementCheckingTime", property="statementCheckingTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="createTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="flowNo", property="flowNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="currencyCode", property="currencyCode", jdbcType=JdbcType.VARCHAR)
    })
    YhglobalToReceiveGrossProfitLedgerWriteoffFlow selectByPrimaryKey(Long flowId);



    @Update({
        "update yhglobal_to_receive_gross_profit_ledger_writeoff_flow",
        "set projectId = #{projectId,jdbcType=BIGINT},",
          "projectName = #{projectName,jdbcType=VARCHAR},",
          "amountBeforeTransaction = #{amountBeforeTransaction,jdbcType=DECIMAL},",
          "transactionAmount = #{transactionAmount,jdbcType=DECIMAL},",
          "amountAfterTransaction = #{amountAfterTransaction,jdbcType=DECIMAL},",
          "transactionTime = #{transactionTime,jdbcType=TIMESTAMP},",
          "purchaseOrderId = #{purchaseOrderId,jdbcType=VARCHAR},",
          "supplierId = #{supplierId,jdbcType=INTEGER},",
          "supplierName = #{supplierName,jdbcType=VARCHAR},",
          "extraInfo = #{extraInfo,jdbcType=VARCHAR},",
          "statementCheckingFlag = #{statementCheckingFlag,jdbcType=TINYINT},",
          "statementCheckingTime = #{statementCheckingTime,jdbcType=TIMESTAMP},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "flowNo = #{flowNo,jdbcType=VARCHAR},",
          "currencyCode = #{currencyCode,jdbcType=VARCHAR}",
        "where flowId = #{flowId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(YhglobalToReceiveGrossProfitLedgerWriteoffFlow record);
}