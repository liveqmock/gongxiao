package com.yhglobal.gongxiao.foundation.product.productprice.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.product.price.model.ProductPrice;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ProductPriceMapper extends BaseMapper {
    @Delete({
            "delete from t_product_price",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into t_product_price (productId, projectId, ",
            "projectName, productName, ",
            "orderStatus, productCode, ",
            "easCode, easName, ",
            "easModel, barCode, ",
            "brandId, brandName, ",
            "categoryId, categoryName, ",
            "guidePrice, listedPrice, ",
            "historyGuidancePriceInfo, createTime, ",
            "lastUpdateTime, tracelog)",
            "values (#{productId,jdbcType=BIGINT}, #{projectId,jdbcType=VARCHAR}, ",
            "#{projectName,jdbcType=VARCHAR}, #{productName,jdbcType=VARCHAR}, ",
            "#{orderStatus,jdbcType=INTEGER}, #{productCode,jdbcType=VARCHAR}, ",
            "#{easCode,jdbcType=VARCHAR}, #{easName,jdbcType=VARCHAR}, ",
            "#{easModel,jdbcType=VARCHAR}, #{barCode,jdbcType=VARCHAR}, ",
            "#{brandId,jdbcType=BIGINT}, #{brandName,jdbcType=VARCHAR}, ",
            "#{categoryId,jdbcType=BIGINT}, #{categoryName,jdbcType=VARCHAR}, ",
            "#{guidePrice,jdbcType=INTEGER}, #{listedPrice,jdbcType=INTEGER}, ",
            "#{historyGuidancePriceInfo,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
            "#{lastUpdateTime,jdbcType=TIMESTAMP}, #{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(ProductPrice record);

    @Select({
            "select",
            "productId, projectId, projectName, productName, orderStatus, productCode, easCode, ",
            "easName, easModel, barCode, brandId, brandName, categoryId, categoryName, guidePrice, ",
            "listedPrice, historyGuidancePriceInfo, createTime, lastUpdateTime, tracelog",
            "from t_product_price",
            "where productId = #{productId}"
    })
    ProductPrice selectByProductId(Long productId);

    @Select({
            "select",
            "productId, projectId, projectName, productName, orderStatus, productCode, easCode, ",
            "easName, easModel, barCode, brandId, brandName, categoryId, categoryName, guidePrice, ",
            "listedPrice, historyGuidancePriceInfo, createTime, lastUpdateTime, tracelog",
            "from t_product_price"
    })
    List<ProductPrice> selectAll();

    @Select({
            "select",
            "productId, projectId, projectName, productName, orderStatus, productCode, easCode, ",
            "easName, easModel, barCode, brandId, brandName, categoryId, categoryName, guidePrice, ",
            "listedPrice, historyGuidancePriceInfo, createTime, lastUpdateTime, tracelog",
            "from t_product_price",
            "where brandName = #{brandName}"
    })
    List<ProductPrice> selectByBrandName(String brandName);

    @Select({
            "select",
            "productId, projectId, projectName, productName, orderStatus, productCode, easCode, ",
            "easName, easModel, barCode, brandId, brandName, categoryId, categoryName, guidePrice, ",
            "listedPrice, historyGuidancePriceInfo, createTime, lastUpdateTime, tracelog",
            "from t_product_price",
            "where brandName = #{brandName}"
    })
    List<ProductPrice> selectByBrandId(String brandId);


    @Select({
            "select",
            "productId, projectId, projectName, productName, orderStatus, productCode, easCode, ",
            "easName, easModel, barCode, brandId, brandName, categoryId, categoryName, guidePrice, ",
            "listedPrice, historyGuidancePriceInfo, createTime, lastUpdateTime, tracelog",
            "from t_product_price",
            "where projectId = #{projectId}"
    })
    List<ProductPrice> selectByProjectId(String projectId);



    @Update({
            "update t_product_price",
            "set projectId = #{projectId,jdbcType=VARCHAR},",
            "projectName = #{projectName,jdbcType=VARCHAR},",
            "productName = #{productName,jdbcType=VARCHAR},",
            "orderStatus = #{orderStatus,jdbcType=INTEGER},",
            "productCode = #{productCode,jdbcType=VARCHAR},",
            "barCode = #{barCode,jdbcType=VARCHAR},",
            "brandId = #{brandId,jdbcType=BIGINT},",
            "brandName = #{brandName,jdbcType=VARCHAR},",
            "categoryId = #{categoryId,jdbcType=BIGINT},",
            "categoryName = #{categoryName,jdbcType=VARCHAR},",
            "guidePrice = #{guidePrice,jdbcType=INTEGER},",
            "listedPrice = #{listedPrice,jdbcType=INTEGER},",
            "historyGuidancePriceInfo = #{historyGuidancePriceInfo,jdbcType=VARCHAR},",
            "createTime = #{createTime,jdbcType=TIMESTAMP},",
            "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
            "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(ProductPrice record);

    @Update({
            "update t_product_price",
            "set productCode = #{productCode,jdbcType=VARCHAR},",
            "easCode = #{easCode,jdbcType=VARCHAR},",
            "easName = #{easName,jdbcType=VARCHAR},",
            "easModel = #{easModel,jdbcType=VARCHAR}",
            "where productCode = #{productCode}"
    })
    int updateEasInfo(@Param("productCode")String productCode,
                      @Param("easCode")String easCode,
                      @Param("easName")String easName,
                      @Param("easModel")String easModel);
}