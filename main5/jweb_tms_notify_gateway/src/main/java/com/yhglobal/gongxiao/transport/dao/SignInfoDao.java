package com.yhglobal.gongxiao.transport.dao;

import com.yhglobal.gongxiao.transport.dao.mapping.SignInfoMapper;
import com.yhglobal.gongxiao.transportataion.sendtotransportation.sales.model.SignInfo;
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
