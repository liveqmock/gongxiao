package com.yhglobal.gongxiao.foundation.yhportal.impl;

import com.yhglobal.gongxiao.foundation.yhportal.dao.FoundationYhportalAccountDao;
import com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserServiceGrpc;
import com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure;
import com.yhglobal.gongxiao.foundation.yhportal.model.FoundationYhportalAccount;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Service
public class FoundationYhportalAccountImpl extends YhportalUserServiceGrpc.YhportalUserServiceImplBase {

    private static Logger logger = LoggerFactory.getLogger(FoundationYhportalAccountImpl.class);

    @Autowired
    FoundationYhportalAccountDao foundationYhportalAccountDao;

    //校验 登录的账号/密码 是否正确  注：似乎没用该接口了
    public void checkAccount(YhportalUserStructure.CheckAccountReq req,
                             StreamObserver<YhportalUserStructure.CheckAccountResp> responseObserver){
        YhportalUserStructure.CheckAccountResp.Builder respBuilder = YhportalUserStructure.CheckAccountResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();

        String userName = req.getUserName();
        String password = req.getPassword();

        logger.info("#traceId={}# [IN][checkAccount] params: userName={},password={} ", rpcHeader.getTraceId(), userName,password);

        try {
            FoundationYhportalAccount account = foundationYhportalAccountDao.getAccountByUserName(userName);
            if (account==null){
                logger.info("[checkAccount] 没有拿到供应商账户数据数据 #traceId={}",rpcHeader.getTraceId());
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            String passwd = account.getPasswd();
            if (passwd.equals(passwd)){
                respBuilder.setPassCheck(true);
                logger.info("login success");
            }else {
                respBuilder.setPassCheck(false);
                logger.info("login fail");
            }
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT][checkAccount] complete params: userName={} password={} ", rpcHeader.getTraceId(), userName,passwd);
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    //登录接口：通过用户名获取账户信息
    public void getUserByUserName (YhportalUserStructure.GetUserByUserNameReq req,
                                   StreamObserver<YhportalUserStructure.GetUserByUserNameResp> responseObserver){
        YhportalUserStructure.GetUserByUserNameResp.Builder respBuilder = YhportalUserStructure.GetUserByUserNameResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String userName = req.getUserName();
        try {
            FoundationYhportalAccount account = foundationYhportalAccountDao.getAccountByUserName(userName);
           foundationYhportalAccountDao.selectAccountListByName(userName);
            YhportalUserStructure.PortalUser.Builder user = YhportalUserStructure.PortalUser.newBuilder();
            user.setUserId(GrpcCommonUtil.getProtoParam(account.getUserId()));
            user.setUserName(GrpcCommonUtil.getProtoParam(account.getUserName()));
            user.setCreateTime(GrpcCommonUtil.getProtoParam(DateUtil.getTime(account.getCreateTime())));
            user.setIsAdmin(GrpcCommonUtil.getProtoParam(account.getIsAdmin()));
            user.setLastUpdateTime(GrpcCommonUtil.getProtoParam(DateUtil.getTime(account.getLastUpdateTime())));
            user.setUserStatus(GrpcCommonUtil.getProtoParam(account.getUserStatus()));
            user.setPasswd(GrpcCommonUtil.getProtoParam(account.getPasswd()));
            user.setPhoneNumber(GrpcCommonUtil.getProtoParam(account.getPhoneNumber()));
            user.setRoleInfo(GrpcCommonUtil.getProtoParam(account.getRoleInfo()));
            user.setTracelog(GrpcCommonUtil.getProtoParam(GrpcCommonUtil.getProtoParam(account.getTracelog())));
            user.setProjectId(GrpcCommonUtil.getProtoParam(account.getProjectId()));
            user.setProjectName(GrpcCommonUtil.getProtoParam(account.getProjectName()));
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    //用户重名的场景 通过用户名获取所有重名的账户
    public void getUserListByAccount (YhportalUserStructure.GetUserListByAccountReq req,
                                   StreamObserver<YhportalUserStructure.GetUserListByAccountResp> responseObserver){
        YhportalUserStructure.GetUserListByAccountResp.Builder respBuilder = YhportalUserStructure.GetUserListByAccountResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String userName = req.getUserName();
        try {
            List<FoundationYhportalAccount> foundationYhportalAccounts = foundationYhportalAccountDao.selectAccountListByName(userName);
            if (foundationYhportalAccounts.size() == 0){
                logger.warn("没有找到账号为{}的portal账号",userName);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            for (FoundationYhportalAccount account : foundationYhportalAccounts){
                YhportalUserStructure.PortalUser.Builder user = YhportalUserStructure.PortalUser.newBuilder();
                user.setUserId(GrpcCommonUtil.getProtoParam(account.getUserId()));
                user.setUserName(GrpcCommonUtil.getProtoParam(account.getUserName()));
                user.setCreateTime(GrpcCommonUtil.getProtoParam(DateUtil.getTime(account.getCreateTime())));
                user.setIsAdmin(GrpcCommonUtil.getProtoParam(account.getIsAdmin()));
                user.setLastUpdateTime(GrpcCommonUtil.getProtoParam(DateUtil.getTime(account.getLastUpdateTime())));
                user.setUserStatus(GrpcCommonUtil.getProtoParam(account.getUserStatus()));
                user.setPasswd(GrpcCommonUtil.getProtoParam(account.getPasswd()));
                user.setPhoneNumber(GrpcCommonUtil.getProtoParam(account.getPhoneNumber()));
                user.setRoleInfo(GrpcCommonUtil.getProtoParam(account.getRoleInfo()));
                user.setTracelog(GrpcCommonUtil.getProtoParam(GrpcCommonUtil.getProtoParam(account.getTracelog())));
                user.setProjectId(GrpcCommonUtil.getProtoParam(account.getProjectId()));
                user.setProjectName(GrpcCommonUtil.getProtoParam(account.getProjectName()));
                respBuilder.addPortalUser(user);
            }
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }

    }

}
