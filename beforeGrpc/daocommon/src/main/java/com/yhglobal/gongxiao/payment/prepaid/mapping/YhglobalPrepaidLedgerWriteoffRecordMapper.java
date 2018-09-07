package com.yhglobal.gongxiao.payment.prepaid.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.prepaid.YhglobalPrepaidLedgerWriteoffRecord;
import com.yhglobal.gongxiao.payment.prepaid.provider.YhglobalPrepaidProvider;
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
            "insert into yhglobal_prepaid_ledger_writeoff_record (writeoffId, receiveId, ",
            "confirmAmount, receiptAmount,useDate,projectId,accountType, ",
            "currencyCode, flowCode,philipNo, ",
            "dataVersion, createdById, ",
            "createdByName, createTime, ",
            "lastUpdateTime)",
            "values (#{writeoffId}, #{receiveId}, ",
            "#{confirmAmount}, #{receiptAmount},#{useDate},#{projectId},#{accountType}, ",
            "#{currencyCode}, #{flowCode},#{philipNo}, ",
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
            "from yhglobal_prepaid_ledger_writeoff_record",
            "where isRollback = 0 and  writeoffId = #{writeoffId,jdbcType=BIGINT} "
    })
    YhglobalPrepaidLedgerWriteoffRecord selectById(Long writeoffId);
    @Select({
            "select",
            "writeoffId, receiveId, confirmAmount,accountType, receiptAmount, currencyCode, flowCode, ",
            "dataVersion, createTime, lastUpdateTime, createdById, createdByName",
            "from yhglobal_prepaid_ledger_writeoff_record",
            "where isRollback = 0 and  receiveId = #{receiveId,jdbcType=BIGINT}"
    })
    List<YhglobalPrepaidLedgerWriteoffRecord> selectByReceiveId(Long receiveId);
    @Select({
            "select",
            "writeoffId, receiveId, confirmAmount,accountType, receiptAmount, currencyCode, flowCode, ",
            "dataVersion, createTime, lastUpdateTime, createdById, createdByName",
            "from yhglobal_prepaid_ledger_writeoff_record",
            "where isRollback = 0 and  flowCode = #{flowCode}"
    })
    List<YhglobalPrepaidLedgerWriteoffRecord> selectByFlowCode(String flowCode);
    @Update({
            "update yhglobal_prepaid_ledger_writeoff_record",
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
    int updateById(YhglobalPrepaidLedgerWriteoffRecord record);

    @SelectProvider(type = YhglobalPrepaidProvider.class, method = "searchPrepaidConfirm")
    List<YhglobalPrepaidLedgerWriteoffRecord> searchPrepaidConfirm(@Param("projectId") Long projectId,
                                                                   @Param("flowCode") String flowCode,
                                                                   @Param("accountType") Integer accountType,
                                                                   @Param("useDateStart") Date useDateStart,
                                                                   @Param("useDateEnd") Date useDateEnd,
                                                                   @Param("dateStart") Date dateStart,
                                                                   @Param("dateEnd") Date dateEnd);
}
