package com.yhglobal.gongxiao.foundation.product.price.service;


import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.product.price.model.ProductPrice;

import java.util.List;

public interface ProductPriceService {

    /**
     * 根据商品id查询价格
     *
     * @param rpcHeader rpc调用的header
     * @param productId  商品id
     * @return
     */
    ProductPrice getByProductId(RpcHeader rpcHeader, Long productId);

    /**
     * 查询所有价格
     *
     * @param rpcHeader rpc调用的header
     * @param brandName 品牌商名称
     * @return
     */
    List<ProductPrice> selectByBrandName(RpcHeader rpcHeader, String brandName);

    /**
     * 分页查询所有的商品价格
     *
     * @param rpcHeader rpc调用的header
     * @return
     */
    List<ProductPrice> selectAll(RpcHeader rpcHeader);

    /**
     * TODO: 添加函数注释
     *
     * @param rpcHeader rpc调用的header
     * @param brandId
     * @return
     */
    List<ProductPrice> selectByBrandId(RpcHeader rpcHeader, String brandId);

    /**
     * TODO：添加函数注释
     *
     * @param rpcHeader
     * @param projectId
     * @return
     */
    List<ProductPrice> selectByProjectId(RpcHeader rpcHeader, String projectId);

}
