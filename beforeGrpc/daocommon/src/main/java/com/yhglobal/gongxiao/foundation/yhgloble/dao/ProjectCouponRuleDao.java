package com.yhglobal.gongxiao.foundation.yhgloble.dao;

import com.yhglobal.gongxiao.foundation.distributor.model.ProjectCouponRule;
import com.yhglobal.gongxiao.foundation.yhgloble.dao.mapper.ProjectCouponRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 项目返利规则
 *
 * @author: 陈浩
 **/
public class ProjectCouponRuleDao {

    @Autowired
    ProjectCouponRuleMapper couponRuleMapper;

    /**
     * 插入分销商返利规则
     * @param projectCouponRule
     * @return
     */
   public int insertCouponRule(ProjectCouponRule projectCouponRule){
       return couponRuleMapper.insert(projectCouponRule);
   }

    /**
     * 获取分销商返利规则
     * @param projectId
     * @return
     */
   public List<ProjectCouponRule> getCouponRule(String projectId){
       return couponRuleMapper.selectByProjectId(projectId);
   }

}
