package com.yhglobal.gongxiao.warehouse.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.type.OutboundOrderStatusEnum;
import com.yhglobal.gongxiao.type.WmsInventoryType;
import com.yhglobal.gongxiao.type.WmsOrderType;
import com.yhglobal.gongxiao.util.TablePrefixUtil;
import com.yhglobal.gongxiao.warehouse.dao.OutBoundOrderDao;
import com.yhglobal.gongxiao.warehouse.dao.OutBoundOrderItemDao;
import com.yhglobal.gongxiao.warehouse.dao.WmsOutboundDao;
import com.yhglobal.gongxiao.warehouse.model.OutboundOrder;
import com.yhglobal.gongxiao.warehouse.model.OutboundOrderItem;
import com.yhglobal.gongxiao.warehouse.model.bo.OutboundOrderBasicInfo;
import com.yhglobal.gongxiao.warehouse.model.bo.OutboundOrderItemShowModel;
import com.yhglobal.gongxiao.warehouse.model.bo.OutboundOrderShowModel;
import com.yhglobal.gongxiao.warehouse.service.OutboundShowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 出库单页面交互类
 *
 * @author : liukai
 */
@Service
public class OutboundShowServiceImpl implements OutboundShowService {

    private Logger logger = LoggerFactory.getLogger(OutboundShowServiceImpl.class);

    @Autowired
    OutBoundOrderDao outBoundOrderDao;

    @Autowired
    OutBoundOrderItemDao outBoundOrderItemDao;

    @Autowired
    WmsOutboundDao wmsOutboundDao;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;


