package com.yhglobal.gongxiao.payment.project.microservice;

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
    comments = "Source: payment.supplier.proto")
public final class SupplierAccountServiceGrpc {

  private SupplierAccountServiceGrpc() {}

  public static final String SERVICE_NAME = "SupplierAccountService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSupplierCouponBufferToDistributorRequest,
      com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.AccountAmountResponse> METHOD_GET_SUPPLIER_COUPON_BUFFER_TO_DISTRIBUTOR =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSupplierCouponBufferToDistributorRequest, com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.AccountAmountResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "getSupplierCouponBufferToDistributor"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSupplierCouponBufferToDistributorRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.AccountAmountResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSupplierAccountAmountRequest,
      com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.AccountAmountResponse> METHOD_GET_SUPPLIER_ACCOUNT_AMOUNT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSupplierAccountAmountRequest, com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.AccountAmountResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "getSupplierAccountAmount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSupplierAccountAmountRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.AccountAmountResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountRequest,
      com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountResponse> METHOD_POST_SUPPLIER_COUPON_ACCOUNT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountRequest, com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "postSupplierCouponAccount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountRequest,
      com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountResponse> METHOD_POST_SUPPLIER_PREPAID_ACCOUNT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountRequest, com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "postSupplierPrepaidAccount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PayForPurchaseRequest,
      com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PayForPurchaseResponse> METHOD_PAY_FOR_PURCHASE =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PayForPurchaseRequest, com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PayForPurchaseResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "payForPurchase"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PayForPurchaseRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PayForPurchaseResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferCouponFlowListRequest,
      com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferCouponFlowListResponse> METHOD_SELECT_BUFFER_COUPON_FLOW_LIST =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferCouponFlowListRequest, com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferCouponFlowListResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "selectBufferCouponFlowList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferCouponFlowListRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferCouponFlowListResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferPrepaidFlowListRequest,
      com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> METHOD_SELECT_BUFFER_PREPAID_FLOW_LIST =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferPrepaidFlowListRequest, com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "selectBufferPrepaidFlowList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferPrepaidFlowListRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetCouponBufferToDistributorSubtotalRequest,
      com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> METHOD_GET_COUPON_BUFFER_TO_DISTRIBUTOR_SUBTOTAL =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetCouponBufferToDistributorSubtotalRequest, com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "getCouponBufferToDistributorSubtotal"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetCouponBufferToDistributorSubtotalRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetPrepaidBufferToDistributorSubtotalRequest,
      com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> METHOD_GET_PREPAID_BUFFER_TO_DISTRIBUTOR_SUBTOTAL =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetPrepaidBufferToDistributorSubtotalRequest, com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "getPrepaidBufferToDistributorSubtotal"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetPrepaidBufferToDistributorSubtotalRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSellHighAccountRequest,
      com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.getSellHighAccountResponse> METHOD_GET_SELL_HIGH_ACCOUNT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSellHighAccountRequest, com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.getSellHighAccountResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "getSellHighAccount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSellHighAccountRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.getSellHighAccountResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SalesUpdateSellHighAccountRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_SALES_UPDATE_SELL_HIGH_ACCOUNT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SalesUpdateSellHighAccountRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "salesUpdateSellHighAccount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SalesUpdateSellHighAccountRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectSupplierSellHighRecordListRequest,
      com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> METHOD_SELECT_SUPPLIER_SELL_HIGH_RECORD_LIST =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectSupplierSellHighRecordListRequest, com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "selectSupplierSellHighRecordList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectSupplierSellHighRecordListRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSellHighSubtotalRequest,
      com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> METHOD_GET_SELL_HIGH_SUBTOTAL =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSellHighSubtotalRequest, com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "getSellHighSubtotal"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSellHighSubtotalRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SupplierAccountServiceStub newStub(io.grpc.Channel channel) {
    return new SupplierAccountServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SupplierAccountServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SupplierAccountServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SupplierAccountServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SupplierAccountServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SupplierAccountServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *查询供应商过账ad账户余额
     * </pre>
     */
    public void getSupplierCouponBufferToDistributor(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSupplierCouponBufferToDistributorRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.AccountAmountResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_SUPPLIER_COUPON_BUFFER_TO_DISTRIBUTOR, responseObserver);
    }

