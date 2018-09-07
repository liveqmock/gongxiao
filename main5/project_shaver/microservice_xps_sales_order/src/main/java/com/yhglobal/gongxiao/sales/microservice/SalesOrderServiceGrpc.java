package com.yhglobal.gongxiao.sales.microservice;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.4.0)",
    comments = "Source: sales.order.proto")
public final class SalesOrderServiceGrpc {

  private SalesOrderServiceGrpc() {}

  public static final String SERVICE_NAME = "SalesOrderService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest,
      com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderByOrderNoResponse> METHOD_GET_ORDER_BY_ORDER_NO =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest, com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderByOrderNoResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderService", "getOrderByOrderNo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderByOrderNoResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest,
      com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderByOrderNoResponse> METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest, com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderByOrderNoResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderService", "getOrderDetailBySalesOrderNo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderByOrderNoResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemRequest,
      com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemResponse> METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO_AND_PROJECT_CODE =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemRequest, com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderService", "getOrderDetailBySalesOrderNoAndProjectCode"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetListSelectivelyRequest,
      com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetListSelectivelyResponse> METHOD_GET_LIST_SELECTIVELY =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetListSelectivelyRequest, com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetListSelectivelyResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderService", "getListSelectively"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetListSelectivelyRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetListSelectivelyResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ChangeSettlementModeRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_CHANGE_SETTLEMENT_MODE =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ChangeSettlementModeRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderService", "changeSettlementMode"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ChangeSettlementModeRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CreateSalesOrderRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_CREATE_SALES_ORDER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CreateSalesOrderRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderService", "createSalesOrder"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CreateSalesOrderRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.UpdateRecipientInfoRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_UPDATE_RECIPIENT_INFO =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.UpdateRecipientInfoRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderService", "updateRecipientInfo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.UpdateRecipientInfoRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ApproveSalesOrderRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_APPROVE_SALES_ORDER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ApproveSalesOrderRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderService", "approveSalesOrder"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ApproveSalesOrderRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_CANCEL_SALES_ORDER_BY_DISTRIBUTOR =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderService", "cancelSalesOrderByDistributor"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_REJECT_SALES_ORDER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderService", "rejectSalesOrder"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_CANCEL_SALES_ORDER_APPROVE =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderService", "cancelSalesOrderApprove"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_CONFIRM_SALES_ORDER_AMOUNT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderService", "confirmSalesOrderAmount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListWithPageRequest,
      com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListWithPageResponse> METHOD_SEARCH_ORDER_LIST_WITH_PAGE =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListWithPageRequest, com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListWithPageResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderService", "searchOrderListWithPage"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListWithPageRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListWithPageResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListRequest,
      com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListResponse> METHOD_SEARCH_ORDER_LIST =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListRequest, com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderService", "searchOrderList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest,
      com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.WhetherOutboundResponse> METHOD_WHETHER_OUTBOUND =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest, com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.WhetherOutboundResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderService", "whetherOutbound"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.WhetherOutboundResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_CANCEL_RECEIVED_CASH =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderService", "cancelReceivedCash"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SalesOrderServiceStub newStub(io.grpc.Channel channel) {
    return new SalesOrderServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SalesOrderServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SalesOrderServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SalesOrderServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SalesOrderServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SalesOrderServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 审核时查询订单详情
     * </pre>
     */
    public void getOrderByOrderNo(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderByOrderNoResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_ORDER_BY_ORDER_NO, responseObserver);
    }

