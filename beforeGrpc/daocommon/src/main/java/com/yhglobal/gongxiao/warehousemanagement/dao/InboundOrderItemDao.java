package com.yhglobal.gongxiao.warehousemanagement.dao;

import com.yhglobal.gongxiao.warehousemanagement.dao.mapping.InboundOrderItemMapper;
import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrderItem;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class InboundOrderItemDao {

    @Autowired
    InboundOrderItemMapper inboundOrderItemMapper;

    public int inserInboundOrderItemInfo(List<InboundOrderItem> inboundOrderItemList) {
        return inboundOrderItemMapper.inserInboundOrderItemInfo(inboundOrderItemList);
    }

    public List<InboundOrderItem> selectInboundOrderItemInfosById(String projectId, String inventoryNum) {
        return inboundOrderItemMapper.selectInboundOrderItemInfosById(projectId,inventoryNum);
    }

    public int updateInStorageDetailInfo(long rowId,int afterImperfectQuantity,int afterInStockQuantity,int afterInTransitQuantity,int afterRealInStockQuantity) {
        return inboundOrderItemMapper.updateInStorageDetailInfo(rowId,afterImperfectQuantity,afterInStockQuantity,afterInTransitQuantity,afterRealInStockQuantity);
    }

    public List<InboundOrderItem> selectInStorageDetailInfoByPurchaseNo(String projectId, String inventoryNum) {
        return inboundOrderItemMapper.selectInStorageDetailInfoByPurchaseNo(projectId, inventoryNum);
    }

    public List<InboundOrderItem> selectByPurchaseNo(String projectId,String purchaseOrderNO){
        return inboundOrderItemMapper.selectByPurchaseNo(projectId,purchaseOrderNO);
    }

    public int updateByCondition(String projectId,String gongxiaoInboundOrderNo,String productCode,int originalQuantity,int adjustmentQuantity,int targetQuantity) {
        return inboundOrderItemMapper.updateByCondition(projectId,gongxiaoInboundOrderNo,productCode,originalQuantity,adjustmentQuantity,targetQuantity);
    }

    public int deleteOrderByOrderNo(String projectId,String gongxiaoInboundOrderNo){
        return inboundOrderItemMapper.deleteOrderByOrderNo(projectId,gongxiaoInboundOrderNo);
    }

    public int updateIOrderDetailStatus(String projectId,String gongxiaoInboundOrderNo){
        return inboundOrderItemMapper.updateIOrderDetailStatus(projectId,gongxiaoInboundOrderNo);
    }

    public List<InboundOrderItem> selectInboundOrderItemByNo(String gongxiaoInboundOrderNo){
        return inboundOrderItemMapper.selectInboundOrderItemByNo(gongxiaoInboundOrderNo);
    }

    public int cancelOrder(String gongxiaoInboundOrderNo){
        return inboundOrderItemMapper.cancelOrder(gongxiaoInboundOrderNo);
    }

    public int closeOrder(String gongxiaoInboundOrderNo){
        return inboundOrderItemMapper.closeOrder(gongxiaoInboundOrderNo);
    }

    public InboundOrderItem selectOrderItemByNo(String gongxiaoInboundOrderNo,String productCode){
        return inboundOrderItemMapper.selectOrderItemByNo(gongxiaoInboundOrderNo,productCode);
    }
}
