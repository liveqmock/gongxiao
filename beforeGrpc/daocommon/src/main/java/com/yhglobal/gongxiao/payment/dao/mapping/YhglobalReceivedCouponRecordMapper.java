package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.YhglobalReceivedCouponRecord;
import com.yhglobal.gongxiao.sales.dao.mapping.SalesOrderSqlProvider;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

public interface YhglobalReceivedCouponRecordMapper extends BaseMapper {
    @Delete({
            "delete from yhglobal_received_coupon_record",
            "where recordId = #{recordId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long recordId);

    @Insert({
            "insert into yhglobal_received_coupon_record (recordId, currencyCode, ",
            "amountBeforeTransaction, transactionAmount, ",
            "amountAfterTransaction, transferTime, ",
            "supplierId, supplierName, ",
            "projectId, projectName, ",
            "createdById, createdByName, ",
            "purchaseOrderNo, recordType, ",
            "createTime,sourceFlowNo,remark)",
            "values (#{recordId,jdbcType=BIGINT}, #{currencyCode,jdbcType=VARCHAR}, ",
            "#{amountBeforeTransaction,jdbcType=BIGINT}, #{transactionAmount,jdbcType=BIGINT}, ",
            "#{amountAfterTransaction,jdbcType=BIGINT}, #{transferTime,jdbcType=TIMESTAMP}, ",
            "#{supplierId,jdbcType=BIGINT}, #{supplierName,jdbcType=VARCHAR}, ",
            "#{projectId,jdbcType=BIGINT}, #{projectName,jdbcType=VARCHAR}, ",
            "#{createdById,jdbcType=BIGINT}, #{createdByName,jdbcType=VARCHAR}, ",
            "#{purchaseOrderNo,jdbcType=VARCHAR}, #{recordType,jdbcType=INTEGER}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{sourceFlowNo,jdbcType=VARCHAR}, #{remark, jdbcType=VARCHAR})"
    })
    int insert(YhglobalReceivedCouponRecord record);

    @Select({
            "select",
            "recordId, currencyCode, amountBeforeTransaction, transactionAmount, amountAfterTransaction, ",
            "transferTime, supplierId, supplierName, projectId, projectName, createdById, ",
            "createdByName, purchaseOrderNo, recordType, createTime",
            "from yhglobal_received_coupon_record",
            "where recordId = #{recordId,jdbcType=BIGINT}"
    })
    YhglobalReceivedCouponRecord selectByPrimaryKey(Long recordId);

    @Update({
            "update yhglobal_received_coupon_record",
            "set currencyCode = #{currencyCode,jdbcType=VARCHAR},",
            "amountBeforeTransaction = #{amountBeforeTransaction,jdbcType=BIGINT},",
            "transactionAmount = #{transactionAmount,jdbcType=BIGINT},",
            "amountAfterTransaction = #{amountAfterTransaction,jdbcType=BIGINT},",
            "transferTime = #{transferTime,jdbcType=TIMESTAMP},",
            "supplierId = #{supplierId,jdbcType=BIGINT},",
            "supplierName = #{supplierName,jdbcType=VARCHAR},",
            "projectId = #{projectId,jdbcType=BIGINT},",
            "projectName = #{projectName,jdbcType=VARCHAR},",
            "createdById = #{createdById,jdbcType=BIGINT},",
            "createdByName = #{createdByName,jdbcType=VARCHAR},",
            "purchaseOrderNo = #{purchaseOrderNo,jdbcType=VARCHAR}",
            "where recordId = #{recordId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(YhglobalReceivedCouponRecord record);

    @SelectProvider(type = YhglobalReceivedCouponRecordSqlProvider.class, method = "selectCouponReceivedRecords")
    List<YhglobalReceivedCouponRecord> selectCouponReceivedRecords(@Param("currencyCode") String currencyCode,
                                                                   @Param("projectId") long projectId,
                                                                   @Param("moneyFlow") Integer moneyFlow,
                                                                   @Param("beginDate") Date beginDate,
                                                                   @Param("endDate") Date endDate);

    @SelectProvider(type = YhglobalReceivedCouponRecordSqlProvider.class, method = "selectIncomeAndExpenditure")
    List<FlowSubtotal> selectIncomeAndExpenditure(@Param("currencyCode") String currencyCode,
                                                  @Param("projectId") long projectId,
                                                  @Param("moneyFlow") Integer moneyFlow,
                                                  @Param("beginDate") Date beginDate,
                                                  @Param("endDate") Date endDate);


}