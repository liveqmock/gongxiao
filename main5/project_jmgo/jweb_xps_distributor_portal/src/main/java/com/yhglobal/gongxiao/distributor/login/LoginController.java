package com.yhglobal.gongxiao.distributor.login;

import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorServiceGrpc;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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
    private PortalConfig portalConfig;

    @ResponseBody
    @RequestMapping(value = "/checkAccount", method = RequestMethod.POST)
    public GongxiaoResult checkAccount(HttpServletRequest request, String distributorName, String password) {
        GongxiaoRpc.RpcHeader rpcHeader = GongxiaoRpc.RpcHeader.newBuilder().setUsername(distributorName).setUid("1").build();
        try {
            DistributorServiceGrpc.DistributorServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(DistributorServiceGrpc.DistributorServiceBlockingStub.class);
            DistributorStructure.GetDistiruborAccountByNameReq getDistiruborAccountByNameReq = DistributorStructure.GetDistiruborAccountByNameReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(portalConfig.getTargetProjectId())
                    .setUserName(distributorName)
                    .build();
            DistributorStructure.GetDistiruborAccountByNameResp resp = rpcStub.getDistiruborAccountByName(getDistiruborAccountByNameReq);
            DistributorStructure.DistributorUserDetail distributorUserDetail = resp.getDistributorUserDetail();
            String passwordDb = distributorUserDetail.getPassword();
            if (!password.equals(passwordDb)) {
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.PASSWORD_NOT_MATCH);
            }
            PortalUserInfo sessionUserInfo = new PortalUserInfo();
            sessionUserInfo.setUserId(distributorUserDetail.getDistributorUserId() + "");
            sessionUserInfo.setUserName(distributorUserDetail.getAccount());
            sessionUserInfo.setProjectId(distributorUserDetail.getProjectId());
            sessionUserInfo.setDistributorId(distributorUserDetail.getDistributorBusinessId());
            sessionUserInfo.setDistributorName(distributorUserDetail.getDistributorName());
            request.getSession().setAttribute("sessionInfo", sessionUserInfo);  //把用户信息存到session
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.SUCCESS);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

    }

}
