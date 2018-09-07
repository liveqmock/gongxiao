package com.yhglobal.gongxiao.grossProfit.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.grossProfit.model.YhglobalToReceiveGrossProfitLedgerWriteoffRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

public interface YhglobalToReceiveGrossProfitLedgerWriteoffRecordMapper extends BaseMapper{
    @Delete({
        "delete from yhglobal_to_receive_gross_profit_ledger_writeoff_record",
        "where confirmId = #{confirmId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long confirmId);

    @Insert({
        "insert into ${tablePrefix}_yhglobal_to_receive_gross_profit_ledger_writeoff_record (confirmId, grossProfitId, ",
        "confirmAmount, receiptAmount, ",
        "flowNo, dataVersion, ",
        "createTime, lastUpdateTime, ",
        "receivedCurrencyCode, confirmCurrencyCode, ",
        "createdById, createdByName, ",
        "isRollback, projectId, ",
        "useDate, transferIntoPattern, ",
        "differenceAmountAdjustPattern, remark, differenceAmount)",
        "values (#{confirmId,jdbcType=BIGINT}, #{grossProfitId,jdbcType=BIGINT}, ",
        "#{confirmAmount,jdbcType=DECIMAL}, #{receiptAmount,jdbcType=DECIMAL}, ",
        "#{flowNo,jdbcType=VARCHAR}, #{dataVersion,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP}, ",
        "#{receivedCurrencyCode,jdbcType=VARCHAR}, #{confirmCurrencyCode,jdbcType=VARCHAR}, ",
        "#{createdById,jdbcType=BIGINT}, #{createdByName,jdbcType=VARCHAR}, ",
        "#{isRollback,jdbcType=INTEGER}, #{projectId,jdbcType=BIGINT}, ",
        "#{useDate,jdbcType=TIMESTAMP}, #{transferIntoPattern,jdbcType=TINYINT}, ",
        "#{differenceAmountAdjustPattern,jdbcType=TINYINT}, #{remark,jdbcType=LONGVARCHAR}, #{differenceAmount,jdbcType=DECIMAL })"
    })
    int insert(YhglobalToReceiveGrossProfitLedgerWriteoffRecord record);



    @Select({
        "select",
        "confirmId, grossProfitId, confirmAmount, receiptAmount, flowNo, dataVersion, ",
        "createTime, lastUpdateTime, receivedCurrencyCode, confirmCurrencyCode, createdById, ",
        "createdByName, isRollback, projectId, useDate, transferIntoPattern, differenceAmountAdjustPattern, ",
        "remark",
        "from yhglobal_to_receive_gross_profit_ledger_writeoff_record",
        "where confirmId = #{confirmId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="confirmId", property="confirmId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="grossProfitId", property="grossProfitId", jdbcType=JdbcType.BIGINT),
        @Result(column="confirmAmount", property="confirmAmount", jdbcType=JdbcType.DECIMAL),
        @Result(column="receiptAmount", property="receiptAmount", jdbcType=JdbcType.DECIMAL),
        @Result(column="flowNo", property="flowNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="dataVersion", property="dataVersion", jdbcType=JdbcType.BIGINT),
        @Result(column="createTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="lastUpdateTime", property="lastUpdateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="receivedCurrencyCode", property="receivedCurrencyCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="confirmCurrencyCode", property="confirmCurrencyCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="createdById", property="createdById", jdbcType=JdbcType.BIGINT),
        @Result(column="createdByName", property="createdByName", jdbcType=JdbcType.VARCHAR),
        @Result(column="isRollback", property="isRollback", jdbcType=JdbcType.INTEGER),
        @Result(column="projectId", property="projectId", jdbcType=JdbcType.BIGINT),
        @Result(column="useDate", property="useDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="transferIntoPattern", property="transferIntoPattern", jdbcType=JdbcType.TINYINT),
        @Result(column="differenceAmountAdjustPattern", property="differenceAmountAdjustPattern", jdbcType=JdbcType.TINYINT),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    YhglobalToReceiveGrossProfitLedgerWriteoffRecord selectByPrimaryKey(Long confirmId);



    @Update({
        "update yhglobal_to_receive_gross_profit_ledger_writeoff_record",
        "set grossProfitId = #{grossProfitId,jdbcType=BIGINT},",
          "confirmAmount = #{confirmAmount,jdbcType=DECIMAL},",
          "receiptAmount = #{receiptAmount,jdbcType=DECIMAL},",
          "flowNo = #{flowNo,jdbcType=VARCHAR},",
          "dataVersion = #{dataVersion,jdbcType=BIGINT},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "receivedCurrencyCode = #{receivedCurrencyCode,jdbcType=VARCHAR},",
          "confirmCurrencyCode = #{confirmCurrencyCode,jdbcType=VARCHAR},",
          "createdById = #{createdById,jdbcType=BIGINT},",
          "createdByName = #{createdByName,jdbcType=VARCHAR},",
          "isRollback = #{isRollback,jdbcType=INTEGER},",
          "projectId = #{projectId,jdbcType=BIGINT},",
          "useDate = #{useDate,jdbcType=TIMESTAMP},",
          "transferIntoPattern = #{transferIntoPattern,jdbcType=TINYINT},",
          "differenceAmountAdjustPattern = #{differenceAmountAdjustPattern,jdbcType=TINYINT},",
          "remark = #{remark,jdbcType=LONGVARCHAR}",
        "where confirmId = #{confirmId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(YhglobalToReceiveGrossProfitLedgerWriteoffRecord record);

    @Update({
        "update yhglobal_to_receive_gross_profit_ledger_writeoff_record",
        "set grossProfitId = #{grossProfitId,jdbcType=BIGINT},",
          "confirmAmount = #{confirmAmount,jdbcType=DECIMAL},",
          "receiptAmount = #{receiptAmount,jdbcType=DECIMAL},",
          "flowNo = #{flowNo,jdbcType=VARCHAR},",
          "dataVersion = #{dataVersion,jdbcType=BIGINT},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "receivedCurrencyCode = #{receivedCurrencyCode,jdbcType=VARCHAR},",
          "confirmCurrencyCode = #{confirmCurrencyCode,jdbcType=VARCHAR},",
          "createdById = #{createdById,jdbcType=BIGINT},",
          "createdByName = #{createdByName,jdbcType=VARCHAR},",
          "isRollback = #{isRollback,jdbcType=INTEGER},",
          "projectId = #{projectId,jdbcType=BIGINT},",
          "useDate = #{useDate,jdbcType=TIMESTAMP},",
          "transferIntoPattern = #{transferIntoPattern,jdbcType=TINYINT},",
          "differenceAmountAdjustPattern = #{differenceAmountAdjustPattern,jdbcType=TINYINT}",
        "where confirmId = #{confirmId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(YhglobalToReceiveGrossProfitLedgerWriteoffRecord record);


    @Update({
            "update ${tablePrefix}_yhglobal_to_receive_gross_profit_ledger_writeoff_record",
            "set isRollback = #{isRollback,jdbcType=INTEGER}",
            "where confirmId = #{confirmId,jdbcType=BIGINT}"})
    int updateRollbackStatus(@Param("tablePrefix")String tablePrefix, @Param("isRollback") int isRollback, @Param("confirmId") Long confirmId);

    @SelectProvider(type = YhglobalToReceiveGrossProfitLedgerWriteoffRecordSqlProvider.class, method = "selectGrossProfitBook")
    List<YhglobalToReceiveGrossProfitLedgerWriteoffRecord> selectGrossProfitBook(@Param("tablePrefix")String tablePrefix, @Param("projectId") Long projectId,
                                                           @Param("flowNo") String flowNo,
                                                           @Param("transferIntoPattern") Integer transferIntoPattern,
                                                           @Param("useDateStart") Date useDateStart,
                                                           @Param("useDateEnd") Date useDateEnd,
                                                           @Param("dateStart") Date dateStart,
                                                           @Param("dateEnd") Date dateEnd);

    @Select({
            "select",
            "confirmId, grossProfitId, confirmAmount, receiptAmount, flowNo, dataVersion, ",
            "createTime, lastUpdateTime, receivedCurrencyCode, confirmCurrencyCode, createdById, ",
            "createdByName, isRollback, projectId, useDate, transferIntoPattern, differenceAmountAdjustPattern, ",
            "remark, differenceAmount",
            "from ${tablePrefix}_yhglobal_to_receive_gross_profit_ledger_writeoff_record",
            "where flowNo = #{flowNo,jdbcType=BIGINT}"
    })
    List<YhglobalToReceiveGrossProfitLedgerWriteoffRecord> selectByFlowCode(@Param("tablePrefix")String tablePrefix,@Param("flowNo")String flowNo);
}