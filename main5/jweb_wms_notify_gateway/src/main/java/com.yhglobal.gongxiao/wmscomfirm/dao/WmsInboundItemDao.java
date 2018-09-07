package com.yhglobal.gongxiao.wmscomfirm.dao;

import com.yhglobal.gongxiao.wmscomfirm.dao.mapper.WmsInboundItemMapper;
import com.yhglobal.gongxiao.wmscomfirm.model.wms.WmsIntboundRecordItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WmsInboundItemDao {

    @Autowired
    WmsInboundItemMapper wmsInboundItemMapper;

    public int insertWmsInboundInfo(List<WmsIntboundRecordItem> wmsIntboundRecordItemList) {
        return wmsInboundItemMapper.insertWmsInboundInfo(wmsIntboundRecordItemList);
    }

    public List<WmsIntboundRecordItem> selectRecordByOrderNo(String projectId, String gongxiaoInboundOrderNo,String wmsInboundOrderNo){
        return wmsInboundItemMapper.selectRecordByOrderNo(projectId, gongxiaoInboundOrderNo,wmsInboundOrderNo);
    }

    public List<WmsIntboundRecordItem> selectInboundOrderItemByNo(String gongxiaoInboundOrderNo,String wmsInboundOrderNo){
        return wmsInboundItemMapper.selectInboundOrderItemByNo(gongxiaoInboundOrderNo,wmsInboundOrderNo);
    }

}
