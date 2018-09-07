package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.CashConfirmMapper;
import com.yhglobal.gongxiao.payment.model.CashConfirm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 销售收款确认表Dao
 *
 * @author: 葛灿
 */
@Repository
public class CashConfirmDao {
    @Autowired
    CashConfirmMapper salesCashConfirmMapper;

    /**
     * 插入收款确认信息
     *
     * @param record 模型
     * @return
     */
    public int insert(String prefix, CashConfirm record) {
        return salesCashConfirmMapper.insert(prefix, record);
    }

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
    public List<CashConfirm> selectListSelectively(String prefix,
                                                   String salesOrderNo,
                                                   Long projectId,
                                                   String distributorName,
                                                   String bankName,
                                                   String orderBegin,
                                                   String orderEnd,
                                                   String confirmBegin,
                                                   String confirmEnd,
                                                   String orderStatus) {
        return salesCashConfirmMapper.selectListSelectively(prefix, salesOrderNo, projectId, distributorName, bankName, orderBegin, orderEnd, confirmBegin, confirmEnd, orderStatus);
    }


    /**
     * 查询该订单未确认收款的部分
     *
     * @param salesOrderNo 销售单号
     * @return
     */
    public CashConfirm getConfirmOrder(String prefix, String salesOrderNo) {
        return salesCashConfirmMapper.getConfirmOrder(prefix, salesOrderNo);
    }


    /**
     * 更新该销售单下所有收款信息的"未收金额"和状态
     *
     * @param salesOrderNo    销售单号
     * @param status          收款状态
     * @param unreceiveAmount 未收款金额
     * @return
     */
    public int updateBySalesOrder(String prefix, String salesOrderNo, int status, long unreceiveAmount) {
        return salesCashConfirmMapper.updateBySalesOrder(prefix, salesOrderNo, status, unreceiveAmount);
    }

    /**
     * 根据流水号查询
     *
     * @param flowNo 流水号
     * @return
     */
    public List<CashConfirm> selectConfirmListByFlowNo(String prefix, String flowNo) {
        return salesCashConfirmMapper.selectConfirmListByFlowNo(prefix, flowNo);
    }

    /**
     * 作废该“现金收款单”
     *
     * @param cashConfirm 对象
     * @return
     */
    public int cancelComfirmOrder(String prefix, CashConfirm cashConfirm) {
        return salesCashConfirmMapper.cancelComfirmOrder(prefix, cashConfirm);
    }

    /**
     * 根据销售单号，查询未作废的条数
     *
     * @param salesOrderNo 销售单号
     * @return
     */
    public int getUncanceledCountBySalesOrderNo(String prefix, String salesOrderNo) {
        return salesCashConfirmMapper.getUncanceledCountBySalesOrderNo(prefix, salesOrderNo);
    }

    /**
     * 查找销售单下所有未作废的收款确认信息
     *
     * @param salesOrderNo 销售单号
     * @return
     */
    public List<CashConfirm> selectListBySalesOrderNo(String prefix, String salesOrderNo) {
        return salesCashConfirmMapper.selectListBySalesOrderNo(prefix, salesOrderNo);
    }

    /**
     * 通过银行流水号查询条数
     *
     * @param bankFlowNo 银行流水号
     * @return 数据库条目数
     */
    public int selectCountByBankFlowNo(String prefix, String bankFlowNo) {
        return salesCashConfirmMapper.selectCountByBankFlowNo(prefix, bankFlowNo);
    }

    /**
     * 通过主键查询
     *
     * @param prefix 表前缀
     * @param id     主键id
     * @return 现金确认对象
     */
    public CashConfirm getById(String prefix, long id) {
        return salesCashConfirmMapper.getById(prefix, id);
    }


    /**
     * 收款确认时更新
     *
     * @param prefix          表前缀
     * @param id              主键id
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
     * @param traceLogJson    操作日志
     * @return 更新成功条数
     */
    public int updateWhenConfirm(String prefix, long id, long dataVersion, int confirmStatus, long confirmAmount, long recipientAmount, long unreceiveAmount,
                                 String bankName, String flowNo, Date confirmTime, String bankFlowNo, Date receiveTime, String clientName,
                                 String remark, String traceLogJson) {
        return salesCashConfirmMapper.updateWhenConfirm(prefix, id, dataVersion, confirmStatus, confirmAmount, recipientAmount, unreceiveAmount,
                bankName, flowNo, confirmTime, bankFlowNo, receiveTime, clientName,
                remark, traceLogJson);
    }

    /**
     * 审批通过时更新
     *
     * @param prefix          表前缀
     * @param confirmId       主键id
     * @param dataVersion     数据版本
     * @param recipientStatus 收款状态
     * @param traceLog        操作日志
     * @return 更新成功条数
     */
    public int updateWhenApprove(String prefix, Long confirmId, long dataVersion, boolean recipientStatus, String traceLog) {
        return salesCashConfirmMapper.updateWhenApprove(prefix, confirmId, dataVersion, recipientStatus, traceLog);
    }

    /**
     * 系统自动确认  未确认的金额
     *
     * @param prefix           表前缀
     * @param confirmId        主键id
     * @param dataVersion      数据版本
     * @param confirmStatus    确认状态
     * @param unreceivedAmount 未收金额
     * @param traceLog         操作日志
     * @return 更新成功条数
     */
    public int updateWhenSystemConfirm(String prefix, Long confirmId, long dataVersion, int confirmStatus, long unreceivedAmount, String traceLog) {
        return salesCashConfirmMapper.updateWhenSystemConfirm(prefix, confirmId, dataVersion, confirmStatus, unreceivedAmount, traceLog);
    }
}

