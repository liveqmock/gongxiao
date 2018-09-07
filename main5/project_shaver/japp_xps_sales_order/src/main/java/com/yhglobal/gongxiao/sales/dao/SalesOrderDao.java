package com.yhglobal.gongxiao.sales.dao;

import com.yhglobal.gongxiao.sales.dao.mapping.SalesOrderMapper;
import com.yhglobal.gongxiao.sales.model.SalesOrder;
import com.yhglobal.gongxiao.sales.model.dto.SalesOrderCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 销售订单dao
 *
 * @author 葛灿
 * @date 2018-02-28 11:37
 */
@Repository
public class SalesOrderDao {
    @Autowired
    private SalesOrderMapper salesOrderMapper;

    /**
     * 根据采购单号查询
     *
     * @param orderNo 采购单号
     * @return SalesOrder
     * @author 葛灿
     * @date 2018/2/28 11:43
     */
    public SalesOrder getByOrderNo(String prefix, String orderNo) {
        return salesOrderMapper.selectByOrderNo(prefix, orderNo);
    }


    /**
     * 插入销售单信息
     *
     * @param record
     * @return int
     * @author 葛灿
     * @date 2018/2/28 11:51
     */
    public int insert(String prefix, SalesOrder record) {
        return salesOrderMapper.insert(prefix, record);
    }

    /**
     * 根据条件选择性查询        YH使用
     *
     * @param projectId   项目id
     * @param orderNo     销售单号
     * @param outDate     出库日期
     * @param createTime  创建时间
     * @param orderStatus 订单状态
     * @return java.util.List<SalesOrder>
     */
    public List<SalesOrder> selectListSelectively(String prefix, long projectId, String orderNo, String outDate, String createTime, Integer orderStatus) {
        return salesOrderMapper.getListSelectively(prefix, projectId, orderNo, outDate, createTime, orderStatus);
    }

    /**
     * 根据条件选择性查询        AD使用
     *
     * @param projectId       项目id
     * @param distributorId   分销商id
     * @param orderNo         销售单号
     * @param productCode     货品编码
     * @param createTimeBegin 创建时间-起始条件
     * @param createTimeEnd   创建时间-截止条件
     * @param orderStatus     订单状态
     * @return java.util.List<SalesOrder>
     */
    public List<SalesOrder> selectListSelectivelyByDistributor(String prefix, long projectId, long distributorId, String orderNo, String productCode, String createTimeBegin, String createTimeEnd, Integer orderStatus) {
        return salesOrderMapper.selectListSelectivelyByDistributor(prefix, projectId, distributorId, orderNo, productCode, null, createTimeBegin, createTimeEnd, orderStatus);
    }

    /**
     * 根据条件选择性查询   YH使用
     *
     * @param projectId  项目id
     * @param orderNo    销售单号
     * @param outDate    出库日期
     * @param createTime 创建时间
     * @return java.util.List<SalesOrder>
     */
    public List<SalesOrderCount> getCountMap(String prefix, long projectId, String orderNo, String outDate, String createTime) {
        return salesOrderMapper.getCountMap(prefix, projectId, orderNo, outDate, createTime);
    }


    public List<SalesOrder> selectToSyncEasOrder(String prefix, int syncEas) {
        return salesOrderMapper.selectToSyncEasOrder(prefix, syncEas);
    }


    /**
     * 修改账期结算模式
     *
     * @param salesOrderId     主键id
     * @param dataVersion      数据版本
     * @param salesOrderStatus 销售单状态
     * @param settlementMode   结算模式
     * @param paymentDays      账期天数
     * @param remark           备注
     * @param tracelog         操作日志
     * @return 更新成功条数
     */
    public int updateSettlementMode(String prefix, long salesOrderId, long dataVersion, int salesOrderStatus, int settlementMode, int paymentDays, String remark, String tracelog) {
        return salesOrderMapper.updateSettlementMode(prefix, salesOrderId, dataVersion, salesOrderStatus, settlementMode, paymentDays, remark, tracelog);
    }

