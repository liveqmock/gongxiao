package com.yhglobal.gongxiao.purchase.service;

import com.yhglobal.gongxiao.model.PurchaseOrder;

/**
 * 采购单生成应收返利类\接口
 *
 * @author: 王帅
 */
public interface YhglobalCouponLedgerService
{

     boolean generateYhglobalCouponLedger(PurchaseOrder purchaseOrder) throws Exception;
}
