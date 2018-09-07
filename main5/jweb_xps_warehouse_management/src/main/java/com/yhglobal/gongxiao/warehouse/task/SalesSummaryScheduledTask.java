package com.yhglobal.gongxiao.warehouse.task;

import com.yhglobal.gongxiao.warehouse.dao.OutBoundOrderItemDao;
import com.yhglobal.gongxiao.warehouse.dao.ReportPsiOverviewDao;
import com.yhglobal.gongxiao.warehouse.dao.WmsOutboundItemDao;
import com.yhglobal.gongxiao.warehouse.model.OutboundOrderItem;
import com.yhglobal.gongxiao.warehouse.model.WmsOutboundRecordItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 葛灿
 */
@Service
public class SalesSummaryScheduledTask {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    OutBoundOrderItemDao outBoundOrderItemDao;

    @Autowired
    WmsOutboundItemDao wmsOutboundItemDao;

    @Autowired
    ReportPsiOverviewDao reportPsiOverviewDao;

    /**
     * 获取出库金额统计
     * 每个月第一天凌晨 01:00 执行
     */
//    @Scheduled(cron = "0 0 1 1 * ? ")
    @Scheduled(cron = "0 25 19 ? * *")
//    @Scheduled(fixedDelay = 50000)  测试使用
    public void get() {
        try {
            long totalAmount = 0L;
            long projectId = 146798161L;
            String prefix = "shaver"; //todo 获取所有的表前缀
            LocalDate now = LocalDate.now();
            logger.info("# [IN] Scheduled Task: get outbound amount summary.");
            // 日期字符串 yyyy-MM-dd
            String dateString = now.minusMonths(1).toString();
            String yearMonth = dateString.replace("-", "").substring(0, 6);
            // 根据出库时间查询所有的明细
            List<WmsOutboundRecordItem> lastMonthRecords = wmsOutboundItemDao.getListByYearAndMonth(projectId, dateString);
            for (WmsOutboundRecordItem outboundRecordItem : lastMonthRecords) {
                String gongxiaoOutboundOrderNo = outboundRecordItem.getGongxiaoOutboundOrderNo();
                String productCode = outboundRecordItem.getProductCode();
                int inventoryType = outboundRecordItem.getInventoryType();
                String batchNo = outboundRecordItem.getBatchNo();
                OutboundOrderItem outboundOrderItem = outBoundOrderItemDao.selectItemByNoAndProductCodeAndBatchNo(gongxiaoOutboundOrderNo, batchNo, productCode, inventoryType, prefix);
                int itemTotalQuantity = outboundOrderItem.getTotalQuantity();
                long wholesalePrice = outboundOrderItem.getWholesalePrice();
                totalAmount += Math.round(wholesalePrice * itemTotalQuantity / 10000.0);
            }
            int update = reportPsiOverviewDao.updateSalesAmountByYearMonth(prefix, yearMonth, totalAmount);
            if (update == 1) {
                logger.info("# [OUT] update successful!");
            } else {
                logger.info("# [OUT] update FAILED! yearMonth={}", yearMonth);
            }

        } catch (Exception e) {
            logger.error("# [OUT] errorMessage: " + e.getMessage());
            throw e;
        }
    }
}
