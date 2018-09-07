package com.yhglobal.gongxiao.foundation.distributor.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.foundation.distributor.dao.FoundationDistributorBasicDao;
import com.yhglobal.gongxiao.foundation.distributor.dao.FoundationDistributorBusinessDao;
import com.yhglobal.gongxiao.foundation.distributor.dao.FoundationDistributorUserDao;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorServiceGrpc;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure;
import com.yhglobal.gongxiao.foundation.distributor.model.FoundationDistributorBusiness;
import com.yhglobal.gongxiao.foundation.distributor.model.FoundationDistributorUser;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.status.FoundationNormalStatus;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 经销商相关主要3个表：
 *   foundation_distributor_basic 基础表
 *   foundation_distributor_business 业务表
 *   foundation_distributor_user 用户表
 */
@Service
public class DistributorServiceImpl extends DistributorServiceGrpc.DistributorServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(DistributorServiceImpl.class);

    @Autowired
    private FoundationDistributorBasicDao distributorBasicDao;

    @Autowired
    private FoundationDistributorBusinessDao distributorBusinessDao;

    @Autowired
    private FoundationDistributorUserDao distributorUserDao;

    //9. 获取某个项目下经销商列表
    public void selectBusinessListByProject(DistributorStructure.SelectBusinessListByProjectReq req,
                                            StreamObserver<DistributorStructure.SelectBusinessListByProjectResp> responseObserver) {
        DistributorStructure.SelectBusinessListByProjectResp.Builder respBuilder = DistributorStructure.SelectBusinessListByProjectResp.newBuilder();
        DistributorStructure.BusinessPage.Builder businessPage = DistributorStructure.BusinessPage.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        long projectId = req.getProjectId();
        logger.info("#traceId={}# [IN][selectBusinessListByProject] params: projectId={} ", rpcHeader.getTraceId(), projectId);
        try {
            List<FoundationDistributorBusiness> foundationDistributorBusinesses
                    = distributorBusinessDao.selectBusinessListByProjectId(projectId);
            if (foundationDistributorBusinesses.size() == 0) {
                logger.info("[selectBusinessListByProject] 没有拿到经销商业务数据 #traceId={}, projectId={}", rpcHeader.getTraceId(), projectId);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            for (FoundationDistributorBusiness distributorBusiness : foundationDistributorBusinesses) {
                DistributorStructure.DistributorBusiness distributorBusiness1 = DistributorStructure.DistributorBusiness.newBuilder()
                        .setDistributorBusinessId(GrpcCommonUtil.getProtoParam(distributorBusiness.getDistributorBusinessId()))
                        .setDistributorBasicId(GrpcCommonUtil.getProtoParam(distributorBusiness.getDistributorBasicId()))
                        .setDistributorCode(GrpcCommonUtil.getProtoParam(distributorBusiness.getDistributorCode()))
                        .setDistributorName(GrpcCommonUtil.getProtoParam(distributorBusiness.getDistributorName()))
                        .setDistributorEasCode(GrpcCommonUtil.getProtoParam(distributorBusiness.getEasCode()))
                        .setRecordStatus(GrpcCommonUtil.getProtoParam(distributorBusiness.getRecordStatus()))
                        .setProjectId(GrpcCommonUtil.getProtoParam(distributorBusiness.getProjectId()))
                        .setProjectName(GrpcCommonUtil.getProtoParam(distributorBusiness.getProjectName()))
                        .setDistributorPurchaseCouponDiscount(GrpcCommonUtil.getProtoParam(distributorBusiness.getDistributorPurchaseCouponDiscount()))
                        .setActualSaleReturn(GrpcCommonUtil.getProtoParam(distributorBusiness.getActualSaleReturn()))
                        .setShouldSaleReturn(GrpcCommonUtil.getProtoParam(distributorBusiness.getShouldSaleReturn()))
                        .setAccordingRealInventorySale(GrpcCommonUtil.getProtoParam(distributorBusiness.getAccordingRealInventorySale()))
                        .setSettlementType(GrpcCommonUtil.getProtoParam(distributorBusiness.getSettlementType()))
                        .setAccountPeriod(GrpcCommonUtil.getProtoParam(distributorBusiness.getAccountPeriod()))
                        .setCreatedById(GrpcCommonUtil.getProtoParam(distributorBusiness.getCreatedById()))
                        .setCreatedByName(GrpcCommonUtil.getProtoParam(distributorBusiness.getCreatedByName()))
                        .setCreateTime(DateUtil.getTime(distributorBusiness.getCreateTime()))
                        .setLastUpdateTime(DateUtil.getTime(distributorBusiness.getLastUpdateTime()))
                        .setTracelog(distributorBusiness.getTracelog() != null ? distributorBusiness.getTracelog() : "")
                        .build();
                businessPage.addDistributorBusiness(distributorBusiness1);
            }
            respBuilder.setBusinessPage(businessPage);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT][selectBusinessListByProject] success params: projectId={},sieze={} ", rpcHeader.getTraceId(), projectId, foundationDistributorBusinesses.size());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    //获取某项目,某经销商的登录账号列表
    //目标是 获取该项目下，指定经销商的子账户
    public void selectAccountListByBusinessId(DistributorStructure.SelectAccountListByBusinessIdReq req,
                                              StreamObserver<DistributorStructure.SelectAccountListByBusinessIdResp> responseObserver) {
        DistributorStructure.SelectAccountListByBusinessIdResp.Builder respBuilder = DistributorStructure.SelectAccountListByBusinessIdResp.newBuilder();
        DistributorStructure.UserPage.Builder userPage = DistributorStructure.UserPage.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        long distributorBusinessId = req.getDistributorBusinessId();
        logger.info("#traceId={}# [IN][selectAccountListByBusinessId] params: distributorBusinessId={} ", rpcHeader.getTraceId(), distributorBusinessId);
        try {
            List<FoundationDistributorUser> foundationDistributorUsers = distributorUserDao.selectUserByBusinessId(distributorBusinessId);
            if (foundationDistributorUsers.size() == 0) {
                logger.info("[selectAccountListByBusinessId] 没有拿到经销商业务数据 #traceId={}, projectId={}", rpcHeader.getTraceId(), distributorBusinessId);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            for (FoundationDistributorUser foundationDistributorUser : foundationDistributorUsers) {
                DistributorStructure.DistributorUser distributorUser = DistributorStructure.DistributorUser.newBuilder()
                        .setDistributorBusinessId(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getDistributorBusinessId()))
                        .setProjectId(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getProjectId()))
                        .setProjectName(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getProjectName()))
                        .setDistributorUserId(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getDistributorUserId()))
                        .setDistributorName(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getDistributorName()))
                        .setAccount(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getAccount()))
                        .setPassword(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getPassword()))
                        .setPriority(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getPriority()))
                        .setCreatedById(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getCreatedById()))
                        .setCreatedByName(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getCreatedByName()))
                        .setCreateTime(DateUtil.getTime(foundationDistributorUser.getCreateTime()))
                        .setLastUpdateTime(DateUtil.getTime(foundationDistributorUser.getLastUpdateTime()))
                        .setRoleInfo(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getRoleInfo()))
                        .build();
                userPage.addDistributorUser(distributorUser);
            }
            respBuilder.setUserPage(userPage);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT][selectAccountListByBusinessId] success params: distributorBusinessId={},sieze={} ", rpcHeader.getTraceId(), distributorBusinessId, foundationDistributorUsers.size());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    //通过经销商项目关系id,获取经销商项目关系详情
    public void getDistributorBusinessById(DistributorStructure.GetDistributorBusinessByIdReq req,
                                           StreamObserver<DistributorStructure.GetDistributorBusinessByIdResp> responseObserver) {
        DistributorStructure.GetDistributorBusinessByIdResp.Builder respBuilder = DistributorStructure.GetDistributorBusinessByIdResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        long distributorBusinessId = req.getDistributorBusinessId();
        logger.info("#traceId={}# [IN][getDistributorBusinessById] params: distributorBusinessId={} ", rpcHeader.getTraceId(), distributorBusinessId);
        try {
            FoundationDistributorBusiness distributorBusiness = distributorBusinessDao.getDistributorBusinessInfo(distributorBusinessId);
            if (distributorBusiness == null) {
                logger.info("[getDistributorBusinessById] 没有拿到经销商业务数据 #traceId={}, projectId={}", rpcHeader.getTraceId(), distributorBusinessId);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            DistributorStructure.DistributorBusiness distributorBusiness1 = DistributorStructure.DistributorBusiness.newBuilder()
                    .setDistributorBusinessId(GrpcCommonUtil.getProtoParam(distributorBusiness.getDistributorBusinessId()))
                    .setDistributorBasicId(GrpcCommonUtil.getProtoParam(distributorBusiness.getDistributorBasicId()))
                    .setDistributorCode(GrpcCommonUtil.getProtoParam(distributorBusiness.getDistributorCode()))
                    .setDistributorName(GrpcCommonUtil.getProtoParam(distributorBusiness.getDistributorName()))
                    .setDistributorEasCode(GrpcCommonUtil.getProtoParam(distributorBusiness.getEasCode()))
                    .setRecordStatus(GrpcCommonUtil.getProtoParam(distributorBusiness.getRecordStatus()))
                    .setProjectId(GrpcCommonUtil.getProtoParam(distributorBusiness.getProjectId()))
                    .setProjectName(GrpcCommonUtil.getProtoParam(distributorBusiness.getProjectName()))
                    .setDistributorPurchaseCouponDiscount(GrpcCommonUtil.getProtoParam(distributorBusiness.getDistributorPurchaseCouponDiscount()))
                    .setActualSaleReturn(GrpcCommonUtil.getProtoParam(distributorBusiness.getActualSaleReturn()))
                    .setShouldSaleReturn(GrpcCommonUtil.getProtoParam(distributorBusiness.getShouldSaleReturn()))
                    .setAccordingRealInventorySale(GrpcCommonUtil.getProtoParam(distributorBusiness.getAccordingRealInventorySale()))
                    .setSettlementType(GrpcCommonUtil.getProtoParam(distributorBusiness.getSettlementType()))
                    .setAccountPeriod(GrpcCommonUtil.getProtoParam(distributorBusiness.getAccountPeriod()))
                    .setCreatedById(GrpcCommonUtil.getProtoParam(distributorBusiness.getCreatedById()))
                    .setCreatedByName(GrpcCommonUtil.getProtoParam(distributorBusiness.getCreatedByName()))
                    .setCreateTime(DateUtil.getTime(distributorBusiness.getCreateTime()))
                    .setLastUpdateTime(DateUtil.getTime(distributorBusiness.getLastUpdateTime()))
                    .setTracelog(distributorBusiness.getTracelog() != null ? distributorBusiness.getTracelog() : "")
                    .build();
            respBuilder.setDistributorBusiness(distributorBusiness1);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT][getDistributorBusinessById] success params: distributorBusinessId={} ", rpcHeader.getTraceId(), distributorBusinessId);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    //通过经销商登录账号id,获取经销商登录账号详情
    public void getDistributorAccountById(DistributorStructure.GetDistributorAccountByIdReq req,
                                          StreamObserver<DistributorStructure.GetDistributorAccountByIdResp> responseObserver) {
        DistributorStructure.GetDistributorAccountByIdResp.Builder respBuilder = DistributorStructure.GetDistributorAccountByIdResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        long distributorUserId = req.getDistributorUserId();
        logger.info("#traceId={}# [IN][getDistributorBusinessById] params: distributorUserId={} ", rpcHeader.getTraceId(), distributorUserId);
        try {
            FoundationDistributorUser foundationDistributorUser = distributorUserDao.getByDistributorUserId(distributorUserId);
            if (foundationDistributorUser == null) {
                logger.info("[getDistributorBusinessById] 没有拿到经销商用户数据 #traceId={}, distributorUserId={}", rpcHeader.getTraceId(), distributorUserId);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            DistributorStructure.DistributorUser distributorUser = DistributorStructure.DistributorUser.newBuilder()
                    .setDistributorBusinessId(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getDistributorBusinessId()))
                    .setProjectId(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getProjectId()))
                    .setProjectName(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getProjectName()))
                    .setDistributorUserId(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getDistributorUserId()))
                    .setDistributorName(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getDistributorName()))
                    .setAccount(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getAccount()))
                    .setPassword(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getPassword()))
                    .setPriority(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getPriority()))
                    .setCreatedById(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getCreatedById()))
                    .setCreatedByName(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getCreatedByName()))
                    .setCreateTime(DateUtil.getTime(foundationDistributorUser.getCreateTime()))
                    .setLastUpdateTime(DateUtil.getTime(foundationDistributorUser.getLastUpdateTime()))
                    .setRoleInfo(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getRoleInfo()))
                    .build();
            respBuilder.setDistributorUser(distributorUser);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT][getDistributorAccountById] success params: distributorUserId={} ", rpcHeader.getTraceId(), distributorUserId);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    //通过经销商account(登录账号名), name(经销商名字) 获取经销商账号信息
    public void selectDistributorAccountByCondition(DistributorStructure.SelectDistributorAccountByConditionReq req,
                                                    StreamObserver<DistributorStructure.SelectDistributorAccountByConditionResp> responseObserver) {
        DistributorStructure.SelectDistributorAccountByConditionResp.Builder respBuilder = DistributorStructure.SelectDistributorAccountByConditionResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        long distributorUserId = req.getDistributorUserId();
        String distributorName = req.getDistributorName();
        long projectId = req.getProjectId();
        int pageNumber = req.getPageNumber();
        int pageSize = req.getPageSize();
        String userName = req.getUserName();
        logger.info("#traceId={}# [IN][selectDistributorAccountByCondition] params: distributorUserId={} ", rpcHeader.getTraceId(), distributorUserId);
        try {
            PageHelper.startPage(pageNumber, pageSize);
            List<FoundationDistributorUser> foundationDistributorUsers
                    = distributorUserDao.selectUserByCondition(projectId, userName,  distributorName);
            if (foundationDistributorUsers.size() == 0) {
                logger.info("[selectDistributorAccountByCondition] 没有拿到经销商账户数据 #traceId={}, distributorUserId={}", rpcHeader.getTraceId(), distributorUserId);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            PageInfo<FoundationDistributorUser> foundationDistributorUserPageInfo = new PageInfo<>(foundationDistributorUsers);

            for (FoundationDistributorUser foundationDistributorUser : foundationDistributorUsers) {
                DistributorStructure.DistributorUser distributorUser = DistributorStructure.DistributorUser.newBuilder()
                        .setDistributorBusinessId(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getDistributorBusinessId()))
                        .setDistributorUserId(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getDistributorUserId()))
                        .setDistributorName(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getDistributorName()))
                        .setAccount(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getAccount()))
                        .setPassword(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getPassword()))
                        .setPriority(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getPriority()))
                        .setCreatedById(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getCreatedById()))
                        .setCreatedByName(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getCreatedByName()))
                        .setCreateTime(DateUtil.getTime(foundationDistributorUser.getCreateTime()))
                        .setLastUpdateTime(DateUtil.getTime(foundationDistributorUser.getLastUpdateTime()))
                        .setRoleInfo(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getRoleInfo()))
                        .build();
                respBuilder.addDistributorUser(distributorUser);
            }
            respBuilder.setTotal(foundationDistributorUserPageInfo.getTotal());
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT][selectDistributorAccountByCondition] params: distributorUserId={} ", rpcHeader.getTraceId(), distributorUserId);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    //?? 需要和原型核对 模糊查询的条件
    public void selectUserByCondition(DistributorStructure.SelectUserByConditionReq req,
                                      StreamObserver<DistributorStructure.SelectUserByConditionResp> responseObserver) {
        DistributorStructure.SelectUserByConditionResp.Builder respBuilder = DistributorStructure.SelectUserByConditionResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String distributorName = req.getDistributorName();
        String easCode = req.getEasCode();
        int pageNumber = req.getPageNumber();
        int pageSize = req.getPageSize();
        int status = req.getStatus();
        long projectId = 0;
        Map<Integer, Byte> map = new HashMap<>();
        map.put(0, FoundationNormalStatus.UNKNOWN.getStatus());
        map.put(1, FoundationNormalStatus.INIT.getStatus());
        map.put(2, FoundationNormalStatus.COMMITTED.getStatus());
        map.put(3, FoundationNormalStatus.AUDITED.getStatus());
        map.put(4, FoundationNormalStatus.REJECTED.getStatus());
        try {
            logger.info("#traceId={}# [IN][selectUserByCondition] params: easCode={} ",rpcHeader.getTraceId(),easCode);
            PageHelper.startPage(pageNumber, pageSize);
            List<FoundationDistributorBusiness> foundationDistributorBusinesses = distributorBusinessDao.selectBusinessByCondition(projectId, easCode, distributorName, map.get(status));
            if (foundationDistributorBusinesses.size() == 0) {
                logger.info("[selectUserByCondition] 没有拿到经销商账户数据 #traceId={}, distributorName={}, easCode={}", rpcHeader.getTraceId(), distributorName, easCode);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            int size = 0;
            PageInfo<FoundationDistributorBusiness> foundationDistributorUserPageInfo = new PageInfo<>(foundationDistributorBusinesses);
            for (FoundationDistributorBusiness business : foundationDistributorBusinesses) {
                Long distributorBusinessId = business.getDistributorBusinessId();
                List<FoundationDistributorUser> foundationDistributorUsers = distributorUserDao.selectUserByBusinessId(distributorBusinessId);
                size += foundationDistributorUsers.size();
                for (FoundationDistributorUser foundationDistributorUser : foundationDistributorUsers) {
                    DistributorStructure.DistributorUser distributorUser = DistributorStructure.DistributorUser.newBuilder()
                            .setDistributorBusinessId(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getDistributorBusinessId()))
                            .setDistributorUserId(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getDistributorUserId()))
                            .setDistributorName(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getDistributorName()))
                            .setAccount(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getAccount()))
                            .setPassword(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getPassword()))
                            .setPriority(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getPriority()))
                            .setCreatedById(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getCreatedById()))
                            .setCreatedByName(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getCreatedByName()))
                            .setCreateTime(DateUtil.getTime(foundationDistributorUser.getCreateTime()))
                            .setLastUpdateTime(DateUtil.getTime(foundationDistributorUser.getLastUpdateTime()))
                            .setRoleInfo(GrpcCommonUtil.getProtoParam(foundationDistributorUser.getRoleInfo()))
                            .build();
                    respBuilder.addDistributorUser(distributorUser);
                }
            }
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT][selectUserByCondition] success params: distributorName={} ", rpcHeader.getTraceId(), distributorName);

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    //尚未添加逻辑 需要和原型核对
    public void editDistributor(DistributorStructure.EditDistributorReq req,
                                StreamObserver<DistributorStructure.EditDistributorResp> responseObserver) {
        DistributorStructure.SelectUserByConditionResp.Builder respBuilder = DistributorStructure.SelectUserByConditionResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        DistributorStructure.DistributorDetail distributorDetail = req.getDistributorDetail();
        //todo:
    }

    //尚未添加逻辑 需要和原型核对
    public void getDistributorDetail(DistributorStructure.GetDistributorDetailReq req,
                                     StreamObserver<DistributorStructure.GetDistributorDetailResp> responseObserver) {
        DistributorStructure.SelectUserByConditionResp.Builder respBuilder = DistributorStructure.SelectUserByConditionResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String distributorBusinessId = req.getDistributorBusinessId();


    }

    //?? 需要和原型核对 模糊查询的条件
    public void selectDistributorBusinessByCondition(DistributorStructure.SelectDistributorBusinessByConditionReq req,
                                                     StreamObserver<DistributorStructure.SelectDistributorBusinessByConditionResp> responseObserver) {
        DistributorStructure.SelectDistributorBusinessByConditionResp.Builder respBuilder = DistributorStructure.SelectDistributorBusinessByConditionResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        long projectId = req.getProjectId();
        long distributorBusinessId = req.getDistributorBusinessId();
        String distributorName = req.getDistributorName();
        int pageNumber = req.getPageNumber();
        int pageSize = req.getPageSize();
        int status = req.getStatus();
        Map<Integer, Byte> map = new HashMap<>();
        map.put(0,FoundationNormalStatus.UNKNOWN.getStatus());
        map.put(1, FoundationNormalStatus.INIT.getStatus());
        map.put(2, FoundationNormalStatus.COMMITTED.getStatus());
        map.put(3, FoundationNormalStatus.AUDITED.getStatus());
        map.put(4, FoundationNormalStatus.REJECTED.getStatus());
        try {
            logger.info("#traceId={}# [IN][selectDistributorBusinessByCondition] params: distributorBusinessId={} ",rpcHeader.getTraceId(),distributorBusinessId);
            PageHelper.startPage(pageNumber, pageSize);
            List<FoundationDistributorBusiness> foundationDistributorBusinesses = distributorBusinessDao.selectBusinessWithCondition(projectId,
                    distributorBusinessId,
                    distributorName,
                    map.get(status));
            PageInfo<FoundationDistributorBusiness> foundationDistributorBusinessPageInfo = new PageInfo<>(foundationDistributorBusinesses);

            if (foundationDistributorBusinesses.size() == 0){
                logger.info("[selectDistributorBusinessByCondition] 没有拿到经销商数据 #traceId={}, distributorName={}", rpcHeader.getTraceId(), distributorName);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            for (FoundationDistributorBusiness distributorBusiness : foundationDistributorBusinesses){
                DistributorStructure.DistributorBusiness distributorBusiness1 = DistributorStructure.DistributorBusiness.newBuilder()
                        .setDistributorBusinessId(GrpcCommonUtil.getProtoParam(distributorBusiness.getDistributorBusinessId()))
                        .setDistributorBasicId(GrpcCommonUtil.getProtoParam(distributorBusiness.getDistributorBasicId()))
                        .setDistributorName(GrpcCommonUtil.getProtoParam(distributorBusiness.getDistributorName()))
                        .setDistributorEasCode(GrpcCommonUtil.getProtoParam(distributorBusiness.getEasCode()))
                        .setRecordStatus(GrpcCommonUtil.getProtoParam(distributorBusiness.getRecordStatus()))
                        .setProjectId(GrpcCommonUtil.getProtoParam(distributorBusiness.getProjectId()))
                        .setProjectName(GrpcCommonUtil.getProtoParam(distributorBusiness.getProjectName()))
                        .setDistributorPurchaseCouponDiscount(GrpcCommonUtil.getProtoParam(distributorBusiness.getDistributorPurchaseCouponDiscount()))
                        .setActualSaleReturn(GrpcCommonUtil.getProtoParam(distributorBusiness.getActualSaleReturn()))
                        .setShouldSaleReturn(GrpcCommonUtil.getProtoParam(distributorBusiness.getShouldSaleReturn()))
                        .setAccordingRealInventorySale(GrpcCommonUtil.getProtoParam(distributorBusiness.getAccordingRealInventorySale()))
                        .setSettlementType(GrpcCommonUtil.getProtoParam(distributorBusiness.getSettlementType()))
                        .setAccountPeriod(GrpcCommonUtil.getProtoParam(distributorBusiness.getAccountPeriod()))
                        .setCreatedById(GrpcCommonUtil.getProtoParam(distributorBusiness.getCreatedById()))
                        .setCreatedByName(GrpcCommonUtil.getProtoParam(distributorBusiness.getCreatedByName()))
                        .setCreateTime(DateUtil.getTime(distributorBusiness.getCreateTime()))
                        .setLastUpdateTime(DateUtil.getTime(distributorBusiness.getLastUpdateTime()))
                        .setTracelog(distributorBusiness.getTracelog() != null ? distributorBusiness.getTracelog() : "")
                        .build();
                respBuilder.addDistributorBusiness(distributorBusiness1);
            }
            respBuilder.setTotal(foundationDistributorBusinessPageInfo.getTotal());
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT][selectDistributorBusinessByCondition] success params: distributorName={} ", rpcHeader.getTraceId(), distributorName);
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    //登录入口 根据 项目id, AD登录的账号查询 返回DistributorUser对象
    public void getDistiruborAccountByName(DistributorStructure.GetDistiruborAccountByNameReq req,
                                           StreamObserver<DistributorStructure.GetDistiruborAccountByNameResp> responseObserver) {
        DistributorStructure.GetDistiruborAccountByNameResp.Builder respBuilder = DistributorStructure.GetDistiruborAccountByNameResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String userName = req.getUserName();
        long projectId = req.getProjectId();
        try {
            logger.info("#traceId={}# [IN][getDistiruborAccountByName] params: userName={} ",rpcHeader.getTraceId(),userName);
            FoundationDistributorUser user = distributorUserDao.getUserByAccount(projectId, userName);
            if (user == null){
                logger.warn("经销商登录信息获取失败");
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }

            DistributorStructure.DistributorUserDetail userDetail = DistributorStructure.DistributorUserDetail.newBuilder()
                    .setDistributorBusinessId(GrpcCommonUtil.getProtoParam(user.getDistributorBusinessId()))
                    .setDistributorUserId(GrpcCommonUtil.getProtoParam(user.getDistributorUserId()))
                    .setDistributorName(GrpcCommonUtil.getProtoParam(user.getDistributorName()))
                    .setAccount(GrpcCommonUtil.getProtoParam(user.getAccount()))
                    .setPassword(GrpcCommonUtil.getProtoParam(user.getPassword()))
                    .setPriority(GrpcCommonUtil.getProtoParam(user.getPriority()))
                    .setProjectId(GrpcCommonUtil.getProtoParam(user.getProjectId()))
                    .setProjectName(GrpcCommonUtil.getProtoParam(user.getProjectName()))
                    .setRoleInfo(GrpcCommonUtil.getProtoParam(user.getRoleInfo()))
                    .build();

            respBuilder.setDistributorUserDetail(userDetail);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT][getDistiruborAccountByName] params: userName={} ",rpcHeader.getTraceId(),userName);
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }

    }

    //通过账号 获取该账号所有的项目列表 （在约定名字不会重复的场景下用）
     public void getAccountListByaccount(DistributorStructure.GetAccountListByaccountReq req,
                                                  StreamObserver<DistributorStructure.GetAccountListByaccountResp> responseObserver) {
         DistributorStructure.GetAccountListByaccountResp.Builder respBuilder = DistributorStructure.GetAccountListByaccountResp.newBuilder();
         GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
         String account = req.getAccount();
         try {
             logger.info("#traceId={}# [IN][getAccountListByaccount] params: account={} ",rpcHeader.getTraceId(),account);
             List<FoundationDistributorUser> foundationDistributorUsers = distributorUserDao.selectUserListByAccount(account);
             if (foundationDistributorUsers.size() == 0){
                 logger.warn("没有拿到账号为{}的账号数据",account);
                 responseObserver.onNext(respBuilder.build());
                 responseObserver.onCompleted();
                 return;
             }
             for (FoundationDistributorUser user : foundationDistributorUsers){
                 DistributorStructure.DistributorUserDetail userDetail = DistributorStructure.DistributorUserDetail.newBuilder()
                         .setDistributorBusinessId(GrpcCommonUtil.getProtoParam(user.getDistributorBusinessId()))
                         .setDistributorUserId(GrpcCommonUtil.getProtoParam(user.getDistributorUserId()))
                         .setDistributorName(GrpcCommonUtil.getProtoParam(user.getDistributorName()))
                         .setAccount(GrpcCommonUtil.getProtoParam(user.getAccount()))
                         .setPassword(GrpcCommonUtil.getProtoParam(user.getPassword()))
                         .setPriority(GrpcCommonUtil.getProtoParam(user.getPriority()))
                         .setProjectId(GrpcCommonUtil.getProtoParam(user.getProjectId()))
                         .setProjectName(GrpcCommonUtil.getProtoParam(user.getProjectName()))
                         .setRoleInfo(GrpcCommonUtil.getProtoParam(user.getRoleInfo()))
                         .build();
                 respBuilder.addDistributorUserDetail(userDetail);
             }
             responseObserver.onNext(respBuilder.build());
             responseObserver.onCompleted();
             logger.info("#traceId={}# [OUT][getAccountListByaccount] params: account={} ",rpcHeader.getTraceId(),account);

         }catch (Exception e){
             logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
             throw e;
         }
    }
}
