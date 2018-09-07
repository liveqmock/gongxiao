package com.yhglobal.gongxiao.inventory.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.productBasic.dao.ProductBasicDao;
import com.yhglobal.gongxiao.foundation.warehouse.dao.ProductInventoryDao;
import com.yhglobal.gongxiao.foundation.warehouse.dao.ProductInventoryFlowDao;
import com.yhglobal.gongxiao.foundation.warehouse.dao.WarehouseDao;
import com.yhglobal.gongxiao.foundation.warehouse.model.Warehouse;
import com.yhglobal.gongxiao.inventory.model.ProductInventory;
import com.yhglobal.gongxiao.inventory.model.ProductInventoryFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service(timeout = 5000)
public class InventorySyncServiceImpl implements InventorySyncService {

    private static Logger logger = LoggerFactory.getLogger(InventorySyncServiceImpl.class);

    @Autowired
    ProductInventoryFlowDao productInventoryFlowDao;

    @Autowired
    ProductInventoryDao productInventoryDao;

    @Autowired
    WarehouseDao warehouseDao;

    @Autowired
    ProductBasicDao productBasicDao;

    @Override
    public int syncInboundInventory(RpcHeader rpcHeader, List<ProductInventory> productInventoryList,String purchaseOrderNo,String gonxiaoInboundOrderNo,int orderType) {
        logger.info("#traceId={}# [IN][syncInboundInventory]: productInventoryList={},purchaseOrderNo={},purchaseOrderNo={},gonxiaoInboundOrderNo={},orderType={}", rpcHeader.getTraceId(), JSON.toJSONString(productInventoryList),purchaseOrderNo,gonxiaoInboundOrderNo,orderType);
        try {
            for (ProductInventory record : productInventoryList) {
                Warehouse warehouse = warehouseDao.selectByPrimaryKey(Long.valueOf(record.getWarehouseId()));
                String batchNo = record.getBatchNo();
                String productCode = record.getProductCode();
                ProductBasic productBasic = productBasicDao.selectByProductCode(productCode);
                ProductInventory productInventory = productInventoryDao.selectRecordByProductNameAndProductCodeAndwarehouse(record.getPurchaseType(),record.getInventoryStatus(),record.getProjectId(),batchNo, productCode, record.getWarehouseId());
                if (productInventory == null) { //若《库存表》不存在该商品 则先创建该商品库存记录

                    /**
                     * 理论上以下参数将唯一确定t_product_inventory中的一行记录
                     *   purchaseType 采购类型（普通采购/赠品）
                     *   inventoryStatus 库存品质
                     *   projectId 项目Id
                     *   productCode 商品编码
                     *   batchNo 批次
                     *   warehouseId 仓库id
                     *
                     * 可能会有多个请求同时过来 需防止同时创建两条记录
                     */
                    synchronized (InventorySyncServiceImpl.class) {
                        productInventory = productInventoryDao.selectRecordByProductNameAndProductCodeAndwarehouse(record.getPurchaseType(),record.getInventoryStatus(),record.getProjectId(),record.getBatchNo(),record.getProductCode(), record.getWarehouseId());
                        if (productInventory == null) {
                            productInventory = new ProductInventory();
                            productInventory.setBatchNo(record.getBatchNo());
                            productInventory.setPurchaseType(record.getPurchaseType());
                            productInventory.setInventoryStatus(record.getInventoryStatus()); //库存状态
                            productInventory.setProjectId(record.getProjectId());
                            productInventory.setProductId(record.getProductId());
                            productInventory.setProductCode(record.getProductCode());
                            productInventory.setProductModel(productBasic.getProductModel());
                            productInventory.setProductName(productBasic.getProductName());
                            productInventory.setMaterial(productBasic.getEasMaterialCode());
                            productInventory.setCostPrice(productBasic.getGuidePrice());
                            productInventory.setWarehouseId(record.getWarehouseId());
                            productInventory.setWarehouseCode(warehouse.getWarehouseCode());
                            productInventory.setWarehouseName(warehouse.getWarehouseName());
                            productInventory.setOnHandQuantity(0); //库存数量
                            productInventory.setOnSalesOrderQuantity(0); //已下单未出库
                            productInventory.setCreateTime(new Date()); //创建时间
                            productInventoryDao.insert(productInventory); //注意：这里插入后需要把id字段注入进来 否则下面根据inventoryId获取后会NPE
                        }
                    }
                }

                //写入库流水到product_inventory_flow
                ProductInventoryFlow flow = new ProductInventoryFlow();
                flow.setOrderNo(gonxiaoInboundOrderNo);
                flow.setInventoryFlowType(record.getInventoryStatus());
                flow.setOrderType(orderType);
                flow.setProjectId(record.getProjectId());
                flow.setProductCode(record.getProductCode());
                flow.setProductModel(productBasic.getProductModel());
                flow.setProductName(productBasic.getProductName());
                flow.setWarehouseId(record.getWarehouseId());
                flow.setWarehouseCode(warehouse.getWarehouseCode());
                flow.setWarehouseName(warehouse.getWarehouseName());
                int amountBeforeTransaction = productInventory.getOnHandQuantity()+productInventory.getOnSalesOrderQuantity(); //发生前数量=原有可用数量+销售占用
                flow.setAmountBeforeTransaction(amountBeforeTransaction);
                flow.setTransactionAmount(record.getOnHandQuantity()); //变动数量
                int amountAfterTransaction = record.getOnHandQuantity() + productInventory.getOnHandQuantity()+productInventory.getOnSalesOrderQuantity(); //发生后数量=变动数量+原有可用数量+销售占用
                flow.setAmountAfterTransaction(amountAfterTransaction);
                flow.setTransactionTime(new Date()); //发生变动时间
                flow.setRelatedOrderId(purchaseOrderNo); //设定关联的订单(采购单)
                flow.setCreateTime(new Date());

                int num = productInventoryFlowDao.insert(flow); //出入库流水记录入库
                if (num != 1) { //流水写入DB失败
                    logger.error("#traceId={}# [OUT] fail to create inbound inventory flow: {}", rpcHeader.traceId, JSON.toJSONString(flow));
                }

                //则更新入库库存汇总信息
                long inventoryId = productInventory.getId();

                int maxTryTimes = 12; //当前最大尝试的次数
                boolean updateSuccess = false; //标记是否更新DB成功
                while (!updateSuccess && maxTryTimes > 0) { //若尚未更新db成功 并且剩余重试数大于0
                    try {
                        ProductInventory currentInventory = productInventoryDao.getProductInventoryById(inventoryId);
                        if (currentInventory == null) {
                            logger.error("====>fail to get ProductInventory to update OnHandQuantity: inventoryId={}", inventoryId);
                        }
                        int targetOnHandQuantity = currentInventory.getOnHandQuantity() + record.getOnHandQuantity(); //新的库存数=原有的库存+实际入库数量
                        int row = productInventoryDao.updateOnHandQuantityById(productInventory.getId(), targetOnHandQuantity, currentInventory.getDataVersion());
                        if (row == 1) {
                            updateSuccess = true;
                            logger.info("update inventory OnHandQuantity success: inventoryId={} batchNo={}", inventoryId, record.getBatchNo());
                        }
                    } catch (Exception e) {
                        logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
                        throw e;
                    }
                    maxTryTimes --;
                }
                if (!updateSuccess && maxTryTimes<=0) {
                    logger.info("#traceId={}# fail to update inventory OnHandQuantity: inventoryId={} batchNo={}", inventoryId, batchNo);
                }
            }
            logger.info("#traceId={}# [OUT][syncInboundInventory]: get syncInboundInventory success", rpcHeader.traceId);
            return ErrorCode.SUCCESS.getErrorCode();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public int syncOutboundInventory(RpcHeader rpcHeader, List<ProductInventory> productInventoryList,String salesOrderNo,String gonxiaoOutboundOrderNo,int orderType) {
        logger.info("#traceId={}# [IN][syncOutboundInventory]: productInventoryList={}, salesOrderNo={}, gonxiaoOutboundOrderNo={}, orderType={}", rpcHeader.getTraceId(), JSON.toJSONString(productInventoryList),salesOrderNo,gonxiaoOutboundOrderNo,orderType);
        try {
            for (ProductInventory record : productInventoryList) {
                Warehouse warehouse = warehouseDao.selectByPrimaryKey(Long.valueOf(record.getWarehouseId()));
                ProductBasic productBasic = productBasicDao.selectByProductCode(record.getProductCode());
                ProductInventory productInventory = productInventoryDao.selectRecordByProductNameAndProductCodeAndwarehouse(record.getPurchaseType(),record.getInventoryStatus(),record.getProjectId(),record.getBatchNo(), record.getProductCode(), record.getWarehouseId());
                if (productInventory == null) { //若《库存表》不存在该商品 说明有错误
                    logger.error("#traceId={}# [OUT][syncOutboundInventory]: not have product inventory in fenxiao'DB , recor={}", rpcHeader.traceId,JSON.toJSONString(record));
                    continue;
                }

                //写出库流水到product_inventory_flow
                ProductInventoryFlow flow = new ProductInventoryFlow();
                flow.setOrderNo(gonxiaoOutboundOrderNo);
                flow.setInventoryFlowType(record.getInventoryStatus());
                flow.setOrderType(orderType);
                flow.setProjectId(record.getProjectId());
                flow.setProductCode(record.getProductCode());
                flow.setProductModel(productBasic.getProductModel());
                flow.setProductName(productBasic.getProductName());
                flow.setWarehouseId(record.getWarehouseId());
                flow.setWarehouseCode(warehouse.getWarehouseCode());
                flow.setWarehouseName(warehouse.getWarehouseName());
                int amountBeforeTransaction = productInventory.getOnHandQuantity() + productInventory.getOnSalesOrderQuantity(); //发生前数量=可用数量+销售占用数量
                flow.setAmountBeforeTransaction(amountBeforeTransaction);
                flow.setTransactionAmount(record.getOnHandQuantity()); //变动数量
                int amountAfterTransaction = productInventory.getOnHandQuantity()+productInventory.getOnSalesOrderQuantity()- record.getOnHandQuantity(); //发生后数量=可用数量+销售占用数量-变动数量
                flow.setAmountAfterTransaction(amountAfterTransaction);
                flow.setTransactionTime(new Date());
                flow.setRelatedOrderId(salesOrderNo); //设定关联的订单(销售单)
                flow.setCreateTime(new Date());

                //下面日记仅为调试交易数量为零用 待去除
                if (flow.getTransactionAmount()==0) {
                    logger.warn("======> got zero TransactionAmount: ProductInventoryRow={}", JSON.toJSONString(record));
                }

                int num = productInventoryFlowDao.insert(flow); //出入库流水记录入库
                if (num == 1) { //流水写入DB成功 则更新出库库存信息
                    logger.info("开始修改库存占用数量,释放销售占用的库存,purchaseType={},inventoryStatus={},projectId={},batchNo={},productCode={},warehouseId={}",productInventory.getPurchaseType(),productInventory.getInventoryStatus(),productInventory.getProjectId(),productInventory.getBatchNo(),productInventory.getProductCode(),productInventory.getWarehouseId());
                    productInventoryDao.updateInventory(record);  //根据出入库流水更新库存信息,释放销售占用数量,
                } else { //流水写入DB失败
                    logger.error("#traceId={}# [OUT] fail to create outbound inventory flow: {}", rpcHeader.traceId, JSON.toJSONString(flow));
                }
            }
            logger.info("#traceId={}# [OUT][syncOutboundInventory]: get syncOutboundInventory success", rpcHeader.traceId);
            return ErrorCode.SUCCESS.getErrorCode();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

}
