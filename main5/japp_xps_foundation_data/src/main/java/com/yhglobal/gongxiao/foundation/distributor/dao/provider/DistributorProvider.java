package com.yhglobal.gongxiao.foundation.distributor.dao.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author 王帅
 */
public class DistributorProvider {

    public String selectBusinessByCondition(@Param("projectId") long projectId,
                                            @Param("easCode") String easCode,
                                            @Param("distributorName") String distributorName,
                                            @Param("recordStatus") byte recordStatus) {
        return new SQL() {{
            SELECT(
                    "distributorBusinessId, distributorBasicId, distributorCode,distributorName,easCode, recordStatus, projectId",
                    "projectName, distributorPurchaseCouponDiscount, actualSaleReturn, shouldSaleReturn ",
                    "accordingRealInventorySale, settlementType, accountPeriod, createdById, createdByName ",
                    "createTime, lastUpdateTime, tracelog");
            FROM("foundation_distributor_business");
            if (projectId != 0) {
                WHERE("projectId=#{projectId}");
            }
            if (recordStatus != 0) {
                WHERE("recordStatus =  #{recordStatus}");
            }
            if (distributorName != null && !"".equals(distributorName)) {
                WHERE("distributorName like concat('%', #{distributorName},'%')");
            }
            if (easCode != null && !"".equals(easCode)) {
                WHERE("easCode like concat('%', #{easCode},'%')");
            }
        }}.toString();
    }

    public String selectUserByCondition(@Param("projectId") long projectId,
                                        @Param("account") String account,
                                        @Param("distributorName") String distributorName) {
        return new SQL() {{
            SELECT(
                    "distributorUserId, distributorBusinessId, distributorName,projectId,projectName, account, password ",
                    "priority, roleInfo, createdById, createdByName, createTime, lastUpdateTime ",
                    "tracelog");
            FROM("foundation_distributor_user");
            if (projectId != 0) {
                WHERE("projectId=#{projectId}");
            }
            if (distributorName != null && !"".equals(distributorName)) {
                WHERE("distributorName like concat('%', #{distributorName},'%')");
            }
            if (account != null && !"".equals(account)) {
                WHERE("account like concat('%', #{account},'%')");
            }
        }}.toString();

    }

    public String selectBusinessWithCondition(@Param("projectId") long projectId,
                                              @Param("distributorBusinessId") long distributorBusinessId,
                                              @Param("distributorName") String distributorName,
                                              @Param("recordStatus") byte recordStatus){
        return new SQL() {{
            SELECT(
                    "distributorBusinessId, distributorBasicId,distributorCode, distributorName,easCode, recordStatus, projectId",
                    "projectName, distributorPurchaseCouponDiscount, actualSaleReturn, shouldSaleReturn ",
                    "accordingRealInventorySale, settlementType, accountPeriod, createdById, createdByName ",
                    "createTime, lastUpdateTime, tracelog");
            FROM("foundation_distributor_business");
            if (projectId != 0) {
                WHERE("projectId=#{projectId}");
            }
            if (distributorBusinessId != 0) {
                WHERE("distributorBusinessId=#{distributorBusinessId}");
            }
            if (distributorName != null && !"".equals(distributorName)) {
                WHERE("distributorName like concat('%', #{distributorName},'%')");
            }
            if (recordStatus != 0) {
                WHERE("recordStatus=#{recordStatus}");
            }
        }}.toString();

    }

}
