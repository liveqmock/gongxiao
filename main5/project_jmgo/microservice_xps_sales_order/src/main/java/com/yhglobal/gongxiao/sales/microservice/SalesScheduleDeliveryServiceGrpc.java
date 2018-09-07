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
    comments = "Source: sales.deliverySchedule.proto")
public final class SalesScheduleDeliveryServiceGrpc {

  private SalesScheduleDeliveryServiceGrpc() {}

  public static final String SERVICE_NAME = "SalesScheduleDeliveryService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.CreateScheduleOrderRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_CREATE_SCHEDULE_ORDER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.CreateScheduleOrderRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesScheduleDeliveryService", "createScheduleOrder"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.CreateScheduleOrderRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_CANCEL_SALES_OUTBOUND_ORDER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesScheduleDeliveryService", "cancelSalesOutboundOrder"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SelectSaleScheduleProductListRequest,
      com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SelectSaleScheduleProductListResponse> METHOD_SELECT_SALE_SCHEDULE_PRODUCT_LIST =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SelectSaleScheduleProductListRequest, com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SelectSaleScheduleProductListResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesScheduleDeliveryService", "selectSaleScheduleProductList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SelectSaleScheduleProductListRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SelectSaleScheduleProductListResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SyncSalesOutboundOrderToEasRequest,
      com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SyncSalesOutboundOrderToEasResponse> METHOD_SYNC_SALES_OUTBOUND_ORDER_TO_EAS =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SyncSalesOutboundOrderToEasRequest, com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SyncSalesOutboundOrderToEasResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesScheduleDeliveryService", "syncSalesOutboundOrderToEas"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SyncSalesOutboundOrderToEasRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SyncSalesOutboundOrderToEasResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_SUBMIT_OUTBOUND_ORDER_TO_TMS =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesScheduleDeliveryService", "submitOutboundOrderToTms"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_ITEMS_OUTBOUND_FINISHED =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesScheduleDeliveryService", "itemsOutboundFinished"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundSignedRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_OUTBOUND_SIGNED =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundSignedRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesScheduleDeliveryService", "outboundSigned"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundSignedRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SalesScheduleDeliveryServiceStub newStub(io.grpc.Channel channel) {
    return new SalesScheduleDeliveryServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SalesScheduleDeliveryServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SalesScheduleDeliveryServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SalesScheduleDeliveryServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SalesScheduleDeliveryServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SalesScheduleDeliveryServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *新建预约发货单
     * </pre>
     */
    public void createScheduleOrder(com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.CreateScheduleOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CREATE_SCHEDULE_ORDER, responseObserver);
    }

