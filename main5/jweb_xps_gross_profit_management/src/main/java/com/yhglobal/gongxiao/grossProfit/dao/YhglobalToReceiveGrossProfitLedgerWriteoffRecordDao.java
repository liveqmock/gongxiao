package com.yhglobal.gongxiao.grossProfit.dao;

import com.yhglobal.gongxiao.grossProfit.dao.mapper.YhglobalToReceiveGrossProfitLedgerWriteoffRecordMapper;
import com.yhglobal.gongxiao.grossProfit.model.YhglobalToReceiveGrossProfitLedgerWriteoffRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author 王帅
 */
@Repository
public class YhglobalToReceiveGrossProfitLedgerWriteoffRecordDao {

    @Autowired
    YhglobalToReceiveGrossProfitLedgerWriteoffRecordMapper yhglobalToReceiveGrossProfitLedgerWriteoffRecordMapper;

    public int insertRecord(YhglobalToReceiveGrossProfitLedgerWriteoffRecord record){
        return  yhglobalToReceiveGrossProfitLedgerWriteoffRecordMapper.insert(record);
    }

    public int updateRecord(String tablePrefix, Integer rollbackStatus, Long confirmId){
        return yhglobalToReceiveGrossProfitLedgerWriteoffRecordMapper.updateRollbackStatus(tablePrefix, rollbackStatus, confirmId);
    }

    public List<YhglobalToReceiveGrossProfitLedgerWriteoffRecord> selectForBook(String tablePrefix,  Long projectId,
                                                                                String flowNo,
                                                                                Integer accountType,
                                                                                 Date useDateStart,
                                                                                Date useDateEnd,
                                                                                 Date dateStart,
                                                                                Date dateEnd){
        return yhglobalToReceiveGrossProfitLedgerWriteoffRecordMapper.selectGrossProfitBook(tablePrefix, projectId, flowNo, accountType, useDateStart, useDateEnd, dateStart, dateEnd);
    }

    public List<YhglobalToReceiveGrossProfitLedgerWriteoffRecord> selectByFlowNo(String tablePrefix,
                                                                                String flowNo){
        return yhglobalToReceiveGrossProfitLedgerWriteoffRecordMapper.selectByFlowCode(tablePrefix,flowNo);
    }
}
