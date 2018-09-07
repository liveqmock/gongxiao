package com.yhglobal.gongxiao.sales.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.CashConfirm;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface SalesCashConfirmMapper extends BaseMapper {

    /**
     * 插入收款确认信息
     *
     * @param record 模型
     * @return
     */
    @Insert({
            "insert into sales_cash_confirm (confirmId, salesOrderNo, ",
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
            "tracelog,bankName,canceled)",
            "values (#{confirmId,jdbcType=BIGINT}, #{salesOrderNo,jdbcType=VARCHAR}, ",
            "#{flowNo,jdbcType=VARCHAR}, #{recipientStatus,jdbcType=INTEGER}, ",
            "#{confirmStatus,jdbcType=INTEGER}, #{distributorId,jdbcType=INTEGER}, ",
            "#{distributorName,jdbcType=VARCHAR}, #{projectId,jdbcType=INTEGER}, ",
            "#{projectName,jdbcType=VARCHAR}, #{shouldReceiveCurrency,jdbcType=VARCHAR}, ",
            "#{shouldReceiveAmount,jdbcType=BIGINT}, #{unreceiveCurrency,jdbcType=VARCHAR}, ",
            "#{unreceiveAmount,jdbcType=BIGINT}, #{confirmCurrency,jdbcType=VARCHAR}, ",
            "#{confirmAmount,jdbcType=BIGINT}, #{recipientCurrency,jdbcType=VARCHAR}, ",
            "#{recipientAmount,jdbcType=BIGINT}, #{confirmTime,jdbcType=TIMESTAMP}, ",
            "#{receiveTime,jdbcType=TIMESTAMP}, #{recipient,jdbcType=VARCHAR}, ",
            "#{payer,jdbcType=VARCHAR}, #{orderTime,jdbcType=TIMESTAMP}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{dataVersion,jdbcType=BIGINT}, ",
            "#{lastUpdateTime,jdbcType=TIMESTAMP}, ",
            "#{tracelog,jdbcType=LONGVARCHAR},#{bankName}, #{canceled} )"
    })
    int insert(CashConfirm record);

    @Select({
            "select",
            "confirmId, salesOrderNo, flowNo, recipientStatus, confirmStatus, distributorId, ",
            "distributorName, projectId, projectName, shouldReceiveCurrency, shouldReceiveAmount, ",
            "unreceiveCurrency, unreceiveAmount, confirmCurrency, confirmAmount, recipientCurrency, ",
            "recipientAmount, confirmTime, receiveTime, recipient, payer, orderTime,bankName, ",
            "createTime, dataVersion, lastUpdateTime,  tracelog, bankFlowNo",
            "from sales_cash_confirm",
            "where salesOrderNo = #{salesOrderNo} and confirmTime is NULL and canceled = 0"
    })
    CashConfirm getConfirmOrder(String salesOrderNo);

    @Update({
            "update sales_cash_confirm",
            "set salesOrderNo = #{salesOrderNo,jdbcType=VARCHAR},",
            "flowNo = #{flowNo,jdbcType=VARCHAR},",
            "recipientStatus = #{recipientStatus,jdbcType=INTEGER},",
            "confirmStatus = #{confirmStatus,jdbcType=INTEGER},",
            "distributorId = #{distributorId,jdbcType=INTEGER},",
            "distributorName = #{distributorName,jdbcType=VARCHAR},",
            "projectId = #{projectId,jdbcType=INTEGER},",
            "projectName = #{projectName,jdbcType=VARCHAR},",
            "shouldReceiveCurrency = #{shouldReceiveCurrency,jdbcType=VARCHAR},",
            "shouldReceiveAmount = #{shouldReceiveAmount,jdbcType=BIGINT},",
            "unreceiveCurrency = #{unreceiveCurrency,jdbcType=VARCHAR},",
            "unreceiveAmount = #{unreceiveAmount,jdbcType=BIGINT},",
            "confirmCurrency = #{confirmCurrency,jdbcType=VARCHAR},",
            "confirmAmount = #{confirmAmount,jdbcType=BIGINT},",
            "recipientCurrency = #{recipientCurrency,jdbcType=VARCHAR},",
            "recipientAmount = #{recipientAmount,jdbcType=BIGINT},",
            "confirmTime = #{confirmTime,jdbcType=TIMESTAMP},",
            "receiveTime = #{receiveTime,jdbcType=TIMESTAMP},",
            "recipient = #{recipient,jdbcType=VARCHAR},",
            "payer = #{payer,jdbcType=VARCHAR},",
            "orderTime = #{orderTime,jdbcType=TIMESTAMP},",
            "createTime = #{createTime,jdbcType=TIMESTAMP},",
            "dataVersion = dataVersion+1,",
            "tracelog = #{tracelog,jdbcType=LONGVARCHAR},",
            "bankName = #{bankName},",
            "bankFlowNo = #{bankFlowNo},",
            "canceled = #{canceled}",
            "where confirmId = #{confirmId,jdbcType=BIGINT} AND dataVersion = #{dataVersion}"
    })
    int updateByPrimaryKeyWithBLOBs(CashConfirm record);


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
    @SelectProvider(type = SalesCashConfirmSqlProvider.class, method = "selectListSelectively")
    List<CashConfirm> selectListSelectively(@Param("salesOrderNo") String salesOrderNo,
                                            @Param("projectId") Long projectId,
                                            @Param("distributorName") String distributorName,
                                            @Param("bankName") String bankName,
                                            @Param("orderBegin") Date orderBegin,
                                            @Param("orderEnd") Date orderEnd,
                                            @Param("confirmBegin") Date confirmBegin,
                                            @Param("confirmEnd") Date confirmEnd,
                                            @Param("orderStatus") Integer[] orderStatus);

    @Update({
            "update sales_cash_confirm",
            "set ",
            "confirmStatus = #{confirmStatus},",
            "unreceiveAmount = #{unreceiveAmount}",
            "where salesOrderNo = #{salesOrderNo} and canceled = 0"
    })
    int updateBySalesOrder(@Param("salesOrderNo") String salesOrderNo,
                           @Param("confirmStatus") int confirmStatus,
                           @Param("unreceiveAmount") long unreceiveAmount);

    @Select({
            "select",
            "confirmId, salesOrderNo, flowNo, recipientStatus, confirmStatus, distributorId, ",
            "distributorName, projectId, projectName, shouldReceiveCurrency, shouldReceiveAmount, ",
            "unreceiveCurrency, unreceiveAmount, confirmCurrency, confirmAmount, recipientCurrency, ",
            "recipientAmount, confirmTime, receiveTime, recipient, payer, orderTime, ",
            "createTime, dataVersion, lastUpdateTime, tracelog , bankName ,bankFlowNo ",
            "from sales_cash_confirm",
            "where flowNo = #{flowNo} and canceled = 0"
    })
    List<CashConfirm> selectConfirmListByFlowNo(@Param("flowNo") String flowNo);

    @Update({
            "update sales_cash_confirm",
            "set ",
            "canceled = 1,",
            "dataVersion = dataVersion+1,",
            "tracelog = #{tracelog}",
            "where confirmId = #{confirmId} AND dataVersion = #{dataVersion}"
    })
    int cancelComfirmOrder(CashConfirm cashConfirm);

    @Select({
            "select count(1)",
            "from sales_cash_confirm",
            "where salesOrderNo = #{salesOrderNo} and canceled = 0"
    })
    int getUncanceledCountBySalesOrderNo(@Param("salesOrderNo") String salesOrderNo);

    @Select({
            "select count(1)",
            "from sales_cash_confirm",
            "where salesOrderNo = #{salesOrderNo} and canceled = 0 and receiveTime is null"
    })
    int getUnreceivedCount(@Param("salesOrderNo") String salesOrderNo);

    @Select({
            "select",
            "confirmId, salesOrderNo, flowNo, recipientStatus, confirmStatus, distributorId, ",
            "distributorName, projectId, projectName, shouldReceiveCurrency, shouldReceiveAmount, ",
            "unreceiveCurrency, unreceiveAmount, confirmCurrency, confirmAmount, recipientCurrency, ",
            "recipientAmount, confirmTime, receiveTime, recipient, payer, orderTime, ",
            "createTime, dataVersion, lastUpdateTime, tracelog, bankName, bankFlowNo",
            "from sales_cash_confirm",
            "where salesOrderNo = #{salesOrderNo} and canceled = 0"
    })
    List<CashConfirm> selectListBySalesOrderNo(String salesOrderNo);

    @Select({
            "select",
            "count(1)",
            "from sales_cash_confirm",
            "where bankFlowNo = #{bankFlowNo} and canceled = 0"
    })
    int selectCountByBankFlowNo(@Param("bankFlowNo") String bankFlowNo);
}