    /**
     * <pre>
     * 取消销售出库单
     * </pre>
     */
    public void cancelSalesOutboundOrder(com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CANCEL_SALES_OUTBOUND_ORDER, responseObserver);
    }

    /**
     * <pre>
     *查询销售单可发货商品
     * </pre>
     */
    public void selectSaleScheduleProductList(com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SelectSaleScheduleProductListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SelectSaleScheduleProductListResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_SALE_SCHEDULE_PRODUCT_LIST, responseObserver);
    }

    /**
     */
    public void syncSalesOutboundOrderToEas(com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SyncSalesOutboundOrderToEasRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SyncSalesOutboundOrderToEasResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SYNC_SALES_OUTBOUND_ORDER_TO_EAS, responseObserver);
    }

    /**
     * <pre>
     *通知tms派车
     * </pre>
     */
    public void submitOutboundOrderToTms(com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SUBMIT_OUTBOUND_ORDER_TO_TMS, responseObserver);
    }

    /**
     * <pre>
     *出库完成
     * </pre>
     */
    public void itemsOutboundFinished(com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_ITEMS_OUTBOUND_FINISHED, responseObserver);
    }

    /**
     * <pre>
     *签收完成
     * </pre>
     */
    public void outboundSigned(com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundSignedRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_OUTBOUND_SIGNED, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_CREATE_SCHEDULE_ORDER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.CreateScheduleOrderRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_CREATE_SCHEDULE_ORDER)))
          .addMethod(
            METHOD_CANCEL_SALES_OUTBOUND_ORDER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_CANCEL_SALES_OUTBOUND_ORDER)))
          .addMethod(
            METHOD_SELECT_SALE_SCHEDULE_PRODUCT_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SelectSaleScheduleProductListRequest,
                com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SelectSaleScheduleProductListResponse>(
                  this, METHODID_SELECT_SALE_SCHEDULE_PRODUCT_LIST)))
          .addMethod(
            METHOD_SYNC_SALES_OUTBOUND_ORDER_TO_EAS,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SyncSalesOutboundOrderToEasRequest,
                com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SyncSalesOutboundOrderToEasResponse>(
                  this, METHODID_SYNC_SALES_OUTBOUND_ORDER_TO_EAS)))
          .addMethod(
            METHOD_SUBMIT_OUTBOUND_ORDER_TO_TMS,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_SUBMIT_OUTBOUND_ORDER_TO_TMS)))
          .addMethod(
            METHOD_ITEMS_OUTBOUND_FINISHED,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_ITEMS_OUTBOUND_FINISHED)))
          .addMethod(
            METHOD_OUTBOUND_SIGNED,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundSignedRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_OUTBOUND_SIGNED)))
          .build();
    }
  }

  /**
   */
  public static final class SalesScheduleDeliveryServiceStub extends io.grpc.stub.AbstractStub<SalesScheduleDeliveryServiceStub> {
    private SalesScheduleDeliveryServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SalesScheduleDeliveryServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SalesScheduleDeliveryServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SalesScheduleDeliveryServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *新建预约发货单
     * </pre>
     */
    public void createScheduleOrder(com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.CreateScheduleOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CREATE_SCHEDULE_ORDER, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 取消销售出库单
     * </pre>
     */
    public void cancelSalesOutboundOrder(com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CANCEL_SALES_OUTBOUND_ORDER, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *查询销售单可发货商品
     * </pre>
     */
    public void selectSaleScheduleProductList(com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SelectSaleScheduleProductListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SelectSaleScheduleProductListResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_SALE_SCHEDULE_PRODUCT_LIST, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void syncSalesOutboundOrderToEas(com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SyncSalesOutboundOrderToEasRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SyncSalesOutboundOrderToEasResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SYNC_SALES_OUTBOUND_ORDER_TO_EAS, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *通知tms派车
     * </pre>
     */
    public void submitOutboundOrderToTms(com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SUBMIT_OUTBOUND_ORDER_TO_TMS, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *出库完成
     * </pre>
     */
    public void itemsOutboundFinished(com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_ITEMS_OUTBOUND_FINISHED, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *签收完成
     * </pre>
     */
    public void outboundSigned(com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundSignedRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_OUTBOUND_SIGNED, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SalesScheduleDeliveryServiceBlockingStub extends io.grpc.stub.AbstractStub<SalesScheduleDeliveryServiceBlockingStub> {
    private SalesScheduleDeliveryServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SalesScheduleDeliveryServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SalesScheduleDeliveryServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SalesScheduleDeliveryServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *新建预约发货单
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult createScheduleOrder(com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.CreateScheduleOrderRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CREATE_SCHEDULE_ORDER, getCallOptions(), request);
    }

    /**
     * <pre>
     * 取消销售出库单
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult cancelSalesOutboundOrder(com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CANCEL_SALES_OUTBOUND_ORDER, getCallOptions(), request);
    }

    /**
     * <pre>
     *查询销售单可发货商品
     * </pre>
     */
    public com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SelectSaleScheduleProductListResponse selectSaleScheduleProductList(com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SelectSaleScheduleProductListRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_SALE_SCHEDULE_PRODUCT_LIST, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SyncSalesOutboundOrderToEasResponse syncSalesOutboundOrderToEas(com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SyncSalesOutboundOrderToEasRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SYNC_SALES_OUTBOUND_ORDER_TO_EAS, getCallOptions(), request);
    }

    /**
     * <pre>
     *通知tms派车
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult submitOutboundOrderToTms(com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SUBMIT_OUTBOUND_ORDER_TO_TMS, getCallOptions(), request);
    }

    /**
     * <pre>
     *出库完成
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult itemsOutboundFinished(com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_ITEMS_OUTBOUND_FINISHED, getCallOptions(), request);
    }

    /**
     * <pre>
     *签收完成
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult outboundSigned(com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundSignedRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_OUTBOUND_SIGNED, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SalesScheduleDeliveryServiceFutureStub extends io.grpc.stub.AbstractStub<SalesScheduleDeliveryServiceFutureStub> {
    private SalesScheduleDeliveryServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SalesScheduleDeliveryServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SalesScheduleDeliveryServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SalesScheduleDeliveryServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *新建预约发货单
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> createScheduleOrder(
        com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.CreateScheduleOrderRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CREATE_SCHEDULE_ORDER, getCallOptions()), request);
    }

    /**
     * <pre>
     * 取消销售出库单
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> cancelSalesOutboundOrder(
        com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CANCEL_SALES_OUTBOUND_ORDER, getCallOptions()), request);
    }

    /**
     * <pre>
     *查询销售单可发货商品
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SelectSaleScheduleProductListResponse> selectSaleScheduleProductList(
        com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SelectSaleScheduleProductListRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_SALE_SCHEDULE_PRODUCT_LIST, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SyncSalesOutboundOrderToEasResponse> syncSalesOutboundOrderToEas(
        com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SyncSalesOutboundOrderToEasRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SYNC_SALES_OUTBOUND_ORDER_TO_EAS, getCallOptions()), request);
    }

    /**
     * <pre>
     *通知tms派车
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> submitOutboundOrderToTms(
        com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SUBMIT_OUTBOUND_ORDER_TO_TMS, getCallOptions()), request);
    }

    /**
     * <pre>
     *出库完成
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> itemsOutboundFinished(
        com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_ITEMS_OUTBOUND_FINISHED, getCallOptions()), request);
    }

    /**
     * <pre>
     *签收完成
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> outboundSigned(
        com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundSignedRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_OUTBOUND_SIGNED, getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_SCHEDULE_ORDER = 0;
  private static final int METHODID_CANCEL_SALES_OUTBOUND_ORDER = 1;
  private static final int METHODID_SELECT_SALE_SCHEDULE_PRODUCT_LIST = 2;
  private static final int METHODID_SYNC_SALES_OUTBOUND_ORDER_TO_EAS = 3;
  private static final int METHODID_SUBMIT_OUTBOUND_ORDER_TO_TMS = 4;
  private static final int METHODID_ITEMS_OUTBOUND_FINISHED = 5;
  private static final int METHODID_OUTBOUND_SIGNED = 6;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SalesScheduleDeliveryServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SalesScheduleDeliveryServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_SCHEDULE_ORDER:
          serviceImpl.createScheduleOrder((com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.CreateScheduleOrderRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_CANCEL_SALES_OUTBOUND_ORDER:
          serviceImpl.cancelSalesOutboundOrder((com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_SELECT_SALE_SCHEDULE_PRODUCT_LIST:
          serviceImpl.selectSaleScheduleProductList((com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SelectSaleScheduleProductListRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SelectSaleScheduleProductListResponse>) responseObserver);
          break;
        case METHODID_SYNC_SALES_OUTBOUND_ORDER_TO_EAS:
          serviceImpl.syncSalesOutboundOrderToEas((com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SyncSalesOutboundOrderToEasRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.SyncSalesOutboundOrderToEasResponse>) responseObserver);
          break;
        case METHODID_SUBMIT_OUTBOUND_ORDER_TO_TMS:
          serviceImpl.submitOutboundOrderToTms((com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_ITEMS_OUTBOUND_FINISHED:
          serviceImpl.itemsOutboundFinished((com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundNoRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_OUTBOUND_SIGNED:
          serviceImpl.outboundSigned((com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.OutboundSignedRequest) request,
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

  private static final class SalesScheduleDeliveryServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.sales.microservice.DeliverScheduleStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SalesScheduleDeliveryServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SalesScheduleDeliveryServiceDescriptorSupplier())
              .addMethod(METHOD_CREATE_SCHEDULE_ORDER)
              .addMethod(METHOD_CANCEL_SALES_OUTBOUND_ORDER)
              .addMethod(METHOD_SELECT_SALE_SCHEDULE_PRODUCT_LIST)
              .addMethod(METHOD_SYNC_SALES_OUTBOUND_ORDER_TO_EAS)
              .addMethod(METHOD_SUBMIT_OUTBOUND_ORDER_TO_TMS)
              .addMethod(METHOD_ITEMS_OUTBOUND_FINISHED)
              .addMethod(METHOD_OUTBOUND_SIGNED)
              .build();
        }
      }
    }
    return result;
  }
}
