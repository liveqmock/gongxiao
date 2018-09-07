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
    comments = "Source: payment.account.distributor.proto")
public final class DistributorAccountServiceGrpc {

  private DistributorAccountServiceGrpc() {}

  public static final String SERVICE_NAME = "DistributorAccountService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetDistributorAccountAmountRequest,
      com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse> METHOD_GET_DISTRIBUTOR_ACCOUNT_AMOUNT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetDistributorAccountAmountRequest, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorAccountService", "getDistributorAccountAmount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetDistributorAccountAmountRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderRequest,
      com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderResponse> METHOD_PAY_FOR_SALES_ORDER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderRequest, com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorAccountService", "payForSalesOrder"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderRequest,
      com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderResponse> METHOD_RETURN_FOR_SALES_RETURN_ORDER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderRequest, com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorAccountService", "returnForSalesReturnOrder"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositCouponReceivedRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_DEPOSIT_COUPON_RECEIVED =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositCouponReceivedRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorAccountService", "depositCouponReceived"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositCouponReceivedRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositCouponReceivedRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_DEPOSIT_PREPAID_RECEIVED =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositCouponReceivedRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorAccountService", "depositPrepaidReceived"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositCouponReceivedRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorAccountListRequest,
      com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DistributorAccountListResponse> METHOD_SELECT_DISTRIBUTOR_COUPON_ACCOUNT_LIST =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorAccountListRequest, com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DistributorAccountListResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorAccountService", "selectDistributorCouponAccountList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorAccountListRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DistributorAccountListResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorAccountListRequest,
      com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DistributorAccountListResponse> METHOD_SELECT_DISTRIBUTOR_PREPAID_ACCOUNT_LIST =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorAccountListRequest, com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DistributorAccountListResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorAccountService", "selectDistributorPrepaidAccountList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorAccountListRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DistributorAccountListResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositReceivedAccountsRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_DEPOSIT_COUPON_RECEIVED_ACCOUNTS =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositReceivedAccountsRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorAccountService", "depositCouponReceivedAccounts"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositReceivedAccountsRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositReceivedAccountsRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_DEPOSIT_PREPAID_RECEIVED_ACCOUNTS =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositReceivedAccountsRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorAccountService", "depositPrepaidReceivedAccounts"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositReceivedAccountsRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorFlowRqeust,
      com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> METHOD_SELECT_CASH_FLOW =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorFlowRqeust, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorAccountService", "selectCashFlow"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorFlowRqeust.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetDistributorSubtotalRequest,
      com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> METHOD_GET_CASH_SUBTOTAL =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetDistributorSubtotalRequest, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorAccountService", "getCashSubtotal"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetDistributorSubtotalRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectPrepaidFlowRequest,
      com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> METHOD_SELECT_PREPAID_FLOW =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectPrepaidFlowRequest, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorAccountService", "selectPrepaidFlow"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectPrepaidFlowRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetPrepaidSubtotalRequest,
      com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> METHOD_GET_PREPAID_SUBTOTAL =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetPrepaidSubtotalRequest, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorAccountService", "getPrepaidSubtotal"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetPrepaidSubtotalRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DistributorAccountServiceStub newStub(io.grpc.Channel channel) {
    return new DistributorAccountServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DistributorAccountServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new DistributorAccountServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DistributorAccountServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new DistributorAccountServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class DistributorAccountServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *查询账户余额
     * </pre>
     */
    public void getDistributorAccountAmount(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetDistributorAccountAmountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_DISTRIBUTOR_ACCOUNT_AMOUNT, responseObserver);
    }

    /**
     * <pre>
     *销售单支付
     * </pre>
     */
    public void payForSalesOrder(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_PAY_FOR_SALES_ORDER, responseObserver);
    }

    /**
     * <pre>
     *销售单退款
     * </pre>
     */
    public void returnForSalesReturnOrder(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_RETURN_FOR_SALES_RETURN_ORDER, responseObserver);
    }

    /**
     * <pre>
     *从返利缓冲账户过账到ad实收账户
     * </pre>
     */
    public void depositCouponReceived(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositCouponReceivedRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_DEPOSIT_COUPON_RECEIVED, responseObserver);
    }

    /**
     * <pre>
     *从代垫缓冲账户过账到ad实收账户
     * </pre>
     */
    public void depositPrepaidReceived(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositCouponReceivedRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_DEPOSIT_PREPAID_RECEIVED, responseObserver);
    }

    /**
     * <pre>
     *查询ad返利账户列表
     * </pre>
     */
    public void selectDistributorCouponAccountList(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorAccountListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DistributorAccountListResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_DISTRIBUTOR_COUPON_ACCOUNT_LIST, responseObserver);
    }

    /**
     * <pre>
     *查询代垫账户列表
     * </pre>
     */
    public void selectDistributorPrepaidAccountList(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorAccountListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DistributorAccountListResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_DISTRIBUTOR_PREPAID_ACCOUNT_LIST, responseObserver);
    }

    /**
     * <pre>
     *从过账缓冲账户给ad多个账户分配返利
     * </pre>
     */
    public void depositCouponReceivedAccounts(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositReceivedAccountsRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_DEPOSIT_COUPON_RECEIVED_ACCOUNTS, responseObserver);
    }

    /**
     * <pre>
     *从过账缓冲账户给ad多个账户分配返利
     * </pre>
     */
    public void depositPrepaidReceivedAccounts(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositReceivedAccountsRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_DEPOSIT_PREPAID_RECEIVED_ACCOUNTS, responseObserver);
    }

    /**
     * <pre>
     * 查询ad现金账户流水
     * </pre>
     */
    public void selectCashFlow(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorFlowRqeust request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_CASH_FLOW, responseObserver);
    }

    /**
     * <pre>
     * 查询ad现金账户小计
     * </pre>
     */
    public void getCashSubtotal(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetDistributorSubtotalRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_CASH_SUBTOTAL, responseObserver);
    }

    /**
     */
    public void selectPrepaidFlow(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectPrepaidFlowRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_PREPAID_FLOW, responseObserver);
    }

    /**
     */
    public void getPrepaidSubtotal(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetPrepaidSubtotalRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_PREPAID_SUBTOTAL, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_DISTRIBUTOR_ACCOUNT_AMOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetDistributorAccountAmountRequest,
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse>(
                  this, METHODID_GET_DISTRIBUTOR_ACCOUNT_AMOUNT)))
          .addMethod(
            METHOD_PAY_FOR_SALES_ORDER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderRequest,
                com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderResponse>(
                  this, METHODID_PAY_FOR_SALES_ORDER)))
          .addMethod(
            METHOD_RETURN_FOR_SALES_RETURN_ORDER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderRequest,
                com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderResponse>(
                  this, METHODID_RETURN_FOR_SALES_RETURN_ORDER)))
          .addMethod(
            METHOD_DEPOSIT_COUPON_RECEIVED,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositCouponReceivedRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_DEPOSIT_COUPON_RECEIVED)))
          .addMethod(
            METHOD_DEPOSIT_PREPAID_RECEIVED,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositCouponReceivedRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_DEPOSIT_PREPAID_RECEIVED)))
          .addMethod(
            METHOD_SELECT_DISTRIBUTOR_COUPON_ACCOUNT_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorAccountListRequest,
                com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DistributorAccountListResponse>(
                  this, METHODID_SELECT_DISTRIBUTOR_COUPON_ACCOUNT_LIST)))
          .addMethod(
            METHOD_SELECT_DISTRIBUTOR_PREPAID_ACCOUNT_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorAccountListRequest,
                com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DistributorAccountListResponse>(
                  this, METHODID_SELECT_DISTRIBUTOR_PREPAID_ACCOUNT_LIST)))
          .addMethod(
            METHOD_DEPOSIT_COUPON_RECEIVED_ACCOUNTS,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositReceivedAccountsRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_DEPOSIT_COUPON_RECEIVED_ACCOUNTS)))
          .addMethod(
            METHOD_DEPOSIT_PREPAID_RECEIVED_ACCOUNTS,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositReceivedAccountsRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_DEPOSIT_PREPAID_RECEIVED_ACCOUNTS)))
          .addMethod(
            METHOD_SELECT_CASH_FLOW,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorFlowRqeust,
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>(
                  this, METHODID_SELECT_CASH_FLOW)))
          .addMethod(
            METHOD_GET_CASH_SUBTOTAL,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetDistributorSubtotalRequest,
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>(
                  this, METHODID_GET_CASH_SUBTOTAL)))
          .addMethod(
            METHOD_SELECT_PREPAID_FLOW,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectPrepaidFlowRequest,
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>(
                  this, METHODID_SELECT_PREPAID_FLOW)))
          .addMethod(
            METHOD_GET_PREPAID_SUBTOTAL,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetPrepaidSubtotalRequest,
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>(
                  this, METHODID_GET_PREPAID_SUBTOTAL)))
          .build();
    }
  }

  /**
   */
  public static final class DistributorAccountServiceStub extends io.grpc.stub.AbstractStub<DistributorAccountServiceStub> {
    private DistributorAccountServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DistributorAccountServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DistributorAccountServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DistributorAccountServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *查询账户余额
     * </pre>
     */
    public void getDistributorAccountAmount(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetDistributorAccountAmountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_DISTRIBUTOR_ACCOUNT_AMOUNT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *销售单支付
     * </pre>
     */
    public void payForSalesOrder(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_PAY_FOR_SALES_ORDER, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *销售单退款
     * </pre>
     */
    public void returnForSalesReturnOrder(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_RETURN_FOR_SALES_RETURN_ORDER, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *从返利缓冲账户过账到ad实收账户
     * </pre>
     */
    public void depositCouponReceived(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositCouponReceivedRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_DEPOSIT_COUPON_RECEIVED, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *从代垫缓冲账户过账到ad实收账户
     * </pre>
     */
    public void depositPrepaidReceived(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositCouponReceivedRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_DEPOSIT_PREPAID_RECEIVED, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *查询ad返利账户列表
     * </pre>
     */
    public void selectDistributorCouponAccountList(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorAccountListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DistributorAccountListResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_DISTRIBUTOR_COUPON_ACCOUNT_LIST, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *查询代垫账户列表
     * </pre>
     */
    public void selectDistributorPrepaidAccountList(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorAccountListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DistributorAccountListResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_DISTRIBUTOR_PREPAID_ACCOUNT_LIST, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *从过账缓冲账户给ad多个账户分配返利
     * </pre>
     */
    public void depositCouponReceivedAccounts(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositReceivedAccountsRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_DEPOSIT_COUPON_RECEIVED_ACCOUNTS, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *从过账缓冲账户给ad多个账户分配返利
     * </pre>
     */
    public void depositPrepaidReceivedAccounts(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositReceivedAccountsRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_DEPOSIT_PREPAID_RECEIVED_ACCOUNTS, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 查询ad现金账户流水
     * </pre>
     */
    public void selectCashFlow(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorFlowRqeust request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_CASH_FLOW, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 查询ad现金账户小计
     * </pre>
     */
    public void getCashSubtotal(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetDistributorSubtotalRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_CASH_SUBTOTAL, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void selectPrepaidFlow(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectPrepaidFlowRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_PREPAID_FLOW, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getPrepaidSubtotal(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetPrepaidSubtotalRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_PREPAID_SUBTOTAL, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class DistributorAccountServiceBlockingStub extends io.grpc.stub.AbstractStub<DistributorAccountServiceBlockingStub> {
    private DistributorAccountServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DistributorAccountServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DistributorAccountServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DistributorAccountServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *查询账户余额
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse getDistributorAccountAmount(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetDistributorAccountAmountRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_DISTRIBUTOR_ACCOUNT_AMOUNT, getCallOptions(), request);
    }

    /**
     * <pre>
     *销售单支付
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderResponse payForSalesOrder(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_PAY_FOR_SALES_ORDER, getCallOptions(), request);
    }

    /**
     * <pre>
     *销售单退款
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderResponse returnForSalesReturnOrder(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_RETURN_FOR_SALES_RETURN_ORDER, getCallOptions(), request);
    }

    /**
     * <pre>
     *从返利缓冲账户过账到ad实收账户
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult depositCouponReceived(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositCouponReceivedRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_DEPOSIT_COUPON_RECEIVED, getCallOptions(), request);
    }

    /**
     * <pre>
     *从代垫缓冲账户过账到ad实收账户
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult depositPrepaidReceived(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositCouponReceivedRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_DEPOSIT_PREPAID_RECEIVED, getCallOptions(), request);
    }

    /**
     * <pre>
     *查询ad返利账户列表
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DistributorAccountListResponse selectDistributorCouponAccountList(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorAccountListRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_DISTRIBUTOR_COUPON_ACCOUNT_LIST, getCallOptions(), request);
    }

    /**
     * <pre>
     *查询代垫账户列表
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DistributorAccountListResponse selectDistributorPrepaidAccountList(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorAccountListRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_DISTRIBUTOR_PREPAID_ACCOUNT_LIST, getCallOptions(), request);
    }

    /**
     * <pre>
     *从过账缓冲账户给ad多个账户分配返利
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult depositCouponReceivedAccounts(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositReceivedAccountsRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_DEPOSIT_COUPON_RECEIVED_ACCOUNTS, getCallOptions(), request);
    }

    /**
     * <pre>
     *从过账缓冲账户给ad多个账户分配返利
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult depositPrepaidReceivedAccounts(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositReceivedAccountsRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_DEPOSIT_PREPAID_RECEIVED_ACCOUNTS, getCallOptions(), request);
    }

    /**
     * <pre>
     * 查询ad现金账户流水
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo selectCashFlow(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorFlowRqeust request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_CASH_FLOW, getCallOptions(), request);
    }

    /**
     * <pre>
     * 查询ad现金账户小计
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal getCashSubtotal(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetDistributorSubtotalRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_CASH_SUBTOTAL, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo selectPrepaidFlow(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectPrepaidFlowRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_PREPAID_FLOW, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal getPrepaidSubtotal(com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetPrepaidSubtotalRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_PREPAID_SUBTOTAL, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class DistributorAccountServiceFutureStub extends io.grpc.stub.AbstractStub<DistributorAccountServiceFutureStub> {
    private DistributorAccountServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DistributorAccountServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DistributorAccountServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DistributorAccountServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *查询账户余额
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse> getDistributorAccountAmount(
        com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetDistributorAccountAmountRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_DISTRIBUTOR_ACCOUNT_AMOUNT, getCallOptions()), request);
    }

    /**
     * <pre>
     *销售单支付
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderResponse> payForSalesOrder(
        com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_PAY_FOR_SALES_ORDER, getCallOptions()), request);
    }

    /**
     * <pre>
     *销售单退款
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderResponse> returnForSalesReturnOrder(
        com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_RETURN_FOR_SALES_RETURN_ORDER, getCallOptions()), request);
    }

    /**
     * <pre>
     *从返利缓冲账户过账到ad实收账户
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> depositCouponReceived(
        com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositCouponReceivedRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_DEPOSIT_COUPON_RECEIVED, getCallOptions()), request);
    }

    /**
     * <pre>
     *从代垫缓冲账户过账到ad实收账户
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> depositPrepaidReceived(
        com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositCouponReceivedRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_DEPOSIT_PREPAID_RECEIVED, getCallOptions()), request);
    }

    /**
     * <pre>
     *查询ad返利账户列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DistributorAccountListResponse> selectDistributorCouponAccountList(
        com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorAccountListRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_DISTRIBUTOR_COUPON_ACCOUNT_LIST, getCallOptions()), request);
    }

    /**
     * <pre>
     *查询代垫账户列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DistributorAccountListResponse> selectDistributorPrepaidAccountList(
        com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorAccountListRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_DISTRIBUTOR_PREPAID_ACCOUNT_LIST, getCallOptions()), request);
    }

    /**
     * <pre>
     *从过账缓冲账户给ad多个账户分配返利
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> depositCouponReceivedAccounts(
        com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositReceivedAccountsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_DEPOSIT_COUPON_RECEIVED_ACCOUNTS, getCallOptions()), request);
    }

    /**
     * <pre>
     *从过账缓冲账户给ad多个账户分配返利
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> depositPrepaidReceivedAccounts(
        com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositReceivedAccountsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_DEPOSIT_PREPAID_RECEIVED_ACCOUNTS, getCallOptions()), request);
    }

    /**
     * <pre>
     * 查询ad现金账户流水
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> selectCashFlow(
        com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorFlowRqeust request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_CASH_FLOW, getCallOptions()), request);
    }

    /**
     * <pre>
     * 查询ad现金账户小计
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> getCashSubtotal(
        com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetDistributorSubtotalRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_CASH_SUBTOTAL, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> selectPrepaidFlow(
        com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectPrepaidFlowRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_PREPAID_FLOW, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> getPrepaidSubtotal(
        com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetPrepaidSubtotalRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_PREPAID_SUBTOTAL, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_DISTRIBUTOR_ACCOUNT_AMOUNT = 0;
  private static final int METHODID_PAY_FOR_SALES_ORDER = 1;
  private static final int METHODID_RETURN_FOR_SALES_RETURN_ORDER = 2;
  private static final int METHODID_DEPOSIT_COUPON_RECEIVED = 3;
  private static final int METHODID_DEPOSIT_PREPAID_RECEIVED = 4;
  private static final int METHODID_SELECT_DISTRIBUTOR_COUPON_ACCOUNT_LIST = 5;
  private static final int METHODID_SELECT_DISTRIBUTOR_PREPAID_ACCOUNT_LIST = 6;
  private static final int METHODID_DEPOSIT_COUPON_RECEIVED_ACCOUNTS = 7;
  private static final int METHODID_DEPOSIT_PREPAID_RECEIVED_ACCOUNTS = 8;
  private static final int METHODID_SELECT_CASH_FLOW = 9;
  private static final int METHODID_GET_CASH_SUBTOTAL = 10;
  private static final int METHODID_SELECT_PREPAID_FLOW = 11;
  private static final int METHODID_GET_PREPAID_SUBTOTAL = 12;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DistributorAccountServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DistributorAccountServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_DISTRIBUTOR_ACCOUNT_AMOUNT:
          serviceImpl.getDistributorAccountAmount((com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetDistributorAccountAmountRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse>) responseObserver);
          break;
        case METHODID_PAY_FOR_SALES_ORDER:
          serviceImpl.payForSalesOrder((com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderResponse>) responseObserver);
          break;
        case METHODID_RETURN_FOR_SALES_RETURN_ORDER:
          serviceImpl.returnForSalesReturnOrder((com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.PayForSalesOrderResponse>) responseObserver);
          break;
        case METHODID_DEPOSIT_COUPON_RECEIVED:
          serviceImpl.depositCouponReceived((com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositCouponReceivedRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_DEPOSIT_PREPAID_RECEIVED:
          serviceImpl.depositPrepaidReceived((com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositCouponReceivedRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_SELECT_DISTRIBUTOR_COUPON_ACCOUNT_LIST:
          serviceImpl.selectDistributorCouponAccountList((com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorAccountListRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DistributorAccountListResponse>) responseObserver);
          break;
        case METHODID_SELECT_DISTRIBUTOR_PREPAID_ACCOUNT_LIST:
          serviceImpl.selectDistributorPrepaidAccountList((com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorAccountListRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DistributorAccountListResponse>) responseObserver);
          break;
        case METHODID_DEPOSIT_COUPON_RECEIVED_ACCOUNTS:
          serviceImpl.depositCouponReceivedAccounts((com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositReceivedAccountsRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_DEPOSIT_PREPAID_RECEIVED_ACCOUNTS:
          serviceImpl.depositPrepaidReceivedAccounts((com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.DepositReceivedAccountsRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_SELECT_CASH_FLOW:
          serviceImpl.selectCashFlow((com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectDistributorFlowRqeust) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>) responseObserver);
          break;
        case METHODID_GET_CASH_SUBTOTAL:
          serviceImpl.getCashSubtotal((com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetDistributorSubtotalRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>) responseObserver);
          break;
        case METHODID_SELECT_PREPAID_FLOW:
          serviceImpl.selectPrepaidFlow((com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.SelectPrepaidFlowRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>) responseObserver);
          break;
        case METHODID_GET_PREPAID_SUBTOTAL:
          serviceImpl.getPrepaidSubtotal((com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.GetPrepaidSubtotalRequest) request,
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

  private static final class DistributorAccountServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (DistributorAccountServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DistributorAccountServiceDescriptorSupplier())
              .addMethod(METHOD_GET_DISTRIBUTOR_ACCOUNT_AMOUNT)
              .addMethod(METHOD_PAY_FOR_SALES_ORDER)
              .addMethod(METHOD_RETURN_FOR_SALES_RETURN_ORDER)
              .addMethod(METHOD_DEPOSIT_COUPON_RECEIVED)
              .addMethod(METHOD_DEPOSIT_PREPAID_RECEIVED)
              .addMethod(METHOD_SELECT_DISTRIBUTOR_COUPON_ACCOUNT_LIST)
              .addMethod(METHOD_SELECT_DISTRIBUTOR_PREPAID_ACCOUNT_LIST)
              .addMethod(METHOD_DEPOSIT_COUPON_RECEIVED_ACCOUNTS)
              .addMethod(METHOD_DEPOSIT_PREPAID_RECEIVED_ACCOUNTS)
              .addMethod(METHOD_SELECT_CASH_FLOW)
              .addMethod(METHOD_GET_CASH_SUBTOTAL)
              .addMethod(METHOD_SELECT_PREPAID_FLOW)
              .addMethod(METHOD_GET_PREPAID_SUBTOTAL)
              .build();
        }
      }
    }
    return result;
  }
}
