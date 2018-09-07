package com.yhglobal.gongxiao.foundation.product.category.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.product.category.model.ProductCategory;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ProductCategoryMapper extends BaseMapper{
    @Delete({
        "delete from t_product_category",
        "where categoryId = #{categoryId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer categoryId);

    @Insert({
        "insert into t_product_category (categoryId, categoryName, ",
        "categoryCode, status, ",
        "createdById, createdByName, ",
        "createTime, lastUpdateTime, ",
        "tracelog)",
        "values (#{categoryId,jdbcType=INTEGER}, #{categoryName,jdbcType=VARCHAR}, ",
        "#{categoryCode,jdbcType=VARCHAR}, #{status,jdbcType=TINYINT}, ",
        "#{createdById,jdbcType=BIGINT}, #{createdByName,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP}, ",
        "#{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(ProductCategory record);

    @Select({
        "select",
        "categoryId, categoryName, categoryCode, status, createdById, createdByName, ",
        "createTime, lastUpdateTime, tracelog",
        "from t_product_category",
        "where categoryId = #{categoryId,jdbcType=INTEGER}"
    })
    ProductCategory selectByPrimaryKey(Integer categoryId);


    @Select({
            "select",
            "categoryId, categoryName, categoryCode, status, createdById, createdByName, ",
            "createTime, lastUpdateTime, tracelog",
            "from t_product_category"
    })
    List<ProductCategory> selectAll();


    @Update({
        "update t_product_category",
        "set categoryName = #{categoryName,jdbcType=VARCHAR},",
          "categoryCode = #{categoryCode,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=TINYINT},",
          "createdById = #{createdById,jdbcType=BIGINT},",
          "createdByName = #{createdByName,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
        "where categoryId = #{categoryId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(ProductCategory record);

    @Update({
        "update t_product_category",
        "set categoryName = #{categoryName,jdbcType=VARCHAR},",
          "categoryCode = #{categoryCode,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=TINYINT},",
          "createdById = #{createdById,jdbcType=BIGINT},",
          "createdByName = #{createdByName,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
        "where categoryId = #{categoryId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ProductCategory record);
}