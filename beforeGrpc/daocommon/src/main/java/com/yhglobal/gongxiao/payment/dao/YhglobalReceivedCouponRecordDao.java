package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.YhglobalReceivedCouponRecordMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.YhglobalReceivedCouponRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author: 葛灿
 */
@Repository
public class YhglobalReceivedCouponRecordDao {

    @Autowired
    YhglobalReceivedCouponRecordMapper yhglobalReceivedCouponRecordMapper;

    public int insert(YhglobalReceivedCouponRecord record) {
        return yhglobalReceivedCouponRecordMapper.insert(record);
    }


    /**
     * 条件模糊查询流水List
     *
     * @param currencyCode 货币编码
     * @param projectId    项目id
     * @param moneyFlow    资金流向
     * @param beginDate    查询开始日期
     * @param endDate      查询截止日期
     * @return
     */
    public List<YhglobalReceivedCouponRecord> selectCouponReceivedRecords(String currencyCode,  long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        return yhglobalReceivedCouponRecordMapper.selectCouponReceivedRecords(currencyCode,  projectId, moneyFlow, beginDate, endDate);
    }

    /**
     * 查询收入的总条数
     *
     * @param currencyCode 货币编码
     * @param projectId    项目id
     * @param moneyFlow    资金流向
     * @param beginDate    查询开始日期
     * @param endDate      查询截止日期
     * @return
     */
    public List<FlowSubtotal> selectIncomeAndExpenditure(String currencyCode, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        return yhglobalReceivedCouponRecordMapper.selectIncomeAndExpenditure(currencyCode, projectId, moneyFlow, beginDate, endDate);
    }


}
