package com.yhglobal.gongxiao.phjd.sales.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.phjd.sales.model.SalesOrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;


/**
 * 销售订单商品
 * @author weizecheng
 * @date 2018/8/21 9:10
 */
public interface SalesOrderItemMapper extends BaseMapper {

    /**
     * 根据销售订单号获取订单商品列表
     *
     * @author weizecheng
     * @date 2018/8/21 9:41
     * @param prefix 数据表前缀
     * @param salesOrderNo 销售订单号
     * @return list<SalesOrderItem>
     */
    @Select({
            "select",
            "salesOrderItemId, salesOrderNo, itemStatus, productId, productCode, productName, ",
            "warehouseId, warehouseName, currencyCode, retailPrice, wholesalePrice, totalOrderAmount, ",
            "totalQuantity, jdSkuCode, jdSalesGuidePrice, jdPurchaseGuidePrice, jdTotalQuantity, ",
            "jdPurchaseNo, jdBackorderProcessing, jdComments, generatedPrepaid, shouldReceiveGrossProfit, ",
            "receivedGrossProfit, cashAmount, usedPrepaidAmount, deliveredQuantity, inTransitQuantity, ",
            "canceledQuantity, returnedQuantity, unhandledQuantity, ongoingOutboundOrderInfo, ",
            "finishedOutboundOrderInfo, dataVersion, createTime, lastUpdateTime, easCode, ",
            "entryId, shortageReason, jdCurrentRecordNumber, categoryId, categoryName, tracelog",
            "from phjd_sales_order_item",
            "where salesOrderNo = #{salesOrderNo}"
    })
    List<SalesOrderItem> listBySalesOrderNo(@Param("prefix") String prefix,
                                                  @Param("salesOrderNo") String salesOrderNo);
   /**
    * 根据销售订单号和商品编码获取订单商品
    *
    * @author weizecheng
    * @date 2018/8/21 10:03
    * @param prefix 数据库表前缀
    * @param productCode 商品编码
    * @param salesOrderNo 销售订单编号
    * @return SalesOrderItem
    */
    @Select({
            "select",
            "salesOrderItemId, salesOrderNo, itemStatus, productId, productCode, productName, ",
            "warehouseId, warehouseName, currencyCode, retailPrice, wholesalePrice, totalOrderAmount, ",
            "totalQuantity, jdSkuCode, jdSalesGuidePrice, jdPurchaseGuidePrice, jdTotalQuantity, ",
            "jdPurchaseNo, jdBackorderProcessing, jdComments, generatedPrepaid, shouldReceiveGrossProfit, ",
            "receivedGrossProfit, cashAmount, usedPrepaidAmount, deliveredQuantity, inTransitQuantity, ",
            "canceledQuantity, returnedQuantity, unhandledQuantity, ongoingOutboundOrderInfo, ",
            "finishedOutboundOrderInfo, dataVersion, createTime, lastUpdateTime, easCode, ",
            "entryId, shortageReason, jdCurrentRecordNumber, categoryId, categoryName, tracelog",
            "from ${prefix}_sales_order_item",
            "where salesOrderNo = #{salesOrderNo}",
            "and",
            "productCode = #{productCode}"
    })
    SalesOrderItem getBySalesOrderNoAndProductCode(@Param("prefix") String prefix,
                                                          @Param("salesOrderNo") String salesOrderNo,
                                                          @Param("productCode")String productCode);
    /**
     *更新订单审核信息
     *
     *@auther weizecheng
     *@date 2018/8/24 10:40
     *@param prefix 表前缀
     *@param dataVersion 版本号 乐观锁
     *@param id 订单商品表Id
     *@param shortageReason 缺货信息
     *@param status 订单商品表状态
     *@param totalOrderAmount 实际付款金额
     *@param totalQuantity 实际总量
     *@return int 1 成功
     */
    @Update({
            "<script>",
            "update ${prefix}_sales_order_item",
            "set ",
            "itemStatus = #{itemStatus},",
            "totalQuantity = #{totalQuantity},",
            "unhandledQuantity = #{totalQuantity},",
            "totalOrderAmount = #{totalOrderAmount},",
            "dataVersion = dataVersion+1",
            "<when test='shortageReason!=null'>",
            ",shortageReason = #{shortageReason}",
            "</when>",
            "where salesOrderItemId = #{salesOrderItemId} AND dataVersion = #{dataVersion}",
            "</script>"
    })
    int updateReviewSalesOrderItem(@Param("prefix") String prefix,
                               @Param("salesOrderItemId")Long id,
                               @Param("itemStatus")Integer status,
                               @Param("shortageReason")String shortageReason,
                               @Param("totalQuantity")Integer totalQuantity,
                               @Param("totalOrderAmount") BigDecimal totalOrderAmount,
                               @Param("dataVersion")Long dataVersion);


