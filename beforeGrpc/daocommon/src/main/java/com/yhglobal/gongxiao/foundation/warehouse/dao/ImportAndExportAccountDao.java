package com.yhglobal.gongxiao.foundation.warehouse.dao;

import com.yhglobal.gongxiao.foundation.warehouse.dao.mapping.ImportAndExportAccountMapper;
import com.yhglobal.gongxiao.warehouse.model.InventoryLedger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class ImportAndExportAccountDao {
    @Autowired
    ImportAndExportAccountMapper importAndExportAccountMapper;

    public List<InventoryLedger> selectAccoutInfosByProductModelAndProdutNameAndWarehouseBetweentDate(String projectId, String productCode, String productName, String warehouseId, Date startDate, Date endDate) {
        return importAndExportAccountMapper.selectAccoutInfosByProductModelAndProdutNameAndWarehouseBetweentDate(projectId,productCode,productName,warehouseId,startDate,endDate);
    }

    public int insert(InventoryLedger inventoryLedger) {
        return importAndExportAccountMapper.insert(inventoryLedger);
    }

    public InventoryLedger selectRecordByProductNameAndProductCodeAndwarehouseAtDate(String projectId, String productName, String productCode, String warehouseName, Date date){
        return importAndExportAccountMapper.selectRecordByProductNameAndProductCodeAndwarehouseAtDate(projectId,productName,productCode,warehouseName,date);
    }
}
