package com.yhglobal.gongxiao.user.service.impl;

import com.yhglobal.gongxiao.foundation.portal.user.model.YhPortalUser;
import com.yhglobal.gongxiao.foundation.yhgloble.dao.YhPortalUserDao;
import com.yhglobal.gongxiao.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    YhPortalUserDao yhPortalUserDao;

    @Override
    public YhPortalUser getYhPortalUserByName(String yhPortalUserByName) {
//        return yhPortalUserDao.getYhPortalUserByName(yhPortalUserByName);
        return yhPortalUserDao.getYhPortalUserByName(yhPortalUserByName);
    }

}
