package com.yhglobal.gongxiao.transportation.dao;

import com.yhglobal.gongxiao.transportation.dao.mapping.CancelledOutboundOrderMapper;
import com.yhglobal.gongxiao.transportation.model.TransportationCancelledOutboundOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 取消出库单DAO
 *
 * @author 葛灿
 */
@Repository
public class CancelledOutboundOrderDao {

    @Autowired
    private CancelledOutboundOrderMapper cancelledOutboundOrderMapper;

    /**
     * 插入对象
     *
     * @param prefix        前缀
     * @param jsonStr       发送给tms的json
     * @param syncTmsStatus 同步tms状态
     * @param dataVersion   数据版本
     * @return 插入成功的记录数
     */
    public int insert(String prefix, String outboundOrderNo, String jsonStr, long syncTmsStatus, long dataVersion) {
        return cancelledOutboundOrderMapper.insert(prefix, outboundOrderNo, jsonStr, syncTmsStatus, dataVersion);
    }

    /**
     * 根据出库单号获取
     *
     * @param prefix          前缀
     * @param outboundOrderNo 出库单号
     * @return 取消的出库单信息
     */
    public TransportationCancelledOutboundOrder getOrderByOutboundOrderNo(String prefix, String outboundOrderNo) {
        return cancelledOutboundOrderMapper.getOrderByOutboundOrderNo(prefix, outboundOrderNo);
    }

    /**
     * 更新同步TMS状态
     *
     * @param prefix          前缀
     * @param oid 主键
     * @param dataVersion     数据版本
     * @param syncTmsStatus   同步TMS状态
     * @param tmsResponse     tms响应
     * @return 更新成功记录数
     */
    public int updateSyncTmsStatus(String prefix, long oid, long dataVersion, int syncTmsStatus, String tmsResponse) {
        return cancelledOutboundOrderMapper.updateSyncTmsStatus(prefix, oid, dataVersion, syncTmsStatus, tmsResponse);
    }

    /**
     * 根据同步TMS状态获取单号清单
     *
     * @param prefix 表前缀
     * @param status 同步tms状态
     * @return LIST<出库单号>
     */
    public List<String> selectListBySyncTmsStatus(String prefix, int status) {
        return cancelledOutboundOrderMapper.selectListBySyncTmsStatus(prefix, status);
    }
}
