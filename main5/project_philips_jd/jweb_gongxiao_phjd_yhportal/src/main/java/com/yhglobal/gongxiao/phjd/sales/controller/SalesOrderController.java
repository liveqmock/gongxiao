package com.yhglobal.gongxiao.phjd.sales.controller;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.constant.sales.SalesOrderStatusEnum;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.phjd.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.phjd.base.protal.PortalConfig;
import com.yhglobal.gongxiao.phjd.sales.model.bo.SalesOrderCountBO;
import com.yhglobal.gongxiao.phjd.sales.model.bo.SalesOrderListBO;
import com.yhglobal.gongxiao.phjd.sales.model.dto.ReviewSalesOrderDTO;
import com.yhglobal.gongxiao.phjd.sales.model.vo.SalesOrderItemVO;
import com.yhglobal.gongxiao.phjd.sales.model.vo.SalesOrderListVO;
import com.yhglobal.gongxiao.phjd.sales.service.SalesOrderItemService;
import com.yhglobal.gongxiao.phjd.utils.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderAddressServiceGrpc;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderAddressServiceStructure;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceGrpc;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 销售订单控制层
 *
 * @author weizecheng
 * @date 2018/9/4 10:22
 */
@Controller
@RequestMapping("/sales")
public class SalesOrderController {
    private static Logger logger = LoggerFactory.getLogger(SalesOrderController.class);


    @Autowired
    private PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

    @Autowired
    private SalesOrderItemService salesOrderItemService;

    /**
     * 销售订单详细信息
     *
     * @author weizecheng
     * @date 2018/8/21 17:11
     * @param request 请求头
     * @param salesOrderNo 销售订单号
     * @param productCode 商品编码
     * @return com.yhglobal.gongxiao.common.GongxiaoResult
     */
    @RequestMapping("/info")
    @ResponseBody
    public GongxiaoResult getSalesOrderWhenView(HttpServletRequest request, String salesOrderNo,String productCode) {
        GongxiaoResult gongxiaoResult;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());

