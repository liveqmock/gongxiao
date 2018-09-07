package com.yhglobal.gongxiao.coupon.dao.mapper.prepaidPaymentOrderMapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;

import com.yhglobal.gongxiao.coupon.model.PrepaidPaymentOrder.YhglobalPrepaidInfoItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface YhglobalPrepaidInfoItemMapper extends BaseMapper {
    @Insert({
        "insert into ${tablePrefix}_yhglobal_prepaid_info_item ( infoId, ",
        "costType, currencyCode, ",
        "exchangeRate, applicationAmount, ",
        "invoiceType, taxPoint, ",
        "standardCurrencyAmount, taxSubsidy, ",
        "isTaxSubsidy, reason, ",
        "dataVersion, createdById, ",
        "createdByName, createTime, ",
        "lastUpdateTime)",
        "values ( #{infoId,jdbcType=INTEGER}, ",
        "#{costType,jdbcType=TINYINT}, #{currencyCode,jdbcType=VARCHAR}, ",
        "#{exchangeRate,jdbcType=BIGINT}, #{applicationAmount,jdbcType=BIGINT}, ",
        "#{invoiceType,jdbcType=TINYINT}, #{taxPoint,jdbcType=BIGINT}, ",
        "#{standardCurrencyAmount,jdbcType=BIGINT}, #{taxSubsidy,jdbcType=BIGINT}, ",
        "#{isTaxSubsidy,jdbcType=TINYINT}, #{reason,jdbcType=VARCHAR}, ",
        "#{dataVersion,jdbcType=BIGINT}, #{createdById,jdbcType=BIGINT}, ",
        "#{createdByName,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{lastUpdateTime,jdbcType=TIMESTAMP})"
    })
    int insert(YhglobalPrepaidInfoItem record);



    @Select({
        "select",
        "rowId, infoId, costType, currencyCode, exchangeRate, applicationAmount, invoiceType, ",
        "taxPoint, standardCurrencyAmount, taxSubsidy, isTaxSubsidy, reason, dataVersion, ",
        "createdById, createdByName, createTime, lastUpdateTime",
        "from ${tablePrefix}_yhglobal_prepaid_info_item",
        "where rowId = #{rowId,jdbcType=INTEGER}"
    })
    YhglobalPrepaidInfoItem selectByPrimaryKey(@Param("tablePrefix")String tablePrefix,@Param("rowId") Integer rowId);

    @Select({
            "select",
            "rowId, infoId, costType, currencyCode, exchangeRate, applicationAmount, invoiceType, ",
            "taxPoint, standardCurrencyAmount, taxSubsidy, isTaxSubsidy, reason, dataVersion, ",
            "createdById, createdByName, createTime, lastUpdateTime",
            "from ${tablePrefix}_yhglobal_prepaid_info_item",
            "where infoId = #{infoId,jdbcType=INTEGER}"
    })
    List<YhglobalPrepaidInfoItem> selectByInfoId(@Param("tablePrefix")String tablePrefix,@Param("infoId") Integer infoId);

    @Update({
        "update ${tablePrefix}_yhglobal_prepaid_info_item",
        "set infoId = #{infoId,jdbcType=INTEGER},",
          "costType = #{costType,jdbcType=TINYINT},",
          "currencyCode = #{currencyCode,jdbcType=VARCHAR},",
          "exchangeRate = #{exchangeRate,jdbcType=BIGINT},",
          "applicationAmount = #{applicationAmount,jdbcType=BIGINT},",
          "invoiceType = #{invoiceType,jdbcType=TINYINT},",
          "taxPoint = #{taxPoint,jdbcType=BIGINT},",
          "standardCurrencyAmount = #{standardCurrencyAmount,jdbcType=BIGINT},",
          "taxSubsidy = #{taxSubsidy,jdbcType=BIGINT},",
          "isTaxSubsidy = #{isTaxSubsidy,jdbcType=TINYINT},",
          "reason = #{reason,jdbcType=VARCHAR},",
          "dataVersion = #{dataVersion,jdbcType=BIGINT},",
          "createdById = #{createdById,jdbcType=BIGINT},",
          "createdByName = #{createdByName,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
        "where rowId = #{rowId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(@Param("tablePrefix")String tablePrefix, YhglobalPrepaidInfoItem record);
}