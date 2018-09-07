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
    comments = "Source: supplierfrozenaccount.proto")
public final class SupplierFrozenAccountServiceGrpc {

  private SupplierFrozenAccountServiceGrpc() {}

  public static final String SERVICE_NAME = "SupplierFrozenAccountService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountAmountReq,
      com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountAmountResp> METHOD_GET_FROZEN_ACCOUNT_AMOUNT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountAmountReq, com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountAmountResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierFrozenAccountService", "getFrozenAccountAmount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountAmountReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountAmountResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountFlowReq,
      com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountFlowResp> METHOD_GET_FROZEN_ACCOUNT_FLOW =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountFlowReq, com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountFlowResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierFrozenAccountService", "getFrozenAccountFlow"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountFlowReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountFlowResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.AccountTransferOutReq,
      com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.AccountTransferOutResp> METHOD_ACCOUNT_TRANSFER_OUT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.AccountTransferOutReq, com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.AccountTransferOutResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierFrozenAccountService", "accountTransferOut"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.AccountTransferOutReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.AccountTransferOutResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountSubtotalReq,
      com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountSubtotalResp> METHOD_GET_FROZEN_ACCOUNT_SUBTOTAL =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountSubtotalReq, com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountSubtotalResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierFrozenAccountService", "getFrozenAccountSubtotal"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountSubtotalReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountSubtotalResp.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SupplierFrozenAccountServiceStub newStub(io.grpc.Channel channel) {
    return new SupplierFrozenAccountServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SupplierFrozenAccountServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SupplierFrozenAccountServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SupplierFrozenAccountServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SupplierFrozenAccountServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SupplierFrozenAccountServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 查询单价折扣冻结账户和采购预留冻结账户的余额
     * </pre>
     */
    public void getFrozenAccountAmount(com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountAmountReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountAmountResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_FROZEN_ACCOUNT_AMOUNT, responseObserver);
    }

    /**
     * <pre>
     * 分页条件查询流水
     * </pre>
     */
    public void getFrozenAccountFlow(com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountFlowReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountFlowResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_FROZEN_ACCOUNT_FLOW, responseObserver);
    }

    /**
     * <pre>
     * 账户额度转出
     * </pre>
     */
    public void accountTransferOut(com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.AccountTransferOutReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.AccountTransferOutResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_ACCOUNT_TRANSFER_OUT, responseObserver);
    }

    /**
     * <pre>
     * 统计流水条数及金额
     * </pre>
     */
    public void getFrozenAccountSubtotal(com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountSubtotalReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountSubtotalResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_FROZEN_ACCOUNT_SUBTOTAL, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_FROZEN_ACCOUNT_AMOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountAmountReq,
                com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountAmountResp>(
                  this, METHODID_GET_FROZEN_ACCOUNT_AMOUNT)))
          .addMethod(
            METHOD_GET_FROZEN_ACCOUNT_FLOW,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountFlowReq,
                com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountFlowResp>(
                  this, METHODID_GET_FROZEN_ACCOUNT_FLOW)))
          .addMethod(
            METHOD_ACCOUNT_TRANSFER_OUT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.AccountTransferOutReq,
                com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.AccountTransferOutResp>(
                  this, METHODID_ACCOUNT_TRANSFER_OUT)))
          .addMethod(
            METHOD_GET_FROZEN_ACCOUNT_SUBTOTAL,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountSubtotalReq,
                com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountSubtotalResp>(
                  this, METHODID_GET_FROZEN_ACCOUNT_SUBTOTAL)))
          .build();
    }
  }

  /**
   */
  public static final class SupplierFrozenAccountServiceStub extends io.grpc.stub.AbstractStub<SupplierFrozenAccountServiceStub> {
    private SupplierFrozenAccountServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SupplierFrozenAccountServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SupplierFrozenAccountServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SupplierFrozenAccountServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 查询单价折扣冻结账户和采购预留冻结账户的余额
     * </pre>
     */
    public void getFrozenAccountAmount(com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountAmountReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountAmountResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_FROZEN_ACCOUNT_AMOUNT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 分页条件查询流水
     * </pre>
     */
    public void getFrozenAccountFlow(com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountFlowReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountFlowResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_FROZEN_ACCOUNT_FLOW, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 账户额度转出
     * </pre>
     */
    public void accountTransferOut(com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.AccountTransferOutReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.AccountTransferOutResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_ACCOUNT_TRANSFER_OUT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 统计流水条数及金额
     * </pre>
     */
    public void getFrozenAccountSubtotal(com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountSubtotalReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountSubtotalResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_FROZEN_ACCOUNT_SUBTOTAL, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SupplierFrozenAccountServiceBlockingStub extends io.grpc.stub.AbstractStub<SupplierFrozenAccountServiceBlockingStub> {
    private SupplierFrozenAccountServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SupplierFrozenAccountServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SupplierFrozenAccountServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SupplierFrozenAccountServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 查询单价折扣冻结账户和采购预留冻结账户的余额
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountAmountResp getFrozenAccountAmount(com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountAmountReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_FROZEN_ACCOUNT_AMOUNT, getCallOptions(), request);
    }

    /**
     * <pre>
     * 分页条件查询流水
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountFlowResp getFrozenAccountFlow(com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountFlowReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_FROZEN_ACCOUNT_FLOW, getCallOptions(), request);
    }

    /**
     * <pre>
     * 账户额度转出
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.AccountTransferOutResp accountTransferOut(com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.AccountTransferOutReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_ACCOUNT_TRANSFER_OUT, getCallOptions(), request);
    }

    /**
     * <pre>
     * 统计流水条数及金额
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountSubtotalResp getFrozenAccountSubtotal(com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountSubtotalReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_FROZEN_ACCOUNT_SUBTOTAL, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SupplierFrozenAccountServiceFutureStub extends io.grpc.stub.AbstractStub<SupplierFrozenAccountServiceFutureStub> {
    private SupplierFrozenAccountServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SupplierFrozenAccountServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SupplierFrozenAccountServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SupplierFrozenAccountServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 查询单价折扣冻结账户和采购预留冻结账户的余额
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountAmountResp> getFrozenAccountAmount(
        com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountAmountReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_FROZEN_ACCOUNT_AMOUNT, getCallOptions()), request);
    }

    /**
     * <pre>
     * 分页条件查询流水
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountFlowResp> getFrozenAccountFlow(
        com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountFlowReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_FROZEN_ACCOUNT_FLOW, getCallOptions()), request);
    }

    /**
     * <pre>
     * 账户额度转出
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.AccountTransferOutResp> accountTransferOut(
        com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.AccountTransferOutReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_ACCOUNT_TRANSFER_OUT, getCallOptions()), request);
    }

    /**
     * <pre>
     * 统计流水条数及金额
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountSubtotalResp> getFrozenAccountSubtotal(
        com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountSubtotalReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_FROZEN_ACCOUNT_SUBTOTAL, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_FROZEN_ACCOUNT_AMOUNT = 0;
  private static final int METHODID_GET_FROZEN_ACCOUNT_FLOW = 1;
  private static final int METHODID_ACCOUNT_TRANSFER_OUT = 2;
  private static final int METHODID_GET_FROZEN_ACCOUNT_SUBTOTAL = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SupplierFrozenAccountServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SupplierFrozenAccountServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_FROZEN_ACCOUNT_AMOUNT:
          serviceImpl.getFrozenAccountAmount((com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountAmountReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountAmountResp>) responseObserver);
          break;
        case METHODID_GET_FROZEN_ACCOUNT_FLOW:
          serviceImpl.getFrozenAccountFlow((com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountFlowReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountFlowResp>) responseObserver);
          break;
        case METHODID_ACCOUNT_TRANSFER_OUT:
          serviceImpl.accountTransferOut((com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.AccountTransferOutReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.AccountTransferOutResp>) responseObserver);
          break;
        case METHODID_GET_FROZEN_ACCOUNT_SUBTOTAL:
          serviceImpl.getFrozenAccountSubtotal((com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountSubtotalReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.GetFrozenAccountSubtotalResp>) responseObserver);
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

  private static final class SupplierFrozenAccountServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.payment.microservice.SupplierFrozenAccountServiceStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SupplierFrozenAccountServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SupplierFrozenAccountServiceDescriptorSupplier())
              .addMethod(METHOD_GET_FROZEN_ACCOUNT_AMOUNT)
              .addMethod(METHOD_GET_FROZEN_ACCOUNT_FLOW)
              .addMethod(METHOD_ACCOUNT_TRANSFER_OUT)
              .addMethod(METHOD_GET_FROZEN_ACCOUNT_SUBTOTAL)
              .build();
        }
      }
    }
    return result;
  }
}
