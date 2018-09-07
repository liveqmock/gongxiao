package com.yhglobal.gongxiao.foundation.distributor.dao;

import com.yhglobal.gongxiao.foundation.constant.FoundationStatusCode;
import com.yhglobal.gongxiao.foundation.distributor.dao.mapping.FoundationDistributorBasicMapper;
import com.yhglobal.gongxiao.foundation.distributor.model.FoundationDistributorBasic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



@Repository
public class FoundationDistributorBasicDao {

    @Autowired
    private FoundationDistributorBasicMapper foundationDistributorBasicMapper;

    /**
     * 插入经销商基础信息表
     * @param foundationDistributorBasic
     * @return
     */
    public int insertDistributor( FoundationDistributorBasic foundationDistributorBasic){
        return foundationDistributorBasicMapper.insert(foundationDistributorBasic);
    }

    /**
     * 通过 distributorBasicId 获取经销商基础信息表明细
     * @param distributorBasicId
     * @return
     */
    public FoundationDistributorBasic getByDistributorBasicId(long distributorBasicId){
        return foundationDistributorBasicMapper.getByDistributorBasicId(distributorBasicId);
    }

    /**
     * 作废经销商基础信息表
     * @param distributorBasicId
     * @return
     */
    public int cancelDistributorBasicInfo(long distributorBasicId){
        return foundationDistributorBasicMapper.updateDistributorBasicInfo(distributorBasicId, FoundationStatusCode.ABANDON.getStatusCode());
    }

    /**
     * 审核经销商基础信息
     * @param distributorBasicId
     * @return
     */
    public int auditDistributorBasicInfo(long distributorBasicId){
        return foundationDistributorBasicMapper.updateDistributorBasicInfo(distributorBasicId, FoundationStatusCode.AUDIT.getStatusCode());
    }

    /**
     * 拒绝经销商基础信息
     * @param distributorBasicId
     * @return
     */
    public int refuseDistributorBasicInfo(long distributorBasicId){
        return foundationDistributorBasicMapper.updateDistributorBasicInfo(distributorBasicId,FoundationStatusCode.REJECT.getStatusCode());
    }

}
