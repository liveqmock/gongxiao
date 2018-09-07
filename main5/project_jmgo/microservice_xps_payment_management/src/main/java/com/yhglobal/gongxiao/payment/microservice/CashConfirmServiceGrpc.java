package com.yhglobal.gongxiao.payment.microservice;

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
    comments = "Source: payment.cashConfirm.proto")
public final class CashConfirmServiceGrpc {

  private CashConfirmServiceGrpc() {}

  public static final String SERVICE_NAME = "CashConfirmService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.CreateSalesOrderCashConfirmOrderRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_CREATE_SALES_ORDER_CASH_CONFIRM_ORDER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.CreateSalesOrderCashConfirmOrderRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "CashConfirmService", "createSalesOrderCashConfirmOrder"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.CreateSalesOrderCashConfirmOrderRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectListSelectiveRequest,
      com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectListSelectiveResponse> METHOD_SELECT_LIST_SELECTIVE =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectListSelectiveRequest, com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectListSelectiveResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "CashConfirmService", "selectListSelective"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectListSelectiveRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectListSelectiveResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectConfirmListRequest,
      com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectConfirmListResponse> METHOD_SELECT_CONFIRM_LIST =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectConfirmListRequest, com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectConfirmListResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "CashConfirmService", "selectConfirmList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectConfirmListRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectConfirmListResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.ConfirmCashRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_CONFIRM_CASH =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.ConfirmCashRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "CashConfirmService", "confirmCash"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.ConfirmCashRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.CancelCashConfirmRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_CANCEL_CASH_CONFIRM =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.CancelCashConfirmRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "CashConfirmService", "cancelCashConfirm"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.CancelCashConfirmRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.ConfirmNegativeAmountAutomaticallyRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_CONFIRM_NEGATIVE_AMOUNT_AUTOMATICALLY =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.ConfirmNegativeAmountAutomaticallyRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "CashConfirmService", "confirmNegativeAmountAutomatically"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.ConfirmNegativeAmountAutomaticallyRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CashConfirmServiceStub newStub(io.grpc.Channel channel) {
    return new CashConfirmServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CashConfirmServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new CashConfirmServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CashConfirmServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new CashConfirmServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class CashConfirmServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *创建现金应收单
     * </pre>
     */
    public void createSalesOrderCashConfirmOrder(com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.CreateSalesOrderCashConfirmOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CREATE_SALES_ORDER_CASH_CONFIRM_ORDER, responseObserver);
    }

    /**
     * <pre>
     *选择性查询现金确认列表(均为可选条件)
     * </pre>
     */
    public void selectListSelective(com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectListSelectiveRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectListSelectiveResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_LIST_SELECTIVE, responseObserver);
    }

    /**
     * <pre>
     *收款确认详情页面
     * </pre>
     */
    public void selectConfirmList(com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectConfirmListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectConfirmListResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_CONFIRM_LIST, responseObserver);
    }

    /**
     * <pre>
     *确认收款提交
     * </pre>
     */
    public void confirmCash(com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.ConfirmCashRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CONFIRM_CASH, responseObserver);
    }

    /**
     * <pre>
     *销售模块取消订单确认
     * </pre>
     */
    public void cancelCashConfirm(com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.CancelCashConfirmRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CANCEL_CASH_CONFIRM, responseObserver);
    }

    /**
     * <pre>
     *自动转入“待确认金额为-值”的条目到ad账户
     * </pre>
     */
    public void confirmNegativeAmountAutomatically(com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.ConfirmNegativeAmountAutomaticallyRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CONFIRM_NEGATIVE_AMOUNT_AUTOMATICALLY, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_CREATE_SALES_ORDER_CASH_CONFIRM_ORDER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.CreateSalesOrderCashConfirmOrderRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_CREATE_SALES_ORDER_CASH_CONFIRM_ORDER)))
          .addMethod(
            METHOD_SELECT_LIST_SELECTIVE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectListSelectiveRequest,
                com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectListSelectiveResponse>(
                  this, METHODID_SELECT_LIST_SELECTIVE)))
          .addMethod(
            METHOD_SELECT_CONFIRM_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectConfirmListRequest,
                com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectConfirmListResponse>(
                  this, METHODID_SELECT_CONFIRM_LIST)))
          .addMethod(
            METHOD_CONFIRM_CASH,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.ConfirmCashRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_CONFIRM_CASH)))
          .addMethod(
            METHOD_CANCEL_CASH_CONFIRM,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.CancelCashConfirmRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_CANCEL_CASH_CONFIRM)))
          .addMethod(
            METHOD_CONFIRM_NEGATIVE_AMOUNT_AUTOMATICALLY,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.ConfirmNegativeAmountAutomaticallyRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_CONFIRM_NEGATIVE_AMOUNT_AUTOMATICALLY)))
          .build();
    }
  }

  /**
   */
  public static final class CashConfirmServiceStub extends io.grpc.stub.AbstractStub<CashConfirmServiceStub> {
    private CashConfirmServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CashConfirmServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CashConfirmServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CashConfirmServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *创建现金应收单
     * </pre>
     */
    public void createSalesOrderCashConfirmOrder(com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.CreateSalesOrderCashConfirmOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CREATE_SALES_ORDER_CASH_CONFIRM_ORDER, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *选择性查询现金确认列表(均为可选条件)
     * </pre>
     */
    public void selectListSelective(com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectListSelectiveRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectListSelectiveResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_LIST_SELECTIVE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *收款确认详情页面
     * </pre>
     */
    public void selectConfirmList(com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectConfirmListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectConfirmListResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_CONFIRM_LIST, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *确认收款提交
     * </pre>
     */
    public void confirmCash(com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.ConfirmCashRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CONFIRM_CASH, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *销售模块取消订单确认
     * </pre>
     */
    public void cancelCashConfirm(com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.CancelCashConfirmRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CANCEL_CASH_CONFIRM, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *自动转入“待确认金额为-值”的条目到ad账户
     * </pre>
     */
    public void confirmNegativeAmountAutomatically(com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.ConfirmNegativeAmountAutomaticallyRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CONFIRM_NEGATIVE_AMOUNT_AUTOMATICALLY, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CashConfirmServiceBlockingStub extends io.grpc.stub.AbstractStub<CashConfirmServiceBlockingStub> {
    private CashConfirmServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CashConfirmServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CashConfirmServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CashConfirmServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *创建现金应收单
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult createSalesOrderCashConfirmOrder(com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.CreateSalesOrderCashConfirmOrderRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CREATE_SALES_ORDER_CASH_CONFIRM_ORDER, getCallOptions(), request);
    }

    /**
     * <pre>
     *选择性查询现金确认列表(均为可选条件)
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectListSelectiveResponse selectListSelective(com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectListSelectiveRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_LIST_SELECTIVE, getCallOptions(), request);
    }

    /**
     * <pre>
     *收款确认详情页面
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectConfirmListResponse selectConfirmList(com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectConfirmListRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_CONFIRM_LIST, getCallOptions(), request);
    }

    /**
     * <pre>
     *确认收款提交
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult confirmCash(com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.ConfirmCashRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CONFIRM_CASH, getCallOptions(), request);
    }

    /**
     * <pre>
     *销售模块取消订单确认
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult cancelCashConfirm(com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.CancelCashConfirmRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CANCEL_CASH_CONFIRM, getCallOptions(), request);
    }

    /**
     * <pre>
     *自动转入“待确认金额为-值”的条目到ad账户
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult confirmNegativeAmountAutomatically(com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.ConfirmNegativeAmountAutomaticallyRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CONFIRM_NEGATIVE_AMOUNT_AUTOMATICALLY, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CashConfirmServiceFutureStub extends io.grpc.stub.AbstractStub<CashConfirmServiceFutureStub> {
    private CashConfirmServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CashConfirmServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CashConfirmServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CashConfirmServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *创建现金应收单
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> createSalesOrderCashConfirmOrder(
        com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.CreateSalesOrderCashConfirmOrderRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CREATE_SALES_ORDER_CASH_CONFIRM_ORDER, getCallOptions()), request);
    }

    /**
     * <pre>
     *选择性查询现金确认列表(均为可选条件)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectListSelectiveResponse> selectListSelective(
        com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectListSelectiveRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_LIST_SELECTIVE, getCallOptions()), request);
    }

    /**
     * <pre>
     *收款确认详情页面
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectConfirmListResponse> selectConfirmList(
        com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectConfirmListRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_CONFIRM_LIST, getCallOptions()), request);
    }

    /**
     * <pre>
     *确认收款提交
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> confirmCash(
        com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.ConfirmCashRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CONFIRM_CASH, getCallOptions()), request);
    }

    /**
     * <pre>
     *销售模块取消订单确认
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> cancelCashConfirm(
        com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.CancelCashConfirmRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CANCEL_CASH_CONFIRM, getCallOptions()), request);
    }

    /**
     * <pre>
     *自动转入“待确认金额为-值”的条目到ad账户
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> confirmNegativeAmountAutomatically(
        com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.ConfirmNegativeAmountAutomaticallyRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CONFIRM_NEGATIVE_AMOUNT_AUTOMATICALLY, getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_SALES_ORDER_CASH_CONFIRM_ORDER = 0;
  private static final int METHODID_SELECT_LIST_SELECTIVE = 1;
  private static final int METHODID_SELECT_CONFIRM_LIST = 2;
  private static final int METHODID_CONFIRM_CASH = 3;
  private static final int METHODID_CANCEL_CASH_CONFIRM = 4;
  private static final int METHODID_CONFIRM_NEGATIVE_AMOUNT_AUTOMATICALLY = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CashConfirmServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CashConfirmServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_SALES_ORDER_CASH_CONFIRM_ORDER:
          serviceImpl.createSalesOrderCashConfirmOrder((com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.CreateSalesOrderCashConfirmOrderRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_SELECT_LIST_SELECTIVE:
          serviceImpl.selectListSelective((com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectListSelectiveRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectListSelectiveResponse>) responseObserver);
          break;
        case METHODID_SELECT_CONFIRM_LIST:
          serviceImpl.selectConfirmList((com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectConfirmListRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.SelectConfirmListResponse>) responseObserver);
          break;
        case METHODID_CONFIRM_CASH:
          serviceImpl.confirmCash((com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.ConfirmCashRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_CANCEL_CASH_CONFIRM:
          serviceImpl.cancelCashConfirm((com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.CancelCashConfirmRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_CONFIRM_NEGATIVE_AMOUNT_AUTOMATICALLY:
          serviceImpl.confirmNegativeAmountAutomatically((com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.ConfirmNegativeAmountAutomaticallyRequest) request,
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

  private static final class CashConfirmServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CashConfirmServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CashConfirmServiceDescriptorSupplier())
              .addMethod(METHOD_CREATE_SALES_ORDER_CASH_CONFIRM_ORDER)
              .addMethod(METHOD_SELECT_LIST_SELECTIVE)
              .addMethod(METHOD_SELECT_CONFIRM_LIST)
              .addMethod(METHOD_CONFIRM_CASH)
              .addMethod(METHOD_CANCEL_CASH_CONFIRM)
              .addMethod(METHOD_CONFIRM_NEGATIVE_AMOUNT_AUTOMATICALLY)
              .build();
        }
      }
    }
    return result;
  }
}
