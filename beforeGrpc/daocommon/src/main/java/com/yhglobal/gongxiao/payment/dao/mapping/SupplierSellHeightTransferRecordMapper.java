package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.SupplierSellHeightTransferRecord;
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

public interface SupplierSellHeightTransferRecordMapper extends BaseMapper {
    @Delete({
            "delete from supplier_sell_height_transfer_record",
            "where recordId = #{recordId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long recordId);

    @Insert({
            "insert into supplier_sell_height_transfer_record (recordId, currencyCode, ",
            "amountBeforeTransaction, transactionAmount, ",
            "amountAfterTransaction, transferTime, ",
            "supplierId, supplierName, ",
            "createdById, createdByName, ",
            "projectId, projectName, ",
            "recordType, createTime,",
            "projectName,projectId)",
            "values (#{recordId,jdbcType=BIGINT}, #{currencyCode,jdbcType=VARCHAR}, ",
            "#{amountBeforeTransaction,jdbcType=BIGINT}, #{transactionAmount,jdbcType=BIGINT}, ",
            "#{amountAfterTransaction,jdbcType=BIGINT}, #{transferTime,jdbcType=TIMESTAMP}, ",
            "#{supplierId,jdbcType=BIGINT}, #{supplierName,jdbcType=VARCHAR}, ",
            "#{createdById,jdbcType=BIGINT}, #{createdByName,jdbcType=VARCHAR}, ",
            "#{projectId}, #{projectName}, ",
            "#{recordType,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}",
            "#{projectName}, #{projectId})"
    })
    int insert(SupplierSellHeightTransferRecord record);

    @Select({
            "select",
            "recordId, currencyCode, amountBeforeTransaction, transactionAmount, amountAfterTransaction, ",
            "transferTime, supplierId, supplierName, createdById, createdByName, recordType,projectId,projectName, ",
            "createTime",
            "from supplier_sell_height_transfer_record",
            "where recordId = #{recordId,jdbcType=BIGINT}"
    })
    SupplierSellHeightTransferRecord selectByPrimaryKey(Long recordId);

    @Update({
            "update supplier_sell_height_transfer_record",
            "set currencyCode = #{currencyCode,jdbcType=VARCHAR},",
            "amountBeforeTransaction = #{amountBeforeTransaction,jdbcType=BIGINT},",
            "transactionAmount = #{transactionAmount,jdbcType=BIGINT},",
            "amountAfterTransaction = #{amountAfterTransaction,jdbcType=BIGINT},",
            "transferTime = #{transferTime,jdbcType=TIMESTAMP},",
            "supplierId = #{supplierId,jdbcType=BIGINT},",
            "supplierName = #{supplierName,jdbcType=VARCHAR},",
            "createdById = #{createdById,jdbcType=BIGINT},",
            "createdByName = #{createdByName,jdbcType=VARCHAR}",
            "where recordId = #{recordId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(SupplierSellHeightTransferRecord record);

    @SelectProvider(type = SupplierSellHeightTransferRecordSqlProvider.class, method = "selectSupplierSellHighRecordList")
    List<SupplierSellHeightTransferRecord> selectSupplierSellHighRecordList(@Param("currencyCode") String currencyCode,
                                                                            @Param("supplierId") long supplierId,
                                                                            @Param("projectId") long projectId,
                                                                            @Param("moneyFlow") Integer moneyFlow,
                                                                            @Param("beginDate") Date beginDate,
                                                                            @Param("endDate") Date endDate);

    @SelectProvider(type = SupplierSellHeightTransferRecordSqlProvider.class, method = "selectIncomeAndExpenditure")
    List<FlowSubtotal> selectIncomeAndExpenditure(@Param("currencyCode") String currencyCode,
                                                  @Param("supplierId") long supplierId,
                                                  @Param("projectId") long projectId,
                                                  @Param("moneyFlow") Integer moneyFlow,
                                                  @Param("beginDate") Date beginDate,
                                                  @Param("endDate") Date endDate);

}