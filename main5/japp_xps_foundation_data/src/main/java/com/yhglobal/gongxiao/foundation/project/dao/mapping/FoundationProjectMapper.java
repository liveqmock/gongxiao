package com.yhglobal.gongxiao.foundation.project.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.project.dao.provider.ProjectProvider;
import com.yhglobal.gongxiao.foundation.project.model.FoundationProject;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface FoundationProjectMapper extends BaseMapper {
    @Delete({
        "delete from foundation_project",
        "where projectId = #{projectId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long projectId);

    @Insert({
        "insert into foundation_project (projectId, projectCode, ",
        "projectName, easProjectCode, ",
        "easProjectName, projectTablePrefix,recordStatus, ",
        "company, monthCouponRate, ",
        "quarterCouponRate, annualCouponRate, ",
        "beforeCouponAmount, afterCouponAmount, ",
        "supplierId, supplierName, ",
        "createdById, createdByName, ",
        "createTime, lastUpdateTime, ",
        "tracelog)",
        "values (#{projectId,jdbcType=BIGINT}, #{projectCode,jdbcType=VARCHAR}, ",
        "#{projectName,jdbcType=VARCHAR}, #{easProjectCode,jdbcType=VARCHAR}, ",
        "#{easProjectName,jdbcType=VARCHAR},#{projectTablePrefix,}, #{recordStatus,jdbcType=TINYINT}, ",
        "#{company,jdbcType=VARCHAR}, #{monthCouponRate,jdbcType=BIGINT}, ",
        "#{quarterCouponRate,jdbcType=BIGINT}, #{annualCouponRate,jdbcType=BIGINT}, ",
        "#{beforeCouponAmount,jdbcType=BIGINT}, #{afterCouponAmount,jdbcType=BIGINT}, ",
        "#{supplierId,jdbcType=VARCHAR}, #{supplierName,jdbcType=VARCHAR}, ",
        "#{createdById,jdbcType=BIGINT}, #{createdByName,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP}, ",
        "#{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(FoundationProject record);

    @Select({
        "select",
        "projectId, projectCode, projectName, easProjectCode, wmsProjectCode, wmsProjectName, tmsProjectCode, tmsProjectName, projectTablePrefix,easProjectName, recordStatus, ",
        "company, monthCouponRate, quarterCouponRate, annualCouponRate, beforeCouponAmount, ",
        "afterCouponAmount, supplierId, supplierName, createdById, createdByName, createTime, ",
        "lastUpdateTime, tracelog",
        "from foundation_project",
        "where projectId = #{projectId,jdbcType=BIGINT}"
    })
    FoundationProject getProjectById(Long projectId);

    @Select({
            "select",
            "projectId, projectCode, projectName, easProjectCode, wmsProjectCode, wmsProjectName, tmsProjectCode, tmsProjectName, easProjectName, projectTablePrefix,recordStatus, ",
            "company, monthCouponRate, quarterCouponRate, annualCouponRate, beforeCouponAmount, ",
            "afterCouponAmount, supplierId, supplierName, createdById, createdByName, createTime, ",
            "lastUpdateTime, tracelog",
            "from foundation_project",
            "where projectId = #{projectId,jdbcType=BIGINT}"
    })
    FoundationProject getProjectByName(String  projectName);

   @SelectProvider(type =ProjectProvider.class, method = "selectProjectByCondition")
    List<FoundationProject> selectProjectCondition(@Param("easCode") String easCode,
                                                   @Param("projectName") String projectName,
                                                   @Param("company") String company);

    @Select({
            "select",
            "projectId, projectCode, projectName, easProjectCode, easProjectName, wmsProjectCode, wmsProjectName, tmsProjectCode, tmsProjectName, projectTablePrefix, recordStatus, ",
            "company, monthCouponRate, quarterCouponRate, annualCouponRate, beforeCouponAmount, ",
            "afterCouponAmount, supplierId, supplierName, createdById, createdByName, createTime, ",
            "lastUpdateTime, tracelog",
            "from foundation_project",
    })
    List<FoundationProject> selectAllProject();

    @Update({
        "update foundation_project",
        "set projectCode = #{projectCode,jdbcType=VARCHAR},",
          "projectName = #{projectName,jdbcType=VARCHAR},",
          "easProjectCode = #{easProjectCode,jdbcType=VARCHAR},",
          "easProjectName = #{easProjectName,jdbcType=VARCHAR},",
          "recordStatus = #{recordStatus,jdbcType=TINYINT},",
          "company = #{company,jdbcType=VARCHAR},",
          "monthCouponRate = #{monthCouponRate,jdbcType=BIGINT},",
          "quarterCouponRate = #{quarterCouponRate,jdbcType=BIGINT},",
          "annualCouponRate = #{annualCouponRate,jdbcType=BIGINT},",
          "beforeCouponAmount = #{beforeCouponAmount,jdbcType=BIGINT},",
          "afterCouponAmount = #{afterCouponAmount,jdbcType=BIGINT},",
          "supplierId = #{supplierId,jdbcType=VARCHAR},",
          "supplierName = #{supplierName,jdbcType=VARCHAR},",
          "createdById = #{createdById,jdbcType=BIGINT},",
          "createdByName = #{createdByName,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
        "where projectId = #{projectId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(FoundationProject record);
}