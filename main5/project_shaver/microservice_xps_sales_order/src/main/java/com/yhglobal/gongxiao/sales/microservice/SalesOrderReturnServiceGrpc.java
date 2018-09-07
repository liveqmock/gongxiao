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
    comments = "Source: sales.orderReturn.proto")
public final class SalesOrderReturnServiceGrpc {

  private SalesOrderReturnServiceGrpc() {}

  public static final String SERVICE_NAME = "SalesOrderReturnService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SearchOrderReturnReq,
      com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnPageInfoResp> METHOD_GETS_ORDER_RETURN =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SearchOrderReturnReq, com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnPageInfoResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderReturnService", "getsOrderReturn"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SearchOrderReturnReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnPageInfoResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SelectClassificationCountReq,
      com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnClassificationCountResp> METHOD_SELECT_CLASSIFICATION_COUNT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SelectClassificationCountReq, com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnClassificationCountResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderReturnService", "selectClassificationCount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SelectClassificationCountReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnClassificationCountResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnOrderReq,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_SAVE_SALES_RETURN_ORDER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnOrderReq, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderReturnService", "saveSalesReturnOrder"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnOrderReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.CheckSalesReturnOrderReq,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_CHECK_SALES_RETURN_ORDER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.CheckSalesReturnOrderReq, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderReturnService", "checkSalesReturnOrder"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.CheckSalesReturnOrderReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnInboundReq,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_SALES_RETURN_INBOUND =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnInboundReq, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderReturnService", "salesReturnInbound"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnInboundReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.GetSalesReturnReq,
      com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnResp> METHOD_GET_SALES_RETURN =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.GetSalesReturnReq, com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderReturnService", "getSalesReturn"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.GetSalesReturnReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnResp.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SalesOrderReturnServiceStub newStub(io.grpc.Channel channel) {
    return new SalesOrderReturnServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SalesOrderReturnServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SalesOrderReturnServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SalesOrderReturnServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SalesOrderReturnServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SalesOrderReturnServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 查询退货列表
     * </pre>
     */
    public void getsOrderReturn(com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SearchOrderReturnReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnPageInfoResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GETS_ORDER_RETURN, responseObserver);
    }

    /**
     * <pre>
     *查询退货列表统计
     * </pre>
     */
    public void selectClassificationCount(com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SelectClassificationCountReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnClassificationCountResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_CLASSIFICATION_COUNT, responseObserver);
    }

    /**
     * <pre>
     *保存销售退货
     * </pre>
     */
    public void saveSalesReturnOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnOrderReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SAVE_SALES_RETURN_ORDER, responseObserver);
    }

    /**
     * <pre>
     *审核
     * </pre>
     */
    public void checkSalesReturnOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.CheckSalesReturnOrderReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CHECK_SALES_RETURN_ORDER, responseObserver);
    }

    /**
     * <pre>
     *预约入库回调
     * </pre>
     */
    public void salesReturnInbound(com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnInboundReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SALES_RETURN_INBOUND, responseObserver);
    }

    /**
     * <pre>
     *获取退货单详情j
     * </pre>
     */
    public void getSalesReturn(com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.GetSalesReturnReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_SALES_RETURN, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GETS_ORDER_RETURN,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SearchOrderReturnReq,
                com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnPageInfoResp>(
                  this, METHODID_GETS_ORDER_RETURN)))
          .addMethod(
            METHOD_SELECT_CLASSIFICATION_COUNT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SelectClassificationCountReq,
                com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnClassificationCountResp>(
                  this, METHODID_SELECT_CLASSIFICATION_COUNT)))
          .addMethod(
            METHOD_SAVE_SALES_RETURN_ORDER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnOrderReq,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_SAVE_SALES_RETURN_ORDER)))
          .addMethod(
            METHOD_CHECK_SALES_RETURN_ORDER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.CheckSalesReturnOrderReq,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_CHECK_SALES_RETURN_ORDER)))
          .addMethod(
            METHOD_SALES_RETURN_INBOUND,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnInboundReq,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_SALES_RETURN_INBOUND)))
          .addMethod(
            METHOD_GET_SALES_RETURN,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.GetSalesReturnReq,
                com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnResp>(
                  this, METHODID_GET_SALES_RETURN)))
          .build();
    }
  }

  /**
   */
  public static final class SalesOrderReturnServiceStub extends io.grpc.stub.AbstractStub<SalesOrderReturnServiceStub> {
    private SalesOrderReturnServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SalesOrderReturnServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SalesOrderReturnServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SalesOrderReturnServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 查询退货列表
     * </pre>
     */
    public void getsOrderReturn(com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SearchOrderReturnReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnPageInfoResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GETS_ORDER_RETURN, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *查询退货列表统计
     * </pre>
     */
    public void selectClassificationCount(com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SelectClassificationCountReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnClassificationCountResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_CLASSIFICATION_COUNT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *保存销售退货
     * </pre>
     */
    public void saveSalesReturnOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnOrderReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SAVE_SALES_RETURN_ORDER, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *审核
     * </pre>
     */
    public void checkSalesReturnOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.CheckSalesReturnOrderReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CHECK_SALES_RETURN_ORDER, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *预约入库回调
     * </pre>
     */
    public void salesReturnInbound(com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnInboundReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SALES_RETURN_INBOUND, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *获取退货单详情j
     * </pre>
     */
    public void getSalesReturn(com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.GetSalesReturnReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_SALES_RETURN, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SalesOrderReturnServiceBlockingStub extends io.grpc.stub.AbstractStub<SalesOrderReturnServiceBlockingStub> {
    private SalesOrderReturnServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SalesOrderReturnServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SalesOrderReturnServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SalesOrderReturnServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 查询退货列表
     * </pre>
     */
    public com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnPageInfoResp getsOrderReturn(com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SearchOrderReturnReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GETS_ORDER_RETURN, getCallOptions(), request);
    }

    /**
     * <pre>
     *查询退货列表统计
     * </pre>
     */
    public com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnClassificationCountResp selectClassificationCount(com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SelectClassificationCountReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_CLASSIFICATION_COUNT, getCallOptions(), request);
    }

    /**
     * <pre>
     *保存销售退货
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult saveSalesReturnOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnOrderReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SAVE_SALES_RETURN_ORDER, getCallOptions(), request);
    }

    /**
     * <pre>
     *审核
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult checkSalesReturnOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.CheckSalesReturnOrderReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CHECK_SALES_RETURN_ORDER, getCallOptions(), request);
    }

    /**
     * <pre>
     *预约入库回调
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult salesReturnInbound(com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnInboundReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SALES_RETURN_INBOUND, getCallOptions(), request);
    }

    /**
     * <pre>
     *获取退货单详情j
     * </pre>
     */
    public com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnResp getSalesReturn(com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.GetSalesReturnReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_SALES_RETURN, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SalesOrderReturnServiceFutureStub extends io.grpc.stub.AbstractStub<SalesOrderReturnServiceFutureStub> {
    private SalesOrderReturnServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SalesOrderReturnServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SalesOrderReturnServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SalesOrderReturnServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 查询退货列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnPageInfoResp> getsOrderReturn(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SearchOrderReturnReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GETS_ORDER_RETURN, getCallOptions()), request);
    }

    /**
     * <pre>
     *查询退货列表统计
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnClassificationCountResp> selectClassificationCount(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SelectClassificationCountReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_CLASSIFICATION_COUNT, getCallOptions()), request);
    }

    /**
     * <pre>
     *保存销售退货
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> saveSalesReturnOrder(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnOrderReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SAVE_SALES_RETURN_ORDER, getCallOptions()), request);
    }

    /**
     * <pre>
     *审核
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> checkSalesReturnOrder(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.CheckSalesReturnOrderReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CHECK_SALES_RETURN_ORDER, getCallOptions()), request);
    }

    /**
     * <pre>
     *预约入库回调
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> salesReturnInbound(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnInboundReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SALES_RETURN_INBOUND, getCallOptions()), request);
    }

    /**
     * <pre>
     *获取退货单详情j
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnResp> getSalesReturn(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.GetSalesReturnReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_SALES_RETURN, getCallOptions()), request);
    }
  }

  private static final int METHODID_GETS_ORDER_RETURN = 0;
  private static final int METHODID_SELECT_CLASSIFICATION_COUNT = 1;
  private static final int METHODID_SAVE_SALES_RETURN_ORDER = 2;
  private static final int METHODID_CHECK_SALES_RETURN_ORDER = 3;
  private static final int METHODID_SALES_RETURN_INBOUND = 4;
  private static final int METHODID_GET_SALES_RETURN = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SalesOrderReturnServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SalesOrderReturnServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GETS_ORDER_RETURN:
          serviceImpl.getsOrderReturn((com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SearchOrderReturnReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnPageInfoResp>) responseObserver);
          break;
        case METHODID_SELECT_CLASSIFICATION_COUNT:
          serviceImpl.selectClassificationCount((com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SelectClassificationCountReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnClassificationCountResp>) responseObserver);
          break;
        case METHODID_SAVE_SALES_RETURN_ORDER:
          serviceImpl.saveSalesReturnOrder((com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnOrderReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_CHECK_SALES_RETURN_ORDER:
          serviceImpl.checkSalesReturnOrder((com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.CheckSalesReturnOrderReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_SALES_RETURN_INBOUND:
          serviceImpl.salesReturnInbound((com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnInboundReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_GET_SALES_RETURN:
          serviceImpl.getSalesReturn((com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.GetSalesReturnReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.SalesReturnResp>) responseObserver);
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

  private static final class SalesOrderReturnServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.sales.microservice.SalesOrderReturnServiceStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SalesOrderReturnServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SalesOrderReturnServiceDescriptorSupplier())
              .addMethod(METHOD_GETS_ORDER_RETURN)
              .addMethod(METHOD_SELECT_CLASSIFICATION_COUNT)
              .addMethod(METHOD_SAVE_SALES_RETURN_ORDER)
              .addMethod(METHOD_CHECK_SALES_RETURN_ORDER)
              .addMethod(METHOD_SALES_RETURN_INBOUND)
              .addMethod(METHOD_GET_SALES_RETURN)
              .build();
        }
      }
    }
    return result;
  }
}
