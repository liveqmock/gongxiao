package com.yhglobal.gongxiao.foundation.product.productBasic.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.productBasic.dao.provider.ProductBasicProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ProductBasicMapper extends BaseMapper {
    @Delete({
            "delete from t_product_basic",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into t_product_basic (id, productName, ",
            "status, productCode, ",
            "productModel, easCode, ",
            "easName, easModel, ",
            "p12NC, easMaterialCode, ",
            "guidePrice, listedPrice, ",
            "barCode, productType, ",
            "brandId, brandName, ",
            "categoryId, categoryName, ",
            "productLine, productLength, ",
            "productWidth, productHeight, ",
            "productNetWeight, productGrossWeight, ",
            "boxLength, boxWidth, ",
            "boxHeight, boxWeight, ",
            "guaranteePeriod, packageNumber, ",
            "createTime, lastUpdateTime)",
            "values (#{id,jdbcType=BIGINT}, #{productName,jdbcType=VARCHAR}, ",
            "#{status,jdbcType=INTEGER}, #{productCode,jdbcType=VARCHAR}, ",
            "#{productModel,jdbcType=VARCHAR}, #{easCode,jdbcType=VARCHAR}, ",
            "#{easName,jdbcType=VARCHAR}, #{easModel,jdbcType=VARCHAR}, ",
            "#{p12NC,jdbcType=VARCHAR}, #{easMaterialCode,jdbcType=VARCHAR}, ",
            "#{guidePrice,jdbcType=BIGINT}, #{listedPrice,jdbcType=BIGINT}, ",
            "#{barCode,jdbcType=VARCHAR}, #{productType,jdbcType=VARCHAR}, ",
            "#{brandId,jdbcType=BIGINT}, #{brandName,jdbcType=VARCHAR}, ",
            "#{categoryId,jdbcType=BIGINT}, #{categoryName,jdbcType=VARCHAR}, ",
            "#{productLine,jdbcType=VARCHAR}, #{productLength,jdbcType=INTEGER}, ",
            "#{productWidth,jdbcType=INTEGER}, #{productHeight,jdbcType=INTEGER}, ",
            "#{productNetWeight,jdbcType=INTEGER}, #{productGrossWeight,jdbcType=INTEGER}, ",
            "#{boxLength,jdbcType=INTEGER}, #{boxWidth,jdbcType=INTEGER}, ",
            "#{boxHeight,jdbcType=INTEGER}, #{boxWeight,jdbcType=INTEGER}, ",
            "#{guaranteePeriod,jdbcType=INTEGER}, #{packageNumber,jdbcType=INTEGER}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP})"
    })
    int insert(ProductBasic record);

    @Select({
            "select",
            "id, productName, status, productCode,WMSCode, productModel, easCode,easUnitCode, easName, easModel, ",
            "p12NC, easMaterialCode, guidePrice, saleGuidePrice,listedPrice, barCode, productType, brandId, ",
            "brandName, categoryId, categoryName, productLine, productLength, productWidth, ",
            "productHeight, productNetWeight, productGrossWeight, boxLength, boxWidth, boxHeight, ",
            "boxWeight, guaranteePeriod, packageNumber, createTime, lastUpdateTime",
            "from t_product_basic",
            "where productCode = #{productCode}"
    })
    ProductBasic getByProductCode(@Param("productCode") String productCode);

    @Select({
            "select",
            "id, productName, status, productCode,WMSCode, productModel, easCode,easUnitCode, easName, easModel, ",
            "p12NC, easMaterialCode, guidePrice,saleGuidePrice, listedPrice, barCode, productType, brandId, ",
            "brandName, categoryId, categoryName, productLine, productLength, productWidth, ",
            "productHeight, productNetWeight, productGrossWeight, boxLength, boxWidth, boxHeight, ",
            "boxWeight, guaranteePeriod, packageNumber, createTime, lastUpdateTime",
            "from t_product_basic",
            "where productModel = #{productModel}"
    })
    ProductBasic getByProductModel(@Param("productModel") String productCode);

    @Select({
            "select",
            "id, productName, status, productCode, WMSCode,productModel, easCode, easName,easUnitCode, easModel, ",
            "p12NC, easMaterialCode, guidePrice,saleGuidePrice, listedPrice, barCode, productType, brandId, ",
            "brandName, categoryId, categoryName, productLine, productLength, productWidth, ",
            "productHeight, productNetWeight, productGrossWeight, boxLength, boxWidth, boxHeight, ",
            "boxWeight, guaranteePeriod, packageNumber, createTime, lastUpdateTime",
            "from t_product_basic",
            "where id = #{id}"
    })
    ProductBasic getProductById(@Param("id") String id);


    @Select({
            "select",
            "id, productName, status, productCode,WMSCode, productModel, easCode, easName, easModel, easUnitCode,",
            "p12NC, easMaterialCode, guidePrice,saleGuidePrice, listedPrice, barCode, productType, brandId, ",
            "brandName, categoryId, categoryName, productLine, productLength, productWidth, ",
            "productHeight, productNetWeight, productGrossWeight, boxLength, boxWidth, boxHeight, ",
            "boxWeight, guaranteePeriod, packageNumber, createTime, lastUpdateTime",
            "from t_product_basic"
    })
    List<ProductBasic> selectAll();

    @Select({
            "select",
            "id, productName, status, productCode,WMSCode, productModel, easCode, easName, easUnitCode,easModel, ",
            "p12NC, easMaterialCode, guidePrice,saleGuidePrice, listedPrice, barCode, productType, brandId, ",
            "brandName, categoryId, categoryName, productLine, productLength, productWidth, ",
            "productHeight, productNetWeight, productGrossWeight, boxLength, boxWidth, boxHeight, ",
            "boxWeight, guaranteePeriod, packageNumber, createTime, lastUpdateTime",
            "from t_product_basic",
            "limit #{startRow},#{pageSize}"
    })
    List<ProductBasic> selectPaging(@Param("startRow") int startRow, @Param("pageSize") int pageSize);

    @Select({
            "<script>",
            "select",
            "id, productName, status, productCode,WMSCode, productId, barCode, productType, easUnitCode,brandId, ",
            "brandName, categoryId, categoryName, productLine, productLength, productWidth, ",
            "productHeight, productNetWeight,saleGuidePrice, productGrossWeight, boxLength, boxWidth, boxHeight, ",
            "boxWeight, guaranteePeriod, packageNumber, createTime, lastUpdateTime",
            "from t_product_basic",
            "where 1=1 <if test='productCode !=null '>AND productCode = #{productCode} </if>",
            "<if test='productName !=null '>AND  productName = #{productName} </if>",
            "<if test='barCode !=null '>AND barCode = #{barCode} </if>",
//            "<if test='productCategory !=null '>AND categoryId = #{productCategory} </if>",
            "<if test='brand !=null '>AND brandId = #{brand} </if>",
            "<if test='productId !=null '>AND productId = #{productId} </if>",
            "<if test='productCategory !=null '>AND categoryId = #{productCategory} </if>",
            "limit #{pageNumber},#{pageSize}",
            "</script>"
    })
    List<ProductBasic> selectWithCondition(@Param("pageNumber") int pageNumber, @Param("pageSize") int pageSize,
                                           @Param("productCode") String productCode, @Param("productName") String productName,
                                           @Param("barCode") String barCode, @Param("productCategory") Long productCategory,
                                           @Param("brand") Long brand, @Param("productId") Long productId,
                                           @Param("sellerProductCategory") Long sellerProductCategory);

    @Select({
            "<script>",
            "select",
            "count(id)",
            "from t_product_basic",
            "where 1=1 <if test='productCode !=null '>AND productCode = #{productCode} </if>",
            "<if test='productName !=null '>AND  productName = #{productName} </if>",
            "<if test='barCode !=null '>AND barCode = #{barCode} </if>",
//            "<if test='productCategory !=null '>AND categoryId = #{productCategory} </if>",
            "<if test='brand !=null '>AND brandId = #{brand} </if>",
            "<if test='productId !=null '>AND productId = #{productId} </if>",
            "<if test='productCategory !=null '>AND categoryId = #{productCategory} </if>",
            "</script>"
    })
    int selectCountWithCondition( @Param("productCode") String productCode, @Param("productName") String productName,
                                  @Param("barCode") String barCode, @Param("productCategory") Long productCategory,
                                  @Param("brand") Long brand, @Param("productId") Long productId,
                                  @Param("sellerProductCategory") Long sellerProductCategory);


    @Update({
            "update t_product_basic",
            "set productName = #{productName,jdbcType=VARCHAR},",
            "status = #{status,jdbcType=INTEGER},",
            "productCode = #{productCode,jdbcType=VARCHAR},",
            "productId = #{productId,jdbcType=BIGINT},",
            "barCode = #{barCode,jdbcType=VARCHAR},",
            "productType = #{productType,jdbcType=VARCHAR},",
            "brandId = #{brandId,jdbcType=BIGINT},",
            "brandName = #{brandName,jdbcType=VARCHAR},",
            "categoryId = #{categoryId,jdbcType=BIGINT},",
            "categoryName = #{categoryName,jdbcType=VARCHAR},",
            "productLine = #{productLine,jdbcType=VARCHAR},",
            "productLength = #{productLength,jdbcType=INTEGER},",
            "productWidth = #{productWidth,jdbcType=INTEGER},",
            "productHeight = #{productHeight,jdbcType=INTEGER},",
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
    int updateByPrimaryKey(ProductBasic record);

    @SelectProvider(type = ProductBasicProvider.class, method = "getList")
    List<ProductBasic> getProductList(@Param("queryStr") String queryStr);

    @Select({
            "select",
            "id, productName, status, productCode,WMSCode, productModel, easCode, easName,easUnitCode, easModel, ",
            "p12NC, easMaterialCode, guidePrice, saleGuidePrice,listedPrice, barCode, productType, brandId, ",
            "brandName, categoryId, categoryName, productLine, productLength, productWidth, ",
            "productHeight, productNetWeight, productGrossWeight, boxLength, boxWidth, boxHeight, ",
            "boxWeight, guaranteePeriod, packageNumber, createTime, lastUpdateTime",
            "from t_product_basic where productCode = #{productCode}"
    })
    ProductBasic selectByProductCode(@Param("productCode") String productCode);


    @SelectProvider(type = ProductBasicProvider.class, method = "selectProductByCondition")
    List<ProductBasic> selectProductByCondition(@Param("projectId") Long projectId,
                                                @Param("productCode") String productCode,
                                                @Param("productName") String productName);

    @Select({
            "select",
            "id, productName, status, productCode,WMSCode, productModel, easCode, easName,easUnitCode, easModel, ",
            "p12NC, easMaterialCode, guidePrice,saleGuidePrice, listedPrice, barCode, productType, brandId, ",
            "brandName, categoryId, categoryName, productLine, productLength, productWidth, ",
            "productHeight, productNetWeight, productGrossWeight, boxLength, boxWidth, boxHeight, ",
            "boxWeight, guaranteePeriod, packageNumber, createTime, lastUpdateTime",
            "from t_product_basic",
            "where WMSCode = #{WMSCode}"
    })
    ProductBasic getByWmsProductCode(@Param("WMSCode") String WMSCode);

    @Select({
            "select",
            "id, productName, status, productCode,WMSCode, productModel, easCode, easName,easUnitCode, easModel, ",
            "p12NC, easMaterialCode, guidePrice,saleGuidePrice, listedPrice, barCode, productType, brandId, ",
            "brandName, categoryId, categoryName, productLine, productLength, productWidth, ",
            "productHeight, productNetWeight, productGrossWeight, boxLength, boxWidth, boxHeight, ",
            "boxWeight, guaranteePeriod, packageNumber, createTime, lastUpdateTime",
            "from t_product_basic",
            "where easModel = #{easModel}"
    })
    ProductBasic getByEasModel(@Param("easModel") String easModel);
}