package com.yhglobal.gongxiao.inventory.account;

import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseServiceGrpc;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.inventory.dao.ImportAndExportAccountDao;
import com.yhglobal.gongxiao.inventory.dao.ProductInventoryDao;
import com.yhglobal.gongxiao.inventory.dao.ProductInventoryFlowDao;
import com.yhglobal.gongxiao.inventory.model.InventoryLedger;
import com.yhglobal.gongxiao.inventory.model.ProductInventory;
import com.yhglobal.gongxiao.inventory.model.ProductInventoryFlow;
import com.yhglobal.gongxiao.inventory.util.TablePrefixUtil;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.type.WmsInventoryType;
import com.yhglobal.gongxiao.type.WmsOrderType;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
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

/**
 * 创建进销存台账流水信息 > 每天都需要生成台账信息
 *
 * @author : liukai
 */

@Service
public class CreateImportAndExportAccountService {

    private static Logger logger = LoggerFactory.getLogger(CreateImportAndExportAccountService.class);

    @Autowired
    ImportAndExportAccountDao importAndExportAccountDao;

    @Autowired
    ProductInventoryFlowDao productInventoryFlowDao;

    @Autowired
    ProductInventoryDao productInventoryDao;

    @Scheduled(cron = "0 0 0 * * ?")
    public void createAccountInfo() {
        Date date = new Date();
        String traceId = String.valueOf(date.getTime());
        String uid = "fenxiao";
        String userName = "fenxiao";
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, uid, userName);
        logger.info("#[IN][createAccountInfo] start calculating product inventory taizhang ");

        //调用基础模块的项目的grpc查询项目信息
        ProjectServiceGrpc.ProjectServiceBlockingStub projectService = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
        ProjectStructure.SelectProjectListReq selectProjectListReq = ProjectStructure.SelectProjectListReq.newBuilder().setRpcHeader(rpcHeader).build();
        ProjectStructure.SelectProjectListResp rpcResponse = projectService.selectProjectList(selectProjectListReq);
        List<ProjectStructure.Project> projectListList = rpcResponse.getProjectListList();


        //查询仓库列表
        //调用基础模块的仓库grpc服务查仓库编码
        WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseService = GlobalRpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
        WarehouseStructure.SelectAllWarehouseReq selectAllWarehouseReq = WarehouseStructure.SelectAllWarehouseReq.newBuilder().setRpcHeader(rpcHeader).build();
        WarehouseStructure.SelectAllWarehouseResp warehouseRpcResp = warehouseService.selectAllWarehouse(selectAllWarehouseReq);
        List<WarehouseStructure.Warehouse> warehouseListList = warehouseRpcResp.getWarehouseListList();

        logger.info("productBasicList.size(): {},warehouseList.size(): {}", warehouseListList.size());
        if (warehouseListList == null) {
            logger.info("未获取到仓库信息或者商品信息");
        } else {

            for (ProjectStructure.Project project : projectListList) {    //遍历项目
                String projectId = String.valueOf(project.getProjectId());
                String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(project.getProjectId());
                //调用基础模块的商品的grpc查询项目信息
                ProductServiceGrpc.ProductServiceBlockingStub productService = GlobalRpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
                ProductStructure.SelectAllProductReq selectAllProductReq = ProductStructure.SelectAllProductReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(project.getProjectId()).build();
                ProductStructure.SelectAllProductResp response = productService.selectAllProduct(selectAllProductReq);
                List<ProductStructure.ProductBusiness> productBusinessList = response.getProductBusinessList();
                for (ProductStructure.ProductBusiness productRecord : productBusinessList) {    //遍历商品列表
                    String productCode = productRecord.getProductModel();
                    String productName = productRecord.getProductName();
                    for (WarehouseStructure.Warehouse warehouse : warehouseListList) {               //遍历仓库列表
                        logger.info("===========projectId={},productCode={},productName={},warehouse={}", projectId, productCode, productName, warehouse.getWarehouseName());
                        int inStockAmount = 0;  //库存变动数量
                        int outStockAmount = 0; //出库变动数量

                        InventoryLedger yesterdayRecord = importAndExportAccountDao.selectRecordByProductNameAndProductCodeAndwarehouseAtDate(projectId, productName, productCode, warehouse.getWarehouseName(), getDateTime(1), projectPrefix);
                        if (yesterdayRecord != null) {  //先判断有没有昨天的台账记录，若有,在查询它的出入库流水
                            calculateByYestodayRecord(project, projectId, projectPrefix, productCode, productName, warehouse, inStockAmount, outStockAmount, yesterdayRecord);

                        } else {   //如果没有昨天的台账记录,直接到《库存表》查询库存，封装成台账记录
                            calculateByInventory(project, projectId, projectPrefix, productCode, productName, warehouse, inStockAmount, outStockAmount);

                        }
                    }
                }
            }
        }

