package com.yhglobal.gongxiao.payment.dao;

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
    public int insert(String prefix, CashLedger record) {
        return cashLedgerMapper.insert(prefix, record);
    }

    /**
     * 根据流水号查找
     *
     * @param flowNo 流水号
     * @return
     */
    public CashLedger getByFlowNo(String prefix, String flowNo) {
        return cashLedgerMapper.getByFlowNo(prefix, flowNo);
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
     * @return 更新成功记录数
     */
    public List<CashLedger> selectCashLedgerList(String prefix, long projectId, String bankName, String flowNo, String confirmBegin, String confirmEnd, String receiveBegin, String receiveEnd, String approveStatus,String clientName) {
        return cashLedgerMapper.selectCashLedgerList(prefix, projectId, bankName, flowNo, confirmBegin, confirmEnd, receiveBegin, receiveEnd, approveStatus,clientName);
    }

    /**
     * 作废台账
     *
     * @param prefix      表前缀
     * @param ledgerId    主键id
     * @param dataVersion 数据版本
     * @param canceled    是否作废
     * @return 更新成功记录数
     */
    public int updateWhenCancelConfirm(String prefix, Long ledgerId, Long dataVersion, boolean canceled) {
        return cashLedgerMapper.updateWhenCancelConfirm(prefix, ledgerId, dataVersion, canceled);
    }

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
    public int updateWhenApprove(String prefix, Long ledgerId, Long dataVersion, boolean approvalStatus, Date approveTime, Long approvalUserId, String approvalUserName) {
        return cashLedgerMapper.updateWhenApprove(prefix, ledgerId, dataVersion, approvalStatus, approveTime, approvalUserId, approvalUserName);
    }
}
