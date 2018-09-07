package com.yhglobal.gongxiao.foundation.product.category.dao;

import com.yhglobal.gongxiao.foundation.product.category.dao.mapping.ProductCategoryMapper;
import com.yhglobal.gongxiao.foundation.product.category.model.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ProductCategoryDao {
    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    public  ProductCategory getById(Integer categoryId){
        return productCategoryMapper.selectByPrimaryKey(categoryId);
    }

    public List<ProductCategory> selectAll(){
        return productCategoryMapper.selectAll();
    }

}
