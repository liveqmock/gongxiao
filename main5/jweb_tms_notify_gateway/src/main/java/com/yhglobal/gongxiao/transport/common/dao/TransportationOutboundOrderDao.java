package com.yhglobal.gongxiao.transport.common.dao;

import com.yhglobal.gongxiao.transport.common.dao.mapping.TransportationOutboundOrderMapper;
import com.yhglobal.gongxiao.transport.model.TransportationOutboundOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 葛灿
 */
@Repository
public class TransportationOutboundOrderDao {


    @Autowired
    private TransportationOutboundOrderMapper transportationOutboundOrderMapper;

    public TransportationOutboundOrder getOrderByOrderNo(String prefix, String orderNo) {
        return transportationOutboundOrderMapper.getOrderByOrderNo(prefix, orderNo);
    }

    public int insert(String prefix, long projectId, String outboundOrderNo, String channelId, String channelToken, String requestJson, int syncTmsStatus) {
        return transportationOutboundOrderMapper.insert(prefix, projectId, outboundOrderNo, channelId, channelToken, requestJson, syncTmsStatus);
    }

    public List<String> selectListBySyncTmsStatus(String prefix, int status) {
        return transportationOutboundOrderMapper.selectListBySyncTmsStatus(prefix, status);
    }

    public int updateSyncTmsStatus(String prefix, long oid, long dataVersion, int syncTms, String tmsResponse) {
        return transportationOutboundOrderMapper.updateSyncTmsStatus(prefix, oid, dataVersion, syncTms, tmsResponse);
    }
}
