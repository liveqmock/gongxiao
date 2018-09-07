package com.yhglobal.gongxiao.foundation.product.basic.service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.dto.ProductDto;

import java.util.List;

public interface ProductService {


    /**
     * 通过货品ID获取当前货品信息
     *
     * @param rpcHeader rpc调用的header
     * @param productCode
     * @return
     */
    ProductBasic getByProductCode(RpcHeader rpcHeader, String productCode);

    /**
     * 通过产品型号获取货品
     * @param rpcHeader
     * @param productModel
     * @return
     */
    ProductBasic getByProductModel(RpcHeader rpcHeader, String productModel);

    /**
     * 通过货品ID获取当前货品信息
     *
     * @param rpcHeader rpc调用的header
     * @param productId
     * @return
     */
    ProductBasic getByProductId(RpcHeader rpcHeader, String productId);



    /**
     * 获取指定项目的货品列表
     *
     * @param rpcHeader rpc调用的header
     * @param projectId  项目id
     * @return
     */
    PageInfo<ProductBasic> selectProductByProjectId(RpcHeader rpcHeader,
                                                    Long projectId,
                                                    String productCode,
                                                    String productName,
                                                    int pageNumber,
                                                    int pageSize);


    /**
     * 查询所有商品基础信息
     *
     * @param rpcHeader rpc调用的header
     * @param pageNo  页码
     * @param pageSize  查询条数
     * @return
     */
    List<ProductBasic> selectAll(RpcHeader rpcHeader, int pageNo, int pageSize);


    /**
     * 根据条件查询，除了pageNumber、pageSize，其他条件都可能为null
     *
     * @param rpcHeader             rpc调用的header
     * @param pageNo                页码
     * @param pageSize              查询条数
     * @param productCode           商品编码
     * @param productName           商品名称
     * @param barCode               条形码
     * @param productCategory       货品类型id
     * @param brand                 商家品牌id
     * @param productId             货品id
     * @param sellerProductCategory 商家品类id
     * @return
     */
    List<ProductBasic> selectWithCondition(RpcHeader rpcHeader, int pageNo, int pageSize, String productCode, String productName,
                                           String barCode, Long productCategory, Long brand, Long productId,
                                           Long sellerProductCategory);

    /**
     * 搜索产品列表
     * @param rpcHeader
     * @param queryStr
     * @return
     */
    List<ProductBasic> search(RpcHeader rpcHeader,String queryStr);


    /**
     * 通过wms商品编码获取当前货品信息
     *
     * @param rpcHeader rpc调用的header
     * @param wmsProductCode
     * @return
     */
    ProductBasic getByWmsProductCode(RpcHeader rpcHeader,String wmsProductCode);

    /**
     * 通过wms商品编码获取当前货品信息
     *
     * @param easModel
     * @return
     */
    ProductBasic getByEasModel(String easModel);

}
