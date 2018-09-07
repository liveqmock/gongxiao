package com.yhglobal.gongxiao.foundation.product.product.service;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.product.product.model.Product;
import com.yhglobal.gongxiao.foundation.product.product.model.ProductDetail;

import java.util.List;

/**
 * 货品服务类-新
 *
 * @author: 陈浩
 **/
public interface ProductService {

    /**
     * 新增新的货品
     * @param productDetail
     * @return
     */
    int addProduct(RpcHeader rpcHeader,ProductDetail productDetail);

    /**
     * 提交货品审核(新建一条product记录 项目ID变化)
     * @param projectId 项目id
     * @param id 货品ID
     * @return
     */
    int submitProduct(RpcHeader rpcHeader,String projectId,long id);

    /**
     * 驳回货品
     * @param id 货品ID
     * @return
     */
    int rebutProduct(RpcHeader rpcHeader,long id);

    /**
     * 审核货品
     * @param id 货品ID
     * @return
     */
    int auditProduct(RpcHeader rpcHeader,long id);

    /**
     * 根据项目ID,审核状态,获取货品管理列表
     * @param projectId  项目ID
     * @param status 状态 1 未提交,2 已提交,3 已审核 4 已驳回
     * @return
     */
    List<Product> selectProductList(RpcHeader rpcHeader,String projectId,int status);

    /**
     * 通过货品ID获取货品详情
     * @param id
     * @return
     */
    ProductDetail getProductDetail(RpcHeader rpcHeader,long id);

    /**
     * 获取待审核货品列表
     * @return
     */
    List<Product> selectNeedAuditProductList(RpcHeader rpcHeader);

}
