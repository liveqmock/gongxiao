package com.yhglobal.gongxiao.foundation.supplier.microservice;

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
    comments = "Source: Supplier/Supplier.proto")
public final class SupplierServiceGrpc {

  private SupplierServiceGrpc() {}

  public static final String SERVICE_NAME = "SupplierService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.SelectAllReq,
      com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.SelectAllResp> METHOD_SELECT_ALL =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.SelectAllReq, com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.SelectAllResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierService", "selectAll"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.SelectAllReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.SelectAllResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByCodeReq,
      com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByCodeResp> METHOD_GET_SUPPLIER_BY_CODE =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByCodeReq, com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByCodeResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierService", "getSupplierByCode"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByCodeReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByCodeResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByIdReq,
      com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByIdResp> METHOD_GET_SUPPLIER_BY_ID =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByIdReq, com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByIdResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierService", "getSupplierById"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByIdReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByIdResp.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SupplierServiceStub newStub(io.grpc.Channel channel) {
    return new SupplierServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SupplierServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SupplierServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SupplierServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SupplierServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SupplierServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1 获取供应商列表
     * </pre>
     */
    public void selectAll(com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.SelectAllReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.SelectAllResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_ALL, responseObserver);
    }

    /**
     * <pre>
     *2 一个项目应该只有一个供应商,一个供应商有多个项目,通过项目可以拿到唯一的供应商
     * </pre>
     */
    public void getSupplierByCode(com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByCodeReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByCodeResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_SUPPLIER_BY_CODE, responseObserver);
    }

    /**
     * <pre>
     *3 通过供应商ID获取供应商信息
     * </pre>
     */
    public void getSupplierById(com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByIdResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_SUPPLIER_BY_ID, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_SELECT_ALL,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.SelectAllReq,
                com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.SelectAllResp>(
                  this, METHODID_SELECT_ALL)))
          .addMethod(
            METHOD_GET_SUPPLIER_BY_CODE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByCodeReq,
                com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByCodeResp>(
                  this, METHODID_GET_SUPPLIER_BY_CODE)))
          .addMethod(
            METHOD_GET_SUPPLIER_BY_ID,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByIdReq,
                com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByIdResp>(
                  this, METHODID_GET_SUPPLIER_BY_ID)))
          .build();
    }
  }

  /**
   */
  public static final class SupplierServiceStub extends io.grpc.stub.AbstractStub<SupplierServiceStub> {
    private SupplierServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SupplierServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SupplierServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SupplierServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 获取供应商列表
     * </pre>
     */
    public void selectAll(com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.SelectAllReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.SelectAllResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_ALL, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *2 一个项目应该只有一个供应商,一个供应商有多个项目,通过项目可以拿到唯一的供应商
     * </pre>
     */
    public void getSupplierByCode(com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByCodeReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByCodeResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_SUPPLIER_BY_CODE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *3 通过供应商ID获取供应商信息
     * </pre>
     */
    public void getSupplierById(com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByIdResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_SUPPLIER_BY_ID, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SupplierServiceBlockingStub extends io.grpc.stub.AbstractStub<SupplierServiceBlockingStub> {
    private SupplierServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SupplierServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SupplierServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SupplierServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 获取供应商列表
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.SelectAllResp selectAll(com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.SelectAllReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_ALL, getCallOptions(), request);
    }

    /**
     * <pre>
     *2 一个项目应该只有一个供应商,一个供应商有多个项目,通过项目可以拿到唯一的供应商
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByCodeResp getSupplierByCode(com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByCodeReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_SUPPLIER_BY_CODE, getCallOptions(), request);
    }

    /**
     * <pre>
     *3 通过供应商ID获取供应商信息
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByIdResp getSupplierById(com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByIdReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_SUPPLIER_BY_ID, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SupplierServiceFutureStub extends io.grpc.stub.AbstractStub<SupplierServiceFutureStub> {
    private SupplierServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SupplierServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SupplierServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SupplierServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 获取供应商列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.SelectAllResp> selectAll(
        com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.SelectAllReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_ALL, getCallOptions()), request);
    }

    /**
     * <pre>
     *2 一个项目应该只有一个供应商,一个供应商有多个项目,通过项目可以拿到唯一的供应商
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByCodeResp> getSupplierByCode(
        com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByCodeReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_SUPPLIER_BY_CODE, getCallOptions()), request);
    }

    /**
     * <pre>
     *3 通过供应商ID获取供应商信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByIdResp> getSupplierById(
        com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByIdReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_SUPPLIER_BY_ID, getCallOptions()), request);
    }
  }

  private static final int METHODID_SELECT_ALL = 0;
  private static final int METHODID_GET_SUPPLIER_BY_CODE = 1;
  private static final int METHODID_GET_SUPPLIER_BY_ID = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SupplierServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SupplierServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SELECT_ALL:
          serviceImpl.selectAll((com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.SelectAllReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.SelectAllResp>) responseObserver);
          break;
        case METHODID_GET_SUPPLIER_BY_CODE:
          serviceImpl.getSupplierByCode((com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByCodeReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByCodeResp>) responseObserver);
          break;
        case METHODID_GET_SUPPLIER_BY_ID:
          serviceImpl.getSupplierById((com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByIdReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.GetSupplierByIdResp>) responseObserver);
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

  private static final class SupplierServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SupplierServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SupplierServiceDescriptorSupplier())
              .addMethod(METHOD_SELECT_ALL)
              .addMethod(METHOD_GET_SUPPLIER_BY_CODE)
              .addMethod(METHOD_GET_SUPPLIER_BY_ID)
              .build();
        }
      }
    }
    return result;
  }
}
