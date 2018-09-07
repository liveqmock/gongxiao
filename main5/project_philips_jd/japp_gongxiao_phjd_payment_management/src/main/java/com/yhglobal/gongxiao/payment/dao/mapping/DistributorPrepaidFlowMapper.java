package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.DistributorPrepaidFlow;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface DistributorPrepaidFlowMapper extends BaseMapper {

    @Insert({
            "insert into distributor_prepaid_flow (flowId, flowNo, ",
            "distributorId, distributorName, ",
            "flowType, projectId, ",
            "projectName, currencyCode, ",
            "amountBeforeTransaction, transactionAmount, ",
            "amountAfterTransaction, transactionTime, ",
            "orderNo, extraInfo, ",
            "statementCheckingFlag, statementCheckingTime, ",
            "createTime, createById, ",
            "createByName, sourceFlowNo)",
            "values (#{flowId,jdbcType=BIGINT}, #{flowNo,jdbcType=VARCHAR}, ",
            "#{distributorId,jdbcType=BIGINT}, #{distributorName,jdbcType=VARCHAR}, ",
            "#{flowType,jdbcType=INTEGER}, #{projectId,jdbcType=BIGINT}, ",
            "#{projectName,jdbcType=VARCHAR}, #{currencyCode,jdbcType=VARCHAR}, ",
            "#{amountBeforeTransaction,jdbcType=BIGINT}, #{transactionAmount,jdbcType=BIGINT}, ",
            "#{amountAfterTransaction,jdbcType=BIGINT}, #{transactionTime,jdbcType=TIMESTAMP}, ",
            "#{orderNo,jdbcType=VARCHAR}, #{extraInfo,jdbcType=VARCHAR}, ",
            "#{statementCheckingFlag,jdbcType=TINYINT}, #{statementCheckingTime,jdbcType=TIMESTAMP}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{createById,jdbcType=BIGINT}, ",
            "#{createByName,jdbcType=VARCHAR}, #{sourceFlowNo})"
    })
    int insert(DistributorPrepaidFlow record);

    @Select({
            "select",
            "flowId, flowNo, distributorId, distributorName, flowType, projectId, projectName, ",
            "currencyCode, amountBeforeTransaction, transactionAmount, amountAfterTransaction, ",
            "transactionTime, orderNo, extraInfo, statementCheckingFlag, statementCheckingTime, ",
            "createTime, createById, createByName, unfreezeFlowNo, bufferAccountFlowNo",
            "from distributor_prepaid_flow",
            "where flowId = #{flowId,jdbcType=BIGINT}"
    })
    DistributorPrepaidFlow selectByPrimaryKey(Long flowId);

    @Update({
            "update distributor_prepaid_flow",
            "set flowNo = #{flowNo,jdbcType=VARCHAR},",
            "distributorId = #{distributorId,jdbcType=BIGINT},",
            "distributorName = #{distributorName,jdbcType=VARCHAR},",
            "flowType = #{flowType,jdbcType=SMALLINT},",
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
            "createTime = #{createTime,jdbcType=TIMESTAMP}",
            "where flowId = #{flowId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DistributorPrepaidFlow record);

    @SelectProvider(type = DistributorPrepaidFlowSqlProvider.class, method = "selectAllBydistributorId")
    List<DistributorPrepaidFlow> selectAllBydistributorId(@Param("currencyCode") String currencyCode,
                                                          @Param("distributorId") long distributorId,
                                                          @Param("projectId") long projectId,
                                                          @Param("moneyFlow") Integer moneyFlow,
                                                          @Param("beginDate") Date beginDate,
                                                          @Param("endDate") Date endDate);


    @SelectProvider(type = DistributorPrepaidFlowSqlProvider.class, method = "selectIncomeAndExpenditure")
    List<FlowSubtotal> selectIncomeAndExpenditure(@Param("currencyCode") String currencyCode,
                                                  @Param("distributorId") long distributorId,
                                                  @Param("projectId") long projectId,
                                                  @Param("moneyFlow") Integer moneyFlow,
                                                  @Param("beginDate") Date beginDate,
                                                  @Param("endDate") Date endDate);
}