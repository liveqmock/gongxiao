package com.yhglobal.gongxiao.dao;

import com.yhglobal.gongxiao.dao.mapper.PurchaseOrderItemMapper;
import com.yhglobal.gongxiao.model.PurchaseOrderItem;
import com.yhglobal.gongxiao.purchase.dto.PurchaseOrderItemBackWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 采购单货品信息
 *
 * @author: 陈浩
 * @create: 2018-02-06 17:25
 **/
@Repository
public class PurchaseOrderItemDao {

    @Autowired
    PurchaseOrderItemMapper purchaseOrderItemMapper;

    /**
     * 插入采购单货品信息
     * @param purchaseOrderItem
     * @return
     */
    public int insert(PurchaseOrderItem purchaseOrderItem){
        return purchaseOrderItemMapper.insert(purchaseOrderItem);
    }
    /**
     * 通过采购单获取采购单货品列表
     * @param purchaseOrderNo 采购单号
     * @return
     */
    public List<PurchaseOrderItem> selectByOrderNo(String  purchaseOrderNo){
        return purchaseOrderItemMapper.selectByOrderNo(purchaseOrderNo);
    }

    /**
     * 模糊查询某订单下的货品
     * @param purchaseOrderNo 采购单
     * @param productCode 货品
     * @return
     */
    public List<PurchaseOrderItem> selectByNoAndProduct(String purchaseOrderNo,String productCode){
        return purchaseOrderItemMapper.selectByNoAndProduct(purchaseOrderNo,productCode);
    }
    /**
     * 获取采购明细Id
     * @param purchaseItemId 货品明细ID
     * @return
     */
    public PurchaseOrderItem selectByOrderItemId(long purchaseItemId){
        return purchaseOrderItemMapper.selectByItemId(purchaseItemId);
    }

    /**
     * 通过采购单号和产品id获取采购货品信息
     * @param purchaseOrderNo
     * @param productCode
     * @return
     */
    public PurchaseOrderItem selectByOrderNoAndProduct(String  purchaseOrderNo,String productCode){
        return purchaseOrderItemMapper.selectByOrderNoAndProduct(purchaseOrderNo,productCode);
    }

    /**
     * 回写采购单信息
     * @param purchaseOrderItemBackWrite 需要回写的信息
     * @return
     */
    public int updateBackWrite(PurchaseOrderItemBackWrite purchaseOrderItemBackWrite){
        return purchaseOrderItemMapper.updateBackWrite(purchaseOrderItemBackWrite);
    }

    public int updateBackWriteItem(PurchaseOrderItemBackWrite purchaseOrderItemBackWrite){
        return purchaseOrderItemMapper.updateBackWritePurchaseItem(purchaseOrderItemBackWrite);
    }

    /**
     * 更新货品入库列表
     * @param purchaseItemId 采购入库货品id
     * @param ongoingInboundOrderInfo 正在收货的预约收货单号
     * @param finishedInboundOrderInfo 完成收货的预约收货单号
     * @return
     */
    public int updateInboundOrder(long purchaseItemId,String uniqueNumber,String ongoingInboundOrderInfo,  String finishedInboundOrderInfo){
        return purchaseOrderItemMapper.updateInboundOrder(purchaseItemId,ongoingInboundOrderInfo,finishedInboundOrderInfo);
    }

    /**
     * 更新EAS信息
     * @param purchaseItemId
     * @param easEntryId
     * @param easMateriaCode
     * @return
     */
    public int updateEasInfo(long purchaseItemId, String easEntryId, String easMateriaCode){
        return purchaseOrderItemMapper.updateEasInfo(purchaseItemId,easEntryId,easMateriaCode);
    }

    /**
     * 更新采购单货品信息
     * @param record
     * @return
     */
    public int updatePurchaseItem(PurchaseOrderItem record){
        return purchaseOrderItemMapper.updateBPurchase(record);
    }
}
