package com.yhglobal.gongxiao.foundation.distributor.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.distributor.dao.provider.DistributorProvider;
import com.yhglobal.gongxiao.foundation.distributor.model.FoundationDistributorUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface FoundationDistributorUserMapper extends BaseMapper {
    @Delete({
        "delete from foundation_distributor_user",
        "where distributorUserId = #{distributorUserId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long distributorUserId);

    @Insert({
        "insert into foundation_distributor_user (distributorUserId, distributorBusinessId, ",
        "distributorName,projectId,projectName, account, ",
        "password, priority, ",
        "roleInfo, createdById, ",
        "createdByName, createTime, ",
        "lastUpdateTime, tracelog)",
        "values (#{distributorUserId,jdbcType=BIGINT}, #{distributorBusinessId,jdbcType=BIGINT}, ",
        "#{distributorName,jdbcType=VARCHAR},#{projectId},#{projectName} #{account,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR}, #{priority,jdbcType=TINYINT}, ",
        "#{roleInfo,jdbcType=VARCHAR}, #{createdById,jdbcType=BIGINT}, ",
        "#{createdByName,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{lastUpdateTime,jdbcType=TIMESTAMP}, #{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(FoundationDistributorUser record);

    @Select({
        "select",
        "distributorUserId, distributorBusinessId, distributorName, projectId,projectName,account, password, ",
        "priority, roleInfo, createdById, createdByName, createTime, lastUpdateTime, ",
        "tracelog",
        "from foundation_distributor_user",
        "where distributorUserId = #{distributorUserId,jdbcType=BIGINT}"
    })
    FoundationDistributorUser selectByPrimaryKey(Long distributorUserId);

    @Select({
            "select",
            "distributorUserId, distributorBusinessId, distributorName, projectId,projectName,account, password, ",
            "priority, roleInfo, createdById, createdByName, createTime, lastUpdateTime, ",
            "tracelog",
            "from foundation_distributor_user",
            "where distributorUserId = #{distributorUserId,jdbcType=BIGINT}"
    })
    FoundationDistributorUser getUserByUserId(Long distributorUserId);

    @Select({
            "select",
            "distributorUserId, distributorBusinessId, distributorName,projectId,projectName, account, password, ",
            "priority, roleInfo, createdById, createdByName, createTime, lastUpdateTime, ",
            "tracelog",
            "from foundation_distributor_user",
            "where distributorName = #{distributorName}"
    })
    FoundationDistributorUser getUserByName(String distributorName);

    @Select({
            "select",
            "distributorUserId, distributorBusinessId, distributorName,projectId,projectName, account, password, ",
            "priority, roleInfo, createdById, createdByName, createTime, lastUpdateTime, ",
            "tracelog",
            "from foundation_distributor_user",
            "where account = #{account} and projectId = #{projectId}"
    })
    FoundationDistributorUser getUserByAccount(@Param("projectId") long projectId,
                                               @Param("account")String account);

    @Select({
            "select",
            "distributorUserId, distributorBusinessId, distributorName,projectId,projectName, account, password, ",
            "priority, roleInfo, createdById, createdByName, createTime, lastUpdateTime, ",
            "tracelog",
            "from foundation_distributor_user",
            "where distributorBusinessId = #{distributorBusinessId}"
    })
    List<FoundationDistributorUser> selectUserByBusinessId(long distributorBusinessId);


    @Select({
            "select",
            "distributorUserId, distributorBusinessId, distributorName,projectId,projectName, account, password, ",
            "priority, roleInfo, createdById, createdByName, createTime, lastUpdateTime, ",
            "tracelog",
            "from foundation_distributor_user",
    })
    List<FoundationDistributorUser> selectAllUser();

    @Select({
            "select",
            "distributorUserId, distributorBusinessId, distributorName,projectId,projectName, account, password, ",
            "priority, roleInfo, createdById, createdByName, createTime, lastUpdateTime, ",
            "tracelog",
            "from foundation_distributor_user where account = #{account}",
    })
    List<FoundationDistributorUser> selectUserListByAccount(String account);

    @SelectProvider(type = DistributorProvider.class,method = "selectUserByCondition")
    List<FoundationDistributorUser> selectUserByCondition(@Param("projectId") long projectId,
                                                          @Param("account")String account,
                                                          @Param("distributorName")String distributorName);



    @Update({
        "update foundation_distributor_user",
        "set distributorBusinessId = #{distributorBusinessId,jdbcType=BIGINT},",
          "distributorName = #{distributorName,jdbcType=VARCHAR},",
          "account = #{account,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "priority = #{priority,jdbcType=TINYINT},",
          "roleInfo = #{roleInfo,jdbcType=VARCHAR},",
          "createdById = #{createdById,jdbcType=BIGINT},",
          "createdByName = #{createdByName,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
        "where distributorUserId = #{distributorUserId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(FoundationDistributorUser record);

}