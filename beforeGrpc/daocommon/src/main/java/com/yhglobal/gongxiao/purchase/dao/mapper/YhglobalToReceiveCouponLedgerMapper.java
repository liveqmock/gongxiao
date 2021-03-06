package com.yhglobal.gongxiao.purchase.dao.mapper;


import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.model.YhglobalCouponLedger;
import com.yhglobal.gongxiao.model.YhglobalCouponLedgerItem;
import com.yhglobal.gongxiao.payment.flow.dao.mapping.YhglobalCouponRecordSqlProvider;
import org.apache.ibatis.annotations.*;

import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

public interface YhglobalToReceiveCouponLedgerMapper extends BaseMapper{
    @Delete({
        "delete from yhglobal_to_receive_coupon_ledger",
        "where purchaseCouponLedgerId = #{purchaseCouponLedgerId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long purchaseCouponLedgerId);

    @Select({
            "select",
            "purchaseCouponLedgerId, couponStatus, couponType, couponModel, couponRatio, ",
            "projectId, projectName, supplierId, supplierName, currencyCode, supplierOrderNo, ",
            "purchaseOrderNo, purchaseTime, estimatedCouponAmount, estimatedPostingDate, ",
            "confirmedCouponAmount, receivedCouponAmount, dataVersion, createTime, lastUpdateTime, ",
            "confirmRecord, toBeConfirmAmount, tracelog",
            "from yhglobal_to_receive_coupon_ledger",
            "where purchaseOrderNo = #{purchaseOrderNo,jdbcType=VARCHAR} and couponType = #{couponType,jdbcType=TINYINT}"})
    YhglobalCouponLedger getByPurchaseOrderNoAndCouponType(@Param("purchaseOrderNo") String purchaseOrderNo,
                                                           @Param("couponType") Byte couponType);
    @Select({
            "select",
            "purchaseCouponLedgerId, couponStatus, couponType, couponModel, couponRatio, ",
            "projectId, projectName, supplierId, supplierName, currencyCode, supplierOrderNo, ",
            "purchaseOrderNo, purchaseTime, estimatedCouponAmount, estimatedPostingDate, ",
            "confirmedCouponAmount, receivedCouponAmount, dataVersion, createTime, lastUpdateTime, ",
            "confirmRecord, toBeConfirmAmount, tracelog",
            "from yhglobal_to_receive_coupon_ledger",
            "where purchaseOrderNo = #{purchaseOrderNo,jdbcType=VARCHAR}"})
    List<YhglobalCouponLedger> getByPurchaseOrderNo(@Param("purchaseOrderNo") String purchaseOrderNo);
    @Insert({
            "insert into yhglobal_to_receive_coupon_ledger (purchaseCouponLedgerId, couponStatus, ",
            "couponType, couponModel, ",
            "couponRatio, projectId, ",
            "projectName, supplierId, ",
            "supplierName, currencyCode, ",
            "supplierOrderNo, purchaseOrderNo, ",
            "purchaseTime, estimatedCouponAmount, ",
            "estimatedPostingDate, confirmedCouponAmount, ",
            "receivedCouponAmount, dataVersion, ",
            "createTime, lastUpdateTime, ",
            "confirmRecord, toBeConfirmAmount, ",
            "tracelog)",
            "values (#{purchaseCouponLedgerId,jdbcType=BIGINT}, #{couponStatus,jdbcType=TINYINT}, ",
            "#{couponType,jdbcType=TINYINT}, #{couponModel,jdbcType=VARCHAR}, ",
            "#{couponRatio,jdbcType=INTEGER}, #{projectId,jdbcType=BIGINT}, ",
            "#{projectName,jdbcType=VARCHAR}, #{supplierId,jdbcType=BIGINT}, ",
            "#{supplierName,jdbcType=VARCHAR}, #{currencyCode,jdbcType=VARCHAR}, ",
            "#{supplierOrderNo,jdbcType=VARCHAR}, #{purchaseOrderNo,jdbcType=VARCHAR}, ",
            "#{purchaseTime,jdbcType=TIMESTAMP}, #{estimatedCouponAmount,jdbcType=BIGINT}, ",
            "#{estimatedPostingDate,jdbcType=TIMESTAMP}, #{confirmedCouponAmount,jdbcType=BIGINT}, ",
            "#{receivedCouponAmount,jdbcType=BIGINT}, #{dataVersion,jdbcType=BIGINT}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP}, ",
            "#{confirmRecord,jdbcType=VARCHAR}, #{toBeConfirmAmount,jdbcType=BIGINT}, ",
            "#{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(YhglobalCouponLedger record);

   /* @InsertProvider(type=YhglobalCouponLedgerSqlProvider.class, method="insertSelective")
    int insertSelective(YhglobalCouponLedger record);*/

    @Select({
            "select",
            "purchaseCouponLedgerId, couponStatus, couponType, couponModel, couponRatio, ",
            "projectId, projectName, supplierId, supplierName, currencyCode, supplierOrderNo, ",
            "purchaseOrderNo, purchaseTime, estimatedCouponAmount, estimatedPostingDate, ",
            "confirmedCouponAmount, receivedCouponAmount, dataVersion, createTime, lastUpdateTime, ",
            "confirmRecord, toBeConfirmAmount, tracelog",
            "from yhglobal_to_receive_coupon_ledger",
            "where purchaseCouponLedgerId = #{purchaseCouponLedgerId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="purchaseCouponLedgerId", property="purchaseCouponLedgerId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="couponStatus", property="couponStatus", jdbcType=JdbcType.TINYINT),
            @Result(column="couponType", property="couponType", jdbcType=JdbcType.TINYINT),
            @Result(column="couponModel", property="couponModel", jdbcType=JdbcType.VARCHAR),
            @Result(column="couponRatio", property="couponRatio", jdbcType=JdbcType.INTEGER),
            @Result(column="projectId", property="projectId", jdbcType=JdbcType.BIGINT),
            @Result(column="projectName", property="projectName", jdbcType=JdbcType.VARCHAR),
            @Result(column="supplierId", property="supplierId", jdbcType=JdbcType.BIGINT),
            @Result(column="supplierName", property="supplierName", jdbcType=JdbcType.VARCHAR),
            @Result(column="currencyCode", property="currencyCode", jdbcType=JdbcType.VARCHAR),
            @Result(column="supplierOrderNo", property="supplierOrderNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="purchaseOrderNo", property="purchaseOrderNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="purchaseTime", property="purchaseTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="estimatedCouponAmount", property="estimatedCouponAmount", jdbcType=JdbcType.BIGINT),
            @Result(column="estimatedPostingDate", property="estimatedPostingDate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="confirmedCouponAmount", property="confirmedCouponAmount", jdbcType=JdbcType.BIGINT),
            @Result(column="receivedCouponAmount", property="receivedCouponAmount", jdbcType=JdbcType.BIGINT),
            @Result(column="dataVersion", property="dataVersion", jdbcType=JdbcType.BIGINT),
            @Result(column="createTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="lastUpdateTime", property="lastUpdateTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="confirmRecord", property="confirmRecord", jdbcType=JdbcType.VARCHAR),
            @Result(column="toBeConfirmAmount", property="toBeConfirmAmount", jdbcType=JdbcType.BIGINT),
            @Result(column="tracelog", property="tracelog", jdbcType=JdbcType.LONGVARCHAR)
    })
    YhglobalCouponLedger selectByPrimaryKey(Long purchaseCouponLedgerId);

   /* @UpdateProvider(type=YhglobalCouponLedgerSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(YhglobalCouponLedger record);
*/
   @Update({
           "update yhglobal_to_receive_coupon_ledger",
           "set couponStatus = #{couponStatus,jdbcType=TINYINT},",
           "couponType = #{couponType,jdbcType=TINYINT},",
           "couponModel = #{couponModel,jdbcType=VARCHAR},",
           "couponRatio = #{couponRatio,jdbcType=INTEGER},",
           "projectId = #{projectId,jdbcType=BIGINT},",
           "projectName = #{projectName,jdbcType=VARCHAR},",
           "supplierId = #{supplierId,jdbcType=BIGINT},",
           "supplierName = #{supplierName,jdbcType=VARCHAR},",
           "currencyCode = #{currencyCode,jdbcType=VARCHAR},",
           "supplierOrderNo = #{supplierOrderNo,jdbcType=VARCHAR},",
           "purchaseOrderNo = #{purchaseOrderNo,jdbcType=VARCHAR},",
           "purchaseTime = #{purchaseTime,jdbcType=TIMESTAMP},",
           "estimatedCouponAmount = #{estimatedCouponAmount,jdbcType=BIGINT},",
           "estimatedPostingDate = #{estimatedPostingDate,jdbcType=TIMESTAMP},",
           "confirmedCouponAmount = #{confirmedCouponAmount,jdbcType=BIGINT},",
           "receivedCouponAmount = #{receivedCouponAmount,jdbcType=BIGINT},",
           "dataVersion = #{dataVersion,jdbcType=BIGINT},",
           "createTime = #{createTime,jdbcType=TIMESTAMP},",
           "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
           "confirmRecord = #{confirmRecord,jdbcType=VARCHAR},",
           "toBeConfirmAmount = #{toBeConfirmAmount,jdbcType=BIGINT},",
           "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
           "where purchaseCouponLedgerId = #{purchaseCouponLedgerId,jdbcType=BIGINT}"
   })
   int updateByPrimaryKeyWithBLOBs(YhglobalCouponLedger record);

    @Update({
            "update yhglobal_to_receive_coupon_ledger",
            "set couponStatus = #{couponStatus,jdbcType=TINYINT},",
            "couponType = #{couponType,jdbcType=TINYINT},",
            "couponModel = #{couponModel,jdbcType=VARCHAR},",
            "couponRatio = #{couponRatio,jdbcType=INTEGER},",
            "projectId = #{projectId,jdbcType=BIGINT},",
            "projectName = #{projectName,jdbcType=VARCHAR},",
            "supplierId = #{supplierId,jdbcType=BIGINT},",
            "supplierName = #{supplierName,jdbcType=VARCHAR},",
            "currencyCode = #{currencyCode,jdbcType=VARCHAR},",
            "supplierOrderNo = #{supplierOrderNo,jdbcType=VARCHAR},",
            "purchaseOrderNo = #{purchaseOrderNo,jdbcType=VARCHAR},",
            "purchaseTime = #{purchaseTime,jdbcType=TIMESTAMP},",
            "estimatedCouponAmount = #{estimatedCouponAmount,jdbcType=BIGINT},",
            "estimatedPostingDate = #{estimatedPostingDate,jdbcType=TIMESTAMP},",
            "confirmedCouponAmount = #{confirmedCouponAmount,jdbcType=BIGINT},",
            "receivedCouponAmount = #{receivedCouponAmount,jdbcType=BIGINT},",
            "dataVersion = #{dataVersion,jdbcType=BIGINT},",
            "createTime = #{createTime,jdbcType=TIMESTAMP},",
            "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
            "confirmRecord = #{confirmRecord,jdbcType=VARCHAR},",
            "toBeConfirmAmount = #{toBeConfirmAmount,jdbcType=BIGINT}",
            "where purchaseCouponLedgerId = #{purchaseCouponLedgerId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(YhglobalCouponLedger record);


    @Select({
            "select",
            "purchaseCouponLedgerId, couponStatus, couponType, couponModel, couponRatio, ",
            "projectId, projectName, supplierId, supplierName, currencyCode, supplierOrderNo, ",
            "purchaseOrderNo, purchaseTime, estimatedCouponAmount, estimatedPostingDate, ",
            "confirmedCouponAmount, receivedCouponAmount, dataVersion, createTime, lastUpdateTime, ",
            "confirmRecord, toBeConfirmAmount, tracelog",
            "from yhglobal_to_receive_coupon_ledger",
            "where projectId = #{projectId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="purchaseCouponLedgerId", property="purchaseCouponLedgerId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="couponStatus", property="couponStatus", jdbcType=JdbcType.TINYINT),
            @Result(column="couponType", property="couponType", jdbcType=JdbcType.TINYINT),
            @Result(column="couponModel", property="couponModel", jdbcType=JdbcType.VARCHAR),
            @Result(column="couponRatio", property="couponRatio", jdbcType=JdbcType.INTEGER),
            @Result(column="projectId", property="projectId", jdbcType=JdbcType.BIGINT),
            @Result(column="projectName", property="projectName", jdbcType=JdbcType.VARCHAR),
            @Result(column="supplierId", property="supplierId", jdbcType=JdbcType.BIGINT),
            @Result(column="supplierName", property="supplierName", jdbcType=JdbcType.VARCHAR),
            @Result(column="currencyCode", property="currencyCode", jdbcType=JdbcType.VARCHAR),
            @Result(column="supplierOrderNo", property="supplierOrderNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="purchaseOrderNo", property="purchaseOrderNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="purchaseTime", property="purchaseTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="estimatedCouponAmount", property="estimatedCouponAmount", jdbcType=JdbcType.BIGINT),
            @Result(column="estimatedPostingDate", property="estimatedPostingDate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="confirmedCouponAmount", property="confirmedCouponAmount", jdbcType=JdbcType.BIGINT),
            @Result(column="receivedCouponAmount", property="receivedCouponAmount", jdbcType=JdbcType.BIGINT),
            @Result(column="dataVersion", property="dataVersion", jdbcType=JdbcType.BIGINT),
            @Result(column="createTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="lastUpdateTime", property="lastUpdateTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="confirmRecord", property="confirmRecord", jdbcType=JdbcType.VARCHAR),
            @Result(column="toBeConfirmAmount", property="toBeConfirmAmount", jdbcType=JdbcType.BIGINT),
            @Result(column="tracelog", property="tracelog", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<YhglobalCouponLedger> selectAllByProjectId(long projectId);

    @SelectProvider(type = YhglobalToReceiveCouponLedgerSqlProvider.class, method = "searchByManyCondition")
    List<YhglobalCouponLedgerItem> searchByManyCondition(@Param("projectId") Long projectId,
                                                         @Param("purchaseOrderNo") String purchaseOrderNo,
                                                         @Param("supplierOrderNo") String supplierOrderNo,
                                                         @Param("flowNo") String flowNo,
                                                         @Param("dateStart") Date dateS,
                                                         @Param("dateEnd") Date dateE,
                                                         @Param("couponStatus") String couponStatus);

    @SelectProvider(type = YhglobalToReceiveCouponLedgerSqlProvider.class, method = "searchMany")
    List<YhglobalCouponLedger> searchMany(@Param("ids") String ids);


}