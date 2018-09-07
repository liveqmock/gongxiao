package com.yhglobal.gongxiao.inventory.service;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.inventory.bo.AuditRecord;

import java.util.List;

/**
 * 销售审核占用库存
 * @author liuikai
 */
public interface AuditOccupyInventoryService {

    /**
     * 创建审核占用记录
     * @param auditRecord
     * @return
     */
    int createAuditRecord(RpcHeader rpcHeader, AuditRecord auditRecord);

    /**
     * 取消审核
     * @param salesOrderNo 销售单号
     * @return
     */
    int cancelAuditRecord(RpcHeader rpcHeader, String salesOrderNo);

    /**
     *  查询审核记录
     * @param projectId     项目id
     * @param salesOrderNo  销售单号
     * @param productCode   商品编码
     * @return
     */
    List<AuditRecord> getAuditRecord(RpcHeader rpcHeader,String projectId,String salesOrderNo,String productCode);
}
