package com.yhglobal.gongxiao.payment.flow.service;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.payment.flow.YhglobalInoutFlow;

import java.util.List;

/**
 * 越海流水表Service
 *
 * @Author: 葛灿
 */
public interface YhglobalInoutFlowService {

    /**
     * 通过项目id查找
     *
     * @param rpcHeader rpc调用的header
     * @param pageNo    页码
     * @param pageSize  条数
     * @param projectId 项目id
     * @return
     */
    List<YhglobalInoutFlow> selectYhglobalInoutFlowListByProjectId(RpcHeader rpcHeader, int pageNo, int pageSize, long projectId);
}
