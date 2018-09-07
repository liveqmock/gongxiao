package com.yhglobal.gongxiao.phjd.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.phjd.model.PurchaseReturnedOrderItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface PurchaseReturnedOrderItemMapper  extends BaseMapper {
    @Delete({
        "delete from purchase_returned_order_item",
        "where rowId = #{rowId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long rowId);

    @Insert({
        "insert into purchase_returned_order_item (rowId, purchaseReturnedOrderNo, ",
        "itemStatus, syncToWmsFlag, ",
        "returnReason, outboundOrderNo, ",
        "warehouseId, warehouseName, ",
        "productId, productCode, ",
        "productName, productUnit, ",
        "currencyCode, currencyName, ",
        "guidePrice, purchasePrice, ",
        "returnAmount, returnReferNumber, ",
        "totalReturnedQuantity, totalImperfectQuantity, ",
        "totalOutStockQuantity, outStockImperfectQuantity, ",
        "deliveredQuantity, dataVersion, ",
        "createTime, lastUpdateTime, ",
        "tracelog)",
        "values (#{rowId,jdbcType=BIGINT}, #{purchaseReturnedOrderNo,jdbcType=VARCHAR}, ",
        "#{itemStatus,jdbcType=TINYINT}, #{syncToWmsFlag,jdbcType=TINYINT}, ",
        "#{returnReason,jdbcType=VARCHAR}, #{outboundOrderNo,jdbcType=VARCHAR}, ",
        "#{warehouseId,jdbcType=VARCHAR}, #{warehouseName,jdbcType=VARCHAR}, ",
        "#{productId,jdbcType=VARCHAR}, #{productCode,jdbcType=VARCHAR}, ",
        "#{productName,jdbcType=VARCHAR}, #{productUnit,jdbcType=VARCHAR}, ",
        "#{currencyCode,jdbcType=VARCHAR}, #{currencyName,jdbcType=VARCHAR}, ",
        "#{guidePrice,jdbcType=BIGINT}, #{purchasePrice,jdbcType=BIGINT}, ",
        "#{returnAmount,jdbcType=BIGINT}, #{returnReferNumber,jdbcType=INTEGER}, ",
        "#{totalReturnedQuantity,jdbcType=INTEGER}, #{totalImperfectQuantity,jdbcType=INTEGER}, ",
        "#{totalOutStockQuantity,jdbcType=INTEGER}, #{outStockImperfectQuantity,jdbcType=INTEGER}, ",
        "#{deliveredQuantity,jdbcType=INTEGER}, #{dataVersion,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP}, ",
        "#{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(PurchaseReturnedOrderItem record);

    @Select({
        "select",
        "rowId, purchaseReturnedOrderNo, itemStatus, syncToWmsFlag, returnReason, outboundOrderNo, ",
        "warehouseId, warehouseName, productId, productCode, productName, productUnit, ",
        "currencyCode, currencyName, guidePrice, purchasePrice, returnAmount, returnReferNumber, ",
        "totalReturnedQuantity, totalImperfectQuantity, totalOutStockQuantity, outStockImperfectQuantity, ",
        "deliveredQuantity, dataVersion, createTime, lastUpdateTime, tracelog",
        "from purchase_returned_order_item",
        "where rowId = #{rowId,jdbcType=BIGINT}"
    })
    PurchaseReturnedOrderItem selectByPrimaryKey(Long rowId);

    @Select({
            "select",
            "rowId, purchaseReturnedOrderNo, itemStatus, syncToWmsFlag, returnReason, outboundOrderNo, ",
            "warehouseId, warehouseName, productId, productCode, productName, productUnit, ",
            "currencyCode, currencyName, guidePrice, purchasePrice, returnAmount, returnReferNumber, ",
            "totalReturnedQuantity, totalImperfectQuantity, totalOutStockQuantity, outStockImperfectQuantity, ",
            "deliveredQuantity, dataVersion, createTime, lastUpdateTime, tracelog",
            "from purchase_returned_order_item",
            "where purchaseReturnedOrderNo = #{purchaseReturnedOrderNo}"
    })

    List<PurchaseReturnedOrderItem> selectByReturnedOrderNo(String purchaseReturnedOrderNo);


    @Update({
            "update purchase_returned_order_item",
            "set",
            "itemStatus = #{itemStatus,jdbcType=TINYINT},",
            "totalOutStockQuantity = #{totalOutStockQuantity,jdbcType=INTEGER},",
            "outStockImperfectQuantity = #{outStockImperfectQuantity,jdbcType=INTEGER},",
            "deliveredQuantity = #{deliveredQuantity,jdbcType=INTEGER},",
            "where rowId = #{rowId,jdbcType=BIGINT}"
    })
    int updateByReturnOrderNo(long rowId, byte itemStatus, int totalOutStockQuantity, int outStockImperfectQuantity, int deliveredQuantity);


}