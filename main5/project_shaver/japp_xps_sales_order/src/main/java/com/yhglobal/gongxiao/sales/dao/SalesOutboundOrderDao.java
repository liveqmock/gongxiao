package com.yhglobal.gongxiao.sales.dao;

import com.yhglobal.gongxiao.sales.dao.mapping.SalesOutboundOrderMapper;
import com.yhglobal.gongxiao.sales.model.SalesOutboundOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author 葛灿
 */
@Repository
public class SalesOutboundOrderDao {

    @Autowired
    SalesOutboundOrderMapper salesOutboundOrderMapper;

    public SalesOutboundOrder getOrderByOutboundNo(String prefix,String outboundNo) {
        return salesOutboundOrderMapper.getOrderByOutboundNo(prefix,outboundNo);
    }

    public int insert(String prefix,SalesOutboundOrder order) {
        return salesOutboundOrderMapper.insert(prefix,order);
    }

//    public int update(SalesOutboundOrder order) {
//        return salesOutboundOrderMapper.update(order);
//    }


    /**
     * 根据同步tms状态状态 查找所有的单号
     *
     * @param status 同步tms状态
     * @return List<出库单号>
     */
    public List<String> selectListByTmsStatus(String prefix,int status) {
        return salesOutboundOrderMapper.selectListByTmsStatus(prefix,status);
    }

    /**
     * 修改同步tms状态
     *
     * @param oid         主键id
     * @param dataVersion 数据版本
     * @param syncTms     同步tms状态
     * @return 更新成功条数
     */
    public int updateSyncTmsStatus(String prefix,long oid, long dataVersion, int syncTms) {
        return salesOutboundOrderMapper.updateSyncTmsStatus(prefix,oid, dataVersion, syncTms);
    }

    /**
     * 修改出库完成时间
     *
     * @param oid                 主键id
     * @param dataVersion         数据版本
     * @param outboundOrderStatus 出库单状态
     * @param outboundTime        出库时间
     * @return 更新成功条数
     */
    public int updateOutboundTime(String prefix,long oid, long dataVersion, int outboundOrderStatus, Date outboundTime) {
        return salesOutboundOrderMapper.updateOutboundTime(prefix,oid, dataVersion, outboundOrderStatus, outboundTime);
    }

    /**
     * 更新签收信息
     *
     * @param oid         主键
     * @param dataVersion 数据版本
     * @param tmsOrderNo  tms运单号
     * @param tmsRemark   备注
     * @param signedBy    签收人
     * @param postedBy    签收人
     * @param signed      签收时间
     * @param signedPhone 签收电话
     * @param transporter 运输商
     * @return
     */
    public int updateWhenSigned(String prefix,long oid, long dataVersion, int orderStatus,String tmsOrderNo, String tmsRemark, String signedBy, String postedBy, Date signed, String signedPhone, String transporter) {
        return salesOutboundOrderMapper.updateWhenSigned(prefix,oid, dataVersion,orderStatus, tmsOrderNo, tmsRemark, signedBy, postedBy, signed, signedPhone, transporter);
    }
}
