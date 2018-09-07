package com.yhglobal.gongxiao.inventory.service;

import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.productBasic.dao.ProductBasicDao;
import com.yhglobal.gongxiao.foundation.project.dao.ProjectDao;
import com.yhglobal.gongxiao.foundation.project.model.Project;
import com.yhglobal.gongxiao.foundation.warehouse.dao.ImportAndExportAccountDao;
import com.yhglobal.gongxiao.foundation.warehouse.dao.ProductInventoryDao;
import com.yhglobal.gongxiao.foundation.warehouse.dao.ProductInventoryFlowDao;
import com.yhglobal.gongxiao.foundation.warehouse.dao.WarehouseDao;
import com.yhglobal.gongxiao.foundation.warehouse.model.Warehouse;
import com.yhglobal.gongxiao.inventory.model.ProductInventory;
import com.yhglobal.gongxiao.inventory.model.ProductInventoryFlow;
import com.yhglobal.gongxiao.warehouse.model.InventoryLedger;
import com.yhglobal.gongxiao.warehouse.type.WmsInventoryType;
import com.yhglobal.gongxiao.warehouse.type.WmsOrderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Delayed;

/**
 * 创建进销存台账流水信息 > 每天都需要生成台账信息
 *
 * @author : liukai
 */

@Service
public class CreateImportAndExportAccountService {

    private static Logger logger = LoggerFactory.getLogger(CreateImportAndExportAccountService.class);

    @Autowired
    ProjectDao projectDao;

    @Autowired
    ProductBasicDao productBasicDao;

    @Autowired
    WarehouseDao warehouseDao;

    @Autowired
    ImportAndExportAccountDao importAndExportAccountDao;

    @Autowired
    ProductInventoryFlowDao productInventoryFlowDao;

    @Autowired
    ProductInventoryDao productInventoryDao;

