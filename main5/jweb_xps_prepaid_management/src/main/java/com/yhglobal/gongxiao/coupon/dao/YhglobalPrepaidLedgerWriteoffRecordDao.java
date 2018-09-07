package com.yhglobal.gongxiao.coupon.dao;

import com.yhglobal.gongxiao.coupon.dao.mapper.YhglobalPrepaidLedgerWriteoffRecordMapper;

import com.yhglobal.gongxiao.coupon.model.YhglobalPrepaidLedgerWriteoffRecord;
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
        return yhglobalPrepaidLedgerWriteoffRecordMapper.insert( record);
    }
    public YhglobalPrepaidLedgerWriteoffRecord selectById(String tablePrefix,Long flowId){
        return yhglobalPrepaidLedgerWriteoffRecordMapper.selectById(tablePrefix, flowId);
    }
    public int updateById(String tablePrefix,YhglobalPrepaidLedgerWriteoffRecord record){
        return yhglobalPrepaidLedgerWriteoffRecordMapper.updateById(tablePrefix, record);
    }
    public int updateIsRollback(String tablePrefix,Long writeoffId, Integer isRollback, Long dataVersion, Date lastUpdateTime){
        return yhglobalPrepaidLedgerWriteoffRecordMapper.updateIsRollback(tablePrefix, writeoffId, isRollback, dataVersion, lastUpdateTime);
    }

    public List<YhglobalPrepaidLedgerWriteoffRecord> selectByReceiveId(String tablePrefix,Long receiveId){
        return yhglobalPrepaidLedgerWriteoffRecordMapper.selectByReceiveId(tablePrefix, receiveId);
    }
    public List<YhglobalPrepaidLedgerWriteoffRecord> searchPrepaidConfirm(String tablePrefix,Long projectId,
                                                                          String flowCode,
                                                                          Integer accountType,
                                                                          Date useDateStart,
                                                                          Date useDateEnd,
                                                                          Date dateStart,
                                                                          Date dateEnd){
        return yhglobalPrepaidLedgerWriteoffRecordMapper.searchPrepaidConfirm(tablePrefix, projectId,flowCode,accountType,useDateStart,useDateEnd,dateStart,dateEnd);
    }
    public List<YhglobalPrepaidLedgerWriteoffRecord> selectByFlowCode(String tablePrefix,String flowCode){
        return yhglobalPrepaidLedgerWriteoffRecordMapper.selectByFlowCode(tablePrefix, flowCode);
    }
}
