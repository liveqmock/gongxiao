package com.yhglobal.gongxiao.payment.dao;

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
    public int insert(String prefix,YhglobalReceivedPrepaidAccount record) {
        return yhglobalReceivedPrepaidAccountMapper.insert( prefix,record);
    }

    /**
     * 更新账户
     *
     * @param record 账户
     * @return
     */
    public int update(String prefix,YhglobalReceivedPrepaidAccount record) {
        return yhglobalReceivedPrepaidAccountMapper.updateByPrimaryKeyWithBLOBs( prefix,record);
    }

    /**
     * 查询账户
     *
     * @param projectId 项目id
     * @return
     */
    public YhglobalReceivedPrepaidAccount getAccount(String prefix,long projectId) {
        return yhglobalReceivedPrepaidAccountMapper.getAccount( prefix,projectId);
    }
}