    /**
     *更新待预约发货数量
     *
     * @author weizecheng
     * @date 2018/8/29 10:24
     * @param prefix 表前缀
     * @param id 销售订单商品表Id
     * @param dataVersion 版本号
     * @param unhandledQuantity 待预约发货数量
     * @return int
     */
    @Update({
            "update ${prefix}_sales_order_item",
            "set ",
            "unhandledQuantity = #{unhandledQuantity},",
            "dataVersion = dataVersion+1",
            "where salesOrderItemId = #{salesOrderItemId} AND dataVersion = #{dataVersion}",
    })
    int updateUnhandledQuantitySalesOrderItem(@Param("prefix") String prefix,
                                              @Param("salesOrderItemId")Long id,
                                              @Param("dataVersion")Long dataVersion,
                                              @Param("unhandledQuantity")Integer unhandledQuantity);

    /**
     * 插入销售订单
     *
     * @author weizecheng
     * @date 2018/8/29 15:46
     * @param prefix 表前缀
     * @param item 销售订单商品
     * @return int
     */
    @Insert({
            "insert into ${prefix}_sales_order_item (salesOrderNo, ",
            "itemStatus, productId, ",
            "productCode, productName, ",
            "warehouseId, warehouseName, ",
            "currencyCode, retailPrice, ",
            "wholesalePrice, totalOrderAmount, ",
            "totalQuantity, jdSkuCode, ",
            "jdSalesGuidePrice, jdPurchaseGuidePrice, ",
            "jdTotalQuantity, jdPurchaseNo, ",
            "jdBackorderProcessing, jdComments, ",
            "cashAmount, ",
            "ongoingOutboundOrderInfo, finishedOutboundOrderInfo, ",
            "dataVersion, createTime, ",
            "easCode, ",
            "entryId, ",
            "jdCurrentRecordNumber, categoryId, ",
            "categoryName, tracelog)",
            "values ( #{item.salesOrderNo,jdbcType=VARCHAR}, ",
            "#{item.itemStatus,jdbcType=TINYINT}, #{item.productId,jdbcType=BIGINT}, ",
            "#{item.productCode,jdbcType=VARCHAR}, #{item.productName,jdbcType=VARCHAR}, ",
            "#{item.warehouseId,jdbcType=INTEGER}, #{item.warehouseName,jdbcType=VARCHAR}, ",
            "#{item.currencyCode,jdbcType=VARCHAR}, #{item.retailPrice,jdbcType=DECIMAL}, ",
            "#{item.wholesalePrice,jdbcType=DECIMAL}, #{item.totalOrderAmount,jdbcType=DECIMAL}, ",
            "#{item.totalQuantity,jdbcType=INTEGER}, #{item.jdSkuCode,jdbcType=VARCHAR}, ",
            "#{item.jdSalesGuidePrice,jdbcType=DECIMAL}, #{item.jdPurchaseGuidePrice,jdbcType=DECIMAL}, ",
            "#{item.jdTotalQuantity,jdbcType=INTEGER}, #{item.jdPurchaseNo,jdbcType=VARCHAR}, ",
            "#{item.jdBackorderProcessing,jdbcType=VARCHAR}, #{item.jdComments,jdbcType=VARCHAR}, ",
            "#{item.cashAmount,jdbcType=DECIMAL}, ",
            "#{item.ongoingOutboundOrderInfo,jdbcType=VARCHAR}, #{item.finishedOutboundOrderInfo,jdbcType=VARCHAR}, ",
            "#{item.dataVersion,jdbcType=BIGINT}, NOW(), ",
            "#{item.easCode,jdbcType=VARCHAR}, ",
            "#{item.entryId,jdbcType=VARCHAR}, ",
            "#{item.jdCurrentRecordNumber,jdbcType=INTEGER}, #{item.categoryId,jdbcType=VARCHAR}, ",
            "#{item.categoryName,jdbcType=VARCHAR}, #{item.tracelog,jdbcType=LONGVARCHAR})"
    })
    int insertSalesOrderItem(@Param("prefix") String prefix,
                             @Param("item")SalesOrderItem item);

