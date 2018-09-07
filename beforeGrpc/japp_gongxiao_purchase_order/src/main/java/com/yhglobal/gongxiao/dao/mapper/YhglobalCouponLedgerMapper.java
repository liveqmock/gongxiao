//package com.yhglobal.gongxiao.dao.mapper;
//
//import com.yhglobal.gongxiao.common.mapper.BaseMapper;
////import com.yhglobal.gongxiao.model.YhglobalCouponLedger;
//import org.apache.ibatis.annotations.Delete;
//import org.apache.ibatis.annotations.Insert;
//import org.apache.ibatis.annotations.Select;
//import org.apache.ibatis.annotations.Update;
//
//import java.util.List;
//
//
//public interface YhglobalToReceiveCouponLedgerMapper extends BaseMapper {
//    @Delete({
//        "delete from yhglobal_coupon_ledger",
//        "where purchaseCouponLedgerId = #{purchaseCouponLedgerId,jdbcType=BIGINT}"
//    })
//    int deleteByPrimaryKey(Long purchaseCouponLedgerId);
//
//    @Insert({
//        "insert into yhglobal_coupon_ledger (purchaseCouponLedgerId, couponStatus, ",
//        "couponType, couponModel, ",
//        "couponRatio, projectId, ",
//        "projectName, supplierId, ",
//        "supplierName, currencyCode, ",
//        "supplierOrderNo, purchaseOrderNo, ",
//        "purchaseTime, estimatedCouponAmount, ",
//        "estimatedPostingDate, confirmedCouponAmount, ",
//        "receivedCouponAmount, dataVersion, ",
//        "createTime, lastUpdateTime, ",
//        "tracelog)",
//        "values (#{purchaseCouponLedgerId,jdbcType=BIGINT}, #{couponStatus,jdbcType=TINYINT}, ",
//        "#{couponType,jdbcType=TINYINT}, #{couponModel,jdbcType=VARCHAR}, ",
//        "#{couponRatio,jdbcType=INTEGER}, #{projectId,jdbcType=BIGINT}, ",
//        "#{projectName,jdbcType=VARCHAR}, #{supplierId,jdbcType=BIGINT}, ",
//        "#{supplierName,jdbcType=VARCHAR}, #{currencyCode,jdbcType=VARCHAR}, ",
//        "#{supplierOrderNo,jdbcType=VARCHAR}, #{purchaseOrderNo,jdbcType=VARCHAR}, ",
//        "#{purchaseTime,jdbcType=TIMESTAMP}, #{estimatedCouponAmount,jdbcType=BIGINT}, ",
//        "#{estimatedPostingDate,jdbcType=TIMESTAMP}, #{confirmedCouponAmount,jdbcType=BIGINT}, ",
//        "#{receivedCouponAmount,jdbcType=BIGINT}, #{dataVersion,jdbcType=BIGINT}, ",
//        "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP}, ",
//        "#{tracelog,jdbcType=LONGVARCHAR})"
//    })
//    int insert(YhglobalCouponLedger record);
//
//    @Select({
//        "select",
//        "purchaseCouponLedgerId, couponStatus, couponType, couponModel, couponRatio, ",
//        "projectId, projectName, supplierId, supplierName, currencyCode, supplierOrderNo, ",
//        "purchaseOrderNo, purchaseTime, estimatedCouponAmount, estimatedPostingDate, ",
//        "confirmedCouponAmount, receivedCouponAmount, dataVersion, createTime, lastUpdateTime, ",
//        "tracelog",
//        "from yhglobal_coupon_ledger",
//        "where purchaseCouponLedgerId = #{purchaseCouponLedgerId,jdbcType=BIGINT}"
//    })
//    YhglobalCouponLedger selectByPrimaryKey(Long purchaseCouponLedgerId);
//
//    @Select({
//            "select",
//            "purchaseCouponLedgerId, couponStatus, couponType, couponModel, couponRatio, ",
//            "projectId, projectName, supplierId, supplierName, currencyCode, supplierOrderNo, ",
//            "purchaseOrderNo, purchaseTime, estimatedCouponAmount, estimatedPostingDate, ",
//            "confirmedCouponAmount, receivedCouponAmount, dataVersion, createTime, lastUpdateTime, ",
//            "tracelog",
//            "from yhglobal_coupon_ledger",
//            "where projectId = #{projectId}"
//    })
//    List<YhglobalCouponLedger> selectByProjectId(String projectId);
//
//
//    @Update({
//        "update yhglobal_coupon_ledger",
//        "set couponStatus = #{couponStatus,jdbcType=TINYINT},",
//          "couponType = #{couponType,jdbcType=TINYINT},",
//          "couponModel = #{couponModel,jdbcType=VARCHAR},",
//          "couponRatio = #{couponRatio,jdbcType=INTEGER},",
//          "projectId = #{projectId,jdbcType=BIGINT},",
//          "projectName = #{projectName,jdbcType=VARCHAR},",
//          "supplierId = #{supplierId,jdbcType=BIGINT},",
//          "supplierName = #{supplierName,jdbcType=VARCHAR},",
//          "currencyCode = #{currencyCode,jdbcType=VARCHAR},",
//          "supplierOrderNo = #{supplierOrderNo,jdbcType=VARCHAR},",
//          "purchaseOrderNo = #{purchaseOrderNo,jdbcType=VARCHAR},",
//          "purchaseTime = #{purchaseTime,jdbcType=TIMESTAMP},",
//          "estimatedCouponAmount = #{estimatedCouponAmount,jdbcType=BIGINT},",
//          "estimatedPostingDate = #{estimatedPostingDate,jdbcType=TIMESTAMP},",
//          "confirmedCouponAmount = #{confirmedCouponAmount,jdbcType=BIGINT},",
//          "receivedCouponAmount = #{receivedCouponAmount,jdbcType=BIGINT},",
//          "dataVersion = #{dataVersion,jdbcType=BIGINT},",
//          "createTime = #{createTime,jdbcType=TIMESTAMP},",
//          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
//        "where purchaseCouponLedgerId = #{purchaseCouponLedgerId,jdbcType=BIGINT}"
//    })
//    int updateByPrimaryKey(YhglobalCouponLedger record);
//
//    // 按照订单号 和 返利类型 获取返利对象
//    @Select({ "select",
//            "purchaseCouponLedgerId, couponStatus, couponType, couponModel, couponRatio, ",
//            "projectId, projectName, supplierId, supplierName, currencyCode, supplierOrderNo, ",
//            "purchaseOrderNo, purchaseTime, estimatedCouponAmount, estimatedPostingDate, ",
//            "confirmedCouponAmount, receivedCouponAmount, dataVersion, createTime, lastUpdateTime, ",
//            "tracelog",
//            "from yhglobal_coupon_ledger",
//            "where supplierOrderNo = #{supplierOrderNo,jdbcType=VARCHAR} and couponType =  #{couponType,jdbcType=TINYINT}"})
//    YhglobalCouponLedger selectBySupplierOrderNoAndCouponType(String supplierOrderNo, int couponType);
//}