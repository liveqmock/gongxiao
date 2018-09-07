package com.yhglobal.gongxiao.foundation.product.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.product.dao.provider.ProductProvider;
import com.yhglobal.gongxiao.foundation.product.model.FoundationProductBusiness;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface FoundationProductBusinessMapper extends BaseMapper {
    @Delete({
            "delete from  ${projectTablePrefix}_product_business",
            "where productBusinessId = #{productBusinessId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(@Param("projectTablePrefix") String projectTablePrefix,
                           @Param("productBusinessId") Long productBusinessId);

    @Insert({
            "insert into ${projectTablePrefix}_product_business (productBusinessId, productBasicId, ",
            "projectId, projectName, ",
            "recordStatus, easSynStatus,grossProfitValue, ",
            "productName, productCode, ",
            "easCode,easUnitCode, wmsCode, ",
            "customerSKUCode, customerProductCode, ",
            "purchaseGuidePrice, taxRate, ",
            "taxCode, saleGuidePrice, ",
            "actualSaleReturn, shouldSaleReturn, ",
            "factorySendReturn, costPrice, ",
            "outPrice, generateCoupon, ",
            "createTime, lastUpdateTime, ",
            "traceLog)",
            "values (#{productBusinessId,jdbcType=BIGINT}, #{productBasicId,jdbcType=BIGINT}, ",
            "#{projectId,jdbcType=VARCHAR}, #{projectName,jdbcType=VARCHAR}, ",
            "#{recordStatus,jdbcType=TINYINT}, #{easSynStatus,jdbcType=TINYINT}, #{grossProfitValue}",
            "#{productName,jdbcType=VARCHAR}, #{productCode,jdbcType=VARCHAR}, ",
            "#{easCode,jdbcType=VARCHAR},#{easUnitCode}, #{wmsCode,jdbcType=VARCHAR}, ",
            "#{customerSKUCode,jdbcType=VARCHAR}, #{customerProductCode,jdbcType=VARCHAR}, ",
            "#{purchaseGuidePrice,jdbcType=BIGINT}, #{taxRate,jdbcType=BIGINT}, ",
            "#{taxCode,jdbcType=VARCHAR}, #{saleGuidePrice,jdbcType=BIGINT}, ",
            "#{actualSaleReturn,jdbcType=BIGINT}, #{shouldSaleReturn,jdbcType=BIGINT}, ",
            "#{factorySendReturn,jdbcType=BIGINT}, #{costPrice,jdbcType=BIGINT}, ",
            "#{outPrice,jdbcType=BIGINT}, #{generateCoupon,jdbcType=TINYINT}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP}, ",
            "#{traceLog,jdbcType=LONGVARCHAR})"
    })
    int insert(@Param("projectTablePrefix") String projectTablePrefix,
               FoundationProductBusiness record);

    @Select({
            "select",
            "productBusinessId, productBasicId, grossProfitValue,projectId, projectName, recordStatus,easUnitCode, easSynStatus, ",
            "productName, productCode, easCode, wmsCode, customerSKUCode, customerProductCode, ",
            "purchaseGuidePrice, taxRate, taxCode, saleGuidePrice, actualSaleReturn, shouldSaleReturn, ",
            "factorySendReturn, costPrice, outPrice, generateCoupon, createTime, lastUpdateTime, ",
            "traceLog",
            "from ${projectTablePrefix}_product_business",
            "where productBusinessId = #{productBusinessId,jdbcType=BIGINT}"
    })
    FoundationProductBusiness getProductBusinessById(@Param("projectTablePrefix") String projectTablePrefix,
                                                     @Param("productBusinessId") Long productBusinessId);

    @Select({
            "select",
            "productBusinessId, productBasicId,grossProfitValue, projectId, projectName, recordStatus, easSynStatus, ",
            "productName, productCode, easCode, easUnitCode,wmsCode, customerSKUCode, customerProductCode, ",
            "purchaseGuidePrice, taxRate, taxCode, saleGuidePrice, actualSaleReturn, shouldSaleReturn, ",
            "factorySendReturn, costPrice, outPrice, generateCoupon, createTime, lastUpdateTime, ",
            "traceLog",
            "from ${projectTablePrefix}_product_business",
            "where productCode = #{productCode}"
    })
    FoundationProductBusiness getProductBusinessByModel(@Param("projectTablePrefix") String projectTablePrefix,
                                                        @Param("productCode") String productCode);

    @Select({
            "select",
            "productBusinessId, productBasicId,grossProfitValue, projectId, projectName, recordStatus, easSynStatus, ",
            "productName, productCode, easCode, easUnitCode,wmsCode, customerSKUCode, customerProductCode, ",
            "purchaseGuidePrice, taxRate, taxCode, saleGuidePrice, actualSaleReturn, shouldSaleReturn, ",
            "factorySendReturn, costPrice, outPrice, generateCoupon, createTime, lastUpdateTime, ",
            "traceLog",
            "from ${projectTablePrefix}_product_business",
            "where wmsCode = #{wmsCode}"
    })
    FoundationProductBusiness getProductBusinessByWmsCode(@Param("projectTablePrefix") String projectTablePrefix,
                                                          @Param("wmsCode") String wmsCode);

    @Select({
            "select",
            "productBusinessId, productBasicId,grossProfitValue, projectId, projectName, recordStatus, easSynStatus, ",
            "productName, productCode, easCode, easUnitCode,wmsCode, customerSKUCode, customerProductCode, ",
            "purchaseGuidePrice, taxRate, taxCode, saleGuidePrice, actualSaleReturn, shouldSaleReturn, ",
            "factorySendReturn, costPrice, outPrice, generateCoupon, createTime, lastUpdateTime, ",
            "traceLog",
            "from ${projectTablePrefix}_product_business",
            "where easCode = #{easCode}  and recordStatus = 3"
    })
    FoundationProductBusiness getProductBusinessByEasCode(@Param("projectTablePrefix") String projectTablePrefix,
                                                          @Param("easCode") String easCode);

    @Select({
            "select",
            "productBusinessId, productBasicId,grossProfitValue, projectId, projectName, recordStatus, easSynStatus, ",
            "productName, productCode, easCode,easUnitCode, wmsCode, customerSKUCode, customerProductCode, ",
            "purchaseGuidePrice, taxRate, taxCode, saleGuidePrice, actualSaleReturn, shouldSaleReturn, ",
            "factorySendReturn, costPrice, outPrice, generateCoupon, createTime, lastUpdateTime, ",
            "traceLog",
            "from ${projectTablePrefix}_product_business",
            "where productBusinessId = #{productBusinessId,jdbcType=BIGINT}"
    })
    List<FoundationProductBusiness> selectBusinessListByBasicId(@Param("projectTablePrefix") String projectTablePrefix,
                                                                @Param("productBusinessId") Long productBusinessId);

    @Select({
            "select",
            "productBusinessId, productBasicId, grossProfitValue,projectId, projectName, recordStatus, easSynStatus, ",
            "productName, productCode, easCode,easUnitCode, wmsCode, customerSKUCode, customerProductCode, ",
            "purchaseGuidePrice, taxRate, taxCode, saleGuidePrice, actualSaleReturn, shouldSaleReturn, ",
            "factorySendReturn, costPrice, outPrice, generateCoupon, createTime, lastUpdateTime, ",
            "traceLog",
            "from ${projectTablePrefix}_product_business",
    })
    List<FoundationProductBusiness> selectProductBusienssByProject(@Param("projectTablePrefix") String projectTablePrefix);

    @SelectProvider(type = ProductProvider.class, method = "selectBusinessByCondition")
    List<FoundationProductBusiness> selectProductByCondition(@Param("projectTablePrefix") String projectTablePrefix,
                                                             @Param("productCode") String productCode,
                                                             @Param("productName") String productName,
                                                             @Param("productEasCode") String productEasCode,
                                                             @Param("recordStatus") int recordStatus);

    @Update({
            "update ${projectTablePrefix}_product_business",
            "set productBasicId = #{productBasicId,jdbcType=BIGINT},",
            "projectId = #{projectId,jdbcType=VARCHAR},",
            "projectName = #{projectName,jdbcType=VARCHAR},",
            "recordStatus = #{recordStatus,jdbcType=TINYINT},",
            "easSynStatus = #{easSynStatus,jdbcType=TINYINT},",
            "productName = #{productName,jdbcType=VARCHAR},",
            "productCode = #{productCode,jdbcType=VARCHAR},",
            "easCode = #{easCode,jdbcType=VARCHAR},",
            "wmsCode = #{wmsCode,jdbcType=VARCHAR},",
            "customerSKUCode = #{customerSKUCode,jdbcType=VARCHAR},",
            "customerProductCode = #{customerProductCode,jdbcType=VARCHAR},",
            "purchaseGuidePrice = #{purchaseGuidePrice,jdbcType=BIGINT},",
            "taxRate = #{taxRate,jdbcType=BIGINT},",
            "taxCode = #{taxCode,jdbcType=VARCHAR},",
            "saleGuidePrice = #{saleGuidePrice,jdbcType=BIGINT},",
            "actualSaleReturn = #{actualSaleReturn,jdbcType=BIGINT},",
            "shouldSaleReturn = #{shouldSaleReturn,jdbcType=BIGINT},",
            "factorySendReturn = #{factorySendReturn,jdbcType=BIGINT},",
            "costPrice = #{costPrice,jdbcType=BIGINT},",
            "outPrice = #{outPrice,jdbcType=BIGINT},",
            "generateCoupon = #{generateCoupon,jdbcType=TINYINT},",
            "createTime = #{createTime,jdbcType=TIMESTAMP},",
            "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
            "traceLog = #{traceLog}",
            "where productBusinessId = #{productBusinessId,jdbcType=BIGINT}"
    })
    int updateBusinessById(@Param("projectTablePrefix") String projectTablePrefix,
                           FoundationProductBusiness record);

    @Update({
            "update ${projectTablePrefix}_product_business",
            "set recordStatus = #{recordStatus},",
            "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
            "where productBusinessId = #{productBusinessId,jdbcType=BIGINT} and recordStatus != 9"
    })
    int updateRecordStatus(@Param("projectTablePrefix") String projectTablePrefix,
                           @Param("productBusinessId") long productBusinessId,
                           @Param("recordStatus") byte recordStatus);

}