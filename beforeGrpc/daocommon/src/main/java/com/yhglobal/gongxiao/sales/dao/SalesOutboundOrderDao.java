package com.yhglobal.gongxiao.sales.dao;

import com.yhglobal.gongxiao.sales.dao.mapping.SalesOutboundOrderMapper;
import com.yhglobal.gongxiao.sales.model.SalesOutboundOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 葛灿
 */
@Repository
public class SalesOutboundOrderDao {

    @Autowired
    SalesOutboundOrderMapper salesOutboundOrderMapper;

    public SalesOutboundOrder getOrderByOutboundNo(String outboundNo){
        return salesOutboundOrderMapper.getOrderByOutboundNo(outboundNo);
    }

    public int insert(SalesOutboundOrder order){
        return salesOutboundOrderMapper.insert(order);
    }

    public int update(SalesOutboundOrder order){
        return salesOutboundOrderMapper.update(order);
    }

    public List<String> selectListByEasStatus(int status) {
        return salesOutboundOrderMapper.selectListByEasStatus(status);
    }

    public List<String> selectListByTmsStatus(int status) {
        return salesOutboundOrderMapper.selectListByTmsStatus(status);
    }
}
