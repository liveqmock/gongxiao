package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.DistributorPrepaidFlow;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface DistributorPrepaidFlowMapper extends BaseMapper {

    @Insert({
            "insert into ${prefix}_distributor_prepaid_flow (flowId, flowNo, ",
            "distributorId, distributorName, ",
            "flowType, projectId, ",
            "projectName, currencyCode, ",
            "amountBeforeTransaction, transactionAmount, ",
            "amountAfterTransaction, transactionTime, ",
            "orderNo, extraInfo, ",
            "statementCheckingFlag, statementCheckingTime, ",
            "createTime, createById, ",
            "createByName, sourceFlowNo)",
            "values (#{record.flowId}, #{record.flowNo}, ",
            "#{record.distributorId}, #{record.distributorName}, ",
            "#{record.flowType}, #{record.projectId}, ",
            "#{record.projectName}, #{record.currencyCode}, ",
            "#{record.amountBeforeTransaction}, #{record.transactionAmount}, ",
            "#{record.amountAfterTransaction}, #{record.transactionTime}, ",
            "#{record.orderNo}, #{record.extraInfo}, ",
            "#{record.statementCheckingFlag}, #{record.statementCheckingTime}, ",
            "#{record.createTime}, #{record.createById}, ",
            "#{record.createByName}, #{record.sourceFlowNo})"
    })
    int insert(@Param("prefix") String prefix,
               @Param("record") DistributorPrepaidFlow record);

    @Select({
            "select",
            "flowId, flowNo, distributorId, distributorName, flowType, projectId, projectName, ",
            "currencyCode, amountBeforeTransaction, transactionAmount, amountAfterTransaction, ",
            "transactionTime, orderNo, extraInfo, statementCheckingFlag, statementCheckingTime, ",
            "createTime, createById, createByName, unfreezeFlowNo, bufferAccountFlowNo",
            "from ${prefix}_distributor_prepaid_flow",
            "where flowId = #{flowId}"
    })
    DistributorPrepaidFlow selectByPrimaryKey(@Param("prefix") String prefix,
                                              @Param("flowId") Long flowId);

    @Update({
            "update ${prefix}_distributor_prepaid_flow",
            "set flowNo = #{record.flowNo},",
            "distributorId = #{record.distributorId},",
            "distributorName = #{record.distributorName},",
            "flowType = #{record.flowType,jdbcType=SMALLINT},",
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
            "createTime = #{record.createTime}",
            "where flowId = #{record.flowId}"
    })
    int updateByPrimaryKey(@Param("prefix") String prefix,
                           @Param("record") DistributorPrepaidFlow record);

    @SelectProvider(type = DistributorPrepaidFlowSqlProvider.class, method = "selectAllBydistributorId")
    List<DistributorPrepaidFlow> selectAllBydistributorId(@Param("prefix") String prefix,
                                                          @Param("currencyCode") String currencyCode,
                                                          @Param("distributorId") long distributorId,
                                                          @Param("projectId") long projectId,
                                                          @Param("moneyFlow") Integer moneyFlow,
                                                          @Param("beginDate") Date beginDate,
                                                          @Param("endDate") Date endDate);


    @SelectProvider(type = DistributorPrepaidFlowSqlProvider.class, method = "selectIncomeAndExpenditure")
    List<FlowSubtotal> selectIncomeAndExpenditure(@Param("prefix") String prefix,
                                                  @Param("currencyCode") String currencyCode,
                                                  @Param("distributorId") long distributorId,
                                                  @Param("projectId") long projectId,
                                                  @Param("moneyFlow") Integer moneyFlow,
                                                  @Param("beginDate") Date beginDate,
                                                  @Param("endDate") Date endDate);
}