package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.inventory.service.InventorySyncService;
import com.yhglobal.gongxiao.warehousemanagement.model.AllocationOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.AllocationOrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 *  *** 暂时不用 预留 ***
 *
 * 根据调拨单更新库存
 * 调拨暂时未做
 */
public class UpdateInventoryTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(UpdateInventoryTask.class);

    private RpcHeader rpcHeader;
    private ApplicationContext applicationContext;
    private AllocationOrder allocationOrder;
    private List<AllocationOrderItem> allocationOrderItemList;


    public UpdateInventoryTask(RpcHeader rpcHeader, ApplicationContext applicationContext, AllocationOrder allocationOrder, List<AllocationOrderItem> allocationOrderItemList) {
        this.rpcHeader = rpcHeader;
        this.applicationContext = applicationContext;
        this.allocationOrder = allocationOrder;
        this.allocationOrderItemList = allocationOrderItemList;
    }

    @Override
    public void run() {
        /// TODO: 2018/5/8 添加任务类：1、项目之间仓内调拨 2、项目之间跨仓调拨 3、项目内跨仓调拨


    }

}
