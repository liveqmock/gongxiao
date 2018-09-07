package com.yhglobal.gongxiao.foundation.product.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.product.model.FoundationProductBasic;
import org.apache.ibatis.annotations.*;

public interface FoundationProductBasicMapper extends BaseMapper {
    @Delete({
        "delete from foundation_product_basic",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into foundation_product_basic (id, easCode, ",
        "wmsCode, easUnit,recordStatus,categoryId, ",
        "categoryName, productLine, ",
        "productLength, productWidth, ",
        "productHeight, productWeight, ",
        "productNetWeight, productGrossWeight, ",
        "boxLength, boxWidth, ",
        "boxHeight, boxWeight, ",
        "guaranteePeriod, packageNumber, ",
        "createTime, lastUpdateTime)",
        "values (#{id,jdbcType=BIGINT}, #{easCode,jdbcType=VARCHAR}, ",
        "#{wmsCode,jdbcType=VARCHAR},#{easUnit},#{recordStatus}, #{categoryId,jdbcType=BIGINT}, ",
        "#{categoryName,jdbcType=VARCHAR}, #{productLine,jdbcType=VARCHAR}, ",
        "#{productLength,jdbcType=INTEGER}, #{productWidth,jdbcType=INTEGER}, ",
        "#{productHeight,jdbcType=INTEGER}, #{productWeight,jdbcType=INTEGER}, ",
        "#{productNetWeight,jdbcType=INTEGER}, #{productGrossWeight,jdbcType=INTEGER}, ",
        "#{boxLength,jdbcType=INTEGER}, #{boxWidth,jdbcType=INTEGER}, ",
        "#{boxHeight,jdbcType=INTEGER}, #{boxWeight,jdbcType=INTEGER}, ",
        "#{guaranteePeriod,jdbcType=INTEGER}, #{packageNumber,jdbcType=INTEGER}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    long insert(FoundationProductBasic record);

    @Select({
        "select",
        "id, easCode, wmsCode,easUnit,recordStatus, categoryId, categoryName, productLine, productLength, ",
        "productWidth, productHeight, productWeight, productNetWeight, productGrossWeight, ",
        "boxLength, boxWidth, boxHeight, boxWeight, guaranteePeriod, packageNumber, createTime, ",
        "lastUpdateTime",
        "from foundation_product_basic",
        "where id = #{id,jdbcType=BIGINT} and recordStatus != 9 "
    })
    FoundationProductBasic getProductBasicById(Long id);

    @Select({
            "select",
            "id, easCode, wmsCode,easUnit,recordStatus, categoryId, categoryName, productLine, productLength, ",
            "productWidth, productHeight, productWeight, productNetWeight, productGrossWeight, ",
            "boxLength, boxWidth, boxHeight, boxWeight, guaranteePeriod, packageNumber, createTime, ",
            "lastUpdateTime",
            "from foundation_product_basic",
            "where easCode = #{easCode} and recordStatus = 3"
    })
    FoundationProductBasic getProductByEasCode(String easCode);

    @Update({
        "update foundation_product_basic",
        "set easCode = #{easCode,jdbcType=VARCHAR},",
          "wmsCode = #{wmsCode,jdbcType=VARCHAR},",
          "categoryId = #{categoryId,jdbcType=BIGINT},",
          "categoryName = #{categoryName,jdbcType=VARCHAR},",
          "productLine = #{productLine,jdbcType=VARCHAR},",
          "productLength = #{productLength,jdbcType=INTEGER},",
          "productWidth = #{productWidth,jdbcType=INTEGER},",
          "productHeight = #{productHeight,jdbcType=INTEGER},",
          "productWeight = #{productWeight,jdbcType=INTEGER},",
          "productNetWeight = #{productNetWeight,jdbcType=INTEGER},",
          "productGrossWeight = #{productGrossWeight,jdbcType=INTEGER},",
          "boxLength = #{boxLength,jdbcType=INTEGER},",
          "boxWidth = #{boxWidth,jdbcType=INTEGER},",
          "boxHeight = #{boxHeight,jdbcType=INTEGER},",
          "boxWeight = #{boxWeight,jdbcType=INTEGER},",
          "guaranteePeriod = #{guaranteePeriod,jdbcType=INTEGER},",
          "packageNumber = #{packageNumber,jdbcType=INTEGER},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateBasicById(FoundationProductBasic record);

    @Update({
            "update foundation_product_basic",
            "set easCode = #{easCode,jdbcType=VARCHAR},",
            "lastUpdateTime = Now()",
            "where id = #{id,jdbcType=BIGINT} and recordStatus != 9"
    })
    int updateProductBasicEasInfo(long id,String easCode);

    @Update({
            "update foundation_product_basic",
            "set ",
            "wmsCode = #{wmsCode,jdbcType=VARCHAR},",
            "lastUpdateTime = Now()",
            "where id = #{id,jdbcType=BIGINT} and recordStatus != 9"
    })
    int updateProductBasicWmsInfo(long id,String wmsCode);

    @Update({
            "update foundation_product_basic",
            "set recordStatus = #{recordStatus},",
            "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
            "where id = #{id,jdbcType=BIGINT} and recordStatus != 9"
    })
    int updateRecordStatus(long id,byte recordStatus);
}