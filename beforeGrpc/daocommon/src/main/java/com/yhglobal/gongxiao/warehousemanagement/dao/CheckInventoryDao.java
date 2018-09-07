package com.yhglobal.gongxiao.warehousemanagement.dao;

import com.yhglobal.gongxiao.warehousemanagement.bo.InventoryCheckModel;
import com.yhglobal.gongxiao.warehousemanagement.dao.mapping.CheckInventoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CheckInventoryDao {
    @Autowired
    CheckInventoryMapper checkInventoryMapper;

    public int insertRecords(List<InventoryCheckModel> inventoryCheckModelList){
        return checkInventoryMapper.insertRecords(inventoryCheckModelList);
    }

    public List<InventoryCheckModel> selectRecords(String projectId,String warehouseId,String productCode,String productName){
        return checkInventoryMapper.selectRecords(projectId,warehouseId,productCode,productName);
    }
}
