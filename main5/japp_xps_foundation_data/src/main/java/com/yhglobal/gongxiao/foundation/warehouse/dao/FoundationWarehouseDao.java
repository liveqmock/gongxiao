package com.yhglobal.gongxiao.foundation.warehouse.dao;

import com.yhglobal.gongxiao.foundation.warehouse.dao.mapping.FoundationWarehouseMapper;
import com.yhglobal.gongxiao.foundation.warehouse.model.FoundationWarehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FoundationWarehouseDao {
    @Autowired
    private FoundationWarehouseMapper warehouseMapper;

    /**
     * 插入新的仓库信息
     * @param record
     * @return
     */
    public int insert(FoundationWarehouse record){
        return warehouseMapper.insert(record);
    }

    /**
     * 获取所有的仓库信息列表
     * @return
     */
    public List<FoundationWarehouse> selectAllWarehouseList(){
        return warehouseMapper.selectAllWarehouseList();
    }

    /**
     * 通过warehouseId获取warehouse信息
     * @param warehouseId
     * @return
     */
    public FoundationWarehouse getWarehouseById(Long warehouseId){
        return warehouseMapper.getWarehouseById(warehouseId);
    }

    /**
     * 通过warehouseCode获取仓库信息
     * @param warehosueCode
     * @return
     */
    public FoundationWarehouse getWarehouseByCode(String  warehosueCode) {
        return warehouseMapper.getWarehouseByCode(warehosueCode);
    }

    /**
     * 条件查询仓库列表
     * @param warehouseId 仓库ID
     * @param warehouseName 仓库名称
     * @return
     */
    public List<FoundationWarehouse> selectWarehouseListByCondition(long projectId,
                                                                    long warehouseId,
                                                                    String warehouseName){
        return warehouseMapper.selectWarehouseByCondition(projectId,warehouseId,warehouseName);
    }

    public int updateWarehouse(FoundationWarehouse foundationWarehouse){
        return warehouseMapper.updateWarehouse(foundationWarehouse);
    }

    /**
     * 通过warehouseid获取warehouse详细地址
     * @param warehouseId
     * @return
     */
    public String getAddressDetailById(long warehouseId){
        FoundationWarehouse warehouse = warehouseMapper.getWarehouseById(warehouseId);
        String detailAddress;
        if (warehouse != null) {
           return detailAddress = warehouse.getProvinceName()+warehouse.getCityName()+warehouse.getDistrictName()+warehouse.getStreetAddress();
        }else {
            return "";
        }
    }
}
