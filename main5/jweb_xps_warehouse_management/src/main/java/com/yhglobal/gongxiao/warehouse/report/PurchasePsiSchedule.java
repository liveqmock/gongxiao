package com.yhglobal.gongxiao.warehouse.report;

import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.warehouse.dao.InboundOrderItemDao;
import com.yhglobal.gongxiao.warehouse.dao.ReportPsiOverviewDao;
import com.yhglobal.gongxiao.warehouse.dao.WmsInboundItemDao;
import com.yhglobal.gongxiao.warehouse.model.InboundOrderItem;
import com.yhglobal.gongxiao.warehouse.model.WmsIntboundRecordItem;
import com.yhglobal.gongxiao.warehouse.model.dto.ShaverReportPsiOverview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Service
public class PurchasePsiSchedule {

    private static Logger logger = LoggerFactory.getLogger(PurchasePsiSchedule.class);

    @Autowired
    WmsInboundItemDao wmsInboundItemDao;

    @Autowired
    InboundOrderItemDao inboundOrderItemDao;

    @Autowired
    ReportPsiOverviewDao reportPsiOverviewDao;

    /**
     * 1. 只计算往月没有计算过的数据
     * 2. 通过Map来给各月份的数据分类
     */
    @Scheduled(cron = "0 */1 * * * ?")   //每3分钟执行一次
    public void countPurchaseAmount() {
        try {
            logger.info("开始按月计算采购金额");
            //获取已计算的的所有月份 用来筛选数据
            List<ShaverReportPsiOverview> shaverReportPsiOverviews = reportPsiOverviewDao.selectAllData();
            StringBuilder monthString = new StringBuilder();
            for (ShaverReportPsiOverview shaverReportPsiOverview : shaverReportPsiOverviews) {
                monthString.append(shaverReportPsiOverview.getYearMonth());
            }
            //获取近6个月的入库数据
            Date date = new Date();
            Date beforeSixMonth = DateUtil.getBeforeSeveralMonthDate(date, 6);
            Date initDate = DateUtil.initThisMonth(date);
            Map<Long, Long> map = new HashMap<>();
            List<WmsIntboundRecordItem> wmsIntboundRecordItems = wmsInboundItemDao.selectItemGroupByMonth(beforeSixMonth, initDate);
            //数据解析
            int i = 0;
            logger.info("开始计算数据");
            for (WmsIntboundRecordItem wmsIntboundRecordItem : wmsIntboundRecordItems) {
                Date createTime = wmsIntboundRecordItem.getCreateTime();
                long yearMonth = DateUtil.getYearMonth(createTime);
                if (monthString.toString().contains(yearMonth + "")) {
                    //如果该月份的数据已经计算过,无需再算
                    continue;
                }
                //获取对应的入库数据,从而获取采购价
                String gongxiaoInboundOrderNo = wmsIntboundRecordItem.getGongxiaoInboundOrderNo();
                String productCode = wmsIntboundRecordItem.getProductCode();
                InboundOrderItem inboundOrderItem = inboundOrderItemDao.selectOrderItemByNo(gongxiaoInboundOrderNo, productCode, "shaver");
                long purchasePrice = inboundOrderItem.getPurchasePrice();
                int inStockQuantity = wmsIntboundRecordItem.getInStockQuantity();

                Long purchaseMoney = map.get(yearMonth);
                if (purchaseMoney == null) {
                    purchaseMoney = (Long) purchasePrice * inStockQuantity / FXConstant.MILLION * FXConstant.HUNDRED;
                    logger.info("计算次数{},年月{},采购金额{}", i++, yearMonth, purchaseMoney);
                    map.put(yearMonth, purchaseMoney);
                } else {
                    purchaseMoney += (Long) purchasePrice * inStockQuantity / FXConstant.MILLION * FXConstant.HUNDRED;
                    logger.info("计算次数{},年月{},采购金额{}", i++, yearMonth, purchaseMoney);
                }
            }
            //插入数据
            logger.info("计算完成,开始将计算后的数据入库,需入库的数据条数={}", map.size());
            for (long key : map.keySet()) {
                ShaverReportPsiOverview shaverReportPsiOverview = new ShaverReportPsiOverview();
                shaverReportPsiOverview.setYearMonth(key);
                shaverReportPsiOverview.setPurchaseAmount(map.get(key));
                shaverReportPsiOverview.setSaleAmount((long)0);
                shaverReportPsiOverview.setInventoryAmount((long)0);
                shaverReportPsiOverview.setInventoryTurnoverRate((long)0);
                reportPsiOverviewDao.insertPurchaseData(shaverReportPsiOverview);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
