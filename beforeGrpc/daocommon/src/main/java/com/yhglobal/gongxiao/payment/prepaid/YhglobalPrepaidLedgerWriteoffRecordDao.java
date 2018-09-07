package com.yhglobal.gongxiao.payment.prepaid;

import com.yhglobal.gongxiao.payment.prepaid.mapping.YhglobalPrepaidLedgerWriteoffRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Description: 越海代垫确认
 * @author: LUOYI
 * @Date: Created in 9:49 2018/4/26
 */
@Repository
public class YhglobalPrepaidLedgerWriteoffRecordDao {
    @Autowired
    private YhglobalPrepaidLedgerWriteoffRecordMapper yhglobalPrepaidLedgerWriteoffRecordMapper;

    public int addWriteoffRecord(YhglobalPrepaidLedgerWriteoffRecord record){
        return yhglobalPrepaidLedgerWriteoffRecordMapper.insert(record);
    }
    public YhglobalPrepaidLedgerWriteoffRecord selectById(Long flowId){
        return yhglobalPrepaidLedgerWriteoffRecordMapper.selectById(flowId);
    }
    public int updateById(YhglobalPrepaidLedgerWriteoffRecord record){
        return yhglobalPrepaidLedgerWriteoffRecordMapper.updateById(record);
    }
    public List<YhglobalPrepaidLedgerWriteoffRecord> selectByReceiveId(Long receiveId){
        return yhglobalPrepaidLedgerWriteoffRecordMapper.selectByReceiveId(receiveId);
    }
    public List<YhglobalPrepaidLedgerWriteoffRecord> searchPrepaidConfirm(Long projectId,
                                                                          String flowCode,
                                                                          Integer accountType,
                                                                          Date useDateStart,
                                                                          Date useDateEnd,
                                                                          Date dateStart,
                                                                          Date dateEnd){
        return yhglobalPrepaidLedgerWriteoffRecordMapper.searchPrepaidConfirm(projectId,flowCode,accountType,useDateStart,useDateEnd,dateStart,dateEnd);
    }
    public List<YhglobalPrepaidLedgerWriteoffRecord> selectByFlowCode(String flowCode){
        return yhglobalPrepaidLedgerWriteoffRecordMapper.selectByFlowCode(flowCode);
    }
}
