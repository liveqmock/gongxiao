package com.yhglobal.gongxiao.dao;

import com.yhglobal.gongxiao.dao.mapper.PurchaseReturnedOrderMapper;
import com.yhglobal.gongxiao.model.PurchaseReturnedOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 采购退货
 *
 * @author: 陈浩
 * @create: 2018-03-16 11:33
 **/
@Repository
public class PurchaseReturnedOrderDao {

    @Autowired
    PurchaseReturnedOrderMapper purchaseReturnedOrderMapper;

    public int insert(PurchaseReturnedOrder record) {
        return purchaseReturnedOrderMapper.insert(record);
    }

    public PurchaseReturnedOrder getByReturnedOrderNo(String purchaseReturnedOrderNo) {
        return purchaseReturnedOrderMapper.getByReturnedOrderNo(purchaseReturnedOrderNo);
    }

    public List<PurchaseReturnedOrder> selectPurchaseReturnList(String projectId,
                                                             String warehouseId,
                                                             String orderNumber,
                                                             String startDate,
                                                             String endDate) {
        return purchaseReturnedOrderMapper.getPurchaseReturnList(projectId,warehouseId, orderNumber, startDate, endDate);
    }

    /**
     * 回写采购退货订单信息
     * @param purchaseReturnedOrderNo 采购退货单号
     * @param returnedOrderStatus 退货单状态
     * @param outStockQuantity 出库数量
     * @param deliveredQuantity 签收数量
     * @return
     */
    public int updateByReturnOrderNo(String purchaseReturnedOrderNo,byte returnedOrderStatus,int outStockQuantity,int deliveredQuantity){
        return purchaseReturnedOrderMapper.updateByReturnOrderNo(purchaseReturnedOrderNo,returnedOrderStatus,outStockQuantity,deliveredQuantity);
    }

}
