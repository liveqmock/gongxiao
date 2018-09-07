package com.yhglobal.gongxiao.warehouse.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.warehouse.dao.WmsInboundDao;
import com.yhglobal.gongxiao.warehouse.dao.WmsInboundItemDao;
import com.yhglobal.gongxiao.warehouse.model.WmsIntboundRecord;
import com.yhglobal.gongxiao.warehouse.model.WmsIntboundRecordItem;
import com.yhglobal.gongxiao.warehouse.service.InboundNotifyOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public PageInfo<WmsIntboundRecord> selectInStorageInfo(GongxiaoRpc.RpcHeader rpcHeader, int pageNumber, int pageSize, String projectId, String gonxiaoInboundNo, String purchaseNo, String productCode, String createTimeBegin, String createTimeTo, String warehouseId, String supplierName) {
        try {
            logger.info("#traceId={}# [IN][selectInStorageInfo] params: projectId={},inventoryNum={},purchaseNo={},productCode={},createTimeBegin={},createTimeTo={},warehouseId={},supplierName={}", rpcHeader.getTraceId(), projectId, gonxiaoInboundNo, purchaseNo, productCode, createTimeBegin, createTimeTo, warehouseId, supplierName);
            PageInfo<WmsIntboundRecord> pageInfo;
            PageHelper.startPage(pageNumber, pageSize);
            List<WmsIntboundRecord> recordList = wmsInboundDao.selectWmsInStorageInfo(projectId, gonxiaoInboundNo, purchaseNo, productCode, createTimeBegin, createTimeTo, warehouseId, supplierName);  //查询入库单列表
            pageInfo = new PageInfo<WmsIntboundRecord>(recordList);
            pageInfo.setPageNum(pageNumber);
            pageInfo.setPageSize(pageSize);
            pageInfo.setTotal(pageInfo.getTotal());
            logger.info("#traceId={}# [OUT] get selectInStorageInfo success: resultList.size():{}", rpcHeader.getTraceId(), recordList.size());
            return pageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public WmsIntboundRecord selectRecordByOrderNo(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String gongxiaoInboundOrderNo, String wmsInboundOrderNo) {
        try {
            logger.info("#traceId={}# [IN][selectRecordByOrderNo] params: projectId={},gongxiaoInboundOrderNo={},wmsInboundOrderNo={}", rpcHeader.getTraceId(), projectId, gongxiaoInboundOrderNo, wmsInboundOrderNo);
            WmsIntboundRecord result = wmsInboundDao.selectRecordByOrderNo(projectId, gongxiaoInboundOrderNo, wmsInboundOrderNo);  //查询入库单列表
            logger.info("#traceId={}# [OUT] get selectRecordByOrderNo success", rpcHeader.getTraceId());
            return result;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public List<WmsIntboundRecordItem> selectWmsItemRecordByOrderNo(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String gongxiaoInboundOrderNo, String wmsInboundOrderNo) {
        try {
            logger.info("#traceId={}# [IN][selectWmsItemRecordByOrderNo] params: projectId={},gongxiaoInboundOrderNo={},wmsInboundOrderNo={}", rpcHeader.getTraceId(), projectId, gongxiaoInboundOrderNo,wmsInboundOrderNo);
            List<WmsIntboundRecordItem> resultList = wmsInboundItemDao.selectRecordByOrderNo(projectId, gongxiaoInboundOrderNo,wmsInboundOrderNo);  //查询入库单列表
            logger.info("#traceId={}# [OUT] get selectWmsItemRecordByOrderNo success: resultList.size():{}", rpcHeader.getTraceId(), resultList.size());
            return resultList;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }
}
