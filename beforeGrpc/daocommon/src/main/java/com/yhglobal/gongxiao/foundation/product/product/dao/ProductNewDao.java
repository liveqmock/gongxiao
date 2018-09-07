package com.yhglobal.gongxiao.foundation.product.product.dao;

import com.yhglobal.gongxiao.foundation.product.product.dao.mapping.ProductNewMapper;
import com.yhglobal.gongxiao.foundation.product.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 *
 * @author: 陈浩
 **/
@Repository
public class ProductNewDao {

    @Autowired
    ProductNewMapper productMapper;

    /**
     * 插入新的货品
     * @param product 货品信息
     * @return
     */
    public int insertProduct(Product product){
        return productMapper.insert(product);
    }

    /**
     * 通过审核状态获取货品信息
     * @param projectId 项目ID
     * @param auditStatus 审核状态
     * @return
     */
    public List<Product> selectByAudit(String projectId,int auditStatus){
        return productMapper.selectByAuditStatus(projectId,auditStatus);
    }

    /**
     * 获取该项目未提交,其他项目已提交的货品列表
     * @param projectId
     * @return
     */
    public List<Product> selectNotCommitList(String projectId){
        return productMapper.selectNotCommitList(projectId);
    }

    /**
     * 获取待审核的货品列表
     * @return
     */
    public List<Product> selectNeedAuditList(){
        return productMapper.selectNeedAuditList();
    }

    /**
     * 通过EAS CODE 获取所有未同步EAS的货品列表
     * @param easCode
     * @return
     */
    public List<Product> selectProductByEasCode(String easCode){
        return productMapper.selectProductByEasCode(easCode);
    }

    /**
     * 通过货品ID获取货品信息
     * @param id 货品ID
     * @return
     */
    public Product getProductById(long id){
        return productMapper.getProductById(id);
    }

    /**
     * 更新货品信息
     * @param product 货品
     * @return
     */
    public int updateProduct(Product product){
        return productMapper.updateById(product);
    }

    /**
     * 更新货品状态
     * @param id 货品ID
     * @param auditStatus 需变更的状态 1 已提交,2 已审核,9 已驳回
     * @return
     */
    public int updateProductStatus(long id,int auditStatus,String traceLog){
        return productMapper.updateRecordStatus(id,auditStatus,traceLog);
    }

    /**
     * 同步EAS状态
     * @param id
     * @return
     */
    public int updateEasStatus(long id){
        return productMapper.updateEasStatus(id);
    }


}
