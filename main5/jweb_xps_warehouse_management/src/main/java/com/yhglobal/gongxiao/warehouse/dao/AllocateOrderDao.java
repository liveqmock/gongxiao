package com.yhglobal.gongxiao.warehouse.dao;

import com.yhglobal.gongxiao.warehouse.dao.mapping.AllocateOrderMapper;
import com.yhglobal.gongxiao.warehouse.model.AllocationOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AllocateOrderDao {

    @Autowired
    AllocateOrderMapper allocateOrderMapper;

    public int insertAllocateOrder(AllocationOrder allocationOrder) {
        return allocateOrderMapper.insertAllocateOrder(allocationOrder);
    }

    public List<AllocationOrder> selectrRecordByCondition(String projectId, String allocateNo, String gongxiaoOutboundOrderNo, String gongxiaoInboundOrderNo, String warehouseOut, String warehouseEnter, String createBeginTime, String createEndTime) {
        return allocateOrderMapper.selectrRecordByCondition(projectId,allocateNo,gongxiaoOutboundOrderNo,gongxiaoInboundOrderNo,warehouseOut,warehouseEnter,createBeginTime,createEndTime);
    }

    public AllocationOrder selectInfoByAllocateNo(String projectId, String allocateNo) {
        return allocateOrderMapper.selectInfoByAllocateNo(projectId,allocateNo);
    }

    public int updateAllocateOrderEntry(String alllocateNo, int quantity){
        return allocateOrderMapper.updateAllocateOrderEntry(alllocateNo,quantity);
    }

    public int updateAllocateOrderOut(String alllocateNo, int quantity){
        return allocateOrderMapper.updateAllocateOrderOut(alllocateNo,quantity);
    }
    public List<AllocationOrder> selectRecordByEasFlag(int syncEas){
        return allocateOrderMapper.selectRecordByEasFlag(syncEas);
    }

    public int syncAllocateEasSuccess(int id, String easId, String easOrderNumber, int syncEasStatus){
        return allocateOrderMapper.syncAllocateEasSuccess(id,easId,easOrderNumber,syncEasStatus);
    }

    public int syncAllocateEasException(int id, int syncEasStatus){
        return allocateOrderMapper.syncAllocateEasException(id, syncEasStatus);
    }

    public int syncAllocateEasFail(int id, int syncEasStatus){
        return allocateOrderMapper.syncAllocateEasFail(id, syncEasStatus);
    }

    public int updateAllocateEasIng(String allocateNo,int syncEasStatus, long dataVersion){
        return allocateOrderMapper.updateAllocateEasIng(allocateNo, syncEasStatus, dataVersion);
    }

    public AllocationOrder selectRecordByAllocateNo(String allocateNo){
        return allocateOrderMapper.selectRecordByAllocateNo(allocateNo);
    }
}
