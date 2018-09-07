package com.yhglobal.gongxiao.inventory.insertinventory.microservice;

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
    comments = "Source: insertInventory.proto")
public final class InsertInventoryServiceGrpc {

  private InsertInventoryServiceGrpc() {}

  public static final String SERVICE_NAME = "InsertInventoryService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetInsertInventoryRequest,
      com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetInsertInventoryResponse> METHOD_INSERT_INVENTORY =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetInsertInventoryRequest, com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetInsertInventoryResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "InsertInventoryService", "insertInventory"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetInsertInventoryRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetInsertInventoryResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetCheckEasInventoryRequest,
      com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetCheckEasInventoryResponse> METHOD_CHECK_EAS_INVENTORY =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetCheckEasInventoryRequest, com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetCheckEasInventoryResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "InsertInventoryService", "checkEasInventory"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetCheckEasInventoryRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetCheckEasInventoryResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectCheckEasInventoryRequest,
      com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectCheckEasInventoryResponse> METHOD_SELECT_EAS_INVENTORY_CHECK =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectCheckEasInventoryRequest, com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectCheckEasInventoryResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "InsertInventoryService", "selectEasInventoryCheck"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectCheckEasInventoryRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectCheckEasInventoryResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectEasInventoryCheckItemRequest,
      com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectEasInventoryCheckItemResponse> METHOD_SELECT_EAS_INVENTORY_CHECK_ITEM =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectEasInventoryCheckItemRequest, com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectEasInventoryCheckItemResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "InsertInventoryService", "selectEasInventoryCheckItem"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectEasInventoryCheckItemRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectEasInventoryCheckItemResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static InsertInventoryServiceStub newStub(io.grpc.Channel channel) {
    return new InsertInventoryServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static InsertInventoryServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new InsertInventoryServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static InsertInventoryServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new InsertInventoryServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class InsertInventoryServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1 导入EAS货品即时库存
     * </pre>
     */
    public void insertInventory(com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetInsertInventoryRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetInsertInventoryResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_INSERT_INVENTORY, responseObserver);
    }

