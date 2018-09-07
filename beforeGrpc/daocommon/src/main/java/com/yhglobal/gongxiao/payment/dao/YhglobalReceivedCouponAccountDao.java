package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.DistributorCouponAccountMapper;
import com.yhglobal.gongxiao.payment.dao.mapping.YhglobalReceivedCouponAccountMapper;
import com.yhglobal.gongxiao.payment.model.YhglobalReceivedCouponAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author: 葛灿
 */
@Repository
public class YhglobalReceivedCouponAccountDao {

    @Autowired
    YhglobalReceivedCouponAccountMapper yhglobalReceivedCouponAccountMapper;

    /**
     * 插入新账户
     *
     * @param record 账户
     * @return
     */
    public int insert(YhglobalReceivedCouponAccount record) {
        return yhglobalReceivedCouponAccountMapper.insert(record);
    }

    /**
     * 更新账户
     *
     * @param record 账户
     * @return
     */
    public int update(YhglobalReceivedCouponAccount record) {
        return yhglobalReceivedCouponAccountMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    /**
     * 查询账户
     *
     * @param projectId 项目id
     * @return
     */
    public YhglobalReceivedCouponAccount getAccount(long projectId) {
        return yhglobalReceivedCouponAccountMapper.getAccount(projectId);
    }
}
