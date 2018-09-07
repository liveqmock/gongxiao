package com.yhglobal.gongxiao.foundation.product.dao;

import com.yhglobal.gongxiao.foundation.product.dao.mapping.FoundationProductBasicMapper;
import com.yhglobal.gongxiao.foundation.product.dao.mapping.FoundationProductBusinessMapper;
import com.yhglobal.gongxiao.foundation.product.model.FoundationProductBasic;
import com.yhglobal.gongxiao.foundation.product.model.FoundationProductBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Repository
public class FoundationProductBusinessDao {

    @Autowired
    FoundationProductBusinessMapper foundationProductBusinessMapper;

    /**
     * 插入货品业务信息
     *
     * @param foundationProductBusiness
     * @return
     */
    public int insertProductBasicInfo(String projectTablePrefix,
                                      FoundationProductBusiness foundationProductBusiness) {
        return foundationProductBusinessMapper.insert(projectTablePrefix, foundationProductBusiness);
    }

    /**
     * 通过货品业务id获取货品业务信息
     *
     * @param productBusinessId
     * @return
     */
    public FoundationProductBusiness getProductBusinessById(String projectTablePrefix,long productBusinessId) {
        return foundationProductBusinessMapper.getProductBusinessById(projectTablePrefix,productBusinessId);
    }

    /**
     * 通过货品业务id获取货品业务信息
     *
     * @param productCode
     * @return
     */
    public FoundationProductBusiness getProductBusinessByModel(String projectTablePrefix,
                                                               String productCode) {
        return foundationProductBusinessMapper.getProductBusinessByModel(projectTablePrefix,productCode);
    }

    /**
     * 通过货品基础资料id获取货品业务信息列表
     *
     * @param productBasicId
     * @return
     */
    public List<FoundationProductBusiness> selectProductBusinessList(String projectTablePrefix,
                                                                     long productBasicId) {
        return foundationProductBusinessMapper.selectBusinessListByBasicId(projectTablePrefix,
                productBasicId);
    }

    /**
     * 获取该项目的货品列表
     *
     * @return
     */
    public List<FoundationProductBusiness> selectProductBusienssByProject(String projectTablePrefix) {
        return foundationProductBusinessMapper.selectProductBusienssByProject(projectTablePrefix);
    }



    /**
     * 通过wmscode获取已审核货品信息
     *
     * @param productWmsCode
     * @return
     */
    public FoundationProductBusiness getProductBusinessByWmsCode(String projectTablePrefix,String productWmsCode) {
        return foundationProductBusinessMapper.getProductBusinessByWmsCode(projectTablePrefix,productWmsCode);
    }

    /**
     * 通过eascode获取已审核货品信息
     *
     * @param easCode
     * @return
     */
    public FoundationProductBusiness getProductBusinessByEasCode(String projectTablePrefix, String easCode) {
        return foundationProductBusinessMapper.getProductBusinessByEasCode(projectTablePrefix, easCode);
    }

    /**
     * 条件查询货品信息
     *
     * @param productCode    货品编码
     * @param productName    货品名称
     * @param productEasCode 货品EAS编码
     * @param recordStatus   状态
     * @return
     */
    public List<FoundationProductBusiness> selectProductByCondition(String projectTablePrefix,
                                                                    String productCode,
                                                                    String productName,
                                                                    String productEasCode,
                                                                    int recordStatus) {
        return foundationProductBusinessMapper.selectProductByCondition(projectTablePrefix, productCode, productName, productEasCode, recordStatus);
    }

    /**
     * 更新货品信息
     *
     * @param foundationProductBusiness
     * @return
     */
    public int updateProductBusiness(String projectTablePrefix,FoundationProductBusiness foundationProductBusiness) {
        return foundationProductBusinessMapper.updateBusinessById(projectTablePrefix,foundationProductBusiness);
    }

    /**
     * 更新状态信息
     *
     * @param productBusinessId
     * @param recordStatus
     * @return
     */
    public int updateRecordStatus(String projectTablePrefix,
                                  long productBusinessId,
                                  byte recordStatus) {
        return foundationProductBusinessMapper.updateRecordStatus(projectTablePrefix,productBusinessId, recordStatus);
    }
}
