package com.yhglobal.gongxiao.phjd.sales.dao;

import com.yhglobal.gongxiao.phjd.sales.dao.mapper.SalesOrderMapper;
import com.yhglobal.gongxiao.phjd.sales.model.SalesOrder;
import com.yhglobal.gongxiao.phjd.sales.model.bo.SalesOrderCountBO;
import com.yhglobal.gongxiao.phjd.sales.model.bo.SalesOrderListBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 @Author weizecheng
 @Date 2018/8/20 15:34
 **/
@Repository
public class SalesOrderDao {

    @Autowired
    private SalesOrderMapper salesOrderMapper;


    /**
     * 根据销售订单编号获取销售订单
     *
     * @auther weizecheng
     * @date 2018/8/28 8:42
     * @param prefix 表前缀
     * @param orderNo 销售订单编号
     * @return
     */
    public SalesOrder getSalesOrderByOrderNo(String prefix, String orderNo) {
        return salesOrderMapper.getByOrderNo(prefix, orderNo);
    }


    /**
     * 根据销售订单Id获取
     *
     * @param prefix
     * @param salesOrderId
     * @return
     */
    public SalesOrder getSalesOrderById(String prefix, Long salesOrderId) {
        return salesOrderMapper.getById(prefix,salesOrderId);
    }

    public List<SalesOrderListBO> listSaleOrderByTerm( String prefix,
                                                       long distributorId,
                                                       String salesOrderNo,
                                                       String productCode,
                                                       String outBoundTime,
                                                       String createTimeBegin,
                                                       String createTimeEnd,
                                                       Integer salesOrderStatus,
                                                       String purchaseNo){
        return salesOrderMapper.listSalesOrderByDistributor(prefix,distributorId,salesOrderNo,productCode,outBoundTime,createTimeBegin,createTimeEnd,salesOrderStatus,purchaseNo);
    }

    /**
     * 审核订单进行相关更新
     *
     * @auther weizecheng
     * @date 2018/8/24 16:05
     * @param prefix 表前缀
     * @param dataVersion 版本号 乐观锁更新
     * @param id 更新的订单Id
     * @param cashAmount 实际价格
     * @param totalCostQuantity 实际发货数量
     * @param salesOrderStatus 销售订单状态
     * @param shortageReasonQuantity 未发货数量
     * @param orderCheckTime 审核时间
     * @param tracelog 更新日志
     * @return int
     */
    public int updateReviewSalesOrder(String prefix, Long dataVersion, Long id, BigDecimal cashAmount, Integer totalCostQuantity, Integer salesOrderStatus, Integer shortageReasonQuantity, Date orderCheckTime, String tracelog){
        return salesOrderMapper.updateReviewSalesOrder(prefix, dataVersion, id, cashAmount, totalCostQuantity, salesOrderStatus, shortageReasonQuantity, orderCheckTime, tracelog);
    }


    /**
     * 驳回销售订单
     *
     * @auther weizecheng
     * @date 2018/8/28 9:54
     * @param prefix 表前缀
     * @param dataVersion 乐观锁版本号
     * @param id 销售订单Id
     * @param salesOrderStatus 更新的订单状态
     * @param rejectedDate 驳回时间
     * @param tracelog 操作日志
     * @return int
     */
    public  int updateRejectedSalesOrder(String prefix, Long dataVersion, Long id,Integer salesOrderStatus,Date rejectedDate,String tracelog){
        return salesOrderMapper.updateRejectedSalesOrder(prefix, dataVersion, id, salesOrderStatus, rejectedDate, tracelog);
    }

    /**
     * 获取 各种状态的订单数量
     *
     * @auther weizecheng
     * @date 2018/8/28 8:41
     * @param prefix 表前缀
     * @return
     */
    public List<SalesOrderCountBO> countSalesOrder(String prefix){
        return salesOrderMapper.countSalesOrderByOrderStatus(prefix);
    }

