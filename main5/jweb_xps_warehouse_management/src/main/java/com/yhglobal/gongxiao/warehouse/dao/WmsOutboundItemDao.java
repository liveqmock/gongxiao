package com.yhglobal.gongxiao.warehouse.dao;

import com.yhglobal.gongxiao.warehouse.dao.mapping.WmsOutboundItemMapper;
import com.yhglobal.gongxiao.warehouse.model.WmsOutboundRecordItem;
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


    /**
     * 根据年月获取所有数据
     *
     * @param projectId 项目id
     * @param dateString   日期yyyy-MM-dd 查询时会根据年月去查询所有数据
     * @return List<货品明细>
     */
    public List<WmsOutboundRecordItem> getListByYearAndMonth(long projectId, String dateString) {
        return wmsOutboundItemMapper.getListByYearAndMonth(projectId, dateString);
    }
}

