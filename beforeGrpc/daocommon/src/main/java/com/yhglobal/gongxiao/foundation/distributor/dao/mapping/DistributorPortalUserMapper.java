package com.yhglobal.gongxiao.foundation.distributor.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.portal.user.model.DistributorPortalUser;
import com.yhglobal.gongxiao.payment.dao.mapping.DistributorCouponFlowSqlProvider;
import com.yhglobal.gongxiao.payment.dao.mapping.DistributorPotalUserSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface DistributorPortalUserMapper extends BaseMapper {

    @Insert({
            "insert into distributor_portal_user (userId, userName, ",
            "passwd, userStatus, projectId, projectName, distributorId, ",
            "distributorName, phoneNumber, isAdmin, ",
            "roleInfo, tracelog, createdById, ",
            "createdByName, createTime)",
            "values (#{userId}, #{userName}, ",
            "#{passwd}, #{userStatus}, #{distributorId}, #{distributorName}, ",
            "#{phoneNumber}, #{isAdmin}, ",
            "#{roleInfo}, #{tracelog}, #{createdById}, ",
            "#{createdByName}, #{createTime})",
    })
    int createDistributorPortalUser(DistributorPortalUser record);

    @Select({
            "select",
            "userId, userName, passwd, userStatus, projectId, projectName, distributorId, ",
            "distributorName, phoneNumber, isAdmin, roleInfo, tracelog, createdById, ",
            "createdByName, createTime",
            "from distributor_portal_user",
            "where userId = #{userId}"
    })
    DistributorPortalUser getDistributorPortalUserById(String userId);

    @Select({
            "select",
            "userId, userName, passwd, userStatus, projectId, projectName, distributorId, ",
            "distributorName, phoneNumber, isAdmin, roleInfo, tracelog, createdById, ",
            "createdByName, createTime",
            "from distributor_portal_user",
            "where distributorId = #{distributorId}"
    })
    DistributorPortalUser getByDistributorId(String distributorId);

    @Select({
            "select",
            "userId, userName, passwd, userStatus, projectId, projectName, distributorId, ",
            "distributorName, phoneNumber, isAdmin, roleInfo, tracelog, createdById, ",
            "createdByName, createTime",
            "from distributor_portal_user",
            "where userName = #{userName}"
    })
    DistributorPortalUser getDistributorPortalUserByName(String userName);


    @Select({
            "select",
            "userId, userName, passwd, userStatus, projectId, projectName, distributorId, ",
            "distributorName, phoneNumber, isAdmin, roleInfo, tracelog, createdById, ",
            "createdByName, createTime",
            "from distributor_portal_user",
            "where projectId = #{projectId} and distributorId=#{distributorId}"
    })
    List<DistributorPortalUser> selectDistributorPortalUserByProjectIdAndDistributorId(@Param("projectId") long projectId, @Param("distributorId") long distributorId);


    @Select({
            "select",
            "userId, userName, passwd, userStatus, projectId, projectName, distributorId, ",
            "distributorName, phoneNumber, isAdmin, roleInfo, tracelog, createdById, ",
            "createdByName, createTime",
            "from distributor_portal_user"
    })
    List<DistributorPortalUser> selectAll();

    // 根据项目ID  客户账号  客户名称的模糊查询
    @SelectProvider(type = DistributorPotalUserSqlProvider.class, method = "selectAllByProjectIdAndUserIdAndUserName")
    List<DistributorPortalUser> selectAllByProjectIdAndUserIdAndUserName(@Param("projectId") Long projectId,
                                                                         @Param("distributorId") String distributorId,
                                                                         @Param("distributorName") String distributorName);

}