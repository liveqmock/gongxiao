package com.yhglobal.gongxiao.sales.dao;

import com.yhglobal.gongxiao.sales.dao.mapping.SalesPlanItemMapper;
import com.yhglobal.gongxiao.sales.model.plan.model.SalesPlanItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 销售计划明细
 *
 * @author: 陈浩
 * @create: 2018-03-08 19:38
 **/
@Repository
public class SalePlanItemDao {

    @Autowired
    SalesPlanItemMapper salesPlanItemMapper;

    /**
     * 插入销售计划数据
     *
     * @param salesPlanItem 销售计划单号
     * @return
     */
    public int insertSalePlanItem(SalesPlanItem salesPlanItem) {
        return salesPlanItemMapper.insert(salesPlanItem);
    }

    /**
     * 通过销售计划单号获取销售计划货品列表
     *
     * @param salesPlanNo 销售计划单号
     * @return
     */
    public List<SalesPlanItem> selectBySalePlanNo(String salesPlanNo, String distributorId, String customerName) {
        return salesPlanItemMapper.selectItemListByNo(salesPlanNo, distributorId, customerName);
    }


    /**
     * 通过明细id获取计划销售信息
     *
     * @param itemId
     * @return
     */
    public SalesPlanItem getByItemId(String itemId) {
        return salesPlanItemMapper.selectBySaleItemId(itemId);
    }

    /**
     * @param itemId               客户计划货销售单号
     * @param onSaleQuantity       可售数量
     * @param brandPrepaidUnit     品牌商单位代垫
     * @param brandPrepaidDiscount 品牌商代垫折扣
     * @param yhPrepaidUnit        越海单位代垫
     * @param yhPrepaidDiscount    越海代垫折扣
     * @param startDate            开始日期
     * @param endDate              结束日期
     * @return
     */
    public int update(String itemId,
                      int onSaleQuantity,
                      int unallocatedQuantity,
                      int brandPrepaidUnit,
                      int brandPrepaidDiscount,
                      int yhPrepaidUnit,
                      int yhPrepaidDiscount,
                      Date startDate,
                      Date endDate) {
        return salesPlanItemMapper.update(itemId,
                onSaleQuantity,
                unallocatedQuantity,
                brandPrepaidUnit,
                brandPrepaidDiscount,
                yhPrepaidUnit,
                yhPrepaidDiscount,
                startDate,
                endDate);
    }

    /**
     * 更新客户计划销售单状态
     *
     * @param itemId     客户计划销售ID
     * @param itemStatus 客户计划销售单状态
     * @return
     */
    public int updatePlanItemStatus(String itemId, int itemStatus) {
        return salesPlanItemMapper.updateStatus(itemId, itemStatus);
    }

    /**
     * 获取用户被分配的产品信息
     *
     * @param projectId
     * @param distributorId
     * @param productId
     * @return
     */
    public SalesPlanItem getCustomerProduct(String projectId, String distributorId, String productId, Date businessDate) {
        return salesPlanItemMapper.getCustomerProduct(projectId, distributorId, productId, businessDate);
    }

    /**
     * 获取某货品分配给某客户的所有记录 统一时间只能有一个有效
     *
     * @param projectId
     * @param distributorId
     * @param productId
     * @return
     */
    public List<SalesPlanItem> selectProductCustomer(String projectId,
                                                     String distributorId,
                                                     String productId,
                                                     Date startDate,
                                                     Date endDate) {
        return salesPlanItemMapper.selectProductCustomer(projectId, distributorId, productId,startDate,endDate);
    }

    /**
     * 获取用户被分配的产品信息
     *
     * @param projectId
     * @param distributorId
     * @param productCode
     * @return
     */
    public SalesPlanItem getCustomerProductList(String projectId, String distributorId, String productCode, Date businessDate) {
        return salesPlanItemMapper.getCustomerProductCondition(projectId, distributorId, productCode, businessDate);
    }

    /**
     * 获取当前客户有效的货品列表
     *
     * @param projectId     项目ID
     * @param distributorId 用户ID
     * @param businessDate  业务日期
     * @return
     */
    public List<SalesPlanItem> getCustomerProductLists(String projectId, String distributorId, Date businessDate) {
        return salesPlanItemMapper.getCustomerProductLists(projectId, distributorId, businessDate);
    }

    /**
     * 查询用户可购买产品列表
     *
     * @param projectId
     * @param distributorId
     * @param queryStr
     * @return
     */
    public List<SalesPlanItem> searchProductByCustomer(String projectId, String distributorId, String queryStr, Date date) {
        return salesPlanItemMapper.searchProductByCustomer(projectId, distributorId, queryStr, date);
    }


