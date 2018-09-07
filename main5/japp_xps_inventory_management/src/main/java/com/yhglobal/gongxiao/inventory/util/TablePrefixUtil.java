package com.yhglobal.gongxiao.inventory.util;

import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import org.apache.axis.utils.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TablePrefixUtil {
    //Map<projectId, tablePrefix>
    private static Map<Long, String> tablePrefixMap = new ConcurrentHashMap<>(); //缓存

    /**
     * 根据projectId获取对应的表前缀
     *
     * 注：该接口针对需根据traceId追踪请求的场景，此时rpcHeader通过参数传进来
     */
    public static String getTablePrefixByProjectId(GongxiaoRpc.RpcHeader rpcHeader, Long projectId) {
        String prefix = tablePrefixMap.get(projectId); //从缓存获取
        if (prefix != null)    return prefix;
        //缓存中没有 则走rpc获取
        ProjectServiceGrpc.ProjectServiceBlockingStub projectStub = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
        ProjectStructure.GetByProjectIdReq.Builder getByProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(projectId + "");
        ProjectStructure.GetByProjectIdResp getByProjectResp = projectStub.getByProjectId(getByProjectIdReq.build());
        ProjectStructure.Project project = getByProjectResp.getProject();
        prefix = project.getProjectTablePrefix();
        if (StringUtils.isEmpty(prefix)) {
            throw new IllegalStateException("table prefix not found: projectId=" + projectId);
        }
        return prefix;
    }

    /**
     * 根据projectId获取对应的表前缀
     *
     * 注：该接口针对无须traceId追踪的场景
     */
    public static String getTablePrefixByProjectId(Long projectId) {
        String prefix = tablePrefixMap.get(projectId); //从缓存获取
        if (prefix != null)    return prefix;
        //缓存中没有 则走rpc获取
        String clientName = TablePrefixUtil.class.getName();
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(clientName, "", clientName); //新构造RpcHeader
        ProjectServiceGrpc.ProjectServiceBlockingStub projectStub = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
        ProjectStructure.GetByProjectIdReq.Builder getByProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(String.valueOf(projectId));
        ProjectStructure.GetByProjectIdResp getByProjectResp = projectStub.getByProjectId(getByProjectIdReq.build());
        ProjectStructure.Project project = getByProjectResp.getProject();
        prefix = project.getProjectTablePrefix();
        if (StringUtils.isEmpty(prefix)) {
            throw new IllegalStateException("table prefix not found: projectId=" + projectId);
        }
        return prefix;
    }

}
