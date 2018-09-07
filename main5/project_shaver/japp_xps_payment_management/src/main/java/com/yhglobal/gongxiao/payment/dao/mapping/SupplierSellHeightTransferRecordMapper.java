package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.SupplierSellHeightTransferRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface SupplierSellHeightTransferRecordMapper extends BaseMapper {

    @Insert({
            "insert into ${prefix}_supplier_sell_height_transfer_record (recordId, currencyCode, ",
            "amountBeforeTransaction, transactionAmount, ",
            "amountAfterTransaction, transferTime, ",
            "supplierId, supplierName, ",
            "createdById, createdByName, ",
            "recordType, createTime,",
            "projectId, projectName)",
            "values (#{record.recordId,jdbcType=BIGINT}, #{record.currencyCode,jdbcType=VARCHAR}, ",
            "#{record.amountBeforeTransaction,jdbcType=BIGINT}, #{record.transactionAmount,jdbcType=BIGINT}, ",
            "#{record.amountAfterTransaction,jdbcType=BIGINT}, #{record.transferTime,jdbcType=TIMESTAMP}, ",
            "#{record.supplierId,jdbcType=BIGINT}, #{record.supplierName,jdbcType=VARCHAR}, ",
            "#{record.createdById,jdbcType=BIGINT}, #{record.createdByName,jdbcType=VARCHAR}, ",
            "#{record.recordType,jdbcType=INTEGER}, #{record.createTime,jdbcType=TIMESTAMP},",
            "#{record.projectId}, #{record.projectName})"
    })
    int insert(@Param("prefix") String prefix,
               @Param("record") SupplierSellHeightTransferRecord record);

    @Select({
            "select",
            "recordId, currencyCode, amountBeforeTransaction, transactionAmount, amountAfterTransaction, ",
            "transferTime, supplierId, supplierName, createdById, createdByName, recordType,projectId,projectName, ",
            "createTime",
            "from ${prefix}_supplier_sell_height_transfer_record",
            "where recordId = #{recordId,jdbcType=BIGINT}"
    })
    SupplierSellHeightTransferRecord selectByPrimaryKey(@Param("prefix") String prefix,
                                                        @Param("recordId") Long recordId);

    @Update({
            "update ${prefix}_supplier_sell_height_transfer_record",
            "set currencyCode = #{record.currencyCode,jdbcType=VARCHAR},",
            "amountBeforeTransaction = #{record.amountBeforeTransaction,jdbcType=BIGINT},",
            "transactionAmount = #{record.transactionAmount,jdbcType=BIGINT},",
            "amountAfterTransaction = #{record.amountAfterTransaction,jdbcType=BIGINT},",
            "transferTime = #{record.transferTime,jdbcType=TIMESTAMP},",
            "supplierId = #{record.supplierId,jdbcType=BIGINT},",
            "supplierName = #{record.supplierName,jdbcType=VARCHAR},",
            "createdById = #{record.createdById,jdbcType=BIGINT},",
            "createdByName = #{record.createdByName,jdbcType=VARCHAR}",
            "where recordId = #{record.recordId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(@Param("prefix") String prefix,
                           @Param("record") SupplierSellHeightTransferRecord record);

    @SelectProvider(type = SupplierSellHeightTransferRecordSqlProvider.class, method = "selectSupplierSellHighRecordList")
    List<SupplierSellHeightTransferRecord> selectSupplierSellHighRecordList(@Param("prefix") String prefix,
                                                                            @Param("currencyCode") String currencyCode,
                                                                            @Param("projectId") long projectId,
                                                                            @Param("moneyFlow") Integer moneyFlow,
                                                                            @Param("beginDate") String beginDate,
                                                                            @Param("endDate") String endDate);

    @SelectProvider(type = SupplierSellHeightTransferRecordSqlProvider.class, method = "selectIncomeAndExpenditure")
    List<FlowSubtotal> selectIncomeAndExpenditure(@Param("prefix") String prefix,
                                                  @Param("currencyCode") String currencyCode,
                                                  @Param("projectId") long projectId,
                                                  @Param("moneyFlow") Integer moneyFlow,
                                                  @Param("beginDate") String beginDate,
                                                  @Param("endDate") String endDate);

}