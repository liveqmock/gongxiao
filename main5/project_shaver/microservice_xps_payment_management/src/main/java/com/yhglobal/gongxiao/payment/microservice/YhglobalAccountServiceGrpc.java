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
    comments = "Source: payment.account.yhglobal.proto")
public final class YhglobalAccountServiceGrpc {

  private YhglobalAccountServiceGrpc() {}

  public static final String SERVICE_NAME = "YhglobalAccountService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest,
      com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse> METHOD_GET_YHGLOBAL_RECEIVED_ACCOUNT_AMOUNT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalAccountService", "getYhglobalReceivedAccountAmount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.SelectYhglobalFlowsRequest,
      com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> METHOD_SELECT_COUPON_RECEIVED_RECORDS =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.SelectYhglobalFlowsRequest, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalAccountService", "selectCouponReceivedRecords"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.SelectYhglobalFlowsRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalSubtotalRequest,
      com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> METHOD_GET_COUPON_SUBTOTAL =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalSubtotalRequest, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalAccountService", "getCouponSubtotal"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalSubtotalRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.SelectYhglobalFlowsRequest,
      com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> METHOD_SELECT_PREPAID_RECEIVED_RECORDS =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.SelectYhglobalFlowsRequest, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalAccountService", "selectPrepaidReceivedRecords"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.SelectYhglobalFlowsRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalSubtotalRequest,
      com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> METHOD_GET_PREPAID_SUBTOTAL =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalSubtotalRequest, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalAccountService", "getPrepaidSubtotal"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalSubtotalRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.TransferReceivedAccountRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_TRANSFER_YHGLOBAL_RECEIVED_COUPON =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.TransferReceivedAccountRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalAccountService", "transferYhglobalReceivedCoupon"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.TransferReceivedAccountRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.TransferReceivedAccountRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_TRANSFER_YHGLOBAL_RECEIVED_PREPAID =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.TransferReceivedAccountRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalAccountService", "transferYhglobalReceivedPrepaid"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.TransferReceivedAccountRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest,
      com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse> METHOD_UPDATE_YHGLOBAL_RECEIVED_COUPON_ACCOUNT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalAccountService", "updateYhglobalReceivedCouponAccount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest,
      com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse> METHOD_UPDATE_YHGLOBAL_RECEIVED_PREPAID_ACCOUNT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalAccountService", "updateYhglobalReceivedPrepaidAccount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static YhglobalAccountServiceStub newStub(io.grpc.Channel channel) {
    return new YhglobalAccountServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static YhglobalAccountServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new YhglobalAccountServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static YhglobalAccountServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new YhglobalAccountServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class YhglobalAccountServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *查询越海实收账户余额
     * </pre>
     */
    public void getYhglobalReceivedAccountAmount(com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_YHGLOBAL_RECEIVED_ACCOUNT_AMOUNT, responseObserver);
    }

