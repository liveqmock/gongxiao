package com.yhglobal.gongxiao.foundation.area.dao;

import com.yhglobal.gongxiao.foundation.area.dao.mapping.FoundationDistributorFileAddressMapper;
import com.yhglobal.gongxiao.foundation.area.model.FoundationDistributorFileAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Repository
public class FoundationDistributorFileAddressDao {

    @Autowired
    FoundationDistributorFileAddressMapper foundationDistributorFileAddressMapper;

    /**
     * 插入新的经销商文件地址
     * @param foundationDistributorFileAddress
     * @return
     */
    public int insertFileAddress(FoundationDistributorFileAddress foundationDistributorFileAddress){
        return foundationDistributorFileAddressMapper.insert(foundationDistributorFileAddress);
    }

    /**
     * 获取默认文件地址
     * @param distributorId 经销商ID
     * @return
     */
    public FoundationDistributorFileAddress getDefaultFileAddress (long distributorId){
        return foundationDistributorFileAddressMapper.getDefaultAddress(distributorId);
    }

    /**
     * 获取所有文件地址列表
     * @return
     */
    public List<FoundationDistributorFileAddress> selectAllFileAddressList(){
        return foundationDistributorFileAddressMapper.getAllFileAddressList();
    }

}
