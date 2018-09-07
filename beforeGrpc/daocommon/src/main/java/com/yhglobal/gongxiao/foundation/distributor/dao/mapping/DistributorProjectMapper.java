package com.yhglobal.gongxiao.foundation.distributor.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.distributor.model.DistributorProject;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface DistributorProjectMapper extends BaseMapper {
    @Delete({
        "delete from distributor_project",
        "where distributorProjectId = #{distributorProjectId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long distributorProjectId);

    @Insert({
        "insert into distributor_project (distributorProjectId, distributorBasicId, ",
        "recordStatus, projectId, ",
        "projectName, distibutorPurchaseCouponDiscount, ",
        "actualSaleReturn, shouldSaleReturn, ",
        "accordingRealInventorySale, settlementType, ",
        "accountPeriod, createdById, ",
        "createdByName, createTime, ",
        "lastUpdateTime, tracelog)",
        "values (#{distributorProjectId,jdbcType=BIGINT}, #{distributorBasicId,jdbcType=BIGINT}, ",
        "#{recordStatus,jdbcType=TINYINT}, #{projectId,jdbcType=VARCHAR}, ",
        "#{projectName,jdbcType=VARCHAR}, #{distibutorPurchaseCouponDiscount,jdbcType=BIGINT}, ",
        "#{actualSaleReturn,jdbcType=BIGINT}, #{shouldSaleReturn,jdbcType=BIGINT}, ",
        "#{accordingRealInventorySale,jdbcType=TINYINT}, #{settlementType,jdbcType=TINYINT}, ",
        "#{accountPeriod,jdbcType=TINYINT}, #{createdById,jdbcType=BIGINT}, ",
        "#{createdByName,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{lastUpdateTime,jdbcType=TIMESTAMP}, #{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(DistributorProject record);

    @Select({
        "select",
        "distributorProjectId, distributorBasicId, recordStatus, projectId, projectName, distibutorPurchaseCouponDiscount, ",
        "actualSaleReturn, shouldSaleReturn, accordingRealInventorySale, settlementType, ",
        "accountPeriod, createdById, createdByName, createTime, lastUpdateTime, tracelog",
        "from distributor_project",
        "where distributorProjectId = #{distributorProjectId,jdbcType=BIGINT}"
    })
    DistributorProject getDistributorProjectById(Long distributorProjectId);

    @Update({
        "update distributor_project",
        "set distributorBasicId = #{distributorBasicId,jdbcType=BIGINT},",
          "recordStatus = #{recordStatus,jdbcType=TINYINT},",
          "projectId = #{projectId,jdbcType=VARCHAR},",
          "projectName = #{projectName,jdbcType=VARCHAR},",
          "distibutorPurchaseCouponDiscount = #{distibutorPurchaseCouponDiscount,jdbcType=BIGINT},",
          "actualSaleReturn = #{actualSaleReturn,jdbcType=BIGINT},",
          "shouldSaleReturn = #{shouldSaleReturn,jdbcType=BIGINT},",
          "accordingRealInventorySale = #{accordingRealInventorySale,jdbcType=TINYINT},",
          "settlementType = #{settlementType,jdbcType=TINYINT},",
          "accountPeriod = #{accountPeriod,jdbcType=TINYINT},",
          "createdById = #{createdById,jdbcType=BIGINT},",
          "createdByName = #{createdByName,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
        "where distributorProjectId = #{distributorProjectId,jdbcType=BIGINT}"
    })
    int updateDistributor(DistributorProject record);
}