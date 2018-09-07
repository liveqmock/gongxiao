package com.yhglobal.gongxiao.foundation.warehouse.microservice;

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
    comments = "Source: Warehouse/Warehouse.proto")
public final class WarehouseServiceGrpc {

  private WarehouseServiceGrpc() {}

  public static final String SERVICE_NAME = "WarehouseService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.GetWarehouseByIdReq,
      com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.GetWarehouseByIdResp> METHOD_GET_WAREHOUSE_BY_ID =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.GetWarehouseByIdReq, com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.GetWarehouseByIdResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "WarehouseService", "getWarehouseById"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.GetWarehouseByIdReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.GetWarehouseByIdResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectAllWarehouseReq,
      com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectAllWarehouseResp> METHOD_SELECT_ALL_WAREHOUSE =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectAllWarehouseReq, com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectAllWarehouseResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "WarehouseService", "SelectAllWarehouse"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectAllWarehouseReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectAllWarehouseResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectWarehouseConditionReq,
      com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectWarehouseConditionResp> METHOD_SELECT_WAREHOUSE_CONDITION =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectWarehouseConditionReq, com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectWarehouseConditionResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "WarehouseService", "SelectWarehouseCondition"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectWarehouseConditionReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectWarehouseConditionResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.InsertWarehouseInfoReq,
      com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.InsertWarehouseInfoResp> METHOD_INSERT_WAREHOUSE_INFO =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.InsertWarehouseInfoReq, com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.InsertWarehouseInfoResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "WarehouseService", "insertWarehouseInfo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.InsertWarehouseInfoReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.InsertWarehouseInfoResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.EditWarehouseInfoReq,
      com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.EditWarehouseInfoResp> METHOD_EDIT_WAREHOUSE_INFO =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.EditWarehouseInfoReq, com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.EditWarehouseInfoResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "WarehouseService", "editWarehouseInfo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.EditWarehouseInfoReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.EditWarehouseInfoResp.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static WarehouseServiceStub newStub(io.grpc.Channel channel) {
    return new WarehouseServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static WarehouseServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new WarehouseServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static WarehouseServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new WarehouseServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class WarehouseServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1 通过仓库ID获取仓库信息
     * </pre>
     */
    public void getWarehouseById(com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.GetWarehouseByIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.GetWarehouseByIdResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_WAREHOUSE_BY_ID, responseObserver);
    }

    /**
     * <pre>
     *2 获取所有仓库信息
     * </pre>
     */
    public void selectAllWarehouse(com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectAllWarehouseReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectAllWarehouseResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_ALL_WAREHOUSE, responseObserver);
    }

    /**
     * <pre>
     *3 条件查询获取仓库列表
     * </pre>
     */
    public void selectWarehouseCondition(com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectWarehouseConditionReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectWarehouseConditionResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_WAREHOUSE_CONDITION, responseObserver);
    }

    /**
     * <pre>
     *4 插入新的仓库信息
     * </pre>
     */
    public void insertWarehouseInfo(com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.InsertWarehouseInfoReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.InsertWarehouseInfoResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_INSERT_WAREHOUSE_INFO, responseObserver);
    }

    /**
     * <pre>
     *5. 编辑接口
     * </pre>
     */
    public void editWarehouseInfo(com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.EditWarehouseInfoReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.EditWarehouseInfoResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_EDIT_WAREHOUSE_INFO, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_WAREHOUSE_BY_ID,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.GetWarehouseByIdReq,
                com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.GetWarehouseByIdResp>(
                  this, METHODID_GET_WAREHOUSE_BY_ID)))
          .addMethod(
            METHOD_SELECT_ALL_WAREHOUSE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectAllWarehouseReq,
                com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectAllWarehouseResp>(
                  this, METHODID_SELECT_ALL_WAREHOUSE)))
          .addMethod(
            METHOD_SELECT_WAREHOUSE_CONDITION,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectWarehouseConditionReq,
                com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectWarehouseConditionResp>(
                  this, METHODID_SELECT_WAREHOUSE_CONDITION)))
          .addMethod(
            METHOD_INSERT_WAREHOUSE_INFO,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.InsertWarehouseInfoReq,
                com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.InsertWarehouseInfoResp>(
                  this, METHODID_INSERT_WAREHOUSE_INFO)))
          .addMethod(
            METHOD_EDIT_WAREHOUSE_INFO,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.EditWarehouseInfoReq,
                com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.EditWarehouseInfoResp>(
                  this, METHODID_EDIT_WAREHOUSE_INFO)))
          .build();
    }
  }

  /**
   */
  public static final class WarehouseServiceStub extends io.grpc.stub.AbstractStub<WarehouseServiceStub> {
    private WarehouseServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WarehouseServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WarehouseServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WarehouseServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 通过仓库ID获取仓库信息
     * </pre>
     */
    public void getWarehouseById(com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.GetWarehouseByIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.GetWarehouseByIdResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_WAREHOUSE_BY_ID, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *2 获取所有仓库信息
     * </pre>
     */
    public void selectAllWarehouse(com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectAllWarehouseReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectAllWarehouseResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_ALL_WAREHOUSE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *3 条件查询获取仓库列表
     * </pre>
     */
    public void selectWarehouseCondition(com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectWarehouseConditionReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectWarehouseConditionResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_WAREHOUSE_CONDITION, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *4 插入新的仓库信息
     * </pre>
     */
    public void insertWarehouseInfo(com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.InsertWarehouseInfoReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.InsertWarehouseInfoResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_INSERT_WAREHOUSE_INFO, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *5. 编辑接口
     * </pre>
     */
    public void editWarehouseInfo(com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.EditWarehouseInfoReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.EditWarehouseInfoResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_EDIT_WAREHOUSE_INFO, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class WarehouseServiceBlockingStub extends io.grpc.stub.AbstractStub<WarehouseServiceBlockingStub> {
    private WarehouseServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WarehouseServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WarehouseServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WarehouseServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 通过仓库ID获取仓库信息
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.GetWarehouseByIdResp getWarehouseById(com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.GetWarehouseByIdReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_WAREHOUSE_BY_ID, getCallOptions(), request);
    }

    /**
     * <pre>
     *2 获取所有仓库信息
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectAllWarehouseResp selectAllWarehouse(com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectAllWarehouseReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_ALL_WAREHOUSE, getCallOptions(), request);
    }

    /**
     * <pre>
     *3 条件查询获取仓库列表
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectWarehouseConditionResp selectWarehouseCondition(com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectWarehouseConditionReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_WAREHOUSE_CONDITION, getCallOptions(), request);
    }

    /**
     * <pre>
     *4 插入新的仓库信息
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.InsertWarehouseInfoResp insertWarehouseInfo(com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.InsertWarehouseInfoReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_INSERT_WAREHOUSE_INFO, getCallOptions(), request);
    }

    /**
     * <pre>
     *5. 编辑接口
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.EditWarehouseInfoResp editWarehouseInfo(com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.EditWarehouseInfoReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_EDIT_WAREHOUSE_INFO, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class WarehouseServiceFutureStub extends io.grpc.stub.AbstractStub<WarehouseServiceFutureStub> {
    private WarehouseServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WarehouseServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WarehouseServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WarehouseServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 通过仓库ID获取仓库信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.GetWarehouseByIdResp> getWarehouseById(
        com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.GetWarehouseByIdReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_WAREHOUSE_BY_ID, getCallOptions()), request);
    }

    /**
     * <pre>
     *2 获取所有仓库信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectAllWarehouseResp> selectAllWarehouse(
        com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectAllWarehouseReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_ALL_WAREHOUSE, getCallOptions()), request);
    }

    /**
     * <pre>
     *3 条件查询获取仓库列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectWarehouseConditionResp> selectWarehouseCondition(
        com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectWarehouseConditionReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_WAREHOUSE_CONDITION, getCallOptions()), request);
    }

    /**
     * <pre>
     *4 插入新的仓库信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.InsertWarehouseInfoResp> insertWarehouseInfo(
        com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.InsertWarehouseInfoReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_INSERT_WAREHOUSE_INFO, getCallOptions()), request);
    }

    /**
     * <pre>
     *5. 编辑接口
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.EditWarehouseInfoResp> editWarehouseInfo(
        com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.EditWarehouseInfoReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_EDIT_WAREHOUSE_INFO, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_WAREHOUSE_BY_ID = 0;
  private static final int METHODID_SELECT_ALL_WAREHOUSE = 1;
  private static final int METHODID_SELECT_WAREHOUSE_CONDITION = 2;
  private static final int METHODID_INSERT_WAREHOUSE_INFO = 3;
  private static final int METHODID_EDIT_WAREHOUSE_INFO = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final WarehouseServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(WarehouseServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_WAREHOUSE_BY_ID:
          serviceImpl.getWarehouseById((com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.GetWarehouseByIdReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.GetWarehouseByIdResp>) responseObserver);
          break;
        case METHODID_SELECT_ALL_WAREHOUSE:
          serviceImpl.selectAllWarehouse((com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectAllWarehouseReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectAllWarehouseResp>) responseObserver);
          break;
        case METHODID_SELECT_WAREHOUSE_CONDITION:
          serviceImpl.selectWarehouseCondition((com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectWarehouseConditionReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.SelectWarehouseConditionResp>) responseObserver);
          break;
        case METHODID_INSERT_WAREHOUSE_INFO:
          serviceImpl.insertWarehouseInfo((com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.InsertWarehouseInfoReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.InsertWarehouseInfoResp>) responseObserver);
          break;
        case METHODID_EDIT_WAREHOUSE_INFO:
          serviceImpl.editWarehouseInfo((com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.EditWarehouseInfoReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.EditWarehouseInfoResp>) responseObserver);
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

  private static final class WarehouseServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (WarehouseServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new WarehouseServiceDescriptorSupplier())
              .addMethod(METHOD_GET_WAREHOUSE_BY_ID)
              .addMethod(METHOD_SELECT_ALL_WAREHOUSE)
              .addMethod(METHOD_SELECT_WAREHOUSE_CONDITION)
              .addMethod(METHOD_INSERT_WAREHOUSE_INFO)
              .addMethod(METHOD_EDIT_WAREHOUSE_INFO)
              .build();
        }
      }
    }
    return result;
  }
}
