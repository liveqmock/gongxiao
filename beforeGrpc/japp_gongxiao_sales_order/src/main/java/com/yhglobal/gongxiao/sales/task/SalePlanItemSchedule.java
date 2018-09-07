package com.yhglobal.gongxiao.sales.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.sales.dao.SalePlanDao;
import com.yhglobal.gongxiao.sales.dao.SalePlanItemDao;
import com.yhglobal.gongxiao.sales.model.plan.model.SalesPlanItem;
import com.yhglobal.gongxiao.sales.service.PlanSaleItemService;
import com.yhglobal.gongxiao.sales.service.PlanSaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SalePlanItemSchedule {

    private Logger logger = LoggerFactory.getLogger(SalePlanItemSchedule.class);

    @Autowired
    SalePlanDao salePlanDao;

    @Autowired
    SalePlanItemDao salePlanItemDao;

    @Scheduled(cron="0 0 0 * * ?")   //每天秒执行一次
    public void cancelPlanSaleItem(){
        logger.info("=========预售单开始定时任务======");
        try {
            List<SalesPlanItem> salesPlanItemList = salePlanItemDao.selectFailurePlanItemList();
            for (SalesPlanItem salesPlanItem:salesPlanItemList){
                logger.info("==========回滚预售明细==========销售单号={} ,预售明细单号={}",salesPlanItem.getSalesPlanNo(),salesPlanItem.getItemId());
                String salesPlanNo = salesPlanItem.getSalesPlanNo();
                Long itemId = salesPlanItem.getItemId();
                int unallocatedQuantity = salesPlanItem.getUnallocatedQuantity();
                //变更客户预售明细
                int cancelNumber = salePlanItemDao.schedulerCancelOrder(itemId);
                //变更订单预售明细
                salePlanDao.updateReturnSaleQuantity(salesPlanNo,unallocatedQuantity);
                logger.info("==========回滚预售明细==========销售单号={} ,预售明细单号={}, 回滚数量={}",salesPlanItem.getSalesPlanNo(),salesPlanItem.getItemId(),unallocatedQuantity);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
