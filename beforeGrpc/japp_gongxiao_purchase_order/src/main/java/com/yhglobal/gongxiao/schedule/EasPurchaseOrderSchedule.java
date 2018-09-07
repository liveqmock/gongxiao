package com.yhglobal.gongxiao.schedule;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.spring.ApplicationContextProvider;
import com.yhglobal.gongxiao.dao.PurchaseOrderDao;
import com.yhglobal.gongxiao.dao.PurchaseOrderItemDao;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.foundation.project.service.ProjectService;
import com.yhglobal.gongxiao.foundation.supplier.service.SupplierService;
import com.yhglobal.gongxiao.foundation.warehouse.service.WarehouseService;
import com.yhglobal.gongxiao.model.PurchaseOrder;
import com.yhglobal.gongxiao.model.PurchaseOrderItem;
import com.yhglobal.gongxiao.payment.service.SupplierAccountService;
import com.yhglobal.gongxiao.status.PurchaseEasStatus;
import com.yhglobal.gongxiao.task.SyncPurchaseOrderToEasTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 采购订单同步给EAS 定时任务
 *
 *
 * @author: 陈浩
 **/

// Seconds Minutes Hours DayofMonth Month DayofWeek
//     Seconds:可出现", - * /"四个字符，有效范围为0-59的整数
//     Minutes:可出现", - * /"四个字符，有效范围为0-59的整数
//     Hours:可出现", - * /"四个字符，有效范围为0-23的整数
//     DayofMonth:可出现", - * / ? L W C"八个字符，有效范围为0-31的整数
//     Month:可出现", - * /"四个字符，有效范围为1-12的整数或JAN-DEc
//     DayofWeek:可出现", - * / ? L C #"四个字符，有效范围为1-7的整数或SUN-SAT两个范围。1表示星期天，2表示星期一， 依次类推
//     Cron表达式范例：
//     每隔5秒执行一次：*/5 * * * * ?
//     每隔1分钟执行一次：0 */1 * * * ?
//     每天23点执行一次：0 0 23 * * ?
//     每天凌晨1点执行一次：0 0 1 * * ?
//     每月1号凌晨1点执行一次：0 0 1 1 * ?
//     每月最后一天23点执行一次：0 0 23 L * ?
//     每周星期天凌晨1点实行一次：0 0 1 ? * L
//     在26分、29分、33分执行一次：0 26,29,33 * * * ?
//     每天的0点、13点、18点、21点都执行一次：0 0 0,13,18,21 * * ?

@Component
public class EasPurchaseOrderSchedule {

    private Logger logger = LoggerFactory.getLogger(EasPurchaseOrderSchedule.class);

    @Autowired
    PurchaseOrderDao purchaseOrderDao;

    @Autowired
    PurchaseOrderItemDao purchaseOrderItemDao;

    @Reference
    SupplierService supplierService; //供应商

    @Reference
    ProjectService projectService; //项目信息

    @Reference
    WarehouseService warehouseService; //仓库信息

    @Reference
    ProductService productService;  //货品信息

    @Reference
    SupplierAccountService supplierAccountService; //供应商上账庄户

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;


    @Scheduled(cron="0 0 10 * * ?")   //每5分钟执行一次
    public void scheduleSynPur2Eas(){
        RpcHeader rpcHeader = new RpcHeader();
        rpcHeader.setTraceId(new Date().toString());
        rpcHeader.setUid("99");
        rpcHeader.setUsername("定时任务");
        //获取所有未同步给eas的采购订单
        List<PurchaseOrder> purchaseOrders = purchaseOrderDao.selectPurchaseOrderListByEasStatus(PurchaseEasStatus.SYN_EAS_FAILURE.getStatus());
        logger.info("未同步给EAS的采购单有{}个",purchaseOrders.size());
        for (PurchaseOrder purchaseOrder:purchaseOrders){
            long purchaseOrderId = purchaseOrder.getPurchaseOrderId();
            //更新采购单状态为中间态,中间态采购单不被其他地方才做`
            int row = purchaseOrderDao.updatePurchaseEasStatus(purchaseOrderId,
                    PurchaseEasStatus.SYN_EAS_FAILURE.getStatus(),
                    PurchaseEasStatus.SYN_EAS_TEMP.getStatus());

            if (row != 1) { //若没有抢到同步的privilege
                continue;
            }
            logger.info("开始同步采购单号={}的采购单到EAS",purchaseOrder.getPurchaseOrderNo());
            List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemDao.selectByOrderNo(purchaseOrder.getPurchaseOrderNo());

            SyncPurchaseOrderToEasTask task = new SyncPurchaseOrderToEasTask(ApplicationContextProvider.getApplicationContext(),
                    rpcHeader,
                    projectService,
                    supplierService,
                    warehouseService,
                    productService,
                    supplierAccountService,
                    purchaseOrder,
                    purchaseOrderItems);

            threadPoolTaskExecutor.submit(task);
        }
    }

}
