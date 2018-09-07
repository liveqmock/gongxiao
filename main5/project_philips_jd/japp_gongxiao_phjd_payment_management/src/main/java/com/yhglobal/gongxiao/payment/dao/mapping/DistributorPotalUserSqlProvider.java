package com.yhglobal.gongxiao.payment.dao.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author 王帅
 */
public class DistributorPotalUserSqlProvider {

    public String selectAllByProjectIdAndUserIdAndUserName(@Param("projectId") Long projectId,
                                           @Param("distributorId") String distributorId,
                                           @Param("distributorName") String distributorName) {
        return new SQL() {{
            SELECT(
                    "userId, userName, passwd, userStatus, projectId, projectName, distributorId, "+
                    "distributorName, phoneNumber, isAdmin, roleInfo, tracelog, createdById, "+
                    "createdByName, createTime");
            FROM("distributor_portal_user");
            if (projectId != null) {
                WHERE("projectId = #{projectId} ");
            }
            if (distributorId != null && !"".equals(distributorId)) {
                WHERE("distributorId =  #{distributorId}");
            }
            if (distributorName != null && !"".equals(distributorName)) {
                WHERE("distributorName like '%' #{distributorName} '%'");
            }
        }}.toString();
    }
}
