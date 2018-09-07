package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.CashConfirm;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface CashConfirmMapper extends BaseMapper {

    /**
     * 插入收款确认信息
     *
     * @param record 模型
     * @return
     */
    @Insert({
            "insert into ${prefix}_payment_cash_confirm (confirmId, salesOrderNo, ",
            "flowNo, recipientStatus, ",
            "confirmStatus, distributorId, ",
            "distributorName, projectId, ",
            "projectName, shouldReceiveCurrency, ",
            "shouldReceiveAmount, unreceiveCurrency, ",
            "unreceiveAmount, confirmCurrency, ",
            "confirmAmount, recipientCurrency, ",
            "recipientAmount, confirmTime, ",
            "receiveTime, recipient, ",
            "payer, orderTime, ",
            "createTime, dataVersion, ",
            "lastUpdateTime, ",
            "tracelog,bankName,canceled,",
            "clientName, remark)",
            "values (#{record.confirmId}, #{record.salesOrderNo}, ",
            "#{record.flowNo}, #{record.recipientStatus}, ",
            "#{record.confirmStatus}, #{record.distributorId}, ",
            "#{record.distributorName}, #{record.projectId}, ",
            "#{record.projectName}, #{record.shouldReceiveCurrency}, ",
            "#{record.shouldReceiveAmount}, #{record.unreceiveCurrency}, ",
            "#{record.unreceiveAmount}, #{record.confirmCurrency}, ",
            "#{record.confirmAmount}, #{record.recipientCurrency}, ",
            "#{record.recipientAmount}, #{record.confirmTime}, ",
            "#{record.receiveTime}, #{record.recipient}, ",
            "#{record.payer}, #{record.orderTime}, ",
            "#{record.createTime}, #{record.dataVersion}, ",
            "#{record.lastUpdateTime}, ",
            "#{record.tracelog},#{record.bankName}, #{record.canceled},",
            "#{record.clientName}, #{record.remark} )"
    })
    int insert(@Param("prefix") String prefix,
               @Param("record") CashConfirm record);

    @Select({
            "select",
            "confirmId, salesOrderNo, flowNo, recipientStatus, confirmStatus, distributorId, ",
            "distributorName, projectId, projectName, shouldReceiveCurrency, shouldReceiveAmount, ",
            "unreceiveCurrency, unreceiveAmount, confirmCurrency, confirmAmount, recipientCurrency, ",
            "recipientAmount, confirmTime, receiveTime, recipient, payer, orderTime,bankName, ",
            "createTime, dataVersion, lastUpdateTime,  tracelog, bankFlowNo, clientName, remark",
            "from ${prefix}_payment_cash_confirm",
            "where salesOrderNo = #{salesOrderNo} and confirmTime is NULL and canceled = 0"
    })
    CashConfirm getConfirmOrder(@Param("prefix") String prefix,
                                @Param("salesOrderNo") String salesOrderNo);

    @Update({
            "update ${prefix}_payment_cash_confirm",
            "set salesOrderNo = #{record.salesOrderNo},",
            "flowNo = #{record.flowNo},",
            "recipientStatus = #{record.recipientStatus},",
            "confirmStatus = #{record.confirmStatus},",
            "distributorId = #{record.distributorId},",
            "distributorName = #{record.distributorName},",
            "projectId = #{record.projectId},",
            "projectName = #{record.projectName},",
            "shouldReceiveCurrency = #{record.shouldReceiveCurrency},",
            "shouldReceiveAmount = #{record.shouldReceiveAmount},",
            "unreceiveCurrency = #{record.unreceiveCurrency},",
            "unreceiveAmount = #{record.unreceiveAmount},",
            "confirmCurrency = #{record.confirmCurrency},",
            "confirmAmount = #{record.confirmAmount},",
            "recipientCurrency = #{record.recipientCurrency},",
            "recipientAmount = #{record.recipientAmount},",
            "confirmTime = #{record.confirmTime},",
            "receiveTime = #{record.receiveTime},",
            "recipient = #{record.recipient},",
            "payer = #{record.payer},",
            "orderTime = #{record.orderTime},",
            "createTime = #{record.createTime},",
            "dataVersion = dataVersion+1,",
            "tracelog = #{record.tracelog},",
            "bankName = #{record.bankName},",
            "bankFlowNo = #{record.bankFlowNo},",
            "canceled = #{record.canceled}",
            "where confirmId = #{record.confirmId} AND dataVersion = #{record.dataVersion}"
    })
    int updateByPrimaryKeyWithBLOBs(@Param("prefix") String prefix,
                                    @Param("record") CashConfirm record);


    /**
     * 选择性查询(均为可选条件)
     *
     * @param salesOrderNo    销售单号
     * @param projectId       项目id
     * @param distributorName ad名称
     * @param bankName        收款银行
     * @param confirmBegin    确认时间开始
     * @param confirmEnd      确认时间截止
     * @param orderBegin      订单时间起始
     * @param orderEnd        订单时间截止
     * @param orderStatus     订单状态
     * @return
     */
    @SelectProvider(type = CashConfirmSqlProvider.class, method = "selectListSelectively")
    List<CashConfirm> selectListSelectively(@Param("prefix") String prefix,
                                            @Param("salesOrderNo") String salesOrderNo,
                                            @Param("projectId") Long projectId,
                                            @Param("distributorName") String distributorName,
                                            @Param("bankName") String bankName,
                                            @Param("orderBegin") String orderBegin,
                                            @Param("orderEnd") String orderEnd,
                                            @Param("confirmBegin") String confirmBegin,
                                            @Param("confirmEnd") String confirmEnd,
                                            @Param("orderStatus") String orderStatus);

    @Update({
            "update ${prefix}_payment_cash_confirm",
            "set ",
            "confirmStatus = #{confirmStatus},",
            "unreceiveAmount = #{unreceiveAmount}",
            "where salesOrderNo = #{salesOrderNo} and canceled = 0"
    })
    int updateBySalesOrder(@Param("prefix") String prefix,
                           @Param("salesOrderNo") String salesOrderNo,
                           @Param("confirmStatus") int confirmStatus,
                           @Param("unreceiveAmount") long unreceiveAmount);

    @Select({
            "select",
            "confirmId, salesOrderNo, flowNo, recipientStatus, confirmStatus, distributorId, ",
            "distributorName, projectId, projectName, shouldReceiveCurrency, shouldReceiveAmount, ",
            "unreceiveCurrency, unreceiveAmount, confirmCurrency, confirmAmount, recipientCurrency, ",
            "recipientAmount, confirmTime, receiveTime, recipient, payer, orderTime,bankName, ",
            "createTime, dataVersion, lastUpdateTime,  tracelog, bankFlowNo, clientName, remark",
            "from ${prefix}_payment_cash_confirm",
            "where flowNo = #{flowNo} and canceled = 0"
    })
    List<CashConfirm> selectConfirmListByFlowNo(@Param("prefix") String prefix,
                                                @Param("flowNo") String flowNo);

    @Update({
            "update ${prefix}_payment_cash_confirm",
            "set ",
            "canceled = 1,",
            "dataVersion = dataVersion+1,",
            "tracelog = #{record.tracelog}",
            "where confirmId = #{record.confirmId} AND dataVersion = #{record.dataVersion}"
    })
    int cancelComfirmOrder(@Param("prefix") String prefix,
                           @Param("record") CashConfirm cashConfirm);

    @Select({
            "select count(1)",
            "from ${prefix}_payment_cash_confirm",
            "where salesOrderNo = #{salesOrderNo} and canceled = 0"
    })
    int getUncanceledCountBySalesOrderNo(@Param("prefix") String prefix,
                                         @Param("salesOrderNo") String salesOrderNo);

    @Select({
            "select count(1)",
            "from ${prefix}_payment_cash_confirm",
            "where salesOrderNo = #{salesOrderNo} and canceled = 0 and receiveTime is null"
    })
    int getUnreceivedCount(@Param("prefix") String prefix,
                           @Param("salesOrderNo") String salesOrderNo);

    @Select({
            "select",
            "confirmId, salesOrderNo, flowNo, recipientStatus, confirmStatus, distributorId, ",
            "distributorName, projectId, projectName, shouldReceiveCurrency, shouldReceiveAmount, ",
            "unreceiveCurrency, unreceiveAmount, confirmCurrency, confirmAmount, recipientCurrency, ",
            "recipientAmount, confirmTime, receiveTime, recipient, payer, orderTime,bankName, ",
            "createTime, dataVersion, lastUpdateTime,  tracelog, bankFlowNo, clientName, remark",
            "from ${prefix}_payment_cash_confirm",
            "where salesOrderNo = #{salesOrderNo} and canceled = 0"
    })
    List<CashConfirm> selectListBySalesOrderNo(@Param("prefix") String prefix,
                                               @Param("salesOrderNo") String salesOrderNo);

    @Select({
            "select",
            "count(1)",
            "from ${prefix}_payment_cash_confirm",
            "where bankFlowNo = #{bankFlowNo} and canceled = 0"
    })
    int selectCountByBankFlowNo(@Param("prefix") String prefix,
                                @Param("bankFlowNo") String bankFlowNo);

    @Select({
            "select",
            "confirmId, salesOrderNo, flowNo, recipientStatus, confirmStatus, distributorId, ",
            "distributorName, projectId, projectName, shouldReceiveCurrency, shouldReceiveAmount, ",
            "unreceiveCurrency, unreceiveAmount, confirmCurrency, confirmAmount, recipientCurrency, ",
            "recipientAmount, confirmTime, receiveTime, recipient, payer, orderTime,bankName, ",
            "createTime, dataVersion, lastUpdateTime,  tracelog, bankFlowNo, clientName, remark",
            "from ${prefix}_payment_cash_confirm",
            "where confirmId = #{confirmId} "
    })
    CashConfirm getById(@Param("prefix") String prefix,
                        @Param("confirmId") long confirmId);

    /**
     * 收款确认时更新
     *
     * @param prefix          表前缀
     * @param confirmId       主键id
     * @param dataVersion     数据版本
     * @param confirmStatus   确认状态
     * @param confirmAmount   确认金额
     * @param recipientAmount 实收金额
     * @param unreceiveAmount 未收金额
     * @param bankName        收款银行
     * @param flowNo          流水号
     * @param confirmTime     确认时间
     * @param bankFlowNo      银行流水号
     * @param receiveTime     收款时间
     * @param clientName      客户
     * @param remark          备注
     * @param tracelog        操作日志
     * @return 更新条数
     */
    @Update({
            "UPDATE ${prefix}_payment_cash_confirm",
            "SET",
            "confirmStatus = #{confirmStatus},",
            "confirmAmount = #{confirmAmount},",
            "recipientAmount = #{recipientAmount},",
            "unreceiveAmount = #{unreceiveAmount},",
            "bankName = #{bankName},",
            "flowNo = #{flowNo},",
            "confirmTime = #{confirmTime},",
            "bankFlowNo = #{bankFlowNo},",
            "receiveTime = #{receiveTime},",
            "clientName = #{clientName},",
            "remark = #{remark},",
            "tracelog = #{tracelog},",
            "dataVersion = dataVersion +1",
            "WHERE",
            "confirmId = #{confirmId} AND dataVersion = #{dataVersion}"
    })
    int updateWhenConfirm(@Param("prefix") String prefix,
                          @Param("confirmId") long confirmId, @Param("dataVersion") long dataVersion,
                          @Param("confirmStatus") int confirmStatus, @Param("confirmAmount") long confirmAmount,
                          @Param("recipientAmount") long recipientAmount, @Param("unreceiveAmount") long unreceiveAmount,
                          @Param("bankName") String bankName, @Param("flowNo") String flowNo,
                          @Param("confirmTime") Date confirmTime, @Param("bankFlowNo") String bankFlowNo,
                          @Param("receiveTime") Date receiveTime, @Param("clientName") String clientName,
                          @Param("remark") String remark, @Param("tracelog") String tracelog);

    /**
     * 审批通过时更新
     *
     * @param prefix          表前缀
     * @param confirmId       主键id
     * @param dataVersion     数据版本
     * @param recipientStatus 收款状态
     * @param tracelog        操作日志
     * @return
     */
    @Update({
            "UPDATE ${prefix}_payment_cash_confirm",
            "SET",
            "recipientStatus = #{recipientStatus},",
            "tracelog = #{tracelog},",
            "dataVersion = dataVersion +1",
            "WHERE",
            "confirmId = #{confirmId} AND dataVersion = #{dataVersion}"
    })
    int updateWhenApprove(@Param("prefix") String prefix,
                          @Param("confirmId") long confirmId, @Param("dataVersion") long dataVersion,
                          @Param("recipientStatus") boolean recipientStatus, @Param("tracelog") String tracelog);


    /**
     * 系统自动确认  未确认的金额
     *
     * @param prefix           表前缀
     * @param confirmId        主键id
     * @param dataVersion      数据版本
     * @param confirmStatus    确认状态
     * @param unreceivedAmount 未收金额
     * @param tracelog         操作日志
     * @return 更新成功条数
     */
    @Update({
            "UPDATE ${prefix}_payment_cash_confirm",
            "SET",
            "confirmStatus = #{confirmStatus},",
            "unreceivedAmount = #{unreceivedAmount},",
            "tracelog = #{tracelog},",
            "dataVersion = dataVersion +1",
            "WHERE",
            "confirmId = #{confirmId} AND dataVersion = #{dataVersion}"
    })
    int updateWhenSystemConfirm(@Param("prefix") String prefix,
                                @Param("confirmId") long confirmId, @Param("dataVersion") long dataVersion,
                                @Param("confirmStatus") int confirmStatus, @Param("unreceivedAmount") long unreceivedAmount,
                                @Param("tracelog") String tracelog);
}