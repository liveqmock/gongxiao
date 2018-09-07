package com.yhglobal.gongxiao.phjd.common.service.impl;

import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.phjd.common.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 公共模块实现
 *
 * @author weizecheng
 * @date 2018/8/30 10:29
 */
@Service
public class CommonServiceImpl implements CommonService {

    private static Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Override
    public ChannelServiceStructure.FoundationXpsSourceChannel getChannelByChannelIdResp(GongxiaoRpc.RpcHeader rpcHeader, String channelId) {
        ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = null;
        logger.info("#getTraceId()={}# [IN][getChannelByChannelIdResp] params: channelId={}"
                , rpcHeader.getTraceId(),channelId );
        try{
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(channelId).build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            return sourceChannel;
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT]  getChannelByChannelIdResp errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ProjectStructure.Project getByProjectIdResp(GongxiaoRpc.RpcHeader rpcHeader, String projectId) {
        ProjectStructure.Project project = null;
        logger.info("#getTraceId()={}# [IN][getByProjectIdResp] params: projectId={}"
                , rpcHeader.getTraceId(),projectId);
        try {
            ProjectServiceGrpc.ProjectServiceBlockingStub couponStub =
                    RpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.GetByProjectIdReq.Builder projectBuilder = ProjectStructure.GetByProjectIdReq.newBuilder();
            ProjectStructure.GetByProjectIdReq projectIdReq =
                    projectBuilder
                            .setRpcHeader(rpcHeader)
                            .setProjectId(projectId )
                            .build();
            ProjectStructure.GetByProjectIdResp projectIdResp = couponStub.getByProjectId(projectIdReq);
            project = projectIdResp.getProject();
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT]  getByProjectIdResp errorMessage: " + e.getMessage(), e);
        }
        return project;

    }

    @Override
    public ProductStructure.GetByProductModelResp getByProductModel(GongxiaoRpc.RpcHeader rpcHeader, Long projectId, String productCode) {
        ProductStructure.GetByProductModelResp productModelResp = null;
        logger.info("#getTraceId()={}# [IN][getByProductModel] params: projectId={},productCode={}"
                , rpcHeader.getTraceId(),projectId,productCode);
        try {
            ProductServiceGrpc.ProductServiceBlockingStub productService = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
            ProductStructure.GetByProductModelReq getByProductModelReq = ProductStructure.GetByProductModelReq.newBuilder().setRpcHeader(rpcHeader)
                    .setProductModel(productCode)
                    .setProjectId(projectId).build();
            productModelResp = productService.getByProductModel(getByProductModelReq);
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT]  getByProductModel errorMessage: " + e.getMessage(), e);
        }
        return productModelResp;
    }
}
