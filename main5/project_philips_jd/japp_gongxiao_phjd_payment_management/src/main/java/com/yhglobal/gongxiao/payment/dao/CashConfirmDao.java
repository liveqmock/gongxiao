package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.CashConfirmMapper;
import com.yhglobal.gongxiao.payment.model.CashConfirm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public int insert(CashConfirm record) {
        return salesCashConfirmMapper.insert(record);
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
    public List<CashConfirm> selectListSelectively(String salesOrderNo,
                                                   Long projectId,
                                                   String distributorName,
                                                   String bankName,
                                                   String orderBegin,
                                                   String orderEnd,
                                                   String confirmBegin,
                                                   String confirmEnd,
                                                   String orderStatus) {
        return salesCashConfirmMapper.selectListSelectively(salesOrderNo, projectId, distributorName, bankName, orderBegin, orderEnd, confirmBegin, confirmEnd, orderStatus);
    }

    /**
     * 更新
     *
     * @param item 数据
     * @return
     */
    public int update(CashConfirm item) {
        return salesCashConfirmMapper.updateByPrimaryKeyWithBLOBs(item);
    }

    /**
     * 查询该订单未确认收款的部分
     *
     * @param salesOrderNo 销售单号
     * @return
     */
    public CashConfirm getConfirmOrder(String salesOrderNo) {
        return salesCashConfirmMapper.getConfirmOrder(salesOrderNo);
    }


    /**
     * 更新该销售单下所有收款信息的"未收金额"和状态
     *
     * @param salesOrderNo    销售单号
     * @param status          收款状态
     * @param unreceiveAmount 未收款金额
     * @return
     */
    public int updateBySalesOrder(String salesOrderNo, int status, long unreceiveAmount) {
        return salesCashConfirmMapper.updateBySalesOrder(salesOrderNo, status, unreceiveAmount);
    }

    /**
     * 根据流水号查询
     *
     * @param flowNo 流水号
     * @return
     */
    public List<CashConfirm> selectConfirmListByFlowNo(String flowNo) {
        return salesCashConfirmMapper.selectConfirmListByFlowNo(flowNo);
    }

    /**
     * 作废该“现金收款单”
     *
     * @param cashConfirm 对象
     * @return
     */
    public int cancelComfirmOrder(CashConfirm cashConfirm) {
        return salesCashConfirmMapper.cancelComfirmOrder(cashConfirm);
    }

    /**
     * 根据销售单号，查询未作废的条数
     *
     * @param salesOrderNo 销售单号
     * @return
     */
    public int getUncanceledCountBySalesOrderNo(String salesOrderNo) {
        return salesCashConfirmMapper.getUncanceledCountBySalesOrderNo(salesOrderNo);
    }

    /**
     * 查找销售单下所有未作废的信息
     *
     * @param salesOrderNo 销售单号
     * @return
     */
    public List<CashConfirm> selectListBySalesOrderNo(String salesOrderNo) {
        return salesCashConfirmMapper.selectListBySalesOrderNo(salesOrderNo);
    }
}
