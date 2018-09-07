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
    comments = "Source: payment.account.supplier.proto")
public final class SupplierAccountServiceGrpc {

  private SupplierAccountServiceGrpc() {}

  public static final String SERVICE_NAME = "SupplierAccountService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierCouponBufferToDistributorRequest,
      com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse> METHOD_GET_SUPPLIER_COUPON_BUFFER_TO_DISTRIBUTOR =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierCouponBufferToDistributorRequest, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "getSupplierCouponBufferToDistributor"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierCouponBufferToDistributorRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountAmountRequest,
      com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse> METHOD_GET_SUPPLIER_ACCOUNT_AMOUNT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountAmountRequest, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "getSupplierAccountAmount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountAmountRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_POST_SUPPLIER_COUPON_ACCOUNT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "postSupplierCouponAccount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_POST_SUPPLIER_PREPAID_ACCOUNT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "postSupplierPrepaidAccount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PayForPurchaseRequest,
      com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PayForPurchaseResponse> METHOD_PAY_FOR_PURCHASE =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PayForPurchaseRequest, com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PayForPurchaseResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "payForPurchase"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PayForPurchaseRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PayForPurchaseResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest,
      com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> METHOD_SELECT_BUFFER_COUPON_FLOW_LIST =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "selectBufferCouponFlowList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest,
      com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> METHOD_SELECT_BUFFER_PREPAID_FLOW_LIST =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "selectBufferPrepaidFlowList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest,
      com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> METHOD_GET_COUPON_BUFFER_TO_DISTRIBUTOR_SUBTOTAL =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "getCouponBufferToDistributorSubtotal"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest,
      com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> METHOD_GET_PREPAID_BUFFER_TO_DISTRIBUTOR_SUBTOTAL =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "getPrepaidBufferToDistributorSubtotal"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSellHighAccountRequest,
      com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSellHighAccountResponse> METHOD_GET_SELL_HIGH_ACCOUNT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSellHighAccountRequest, com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSellHighAccountResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "getSellHighAccount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSellHighAccountRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSellHighAccountResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.UpdateSupplierSellHighAccountOnJmgoRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_UPDATE_SUPPLIER_SELL_HIGH_ACCOUNT_ON_JMGO =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.UpdateSupplierSellHighAccountOnJmgoRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "updateSupplierSellHighAccountOnJmgo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.UpdateSupplierSellHighAccountOnJmgoRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SalesUpdateSellHighAccountRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_SALES_UPDATE_SELL_HIGH_ACCOUNT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SalesUpdateSellHighAccountRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "salesUpdateSellHighAccount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SalesUpdateSellHighAccountRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest,
      com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse> METHOD_WRITE_OFF_UPDATE_SELL_HIGH_ACCOUNT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "writeOffUpdateSellHighAccount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest,
      com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> METHOD_SELECT_SUPPLIER_SELL_HIGH_RECORD_LIST =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "selectSupplierSellHighRecordList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest,
      com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> METHOD_GET_SELL_HIGH_SUBTOTAL =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "getSellHighSubtotal"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectPrepaidBySupplierIdRequest,
      com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> METHOD_SELECT_PREPAID_BY_SUPPLIER_ID =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectPrepaidBySupplierIdRequest, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "selectPrepaidBySupplierId"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectPrepaidBySupplierIdRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectIncomeAndExpenditureRequest,
      com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> METHOD_SELECT_INCOME_AND_EXPENDITURE =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectIncomeAndExpenditureRequest, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierAccountService", "selectIncomeAndExpenditure"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectIncomeAndExpenditureRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal.getDefaultInstance()))
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
    public void getSupplierCouponBufferToDistributor(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierCouponBufferToDistributorRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_SUPPLIER_COUPON_BUFFER_TO_DISTRIBUTOR, responseObserver);
    }

    /**
     * <pre>
     *查询供应商上账账户余额
     * </pre>
     */
    public void getSupplierAccountAmount(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountAmountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_SUPPLIER_ACCOUNT_AMOUNT, responseObserver);
    }

    /**
     * <pre>
     *返利上账
     * </pre>
     */
    public void postSupplierCouponAccount(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_POST_SUPPLIER_COUPON_ACCOUNT, responseObserver);
    }

    /**
     * <pre>
     *代垫上账
     * </pre>
     */
    public void postSupplierPrepaidAccount(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_POST_SUPPLIER_PREPAID_ACCOUNT, responseObserver);
    }

    /**
     * <pre>
     *上账账户暂扣返利、代垫
     * </pre>
     */
    public void payForPurchase(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PayForPurchaseRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PayForPurchaseResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_PAY_FOR_PURCHASE, responseObserver);
    }

    /**
     * <pre>
     *查询 ad返利过账账户 流水
     * </pre>
     */
    public void selectBufferCouponFlowList(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_BUFFER_COUPON_FLOW_LIST, responseObserver);
    }

    /**
     * <pre>
     *查询 ad代垫过账账户 流水
     * </pre>
     */
    public void selectBufferPrepaidFlowList(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_BUFFER_PREPAID_FLOW_LIST, responseObserver);
    }

    /**
     * <pre>
     *查询 ad返利过账账户 流水小计
     * </pre>
     */
    public void getCouponBufferToDistributorSubtotal(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_COUPON_BUFFER_TO_DISTRIBUTOR_SUBTOTAL, responseObserver);
    }

    /**
     * <pre>
     *查询 ad代垫过账账户 流水小计
     * </pre>
     */
    public void getPrepaidBufferToDistributorSubtotal(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_PREPAID_BUFFER_TO_DISTRIBUTOR_SUBTOTAL, responseObserver);
    }

    /**
     * <pre>
     *查询供应商差价账户余额
     * </pre>
     */
    public void getSellHighAccount(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSellHighAccountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSellHighAccountResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_SELL_HIGH_ACCOUNT, responseObserver);
    }

    /**
     * <pre>
     * 坚果项目修改差价账户
     * </pre>
     */
    public void updateSupplierSellHighAccountOnJmgo(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.UpdateSupplierSellHighAccountOnJmgoRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_SUPPLIER_SELL_HIGH_ACCOUNT_ON_JMGO, responseObserver);
    }

    /**
     * <pre>
     *销售更新低买高卖账户
     * </pre>
     */
    public void salesUpdateSellHighAccount(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SalesUpdateSellHighAccountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SALES_UPDATE_SELL_HIGH_ACCOUNT, responseObserver);
    }

    /**
     * <pre>
     *核销更新高卖账户
     * </pre>
     */
    public void writeOffUpdateSellHighAccount(com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_WRITE_OFF_UPDATE_SELL_HIGH_ACCOUNT, responseObserver);
    }

    /**
     * <pre>
     *查询 供应商低买高卖账户 流水
     * </pre>
     */
    public void selectSupplierSellHighRecordList(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_SUPPLIER_SELL_HIGH_RECORD_LIST, responseObserver);
    }

    /**
     * <pre>
     *查询 供应商低买高卖账户 小计
     * </pre>
     */
    public void getSellHighSubtotal(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_SELL_HIGH_SUBTOTAL, responseObserver);
    }

    /**
     */
    public void selectPrepaidBySupplierId(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectPrepaidBySupplierIdRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_PREPAID_BY_SUPPLIER_ID, responseObserver);
    }

    /**
     */
    public void selectIncomeAndExpenditure(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectIncomeAndExpenditureRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_INCOME_AND_EXPENDITURE, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_SUPPLIER_COUPON_BUFFER_TO_DISTRIBUTOR,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierCouponBufferToDistributorRequest,
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse>(
                  this, METHODID_GET_SUPPLIER_COUPON_BUFFER_TO_DISTRIBUTOR)))
          .addMethod(
            METHOD_GET_SUPPLIER_ACCOUNT_AMOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountAmountRequest,
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse>(
                  this, METHODID_GET_SUPPLIER_ACCOUNT_AMOUNT)))
          .addMethod(
            METHOD_POST_SUPPLIER_COUPON_ACCOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_POST_SUPPLIER_COUPON_ACCOUNT)))
          .addMethod(
            METHOD_POST_SUPPLIER_PREPAID_ACCOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_POST_SUPPLIER_PREPAID_ACCOUNT)))
          .addMethod(
            METHOD_PAY_FOR_PURCHASE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PayForPurchaseRequest,
                com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PayForPurchaseResponse>(
                  this, METHODID_PAY_FOR_PURCHASE)))
          .addMethod(
            METHOD_SELECT_BUFFER_COUPON_FLOW_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest,
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>(
                  this, METHODID_SELECT_BUFFER_COUPON_FLOW_LIST)))
          .addMethod(
            METHOD_SELECT_BUFFER_PREPAID_FLOW_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest,
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>(
                  this, METHODID_SELECT_BUFFER_PREPAID_FLOW_LIST)))
          .addMethod(
            METHOD_GET_COUPON_BUFFER_TO_DISTRIBUTOR_SUBTOTAL,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest,
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>(
                  this, METHODID_GET_COUPON_BUFFER_TO_DISTRIBUTOR_SUBTOTAL)))
          .addMethod(
            METHOD_GET_PREPAID_BUFFER_TO_DISTRIBUTOR_SUBTOTAL,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest,
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>(
                  this, METHODID_GET_PREPAID_BUFFER_TO_DISTRIBUTOR_SUBTOTAL)))
          .addMethod(
            METHOD_GET_SELL_HIGH_ACCOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSellHighAccountRequest,
                com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSellHighAccountResponse>(
                  this, METHODID_GET_SELL_HIGH_ACCOUNT)))
          .addMethod(
            METHOD_UPDATE_SUPPLIER_SELL_HIGH_ACCOUNT_ON_JMGO,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.UpdateSupplierSellHighAccountOnJmgoRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_UPDATE_SUPPLIER_SELL_HIGH_ACCOUNT_ON_JMGO)))
          .addMethod(
            METHOD_SALES_UPDATE_SELL_HIGH_ACCOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SalesUpdateSellHighAccountRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_SALES_UPDATE_SELL_HIGH_ACCOUNT)))
          .addMethod(
            METHOD_WRITE_OFF_UPDATE_SELL_HIGH_ACCOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest,
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse>(
                  this, METHODID_WRITE_OFF_UPDATE_SELL_HIGH_ACCOUNT)))
          .addMethod(
            METHOD_SELECT_SUPPLIER_SELL_HIGH_RECORD_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest,
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>(
                  this, METHODID_SELECT_SUPPLIER_SELL_HIGH_RECORD_LIST)))
          .addMethod(
            METHOD_GET_SELL_HIGH_SUBTOTAL,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest,
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>(
                  this, METHODID_GET_SELL_HIGH_SUBTOTAL)))
          .addMethod(
            METHOD_SELECT_PREPAID_BY_SUPPLIER_ID,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectPrepaidBySupplierIdRequest,
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>(
                  this, METHODID_SELECT_PREPAID_BY_SUPPLIER_ID)))
          .addMethod(
            METHOD_SELECT_INCOME_AND_EXPENDITURE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectIncomeAndExpenditureRequest,
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>(
                  this, METHODID_SELECT_INCOME_AND_EXPENDITURE)))
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
    public void getSupplierCouponBufferToDistributor(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierCouponBufferToDistributorRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_SUPPLIER_COUPON_BUFFER_TO_DISTRIBUTOR, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *查询供应商上账账户余额
     * </pre>
     */
    public void getSupplierAccountAmount(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountAmountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_SUPPLIER_ACCOUNT_AMOUNT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *返利上账
     * </pre>
     */
    public void postSupplierCouponAccount(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_POST_SUPPLIER_COUPON_ACCOUNT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *代垫上账
     * </pre>
     */
    public void postSupplierPrepaidAccount(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_POST_SUPPLIER_PREPAID_ACCOUNT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *上账账户暂扣返利、代垫
     * </pre>
     */
    public void payForPurchase(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PayForPurchaseRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PayForPurchaseResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_PAY_FOR_PURCHASE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *查询 ad返利过账账户 流水
     * </pre>
     */
    public void selectBufferCouponFlowList(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_BUFFER_COUPON_FLOW_LIST, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *查询 ad代垫过账账户 流水
     * </pre>
     */
    public void selectBufferPrepaidFlowList(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_BUFFER_PREPAID_FLOW_LIST, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *查询 ad返利过账账户 流水小计
     * </pre>
     */
    public void getCouponBufferToDistributorSubtotal(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_COUPON_BUFFER_TO_DISTRIBUTOR_SUBTOTAL, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *查询 ad代垫过账账户 流水小计
     * </pre>
     */
    public void getPrepaidBufferToDistributorSubtotal(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_PREPAID_BUFFER_TO_DISTRIBUTOR_SUBTOTAL, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *查询供应商差价账户余额
     * </pre>
     */
    public void getSellHighAccount(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSellHighAccountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSellHighAccountResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_SELL_HIGH_ACCOUNT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 坚果项目修改差价账户
     * </pre>
     */
    public void updateSupplierSellHighAccountOnJmgo(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.UpdateSupplierSellHighAccountOnJmgoRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_SUPPLIER_SELL_HIGH_ACCOUNT_ON_JMGO, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *销售更新低买高卖账户
     * </pre>
     */
    public void salesUpdateSellHighAccount(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SalesUpdateSellHighAccountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SALES_UPDATE_SELL_HIGH_ACCOUNT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *核销更新高卖账户
     * </pre>
     */
    public void writeOffUpdateSellHighAccount(com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_WRITE_OFF_UPDATE_SELL_HIGH_ACCOUNT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *查询 供应商低买高卖账户 流水
     * </pre>
     */
    public void selectSupplierSellHighRecordList(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_SUPPLIER_SELL_HIGH_RECORD_LIST, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *查询 供应商低买高卖账户 小计
     * </pre>
     */
    public void getSellHighSubtotal(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_SELL_HIGH_SUBTOTAL, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void selectPrepaidBySupplierId(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectPrepaidBySupplierIdRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_PREPAID_BY_SUPPLIER_ID, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void selectIncomeAndExpenditure(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectIncomeAndExpenditureRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_INCOME_AND_EXPENDITURE, getCallOptions()), request, responseObserver);
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
    public com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse getSupplierCouponBufferToDistributor(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierCouponBufferToDistributorRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_SUPPLIER_COUPON_BUFFER_TO_DISTRIBUTOR, getCallOptions(), request);
    }

    /**
     * <pre>
     *查询供应商上账账户余额
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse getSupplierAccountAmount(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountAmountRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_SUPPLIER_ACCOUNT_AMOUNT, getCallOptions(), request);
    }

    /**
     * <pre>
     *返利上账
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult postSupplierCouponAccount(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_POST_SUPPLIER_COUPON_ACCOUNT, getCallOptions(), request);
    }

    /**
     * <pre>
     *代垫上账
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult postSupplierPrepaidAccount(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_POST_SUPPLIER_PREPAID_ACCOUNT, getCallOptions(), request);
    }

    /**
     * <pre>
     *上账账户暂扣返利、代垫
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PayForPurchaseResponse payForPurchase(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PayForPurchaseRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_PAY_FOR_PURCHASE, getCallOptions(), request);
    }

    /**
     * <pre>
     *查询 ad返利过账账户 流水
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo selectBufferCouponFlowList(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_BUFFER_COUPON_FLOW_LIST, getCallOptions(), request);
    }

    /**
     * <pre>
     *查询 ad代垫过账账户 流水
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo selectBufferPrepaidFlowList(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_BUFFER_PREPAID_FLOW_LIST, getCallOptions(), request);
    }

    /**
     * <pre>
     *查询 ad返利过账账户 流水小计
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal getCouponBufferToDistributorSubtotal(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_COUPON_BUFFER_TO_DISTRIBUTOR_SUBTOTAL, getCallOptions(), request);
    }

    /**
     * <pre>
     *查询 ad代垫过账账户 流水小计
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal getPrepaidBufferToDistributorSubtotal(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_PREPAID_BUFFER_TO_DISTRIBUTOR_SUBTOTAL, getCallOptions(), request);
    }

    /**
     * <pre>
     *查询供应商差价账户余额
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSellHighAccountResponse getSellHighAccount(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSellHighAccountRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_SELL_HIGH_ACCOUNT, getCallOptions(), request);
    }

    /**
     * <pre>
     * 坚果项目修改差价账户
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult updateSupplierSellHighAccountOnJmgo(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.UpdateSupplierSellHighAccountOnJmgoRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_SUPPLIER_SELL_HIGH_ACCOUNT_ON_JMGO, getCallOptions(), request);
    }

    /**
     * <pre>
     *销售更新低买高卖账户
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult salesUpdateSellHighAccount(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SalesUpdateSellHighAccountRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SALES_UPDATE_SELL_HIGH_ACCOUNT, getCallOptions(), request);
    }

    /**
     * <pre>
     *核销更新高卖账户
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse writeOffUpdateSellHighAccount(com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_WRITE_OFF_UPDATE_SELL_HIGH_ACCOUNT, getCallOptions(), request);
    }

    /**
     * <pre>
     *查询 供应商低买高卖账户 流水
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo selectSupplierSellHighRecordList(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_SUPPLIER_SELL_HIGH_RECORD_LIST, getCallOptions(), request);
    }

    /**
     * <pre>
     *查询 供应商低买高卖账户 小计
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal getSellHighSubtotal(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_SELL_HIGH_SUBTOTAL, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo selectPrepaidBySupplierId(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectPrepaidBySupplierIdRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_PREPAID_BY_SUPPLIER_ID, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal selectIncomeAndExpenditure(com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectIncomeAndExpenditureRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_INCOME_AND_EXPENDITURE, getCallOptions(), request);
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
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse> getSupplierCouponBufferToDistributor(
        com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierCouponBufferToDistributorRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_SUPPLIER_COUPON_BUFFER_TO_DISTRIBUTOR, getCallOptions()), request);
    }

    /**
     * <pre>
     *查询供应商上账账户余额
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse> getSupplierAccountAmount(
        com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountAmountRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_SUPPLIER_ACCOUNT_AMOUNT, getCallOptions()), request);
    }

    /**
     * <pre>
     *返利上账
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> postSupplierCouponAccount(
        com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_POST_SUPPLIER_COUPON_ACCOUNT, getCallOptions()), request);
    }

    /**
     * <pre>
     *代垫上账
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> postSupplierPrepaidAccount(
        com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_POST_SUPPLIER_PREPAID_ACCOUNT, getCallOptions()), request);
    }

    /**
     * <pre>
     *上账账户暂扣返利、代垫
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PayForPurchaseResponse> payForPurchase(
        com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PayForPurchaseRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_PAY_FOR_PURCHASE, getCallOptions()), request);
    }

    /**
     * <pre>
     *查询 ad返利过账账户 流水
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> selectBufferCouponFlowList(
        com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_BUFFER_COUPON_FLOW_LIST, getCallOptions()), request);
    }

    /**
     * <pre>
     *查询 ad代垫过账账户 流水
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> selectBufferPrepaidFlowList(
        com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_BUFFER_PREPAID_FLOW_LIST, getCallOptions()), request);
    }

    /**
     * <pre>
     *查询 ad返利过账账户 流水小计
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> getCouponBufferToDistributorSubtotal(
        com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_COUPON_BUFFER_TO_DISTRIBUTOR_SUBTOTAL, getCallOptions()), request);
    }

    /**
     * <pre>
     *查询 ad代垫过账账户 流水小计
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> getPrepaidBufferToDistributorSubtotal(
        com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_PREPAID_BUFFER_TO_DISTRIBUTOR_SUBTOTAL, getCallOptions()), request);
    }

    /**
     * <pre>
     *查询供应商差价账户余额
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSellHighAccountResponse> getSellHighAccount(
        com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSellHighAccountRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_SELL_HIGH_ACCOUNT, getCallOptions()), request);
    }

    /**
     * <pre>
     * 坚果项目修改差价账户
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> updateSupplierSellHighAccountOnJmgo(
        com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.UpdateSupplierSellHighAccountOnJmgoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_SUPPLIER_SELL_HIGH_ACCOUNT_ON_JMGO, getCallOptions()), request);
    }

    /**
     * <pre>
     *销售更新低买高卖账户
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> salesUpdateSellHighAccount(
        com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SalesUpdateSellHighAccountRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SALES_UPDATE_SELL_HIGH_ACCOUNT, getCallOptions()), request);
    }

    /**
     * <pre>
     *核销更新高卖账户
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse> writeOffUpdateSellHighAccount(
        com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_WRITE_OFF_UPDATE_SELL_HIGH_ACCOUNT, getCallOptions()), request);
    }

    /**
     * <pre>
     *查询 供应商低买高卖账户 流水
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> selectSupplierSellHighRecordList(
        com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_SUPPLIER_SELL_HIGH_RECORD_LIST, getCallOptions()), request);
    }

    /**
     * <pre>
     *查询 供应商低买高卖账户 小计
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> getSellHighSubtotal(
        com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_SELL_HIGH_SUBTOTAL, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> selectPrepaidBySupplierId(
        com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectPrepaidBySupplierIdRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_PREPAID_BY_SUPPLIER_ID, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> selectIncomeAndExpenditure(
        com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectIncomeAndExpenditureRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_INCOME_AND_EXPENDITURE, getCallOptions()), request);
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
  private static final int METHODID_UPDATE_SUPPLIER_SELL_HIGH_ACCOUNT_ON_JMGO = 10;
  private static final int METHODID_SALES_UPDATE_SELL_HIGH_ACCOUNT = 11;
  private static final int METHODID_WRITE_OFF_UPDATE_SELL_HIGH_ACCOUNT = 12;
  private static final int METHODID_SELECT_SUPPLIER_SELL_HIGH_RECORD_LIST = 13;
  private static final int METHODID_GET_SELL_HIGH_SUBTOTAL = 14;
  private static final int METHODID_SELECT_PREPAID_BY_SUPPLIER_ID = 15;
  private static final int METHODID_SELECT_INCOME_AND_EXPENDITURE = 16;

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
          serviceImpl.getSupplierCouponBufferToDistributor((com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierCouponBufferToDistributorRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse>) responseObserver);
          break;
        case METHODID_GET_SUPPLIER_ACCOUNT_AMOUNT:
          serviceImpl.getSupplierAccountAmount((com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountAmountRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse>) responseObserver);
          break;
        case METHODID_POST_SUPPLIER_COUPON_ACCOUNT:
          serviceImpl.postSupplierCouponAccount((com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PostSupplierCouponAccountRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_POST_SUPPLIER_PREPAID_ACCOUNT:
          serviceImpl.postSupplierPrepaidAccount((com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PostSupplierPrepaidAccountRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_PAY_FOR_PURCHASE:
          serviceImpl.payForPurchase((com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PayForPurchaseRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.PayForPurchaseResponse>) responseObserver);
          break;
        case METHODID_SELECT_BUFFER_COUPON_FLOW_LIST:
          serviceImpl.selectBufferCouponFlowList((com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>) responseObserver);
          break;
        case METHODID_SELECT_BUFFER_PREPAID_FLOW_LIST:
          serviceImpl.selectBufferPrepaidFlowList((com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>) responseObserver);
          break;
        case METHODID_GET_COUPON_BUFFER_TO_DISTRIBUTOR_SUBTOTAL:
          serviceImpl.getCouponBufferToDistributorSubtotal((com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>) responseObserver);
          break;
        case METHODID_GET_PREPAID_BUFFER_TO_DISTRIBUTOR_SUBTOTAL:
          serviceImpl.getPrepaidBufferToDistributorSubtotal((com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>) responseObserver);
          break;
        case METHODID_GET_SELL_HIGH_ACCOUNT:
          serviceImpl.getSellHighAccount((com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSellHighAccountRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSellHighAccountResponse>) responseObserver);
          break;
        case METHODID_UPDATE_SUPPLIER_SELL_HIGH_ACCOUNT_ON_JMGO:
          serviceImpl.updateSupplierSellHighAccountOnJmgo((com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.UpdateSupplierSellHighAccountOnJmgoRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_SALES_UPDATE_SELL_HIGH_ACCOUNT:
          serviceImpl.salesUpdateSellHighAccount((com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SalesUpdateSellHighAccountRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_WRITE_OFF_UPDATE_SELL_HIGH_ACCOUNT:
          serviceImpl.writeOffUpdateSellHighAccount((com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse>) responseObserver);
          break;
        case METHODID_SELECT_SUPPLIER_SELL_HIGH_RECORD_LIST:
          serviceImpl.selectSupplierSellHighRecordList((com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>) responseObserver);
          break;
        case METHODID_GET_SELL_HIGH_SUBTOTAL:
          serviceImpl.getSellHighSubtotal((com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>) responseObserver);
          break;
        case METHODID_SELECT_PREPAID_BY_SUPPLIER_ID:
          serviceImpl.selectPrepaidBySupplierId((com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectPrepaidBySupplierIdRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>) responseObserver);
          break;
        case METHODID_SELECT_INCOME_AND_EXPENDITURE:
          serviceImpl.selectIncomeAndExpenditure((com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.SelectIncomeAndExpenditureRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>) responseObserver);
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
      return com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure.getDescriptor();
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
              .addMethod(METHOD_UPDATE_SUPPLIER_SELL_HIGH_ACCOUNT_ON_JMGO)
              .addMethod(METHOD_SALES_UPDATE_SELL_HIGH_ACCOUNT)
              .addMethod(METHOD_WRITE_OFF_UPDATE_SELL_HIGH_ACCOUNT)
              .addMethod(METHOD_SELECT_SUPPLIER_SELL_HIGH_RECORD_LIST)
              .addMethod(METHOD_GET_SELL_HIGH_SUBTOTAL)
              .addMethod(METHOD_SELECT_PREPAID_BY_SUPPLIER_ID)
              .addMethod(METHOD_SELECT_INCOME_AND_EXPENDITURE)
              .build();
        }
      }
    }
    return result;
  }
}
