package com.yhglobal.gongxiao.inventory.inventorysync.microservice;

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
    comments = "Source: inventorysync.proto")
public final class InventorysyncServiceGrpc {

  private InventorysyncServiceGrpc() {}

  public static final String SERVICE_NAME = "InventorysyncService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncInboundRequest,
      com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncResponse> METHOD_SYNC_INBOUND_INVENTORY =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncInboundRequest, com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "InventorysyncService", "syncInboundInventory"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncInboundRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncOutboundRequest,
      com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncResponse> METHOD_SYNC_OUTBOUND_INVENTORY =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncOutboundRequest, com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "InventorysyncService", "syncOutboundInventory"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncOutboundRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.UpdateInventoryRequest,
      com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.UpdateInventoryResponse> METHOD_UPDATE_INVENTORY_INFO =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.UpdateInventoryRequest, com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.UpdateInventoryResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "InventorysyncService", "updateInventoryInfo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.UpdateInventoryRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.UpdateInventoryResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.ResumeOnsalesQuantiryRequest,
      com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.ResumeOnsalesQuantiryResponse> METHOD_RESUME_ONSALES_QUANTIRY =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.ResumeOnsalesQuantiryRequest, com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.ResumeOnsalesQuantiryResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "InventorysyncService", "resumeOnsalesQuantiry"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.ResumeOnsalesQuantiryRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.ResumeOnsalesQuantiryResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static InventorysyncServiceStub newStub(io.grpc.Channel channel) {
    return new InventorysyncServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static InventorysyncServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new InventorysyncServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static InventorysyncServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new InventorysyncServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class InventorysyncServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1 同步商品入库信息到库存模块
     * </pre>
     */
    public void syncInboundInventory(com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncInboundRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SYNC_INBOUND_INVENTORY, responseObserver);
    }

