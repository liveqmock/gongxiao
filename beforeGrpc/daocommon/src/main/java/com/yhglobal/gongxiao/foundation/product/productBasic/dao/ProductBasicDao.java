package com.yhglobal.gongxiao.foundation.product.productBasic.dao;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.productBasic.dao.mapping.ProductBasicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductBasicDao {
    @Autowired
    private ProductBasicMapper productBasicMapper;

    public ProductBasic getByProductCode(String productCode) {
        return productBasicMapper.getByProductCode(productCode);
    }

    public ProductBasic getByProductModel(String productModel){
        return productBasicMapper.getByProductModel(productModel);
    }
    /**
     * 获取货品信息
     *
     * @param productId
     * @return
     */
    public ProductBasic getProductById(String productId) {
        return productBasicMapper.getProductById(productId);
    }

    public List<ProductBasic> selectAll() {
        return productBasicMapper.selectAll();
    }

    /**
     * 分页查询
     *
     * @return java.util.List<com.yhglobal.gongxiao.foundation.product.productBasic.ProductBasic>
     * @author 葛灿
     * @date 2018/2/6 16:24
     */
    public int selectCount(String productCode, String productName,
                           String barCode, Long productCategory, Long brand, Long productId, Long sellerProductCategory) {

        return productBasicMapper.selectCountWithCondition(productCode, productName,
                barCode, productCategory, brand, productId, sellerProductCategory);
    }

    public List<ProductBasic> selectWithCondition(int pageNumber, int pageSize, String productCode, String productName,
                                                  String barCode, Long productCategory, Long brand, Long productId, Long sellerProductCategory) {

        return productBasicMapper.selectWithCondition((pageNumber - 1) * pageSize, pageSize, productCode, productName,
                barCode, productCategory, brand, productId, sellerProductCategory);
    }

    public List<ProductBasic> getProductList(String queryStr) {
        return productBasicMapper.getProductList(queryStr);
    }

    public ProductBasic selectByProductCode(String productCode) {
        return productBasicMapper.selectByProductCode(productCode);
    }

    public List<ProductBasic> selectProductListByCondition(Long projectId, String productCode, String productName) {
        return productBasicMapper.selectProductByCondition(projectId, productCode, productName);
    }

    public ProductBasic getByWmsProductCode(String wmsProductCode){
        return productBasicMapper.getByWmsProductCode(wmsProductCode);
    }

    public ProductBasic getByEasModel(String easModel){
        return productBasicMapper.getByEasModel(easModel);
    }

}
