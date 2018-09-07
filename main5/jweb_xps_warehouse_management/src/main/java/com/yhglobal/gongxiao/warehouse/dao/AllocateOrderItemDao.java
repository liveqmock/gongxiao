package com.yhglobal.gongxiao.warehouse.dao;

import com.yhglobal.gongxiao.warehouse.dao.mapping.AllocateOrderItemMapper;
import com.yhglobal.gongxiao.warehouse.model.AllocationOrderItem;
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

    public int updateAllocateEntryItem(long id, int quantity){
        return allocateOrderItemMapper.updateAllocateEntryItem(id,quantity);
    }

    public int updateAllocateOutItem(long id, int quantity){
        return allocateOrderItemMapper.updateAllocateOutItem(id,quantity);
    }

    public AllocationOrderItem getAllocationOrderItem(String alllocateNo,String productCode){
        return allocateOrderItemMapper.getAllocationOrderItem(alllocateNo,productCode);
    }

    public List<AllocationOrderItem> selectOutboundOrderByNo(String allocateNo){
        return allocateOrderItemMapper.selectOutboundOrderByNo(allocateNo);
    }

    public int updateItemEasInfo(int id, String entryId, String materialCode){
        return allocateOrderItemMapper.updateItemEasInfo(id,entryId,materialCode);
    }

    public List<AllocationOrderItem> selectRecorByAllocateNo(String allocateNo){
        return allocateOrderItemMapper.selectRecorByAllocateNo(allocateNo);
    }
}
