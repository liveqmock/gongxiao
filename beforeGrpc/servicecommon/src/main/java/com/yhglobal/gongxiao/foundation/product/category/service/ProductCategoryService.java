package com.yhglobal.gongxiao.foundation.product.category.service;


import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.product.category.model.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    /**
     * 查询所有商品品类
     *
     * @param rpcHeader rpc调用的header
     * @return
     */
    List<ProductCategory> selectAll(RpcHeader rpcHeader);

}
