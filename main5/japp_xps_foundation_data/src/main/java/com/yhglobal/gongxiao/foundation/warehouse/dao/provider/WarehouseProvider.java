package com.yhglobal.gongxiao.foundation.warehouse.dao.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public class WarehouseProvider {
    public String selectWarehouseByCondition(@Param("projectId") long projectId,
                                             @Param("warehouseId") long warehouseId,
                                             @Param("warehouseName") String warehouseName) {
        return new SQL() {{
            SELECT("warehouseId, warehouseCode, warehouseName, easWarehouseCode, easWarehouseName ",
                    "wmsWarehouseCode, wmsWarehouseName, sendNotitionToWarehouse, recordStatus, locationNumber ",
                    "countryCode, countryName, provinceId, provinceName, cityId, cityName, districtId ",
                    "districtName, streetAddress, shortAddress, generalContactName, generalphoneNumber ",
                    "generalMobile, createdById, createdByName, createTime, lastUpdateTime, tracelog");
            FROM("foundation_warehouse");
            WHERE("projectId=#{projectId}");
            if (warehouseId != 0) {
                WHERE("warehouseId=#{warehouseId}");
            }
            if (warehouseName != null && "".equals(warehouseName)) {
                WHERE("warehouseName like concat('%', #{warehouseName},'%')");
            }
        }}.toString();
    }

}