    /**
     * <pre>
     *2 同步商品出库信息到库存模块
     * </pre>
     */
    public void syncOutboundInventory(com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncOutboundRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SYNC_OUTBOUND_INVENTORY, responseObserver);
    }

    /**
     * <pre>
     *3 更新商品库存信息
     * </pre>
     */
    public void updateInventoryInfo(com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.UpdateInventoryRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.UpdateInventoryResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_INVENTORY_INFO, responseObserver);
    }

    /**
     * <pre>
     *4 取消出库单时 释放销售占用数量
     * </pre>
     */
    public void resumeOnsalesQuantiry(com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.ResumeOnsalesQuantiryRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.ResumeOnsalesQuantiryResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_RESUME_ONSALES_QUANTIRY, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_SYNC_INBOUND_INVENTORY,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncInboundRequest,
                com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncResponse>(
                  this, METHODID_SYNC_INBOUND_INVENTORY)))
          .addMethod(
            METHOD_SYNC_OUTBOUND_INVENTORY,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncOutboundRequest,
                com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncResponse>(
                  this, METHODID_SYNC_OUTBOUND_INVENTORY)))
          .addMethod(
            METHOD_UPDATE_INVENTORY_INFO,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.UpdateInventoryRequest,
                com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.UpdateInventoryResponse>(
                  this, METHODID_UPDATE_INVENTORY_INFO)))
          .addMethod(
            METHOD_RESUME_ONSALES_QUANTIRY,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.ResumeOnsalesQuantiryRequest,
                com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.ResumeOnsalesQuantiryResponse>(
                  this, METHODID_RESUME_ONSALES_QUANTIRY)))
          .build();
    }
  }

  /**
   */
  public static final class InventorysyncServiceStub extends io.grpc.stub.AbstractStub<InventorysyncServiceStub> {
    private InventorysyncServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventorysyncServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventorysyncServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventorysyncServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 同步商品入库信息到库存模块
     * </pre>
     */
    public void syncInboundInventory(com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncInboundRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SYNC_INBOUND_INVENTORY, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *2 同步商品出库信息到库存模块
     * </pre>
     */
    public void syncOutboundInventory(com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncOutboundRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SYNC_OUTBOUND_INVENTORY, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *3 更新商品库存信息
     * </pre>
     */
    public void updateInventoryInfo(com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.UpdateInventoryRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.UpdateInventoryResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_INVENTORY_INFO, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *4 取消出库单时 释放销售占用数量
     * </pre>
     */
    public void resumeOnsalesQuantiry(com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.ResumeOnsalesQuantiryRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.ResumeOnsalesQuantiryResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_RESUME_ONSALES_QUANTIRY, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class InventorysyncServiceBlockingStub extends io.grpc.stub.AbstractStub<InventorysyncServiceBlockingStub> {
    private InventorysyncServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventorysyncServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventorysyncServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventorysyncServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 同步商品入库信息到库存模块
     * </pre>
     */
    public com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncResponse syncInboundInventory(com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncInboundRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SYNC_INBOUND_INVENTORY, getCallOptions(), request);
    }

    /**
     * <pre>
     *2 同步商品出库信息到库存模块
     * </pre>
     */
    public com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncResponse syncOutboundInventory(com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncOutboundRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SYNC_OUTBOUND_INVENTORY, getCallOptions(), request);
    }

    /**
     * <pre>
     *3 更新商品库存信息
     * </pre>
     */
    public com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.UpdateInventoryResponse updateInventoryInfo(com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.UpdateInventoryRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_INVENTORY_INFO, getCallOptions(), request);
    }

    /**
     * <pre>
     *4 取消出库单时 释放销售占用数量
     * </pre>
     */
    public com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.ResumeOnsalesQuantiryResponse resumeOnsalesQuantiry(com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.ResumeOnsalesQuantiryRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_RESUME_ONSALES_QUANTIRY, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class InventorysyncServiceFutureStub extends io.grpc.stub.AbstractStub<InventorysyncServiceFutureStub> {
    private InventorysyncServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventorysyncServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventorysyncServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventorysyncServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 同步商品入库信息到库存模块
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncResponse> syncInboundInventory(
        com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncInboundRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SYNC_INBOUND_INVENTORY, getCallOptions()), request);
    }

    /**
     * <pre>
     *2 同步商品出库信息到库存模块
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncResponse> syncOutboundInventory(
        com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncOutboundRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SYNC_OUTBOUND_INVENTORY, getCallOptions()), request);
    }

    /**
     * <pre>
     *3 更新商品库存信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.UpdateInventoryResponse> updateInventoryInfo(
        com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.UpdateInventoryRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_INVENTORY_INFO, getCallOptions()), request);
    }

    /**
     * <pre>
     *4 取消出库单时 释放销售占用数量
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.ResumeOnsalesQuantiryResponse> resumeOnsalesQuantiry(
        com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.ResumeOnsalesQuantiryRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_RESUME_ONSALES_QUANTIRY, getCallOptions()), request);
    }
  }

  private static final int METHODID_SYNC_INBOUND_INVENTORY = 0;
  private static final int METHODID_SYNC_OUTBOUND_INVENTORY = 1;
  private static final int METHODID_UPDATE_INVENTORY_INFO = 2;
  private static final int METHODID_RESUME_ONSALES_QUANTIRY = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final InventorysyncServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(InventorysyncServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SYNC_INBOUND_INVENTORY:
          serviceImpl.syncInboundInventory((com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncInboundRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncResponse>) responseObserver);
          break;
        case METHODID_SYNC_OUTBOUND_INVENTORY:
          serviceImpl.syncOutboundInventory((com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncOutboundRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.SyncResponse>) responseObserver);
          break;
        case METHODID_UPDATE_INVENTORY_INFO:
          serviceImpl.updateInventoryInfo((com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.UpdateInventoryRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.UpdateInventoryResponse>) responseObserver);
          break;
        case METHODID_RESUME_ONSALES_QUANTIRY:
          serviceImpl.resumeOnsalesQuantiry((com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.ResumeOnsalesQuantiryRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.ResumeOnsalesQuantiryResponse>) responseObserver);
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

  private static final class InventorysyncServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (InventorysyncServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new InventorysyncServiceDescriptorSupplier())
              .addMethod(METHOD_SYNC_INBOUND_INVENTORY)
              .addMethod(METHOD_SYNC_OUTBOUND_INVENTORY)
              .addMethod(METHOD_UPDATE_INVENTORY_INFO)
              .addMethod(METHOD_RESUME_ONSALES_QUANTIRY)
              .build();
        }
      }
    }
    return result;
  }
}
