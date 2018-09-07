package com.yhglobal.gongxiao.foundation.warehouse.dao;

import com.yhglobal.gongxiao.inventory.model.ProductInventoryFlow;
import com.yhglobal.gongxiao.foundation.warehouse.dao.mapping.ProductInventoryFlowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class ProductInventoryFlowDao {
    @Autowired
    ProductInventoryFlowMapper productInventoryFlowMapper;

    public int insert(ProductInventoryFlow productInventoryFlow){
        return productInventoryFlowMapper.insert(productInventoryFlow);
    }

    public List<ProductInventoryFlow> selectRecordByProductNameAndProductCodeAndwarehouseAtDate(String projectId, String productName, String productCode, String warehouseName, Date dateTime){
        return productInventoryFlowMapper.selectRecordByProductNameAndProductCodeAndwarehouseAtDate(projectId,productName, productCode, warehouseName, dateTime);
    }
    public List<ProductInventoryFlow> selectRecordByProductNameAndProductCodeAndwarehouseBetweenDate(String projectId,String productName, String productCode, String inventoryFlowId, String warehouseName, String beginDate, String endDate) {
        return productInventoryFlowMapper.selectRecordByProductNameAndProductCodeAndwarehouseBetweenDate(projectId,productName,productCode,inventoryFlowId,warehouseName,beginDate,endDate);
    }

}