            SalesOrderItemVO salesOrderItemVO = salesOrderItemService.getSalesOrderItemByOrderNoAndProductCode(rpcHeader,salesOrderNo,productCode,portalUserInfo.getProjectId());
            if(null == salesOrderItemVO){
                gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.TARGET_DATA_NOT_EXIST);
            } else {
                gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), salesOrderItemVO);
            }
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 销售订单列表
     *
     * @author weizecheng
     * @date 2018/8/24 16:26
     * @param request  请求头
     * @param orderNo 销售订单号
     * @param outDate 出库时间
     * @param createTime 创建时间
     * @param orderStatus 订单状态
     * @param pageSize 订单每页数量
     * @param pageNumber 第几页
     * @return GongxiaoResult
     */
    @RequestMapping("/list")
    @ResponseBody
    public GongxiaoResult listSalesOrderByCondition (HttpServletRequest request,
                                                     @RequestParam(defaultValue = "") String orderNo,
                                                     @RequestParam(defaultValue = "") String outDate,
                                                     @RequestParam(defaultValue = "") String createTime,
                                                     @RequestParam(defaultValue = "0") Integer orderStatus,
                                                     int pageSize,
                                                     int pageNumber) {
        GongxiaoResult gongxiaoResult;
        String traceId = null;
        try {
            long projectId = portalUserInfo.getProjectId();
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][listSalesOrderByCondition] params:  salesOrderNo={}, projectId={}, outDate={}, createTime={}, " +
                    "orderStatus={}, pageSize={}, pageRow={}", traceId, orderNo, projectId, outDate, createTime, orderStatus, pageSize, pageNumber);
            SalesOrderServiceStructure.ListOrderPageRequest requestRpc = SalesOrderServiceStructure.ListOrderPageRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setSalesOrderNo(orderNo)
                    .setDateEnd(outDate)
                    .setDateStart(createTime)
                    .setStatus(orderStatus)
                    .setPage(pageNumber)
                    .setPageSize(pageSize)
                    .build();
            // 获取注入map的客户端实例
            SalesOrderServiceGrpc.SalesOrderServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SalesOrderServiceGrpc.SalesOrderServiceBlockingStub.class);
            SalesOrderServiceStructure.ListOrderPageResponse rpcResp = rpcStub.listOrderPage(requestRpc);

            // 返回页面的数据
            SalesOrderListVO vo = new SalesOrderListVO();
            // 页面订单 数量 数据组装 开始
            LinkedList<SalesOrderCountBO> salesOrderCounts = new LinkedList<>();
            rpcResp.getCountList().forEach(count->{
                SalesOrderCountBO countBO = new SalesOrderCountBO();
                countBO.setCount(count.getCount());
                countBO.setSalesOrderStatus(count.getSatus());
                salesOrderCounts.add(countBO);
            });
            vo.setCount(salesOrderCounts);
            // 页面订单 数量 数据组装 结束
            // 页面订单数据组装 开始
            if(rpcResp.getOrderSize() > 0){
                List<SalesOrderListBO> listOrder = new ArrayList<>(rpcResp.getOrderSize());
                rpcResp.getListList().forEach(order->{
                    SalesOrderListBO bo = new SalesOrderListBO();
                    bo.setCashAmountDouble(order.getCashAmountDouble());
                    bo.setSalesOrderStatus(SalesOrderStatusEnum.getMessage(order.getSalesOrderStatus()));
                    bo.setSettlementMode(order.getSettlementMode());
                    bo.setCashAmountDouble(order.getCashAmountDouble());
                    bo.setSalesOrderNo(order.getSalesOrderNo());
                    bo.setPaymentDays(order.getPaymentDays() == 0 ? null : order.getPaymentDays());
                    bo.setDistributorName(order.getDistributorName());
                    bo.setTotalOrderAmountDouble(order.getTotalOrderAmountDouble());
                    bo.setTotalQuantity(order.getTotalQuantity());
                    bo.setUnhandledQuantity(order.getUnhandledQuantity());
                    bo.setCreateTime(GrpcCommonUtil.getDateTimeFormat(order.getCreateTime()));
                    bo.setPaidTime(GrpcCommonUtil.getDateTimeFormat(order.getPaidTime()));
                    bo.setPurchaseNo(order.getPurchaseNo());
                    bo.setSalesOrderAttribute(order.getOrderAttribute());
                    // 账单过期时间
                    listOrder.add(bo);
                });
                vo.setList(listOrder);
                vo.setTotal(rpcResp.getTotal());
            }
            // 页面订单数据组装 结束
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), vo);
            logger.info("#traceId={}# [OUT] get list success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
    /**
     * 销售订单审核
     *
     * @author weizecheng
     * @date 2018/8/24 16:31
     * @param request 请求头
     * @param salesOrderNo 销售订单号
     * @return  GongxiaoResult
     */
    @RequestMapping("/review")
    @ResponseBody
    public GongxiaoResult reviewSalesOrder(HttpServletRequest request,
                                           String salesOrderNo,
                                           String itemList) {

        GongxiaoResult gongxiaoResult = null;
//        String itemList ="[{\"productCode\":\"999888\",\"count\":200},{\"productCode\":\"888777\",\"count\":100}]";
        String traceId = null;
        try {
            //从session中获取项目id
            long projectId = portalUserInfo.getProjectId();

            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            // 拼接请求头 开始
            List<ReviewSalesOrderDTO> reviewSalesOrderDTOS = JSON.parseArray(itemList,ReviewSalesOrderDTO.class);
            List<SalesOrderServiceStructure.ReviewSalesOrderItem> reviewSalesOrderItems = new LinkedList<>();
            reviewSalesOrderDTOS.forEach(item->{
                SalesOrderServiceStructure.ReviewSalesOrderItem reviewSalesOrderItem = SalesOrderServiceStructure.ReviewSalesOrderItem.newBuilder()
                        .setCount(item.getCount())
                        .setProductCode(item.getProductCode())
                        .setShortageReason(item.getShortageReason() == null ? "":item.getShortageReason())
                        .build();
                reviewSalesOrderItems.add(reviewSalesOrderItem);
            });
            // 拼接请求头 结束
            logger.info("#traceId={}# [IN][reviewSalesOrder] params:  salesOrderNo={}, itemList={}", traceId, salesOrderNo, itemList);

            SalesOrderServiceStructure.ReviewSalesOrderRequest rpcRequest = SalesOrderServiceStructure.ReviewSalesOrderRequest.newBuilder()
                    .setProjectId(projectId)
                    .setRpcHeader(rpcHeader)
                    .setSalesOrderNo(salesOrderNo)
                    .addAllReviewItem(reviewSalesOrderItems).build();
            SalesOrderServiceGrpc.SalesOrderServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SalesOrderServiceGrpc.SalesOrderServiceBlockingStub.class);
            GongxiaoRpc.RpcResult rpcResponse = rpcStub.reviewSalesOrder(rpcRequest);
            if (rpcResponse.getReturnCode() == 0) {
                //修改成功
                gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), null);
                logger.info("#traceId={}# [OUT] review success. salesOrderNo={}", traceId, salesOrderNo);
            } else {
                //修改失败
                gongxiaoResult = new GongxiaoResult(rpcResponse.getReturnCode(), rpcResponse.getMsg());
                logger.info("#traceId={}# [OUT] review fail. salesOrderNo={}", traceId, salesOrderNo);
            }
            logger.info("#traceId={}# [OUT] review.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 驳回销售订单
     *
     * @author weizecheng
     * @date 2018/8/28 10:23
     * @param request 请求消息头
     * @param salesOrderNo 销售订单号
     * @return GongxiaoResult
     */
    @RequestMapping("/rejected")
    @ResponseBody
    public GongxiaoResult rejectedSalesOrder(HttpServletRequest request,
                                             String salesOrderNo){
        GongxiaoResult gongxiaoResult;
        String traceId = null;
        try {
            long projectId = portalUserInfo.getProjectId();
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            // 设置请求消息头
            SalesOrderServiceStructure.CommonSalesOrderRequest rpcRequest = SalesOrderServiceStructure.CommonSalesOrderRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setSalesOrderNo(salesOrderNo)
                    .build();
            SalesOrderServiceGrpc.SalesOrderServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SalesOrderServiceGrpc.SalesOrderServiceBlockingStub.class);
            GongxiaoRpc.RpcResult rpcResult =rpcStub.rejectedSalesOrder(rpcRequest);
            if (rpcResult.getReturnCode() == 0) {
                // 驳回修改成功
                gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), null);
                logger.info("#traceId={}# [OUT] rejected success. salesOrderNo={}", traceId, salesOrderNo);
            } else {
                // 驳回修改失败
                gongxiaoResult = new GongxiaoResult(rpcResult.getReturnCode(), rpcResult.getMsg());
                logger.info("#traceId={}# [OUT] rejected fail. salesOrderNo={}", traceId, salesOrderNo);
            }
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] rejected errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 更新销售订单地址
     * // TODO RPC已写完 这里暂停下
     *
     * @param request 请求头
     * @param salesOrderId 销售订单Id
     * @return GongxiaoResult
     */
    @RequestMapping(value = "/updateAddress",method = RequestMethod.POST)
    @ResponseBody
    public GongxiaoResult updateSalesOrderAddress(HttpServletRequest request,
                                                  String salesOrderId,
                                                  String arrived,
                                                  String receiver,
                                                  String receiverTel,
                                                  String receivingAddress) {
        GongxiaoResult gongxiaoResult;
        String traceId = null;
        try {
            Long projectId = portalUserInfo.getProjectId();
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            SalesOrderAddressServiceStructure.UpdateShippingAddressRequest rpcRequest = SalesOrderAddressServiceStructure.UpdateShippingAddressRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setArrived(GrpcCommonUtil.getProtoParam(arrived))
                    .setReceiver(GrpcCommonUtil.getProtoParam(receiver))
                    .setReceiverTel(GrpcCommonUtil.getProtoParam(receiverTel))
                    .setReceivingAddress(GrpcCommonUtil.getProtoParam(receivingAddress))
                    .setSalesOrderId(Long.parseLong(salesOrderId))
                    .setProjectId(projectId)
                    .build();
            SalesOrderAddressServiceGrpc.SalesOrderAddressServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SalesOrderAddressServiceGrpc.SalesOrderAddressServiceBlockingStub.class);
            GongxiaoRpc.RpcResult rpcResult =rpcStub.updateRecipientInfo(rpcRequest);
            if (rpcResult.getReturnCode() == 0) {
                // 驳回修改成功
                gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), null);
                logger.info("#traceId={}# [OUT] updateAddress success. salesOrderId={},arrived={},receiver={},receiverTel={},receivingAddress={}", traceId, salesOrderId,arrived,receiver,receiverTel,receivingAddress);
            } else {
                // 驳回修改失败
                gongxiaoResult = new GongxiaoResult(rpcResult.getReturnCode(), rpcResult.getMsg());
                logger.info("#traceId={}# [OUT] updateAddress fail. salesOrderId={},arrived={},receiver={},receiverTel={},receivingAddress={}", traceId, salesOrderId,arrived,receiver,receiverTel,receivingAddress);
            }
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] rejected errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

}


