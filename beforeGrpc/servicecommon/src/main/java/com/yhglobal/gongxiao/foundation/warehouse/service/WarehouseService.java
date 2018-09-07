package com.yhglobal.gongxiao.foundation.warehouse.service;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.warehouse.model.Warehouse;

import java.util.Date;
import java.util.List;

/**
 * 仓库基本资料相关服务
 *
 * @author: 陈浩
 **/
public interface WarehouseService {


    /**
     * 新添加warehouse
     *
     * @param rpcHeader rpc调用的header
     * @param warehouseName
     * @param warehouseCode
     * @param longitude
     * @param latitude
     * @param countryCode
     * @param countryName
     * @param provinceId
     * @param provinceName
     * @param cityId
     * @param cityName
     * @param districtName
     * @param streetAddress
     * @param createdById
     * @param createdByName
     * @param createTime
     * @return
     */
    int  createWarehouse(RpcHeader rpcHeader, String warehouseName, String warehouseCode, String longitude, String latitude,
                         String countryCode, String countryName, Integer provinceId, String provinceName,
                         Integer cityId, String cityName, String districtName, String streetAddress,
                         Long createdById, String createdByName, Date createTime);


    /**
     * 通过仓库ID获取仓库信息
     *
     * @param rpcHeader rpc调用的header
     * @param warehouseId
     * @return
     */
    Warehouse getWarehouseById(RpcHeader rpcHeader, String warehouseId);


    /**
     * 获取所有仓库信息
     *
     * @param rpcHeader rpc调用的header
     * @return
     */
    List<Warehouse> selectAll(RpcHeader rpcHeader);

    /**
     * 通过仓库名获取仓库信息
     * @param easWarehouseName
     * @return
     */
    Warehouse getWarehouseByEASName(String easWarehouseName);

    /**
     * 通过wms仓库code获取仓库信息
     * @return
     */
    Warehouse getwarehoueseByWmswarehouseCode(String WmswarehouseCode);

}
