package com.yhglobal.gongxiao.inventory.dao;

import com.yhglobal.gongxiao.inventory.dao.mapping.CheckEasInventoryItemMapper;
import com.yhglobal.gongxiao.inventory.model.bo.EasAndXpsInventoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CheckEasInventoryItemDao {
    @Autowired
    CheckEasInventoryItemMapper checkEasInventoryItemMapper;

    public int insertRecords(List<EasAndXpsInventoryModel> easAndXpsInventoryModelList, String prefix){
        return checkEasInventoryItemMapper.insertRecords(easAndXpsInventoryModelList,prefix);
    }

    public List<EasAndXpsInventoryModel> selectRecords(String projectId,String warehouseId,String productCode,String productName, String prefix){
        return checkEasInventoryItemMapper.selectEasInventoryCheckRecords(projectId,warehouseId,productCode,productName,prefix);
    }
}
