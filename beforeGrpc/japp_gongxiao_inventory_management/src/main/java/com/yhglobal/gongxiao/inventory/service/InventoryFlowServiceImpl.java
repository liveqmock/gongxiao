package com.yhglobal.gongxiao.inventory.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.inventory.model.ProductInventoryFlow;
import com.yhglobal.gongxiao.foundation.warehouse.dao.ProductInventoryFlowDao;
import com.yhglobal.gongxiao.warehouse.type.WmsInventoryType;
import com.yhglobal.gongxiao.warehouse.type.WmsOrderType;
import com.yhglobal.gongxiao.warehousemanagement.bo.ProductInventoryFlowShow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service(timeout = 5000)
public class InventoryFlowServiceImpl implements InventoryFlowService {

    private Logger logger = LoggerFactory.getLogger(InventoryFlowServiceImpl.class);

    @Autowired
    ProductInventoryFlowDao productInventoryFlowDao;

    @Override
    public PageInfo<ProductInventoryFlowShow> conditionalSelectInventoryFlow(RpcHeader rpcHeader, int pageNumber, int pageSize, String projectId, String productCode, String productName, String warehouseName, String inventoryFlowId, String startDate, String endDate) {

        try {
            logger.info("#traceId={}# [IN][conditionalSelectInventoryFlow]: projectId:{}, productModel:{}, productName:{}, warehouseId:{}, " +
                            "inventoryFlowId:{}, beginDate:{}, endDate:{}", rpcHeader.getTraceId(),projectId,productCode,productName,warehouseName,inventoryFlowId,startDate,endDate);
            PageInfo<ProductInventoryFlowShow> pageInfo;
            PageHelper.startPage(pageNumber, pageSize);
            List<ProductInventoryFlowShow> productInventoryFlowShowList = new ArrayList<>();
            List<ProductInventoryFlow> productInventoryFlowList = productInventoryFlowDao.selectRecordByProductNameAndProductCodeAndwarehouseBetweenDate(projectId,productName,productCode,inventoryFlowId,warehouseName,startDate,endDate);
            for (ProductInventoryFlow record : productInventoryFlowList){
                ProductInventoryFlowShow productInventoryFlowShow = new ProductInventoryFlowShow();
                int imperfectQuantity = 0;
                int transactionAmount = record.getTransactionAmount();

                productInventoryFlowShow.setOrderNo(record.getOrderNo());
                productInventoryFlowShow.setRelatedOrderNo(record.getRelatedOrderId());
                productInventoryFlowShow.setAmountAfterTransaction(record.getAmountAfterTransaction());
                productInventoryFlowShow.setAmountBeforeTransaction(record.getAmountBeforeTransaction());
                productInventoryFlowShow.setInventoryFlowId(record.getInventoryFlowId());
                if (WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType() == record.getInventoryFlowType()){
                    productInventoryFlowShow.setInventoryFlowType("普通好机");
                    productInventoryFlowShow.setPerfectQuantity(record.getTransactionAmount());
                }else if (WmsInventoryType.DEFECTIVE.getInventoryType() == record.getInventoryFlowType()){
                    productInventoryFlowShow.setInventoryFlowType("残次");
                    imperfectQuantity += record.getTransactionAmount();
                }else if (WmsInventoryType.ENGINE_DAMAGE.getInventoryType() == record.getInventoryFlowType()){
                    productInventoryFlowShow.setInventoryFlowType("机损");
                    imperfectQuantity += record.getTransactionAmount();
                }else if (WmsInventoryType.BOX_DAMAGE.getInventoryType() == record.getInventoryFlowType()){
                    productInventoryFlowShow.setInventoryFlowType("箱损");
                    imperfectQuantity += record.getTransactionAmount();
                }else if (WmsInventoryType.TRANSPORTATION_INVENTORY.getInventoryType() == record.getInventoryFlowType()){  //在途库存
                    productInventoryFlowShow.setInventoryFlowType("在途");
                    imperfectQuantity += record.getTransactionAmount();
                }else if(WmsInventoryType.FROZEN_STOCK.getInventoryType() == record.getInventoryFlowType()){     //冻结库存
                    productInventoryFlowShow.setInventoryFlowType("冻结");
                    imperfectQuantity += record.getTransactionAmount();
                }else {
                    productInventoryFlowShow.setInventoryFlowType("不详");
                }
                productInventoryFlowShow.setImperfectQuantity(imperfectQuantity);
                if (WmsOrderType.INBOUND_FOR_PURCHASING_PRODUCT.getInboundOrderCode() == record.getOrderType()){
                    productInventoryFlowShow.setOrderType("采购入库");
                }else if (WmsOrderType.INBOUND_FOR_TRANSFERRING_PRODUCT.getInboundOrderCode() == record.getOrderType()){
                    productInventoryFlowShow.setOrderType("调拨入库");
                }else if (WmsOrderType.INBOUND_FOR_RETURNING_PRODUCT.getInboundOrderCode() == record.getOrderType()){
                    productInventoryFlowShow.setOrderType("退货入库");
                }else if (WmsOrderType.INBOUND_FOR_OTHER_REASON.getInboundOrderCode() == record.getOrderType()){
                    productInventoryFlowShow.setOrderType("其他入库");
                }else if (WmsOrderType.OUTBOUND_FOR_TRANSFERRING_PRODUCT.getInboundOrderCode() == record.getOrderType()){
                    productInventoryFlowShow.setOrderType("调拨出库");
                    transactionAmount = -1*transactionAmount;
                }else if (WmsOrderType.OUTBOUND_FOR_RETURN_PRODUCT.getInboundOrderCode() == record.getOrderType()){
                    productInventoryFlowShow.setOrderType("采购退货出库");
                    transactionAmount = -1*transactionAmount;
                }else if (WmsOrderType.OUTBOUND_FOR_B2B_SELLING_PRODUCT.getInboundOrderCode() == record.getOrderType()){
                    productInventoryFlowShow.setOrderType("普通出库");
                    transactionAmount = -1*transactionAmount;
                }else if (WmsOrderType.OUTBOUND_FOR_EXCHANGING_PRODUCT.getInboundOrderCode() == record.getOrderType()){
                    productInventoryFlowShow.setOrderType("换货出库");
                    transactionAmount = -1*transactionAmount;
                }else if (WmsOrderType.OUTBOUND_FOR_B2C_SELLING_PRODUCT.getInboundOrderCode() == record.getOrderType()){
                    productInventoryFlowShow.setOrderType("交易出库");
                    transactionAmount = -1*transactionAmount;
                }else if (WmsOrderType.OUTBOUND_FOR_DISCARDING_PRODUCT.getInboundOrderCode() == record.getOrderType()){
                    productInventoryFlowShow.setOrderType("报废出库");
                    transactionAmount = -1*transactionAmount;
                }else if (WmsOrderType.OUTBOUND_FOR_OTHER_REASON.getInboundOrderCode() == record.getOrderType()){
                    productInventoryFlowShow.setOrderType("其他出库");
                    transactionAmount = -1*transactionAmount;
                }else {
                    productInventoryFlowShow.setOrderType("不详");
                }
                productInventoryFlowShow.setTransactionAmount(transactionAmount);
                productInventoryFlowShow.setProductCode(record.getProductCode());
                productInventoryFlowShow.setProductId(record.getProductId());
                productInventoryFlowShow.setProductModel(record.getProductModel());
                productInventoryFlowShow.setProductName(record.getProductName());
                productInventoryFlowShow.setProjectId(record.getProjectId());
                productInventoryFlowShow.setWarehouseId(record.getWarehouseId());
                productInventoryFlowShow.setWarehouseName(record.getWarehouseName());
                productInventoryFlowShow.setCreateTime(record.getTransactionTime());

                productInventoryFlowShowList.add(productInventoryFlowShow);
            }
            pageInfo = new PageInfo<ProductInventoryFlowShow>(productInventoryFlowShowList);
            PageInfo<ProductInventoryFlowShow> orderPageInfo = new PageInfo<>(productInventoryFlowShowList);
            pageInfo.setTotal(orderPageInfo.getTotal());
            logger.info("#traceId={}# [OUT] get conditionalSelectInventoryFlow success: result.size():{}", rpcHeader.getTraceId(),productInventoryFlowList.size());
            return pageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            throw e;
        }
    }

}
