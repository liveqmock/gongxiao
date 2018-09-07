package com.yhglobal.gongxiao.coupon.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;

import com.yhglobal.gongxiao.coupon.dao.provider.YhglobalPrepaidProvider;
import com.yhglobal.gongxiao.coupon.model.YhglobalPrepaidLedgerWriteoffRecord;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * @Description: 越海代垫确认
 * @author: LUOYI
 * @Date: Created in 9:52 2018/4/26
 */
public interface YhglobalPrepaidLedgerWriteoffRecordMapper extends BaseMapper {
    @Insert({
            "insert into ${tablePrefix}_yhglobal_prepaid_ledger_writeoff_record (writeoffId, receiveId, ",
            "confirmAmount, receiptAmount,useDate,projectId,accountType, ",
            "currencyCode, flowCode,philipNo, isRollback,",
            "dataVersion, createdById, ",
            "createdByName, createTime, ",
            "lastUpdateTime)",
            "values (#{writeoffId}, #{receiveId}, ",
            "#{confirmAmount}, #{receiptAmount},#{useDate},#{projectId},#{accountType}, ",
            "#{currencyCode}, #{flowCode},#{philipNo}, #{isRollback}, ",
            "#{dataVersion}, #{createdById}, ",
            "#{createdByName}, #{createTime}, ",
            "#{lastUpdateTime})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "record.writeoffId")
    int insert(YhglobalPrepaidLedgerWriteoffRecord record);

    @Select({
            "select",
            "writeoffId, receiveId, confirmAmount, receiptAmount, currencyCode, flowCode, ",
            "dataVersion, createTime, lastUpdateTime, createdById, createdByName",
            "from ${tablePrefix}_yhglobal_prepaid_ledger_writeoff_record",
            "where isRollback = 2 and  writeoffId = #{writeoffId,jdbcType=BIGINT} "
    })
    YhglobalPrepaidLedgerWriteoffRecord selectById(@Param("tablePrefix")String tablePrefix,@Param("writeoffId") Long writeoffId);

    @Select({
            "select",
            "writeoffId, receiveId, confirmAmount,accountType, receiptAmount, currencyCode, flowCode, ",
            "dataVersion, createTime, lastUpdateTime, createdById, createdByName",
            "from ${tablePrefix}_yhglobal_prepaid_ledger_writeoff_record",
            "where isRollback = 2 and  receiveId = #{receiveId,jdbcType=BIGINT}"
    })
    List<YhglobalPrepaidLedgerWriteoffRecord> selectByReceiveId(@Param("tablePrefix")String tablePrefix,@Param("receiveId") Long receiveId);

    @Select({
            "select",
            "writeoffId, receiveId, confirmAmount,accountType, receiptAmount, currencyCode, flowCode, ",
            "dataVersion, createTime, lastUpdateTime, createdById, createdByName",
            "from ${tablePrefix}_yhglobal_prepaid_ledger_writeoff_record",
            "where isRollback = 2 and  flowCode = #{flowCode}"
    })
    List<YhglobalPrepaidLedgerWriteoffRecord> selectByFlowCode(@Param("tablePrefix")String tablePrefix,@Param("flowCode") String flowCode);

    @Update({
            "update ${tablePrefix}_yhglobal_prepaid_ledger_writeoff_record",
            "set receiveId = #{receiveId,jdbcType=BIGINT},",
            "confirmAmount = #{confirmAmount,jdbcType=BIGINT},",
            "receiptAmount = #{receiptAmount,jdbcType=BIGINT},",
            "currencyCode = #{currencyCode,jdbcType=VARCHAR},",
            "flowCode = #{flowCode,jdbcType=VARCHAR},",
            "isRollback = #{isRollback},",
            "dataVersion = #{dataVersion,jdbcType=BIGINT},",
            "createTime = #{createTime,jdbcType=TIMESTAMP},",
            "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
            "createdById = #{createdById,jdbcType=BIGINT},",
            "createdByName = #{createdByName,jdbcType=VARCHAR}",
            "where writeoffId = #{writeoffId,jdbcType=BIGINT}"
    })
    int updateById(@Param("tablePrefix")String tablePrefix,YhglobalPrepaidLedgerWriteoffRecord record);

    @Update({
            "update ${tablePrefix}_yhglobal_prepaid_ledger_writeoff_record",
            "set isRollback = #{isRollback},",
            "dataVersion = #{dataVersion,jdbcType=BIGINT} + 1,",
            "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP} ",
            "where writeoffId = #{writeoffId,jdbcType=BIGINT}"
    })
    int updateIsRollback(@Param("tablePrefix")String tablePrefix,
                         @Param("writeoffId") Long writeoffId,
                         @Param("isRollback") Integer isRollback,
                         @Param("dataVersion")Long dataVersion,
                         @Param("lastUpdateTime")Date lastUpdateTime);

    @SelectProvider(type = YhglobalPrepaidProvider.class, method = "searchPrepaidConfirm")
    List<YhglobalPrepaidLedgerWriteoffRecord> searchPrepaidConfirm(@Param("tablePrefix")String tablePrefix,
                                                                   @Param("projectId") Long projectId,
                                                                   @Param("flowCode") String flowCode,
                                                                   @Param("accountType") Integer accountType,
                                                                   @Param("useDateStart") Date useDateStart,
                                                                   @Param("useDateEnd") Date useDateEnd,
                                                                   @Param("dateStart") Date dateStart,
                                                                   @Param("dateEnd") Date dateEnd);
}
