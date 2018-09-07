package com.yhglobal.gongxiao.foundation.warehouse.dao;

import com.yhglobal.gongxiao.foundation.warehouse.model.Warehouse;
import com.yhglobal.gongxiao.foundation.warehouse.dao.mapping.WarehouseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WarehouseDao {
    @Autowired
    private WarehouseMapper warehouseMapper;

    public List<Warehouse> selectAll(){
        return warehouseMapper.selectAll();
    }

    public Warehouse selectByPrimaryKey(Long warehouseid){
        return warehouseMapper.selectByWarehouseId(warehouseid);
    }

    public List<Warehouse> selectByIdAndName(Long warehouseId,String warehouseName){
        return warehouseMapper.selectByIdAndName(warehouseId, warehouseName);
    }

    public int insert(Warehouse record){
        return warehouseMapper.insert(record);
    }

    public String selectWarehouseCodeById(Long warehouseId) {
        return warehouseMapper.selectWarehouseCodeById(warehouseId);
    }

    public String selectDeliverAddressById(String warehouseId){
        Warehouse warehouse = warehouseMapper.selectWarehouseById(warehouseId);
        String detailAddress;
        if (warehouse != null) {
           return detailAddress = warehouse.getProvinceName()+warehouse.getCityName()+warehouse.getDistrictName()+warehouse.getStreetAddress();
        }else {
            return "";
        }

    }

    public Warehouse getWarehouseByEASName(String easWarehouseName){
        return warehouseMapper.getWarehouseByEASName(easWarehouseName);
    }

    public Warehouse getWarehoueseByWmsCode(String wmsWarehouseCode){
        return warehouseMapper.getWarehoueseByWmsCode(wmsWarehouseCode);
    }
}
