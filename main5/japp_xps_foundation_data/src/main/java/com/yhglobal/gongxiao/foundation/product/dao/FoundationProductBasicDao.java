package com.yhglobal.gongxiao.foundation.product.dao;

import com.yhglobal.gongxiao.foundation.product.dao.mapping.FoundationProductBasicMapper;
import com.yhglobal.gongxiao.foundation.product.model.FoundationProductBasic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Repository
public class FoundationProductBasicDao {

    @Autowired
    FoundationProductBasicMapper foundationProductBasicMapper;

    /**
     * 插入货品基础信息
     * @param foundationProductBasic
     * @return
     */
    public long insertProductBasic(FoundationProductBasic foundationProductBasic){
        return foundationProductBasicMapper.insert(foundationProductBasic);
    }

    /**
     * 更新货品基础资料的eas相关信息
     * @param productBasicId
     * @param easCode
     * @return
     */
    public int updateProductBasicEasInfo(long productBasicId,String easCode){
        return foundationProductBasicMapper.updateProductBasicEasInfo(productBasicId,easCode);
    }

    /**
     * 更新货品基础资料的wms相关信息
     * @param productBasicId
     * @param wmsCode
     * @return
     */
    public int updateProductBasicWmsInfo(long productBasicId,String wmsCode){
        return foundationProductBasicMapper.updateProductBasicWmsInfo(productBasicId,wmsCode);
    }

    /**
     * 更新基本信息
     * @param foundationProductBasic
     * @return
     */
    public int updateProductById(FoundationProductBasic foundationProductBasic){
        return foundationProductBasicMapper.updateBasicById(foundationProductBasic);
    }

    /**
     * 通过货品基础资料ID获取货品基础资料信息
     * @param productBasicId
     * @return
     */
    public FoundationProductBasic getProductBasicInfoById(long productBasicId){
        return foundationProductBasicMapper.getProductBasicById(productBasicId);
    }

    /**
     * 通过easCode获取货品基础信息
     * @param easCode eas编码
     * @return
     */
    public FoundationProductBasic getProductBasicByEasCode(String easCode){
        return foundationProductBasicMapper.getProductByEasCode(easCode);
    }

    /**
     * 更新状态
     * @param id
     * @param recordStatus
     * @return
     */
    public int updateRecordStatus(long id,byte recordStatus){
        return foundationProductBasicMapper.updateRecordStatus(id,recordStatus);
    }

}
