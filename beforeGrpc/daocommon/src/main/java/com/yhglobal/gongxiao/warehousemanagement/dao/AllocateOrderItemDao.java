package com.yhglobal.gongxiao.warehousemanagement.dao;

import com.yhglobal.gongxiao.warehousemanagement.dao.mapping.AllocateOrderItemMapper;
import com.yhglobal.gongxiao.warehousemanagement.model.AllocationOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AllocateOrderItemDao {
    @Autowired
    AllocateOrderItemMapper allocateOrderItemMapper;

    public int insertAllocateOrderItems(List<AllocationOrderItem> allocationOrderItemList) {
        return allocateOrderItemMapper.insertAllocateOrderItems(allocationOrderItemList);
    }

    public List<AllocationOrderItem> getAllocationOrderItemInfos(String projectId, String allocateNo) {
        return allocateOrderItemMapper.getAllocationOrderItemInfos(projectId,allocateNo);
    }

    public int updateByAllocateNo(String projectId, String allocateNo){
        return allocateOrderItemMapper.updateByAllocateNo(projectId,allocateNo);
    }
}
