package com.yhglobal.gongxiao.grossProfit.dao.mapper;


import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.grossProfit.model.GrossProfitItem;
import com.yhglobal.gongxiao.grossProfit.model.YhglobalToReceiveGrossProfitLedger;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface YhglobalToReceiveGrossProfitLedgerMapper extends BaseMapper{
    @Delete({
        "delete from yhglobal_to_receive_gross_profit_ledger",
        "where grossProfitId = #{grossProfitId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long grossProfitId);

    @Insert({
        "insert into ${tablePrefix}_yhglobal_to_receive_gross_profit_ledger (grossProfitId, confirmStatus, ",
        "salesOrderNo, purchaseOrderNo, ",
        "createTime, estimatedGrossProfitAmount, ",
        "toBeConfirmAmount, projectId, ",
        "projectName, supplierId, ",
        "supplierName, currencyCode, ",
        "supplierOrderNo, salesTime, ",
        "confirmedAmount, receivedAmount, ",
        "dataVersion, lastUpdateTime, ",
        "confirmRecord, tracelog)",
        "values (#{grossProfitId,jdbcType=BIGINT}, #{confirmStatus,jdbcType=TINYINT}, ",
        "#{salesOrderNo,jdbcType=VARCHAR}, #{purchaseOrderNo,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{estimatedGrossProfitAmount,jdbcType=DECIMAL}, ",
        "#{toBeConfirmAmount,jdbcType=DECIMAL}, #{projectId,jdbcType=BIGINT}, ",
        "#{projectName,jdbcType=VARCHAR}, #{supplierId,jdbcType=BIGINT}, ",
        "#{supplierName,jdbcType=VARCHAR}, #{currencyCode,jdbcType=VARCHAR}, ",
        "#{supplierOrderNo,jdbcType=VARCHAR}, #{salesTime,jdbcType=TIMESTAMP}, ",
        "#{confirmedAmount,jdbcType=DECIMAL}, #{receivedAmount,jdbcType=DECIMAL}, ",
        "#{dataVersion,jdbcType=BIGINT}, #{lastUpdateTime,jdbcType=TIMESTAMP}, ",
        "#{confirmRecord,jdbcType=VARCHAR}, #{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(YhglobalToReceiveGrossProfitLedger record);



    @Select({
        "select",
        "grossProfitId, confirmStatus, salesOrderNo, purchaseOrderNo, createTime, estimatedGrossProfitAmount, ",
        "toBeConfirmAmount, projectId, projectName, supplierId, supplierName, currencyCode, ",
        "supplierOrderNo, salesTime, confirmedAmount, receivedAmount, dataVersion, lastUpdateTime, ",
        "confirmRecord, tracelog",
        "from ${tablePrefix}_yhglobal_to_receive_gross_profit_ledger",
        "where grossProfitId = #{grossProfitId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="grossProfitId", property="grossProfitId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="confirmStatus", property="confirmStatus", jdbcType=JdbcType.TINYINT),
        @Result(column="salesOrderNo", property="salesOrderNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="purchaseOrderNo", property="purchaseOrderNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="createTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="estimatedGrossProfitAmount", property="estimatedGrossProfitAmount", jdbcType=JdbcType.DECIMAL),
        @Result(column="toBeConfirmAmount", property="toBeConfirmAmount", jdbcType=JdbcType.DECIMAL),
        @Result(column="projectId", property="projectId", jdbcType=JdbcType.BIGINT),
        @Result(column="projectName", property="projectName", jdbcType=JdbcType.VARCHAR),
        @Result(column="supplierId", property="supplierId", jdbcType=JdbcType.BIGINT),
        @Result(column="supplierName", property="supplierName", jdbcType=JdbcType.VARCHAR),
        @Result(column="currencyCode", property="currencyCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="supplierOrderNo", property="supplierOrderNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="salesTime", property="salesTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="confirmedAmount", property="confirmedAmount", jdbcType=JdbcType.DECIMAL),
        @Result(column="receivedAmount", property="receivedAmount", jdbcType=JdbcType.DECIMAL),
        @Result(column="dataVersion", property="dataVersion", jdbcType=JdbcType.BIGINT),
        @Result(column="lastUpdateTime", property="lastUpdateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="confirmRecord", property="confirmRecord", jdbcType=JdbcType.VARCHAR),
        @Result(column="tracelog", property="tracelog", jdbcType=JdbcType.LONGVARCHAR)
    })
    YhglobalToReceiveGrossProfitLedger selectByPrimaryKey(@Param("tablePrefix") String tablePrefix, @Param("grossProfitId") Long grossProfitId);



    @Update({
        "update yhglobal_to_receive_gross_profit_ledger",
        "set confirmStatus = #{confirmStatus,jdbcType=TINYINT},",
          "salesOrderNo = #{salesOrderNo,jdbcType=VARCHAR},",
          "purchaseOrderNo = #{purchaseOrderNo,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "estimatedGrossProfitAmount = #{estimatedGrossProfitAmount,jdbcType=DECIMAL},",
          "toBeConfirmAmount = #{toBeConfirmAmount,jdbcType=DECIMAL},",
          "projectId = #{projectId,jdbcType=BIGINT},",
          "projectName = #{projectName,jdbcType=VARCHAR},",
          "supplierId = #{supplierId,jdbcType=BIGINT},",
          "supplierName = #{supplierName,jdbcType=VARCHAR},",
          "currencyCode = #{currencyCode,jdbcType=VARCHAR},",
          "supplierOrderNo = #{supplierOrderNo,jdbcType=VARCHAR},",
          "salesTime = #{salesTime,jdbcType=TIMESTAMP},",
          "confirmedAmount = #{confirmedAmount,jdbcType=DECIMAL},",
          "receivedAmount = #{receivedAmount,jdbcType=DECIMAL},",
          "dataVersion = #{dataVersion,jdbcType=BIGINT},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "confirmRecord = #{confirmRecord,jdbcType=VARCHAR},",
          "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
        "where grossProfitId = #{grossProfitId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(YhglobalToReceiveGrossProfitLedger record);

    @Update({
        "update yhglobal_to_receive_gross_profit_ledger",
        "set confirmStatus = #{confirmStatus,jdbcType=TINYINT},",
          "salesOrderNo = #{salesOrderNo,jdbcType=VARCHAR},",
          "purchaseOrderNo = #{purchaseOrderNo,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "estimatedGrossProfitAmount = #{estimatedGrossProfitAmount,jdbcType=DECIMAL},",
          "toBeConfirmAmount = #{toBeConfirmAmount,jdbcType=DECIMAL},",
          "projectId = #{projectId,jdbcType=BIGINT},",
          "projectName = #{projectName,jdbcType=VARCHAR},",
          "supplierId = #{supplierId,jdbcType=BIGINT},",
          "supplierName = #{supplierName,jdbcType=VARCHAR},",
          "currencyCode = #{currencyCode,jdbcType=VARCHAR},",
          "supplierOrderNo = #{supplierOrderNo,jdbcType=VARCHAR},",
          "salesTime = #{salesTime,jdbcType=TIMESTAMP},",
          "confirmedAmount = #{confirmedAmount,jdbcType=DECIMAL},",
          "receivedAmount = #{receivedAmount,jdbcType=DECIMAL},",
          "dataVersion = #{dataVersion,jdbcType=BIGINT},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "confirmRecord = #{confirmRecord,jdbcType=VARCHAR}",
        "where grossProfitId = #{grossProfitId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(YhglobalToReceiveGrossProfitLedger record);


    @Select({
            "select",
            "grossProfitId, estimatedGrossProfitAmount,currencyCode, confirmStatus,toBeConfirmAmount, confirmedAmount, receivedAmount, dataVersion, confirmRecord, tracelog",
            "from ${tablePrefix}_yhglobal_to_receive_gross_profit_ledger ",
            "where grossProfitId = #{grossProfitId,jdbcType=BIGINT}"
    })
    YhglobalToReceiveGrossProfitLedger getForWriteoff(@Param("tablePrefix") String tablePrefix,
                                                      @Param("grossProfitId")Long grossProfitId);
    @Update({
            "update ${tablePrefix}_yhglobal_to_receive_gross_profit_ledger",
            "set confirmStatus = #{confirmStatus,jdbcType=TINYINT},",
            "toBeConfirmAmount = #{toBeConfirmAmount,jdbcType=DECIMAL},",
            "confirmedAmount = #{confirmedAmount,jdbcType=DECIMAL},",
            "receivedAmount = #{receivedAmount,jdbcType=DECIMAL},",
            "dataVersion = #{dataVersion,jdbcType=BIGINT} + 1,",
            "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
            "confirmRecord = #{confirmRecord,jdbcType=VARCHAR},",
            "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
            "where grossProfitId = #{grossProfitId,jdbcType=BIGINT}"
    })
    int updateLedger(@Param("tablePrefix") String tablePrefix,
                     @Param("grossProfitId")Long grossProfitId,
                     @Param("confirmStatus") Byte confirmStatus,
                     @Param("toBeConfirmAmount")BigDecimal toBeConfirmAmount,
                     @Param("confirmedAmount")BigDecimal confirmedAmount,
                     @Param("receivedAmount")BigDecimal receivedAmount,
                     @Param("dataVersion")Long dataVersion,
                     @Param("tracelog")String tracelog,
                     @Param("lastUpdateTime")Date lastUpdateTime,
                     @Param("confirmRecord")String confirmRecord);

    @SelectProvider(type = YhglobalToReceiveGrossProfitLedgerSqlProvider.class, method = "selectByManyCondition")
    List<GrossProfitItem> selectByManyCondition(@Param("tablePrefix")String tablePrefix,
                                                @Param("projectId") Long projectId,
                                                @Param("purchaseOrderNo") String purchaseOrderNo,
                                                @Param("salesOrderNo") String salesOrderNo,
                                                @Param("flowNo") String flowNo,
                                                @Param("dateStart") Date dateS,
                                                @Param("dateEnd") Date dateE,
                                                @Param("confirmStatus") String confirmStatus);

    @SelectProvider(type = YhglobalToReceiveGrossProfitLedgerSqlProvider.class, method = "selectByIds")
    List<YhglobalToReceiveGrossProfitLedger> selectByIds(@Param("tablePrefix")String tablePrefix, @Param("ids") String ids);
}