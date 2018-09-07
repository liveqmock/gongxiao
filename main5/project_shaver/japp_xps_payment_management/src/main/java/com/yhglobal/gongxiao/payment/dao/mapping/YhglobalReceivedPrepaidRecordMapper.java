package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.YhglobalReceivedPrepaidRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface YhglobalReceivedPrepaidRecordMapper extends BaseMapper {

    @Insert({
            "insert into  ${prefix}_yhglobal_received_prepaid_record (recordId, currencyCode, ",
            "amountBeforeTransaction, transactionAmount, ",
            "amountAfterTransaction, transferTime, ",
            "supplierId, supplierName, ",
            "projectId, projectName, ",
            "createdById, createdByName, ",
            "purchaseOrderNo, recordType, ",
            "createTime,sourceFlowNo)",
            "values (#{record.recordId,jdbcType=BIGINT}, #{record.currencyCode,jdbcType=VARCHAR}, ",
            "#{record.amountBeforeTransaction,jdbcType=BIGINT}, #{record.transactionAmount,jdbcType=BIGINT}, ",
            "#{record.amountAfterTransaction,jdbcType=BIGINT}, #{record.transferTime,jdbcType=TIMESTAMP}, ",
            "#{record.supplierId,jdbcType=BIGINT}, #{record.supplierName,jdbcType=VARCHAR}, ",
            "#{record.projectId,jdbcType=BIGINT}, #{record.projectName,jdbcType=VARCHAR}, ",
            "#{record.createdById,jdbcType=BIGINT}, #{record.createdByName,jdbcType=VARCHAR}, ",
            "#{record.purchaseOrderNo,jdbcType=VARCHAR}, #{record.recordType,jdbcType=INTEGER}, ",
            "#{record.createTime,jdbcType=TIMESTAMP}, #{record.sourceFlowNo})"
    })
    int insert(@Param("prefix") String prefix,
               @Param("record") YhglobalReceivedPrepaidRecord record);

    @Update({
            "update  ${prefix}_yhglobal_received_prepaid_record",
            "set currencyCode = #{record.currencyCode,jdbcType=VARCHAR},",
            "amountBeforeTransaction = #{record.amountBeforeTransaction,jdbcType=BIGINT},",
            "transactionAmount = #{record.transactionAmount,jdbcType=BIGINT},",
            "amountAfterTransaction = #{record.amountAfterTransaction,jdbcType=BIGINT},",
            "transferTime = #{record.transferTime,jdbcType=TIMESTAMP},",
            "supplierId = #{record.supplierId,jdbcType=BIGINT},",
            "supplierName = #{record.supplierName,jdbcType=VARCHAR},",
            "projectId = #{record.projectId,jdbcType=BIGINT},",
            "projectName = #{record.projectName,jdbcType=VARCHAR},",
            "createdById = #{record.createdById,jdbcType=BIGINT},",
            "createdByName = #{record.createdByName,jdbcType=VARCHAR},",
            "purchaseOrderNo = #{record.purchaseOrderNo,jdbcType=VARCHAR}",
            "where recordId = #{record.recordId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(@Param("prefix") String prefix,
                           @Param("record")     YhglobalReceivedPrepaidRecord record);

    @SelectProvider(type = YhglobalReceivedPrepaidRecordSqlProvider.class, method = "selectPrepaidReceivedRecords")
    List<YhglobalReceivedPrepaidRecord> selectPrepaidReceivedRecords(@Param("prefix") String prefix,
                                                                     @Param("currencyCode") String currencyCode,
                                                                     @Param("projectId") long projectId,
                                                                     @Param("moneyFlow") Integer moneyFlow,
                                                                     @Param("beginDate") String beginDate,
                                                                     @Param("endDate") String endDate);

    @SelectProvider(type = YhglobalReceivedPrepaidRecordSqlProvider.class, method = "selectIncomeAndExpenditure")
    List<FlowSubtotal> selectIncomeAndExpenditure(@Param("prefix") String prefix,
                                                  @Param("currencyCode") String currencyCode,
                                                  @Param("projectId") long projectId,
                                                  @Param("moneyFlow") Integer moneyFlow,
                                                  @Param("beginDate") String beginDate,
                                                  @Param("endDate") String endDate);
}
