package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.CashLedger;
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

/**
 * 现金台账Mapper
 *
 * @Author: 葛灿
 */
public interface CashLedgerMapper extends BaseMapper {

    @Insert({
            "insert into payment_cash_ledger (ledgerId, flowNo, ",
            "approvalStatus, distributorId, ",
            "distributorName, projectId, ",
            "projectName, confirmCurrency, ",
            "confirmAmount, recipientCurrency, ",
            "recipientAmount, confirmTime, ",
            "approvalUserId, approvalUserName, ",
            "recipient, receiveTime, ",
            "createTime, dataVersion, ",
            "lastUpdateTime, bankName, ",
            "approveTime,canceled,tracelog, bankFlowNo)",
            "values (#{ledgerId,jdbcType=BIGINT}, #{flowNo,jdbcType=VARCHAR}, ",
            "#{approvalStatus,jdbcType=INTEGER}, #{distributorId,jdbcType=INTEGER}, ",
            "#{distributorName,jdbcType=VARCHAR}, #{projectId,jdbcType=INTEGER}, ",
            "#{projectName,jdbcType=VARCHAR}, #{confirmCurrency,jdbcType=VARCHAR}, ",
            "#{confirmAmount,jdbcType=BIGINT}, #{recipientCurrency,jdbcType=VARCHAR}, ",
            "#{recipientAmount,jdbcType=BIGINT}, #{confirmTime,jdbcType=TIMESTAMP}, ",
            "#{approvalUserId,jdbcType=BIGINT}, #{approvalUserName,jdbcType=VARCHAR}, ",
            "#{recipient,jdbcType=VARCHAR}, #{receiveTime,jdbcType=TIMESTAMP}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{dataVersion,jdbcType=BIGINT}, ",
            "#{lastUpdateTime,jdbcType=TIMESTAMP}, #{bankName,jdbcType=VARCHAR}, ",
            "#{approveTime,jdbcType=TIMESTAMP}, #{canceled} ,#{tracelog}, #{bankFlowNo})"
    })
    int insert(CashLedger record);

    @Select({
            "select",
            "ledgerId, flowNo, approvalStatus, distributorId, distributorName, projectId, ",
            "projectName, confirmCurrency, confirmAmount, recipientCurrency, recipientAmount, ",
            "confirmTime, approvalUserId, approvalUserName, recipient, receiveTime, createTime, ",
            "dataVersion, lastUpdateTime, bankName, approveTime,canceled,tracelog, bankFlowNo",
            "from payment_cash_ledger",
            "where ledgerId = #{ledgerId,jdbcType=BIGINT}"
    })
    CashLedger selectByPrimaryKey(Long ledgerId);

    @Update({
            "update payment_cash_ledger",
            "set flowNo = #{flowNo,jdbcType=VARCHAR},",
            "approvalStatus = #{approvalStatus,jdbcType=INTEGER},",
            "distributorId = #{distributorId,jdbcType=INTEGER},",
            "distributorName = #{distributorName,jdbcType=VARCHAR},",
            "projectId = #{projectId,jdbcType=INTEGER},",
            "projectName = #{projectName,jdbcType=VARCHAR},",
            "confirmCurrency = #{confirmCurrency,jdbcType=VARCHAR},",
            "confirmAmount = #{confirmAmount,jdbcType=BIGINT},",
            "recipientCurrency = #{recipientCurrency,jdbcType=VARCHAR},",
            "recipientAmount = #{recipientAmount,jdbcType=BIGINT},",
            "confirmTime = #{confirmTime,jdbcType=TIMESTAMP},",
            "approvalUserId = #{approvalUserId,jdbcType=BIGINT},",
            "approvalUserName = #{approvalUserName,jdbcType=VARCHAR},",
            "recipient = #{recipient,jdbcType=VARCHAR},",
            "receiveTime = #{receiveTime,jdbcType=TIMESTAMP},",
            "createTime = #{createTime,jdbcType=TIMESTAMP},",
            "dataVersion = dataVersion+1,",
            "bankName = #{bankName,jdbcType=VARCHAR},",
            "approveTime = #{approveTime,jdbcType=TIMESTAMP},",
            "tracelog = #{tracelog},",
            "canceled = #{canceled}",
            "where ledgerId = #{ledgerId,jdbcType=BIGINT} AND dataVersion = #{dataVersion}"
    })
    int updateByPrimaryKey(CashLedger record);

    @Select({
            "select",
            "canceled,ledgerId, flowNo, approvalStatus, distributorId, distributorName, projectId, ",
            "projectName, confirmCurrency, confirmAmount, recipientCurrency, recipientAmount, ",
            "confirmTime, approvalUserId, approvalUserName, recipient, receiveTime, createTime, ",
            "dataVersion, lastUpdateTime, bankName, approveTime,tracelog, bankFlowNo",
            "from payment_cash_ledger",
            "where flowNo = #{flowNo}"
    })
    CashLedger getByFlowNo(@Param("flowNo") String flowNo);


    @SelectProvider(type = CashLedgerSqlProvider.class, method = "selectCashLedgerList")
    List<CashLedger> selectCashLedgerList(@Param("projectId") Long projectId,
                                          @Param("bankName") String bankName,
                                          @Param("flowNo") String flowNo,
                                          @Param("confirmBegin") Date confirmBegin,
                                          @Param("confirmEnd") Date confirmEnd,
                                          @Param("receiveBegin") Date receiveBegin,
                                          @Param("receiveEnd") Date receiveEnd,
                                          @Param("approveStatus") Boolean approveStatus);
}