package com.yhglobal.gongxiao.inventory.dao;

import com.yhglobal.gongxiao.inventory.dao.mapping.ProductInventoryFlowMapper;
import com.yhglobal.gongxiao.inventory.model.ProductInventoryFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class ProductInventoryFlowDao {
    @Autowired
    ProductInventoryFlowMapper productInventoryFlowMapper;

    public int insert(ProductInventoryFlow productInventoryFlow, String prefix){
        return productInventoryFlowMapper.insert(productInventoryFlow,prefix);
    }

    public List<ProductInventoryFlow> selectRecordByProductNameAndProductCodeAndwarehouseAtDate(String projectId, String productName, String productCode, String warehouseName, Date dateTime, String prefix){
        return productInventoryFlowMapper.selectRecordByProductNameAndProductCodeAndwarehouseAtDate(projectId,productName, productCode, warehouseName, dateTime,prefix);
    }
    public List<ProductInventoryFlow> selectRecordByProductNameAndProductCodeAndwarehouseBetweenDate(String projectId,String productName, String productCode, String inventoryFlowId, String warehouseName, String beginDate, String endDate, String prefix) {
        return productInventoryFlowMapper.selectRecordByProductNameAndProductCodeAndwarehouseBetweenDate(projectId,productName,productCode,inventoryFlowId,warehouseName,beginDate,endDate,prefix);
    }

}
