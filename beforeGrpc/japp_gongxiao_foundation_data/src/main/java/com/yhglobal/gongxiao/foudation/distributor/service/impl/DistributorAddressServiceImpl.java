package com.yhglobal.gongxiao.foudation.distributor.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.distributor.dao.DistributorShippingAddressDao;
import com.yhglobal.gongxiao.foundation.distributor.dao.DistributorShippingPreferenceDao;
import com.yhglobal.gongxiao.foundation.distributor.model.DistributorShippingAddress;
import com.yhglobal.gongxiao.foundation.distributor.model.DistributorShippingPreference;
import com.yhglobal.gongxiao.foundation.distributor.service.DistributorAddressService;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 经销商地址实现类
 *
 * @author: 陈浩
 **/
@Service
public class DistributorAddressServiceImpl implements DistributorAddressService {
    private static final Logger logger = LoggerFactory.getLogger(DistributorAddressServiceImpl.class);


    @Autowired
    DistributorShippingPreferenceDao distributorShippingPreferenceDao;

    @Autowired
    DistributorShippingAddressDao distributorShippingAddressDao;

    @Override
    public List<DistributorShippingAddress> selectADPreferenceList(RpcHeader rpcHeader,
                                                                   String distributorId) {
        logger.info("#traceId={}# [IN][selectADPreferenceList] params: distributorId={}", rpcHeader, distributorId);
        try {
            //获取经销商偏好地址信息
            DistributorShippingPreference distributorShippingPreference =
                    distributorShippingPreferenceDao.getPreferenceAddressById(distributorId);
            int defaultAddressId = distributorShippingPreference.getDefaultAddressId();
            int lastSelectedAddressId = distributorShippingPreference.getLastSelectedAddressId();
            //获取经销商地址信息
            DistributorShippingAddress defaultAddress = distributorShippingAddressDao.getByAddressId(defaultAddressId + "");
            DistributorShippingAddress lastAddress = distributorShippingAddressDao.getByAddressId(lastSelectedAddressId + "");
            List<DistributorShippingAddress> distributorShippingAddressList = new ArrayList<>();
            if (defaultAddress != null) {
                distributorShippingAddressList.add(defaultAddress);
            }
            if (lastAddress != null) {
                distributorShippingAddressList.add(lastAddress);
            }
            logger.info("#traceId={}# [OUT] selectADPreferenceList success", rpcHeader.traceId);
            return distributorShippingAddressList;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public List<DistributorShippingAddress> selectDistributorList(RpcHeader rpcHeader, String distributorId) {
        logger.info("#traceId={}# [IN][selectDistributorList] params: distributorId={}", rpcHeader, distributorId);

        try {
            List<DistributorShippingAddress> resultList = new ArrayList<>();

            List<DistributorShippingAddress> distributorShippingAddressList
                    = distributorShippingAddressDao.selectAddressListById(distributorId);
            if (distributorShippingAddressList.size() == 0) {
                logger.info("#traceId={}# [OUT] fail to selectDistributorList: distributorId={} Currency=NULL", rpcHeader.traceId, distributorId);
                return null;
            } else {
                DistributorShippingPreference preferenceAddress = distributorShippingPreferenceDao.getPreferenceAddressById(distributorId);
                //地址偏好不为空
                if (preferenceAddress != null) {
                    int defaultAddressId1 = preferenceAddress.getDefaultAddressId();
                    DistributorShippingAddress defaultAddress = null;
                    //默认地址不为空
                    if (defaultAddressId1!=0){
                        for (DistributorShippingAddress distributorShippingAddress : distributorShippingAddressList) {
                            long addressId = distributorShippingAddress.getAddressId();
                            if (addressId == defaultAddressId1) {
                                defaultAddress = distributorShippingAddress;
                                break;
                            }
                        }
                        distributorShippingAddressList.remove(defaultAddress);
                        resultList.add(defaultAddress);
                    }
                    resultList.addAll(distributorShippingAddressList);
                } else {
                    return distributorShippingAddressList;
                }

                logger.info("#traceId={}# [OUT] selectDistributorList success :distributorShippingAddressList.size={}", rpcHeader.traceId, distributorShippingAddressList.size());
                return resultList;
            }
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<DistributorShippingAddress> selectValidAddress(RpcHeader rpcHeader, String distributorId) {
        logger.info("#traceId={}# [IN][selectValidAddress] params: distributorId={}", rpcHeader, distributorId);

        try {
            List<DistributorShippingAddress> resultList = new ArrayList<>();

            List<DistributorShippingAddress> distributorShippingAddressList
                    = distributorShippingAddressDao.selectValidAddressListById(distributorId);
            if (distributorShippingAddressList.size() == 0) {
                logger.info("#traceId={}# [OUT] fail to selectValidAddress: distributorId={} Currency=NULL", rpcHeader.traceId, distributorId);
                return null;
            } else {
                DistributorShippingPreference preferenceAddress = distributorShippingPreferenceDao.getPreferenceAddressById(distributorId);
                //地址偏好不为空
                if (preferenceAddress != null) {
                    int defaultAddressId1 = preferenceAddress.getDefaultAddressId();
                    DistributorShippingAddress defaultAddress = null;
                    //默认地址不为空
                    if (defaultAddressId1!=0){
                        for (DistributorShippingAddress distributorShippingAddress : distributorShippingAddressList) {
                            long addressId = distributorShippingAddress.getAddressId();
                            if (addressId == defaultAddressId1) {
                                defaultAddress = distributorShippingAddress;
                                break;
                            }
                        }
                        distributorShippingAddressList.remove(defaultAddress);
                        resultList.add(defaultAddress);
                    }
                    resultList.addAll(distributorShippingAddressList);
                } else {
                    return distributorShippingAddressList;
                }

                logger.info("#traceId={}# [OUT] selectValidAddress success :distributorShippingAddressList.size={}", rpcHeader.traceId, distributorShippingAddressList.size());
                return resultList;
            }
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int insertAddress(RpcHeader rpcHeader, boolean isDefaultAddress, DistributorShippingAddress distributorShippingAddress) {
        logger.info("#traceId={}# [IN][insertAddress] params: isDefaultAddress={}", rpcHeader, isDefaultAddress);
        try {
            int row = distributorShippingAddressDao.createDistributorShippingAddress(distributorShippingAddress);
            long addressId = distributorShippingAddress.getAddressId();

            Long distributorId = distributorShippingAddress.getDistributorId();
            //如果该条数据是默认地址
            if (isDefaultAddress) {
                DistributorShippingPreference distributorShippingPreference
                        = distributorShippingPreferenceDao.getPreferenceAddressById(distributorId + "");
                //如果数据库没有该经销商的偏好信息,插入新的
                if (distributorShippingPreference == null) {
                    distributorShippingPreference = new DistributorShippingPreference();
                    distributorShippingPreference.setDefaultAddressId((int) addressId);
                    distributorShippingPreference.setDistributorId(distributorShippingAddress.getDistributorId());
                    distributorShippingPreference.setDistributorName(distributorShippingAddress.getDistributorName());
                    distributorShippingPreferenceDao.insert(distributorShippingPreference);
                } else {
                    //如果数据库已经有该经销商的偏好信息,更新默认地址
                    distributorShippingPreference.setDefaultAddressId((int) addressId);
                    distributorShippingPreferenceDao.updateByPrimaryKey(distributorShippingPreference);
                }
            }
            logger.info("#traceId={}# [OUT] insertAddress success :", rpcHeader.traceId);
            return 1;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int deleteAddress(RpcHeader rpcHeader, String distributorId, int addressId) {
        logger.info("#traceId={}# [IN][deleteAddress] params: distributorId={}, addressId={}", rpcHeader, distributorId, addressId);
        try {
            //废除该地址
            distributorShippingAddressDao.cancelAddress(addressId);
            //设置经销商偏好地址信息
            DistributorShippingPreference distributorShippingPreference =
                    distributorShippingPreferenceDao.getPreferenceAddressById(distributorId);
            int defaultAddressId = distributorShippingPreference.getDefaultAddressId();
            int lastSelectedAddressId = distributorShippingPreference.getLastSelectedAddressId();
            boolean isChange = false;
            //废除的地址是默认地址
            if (defaultAddressId == addressId) {
                distributorShippingPreference.setDefaultAddressId(0);
                isChange = true;
            }
            //废除的地址市上次选择的地址
            if (lastSelectedAddressId == addressId) {
                distributorShippingPreference.setLastSelectedAddressId(0);
                isChange = true;
            }
            if (isChange) {
                distributorShippingPreferenceDao.updateByPrimaryKey(distributorShippingPreference);
            }
            logger.info("#traceId={}# [OUT] deleteAddress success", rpcHeader.traceId);
            return 1;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int setDefaultAddress(RpcHeader rpcHeader, String distributorId, int addressId) {
        DistributorShippingPreference preferenceAddress = distributorShippingPreferenceDao.getPreferenceAddressById(distributorId);
        int defaultAddressId = preferenceAddress.getDefaultAddressId();
        if (addressId == defaultAddressId) {
            return 0;
        }
        preferenceAddress.setLastSelectedAddressId(defaultAddressId);
        preferenceAddress.setDefaultAddressId(addressId);
        distributorShippingPreferenceDao.updateByPrimaryKey(preferenceAddress);
        return 1;
    }

    @Override
    public PageInfo<DistributorShippingAddress> selectNeedAuditAddressList(RpcHeader rpcHeader,
                                                                           String projectId,
                                                                           String distributorAccount,
                                                                           String distributorName,
                                                                           int auditStatus,
                                                                           int pageNumber,
                                                                           int pageSize) {
        logger.info("#traceId={}# [IN][insertAddress] params:  projectId={}, distributorAccount={}, distributorName={}",
                rpcHeader, projectId, distributorAccount, distributorName);
        try {
            PageHelper.startPage(pageNumber, pageSize);
            List<DistributorShippingAddress> distributorShippingAddresses = distributorShippingAddressDao.selectNeedAuditAddressList(projectId, distributorAccount, distributorName, auditStatus);
            PageInfo<DistributorShippingAddress> addressPageInfo = new PageInfo<DistributorShippingAddress>(distributorShippingAddresses);

            if (distributorShippingAddresses.size() == 0) {
                logger.info("#traceId={}# [OUT] selectNeedAuditAddressList false", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] selectNeedAuditAddressList success: distributorShippingAddresses.size()={}", rpcHeader.traceId, distributorShippingAddresses.size());
            }
            return addressPageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public int auditAddress(RpcHeader rpcHeader, String projectId, int addressId) {
        logger.info("#traceId={}# [IN][auditAddress] params: projectId={}, addressId={}",
                rpcHeader, projectId, addressId);
        try {
            int num = distributorShippingAddressDao.auditAddress(addressId);
            DistributorShippingAddress distributorShippingAddress = distributorShippingAddressDao.getByAddressId(addressId + "");

            DistributorShippingPreference distributorShippingPreference = distributorShippingPreferenceDao.getPreferenceAddressById(distributorShippingAddress.getDistributorId() + "");
            if (distributorShippingPreference == null) {
                distributorShippingPreference = new DistributorShippingPreference();
                distributorShippingPreference.setDefaultAddressId((int) distributorShippingAddress.getAddressId());
                distributorShippingPreference.setDistributorId(distributorShippingAddress.getDistributorId());
                distributorShippingPreference.setDistributorName(distributorShippingAddress.getDistributorName());
                distributorShippingPreference.setCreateTime(new Date());
                distributorShippingPreference.setLastUpdateTime(new Date());
                distributorShippingPreferenceDao.insert(distributorShippingPreference);
            } else {
                if (distributorShippingPreference.getLastSelectedAddressId() == 0) {
                    distributorShippingPreference.setLastSelectedAddressId((int) distributorShippingAddress.getAddressId());
                    distributorShippingPreferenceDao.updateByPrimaryKey(distributorShippingPreference);
                }
            }
            if (num == 0) {
                logger.info("#traceId={}# [OUT] auditAddress false", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] auditAddress success: ", rpcHeader.traceId);
            }
            return num;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int rejectAddress(RpcHeader rpcHeader, String projectId, int addressId) {
        logger.info("#traceId={}# [IN][rejectAddress] params: projectId={}, addressId={}",
                rpcHeader, projectId, addressId);
        try {
            int num = distributorShippingAddressDao.rejectAddress(addressId);
            if (num == 0) {
                logger.info("#traceId={}# [OUT] rejectAddress false", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] rejectAddress success: ", rpcHeader.traceId);
            }
            return num;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


}
