package com.yhglobal.gongxiao.grossProfit.dao;

import com.yhglobal.gongxiao.grossProfit.dao.mapper.YhglobalToReceiveGrossProfitLedgerWriteoffFlowMapper;
import com.yhglobal.gongxiao.grossProfit.model.YhglobalToReceiveGrossProfitLedgerWriteoffFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author 王帅
 */
@Repository
public class YhglobalToReceiveGrossProfitLedgerWriteoffFlowDao {

    @Autowired
    YhglobalToReceiveGrossProfitLedgerWriteoffFlowMapper yhglobalToReceiveGrossProfitLedgerWriteoffFlowMapper;

    public int insertFlow(YhglobalToReceiveGrossProfitLedgerWriteoffFlow flow){
        return yhglobalToReceiveGrossProfitLedgerWriteoffFlowMapper.insert(flow);
    }
}
