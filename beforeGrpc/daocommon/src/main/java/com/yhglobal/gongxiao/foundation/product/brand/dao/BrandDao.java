package com.yhglobal.gongxiao.foundation.product.brand.dao;

import com.yhglobal.gongxiao.foundation.product.brand.dao.mapping.BrandMapper;
import com.yhglobal.gongxiao.foundation.product.brand.model.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BrandDao {
    @Autowired
    private BrandMapper brandMapper;

    public int insert(Brand record){
        return brandMapper.insert(record);
    }


    public Brand getByBrandName(String brandName){
        return brandMapper.selectByPrimaryKey(brandName);
    }

    public Brand getByBrandId(String brandId){
        return brandMapper.selectByBrandId(brandId);
    }

    public List<Brand> selectAll(){
        return brandMapper.selectAll();
    }

}
