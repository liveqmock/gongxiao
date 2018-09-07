package com.yhglobal.gongxiao.dao;

import com.yhglobal.gongxiao.dao.mapper.PurchaseReturnedOrderItemMapper;
import com.yhglobal.gongxiao.model.PurchaseReturnedOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 采购退货明细
 *
 * @author: 陈浩
 * @create: 2018-03-16 11:34
 **/
@Repository
public class PurchaseReturnedOrderItemDao {
    @Autowired
    PurchaseReturnedOrderItemMapper purchaseReturnedOrderItemMapper;

    public int insert(PurchaseReturnedOrderItem record){
        return purchaseReturnedOrderItemMapper.insert(record);
    }

    public PurchaseReturnedOrderItem getByPrimaryKey(Long rowId){
        return purchaseReturnedOrderItemMapper.selectByPrimaryKey(rowId);
    }

    /**
     * 通过采购退货单号获取采购退货单明细
     * @param purchaseReturnedOrderNo 采购退货单号
     * @return
     */
    public List<PurchaseReturnedOrderItem> selectByReturnedOrderNo(String  purchaseReturnedOrderNo){
        return purchaseReturnedOrderItemMapper.selectByReturnedOrderNo(purchaseReturnedOrderNo);
    }

    /**
     * 更新货品信息
     * @param rowId
     * @param itemStatus 订单状态
     * @param totalOutStockQuantity 总出库数量
     * @param outStockImperfectQuantity 残次品数量
     * @param deliveredQuantity 签收数量
     * @return
     */
    public int updateByReturnOrderNo(long rowId,
                                     byte itemStatus,
                                     int totalOutStockQuantity,
                                     int outStockImperfectQuantity,
                                     int deliveredQuantity){
        return purchaseReturnedOrderItemMapper.updateByReturnOrderNo(rowId,itemStatus,totalOutStockQuantity,outStockImperfectQuantity,deliveredQuantity);
    }
}
