package com.yhglobal.gongxiao.user.service;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.portal.user.model.YhPortalUser;

/**
 * @author: 葛灿
 */
public interface UserService {

    YhPortalUser getYhPortalUserByName(String userName);

}
