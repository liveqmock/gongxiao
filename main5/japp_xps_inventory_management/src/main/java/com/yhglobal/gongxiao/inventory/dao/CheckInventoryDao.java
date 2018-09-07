package com.yhglobal.gongxiao.inventory.dao;

import com.yhglobal.gongxiao.inventory.dao.mapping.CheckInventoryMapper;
import com.yhglobal.gongxiao.inventory.model.bo.InventoryCheckModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CheckInventoryDao {
    @Autowired
    CheckInventoryMapper checkInventoryMapper;

    public int insertRecords(List<InventoryCheckModel> inventoryCheckModelList, String prefix){
        return checkInventoryMapper.insertRecords(inventoryCheckModelList,prefix);
    }

    public List<InventoryCheckModel> selectRecords(String projectId,String warehouseId,String productCode,String productName, String prefix){
        return checkInventoryMapper.selectInventoryCheckRecords(projectId,warehouseId,productCode,productName,prefix);
    }
}
