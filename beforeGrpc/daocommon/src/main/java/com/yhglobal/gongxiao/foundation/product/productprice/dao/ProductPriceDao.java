package com.yhglobal.gongxiao.foundation.product.productprice.dao;

import com.yhglobal.gongxiao.foundation.product.price.model.ProductPrice;
import com.yhglobal.gongxiao.foundation.product.productprice.dao.mapping.ProductPriceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductPriceDao {
    @Autowired
    private ProductPriceMapper productPriceMapper;

    public ProductPrice getByProductId(Long productId){
        return productPriceMapper.selectByProductId(productId);
    }


    public List<ProductPrice> selectAll(){
        return productPriceMapper.selectAll();
    }

    public List<ProductPrice> selectByBrandName(String brandName){
        return productPriceMapper.selectByBrandName(brandName);
    }

    public List<ProductPrice> selectByBrandId(String brandId){
        return productPriceMapper.selectByBrandId(brandId);
    }

    public List<ProductPrice> selectByProjectId(String projectId){
        return productPriceMapper.selectByProjectId(projectId);
    }
    public int updateEasInfo(String productCode,String easCode ,String easName,String easModel){
        return productPriceMapper.updateEasInfo(productCode,easCode,easName,easModel);
    }

}
