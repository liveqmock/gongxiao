package com.yhglobal.gongxiao.tms.dao;

import com.yhglobal.gongxiao.tms.dao.mapping.SignInfoMapper;
import com.yhglobal.gongxiao.tms.model.SignInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author: 葛灿
 */
@Repository
public class SignInfoDao {

    @Autowired
    SignInfoMapper signInfoMapper;

    public int insert(SignInfo record) {
        return signInfoMapper.insert(record);
    }

    public SignInfo getByOrderNo(String orderNo) {
        return signInfoMapper.getByOrderNo(orderNo);
    }

    public int update(SignInfo record) {
        return signInfoMapper.update(record);
    }
}
