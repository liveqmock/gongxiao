package com.yhglobal.gongxiao.foundation.distributor.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.distributor.model.DistributorUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface DistributorUserMapper extends BaseMapper {
    @Delete({
        "delete from distributor_user",
        "where distributorUserId = #{distributorUserId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long distributorUserId);

    @Insert({
        "insert into distributor_user (distributorUserId, distributorProjectId, ",
        "distributorName,distributorCode, account, ",
        "password, priority, ",
        "roleInfo, createdById, ",
        "createdByName, createTime, ",
        "lastUpdateTime, tracelog)",
        "values (#{distributorUserId,jdbcType=BIGINT}, #{distributorProjectId,jdbcType=BIGINT}, ",
        "#{distributorName,jdbcType=VARCHAR},#{distributorCode}, #{account,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR}, #{priority,jdbcType=TINYINT}, ",
        "#{roleInfo,jdbcType=VARCHAR}, #{createdById,jdbcType=BIGINT}, ",
        "#{createdByName,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{lastUpdateTime,jdbcType=TIMESTAMP}, #{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(DistributorUser record);

    @Select({
        "select",
        "distributorUserId, distributorProjectId, distributorName, distributorCode,account, password, priority, roleInfo, createdById, ",
        "createdByName, createTime, lastUpdateTime, tracelog",
        "from distributor_user",
        "where distributorUserId = #{distributorUserId,jdbcType=BIGINT}"
    })
    DistributorUser getDistributorById(Long distributorUserId);

    @Update({
        "update distributor_user",
        "set distributorProjectId = #{distributorProjectId,jdbcType=BIGINT},",
          "distributorName = #{distributorName,jdbcType=VARCHAR},",
          "account = #{account,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "priority = #{priority,jdbcType=TINYINT},",
          "roleInfo = #{roleInfo,jdbcType=VARCHAR},",
          "createdById = #{createdById,jdbcType=BIGINT},",
          "createdByName = #{createdByName,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
        "where distributorUserId = #{distributorUserId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DistributorUser record);

    @Update({
            "update distributor_user",
            "set ",
            "password = #{password,jdbcType=VARCHAR},",
            "lastUpdateTime =now()",
            "where distributorUserId = #{distributorUserId,jdbcType=BIGINT}"
    })
    int updatePassword(long distributorUserId,String password);

    @Update({
            "update distributor_user",
            "set ",
            "account = #{account,jdbcType=VARCHAR},",
            "password = #{password,jdbcType=VARCHAR},",
            "lastUpdateTime =now()",
            "where distributorUserId = #{distributorUserId,jdbcType=BIGINT}"
    })
    int updateAccountAndPassword(long distributorUserId ,String account,String password);
}