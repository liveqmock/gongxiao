package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.DistributorCouponAccountMapper;
import com.yhglobal.gongxiao.payment.dao.mapping.YhglobalReceivedPrepaidAccountMapper;
import com.yhglobal.gongxiao.payment.model.YhglobalReceivedPrepaidAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author: 葛灿
 */
@Repository
public class YhglobalReceivedPrepaidAccountDao {

    @Autowired
    YhglobalReceivedPrepaidAccountMapper yhglobalReceivedPrepaidAccountMapper;

    /**
     * 插入新账户
     *
     * @param record 账户
     * @return
     */
    public int insert(YhglobalReceivedPrepaidAccount record) {
        return yhglobalReceivedPrepaidAccountMapper.insert(record);
    }

    /**
     * 更新账户
     *
     * @param record 账户
     * @return
     */
    public int update(YhglobalReceivedPrepaidAccount record) {
        return yhglobalReceivedPrepaidAccountMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    /**
     * 查询账户
     *
     * @param projectId 项目id
     * @return
     */
    public YhglobalReceivedPrepaidAccount getAccount(long projectId) {
        return yhglobalReceivedPrepaidAccountMapper.getAccount(projectId);
    }
}