    /**
     * <pre>
     *2 核对EAS货品库存
     * </pre>
     */
    public void checkEasInventory(com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetCheckEasInventoryRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetCheckEasInventoryResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CHECK_EAS_INVENTORY, responseObserver);
    }

    /**
     * <pre>
     *3 查询核对EAS货品库存
     * </pre>
     */
    public void selectEasInventoryCheck(com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectCheckEasInventoryRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectCheckEasInventoryResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_EAS_INVENTORY_CHECK, responseObserver);
    }

    /**
     * <pre>
     *4 查询核对EAS货品库存
     * </pre>
     */
    public void selectEasInventoryCheckItem(com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectEasInventoryCheckItemRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectEasInventoryCheckItemResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_EAS_INVENTORY_CHECK_ITEM, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_INSERT_INVENTORY,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetInsertInventoryRequest,
                com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetInsertInventoryResponse>(
                  this, METHODID_INSERT_INVENTORY)))
          .addMethod(
            METHOD_CHECK_EAS_INVENTORY,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetCheckEasInventoryRequest,
                com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetCheckEasInventoryResponse>(
                  this, METHODID_CHECK_EAS_INVENTORY)))
          .addMethod(
            METHOD_SELECT_EAS_INVENTORY_CHECK,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectCheckEasInventoryRequest,
                com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectCheckEasInventoryResponse>(
                  this, METHODID_SELECT_EAS_INVENTORY_CHECK)))
          .addMethod(
            METHOD_SELECT_EAS_INVENTORY_CHECK_ITEM,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectEasInventoryCheckItemRequest,
                com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectEasInventoryCheckItemResponse>(
                  this, METHODID_SELECT_EAS_INVENTORY_CHECK_ITEM)))
          .build();
    }
  }

  /**
   */
  public static final class InsertInventoryServiceStub extends io.grpc.stub.AbstractStub<InsertInventoryServiceStub> {
    private InsertInventoryServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InsertInventoryServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InsertInventoryServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InsertInventoryServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 导入EAS货品即时库存
     * </pre>
     */
    public void insertInventory(com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetInsertInventoryRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetInsertInventoryResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_INSERT_INVENTORY, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *2 核对EAS货品库存
     * </pre>
     */
    public void checkEasInventory(com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetCheckEasInventoryRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetCheckEasInventoryResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CHECK_EAS_INVENTORY, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *3 查询核对EAS货品库存
     * </pre>
     */
    public void selectEasInventoryCheck(com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectCheckEasInventoryRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectCheckEasInventoryResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_EAS_INVENTORY_CHECK, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *4 查询核对EAS货品库存
     * </pre>
     */
    public void selectEasInventoryCheckItem(com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectEasInventoryCheckItemRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectEasInventoryCheckItemResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_EAS_INVENTORY_CHECK_ITEM, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class InsertInventoryServiceBlockingStub extends io.grpc.stub.AbstractStub<InsertInventoryServiceBlockingStub> {
    private InsertInventoryServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InsertInventoryServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InsertInventoryServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InsertInventoryServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 导入EAS货品即时库存
     * </pre>
     */
    public com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetInsertInventoryResponse insertInventory(com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetInsertInventoryRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_INSERT_INVENTORY, getCallOptions(), request);
    }

    /**
     * <pre>
     *2 核对EAS货品库存
     * </pre>
     */
    public com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetCheckEasInventoryResponse checkEasInventory(com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetCheckEasInventoryRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CHECK_EAS_INVENTORY, getCallOptions(), request);
    }

    /**
     * <pre>
     *3 查询核对EAS货品库存
     * </pre>
     */
    public com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectCheckEasInventoryResponse selectEasInventoryCheck(com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectCheckEasInventoryRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_EAS_INVENTORY_CHECK, getCallOptions(), request);
    }

    /**
     * <pre>
     *4 查询核对EAS货品库存
     * </pre>
     */
    public com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectEasInventoryCheckItemResponse selectEasInventoryCheckItem(com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectEasInventoryCheckItemRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_EAS_INVENTORY_CHECK_ITEM, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class InsertInventoryServiceFutureStub extends io.grpc.stub.AbstractStub<InsertInventoryServiceFutureStub> {
    private InsertInventoryServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InsertInventoryServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InsertInventoryServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InsertInventoryServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 导入EAS货品即时库存
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetInsertInventoryResponse> insertInventory(
        com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetInsertInventoryRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_INSERT_INVENTORY, getCallOptions()), request);
    }

    /**
     * <pre>
     *2 核对EAS货品库存
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetCheckEasInventoryResponse> checkEasInventory(
        com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetCheckEasInventoryRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CHECK_EAS_INVENTORY, getCallOptions()), request);
    }

    /**
     * <pre>
     *3 查询核对EAS货品库存
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectCheckEasInventoryResponse> selectEasInventoryCheck(
        com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectCheckEasInventoryRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_EAS_INVENTORY_CHECK, getCallOptions()), request);
    }

    /**
     * <pre>
     *4 查询核对EAS货品库存
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectEasInventoryCheckItemResponse> selectEasInventoryCheckItem(
        com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectEasInventoryCheckItemRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_EAS_INVENTORY_CHECK_ITEM, getCallOptions()), request);
    }
  }

  private static final int METHODID_INSERT_INVENTORY = 0;
  private static final int METHODID_CHECK_EAS_INVENTORY = 1;
  private static final int METHODID_SELECT_EAS_INVENTORY_CHECK = 2;
  private static final int METHODID_SELECT_EAS_INVENTORY_CHECK_ITEM = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final InsertInventoryServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(InsertInventoryServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_INSERT_INVENTORY:
          serviceImpl.insertInventory((com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetInsertInventoryRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetInsertInventoryResponse>) responseObserver);
          break;
        case METHODID_CHECK_EAS_INVENTORY:
          serviceImpl.checkEasInventory((com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetCheckEasInventoryRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.GetCheckEasInventoryResponse>) responseObserver);
          break;
        case METHODID_SELECT_EAS_INVENTORY_CHECK:
          serviceImpl.selectEasInventoryCheck((com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectCheckEasInventoryRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectCheckEasInventoryResponse>) responseObserver);
          break;
        case METHODID_SELECT_EAS_INVENTORY_CHECK_ITEM:
          serviceImpl.selectEasInventoryCheckItem((com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectEasInventoryCheckItemRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.SelectEasInventoryCheckItemResponse>) responseObserver);
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

  private static final class InsertInventoryServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (InsertInventoryServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new InsertInventoryServiceDescriptorSupplier())
              .addMethod(METHOD_INSERT_INVENTORY)
              .addMethod(METHOD_CHECK_EAS_INVENTORY)
              .addMethod(METHOD_SELECT_EAS_INVENTORY_CHECK)
              .addMethod(METHOD_SELECT_EAS_INVENTORY_CHECK_ITEM)
              .build();
        }
      }
    }
    return result;
  }
}
