package com.yhglobal.gongxiao.warehousemanagement.dao;

import com.yhglobal.gongxiao.warehousemanagement.dao.mapping.WmsInboundMapper;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsIntboundRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WmsInboundDao {

    @Autowired
    WmsInboundMapper wmsInboundMapper;

    public int insertWmsInboundInfo(WmsIntboundRecord wmsIntboundRecord) {
        return wmsInboundMapper.insertWmsInboundInfo(wmsIntboundRecord);
    }

    public List<WmsIntboundRecord> selectWmsInStorageInfo(String projectId, String  gonxiaoInboundNo, String  batchNo, String  orderType, String  warehouseId, String  supplierName, String wmsInboundOrderNo,String createTimeBegin, String createTimeTo){
        return wmsInboundMapper.selectWmsInStorageInfo(projectId, gonxiaoInboundNo, batchNo, orderType, warehouseId, supplierName,wmsInboundOrderNo, createTimeBegin, createTimeTo);
    }

    public WmsIntboundRecord selectRecordByOrderNo(String projectId, String gongxiaoInboundOrderNo,String wmsInboundOrderNo){
        return wmsInboundMapper.selectRecordByOrderNo(projectId, gongxiaoInboundOrderNo,wmsInboundOrderNo);
    }

    public List<WmsIntboundRecord> selectInboundRecordByEasFlag(int easFlag){
        return wmsInboundMapper.selectInboundRecordByEasFlag(easFlag);
    }

    public int notifyEasSuccess(String gongxiaoInboundOrderNo,String wmsInboundOrderNo,int easFlag,String easInboundOrderNo){
        return wmsInboundMapper.notifyEasSuccess(gongxiaoInboundOrderNo,wmsInboundOrderNo,easFlag,easInboundOrderNo);
    }

    public int updateEasFlagToIng(String gongxiaoInboundOrderNo,String wmsInboundOrderNo,int easFlag,int dataVersion){
        return wmsInboundMapper.updateEasFlagToIng(gongxiaoInboundOrderNo,wmsInboundOrderNo,easFlag,dataVersion);
    }

    public int notifyEasFail(String gongxiaoInboundOrderNo,String wmsInboundOrderNo,int easFlag){
        return wmsInboundMapper.notifyEasFail(gongxiaoInboundOrderNo,wmsInboundOrderNo,easFlag);
    }

    public int notifyEasNeedHandle(String gongxiaoInboundOrderNo,String wmsInboundOrderNo,int easFlag){
        return wmsInboundMapper.notifyEasNeedHandle(gongxiaoInboundOrderNo,wmsInboundOrderNo,easFlag);
    }
}