        @Scheduled(cron = "0 0 0 * * ?")
    public void createAccountInfo() {
        logger.info("#[IN][createAccountInfo] start calculating product inventory taizhang ");
        List<Project> projectList = projectDao.selectAll();
        List<ProductBasic> productBasicList = productBasicDao.selectAll(); //查询商品列表
        List<Warehouse> warehouseList = warehouseDao.selectAll();   //查询仓库列表
        logger.info("productBasicList.size(): {},warehouseList.size(): {}", productBasicList.size(), warehouseList.size());
        if (warehouseList == null || productBasicList == null) {
            logger.info("未获取到仓库信息或者商品信息");
        } else {

            for (Project project : projectList) {    //遍历项目
                String projectId = String.valueOf(project.getProjectId());
                for (ProductBasic productRecord : productBasicList) {    //遍历商品列表
                    String productCode = productRecord.getProductCode();
                    String productName = productRecord.getProductName();
                    for (Warehouse warehouse : warehouseList) {               //遍历仓库列表
                        logger.info("===========projectId={},productCode={},productName={},warehouse={}", projectId, productCode, productName, warehouse.getWarehouseName());
                        int inStockAmount = 0;  //库存变动数量
                        int outStockAmount = 0; //出库变动数量
                        InventoryLedger yesterdayRecord = importAndExportAccountDao.selectRecordByProductNameAndProductCodeAndwarehouseAtDate(projectId, productName, productCode, warehouse.getWarehouseName(), getDateTime(1));
                        if (yesterdayRecord != null) {  //先判断有没有昨天的台账记录，若有,在查询它的出入库流水
                            List<ProductInventoryFlow> yesterdayFlowList = productInventoryFlowDao.selectRecordByProductNameAndProductCodeAndwarehouseAtDate(projectId, productName, productCode, warehouse.getWarehouseName(), getDateTime(1));
                            logger.info("yestoday have :{} slip inentoryFlow records", yesterdayFlowList.size());
                            if (yesterdayFlowList.size() > 0) {  //如果有昨天的出入库流水记录,汇总入库变动数量、出库变动数量
                                for (ProductInventoryFlow flowRecord : yesterdayFlowList) {
                                    if (flowRecord.getOrderType() == WmsOrderType.INBOUND_FOR_PURCHASING_PRODUCT.getInboundOrderCode()   //采购
                                            || flowRecord.getOrderType() == WmsOrderType.INBOUND_FOR_TRANSFERRING_PRODUCT.getInboundOrderCode()  //调拨
                                            || flowRecord.getOrderType() == WmsOrderType.INBOUND_FOR_OTHER_REASON.getInboundOrderCode()         //其他
                                            || flowRecord.getOrderType() == WmsOrderType.INBOUND_FOR_RETURNING_PRODUCT.getInboundOrderCode()) {   //退货
                                        inStockAmount += flowRecord.getTransactionAmount();     //当日入库数量
                                    } else if (flowRecord.getOrderType() == WmsOrderType.OUTBOUND_FOR_B2B_SELLING_PRODUCT.getInboundOrderCode()
                                            || flowRecord.getOrderType() == WmsOrderType.OUTBOUND_FOR_TRANSFERRING_PRODUCT.getInboundOrderCode()
                                            || flowRecord.getOrderType() == WmsOrderType.OUTBOUND_FOR_OTHER_REASON.getInboundOrderCode()) {
                                        outStockAmount += flowRecord.getTransactionAmount();    //当日出库数量
                                    }
                                }

                            } else {    //如果没有昨天的出入库流水记录,出库数量和入库数量的变动都为0
                                inStockAmount = 0;
                                outStockAmount = 0;
                            }

                            InventoryLedger accountRecord = new InventoryLedger();
                            accountRecord.setProjectId(project.getProjectId());
                            accountRecord.setDateTime(getDateTime(0));                     //日期
                            accountRecord.setProductId(yesterdayRecord.getProductId());      //货品id
                            accountRecord.setProductCode(productCode);                       //货品编码
                            accountRecord.setProductModel(productCode);                      //货品型号
                            accountRecord.setProductName(productName);                       //货品名称
                            accountRecord.setWarehouseName(warehouse.getWarehouseName());    //仓库名称

                            accountRecord.setBeginTotalAmount(yesterdayRecord.getEndTotalAmount());          //期初总量=期初良品+期初次品
                            accountRecord.setBeginNonDefective(yesterdayRecord.getEndNonDefectiveAmount());  //期初良品
                            accountRecord.setBeginDefective(yesterdayRecord.getEndDefectiveAmount());        //期初次品

                            accountRecord.setInStockTotalAmount(inStockAmount);              //入库总量(当日)
                            accountRecord.setInStockNonDefectiveAmount(inStockAmount);       //入库良品(当日)
                            accountRecord.setInStockDefectiveAmount(0);                      //入库次品(当日) ???怎么判断当日入库的数----在采购流水里面添加字段
                            accountRecord.setOutStockTotalAmount(outStockAmount);            //出库总量
                            accountRecord.setNonDefectiveSaleAmount(outStockAmount);         //良品销售发货
                            accountRecord.setNonDefectiveOtherAmount(0);                     //良品其他出库  ?????  WMS良品出库
                            accountRecord.setDefectiveOutAmount(0);                           //次品出库      ?????  WMS次品出库

                            accountRecord.setNonDefectiveProfitkAmount(0);               //良品盘盈
                            accountRecord.setDefectiveProfitAmount(0);                   //次品盘盈

                            accountRecord.setNonDefectiveLossAmount(0);                  //良品盘亏
                            accountRecord.setDefectiveLossAmount(0);                     //次品盘亏

                            int endNonDefectiveAmount = yesterdayRecord.getEndNonDefectiveAmount() + inStockAmount - outStockAmount;
                            int endDefectiveAmount = yesterdayRecord.getEndDefectiveAmount();
                            accountRecord.setEndTotalAmount(endNonDefectiveAmount + endDefectiveAmount);           //期末总量
                            accountRecord.setEndNonDefectiveAmount(endNonDefectiveAmount);                       //期末良品=期初良品+入库良品-良品销售发货+良品盘盈-良品盘亏-调账出库良品-调整出库良品+调账入库良品+调账入库良品
                            accountRecord.setEndDefectiveAmount(endDefectiveAmount);                             //期末次品=期初次品+入库次品-次品出库+次品盘盈-次品盘亏-调账出库非良品-调整出库非良品+调账入库非良品+调整入库非良品

                            accountRecord.setOnPurchaseAmount(0);                        //采购在途 ("采购单预约收货后、仓库入库前的数量,在途数量不纳入库存计算中)
                            accountRecord.setOnTransferInAmount(0);                      //调拨入库在途
                            accountRecord.setOnTransferOutAmount(0);                     //调拨出库在途

                            accountRecord.setAdjustmentAccountTotalOut(0);               //调账出库总量
                            accountRecord.setAdjustmentAccountNonDefectiveOut(0);        //调账出库良品
                            accountRecord.setAdjustmentAccountDefectiveOut(0);           //调账出库非良品

                            accountRecord.setModifyTotalAmountOut(0);                    //调整出库总量
                            accountRecord.setModifyNonDefectiveAmountOut(0);             //调整出库良品
                            accountRecord.setModifyDefectiveAmountOut(0);                //调整出库非良品

                            accountRecord.setAdjustmentAccountTotalIn(0);                //调账入库总量
                            accountRecord.setAdjustmentAccountNonDefectiveIn(0);         //调账入库良品
                            accountRecord.setAdjustmentAccountDefectiveIn(0);            //调账入库非良品

                            accountRecord.setModifyTotalAmountIn(0);                     //调整入库总量
                            accountRecord.setModifyNonDefectiveAmountIn(0);              //调整入库良品
                            accountRecord.setModifyDefectiveAmountIn(0);                 //调整入库非良品

                            try {
                                importAndExportAccountDao.insert(accountRecord);
                            } catch (Exception e) {
                                logger.error("#[createAccountInfo] errorMessage: " + e.getMessage(), e);
                                throw e;
                            }

                        } else {   //如果没有昨天的台账记录,直接到《库存表》查询库存，封装成台账记录
                            logger.info("yestoday have no inentoryFlow records");
                            List<ProductInventory> productInventoryList = productInventoryDao.selectRecordBywarehouse(projectId, productName, productCode, warehouse.getWarehouseName());
                            if (productInventoryList.size() == 0) {  //如果库存表中不存在，不用生成台账
                                logger.info("inventory don't hava revord where productCode={},productName={},warehouse={} ", productCode, productName, warehouse.getWarehouseName());
                                continue;
                            }
                            //到《采购流水表》查询出入库记录,统计出入库数量
                            List<ProductInventoryFlow> yesterdayFlowList = productInventoryFlowDao.selectRecordByProductNameAndProductCodeAndwarehouseAtDate(projectId, productName, productCode, warehouse.getWarehouseName(), getDateTime(1));
                            if (yesterdayFlowList != null) {    //如果有库存信息，再到流水表查询出入库流水信息
                                for (ProductInventoryFlow record : yesterdayFlowList) {
                                    System.out.println("==============================record.getTransactionAmount()=" + record.getTransactionAmount());
                                    if (record.getTransactionAmount() > 0) {
                                        inStockAmount += record.getTransactionAmount();     //当日入库数量
                                    } else {
                                        outStockAmount += record.getTransactionAmount();    //当日出库数量
                                    }
                                }
                            }
                            InventoryLedger accountRecord = new InventoryLedger();
                            accountRecord.setProjectId(project.getProjectId());
                            accountRecord.setDateTime(getDateTime(0));                     //日期
                            for (ProductInventory productInventory : productInventoryList) {
                                accountRecord.setProductId(productInventory.getProductId());      //货品id
                                accountRecord.setProductCode(productCode);                      //货品编码
                                accountRecord.setProductModel(productCode);                      //货品型号
                                accountRecord.setProductName(productName);                       //货品名称
                                accountRecord.setWarehouseName(warehouse.getWarehouseName());    //仓库名称

                                if (productInventory.getInventoryStatus() == WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType()) {
                                    accountRecord.setBeginNonDefective(accountRecord.getBeginNonDefective() + productInventory.getOnHandQuantity());        //期初良品
                                    accountRecord.setInStockNonDefectiveAmount(accountRecord.getInStockNonDefectiveAmount() + productInventory.getOnHandQuantity());       //入库良品(当日)
                                } else if (productInventory.getInventoryStatus() == WmsInventoryType.DEFECTIVE.getInventoryType()
                                        || productInventory.getInventoryStatus() == WmsInventoryType.ENGINE_DAMAGE.getInventoryType()
                                        || productInventory.getInventoryStatus() == WmsInventoryType.BOX_DAMAGE.getInventoryType()) {
                                    accountRecord.setBeginDefective(accountRecord.getBeginDefective() + productInventory.getOnHandQuantity());        //期初次品
                                    accountRecord.setInStockDefectiveAmount(accountRecord.getInStockDefectiveAmount() + productInventory.getOnHandQuantity());                                  //入库次品(当日) ???怎么判断当日入库的数----在采购流水里面添加字段
                                } else if (productInventory.getInventoryStatus() == WmsInventoryType.TRANSPORTATION_INVENTORY.getInventoryType()) {

                                } else if (productInventory.getInventoryStatus() == WmsInventoryType.FROZEN_STOCK.getInventoryType()) {

                                }
                                accountRecord.setBeginTotalAmount(accountRecord.getBeginTotalAmount() + productInventory.getOnHandQuantity());         //期初总量=期初良品+期初次品

                                accountRecord.setInStockTotalAmount(inStockAmount);              //入库总量(昨天)

                                accountRecord.setOutStockTotalAmount(outStockAmount);            //出库总量
                                accountRecord.setNonDefectiveSaleAmount(outStockAmount);         //良品销售发货
                                accountRecord.setNonDefectiveOtherAmount(0);                     //良品其他出库  ?????  WMS良品出库
                                accountRecord.setDefectiveOutAmount(0);                           //次品出库      ?????  WMS次品出库

                                accountRecord.setNonDefectiveProfitkAmount(0);               //良品盘盈
                                accountRecord.setDefectiveProfitAmount(0);                   //次品盘盈

                                accountRecord.setNonDefectiveLossAmount(0);                  //良品盘亏
                                accountRecord.setDefectiveLossAmount(0);                     //次品盘亏

                                accountRecord.setEndTotalAmount(productInventory.getOnHandQuantity());           //期末总量
//                            accountRecord.setEndNonDefectiveAmount(productInventory.getOnHandQuantity() - );   //期末良品=期初良品+入库良品-良品销售发货+良品盘盈-良品盘亏-调账出库良品-调整出库良品+调账入库良品+调账入库良品
//                            accountRecord.setEndDefectiveAmount(productInventory.getDefectiveQuantity());                             //期末次品=期初次品+入库次品-次品出库+次品盘盈-次品盘亏-调账出库非良品-调整出库非良品+调账入库非良品+调整入库非良品

                                accountRecord.setOnPurchaseAmount(0);                        //采购在途 ("采购单预约收货后、仓库入库前的数量,在途数量不纳入库存计算中)
                                accountRecord.setOnTransferInAmount(0);                      //调拨入库在途
                                accountRecord.setOnTransferOutAmount(0);                     //调拨出库在途

                                accountRecord.setAdjustmentAccountTotalOut(0);               //调账出库总量
                                accountRecord.setAdjustmentAccountNonDefectiveOut(0);        //调账出库良品
                                accountRecord.setAdjustmentAccountDefectiveOut(0);           //调账出库非良品

                                accountRecord.setModifyTotalAmountOut(0);                    //调整出库总量
                                accountRecord.setModifyNonDefectiveAmountOut(0);             //调整出库良品
                                accountRecord.setModifyDefectiveAmountOut(0);                //调整出库非良品

                                accountRecord.setAdjustmentAccountTotalIn(0);                //调账入库总量
                                accountRecord.setAdjustmentAccountNonDefectiveIn(0);         //调账入库良品
                                accountRecord.setAdjustmentAccountDefectiveIn(0);            //调账入库非良品

                                accountRecord.setModifyTotalAmountIn(0);                     //调整入库总量
                                accountRecord.setModifyNonDefectiveAmountIn(0);              //调整入库良品
                                accountRecord.setModifyDefectiveAmountIn(0);                 //调整入库非良品
                            }


                            try {
                                importAndExportAccountDao.insert(accountRecord);
                            } catch (Exception e) {
                                logger.error("#[createAccountInfo] errorMessage: " + e.getMessage(), e);
                                throw e;
                            }


                        }
                    }
                }
            }
        }


        logger.info("#traceId={}# [OUT][createAccountInfo] params: ");

    }

    public Date getDateTime(int i) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now();
        localDate = localDate.minusDays(i);
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }
}
