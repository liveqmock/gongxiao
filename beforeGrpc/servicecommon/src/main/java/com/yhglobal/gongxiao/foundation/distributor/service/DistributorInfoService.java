package com.yhglobal.gongxiao.foundation.distributor.service;




import com.yhglobal.gongxiao.foundation.distributor.model.DistributorDetail;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public interface DistributorInfoService {

    /**
     * 新增新的经销商信息
     * @param distributorDetail
     * @return
     */
    public int insertDistributorInfo(DistributorDetail distributorDetail);

    /**
     * 更新经销商信息
     * @param distributorDetail
     * @return
     */
    public int updateDistributorInfo(DistributorDetail distributorDetail);

    /**
     * 新增新的经销商项目关系
     * @param distributorProjectId
     * @param projectId
     * @param account
     * @param password
     * @return
     */
    public int addDistributorRelation(long distributorProjectId,String projectId,String account,String password);

    /**
     * 通过详情增加经销商跟项目关系
     * @param distributorDetail 经销商详情
     * @param projectId 项目ID
     * @return
     */
    public int addDistributorRelation(DistributorDetail distributorDetail,String projectId);

    /**
     * 获取经销商详情
     * @param projectId
     * @param distributorUserId
     * @return
     */
    public DistributorDetail getDistributorDetail(String projectId,long distributorUserId);

    /**
     * 获取经销商列表
     * @param projectId 项目ID
     * @param easDistributorCode
     * @param distributorName
     * @param status 1 未提交 2 已提交 3 已审核 4 已驳回
     * @return
     */
    public List<DistributorDetail> selectDistributorDetailList(String projectId,String easDistributorCode,String distributorName,int status);

    /**
     * 审核经销商
     * @param distributorProjectId 经销商项目ID
     * @param auditStatus 1 审核通过 2 驳回
     * @return
     */
    public int auditDistributorProject(int distributorProjectId,int auditStatus);


}
