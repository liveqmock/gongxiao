package com.yhglobal.gongxiao.wmscomfirm.dao;

import com.yhglobal.gongxiao.wmscomfirm.dao.mapper.WmsOutboundItemMapper;
import com.yhglobal.gongxiao.wmscomfirm.model.wms.WmsOutboundRecordItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WmsOutboundItemDao {

    @Autowired
    WmsOutboundItemMapper wmsOutboundItemMapper;

    public int insertWmsOutboundInfo(List<WmsOutboundRecordItem> wmsOutboundRecordItemList) {
        return wmsOutboundItemMapper.insertWmsOutboundInfo(wmsOutboundRecordItemList);
    }

    public List<WmsOutboundRecordItem> selectRecordByOrderNo(String projectId,String gongxiaoOutboundOrderNo, String wmsOutboundOrderNo){
        return  wmsOutboundItemMapper.selectRecordByOrderNo(projectId, gongxiaoOutboundOrderNo, wmsOutboundOrderNo);
    }

    public List<WmsOutboundRecordItem> selectOutboundOrderItemByNo(String gongxiaoOutboundOrderNo,String wmsOutboundOrderNo){
        return wmsOutboundItemMapper.selectOutboundOrderItemByNo(gongxiaoOutboundOrderNo,wmsOutboundOrderNo);
    }


}

