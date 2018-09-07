package com.yhglobal.gongxiao.sales.dao;

import com.yhglobal.gongxiao.sales.dao.mapping.SalesPlanMapper;
import com.yhglobal.gongxiao.sales.model.plan.model.SalesPlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 销售计划
 *
 * @author: 陈浩
 * @create: 2018-03-08 19:38
 **/
@Repository
public class SalePlanDao {
    @Autowired
    SalesPlanMapper salesPlanMapper;

    /**
     * 插入销售计划数据
     *
     * @param salesPlan 销售计划
     * @return
     */
    public int insertSalePlane(SalesPlan salesPlan) {
        return salesPlanMapper.insert(salesPlan);
    }

    /**
     * 获取当前项目的销售计划列表
     *
     * @param projectId 项目ID
     * @return
     */
    public List<SalesPlan> selectSalePlanListByProject(String projectId,
                                                       String productCode,
                                                       String productName,
                                                       String createTime) {
        List<SalesPlan> salePlanList = salesPlanMapper.getPlanSaleList(projectId, productCode, productName, createTime);
        return salePlanList;
    }

    /**
     * 通过预售计划获取销售计划信息
     *
     * @param salesPlanNo
     * @return
     */
    public SalesPlan getSalePlanNo(String salesPlanNo) {
        return salesPlanMapper.getSalePlanNo(salesPlanNo);
    }

    /**
     * 判断货品是否重复
     * @param projectId 项目ID
     * @param productId 货品ID
     * @return
     */
    public int countRepeatRecord(String projectId,
                                 String productId,
                                 Date startTime,
                                 Date endTime){
        return salesPlanMapper.countRepeatRecord(projectId,productId,startTime,endTime);
    }
    /**
     * 修改计划可售数量以及时间
     *
     * @param salesPlanNo    销售计划单号
     * @param onSaleQuantity 可售数量
     * @param startDate      开始时间
     * @param endDate        结束时间
     * @return
     */
    public int editSalePlan(String salesPlanNo,
                            int onSaleQuantity,
                            int unallocatedQuantity,
                            String startDate,
                            String endDate) {
        return salesPlanMapper.editSalePlan(salesPlanNo, onSaleQuantity, unallocatedQuantity,startDate, endDate);
    }

    /**
     * @param salesPlanNo         销售计划单号
     * @param onSaleQuantity      可售数量
     * @param allocatedQuantity   已分配数量
     * @param unallocatedQuantity 未分配数量
     * @return
     */
    public int updateProductQuantity(String salesPlanNo, int onSaleQuantity, int allocatedQuantity, int unallocatedQuantity) {
        return salesPlanMapper.updateProductQuantity(salesPlanNo, onSaleQuantity, allocatedQuantity, unallocatedQuantity);
    }

    /**
     * 更新已处理数量
     * @param salesPlanNo 预售单号
     * @param allocatedQuantity 已分配数量
     * @param unallocatedQuantity 未分配数量
     * @return
     */
    public int updateHandleQuantity(String salesPlanNo,
                                    int allocatedQuantity,
                                    int unallocatedQuantity) {
        return salesPlanMapper.updateHandleQuantity(salesPlanNo, allocatedQuantity, unallocatedQuantity);
    }

    /**
     * 作废销售订单
     * @param salesPlanNo
     * @return
     */
    public int cancleSalePlanOrder(String salesPlanNo){
        return salesPlanMapper.cancleSalePlanOrder(salesPlanNo,91);
    }

    /**
     * 预售明细作废后,变更货品订单的未处理数量
     * @param salePlanNo 预售单号
     * @param changeQuantity 变更数量
     * @return
     */
    public int updateReturnSaleQuantity(String salePlanNo, int changeQuantity){
        return salesPlanMapper.updateReturnSaleQuantity(salePlanNo,changeQuantity);
    }


}
