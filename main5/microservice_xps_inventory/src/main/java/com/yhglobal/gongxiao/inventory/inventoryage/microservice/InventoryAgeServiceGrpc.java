package com.yhglobal.gongxiao.inventory.inventoryage.microservice;

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
    comments = "Source: inventoryage.proto")
public final class InventoryAgeServiceGrpc {

  private InventoryAgeServiceGrpc() {}

  public static final String SERVICE_NAME = "InventoryAgeService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeStructure.GetInventoryAgeRequest,
      com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeStructure.InventoryAgePageInfo> METHOD_GET_INVENTORY_AGE =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeStructure.GetInventoryAgeRequest, com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeStructure.InventoryAgePageInfo>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "InventoryAgeService", "getInventoryAge"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeStructure.GetInventoryAgeRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeStructure.InventoryAgePageInfo.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static InventoryAgeServiceStub newStub(io.grpc.Channel channel) {
    return new InventoryAgeServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static InventoryAgeServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new InventoryAgeServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static InventoryAgeServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new InventoryAgeServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class InventoryAgeServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1 多条件查询进销存台账
     * </pre>
     */
    public void getInventoryAge(com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeStructure.GetInventoryAgeRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeStructure.InventoryAgePageInfo> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_INVENTORY_AGE, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_INVENTORY_AGE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeStructure.GetInventoryAgeRequest,
                com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeStructure.InventoryAgePageInfo>(
                  this, METHODID_GET_INVENTORY_AGE)))
          .build();
    }
  }

  /**
   */
  public static final class InventoryAgeServiceStub extends io.grpc.stub.AbstractStub<InventoryAgeServiceStub> {
    private InventoryAgeServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventoryAgeServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryAgeServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventoryAgeServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 多条件查询进销存台账
     * </pre>
     */
    public void getInventoryAge(com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeStructure.GetInventoryAgeRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeStructure.InventoryAgePageInfo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_INVENTORY_AGE, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class InventoryAgeServiceBlockingStub extends io.grpc.stub.AbstractStub<InventoryAgeServiceBlockingStub> {
    private InventoryAgeServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventoryAgeServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryAgeServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventoryAgeServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 多条件查询进销存台账
     * </pre>
     */
    public com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeStructure.InventoryAgePageInfo getInventoryAge(com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeStructure.GetInventoryAgeRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_INVENTORY_AGE, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class InventoryAgeServiceFutureStub extends io.grpc.stub.AbstractStub<InventoryAgeServiceFutureStub> {
    private InventoryAgeServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventoryAgeServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryAgeServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventoryAgeServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 多条件查询进销存台账
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeStructure.InventoryAgePageInfo> getInventoryAge(
        com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeStructure.GetInventoryAgeRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_INVENTORY_AGE, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_INVENTORY_AGE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final InventoryAgeServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(InventoryAgeServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_INVENTORY_AGE:
          serviceImpl.getInventoryAge((com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeStructure.GetInventoryAgeRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeStructure.InventoryAgePageInfo>) responseObserver);
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

  private static final class InventoryAgeServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (InventoryAgeServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new InventoryAgeServiceDescriptorSupplier())
              .addMethod(METHOD_GET_INVENTORY_AGE)
              .build();
        }
      }
    }
    return result;
  }
}
