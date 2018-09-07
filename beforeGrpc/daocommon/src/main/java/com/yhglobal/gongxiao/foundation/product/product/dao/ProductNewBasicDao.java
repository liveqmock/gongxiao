package com.yhglobal.gongxiao.foundation.product.product.dao;

import com.yhglobal.gongxiao.foundation.product.product.dao.mapping.ProductBasicNewMapper;
import com.yhglobal.gongxiao.foundation.product.product.model.ProductBasic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Repository
public class ProductNewBasicDao {

    @Autowired
    ProductBasicNewMapper productBasicMapper;

    /**
     * 插入新的货品
     * @param productBasic
     * @return
     */
    public int insertProductBasic(ProductBasic productBasic){
        return productBasicMapper.insert(productBasic);
    }

    /**
     * 通过货品ID获取货品信息
     * @param id
     * @return
     */
    public ProductBasic getById(long id){
        return productBasicMapper.getById(id);
    }

    /**
     * 更新货品基础信息
     * @param productBasic
     * @return
     */
    public int updateRecord(ProductBasic productBasic){
        return productBasicMapper.updateById(productBasic);
    }

    /**
     * 获取所有货品信息
     * @return
     */
    public List<ProductBasic> selectAllProduct(){
        return productBasicMapper.selectAllProduct();
    }

}
