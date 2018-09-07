package com.yhglobal.gongxiao.foundation.project.service;


import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.project.model.Project;

import java.util.List;

public interface ProjectService {



    int insertProject(RpcHeader rpcHeader,Project record);
    /**
     * 查询所有的项目
     *
     * @param rpcHeader rpc调用的header
     * @return
     */
    List<Project> selectProjectList(RpcHeader rpcHeader);

    /**
     * 通过项目id获取该项目的信息
     *
     * @param rpcHeader rpc调用的header
     * @param projectId  项目id
     * @return
     */
    Project getByProjectId(RpcHeader rpcHeader, String projectId);

    /**
     * 通过项目名获取该项目的信息
     * @param easProjectName  EAS项目名
     * @return
     */
    Project getByEASProjectName(String easProjectName);

}
