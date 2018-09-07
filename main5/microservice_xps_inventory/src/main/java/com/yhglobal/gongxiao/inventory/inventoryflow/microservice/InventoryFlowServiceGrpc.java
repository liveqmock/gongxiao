package com.yhglobal.gongxiao.inventory.inventoryflow.microservice;

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
    comments = "Source: inventoryflow.proto")
public final class InventoryFlowServiceGrpc {

  private InventoryFlowServiceGrpc() {}

  public static final String SERVICE_NAME = "InventoryFlowService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.inventory.inventoryflow.microservice.InventoryflowStructure.GetInventoryFlowRequest,
      com.yhglobal.gongxiao.inventory.inventoryflow.microservice.InventoryflowStructure.GetInventoryFlowResponse> METHOD_GET_INVENTORY_FLOW =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.inventory.inventoryflow.microservice.InventoryflowStructure.GetInventoryFlowRequest, com.yhglobal.gongxiao.inventory.inventoryflow.microservice.InventoryflowStructure.GetInventoryFlowResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "InventoryFlowService", "getInventoryFlow"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventoryflow.microservice.InventoryflowStructure.GetInventoryFlowRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventoryflow.microservice.InventoryflowStructure.GetInventoryFlowResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static InventoryFlowServiceStub newStub(io.grpc.Channel channel) {
    return new InventoryFlowServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static InventoryFlowServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new InventoryFlowServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static InventoryFlowServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new InventoryFlowServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class InventoryFlowServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1 查询库存流水信息
     * </pre>
     */
    public void getInventoryFlow(com.yhglobal.gongxiao.inventory.inventoryflow.microservice.InventoryflowStructure.GetInventoryFlowRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventoryflow.microservice.InventoryflowStructure.GetInventoryFlowResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_INVENTORY_FLOW, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_INVENTORY_FLOW,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.inventory.inventoryflow.microservice.InventoryflowStructure.GetInventoryFlowRequest,
                com.yhglobal.gongxiao.inventory.inventoryflow.microservice.InventoryflowStructure.GetInventoryFlowResponse>(
                  this, METHODID_GET_INVENTORY_FLOW)))
          .build();
    }
  }

  /**
   */
  public static final class InventoryFlowServiceStub extends io.grpc.stub.AbstractStub<InventoryFlowServiceStub> {
    private InventoryFlowServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventoryFlowServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryFlowServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventoryFlowServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 查询库存流水信息
     * </pre>
     */
    public void getInventoryFlow(com.yhglobal.gongxiao.inventory.inventoryflow.microservice.InventoryflowStructure.GetInventoryFlowRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventoryflow.microservice.InventoryflowStructure.GetInventoryFlowResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_INVENTORY_FLOW, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class InventoryFlowServiceBlockingStub extends io.grpc.stub.AbstractStub<InventoryFlowServiceBlockingStub> {
    private InventoryFlowServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventoryFlowServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryFlowServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventoryFlowServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 查询库存流水信息
     * </pre>
     */
    public com.yhglobal.gongxiao.inventory.inventoryflow.microservice.InventoryflowStructure.GetInventoryFlowResponse getInventoryFlow(com.yhglobal.gongxiao.inventory.inventoryflow.microservice.InventoryflowStructure.GetInventoryFlowRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_INVENTORY_FLOW, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class InventoryFlowServiceFutureStub extends io.grpc.stub.AbstractStub<InventoryFlowServiceFutureStub> {
    private InventoryFlowServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventoryFlowServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryFlowServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventoryFlowServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 查询库存流水信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.inventory.inventoryflow.microservice.InventoryflowStructure.GetInventoryFlowResponse> getInventoryFlow(
        com.yhglobal.gongxiao.inventory.inventoryflow.microservice.InventoryflowStructure.GetInventoryFlowRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_INVENTORY_FLOW, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_INVENTORY_FLOW = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final InventoryFlowServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(InventoryFlowServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_INVENTORY_FLOW:
          serviceImpl.getInventoryFlow((com.yhglobal.gongxiao.inventory.inventoryflow.microservice.InventoryflowStructure.GetInventoryFlowRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventoryflow.microservice.InventoryflowStructure.GetInventoryFlowResponse>) responseObserver);
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

  private static final class InventoryFlowServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.inventory.inventoryflow.microservice.InventoryflowStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (InventoryFlowServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new InventoryFlowServiceDescriptorSupplier())
              .addMethod(METHOD_GET_INVENTORY_FLOW)
              .build();
        }
      }
    }
    return result;
  }
}
