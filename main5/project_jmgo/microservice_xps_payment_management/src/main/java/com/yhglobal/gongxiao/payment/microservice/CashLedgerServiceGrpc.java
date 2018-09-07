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
    comments = "Source: payment.cashLedger.proto")
public final class CashLedgerServiceGrpc {

  private CashLedgerServiceGrpc() {}

  public static final String SERVICE_NAME = "CashLedgerService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.SelectCashLedgerListRequest,
      com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.SelectCashLedgerListResponse> METHOD_SELECT_CASH_LEDGER_LIST =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.SelectCashLedgerListRequest, com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.SelectCashLedgerListResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "CashLedgerService", "selectCashLedgerList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.SelectCashLedgerListRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.SelectCashLedgerListResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_CANCEL_CONFIRM =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "CashLedgerService", "cancelConfirm"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_APPROVE_LEDGER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "CashLedgerService", "approveLedger"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_CANCEL_APPROVE_LEDGER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "CashLedgerService", "cancelApproveLedger"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CashLedgerServiceStub newStub(io.grpc.Channel channel) {
    return new CashLedgerServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CashLedgerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new CashLedgerServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CashLedgerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new CashLedgerServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class CashLedgerServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *选择性查询(均为可选条件)
     * </pre>
     */
    public void selectCashLedgerList(com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.SelectCashLedgerListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.SelectCashLedgerListResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_CASH_LEDGER_LIST, responseObserver);
    }

    /**
     * <pre>
     *取消收款确认
     * </pre>
     */
    public void cancelConfirm(com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CANCEL_CONFIRM, responseObserver);
    }

    /**
     * <pre>
     *审批通过
     * </pre>
     */
    public void approveLedger(com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_APPROVE_LEDGER, responseObserver);
    }

    /**
     * <pre>
     *取消审批
     * </pre>
     */
    public void cancelApproveLedger(com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CANCEL_APPROVE_LEDGER, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_SELECT_CASH_LEDGER_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.SelectCashLedgerListRequest,
                com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.SelectCashLedgerListResponse>(
                  this, METHODID_SELECT_CASH_LEDGER_LIST)))
          .addMethod(
            METHOD_CANCEL_CONFIRM,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_CANCEL_CONFIRM)))
          .addMethod(
            METHOD_APPROVE_LEDGER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_APPROVE_LEDGER)))
          .addMethod(
            METHOD_CANCEL_APPROVE_LEDGER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_CANCEL_APPROVE_LEDGER)))
          .build();
    }
  }

  /**
   */
  public static final class CashLedgerServiceStub extends io.grpc.stub.AbstractStub<CashLedgerServiceStub> {
    private CashLedgerServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CashLedgerServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CashLedgerServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CashLedgerServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *选择性查询(均为可选条件)
     * </pre>
     */
    public void selectCashLedgerList(com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.SelectCashLedgerListRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.SelectCashLedgerListResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_CASH_LEDGER_LIST, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *取消收款确认
     * </pre>
     */
    public void cancelConfirm(com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CANCEL_CONFIRM, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *审批通过
     * </pre>
     */
    public void approveLedger(com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_APPROVE_LEDGER, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *取消审批
     * </pre>
     */
    public void cancelApproveLedger(com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CANCEL_APPROVE_LEDGER, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CashLedgerServiceBlockingStub extends io.grpc.stub.AbstractStub<CashLedgerServiceBlockingStub> {
    private CashLedgerServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CashLedgerServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CashLedgerServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CashLedgerServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *选择性查询(均为可选条件)
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.SelectCashLedgerListResponse selectCashLedgerList(com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.SelectCashLedgerListRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_CASH_LEDGER_LIST, getCallOptions(), request);
    }

    /**
     * <pre>
     *取消收款确认
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult cancelConfirm(com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CANCEL_CONFIRM, getCallOptions(), request);
    }

    /**
     * <pre>
     *审批通过
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult approveLedger(com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_APPROVE_LEDGER, getCallOptions(), request);
    }

    /**
     * <pre>
     *取消审批
     * </pre>
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult cancelApproveLedger(com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CANCEL_APPROVE_LEDGER, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CashLedgerServiceFutureStub extends io.grpc.stub.AbstractStub<CashLedgerServiceFutureStub> {
    private CashLedgerServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CashLedgerServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CashLedgerServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CashLedgerServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *选择性查询(均为可选条件)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.SelectCashLedgerListResponse> selectCashLedgerList(
        com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.SelectCashLedgerListRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_CASH_LEDGER_LIST, getCallOptions()), request);
    }

    /**
     * <pre>
     *取消收款确认
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> cancelConfirm(
        com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CANCEL_CONFIRM, getCallOptions()), request);
    }

    /**
     * <pre>
     *审批通过
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> approveLedger(
        com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_APPROVE_LEDGER, getCallOptions()), request);
    }

    /**
     * <pre>
     *取消审批
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> cancelApproveLedger(
        com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CANCEL_APPROVE_LEDGER, getCallOptions()), request);
    }
  }

  private static final int METHODID_SELECT_CASH_LEDGER_LIST = 0;
  private static final int METHODID_CANCEL_CONFIRM = 1;
  private static final int METHODID_APPROVE_LEDGER = 2;
  private static final int METHODID_CANCEL_APPROVE_LEDGER = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CashLedgerServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CashLedgerServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SELECT_CASH_LEDGER_LIST:
          serviceImpl.selectCashLedgerList((com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.SelectCashLedgerListRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.SelectCashLedgerListResponse>) responseObserver);
          break;
        case METHODID_CANCEL_CONFIRM:
          serviceImpl.cancelConfirm((com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_APPROVE_LEDGER:
          serviceImpl.approveLedger((com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_CANCEL_APPROVE_LEDGER:
          serviceImpl.cancelApproveLedger((com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.CashLedgerRequest) request,
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

  private static final class CashLedgerServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CashLedgerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CashLedgerServiceDescriptorSupplier())
              .addMethod(METHOD_SELECT_CASH_LEDGER_LIST)
              .addMethod(METHOD_CANCEL_CONFIRM)
              .addMethod(METHOD_APPROVE_LEDGER)
              .addMethod(METHOD_CANCEL_APPROVE_LEDGER)
              .build();
        }
      }
    }
    return result;
  }
}
