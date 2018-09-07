package com.yhglobal.gongxiao.warehousemanagement.bo;

import com.yhglobal.gongxiao.warehousemanagement.model.AllocationOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.AllocationOrderItem;

import java.io.Serializable;
import java.util.List;

/**
 * 新增的调拨单信息
 * @author liukai
 */
public class AllocateOrderInfo implements Serializable {
    private AllocationOrder allocationOrder;
    private List<AllocationOrderItem> allocationOrderItem;

    public AllocationOrder getAllocationOrder() {
        return allocationOrder;
    }

    public void setAllocationOrder(AllocationOrder allocationOrder) {
        this.allocationOrder = allocationOrder;
    }

    public List<AllocationOrderItem> getAllocationOrderItem() {
        return allocationOrderItem;
    }

    public void setAllocationOrderItem(List<AllocationOrderItem> allocationOrderItem) {
        this.allocationOrderItem = allocationOrderItem;
    }
}
