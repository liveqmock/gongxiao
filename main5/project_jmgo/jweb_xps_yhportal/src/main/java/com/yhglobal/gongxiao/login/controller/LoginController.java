package com.yhglobal.gongxiao.login.controller;

import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.protal.PortalConfig;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserServiceGrpc;
import com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.utils.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);


    @Autowired
    PortalConfig portalConfig;

    @ResponseBody
    @RequestMapping(value = "/checkAccount", method = RequestMethod.POST)
    public GongxiaoResult checkAccount(HttpServletRequest request, String userName, String passWord) {

        GongxiaoRpc.RpcHeader rpcHeader = GongxiaoRpc.RpcHeader.newBuilder().setUsername(userName).setUid("1").build();
        try {
            logger.info("#traceId={}# [IN][checkAccount] params:   userName={}, password={}",
                    rpcHeader.getTraceId(), userName, passWord);
            boolean paramNotNull = ValidationUtil.isNotEmpty(userName, passWord);
            if(!paramNotNull){
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.USER_PWD_NOT_NULL);
            }
            //通过账号获取用户列表
            YhportalUserServiceGrpc.YhportalUserServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(YhportalUserServiceGrpc.YhportalUserServiceBlockingStub.class);
            YhportalUserStructure.GetUserListByAccountReq getUserByUserNameReq = YhportalUserStructure.GetUserListByAccountReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setUserName(userName)
                    .build();
            YhportalUserStructure.GetUserListByAccountResp userListByAccountResp = rpcStub.getUserListByAccount(getUserByUserNameReq);
            List<YhportalUserStructure.PortalUser> portalUserList = userListByAccountResp.getPortalUserList();
            if (portalUserList.size() == 0) {
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.USER_NOT_EXIST);
            }
            boolean projectExist = false;
            // 校验账号是否在项目下
            PortalUserInfo sessionUserInfo = new PortalUserInfo();
            for (YhportalUserStructure.PortalUser user : portalUserList) {
                long projectId = user.getProjectId();
                if (projectId == portalConfig.getTargetProjectId()) {
                    //校验密码
                    if (!passWord.equals(user.getPasswd())){
                        return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.PASSWORD_NOT_MATCH);
                    }
                    //填充信息
                    projectExist = true;
                    sessionUserInfo.setUserId(user.getUserId() + "");
                    sessionUserInfo.setUserName(user.getUserName());
                    break;
                }
            }
            if (!projectExist) {
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.PROJECT_NOT_ACCOUNT);
            }
            //TODO: 和DB中该用户归属的projectId列表 做校验
            sessionUserInfo.setProjectId(portalConfig.getTargetProjectId());
            request.getSession().setAttribute("sessionInfo", sessionUserInfo);  //把用户信息存到session
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.SUCCESS);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

}
