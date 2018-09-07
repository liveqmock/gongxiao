package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.YhglobalReceivedPrepaidRecordMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.YhglobalReceivedPrepaidRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author: 葛灿
 */
@Repository
public class YhglobalReceivedPrepaidRecordDao {

    @Autowired
    YhglobalReceivedPrepaidRecordMapper yhglobalReceivedPrepaidRecordMapper;

    /**
     * 插入流水记录
     *
     * @param record 记录
     * @return
     */
    public int insert(YhglobalReceivedPrepaidRecord record) {
        return yhglobalReceivedPrepaidRecordMapper.insert(record);
    }

    /**
     * 条件查询流水信息
     *
     * @param currencyCode 货币编码
     * @param projectId    项目id
     * @param moneyFlow    资金流向
     * @param beginDate    查询起始日期
     * @param endDate      查询截止日期
     * @return
     */
    public List<YhglobalReceivedPrepaidRecord> selectPrepaidReceivedRecords(String currencyCode, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        return yhglobalReceivedPrepaidRecordMapper.selectPrepaidReceivedRecords(currencyCode,  projectId, moneyFlow, beginDate, endDate);
    }


    public List<FlowSubtotal> selectIncomeAndExpenditure(String currencyCode, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        return yhglobalReceivedPrepaidRecordMapper.selectIncomeAndExpenditure(currencyCode,  projectId, moneyFlow, beginDate, endDate);
    }

}
