package com.yhglobal.gongxiao.phjd.sales.dao;

import com.yhglobal.gongxiao.phjd.sales.dao.mapper.SalesOutboundOrderMapper;
import com.yhglobal.gongxiao.phjd.sales.model.SalesOutboundOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 销售订单出库
 *
 * @author weizecheng
 * @date 2018/8/29 11:22
 */
@Repository
public class SalesOutboundOrderDao {
    @Autowired
    private SalesOutboundOrderMapper salesOutboundOrderMapper;

    /**
     * 插入销售订单出库单
     *
     * @author weizecheng
     * @date 2018/8/29 11:22
     * @param prefix 表前缀
     * @param order 销售订单出库
     * @return int
     */
    public int insertSalesOutboundOrder(String prefix, SalesOutboundOrder order){
        return salesOutboundOrderMapper.insert(prefix, order);
    }
    /**
     *根据出库单号获取出库单
     *
     * @author weizecheng
     * @date 2018/8/29 12:20
     * @param prefix 表前缀
     * @param outboundOrderNo  出库单号
     * @return SalesOutboundOrder
     */
    public SalesOutboundOrder getByOutboundOrderNo(String prefix, String outboundOrderNo){
        return salesOutboundOrderMapper.getByOutboundOrderNo(prefix,outboundOrderNo);
    }

    /**
     * 更新Tms状态
     *
     * @author weizecheng
     * @date 2018/8/29 16:23
     * @param prefix 表前缀
     * @param id 出库单Id
     * @param dataVersion 版本号
     * @param status Tms状态
     * @return
     */
    public int updateSyncTms(String prefix, Long id, Long dataVersion, Integer status){
        return salesOutboundOrderMapper.updateSyncToTms(prefix, id, dataVersion, status);
    }

    /**
     * 更新出库时间
     *
     * @author weizecheng
     * @date 2018/8/31 19:07
     */
    public int updateOutboundTime(String prefix,Long oid, Long dataVersion, Integer outboundOrderStatus, Date outboundTime) {
        return salesOutboundOrderMapper.updateOutboundTime(prefix,oid, dataVersion, outboundOrderStatus, outboundTime);
    }

    /**
     * 更新签收
     *
     * @author weizecheng
     * @date 2018/8/31 19:07
     */
    public int updateWhenSigned(String prefix,long oid, long dataVersion, int orderStatus,String tmsOrderNo, String tmsRemark, String signedBy, String postedBy, Date signed, String signedPhone, String transporter) {
        return salesOutboundOrderMapper.updateWhenSigned(prefix,oid, dataVersion,orderStatus, tmsOrderNo, tmsRemark, signedBy, postedBy, signed, signedPhone, transporter);
    }

    /**
     * 获取同步TMS
     *
     * @author weizecheng
     * @date 2018/9/3 18:16
     * @param prefix 表前缀
     * @param syncToTms 同步TMS状态
     * @return int
     */
    public List<String> listByTmsStatus(String prefix, Integer syncToTms ){
        return salesOutboundOrderMapper.listByTmsStatus(prefix,syncToTms);
    }
}
