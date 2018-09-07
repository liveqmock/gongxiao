package com.yhglobal.gongxiao.foundation.distributor.service;


import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.portal.user.model.DistributorPortalUser;

import java.util.Date;
import java.util.List;

public interface DistributorPortalUserService {

    /**
     * 新建分销商用户
     *
     * @param rpcHeader       rpc调用的header
     * @param userId          用户ID
     * @param userName       用户名称
     * @param passwd        密码
     * @param userStatus    用户状态
     * @param projectId     项目ID
     * @param projectName   项目名称
     * @param distributorId 经销商ID
     * @param distributorName   经销商名称
     * @param phoneNumber   电话号码
     * @param isAdmin
     * @param tracelog
     * @param createdById
     * @param createdByName
     * @param createTime
     * @return 写入database的记录数量 正确写入返回1 否则返回0
     */
    int createPortalUser(RpcHeader rpcHeader, String userId, String userName, String passwd, Byte userStatus,
                         Long projectId, String projectName, Long distributorId, String distributorName,
                         String phoneNumber, boolean isAdmin, String tracelog,
                         Long createdById, String createdByName, Date createTime);


    /**
     * 通过用户id查询DistributorPortalUser
     *
     * @param rpcHeader               rpc调用的header
     * @param distributorPortalUserId
     * @return
     */
    DistributorPortalUser getPortalUserById(RpcHeader rpcHeader, String distributorPortalUserId);

    /**
     * 通过经销商ID获取经销商登录信息
     * @param rpcHeader
     * @param distributorId 经销商ID
     * @return
     */
    DistributorPortalUser getByDistributorId(RpcHeader rpcHeader, String distributorId);

    /**
     * 通过用户名字查询DistributorPortalUser 注：这里distributorPortalUserName值唯一
     * @param rpcHeader                 rpc调用的header
     * @param distributorPortalUserName 用户名字
     * @return
     */
    DistributorPortalUser getPortalUserByName(RpcHeader rpcHeader, String distributorPortalUserName);


    /**
     * 通过项目id和分销商id 查询该分销商下所有的用户
     *
     * @param rpcHeader     rpc调用的header
     * @param projectId     项目id
     * @param distributorId 分销商id
     * @return 指定项目下指定分销商 所有的用户
     */
    List<DistributorPortalUser> selectPortalUserByProjectIdAndDistributorId(RpcHeader rpcHeader, long projectId, long distributorId);


    /**
     * 选取所有分销商用户
     *
     * @param rpcHeader rpc调用的header
     * @return 所有分销商用户
     */
    List<DistributorPortalUser> selectAll(RpcHeader rpcHeader);

    /**
     * 获取项目下所有客户
     *
     * @param rpcHeader rpc调用的header
     * @param projectId 项目ID
     * @return
     */
    PageInfo<DistributorPortalUser> selectDistributorByProject(RpcHeader rpcHeader,
                                                               String projectId,
                                                               String customerId,
                                                               String customerName,
                                                               int pageNumber,
                                                               int pageSize);


    /**
     * 根据项目ID  客户账号即ID  客户名称 模糊查询
     */
    List<DistributorPortalUser> selectAllByProjectIdAndUserIdAndUserName(RpcHeader rpcHeader,
                                                                         Long projectId,
                                                                         String userId,
                                                                         String userName);

}