    /**
     * 更新销售占用数量
     *
     * @param itemId                 商品预销明细ID
     * @param saleOccupationQuantity 销售占用数量
     * @return
     */
    public int updateSaleOccupationQuantity(long itemId,
                                            int unallocatedQuantity,
                                            int saleOccupationQuantity) {
        return salesPlanItemMapper.updateSaleOccupationQuantity(itemId,
                unallocatedQuantity,
                saleOccupationQuantity);
    }

    /**
     * 更新已售数量
     *
     * @param itemId       商品预销明细ID
     * @param  saleOccupationQuantity 销售占用
     * @param soldQuantity 商品已售数量
     * @return
     */
    public int updateSoldQuantity(long itemId,
                                  int saleOccupationQuantity,
                                  int soldQuantity) {
        return salesPlanItemMapper.updateSoldQuantity(itemId, saleOccupationQuantity,soldQuantity);
    }

    public int cancelAudit(long itemId,
                           int unallocatedQuantity,
                           int soldQuantity){
        return salesPlanItemMapper.cancelAudit(itemId,unallocatedQuantity,soldQuantity);

    }

    /**
     * 获取客户列表
     *
     * @param projectId     项目ID
     * @param productCode   货品ID
     * @param distributorId 经销商ID
     * @param businessDate  业务发生时间
     * @return
     */
    public List<SalesPlanItem> selectItemListByCondition(String projectId, String productCode, String distributorId, Date businessDate) {
        return salesPlanItemMapper.getItemListByCondition(projectId, productCode, distributorId, businessDate);
    }

    /**
     * 通过货品编码、项目id、客户id、有效期查询
     *
     * @param projectId    项目id
     * @param customerId   客户id
     * @param productCode  货品编码
     * @param businessDate 日期
     * @return
     */
    public SalesPlanItem getItemByProductCode(String projectId,
                                              String customerId,
                                              String productCode,
                                              Date businessDate) {
        return salesPlanItemMapper.getItemByProductCode(projectId, customerId, productCode, businessDate);
    }

    /**
     * 获取某个客户在某个项目下的销售明细列表
     *
     * @param projectId       项目ID
     * @param distributorId   经销商ID
     * @param distributorName 将销售名称
     * @return
     */
    public List<String> selectCustomerPlanItemList(String projectId,
                                                   String distributorId,
                                                   String distributorName) {
        return salesPlanItemMapper.selectCustomerPlanItemList(projectId, distributorId, distributorName);
    }

    public List<SalesPlanItem> selectCustomerSalePlanItemList(String projectId, String distributorId) {
        return salesPlanItemMapper.selectCustomerSalePlanItemList(projectId, distributorId);
    }

    /**
     * 获取某个客户的所有销售计划信息列表
     *
     * @param projectId     项目ID
     * @param distributorId 经销商ID
     * @return
     */
    public List<SalesPlanItem> selectCustomerSalePlanList(String projectId,
                                                          String distributorId,
                                                          String salePlanNo,
                                                          String productCode,
                                                          String productName,
                                                          int orderStatus,
                                                          Date startDate,
                                                          Date endDate) {
        return salesPlanItemMapper.selectCustomerSalePlanList(projectId,
                distributorId,
                salePlanNo,
                productCode,
                productName,
                orderStatus,
                startDate,
                endDate);
    }

    /**
     * 通过预收单号,变更明细状态
     *
     * @param salePlanNo  预售单号
     * @param orderStatus 状态
     * @return
     */
    public int cancelSalePlanItemBySalePlanNo(String salePlanNo, int orderStatus) {
        return salesPlanItemMapper.cancelSalePlanItemBySalePlanNo(salePlanNo, orderStatus);
    }

    /**
     * 通过itemId获取可售数量
     * @param itemId
     * @return
     */
    public SalesPlanItem getOnSaleQuantity(String itemId){
        return salesPlanItemMapper.getByItemId(itemId);
    }

    /**
     * 获取已失效的数据
     * @return
     */
    public List<SalesPlanItem> selectFailurePlanItemList(){
        return salesPlanItemMapper.selectFailurePlanItemList();
    }

    /**
     * 定时任务取消订单
     * @param itemId
     * @return
     */
    public int schedulerCancelOrder( long itemId){
        return salesPlanItemMapper.updateSchedulerCancelOrder(itemId);
    }

    public int cancelCustomerOrder(String projectId,
                                   String distributorId,
                                   String productCode,
                                   Date businessDate,
                                   int saleOccupationQuantity,
                                   int soldQuantity){
        return salesPlanItemMapper.cancelCustomerOrder(projectId,distributorId,productCode,businessDate,saleOccupationQuantity,soldQuantity);
    }
}
