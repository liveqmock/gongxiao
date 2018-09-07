package com.yhglobal.gongxiao.foundation.distributor.service;


import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.distributor.model.DistributorShippingAddress;

import java.util.List;

/**
 * 经销商地址服务类
 *
 * @author: 陈浩
 **/
public interface DistributorAddressService {

    /**
     * 获取默认地址和上次选择地址
     *
     * @param distributorId 经销商地址
     * @return
     */
    List<DistributorShippingAddress> selectADPreferenceList(RpcHeader rpcHeader, String distributorId);

    /**
     * 获取AD所有地址
     *
     * @param distributorId 经销商ID
     * @return
     */
    List<DistributorShippingAddress> selectDistributorList(RpcHeader rpcHeader, String distributorId);


    List<DistributorShippingAddress> selectValidAddress(RpcHeader rpcHeader, String distributorId);


    /**
     * 插入新地址
     *
     * @param rpcHeader
     * @param isDefaultAddress
     * @param distributorShippingAddress
     * @return
     */
    int insertAddress(RpcHeader rpcHeader, boolean isDefaultAddress, DistributorShippingAddress distributorShippingAddress);

    /**
     *
     * @param rpcHeader
     * @param distributorId
     * @param addressId
     * @return
     */
    int deleteAddress(RpcHeader rpcHeader, String distributorId, int addressId);

    /**
     *
     * @param rpcHeader
     * @param distributorId
     * @param addressId
     * @return
     */
    int setDefaultAddress(RpcHeader rpcHeader, String distributorId, int addressId);
///////////////////////////////////////////////////////
//👆 AD
//👇 商务
///////////////////////////////////////////////////////
    /**
     * 获取未审核的地址列表
     *
     * @param rpcHeader
     * @param distributorId
     * @param projectId          必填  项目ID
     * @param distributorAccount 选填  经销商编号
     * @param distributorName    选填  经销商名称
     * @param auditStatus        选填   查询状态 1 新建 2 已审核 3已废弃
     * @return
     */
    PageInfo<DistributorShippingAddress> selectNeedAuditAddressList(RpcHeader rpcHeader,
                                                                    String projectId,
                                                                    String distributorAccount,
                                                                    String distributorName,
                                                                    int auditStatus,
                                                                    int pageNumber,
                                                                    int pageSize);

    /**
     * 审核地址申请
     * @param rpcHeader
     * @param projectId 必填  项目ID
     * @param addressId 必填  地址ID
     * @return
     */
    int auditAddress(RpcHeader rpcHeader,
                     String projectId,
                     int addressId);

    /**
     * 驳回地址申请
     * @param rpcHeader
     * @param projectId 必填  项目ID
     * @param addressId 必填  地址ID
     * @return
     */
    int rejectAddress(RpcHeader rpcHeader,
                      String projectId,
                      int addressId);
}