        logger.info("#traceId={}# [OUT][createAccountInfo] params: ");

    }

    private void calculateByInventory(ProjectStructure.Project project, String projectId, String projectPrefix, String productCode, String productName, WarehouseStructure.Warehouse warehouse, int inStockAmount, int outStockAmount) {
        logger.info("#[IN][calculateByInventory] yestoday have no inentoryFlow records");
        List<ProductInventory> productInventoryList = productInventoryDao.selectRecordBywarehouse(projectId, productName, productCode, warehouse.getWarehouseName(), projectPrefix);
        if (productInventoryList.size() == 0) {  //如果库存表中不存在，不用生成台账
            logger.info("inventory don't hava revord where productCode={},productName={},warehouse={} ", productCode, productName, warehouse.getWarehouseName());
            return;
        }
        //到《采购流水表》查询出入库记录,统计出入库数量
        List<ProductInventoryFlow> yesterdayFlowList = productInventoryFlowDao.selectRecordByProductNameAndProductCodeAndwarehouseAtDate(projectId, productName, productCode, warehouse.getWarehouseName(), getDateTime(1), projectPrefix);
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
        accountRecord.setDateTime(getDateTime(0));  //日期
        for (ProductInventory productInventory : productInventoryList) {
            accountRecord.setProductId(productInventory.getProductId());      //货品id
            accountRecord.setProductCode(productCode);                      //货品编码
            accountRecord.setProductModel(productCode);                      //货品型号
            accountRecord.setProductName(productName);                       //货品名称
            accountRecord.setWarehouseName(warehouse.getWarehouseName());    //仓库名称

            if (productInventory.getInventoryStatus() == WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType()) {
                accountRecord.setBeginNonDefective(accountRecord.getBeginNonDefective() + productInventory.getOnHandQuantity()); //期初良品
                accountRecord.setInStockNonDefectiveAmount(accountRecord.getInStockNonDefectiveAmount() + productInventory.getOnHandQuantity()); //入库良品(当日)
            } else if (productInventory.getInventoryStatus() == WmsInventoryType.DEFECTIVE.getInventoryType()
                    || productInventory.getInventoryStatus() == WmsInventoryType.ENGINE_DAMAGE.getInventoryType()
                    || productInventory.getInventoryStatus() == WmsInventoryType.BOX_DAMAGE.getInventoryType()) {
                accountRecord.setBeginDefective(accountRecord.getBeginDefective() + productInventory.getOnHandQuantity());        //期初次品
                accountRecord.setInStockDefectiveAmount(accountRecord.getInStockDefectiveAmount() + productInventory.getOnHandQuantity()); //入库次品(当日)
            } else if (productInventory.getInventoryStatus() == WmsInventoryType.TRANSPORTATION_INVENTORY.getInventoryType()) {

            } else if (productInventory.getInventoryStatus() == WmsInventoryType.FROZEN_STOCK.getInventoryType()) {

            }
            accountRecord.setBeginTotalAmount(accountRecord.getBeginTotalAmount() + productInventory.getOnHandQuantity() + productInventory.getOnSalesOrderQuantity()); //期初总量=期初良品+期初次品
            accountRecord.setInStockTotalAmount(inStockAmount);  //入库总量(昨天)

            accountRecord.setOutStockTotalAmount(outStockAmount); //出库总量
            accountRecord.setNonDefectiveSaleAmount(accountRecord.getNonDefectiveSaleAmount() + productInventory.getOnSalesOrderQuantity());  //良品销售发货
            accountRecord.setNonDefectiveOtherAmount(0); //良品其他出库  ?????  WMS良品出库
            accountRecord.setDefectiveOutAmount(0);   //次品出库      ?????  WMS次品出库

            accountRecord.setNonDefectiveProfitkAmount(0); //良品盘盈
            accountRecord.setDefectiveProfitAmount(0); //次品盘盈

            accountRecord.setNonDefectiveLossAmount(0); //良品盘亏
            accountRecord.setDefectiveLossAmount(0);  //次品盘亏

            accountRecord.setEndTotalAmount(accountRecord.getEndTotalAmount() + productInventory.getOnHandQuantity() + productInventory.getOnSalesOrderQuantity());           //期末总量
//                            accountRecord.setEndNonDefectiveAmount(productInventory.getOnHandQuantity() - );   //期末良品=期初良品+入库良品-良品销售发货+良品盘盈-良品盘亏-调账出库良品-调整出库良品+调账入库良品+调账入库良品
//                            accountRecord.setEndDefectiveAmount(productInventory.getDefectiveQuantity());     //期末次品=期初次品+入库次品-次品出库+次品盘盈-次品盘亏-调账出库非良品-调整出库非良品+调账入库非良品+调整入库非良品

            accountRecord.setOnPurchaseAmount(0);     //采购在途 ("采购单预约收货后、仓库入库前的数量,在途数量不纳入库存计算中)
            accountRecord.setOnTransferInAmount(0);   //调拨入库在途
            accountRecord.setOnTransferOutAmount(0);  //调拨出库在途

            accountRecord.setAdjustmentAccountTotalOut(0);        //调账出库总量
            accountRecord.setAdjustmentAccountNonDefectiveOut(0); //调账出库良品
            accountRecord.setAdjustmentAccountDefectiveOut(0);    //调账出库非良品

            accountRecord.setModifyTotalAmountOut(0);       //调整出库总量
            accountRecord.setModifyNonDefectiveAmountOut(0);//调整出库良品
            accountRecord.setModifyDefectiveAmountOut(0);   //调整出库非良品

            accountRecord.setAdjustmentAccountTotalIn(0);        //调账入库总量
            accountRecord.setAdjustmentAccountNonDefectiveIn(0); //调账入库良品
            accountRecord.setAdjustmentAccountDefectiveIn(0);    //调账入库非良品

            accountRecord.setModifyTotalAmountIn(0);       //调整入库总量
            accountRecord.setModifyNonDefectiveAmountIn(0);//调整入库良品
            accountRecord.setModifyDefectiveAmountIn(0);   //调整入库非良品
        }


        try {
            importAndExportAccountDao.insert(accountRecord, projectPrefix);
        } catch (Exception e) {
            logger.error("#[createAccountInfo] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    private void calculateByYestodayRecord(ProjectStructure.Project project, String projectId, String projectPrefix, String productCode, String productName, WarehouseStructure.Warehouse warehouse, int inStockAmount, int outStockAmount, InventoryLedger yesterdayRecord) {
        logger.info("#[IN][calculateByYestodayRecord] according to the yestoday inentoryFlow records calculate");
        List<ProductInventoryFlow> yesterdayFlowList = productInventoryFlowDao.selectRecordByProductNameAndProductCodeAndwarehouseAtDate(projectId, productName, productCode, warehouse.getWarehouseName(), getDateTime(1), projectPrefix);
        int inStockNonDefectiveAmount = 0;  //入库良品数量
        int inStockDefectiveAmount = 0;     //入库残品数量
        if (yesterdayFlowList.size() > 0) {  //如果有昨天的出入库流水记录,汇总入库总数量、出库总数量、入库良品数量、入库残品数量
            for (ProductInventoryFlow flowRecord : yesterdayFlowList) {
                /**
                 * 根据流水记录的单据类型得到入库总数量、出库总数量
                 */
                if (flowRecord.getOrderType() == WmsOrderType.INBOUND_FOR_PURCHASING_PRODUCT.getInboundOrderCode()   //采购入
                        || flowRecord.getOrderType() == WmsOrderType.INBOUND_FOR_TRANSFERRING_PRODUCT.getInboundOrderCode()  //调拨入
                        || flowRecord.getOrderType() == WmsOrderType.INBOUND_FOR_OTHER_REASON.getInboundOrderCode()         //其他入
                        || flowRecord.getOrderType() == WmsOrderType.INBOUND_FOR_RETURNING_PRODUCT.getInboundOrderCode()) {   //退货入
                    inStockAmount += flowRecord.getTransactionAmount();     //当日入库数量
                } else if (flowRecord.getOrderType() == WmsOrderType.OUTBOUND_FOR_B2B_SELLING_PRODUCT.getInboundOrderCode()  //普通出库单
                        || flowRecord.getOrderType() == WmsOrderType.OUTBOUND_FOR_TRANSFERRING_PRODUCT.getInboundOrderCode()  //调拨出
                        || flowRecord.getOrderType() == WmsOrderType.OUTBOUND_FOR_OTHER_REASON.getInboundOrderCode()) {   //其他出
                    outStockAmount += flowRecord.getTransactionAmount();    //当日出库数量
                }

                /**
                 * 根据流水的库存类型得到入库良品数量、入库残品数量
                 */
                if (flowRecord.getInventoryFlowType() == WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType()) {    //普通好机
                    inStockNonDefectiveAmount += flowRecord.getTransactionAmount();
                } else {   //机损、箱损、残次
                    inStockDefectiveAmount += flowRecord.getTransactionAmount();
                }
            }

        } else {    //如果没有昨天的出入库流水记录,出库数量和入库数量的变动都为0
            inStockAmount = 0;
            outStockAmount = 0;
            inStockNonDefectiveAmount = 0;
            inStockDefectiveAmount = 0;
        }

        /**
         * 根据库存表的记录得到 销售占用数量
         */
        List<ProductInventory> productInventoryList = productInventoryDao.selectRecordBywarehouse(projectId, productName, productCode, warehouse.getWarehouseName(), projectPrefix);
        int endNonDefectiveAmount = 0;  //期末良品
        int endDefectiveAmount = 0;     //期末残品
        int onSalesQuantity = 0;        //销售占用
        for (ProductInventory inventory : productInventoryList) {
            onSalesQuantity += inventory.getOnSalesOrderQuantity();
            if (inventory.getInventoryStatus() == WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType()) {  //普通好机
                endDefectiveAmount += inventory.getOnHandQuantity();
            } else {  //残次品
                endDefectiveAmount += inventory.getOnHandQuantity();
            }
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

        accountRecord.setInStockTotalAmount(inStockAmount); //入库总量(当日)
        accountRecord.setInStockNonDefectiveAmount(inStockNonDefectiveAmount); //入库良品(当日)
        accountRecord.setInStockDefectiveAmount(inStockDefectiveAmount); //入库次品(当日)
        accountRecord.setOutStockTotalAmount(outStockAmount);            //出库总量
        accountRecord.setNonDefectiveSaleAmount(onSalesQuantity);         //良品销售发货
        accountRecord.setNonDefectiveOtherAmount(0);                     //良品其他出库  ?????  WMS良品出库
        accountRecord.setDefectiveOutAmount(0);                           //次品出库      ?????  WMS次品出库

        accountRecord.setNonDefectiveProfitkAmount(0);               //良品盘盈
        accountRecord.setDefectiveProfitAmount(0);                   //次品盘盈

        accountRecord.setNonDefectiveLossAmount(0);                  //良品盘亏
        accountRecord.setDefectiveLossAmount(0);                     //次品盘亏


        accountRecord.setEndTotalAmount(endNonDefectiveAmount + endDefectiveAmount); //期末总量
        accountRecord.setEndNonDefectiveAmount(endNonDefectiveAmount);   //期末良品
        accountRecord.setEndDefectiveAmount(endDefectiveAmount);   //期末次品

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
            importAndExportAccountDao.insert(accountRecord, projectPrefix);
        } catch (Exception e) {
            logger.error("#[createAccountInfo] errorMessage: " + e.getMessage(), e);
            throw e;
        }
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
