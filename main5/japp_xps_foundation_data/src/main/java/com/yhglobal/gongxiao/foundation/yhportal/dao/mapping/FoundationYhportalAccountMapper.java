package com.yhglobal.gongxiao.foundation.yhportal.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.yhportal.model.FoundationYhportalAccount;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface FoundationYhportalAccountMapper extends BaseMapper {
    @Delete({
        "delete from foundation_yhportal_account",
        "where userId = #{userId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long userId);

    @Insert({
        "insert into foundation_yhportal_account (userId, userName,projectId,projectName, ",
        "passwd, userStatus, ",
        "phoneNumber, isAdmin, ",
        "roleInfo, createTime, ",
        "lastUpdateTime, tracelog)",
        "values (#{userId,jdbcType=BIGINT}, #{userName,jdbcType=VARCHAR}, ",
        "#{passwd,jdbcType=VARCHAR}, #{userStatus,jdbcType=TINYINT}, ",
        "#{phoneNumber,jdbcType=VARCHAR}, #{isAdmin,jdbcType=TINYINT}, ",
        "#{roleInfo,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{lastUpdateTime,jdbcType=TIMESTAMP}, #{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(FoundationYhportalAccount record);

    @Select({
        "select",
        "userId, userName, passwd, userStatus,projectId,projectName, phoneNumber, isAdmin, roleInfo, createTime, ",
        "lastUpdateTime, tracelog",
        "from foundation_yhportal_account",
        "where userId = #{userId,jdbcType=BIGINT}"
    })
    FoundationYhportalAccount selectByPrimaryKey(Long userId);

    @Select({
            "select",
            "userId, userName, passwd, userStatus,projectId,projectName, phoneNumber, isAdmin, roleInfo, createTime, ",
            "lastUpdateTime, tracelog",
            "from foundation_yhportal_account",
            "where userName = #{userName,jdbcType=BIGINT}"
    })
    FoundationYhportalAccount getUserByName(String userName);

    @Select({
            "select",
            "userId, userName, passwd, userStatus,projectId,projectName, phoneNumber, isAdmin, roleInfo, createTime, ",
            "lastUpdateTime, tracelog",
            "from foundation_yhportal_account",
    })
    List<FoundationYhportalAccount> selectAllUser();

    @Select({
            "select",
            "userId, userName, passwd, userStatus,projectId,projectName, phoneNumber, isAdmin, roleInfo, createTime, ",
            "lastUpdateTime, tracelog",
            "from foundation_yhportal_account where userName = #{userName}",
    })
    List<FoundationYhportalAccount> getUserListByAccount(String userName);

    @Update({
        "update foundation_yhportal_account",
        "set userName = #{userName,jdbcType=VARCHAR},",
          "passwd = #{passwd,jdbcType=VARCHAR},",
          "userStatus = #{userStatus,jdbcType=TINYINT},",
          "phoneNumber = #{phoneNumber,jdbcType=VARCHAR},",
          "isAdmin = #{isAdmin,jdbcType=TINYINT},",
          "roleInfo = #{roleInfo,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
        "where userId = #{userId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(FoundationYhportalAccount record);

}