package com.yhglobal.gongxiao.inventory.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.inventory.model.bo.AuditRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AuditRecordMapper extends BaseMapper {

    @Insert({
            "insert into audit_inventory_record (",
            "projectId, salesOrderNo, productCode, quantity, ",
            "createrId, createrName, createTime",
            ")",
            "values (",
            "#{record.projectId}, #{record.salesOrderNo}, ",
            "#{record.productCode}, #{record.quantity}, ",
            "#{record.createrId}, #{record.createrName}, #{record.createTime} ",
            ")"
    })
    int insertAuditRecord(@Param("record") AuditRecord auditRecord);

    @Select({
            "select",
            "projectId, salesOrderNo, productCode, quantity, ",
            "createrId, createrName, createTime",
            "from audit_inventory_record",
            "where projectId = #{projectId} and salesOrderNo = #{salesOrderNo} and productCode = #{productCode}"
    })
    List<AuditRecord> getAuditRecord(@Param("projectId") String projectId, @Param("salesOrderNo") String salesOrderNo, @Param("productCode") String productCode);

    @Update({
            "update audit_inventory_record",
            "set quantity = 0 ",
            "where salesOrderNo = #{salesOrderNo}"
    })
    int updateAuditRecord(@Param("salesOrderNo") String salesOrderNo);
}