    /**
     * 修改收件信息
     *
     * @param salesOrderId     主键id
     * @param dataVersion      数据版本
     * @param recipientName    收件人
     * @param recipientMobile  收件人手机
     * @param provinceId       省id
     * @param provinceName     省
     * @param cityId           市id
     * @param cityName         市
     * @param districtId       区id
     * @param districtName     区
     * @param shippingAddress  详细地址
     * @param recipientCompany 收件人公司
     * @param tracelog         操作日志
     * @return 更新成功条数
     */
    public int updateRecipientInfo(String prefix, Long salesOrderId, Long dataVersion, String recipientName, String recipientMobile, String provinceId, String provinceName, String cityId, String cityName, String districtId, String districtName, String shippingAddress, String recipientCompany, String tracelog) {
        return salesOrderMapper.updateRecipientInfo(prefix, salesOrderId, dataVersion,
                recipientName, recipientMobile, provinceId, provinceName, cityId, cityName, districtId, districtName, shippingAddress, recipientCompany, tracelog);
    }

    /**
     * 更新审批信息
     *
     * @param salesOrderId     主键id
     * @param dataVersion      数据版本
     * @param salesOrderStatus 销售单状态
     * @param couponAmount     返利金额
     * @param prepaidAmount    代垫金额
     * @param cashAmount       现金金额
     * @param orderCheckTime   审批时间
     * @param tracelog         操作日志
     * @return 更新成功条数
     */
    public int updateApprovalInfo(String prefix, Long salesOrderId, Long dataVersion, int salesOrderStatus, long couponAmount, long prepaidAmount, long cashAmount, Date orderCheckTime, String tracelog,String salesContractNo) {        return salesOrderMapper.updateApprovalInfo(prefix, salesOrderId, dataVersion, salesOrderStatus, couponAmount, prepaidAmount, cashAmount, orderCheckTime, tracelog,salesContractNo);
    }

    /**
     * 更新返利、代垫流水号
     *
     * @param salesOrderId  主键id
     * @param dataVersion   数据版本
     * @param couponFlowNo  返利流水号
     * @param prepaidFlowNo 代垫流水号
     * @return 更新成功条数
     */
    public int updateCouponPrepaidFlowNo(String prefix, Long salesOrderId, Long dataVersion, String couponFlowNo, String prepaidFlowNo) {
        return salesOrderMapper.updateCouponPrepaidFlowNo(prefix, salesOrderId, dataVersion, couponFlowNo, prepaidFlowNo);
    }

    /**
     * 更细销售单状态
     *
     * @param salesOrderId     主键id
     * @param dataVersion      数据版本
     * @param salesOrderStatus 订单状态
     * @param tracelog         操作日志
     * @return 更新成功条数
     */
    public int updateSalesOrderStatus(String prefix, Long salesOrderId, Long dataVersion, int salesOrderStatus, String tracelog) {
        return salesOrderMapper.updateSalesOrderStatus(prefix, salesOrderId, dataVersion, salesOrderStatus, tracelog);
    }

    /**
     * 更细销售单流水号
     *
     * @param salesOrderId     主键id
     * @param dataVersion      数据版本
     * @param returnCashFlowNo 取消订单对应的现金流水号
     * @return 更新成功条数
     */
    public int updateReturnCashFlowNo(String prefix, Long salesOrderId, Long dataVersion, String returnCashFlowNo) {
        return salesOrderMapper.updateReturnCashFlowNo(prefix, salesOrderId, dataVersion, returnCashFlowNo);
    }

    /**
     * 取消订单
     *
     * @param salesOrderId     主键id
     * @param dataVersion      数据版本
     * @param salesOrderStatus 销售单状态
     * @param tracelog         操作日志
     * @param rejectTime       驳回时间
     * @return 更新成功条数
     */
    public int updateSalesOrderStatusAndRejectTime(String prefix, Long salesOrderId, Long dataVersion, int salesOrderStatus, String tracelog, Date rejectTime) {
        return salesOrderMapper.updateSalesOrderStatusAndRejectTime(prefix, salesOrderId, dataVersion, salesOrderStatus, tracelog, rejectTime);
    }

    /**
     * 更新取消订单对应的流水号信息
     *
     * @param salesOrderId        主键id
     * @param dataVersion         数据版本
     * @param returnCashFlowNo    取消订单对应的现金流水号
     * @param returnCouponFlowNo  取消订单对应的返利流水号
     * @param returnPrepaidFlowNo 取消订单对应的代垫流水号
     * @return 更新成功条数
     */
    public int updateReturnFlowNos(String prefix, Long salesOrderId, Long dataVersion, String returnCashFlowNo, String returnCouponFlowNo, String returnPrepaidFlowNo) {
        return salesOrderMapper.updateReturnFlowNos(prefix, salesOrderId, dataVersion, returnCashFlowNo, returnCouponFlowNo, returnPrepaidFlowNo);
    }


