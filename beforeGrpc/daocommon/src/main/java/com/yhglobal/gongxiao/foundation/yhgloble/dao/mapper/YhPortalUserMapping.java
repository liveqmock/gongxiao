package com.yhglobal.gongxiao.foundation.yhgloble.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.portal.user.model.YhPortalUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 越海portal用户Mapper
 */
public interface YhPortalUserMapping extends BaseMapper{
    @Insert({
            "insert into yhglobal_portal_user",
            "(userId,userName,passwd,userStatus,",
            "phoneNumber,isAdmin,roleInfo,",
            "tracelog,createTime)",
            "values",
            "(#{userId},#{userName},#{passwd},#{userStatus},",
            "#{phoneNumber},#{isAdmin},#{roleInfo},",
            "#{tracelog},#{createTime})"
    })
    int insertOne(YhPortalUser user);

    /**
     *
     * @param userId 用户id
     * @return
     */
    @Select({
            "select",
            "userId,userName,passwd,userStatus,",
            "phoneNumber,isAdmin,roleInfo,",
            "tracelog,createTime，lastUpdateTime",
            "from yhglobal_portal_user",
            "where userId=#{userId}"
    })
    YhPortalUser selectById(String userId);

    @Select({
            "select",
            "userId,userName,passwd,userStatus,",
            "phoneNumber,isAdmin,roleInfo,",
            "tracelog,createTime,lastUpdateTime",
            "from yhglobal_portal_user",
            "where userName=#{userName} and passwd = #{passwd}"
    })
    YhPortalUser selectInfoByUserNameAndPassword(@Param("userName") String userName, @Param("passwd") String passwd);

    @Select({
            "select",
            "userId,userName,passwd,userStatus,",
            "phoneNumber,isAdmin,roleInfo,",
            "tracelog,createTime,lastUpdateTime",
            "from yhglobal_portal_user",
            "where userName=#{yhPortalUserByName}"
    })
    YhPortalUser getYhPortalUserByName(@Param("yhPortalUserByName") String yhPortalUserByName);
}
