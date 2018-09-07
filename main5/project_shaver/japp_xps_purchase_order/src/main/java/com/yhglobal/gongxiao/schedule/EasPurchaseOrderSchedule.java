package com.yhglobal.gongxiao.schedule;

import com.yhglobal.gongxiao.common.spring.ApplicationContextProvider;
import com.yhglobal.gongxiao.dao.PurchaseOrderDao;
import com.yhglobal.gongxiao.dao.PurchaseOrderItemDao;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.PurchaseOrder;
import com.yhglobal.gongxiao.model.PurchaseOrderItem;
import com.yhglobal.gongxiao.status.PurchaseEasStatus;
import com.yhglobal.gongxiao.task.SyncPurchaseOrderToEasTask;
import com.yhglobal.gongxiao.util.PropertyUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.TablePrefixUtil;
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

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    PropertyUtil propertyUtil;

    @Scheduled(cron="0 */1 * * * ?")   //每5分钟执行一次
    public void scheduleSynPur2Eas(){
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(new Date().toString(),
                "99",
                "定时任务");
        long projectId = propertyUtil.getTargetProjectId();
        //获取所有未同步给eas的采购订单
        String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);

        List<PurchaseOrder> purchaseOrders = purchaseOrderDao.selectPurchaseOrderListByEasStatus(tablePrefix,PurchaseEasStatus.SYN_EAS_FAILURE.getStatus());
        logger.info("未同步给EAS的采购单有{}个",purchaseOrders.size());
        for (PurchaseOrder purchaseOrder:purchaseOrders){
            long purchaseOrderId = purchaseOrder.getPurchaseOrderId();
            //更新采购单状态为中间态, 避免多线程同时执行造成多次同步
            int row = purchaseOrderDao.updatePurchaseEasStatus(tablePrefix,
                    purchaseOrderId,
                    PurchaseEasStatus.SYN_EAS_FAILURE.getStatus(),
                    PurchaseEasStatus.SYN_EAS_TEMP.getStatus());

            if (row != 1) { //若没有抢到同步的privilege
                continue;
            }
            logger.info("开始同步采购单号={}的采购单到EAS",purchaseOrder.getPurchaseOrderNo());
            List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemDao.selectByOrderNo(tablePrefix,
                    purchaseOrder.getPurchaseOrderNo());

            SyncPurchaseOrderToEasTask task = new SyncPurchaseOrderToEasTask(
                    ApplicationContextProvider.getApplicationContext(),
                    rpcHeader,
                    purchaseOrder,
                    purchaseOrderItems);

            threadPoolTaskExecutor.submit(task);
        }
    }

}
