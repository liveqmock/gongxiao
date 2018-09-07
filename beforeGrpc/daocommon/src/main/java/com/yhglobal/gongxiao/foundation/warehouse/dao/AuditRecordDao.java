package com.yhglobal.gongxiao.foundation.warehouse.dao;

import com.yhglobal.gongxiao.foundation.warehouse.dao.mapping.AuditRecordMapper;
import com.yhglobal.gongxiao.inventory.bo.AuditRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuditRecordDao {

    @Autowired
    AuditRecordMapper auditRecordMapper;

    public int insertAuditRecord(AuditRecord auditRecord){
        return auditRecordMapper.insertAuditRecord(auditRecord);
    }

    public List<AuditRecord> getAuditRecord(String projectId,String salesOrderNo,String productCode){
        return auditRecordMapper.getAuditRecord(projectId,salesOrderNo,productCode);
    }

    public int updateAuditRecord(String salesOrderNo){
        return auditRecordMapper.updateAuditRecord(salesOrderNo);
    }
}
