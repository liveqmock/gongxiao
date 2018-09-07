package com.yhglobal.gongxiao.dao;

import com.yhglobal.gongxiao.dao.mapper.PurchaseCouponFlowMapper;
import com.yhglobal.gongxiao.model.PurchaseCouponFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *采购返利发放记录
 *
 * @author: 陈浩
 **/
@Repository
public class PurchaseCouponFlowDao {

    @Autowired
    PurchaseCouponFlowMapper purchaseCouponFlowMapper;

    /**
     * 通过ID获取流水
     * @param purchaseCouponFlowId
     * @return
     */
    public PurchaseCouponFlow getById(long purchaseCouponFlowId ){
        return purchaseCouponFlowMapper.selectByPrimaryKey(purchaseCouponFlowId);
    }

    /**
     * 插入采购返利流水
     * @param flow 采购返利流水
     * @return
     */
    public int insertFlow(PurchaseCouponFlow flow){
        return purchaseCouponFlowMapper.insert(flow);
    }

    /**
     * 通过项目ID获取采购返利流水
     * @param projectId
     * @return
     */
    public List<PurchaseCouponFlow> selectCouponFlowByProject(String projectId){
        return purchaseCouponFlowMapper.selectByProjectId(projectId);
    }

}
