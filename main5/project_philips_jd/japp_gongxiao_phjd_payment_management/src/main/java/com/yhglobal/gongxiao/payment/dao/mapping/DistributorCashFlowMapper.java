package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.DistributorCashFlow;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface DistributorCashFlowMapper extends BaseMapper {

    @Insert({
            "insert into distributor_cash_flow (flowId, flowNo, ",
            "distributorId, distributorName, ",
            "flowType, projectId, ",
            "projectName, currencyCode, ",
            "amountBeforeTransaction, transactionAmount, ",
            "amountAfterTransaction, transactionTime, ",
            "orderNo, extraInfo, ",
            "statementCheckingFlag, statementCheckingTime, ",
            "createById, createByName, ",
            "unfreezeFlowNo, createTime)",
            "values (#{flowId,jdbcType=BIGINT}, #{flowNo,jdbcType=VARCHAR}, ",
            "#{distributorId,jdbcType=BIGINT}, #{distributorName,jdbcType=VARCHAR}, ",
            "#{flowType,jdbcType=INTEGER}, #{projectId,jdbcType=BIGINT}, ",
            "#{projectName,jdbcType=VARCHAR}, #{currencyCode,jdbcType=VARCHAR}, ",
            "#{amountBeforeTransaction,jdbcType=BIGINT}, #{transactionAmount,jdbcType=BIGINT}, ",
            "#{amountAfterTransaction,jdbcType=BIGINT}, #{transactionTime,jdbcType=TIMESTAMP}, ",
            "#{orderNo,jdbcType=VARCHAR}, #{extraInfo,jdbcType=VARCHAR}, ",
            "#{statementCheckingFlag,jdbcType=TINYINT}, #{statementCheckingTime,jdbcType=TIMESTAMP}, ",
            "#{createById,jdbcType=BIGINT}, #{createByName,jdbcType=VARCHAR}, ",
            "#{unfreezeFlowNo,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(DistributorCashFlow record);


    @Select({
            "select",
            "flowId, flowNo, distributorId, distributorName, flowType, projectId, projectName, ",
            "currencyCode, amountBeforeTransaction, transactionAmount, amountAfterTransaction, ",
            "transactionTime, orderNo, extraInfo, statementCheckingFlag, statementCheckingTime, ",
            "createById, createByName, unfreezeFlowNo, createTime",
            "from distributor_cash_flow",
            "where flowId = #{flowId,jdbcType=BIGINT}"
    })
    DistributorCashFlow selectByPrimaryKey(Long flowId);

    @Update({
            "update distributor_cash_flow",
            "set flowNo = #{flowNo,jdbcType=VARCHAR},",
            "distributorId = #{distributorId,jdbcType=BIGINT},",
            "distributorName = #{distributorName,jdbcType=VARCHAR},",
            "flowType = #{flowType,jdbcType=INTEGER},",
            "projectId = #{projectId,jdbcType=BIGINT},",
            "projectName = #{projectName,jdbcType=VARCHAR},",
            "currencyCode = #{currencyCode,jdbcType=VARCHAR},",
            "amountBeforeTransaction = #{amountBeforeTransaction,jdbcType=BIGINT},",
            "transactionAmount = #{transactionAmount,jdbcType=BIGINT},",
            "amountAfterTransaction = #{amountAfterTransaction,jdbcType=BIGINT},",
            "transactionTime = #{transactionTime,jdbcType=TIMESTAMP},",
            "orderNo = #{orderNo,jdbcType=VARCHAR},",
            "extraInfo = #{extraInfo,jdbcType=VARCHAR},",
            "statementCheckingFlag = #{statementCheckingFlag,jdbcType=TINYINT},",
            "statementCheckingTime = #{statementCheckingTime,jdbcType=TIMESTAMP},",
            "createById = #{createById,jdbcType=BIGINT},",
            "createByName = #{createByName,jdbcType=VARCHAR},",
            "unfreezeFlowNo = #{unfreezeFlowNo,jdbcType=VARCHAR},",
            "createTime = #{createTime,jdbcType=TIMESTAMP}",
            "where flowId = #{flowId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DistributorCashFlow record);

    // 写provider的多条件查询
    @SelectProvider(type = DistributorCashFlowSqlProvider.class, method = "selectAllBydistributorId")
    List<DistributorCashFlow> selectAllBydistributorId(@Param("currencyCode") String currencyCode,
                                                       @Param("distributorId") long distributorId,
                                                       @Param("projectId") long projectId,
                                                       @Param("moneyFlow") int moneyFlow,
                                                       @Param("beginDate") String beginDate,
                                                       @Param("endDate") String endDate);


    @SelectProvider(type = DistributorCashFlowSqlProvider.class, method = "selectIncomeAndExpenditure")
    List<FlowSubtotal> selectIncomeAndExpenditure(@Param("currencyCode") String currencyCode,
                                                  @Param("distributorId") long distributorId,
                                                  @Param("projectId") long projectId,
                                                  @Param("moneyFlow") Integer moneyFlow,
                                                  @Param("beginDate") String beginDate,
                                                  @Param("endDate") String endDate);


}