package com.yhglobal.gongxiao.coupon.dao.mapper.prepaidPaymentOrderMapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;

import com.yhglobal.gongxiao.coupon.dao.provider.YhglobalPrepaidProvider;
import com.yhglobal.gongxiao.coupon.model.PrepaidPaymentOrder.YhglobalPrepaidInfo;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 15:25 2018/4/27
 */
public interface YhglobalPrepaidInfoMapper  extends BaseMapper {

    @Insert({
        "insert into ${tablePrefix}_yhglobal_prepaid_info (prepaidNo,supplierId, ",
        "projectId, projectName, ",
        "supplierName, payer, ",
        "receivables, settlementNo,settlementType, ",
        "dateBusiness, taxNo, ",
        "contactInfo, provinceId, ",
        "provinceName, cityId, ",
        "cityName, districtId, ",
        "districtName, streetAddress, ",
        "accountCNY, bankNameCNY,standardCurrencyAmount, remark,",
        "dataVersion, createdById, ",
        "createdByName, createTime, ",
        "lastUpdateTime, tracelog)",
        "values (#{prepaidNo},#{supplierId,jdbcType=INTEGER}, ",
         "#{projectId}, #{projectName,jdbcType=VARCHAR}, ",
        "#{supplierName,jdbcType=VARCHAR}, #{payer,jdbcType=VARCHAR}, ",
        "#{receivables,jdbcType=VARCHAR}, #{settlementNo,jdbcType=VARCHAR}, #{settlementType,jdbcType=INTEGER },",
        "#{dateBusiness,jdbcType=TIMESTAMP}, #{taxNo,jdbcType=VARCHAR}, ",
        "#{contactInfo,jdbcType=VARCHAR}, #{provinceId,jdbcType=INTEGER}, ",
        "#{provinceName,jdbcType=VARCHAR}, #{cityId,jdbcType=INTEGER}, ",
        "#{cityName,jdbcType=VARCHAR}, #{districtId,jdbcType=INTEGER}, ",
        "#{districtName,jdbcType=VARCHAR}, #{streetAddress,jdbcType=VARCHAR}, ",
        "#{accountCNY,jdbcType=VARCHAR}, #{bankNameCNY,jdbcType=VARCHAR},#{standardCurrencyAmount},#{remark}, ",
        "#{dataVersion,jdbcType=BIGINT}, #{createdById,jdbcType=BIGINT}, ",
        "#{createdByName,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{lastUpdateTime,jdbcType=TIMESTAMP}, #{tracelog,jdbcType=LONGVARCHAR})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "prepaidId")
    int insert(YhglobalPrepaidInfo record);

    @Select({
        "select",
        "prepaidNo,projectId,projectName,prepaidId, supplierId, supplierName, payer, receivables, settlementNo,settlementType, dateBusiness, ",
        "taxNo, contactInfo, provinceId, provinceName, cityId, cityName, districtId, ",
        "districtName, streetAddress, accountCNY, bankNameCNY,standardCurrencyAmount,remark, dataVersion, createdById, ",
        "createdByName, createTime, lastUpdateTime, tracelog",
        "from ${tablePrefix}_yhglobal_prepaid_info",
        "where prepaidId = #{prepaidId,jdbcType=INTEGER}"
    })
    YhglobalPrepaidInfo selectByPrimaryKey(@Param("tablePrefix")String tablePrefix,@Param("prepaidId") Integer prepaidId);

    @SelectProvider(type = YhglobalPrepaidProvider.class, method = "searchPrepaidInfo")
    List<YhglobalPrepaidInfo> selectAll(@Param("tablePrefix")String tablePrefix,
                                        @Param("projectId") Long projectId,
                                        @Param("prepaidNo") String prepaidNo,
                                        @Param("receivables") String receivables,
                                        @Param("dateStart") Date dateStart,
                                        @Param("dateEnd") Date dateEnd);

    @Update({
        "update ${tablePrefix}_yhglobal_prepaid_info",
        "set supplierId = #{supplierId,jdbcType=INTEGER},",
          "supplierName = #{supplierName,jdbcType=VARCHAR},",
          "payer = #{payer,jdbcType=VARCHAR},",
          "receivables = #{receivables,jdbcType=VARCHAR},",
          "settlementNo = #{settlementNo,jdbcType=VARCHAR},settlementType = #{settlementType,jdbcType=INTEGER }, ",
          "dateBusiness = #{dateBusiness,jdbcType=TIMESTAMP},",
          "taxNo = #{taxNo,jdbcType=VARCHAR},",
          "contactInfo = #{contactInfo,jdbcType=VARCHAR},",
          "provinceId = #{provinceId,jdbcType=INTEGER},",
          "provinceName = #{provinceName,jdbcType=VARCHAR},",
          "cityId = #{cityId,jdbcType=INTEGER},",
          "cityName = #{cityName,jdbcType=VARCHAR},",
          "districtId = #{districtId,jdbcType=INTEGER},",
          "districtName = #{districtName,jdbcType=VARCHAR},",
          "streetAddress = #{streetAddress,jdbcType=VARCHAR},",
          "accountCNY = #{accountCNY,jdbcType=VARCHAR},",
          "bankNameCNY = #{bankNameCNY,jdbcType=VARCHAR},",
          "standardCurrencyAmount = #{standardCurrencyAmount,jdbcType=BIGINT},",
          "dataVersion = #{dataVersion,jdbcType=BIGINT},",
          "createdById = #{createdById,jdbcType=BIGINT},",
          "createdByName = #{createdByName,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
        "where prepaidId = #{prepaidId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(@Param("tablePrefix")String tablePrefix,YhglobalPrepaidInfo record);

}