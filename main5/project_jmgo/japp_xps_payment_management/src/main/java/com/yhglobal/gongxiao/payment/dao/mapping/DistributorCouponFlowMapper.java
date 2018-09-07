package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.DistributorCouponFlow;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface DistributorCouponFlowMapper extends BaseMapper {

    @Insert({
            "insert into ${prefix}_distributor_coupon_flow (flowId, flowNo, ",
            "distributorId, distributorName, ",
            "flowType, projectId, ",
            "projectName, currencyCode, ",
            "amountBeforeTransaction, transactionAmount, ",
            "amountAfterTransaction, transactionTime, ",
            "orderNo, extraInfo, ",
            "statementCheckingFlag, statementCheckingTime, ",
            "createById, createByName, ",
            "unfreezeFlowNo, bufferAccountFlowNo, ",
            "createTime, sourceFlowNo)",
            "values (#{record.flowId}, #{record.flowNo}, ",
            "#{record.distributorId}, #{record.distributorName}, ",
            "#{record.flowType}, #{record.projectId}, ",
            "#{record.projectName}, #{record.currencyCode}, ",
            "#{record.amountBeforeTransaction}, #{record.transactionAmount}, ",
            "#{record.amountAfterTransaction}, #{record.transactionTime}, ",
            "#{record.orderNo}, #{record.extraInfo}, ",
            "#{record.statementCheckingFlag}, #{record.statementCheckingTime}, ",
            "#{record.createById}, #{record.createByName}, ",
            "#{record.unfreezeFlowNo}, #{record.bufferAccountFlowNo}, ",
            "#{record.createTime}, #{record.sourceFlowNo})"
    })
    int insert(@Param("prefix") String prefix,
               @Param("record") DistributorCouponFlow record);


    @Update({
            "update ${prefix}_distributor_coupon_flow",
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
                           @Param("record") DistributorCouponFlow record);

    // 写provider的多条件查询
    @SelectProvider(type = DistributorCouponFlowSqlProvider.class, method = "selectAllBydistributorId")
    List<DistributorCouponFlow> selectAllBydistributorId(@Param("prefix") String prefix,
                                                         @Param("currencyCode") String currencyCode,
                                                         @Param("distributorId") long distributorId,
                                                         @Param("projectId") long projectId,
                                                         @Param("moneyFlow") Integer moneyFlow,
                                                         @Param("beginDate") Date beginDate,
                                                         @Param("endDate") Date endDate);


    @SelectProvider(type = DistributorCouponFlowSqlProvider.class, method = "selectIncomeCount")
    int selectIncomeCount(@Param("prefix") String prefix,
                          @Param("currencyCode") String currencyCode,
                          @Param("distributorId") long distributorId,
                          @Param("projectId") long projectId,
                          @Param("moneyFlow") Integer moneyFlow,
                          @Param("beginDate") Date beginDate,
                          @Param("endDate") Date endDate);

    @SelectProvider(type = DistributorCouponFlowSqlProvider.class, method = "selectIncomeAmount")
    Long selectIncomeAmount(@Param("prefix") String prefix,
                            @Param("currencyCode") String currencyCode,
                            @Param("distributorId") long distributorId,
                            @Param("projectId") long projectId,
                            @Param("moneyFlow") Integer moneyFlow,
                            @Param("beginDate") Date beginDate,
                            @Param("endDate") Date endDate);

    @SelectProvider(type = DistributorCouponFlowSqlProvider.class, method = "selectExpenditureCount")
    int selectExpenditureCount(@Param("prefix") String prefix,
                               @Param("currencyCode") String currencyCode,
                               @Param("distributorId") long distributorId,
                               @Param("projectId") long projectId,
                               @Param("moneyFlow") Integer moneyFlow,
                               @Param("beginDate") Date beginDate,
                               @Param("endDate") Date endDate);

    @SelectProvider(type = DistributorCouponFlowSqlProvider.class, method = "selectExpenditureAmount")
    Long selectExpenditureAmount(@Param("prefix") String prefix,
                                 @Param("currencyCode") String currencyCode,
                                 @Param("distributorId") long distributorId,
                                 @Param("projectId") long projectId,
                                 @Param("moneyFlow") Integer moneyFlow,
                                 @Param("beginDate") Date beginDate,
                                 @Param("endDate") Date endDate);

}