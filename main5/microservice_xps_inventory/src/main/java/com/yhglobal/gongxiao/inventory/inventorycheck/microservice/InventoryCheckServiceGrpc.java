package com.yhglobal.gongxiao.inventory.inventorycheck.microservice;

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
    comments = "Source: inventorycheck.proto")
public final class InventoryCheckServiceGrpc {

  private InventoryCheckServiceGrpc() {}

  public static final String SERVICE_NAME = "InventoryCheckService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.inventory.inventorycheck.microservice.InventorycheckStructure.GetInventoryCheckRequest,
      com.yhglobal.gongxiao.inventory.inventorycheck.microservice.InventorycheckStructure.InventoryCheckPageInfo> METHOD_GET_INVENTORY_CHECK =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.inventory.inventorycheck.microservice.InventorycheckStructure.GetInventoryCheckRequest, com.yhglobal.gongxiao.inventory.inventorycheck.microservice.InventorycheckStructure.InventoryCheckPageInfo>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "InventoryCheckService", "getInventoryCheck"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventorycheck.microservice.InventorycheckStructure.GetInventoryCheckRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventorycheck.microservice.InventorycheckStructure.InventoryCheckPageInfo.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static InventoryCheckServiceStub newStub(io.grpc.Channel channel) {
    return new InventoryCheckServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static InventoryCheckServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new InventoryCheckServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static InventoryCheckServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new InventoryCheckServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class InventoryCheckServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1 查询库存核对的结果
     * </pre>
     */
    public void getInventoryCheck(com.yhglobal.gongxiao.inventory.inventorycheck.microservice.InventorycheckStructure.GetInventoryCheckRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventorycheck.microservice.InventorycheckStructure.InventoryCheckPageInfo> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_INVENTORY_CHECK, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_INVENTORY_CHECK,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.inventory.inventorycheck.microservice.InventorycheckStructure.GetInventoryCheckRequest,
                com.yhglobal.gongxiao.inventory.inventorycheck.microservice.InventorycheckStructure.InventoryCheckPageInfo>(
                  this, METHODID_GET_INVENTORY_CHECK)))
          .build();
    }
  }

  /**
   */
  public static final class InventoryCheckServiceStub extends io.grpc.stub.AbstractStub<InventoryCheckServiceStub> {
    private InventoryCheckServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventoryCheckServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryCheckServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventoryCheckServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 查询库存核对的结果
     * </pre>
     */
    public void getInventoryCheck(com.yhglobal.gongxiao.inventory.inventorycheck.microservice.InventorycheckStructure.GetInventoryCheckRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventorycheck.microservice.InventorycheckStructure.InventoryCheckPageInfo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_INVENTORY_CHECK, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class InventoryCheckServiceBlockingStub extends io.grpc.stub.AbstractStub<InventoryCheckServiceBlockingStub> {
    private InventoryCheckServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventoryCheckServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryCheckServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventoryCheckServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 查询库存核对的结果
     * </pre>
     */
    public com.yhglobal.gongxiao.inventory.inventorycheck.microservice.InventorycheckStructure.InventoryCheckPageInfo getInventoryCheck(com.yhglobal.gongxiao.inventory.inventorycheck.microservice.InventorycheckStructure.GetInventoryCheckRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_INVENTORY_CHECK, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class InventoryCheckServiceFutureStub extends io.grpc.stub.AbstractStub<InventoryCheckServiceFutureStub> {
    private InventoryCheckServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventoryCheckServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryCheckServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventoryCheckServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 查询库存核对的结果
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.inventory.inventorycheck.microservice.InventorycheckStructure.InventoryCheckPageInfo> getInventoryCheck(
        com.yhglobal.gongxiao.inventory.inventorycheck.microservice.InventorycheckStructure.GetInventoryCheckRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_INVENTORY_CHECK, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_INVENTORY_CHECK = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final InventoryCheckServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(InventoryCheckServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_INVENTORY_CHECK:
          serviceImpl.getInventoryCheck((com.yhglobal.gongxiao.inventory.inventorycheck.microservice.InventorycheckStructure.GetInventoryCheckRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventorycheck.microservice.InventorycheckStructure.InventoryCheckPageInfo>) responseObserver);
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

  private static final class InventoryCheckServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.inventory.inventorycheck.microservice.InventorycheckStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (InventoryCheckServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new InventoryCheckServiceDescriptorSupplier())
              .addMethod(METHOD_GET_INVENTORY_CHECK)
              .build();
        }
      }
    }
    return result;
  }
}
