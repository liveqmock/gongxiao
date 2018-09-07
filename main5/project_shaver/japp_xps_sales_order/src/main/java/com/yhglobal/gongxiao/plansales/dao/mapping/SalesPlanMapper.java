package com.yhglobal.gongxiao.plansales.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.plansales.dao.provider.SalesPlanSqlProvider;
import com.yhglobal.gongxiao.sales.model.plan.model.SalesPlan;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;


public interface SalesPlanMapper extends BaseMapper {
    @Delete({
            "delete from ${tablePrefix}_sales_plan",
            "where salesPlanId = #{salesPlanId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(@Param("tablePrefix")String tablePrefix,
                           @Param("salesPlanId") Long salesPlanId);

    @Insert({
            "insert into ${tablePrefix}_sales_plan (salesPlanStatus, ",
            "salesPlanNo, projectId,grossProfitValue, ",
            "projectName, brandId, ",
            "brandName, supplierId, ",
            "supplierName, productCategory, ",
            "productId, productCode, ",
            "productName, guidePrice,saleGuidePrice, ",
            "onSaleQuantity, allocatedQuantity, ",
            "unallocatedQuantity, inStockQuantity, ",
            "startTime, endTime, ",
            "dataVersion, createdById, ",
            "createdByName, createTime, ",
            "lastUpdateTime, tracelog)",
            "values ( #{salesPlanStatus,jdbcType=TINYINT}, ",
            "#{salesPlanNo,jdbcType=VARCHAR}, #{projectId,jdbcType=BIGINT}, #{grossProfitValue},",
            "#{projectName,jdbcType=VARCHAR}, #{brandId,jdbcType=INTEGER}, ",
            "#{brandName,jdbcType=VARCHAR}, #{supplierId,jdbcType=BIGINT}, ",
            "#{supplierName,jdbcType=VARCHAR}, #{productCategory,jdbcType=TINYINT}, ",
            "#{productId,jdbcType=BIGINT}, #{productCode,jdbcType=VARCHAR}, ",
            "#{productName,jdbcType=VARCHAR}, #{guidePrice},#{saleGuidePrice}, ",
            "#{onSaleQuantity,jdbcType=INTEGER}, #{allocatedQuantity,jdbcType=INTEGER}, ",
            "#{unallocatedQuantity,jdbcType=INTEGER}, #{inStockQuantity,jdbcType=INTEGER}, ",
            "#{startTime,jdbcType=TIMESTAMP}, #{endTime,jdbcType=TIMESTAMP}, ",
            "#{dataVersion,jdbcType=BIGINT}, #{createdById,jdbcType=BIGINT}, ",
            "#{createdByName,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
            "now(), #{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(SalesPlan record);

    @Select({
            "select",
            "salesPlanId,grossProfitValue, salesPlanStatus, salesPlanNo, projectId, projectName, brandId, ",
            "brandName, supplierId, supplierName, productCategory, productId, productCode, ",
            "productName, guidePrice,saleGuidePrice, onSaleQuantity, allocatedQuantity, unallocatedQuantity, ",
            "inStockQuantity, startTime, endTime, dataVersion, createdById, createdByName, ",
            "createTime, lastUpdateTime, tracelog",
            "from ${tablePrefix}_sales_plan",
            "where salesPlanId = #{salesPlanId,jdbcType=BIGINT}"
    })
    SalesPlan selectByPrimaryKey(@Param("tablePrefix")String tablePrefix,
                                 @Param("salesPlanId") Long salesPlanId);

    @Select({
            "select",
            "salesPlanId, grossProfitValue,salesPlanStatus, salesPlanNo, projectId, projectName, brandId, ",
            "brandName, supplierId, supplierName, productCategory, productId, productCode, ",
            "productName, guidePrice,saleGuidePrice, onSaleQuantity, allocatedQuantity, unallocatedQuantity, ",
            "inStockQuantity, startTime, endTime, dataVersion, createdById, createdByName, ",
            "createTime, lastUpdateTime, tracelog",
            "from ${tablePrefix}_sales_plan",
    })
    List<SalesPlan> getSalePlanListByProject(@Param("tablePrefix")String tablePrefix,
                                             @Param("projectId") String projectId);

    @Select({
            "select",
            "salesPlanId, grossProfitValue,salesPlanStatus, salesPlanNo, projectId, projectName, brandId, ",
            "brandName, supplierId, supplierName, productCategory, productId, productCode, ",
            "productName, guidePrice,saleGuidePrice, onSaleQuantity, allocatedQuantity, unallocatedQuantity, ",
            "inStockQuantity, startTime, endTime, dataVersion, createdById, createdByName, ",
            "createTime, lastUpdateTime, tracelog",
            "from ${tablePrefix}_sales_plan",
            "where salesPlanNo = #{salesPlanNo}"
    })
    SalesPlan getSalePlanNo(@Param("tablePrefix")String tablePrefix,
                            @Param("salesPlanNo") String salesPlanNo);

    @Select({
            "select",
            "count(salesPlanId)",
            "from ${tablePrefix}_sales_plan",
            "where productId = #{productId} and salesPlanStatus!=91" ,
            "and ((startTime>=#{startTime} && startTime<=#{endTime}) or (endTime>=#{endTime} && endTime<=#{endTime}))"
    })
    int countRepeatRecord(@Param("tablePrefix")String tablePrefix,
                          @Param("productId") String productId,
                          @Param("startTime") Date startTime,
                          @Param("endTime") Date endTime);

    @Update({
            "update ${tablePrefix}_sales_plan",
            "set salesPlanStatus = #{salesPlanStatus,jdbcType=TINYINT},",
            "salesPlanNo = #{salesPlanNo,jdbcType=VARCHAR},",
            "projectId = #{projectId,jdbcType=BIGINT},",
            "projectName = #{projectName,jdbcType=VARCHAR},",
            "brandId = #{brandId,jdbcType=INTEGER},",
            "brandName = #{brandName,jdbcType=VARCHAR},",
            "supplierId = #{supplierId,jdbcType=BIGINT},",
            "supplierName = #{supplierName,jdbcType=VARCHAR},",
            "productCategory = #{productCategory,jdbcType=TINYINT},",
            "productId = #{productId,jdbcType=BIGINT},",
            "productCode = #{productCode,jdbcType=VARCHAR},",
            "productName = #{productName,jdbcType=VARCHAR},",
            "guidePrice = #{guidePrice},",
            "onSaleQuantity = #{onSaleQuantity,jdbcType=INTEGER},",
            "allocatedQuantity = #{allocatedQuantity,jdbcType=INTEGER},",
            "unallocatedQuantity = #{unallocatedQuantity,jdbcType=INTEGER},",
            "inStockQuantity = #{inStockQuantity,jdbcType=INTEGER},",
            "startTime = #{startTime,jdbcType=TIMESTAMP},",
            "endTime = #{endTime,jdbcType=TIMESTAMP},",
            "dataVersion = #{dataVersion,jdbcType=BIGINT},",
            "createdById = #{createdById,jdbcType=BIGINT},",
            "createdByName = #{createdByName,jdbcType=VARCHAR},",
            "createTime = #{createTime,jdbcType=TIMESTAMP},",
            "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
            "where salesPlanId = #{salesPlanId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(@Param("tablePrefix")String tablePrefix,
                           SalesPlan record);

    @Update({
            "update ${tablePrefix}_sales_plan",
            "set ",
            "onSaleQuantity = #{onSaleQuantity,jdbcType=INTEGER},",
            "allocatedQuantity = #{allocatedQuantity,jdbcType=INTEGER},",
            "unallocatedQuantity = #{unallocatedQuantity,jdbcType=INTEGER}",
            "where salesPlanNo = #{salesPlanNo}"
    })
    int updateProductQuantity(@Param("tablePrefix")String tablePrefix,
                              @Param("salesPlanNo") String salesPlanNo,
                              @Param("onSaleQuantity") int onSaleQuantity,
                              @Param("allocatedQuantity") int allocatedQuantity,
                              @Param("unallocatedQuantity") int unallocatedQuantity);

    @Update({
            "update ${tablePrefix}_sales_plan",
            "set ",
            "onSaleQuantity = #{onSaleQuantity},",
            "unallocatedQuantity = #{unallocatedQuantity},",
            "startTime = #{startTime},",
            "endTime = #{endTime}",
            "where salesPlanNo = #{salesPlanNo}"
    })
    int editSalePlan(@Param("tablePrefix")String tablePrefix,
                     @Param("salesPlanNo") String salesPlanNo,
                     @Param("onSaleQuantity") int onSaleQuantity,
                     @Param("unallocatedQuantity") int unallocatedQuantity,
                     @Param("startTime") String startTime,
                     @Param("endTime") String endTime);

    @Update({
            "update ${tablePrefix}_sales_plan",
            "set ",
            "allocatedQuantity = #{allocatedQuantity,jdbcType=INTEGER},",
            "unallocatedQuantity = #{unallocatedQuantity,jdbcType=INTEGER}",
            "where salesPlanNo = #{salesPlanNo}"
    })
    int updateHandleQuantity(@Param("tablePrefix")String tablePrefix,
                             @Param("salesPlanNo") String salesPlanNo,
                             @Param("allocatedQuantity") int allocatedQuantity,
                             @Param("unallocatedQuantity") int unallocatedQuantity);

    @Update({
            "update ${tablePrefix}_sales_plan",
            "set ",
            "salesPlanStatus = #{salesPlanStatus}",
            "where salesPlanNo = #{salesPlanNo}"
    })
    int cancleSalePlanOrder(@Param("tablePrefix")String tablePrefix,
                            @Param("salesPlanNo") String salesPlanNo,
                            @Param("salesPlanStatus") int salesPlanStatus);

    @Update({
            "update ${tablePrefix}_sales_plan",
            "set ",
            "allocatedQuantity = allocatedQuantity - #{changeQuantity},",
            "unallocatedQuantity = unallocatedQuantity + #{changeQuantity}",
            "where salesPlanNo = #{salesPlanNo}"
    })
    int updateReturnSaleQuantity(@Param("tablePrefix")String tablePrefix,
                                 @Param("salesPlanNo") String salesPlanNo,
                                 @Param("changeQuantity") int changeQuantity);

    @SelectProvider(type = SalesPlanSqlProvider.class, method = "getList")
    List<SalesPlan> getPlanSaleList(@Param("tablePrefix")String tablePrefix,
                                    @Param("productCode") String productCode,
                                    @Param("productName") String productName,
                                    @Param("createTime") String createTime);
}