package com.yhglobal.gongxiao.foundation.product.brand.service;


import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.product.brand.model.Brand;

import java.util.List;

public interface BrandService {

    /**
     * 根据品牌id来查询品牌对象
     *
     * @param rpcHeader rpc调用的header
     * @param brandId  品牌id
     * @return
     */
    Brand getBrandById(RpcHeader rpcHeader, String brandId);

    /**
     * 根据品牌名称查询品牌对象
     *
     * @param rpcHeader rpc调用的header
     * @param brandName 品牌名称 注:这里需准确的品牌全称 模糊查询的话会返回多条记录
     * @return
     */
    Brand getBrandByName(RpcHeader rpcHeader, String brandName);

    /**
     * 查询所有品牌
     *
     * @param rpcHeader rpc调用的header
     * @return
     */
    List<Brand> selectAll(RpcHeader rpcHeader);

}
