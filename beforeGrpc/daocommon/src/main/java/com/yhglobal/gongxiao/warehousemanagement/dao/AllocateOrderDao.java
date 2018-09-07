package com.yhglobal.gongxiao.warehousemanagement.dao;

import com.yhglobal.gongxiao.warehousemanagement.dao.mapping.AllocateOrderMapper;
import com.yhglobal.gongxiao.warehousemanagement.model.AllocationOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.AllocationOrderItem;
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

    public List<AllocationOrder> selectrRecordByCondition(String projectId, String allocateNo, String gongxiaoOutboundOrderNo, String gongxiaoInboundOrderNo, String warehouseOut, String warehouseEnter, String status, String createBeginTime, String createEndTime) {
        return allocateOrderMapper.selectrRecordByCondition(projectId,allocateNo,gongxiaoOutboundOrderNo,gongxiaoInboundOrderNo,warehouseOut,warehouseEnter,status,createBeginTime,createEndTime);
    }

    public AllocationOrder selectInfoByAllocateNo(String projectId, String allocateNo) {
        return allocateOrderMapper.selectInfoByAllocateNo(projectId,allocateNo);
    }

    public int updateByAllocateNo(String projectId,String allocateNo){
        return allocateOrderMapper.updateByAllocateNo(projectId,allocateNo);
    }

}