    /**
     * 更新销售订单日志
     *
     * @author weizecheng
     * @date 2018/8/28 14:36
     * @param prefix 表前缀
     * @param dataVersion 版本号
     * @param id 销售 订单Id
     * @param tracelog 日志
     * @return int
     */
    public int updateTracelog(String prefix, Long dataVersion, Long id, String tracelog){
        return salesOrderMapper.updateTracelogSalesOrder(prefix, dataVersion, id, tracelog);
    }
    /**
     * 更新预约发货信息
     *
     * @author weizecheng
     * @date 2018/8/29 11:04
     * @param prefix 表前缀
     * @param dataVersion 版本号
     * @param id 销售订单Id
     * @param tracelog 操作日志
     * @param salesOrderStatus 销售订单状态
     * @param informWarehouseTime 通知仓库时间
     * @param syncEasStatus 同步eas状态
     * @param totalUnhandledQuantity 待预约发货数量
     * @param newOngoingOutboundOrderInfo 正在进行的出库通知单(JSON)
     * @return int
     */
    public int updateScheduleDelivery(String prefix, Long dataVersion, Long id, String tracelog, Integer salesOrderStatus, Date informWarehouseTime, Integer syncEasStatus, Integer totalUnhandledQuantity, String newOngoingOutboundOrderInfo){
        return salesOrderMapper.updateScheduleDelivery(prefix, dataVersion, id, tracelog, salesOrderStatus, informWarehouseTime, syncEasStatus, totalUnhandledQuantity, newOngoingOutboundOrderInfo);
    }

    /**
     * 插入销售订单
     *
     * @author weizecheng
     * @date 2018/8/29 15:38
     * @param prefix 表前缀
     * @param order 销售订单
     * @return
     */
    public Long insertSalesOrder(String prefix,SalesOrder order){
        salesOrderMapper.insertSalesOrder(prefix,order);
        return order.getSalesOrderId();
    }

    /**
     * 更新订单出库时间 修改毛利 代垫等等
     *
     * @author weizecheng
     * @date 2018/8/31 19:25
     */
    public int updateWhenOutbound(String prefix, Long id,Long dateVersion,Integer salesOrderStatus,Date outboundTime,String onGoingOutboundOrderInfo,String finishedOutboundOrderInfo,Integer transitQuantity,String tracelog,
                                  BigDecimal totalGeneratedPrepaid,
                                  BigDecimal shouldReceiveGrossProfit,
                                  BigDecimal receivedGrossProfit){
        return salesOrderMapper.updateWhenOutbound(prefix, id, dateVersion, salesOrderStatus, outboundTime, onGoingOutboundOrderInfo, finishedOutboundOrderInfo, transitQuantity, tracelog,totalGeneratedPrepaid,shouldReceiveGrossProfit,receivedGrossProfit);
    }

    /**
     * 修改数量相关
     *
     * @author weizecheng
     * @date 2018/9/3 15:29
     */
    public int updateWhenOutboundSign(String prefix, Long id,Long dateVersion,Integer salesOrderStatus,Date outboundTime,Integer deliveredQuantity,Integer inTransitQuantity,String tracelog){
        return salesOrderMapper.updateWhenOutboundSign(prefix, id, dateVersion, salesOrderStatus, outboundTime, deliveredQuantity, inTransitQuantity,tracelog);
    }

    /**
     * 根据sync状态获取List
     *
     * @author weizecheng
     * @date 2018/9/3 17:19
     */
    public List<SalesOrder> listBySyncStatus(String prefix,Integer orderStatus){
        return salesOrderMapper.listBySyncStatus(prefix,orderStatus);
    }

    /**
     * 更新EAS状态
     *
     * @author weizecheng
     * @date 2018/9/3 17:35
     * @param prefix 表前缀
     * @param salesOrderId 销售订单Id
     * @param dataVersion 版本号
     * @param syncEas eas状态
     * @return
     */
    public int updateSyncEasStatus(String prefix, Long salesOrderId, Long dataVersion, Integer syncEas){
        return  salesOrderMapper.updateSyncEasStatus(prefix, salesOrderId, dataVersion, syncEas);
    }

    /**
     * 更新"同步eas状态",更新eas回告的订单号和id
     *
     * @param salesOrderId 主键id
     * @param dataVersion  数据版本
     * @param syncEas      同步eas状态
     * @param easOrderNo   eas订单号
     * @param easOrderId   eas订单id
     * @return 更新成功条数
     */
    public int updateEasInfo(String prefix, Long salesOrderId, Long dataVersion, int syncEas, String easOrderNo, String easOrderId) {
        return salesOrderMapper.updateEasInfo(prefix, salesOrderId, dataVersion, syncEas, easOrderNo, easOrderId);
    }

}
