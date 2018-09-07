package com.yhglobal.gongxiao.foundation.yhgloble.dao;

import com.yhglobal.gongxiao.foundation.portal.user.model.YhPortalUser;
import com.yhglobal.gongxiao.foundation.yhgloble.dao.mapper.YhPortalUserMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * 越海portal用户 DAO
 */
@Repository
public class YhPortalUserDao {
    @Autowired
    YhPortalUserMapping yhPortalUserMapping;

    public int insertOne(YhPortalUser record){
        return yhPortalUserMapping.insertOne(record);

    }

    /**
     *
     * @param userId 通过用户ID来查询
     * @return
     */
    public YhPortalUser selectById(String userId){
        return yhPortalUserMapping.selectById(userId);
    }

    public YhPortalUser selectInfoByUserNameAndPassword(String userName,String passWord) {
        return yhPortalUserMapping.selectInfoByUserNameAndPassword(userName,passWord);
    }

    public YhPortalUser getYhPortalUserByName(String yhPortalUserByName) {
        return yhPortalUserMapping.getYhPortalUserByName(yhPortalUserByName);
    }
}
