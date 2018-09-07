package com.yhglobal.gongxiao.sales.service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.inventory.bo.InventoryBatch;
import com.yhglobal.gongxiao.purchase.bo.CreatePurchaseBasicInfo;
import com.yhglobal.gongxiao.purchase.bo.CreatePurchaseItemInfo;
import com.yhglobal.gongxiao.sales.bo.SalesOrderDetail;
import com.yhglobal.gongxiao.sales.bo.SalesOrderInfo;
import com.yhglobal.gongxiao.sales.bo.SalesOrderItemInfo;
import com.yhglobal.gongxiao.sales.bo.SalesOrderList;
import com.yhglobal.gongxiao.sales.model.SalesOrder;
import com.yhglobal.gongxiao.sales.model.SalesOrderItem;
import com.yhglobal.gongxiao.sales.model.SalesOrderSettlementModeEnum;
import com.yhglobal.gongxiao.sales.model.cancel.dto.SalesReturnItem;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.Inventory;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
 * 销售单Service
 *
 * @Author: 葛灿
 */
public interface SalesOrderService {

    /**
     * 审核页面
     * 根据单号查询该单详情
     *
     * @param rpcHeader rpc调用的header
     * @param orderNo   销售单号
     * @return SalesOrder 销售单对象
     */
    SalesOrderDetail getOrderByOrderNo(RpcHeader rpcHeader, String orderNo);


    /**
     * 选择性查询
     *
     * @param rpcHeader   rpc调用的header
     * @param orderNo     订单号
     * @param outDate     出库时间
     * @param createTime  创建时间
     * @param orderStatus 订单状态
     * @param pageSize    页面条数
     * @param pageRow     页码
     * @return List<SalesOrder> 多条销售单
     */
    SalesOrderList getListSelectively(RpcHeader rpcHeader, long projectId, String orderNo, Date outDate, Date createTime, Integer orderStatus, int pageSize, int pageRow);

    /**
     * 修改销售单结算方式
     *
     * @param rpcHeader    头
     * @param salesOrderNo 销售单号
     * @return
     */
    RpcResult changeSettlementMode(RpcHeader rpcHeader, String[] salesOrderNo, int days, String remark);

    /**
     * 新建销售单
     *
     * @param rpcHeader        rpc调用的header
     * @param salesOrderNo     销售单号
     * @param marketingChannel 销售渠道 使用枚举类 SalesMarketingChannelEnum
     * @param salesContactNo   销售合同号
     * @param paymentChannel   支付渠道 使用枚举类 SalesPaymentChannelEnum
     * @param projectId        项目id
     * @param projectName      项目名称
     * @param distributorId    经销商id
     * @param distributorName  经销商名称
     * @param shippingAddress  经销商的收货地址
     * @param provinceId       省id
     * @param provinceName     省
     * @param cityId           市id
     * @param cityName         市
     * @param districtId       区id
     * @param districtName     区
     * @param recipientName    收件人名字
     * @param recipientMobile  收件人手机号
     * @param currencyId       结算货币ID
     * @param currencyCode     结算货币码
     * @param cashAmount       现金金额
     * @param couponAmount     返利金额
     * @param prepaidAmount    代垫金额
     * @param totalOrderAmount 订单总金额
     * @param totalPaidAmount  实付总金额
     * @param createTime       交易时间
     * @param list             销售单中的商品信息(页面只用传 productCode、totalQuantity、totalOrderAmountDouble)
     * @return int 插入条数
     */
    RpcResult createSalesOrder(RpcHeader rpcHeader,
                               String salesOrderNo, String salesContactNo,
                               Integer marketingChannel,
                               Integer paymentChannel, Long projectId,
                               String projectName, Long distributorId,
                               String distributorName, String shippingAddress,
                               String provinceId, String provinceName,
                               String cityId, String cityName,
                               String districtId, String districtName,
                               String recipientName, String recipientMobile,
                               String currencyId, String currencyCode,
                               double cashAmount, double couponAmount,
                               double prepaidAmount, double totalOrderAmount, double totalPaidAmount,
                               Date createTime, List<SalesOrderItemInfo> list);