    /**
     * 更新已送达数量 和 在途数量
     *
     * @author weizecheng
     * @date 2018/8/31 19:28
     * @param prefix 表前缀
     * @param salesOrderItemId 主键Id
     * @param dataVersion 版本号
     * @param deliveredQuantity 已送达数量
     * @param inTransitQuantity 在途数量
     * @param generatedPrepaid 产生代垫
     * @param shouldReceiveGrossProfit 应收毛利
     * @param receivedGrossProfit 实收毛利
     * @return int
     */
    @Update({
            "update ${prefix}_sales_order_item",
            "set ",
            "deliveredQuantity = #{deliveredQuantity},",
            "inTransitQuantity = #{inTransitQuantity},",
            "generatedPrepaid = #{generatedPrepaid},",
            "shouldReceiveGrossProfit = #{shouldReceiveGrossProfit},",
            "receivedGrossProfit = #{receivedGrossProfit},",
            "dataVersion = dataVersion+1",
            "where salesOrderItemId = #{salesOrderItemId} AND dataVersion = #{dataVersion}"
    })
    int  updateDeliveryAndInTransitQuantity(@Param("prefix") String prefix,
                                            @Param("salesOrderItemId") Long salesOrderItemId,
                                            @Param("dataVersion") Long dataVersion,
                                            @Param("deliveredQuantity") int deliveredQuantity,
                                            @Param("inTransitQuantity") int inTransitQuantity,
                                            @Param("generatedPrepaid") BigDecimal generatedPrepaid,
                                            @Param("shouldReceiveGrossProfit") BigDecimal shouldReceiveGrossProfit,
                                            @Param("receivedGrossProfit") BigDecimal receivedGrossProfit);

    /**
     * 更新已送达数量 和 在途数量
     *
     * @author weizecheng
     * @date 2018/8/31 19:28
     * @param prefix 表前缀
     * @param salesOrderItemId 主键Id
     * @param dataVersion 版本号
     * @param deliveredQuantity 已送达数量
     * @param inTransitQuantity 在途数量
     * @return int
     */
    @Update({
            "update ${prefix}_sales_order_item",
            "set ",
            "deliveredQuantity = #{deliveredQuantity},",
            "inTransitQuantity = #{inTransitQuantity},",
            "dataVersion = dataVersion+1",
            "where salesOrderItemId = #{salesOrderItemId} AND dataVersion = #{dataVersion}"
    })
    int  updateDeliveryAndInTransitQuantitySign(@Param("prefix") String prefix,
                                            @Param("salesOrderItemId") Long salesOrderItemId,
                                            @Param("dataVersion") Long dataVersion,
                                            @Param("deliveredQuantity") int deliveredQuantity,
                                            @Param("inTransitQuantity") int inTransitQuantity);

    /**
     * 更新销售订单商品Eas信息
     *
     * @author weizecheng
     * @date 2018/9/3 18:00
     * @param prefix
     * @param salesOrderItemId
     * @param dataVersion
     * @param entryId
     * @return
     */
    @Update({
            "update ${prefix}_sales_order_item",
            "set ",
            "entryId = #{entryId},",
            "dataVersion = dataVersion+1",
            "where salesOrderItemId = #{salesOrderItemId} AND dataVersion = #{dataVersion}"
    })
    int updateEasEntryId(@Param("prefix") String prefix,
                         @Param("salesOrderItemId") Long salesOrderItemId,
                         @Param("dataVersion") Long dataVersion,
                         @Param("entryId") String entryId);

}
