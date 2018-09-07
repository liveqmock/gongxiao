package com.yhglobal.gongxiao.distributor.user.service.impl;

import com.yhglobal.gongxiao.foundation.distributor.dao.DistributorPortalUserDao;
import com.yhglobal.gongxiao.foundation.portal.user.model.DistributorPortalUser;
import com.yhglobal.gongxiao.distributor.user.service.DistributorUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistributorUserServiceImpl implements DistributorUserService {

    @Autowired
    DistributorPortalUserDao distributorPortalUserDao;

    @Override
    public DistributorPortalUser getDistributorPortalUserByName(String userName) {
        return distributorPortalUserDao.getDistributorPortalUserByName(userName);
    }

}
