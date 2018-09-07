package com.yhglobal.gongxiao.warehouse.dao;

import com.yhglobal.gongxiao.warehouse.dao.mapping.InboundOrderItemMapper;
import com.yhglobal.gongxiao.warehouse.model.InboundOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class InboundOrderItemDao {

    @Autowired
    InboundOrderItemMapper inboundOrderItemMapper;

    public int inserInboundOrderItemInfo(List<InboundOrderItem> inboundOrderItemList,String prefix) {
        return inboundOrderItemMapper.inserInboundOrderItemInfo(inboundOrderItemList,prefix);
    }

    public List<InboundOrderItem> selectInboundOrderItemInfosById(String projectId, String inventoryNum,String prefix) {
        return inboundOrderItemMapper.selectInboundOrderItemInfosById(projectId,inventoryNum,prefix);
    }

    public int updateInStorageDetailInfo(long rowId,int afterImperfectQuantity,int afterInStockQuantity,int afterInTransitQuantity,int afterRealInStockQuantity,String prefix) {
        return inboundOrderItemMapper.updateInStorageDetailInfo(rowId,afterImperfectQuantity,afterInStockQuantity,afterInTransitQuantity,afterRealInStockQuantity,prefix);
    }

    public List<InboundOrderItem> selectInStorageDetailInfoByPurchaseNo(String projectId, String inventoryNum,String prefix) {
        return inboundOrderItemMapper.selectInStorageDetailInfoByPurchaseNo(projectId, inventoryNum,prefix);
    }

    public List<InboundOrderItem> selectByPurchaseNo(String projectId,String purchaseOrderNO,String prefix){
        return inboundOrderItemMapper.selectByPurchaseNo(projectId,purchaseOrderNO,prefix);
    }

    public List<InboundOrderItem> selectInboundOrderItemByNo(String gongxiaoInboundOrderNo,String prefix){
        return inboundOrderItemMapper.selectInboundOrderItemByNo(gongxiaoInboundOrderNo,prefix);
    }

    public int cancelOrder(String gongxiaoInboundOrderNo,String prefix){
        return inboundOrderItemMapper.cancelOrder(gongxiaoInboundOrderNo,prefix);
    }

    public int closeOrder(String gongxiaoInboundOrderNo,String prefix){
        return inboundOrderItemMapper.closeOrder(gongxiaoInboundOrderNo,prefix);
    }

    public InboundOrderItem selectOrderItemByNo(String gongxiaoInboundOrderNo,String productCode,String prefix){
        return inboundOrderItemMapper.selectOrderItemByNo(gongxiaoInboundOrderNo,productCode,prefix);
    }
}
