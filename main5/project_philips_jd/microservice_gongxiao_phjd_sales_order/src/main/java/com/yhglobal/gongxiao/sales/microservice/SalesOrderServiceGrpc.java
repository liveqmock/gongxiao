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
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ListOrderPageRequest,
      com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ListOrderPageResponse> METHOD_LIST_ORDER_PAGE =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ListOrderPageRequest, com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ListOrderPageResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderService", "listOrderPage"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ListOrderPageRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ListOrderPageResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ReviewSalesOrderRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_REVIEW_SALES_ORDER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ReviewSalesOrderRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderService", "reviewSalesOrder"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ReviewSalesOrderRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_REJECTED_SALES_ORDER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderService", "rejectedSalesOrder"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CreateSalesOrderRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_INSERT_SALES_ORDER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CreateSalesOrderRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderService", "insertSalesOrder"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CreateSalesOrderRequest.getDefaultInstance()))
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
     */
    public void getOrderDetailBySalesOrderNoAndProjectCode(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO_AND_PROJECT_CODE, responseObserver);
    }

    /**
     */
    public void getOrderDetailBySalesOrderNo(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderByOrderNoResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO, responseObserver);
    }

    /**
     */
    public void listOrderPage(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ListOrderPageRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ListOrderPageResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_LIST_ORDER_PAGE, responseObserver);
    }

    /**
     */
    public void reviewSalesOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ReviewSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_REVIEW_SALES_ORDER, responseObserver);
    }

    /**
     */
    public void rejectedSalesOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_REJECTED_SALES_ORDER, responseObserver);
    }

    /**
     */
    public void insertSalesOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CreateSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_INSERT_SALES_ORDER, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO_AND_PROJECT_CODE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemRequest,
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemResponse>(
                  this, METHODID_GET_ORDER_DETAIL_BY_SALES_ORDER_NO_AND_PROJECT_CODE)))
          .addMethod(
            METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest,
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderByOrderNoResponse>(
                  this, METHODID_GET_ORDER_DETAIL_BY_SALES_ORDER_NO)))
          .addMethod(
            METHOD_LIST_ORDER_PAGE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ListOrderPageRequest,
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ListOrderPageResponse>(
                  this, METHODID_LIST_ORDER_PAGE)))
          .addMethod(
            METHOD_REVIEW_SALES_ORDER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ReviewSalesOrderRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_REVIEW_SALES_ORDER)))
          .addMethod(
            METHOD_REJECTED_SALES_ORDER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_REJECTED_SALES_ORDER)))
          .addMethod(
            METHOD_INSERT_SALES_ORDER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CreateSalesOrderRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_INSERT_SALES_ORDER)))
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
     */
    public void getOrderDetailBySalesOrderNoAndProjectCode(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO_AND_PROJECT_CODE, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getOrderDetailBySalesOrderNo(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderByOrderNoResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listOrderPage(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ListOrderPageRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ListOrderPageResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_LIST_ORDER_PAGE, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void reviewSalesOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ReviewSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_REVIEW_SALES_ORDER, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rejectedSalesOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_REJECTED_SALES_ORDER, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void insertSalesOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CreateSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_INSERT_SALES_ORDER, getCallOptions()), request, responseObserver);
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
     */
    public com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemResponse getOrderDetailBySalesOrderNoAndProjectCode(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO_AND_PROJECT_CODE, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderByOrderNoResponse getOrderDetailBySalesOrderNo(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ListOrderPageResponse listOrderPage(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ListOrderPageRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_LIST_ORDER_PAGE, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult reviewSalesOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ReviewSalesOrderRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_REVIEW_SALES_ORDER, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult rejectedSalesOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_REJECTED_SALES_ORDER, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult insertSalesOrder(com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CreateSalesOrderRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_INSERT_SALES_ORDER, getCallOptions(), request);
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
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemResponse> getOrderDetailBySalesOrderNoAndProjectCode(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO_AND_PROJECT_CODE, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderByOrderNoResponse> getOrderDetailBySalesOrderNo(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ListOrderPageResponse> listOrderPage(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ListOrderPageRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_LIST_ORDER_PAGE, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> reviewSalesOrder(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ReviewSalesOrderRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_REVIEW_SALES_ORDER, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> rejectedSalesOrder(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_REJECTED_SALES_ORDER, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> insertSalesOrder(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CreateSalesOrderRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_INSERT_SALES_ORDER, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_ORDER_DETAIL_BY_SALES_ORDER_NO_AND_PROJECT_CODE = 0;
  private static final int METHODID_GET_ORDER_DETAIL_BY_SALES_ORDER_NO = 1;
  private static final int METHODID_LIST_ORDER_PAGE = 2;
  private static final int METHODID_REVIEW_SALES_ORDER = 3;
  private static final int METHODID_REJECTED_SALES_ORDER = 4;
  private static final int METHODID_INSERT_SALES_ORDER = 5;

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
        case METHODID_GET_ORDER_DETAIL_BY_SALES_ORDER_NO_AND_PROJECT_CODE:
          serviceImpl.getOrderDetailBySalesOrderNoAndProjectCode((com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderItemResponse>) responseObserver);
          break;
        case METHODID_GET_ORDER_DETAIL_BY_SALES_ORDER_NO:
          serviceImpl.getOrderDetailBySalesOrderNo((com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.GetOrderByOrderNoResponse>) responseObserver);
          break;
        case METHODID_LIST_ORDER_PAGE:
          serviceImpl.listOrderPage((com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ListOrderPageRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ListOrderPageResponse>) responseObserver);
          break;
        case METHODID_REVIEW_SALES_ORDER:
          serviceImpl.reviewSalesOrder((com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.ReviewSalesOrderRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_REJECTED_SALES_ORDER:
          serviceImpl.rejectedSalesOrder((com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CommonSalesOrderRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_INSERT_SALES_ORDER:
          serviceImpl.insertSalesOrder((com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure.CreateSalesOrderRequest) request,
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
              .addMethod(METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO_AND_PROJECT_CODE)
              .addMethod(METHOD_GET_ORDER_DETAIL_BY_SALES_ORDER_NO)
              .addMethod(METHOD_LIST_ORDER_PAGE)
              .addMethod(METHOD_REVIEW_SALES_ORDER)
              .addMethod(METHOD_REJECTED_SALES_ORDER)
              .addMethod(METHOD_INSERT_SALES_ORDER)
              .build();
        }
      }
    }
    return result;
  }
}