    /**
     * 更新销售单状态及支付时间
     *
     * @param salesOrderId     主键id
     * @param dataVersion      数据版本
     * @param salesOrderStatus 订单状态
     * @param tracelog         操作日志
     * @param paidTime         支付时间
     * @return 更新成功条数
     */
    public int updateSalesOrderStatusAndPaidTime(String prefix, Long salesOrderId, Long dataVersion, int salesOrderStatus, String tracelog, Date paidTime) {
        return salesOrderMapper.updateSalesOrderStatusAndPaidTime(prefix, salesOrderId, dataVersion, salesOrderStatus, tracelog, paidTime);
    }

    /**
     * 预约发货后,修改订单状态
     *
     * @param salesOrderId             主键id
     * @param dataVersion              数据版本
     * @param salesOrderStatus         订单状态
     * @param informWarehouseTime      通知仓库时间
     * @param syncEasStatus            同步eas状态
     * @param unhandledQuantity        未处理数量
     * @param ongoingOutboundOrderInfo 正在进行的出库单json
     * @param tracelog                 操作日志
     * @return 更新成功条数
     */
    public int updateWhenScheduleDelivery(String prefix, Long salesOrderId, Long dataVersion, int salesOrderStatus, Date informWarehouseTime, int syncEasStatus, int unhandledQuantity, String ongoingOutboundOrderInfo, String tracelog) {
        return salesOrderMapper.updateWhenScheduleDelivery(prefix, salesOrderId, dataVersion, salesOrderStatus, informWarehouseTime, syncEasStatus, unhandledQuantity, ongoingOutboundOrderInfo, tracelog);
    }

    /**
     * 当有出库单出库完成后,修改订单的状态
     *
     * @param salesOrderId              主键id
     * @param dataVersion               数据版本
     * @param salesOrderStatus          订单状态
     * @param outboundTime              出库时间
     * @param onGoingOutboundOrderInfo  正在进行的出库单json
     * @param finishedOutboundOrderInfo 已完成的出库单json
     * @param transitQuantity           在途数量
     * @param tracelog                  操作日志
     * @return 更新成功条数
     */
    public int updateWhenOutbound(String prefix, Long salesOrderId, Long dataVersion, int salesOrderStatus, Date outboundTime, String onGoingOutboundOrderInfo, String finishedOutboundOrderInfo, int transitQuantity, String tracelog) {
        return salesOrderMapper.updateWhenOutbound(prefix, salesOrderId, dataVersion, salesOrderStatus, outboundTime, onGoingOutboundOrderInfo, finishedOutboundOrderInfo, transitQuantity, tracelog);
    }

    /**
     * 当有出库单签收后,修改订单的状态
     *
     * @param salesOrderId      主键id
     * @param dataVersion       数据版本
     * @param deliveredQuantity 送达数量
     * @param inTransitQuantity 在途数量
     * @param salesOrderStatus  销售单状态
     * @param signTime          签收时间
     * @param tracelog          操作日志
     * @return 更新成功条数
     */
    public int updateWhenOutboundSigned(String prefix, Long salesOrderId, Long dataVersion, int deliveredQuantity, int inTransitQuantity, int salesOrderStatus, Date signTime, String tracelog) {
        return salesOrderMapper.updateWhenOutboundSigned(prefix, salesOrderId, dataVersion, deliveredQuantity, inTransitQuantity, salesOrderStatus, signTime, tracelog);
    }

    /**
     * 更新"同步eas状态"
     *
     * @param salesOrderId 主键id
     * @param dataVersion  数据版本
     * @param syncEas      同步eas状态
     * @return 更新成功条数
     */
    public int updateSyncEasStatus(String prefix, Long salesOrderId, Long dataVersion, int syncEas) {
        return salesOrderMapper.updateSyncEasStatus(prefix, salesOrderId, dataVersion, syncEas);

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


    /**
     * 更新销售单退货数量
     *
     * @param salesOrderId   主键id
     * @param dataVersion    数据版本
     * @param returnQuantity 退货数量
     * @return 更新成功条数
     */
    public int updateReturnedQuantity(String prefix, Long salesOrderId, Long dataVersion, int returnQuantity) {
        return salesOrderMapper.updateReturnedQuantity(prefix, salesOrderId, dataVersion, returnQuantity);
    }
}
