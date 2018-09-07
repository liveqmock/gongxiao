package com.yhglobal.gongxiao.foundation.yhgloble.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.distributor.model.ProjectCouponRule;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ProjectCouponRuleMapper  extends BaseMapper {
    @Delete({
        "delete from t_project_coupon_rule",
        "where ruleId = #{ruleId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer ruleId);

    @Insert({
        "insert into t_project_coupon_rule (ruleId, status, ",
        "projectId, projectName, ",
        "couponType, couponCategoryName, ",
        "couponName, couponRatio, ",
        "startDate, endDate, createTime)",
        "values (#{ruleId,jdbcType=INTEGER}, #{status,jdbcType=TINYINT}, ",
        "#{projectId,jdbcType=VARCHAR}, #{projectName,jdbcType=VARCHAR}, ",
        "#{couponType,jdbcType=TINYINT}, #{couponCategoryName,jdbcType=VARCHAR}, ",
        "#{couponName,jdbcType=VARCHAR}, #{couponRatio,jdbcType=INTEGER}, ",
        "#{startDate,jdbcType=DATE}, #{endDate,jdbcType=DATE}, #{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(ProjectCouponRule record);

    @Select({
        "select",
        "ruleId, status, projectId, projectName, couponType, couponCategoryName, couponName, ",
        "couponRatio, startDate, endDate, createTime",
        "from t_project_coupon_rule",
        "where ruleId = #{ruleId,jdbcType=INTEGER}"
    })

    ProjectCouponRule selectByPrimaryKey(Integer ruleId);

    @Select({
            "select",
            "ruleId, status, projectId, projectName, couponType, couponCategoryName, couponName, ",
            "couponRatio, startDate, endDate, createTime",
            "from t_project_coupon_rule",
            "where projectId = #{projectId,jdbcType=INTEGER}"
    })
    List<ProjectCouponRule> selectByProjectId(@Param("projectId") String projectId);

    @Update({
        "update t_project_coupon_rule",
        "set status = #{status,jdbcType=TINYINT},",
          "projectId = #{projectId,jdbcType=VARCHAR},",
          "projectName = #{projectName,jdbcType=VARCHAR},",
          "couponType = #{couponType,jdbcType=TINYINT},",
          "couponCategoryName = #{couponCategoryName,jdbcType=VARCHAR},",
          "couponName = #{couponName,jdbcType=VARCHAR},",
          "couponRatio = #{couponRatio,jdbcType=INTEGER},",
          "startDate = #{startDate,jdbcType=DATE},",
          "endDate = #{endDate,jdbcType=DATE},",
          "createTime = #{createTime,jdbcType=TIMESTAMP}",
        "where ruleId = #{ruleId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ProjectCouponRule record);
}