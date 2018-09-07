package com.yhglobal.gongxiao.sales.microservice;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.4.0)",
    comments = "Source: sales.order.delivery.proto")
public final class SalesOrderDeliveryServiceGrpc {

  private SalesOrderDeliveryServiceGrpc() {}

  public static final String SERVICE_NAME = "SalesOrderDeliveryService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.CreateDeliveryOrderRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_CREATE_DELIVERY_ORDER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.CreateDeliveryOrderRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderDeliveryService", "createDeliveryOrder"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.CreateDeliveryOrderRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_CANCEL_SALES_OUTBOUND_ORDER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderDeliveryService", "cancelSalesOutboundOrder"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_SUBMIT_OUTBOUND_ORDER_TO_TMS =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderDeliveryService", "submitOutboundOrderToTms"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_ITEMS_OUTBOUND_FINISHED =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderDeliveryService", "itemsOutboundFinished"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SaleScheduleProductListRequest,
      com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SaleScheduleProductListResponse> METHOD_GET_SALE_SCHEDULE_PRODUCT_LIST =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SaleScheduleProductListRequest, com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SaleScheduleProductListResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderDeliveryService", "getSaleScheduleProductList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SaleScheduleProductListRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SaleScheduleProductListResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SyncSalesOutboundOrderToEasRequest,
      com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SyncSalesOutboundOrderToEasResponse> METHOD_SYNC_SALES_OUTBOUND_ORDER_TO_EAS =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SyncSalesOutboundOrderToEasRequest, com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SyncSalesOutboundOrderToEasResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderDeliveryService", "syncSalesOutboundOrderToEas"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SyncSalesOutboundOrderToEasRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SyncSalesOutboundOrderToEasResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundSignedRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_OUTBOUND_SIGNED =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundSignedRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderDeliveryService", "outboundSigned"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundSignedRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SalesOrderDeliveryServiceStub newStub(io.grpc.Channel channel) {
    return new SalesOrderDeliveryServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SalesOrderDeliveryServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SalesOrderDeliveryServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SalesOrderDeliveryServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SalesOrderDeliveryServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SalesOrderDeliveryServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void createDeliveryOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.CreateDeliveryOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CREATE_DELIVERY_ORDER, responseObserver);
    }

    /**
     */
    public void cancelSalesOutboundOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CANCEL_SALES_OUTBOUND_ORDER, responseObserver);
    }

    /**
     */
    public void submitOutboundOrderToTms(com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SUBMIT_OUTBOUND_ORDER_TO_TMS, responseObserver);
    }

    /**
     */
    public void itemsOutboundFinished(com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_ITEMS_OUTBOUND_FINISHED, responseObserver);
    }

    /**
     */
    public void getSaleScheduleProductList(com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SaleScheduleProductListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SaleScheduleProductListResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_SALE_SCHEDULE_PRODUCT_LIST, responseObserver);
    }

    /**
     */
    public void syncSalesOutboundOrderToEas(com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SyncSalesOutboundOrderToEasRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SyncSalesOutboundOrderToEasResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SYNC_SALES_OUTBOUND_ORDER_TO_EAS, responseObserver);
    }

    /**
     */
    public void outboundSigned(com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundSignedRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_OUTBOUND_SIGNED, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_CREATE_DELIVERY_ORDER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.CreateDeliveryOrderRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_CREATE_DELIVERY_ORDER)))
          .addMethod(
            METHOD_CANCEL_SALES_OUTBOUND_ORDER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_CANCEL_SALES_OUTBOUND_ORDER)))
          .addMethod(
            METHOD_SUBMIT_OUTBOUND_ORDER_TO_TMS,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_SUBMIT_OUTBOUND_ORDER_TO_TMS)))
          .addMethod(
            METHOD_ITEMS_OUTBOUND_FINISHED,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_ITEMS_OUTBOUND_FINISHED)))
          .addMethod(
            METHOD_GET_SALE_SCHEDULE_PRODUCT_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SaleScheduleProductListRequest,
                com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SaleScheduleProductListResponse>(
                  this, METHODID_GET_SALE_SCHEDULE_PRODUCT_LIST)))
          .addMethod(
            METHOD_SYNC_SALES_OUTBOUND_ORDER_TO_EAS,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SyncSalesOutboundOrderToEasRequest,
                com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SyncSalesOutboundOrderToEasResponse>(
                  this, METHODID_SYNC_SALES_OUTBOUND_ORDER_TO_EAS)))
          .addMethod(
            METHOD_OUTBOUND_SIGNED,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundSignedRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_OUTBOUND_SIGNED)))
          .build();
    }
  }

  /**
   */
  public static final class SalesOrderDeliveryServiceStub extends io.grpc.stub.AbstractStub<SalesOrderDeliveryServiceStub> {
    private SalesOrderDeliveryServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SalesOrderDeliveryServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SalesOrderDeliveryServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SalesOrderDeliveryServiceStub(channel, callOptions);
    }

    /**
     */
    public void createDeliveryOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.CreateDeliveryOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CREATE_DELIVERY_ORDER, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void cancelSalesOutboundOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CANCEL_SALES_OUTBOUND_ORDER, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void submitOutboundOrderToTms(com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SUBMIT_OUTBOUND_ORDER_TO_TMS, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void itemsOutboundFinished(com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_ITEMS_OUTBOUND_FINISHED, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getSaleScheduleProductList(com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SaleScheduleProductListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SaleScheduleProductListResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_SALE_SCHEDULE_PRODUCT_LIST, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void syncSalesOutboundOrderToEas(com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SyncSalesOutboundOrderToEasRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SyncSalesOutboundOrderToEasResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SYNC_SALES_OUTBOUND_ORDER_TO_EAS, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void outboundSigned(com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundSignedRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_OUTBOUND_SIGNED, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SalesOrderDeliveryServiceBlockingStub extends io.grpc.stub.AbstractStub<SalesOrderDeliveryServiceBlockingStub> {
    private SalesOrderDeliveryServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SalesOrderDeliveryServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SalesOrderDeliveryServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SalesOrderDeliveryServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult createDeliveryOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.CreateDeliveryOrderRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CREATE_DELIVERY_ORDER, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult cancelSalesOutboundOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CANCEL_SALES_OUTBOUND_ORDER, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult submitOutboundOrderToTms(com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SUBMIT_OUTBOUND_ORDER_TO_TMS, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult itemsOutboundFinished(com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_ITEMS_OUTBOUND_FINISHED, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SaleScheduleProductListResponse getSaleScheduleProductList(com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SaleScheduleProductListRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_SALE_SCHEDULE_PRODUCT_LIST, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SyncSalesOutboundOrderToEasResponse syncSalesOutboundOrderToEas(com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SyncSalesOutboundOrderToEasRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SYNC_SALES_OUTBOUND_ORDER_TO_EAS, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult outboundSigned(com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundSignedRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_OUTBOUND_SIGNED, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SalesOrderDeliveryServiceFutureStub extends io.grpc.stub.AbstractStub<SalesOrderDeliveryServiceFutureStub> {
    private SalesOrderDeliveryServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SalesOrderDeliveryServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SalesOrderDeliveryServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SalesOrderDeliveryServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> createDeliveryOrder(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.CreateDeliveryOrderRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CREATE_DELIVERY_ORDER, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> cancelSalesOutboundOrder(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CANCEL_SALES_OUTBOUND_ORDER, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> submitOutboundOrderToTms(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SUBMIT_OUTBOUND_ORDER_TO_TMS, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> itemsOutboundFinished(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_ITEMS_OUTBOUND_FINISHED, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SaleScheduleProductListResponse> getSaleScheduleProductList(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SaleScheduleProductListRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_SALE_SCHEDULE_PRODUCT_LIST, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SyncSalesOutboundOrderToEasResponse> syncSalesOutboundOrderToEas(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SyncSalesOutboundOrderToEasRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SYNC_SALES_OUTBOUND_ORDER_TO_EAS, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> outboundSigned(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundSignedRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_OUTBOUND_SIGNED, getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_DELIVERY_ORDER = 0;
  private static final int METHODID_CANCEL_SALES_OUTBOUND_ORDER = 1;
  private static final int METHODID_SUBMIT_OUTBOUND_ORDER_TO_TMS = 2;
  private static final int METHODID_ITEMS_OUTBOUND_FINISHED = 3;
  private static final int METHODID_GET_SALE_SCHEDULE_PRODUCT_LIST = 4;
  private static final int METHODID_SYNC_SALES_OUTBOUND_ORDER_TO_EAS = 5;
  private static final int METHODID_OUTBOUND_SIGNED = 6;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SalesOrderDeliveryServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SalesOrderDeliveryServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_DELIVERY_ORDER:
          serviceImpl.createDeliveryOrder((com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.CreateDeliveryOrderRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_CANCEL_SALES_OUTBOUND_ORDER:
          serviceImpl.cancelSalesOutboundOrder((com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_SUBMIT_OUTBOUND_ORDER_TO_TMS:
          serviceImpl.submitOutboundOrderToTms((com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_ITEMS_OUTBOUND_FINISHED:
          serviceImpl.itemsOutboundFinished((com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundNoRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_GET_SALE_SCHEDULE_PRODUCT_LIST:
          serviceImpl.getSaleScheduleProductList((com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SaleScheduleProductListRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SaleScheduleProductListResponse>) responseObserver);
          break;
        case METHODID_SYNC_SALES_OUTBOUND_ORDER_TO_EAS:
          serviceImpl.syncSalesOutboundOrderToEas((com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SyncSalesOutboundOrderToEasRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.SyncSalesOutboundOrderToEasResponse>) responseObserver);
          break;
        case METHODID_OUTBOUND_SIGNED:
          serviceImpl.outboundSigned((com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.OutboundSignedRequest) request,
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

  private static final class SalesOrderDeliveryServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.sales.microservice.SalesOrderDeliveryServiceStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SalesOrderDeliveryServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SalesOrderDeliveryServiceDescriptorSupplier())
              .addMethod(METHOD_CREATE_DELIVERY_ORDER)
              .addMethod(METHOD_CANCEL_SALES_OUTBOUND_ORDER)
              .addMethod(METHOD_SUBMIT_OUTBOUND_ORDER_TO_TMS)
              .addMethod(METHOD_ITEMS_OUTBOUND_FINISHED)
              .addMethod(METHOD_GET_SALE_SCHEDULE_PRODUCT_LIST)
              .addMethod(METHOD_SYNC_SALES_OUTBOUND_ORDER_TO_EAS)
              .addMethod(METHOD_OUTBOUND_SIGNED)
              .build();
        }
      }
    }
    return result;
  }
}
