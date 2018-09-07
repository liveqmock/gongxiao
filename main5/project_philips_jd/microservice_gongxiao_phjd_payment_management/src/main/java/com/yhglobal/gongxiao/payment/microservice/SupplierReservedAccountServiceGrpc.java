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
    comments = "Source: supplierreservedaccount.proto")
public final class SupplierReservedAccountServiceGrpc {

  private SupplierReservedAccountServiceGrpc() {}

  public static final String SERVICE_NAME = "SupplierReservedAccountService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountAmountReq,
      com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountAmountResp> METHOD_GET_RESERVED_ACCOUNT_AMOUNT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountAmountReq, com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountAmountResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierReservedAccountService", "getReservedAccountAmount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountAmountReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountAmountResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountFlowReq,
      com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountFlowResp> METHOD_GET_RESERVED_ACCOUNT_FLOW =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountFlowReq, com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountFlowResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierReservedAccountService", "getReservedAccountFlow"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountFlowReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountFlowResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalReq,
      com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalResp> METHOD_GET_RESERVED_ACCOUNT_SUBTOTAL =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalReq, com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierReservedAccountService", "getReservedAccountSubtotal"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalResp.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SupplierReservedAccountServiceStub newStub(io.grpc.Channel channel) {
    return new SupplierReservedAccountServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SupplierReservedAccountServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SupplierReservedAccountServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SupplierReservedAccountServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SupplierReservedAccountServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SupplierReservedAccountServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 查询单价折扣冻结账户和采购预留冻结账户的余额
     * </pre>
     */
    public void getReservedAccountAmount(com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountAmountReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountAmountResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_RESERVED_ACCOUNT_AMOUNT, responseObserver);
    }

    /**
     * <pre>
     * 分页条件查询流水
     * </pre>
     */
    public void getReservedAccountFlow(com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountFlowReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountFlowResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_RESERVED_ACCOUNT_FLOW, responseObserver);
    }

    /**
     * <pre>
     * 统计流水条数及金额
     * </pre>
     */
    public void getReservedAccountSubtotal(com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_RESERVED_ACCOUNT_SUBTOTAL, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_RESERVED_ACCOUNT_AMOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountAmountReq,
                com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountAmountResp>(
                  this, METHODID_GET_RESERVED_ACCOUNT_AMOUNT)))
          .addMethod(
            METHOD_GET_RESERVED_ACCOUNT_FLOW,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountFlowReq,
                com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountFlowResp>(
                  this, METHODID_GET_RESERVED_ACCOUNT_FLOW)))
          .addMethod(
            METHOD_GET_RESERVED_ACCOUNT_SUBTOTAL,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalReq,
                com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalResp>(
                  this, METHODID_GET_RESERVED_ACCOUNT_SUBTOTAL)))
          .build();
    }
  }

  /**
   */
  public static final class SupplierReservedAccountServiceStub extends io.grpc.stub.AbstractStub<SupplierReservedAccountServiceStub> {
    private SupplierReservedAccountServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SupplierReservedAccountServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SupplierReservedAccountServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SupplierReservedAccountServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 查询单价折扣冻结账户和采购预留冻结账户的余额
     * </pre>
     */
    public void getReservedAccountAmount(com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountAmountReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountAmountResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_RESERVED_ACCOUNT_AMOUNT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 分页条件查询流水
     * </pre>
     */
    public void getReservedAccountFlow(com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountFlowReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountFlowResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_RESERVED_ACCOUNT_FLOW, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 统计流水条数及金额
     * </pre>
     */
    public void getReservedAccountSubtotal(com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_RESERVED_ACCOUNT_SUBTOTAL, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SupplierReservedAccountServiceBlockingStub extends io.grpc.stub.AbstractStub<SupplierReservedAccountServiceBlockingStub> {
    private SupplierReservedAccountServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SupplierReservedAccountServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SupplierReservedAccountServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SupplierReservedAccountServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 查询单价折扣冻结账户和采购预留冻结账户的余额
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountAmountResp getReservedAccountAmount(com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountAmountReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_RESERVED_ACCOUNT_AMOUNT, getCallOptions(), request);
    }

    /**
     * <pre>
     * 分页条件查询流水
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountFlowResp getReservedAccountFlow(com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountFlowReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_RESERVED_ACCOUNT_FLOW, getCallOptions(), request);
    }

    /**
     * <pre>
     * 统计流水条数及金额
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalResp getReservedAccountSubtotal(com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_RESERVED_ACCOUNT_SUBTOTAL, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SupplierReservedAccountServiceFutureStub extends io.grpc.stub.AbstractStub<SupplierReservedAccountServiceFutureStub> {
    private SupplierReservedAccountServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SupplierReservedAccountServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SupplierReservedAccountServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SupplierReservedAccountServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 查询单价折扣冻结账户和采购预留冻结账户的余额
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountAmountResp> getReservedAccountAmount(
        com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountAmountReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_RESERVED_ACCOUNT_AMOUNT, getCallOptions()), request);
    }

    /**
     * <pre>
     * 分页条件查询流水
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountFlowResp> getReservedAccountFlow(
        com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountFlowReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_RESERVED_ACCOUNT_FLOW, getCallOptions()), request);
    }

    /**
     * <pre>
     * 统计流水条数及金额
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalResp> getReservedAccountSubtotal(
        com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_RESERVED_ACCOUNT_SUBTOTAL, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_RESERVED_ACCOUNT_AMOUNT = 0;
  private static final int METHODID_GET_RESERVED_ACCOUNT_FLOW = 1;
  private static final int METHODID_GET_RESERVED_ACCOUNT_SUBTOTAL = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SupplierReservedAccountServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SupplierReservedAccountServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_RESERVED_ACCOUNT_AMOUNT:
          serviceImpl.getReservedAccountAmount((com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountAmountReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountAmountResp>) responseObserver);
          break;
        case METHODID_GET_RESERVED_ACCOUNT_FLOW:
          serviceImpl.getReservedAccountFlow((com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountFlowReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountFlowResp>) responseObserver);
          break;
        case METHODID_GET_RESERVED_ACCOUNT_SUBTOTAL:
          serviceImpl.getReservedAccountSubtotal((com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.GetReservedAccountSubtotalResp>) responseObserver);
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

  private static final class SupplierReservedAccountServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.payment.microservice.SupplierReservedAccountServiceStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SupplierReservedAccountServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SupplierReservedAccountServiceDescriptorSupplier())
              .addMethod(METHOD_GET_RESERVED_ACCOUNT_AMOUNT)
              .addMethod(METHOD_GET_RESERVED_ACCOUNT_FLOW)
              .addMethod(METHOD_GET_RESERVED_ACCOUNT_SUBTOTAL)
              .build();
        }
      }
    }
    return result;
  }
}
