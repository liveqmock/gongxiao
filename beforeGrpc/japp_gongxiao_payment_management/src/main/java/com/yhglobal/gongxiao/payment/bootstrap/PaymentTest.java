package com.yhglobal.gongxiao.payment.bootstrap;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.payment.service.DistributorAccountService;
import com.yhglobal.gongxiao.payment.service.SupplierAccountService;
import com.yhglobal.gongxiao.payment.service.YhglobalAccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * @author: 葛灿
 */
public class PaymentTest {


    public static void main(String[] args) {
        try {
            RpcHeader rpcHeader = new RpcHeader("test");
            rpcHeader.setUid("111");
            rpcHeader.setUsername("gecan");
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");

            //1、ad账户
            DistributorAccountService distributorAccountService = applicationContext.getBean("distributorAccountServiceImpl", DistributorAccountService.class);
            distributorAccountService.payForSalesOrder(rpcHeader, "cny", 2, 146798161, 100, 700, 100, "test", new Date());
            //2.供应商账户
            SupplierAccountService supplierAccountService = applicationContext.getBean("supplierAccountServiceImpl", SupplierAccountService.class);
            //上账 通
//            supplierAccountService.postSupplierCouponAccount(rpcHeader, "CNY", 1L, 146798161L, 66, null);
//            supplierAccountService.postSupplierPrepaidAccount(rpcHeader, "CNY", 1L, 146798161L, 77, null);
            //采购场景 通
//            supplierAccountService.payForPurchase(rpcHeader, "CNY", 1L, 146798161L, 100, 200, 300, 400, "测试采购单1", new Date());
            //采购确认场景 通
//            supplierAccountService.purchaseAmountConfirm(rpcHeader, "CNY", 1L, 146798161L, 0, 32, 58, "测试采购单1", new Date());
            //低买高卖账户 通
//            supplierAccountService.plusSellHeightAmount(rpcHeader, "CNY", 1L, 146798161L, 300, "测试销售单1", new Date());
//            supplierAccountService.writeoffSellHeightAmount(rpcHeader, "CNY", 1L, 146798161L, 100, new Date());

            //3、越海账户
            YhglobalAccountService yhglobalAccountService = applicationContext.getBean("yhglobalAccountServiceImpl", YhglobalAccountService.class);
            //缓冲账户过账到实收账户 通
//            yhglobalAccountService.yhglobalCouponBufferToReceived(rpcHeader, "CNY", 1L, 146798161L, 100, new Date(), null);
//            yhglobalAccountService.yhglobalPrepaidBufferToReceived(rpcHeader, "CNY", 1L, 146798161L, 100, new Date(), null);
            //实收账户做核销，扣减金额 通
//            yhglobalAccountService.writeoffYhglobalCouponReceivedAccount(rpcHeader, "CNY", 1L, 146798161L, 200, new Date());
//            yhglobalAccountService.writeoffYhglobalPrepaidReceivedAccount(rpcHeader, "CNY", 1L, 146798161L, 200, new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