    /**
     * <pre>
     *查询销售单详情
     * </pre>
     */
    public void getOrderDetailBySalesOrderNo(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderByOrderNoResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO, responseObserver);
    }

    /**
     * <pre>
     *查询销售单明细
     * </pre>
     */
    public void getOrderDetailBySalesOrderNoAndProjectCode(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO_AND_PROJECT_CODE, responseObserver);
    }

    /**
     * <pre>
     * 选择性查询
     * </pre>
     */
    public void getListSelectively(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetListSelectivelyRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetListSelectivelyResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_LIST_SELECTIVELY, responseObserver);
    }

    /**
     * <pre>
     * 修改销售单结算方式
     * </pre>
     */
    public void changeSettlementMode(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ChangeSettlementModeRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CHANGE_SETTLEMENT_MODE, responseObserver);
    }

    /**
     * <pre>
     *新建销售单
     * </pre>
     */
    public void createSalesOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CreateSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CREATE_SALES_ORDER, responseObserver);
    }

    /**
     * <pre>
     *修改收件人信息
     * </pre>
     */
    public void updateRecipientInfo(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.UpdateRecipientInfoRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_RECIPIENT_INFO, responseObserver);
    }

    /**
     * <pre>
     *审核销售单
     * </pre>
     */
    public void approveSalesOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ApproveSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_APPROVE_SALES_ORDER, responseObserver);
    }

    /**
     * <pre>
     *AD取消销售单
     * </pre>
     */
    public void cancelSalesOrderByDistributor(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CANCEL_SALES_ORDER_BY_DISTRIBUTOR, responseObserver);
    }

    /**
     * <pre>
     *驳回销售单
     * </pre>
     */
    public void rejectSalesOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_REJECT_SALES_ORDER, responseObserver);
    }

    /**
     * <pre>
     *越海取消审核
     * </pre>
     */
    public void cancelSalesOrderApprove(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CANCEL_SALES_ORDER_APPROVE, responseObserver);
    }

    /**
     * <pre>
     *收款确认
     * </pre>
     */
    public void confirmSalesOrderAmount(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CONFIRM_SALES_ORDER_AMOUNT, responseObserver);
    }

    /**
     * <pre>
     *todo 罗一
     *ad查询销售单列表(带分页)
     * </pre>
     */
    public void searchOrderListWithPage(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListWithPageRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListWithPageResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SEARCH_ORDER_LIST_WITH_PAGE, responseObserver);
    }

    /**
     * <pre>
     *todo 罗一
     *ad查询销售单列表(不带分页)
     * </pre>
     */
    public void searchOrderList(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SEARCH_ORDER_LIST, responseObserver);
    }

    /**
     * <pre>
     * 判断是否出库
     * </pre>
     */
    public void whetherOutbound(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.WhetherOutboundResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_WHETHER_OUTBOUND, responseObserver);
    }

    /**
     * <pre>
     *取消收款
     * </pre>
     */
    public void cancelReceivedCash(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CANCEL_RECEIVED_CASH, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_ORDER_BY_ORDER_NO,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest,
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderByOrderNoResponse>(
                  this, METHODID_GET_ORDER_BY_ORDER_NO)))
          .addMethod(
            METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest,
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderByOrderNoResponse>(
                  this, METHODID_GET_ORDER_DETAIL_BY_SALES_ORDER_NO)))
          .addMethod(
            METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO_AND_PROJECT_CODE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemRequest,
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemResponse>(
                  this, METHODID_GET_ORDER_DETAIL_BY_SALES_ORDER_NO_AND_PROJECT_CODE)))
          .addMethod(
            METHOD_GET_LIST_SELECTIVELY,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetListSelectivelyRequest,
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetListSelectivelyResponse>(
                  this, METHODID_GET_LIST_SELECTIVELY)))
          .addMethod(
            METHOD_CHANGE_SETTLEMENT_MODE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ChangeSettlementModeRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_CHANGE_SETTLEMENT_MODE)))
          .addMethod(
            METHOD_CREATE_SALES_ORDER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CreateSalesOrderRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_CREATE_SALES_ORDER)))
          .addMethod(
            METHOD_UPDATE_RECIPIENT_INFO,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.UpdateRecipientInfoRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_UPDATE_RECIPIENT_INFO)))
          .addMethod(
            METHOD_APPROVE_SALES_ORDER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ApproveSalesOrderRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_APPROVE_SALES_ORDER)))
          .addMethod(
            METHOD_CANCEL_SALES_ORDER_BY_DISTRIBUTOR,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_CANCEL_SALES_ORDER_BY_DISTRIBUTOR)))
          .addMethod(
            METHOD_REJECT_SALES_ORDER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_REJECT_SALES_ORDER)))
          .addMethod(
            METHOD_CANCEL_SALES_ORDER_APPROVE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_CANCEL_SALES_ORDER_APPROVE)))
          .addMethod(
            METHOD_CONFIRM_SALES_ORDER_AMOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_CONFIRM_SALES_ORDER_AMOUNT)))
          .addMethod(
            METHOD_SEARCH_ORDER_LIST_WITH_PAGE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListWithPageRequest,
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListWithPageResponse>(
                  this, METHODID_SEARCH_ORDER_LIST_WITH_PAGE)))
          .addMethod(
            METHOD_SEARCH_ORDER_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListRequest,
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListResponse>(
                  this, METHODID_SEARCH_ORDER_LIST)))
          .addMethod(
            METHOD_WHETHER_OUTBOUND,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest,
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.WhetherOutboundResponse>(
                  this, METHODID_WHETHER_OUTBOUND)))
          .addMethod(
            METHOD_CANCEL_RECEIVED_CASH,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_CANCEL_RECEIVED_CASH)))
          .build();
    }
  }

  /**
   */
  public static final class SalesOrderServiceStub extends io.grpc.stub.AbstractStub<SalesOrderServiceStub> {
    private SalesOrderServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SalesOrderServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SalesOrderServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SalesOrderServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 审核时查询订单详情
     * </pre>
     */
    public void getOrderByOrderNo(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderByOrderNoResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_ORDER_BY_ORDER_NO, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *查询销售单详情
     * </pre>
     */
    public void getOrderDetailBySalesOrderNo(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderByOrderNoResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *查询销售单明细
     * </pre>
     */
    public void getOrderDetailBySalesOrderNoAndProjectCode(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO_AND_PROJECT_CODE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 选择性查询
     * </pre>
     */
    public void getListSelectively(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetListSelectivelyRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetListSelectivelyResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_LIST_SELECTIVELY, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 修改销售单结算方式
     * </pre>
     */
    public void changeSettlementMode(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ChangeSettlementModeRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CHANGE_SETTLEMENT_MODE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *新建销售单
     * </pre>
     */
    public void createSalesOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CreateSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CREATE_SALES_ORDER, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *修改收件人信息
     * </pre>
     */
    public void updateRecipientInfo(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.UpdateRecipientInfoRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_RECIPIENT_INFO, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *审核销售单
     * </pre>
     */
    public void approveSalesOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ApproveSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_APPROVE_SALES_ORDER, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *AD取消销售单
     * </pre>
     */
    public void cancelSalesOrderByDistributor(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CANCEL_SALES_ORDER_BY_DISTRIBUTOR, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *驳回销售单
     * </pre>
     */
    public void rejectSalesOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_REJECT_SALES_ORDER, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *越海取消审核
     * </pre>
     */
    public void cancelSalesOrderApprove(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CANCEL_SALES_ORDER_APPROVE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *收款确认
     * </pre>
     */
    public void confirmSalesOrderAmount(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CONFIRM_SALES_ORDER_AMOUNT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *todo 罗一
     *ad查询销售单列表(带分页)
     * </pre>
     */
    public void searchOrderListWithPage(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListWithPageRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListWithPageResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SEARCH_ORDER_LIST_WITH_PAGE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *todo 罗一
     *ad查询销售单列表(不带分页)
     * </pre>
     */
    public void searchOrderList(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SEARCH_ORDER_LIST, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 判断是否出库
     * </pre>
     */
    public void whetherOutbound(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.WhetherOutboundResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_WHETHER_OUTBOUND, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *取消收款
     * </pre>
     */
    public void cancelReceivedCash(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CANCEL_RECEIVED_CASH, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SalesOrderServiceBlockingStub extends io.grpc.stub.AbstractStub<SalesOrderServiceBlockingStub> {
    private SalesOrderServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SalesOrderServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SalesOrderServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SalesOrderServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 审核时查询订单详情
     * </pre>
     */
    public com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderByOrderNoResponse getOrderByOrderNo(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_ORDER_BY_ORDER_NO, getCallOptions(), request);
    }

    /**
     * <pre>
     *查询销售单详情
     * </pre>
     */
    public com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderByOrderNoResponse getOrderDetailBySalesOrderNo(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO, getCallOptions(), request);
    }

    /**
     * <pre>
     *查询销售单明细
     * </pre>
     */
    public com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemResponse getOrderDetailBySalesOrderNoAndProjectCode(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO_AND_PROJECT_CODE, getCallOptions(), request);
    }

    /**
     * <pre>
     * 选择性查询
     * </pre>
     */
    public com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetListSelectivelyResponse getListSelectively(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetListSelectivelyRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_LIST_SELECTIVELY, getCallOptions(), request);
    }

    /**
     * <pre>
     * 修改销售单结算方式
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult changeSettlementMode(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ChangeSettlementModeRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CHANGE_SETTLEMENT_MODE, getCallOptions(), request);
    }

    /**
     * <pre>
     *新建销售单
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult createSalesOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CreateSalesOrderRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CREATE_SALES_ORDER, getCallOptions(), request);
    }

    /**
     * <pre>
     *修改收件人信息
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult updateRecipientInfo(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.UpdateRecipientInfoRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_RECIPIENT_INFO, getCallOptions(), request);
    }

    /**
     * <pre>
     *审核销售单
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult approveSalesOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ApproveSalesOrderRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_APPROVE_SALES_ORDER, getCallOptions(), request);
    }

    /**
     * <pre>
     *AD取消销售单
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult cancelSalesOrderByDistributor(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CANCEL_SALES_ORDER_BY_DISTRIBUTOR, getCallOptions(), request);
    }

    /**
     * <pre>
     *驳回销售单
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult rejectSalesOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_REJECT_SALES_ORDER, getCallOptions(), request);
    }

    /**
     * <pre>
     *越海取消审核
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult cancelSalesOrderApprove(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CANCEL_SALES_ORDER_APPROVE, getCallOptions(), request);
    }

    /**
     * <pre>
     *收款确认
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult confirmSalesOrderAmount(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CONFIRM_SALES_ORDER_AMOUNT, getCallOptions(), request);
    }

    /**
     * <pre>
     *todo 罗一
     *ad查询销售单列表(带分页)
     * </pre>
     */
    public com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListWithPageResponse searchOrderListWithPage(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListWithPageRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SEARCH_ORDER_LIST_WITH_PAGE, getCallOptions(), request);
    }

    /**
     * <pre>
     *todo 罗一
     *ad查询销售单列表(不带分页)
     * </pre>
     */
    public com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListResponse searchOrderList(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SEARCH_ORDER_LIST, getCallOptions(), request);
    }

    /**
     * <pre>
     * 判断是否出库
     * </pre>
     */
    public com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.WhetherOutboundResponse whetherOutbound(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_WHETHER_OUTBOUND, getCallOptions(), request);
    }

    /**
     * <pre>
     *取消收款
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult cancelReceivedCash(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CANCEL_RECEIVED_CASH, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SalesOrderServiceFutureStub extends io.grpc.stub.AbstractStub<SalesOrderServiceFutureStub> {
    private SalesOrderServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SalesOrderServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SalesOrderServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SalesOrderServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 审核时查询订单详情
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderByOrderNoResponse> getOrderByOrderNo(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_ORDER_BY_ORDER_NO, getCallOptions()), request);
    }

    /**
     * <pre>
     *查询销售单详情
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderByOrderNoResponse> getOrderDetailBySalesOrderNo(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO, getCallOptions()), request);
    }

    /**
     * <pre>
     *查询销售单明细
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemResponse> getOrderDetailBySalesOrderNoAndProjectCode(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO_AND_PROJECT_CODE, getCallOptions()), request);
    }

    /**
     * <pre>
     * 选择性查询
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetListSelectivelyResponse> getListSelectively(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetListSelectivelyRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_LIST_SELECTIVELY, getCallOptions()), request);
    }

    /**
     * <pre>
     * 修改销售单结算方式
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> changeSettlementMode(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ChangeSettlementModeRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CHANGE_SETTLEMENT_MODE, getCallOptions()), request);
    }

    /**
     * <pre>
     *新建销售单
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> createSalesOrder(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CreateSalesOrderRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CREATE_SALES_ORDER, getCallOptions()), request);
    }

    /**
     * <pre>
     *修改收件人信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> updateRecipientInfo(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.UpdateRecipientInfoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_RECIPIENT_INFO, getCallOptions()), request);
    }

    /**
     * <pre>
     *审核销售单
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> approveSalesOrder(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ApproveSalesOrderRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_APPROVE_SALES_ORDER, getCallOptions()), request);
    }

    /**
     * <pre>
     *AD取消销售单
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> cancelSalesOrderByDistributor(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CANCEL_SALES_ORDER_BY_DISTRIBUTOR, getCallOptions()), request);
    }

    /**
     * <pre>
     *驳回销售单
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> rejectSalesOrder(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_REJECT_SALES_ORDER, getCallOptions()), request);
    }

    /**
     * <pre>
     *越海取消审核
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> cancelSalesOrderApprove(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CANCEL_SALES_ORDER_APPROVE, getCallOptions()), request);
    }

    /**
     * <pre>
     *收款确认
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> confirmSalesOrderAmount(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CONFIRM_SALES_ORDER_AMOUNT, getCallOptions()), request);
    }

    /**
     * <pre>
     *todo 罗一
     *ad查询销售单列表(带分页)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListWithPageResponse> searchOrderListWithPage(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListWithPageRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SEARCH_ORDER_LIST_WITH_PAGE, getCallOptions()), request);
    }

    /**
     * <pre>
     *todo 罗一
     *ad查询销售单列表(不带分页)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListResponse> searchOrderList(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SEARCH_ORDER_LIST, getCallOptions()), request);
    }

    /**
     * <pre>
     * 判断是否出库
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.WhetherOutboundResponse> whetherOutbound(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_WHETHER_OUTBOUND, getCallOptions()), request);
    }

    /**
     * <pre>
     *取消收款
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> cancelReceivedCash(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CANCEL_RECEIVED_CASH, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_ORDER_BY_ORDER_NO = 0;
  private static final int METHODID_GET_ORDER_DETAIL_BY_SALES_ORDER_NO = 1;
  private static final int METHODID_GET_ORDER_DETAIL_BY_SALES_ORDER_NO_AND_PROJECT_CODE = 2;
  private static final int METHODID_GET_LIST_SELECTIVELY = 3;
  private static final int METHODID_CHANGE_SETTLEMENT_MODE = 4;
  private static final int METHODID_CREATE_SALES_ORDER = 5;
  private static final int METHODID_UPDATE_RECIPIENT_INFO = 6;
  private static final int METHODID_APPROVE_SALES_ORDER = 7;
  private static final int METHODID_CANCEL_SALES_ORDER_BY_DISTRIBUTOR = 8;
  private static final int METHODID_REJECT_SALES_ORDER = 9;
  private static final int METHODID_CANCEL_SALES_ORDER_APPROVE = 10;
  private static final int METHODID_CONFIRM_SALES_ORDER_AMOUNT = 11;
  private static final int METHODID_SEARCH_ORDER_LIST_WITH_PAGE = 12;
  private static final int METHODID_SEARCH_ORDER_LIST = 13;
  private static final int METHODID_WHETHER_OUTBOUND = 14;
  private static final int METHODID_CANCEL_RECEIVED_CASH = 15;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SalesOrderServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SalesOrderServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_ORDER_BY_ORDER_NO:
          serviceImpl.getOrderByOrderNo((com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderByOrderNoResponse>) responseObserver);
          break;
        case METHODID_GET_ORDER_DETAIL_BY_SALES_ORDER_NO:
          serviceImpl.getOrderDetailBySalesOrderNo((com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderByOrderNoResponse>) responseObserver);
          break;
        case METHODID_GET_ORDER_DETAIL_BY_SALES_ORDER_NO_AND_PROJECT_CODE:
          serviceImpl.getOrderDetailBySalesOrderNoAndProjectCode((com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemResponse>) responseObserver);
          break;
        case METHODID_GET_LIST_SELECTIVELY:
          serviceImpl.getListSelectively((com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetListSelectivelyRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetListSelectivelyResponse>) responseObserver);
          break;
        case METHODID_CHANGE_SETTLEMENT_MODE:
          serviceImpl.changeSettlementMode((com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ChangeSettlementModeRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_CREATE_SALES_ORDER:
          serviceImpl.createSalesOrder((com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CreateSalesOrderRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_UPDATE_RECIPIENT_INFO:
          serviceImpl.updateRecipientInfo((com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.UpdateRecipientInfoRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_APPROVE_SALES_ORDER:
          serviceImpl.approveSalesOrder((com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ApproveSalesOrderRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_CANCEL_SALES_ORDER_BY_DISTRIBUTOR:
          serviceImpl.cancelSalesOrderByDistributor((com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_REJECT_SALES_ORDER:
          serviceImpl.rejectSalesOrder((com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_CANCEL_SALES_ORDER_APPROVE:
          serviceImpl.cancelSalesOrderApprove((com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_CONFIRM_SALES_ORDER_AMOUNT:
          serviceImpl.confirmSalesOrderAmount((com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_SEARCH_ORDER_LIST_WITH_PAGE:
          serviceImpl.searchOrderListWithPage((com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListWithPageRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListWithPageResponse>) responseObserver);
          break;
        case METHODID_SEARCH_ORDER_LIST:
          serviceImpl.searchOrderList((com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.SearchOrderListResponse>) responseObserver);
          break;
        case METHODID_WHETHER_OUTBOUND:
          serviceImpl.whetherOutbound((com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.WhetherOutboundResponse>) responseObserver);
          break;
        case METHODID_CANCEL_RECEIVED_CASH:
          serviceImpl.cancelReceivedCash((com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class SalesOrderServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SalesOrderServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SalesOrderServiceDescriptorSupplier())
              .addMethod(METHOD_GET_ORDER_BY_ORDER_NO)
              .addMethod(METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO)
              .addMethod(METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO_AND_PROJECT_CODE)
              .addMethod(METHOD_GET_LIST_SELECTIVELY)
              .addMethod(METHOD_CHANGE_SETTLEMENT_MODE)
              .addMethod(METHOD_CREATE_SALES_ORDER)
              .addMethod(METHOD_UPDATE_RECIPIENT_INFO)
              .addMethod(METHOD_APPROVE_SALES_ORDER)
              .addMethod(METHOD_CANCEL_SALES_ORDER_BY_DISTRIBUTOR)
              .addMethod(METHOD_REJECT_SALES_ORDER)
              .addMethod(METHOD_CANCEL_SALES_ORDER_APPROVE)
              .addMethod(METHOD_CONFIRM_SALES_ORDER_AMOUNT)
              .addMethod(METHOD_SEARCH_ORDER_LIST_WITH_PAGE)
              .addMethod(METHOD_SEARCH_ORDER_LIST)
              .addMethod(METHOD_WHETHER_OUTBOUND)
              .addMethod(METHOD_CANCEL_RECEIVED_CASH)
              .build();
        }
      }
    }
    return result;
  }
}