    /**
     * 改变销售单收件人信息
     *
     * @param rpcHeader        rpc调用的header
     * @param orderNo          销售单号
     * @param recipientName    收件人名称
     * @param recipientMobile  收件人电话
     * @param provinceId       省id
     * @param provinceName     省
     * @param cityId           市id
     * @param cityName         市
     * @param districtId       区id
     * @param districtName     区
     * @param shippingAddress  收件地址
     * @param recipientCompany 收件公司
     * @return int 更新条数
     */
    RpcResult updateRecipientInfo(RpcHeader rpcHeader, String orderNo, String recipientName, String recipientMobile, String provinceId, String provinceName,
                            String cityId, String cityName,
                            String districtId, String districtName, String shippingAddress, String recipientCompany);

    /**
     * 销售订单审核通过
     *
     * @param rpcHeader           rpc调用的header
     * @param salesOrderNo        销售单号
     * @param couponAmountDouble  使用返利金额
     * @param prepaidAmountDouble 使用代垫金额
     * @return
     */
    RpcResult approveSalesOrder(RpcHeader rpcHeader, String salesOrderNo, Double couponAmountDouble, Double prepaidAmountDouble) throws RemoteException, MalformedURLException;



    /**
     * 客户取消销售订单
     *
     * @param rpcHeader    rpc调用的header
     * @param salesOrderNo 销售单号
     * @return true表示成功 false表示失败
     */
    RpcResult cancelSalesOrderByDistributor(RpcHeader rpcHeader, String salesOrderNo);

    /**
     * 驳回销售订单
     *
     * @param rpcHeader    rpc调用的header
     * @param salesOrderNo 销售单号
     * @return
     */
    RpcResult rejectSalesOrder(RpcHeader rpcHeader, String salesOrderNo);

    /**
     * 取消订单审核
     *
     * @param rpcHeader    头
     * @param salesOrderNo 销售单号
     * @return
     */
    RpcResult cancelSalesOrderApprove(RpcHeader rpcHeader, String salesOrderNo);

    /**
     * 确认销售订单收款
     *
     * @param rpcHeader    rpc调用的header
     * @param salesOrderNo 销售单号
     * @return
     */
    RpcResult confirmSalesOrderAmount(RpcHeader rpcHeader, String salesOrderNo);

    /**
     * AD查询订单列表
     *
     * @param rpcHeader
     * @param purchaseNo
     * @param productCode
     * @param status
     * @param dateStart
     * @param dateEnd
     * @return
     */
    PageInfo<SalesOrderInfo> searchOrderListWithPage(RpcHeader rpcHeader,
                                             String purchaseNo,
                                             String productCode,
                                             Integer status,
                                             Date dateStart,
                                             Date dateEnd,
                                             Integer page,
                                             Integer pageSize);

    /**
     * AD查询订单列表 不分页
     *
     * @param rpcHeader
     * @param purchaseNo
     * @param productCode
     * @param status
     * @param dateStart
     * @param dateEnd
     * @return
     */
    List<SalesOrderInfo> searchOrderList(RpcHeader rpcHeader,
                                         String purchaseNo,
                                         String productCode,
                                         Integer status,
                                         Date dateStart,
                                         Date dateEnd);




    /**
     * 详情页面，获取销售单详情
     *
     * @param rpcHeader    头
     * @param salesOrderNo 销售单号
     * @return
     */
    SalesOrderDetail getOrderDetailBySalesOrderNo(RpcHeader rpcHeader, String salesOrderNo);

    /**
     * 判断是否已经发货
     *
     * @param rpcHeader    头
     * @param salesOrderNo 销售单号
     * @return
     */
    boolean whetherOutbound(RpcHeader rpcHeader, String salesOrderNo);

    /**
     * 台账取消审批后，联动修改销售单的收款状态
     *
     * @param rpcHeader    头
     * @param salesOrderNo 销售单号
     * @return 是否取消成功
     */
    RpcResult cancelReceivedCash(RpcHeader rpcHeader, String salesOrderNo);

}
