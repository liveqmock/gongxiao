package com.yhglobal.gongxiao.phjd.common.service;

import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;

/**
 * 公共服务Service调用
 *
 * @author weizecheng
 * @date 2018/8/30 10:22
 */
public interface CommonService {

    /**
     * 获取通道验证
     *
     * @author weizecheng
     * @date 2018/8/30 10:27
     * @param rpcHeader 请求头
     * @param channelId 通道Id
     * @return
     */
    ChannelServiceStructure.FoundationXpsSourceChannel getChannelByChannelIdResp(GongxiaoRpc.RpcHeader rpcHeader,String channelId);

    /**
     * 根据项目Id获取项目信息
     *
     * @author weizecheng
     * @date 2018/8/30 10:42
     * @param rpcHeader 请求头部
     * @param projectId 项目Id
     * @return 项目信息
     */
    ProjectStructure.Project getByProjectIdResp(GongxiaoRpc.RpcHeader rpcHeader, String projectId);

    /**
     * 获取商品的基本信息
     *
     * @author weizecheng
     * @date 2018/8/30 10:58
     * @param rpcHeader 相关头部
     * @param projectId 项目Id
     * @param productCode 商品编码
     * @return 商品信息
     */
    ProductStructure.GetByProductModelResp getByProductModel(GongxiaoRpc.RpcHeader rpcHeader, Long projectId, String productCode);
}
