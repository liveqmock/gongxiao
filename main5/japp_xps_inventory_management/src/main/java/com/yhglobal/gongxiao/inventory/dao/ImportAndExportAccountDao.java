package com.yhglobal.gongxiao.inventory.dao;

import com.yhglobal.gongxiao.inventory.dao.mapping.ImportAndExportAccountMapper;
import com.yhglobal.gongxiao.inventory.model.InventoryLedger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class ImportAndExportAccountDao {
    @Autowired
    ImportAndExportAccountMapper importAndExportAccountMapper;

    public List<InventoryLedger> selectAccoutInfosByProductModelAndProdutNameAndWarehouseBetweentDate(String projectId, String productCode, String productName, String warehouseId, String startDate, String endDate, String prefix) {
        return importAndExportAccountMapper.selectAccoutInfosByProductModelAndProdutNameAndWarehouseBetweentDate(projectId,productCode,productName,warehouseId,startDate,endDate,prefix);
    }

    public int insert(InventoryLedger inventoryLedger, String prefix) {
        return importAndExportAccountMapper.insert(inventoryLedger,prefix);
    }

    public InventoryLedger selectRecordByProductNameAndProductCodeAndwarehouseAtDate(String projectId, String productName, String productCode, String warehouseName, Date date, String prefix){
        return importAndExportAccountMapper.selectRecordByProductNameAndProductCodeAndwarehouseAtDate(projectId,productName,productCode,warehouseName,date,prefix);
    }
}
