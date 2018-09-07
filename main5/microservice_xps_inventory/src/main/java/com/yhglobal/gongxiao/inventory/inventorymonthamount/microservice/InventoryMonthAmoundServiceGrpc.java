package com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice;

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
    comments = "Source: inventorymonthamount.proto")
public final class InventoryMonthAmoundServiceGrpc {

  private InventoryMonthAmoundServiceGrpc() {}

  public static final String SERVICE_NAME = "InventoryMonthAmoundService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice.InventoryMonthAmountStructure.GetInventoryMonthAmountRequest,
      com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice.InventoryMonthAmountStructure.GetInventoryMonthAmountRespon> METHOD_GET_INVENTORY_MONTH_AMOUNT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice.InventoryMonthAmountStructure.GetInventoryMonthAmountRequest, com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice.InventoryMonthAmountStructure.GetInventoryMonthAmountRespon>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "InventoryMonthAmoundService", "getInventoryMonthAmount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice.InventoryMonthAmountStructure.GetInventoryMonthAmountRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice.InventoryMonthAmountStructure.GetInventoryMonthAmountRespon.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static InventoryMonthAmoundServiceStub newStub(io.grpc.Channel channel) {
    return new InventoryMonthAmoundServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static InventoryMonthAmoundServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new InventoryMonthAmoundServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static InventoryMonthAmoundServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new InventoryMonthAmoundServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class InventoryMonthAmoundServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1 查询月度库存金额
     * </pre>
     */
    public void getInventoryMonthAmount(com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice.InventoryMonthAmountStructure.GetInventoryMonthAmountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice.InventoryMonthAmountStructure.GetInventoryMonthAmountRespon> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_INVENTORY_MONTH_AMOUNT, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_INVENTORY_MONTH_AMOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice.InventoryMonthAmountStructure.GetInventoryMonthAmountRequest,
                com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice.InventoryMonthAmountStructure.GetInventoryMonthAmountRespon>(
                  this, METHODID_GET_INVENTORY_MONTH_AMOUNT)))
          .build();
    }
  }

  /**
   */
  public static final class InventoryMonthAmoundServiceStub extends io.grpc.stub.AbstractStub<InventoryMonthAmoundServiceStub> {
    private InventoryMonthAmoundServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventoryMonthAmoundServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryMonthAmoundServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventoryMonthAmoundServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 查询月度库存金额
     * </pre>
     */
    public void getInventoryMonthAmount(com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice.InventoryMonthAmountStructure.GetInventoryMonthAmountRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice.InventoryMonthAmountStructure.GetInventoryMonthAmountRespon> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_INVENTORY_MONTH_AMOUNT, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class InventoryMonthAmoundServiceBlockingStub extends io.grpc.stub.AbstractStub<InventoryMonthAmoundServiceBlockingStub> {
    private InventoryMonthAmoundServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventoryMonthAmoundServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryMonthAmoundServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventoryMonthAmoundServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 查询月度库存金额
     * </pre>
     */
    public com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice.InventoryMonthAmountStructure.GetInventoryMonthAmountRespon getInventoryMonthAmount(com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice.InventoryMonthAmountStructure.GetInventoryMonthAmountRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_INVENTORY_MONTH_AMOUNT, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class InventoryMonthAmoundServiceFutureStub extends io.grpc.stub.AbstractStub<InventoryMonthAmoundServiceFutureStub> {
    private InventoryMonthAmoundServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventoryMonthAmoundServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryMonthAmoundServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventoryMonthAmoundServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 查询月度库存金额
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice.InventoryMonthAmountStructure.GetInventoryMonthAmountRespon> getInventoryMonthAmount(
        com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice.InventoryMonthAmountStructure.GetInventoryMonthAmountRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_INVENTORY_MONTH_AMOUNT, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_INVENTORY_MONTH_AMOUNT = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final InventoryMonthAmoundServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(InventoryMonthAmoundServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_INVENTORY_MONTH_AMOUNT:
          serviceImpl.getInventoryMonthAmount((com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice.InventoryMonthAmountStructure.GetInventoryMonthAmountRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice.InventoryMonthAmountStructure.GetInventoryMonthAmountRespon>) responseObserver);
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

  private static final class InventoryMonthAmoundServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice.InventoryMonthAmountStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (InventoryMonthAmoundServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new InventoryMonthAmoundServiceDescriptorSupplier())
              .addMethod(METHOD_GET_INVENTORY_MONTH_AMOUNT)
              .build();
        }
      }
    }
    return result;
  }
}