    @Override
    public PageInfo<OutboundOrderShowModel> selectOutStorageInfo(GongxiaoRpc.RpcHeader rpcHeader, int pageNumber, int pageSize, String projectId, String inventoryNum, String salseNum, String createTimeBeging, String createTimeLast, String warehouseId, String productCode, String finishTimeBegin, String finishTimeLaste, String supplier, String customer) {
        logger.info("#traceId={}# [IN][selectInStorageInfo] params: projectId={},inventoryNum={},salseNum={},createTimeBeging={},createTimeLast={},warehouseId={},productCode={},finishTimeBegin={},finishTimeLaste={},supplier,customer={}", rpcHeader.getTraceId(), projectId, inventoryNum, salseNum, createTimeBeging, createTimeLast, warehouseId, productCode, finishTimeBegin, finishTimeLaste, supplier, customer);
        PageInfo<OutboundOrderShowModel> pageInfo;
        try {
            String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
            PageHelper.startPage(pageNumber, pageSize);
            List<OutboundOrder> outboundOrderList = outBoundOrderDao.selectOutStorageInfoByRequire(projectId, inventoryNum, salseNum, createTimeBeging, createTimeLast, warehouseId, productCode, finishTimeBegin, finishTimeLaste, supplier, customer, projectPrefix);
            PageInfo<OutboundOrder> outboundOrderShowModelPageInfo = new PageInfo<OutboundOrder>(outboundOrderList);
            long total = outboundOrderShowModelPageInfo.getTotal();
            List<OutboundOrderShowModel> resultList = new ArrayList<>();
            for (OutboundOrder record : outboundOrderList) {
                OutboundOrderShowModel newRecord = new OutboundOrderShowModel();
                newRecord.setProjectId(record.getProjectId());
                newRecord.setProductCode(record.getConnectedProduct());
                newRecord.setWarehouseId(record.getWarehouseId());
                newRecord.setSalesOrderNo(record.getSalesOrderNo());
                newRecord.setGongxiaoOutboundOrderNo(record.getGongxiaoOutboundOrderNo());
                newRecord.setOutboundOrderItemNo(record.getOutboundOrderItemNo());
                newRecord.setInventoryType(record.getInventoryType());
                if (WmsOrderType.OUTBOUND_FOR_RETURN_PRODUCT.getInboundOrderCode() == record.getOutboundType()) {
                    newRecord.setOutStorageType("采购退货出库");
                } else if (WmsOrderType.OUTBOUND_FOR_TRANSFERRING_PRODUCT.getInboundOrderCode() == record.getOutboundType()) {
                    newRecord.setOutStorageType("调拨出库");
                } else if (WmsOrderType.OUTBOUND_FOR_OTHER_REASON.getInboundOrderCode() == record.getOutboundType()) {
                    newRecord.setOutStorageType("其他出库");
                } else if (WmsOrderType.OUTBOUND_FOR_B2B_SELLING_PRODUCT.getInboundOrderCode() == record.getOutboundType()) {
                    newRecord.setOutStorageType("普通出库");
                } else {
                    newRecord.setOutStorageType("不详");
                }

                newRecord.setOrderStatus(record.getOrderStatus());

                newRecord.setSupplier(record.getSupplierName());
                newRecord.setCustomer(record.getCustomer());
                newRecord.setDeliverWarehouse(record.getWarehouseName());
                newRecord.setCreateTime(record.getCreateTime());
                newRecord.setPlantQuantity(record.getTotalQuantity());
                newRecord.setActualQuantity(record.getRealOutStockQuantity());
                newRecord.setDifferentQuantity(record.getTotalQuantity() - record.getRealOutStockQuantity());
                resultList.add(newRecord);
            }

            pageInfo = new PageInfo<OutboundOrderShowModel>(resultList);
            pageInfo.setPageSize(pageSize);
            pageInfo.setPageNum(pageNumber);
            pageInfo.setTotal(total);
            logger.info("#traceId={}# [OUT] get selectOutStorageInfo success: resultList.size()={}", rpcHeader.getTraceId(), resultList.size());

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
        return pageInfo;
    }

    @Override
    public OutboundOrderBasicInfo selectOutStorageInfoById(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String outboundOrderItemNo) {
        logger.info("#traceId={}# [IN][selectInStorageInfo] params: projectId={},inventoryNum={}", rpcHeader.getTraceId(), projectId, outboundOrderItemNo);
        try {
            String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
            OutboundOrder outboundOrder = outBoundOrderDao.getOutStorageInfoById(projectId, outboundOrderItemNo, projectPrefix);
            if (outboundOrder != null) {
                OutboundOrderBasicInfo result = new OutboundOrderBasicInfo();
                result.setProjectId(outboundOrder.getProjectId());
                result.setSalesOrderNo(outboundOrder.getSalesOrderNo());
                result.setGongxiaoOutboundOrderNo(outboundOrder.getGongxiaoOutboundOrderNo());
                result.setOutboundOrderItemNo(outboundOrder.getOutboundOrderItemNo());
                result.setBachNo(outboundOrder.getBatchNo());

                if (WmsOrderType.OUTBOUND_FOR_B2B_SELLING_PRODUCT.getInboundOrderCode() == outboundOrder.getOutboundType()) {
                    result.setOutStorageType("普通出库");
                } else if (WmsOrderType.OUTBOUND_FOR_TRANSFERRING_PRODUCT.getInboundOrderCode() == outboundOrder.getOutboundType()) {
                    result.setOutStorageType("调拨出库");
                } else if (WmsOrderType.OUTBOUND_FOR_OTHER_REASON.getInboundOrderCode() == outboundOrder.getOutboundType()) {
                    result.setOutStorageType("其他出库");
                } else {
                    result.setOutStorageType("不详");
                }
                if (OutboundOrderStatusEnum.OUTBOUND_ORDER_WAIT.getStatus() == outboundOrder.getOrderStatus()) {
                    result.setOrderStatus("等待发货");
                } else if (OutboundOrderStatusEnum.OUTBOUND_ORDER_DELIVER_PART.getStatus() == outboundOrder.getOrderStatus()) {
                    result.setOrderStatus("部分发货");
                } else if (OutboundOrderStatusEnum.OUTBOUND_ORDER_DELIVER_FINISH.getStatus() == outboundOrder.getOrderStatus()) {
                    result.setOrderStatus("发货完成");
                } else if (OutboundOrderStatusEnum.OUTBOUND_ORDER_CANCEL.getStatus() == outboundOrder.getOrderStatus()) {
                    result.setOrderStatus("订单已取消");
                } else if (OutboundOrderStatusEnum.OUTBOUND_ORDER_SIGHT.getStatus() == outboundOrder.getOrderStatus()) {
                    result.setOrderStatus("订单已签收");
                } else if (OutboundOrderStatusEnum.OUTBOUND_ORDER_CLOSE.getStatus() == outboundOrder.getOrderStatus()) {
                    result.setOrderStatus("订单已关闭");
                } else {
                    result.setOrderStatus("订单同步到wms中..");
                }
                result.setDeliverWarehouse(outboundOrder.getWarehouseName());
                result.setDeliverAddress(outboundOrder.getDeliverAddress());
                result.setSupplier(outboundOrder.getSupplierName());
                result.setCustomer(outboundOrder.getCustomer());
                result.setCreateTime(outboundOrder.getCreateTime());
                result.setEstimateOutTime(outboundOrder.getExpectedArrivalTime());
                result.setLastUpdateTime(outboundOrder.getLastUpdateTime());
                result.setContectPeople(outboundOrder.getContactsPeople());
                result.setPhoneNum(outboundOrder.getPhoneNum());
                result.setReceiveAdrress(outboundOrder.getShippingAddress());
                List<TraceLog> traceLog = JSON.parseArray(outboundOrder.getTracelog(), TraceLog.class);
                result.setTraceLog(traceLog);
                result.setRemark(outboundOrder.getNote());
                result.setTransporter(outboundOrder.getTransporter());
                result.setTmsOrdNo(outboundOrder.getTmsOrdNo());
                if (outboundOrder.getTmsOrderStatus() == 0) {
                    result.setTransportStatus("在途");
                } else {
                    result.setTransportStatus("已签收");
                }
                result.setTmsSignedBy(outboundOrder.getTmsSignedBy());
                result.setTmsSignedPhone(outboundOrder.getTmsSignedPhone());
                result.setTmsSingedTime(outboundOrder.getTmsSingedTime());
                result.setRemark(outboundOrder.getNote());
                logger.info("#traceId={}# [OUT] get selectOutStorageInfoById success: resultList.size()={}", rpcHeader.getTraceId(), 1);
                return result;
            } else {
                logger.info("#traceId={}# [OUT] get selectOutStorageInfoById success: resultList.size()={}", rpcHeader.getTraceId(), 0);
                return null;
            }
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<OutboundOrderItemShowModel> selectOutboundOrderItem(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String gongxiaoOutboundOrderNo) {
        logger.info("#traceId={}# [IN][selectOutboundOrderItem] params: projectId={},gongxiaoOutboundOrderNo={}", rpcHeader.getTraceId(), projectId, gongxiaoOutboundOrderNo);
        try {
            String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
            List<OutboundOrderItem> outboundOrderItemList = outBoundOrderItemDao.selectOutboundOrderItems(projectId, gongxiaoOutboundOrderNo, projectPrefix);
            List<OutboundOrderItemShowModel> resultList = new ArrayList<>();
            for (OutboundOrderItem record : outboundOrderItemList) {
                OutboundOrderItemShowModel newRecord = new OutboundOrderItemShowModel();
                newRecord.setProjectId(record.getProjectId());
                newRecord.setOutboundOrderItemNo(record.getOutboundOrderItemNo());
                newRecord.setBatchNo(record.getBatchNo());
                newRecord.setProductCode(record.getProductCode());
                newRecord.setProductName(record.getProductName());
                newRecord.setProductUnit(record.getProductUnit());
                if (record.getInventoryType() == WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType()) {           //1:可销售库存
                    newRecord.setInventoryType("普通好机");
                } else if (record.getInventoryType() == WmsInventoryType.DEFECTIVE.getInventoryType()) {  //101:残次
                    newRecord.setInventoryType("残次");
                } else if (record.getInventoryType() == WmsInventoryType.ENGINE_DAMAGE.getInventoryType()) {  //102:机损
                    newRecord.setInventoryType("机损");
                } else if (record.getInventoryType() == WmsInventoryType.BOX_DAMAGE.getInventoryType()) {  //103:箱损
                    newRecord.setInventoryType("箱损");
                } else if (record.getInventoryType() == WmsInventoryType.FROZEN_STOCK.getInventoryType()) {  //201:冻结库存
                    newRecord.setInventoryType("冻结库存");
                } else if (record.getInventoryType() == WmsInventoryType.TRANSPORTATION_INVENTORY.getInventoryType()) {  //301:在途库存
                    newRecord.setInventoryType("在途库存");
                }
                newRecord.setPlanOutStockQuantity(record.getTotalQuantity());
                newRecord.setActualOutStockQuantity(record.getRealOutStockQuantity());
                newRecord.setDifferentOutStockQuantity(record.getTotalQuantity() - record.getRealOutStockQuantity());
                newRecord.setCreateTime(record.getCreateTime());
                resultList.add(newRecord);
            }
            logger.info("#traceId={}# [OUT] get selectOutboundOrderItem success: resultList.size()={}", rpcHeader.getTraceId(), resultList.size());
            return resultList;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public OutboundOrderItem selectRecordBySalseNoAndProduct(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String salesOrderNo, String productCode) {
        logger.info("#traceId={}# [IN][selectRecordBySalseNoAndProduct] params: projectId={},salesOrderNo={},productCode={}", rpcHeader.getTraceId(), projectId, salesOrderNo, productCode);
        try {
            String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
            OutboundOrderItem result = outBoundOrderItemDao.selectRecordBySalseNoAndProduct(projectId, salesOrderNo, productCode, projectPrefix);
            logger.info("#traceId={}# [OUT] get selectRecordBySalseNoAndProduct success", rpcHeader.getTraceId());
            return result;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public List<OutboundOrder> selectRecordBySalesNo(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String salesNo) {
        logger.info("#traceId={}# [IN][selectRecordBySalesNo] params: projectId={},salesNo={}", rpcHeader.getTraceId(), projectId, salesNo);
        try {
            String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
            List<OutboundOrder> resultList = outBoundOrderDao.selectRecordBySalesNo(projectId, salesNo, projectPrefix);
            logger.info("#traceId={}# [OUT] get selectRecordBySalesNo success: resultList.size()={}", rpcHeader.getTraceId(), resultList.size());
            return resultList;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }


    }

    @Override
    public List<OutboundOrderItem> selectRecordItemByOutboundOrderNo(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String gongxiaoOutboundOrderNo) {
        logger.info("#traceId={}# [IN][selectRecordItemByOutboundOrderNo]: projectId={},inventoryNum={}", rpcHeader.getTraceId(), projectId, gongxiaoOutboundOrderNo);
        try {
            String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
            List<OutboundOrderItem> resultList = outBoundOrderItemDao.selectRecordItemByOutboundOrderNo(projectId, gongxiaoOutboundOrderNo, projectPrefix);
            logger.info("#traceId={}# [OUT] get selectRecordItemByOutboundOrderNo success: resultList.size()={}", rpcHeader.getTraceId(), resultList.size());
            return resultList;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public int modifyReturnQuantity(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String gongxiaoOutboundOrderNo, String productCode, int quantity) {
        logger.info("#traceId={}# [IN][modifyReturnQuantity]: projectId={},gongxiaoOutboundOrderNo={},productCode={},quantity={}", rpcHeader.getTraceId(), projectId, gongxiaoOutboundOrderNo, productCode, quantity);
        try {
            String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
            int result = outBoundOrderItemDao.midifyReturnQuantity(gongxiaoOutboundOrderNo, productCode, quantity, projectPrefix);
            logger.info("#traceId={}# [OUT] get modifyReturnQuantity success", rpcHeader.getTraceId());
            return result;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

}
