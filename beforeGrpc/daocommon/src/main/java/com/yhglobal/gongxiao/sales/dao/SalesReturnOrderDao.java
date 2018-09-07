package com.yhglobal.gongxiao.sales.dao;

import com.yhglobal.gongxiao.sales.bo.SalesReturnClassificationCount;
import com.yhglobal.gongxiao.sales.dao.mapping.SalesReturnOrderMapper;
import com.yhglobal.gongxiao.sales.model.cancel.model.SalesReturnOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description: 销售退货DAO
 * @author: LUOYI
 * @Date: Created in 10:11 2018/3/21
 */
@Repository
public class SalesReturnOrderDao {

    private Logger logger = LoggerFactory.getLogger(SalesReturnOrderDao.class);

    @Autowired
    private SalesReturnOrderMapper salesReturnOrderMapper;

    /**
     * 根据项目查询退货单列表
     *
     * @param projectId
     * @param returnedType
     * @param salesReturnedOrderNo
     * @param warehouseId
     * @param timeStart
     * @param timeEnd
     * @return List<SalesReturnOrder>
     */
    public List<SalesReturnOrder> selectSalesReturnedOrderByProject(Integer projectId,
                                                                    Integer returnedType,
                                                                    String salesReturnedOrderNo,
                                                                    String warehouseId,
                                                                    String timeStart,
                                                                    String timeEnd,
                                                                    Integer returnedOrderStatus) {
        List<SalesReturnOrder> returnList = salesReturnOrderMapper.getSalesReturnOrderByProject(projectId, returnedType, salesReturnedOrderNo, warehouseId, timeStart, timeEnd, returnedOrderStatus);
        return returnList;
    }

    /**
     * 查询退货单总数
     *
     * @param projectId
     * @param returnedType
     * @param salesReturnedOrderNo
     * @param warehouseId
     * @param timeStart
     * @param timeEnd
     * @return List<SalesReturnOrder>
     */
    public Integer selectSalesReturnedOrderCount(Integer projectId,
                                                 Integer returnedType,
                                                 String salesReturnedOrderNo,
                                                 String warehouseId,
                                                 String timeStart,
                                                 String timeEnd) {
        Integer count = salesReturnOrderMapper.getCount(projectId, returnedType, salesReturnedOrderNo, warehouseId, timeStart, timeEnd);
        return count;
    }

    /**
     * 查询退货单各分类条数
     *
     * @param projectId
     * @param returnedType
     * @param salesReturnedOrderNo
     * @param warehouseId
     * @param timeStart
     * @param timeEnd
     * @return List<SalesReturnOrder>
     */
    public List<SalesReturnClassificationCount> selectClassificationCount(Integer projectId,
                                                                Integer returnedType,
                                                                String salesReturnedOrderNo,
                                                                String warehouseId,
                                                                String timeStart,
                                                                String timeEnd) {
        List<SalesReturnClassificationCount> result = salesReturnOrderMapper.getClassificationCount(projectId, returnedType, salesReturnedOrderNo, warehouseId, timeStart, timeEnd);
        return result;
    }

    /**
     * 新增销售退货
     *
     * @param salesReturnOrder
     * @return
     */
    public int addSalesReturnOrder(SalesReturnOrder salesReturnOrder) {
        int result = salesReturnOrderMapper.addSalesReturnOrder(salesReturnOrder);
        return result;
    }

    /**
     * 按ID查询销售退货单
     *
     * @param salesReturnedOrderId 退货单ID
     * @return
     */
    public SalesReturnOrder selectById(Long salesReturnedOrderId) {
        SalesReturnOrder salesReturnOrder = salesReturnOrderMapper.selectById(salesReturnedOrderId);
        return salesReturnOrder;
    }

    /**
     * 更新销售退货单状态
     *
     * @param salesReturnedOrderId
     * @param returnedOrderStatus
     * @param dataVersion
     * @param tracelog
     * @return
     */
    public int updateStatus(Long salesReturnedOrderId, Integer returnedOrderStatus, Long dataVersion, String tracelog) {
        int result = salesReturnOrderMapper.updateStatus(salesReturnedOrderId, returnedOrderStatus, dataVersion, tracelog);
        return result;
    }

    /**
     * 更新入库单号
     *
     * @param salesReturnedOrderId
     * @param dataVersion
     * @return
     */
    public int updateInboundOrderNo(String gongxiaoWarehouseInboundOrderNo, Long salesReturnedOrderId, Long dataVersion) {
        int result = salesReturnOrderMapper.updateInboundOrderNo(gongxiaoWarehouseInboundOrderNo, salesReturnedOrderId, dataVersion);
        return result;
    }

    /**
     * 按入库单号查询
     * @param inBOundOrderNo
     * @return
     */
    public SalesReturnOrder selectSalesReturnOrderByInBoundOrderNo(String inBOundOrderNo){
        return salesReturnOrderMapper.selectSalesReturnOrderByInBoundOrderNo(inBOundOrderNo);
    }

    /**
     * 更新退货到仓后应归还的返利代垫现金及总数量
     * @param returnedCouponAmount
     * @param returnedPrepaidAmount
     * @param returnedCashAmount
     * @param inStockQuantity
     * @param salesReturnedOrderId
     * @param dataVersion
     * @return
     */
    public int updateInboundRecord(Long returnedCouponAmount,
                                    Long returnedPrepaidAmount,
                                    Long returnedCashAmount,
                                    int inStockQuantity,
                                    Long salesReturnedOrderId,
                                    String tracelog,
                                    Long dataVersion){
        return salesReturnOrderMapper.updateInboundRecord(returnedCouponAmount,returnedPrepaidAmount,returnedCashAmount,inStockQuantity,salesReturnedOrderId,tracelog,dataVersion);
    }
}
