package com.yhglobal.gongxiao.inventory.inventoryquerry.microservice;

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
    comments = "Source: inventoryquerry.proto")
public final class InventoryquerryServiceGrpc {

  private InventoryquerryServiceGrpc() {}

  public static final String SERVICE_NAME = "InventoryquerryService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetProductInventoryByNameRequest,
      com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetProductInventoryByNameResponse> METHOD_GET_PRODUCT_INVENTORY_BY_NAME =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetProductInventoryByNameRequest, com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetProductInventoryByNameResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "InventoryquerryService", "getProductInventoryByName"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetProductInventoryByNameRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetProductInventoryByNameResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetWarehouseInventoryRequest,
      com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetWarehouseInventoryResponse> METHOD_GET_WAREHOUSE_INVENTORY =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetWarehouseInventoryRequest, com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetWarehouseInventoryResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "InventoryquerryService", "getWarehouseInventory"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetWarehouseInventoryRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetWarehouseInventoryResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetQuantityRequest,
      com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetQuantityResponse> METHOD_SELECT_QUANTITY_BY_PROJECT_AND_PRODUCT_CODE =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetQuantityRequest, com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetQuantityResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "InventoryquerryService", "selectQuantityByProjectAndProductCode"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetQuantityRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetQuantityResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.UpdateRequest,
      com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.UpdateResponse> METHOD_UPDATE_INVENTORY_INFO =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.UpdateRequest, com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.UpdateResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "InventoryquerryService", "updateInventoryInfo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.UpdateRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.UpdateResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetInventoryInfoRequest,
      com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetInventoryInfoResponse> METHOD_SELECT_INVENTORY_INFO =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetInventoryInfoRequest, com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetInventoryInfoResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "InventoryquerryService", "selectInventoryInfo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetInventoryInfoRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetInventoryInfoResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetAllInventoryInfoRequest,
      com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetAllInventoryInfoResponse> METHOD_SELECT_ALL_INVENTORY_INFO =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetAllInventoryInfoRequest, com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetAllInventoryInfoResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "InventoryquerryService", "selectAllInventoryInfo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetAllInventoryInfoRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetAllInventoryInfoResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static InventoryquerryServiceStub newStub(io.grpc.Channel channel) {
    return new InventoryquerryServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static InventoryquerryServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new InventoryquerryServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static InventoryquerryServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new InventoryquerryServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class InventoryquerryServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1 [货品库存]页 根据productName 分页查询 货品库存
     * </pre>
     */
    public void getProductInventoryByName(com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetProductInventoryByNameRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetProductInventoryByNameResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_PRODUCT_INVENTORY_BY_NAME, responseObserver);
    }

    /**
     * <pre>
     *2 [仓库库存] 按商品编码+商品名称+仓库名称条件 分页查询仓库库存
     * </pre>
     */
    public void getWarehouseInventory(com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetWarehouseInventoryRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetWarehouseInventoryResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_WAREHOUSE_INVENTORY, responseObserver);
    }

    /**
     * <pre>
     *3 实时查询 单个商品的库存
     * </pre>
     */
    public void selectQuantityByProjectAndProductCode(com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetQuantityRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetQuantityResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_QUANTITY_BY_PROJECT_AND_PRODUCT_CODE, responseObserver);
    }

    /**
     * <pre>
     *4 更新商品的库存
     * </pre>
     */
    public void updateInventoryInfo(com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.UpdateRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.UpdateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_INVENTORY_INFO, responseObserver);
    }

    /**
     * <pre>
     *5 根据条件查询商品库存信息
     * </pre>
     */
    public void selectInventoryInfo(com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetInventoryInfoRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetInventoryInfoResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_INVENTORY_INFO, responseObserver);
    }

    /**
     * <pre>
     *6 查询所有商品库存信息
     * </pre>
     */
    public void selectAllInventoryInfo(com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetAllInventoryInfoRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetAllInventoryInfoResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_ALL_INVENTORY_INFO, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_PRODUCT_INVENTORY_BY_NAME,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetProductInventoryByNameRequest,
                com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetProductInventoryByNameResponse>(
                  this, METHODID_GET_PRODUCT_INVENTORY_BY_NAME)))
          .addMethod(
            METHOD_GET_WAREHOUSE_INVENTORY,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetWarehouseInventoryRequest,
                com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetWarehouseInventoryResponse>(
                  this, METHODID_GET_WAREHOUSE_INVENTORY)))
          .addMethod(
            METHOD_SELECT_QUANTITY_BY_PROJECT_AND_PRODUCT_CODE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetQuantityRequest,
                com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetQuantityResponse>(
                  this, METHODID_SELECT_QUANTITY_BY_PROJECT_AND_PRODUCT_CODE)))
          .addMethod(
            METHOD_UPDATE_INVENTORY_INFO,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.UpdateRequest,
                com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.UpdateResponse>(
                  this, METHODID_UPDATE_INVENTORY_INFO)))
          .addMethod(
            METHOD_SELECT_INVENTORY_INFO,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetInventoryInfoRequest,
                com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetInventoryInfoResponse>(
                  this, METHODID_SELECT_INVENTORY_INFO)))
          .addMethod(
            METHOD_SELECT_ALL_INVENTORY_INFO,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetAllInventoryInfoRequest,
                com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetAllInventoryInfoResponse>(
                  this, METHODID_SELECT_ALL_INVENTORY_INFO)))
          .build();
    }
  }

  /**
   */
  public static final class InventoryquerryServiceStub extends io.grpc.stub.AbstractStub<InventoryquerryServiceStub> {
    private InventoryquerryServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventoryquerryServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryquerryServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventoryquerryServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 [货品库存]页 根据productName 分页查询 货品库存
     * </pre>
     */
    public void getProductInventoryByName(com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetProductInventoryByNameRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetProductInventoryByNameResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_PRODUCT_INVENTORY_BY_NAME, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *2 [仓库库存] 按商品编码+商品名称+仓库名称条件 分页查询仓库库存
     * </pre>
     */
    public void getWarehouseInventory(com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetWarehouseInventoryRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetWarehouseInventoryResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_WAREHOUSE_INVENTORY, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *3 实时查询 单个商品的库存
     * </pre>
     */
    public void selectQuantityByProjectAndProductCode(com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetQuantityRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetQuantityResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_QUANTITY_BY_PROJECT_AND_PRODUCT_CODE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *4 更新商品的库存
     * </pre>
     */
    public void updateInventoryInfo(com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.UpdateRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.UpdateResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_INVENTORY_INFO, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *5 根据条件查询商品库存信息
     * </pre>
     */
    public void selectInventoryInfo(com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetInventoryInfoRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetInventoryInfoResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_INVENTORY_INFO, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *6 查询所有商品库存信息
     * </pre>
     */
    public void selectAllInventoryInfo(com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetAllInventoryInfoRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetAllInventoryInfoResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_ALL_INVENTORY_INFO, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class InventoryquerryServiceBlockingStub extends io.grpc.stub.AbstractStub<InventoryquerryServiceBlockingStub> {
    private InventoryquerryServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventoryquerryServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryquerryServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventoryquerryServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 [货品库存]页 根据productName 分页查询 货品库存
     * </pre>
     */
    public com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetProductInventoryByNameResponse getProductInventoryByName(com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetProductInventoryByNameRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_PRODUCT_INVENTORY_BY_NAME, getCallOptions(), request);
    }

    /**
     * <pre>
     *2 [仓库库存] 按商品编码+商品名称+仓库名称条件 分页查询仓库库存
     * </pre>
     */
    public com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetWarehouseInventoryResponse getWarehouseInventory(com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetWarehouseInventoryRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_WAREHOUSE_INVENTORY, getCallOptions(), request);
    }

    /**
     * <pre>
     *3 实时查询 单个商品的库存
     * </pre>
     */
    public com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetQuantityResponse selectQuantityByProjectAndProductCode(com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetQuantityRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_QUANTITY_BY_PROJECT_AND_PRODUCT_CODE, getCallOptions(), request);
    }

    /**
     * <pre>
     *4 更新商品的库存
     * </pre>
     */
    public com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.UpdateResponse updateInventoryInfo(com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.UpdateRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_INVENTORY_INFO, getCallOptions(), request);
    }

    /**
     * <pre>
     *5 根据条件查询商品库存信息
     * </pre>
     */
    public com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetInventoryInfoResponse selectInventoryInfo(com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetInventoryInfoRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_INVENTORY_INFO, getCallOptions(), request);
    }

    /**
     * <pre>
     *6 查询所有商品库存信息
     * </pre>
     */
    public com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetAllInventoryInfoResponse selectAllInventoryInfo(com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetAllInventoryInfoRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_ALL_INVENTORY_INFO, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class InventoryquerryServiceFutureStub extends io.grpc.stub.AbstractStub<InventoryquerryServiceFutureStub> {
    private InventoryquerryServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventoryquerryServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryquerryServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventoryquerryServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 [货品库存]页 根据productName 分页查询 货品库存
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetProductInventoryByNameResponse> getProductInventoryByName(
        com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetProductInventoryByNameRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_PRODUCT_INVENTORY_BY_NAME, getCallOptions()), request);
    }

    /**
     * <pre>
     *2 [仓库库存] 按商品编码+商品名称+仓库名称条件 分页查询仓库库存
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetWarehouseInventoryResponse> getWarehouseInventory(
        com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetWarehouseInventoryRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_WAREHOUSE_INVENTORY, getCallOptions()), request);
    }

    /**
     * <pre>
     *3 实时查询 单个商品的库存
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetQuantityResponse> selectQuantityByProjectAndProductCode(
        com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetQuantityRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_QUANTITY_BY_PROJECT_AND_PRODUCT_CODE, getCallOptions()), request);
    }

    /**
     * <pre>
     *4 更新商品的库存
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.UpdateResponse> updateInventoryInfo(
        com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.UpdateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_INVENTORY_INFO, getCallOptions()), request);
    }

    /**
     * <pre>
     *5 根据条件查询商品库存信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetInventoryInfoResponse> selectInventoryInfo(
        com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetInventoryInfoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_INVENTORY_INFO, getCallOptions()), request);
    }

    /**
     * <pre>
     *6 查询所有商品库存信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetAllInventoryInfoResponse> selectAllInventoryInfo(
        com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetAllInventoryInfoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_ALL_INVENTORY_INFO, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_PRODUCT_INVENTORY_BY_NAME = 0;
  private static final int METHODID_GET_WAREHOUSE_INVENTORY = 1;
  private static final int METHODID_SELECT_QUANTITY_BY_PROJECT_AND_PRODUCT_CODE = 2;
  private static final int METHODID_UPDATE_INVENTORY_INFO = 3;
  private static final int METHODID_SELECT_INVENTORY_INFO = 4;
  private static final int METHODID_SELECT_ALL_INVENTORY_INFO = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final InventoryquerryServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(InventoryquerryServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_PRODUCT_INVENTORY_BY_NAME:
          serviceImpl.getProductInventoryByName((com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetProductInventoryByNameRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetProductInventoryByNameResponse>) responseObserver);
          break;
        case METHODID_GET_WAREHOUSE_INVENTORY:
          serviceImpl.getWarehouseInventory((com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetWarehouseInventoryRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetWarehouseInventoryResponse>) responseObserver);
          break;
        case METHODID_SELECT_QUANTITY_BY_PROJECT_AND_PRODUCT_CODE:
          serviceImpl.selectQuantityByProjectAndProductCode((com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetQuantityRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetQuantityResponse>) responseObserver);
          break;
        case METHODID_UPDATE_INVENTORY_INFO:
          serviceImpl.updateInventoryInfo((com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.UpdateRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.UpdateResponse>) responseObserver);
          break;
        case METHODID_SELECT_INVENTORY_INFO:
          serviceImpl.selectInventoryInfo((com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetInventoryInfoRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetInventoryInfoResponse>) responseObserver);
          break;
        case METHODID_SELECT_ALL_INVENTORY_INFO:
          serviceImpl.selectAllInventoryInfo((com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetAllInventoryInfoRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.GetAllInventoryInfoResponse>) responseObserver);
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

  private static final class InventoryquerryServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (InventoryquerryServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new InventoryquerryServiceDescriptorSupplier())
              .addMethod(METHOD_GET_PRODUCT_INVENTORY_BY_NAME)
              .addMethod(METHOD_GET_WAREHOUSE_INVENTORY)
              .addMethod(METHOD_SELECT_QUANTITY_BY_PROJECT_AND_PRODUCT_CODE)
              .addMethod(METHOD_UPDATE_INVENTORY_INFO)
              .addMethod(METHOD_SELECT_INVENTORY_INFO)
              .addMethod(METHOD_SELECT_ALL_INVENTORY_INFO)
              .build();
        }
      }
    }
    return result;
  }
}
