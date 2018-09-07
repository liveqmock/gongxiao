package com.yhglobal.gongxiao.payment.dao;

import com.yhglobal.gongxiao.payment.dao.mapping.YhglobalPrepaidLedgerWriteoffFlowMapper;
import com.yhglobal.gongxiao.payment.model.YhglobalPrepaidLedgerWriteoffFlow;
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
    public YhglobalPrepaidLedgerWriteoffFlow selectById(Long flowId){
        return yhglobalPrepaidLedgerWriteoffFlowMapper.selectById(flowId);
    }
    public int updateById(YhglobalPrepaidLedgerWriteoffFlow record){
        return yhglobalPrepaidLedgerWriteoffFlowMapper.updateById(record);
    }
}
