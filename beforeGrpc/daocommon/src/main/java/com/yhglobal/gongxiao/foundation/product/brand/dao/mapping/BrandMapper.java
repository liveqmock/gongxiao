package com.yhglobal.gongxiao.foundation.product.brand.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.product.brand.model.Brand;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface BrandMapper extends BaseMapper {
    @Delete({
        "delete from t_brand",
        "where brandId = #{brandid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer brandid);

    @Insert({
        "insert into t_brand (brandId, brandName, ",
        "status, supplierId, ",
        "supplierName, currentProjectInfo, ",
        "historyProjectInfo, createdById, ",
        "createdByName, createTime, ",
        "lastUpdateTime, tracelog)",
        "values (#{brandid,jdbcType=INTEGER}, #{brandname,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=TINYINT}, #{supplierid,jdbcType=INTEGER}, ",
        "#{suppliername,jdbcType=VARCHAR}, #{currentprojectinfo,jdbcType=VARCHAR}, ",
        "#{historyprojectinfo,jdbcType=VARCHAR}, #{createdbyid,jdbcType=BIGINT}, ",
        "#{createdbyname,jdbcType=VARCHAR}, #{createtime,jdbcType=TIMESTAMP}, ",
        "#{lastupdatetime,jdbcType=TIMESTAMP}, #{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(Brand record);

    @Select({
        "select",
        "brandId, brandName, status, supplierId, supplierName, currentProjectInfo, historyProjectInfo, ",
        "createdById, createdByName, createTime, lastUpdateTime, tracelog",
        "from t_brand",
        "where brandName = #{brandName}"
    })
    Brand selectByPrimaryKey(@Param("brandName") String brandName);

    @Select({
            "select",
            "brandId, brandName, status, supplierId, supplierName, currentProjectInfo, historyProjectInfo, ",
            "createdById, createdByName, createTime, lastUpdateTime, tracelog",
            "from t_brand",
            "where brandId = #{brandId}"
    })
    Brand selectByBrandId(@Param("brandId") String brandId);

    @Select({
            "select",
            "brandId, brandName, status, supplierId, supplierName, currentProjectInfo, historyProjectInfo, ",
            "createdById, createdByName, createTime, lastUpdateTime, tracelog",
            "from t_brand"
    })
    List<Brand> selectAll();


    @Update({
        "update t_brand",
        "set brandName = #{brandname,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=TINYINT},",
          "supplierId = #{supplierid,jdbcType=INTEGER},",
          "supplierName = #{suppliername,jdbcType=VARCHAR},",
          "currentProjectInfo = #{currentprojectinfo,jdbcType=VARCHAR},",
          "historyProjectInfo = #{historyprojectinfo,jdbcType=VARCHAR},",
          "createdById = #{createdbyid,jdbcType=BIGINT},",
          "createdByName = #{createdbyname,jdbcType=VARCHAR},",
          "createTime = #{createtime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastupdatetime,jdbcType=TIMESTAMP},",
          "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
        "where brandId = #{brandid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(Brand record);

    @Update({
        "update t_brand",
        "set brandName = #{brandname,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=TINYINT},",
          "supplierId = #{supplierid,jdbcType=INTEGER},",
          "supplierName = #{suppliername,jdbcType=VARCHAR},",
          "currentProjectInfo = #{currentprojectinfo,jdbcType=VARCHAR},",
          "historyProjectInfo = #{historyprojectinfo,jdbcType=VARCHAR},",
          "createdById = #{createdbyid,jdbcType=BIGINT},",
          "createdByName = #{createdbyname,jdbcType=VARCHAR},",
          "createTime = #{createtime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastupdatetime,jdbcType=TIMESTAMP}",
        "where brandId = #{brandid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Brand record);
}