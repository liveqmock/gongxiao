package com.yhglobal.gongxiao.foundation.area.microservice;

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
    comments = "Source: area/shippingAddress.proto")
public final class ShippingAddressServiceGrpc {

  private ShippingAddressServiceGrpc() {}

  public static final String SERVICE_NAME = "ShippingAddressService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.GetDefaultShippingAddressReq,
      com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.GetDefaultShippingAddressResp> METHOD_GET_DEFAULT_SHIPPING_ADDRESS =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.GetDefaultShippingAddressReq, com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.GetDefaultShippingAddressResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ShippingAddressService", "getDefaultShippingAddress"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.GetDefaultShippingAddressReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.GetDefaultShippingAddressResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAddressListByStatusReq,
      com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAddressListByStatusResp> METHOD_SELECT_ADDRESS_LIST_BY_STATUS =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAddressListByStatusReq, com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAddressListByStatusResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ShippingAddressService", "selectAddressListByStatus"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAddressListByStatusReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAddressListByStatusResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.AuditAddressReq,
      com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.AuditAddressResp> METHOD_AUDIT_ADDRESS =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.AuditAddressReq, com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.AuditAddressResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ShippingAddressService", "auditAddress"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.AuditAddressReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.AuditAddressResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAuditedAddressByDistributorIdReq,
      com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAuditedAddressByDistributorIResp> METHOD_SELECT_AUDITED_ADDRESS_BY_DISTRIBUTOR_ID =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAuditedAddressByDistributorIdReq, com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAuditedAddressByDistributorIResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ShippingAddressService", "selectAuditedAddressByDistributorId"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAuditedAddressByDistributorIdReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAuditedAddressByDistributorIResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SetDefaultAddressReq,
      com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SetDefaultAddressResp> METHOD_SET_DEFAULT_ADDRESS =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SetDefaultAddressReq, com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SetDefaultAddressResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ShippingAddressService", "setDefaultAddress"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SetDefaultAddressReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SetDefaultAddressResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAllAddressByDistributorIdReq,
      com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAllAddressByDistributorIdResp> METHOD_SELECT_ALL_ADDRESS_BY_DISTRIBUTOR_ID =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAllAddressByDistributorIdReq, com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAllAddressByDistributorIdResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ShippingAddressService", "selectAllAddressByDistributorId"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAllAddressByDistributorIdReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAllAddressByDistributorIdResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.InsertShippingAddressReq,
      com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.InsertShippingAddressResp> METHOD_INSERT_SHIPPING_ADDRESS =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.InsertShippingAddressReq, com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.InsertShippingAddressResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ShippingAddressService", "insertShippingAddress"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.InsertShippingAddressReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.InsertShippingAddressResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.DeleteShippingAddressReq,
      com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.DeleteShippingAddressResp> METHOD_DELETE_SHIPPING_ADDRESS =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.DeleteShippingAddressReq, com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.DeleteShippingAddressResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ShippingAddressService", "deleteShippingAddress"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.DeleteShippingAddressReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.DeleteShippingAddressResp.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ShippingAddressServiceStub newStub(io.grpc.Channel channel) {
    return new ShippingAddressServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ShippingAddressServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ShippingAddressServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ShippingAddressServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ShippingAddressServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class ShippingAddressServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1 获取默认地址
     * </pre>
     */
    public void getDefaultShippingAddress(com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.GetDefaultShippingAddressReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.GetDefaultShippingAddressResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_DEFAULT_SHIPPING_ADDRESS, responseObserver);
    }

    /**
     * <pre>
     *2 通过状态获取地址列表
     * </pre>
     */
    public void selectAddressListByStatus(com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAddressListByStatusReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAddressListByStatusResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_ADDRESS_LIST_BY_STATUS, responseObserver);
    }

    /**
     * <pre>
     *3 审核地址 1 审核通过 2 驳回
     * </pre>
     */
    public void auditAddress(com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.AuditAddressReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.AuditAddressResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_AUDIT_ADDRESS, responseObserver);
    }

    /**
     * <pre>
     *4 通过经销商id获取已审核的地址列表
     * </pre>
     */
    public void selectAuditedAddressByDistributorId(com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAuditedAddressByDistributorIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAuditedAddressByDistributorIResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_AUDITED_ADDRESS_BY_DISTRIBUTOR_ID, responseObserver);
    }

    /**
     * <pre>
     *5 设置默认地址
     * </pre>
     */
    public void setDefaultAddress(com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SetDefaultAddressReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SetDefaultAddressResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SET_DEFAULT_ADDRESS, responseObserver);
    }

    /**
     * <pre>
     *6 通过经销商id获取已审核的地址列表
     * </pre>
     */
    public void selectAllAddressByDistributorId(com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAllAddressByDistributorIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAllAddressByDistributorIdResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_ALL_ADDRESS_BY_DISTRIBUTOR_ID, responseObserver);
    }

    /**
     * <pre>
     *7插入新地址
     * </pre>
     */
    public void insertShippingAddress(com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.InsertShippingAddressReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.InsertShippingAddressResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_INSERT_SHIPPING_ADDRESS, responseObserver);
    }

    /**
     * <pre>
     *8删除新地址
     * </pre>
     */
    public void deleteShippingAddress(com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.DeleteShippingAddressReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.DeleteShippingAddressResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_DELETE_SHIPPING_ADDRESS, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_DEFAULT_SHIPPING_ADDRESS,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.GetDefaultShippingAddressReq,
                com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.GetDefaultShippingAddressResp>(
                  this, METHODID_GET_DEFAULT_SHIPPING_ADDRESS)))
          .addMethod(
            METHOD_SELECT_ADDRESS_LIST_BY_STATUS,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAddressListByStatusReq,
                com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAddressListByStatusResp>(
                  this, METHODID_SELECT_ADDRESS_LIST_BY_STATUS)))
          .addMethod(
            METHOD_AUDIT_ADDRESS,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.AuditAddressReq,
                com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.AuditAddressResp>(
                  this, METHODID_AUDIT_ADDRESS)))
          .addMethod(
            METHOD_SELECT_AUDITED_ADDRESS_BY_DISTRIBUTOR_ID,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAuditedAddressByDistributorIdReq,
                com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAuditedAddressByDistributorIResp>(
                  this, METHODID_SELECT_AUDITED_ADDRESS_BY_DISTRIBUTOR_ID)))
          .addMethod(
            METHOD_SET_DEFAULT_ADDRESS,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SetDefaultAddressReq,
                com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SetDefaultAddressResp>(
                  this, METHODID_SET_DEFAULT_ADDRESS)))
          .addMethod(
            METHOD_SELECT_ALL_ADDRESS_BY_DISTRIBUTOR_ID,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAllAddressByDistributorIdReq,
                com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAllAddressByDistributorIdResp>(
                  this, METHODID_SELECT_ALL_ADDRESS_BY_DISTRIBUTOR_ID)))
          .addMethod(
            METHOD_INSERT_SHIPPING_ADDRESS,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.InsertShippingAddressReq,
                com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.InsertShippingAddressResp>(
                  this, METHODID_INSERT_SHIPPING_ADDRESS)))
          .addMethod(
            METHOD_DELETE_SHIPPING_ADDRESS,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.DeleteShippingAddressReq,
                com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.DeleteShippingAddressResp>(
                  this, METHODID_DELETE_SHIPPING_ADDRESS)))
          .build();
    }
  }

  /**
   */
  public static final class ShippingAddressServiceStub extends io.grpc.stub.AbstractStub<ShippingAddressServiceStub> {
    private ShippingAddressServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ShippingAddressServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ShippingAddressServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ShippingAddressServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 获取默认地址
     * </pre>
     */
    public void getDefaultShippingAddress(com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.GetDefaultShippingAddressReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.GetDefaultShippingAddressResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_DEFAULT_SHIPPING_ADDRESS, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *2 通过状态获取地址列表
     * </pre>
     */
    public void selectAddressListByStatus(com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAddressListByStatusReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAddressListByStatusResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_ADDRESS_LIST_BY_STATUS, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *3 审核地址 1 审核通过 2 驳回
     * </pre>
     */
    public void auditAddress(com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.AuditAddressReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.AuditAddressResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_AUDIT_ADDRESS, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *4 通过经销商id获取已审核的地址列表
     * </pre>
     */
    public void selectAuditedAddressByDistributorId(com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAuditedAddressByDistributorIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAuditedAddressByDistributorIResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_AUDITED_ADDRESS_BY_DISTRIBUTOR_ID, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *5 设置默认地址
     * </pre>
     */
    public void setDefaultAddress(com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SetDefaultAddressReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SetDefaultAddressResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SET_DEFAULT_ADDRESS, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *6 通过经销商id获取已审核的地址列表
     * </pre>
     */
    public void selectAllAddressByDistributorId(com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAllAddressByDistributorIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAllAddressByDistributorIdResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_ALL_ADDRESS_BY_DISTRIBUTOR_ID, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *7插入新地址
     * </pre>
     */
    public void insertShippingAddress(com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.InsertShippingAddressReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.InsertShippingAddressResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_INSERT_SHIPPING_ADDRESS, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *8删除新地址
     * </pre>
     */
    public void deleteShippingAddress(com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.DeleteShippingAddressReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.DeleteShippingAddressResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_DELETE_SHIPPING_ADDRESS, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ShippingAddressServiceBlockingStub extends io.grpc.stub.AbstractStub<ShippingAddressServiceBlockingStub> {
    private ShippingAddressServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ShippingAddressServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ShippingAddressServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ShippingAddressServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 获取默认地址
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.GetDefaultShippingAddressResp getDefaultShippingAddress(com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.GetDefaultShippingAddressReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_DEFAULT_SHIPPING_ADDRESS, getCallOptions(), request);
    }

    /**
     * <pre>
     *2 通过状态获取地址列表
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAddressListByStatusResp selectAddressListByStatus(com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAddressListByStatusReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_ADDRESS_LIST_BY_STATUS, getCallOptions(), request);
    }

    /**
     * <pre>
     *3 审核地址 1 审核通过 2 驳回
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.AuditAddressResp auditAddress(com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.AuditAddressReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_AUDIT_ADDRESS, getCallOptions(), request);
    }

    /**
     * <pre>
     *4 通过经销商id获取已审核的地址列表
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAuditedAddressByDistributorIResp selectAuditedAddressByDistributorId(com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAuditedAddressByDistributorIdReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_AUDITED_ADDRESS_BY_DISTRIBUTOR_ID, getCallOptions(), request);
    }

    /**
     * <pre>
     *5 设置默认地址
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SetDefaultAddressResp setDefaultAddress(com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SetDefaultAddressReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SET_DEFAULT_ADDRESS, getCallOptions(), request);
    }

    /**
     * <pre>
     *6 通过经销商id获取已审核的地址列表
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAllAddressByDistributorIdResp selectAllAddressByDistributorId(com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAllAddressByDistributorIdReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_ALL_ADDRESS_BY_DISTRIBUTOR_ID, getCallOptions(), request);
    }

    /**
     * <pre>
     *7插入新地址
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.InsertShippingAddressResp insertShippingAddress(com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.InsertShippingAddressReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_INSERT_SHIPPING_ADDRESS, getCallOptions(), request);
    }

    /**
     * <pre>
     *8删除新地址
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.DeleteShippingAddressResp deleteShippingAddress(com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.DeleteShippingAddressReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_DELETE_SHIPPING_ADDRESS, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ShippingAddressServiceFutureStub extends io.grpc.stub.AbstractStub<ShippingAddressServiceFutureStub> {
    private ShippingAddressServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ShippingAddressServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ShippingAddressServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ShippingAddressServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 获取默认地址
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.GetDefaultShippingAddressResp> getDefaultShippingAddress(
        com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.GetDefaultShippingAddressReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_DEFAULT_SHIPPING_ADDRESS, getCallOptions()), request);
    }

    /**
     * <pre>
     *2 通过状态获取地址列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAddressListByStatusResp> selectAddressListByStatus(
        com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAddressListByStatusReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_ADDRESS_LIST_BY_STATUS, getCallOptions()), request);
    }

    /**
     * <pre>
     *3 审核地址 1 审核通过 2 驳回
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.AuditAddressResp> auditAddress(
        com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.AuditAddressReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_AUDIT_ADDRESS, getCallOptions()), request);
    }

    /**
     * <pre>
     *4 通过经销商id获取已审核的地址列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAuditedAddressByDistributorIResp> selectAuditedAddressByDistributorId(
        com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAuditedAddressByDistributorIdReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_AUDITED_ADDRESS_BY_DISTRIBUTOR_ID, getCallOptions()), request);
    }

    /**
     * <pre>
     *5 设置默认地址
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SetDefaultAddressResp> setDefaultAddress(
        com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SetDefaultAddressReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SET_DEFAULT_ADDRESS, getCallOptions()), request);
    }

    /**
     * <pre>
     *6 通过经销商id获取已审核的地址列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAllAddressByDistributorIdResp> selectAllAddressByDistributorId(
        com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAllAddressByDistributorIdReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_ALL_ADDRESS_BY_DISTRIBUTOR_ID, getCallOptions()), request);
    }

    /**
     * <pre>
     *7插入新地址
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.InsertShippingAddressResp> insertShippingAddress(
        com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.InsertShippingAddressReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_INSERT_SHIPPING_ADDRESS, getCallOptions()), request);
    }

    /**
     * <pre>
     *8删除新地址
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.DeleteShippingAddressResp> deleteShippingAddress(
        com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.DeleteShippingAddressReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_DELETE_SHIPPING_ADDRESS, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_DEFAULT_SHIPPING_ADDRESS = 0;
  private static final int METHODID_SELECT_ADDRESS_LIST_BY_STATUS = 1;
  private static final int METHODID_AUDIT_ADDRESS = 2;
  private static final int METHODID_SELECT_AUDITED_ADDRESS_BY_DISTRIBUTOR_ID = 3;
  private static final int METHODID_SET_DEFAULT_ADDRESS = 4;
  private static final int METHODID_SELECT_ALL_ADDRESS_BY_DISTRIBUTOR_ID = 5;
  private static final int METHODID_INSERT_SHIPPING_ADDRESS = 6;
  private static final int METHODID_DELETE_SHIPPING_ADDRESS = 7;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ShippingAddressServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ShippingAddressServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_DEFAULT_SHIPPING_ADDRESS:
          serviceImpl.getDefaultShippingAddress((com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.GetDefaultShippingAddressReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.GetDefaultShippingAddressResp>) responseObserver);
          break;
        case METHODID_SELECT_ADDRESS_LIST_BY_STATUS:
          serviceImpl.selectAddressListByStatus((com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAddressListByStatusReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAddressListByStatusResp>) responseObserver);
          break;
        case METHODID_AUDIT_ADDRESS:
          serviceImpl.auditAddress((com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.AuditAddressReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.AuditAddressResp>) responseObserver);
          break;
        case METHODID_SELECT_AUDITED_ADDRESS_BY_DISTRIBUTOR_ID:
          serviceImpl.selectAuditedAddressByDistributorId((com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAuditedAddressByDistributorIdReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAuditedAddressByDistributorIResp>) responseObserver);
          break;
        case METHODID_SET_DEFAULT_ADDRESS:
          serviceImpl.setDefaultAddress((com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SetDefaultAddressReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SetDefaultAddressResp>) responseObserver);
          break;
        case METHODID_SELECT_ALL_ADDRESS_BY_DISTRIBUTOR_ID:
          serviceImpl.selectAllAddressByDistributorId((com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAllAddressByDistributorIdReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.SelectAllAddressByDistributorIdResp>) responseObserver);
          break;
        case METHODID_INSERT_SHIPPING_ADDRESS:
          serviceImpl.insertShippingAddress((com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.InsertShippingAddressReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.InsertShippingAddressResp>) responseObserver);
          break;
        case METHODID_DELETE_SHIPPING_ADDRESS:
          serviceImpl.deleteShippingAddress((com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.DeleteShippingAddressReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.DeleteShippingAddressResp>) responseObserver);
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

  private static final class ShippingAddressServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ShippingAddressServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ShippingAddressServiceDescriptorSupplier())
              .addMethod(METHOD_GET_DEFAULT_SHIPPING_ADDRESS)
              .addMethod(METHOD_SELECT_ADDRESS_LIST_BY_STATUS)
              .addMethod(METHOD_AUDIT_ADDRESS)
              .addMethod(METHOD_SELECT_AUDITED_ADDRESS_BY_DISTRIBUTOR_ID)
              .addMethod(METHOD_SET_DEFAULT_ADDRESS)
              .addMethod(METHOD_SELECT_ALL_ADDRESS_BY_DISTRIBUTOR_ID)
              .addMethod(METHOD_INSERT_SHIPPING_ADDRESS)
              .addMethod(METHOD_DELETE_SHIPPING_ADDRESS)
              .build();
        }
      }
    }
    return result;
  }
}
