package com.yhglobal.gongxiao.foundation.distributor.dao;

import com.yhglobal.gongxiao.foundation.distributor.dao.mapping.FoundationDistributorBusinessMapper;
import com.yhglobal.gongxiao.foundation.distributor.model.FoundationDistributorBusiness;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 经销商业务信息
 *
 * @author: 陈浩
 **/
@Repository
public class FoundationDistributorBusinessDao {

    @Autowired
    FoundationDistributorBusinessMapper foundationDistributorBusinessMapper;

    /**
     * 新增经销商业务信息
     *
     * @param foundationDistributorBusiness
     * @return
     */
    public int insertDistributorBusiness(FoundationDistributorBusiness foundationDistributorBusiness) {
        return foundationDistributorBusinessMapper.insert(foundationDistributorBusiness);
    }

    /**
     * 通过经销商业务id获取经销商业务信息
     *
     * @param distributorBusinessId
     * @return
     */
    public FoundationDistributorBusiness getDistributorBusinessInfo(long distributorBusinessId) {
        return foundationDistributorBusinessMapper.getByDistributorBusinessId(distributorBusinessId);
    }

    /**
     * 通过经销商基础信息ID获取经销商业务信息列表
     *
     * @param distributorBasicId
     * @return
     */
    public List<FoundationDistributorBusiness> selectBusinessListByBasicId(long distributorBasicId) {
        return foundationDistributorBusinessMapper.selectByDistributorBasicId(distributorBasicId);
    }

    /**
     * 通过项目ID获取经销商业务列表
     *
     * @param projectId
     * @return
     */
    public List<FoundationDistributorBusiness> selectBusinessListByProjectId(long projectId) {
        return foundationDistributorBusinessMapper.selectBusinessListByProjectId(projectId);
    }

    /**
     * 条件查询经销商列表
     * @param projectId 项目ID
     * @param easCode eas编码
     * @param distributorName 经销商名称
     * @param recordStatus 状态
     * @return
     */
    public List<FoundationDistributorBusiness> selectBusinessByCondition(long projectId,
                                                                         String easCode,
                                                                         String distributorName,
                                                                         byte recordStatus) {
        return foundationDistributorBusinessMapper.selectBusinessByCondition(projectId,
                easCode,
                distributorName,
                recordStatus);
    }

    /**
     *
     * @param projectId 项目ID
     * @param distributorBusinessId 经销商业务ID
     * @param distributorName   经销商名称
     * @param recordStatus 状态
     * @return
     */
    public List<FoundationDistributorBusiness> selectBusinessWithCondition(long projectId,
                                                                           long distributorBusinessId,
                                                                           String distributorName,
                                                                           byte recordStatus){
        return foundationDistributorBusinessMapper.selectBusinessWithCondition(projectId,
                distributorBusinessId,
                distributorName,
                recordStatus);
    }



}
