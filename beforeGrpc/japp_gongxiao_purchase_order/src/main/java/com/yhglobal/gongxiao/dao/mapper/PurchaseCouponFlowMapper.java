package com.yhglobal.gongxiao.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.model.PurchaseCouponFlow;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface PurchaseCouponFlowMapper extends BaseMapper{
    @Delete({
        "delete from purchase_coupon_flow",
        "where purchaseCouponFlowId = #{purchaseCouponFlowId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long purchaseCouponFlowId);

    @Insert({
        "insert into purchase_coupon_flow (purchaseCouponFlowId, projectId, ",
        "projectName, purchaseCouponLedgerId, ",
        "receivedCurrencyCode, confirmedCouponAmount, ",
        "receivedCouponAmount, differenceAmount, ",
        "transferDate, transferFlowId, ",
        "confirmDate, startDate, ",
        "endDate, sourcePurchaseOrderNo, ",
        "syncToCouponCentralFlag, couponCenterFlowId, ",
        "dataVersion, createdById, ",
        "createdByName, createTime, ",
        "lastUpdateTime, transferPattern, ",
        "differenceAmountAdjustPattern, confirmCurrencyCode )",
        "values (#{purchaseCouponFlowId,jdbcType=BIGINT}, #{projectId,jdbcType=BIGINT}, ",
        "#{projectName,jdbcType=VARCHAR}, #{purchaseCouponLedgerId,jdbcType=BIGINT}, ",
        "#{receivedCurrencyCode,jdbcType=VARCHAR}, #{confirmedCouponAmount,jdbcType=BIGINT}, ",
        "#{receivedCouponAmount,jdbcType=BIGINT}, #{differenceAmount,jdbcType=BIGINT}, ",
        "#{transferDate,jdbcType=TIMESTAMP}, #{transferFlowId,jdbcType=VARCHAR}, ",
        "#{confirmDate,jdbcType=TIMESTAMP}, #{startDate,jdbcType=TIMESTAMP}, ",
        "#{endDate,jdbcType=TIMESTAMP}, #{sourcePurchaseOrderNo,jdbcType=VARCHAR}, ",
        "#{syncToCouponCentralFlag,jdbcType=TINYINT}, #{couponCenterFlowId,jdbcType=VARCHAR}, ",
        "#{dataVersion,jdbcType=BIGINT}, #{createdById,jdbcType=BIGINT}, ",
        "#{createdByName,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{lastUpdateTime,jdbcType=TIMESTAMP}, #{transferPattern, jdbcType=VARCHAR}",
        "#{differenceAmountAdjustPattern, jdbcType=VARCHAR}, #{confirmCurrencyCode, jdbcType=VARCHAR})"
    })
    int insert(PurchaseCouponFlow record);

    @Select({
        "select",
        "purchaseCouponFlowId, projectId, projectName, purchaseCouponLedgerId, receivedCurrencyCode, ",
        "confirmedCouponAmount, receivedCouponAmount, differenceAmount, transferDate, ",
        "transferFlowId, confirmDate, startDate, endDate, sourcePurchaseOrderNo, syncToCouponCentralFlag, ",
        "couponCenterFlowId, dataVersion, createdById, createdByName, createTime, lastUpdateTime",
        "transferPattern, differenceAmountAdjustPattern, confirmCurrencyCode",
        "from purchase_coupon_flow",
        "where purchaseCouponFlowId = #{purchaseCouponFlowId,jdbcType=BIGINT}"
    })
    PurchaseCouponFlow selectByPrimaryKey(Long purchaseCouponFlowId);

    @Select({
            "select",
            "purchaseCouponFlowId, projectId, projectName, purchaseCouponLedgerId, receivedCurrencyCode, ",
            "confirmedCouponAmount, receivedCouponAmount, differenceAmount, transferDate, ",
            "transferFlowId, confirmDate, startDate, endDate, sourcePurchaseOrderNo, syncToCouponCentralFlag, ",
            "couponCenterFlowId, dataVersion, createdById, createdByName, createTime, lastUpdateTime",
            "transferPattern, differenceAmountAdjustPattern, confirmCurrencyCode",
            "from purchase_coupon_flow",
            "where purchaseCouponFlowId = #{purchaseCouponFlowId,jdbcType=BIGINT}"
    })
    List<PurchaseCouponFlow> selectByProjectId(String projectId);

    @Update({
        "update purchase_coupon_flow",
        "set projectId = #{projectId,jdbcType=BIGINT},",
          "projectName = #{projectName,jdbcType=VARCHAR},",
          "purchaseCouponLedgerId = #{purchaseCouponLedgerId,jdbcType=BIGINT},",
          "receivedCurrencyCode = #{receivedCurrencyCode,jdbcType=VARCHAR},",
          "confirmedCouponAmount = #{confirmedCouponAmount,jdbcType=BIGINT},",
          "receivedCouponAmount = #{receivedCouponAmount,jdbcType=BIGINT},",
          "differenceAmount = #{differenceAmount,jdbcType=BIGINT},",
          "transferDate = #{transferDate,jdbcType=TIMESTAMP},",
          "transferFlowId = #{transferFlowId,jdbcType=VARCHAR},",
          "confirmDate = #{confirmDate,jdbcType=TIMESTAMP},",
          "startDate = #{startDate,jdbcType=TIMESTAMP},",
          "endDate = #{endDate,jdbcType=TIMESTAMP},",
          "sourcePurchaseOrderNo = #{sourcePurchaseOrderNo,jdbcType=VARCHAR},",
          "syncToCouponCentralFlag = #{syncToCouponCentralFlag,jdbcType=TINYINT},",
          "couponCenterFlowId = #{couponCenterFlowId,jdbcType=VARCHAR},",
          "dataVersion = #{dataVersion,jdbcType=BIGINT},",
          "createdById = #{createdById,jdbcType=BIGINT},",
          "createdByName = #{createdByName,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
          "transferPattern = #{createdByName,jdbcType=VARCHAR}  ",
          "differenceAmountAdjustPattern = #{differenceAmountAdjustPattern,jdbcType=VARCHAR}",
          "confirmCurrencyCode = #{confirmCurrencyCode,jdbcType=VARCHAR}",
        "where purchaseCouponFlowId = #{purchaseCouponFlowId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PurchaseCouponFlow record);
}