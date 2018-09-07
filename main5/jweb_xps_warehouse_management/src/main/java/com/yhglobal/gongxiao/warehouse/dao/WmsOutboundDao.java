package com.yhglobal.gongxiao.warehouse.dao;

import com.yhglobal.gongxiao.warehouse.dao.mapping.WmsOutboundMapper;
import com.yhglobal.gongxiao.warehouse.model.WmsOutboundRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WmsOutboundDao {

    @Autowired
    WmsOutboundMapper wmsOutboundMapper;

    public int insertWmsOutboundInfo(WmsOutboundRecord wmsOutboundRecord) {
        return wmsOutboundMapper.insertWmsOutboundInfo(wmsOutboundRecord);
    }

    public List<WmsOutboundRecord> selectWmsInStorageInfo(String projectId, String gongxiaoOutboundOrderNo, String purchaseNo, String productCode, String createTimeBegin, String createTimeTo, String warehouseId, String supplierName){
        return  wmsOutboundMapper.selectWmsInStorageInfo(projectId, gongxiaoOutboundOrderNo, purchaseNo, productCode, createTimeBegin, createTimeTo, warehouseId, supplierName);
    }

    public WmsOutboundRecord selectRecordByOrderNo(String gongxiaoOutboundOrderNo,String wmsOutboundOrderNo){
        return  wmsOutboundMapper.selectRecordByOrderNo(gongxiaoOutboundOrderNo, wmsOutboundOrderNo);
    }

    public List<WmsOutboundRecord> selectRecordByEasFlag(int easFlag){
        return wmsOutboundMapper.selectRecordByEasFlag(easFlag);
    }

    public int notifyEasSuccess(String gongxiaoOutboundOrderNo,String wmsOutboundOrderNo, int easFlag, String easOutBoundNo){
        return wmsOutboundMapper.notifyEasSuccess(gongxiaoOutboundOrderNo,wmsOutboundOrderNo,easFlag,easOutBoundNo);
    }

    public int updateEasFlagToIng(String gongxiaoOutboundOrderNo,String wmsOutboundOrderNo, int easFlag,int dataVersion){
        return wmsOutboundMapper.updateEasFlagToIng(gongxiaoOutboundOrderNo,wmsOutboundOrderNo,easFlag,dataVersion);
    }

    public int updateTmsOrderNo(String gongxiaoOutboundOrderNo,String tmsOrderNo){
        return wmsOutboundMapper.updateTmsOrderNo(gongxiaoOutboundOrderNo,tmsOrderNo);
    }

    public int notifyEasFail(String gongxiaoOutboundOrderNo,String wmsOutboundOrderNo, int easFlag){
        return wmsOutboundMapper.notifyEasFail(gongxiaoOutboundOrderNo,wmsOutboundOrderNo,easFlag);
    }

    public int notifyEasNeedHandle(String gongxiaoOutboundOrderNo,String wmsOutboundOrderNo, int easFlag){
        return wmsOutboundMapper.notifyEasNeedHandle(gongxiaoOutboundOrderNo,wmsOutboundOrderNo,easFlag);
    }

}

