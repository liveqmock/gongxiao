package com.yhglobal.gongxiao.foundation.project.dao.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public class ProjectProvider {

    public String selectProjectByCondition(@Param("easCode") String easCode,
                                           @Param("projectName") String projectName,
                                           @Param("company") String company) {
        return new SQL() {{
            SELECT("projectId, projectCode, projectName, easProjectCode, easProjectName, wmsProjectCode, wmsProjectName, tmsProjectCode, tmsProjectName, projectTablePrefix,recordStatus ",
                    "company, monthCouponRate, quarterCouponRate, annualCouponRate, beforeCouponAmount ",
                    "afterCouponAmount, supplierId, supplierName, createdById, createdByName, createTime",
                    "lastUpdateTime, tracelog");
            FROM("foundation_project");
            if (easCode !=null && !"".equals(easCode)) {
                WHERE("easProjectCode=#{easCode}");
            }
            if (projectName != null && !"".equals(projectName)) {
                WHERE("projectName like concat('%', #{projectName},'%')");
            }
            if (company != null && !"".equals(company)) {
                WHERE("company like concat('%', #{company},'%')");
            }
        }}.toString();
    }
}