    /**
     * <pre>
     *查询供应商上账账户余额
     * </pre>
     */
    public void getSupplierAccountAmount(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSupplierAccountAmountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.AccountAmountResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_SUPPLIER_ACCOUNT_AMOUNT, responseObserver);
    }

    /**
     * <pre>
     *返利上账
     * </pre>
     */
    public void postSupplierCouponAccount(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_POST_SUPPLIER_COUPON_ACCOUNT, responseObserver);
    }

    /**
     * <pre>
     *代垫上账
     * </pre>
     */
    public void postSupplierPrepaidAccount(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_POST_SUPPLIER_PREPAID_ACCOUNT, responseObserver);
    }

    /**
     * <pre>
     *上账账户暂扣返利、代垫
     * </pre>
     */
    public void payForPurchase(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PayForPurchaseRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PayForPurchaseResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_PAY_FOR_PURCHASE, responseObserver);
    }

    /**
     * <pre>
     *查询 ad返利过账账户 流水
     * </pre>
     */
    public void selectBufferCouponFlowList(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferCouponFlowListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferCouponFlowListResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_BUFFER_COUPON_FLOW_LIST, responseObserver);
    }

    /**
     * <pre>
     *查询 ad代垫过账账户 流水
     * </pre>
     */
    public void selectBufferPrepaidFlowList(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferPrepaidFlowListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_BUFFER_PREPAID_FLOW_LIST, responseObserver);
    }

    /**
     */
    public void getCouponBufferToDistributorSubtotal(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetCouponBufferToDistributorSubtotalRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_COUPON_BUFFER_TO_DISTRIBUTOR_SUBTOTAL, responseObserver);
    }

    /**
     */
    public void getPrepaidBufferToDistributorSubtotal(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetPrepaidBufferToDistributorSubtotalRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_PREPAID_BUFFER_TO_DISTRIBUTOR_SUBTOTAL, responseObserver);
    }

    /**
     */
    public void getSellHighAccount(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSellHighAccountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.getSellHighAccountResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_SELL_HIGH_ACCOUNT, responseObserver);
    }

    /**
     */
    public void salesUpdateSellHighAccount(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SalesUpdateSellHighAccountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SALES_UPDATE_SELL_HIGH_ACCOUNT, responseObserver);
    }

    /**
     */
    public void selectSupplierSellHighRecordList(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectSupplierSellHighRecordListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_SUPPLIER_SELL_HIGH_RECORD_LIST, responseObserver);
    }

    /**
     */
    public void getSellHighSubtotal(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSellHighSubtotalRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_SELL_HIGH_SUBTOTAL, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_SUPPLIER_COUPON_BUFFER_TO_DISTRIBUTOR,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSupplierCouponBufferToDistributorRequest,
                com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.AccountAmountResponse>(
                  this, METHODID_GET_SUPPLIER_COUPON_BUFFER_TO_DISTRIBUTOR)))
          .addMethod(
            METHOD_GET_SUPPLIER_ACCOUNT_AMOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSupplierAccountAmountRequest,
                com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.AccountAmountResponse>(
                  this, METHODID_GET_SUPPLIER_ACCOUNT_AMOUNT)))
          .addMethod(
            METHOD_POST_SUPPLIER_COUPON_ACCOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountRequest,
                com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountResponse>(
                  this, METHODID_POST_SUPPLIER_COUPON_ACCOUNT)))
          .addMethod(
            METHOD_POST_SUPPLIER_PREPAID_ACCOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountRequest,
                com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountResponse>(
                  this, METHODID_POST_SUPPLIER_PREPAID_ACCOUNT)))
          .addMethod(
            METHOD_PAY_FOR_PURCHASE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PayForPurchaseRequest,
                com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PayForPurchaseResponse>(
                  this, METHODID_PAY_FOR_PURCHASE)))
          .addMethod(
            METHOD_SELECT_BUFFER_COUPON_FLOW_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferCouponFlowListRequest,
                com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferCouponFlowListResponse>(
                  this, METHODID_SELECT_BUFFER_COUPON_FLOW_LIST)))
          .addMethod(
            METHOD_SELECT_BUFFER_PREPAID_FLOW_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferPrepaidFlowListRequest,
                com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>(
                  this, METHODID_SELECT_BUFFER_PREPAID_FLOW_LIST)))
          .addMethod(
            METHOD_GET_COUPON_BUFFER_TO_DISTRIBUTOR_SUBTOTAL,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetCouponBufferToDistributorSubtotalRequest,
                com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>(
                  this, METHODID_GET_COUPON_BUFFER_TO_DISTRIBUTOR_SUBTOTAL)))
          .addMethod(
            METHOD_GET_PREPAID_BUFFER_TO_DISTRIBUTOR_SUBTOTAL,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetPrepaidBufferToDistributorSubtotalRequest,
                com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>(
                  this, METHODID_GET_PREPAID_BUFFER_TO_DISTRIBUTOR_SUBTOTAL)))
          .addMethod(
            METHOD_GET_SELL_HIGH_ACCOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSellHighAccountRequest,
                com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.getSellHighAccountResponse>(
                  this, METHODID_GET_SELL_HIGH_ACCOUNT)))
          .addMethod(
            METHOD_SALES_UPDATE_SELL_HIGH_ACCOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SalesUpdateSellHighAccountRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_SALES_UPDATE_SELL_HIGH_ACCOUNT)))
          .addMethod(
            METHOD_SELECT_SUPPLIER_SELL_HIGH_RECORD_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectSupplierSellHighRecordListRequest,
                com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>(
                  this, METHODID_SELECT_SUPPLIER_SELL_HIGH_RECORD_LIST)))
          .addMethod(
            METHOD_GET_SELL_HIGH_SUBTOTAL,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSellHighSubtotalRequest,
                com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>(
                  this, METHODID_GET_SELL_HIGH_SUBTOTAL)))
          .build();
    }
  }

  /**
   */
  public static final class SupplierAccountServiceStub extends io.grpc.stub.AbstractStub<SupplierAccountServiceStub> {
    private SupplierAccountServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SupplierAccountServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SupplierAccountServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SupplierAccountServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *查询供应商过账ad账户余额
     * </pre>
     */
    public void getSupplierCouponBufferToDistributor(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSupplierCouponBufferToDistributorRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.AccountAmountResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_SUPPLIER_COUPON_BUFFER_TO_DISTRIBUTOR, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *查询供应商上账账户余额
     * </pre>
     */
    public void getSupplierAccountAmount(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSupplierAccountAmountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.AccountAmountResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_SUPPLIER_ACCOUNT_AMOUNT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *返利上账
     * </pre>
     */
    public void postSupplierCouponAccount(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_POST_SUPPLIER_COUPON_ACCOUNT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *代垫上账
     * </pre>
     */
    public void postSupplierPrepaidAccount(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_POST_SUPPLIER_PREPAID_ACCOUNT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *上账账户暂扣返利、代垫
     * </pre>
     */
    public void payForPurchase(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PayForPurchaseRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PayForPurchaseResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_PAY_FOR_PURCHASE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *查询 ad返利过账账户 流水
     * </pre>
     */
    public void selectBufferCouponFlowList(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferCouponFlowListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferCouponFlowListResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_BUFFER_COUPON_FLOW_LIST, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *查询 ad代垫过账账户 流水
     * </pre>
     */
    public void selectBufferPrepaidFlowList(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferPrepaidFlowListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_BUFFER_PREPAID_FLOW_LIST, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getCouponBufferToDistributorSubtotal(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetCouponBufferToDistributorSubtotalRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_COUPON_BUFFER_TO_DISTRIBUTOR_SUBTOTAL, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getPrepaidBufferToDistributorSubtotal(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetPrepaidBufferToDistributorSubtotalRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_PREPAID_BUFFER_TO_DISTRIBUTOR_SUBTOTAL, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getSellHighAccount(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSellHighAccountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.getSellHighAccountResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_SELL_HIGH_ACCOUNT, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void salesUpdateSellHighAccount(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SalesUpdateSellHighAccountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SALES_UPDATE_SELL_HIGH_ACCOUNT, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void selectSupplierSellHighRecordList(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectSupplierSellHighRecordListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_SUPPLIER_SELL_HIGH_RECORD_LIST, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getSellHighSubtotal(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSellHighSubtotalRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_SELL_HIGH_SUBTOTAL, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SupplierAccountServiceBlockingStub extends io.grpc.stub.AbstractStub<SupplierAccountServiceBlockingStub> {
    private SupplierAccountServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SupplierAccountServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SupplierAccountServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SupplierAccountServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *查询供应商过账ad账户余额
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.AccountAmountResponse getSupplierCouponBufferToDistributor(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSupplierCouponBufferToDistributorRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_SUPPLIER_COUPON_BUFFER_TO_DISTRIBUTOR, getCallOptions(), request);
    }

    /**
     * <pre>
     *查询供应商上账账户余额
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.AccountAmountResponse getSupplierAccountAmount(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSupplierAccountAmountRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_SUPPLIER_ACCOUNT_AMOUNT, getCallOptions(), request);
    }

    /**
     * <pre>
     *返利上账
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountResponse postSupplierCouponAccount(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_POST_SUPPLIER_COUPON_ACCOUNT, getCallOptions(), request);
    }

    /**
     * <pre>
     *代垫上账
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountResponse postSupplierPrepaidAccount(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_POST_SUPPLIER_PREPAID_ACCOUNT, getCallOptions(), request);
    }

    /**
     * <pre>
     *上账账户暂扣返利、代垫
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PayForPurchaseResponse payForPurchase(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PayForPurchaseRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_PAY_FOR_PURCHASE, getCallOptions(), request);
    }

    /**
     * <pre>
     *查询 ad返利过账账户 流水
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferCouponFlowListResponse selectBufferCouponFlowList(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferCouponFlowListRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_BUFFER_COUPON_FLOW_LIST, getCallOptions(), request);
    }

    /**
     * <pre>
     *查询 ad代垫过账账户 流水
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo selectBufferPrepaidFlowList(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferPrepaidFlowListRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_BUFFER_PREPAID_FLOW_LIST, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal getCouponBufferToDistributorSubtotal(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetCouponBufferToDistributorSubtotalRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_COUPON_BUFFER_TO_DISTRIBUTOR_SUBTOTAL, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal getPrepaidBufferToDistributorSubtotal(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetPrepaidBufferToDistributorSubtotalRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_PREPAID_BUFFER_TO_DISTRIBUTOR_SUBTOTAL, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.getSellHighAccountResponse getSellHighAccount(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSellHighAccountRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_SELL_HIGH_ACCOUNT, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult salesUpdateSellHighAccount(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SalesUpdateSellHighAccountRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SALES_UPDATE_SELL_HIGH_ACCOUNT, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo selectSupplierSellHighRecordList(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectSupplierSellHighRecordListRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_SUPPLIER_SELL_HIGH_RECORD_LIST, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal getSellHighSubtotal(com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSellHighSubtotalRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_SELL_HIGH_SUBTOTAL, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SupplierAccountServiceFutureStub extends io.grpc.stub.AbstractStub<SupplierAccountServiceFutureStub> {
    private SupplierAccountServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SupplierAccountServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SupplierAccountServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SupplierAccountServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *查询供应商过账ad账户余额
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.AccountAmountResponse> getSupplierCouponBufferToDistributor(
        com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSupplierCouponBufferToDistributorRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_SUPPLIER_COUPON_BUFFER_TO_DISTRIBUTOR, getCallOptions()), request);
    }

    /**
     * <pre>
     *查询供应商上账账户余额
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.AccountAmountResponse> getSupplierAccountAmount(
        com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSupplierAccountAmountRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_SUPPLIER_ACCOUNT_AMOUNT, getCallOptions()), request);
    }

    /**
     * <pre>
     *返利上账
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountResponse> postSupplierCouponAccount(
        com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_POST_SUPPLIER_COUPON_ACCOUNT, getCallOptions()), request);
    }

    /**
     * <pre>
     *代垫上账
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountResponse> postSupplierPrepaidAccount(
        com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_POST_SUPPLIER_PREPAID_ACCOUNT, getCallOptions()), request);
    }

    /**
     * <pre>
     *上账账户暂扣返利、代垫
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PayForPurchaseResponse> payForPurchase(
        com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PayForPurchaseRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_PAY_FOR_PURCHASE, getCallOptions()), request);
    }

    /**
     * <pre>
     *查询 ad返利过账账户 流水
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferCouponFlowListResponse> selectBufferCouponFlowList(
        com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferCouponFlowListRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_BUFFER_COUPON_FLOW_LIST, getCallOptions()), request);
    }

    /**
     * <pre>
     *查询 ad代垫过账账户 流水
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> selectBufferPrepaidFlowList(
        com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferPrepaidFlowListRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_BUFFER_PREPAID_FLOW_LIST, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> getCouponBufferToDistributorSubtotal(
        com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetCouponBufferToDistributorSubtotalRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_COUPON_BUFFER_TO_DISTRIBUTOR_SUBTOTAL, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> getPrepaidBufferToDistributorSubtotal(
        com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetPrepaidBufferToDistributorSubtotalRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_PREPAID_BUFFER_TO_DISTRIBUTOR_SUBTOTAL, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.getSellHighAccountResponse> getSellHighAccount(
        com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSellHighAccountRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_SELL_HIGH_ACCOUNT, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> salesUpdateSellHighAccount(
        com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SalesUpdateSellHighAccountRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SALES_UPDATE_SELL_HIGH_ACCOUNT, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> selectSupplierSellHighRecordList(
        com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectSupplierSellHighRecordListRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_SUPPLIER_SELL_HIGH_RECORD_LIST, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> getSellHighSubtotal(
        com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSellHighSubtotalRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_SELL_HIGH_SUBTOTAL, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_SUPPLIER_COUPON_BUFFER_TO_DISTRIBUTOR = 0;
  private static final int METHODID_GET_SUPPLIER_ACCOUNT_AMOUNT = 1;
  private static final int METHODID_POST_SUPPLIER_COUPON_ACCOUNT = 2;
  private static final int METHODID_POST_SUPPLIER_PREPAID_ACCOUNT = 3;
  private static final int METHODID_PAY_FOR_PURCHASE = 4;
  private static final int METHODID_SELECT_BUFFER_COUPON_FLOW_LIST = 5;
  private static final int METHODID_SELECT_BUFFER_PREPAID_FLOW_LIST = 6;
  private static final int METHODID_GET_COUPON_BUFFER_TO_DISTRIBUTOR_SUBTOTAL = 7;
  private static final int METHODID_GET_PREPAID_BUFFER_TO_DISTRIBUTOR_SUBTOTAL = 8;
  private static final int METHODID_GET_SELL_HIGH_ACCOUNT = 9;
  private static final int METHODID_SALES_UPDATE_SELL_HIGH_ACCOUNT = 10;
  private static final int METHODID_SELECT_SUPPLIER_SELL_HIGH_RECORD_LIST = 11;
  private static final int METHODID_GET_SELL_HIGH_SUBTOTAL = 12;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SupplierAccountServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SupplierAccountServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_SUPPLIER_COUPON_BUFFER_TO_DISTRIBUTOR:
          serviceImpl.getSupplierCouponBufferToDistributor((com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSupplierCouponBufferToDistributorRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.AccountAmountResponse>) responseObserver);
          break;
        case METHODID_GET_SUPPLIER_ACCOUNT_AMOUNT:
          serviceImpl.getSupplierAccountAmount((com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSupplierAccountAmountRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.AccountAmountResponse>) responseObserver);
          break;
        case METHODID_POST_SUPPLIER_COUPON_ACCOUNT:
          serviceImpl.postSupplierCouponAccount((com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountResponse>) responseObserver);
          break;
        case METHODID_POST_SUPPLIER_PREPAID_ACCOUNT:
          serviceImpl.postSupplierPrepaidAccount((com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountResponse>) responseObserver);
          break;
        case METHODID_PAY_FOR_PURCHASE:
          serviceImpl.payForPurchase((com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PayForPurchaseRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.PayForPurchaseResponse>) responseObserver);
          break;
        case METHODID_SELECT_BUFFER_COUPON_FLOW_LIST:
          serviceImpl.selectBufferCouponFlowList((com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferCouponFlowListRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferCouponFlowListResponse>) responseObserver);
          break;
        case METHODID_SELECT_BUFFER_PREPAID_FLOW_LIST:
          serviceImpl.selectBufferPrepaidFlowList((com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectBufferPrepaidFlowListRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>) responseObserver);
          break;
        case METHODID_GET_COUPON_BUFFER_TO_DISTRIBUTOR_SUBTOTAL:
          serviceImpl.getCouponBufferToDistributorSubtotal((com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetCouponBufferToDistributorSubtotalRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>) responseObserver);
          break;
        case METHODID_GET_PREPAID_BUFFER_TO_DISTRIBUTOR_SUBTOTAL:
          serviceImpl.getPrepaidBufferToDistributorSubtotal((com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetPrepaidBufferToDistributorSubtotalRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>) responseObserver);
          break;
        case METHODID_GET_SELL_HIGH_ACCOUNT:
          serviceImpl.getSellHighAccount((com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSellHighAccountRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.getSellHighAccountResponse>) responseObserver);
          break;
        case METHODID_SALES_UPDATE_SELL_HIGH_ACCOUNT:
          serviceImpl.salesUpdateSellHighAccount((com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SalesUpdateSellHighAccountRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_SELECT_SUPPLIER_SELL_HIGH_RECORD_LIST:
          serviceImpl.selectSupplierSellHighRecordList((com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.SelectSupplierSellHighRecordListRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>) responseObserver);
          break;
        case METHODID_GET_SELL_HIGH_SUBTOTAL:
          serviceImpl.getSellHighSubtotal((com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.GetSellHighSubtotalRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>) responseObserver);
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

  private static final class SupplierAccountServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.payment.project.microservice.SupplierAccountServiceStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SupplierAccountServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SupplierAccountServiceDescriptorSupplier())
              .addMethod(METHOD_GET_SUPPLIER_COUPON_BUFFER_TO_DISTRIBUTOR)
              .addMethod(METHOD_GET_SUPPLIER_ACCOUNT_AMOUNT)
              .addMethod(METHOD_POST_SUPPLIER_COUPON_ACCOUNT)
              .addMethod(METHOD_POST_SUPPLIER_PREPAID_ACCOUNT)
              .addMethod(METHOD_PAY_FOR_PURCHASE)
              .addMethod(METHOD_SELECT_BUFFER_COUPON_FLOW_LIST)
              .addMethod(METHOD_SELECT_BUFFER_PREPAID_FLOW_LIST)
              .addMethod(METHOD_GET_COUPON_BUFFER_TO_DISTRIBUTOR_SUBTOTAL)
              .addMethod(METHOD_GET_PREPAID_BUFFER_TO_DISTRIBUTOR_SUBTOTAL)
              .addMethod(METHOD_GET_SELL_HIGH_ACCOUNT)
              .addMethod(METHOD_SALES_UPDATE_SELL_HIGH_ACCOUNT)
              .addMethod(METHOD_SELECT_SUPPLIER_SELL_HIGH_RECORD_LIST)
              .addMethod(METHOD_GET_SELL_HIGH_SUBTOTAL)
              .build();
        }
      }
    }
    return result;
  }
}
