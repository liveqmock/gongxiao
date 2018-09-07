package com.yhglobal.gongxiao.warehouse.dao;

import com.yhglobal.gongxiao.warehouse.dao.mapping.WmsInboundMapper;
import com.yhglobal.gongxiao.warehouse.model.WmsIntboundRecord;
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

    public List<WmsIntboundRecord> selectWmsInStorageInfo(String projectId, String gonxiaoInboundNo, String purchaseNo, String productCode, String createTimeBegin, String createTimeTo, String warehouseId, String supplierName){
        return wmsInboundMapper.selectWmsInStorageInfo(projectId, gonxiaoInboundNo, purchaseNo, productCode, createTimeBegin, createTimeTo, warehouseId, supplierName);
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
