package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.DistributorCashFlow;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface DistributorCashFlowMapper extends BaseMapper {

    @Insert({
            "insert into ${prefix}_distributor_cash_flow (flowId, flowNo, ",
            "distributorId, distributorName, ",
            "flowType, projectId, ",
            "projectName, currencyCode, ",
            "amountBeforeTransaction, transactionAmount, ",
            "amountAfterTransaction, transactionTime, ",
            "orderNo, extraInfo, ",
            "statementCheckingFlag, statementCheckingTime, ",
            "createById, createByName, ",
            "unfreezeFlowNo, createTime)",
            "values (#{record.flowId}, #{record.flowNo}, ",
            "#{record.distributorId}, #{record.distributorName}, ",
            "#{record.flowType,jdbcType=INTEGER}, #{record.projectId}, ",
            "#{record.projectName}, #{record.currencyCode}, ",
            "#{record.amountBeforeTransaction}, #{record.transactionAmount}, ",
            "#{record.amountAfterTransaction}, #{record.transactionTime}, ",
            "#{record.orderNo}, #{record.extraInfo}, ",
            "#{record.statementCheckingFlag}, #{record.statementCheckingTime}, ",
            "#{record.createById}, #{record.createByName}, ",
            "#{record.unfreezeFlowNo}, #{record.createTime})"
    })
    int insert(@Param("prefix") String prefix,
               @Param("record") DistributorCashFlow record);

    @Update({
            "update ${prefix}_distributor_cash_flow",
            "set flowNo = #{record.flowNo},",
            "distributorId = #{record.distributorId},",
            "distributorName = #{record.distributorName},",
            "flowType = #{record.flowType,jdbcType=INTEGER},",
            "projectId = #{record.projectId},",
            "projectName = #{record.projectName},",
            "currencyCode = #{record.currencyCode},",
            "amountBeforeTransaction = #{record.amountBeforeTransaction},",
            "transactionAmount = #{record.transactionAmount},",
            "amountAfterTransaction = #{record.amountAfterTransaction},",
            "transactionTime = #{record.transactionTime},",
            "orderNo = #{record.orderNo},",
            "extraInfo = #{record.extraInfo},",
            "statementCheckingFlag = #{record.statementCheckingFlag},",
            "statementCheckingTime = #{record.statementCheckingTime},",
            "createById = #{record.createById},",
            "createByName = #{record.createByName},",
            "unfreezeFlowNo = #{record.unfreezeFlowNo},",
            "createTime = #{record.createTime}",
            "where flowId = #{record.flowId}"
    })
    int updateByPrimaryKey(@Param("prefix") String prefix,
                           @Param("record") DistributorCashFlow record);

    // 写provider的多条件查询
    @SelectProvider(type = DistributorCashFlowSqlProvider.class, method = "selectAllBydistributorId")
    List<DistributorCashFlow> selectAllBydistributorId(@Param("prefix") String prefix,
                                                       @Param("currencyCode") String currencyCode,
                                                       @Param("distributorId") long distributorId,
                                                       @Param("projectId") long projectId,
                                                       @Param("moneyFlow") int moneyFlow,
                                                       @Param("beginDate") String beginDate,
                                                       @Param("endDate") String endDate);


    @SelectProvider(type = DistributorCashFlowSqlProvider.class, method = "selectIncomeAndExpenditure")
    List<FlowSubtotal> selectIncomeAndExpenditure(@Param("prefix") String prefix, @Param("currencyCode") String currencyCode,
                                                  @Param("distributorId") long distributorId,
                                                  @Param("projectId") long projectId,
                                                  @Param("moneyFlow") Integer moneyFlow,
                                                  @Param("beginDate") String beginDate,
                                                  @Param("endDate") String endDate);


}