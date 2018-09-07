package com.yhglobal.gongxiao.foudation.distributor.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.distributor.dao.DistributorPortalUserDao;
import com.yhglobal.gongxiao.foundation.distributor.service.DistributorPortalUserService;
import com.yhglobal.gongxiao.foundation.portal.user.model.DistributorPortalUser;
import com.yhglobal.gongxiao.model.PurchaseOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service
public class DistributorPortalUserServiceImpl implements DistributorPortalUserService {
    private static Logger logger = LoggerFactory.getLogger(DistributorPortalUserServiceImpl.class);

    @Autowired
    private DistributorPortalUserDao distributorPortalUserDao;

    @Override
    public int createPortalUser(RpcHeader rpcHeader, String userId, String userName, String passwd, Byte userStatus,
                                Long projectId, String projectName, Long distributorId, String distributorName,
                                String phoneNumber, boolean isAdmin, String tracelog,
                                Long createdById, String createdByName, Date createTime) {
        logger.info("#traceId={}# [IN][getCurrencyByCode] params: userName={}, passwd={}", rpcHeader.traceId, userName, passwd);

        try {
            DistributorPortalUser newPortalUser = new DistributorPortalUser();
            newPortalUser.setUserId(userId);
            newPortalUser.setUserName(userName);
            newPortalUser.setPasswd(passwd);
            newPortalUser.setUserStatus(userStatus);
            newPortalUser.setProjectId(projectId);
            newPortalUser.setProjectName(projectName);
            newPortalUser.setDistributorId(distributorId);
            newPortalUser.setDistributorName(distributorName);
            newPortalUser.setPhoneNumber(phoneNumber);
            newPortalUser.setIsAdmin((byte) (isAdmin ? 1 : 0)); //boolean转换为byte
            newPortalUser.setTracelog(tracelog);
            newPortalUser.setCreatedById(createdById);
            newPortalUser.setCreatedByName(createdByName);
            newPortalUser.setCreateTime(createTime);
            newPortalUser.setCreatedById(createdById);
            int portalUser = distributorPortalUserDao.createDistributorPortalUser(newPortalUser);
            if (portalUser == 0) {
                logger.info("#traceId={}# [OUT] fail to createPortalUser: ", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] createPortalUser success: ", rpcHeader.traceId);
            }
            return portalUser;
        } catch (Exception e) {
            logger.error("[OUT] #traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public DistributorPortalUser getPortalUserById(RpcHeader rpcHeader, String distributorPortalUserId) {
        logger.info("[IN] #traceId={}# [getPortalUserById]: distributorPortalUserId={}", rpcHeader.traceId, distributorPortalUserId);
        try {
            DistributorPortalUser distributorPortalUser = distributorPortalUserDao.getDistributorPortalUserById(distributorPortalUserId);
            if (distributorPortalUser == null) {
                logger.info("#traceId={}# [OUT] fail to getPortalUserById: distributorPortalUserId=NULL", rpcHeader.traceId, distributorPortalUserId);
            } else {
                logger.info("#traceId={}# [OUT] getPortalUserById success: distributorPortalUser={}", rpcHeader.traceId, distributorPortalUser.toString());
            }
            return distributorPortalUser;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public DistributorPortalUser getByDistributorId(RpcHeader rpcHeader, String distributorId) {
        logger.info("[IN] #traceId={}# [getPortalUserById]: distributorPortalUserId={}", rpcHeader.traceId, distributorId);
        try {
            DistributorPortalUser distributorPortalUser = distributorPortalUserDao.getByDistributorId(distributorId);
            if (distributorPortalUser == null) {
                logger.info("#traceId={}# [OUT] fail to getByDistributorId: distributorPortalUserId=NULL", rpcHeader.traceId, distributorId);
            } else {
                logger.info("#traceId={}# [OUT] getByDistributorId success: distributorPortalUser={}", rpcHeader.traceId, distributorPortalUser.toString());
            }
            return distributorPortalUser;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public DistributorPortalUser getPortalUserByName(RpcHeader rpcHeader, String distributorPortalUserName) {
        logger.info("[IN] #traceId={}# [getCurrencyByCode]: distributorPortalUserName={}", rpcHeader.traceId, distributorPortalUserName);
        try {
            DistributorPortalUser distributorPortalUser = distributorPortalUserDao.getDistributorPortalUserByName(distributorPortalUserName);
            if (distributorPortalUser == null) {
                logger.info("#traceId={}# [OUT] fail to getPortalUserByName: distributorPortalUserId=NULL", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT]  getPortalUserByName success: distributorPortalUser={} ", rpcHeader.traceId, distributorPortalUser.toString());
            }
            return distributorPortalUser;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public List<DistributorPortalUser> selectPortalUserByProjectIdAndDistributorId(RpcHeader rpcHeader, long projectId, long distributorId) {
        logger.info("[IN] #traceId={}# [selectPortalUserByProjectIdAndDistributorId]: projectId={}  distributorId={}", rpcHeader.traceId, projectId, distributorId);
        try {
            List<DistributorPortalUser> distributorPortalUsers
                    = distributorPortalUserDao.selectDistributorPortalUserByProjectIdAndDistributorId(projectId, distributorId);
            if (distributorPortalUsers == null) {
                logger.info("#traceId={}# [OUT] fail to getPortalUserById: distributorPortalUsers=null ", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] get selectPortalUserByProjectIdAndDistributorId success: distributorPortalUsers={} ", rpcHeader.traceId, distributorPortalUsers.size());
            }
            return distributorPortalUsers;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<DistributorPortalUser> selectAll(RpcHeader rpcHeader) {
        logger.info("[IN] #traceId={}# [selectAll]", rpcHeader.traceId);
        try {
            List<DistributorPortalUser> distributorPortalUsers = distributorPortalUserDao.selectAll();
            if (distributorPortalUsers == null) {
                logger.info("#traceId={}# [OUT] fail to getPortalUserById: distributorPortalUsers=null ", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] get selectAll success: distributorPortalUsers={} ", rpcHeader.traceId, distributorPortalUsers.size());
            }
            return distributorPortalUsers;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public PageInfo<DistributorPortalUser> selectDistributorByProject(RpcHeader rpcHeader,
                                                                      String projectId,
                                                                      String customerId,
                                                                      String customerName,
                                                                      int pageNumber,
                                                                      int pageSize) {
        logger.info("[IN] #traceId={}# [selectAll] params: projectId={}", rpcHeader.traceId, projectId);
        try {
            PageHelper.startPage(pageNumber, pageSize);
            List<DistributorPortalUser> distributorPortalUsers = distributorPortalUserDao.selectAllByProjectIdAndUserIdAndUserName(Long.parseLong(projectId),
                    customerId,
                    customerName);
            PageInfo<DistributorPortalUser> purchaseOrderPageInfo = new PageInfo<>(distributorPortalUsers);

            if (purchaseOrderPageInfo == null) {
                logger.info("#traceId={}# [OUT] fail to getPortalUserById: distributorPortalUsers=null ", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] get selectAll success: distributorPortalUsers={} ", rpcHeader.traceId, distributorPortalUsers.size());
            }
            return purchaseOrderPageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public List<DistributorPortalUser> selectAllByProjectIdAndUserIdAndUserName(RpcHeader rpcHeader, Long projectId,
                                                                                String distributorId,
                                                                                String distributorName) {
        logger.info("[IN] #traceId={}# [selectAll] params: projectId={}", rpcHeader.traceId, projectId);
        try {
            List<DistributorPortalUser> distributorPortalUsers = distributorPortalUserDao.selectAllByProjectIdAndUserIdAndUserName(projectId, distributorId, distributorName);
            if (distributorPortalUsers == null) {
                logger.info("#traceId={}# [OUT] fail to selectAllByProjectIdAndUserIdAndUserName: distributorPortalUsers=null ", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] get selectAllByProjectIdAndUserIdAndUserName success: distributorPortalUsers={} ", rpcHeader.traceId, distributorPortalUsers.size());
            }
            return distributorPortalUsers;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }
}
