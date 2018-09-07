package com.yhglobal.gongxiao.dao;

import com.yhglobal.gongxiao.dao.mapper.PurchaseReverseDeliveryMapper;
import com.yhglobal.gongxiao.model.PurchaseReverseDelivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 采购逆向退货预约出库
 *
 * @author: 陈浩
 **/
@Repository
public class PurchaseReverseDeliveryDao {

    @Autowired
    PurchaseReverseDeliveryMapper purchaseReverseDeliveryMapper;

    /**
     * 插入采购逆向退货记录
     * @param purchaseReverseDelivery
     * @return
     */
    public int insert(PurchaseReverseDelivery purchaseReverseDelivery){
        return purchaseReverseDeliveryMapper.insert(purchaseReverseDelivery);
    }

    /**
     * 通过仓储模块那边给的回执获取采购逆向退货预约出库单信息
     *
     * @param gongxiaoWarehouseOutboundOrderNo 仓储模块那边给的回执
     * @return
     */
    public PurchaseReverseDelivery getDeliveryByOutboundOrderNo(String gongxiaoWarehouseOutboundOrderNo) {
        return purchaseReverseDeliveryMapper.getDeliveryByOutboundOrderNo(gongxiaoWarehouseOutboundOrderNo);
    }

    /**
     * 通过退货单号获取采购逆向退货列表
     * @param returnOrderNo 采购退货单号
     * @return
     */
    public List<PurchaseReverseDelivery> selectByReturnOrder(String returnOrderNo) {
        return purchaseReverseDeliveryMapper.selectByReturnOrder(returnOrderNo);
    }

    /**
     * 更新出库单信息
     * @param outboundOrderNo 预约出库单号
     * @param reverseStatus 逆向流程状态
     * @param totalQuantity 预约出库数量
     * @param productInfo 货品信息
     * @return
     */
    public int updateByOutboundOrderNo(String outboundOrderNo,byte reverseStatus,int totalQuantity,String productInfo){
        return purchaseReverseDeliveryMapper.updateByOutboundOrderNo(outboundOrderNo,reverseStatus,totalQuantity,productInfo);
    }

    /**
     * 更新采退记录状态
     *
     * @param outboundOrderNo 预约出库单号
     * @param reverseStatus 状态
     * @return
     */
    public int updateStatus(String outboundOrderNo,byte reverseStatus){
        return purchaseReverseDeliveryMapper.updateStatus(outboundOrderNo,reverseStatus);
    }

}
