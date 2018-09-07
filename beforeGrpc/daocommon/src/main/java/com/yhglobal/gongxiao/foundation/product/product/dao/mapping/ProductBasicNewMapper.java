package com.yhglobal.gongxiao.foundation.product.product.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.product.product.model.ProductBasic;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface ProductBasicNewMapper extends BaseMapper {
    @Delete({
        "delete from product_basic",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into product_basic (id, categoryId, easCode,wmsCode,",
        "categoryName, productLine, ",
        "productLength, productWidth, ",
        "productHeight, productWeight, ",
        "productNetWeight, productGrossWeight, ",
        "boxLength, boxWidth, ",
        "boxHeight, boxWeight, ",
        "guaranteePeriod, packageNumber, ",
        "createTime, lastUpdateTime)",
        "values (#{id,jdbcType=BIGINT}, #{categoryId,jdbcType=BIGINT}, #{easCode},#{wmsCode}, ",
        "#{categoryName,jdbcType=VARCHAR}, #{productLine,jdbcType=VARCHAR}, ",
        "#{productLength,jdbcType=INTEGER}, #{productWidth,jdbcType=INTEGER}, ",
        "#{productHeight,jdbcType=INTEGER}, #{productWeight,jdbcType=INTEGER}, ",
        "#{productNetWeight,jdbcType=INTEGER}, #{productGrossWeight,jdbcType=INTEGER}, ",
        "#{boxLength,jdbcType=INTEGER}, #{boxWidth,jdbcType=INTEGER}, ",
        "#{boxHeight,jdbcType=INTEGER}, #{boxWeight,jdbcType=INTEGER}, ",
        "#{guaranteePeriod,jdbcType=INTEGER}, #{packageNumber,jdbcType=INTEGER}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true, keyProperty="id")//添加该行，product中的id将被自动添加
    int insert(ProductBasic record);

    @Select({
        "select",
        "id, categoryId,easCode,wmsCode, categoryName, productLine, productLength, productWidth, productHeight, ",
        "productWeight, productNetWeight, productGrossWeight, boxLength, boxWidth, boxHeight, ",
        "boxWeight, guaranteePeriod, packageNumber, createTime, lastUpdateTime",
        "from product_basic",
        "where id = #{id,jdbcType=BIGINT}"
    })

    ProductBasic getById(Long id);

    @Select({
            "select",
            "id, categoryId,easCode,wmsCode, categoryName, productLine, productLength, productWidth, productHeight, ",
            "productWeight, productNetWeight, productGrossWeight, boxLength, boxWidth, boxHeight, ",
            "boxWeight, guaranteePeriod, packageNumber, createTime, lastUpdateTime",
            "from product_basic"
    })
    List<ProductBasic> selectAllProduct();

    @Update({
        "update product_basic",
        "set categoryId = #{categoryId,jdbcType=BIGINT},",
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
    int updateById(ProductBasic record);
}