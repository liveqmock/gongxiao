package com.yhglobal.gongxiao.foundation.warehouse.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.warehouse.model.Warehouse;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface WarehouseMapper extends BaseMapper{
    @Delete({
        "delete from t_warehouse",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
            "insert into t_warehouse (id, warehouseId, ",
            "warehouseName, warehouseCode, ",
            "easWarehouseCode, easWarehouseName, ",
            "wmsWarehouseCode, wmsWarehouseName, ",
            "status, locationNumber, ",
            "longitude, latitude, ",
            "countryCode, countryName, ",
            "provinceId, provinceName, ",
            "cityId, cityName, ",
            "districtName, streetAddress, ",
            "shortAddress, generalContactName, ",
            "generalphoneNumber, generalMobile, ",
            "reservationContactName, reservationPhoneNumber, ",
            "reservationMobile, createdById, ",
            "createdByName, createTime, ",
            "lastUpdateTime, tracelog)",
            "values (#{id,jdbcType=INTEGER}, #{warehouseId,jdbcType=BIGINT}, ",
            "#{warehouseName,jdbcType=VARCHAR}, #{warehouseCode,jdbcType=VARCHAR}, ",
            "#{easWarehouseCode,jdbcType=VARCHAR}, #{easWarehouseName,jdbcType=VARCHAR}, ",
            "#{wmsWarehouseCode,jdbcType=VARCHAR}, #{wmsWarehouseName,jdbcType=VARCHAR}, ",
            "#{status,jdbcType=TINYINT}, #{locationNumber,jdbcType=INTEGER}, ",
            "#{longitude,jdbcType=VARCHAR}, #{latitude,jdbcType=VARCHAR}, ",
            "#{countryCode,jdbcType=VARCHAR}, #{countryName,jdbcType=VARCHAR}, ",
            "#{provinceId,jdbcType=INTEGER}, #{provinceName,jdbcType=VARCHAR}, ",
            "#{cityId,jdbcType=INTEGER}, #{cityName,jdbcType=VARCHAR}, ",
            "#{districtName,jdbcType=VARCHAR}, #{streetAddress,jdbcType=VARCHAR}, ",
            "#{shortAddress,jdbcType=VARCHAR}, #{generalContactName,jdbcType=VARCHAR}, ",
            "#{generalphoneNumber,jdbcType=VARCHAR}, #{generalMobile,jdbcType=VARCHAR}, ",
            "#{reservationContactName,jdbcType=VARCHAR}, #{reservationPhoneNumber,jdbcType=VARCHAR}, ",
            "#{reservationMobile,jdbcType=VARCHAR}, #{createdById,jdbcType=BIGINT}, ",
            "#{createdByName,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
            "#{lastUpdateTime,jdbcType=TIMESTAMP}, #{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(Warehouse record);


    @Select({
        "select",
            "id, warehouseId, warehouseName, warehouseCode, easWarehouseCode, easWarehouseName, ",
            "wmsWarehouseCode, wmsWarehouseName, status, locationNumber, longitude, latitude, ",
            "countryCode, countryName, provinceId, provinceName, cityId, cityName, districtName, ",
            "streetAddress, shortAddress, generalContactName, generalphoneNumber, generalMobile, ",
            "reservationContactName, reservationPhoneNumber, reservationMobile, createdById, ",
            "createdByName, createTime, lastUpdateTime, tracelog",
        "from t_warehouse",
        "where warehouseId = #{warehouseId,jdbcType=INTEGER}"
    })
    Warehouse selectByWarehouseId(Long warehouseId);

    @Select({
            "select",
            "id, warehouseId, warehouseName, warehouseCode, easWarehouseCode, easWarehouseName, ",
            "wmsWarehouseCode, wmsWarehouseName, status, locationNumber, longitude, latitude, ",
            "countryCode, countryName, provinceId, provinceName, cityId, cityName, districtName, ",
            "streetAddress, shortAddress, generalContactName, generalphoneNumber, generalMobile, ",
            "reservationContactName, reservationPhoneNumber, reservationMobile, createdById, ",
            "createdByName, createTime, lastUpdateTime, tracelog",
            "from t_warehouse"
    })
    List<Warehouse> selectAll();

    @Select({
            "<script>",
                "select",
                "id, warehouseId, warehouseName, warehouseCode, status, locationNumber, longitude, ",
                "latitude, countryCode, countryName, provinceId, provinceName, cityId, cityName, ",
                "districtName, streetAddress, shortAddress, generalContactName, generalphoneNumber, ",
                "generalMobile, reservationContactName, reservationPhoneNumber, reservationMobile, ",
                "createdById, createdByName, createTime, lastUpdateTime, tracelog",
                "from t_warehouse",
                "where <if test=\"warehouseId !=null \">warehouseId = #{warehouseId} </if>",
                        "<if test=\"warehouseName !=null \">warehouseName = #{warehouseName} </if>",
            "</script>"
    })
    List<Warehouse> selectByIdAndName(@Param("warehouseId") Long warehouseId,
                                      @Param("warehouseName") String warehouseName);


    @Update({
        "update t_warehouse",
        "set warehouseId = #{warehouseId,jdbcType=BIGINT},",
          "warehouseName = #{warehouseName,jdbcType=VARCHAR},",
          "warehouseCode = #{warehouseCode,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=TINYINT},",
          "locationNumber = #{locationNumber,jdbcType=INTEGER},",
          "longitude = #{longitude,jdbcType=VARCHAR},",
          "latitude = #{latitude,jdbcType=VARCHAR},",
          "countryCode = #{countryCode,jdbcType=VARCHAR},",
          "countryName = #{countryName,jdbcType=VARCHAR},",
          "provinceId = #{provinceId,jdbcType=INTEGER},",
          "provinceName = #{provinceName,jdbcType=VARCHAR},",
          "cityId = #{cityId,jdbcType=INTEGER},",
          "cityName = #{cityName,jdbcType=VARCHAR},",
          "districtName = #{districtName,jdbcType=VARCHAR},",
          "streetAddress = #{streetAddress,jdbcType=VARCHAR},",
          "shortAddress = #{shortAddress,jdbcType=VARCHAR},",
          "generalContactName = #{generalContactName,jdbcType=VARCHAR},",
          "generalphoneNumber = #{generalphoneNumber,jdbcType=VARCHAR},",
          "generalMobile = #{generalMobile,jdbcType=VARCHAR},",
          "reservationContactName = #{reservationContactName,jdbcType=VARCHAR},",
          "reservationPhoneNumber = #{reservationPhoneNumber,jdbcType=VARCHAR},",
          "reservationMobile = #{reservationMobile,jdbcType=VARCHAR},",
          "createdById = #{createdById,jdbcType=BIGINT},",
          "createdByName = #{createdByName,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(Warehouse record);

    @Update({
        "update t_warehouse",
        "set warehouseId = #{warehouseId,jdbcType=BIGINT},",
          "warehouseName = #{warehouseName,jdbcType=VARCHAR},",
          "warehouseCode = #{warehouseCode,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=TINYINT},",
          "locationNumber = #{locationNumber,jdbcType=INTEGER},",
          "longitude = #{longitude,jdbcType=VARCHAR},",
          "latitude = #{latitude,jdbcType=VARCHAR},",
          "countryCode = #{countryCode,jdbcType=VARCHAR},",
          "countryName = #{countryName,jdbcType=VARCHAR},",
          "provinceId = #{provinceId,jdbcType=INTEGER},",
          "provinceName = #{provinceName,jdbcType=VARCHAR},",
          "cityId = #{cityId,jdbcType=INTEGER},",
          "cityName = #{cityName,jdbcType=VARCHAR},",
          "districtName = #{districtName,jdbcType=VARCHAR},",
          "streetAddress = #{streetAddress,jdbcType=VARCHAR},",
          "shortAddress = #{shortAddress,jdbcType=VARCHAR},",
          "generalContactName = #{generalContactName,jdbcType=VARCHAR},",
          "generalphoneNumber = #{generalphoneNumber,jdbcType=VARCHAR},",
          "generalMobile = #{generalMobile,jdbcType=VARCHAR},",
          "reservationContactName = #{reservationContactName,jdbcType=VARCHAR},",
          "reservationPhoneNumber = #{reservationPhoneNumber,jdbcType=VARCHAR},",
          "reservationMobile = #{reservationMobile,jdbcType=VARCHAR},",
          "createdById = #{createdById,jdbcType=BIGINT},",
          "createdByName = #{createdByName,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Warehouse record);

    @Select({
            "select",
            "warehouseCode",
            "from t_warehouse where warehouseId = #{warehouseId}"
    })
    String selectWarehouseCodeById(@Param("warehouseId") Long warehouseId);

    @Select({
            "select",
            "provinceName,cityName,districtName,streetAddress",
            "from t_warehouse where warehouseId = #{warehouseId}"
    })
    Warehouse selectWarehouseById(@Param("warehouseId") String warehouseId);

    @Select({
            "select",
            "id, warehouseId, warehouseName, warehouseCode, easWarehouseCode, easWarehouseName, ",
            "wmsWarehouseCode, wmsWarehouseName, status, locationNumber, longitude, latitude, ",
            "countryCode, countryName, provinceId, provinceName, cityId, cityName, districtName, ",
            "streetAddress, shortAddress, generalContactName, generalphoneNumber, generalMobile, ",
            "reservationContactName, reservationPhoneNumber, reservationMobile, createdById, ",
            "createdByName, createTime, lastUpdateTime, tracelog",
            "from t_warehouse",
            "where easWarehouseName = #{easWarehouseName}"
    })
    Warehouse getWarehouseByEASName(@Param("easWarehouseName") String easWarehouseName);

    @Select({
            "select",
            "id, warehouseId, warehouseName, warehouseCode, easWarehouseCode, easWarehouseName, ",
            "wmsWarehouseCode, wmsWarehouseName, status, locationNumber, longitude, latitude, ",
            "countryCode, countryName, provinceId, provinceName, cityId, cityName, districtName, ",
            "streetAddress, shortAddress, generalContactName, generalphoneNumber, generalMobile, ",
            "reservationContactName, reservationPhoneNumber, reservationMobile, createdById, ",
            "createdByName, createTime, lastUpdateTime, tracelog",
            "from t_warehouse",
            "where wmsWarehouseCode = #{wmsWarehouseCode}"
    })
    Warehouse getWarehoueseByWmsCode(@Param("wmsWarehouseCode") String wmsWarehouseCode);
}