    /**
     * <pre>
     *查询返利实收账户流水
     * </pre>
     */
    public void selectCouponReceivedRecords(com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.SelectYhglobalFlowsRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_COUPON_RECEIVED_RECORDS, responseObserver);
    }

    /**
     * <pre>
     *查询返利实收账户小计
     * </pre>
     */
    public void getCouponSubtotal(com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalSubtotalRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_COUPON_SUBTOTAL, responseObserver);
    }

    /**
     * <pre>
     *查询代垫实收账户流水
     * </pre>
     */
    public void selectPrepaidReceivedRecords(com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.SelectYhglobalFlowsRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_PREPAID_RECEIVED_RECORDS, responseObserver);
    }

    /**
     * <pre>
     *查询代垫实收账户小计
     * </pre>
     */
    public void getPrepaidSubtotal(com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalSubtotalRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_PREPAID_SUBTOTAL, responseObserver);
    }

    /**
     * <pre>
     * 越海实收返利转出
     * </pre>
     */
    public void transferYhglobalReceivedCoupon(com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.TransferReceivedAccountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_TRANSFER_YHGLOBAL_RECEIVED_COUPON, responseObserver);
    }

    /**
     * <pre>
     * 越海实收代垫转出
     * </pre>
     */
    public void transferYhglobalReceivedPrepaid(com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.TransferReceivedAccountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_TRANSFER_YHGLOBAL_RECEIVED_PREPAID, responseObserver);
    }

    /**
     * <pre>
     *更新返利实收账户
     * </pre>
     */
    public void updateYhglobalReceivedCouponAccount(com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_YHGLOBAL_RECEIVED_COUPON_ACCOUNT, responseObserver);
    }

    /**
     * <pre>
     *更新返利实收账户
     * </pre>
     */
    public void updateYhglobalReceivedPrepaidAccount(com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_YHGLOBAL_RECEIVED_PREPAID_ACCOUNT, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_YHGLOBAL_RECEIVED_ACCOUNT_AMOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest,
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse>(
                  this, METHODID_GET_YHGLOBAL_RECEIVED_ACCOUNT_AMOUNT)))
          .addMethod(
            METHOD_SELECT_COUPON_RECEIVED_RECORDS,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.SelectYhglobalFlowsRequest,
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>(
                  this, METHODID_SELECT_COUPON_RECEIVED_RECORDS)))
          .addMethod(
            METHOD_GET_COUPON_SUBTOTAL,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalSubtotalRequest,
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>(
                  this, METHODID_GET_COUPON_SUBTOTAL)))
          .addMethod(
            METHOD_SELECT_PREPAID_RECEIVED_RECORDS,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.SelectYhglobalFlowsRequest,
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>(
                  this, METHODID_SELECT_PREPAID_RECEIVED_RECORDS)))
          .addMethod(
            METHOD_GET_PREPAID_SUBTOTAL,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalSubtotalRequest,
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>(
                  this, METHODID_GET_PREPAID_SUBTOTAL)))
          .addMethod(
            METHOD_TRANSFER_YHGLOBAL_RECEIVED_COUPON,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.TransferReceivedAccountRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_TRANSFER_YHGLOBAL_RECEIVED_COUPON)))
          .addMethod(
            METHOD_TRANSFER_YHGLOBAL_RECEIVED_PREPAID,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.TransferReceivedAccountRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_TRANSFER_YHGLOBAL_RECEIVED_PREPAID)))
          .addMethod(
            METHOD_UPDATE_YHGLOBAL_RECEIVED_COUPON_ACCOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest,
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse>(
                  this, METHODID_UPDATE_YHGLOBAL_RECEIVED_COUPON_ACCOUNT)))
          .addMethod(
            METHOD_UPDATE_YHGLOBAL_RECEIVED_PREPAID_ACCOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest,
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse>(
                  this, METHODID_UPDATE_YHGLOBAL_RECEIVED_PREPAID_ACCOUNT)))
          .build();
    }
  }

  /**
   */
  public static final class YhglobalAccountServiceStub extends io.grpc.stub.AbstractStub<YhglobalAccountServiceStub> {
    private YhglobalAccountServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private YhglobalAccountServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected YhglobalAccountServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new YhglobalAccountServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *查询越海实收账户余额
     * </pre>
     */
    public void getYhglobalReceivedAccountAmount(com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_YHGLOBAL_RECEIVED_ACCOUNT_AMOUNT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *查询返利实收账户流水
     * </pre>
     */
    public void selectCouponReceivedRecords(com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.SelectYhglobalFlowsRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_COUPON_RECEIVED_RECORDS, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *查询返利实收账户小计
     * </pre>
     */
    public void getCouponSubtotal(com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalSubtotalRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_COUPON_SUBTOTAL, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *查询代垫实收账户流水
     * </pre>
     */
    public void selectPrepaidReceivedRecords(com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.SelectYhglobalFlowsRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_PREPAID_RECEIVED_RECORDS, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *查询代垫实收账户小计
     * </pre>
     */
    public void getPrepaidSubtotal(com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalSubtotalRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_PREPAID_SUBTOTAL, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 越海实收返利转出
     * </pre>
     */
    public void transferYhglobalReceivedCoupon(com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.TransferReceivedAccountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_TRANSFER_YHGLOBAL_RECEIVED_COUPON, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 越海实收代垫转出
     * </pre>
     */
    public void transferYhglobalReceivedPrepaid(com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.TransferReceivedAccountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_TRANSFER_YHGLOBAL_RECEIVED_PREPAID, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *更新返利实收账户
     * </pre>
     */
    public void updateYhglobalReceivedCouponAccount(com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_YHGLOBAL_RECEIVED_COUPON_ACCOUNT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *更新返利实收账户
     * </pre>
     */
    public void updateYhglobalReceivedPrepaidAccount(com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_YHGLOBAL_RECEIVED_PREPAID_ACCOUNT, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class YhglobalAccountServiceBlockingStub extends io.grpc.stub.AbstractStub<YhglobalAccountServiceBlockingStub> {
    private YhglobalAccountServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private YhglobalAccountServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected YhglobalAccountServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new YhglobalAccountServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *查询越海实收账户余额
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse getYhglobalReceivedAccountAmount(com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_YHGLOBAL_RECEIVED_ACCOUNT_AMOUNT, getCallOptions(), request);
    }

    /**
     * <pre>
     *查询返利实收账户流水
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo selectCouponReceivedRecords(com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.SelectYhglobalFlowsRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_COUPON_RECEIVED_RECORDS, getCallOptions(), request);
    }

    /**
     * <pre>
     *查询返利实收账户小计
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal getCouponSubtotal(com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalSubtotalRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_COUPON_SUBTOTAL, getCallOptions(), request);
    }

    /**
     * <pre>
     *查询代垫实收账户流水
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo selectPrepaidReceivedRecords(com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.SelectYhglobalFlowsRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_PREPAID_RECEIVED_RECORDS, getCallOptions(), request);
    }

    /**
     * <pre>
     *查询代垫实收账户小计
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal getPrepaidSubtotal(com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalSubtotalRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_PREPAID_SUBTOTAL, getCallOptions(), request);
    }

    /**
     * <pre>
     * 越海实收返利转出
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult transferYhglobalReceivedCoupon(com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.TransferReceivedAccountRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_TRANSFER_YHGLOBAL_RECEIVED_COUPON, getCallOptions(), request);
    }

    /**
     * <pre>
     * 越海实收代垫转出
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult transferYhglobalReceivedPrepaid(com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.TransferReceivedAccountRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_TRANSFER_YHGLOBAL_RECEIVED_PREPAID, getCallOptions(), request);
    }

    /**
     * <pre>
     *更新返利实收账户
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse updateYhglobalReceivedCouponAccount(com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_YHGLOBAL_RECEIVED_COUPON_ACCOUNT, getCallOptions(), request);
    }

    /**
     * <pre>
     *更新返利实收账户
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse updateYhglobalReceivedPrepaidAccount(com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_YHGLOBAL_RECEIVED_PREPAID_ACCOUNT, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class YhglobalAccountServiceFutureStub extends io.grpc.stub.AbstractStub<YhglobalAccountServiceFutureStub> {
    private YhglobalAccountServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private YhglobalAccountServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected YhglobalAccountServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new YhglobalAccountServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *查询越海实收账户余额
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse> getYhglobalReceivedAccountAmount(
        com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_YHGLOBAL_RECEIVED_ACCOUNT_AMOUNT, getCallOptions()), request);
    }

    /**
     * <pre>
     *查询返利实收账户流水
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> selectCouponReceivedRecords(
        com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.SelectYhglobalFlowsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_COUPON_RECEIVED_RECORDS, getCallOptions()), request);
    }

    /**
     * <pre>
     *查询返利实收账户小计
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> getCouponSubtotal(
        com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalSubtotalRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_COUPON_SUBTOTAL, getCallOptions()), request);
    }

    /**
     * <pre>
     *查询代垫实收账户流水
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> selectPrepaidReceivedRecords(
        com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.SelectYhglobalFlowsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_PREPAID_RECEIVED_RECORDS, getCallOptions()), request);
    }

    /**
     * <pre>
     *查询代垫实收账户小计
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal> getPrepaidSubtotal(
        com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalSubtotalRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_PREPAID_SUBTOTAL, getCallOptions()), request);
    }

    /**
     * <pre>
     * 越海实收返利转出
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> transferYhglobalReceivedCoupon(
        com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.TransferReceivedAccountRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_TRANSFER_YHGLOBAL_RECEIVED_COUPON, getCallOptions()), request);
    }

    /**
     * <pre>
     * 越海实收代垫转出
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> transferYhglobalReceivedPrepaid(
        com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.TransferReceivedAccountRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_TRANSFER_YHGLOBAL_RECEIVED_PREPAID, getCallOptions()), request);
    }

    /**
     * <pre>
     *更新返利实收账户
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse> updateYhglobalReceivedCouponAccount(
        com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_YHGLOBAL_RECEIVED_COUPON_ACCOUNT, getCallOptions()), request);
    }

    /**
     * <pre>
     *更新返利实收账户
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse> updateYhglobalReceivedPrepaidAccount(
        com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_YHGLOBAL_RECEIVED_PREPAID_ACCOUNT, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_YHGLOBAL_RECEIVED_ACCOUNT_AMOUNT = 0;
  private static final int METHODID_SELECT_COUPON_RECEIVED_RECORDS = 1;
  private static final int METHODID_GET_COUPON_SUBTOTAL = 2;
  private static final int METHODID_SELECT_PREPAID_RECEIVED_RECORDS = 3;
  private static final int METHODID_GET_PREPAID_SUBTOTAL = 4;
  private static final int METHODID_TRANSFER_YHGLOBAL_RECEIVED_COUPON = 5;
  private static final int METHODID_TRANSFER_YHGLOBAL_RECEIVED_PREPAID = 6;
  private static final int METHODID_UPDATE_YHGLOBAL_RECEIVED_COUPON_ACCOUNT = 7;
  private static final int METHODID_UPDATE_YHGLOBAL_RECEIVED_PREPAID_ACCOUNT = 8;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final YhglobalAccountServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(YhglobalAccountServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_YHGLOBAL_RECEIVED_ACCOUNT_AMOUNT:
          serviceImpl.getYhglobalReceivedAccountAmount((com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse>) responseObserver);
          break;
        case METHODID_SELECT_COUPON_RECEIVED_RECORDS:
          serviceImpl.selectCouponReceivedRecords((com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.SelectYhglobalFlowsRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>) responseObserver);
          break;
        case METHODID_GET_COUPON_SUBTOTAL:
          serviceImpl.getCouponSubtotal((com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalSubtotalRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>) responseObserver);
          break;
        case METHODID_SELECT_PREPAID_RECEIVED_RECORDS:
          serviceImpl.selectPrepaidReceivedRecords((com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.SelectYhglobalFlowsRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>) responseObserver);
          break;
        case METHODID_GET_PREPAID_SUBTOTAL:
          serviceImpl.getPrepaidSubtotal((com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.GetYhglobalSubtotalRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal>) responseObserver);
          break;
        case METHODID_TRANSFER_YHGLOBAL_RECEIVED_COUPON:
          serviceImpl.transferYhglobalReceivedCoupon((com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.TransferReceivedAccountRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_TRANSFER_YHGLOBAL_RECEIVED_PREPAID:
          serviceImpl.transferYhglobalReceivedPrepaid((com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.TransferReceivedAccountRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_UPDATE_YHGLOBAL_RECEIVED_COUPON_ACCOUNT:
          serviceImpl.updateYhglobalReceivedCouponAccount((com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse>) responseObserver);
          break;
        case METHODID_UPDATE_YHGLOBAL_RECEIVED_PREPAID_ACCOUNT:
          serviceImpl.updateYhglobalReceivedPrepaidAccount((com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.WriteOffResponse>) responseObserver);
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

  private static final class YhglobalAccountServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (YhglobalAccountServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new YhglobalAccountServiceDescriptorSupplier())
              .addMethod(METHOD_GET_YHGLOBAL_RECEIVED_ACCOUNT_AMOUNT)
              .addMethod(METHOD_SELECT_COUPON_RECEIVED_RECORDS)
              .addMethod(METHOD_GET_COUPON_SUBTOTAL)
              .addMethod(METHOD_SELECT_PREPAID_RECEIVED_RECORDS)
              .addMethod(METHOD_GET_PREPAID_SUBTOTAL)
              .addMethod(METHOD_TRANSFER_YHGLOBAL_RECEIVED_COUPON)
              .addMethod(METHOD_TRANSFER_YHGLOBAL_RECEIVED_PREPAID)
              .addMethod(METHOD_UPDATE_YHGLOBAL_RECEIVED_COUPON_ACCOUNT)
              .addMethod(METHOD_UPDATE_YHGLOBAL_RECEIVED_PREPAID_ACCOUNT)
              .build();
        }
      }
    }
    return result;
  }
}
