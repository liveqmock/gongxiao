package com.yhglobal.gongxiao.purchase.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 回写控制类
 *
 * @author: 陈浩
 * @create: 2018-03-05 16:47
 **/
@Controller
@RequestMapping("/purchase")
public class WriteBackController {

    @RequestMapping(value = "/writePurchaseOrder", method = RequestMethod.GET)
    @ResponseBody
    public void writePurchaseOrder(HttpServletResponse response){


//        List<PurchaseWriteBackItem> purchaseWriteBackList  = new ArrayList<>();
//        PurchaseWriteBackOrder purchaseWriteBackOrder = new PurchaseWriteBackOrder();
//        purchaseWriteBackOrder.setPurchaseOrderNo("P1520413885394");
//        PurchaseWriteBackItem item1 = new PurchaseWriteBackItem();
//        item1.setPurchaseItemId(Long.parseLong("1520413885461"));
//        item1.setInboundOrderItemNo("111");
//        item1.setInStockQuantity(11);
//        item1.setInStockQuantity(12);
//        purchaseWriteBackList.add(item1);
//        PurchaseWriteBackItem item2 = new PurchaseWriteBackItem();
//        item2.setPurchaseItemId(Long.parseLong("1520413377023"));
//        item2.setInboundOrderItemNo("222");
//        item2.setInStockQuantity(22);
//        item2.setInStockQuantity(23);
//        purchaseWriteBackList.add(item2);
//        purchaseWriteBackService.completePlanInstock("P1520413885394","1520846157758",purchaseWriteBackList);
        return;
    }


}
