package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.DistributorCouponFlow;
import com.yhglobal.gongxiao.payment.model.SupplierCouponTransferToDistributorFlow;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

public interface DistributorCouponFlowMapper extends BaseMapper {

    @Insert({
            "insert into distributor_coupon_flow (flowId, flowNo, ",
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
            "values (#{flowId,jdbcType=BIGINT}, #{flowNo,jdbcType=VARCHAR}, ",
            "#{distributorId,jdbcType=BIGINT}, #{distributorName,jdbcType=VARCHAR}, ",
            "#{flowType,jdbcType=INTEGER}, #{projectId,jdbcType=BIGINT}, ",
            "#{projectName,jdbcType=VARCHAR}, #{currencyCode,jdbcType=VARCHAR}, ",
            "#{amountBeforeTransaction,jdbcType=BIGINT}, #{transactionAmount,jdbcType=BIGINT}, ",
            "#{amountAfterTransaction,jdbcType=BIGINT}, #{transactionTime,jdbcType=TIMESTAMP}, ",
            "#{orderNo,jdbcType=VARCHAR}, #{extraInfo,jdbcType=VARCHAR}, ",
            "#{statementCheckingFlag,jdbcType=TINYINT}, #{statementCheckingTime,jdbcType=TIMESTAMP}, ",
            "#{createById,jdbcType=BIGINT}, #{createByName,jdbcType=VARCHAR}, ",
            "#{unfreezeFlowNo,jdbcType=VARCHAR}, #{bufferAccountFlowNo,jdbcType=VARCHAR}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{sourceFlowNo})"
    })
    int insert(DistributorCouponFlow record);

    @Select({
            "select",
            "flowId, flowNo, distributorId, distributorName, flowType, projectId, projectName, ",
            "currencyCode, amountBeforeTransaction, transactionAmount, amountAfterTransaction, ",
            "transactionTime, orderNo, extraInfo, statementCheckingFlag, statementCheckingTime, ",
            "createById, createByName, unfreezeFlowNo, bufferAccountFlowNo, createTime",
            "from distributor_coupon_flow",
            "where flowId = #{flowId,jdbcType=BIGINT}"
    })
    DistributorCouponFlow selectByPrimaryKey(Long flowId);

    @Select({
            "select",
            "flowId, flowNo, distributorId, distributorName, flowType, projectId, projectName, ",
            "currencyCode, amountBeforeTransaction, transactionAmount, amountAfterTransaction, ",
            "transactionTime, orderNo, extraInfo, statementCheckingFlag, statementCheckingTime, ",
            "createById, createByName, unfreezeFlowNo, bufferAccountFlowNo, createTime",
            "from distributor_coupon_flow",
            "where projectId = #{projectId,jdbcType=BIGINT} and supplierId = #{supplierId,jdbcType=BIGINT}"
    })
    List<DistributorCouponFlow> selectAll(long projectId, long supplierId);

    @Update({
            "update distributor_coupon_flow",
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
    int updateByPrimaryKey(DistributorCouponFlow record);

    // 写provider的多条件查询
    @SelectProvider(type = DistributorCouponFlowSqlProvider.class, method = "selectAllBydistributorId")
    List<DistributorCouponFlow> selectAllBydistributorId(@Param("currencyCode") String currencyCode,
                                                         @Param("distributorId") long distributorId,
                                                         @Param("projectId") long projectId,
                                                         @Param("moneyFlow") Integer moneyFlow,
                                                         @Param("beginDate") Date beginDate,
                                                         @Param("endDate") Date endDate);


    @SelectProvider(type = DistributorCouponFlowSqlProvider.class, method = "selectIncomeCount")
    int selectIncomeCount(@Param("currencyCode") String currencyCode,
                          @Param("distributorId") long distributorId,
                          @Param("projectId") long projectId,
                          @Param("moneyFlow") Integer moneyFlow,
                          @Param("beginDate") Date beginDate,
                          @Param("endDate") Date endDate);

    @SelectProvider(type = DistributorCouponFlowSqlProvider.class, method = "selectIncomeAmount")
    Long selectIncomeAmount(@Param("currencyCode") String currencyCode,
                            @Param("distributorId") long distributorId,
                            @Param("projectId") long projectId,
                            @Param("moneyFlow") Integer moneyFlow,
                            @Param("beginDate") Date beginDate,
                            @Param("endDate") Date endDate);

    @SelectProvider(type = DistributorCouponFlowSqlProvider.class, method = "selectExpenditureCount")
    int selectExpenditureCount(@Param("currencyCode") String currencyCode,
                               @Param("distributorId") long distributorId,
                               @Param("projectId") long projectId,
                               @Param("moneyFlow") Integer moneyFlow,
                               @Param("beginDate") Date beginDate,
                               @Param("endDate") Date endDate);

    @SelectProvider(type = DistributorCouponFlowSqlProvider.class, method = "selectExpenditureAmount")
    Long selectExpenditureAmount(@Param("currencyCode") String currencyCode,
                                 @Param("distributorId") long distributorId,
                                 @Param("projectId") long projectId,
                                 @Param("moneyFlow") Integer moneyFlow,
                                 @Param("beginDate") Date beginDate,
                                 @Param("endDate") Date endDate);

}