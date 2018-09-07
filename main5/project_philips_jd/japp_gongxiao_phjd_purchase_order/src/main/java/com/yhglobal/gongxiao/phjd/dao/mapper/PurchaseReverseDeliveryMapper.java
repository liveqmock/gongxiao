package com.yhglobal.gongxiao.phjd.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.phjd.model.PurchaseReverseDelivery;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface PurchaseReverseDeliveryMapper extends BaseMapper {
    @Delete({
        "delete from purchase_reverse_delivery",
        "where reverseId = #{reverseId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long reverseId);

    @Insert({
        "insert into purchase_reverse_delivery (reverseId, reverseStatus, ",
        "reverseType, purchaseReturnedOrderNo, ",
        "syncToGongxiaoWarehouseFlag, gongxiaoWarehouseOutboundOrderNo, ",
        "originalPurchaseOrderNo, warehouseId, ",
        "warehouseName, shippingMode, ",
        "logisticsOrderNo, logisticsCompany, ",
        "productInfo, totalQuantity, ",
        "dataVersion, createTime, ",
        "lastUpdateTime, tracelog)",
        "values (#{reverseId,jdbcType=BIGINT}, #{reverseStatus,jdbcType=TINYINT}, ",
        "#{reverseType,jdbcType=TINYINT}, #{purchaseReturnedOrderNo}, ",
        "#{syncToGongxiaoWarehouseFlag,jdbcType=TINYINT}, #{gongxiaoWarehouseOutboundOrderNo,jdbcType=VARCHAR}, ",
        "#{originalPurchaseOrderNo,jdbcType=VARCHAR}, #{warehouseId,jdbcType=VARCHAR}, ",
        "#{warehouseName,jdbcType=VARCHAR}, #{shippingMode,jdbcType=TINYINT}, ",
        "#{logisticsOrderNo,jdbcType=VARCHAR}, #{logisticsCompany,jdbcType=VARCHAR}, ",
        "#{productInfo,jdbcType=VARCHAR}, #{totalQuantity,jdbcType=INTEGER}, ",
        "#{dataVersion,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{lastUpdateTime,jdbcType=TIMESTAMP}, #{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(PurchaseReverseDelivery record);


    @Select({
        "select",
        "reverseId, reverseStatus, reverseType, purchaseReturnedOrderNo, syncToGongxiaoWarehouseFlag, ",
        "gongxiaoWarehouseOutboundOrderNo, originalPurchaseOrderNo, warehouseId, warehouseName, ",
        "shippingMode, logisticsOrderNo, logisticsCompany, productInfo, totalQuantity, ",
        "dataVersion, createTime, lastUpdateTime, tracelog",
        "from purchase_reverse_delivery",
        "where reverseId = #{reverseId,jdbcType=BIGINT}"
    })
    PurchaseReverseDelivery selectByPrimaryKey(Long reverseId);

    @Select({
            "select",
            "reverseId, reverseStatus, reverseType, purchaseReturnedOrderNo, syncToGongxiaoWarehouseFlag, ",
            "gongxiaoWarehouseOutboundOrderNo, originalPurchaseOrderNo, warehouseId, warehouseName, ",
            "shippingMode, logisticsOrderNo, logisticsCompany, productInfo, totalQuantity, ",
            "dataVersion, createTime, lastUpdateTime, tracelog",
            "from purchase_reverse_delivery",
            "where gongxiaoWarehouseOutboundOrderNo = #{gongxiaoWarehouseOutboundOrderNo}"
    })
    PurchaseReverseDelivery getDeliveryByOutboundOrderNo(String gongxiaoWarehouseOutboundOrderNo);

    @Select({
            "select",
            "reverseId, reverseStatus, reverseType, purchaseReturnedOrderNo, syncToGongxiaoWarehouseFlag, ",
            "gongxiaoWarehouseOutboundOrderNo, originalPurchaseOrderNo, warehouseId, warehouseName, ",
            "shippingMode, logisticsOrderNo, logisticsCompany, productInfo, totalQuantity, ",
            "dataVersion, createTime, lastUpdateTime, tracelog",
            "from purchase_reverse_delivery",
            "where gongxiaoWarehouseOutboundOrderNo = #{gongxiaoWarehouseOutboundOrderNo}"
    })
    List<PurchaseReverseDelivery> selectByReturnOrder(String purchaseReturnedOrderNo);



    @Update({
        "update purchase_reverse_delivery",
        "set reverseStatus = #{reverseStatus,jdbcType=TINYINT},",
          "reverseType = #{reverseType,jdbcType=TINYINT},",
          "purchaseReturnedOrderNo = #{purchaseReturnedOrderNo},",
          "syncToGongxiaoWarehouseFlag = #{syncToGongxiaoWarehouseFlag,jdbcType=TINYINT},",
          "gongxiaoWarehouseOutboundOrderNo = #{gongxiaoWarehouseOutboundOrderNo,jdbcType=VARCHAR},",
          "originalPurchaseOrderNo = #{originalPurchaseOrderNo,jdbcType=VARCHAR},",
          "warehouseId = #{warehouseId,jdbcType=VARCHAR},",
          "warehouseName = #{warehouseName,jdbcType=VARCHAR},",
          "shippingMode = #{shippingMode,jdbcType=TINYINT},",
          "logisticsOrderNo = #{logisticsOrderNo,jdbcType=VARCHAR},",
          "logisticsCompany = #{logisticsCompany,jdbcType=VARCHAR},",
          "productInfo = #{productInfo,jdbcType=VARCHAR},",
          "totalQuantity = #{totalQuantity,jdbcType=INTEGER},",
          "dataVersion = #{dataVersion,jdbcType=BIGINT},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
        "where reverseId = #{reverseId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PurchaseReverseDelivery record);

    @Update({
            "update purchase_reverse_delivery",
            "set reverseStatus = #{reverseStatus,jdbcType=TINYINT},",
            "productInfo = #{productInfo,jdbcType=VARCHAR},",
            "totalQuantity = #{totalQuantity,jdbcType=INTEGER},",
            "where gongxiaoWarehouseOutboundOrderNo = #{gongxiaoWarehouseOutboundOrderNo}"
    })
    int updateByOutboundOrderNo(String gongxiaoWarehouseOutboundOrderNo, byte reverseStatus, int totalQuantity, String productInfo);

    @Update({
            "update purchase_reverse_delivery",
            "set reverseStatus = #{reverseStatus,jdbcType=TINYINT},",
            "where gongxiaoWarehouseOutboundOrderNo = #{gongxiaoWarehouseOutboundOrderNo}"
    })
    int updateStatus(String gongxiaoWarehouseOutboundOrderNo, byte reverseStatus);
}