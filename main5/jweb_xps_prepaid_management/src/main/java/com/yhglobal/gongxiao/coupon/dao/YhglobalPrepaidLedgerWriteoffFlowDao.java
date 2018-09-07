package com.yhglobal.gongxiao.coupon.dao;

import com.yhglobal.gongxiao.coupon.dao.mapper.YhglobalPrepaidLedgerWriteoffFlowMapper;

import com.yhglobal.gongxiao.coupon.model.YhglobalPrepaidLedgerWriteoffFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Description: 越海代垫确认流水
 * @author: LUOYI
 * @Date: Created in 15:35 2018/4/27
 */
@Repository
public class YhglobalPrepaidLedgerWriteoffFlowDao {
    @Autowired
    private YhglobalPrepaidLedgerWriteoffFlowMapper yhglobalPrepaidLedgerWriteoffFlowMapper;

    public int addFlow(YhglobalPrepaidLedgerWriteoffFlow record){
        return yhglobalPrepaidLedgerWriteoffFlowMapper.insert(record);
    }
    public YhglobalPrepaidLedgerWriteoffFlow selectById(String tablePrefix,Long flowId){
        return yhglobalPrepaidLedgerWriteoffFlowMapper.selectById(tablePrefix, flowId);
    }
    public int updateById(String tablePrefix,YhglobalPrepaidLedgerWriteoffFlow record){
        return yhglobalPrepaidLedgerWriteoffFlowMapper.updateById(tablePrefix, record);
    }
}
