package com.yhglobal.gongxiao.warehouse.report;

import com.yhglobal.gongxiao.grpc.client.WarehouseRpcStubStore;
import com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice.InventoryMonthAmoundServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice.InventoryMonthAmountStructure;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.warehouse.dao.ReportPsiOverviewDao;
import com.yhglobal.gongxiao.warehouse.model.dto.ShaverReportPsiOverview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * 月度进销存psi
 */
@Service
public class InventoryMonthPsiSchedule {

    private static Logger logger = LoggerFactory.getLogger(InventoryMonthPsiSchedule.class);

    @Autowired
    ReportPsiOverviewDao reportPsiOverviewDao;

    /**
     * 计算每月库存总值和库存周转率
     */
//    @Scheduled(cron = "0 0 2 1 * ? ")
    @Scheduled(cron = "0 4 20 ? * *")
    public void calculateInventoryMonth() {
        try {
            GongxiaoRpc.RpcHeader rpcHeader = GongxiaoRpc.RpcHeader.newBuilder().setUsername("fenxiao").setTraceId("345236576").setUid("1").build();
            long projectId = 146798161L;
            String prefix = "shaver"; //todo 获取所有的表前缀
            logger.info("# [IN] calculateInventoryMonth Task: get inventory amount summary.");
            // 查询所有的psi记录
            List<ShaverReportPsiOverview> reportPsiOverviewList = reportPsiOverviewDao.selectAllData();
            for (ShaverReportPsiOverview record : reportPsiOverviewList) {
                if (record.getInventoryAmount() == 0) {    //如果库存金额为0，需要计算
                    InventoryMonthAmoundServiceGrpc.InventoryMonthAmoundServiceBlockingStub inventoryMonthAmoundService = WarehouseRpcStubStore.getRpcStub(InventoryMonthAmoundServiceGrpc.InventoryMonthAmoundServiceBlockingStub.class);
                    InventoryMonthAmountStructure.GetInventoryMonthAmountRequest getDistributorBusinessByIdReq = InventoryMonthAmountStructure.GetInventoryMonthAmountRequest.newBuilder().setRpcHeader(rpcHeader).setProjectId(projectId).setYearMonth(String.valueOf(record.getYearMonth())).build();
                    InventoryMonthAmountStructure.GetInventoryMonthAmountRespon getInventoryMonthAmountRespon = inventoryMonthAmoundService.getInventoryMonthAmount(getDistributorBusinessByIdReq);
                    InventoryMonthAmountStructure.InventoryMonthAmount inventoryMonthAmount = getInventoryMonthAmountRespon.getInventoryMonthAmount();
                    float inventoryTurnoverRate = ((inventoryMonthAmount.getSumOfmoney() / 1000000) / record.getSaleAmount()) * 3;
                    int update = reportPsiOverviewDao.updateInventoryAmountByYearMonth(String.valueOf(record.getYearMonth()), inventoryMonthAmount.getSumOfmoney(), inventoryTurnoverRate, prefix);

                    if (update == 1) {
                        logger.info("# [OUT] update successful!");
                    } else {
                        logger.info("# [OUT] update FAILED! yearMonth={}", String.valueOf(record.getYearMonth()));
                    }
                }

            }

        } catch (Exception e) {
            logger.error("# [OUT] errorMessage: " + e.getMessage());
            throw e;
        }
    }

    /**
     * 因为当前月的预售是变动的，所以在计算上个月的库存周转率也是变动的
     * 每天更新上个月的库存周转率
     */
//    @Scheduled(cron = "0 0 1 1 * ? ")
//    public void updateInventoryMonth() {
//        try {
//            long totalAmount = 0L;
//            long projectId = 146798161L;
//            String prefix = "shaver";
//            LocalDate now = LocalDate.now();
//            logger.info("# [IN] updateInventoryMonth Task: get inventory amount summary.");
//            // 日期字符串 yyyy-MM-dd
//            String dateString = now.minusMonths(1).toString();
//            String yearMonth = dateString.replace("-", "").substring(0, 6);
//            // 根据出库时间查询所有的明细
//
//            int update = reportPsiOverviewDao.updateSalesAmountByYearMonth(prefix, yearMonth, totalAmount);
//            if (update == 1) {
//                logger.info("# [OUT] update successful!");
//            } else {
//                logger.info("# [OUT] update FAILED! yearMonth={}", yearMonth);
//            }
//
//        } catch (Exception e) {
//            logger.error("# [OUT] errorMessage: " + e.getMessage());
//            throw e;
//        }
//    }


}
