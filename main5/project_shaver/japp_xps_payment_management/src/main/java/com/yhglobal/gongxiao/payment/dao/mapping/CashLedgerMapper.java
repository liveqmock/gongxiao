package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.CashLedger;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * 现金台账Mapper
 *
 * @Author: 葛灿
 */
public interface CashLedgerMapper extends BaseMapper {

    @Insert({
            "insert into ${prefix}_payment_cash_ledger (ledgerId, flowNo, ",
            "approvalStatus, distributorId, ",
            "distributorName, projectId, ",
            "projectName, confirmCurrency, ",
            "confirmAmount, recipientCurrency, ",
            "recipientAmount, confirmTime, ",
            "approvalUserId, approvalUserName, ",
            "recipient, receiveTime, ",
            "createTime, dataVersion, ",
            "lastUpdateTime, bankName, ",
            "approveTime,canceled, tracelog, bankFlowNo,",
            "clientName, remark, createdById, createdByName)",
            "values (#{record.ledgerId}, #{record.flowNo}, ",
            "#{record.approvalStatus}, #{record.distributorId}, ",
            "#{record.distributorName}, #{record.projectId}, ",
            "#{record.projectName}, #{record.confirmCurrency}, ",
            "#{record.confirmAmount}, #{record.recipientCurrency}, ",
            "#{record.recipientAmount}, #{record.confirmTime}, ",
            "#{record.approvalUserId}, #{record.approvalUserName}, ",
            "#{record.recipient}, #{record.receiveTime}, ",
            "#{record.createTime}, #{record.dataVersion}, ",
            "#{record.lastUpdateTime}, #{record.bankName}, ",
            "#{record.approveTime}, #{record.canceled},",
            "#{record.tracelog}, #{record.bankFlowNo},",
            "#{record.clientName}, #{record.remark},",
            "#{record.createdById}, #{record.createdByName})"
    })
    int insert(@Param("prefix") String prefix,
               @Param("record") CashLedger record);

    @Select({
            "select",
            "canceled,ledgerId, flowNo, approvalStatus, distributorId, distributorName, projectId, ",
            "projectName, confirmCurrency, confirmAmount, recipientCurrency, recipientAmount, ",
            "confirmTime, approvalUserId, approvalUserName, recipient, receiveTime, createTime, ",
            "dataVersion, lastUpdateTime, bankName, approveTime,tracelog, bankFlowNo, clientName, remark",
            "from ${prefix}_payment_cash_ledger",
            "where flowNo = #{flowNo}"
    })
    CashLedger getByFlowNo(@Param("prefix") String prefix,
                           @Param("flowNo") String flowNo);

    /**
     * 作废台账
     *
     * @param prefix      表前缀
     * @param ledgerId    主键id
     * @param dataVersion 数据版本
     * @param canceled    是否作废
     * @return
     */
    @Update({
            "UPDATE ${prefix}_payment_cash_ledger",
            "SET",
            "canceled = #{canceled},",
            "dataVersion = dataVersion + 1 ",
            "where ledgerId = #{ledgerId} AND dataVersion = #{dataVersion}"
    })
    int updateWhenCancelConfirm(@Param("prefix") String prefix,
                                @Param("ledgerId") Long ledgerId,
                                @Param("dataVersion") Long dataVersion,
                                @Param("canceled") boolean canceled);

    /**
     * 修改审批相关信息
     *
     * @param prefix           表前缀
     * @param ledgerId         主键id
     * @param dataVersion      数据版本
     * @param approvalStatus   是否审批
     * @param approveTime      审批时间
     * @param approvalUserId   审批人用户id
     * @param approvalUserName 审批人用户名
     * @return 更新成功记录数
     */
    @Update({
            "UPDATE ${prefix}_payment_cash_ledger",
            "SET",
            "approvalStatus = #{approvalStatus},",
            "approveTime = #{approveTime},",
            "approvalUserId = #{approvalUserId},",
            "approvalUserName = #{approvalUserName},",
            "dataVersion = dataVersion + 1 ",
            "where ledgerId = #{ledgerId} AND dataVersion = #{dataVersion}"
    })
    int updateWhenApprove(@Param("prefix") String prefix,
                          @Param("ledgerId") Long ledgerId,
                          @Param("dataVersion") Long dataVersion,
                          @Param("approvalStatus") boolean approvalStatus,
                          @Param("approveTime") Date approveTime,
                          @Param("approvalUserId") Long approvalUserId,
                          @Param("approvalUserName") String approvalUserName);


    @SelectProvider(type = CashLedgerSqlProvider.class, method = "selectCashLedgerList")
    List<CashLedger> selectCashLedgerList(@Param("prefix") String prefix,
                                          @Param("projectId") Long projectId,
                                          @Param("bankName") String bankName,
                                          @Param("flowNo") String flowNo,
                                          @Param("confirmBegin") String confirmBegin,
                                          @Param("confirmEnd") String confirmEnd,
                                          @Param("receiveBegin") String receiveBegin,
                                          @Param("receiveEnd") String receiveEnd,
                                          @Param("approveStatus") String approveStatus,
                                          @Param("clientName") String clientName);
}