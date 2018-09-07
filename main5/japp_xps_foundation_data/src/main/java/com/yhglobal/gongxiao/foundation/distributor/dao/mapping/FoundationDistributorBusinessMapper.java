package com.yhglobal.gongxiao.foundation.distributor.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.distributor.dao.provider.DistributorProvider;
import com.yhglobal.gongxiao.foundation.distributor.model.FoundationDistributorBusiness;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface FoundationDistributorBusinessMapper extends BaseMapper {
    @Delete({
            "delete from foundation_distributor_business",
            "where distributorBusinessId = #{distributorBusinessId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long distributorBusinessId);

    @Insert({
            "insert into foundation_distributor_business (distributorBusinessId, distributorBasicId,easCode, ",
            "distributorName,distributorCode, recordStatus, ",
            "projectId, projectName, ",
            "distributorPurchaseCouponDiscount, actualSaleReturn, ",
            "shouldSaleReturn, accordingRealInventorySale, ",
            "settlementType, accountPeriod, ",
            "createdById, createdByName, ",
            "createTime, lastUpdateTime, ",
            "tracelog)",
            "values (#{distributorBusinessId,jdbcType=BIGINT}, #{distributorBasicId,jdbcType=BIGINT},#{easCode}, ",
            "#{distributorName,jdbcType=VARCHAR},#{distributorCode}, #{recordStatus,jdbcType=TINYINT}, ",
            "#{projectId,jdbcType=VARCHAR}, #{projectName,jdbcType=VARCHAR}, ",
            "#{distributorPurchaseCouponDiscount,jdbcType=BIGINT}, #{actualSaleReturn,jdbcType=BIGINT}, ",
            "#{shouldSaleReturn,jdbcType=BIGINT}, #{accordingRealInventorySale,jdbcType=TINYINT}, ",
            "#{settlementType,jdbcType=TINYINT}, #{accountPeriod,jdbcType=TINYINT}, ",
            "#{createdById,jdbcType=BIGINT}, #{createdByName,jdbcType=VARCHAR}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP}, ",
            "#{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(FoundationDistributorBusiness record);

    @Select({
            "select",
            "distributorBusinessId, distributorBasicId, distributorCode,distributorName,easCode, recordStatus, projectId, ",
            "projectName, distributorPurchaseCouponDiscount, actualSaleReturn, shouldSaleReturn, ",
            "accordingRealInventorySale, settlementType, accountPeriod, createdById, createdByName, ",
            "createTime, lastUpdateTime, tracelog",
            "from foundation_distributor_business",
            "where distributorBusinessId = #{distributorBusinessId,jdbcType=BIGINT}"
    })
    FoundationDistributorBusiness getByDistributorBusinessId(Long distributorBusinessId);

    @Select({
            "select",
            "distributorBusinessId, distributorBasicId,distributorCode, distributorName,easCode, recordStatus, projectId, ",
            "projectName, distributorPurchaseCouponDiscount, actualSaleReturn, shouldSaleReturn, ",
            "accordingRealInventorySale, settlementType, accountPeriod, createdById, createdByName, ",
            "createTime, lastUpdateTime, tracelog",
            "from foundation_distributor_business",
            "where distributorBusinessId = #{distributorBusinessId,jdbcType=BIGINT}"
    })
    List<FoundationDistributorBusiness> selectByDistributorBasicId(Long distributorBasicId);

    @Select({
            "select",
            "distributorBusinessId, distributorBasicId,distributorCode, distributorName,easCode, recordStatus, projectId, ",
            "projectName, distributorPurchaseCouponDiscount, actualSaleReturn, shouldSaleReturn, ",
            "accordingRealInventorySale, settlementType, accountPeriod, createdById, createdByName, ",
            "createTime, lastUpdateTime, tracelog",
            "from foundation_distributor_business",
            "where projectId = #{projectId,jdbcType=BIGINT}"
    })
    List<FoundationDistributorBusiness> selectBusinessListByProjectId(Long projectId);

    @Update({
            "update foundation_distributor_business",
            "set distributorBasicId = #{distributorBasicId,jdbcType=BIGINT},",
            "distributorName = #{distributorName,jdbcType=VARCHAR},",
            "recordStatus = #{recordStatus,jdbcType=TINYINT},",
            "projectId = #{projectId,jdbcType=VARCHAR},",
            "projectName = #{projectName,jdbcType=VARCHAR},",
            "distributorPurchaseCouponDiscount = #{distributorPurchaseCouponDiscount,jdbcType=BIGINT},",
            "actualSaleReturn = #{actualSaleReturn,jdbcType=BIGINT},",
            "shouldSaleReturn = #{shouldSaleReturn,jdbcType=BIGINT},",
            "accordingRealInventorySale = #{accordingRealInventorySale,jdbcType=TINYINT},",
            "settlementType = #{settlementType,jdbcType=TINYINT},",
            "accountPeriod = #{accountPeriod,jdbcType=TINYINT},",
            "createdById = #{createdById,jdbcType=BIGINT},",
            "createdByName = #{createdByName,jdbcType=VARCHAR},",
            "createTime = #{createTime,jdbcType=TIMESTAMP},",
            "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
            "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
            "where distributorBusinessId = #{distributorBusinessId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(FoundationDistributorBusiness record);

    @SelectProvider(type = DistributorProvider.class, method = "selectBusinessByCondition")
    List<FoundationDistributorBusiness> selectBusinessByCondition(@Param("projectId") long projectId,
                                                                  @Param("easCode") String easCode,
                                                                  @Param("distributorName") String distributorName,
                                                                  @Param("recordStatus") byte recordStatus);


    @SelectProvider(type = DistributorProvider.class, method = "selectBusinessWithCondition")
    List<FoundationDistributorBusiness> selectBusinessWithCondition(@Param("projectId") long projectId,
                                                                    @Param("distributorBusinessId") long distributorBusinessId,
                                                                    @Param("distributorName") String distributorName,
                                                                    @Param("recordStatus") byte recordStatus);

}