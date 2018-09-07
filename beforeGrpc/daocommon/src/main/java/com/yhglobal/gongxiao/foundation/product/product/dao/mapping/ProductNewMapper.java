package com.yhglobal.gongxiao.foundation.product.product.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.product.product.model.Product;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface ProductNewMapper extends BaseMapper {
    @Delete({
            "delete from product",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into product ( productBasicId, ",
            "projectId, projectName,recordStatus, ",
            "productName, productModel, ",
            "easCode, wmsCode, ",
            "customerSKUCode, customerProductCode, ",
            "purchaseGuidePrice, taxRate, ",
            "taxCode, saleGuidePrice, ",
            "actualSaleReturn, shouldSaleReturn, ",
            "factorySendReturn, costPrice, ",
            "outPrice, generateCoupon,traceLog ",
            "createTime, lastUpdateTime)",
            "values ( #{productBasicId,jdbcType=BIGINT}, ",
            "#{projectId},#{projectName}, #{recordStatus,jdbcType=TINYINT}, ",
            "#{productName,jdbcType=VARCHAR}, #{productModel,jdbcType=VARCHAR}, ",
            "#{easCode,jdbcType=VARCHAR}, #{wmsCode,jdbcType=VARCHAR}, ",
            "#{customerSKUCode,jdbcType=VARCHAR}, #{customerProductCode,jdbcType=VARCHAR}, ",
            "#{purchaseGuidePrice,jdbcType=BIGINT}, #{taxRate}, ",
            "#{taxCode,jdbcType=VARCHAR}, #{saleGuidePrice,jdbcType=BIGINT}, ",
            "#{actualSaleReturn,jdbcType=BIGINT}, #{shouldSaleReturn,jdbcType=BIGINT}, ",
            "#{factorySendReturn,jdbcType=BIGINT}, #{costPrice,jdbcType=BIGINT}, ",
            "#{outPrice,jdbcType=BIGINT}, #{generateCoupon,jdbcType=TINYINT}, ",
            "#{traceLog}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
//添加该行，product中的id将被自动添加
    int insert(Product record);

    @Select({
            "select",
            "id, productBasicId, projectId,projectName, recordStatus,easSynStatus, productName, productModel, easCode, ",
            "wmsCode, customerSKUCode, customerProductCode, purchaseGuidePrice, taxRate, ",
            "taxCode, saleGuidePrice, actualSaleReturn, shouldSaleReturn, factorySendReturn, ",
            "costPrice, outPrice, generateCoupon,traceLog, createTime, lastUpdateTime",
            "from product",
            "where id = #{id,jdbcType=BIGINT}"
    })
    Product getProductById(Long id);

    @Select({
            "select",
            "id, productBasicId, projectId,projectName, recordStatus,easSynStatus, productName, productModel, easCode, ",
            "wmsCode, customerSKUCode, customerProductCode, purchaseGuidePrice, taxRate, ",
            "taxCode, saleGuidePrice, actualSaleReturn, shouldSaleReturn, factorySendReturn, ",
            "costPrice, outPrice, generateCoupon,traceLog, createTime, lastUpdateTime",
            "from product",
            "where projectId = #{projectId} and recordStatus=#{recordStatus}"
    })
    List<Product> selectByAuditStatus(String projectId, int recordStatus);

    @Select({
            "select",
            "id, productBasicId, projectId,projectName, recordStatus,easSynStatus, productName, productModel, easCode, ",
            "wmsCode, customerSKUCode, customerProductCode, purchaseGuidePrice, taxRate, ",
            "taxCode, saleGuidePrice, actualSaleReturn, shouldSaleReturn, factorySendReturn, ",
            "costPrice, outPrice, generateCoupon,traceLog, createTime, lastUpdateTime",
            "from product",
            "where easCode = #{easCode} and easSynStatus=1 "
    })
    List<Product> selectProductByEasCode(String easCode);

    @Select({
            "select",
            "id, productBasicId, projectId,projectName, recordStatus,easSynStatus, productName, productModel, easCode, ",
            "wmsCode, customerSKUCode, customerProductCode, purchaseGuidePrice, taxRate, ",
            "taxCode, saleGuidePrice, actualSaleReturn, shouldSaleReturn, factorySendReturn, ",
            "costPrice, outPrice, generateCoupon,traceLog, createTime, lastUpdateTime",
            "from product",
            "where projectId != #{projectId} ",
            "group by productModel"
    })
    List<Product> selectNotCommitList(String projectId);

    @Select({
            "select",
            "id, productBasicId, projectId,projectName, recordStatus,easSynStatus, productName, productModel, easCode, ",
            "wmsCode, customerSKUCode, customerProductCode, purchaseGuidePrice, taxRate, ",
            "taxCode, saleGuidePrice, actualSaleReturn, shouldSaleReturn, factorySendReturn, ",
            "costPrice, outPrice, generateCoupon, traceLog,createTime, lastUpdateTime",
            "from product",
            "where recordStatus=1 ",
            "order by id desc"
    })
    List<Product> selectNeedAuditList();

    @Update({
            "update product",
            "set productBasicId = #{productBasicId,jdbcType=BIGINT},",
            "projectId = #{projectId},",
            "projectName = #{projectName},",
            "recordStatus = #{recordStatus,jdbcType=TINYINT},",
            "productName = #{productName,jdbcType=VARCHAR},",
            "productModel = #{productModel,jdbcType=VARCHAR},",
            "easCode = #{easCode,jdbcType=VARCHAR},",
            "wmsCode = #{wmsCode,jdbcType=VARCHAR},",
            "customerSKUCode = #{customerSKUCode,jdbcType=VARCHAR},",
            "customerProductCode = #{customerProductCode,jdbcType=VARCHAR},",
            "purchaseGuidePrice = #{purchaseGuidePrice,jdbcType=BIGINT},",
            "taxRate = #{taxRate},",
            "taxCode = #{taxCode,jdbcType=VARCHAR},",
            "saleGuidePrice = #{saleGuidePrice,jdbcType=BIGINT},",
            "actualSaleReturn = #{actualSaleReturn,jdbcType=BIGINT},",
            "shouldSaleReturn = #{shouldSaleReturn,jdbcType=BIGINT},",
            "factorySendReturn = #{factorySendReturn,jdbcType=BIGINT},",
            "costPrice = #{costPrice,jdbcType=BIGINT},",
            "outPrice = #{outPrice,jdbcType=BIGINT},",
            "generateCoupon = #{generateCoupon,jdbcType=TINYINT},",
            "createTime = #{createTime,jdbcType=TIMESTAMP},",
            "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateById(Product record);

    @Update({
            "update product",
            "set ",
            "recordStatus = #{recordStatus},",
            "traceLog = #{traceLog},",
            "lastUpdateTime = now()",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateRecordStatus(long id, int recordStatus,String traceLog);

    @Update({
            "update product",
            "set ",
            "easSynStatus = 2,",
            "lastUpdateTime = now()",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateEasStatus(long id);
}