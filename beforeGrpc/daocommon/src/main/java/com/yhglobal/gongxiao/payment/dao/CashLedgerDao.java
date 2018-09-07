package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.payment.dao.mapping.CashLedgerMapper;
import com.yhglobal.gongxiao.payment.model.CashLedger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 现金台账DAO
 *
 * @author: 葛灿
 */
@Repository
public class CashLedgerDao {

    @Autowired
    CashLedgerMapper cashLedgerMapper;

    /**
     * 插入新记录
     *
     * @param record 账户
     * @return
     */
    public int insert(CashLedger record) {
        return cashLedgerMapper.insert(record);
    }

    /**
     * 更新收款状态
     *
     * @param record 账户
     * @return
     */
    public int update(CashLedger record) {
        return cashLedgerMapper.updateByPrimaryKey(record);
    }

    /**
     * 根据流水号查找
     *
     * @param flowNo 流水号
     * @return
     */
    public CashLedger getByFlowNo(String flowNo) {
        return cashLedgerMapper.getByFlowNo(flowNo);
    }

    /**
     * 选择性查询(均为可选条件)
     *
     * @param projectId     项目id
     * @param bankName      银行名称
     * @param flowNo        流水号
     * @param confirmBegin  确认开始时间
     * @param confirmEnd    确认截止时间
     * @param receiveBegin  收款开始时间
     * @param receiveEnd    收款截止时间
     * @param approveStatus 审核状态
     * @return
     */
    public List<CashLedger> selectCashLedgerList(Long projectId, String bankName, String flowNo, Date confirmBegin, Date confirmEnd, Date receiveBegin, Date receiveEnd, Boolean approveStatus) {
        return cashLedgerMapper.selectCashLedgerList(projectId, bankName, flowNo, confirmBegin, confirmEnd, receiveBegin, receiveEnd, approveStatus);
    }
}
