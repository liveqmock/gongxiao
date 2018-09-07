package com.yhglobal.gongxiao.inventory.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.warehouse.dao.AuditRecordDao;
import com.yhglobal.gongxiao.inventory.bo.AuditRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 审核订单占用库存记录
 * @author liukai
 */
@Service
public class AuditOccupyInventoryServiceImpl implements AuditOccupyInventoryService {

    private static final Logger logger = LoggerFactory.getLogger(AuditOccupyInventoryServiceImpl.class);

    @Autowired
    AuditRecordDao auditRecordDao;


    @Override
    public int createAuditRecord(RpcHeader rpcHeader, AuditRecord auditRecord) {
        logger.info("#traceId={}# [IN][createAuditRecord]: auditRecord={}", rpcHeader.getTraceId(), JSON.toJSONString(auditRecord));

        try {
            int i = auditRecordDao.insertAuditRecord(auditRecord);
            logger.info("#traceId={}# [OUT] get createAuditRecord success", rpcHeader.getTraceId());
            return i;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            return 0;
        }

    }

    @Override
    public int cancelAuditRecord(RpcHeader rpcHeader, String salesOrderNo) {
        logger.info("#traceId={}# [IN][cancelAuditRecord]: salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);

        try {
            int i = auditRecordDao.updateAuditRecord(salesOrderNo);
            logger.info("#traceId={}# [OUT] get cancelAuditRecord success", rpcHeader.getTraceId());
            return i;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            return 0;
        }
    }

    @Override
    public List<AuditRecord> getAuditRecord(RpcHeader rpcHeader, String projectId, String salesOrderNo, String productCode) {
        logger.info("#traceId={}# [IN][getAuditRecord]: projectId={},salesOrderNo={},productCode={}", rpcHeader.getTraceId(),projectId,salesOrderNo,productCode);
        List<AuditRecord> recordList = new ArrayList<>();
        try {
            recordList = auditRecordDao.getAuditRecord(projectId,salesOrderNo,productCode);
            logger.info("#traceId={}# [OUT] get getAuditRecord success ,recordList.size()={}", rpcHeader.getTraceId(),recordList.size());
            return recordList;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }
}
