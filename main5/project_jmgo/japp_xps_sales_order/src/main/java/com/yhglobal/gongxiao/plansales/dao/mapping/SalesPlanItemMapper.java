package com.yhglobal.gongxiao.plansales.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.plansales.dao.provider.SalesPlanSqlProvider;
import com.yhglobal.gongxiao.sales.dao.Provider.SalesPlanItemProvider;
import com.yhglobal.gongxiao.sales.dao.mapping.SalesOrderItemSqlProvider;
import com.yhglobal.gongxiao.sales.model.plan.model.SalesPlanItem;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;


public interface SalesPlanItemMapper extends BaseMapper {
    @Delete({
            "delete from ${tablePrefix}_sales_plan_item",
            "where itemId = #{itemId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(@Param("tablePrefix") String tablePrefix,
                           @Param("itemId") Long itemId);

    @Insert({
            "insert into ${tablePrefix}_sales_plan_item (itemId, itemStatus,brandId,brandName, ",
            "salesPlanNo, customerId,grossProfitValue, ",
            "customerName, projectId, ",
            "projectName, supplierId, ",
            "supplierName, productId, ",
            "productCode, productName, ",
            "distributorId, distributorName, ",
            "onSaleQuantity, saleOccupationQuantity, ",
            "soldQuantity, freezedQuantity, ",
            "unallocatedQuantity, guidePrice, saleGuidePrice,",
            "currencyCode, currencyName, ",
            "brandPrepaidUnit, brandPrepaidDiscount, ",
            "brandPrepaidAmount, yhPrepaidUnit, ",
            "yhPrepaidDiscount, yhPrepaidAmount, ",
            "wholesalePrice, startTime, ",
            "endTime, dataVersion, ",
            "createdById, createdByName, ",
            "createTime, lastUpdateTime, ",
            "tracelog)",
            "values (#{itemId,jdbcType=BIGINT}, #{itemStatus,jdbcType=TINYINT}, ",
            "#{brandId,jdbcType=VARCHAR}, #{brandName,jdbcType=VARCHAR}, ",
            "#{salesPlanNo,jdbcType=VARCHAR}, #{customerId,jdbcType=VARCHAR}, #{grossProfitValue},",
            "#{customerName,jdbcType=VARCHAR}, #{projectId,jdbcType=BIGINT}, ",
            "#{projectName,jdbcType=VARCHAR}, #{supplierId,jdbcType=BIGINT}, ",
            "#{supplierName,jdbcType=VARCHAR}, #{productId,jdbcType=BIGINT}, ",
            "#{productCode,jdbcType=VARCHAR}, #{productName,jdbcType=VARCHAR}, ",
            "#{distributorId,jdbcType=BIGINT}, #{distributorName,jdbcType=VARCHAR}, ",
            "#{onSaleQuantity,jdbcType=INTEGER}, #{saleOccupationQuantity,jdbcType=INTEGER}, ",
            "#{soldQuantity,jdbcType=INTEGER}, #{freezedQuantity,jdbcType=INTEGER}, ",
            "#{unallocatedQuantity,jdbcType=INTEGER}, #{guidePrice,jdbcType=INTEGER},#{saleGuidePrice} ,",
            "#{currencyCode,jdbcType=VARCHAR}, #{currencyName,jdbcType=VARCHAR}, ",
            "#{brandPrepaidUnit,jdbcType=INTEGER}, #{brandPrepaidDiscount,jdbcType=INTEGER}, ",
            "#{brandPrepaidAmount,jdbcType=BIGINT}, #{yhPrepaidUnit,jdbcType=INTEGER}, ",
            "#{yhPrepaidDiscount,jdbcType=INTEGER}, #{yhPrepaidAmount,jdbcType=BIGINT}, ",
            "#{wholesalePrice,jdbcType=INTEGER}, #{startTime,jdbcType=TIMESTAMP}, ",
            "#{endTime,jdbcType=TIMESTAMP}, #{dataVersion,jdbcType=BIGINT}, ",
            "#{createdById,jdbcType=BIGINT}, #{createdByName,jdbcType=VARCHAR}, ",
            "#{createTime,jdbcType=TIMESTAMP}, now(), ",
            "#{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(SalesPlanItem record);

    @Select({
            "select",
            "itemId, itemStatus,grossProfitValue, brandId,brandName,salesPlanNo, customerId, customerName, projectId, projectName, ",
            "supplierId, supplierName, productId, productCode, productName, distributorId, ",
            "distributorName, onSaleQuantity, saleOccupationQuantity, soldQuantity, freezedQuantity, ",
            "unallocatedQuantity, guidePrice,saleGuidePrice, currencyCode, currencyName, brandPrepaidUnit, ",
            "brandPrepaidDiscount, brandPrepaidAmount, yhPrepaidUnit, yhPrepaidDiscount, ",
            "yhPrepaidAmount, wholesalePrice, startTime, endTime, dataVersion, createdById, ",
            "createdByName, createTime, lastUpdateTime, tracelog",
            "from ${tablePrefix}_sales_plan_item",
            "where salesPlanNo = #{salesPlanNo}"
    })
    List<SalesPlanItem> selectBySalePlanNo(@Param("tablePrefix") String tablePrefix,
                                           @Param("salesPlanNo") String salesPlanNo);

    @Select({
            "select",
            "itemId, itemStatus,grossProfitValue, brandId,brandName,salesPlanNo, customerId, customerName, projectId, projectName, ",
            "supplierId, supplierName, productId, productCode, productName, distributorId, ",
            "distributorName, onSaleQuantity, saleOccupationQuantity, soldQuantity, freezedQuantity, ",
            "unallocatedQuantity, guidePrice,saleGuidePrice, currencyCode, currencyName, brandPrepaidUnit, ",
            "brandPrepaidDiscount, brandPrepaidAmount, yhPrepaidUnit, yhPrepaidDiscount, ",
            "yhPrepaidAmount, wholesalePrice, startTime, endTime, dataVersion, createdById, ",
            "createdByName, createTime, lastUpdateTime, tracelog",
            "from ${tablePrefix}_sales_plan_item",
            "where itemId = #{itemId}"
    })
    SalesPlanItem selectBySaleItemId(@Param("tablePrefix") String tablePrefix,
                                     @Param("itemId") String itemId);

    @Select({
            "select",
            "itemId, itemStatus,grossProfitValue, brandId,brandName,salesPlanNo, customerId, customerName, projectId, projectName, ",
            "supplierId, supplierName, productId, productCode, productName, distributorId, ",
            "distributorName, onSaleQuantity, saleOccupationQuantity, soldQuantity, freezedQuantity, ",
            "unallocatedQuantity, guidePrice, saleGuidePrice,currencyCode, currencyName, brandPrepaidUnit, ",
            "brandPrepaidDiscount, brandPrepaidAmount, yhPrepaidUnit, yhPrepaidDiscount, ",
            "yhPrepaidAmount, wholesalePrice, startTime, endTime, dataVersion, createdById, ",
            "createdByName, createTime, lastUpdateTime, tracelog",
            "from ${tablePrefix}_sales_plan_item",
            "where distributorId = #{distributorId} and productId = #{productId} and #{businessDate}>=startTime and #{businessDate}<=endTime"
    })
    SalesPlanItem getCustomerProductList(@Param("tablePrefix") String tablePrefix,
                                         @Param("distributorId") String distributorId,
                                         @Param("productId") String productCode,
                                         @Param("businessDate") Date businessDate);

    @Select({
            "select",
            "itemId, itemStatus,grossProfitValue,brandId,brandName, salesPlanNo, customerId, customerName, projectId, projectName, ",
            "supplierId, supplierName, productId, productCode, productName, distributorId, ",
            "distributorName, onSaleQuantity, saleOccupationQuantity, soldQuantity, freezedQuantity, ",
            "unallocatedQuantity, guidePrice,saleGuidePrice, currencyCode, currencyName, brandPrepaidUnit, ",
            "brandPrepaidDiscount, brandPrepaidAmount, yhPrepaidUnit, yhPrepaidDiscount, ",
            "yhPrepaidAmount, wholesalePrice, startTime, endTime, dataVersion, createdById, ",
            "createdByName, createTime, lastUpdateTime, tracelog",
            "from ${tablePrefix}_sales_plan_item",
            "where distributorId = #{distributorId} and (productId = #{productId} or productCode=#{productId} ) and #{businessDate}>=startTime and #{businessDate}<=endTime",
            "AND itemStatus!= 91"

    })
    SalesPlanItem getCustomerProduct(@Param("tablePrefix") String tablePrefix,
                                     @Param("distributorId") String distributorId,
                                     @Param("productId") String productId,
                                     @Param("businessDate") Date businessDate);

    @Select({
            "select",
            "itemId, itemStatus,grossProfitValue,brandId,brandName, salesPlanNo, customerId, customerName, projectId, projectName, ",
            "supplierId, supplierName, productId, productCode, productName, distributorId, ",
            "distributorName, onSaleQuantity, saleOccupationQuantity, soldQuantity, freezedQuantity, ",
            "unallocatedQuantity, guidePrice, saleGuidePrice,currencyCode, currencyName, brandPrepaidUnit, ",
            "brandPrepaidDiscount, brandPrepaidAmount, yhPrepaidUnit, yhPrepaidDiscount, ",
            "yhPrepaidAmount, wholesalePrice, startTime, endTime, dataVersion, createdById, ",
            "createdByName, createTime, lastUpdateTime, tracelog",
            "from ${tablePrefix}_sales_plan_item",
            "where distributorId = #{distributorId} and productId = #{productId}   and itemStatus!=91",
            "and ((startTime>=#{startDate} && startTime<=#{endDate}) or (endTime>=#{startDate} && endTime<=#{endDate}) )"
    })
    List<SalesPlanItem> selectProductCustomer(@Param("tablePrefix") String tablePrefix,
                                              @Param("distributorId") String distributorId,
                                              @Param("productId") String productId,
                                              @Param("startDate") Date startDate,
                                              @Param("endDate") Date endDate);

    @Select({
            "select",
            "itemId, itemStatus,grossProfitValue, brandId,brandName,salesPlanNo, customerId, customerName, projectId, projectName, ",
            "supplierId, supplierName, productId, productCode, productName, distributorId, ",
            "distributorName, onSaleQuantity, saleOccupationQuantity, soldQuantity, freezedQuantity, ",
            "unallocatedQuantity, guidePrice,saleGuidePrice, currencyCode, currencyName, brandPrepaidUnit, ",
            "brandPrepaidDiscount, brandPrepaidAmount, yhPrepaidUnit, yhPrepaidDiscount, ",
            "yhPrepaidAmount, wholesalePrice, startTime, endTime, dataVersion, createdById, ",
            "createdByName, createTime, lastUpdateTime, tracelog",
            "from ${tablePrefix}_sales_plan_item",
            "where distributorId = #{distributorId} and #{businessDate}>=startTime and #{businessDate}<=endTime"
    })
    List<SalesPlanItem> getCustomerProductLists(@Param("tablePrefix") String tablePrefix,
                                                @Param("distributorId") String distributorId,
                                                @Param("businessDate") Date businessDate);

    @Select({
            "select",
            "itemId, itemStatus,grossProfitValue, brandId,brandName,salesPlanNo, customerId, customerName, projectId, projectName, ",
            "supplierId, supplierName, productId, productCode, productName, distributorId, ",
            "distributorName, onSaleQuantity, saleOccupationQuantity, soldQuantity, freezedQuantity, ",
            "unallocatedQuantity, guidePrice,saleGuidePrice, currencyCode, currencyName, brandPrepaidUnit, ",
            "brandPrepaidDiscount, brandPrepaidAmount, yhPrepaidUnit, yhPrepaidDiscount, ",
            "yhPrepaidAmount, wholesalePrice, startTime, endTime, dataVersion, createdById, ",
            "createdByName, createTime, lastUpdateTime, tracelog",
            "from ${tablePrefix}_sales_plan_item",
            "where distributorId = #{distributorId} and itemStatus!=91 "
    })
    List<SalesPlanItem> selectCustomerSalePlanItemList(@Param("tablePrefix") String tablePrefix,
                                                       @Param("distributorId") String distributorId);

    @Select({
            "select",
            "itemId, itemStatus,grossProfitValue, brandId,brandName,salesPlanNo, customerId, customerName, projectId, projectName, ",
            "supplierId, supplierName, productId, productCode, productName, distributorId, ",
            "distributorName, onSaleQuantity, saleOccupationQuantity, soldQuantity, freezedQuantity, ",
            "unallocatedQuantity, guidePrice,saleGuidePrice, currencyCode, currencyName, brandPrepaidUnit, ",
            "brandPrepaidDiscount, brandPrepaidAmount, yhPrepaidUnit, yhPrepaidDiscount, ",
            "yhPrepaidAmount, wholesalePrice, startTime, endTime, dataVersion, createdById, ",
            "createdByName, createTime, lastUpdateTime, tracelog",
            "from ${tablePrefix}_sales_plan_item",
            "where  itemStatus!=92 and itemStatus!=91 and NOW()>=endTime"
    })
    List<SalesPlanItem> selectFailurePlanItemList(@Param("tablePrefix") String tablePrefix);

    @Select({
            "select",
            "itemId, itemStatus,grossProfitValue, brandId,brandName,salesPlanNo, customerId, customerName, projectId, projectName, ",
            "supplierId, supplierName, productId, productCode, productName, distributorId, ",
            "distributorName, onSaleQuantity, saleOccupationQuantity, soldQuantity, freezedQuantity, ",
            "unallocatedQuantity, guidePrice, saleGuidePrice,currencyCode, currencyName, brandPrepaidUnit, ",
            "brandPrepaidDiscount, brandPrepaidAmount, yhPrepaidUnit, yhPrepaidDiscount, ",
            "yhPrepaidAmount, wholesalePrice, startTime, endTime, dataVersion, createdById, ",
            "createdByName, createTime, lastUpdateTime, tracelog",
            "from ${tablePrefix}_sales_plan_item",
            "where itemId = #{itemId}  "
    })
    SalesPlanItem getByItemId(@Param("tablePrefix") String tablePrefix,
                              @Param("itemId") String itemId);


    @SelectProvider(type = SalesPlanItemProvider.class, method = "selectCustomerSalePlanList")
    List<SalesPlanItem> selectCustomerSalePlanList(@Param("tablePrefix") String tablePrefix,
                                                   @Param("distributorId") String distributorId,
                                                   @Param("salePlanNo") String salePlanNo,
                                                   @Param("productCode") String productCode,
                                                   @Param("productName") String productName,
                                                   @Param("orderStatus") int orderStatus,
                                                   @Param("startDate") Date startDate,
                                                   @Param("endDate") Date endDate);

    @SelectProvider(type = SalesPlanItemProvider.class, method = "searchProductByCustomer")
    List<SalesPlanItem> searchProductByCustomer(@Param("tablePrefix") String tablePrefix,
                                                @Param("distributorId") String distributorId,
                                                @Param("queryStr") String queryStr,
                                                @Param("date") Date date);


    @Update({
            "update ${tablePrefix}_sales_plan_item",
            "set onSaleQuantity = #{onSaleQuantity,jdbcType=INTEGER},",
            "unallocatedQuantity = #{unallocatedQuantity},",
            "brandPrepaidUnit = #{brandPrepaidUnit,jdbcType=INTEGER},",
            "brandPrepaidDiscount = #{brandPrepaidDiscount,jdbcType=INTEGER},",
            "yhPrepaidUnit = #{yhPrepaidUnit,jdbcType=INTEGER},",
            "yhPrepaidDiscount = #{yhPrepaidDiscount,jdbcType=INTEGER},",
            "wholesalePrice=saleGuidePrice-#{brandPrepaidUnit}-#{yhPrepaidUnit},",
            "startTime = #{startTime,jdbcType=TIMESTAMP},",
            "endTime = #{endTime,jdbcType=TIMESTAMP}",
            "where itemId = #{itemId,jdbcType=BIGINT}"
    })
    int update(@Param("tablePrefix") String tablePrefix,
               @Param("itemId") String itemId,
               @Param("onSaleQuantity") int onSaleQuantity,
               @Param("unallocatedQuantity") int unallocatedQuantity,
               @Param("brandPrepaidUnit") int brandPrepaidUnit,
               @Param("brandPrepaidDiscount") int brandPrepaidDiscount,
               @Param("yhPrepaidUnit") int yhPrepaidUnit,
               @Param("yhPrepaidDiscount") int yhPrepaidDiscount,
               @Param("startTime") Date startTime,
               @Param("endTime") Date endTime
    );

    @Update({
            "update ${tablePrefix}_sales_plan_item",
            "set soldQuantity = #{soldQuantity},",
            "unallocatedQuantity = #{unallocatedQuantity}",
            "where itemId = #{itemId}"
    })
    int cancelSaleOrder(@Param("tablePrefix") String tablePrefix,
                        @Param("itemId") long itemId,
                        @Param("unallocatedQuantity") int unallocatedQuantity ,
                        @Param("soldQuantity") int soldQuantity);


    @Update({
            "update ${tablePrefix}_sales_plan_item",
            "set itemStatus = #{itemStatus}",
            "where itemId = #{itemId}"
    })
    int updateStatus(@Param("tablePrefix") String tablePrefix,
                     @Param("itemId") String itemId,
                     @Param("itemStatus") int itemStatus);

    @Update({
            "update ${tablePrefix}_sales_plan_item",
            "set itemStatus = #{itemStatus}",
            "where salesPlanNo = #{salesPlanNo}"
    })
    int cancelSalePlanItemBySalePlanNo(@Param("tablePrefix") String tablePrefix,
                                       @Param("salesPlanNo") String salesPlanNo,
                                       @Param("itemStatus") int itemStatus);

    @Update({
            "update ${tablePrefix}_sales_plan_item",
            "set itemStatus = 92",
            "where itemId = #{itemId}"
    })
    int updateSchedulerCancelOrder(@Param("tablePrefix") String tablePrefix,
                                   @Param("itemId") long itemId);

    @Update({
            "update ${tablePrefix}_sales_plan_item",
            "set saleOccupationQuantity = saleOccupationQuantity-#{saleOccupationQuantity},",
            "soldQuantity = soldQuantity-#{soldQuantity},",
            "unallocatedQuantity = unallocatedQuantity+#{soldQuantity}+#{saleOccupationQuantity}",
            "where customerId=#{distributorId} and productCode=#{productCode}  ",
            "and #{businessDate}<=endTime and #{businessDate}>=startTime"
    })
    int cancelCustomerOrder(@Param("tablePrefix") String tablePrefix,
                            @Param("distributorId") String distributorId,
                            @Param("productCode") String productCode,
                            @Param("businessDate") Date businessDate,
                            @Param("saleOccupationQuantity") int saleOccupationQuantity,
                            @Param("soldQuantity") int soldQuantity);

    @Update({
            "update ${tablePrefix}_sales_plan_item",
            "set saleOccupationQuantity = #{saleOccupationQuantity},",
            "unallocatedQuantity = #{unallocatedQuantity}",
            "where itemId = #{itemId}"
    })
    int updateSaleOccupationQuantity(@Param("tablePrefix") String tablePrefix,
                                     @Param("itemId") long itemId,
                                     @Param("unallocatedQuantity") int unallocatedQuantity,
                                     @Param("saleOccupationQuantity") int saleOccupationQuantity);

    @Update({
            "update ${tablePrefix}_sales_plan_item",
            "set soldQuantity = #{soldQuantity},",
            "saleOccupationQuantity = #{saleOccupationQuantity}",
            "where itemId = #{itemId}"
    })
    int updateSoldQuantity(@Param("tablePrefix") String tablePrefix,
                           @Param("itemId") long itemId,
                           @Param("saleOccupationQuantity") int saleOccupationQuantity ,
                           @Param("soldQuantity") int soldQuantity);

    @SelectProvider(type = SalesOrderItemSqlProvider.class, method = "selectItemListByNo")
    List<SalesPlanItem> selectItemListByNo(@Param("tablePrefix") String tablePrefix,
                                           @Param("salesPlanNo") String salesPlanNo,
                                           @Param("distributorId") String distributorId,
                                           @Param("distributorName") String distributorName);

    @SelectProvider(type = SalesPlanSqlProvider.class, method = "getItemListByCondition")
    List<SalesPlanItem> getItemListByCondition(@Param("tablePrefix") String tablePrefix,
                                               @Param("productCode") String productCode,
                                               @Param("distributorId") String distributorId,
                                               @Param("businessDate") Date businessDate);

    @SelectProvider(type = SalesPlanSqlProvider.class, method = "getCustomerItemListByCondition")
    List<String> selectCustomerPlanItemList(@Param("tablePrefix") String tablePrefix,
                                            @Param("distributorId") String distributorId,
                                            @Param("distributorName") String distributorName);

    /**
     * 通过货品编码、项目id、客户id、有效期查询
     *
     * @param tablePrefix    项目id
     * @param productCode  货品编码
     * @param businessDate 日期
     * @return
     */
    @Select({
            "select",
            "itemId, itemStatus, grossProfitValue,salesPlanNo, customerId, customerName, projectId, projectName, ",
            "supplierId, supplierName, productId, productCode, productName, distributorId, ",
            "distributorName, onSaleQuantity, saleOccupationQuantity, soldQuantity, freezedQuantity, ",
            "unallocatedQuantity, guidePrice, saleGuidePrice,currencyCode, currencyName, brandPrepaidUnit, ",
            "brandPrepaidDiscount, brandPrepaidAmount, yhPrepaidUnit, yhPrepaidDiscount, ",
            "yhPrepaidAmount, wholesalePrice, startTime, endTime, dataVersion, createdById, ",
            "createdByName, createTime, lastUpdateTime, tracelog",
            "from ${tablePrefix}_sales_plan_item",
            "where distributorId = #{distributorId} and productCode = #{productCode} and #{businessDate}>=startTime and #{businessDate}<=endTime",
            "and itemStatus<>91"
    })
    SalesPlanItem getItemByModel(@Param("tablePrefix") String tablePrefix,
                                 @Param("distributorId") String distributorId,
                                 @Param("productCode") String productCode,
                                 @Param("businessDate") Date businessDate);


}