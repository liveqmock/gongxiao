package com.yhglobal.gongxiao.warehousemanagement.dao;

import com.yhglobal.gongxiao.warehousemanagement.dao.mapping.WmsOutboundMapper;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsOutboundRecord;
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

    public List<WmsOutboundRecord> selectWmsOutStorageInfo(String projectId, String gongxiaoOutboundOrderNo, String salesNo, String orderType,String customer, String createTimeBeging, String createTimeLast){
        return  wmsOutboundMapper.selectWmsOutStorageInfo(projectId, gongxiaoOutboundOrderNo, salesNo, orderType, customer, createTimeBeging, createTimeLast);
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

    public int notifyEasFail(String gongxiaoOutboundOrderNo,String wmsOutboundOrderNo, int easFlag){
        return wmsOutboundMapper.notifyEasFail(gongxiaoOutboundOrderNo,wmsOutboundOrderNo,easFlag);
    }

    public int notifyEasNeedHandle(String gongxiaoOutboundOrderNo,String wmsOutboundOrderNo, int easFlag){
        return wmsOutboundMapper.notifyEasNeedHandle(gongxiaoOutboundOrderNo,wmsOutboundOrderNo,easFlag);
    }

}

