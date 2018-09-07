package com.yhglobal.gongxiao.payment.AccountManageDao.mapping;


import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.YhglobalToPayMoney;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

public interface YhglobalToPayMoneyMapper extends BaseMapper{
    @Delete({
        "delete from yhglobal_to_pay_money",
        "where paymentId = #{paymentId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long paymentId);

    @Insert({
        "insert into yhglobal_to_pay_money (paymentId, confirmStatus, ",
        "status, paymentApplyNo, ",
        "createTime, estimatedPaymentTime, ",
        "supplierId, supplierName, ",
        "settlementType, creditPaymentDays, ",
        "paymentType, bankAcceptancePeriod, ",
        "purchasePlanNo, orderTime, ",
        "currencyCode, toPayAmount, ",
        "toBePayAmount, confirmAmount, ",
        "receiptAmount, receiverName, ",
        "projectId, projectName, ",
        "dataVersion, lastUpdateTime, ",
        "confirmRecord, planOrderAmount, ",
        "tracelog)",
        "values (#{paymentId,jdbcType=BIGINT}, #{confirmStatus,jdbcType=TINYINT}, ",
        "#{status,jdbcType=TINYINT}, #{paymentApplyNo,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{estimatedPaymentTime,jdbcType=TIMESTAMP}, ",
        "#{supplierId,jdbcType=BIGINT}, #{supplierName,jdbcType=VARCHAR}, ",
        "#{settlementType,jdbcType=TINYINT}, #{creditPaymentDays,jdbcType=INTEGER}, ",
        "#{paymentType,jdbcType=TINYINT}, #{bankAcceptancePeriod,jdbcType=INTEGER}, ",
        "#{purchasePlanNo,jdbcType=VARCHAR}, #{orderTime,jdbcType=TIMESTAMP}, ",
        "#{currencyCode,jdbcType=VARCHAR}, #{toPayAmount,jdbcType=DECIMAL}, ",
        "#{toBePayAmount,jdbcType=DECIMAL}, #{confirmAmount,jdbcType=DECIMAL}, ",
        "#{receiptAmount,jdbcType=DECIMAL}, #{receiverName,jdbcType=VARCHAR}, ",
        "#{projectId,jdbcType=BIGINT}, #{projectName,jdbcType=VARCHAR}, ",
        "#{dataVersion,jdbcType=BIGINT}, #{lastUpdateTime,jdbcType=TIMESTAMP}, ",
        "#{confirmRecord,jdbcType=VARCHAR}, #{planOrderAmount,jdbcType=DECIMAL}, ",
        "#{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(YhglobalToPayMoney record);



    @Select({
        "select",
        "paymentId, confirmStatus, status, paymentApplyNo, createTime, estimatedPaymentTime, ",
        "supplierId, supplierName, settlementType, creditPaymentDays, paymentType, bankAcceptancePeriod, ",
        "purchasePlanNo, orderTime, currencyCode, toPayAmount, toBePayAmount, confirmAmount, ",
        "receiptAmount, receiverName, projectId, projectName, dataVersion, lastUpdateTime, ",
        "confirmRecord, planOrderAmount, tracelog",
        "from yhglobal_to_pay_money",
        "where paymentId = #{paymentId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="paymentId", property="paymentId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="confirmStatus", property="confirmStatus", jdbcType=JdbcType.TINYINT),
        @Result(column="status", property="status", jdbcType=JdbcType.TINYINT),
        @Result(column="paymentApplyNo", property="paymentApplyNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="createTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="estimatedPaymentTime", property="estimatedPaymentTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="supplierId", property="supplierId", jdbcType=JdbcType.BIGINT),
        @Result(column="supplierName", property="supplierName", jdbcType=JdbcType.VARCHAR),
        @Result(column="settlementType", property="settlementType", jdbcType=JdbcType.TINYINT),
        @Result(column="creditPaymentDays", property="creditPaymentDays", jdbcType=JdbcType.INTEGER),
        @Result(column="paymentType", property="paymentType", jdbcType=JdbcType.TINYINT),
        @Result(column="bankAcceptancePeriod", property="bankAcceptancePeriod", jdbcType=JdbcType.INTEGER),
        @Result(column="purchasePlanNo", property="purchasePlanNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="orderTime", property="orderTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="currencyCode", property="currencyCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="toPayAmount", property="toPayAmount", jdbcType=JdbcType.DECIMAL),
        @Result(column="toBePayAmount", property="toBePayAmount", jdbcType=JdbcType.DECIMAL),
        @Result(column="confirmAmount", property="confirmAmount", jdbcType=JdbcType.DECIMAL),
        @Result(column="receiptAmount", property="receiptAmount", jdbcType=JdbcType.DECIMAL),
        @Result(column="receiverName", property="receiverName", jdbcType=JdbcType.VARCHAR),
        @Result(column="projectId", property="projectId", jdbcType=JdbcType.BIGINT),
        @Result(column="projectName", property="projectName", jdbcType=JdbcType.VARCHAR),
        @Result(column="dataVersion", property="dataVersion", jdbcType=JdbcType.BIGINT),
        @Result(column="lastUpdateTime", property="lastUpdateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="confirmRecord", property="confirmRecord", jdbcType=JdbcType.VARCHAR),
        @Result(column="planOrderAmount", property="planOrderAmount", jdbcType=JdbcType.DECIMAL),
        @Result(column="tracelog", property="tracelog", jdbcType=JdbcType.LONGVARCHAR)
    })
    YhglobalToPayMoney selectByPrimaryKey(Long paymentId);



    @Update({
        "update yhglobal_to_pay_money",
        "set confirmStatus = #{confirmStatus,jdbcType=TINYINT},",
          "status = #{status,jdbcType=TINYINT},",
          "paymentApplyNo = #{paymentApplyNo,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "estimatedPaymentTime = #{estimatedPaymentTime,jdbcType=TIMESTAMP},",
          "supplierId = #{supplierId,jdbcType=BIGINT},",
          "supplierName = #{supplierName,jdbcType=VARCHAR},",
          "settlementType = #{settlementType,jdbcType=TINYINT},",
          "creditPaymentDays = #{creditPaymentDays,jdbcType=INTEGER},",
          "paymentType = #{paymentType,jdbcType=TINYINT},",
          "bankAcceptancePeriod = #{bankAcceptancePeriod,jdbcType=INTEGER},",
          "purchasePlanNo = #{purchasePlanNo,jdbcType=VARCHAR},",
          "orderTime = #{orderTime,jdbcType=TIMESTAMP},",
          "currencyCode = #{currencyCode,jdbcType=VARCHAR},",
          "toPayAmount = #{toPayAmount,jdbcType=DECIMAL},",
          "toBePayAmount = #{toBePayAmount,jdbcType=DECIMAL},",
          "confirmAmount = #{confirmAmount,jdbcType=DECIMAL},",
          "receiptAmount = #{receiptAmount,jdbcType=DECIMAL},",
          "receiverName = #{receiverName,jdbcType=VARCHAR},",
          "projectId = #{projectId,jdbcType=BIGINT},",
          "projectName = #{projectName,jdbcType=VARCHAR},",
          "dataVersion = #{dataVersion,jdbcType=BIGINT},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "confirmRecord = #{confirmRecord,jdbcType=VARCHAR},",
          "planOrderAmount = #{planOrderAmount,jdbcType=DECIMAL},",
          "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
        "where paymentId = #{paymentId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(YhglobalToPayMoney record);

    @Update({
        "update yhglobal_to_pay_money",
        "set confirmStatus = #{confirmStatus,jdbcType=TINYINT},",
          "status = #{status,jdbcType=TINYINT},",
          "paymentApplyNo = #{paymentApplyNo,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "estimatedPaymentTime = #{estimatedPaymentTime,jdbcType=TIMESTAMP},",
          "supplierId = #{supplierId,jdbcType=BIGINT},",
          "supplierName = #{supplierName,jdbcType=VARCHAR},",
          "settlementType = #{settlementType,jdbcType=TINYINT},",
          "creditPaymentDays = #{creditPaymentDays,jdbcType=INTEGER},",
          "paymentType = #{paymentType,jdbcType=TINYINT},",
          "bankAcceptancePeriod = #{bankAcceptancePeriod,jdbcType=INTEGER},",
          "purchasePlanNo = #{purchasePlanNo,jdbcType=VARCHAR},",
          "orderTime = #{orderTime,jdbcType=TIMESTAMP},",
          "currencyCode = #{currencyCode,jdbcType=VARCHAR},",
          "toPayAmount = #{toPayAmount,jdbcType=DECIMAL},",
          "toBePayAmount = #{toBePayAmount,jdbcType=DECIMAL},",
          "confirmAmount = #{confirmAmount,jdbcType=DECIMAL},",
          "receiptAmount = #{receiptAmount,jdbcType=DECIMAL},",
          "receiverName = #{receiverName,jdbcType=VARCHAR},",
          "projectId = #{projectId,jdbcType=BIGINT},",
          "projectName = #{projectName,jdbcType=VARCHAR},",
          "dataVersion = #{dataVersion,jdbcType=BIGINT},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "confirmRecord = #{confirmRecord,jdbcType=VARCHAR},",
          "planOrderAmount = #{planOrderAmount,jdbcType=DECIMAL}",
        "where paymentId = #{paymentId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(YhglobalToPayMoney record);

    @SelectProvider(type = YhglobalToPayMoneySqlProvider.class, method = "queryData")
    List<YhglobalToPayMoney> queryData(@Param("paymentApplyNo")String paymentApplyNo,
                                       @Param("purchasePlanNo")String purchasePlanNo,
                                       @Param("supplierId")Long supplierId,
                                       @Param("status")Byte status,
                                       @Param("settlementType")Byte settlementType,
                                       @Param("paymentType")Byte paymentType,
                                       @Param("dateS")Date dateS,
                                       @Param("dateE")Date dateE);
}