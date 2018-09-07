package com.yhglobal.gongxiao.inventory.inventorybatch.microservice;

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
    comments = "Source: inventorybatch.proto")
public final class InventoryBatchServiceGrpc {

  private InventoryBatchServiceGrpc() {}

  public static final String SERVICE_NAME = "InventoryBatchService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.GetBatchDetailRequest,
      com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.BatchDetailPageInfo> METHOD_GET_BATCH_DETAIL =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.GetBatchDetailRequest, com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.BatchDetailPageInfo>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "InventoryBatchService", "getBatchDetail"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.GetBatchDetailRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.BatchDetailPageInfo.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.GetBatchDetailByWarehouseRequest,
      com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.BatchDetailPageInfo> METHOD_GET_BATCH_DETAIL_BY_WAREHOUSE =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.GetBatchDetailByWarehouseRequest, com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.BatchDetailPageInfo>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "InventoryBatchService", "getBatchDetailByWarehouse"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.GetBatchDetailByWarehouseRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.BatchDetailPageInfo.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static InventoryBatchServiceStub newStub(io.grpc.Channel channel) {
    return new InventoryBatchServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static InventoryBatchServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new InventoryBatchServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static InventoryBatchServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new InventoryBatchServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class InventoryBatchServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *2 返回同一个项目下，同一种商品的，每个批次的记录
     * </pre>
     */
    public void getBatchDetail(com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.GetBatchDetailRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.BatchDetailPageInfo> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_BATCH_DETAIL, responseObserver);
    }

    /**
     * <pre>
     *3 根据所选的仓库和商品查询
     * </pre>
     */
    public void getBatchDetailByWarehouse(com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.GetBatchDetailByWarehouseRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.BatchDetailPageInfo> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_BATCH_DETAIL_BY_WAREHOUSE, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_BATCH_DETAIL,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.GetBatchDetailRequest,
                com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.BatchDetailPageInfo>(
                  this, METHODID_GET_BATCH_DETAIL)))
          .addMethod(
            METHOD_GET_BATCH_DETAIL_BY_WAREHOUSE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.GetBatchDetailByWarehouseRequest,
                com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.BatchDetailPageInfo>(
                  this, METHODID_GET_BATCH_DETAIL_BY_WAREHOUSE)))
          .build();
    }
  }

  /**
   */
  public static final class InventoryBatchServiceStub extends io.grpc.stub.AbstractStub<InventoryBatchServiceStub> {
    private InventoryBatchServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventoryBatchServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryBatchServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventoryBatchServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *2 返回同一个项目下，同一种商品的，每个批次的记录
     * </pre>
     */
    public void getBatchDetail(com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.GetBatchDetailRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.BatchDetailPageInfo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_BATCH_DETAIL, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *3 根据所选的仓库和商品查询
     * </pre>
     */
    public void getBatchDetailByWarehouse(com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.GetBatchDetailByWarehouseRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.BatchDetailPageInfo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_BATCH_DETAIL_BY_WAREHOUSE, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class InventoryBatchServiceBlockingStub extends io.grpc.stub.AbstractStub<InventoryBatchServiceBlockingStub> {
    private InventoryBatchServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventoryBatchServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryBatchServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventoryBatchServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *2 返回同一个项目下，同一种商品的，每个批次的记录
     * </pre>
     */
    public com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.BatchDetailPageInfo getBatchDetail(com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.GetBatchDetailRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_BATCH_DETAIL, getCallOptions(), request);
    }

    /**
     * <pre>
     *3 根据所选的仓库和商品查询
     * </pre>
     */
    public com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.BatchDetailPageInfo getBatchDetailByWarehouse(com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.GetBatchDetailByWarehouseRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_BATCH_DETAIL_BY_WAREHOUSE, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class InventoryBatchServiceFutureStub extends io.grpc.stub.AbstractStub<InventoryBatchServiceFutureStub> {
    private InventoryBatchServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventoryBatchServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryBatchServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventoryBatchServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *2 返回同一个项目下，同一种商品的，每个批次的记录
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.BatchDetailPageInfo> getBatchDetail(
        com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.GetBatchDetailRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_BATCH_DETAIL, getCallOptions()), request);
    }

    /**
     * <pre>
     *3 根据所选的仓库和商品查询
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.BatchDetailPageInfo> getBatchDetailByWarehouse(
        com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.GetBatchDetailByWarehouseRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_BATCH_DETAIL_BY_WAREHOUSE, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_BATCH_DETAIL = 0;
  private static final int METHODID_GET_BATCH_DETAIL_BY_WAREHOUSE = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final InventoryBatchServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(InventoryBatchServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_BATCH_DETAIL:
          serviceImpl.getBatchDetail((com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.GetBatchDetailRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.BatchDetailPageInfo>) responseObserver);
          break;
        case METHODID_GET_BATCH_DETAIL_BY_WAREHOUSE:
          serviceImpl.getBatchDetailByWarehouse((com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.GetBatchDetailByWarehouseRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.BatchDetailPageInfo>) responseObserver);
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

  private static final class InventoryBatchServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (InventoryBatchServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new InventoryBatchServiceDescriptorSupplier())
              .addMethod(METHOD_GET_BATCH_DETAIL)
              .addMethod(METHOD_GET_BATCH_DETAIL_BY_WAREHOUSE)
              .build();
        }
      }
    }
    return result;
  }
}
