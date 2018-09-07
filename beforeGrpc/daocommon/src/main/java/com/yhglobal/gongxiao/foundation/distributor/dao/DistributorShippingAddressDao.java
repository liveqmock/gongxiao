package com.yhglobal.gongxiao.foundation.distributor.dao;

import com.yhglobal.gongxiao.foundation.distributor.dao.mapping.DistributorShippingAddressMapper;
import com.yhglobal.gongxiao.foundation.distributor.model.DistributorShippingAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DistributorShippingAddressDao {
    @Autowired
    private DistributorShippingAddressMapper distributorShippingAddressMapper;

    /**
     * 创建新的地址
     * @param record
     * @return
     */
    public int createDistributorShippingAddress(DistributorShippingAddress record) {
        return distributorShippingAddressMapper.insert(record);
    }

    public DistributorShippingAddress selectByIdAndName(Long distributorId, String distributorName){
        return distributorShippingAddressMapper.selectByIdAndName(distributorId,distributorName);
    }

    public List<DistributorShippingAddress> selectAddressListById(String distributorId){
        return distributorShippingAddressMapper.selectAddressListByAD(distributorId);
    }

    public List<DistributorShippingAddress> selectValidAddressListById(String distributorId){
        return distributorShippingAddressMapper.selectValidAddressListById(distributorId);
    }

    public DistributorShippingAddress getByAddressId(String addressId){
        return distributorShippingAddressMapper.getByAddressId(addressId);
    }

    public int cancelAddress(int addressId){
        return distributorShippingAddressMapper.cancelAddress(addressId);
    }

    /**
     * 获取需审核的地址列表
     * @param projectId   必填  项目ID
     * @param distributorId   选填  经销商编码
     * @param distributorName 选填  经销商名称
     * @return
     */
    public List<DistributorShippingAddress> selectNeedAuditAddressList( String projectId,
                                                                       String distributorId,
                                                                       String distributorName,
                                                                        int auditStatus){
        return distributorShippingAddressMapper.selectNeedAuditAddressList(projectId,distributorId,distributorName,auditStatus);
    }

    /**
     * 审核地址
     * @param addressId 地址ID
     * @return
     */
    public int auditAddress(int addressId){
        return distributorShippingAddressMapper.auditAddress(addressId);
    }

    /**
     * 拒绝地址申请
     * @param addressId 地址ID
     * @return
     */
    public int rejectAddress(int addressId){
        return distributorShippingAddressMapper.rejectAddress(addressId);
    }

    public int updateByPrimaryKey(DistributorShippingAddress record){
        return distributorShippingAddressMapper.updateByPrimaryKey(record);
    }
}
