package com.yhglobal.gongxiao.foundation.area.dao;

import com.yhglobal.gongxiao.foundation.area.dao.mapping.FoundationDistributorShippingAddressMapper;
import com.yhglobal.gongxiao.foundation.area.model.FoundationDistributorShippingAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Repository
public class FoundationDistributorShippingAddressDao {

    @Autowired
    FoundationDistributorShippingAddressMapper foundationDistributorShippingAddressMapper;

    /**
     * 新增新的收货地址
     * @param foundationDistributorShippingAddress
     * @return
     */
    public int insertShippingAddress(FoundationDistributorShippingAddress foundationDistributorShippingAddress){
        return foundationDistributorShippingAddressMapper.insert(foundationDistributorShippingAddress);
    }


    /**
     * 通过addressid获取shipping address
     * @param addressId
     * @return
     */
    public FoundationDistributorShippingAddress getAddressByAddressId(long addressId){
        return foundationDistributorShippingAddressMapper.getAddressByAddressId(addressId);
    }

    /**
     * 获取经销商默认地址
     * @param distributorId
     * @return
     */
    public FoundationDistributorShippingAddress getDefaultAddress(long distributorId){
        return foundationDistributorShippingAddressMapper.getDefaultAddress(distributorId);
    }

    /**
     * 获取经销商已审核地址列表
     * @param distributorID
     * @return
     */
    public List<FoundationDistributorShippingAddress> selectAuditedAddressListByDistributor(long distributorID){
        return foundationDistributorShippingAddressMapper.selectAuditedAddressListByDistributor(distributorID);
    }

    /**
     * 变更经销商收获地址状态
     * @param addressId 经销商地址ID
     * @param auditStatus 0初始化 1 已提交,2 已审核,3 已驳回 9已废弃
     * @return
     */
    public int updateShippingAddressStatus(long addressId,byte auditStatus){
        return foundationDistributorShippingAddressMapper.updateShippingAddressStatus(addressId,auditStatus);
    }

    /**
     * 根据状态获取经销商收获地址列表
     * @param distributorId
     * @param distributorName
     * @param auditStatus
     * @return
     */
    public List<FoundationDistributorShippingAddress> selectShippingAddressListByStatus(long distributorId,String distributorName,int auditStatus){
        return foundationDistributorShippingAddressMapper.selectCustomerSalePlanList(distributorId,distributorName,auditStatus);
    }

    /**
     * 根据状态获取经销商收获地址列表
     * @param distributorId
     * @return
     */
    public List<FoundationDistributorShippingAddress> selectAllShippingAddressListByDistributorId(long distributorId){
        return foundationDistributorShippingAddressMapper.selectShippingAddressByDistributorId(distributorId);
    }

    /**
     * 设置默认地址
     * @param distributorId 经销商ID
     * @param isDefaultAddress 0 不是默认地址 1 默认地址
     * @return
     */
    public int setDefaultAddress(long distributorId,byte isDefaultAddress){
        return foundationDistributorShippingAddressMapper.setDefaultAddress(distributorId,isDefaultAddress);
    }
}
