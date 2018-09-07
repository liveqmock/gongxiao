package com.yhglobal.gongxiao.foundation.warehouse.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.warehouse.dao.provider.WarehouseProvider;
import com.yhglobal.gongxiao.foundation.warehouse.model.FoundationWarehouse;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface FoundationWarehouseMapper extends BaseMapper {
    @Delete({
            "delete from foundation_warehouse",
            "where warehouseId = #{warehouseId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long warehouseId);

    @Insert({
            "insert into foundation_warehouse (warehouseId, warehouseCode, ",
            "warehouseName, easWarehouseCode, ",
            "easWarehouseName, wmsWarehouseCode, ",
            "wmsWarehouseName, sendNotitionToWarehouse, ",
            "recordStatus, locationNumber, ",
            "countryCode, countryName, ",
            "provinceId, provinceName, ",
            "cityId, cityName, ",
            "districtId, districtName, ",
            "streetAddress, shortAddress, ",
            "generalContactName, generalphoneNumber, ",
            "generalMobile, createdById, ",
            "createdByName, createTime, ",
            "lastUpdateTime, tracelog)",
            "values (#{warehouseId,jdbcType=BIGINT}, #{warehouseCode,jdbcType=VARCHAR}, ",
            "#{warehouseName,jdbcType=VARCHAR}, #{easWarehouseCode,jdbcType=VARCHAR}, ",
            "#{easWarehouseName,jdbcType=VARCHAR}, #{wmsWarehouseCode,jdbcType=VARCHAR}, ",
            "#{wmsWarehouseName,jdbcType=VARCHAR}, #{sendNotitionToWarehouse,jdbcType=TINYINT}, ",
            "#{recordStatus,jdbcType=TINYINT}, #{locationNumber,jdbcType=INTEGER}, ",
            "#{countryCode,jdbcType=VARCHAR}, #{countryName,jdbcType=VARCHAR}, ",
            "#{provinceId,jdbcType=INTEGER}, #{provinceName,jdbcType=VARCHAR}, ",
            "#{cityId,jdbcType=INTEGER}, #{cityName,jdbcType=VARCHAR}, ",
            "#{districtId,jdbcType=INTEGER}, #{districtName,jdbcType=VARCHAR}, ",
            "#{streetAddress,jdbcType=VARCHAR}, #{shortAddress,jdbcType=VARCHAR}, ",
            "#{generalContactName,jdbcType=VARCHAR}, #{generalphoneNumber,jdbcType=VARCHAR}, ",
            "#{generalMobile,jdbcType=VARCHAR}, #{createdById,jdbcType=BIGINT}, ",
            "#{createdByName,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
            "#{lastUpdateTime,jdbcType=TIMESTAMP}, #{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(FoundationWarehouse record);

    @Select({
            "select",
            "warehouseId, warehouseCode, warehouseName, easWarehouseCode, easWarehouseName, ",
            "wmsWarehouseCode, wmsWarehouseName, sendNotitionToWarehouse, recordStatus, locationNumber, ",
            "countryCode, countryName, provinceId, provinceName, cityId, cityName, districtId, ",
            "districtName, streetAddress, shortAddress, generalContactName, generalphoneNumber, ",
            "generalMobile, createdById, createdByName, createTime, lastUpdateTime, tracelog",
            "from foundation_warehouse",
            "where warehouseId = #{warehouseId,jdbcType=BIGINT}"
    })
    FoundationWarehouse getWarehouseById(Long warehouseId);

    @SelectProvider(type = WarehouseProvider.class, method = "selectWarehouseByCondition")
    List<FoundationWarehouse> selectWarehouseByCondition(@Param("projectId") long projectId,
                                                         @Param("warehouseId") long warehouseId,
                                                         @Param("warehouseName") String warehouseName);

    @Select({
            "select",
            "warehouseId, warehouseCode, warehouseName, easWarehouseCode, easWarehouseName, ",
            "wmsWarehouseCode, wmsWarehouseName, sendNotitionToWarehouse, recordStatus, locationNumber, ",
            "countryCode, countryName, provinceId, provinceName, cityId, cityName, districtId, ",
            "districtName, streetAddress, shortAddress, generalContactName, generalphoneNumber, ",
            "generalMobile, createdById, createdByName, createTime, lastUpdateTime, tracelog",
            "from foundation_warehouse",
            "where warehouseCode = #{warehouseCode}"
    })
    FoundationWarehouse getWarehouseByCode(String warehouseCode);

    @Select({
            "select",
            "warehouseId, warehouseCode, warehouseName, easWarehouseCode, easWarehouseName, ",
            "wmsWarehouseCode, wmsWarehouseName, sendNotitionToWarehouse, recordStatus, locationNumber, ",
            "countryCode, countryName, provinceId, provinceName, cityId, cityName, districtId, ",
            "districtName, streetAddress, shortAddress, generalContactName, generalphoneNumber, ",
            "generalMobile, createdById, createdByName, createTime, lastUpdateTime, tracelog",
            "from foundation_warehouse",
    })
    List<FoundationWarehouse> selectAllWarehouseList();

    @Update({
            "update foundation_warehouse",
            "set warehouseCode = #{warehouseCode,jdbcType=VARCHAR},",
            "warehouseName = #{warehouseName,jdbcType=VARCHAR},",
            "easWarehouseCode = #{easWarehouseCode,jdbcType=VARCHAR},",
            "easWarehouseName = #{easWarehouseName,jdbcType=VARCHAR},",
            "wmsWarehouseCode = #{wmsWarehouseCode,jdbcType=VARCHAR},",
            "wmsWarehouseName = #{wmsWarehouseName,jdbcType=VARCHAR},",
            "sendNotitionToWarehouse = #{sendNotitionToWarehouse,jdbcType=TINYINT},",
            "recordStatus = #{recordStatus,jdbcType=TINYINT},",
            "locationNumber = #{locationNumber,jdbcType=INTEGER},",
            "countryCode = #{countryCode,jdbcType=VARCHAR},",
            "countryName = #{countryName,jdbcType=VARCHAR},",
            "provinceId = #{provinceId,jdbcType=INTEGER},",
            "provinceName = #{provinceName,jdbcType=VARCHAR},",
            "cityId = #{cityId,jdbcType=INTEGER},",
            "cityName = #{cityName,jdbcType=VARCHAR},",
            "districtId = #{districtId,jdbcType=INTEGER},",
            "districtName = #{districtName,jdbcType=VARCHAR},",
            "streetAddress = #{streetAddress,jdbcType=VARCHAR},",
            "shortAddress = #{shortAddress,jdbcType=VARCHAR},",
            "generalContactName = #{generalContactName,jdbcType=VARCHAR},",
            "generalphoneNumber = #{generalphoneNumber,jdbcType=VARCHAR},",
            "generalMobile = #{generalMobile,jdbcType=VARCHAR},",
            "createdById = #{createdById,jdbcType=BIGINT},",
            "createdByName = #{createdByName,jdbcType=VARCHAR},",
            "createTime = #{createTime,jdbcType=TIMESTAMP},",
            "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
            "where warehouseId = #{warehouseId,jdbcType=BIGINT}"
    })
    int updateWarehouse(FoundationWarehouse record);
}