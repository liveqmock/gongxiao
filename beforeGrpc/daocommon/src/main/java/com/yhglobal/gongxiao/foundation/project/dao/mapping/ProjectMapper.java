package com.yhglobal.gongxiao.foundation.project.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.project.model.Project;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ProjectMapper extends BaseMapper {
    @Delete({
            "delete from t_project",
            "where id = #{id}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
            "insert into t_project (projectId, projectName, ",
            "easProjectCode, easProjectName, ",
            "OrgCode, OrgName, ",
            "status, brandId, ",
            "brandName, supplierId, ",
            "supplierName, distributionInfo, ",
            "prepaidUseReferRate, couponUseReferRate, ",
            "monthlyCouponGenerateRate, quarterlyCouponGenerateRate, ",
            "annualCouponGenerateRate, createdById, ",
            "createdByName, createTime, ",
            "lastUpdateTime, cashRebateRateBefore",
            "cashRebateRateAfter, tracelog)",
            "values (#{projectId,jdbcType=INTEGER}, #{projectName,jdbcType=VARCHAR}, ",
            "#{easProjectCode,jdbcType=VARCHAR}, #{easProjectName,jdbcType=VARCHAR}, ",
            "#{orgCode,jdbcType=VARCHAR}, #{orgName,jdbcType=VARCHAR}, ",
            "#{status,jdbcType=TINYINT}, #{brandId,jdbcType=INTEGER}, ",
            "#{brandName,jdbcType=VARCHAR}, #{supplierId,jdbcType=INTEGER}, ",
            "#{supplierName,jdbcType=VARCHAR}, #{distributionInfo,jdbcType=VARCHAR}, ",
            "#{prepaidUseReferRate,jdbcType=INTEGER}, #{couponUseReferRate,jdbcType=INTEGER}, ",
            "#{monthlyCouponGenerateRate,jdbcType=INTEGER}, #{quarterlyCouponGenerateRate,jdbcType=INTEGER}, ",
            "#{annualCouponGenerateRate,jdbcType=INTEGER}, #{createdById,jdbcType=INTEGER}, ",
            "#{createdByName,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
            "#{lastUpdateTime,jdbcType=TIMESTAMP}, #{cashRebateRateBefore, jdbcType=INTEGER}, ",
            "#{cashRebateRateAfter, jdbcType=INTEGER}, #{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(Project record);


    @Select({
            "select",
            "projectId, projectName, easProjectCode, easProjectName, OrgCode, OrgName, status, ",
            "brandId, brandName, supplierId, supplierName, distributionInfo, prepaidUseReferRate, ",
            "couponUseReferRate, monthlyCouponGenerateRate, quarterlyCouponGenerateRate, ",
            "annualCouponGenerateRate, createdById, createdByName, createTime, lastUpdateTime, cashRebateRateBefore,",
            "cashRebateRateAfter, tracelog",
            "from t_project",
            "where projectId = #{projectId}"
    })
    Project selectByProjectId(@Param("projectId") Long projectId);

    @Select({
            "select",
            "projectId, projectName, status, brandId, brandName, supplierId, supplierName, ",
            "distributionInfo, prepaidUseReferRate, couponUseReferRate, monthlyCouponGenerateRate, ",
            "quarterlyCouponGenerateRate, annualCouponGenerateRate, createdById, createdByName, ",
            "createTime, lastUpdateTime,cashRebateRateBefore, cashRebateRateAfter, tracelog",
            "from t_project"
    })
    List<Project> selectAll();

    @Select({
            "select",
            "projectId, projectName, easProjectCode, easProjectName, OrgCode, OrgName, status, ",
            "brandId, brandName, supplierId, supplierName, distributionInfo, prepaidUseReferRate, ",
            "couponUseReferRate, monthlyCouponGenerateRate, quarterlyCouponGenerateRate, ",
            "annualCouponGenerateRate, createdById, createdByName, createTime, lastUpdateTime, ",
            "cashRebateRateBefore, cashRebateRateAfter, tracelog",
            "from t_project",
            "where easProjectName = #{easProjectName}"

    })
    Project getByEASProjectName( @Param("easProjectName") String easProjectName);


    @Update({
            "update t_project",
            "set projectName = #{projectName,jdbcType=VARCHAR},",
            "status = #{status,jdbcType=TINYINT},",
            "brandId = #{brandId,jdbcType=INTEGER},",
            "brandName = #{brandName,jdbcType=VARCHAR},",
            "supplierId = #{supplierId,jdbcType=INTEGER},",
            "supplierName = #{supplierName,jdbcType=VARCHAR},",
            "distributionInfo = #{distributionInfo,jdbcType=VARCHAR},",
            "prepaidUseReferRate = #{prepaidUseReferRate,jdbcType=INTEGER},",
            "couponUseReferRate = #{couponUseReferRate,jdbcType=INTEGER},",
            "monthlyCouponGenerateRate = #{monthlyCouponGenerateRate,jdbcType=INTEGER},",
            "quarterlyCouponGenerateRate = #{quarterlyCouponGenerateRate,jdbcType=INTEGER},",
            "annualCouponGenerateRate = #{annualCouponGenerateRate,jdbcType=INTEGER},",
            "createdById = #{createdById,jdbcType=INTEGER},",
            "createdByName = #{createdByName,jdbcType=VARCHAR},",
            "createTime = #{createTime,jdbcType=TIMESTAMP},",
            "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
            "cashRebateRateBefore = #{cashRebateRateBefore,jdbcType=INTEGER},",
            "cashRebateRateAfter = #{cashRebateRateAfter,jdbcType=INTEGER},",
            "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
            "where id = #{id}"
    })
    int updateByPrimaryKey(Project record);

    @Update({
            "update t_project",
            "set easProjectName = #{easProjectName,jdbcType=VARCHAR},",
            "easProjectCode = #{easProjectCode,jdbcType=VARCHAR},",
            "orgCode = #{orgCode,jdbcType=VARCHAR},",
            "orgName = #{orgName,jdbcType=VARCHAR}",
            "where projectId = #{projectId}"
    })
    int updateProjectEasInfo( @Param("projectId")int projectId,
                              @Param("easProjectName")String easProjectName,
                              @Param("easProjectCode")String easProjectCode,
                              @Param("orgCode")String orgCode,
                              @Param("orgName")String orgName);

    @Select({
            "select",
            "supplierName",
            "from t_project where projectId = #{projectId}"
    })
    String selectSupplierNameById(@Param("projectId")String projectId);

    @Select({
            "select",
            "easProjectCode",
            "from t_project where projectId = #{projectId}"
    })
    String getProjectCodeById(@Param("projectId")String projectId);
}