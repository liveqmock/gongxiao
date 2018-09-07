package com.yhglobal.gongxiao.coupon.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;

import com.yhglobal.gongxiao.coupon.dao.provider.YhglobalPrepaidProvider;
import com.yhglobal.gongxiao.coupon.model.YhglobalToReceivePrepaidCount;
import com.yhglobal.gongxiao.coupon.model.YhglobalToReceivePrepaidLedger;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * @Description: 越海代垫应收
 * @author: LUOYI
 * @Date: Created in 9:52 2018/4/26
 */
public interface YhglobalToReceivePrepaidLedgerMapper extends BaseMapper {
    @Insert({
            "insert into ${tablePrefix}_yhglobal_to_receive_prepaid_ledger (receiveId, orderId, ",
            "salesContractNo,projectId,projectName, receiveAmount, ",
            "supplierId,supplierName,currencyCode, toBeConfirmAmount, ",
            "confirmAmount, receiptAmount, ",
            "dateInto, differenceAmountAdjustPattern, ",
            "confirmRecord,receiveStatus, dataVersion, ",
            "createdById, createdByName, ",
            "createTime,lastUpdateTime)",
            "values (#{receiveId}, #{orderId}, ",
            "#{salesContractNo},#{projectId},#{projectName}, #{receiveAmount}, ",
            "#{supplierId},#{supplierName},#{currencyCode}, #{toBeConfirmAmount}, ",
            "#{confirmAmount}, #{receiptAmount}, ",
            "#{dateInto}, #{differenceAmountAdjustPattern}, ",
            "#{confirmRecord},#{receiveStatus}, #{dataVersion}, ",
            "#{createdById}, #{createdByName}, ",
            "#{createTime},#{lastUpdateTime})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "receive.receiveId")
    int insert(YhglobalToReceivePrepaidLedger receive);
    @Select({
            "select",
            "receiveId, orderId,projectId,projectName,supplierId,supplierId, salesContractNo, receiveAmount, currencyCode, toBeConfirmAmount, ",
            "confirmAmount, receiptAmount, dateInto, differenceAmountAdjustPattern, confirmRecord, ",
            "receiveStatus,dataVersion, createTime, lastUpdateTime, createdById, createdByName",
            "from ${tablePrefix}_yhglobal_to_receive_prepaid_ledger",
            "where receiveId = #{receiveId,jdbcType=BIGINT}"
    })
    YhglobalToReceivePrepaidLedger selectById(@Param("tablePrefix")String tablePrefix,@Param("receiveId") Long receiveId);

    @Select({
            "select",
            "receiveId, orderId,projectId,projectName,supplierId,supplierId, salesContractNo, receiveAmount, currencyCode, toBeConfirmAmount, ",
            "confirmAmount, receiptAmount, dateInto, differenceAmountAdjustPattern, confirmRecord, ",
            "receiveStatus,dataVersion, createTime, lastUpdateTime, createdById, createdByName",
            "from ${tablePrefix}_yhglobal_to_receive_prepaid_ledger",
            "where orderId = #{orderId,jdbcType=VARCHAR}"
    })
    YhglobalToReceivePrepaidLedger selectByOrderId(@Param("tablePrefix")String tablePrefix,@Param("orderId") String orderId);


    @Update({
            "update ${tablePrefix}_yhglobal_to_receive_prepaid_ledger",
            "set toBeConfirmAmount = #{toBeConfirmAmount,jdbcType=BIGINT},",
            "confirmAmount = #{confirmAmount,jdbcType=BIGINT},",
            "receiptAmount = #{receiptAmount,jdbcType=BIGINT},",
            "receiveStatus = #{receiveStatus},",
            "dataVersion = #{dataVersion,jdbcType=BIGINT} + 1,",
            "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},traceLog = #{appendTraceLog}",
            "where receiveId = #{receiveId,jdbcType=BIGINT}"
    })
    int updatePrepaidLedgerForWriteoff(@Param("tablePrefix")String tablePrefix,@Param("receiveId") Long receiveId,
                                       @Param("confirmAmount") Long confirmAmount,
                                       @Param("receiptAmount") Long receiptAmount,@Param("toBeConfirmAmount") Long toBeConfirmAmount,
                                       @Param("receiveStatus") Integer receiveStatus,@Param("dataVersion")  Long dataVersion,
                                       @Param("appendTraceLog") String appendTraceLog, @Param("lastUpdateTime") Date lastUpdateTime);
    @Select({
            "select",
            "receiveId, orderId,projectId,projectName,supplierId,supplierId, salesContractNo, receiveAmount, currencyCode, toBeConfirmAmount, ",
            "confirmAmount, receiptAmount, dateInto, differenceAmountAdjustPattern, confirmRecord, ",
            "receiveStatus,dataVersion, createTime, lastUpdateTime, createdById, createdByName",
            "from ${tablePrefix}_yhglobal_to_receive_prepaid_ledger",
            "where projectId = #{projectId}"
    })
    List<YhglobalToReceivePrepaidLedger> selectByProjectId(@Param("tablePrefix")String tablePrefix,@Param("projectId") Long projectId);

    @SelectProvider(type = YhglobalPrepaidProvider.class, method = "selectByIdsStr")
    List<YhglobalToReceivePrepaidLedger> selectByIdsStr(@Param("tablePrefix")String tablePrefix,@Param("ids") String ids);

    @SelectProvider(type = YhglobalPrepaidProvider.class, method = "selectReceiveAndRecord")
    List<YhglobalToReceivePrepaidLedger> selectReceiveAndRecordByProjectId(@Param("tablePrefix")String tablePrefix,
                                                                           @Param("projectId") Long projectId,
                                                                           @Param("orderId") String orderId,
                                                                           @Param("flowCode") String flowCode,
                                                                           @Param("accountType") Integer accountType,
                                                                           @Param("dateStart") Date dateStart,
                                                                           @Param("dateEnd") Date dateEnd,
                                                                           @Param("dateStartConfirm") Date dateStartConfirm,
                                                                           @Param("dateEndConfirm") Date dateEndConfirm,
                                                                           @Param("receiveStatus") String receiveStatus);

    @SelectProvider(type = YhglobalPrepaidProvider.class, method = "selectReceiveAndRecordCount")
    List<YhglobalToReceivePrepaidCount> selectReceiveAndRecordCount(@Param("tablePrefix")String tablePrefix,
                                                                    @Param("projectId") Long projectId,
                                                                    @Param("receiveIds") String receiveIds);

    @SelectProvider(type = YhglobalPrepaidProvider.class, method = "getCountByTime")
    YhglobalToReceivePrepaidLedger getCountByTime(@Param("tablePrefix")String tablePrefix,
                                                                    @Param("projectId") Long projectId,
                                                               @Param("dateStart") Date dateStart,
                                                               @Param("dateEnd") Date dateEnd);
    @Update({
            "update ${tablePrefix}_yhglobal_to_receive_prepaid_ledger",
            "set orderId = #{orderId,jdbcType=VARCHAR},",
            "salesContractNo = #{salesContractNo,jdbcType=VARCHAR},",
            "receiveAmount = #{receiveAmount,jdbcType=BIGINT},",
            "projectId = #{projectId},",
            "projectName = #{projectName},",
            "supplierId = #{supplierId},",
            "supplierId = #{supplierId},",
            "currencyCode = #{currencyCode,jdbcType=VARCHAR},",
            "toBeConfirmAmount = #{toBeConfirmAmount,jdbcType=BIGINT},",
            "confirmAmount = #{confirmAmount,jdbcType=BIGINT},",
            "receiptAmount = #{receiptAmount,jdbcType=BIGINT},",
            "dateInto = #{dateInto,jdbcType=TIMESTAMP},",
            "differenceAmountAdjustPattern = #{differenceAmountAdjustPattern,jdbcType=VARCHAR},",
            "receiveStatus = #{receiveStatus},",
            "confirmRecord = #{confirmRecord,jdbcType=VARCHAR},",
            "dataVersion = #{dataVersion,jdbcType=BIGINT},",
            "createTime = #{createTime,jdbcType=TIMESTAMP},",
            "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
            "createdById = #{createdById,jdbcType=BIGINT},",
            "createdByName = #{createdByName,jdbcType=VARCHAR}",
            "where receiveId = #{receiveId,jdbcType=BIGINT}"
    })
    int updateById(@Param("tablePrefix")String tablePrefix,YhglobalToReceivePrepaidLedger record);

    @Update({
            "update ${tablePrefix}_yhglobal_to_receive_prepaid_ledger",
            "set confirmRecord = #{confirmRecord},",
            "receiveStatus = #{receiveStatus},",
            "toBeConfirmAmount = #{toBeConfirmAmount},",
            "confirmAmount = #{confirmAmount},",
            "receiptAmount = #{receiptAmount},",
            "dataVersion = #{dataVersion} + 1",
            "where receiveId = #{receiveId} and dataVersion = #{dataVersion}"
    })
    int updateForConfirm(@Param("tablePrefix")String tablePrefix,
                         @Param("receiveId") Long receiveId,
                         @Param("dataVersion") Long dataVersion,
                         @Param("confirmRecord") String confirmRecord,
                         @Param("receiveStatus") Integer receiveStatus,
                         @Param("toBeConfirmAmount") Long toBeConfirmAmount,
                         @Param("receiptAmount") Long receiptAmount,
                         @Param("confirmAmount") Long confirmAmount);
}
