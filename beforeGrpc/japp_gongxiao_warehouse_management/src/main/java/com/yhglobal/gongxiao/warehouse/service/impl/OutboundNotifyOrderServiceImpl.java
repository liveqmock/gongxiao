package com.yhglobal.gongxiao.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.warehouse.service.OutboundNotifyOrderService;
import com.yhglobal.gongxiao.warehouse.type.WmsOrderType;
import com.yhglobal.gongxiao.warehousemanagement.bo.WmsOutboundBasic;
import com.yhglobal.gongxiao.warehousemanagement.dao.WmsOutboundDao;
import com.yhglobal.gongxiao.warehousemanagement.dao.WmsOutboundItemDao;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsIntboundRecord;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsOutboundRecord;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsOutboundRecordItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 出库单管理实现类
 * @author liukai
 */
@Service
public class OutboundNotifyOrderServiceImpl implements OutboundNotifyOrderService {

    private Logger logger = LoggerFactory.getLogger(OutboundNotifyOrderServiceImpl.class);

    @Autowired
    private WmsOutboundDao wmsOutboundDao;

    @Autowired
    private WmsOutboundItemDao wmsOutboundItemDao;

    @Override
    public PageInfo<WmsOutboundRecord> selectOutStorageInfo(RpcHeader rpcHeader, int pageNumber, int pageSize, String projectId, String  gonxiaoOutboundNo, String  salesNo, String  orderType,String  customer, String createTimeBegin, String createTimeTo) {
        try {
            logger.info("#traceId={}# [IN][selectOutStorageInfo] params: projectId={},gongxiaoOutboundOrderNo={},wmsOutboundNo={},orderType={},supplierName={},createTimeBegin={},createTimeTo={}", rpcHeader.getTraceId(), projectId, gonxiaoOutboundNo, salesNo, orderType, customer, createTimeBegin, createTimeTo);
            PageInfo<WmsOutboundRecord> pageInfo;
            PageHelper.startPage(pageNumber, pageSize);
            List<WmsOutboundRecord> recordList = wmsOutboundDao.selectWmsOutStorageInfo(projectId, gonxiaoOutboundNo, salesNo, orderType, customer, createTimeBegin, createTimeTo);  //查询入库单列表

            pageInfo = new PageInfo<WmsOutboundRecord>(recordList);
            logger.info("#traceId={}# [OUT] get selectOutStorageInfo success: resultList.size():{}", rpcHeader.getTraceId(), recordList.size());
            return pageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public WmsOutboundBasic selectRecordByOrderNo(RpcHeader rpcHeader, String gongxiaoOutboundOrderNo, String wmsOutboundOrderNo) {
        try {
            logger.info("#traceId={}# [IN][selectRecordByOrderNo] params: gongxiaoOutboundOrderNo={}，wmsOutboundOrderNo={}", rpcHeader.getTraceId(), gongxiaoOutboundOrderNo,wmsOutboundOrderNo);
            WmsOutboundBasic result = new WmsOutboundBasic();
            WmsOutboundRecord wmsOutboundRecord = wmsOutboundDao.selectRecordByOrderNo(gongxiaoOutboundOrderNo,wmsOutboundOrderNo);  //查询入库单列表
            if (wmsOutboundRecord != null){
                result.setCreateTime(wmsOutboundRecord.getCreateTime());
                result.setCustomer(wmsOutboundRecord.getCustomer());
                result.setEasOutboundOrderNo(wmsOutboundRecord.getEasOutboundOrderNo());
                result.setGongxiaoOutboundOrderNo(wmsOutboundRecord.getGongxiaoOutboundOrderNo());
                result.setId(wmsOutboundRecord.getId());
                result.setOutStockQuantity(wmsOutboundRecord.getOutStockQuantity());
                result.setProductCode(wmsOutboundRecord.getProductCode());
                result.setProjectId(wmsOutboundRecord.getProjectId());
                result.setPurchaseOrderNo(wmsOutboundRecord.getPurchaseOrderNo());
                result.setSalesOrderNo(wmsOutboundRecord.getSalesOrderNo());
                result.setTmsOutboundOrderNo(wmsOutboundRecord.getTmsOutboundOrderNo());
                result.setCustomer(wmsOutboundRecord.getCustomer());
                result.setWarehouseId(wmsOutboundRecord.getWarehouseId());
                result.setWarehouseName(wmsOutboundRecord.getWarehouseName());
                result.setWmsOutboundOrderNo(wmsOutboundRecord.getWmsOutboundOrderNo());
                if (wmsOutboundRecord.getOutboundType() == WmsOrderType.OUTBOUND_FOR_TRANSFERRING_PRODUCT.getInboundOrderCode()){
                    result.setOutboundType("调拨出库单/移库");
                }else if (wmsOutboundRecord.getOutboundType() == WmsOrderType.OUTBOUND_FOR_RETURN_PRODUCT.getInboundOrderCode()){
                    result.setOutboundType("采购退货出库");
                }else if (wmsOutboundRecord.getOutboundType() == WmsOrderType.OUTBOUND_FOR_B2B_SELLING_PRODUCT.getInboundOrderCode()){
                    result.setOutboundType("普通出库单");
                }else if (wmsOutboundRecord.getOutboundType() == WmsOrderType.OUTBOUND_FOR_EXCHANGING_PRODUCT.getInboundOrderCode()){
                    result.setOutboundType("换货出库单");
                }else if (wmsOutboundRecord.getOutboundType() == WmsOrderType.OUTBOUND_FOR_B2C_SELLING_PRODUCT.getInboundOrderCode()){
                    result.setOutboundType("交易出库单");
                }else if (wmsOutboundRecord.getOutboundType() == WmsOrderType.OUTBOUND_FOR_DISCARDING_PRODUCT.getInboundOrderCode()){
                    result.setOutboundType("报废出库");
                }else if (wmsOutboundRecord.getOutboundType() == WmsOrderType.OUTBOUND_FOR_OTHER_REASON.getInboundOrderCode()){
                    result.setOutboundType("其它出库");
                }else {
                    result.setOutboundType("不详");
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
    public List<WmsOutboundRecordItem> selectWmsItemRecordByOrderNo(RpcHeader rpcHeader, String projectId, String gongxiaoOutboundOrderNo, String wmsOutboundOrderNo) {
        try {
            logger.info("#traceId={}# [IN][selectWmsItemRecordByOrderNo] params: projectId={},gongxiaoOutboundOrderNo={},wmsOutboundOrderNo={}", rpcHeader.getTraceId(), projectId, gongxiaoOutboundOrderNo,wmsOutboundOrderNo);
            List<WmsOutboundRecordItem> resultList = wmsOutboundItemDao.selectRecordByOrderNo(projectId, gongxiaoOutboundOrderNo, wmsOutboundOrderNo);  //查询入库单列表
            logger.info("#traceId={}# [OUT] get selectWmsItemRecordByOrderNo success: resultList.size():{}", rpcHeader.getTraceId(), resultList.size());
            return resultList;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }
}
