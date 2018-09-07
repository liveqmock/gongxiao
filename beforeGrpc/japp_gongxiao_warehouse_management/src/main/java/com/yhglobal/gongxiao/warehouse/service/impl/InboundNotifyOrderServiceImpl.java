package com.yhglobal.gongxiao.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.warehouse.service.InboundNotifyOrderService;
import com.yhglobal.gongxiao.warehouse.type.WmsOrderType;
import com.yhglobal.gongxiao.warehousemanagement.bo.WmsInboundBasic;
import com.yhglobal.gongxiao.warehousemanagement.dao.WmsInboundDao;
import com.yhglobal.gongxiao.warehousemanagement.dao.WmsInboundItemDao;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsIntboundRecord;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsIntboundRecordItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * 入库单管理实现类
 *
 * @author liukai
 */
@Service
public class InboundNotifyOrderServiceImpl implements InboundNotifyOrderService {

    private Logger logger = LoggerFactory.getLogger(InboundNotifyOrderServiceImpl.class);

    @Autowired
    private WmsInboundDao wmsInboundDao;

    @Autowired
    private WmsInboundItemDao wmsInboundItemDao;

    @Override
    public PageInfo<WmsIntboundRecord> selectInStorageInfo(RpcHeader rpcHeader, int pageNumber, int pageSize, String projectId, String  gonxiaoInboundNo, String  batchNo, String  orderType, String  warehouseId, String  supplier, String wmsInboundOrderNo,String createTimeBegin, String createTimeTo) {
        try {
            logger.info("#traceId={}# [IN][selectInStorageInfo] params: projectId={},inventoryNum={},batchNo={},orderType={},warehouseId={},supplierName={},wmsInboundOrderNo={},createTimeBegin={},createTimeTo={}", rpcHeader.getTraceId(), projectId, gonxiaoInboundNo, batchNo, orderType,warehouseId, supplier,wmsInboundOrderNo, createTimeBegin, createTimeTo);
            PageInfo<WmsIntboundRecord> pageInfo;
            PageHelper.startPage(pageNumber, pageSize);
            List<WmsIntboundRecord> recordList = wmsInboundDao.selectWmsInStorageInfo(projectId, gonxiaoInboundNo, batchNo, orderType, warehouseId, supplier,wmsInboundOrderNo, createTimeBegin, createTimeTo);  //查询入库单列表

            pageInfo = new PageInfo<WmsIntboundRecord>(recordList);
            logger.info("#traceId={}# [OUT] get selectInStorageInfo success: resultList.size():{}", rpcHeader.getTraceId(), recordList.size());
            return pageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public WmsInboundBasic selectRecordByOrderNo(RpcHeader rpcHeader, String projectId, String gongxiaoInboundOrderNo, String wmsInboundOrderNo) {
        try {
            logger.info("#traceId={}# [IN][selectRecordByOrderNo] params: projectId={},gongxiaoInboundOrderNo={},wmsInboundOrderNo={}", rpcHeader.getTraceId(), projectId, gongxiaoInboundOrderNo,wmsInboundOrderNo);
            WmsInboundBasic result = new WmsInboundBasic();
            WmsIntboundRecord wmsIntboundRecord = wmsInboundDao.selectRecordByOrderNo(projectId, gongxiaoInboundOrderNo,wmsInboundOrderNo);  //查询入库单列表
            if (wmsIntboundRecord != null){
                result.setBatchNo(wmsIntboundRecord.getBatchNo());
                result.setCreateTime(wmsIntboundRecord.getCreateTime());
                result.setEasInboundOrderNo(wmsIntboundRecord.getEasInboundOrderNo());
                result.setGongxiaoInboundOrderNo(wmsIntboundRecord.getGongxiaoInboundOrderNo());
                result.setId(wmsIntboundRecord.getId());
                result.setProjectId(wmsIntboundRecord.getProjectId());
                result.setInStockQuantity(wmsIntboundRecord.getInStockQuantity());
                result.setProductCode(wmsIntboundRecord.getProductCode());
                result.setPurchaseOrderNo(wmsIntboundRecord.getPurchaseOrderNo());
                result.setPurchaseType(wmsIntboundRecord.getPurchaseType());
                result.setSupplier(wmsIntboundRecord.getSupplier());
                result.setWarehouseId(wmsIntboundRecord.getWarehouseId());
                result.setWarehouseName(wmsIntboundRecord.getWarehouseName());
                result.setWmsInboundOrderNo(wmsIntboundRecord.getWmsInboundOrderNo());
                if (wmsIntboundRecord.getInboundType() == WmsOrderType.INBOUND_FOR_PURCHASING_PRODUCT.getInboundOrderCode()){
                    result.setInboundType("采购入库单");
                }else if (wmsIntboundRecord.getInboundType() == WmsOrderType.INBOUND_FOR_TRANSFERRING_PRODUCT.getInboundOrderCode()){
                    result.setInboundType("调拨入库单");
                }else if (wmsIntboundRecord.getInboundType() == WmsOrderType.INBOUND_FOR_RETURNING_PRODUCT.getInboundOrderCode()){
                    result.setInboundType("退货入库单");
                }else if (wmsIntboundRecord.getInboundType() == WmsOrderType.INBOUND_FOR_OTHER_REASON.getInboundOrderCode()){
                    result.setInboundType("其它入库");
                }else {
                    result.setInboundType("不详");
                }
            }
            logger.info("#traceId={}# [OUT] get selectRecordByOrderNo success", rpcHeader.getTraceId());
            return result;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public List<WmsIntboundRecordItem> selectWmsItemRecordByOrderNo(RpcHeader rpcHeader, String projectId, String gongxiaoInboundOrderNo, String wmsInboundOrderNo) {
        try {
            logger.info("#traceId={}# [IN][selectWmsItemRecordByOrderNo] params: projectId={},gongxiaoInboundOrderNo={},wmsInboundOrderNo={}", rpcHeader.getTraceId(), projectId, gongxiaoInboundOrderNo,wmsInboundOrderNo);
            List<WmsIntboundRecordItem> resultList = wmsInboundItemDao.selectRecordByOrderNo(projectId, gongxiaoInboundOrderNo,wmsInboundOrderNo);  //查询入库单列表
            logger.info("#traceId={}# [OUT] get selectWmsItemRecordByOrderNo success: resultList.size():{}", rpcHeader.getTraceId(), resultList.size());
            return resultList;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }
}
