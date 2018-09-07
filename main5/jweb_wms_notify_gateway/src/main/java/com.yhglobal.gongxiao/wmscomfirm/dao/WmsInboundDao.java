package com.yhglobal.gongxiao.wmscomfirm.dao;

import com.yhglobal.gongxiao.wmscomfirm.dao.mapper.WmsInboundMapper;
import com.yhglobal.gongxiao.wmscomfirm.model.wms.WmsIntboundRecord;
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

}
