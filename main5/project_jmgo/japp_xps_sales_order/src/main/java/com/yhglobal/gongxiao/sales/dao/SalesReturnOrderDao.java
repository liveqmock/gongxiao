package com.yhglobal.gongxiao.sales.dao;

import com.yhglobal.gongxiao.sales.dao.mapping.SalesReturnOrderMapper;
import com.yhglobal.gongxiao.sales.model.SalesReturnOrder;
import com.yhglobal.gongxiao.sales.model.bo.SalesReturnClassificationCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<SalesReturnOrder> selectSalesReturnedOrderByProject(String prefix, Long projectId,
                                                                    Integer returnedType,
                                                                    String salesReturnedOrderNo,
                                                                    String warehouseId,
                                                                    String timeStart,
                                                                    String timeEnd,
                                                                    Integer returnedOrderStatus) {
        List<SalesReturnOrder> returnList = salesReturnOrderMapper.getSalesReturnOrderByProject(prefix, projectId, returnedType, salesReturnedOrderNo, warehouseId, timeStart, timeEnd, returnedOrderStatus);
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
    public Integer selectSalesReturnedOrderCount(String prefix, Long projectId,
                                                 Integer returnedType,
                                                 String salesReturnedOrderNo,
                                                 String warehouseId,
                                                 String timeStart,
                                                 String timeEnd) {
        Integer count = salesReturnOrderMapper.getCount(prefix, projectId, returnedType, salesReturnedOrderNo, warehouseId, timeStart, timeEnd);
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
    public List<SalesReturnClassificationCount> selectClassificationCount(String prefix, Long projectId,
                                                                          Integer returnedType,
                                                                          String salesReturnedOrderNo,
                                                                          String warehouseId,
                                                                          String timeStart,
                                                                          String timeEnd) {
        List<SalesReturnClassificationCount> result = salesReturnOrderMapper.getClassificationCount(prefix, projectId, returnedType, salesReturnedOrderNo, warehouseId, timeStart, timeEnd);
        return result;
    }

    /**
     * 新增销售退货
     *
     * @param salesReturnOrder
     * @return
     */
    public int addSalesReturnOrder(String prefix, SalesReturnOrder salesReturnOrder) {
        int result = salesReturnOrderMapper.addSalesReturnOrder(prefix, salesReturnOrder);
        return result;
    }

    /**
     * 按ID查询销售退货单
     *
     * @param salesReturnedOrderId 退货单ID
     * @return
     */
    public SalesReturnOrder selectById(String prefix, Long salesReturnedOrderId) {
        SalesReturnOrder salesReturnOrder = salesReturnOrderMapper.selectById(prefix, salesReturnedOrderId);
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
    public int updateStatus(String prefix, Long salesReturnedOrderId, Integer returnedOrderStatus, Long dataVersion, String tracelog) {
        int result = salesReturnOrderMapper.updateStatus(prefix, salesReturnedOrderId, returnedOrderStatus, dataVersion, tracelog);
        return result;
    }

    /**
     * 更新入库单号
     *
     * @param salesReturnedOrderId
     * @param dataVersion
     * @return
     */
    public int updateInboundOrderNo(String prefix, String gongxiaoWarehouseInboundOrderNo, Long salesReturnedOrderId, Long dataVersion) {
        int result = salesReturnOrderMapper.updateInboundOrderNo(prefix, gongxiaoWarehouseInboundOrderNo, salesReturnedOrderId, dataVersion);
        return result;
    }

    /**
     * 按入库单号查询
     *
     * @param inBOundOrderNo
     * @return
     */
    public SalesReturnOrder selectSalesReturnOrderByInBoundOrderNo(String prefix, String inBOundOrderNo) {
        return salesReturnOrderMapper.selectSalesReturnOrderByInBoundOrderNo(prefix, inBOundOrderNo);
    }

    /**
     * 更新退货到仓后应归还的返利代垫现金及总数量
     *
     * @param returnedCouponAmount
     * @param returnedPrepaidAmount
     * @param returnedCashAmount
     * @param inStockQuantity
     * @param salesReturnedOrderId
     * @param dataVersion
     * @return
     */
    public int updateInboundRecord(String prefix, Long returnedCouponAmount,
                                   Long returnedPrepaidAmount,
                                   Long returnedCashAmount,
                                   int inStockQuantity,
                                   Long salesReturnedOrderId,
                                   String tracelog,
                                   Long dataVersion,
                                   int returnOrderStatus) {
        return salesReturnOrderMapper.updateInboundRecord(prefix, returnedCouponAmount, returnedPrepaidAmount, returnedCashAmount, inStockQuantity, salesReturnedOrderId, tracelog, dataVersion,
                returnOrderStatus);
    }
}
