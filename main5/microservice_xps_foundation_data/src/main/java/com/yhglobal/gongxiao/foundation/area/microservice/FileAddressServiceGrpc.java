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
    comments = "Source: area/fileAddress.proto")
public final class FileAddressServiceGrpc {

  private FileAddressServiceGrpc() {}

  public static final String SERVICE_NAME = "FileAddressService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.InsertFileAddressReq,
      com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.InsertFileAddressResp> METHOD_INSERT_FILE_ADDRESS =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.InsertFileAddressReq, com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.InsertFileAddressResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "FileAddressService", "insertFileAddress"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.InsertFileAddressReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.InsertFileAddressResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.GetDefaultFileAddressReq,
      com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.GetDefaultFileAddressResp> METHOD_GET_DEFAULT_FILE_ADDRESS =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.GetDefaultFileAddressReq, com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.GetDefaultFileAddressResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "FileAddressService", "getDefaultFileAddress"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.GetDefaultFileAddressReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.GetDefaultFileAddressResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SetDefaultShippingAddressReq,
      com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SetDefaultShippingAddressResp> METHOD_SET_DEFAULT_SHIPPING_ADDRESS =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SetDefaultShippingAddressReq, com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SetDefaultShippingAddressResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "FileAddressService", "setDefaultShippingAddress"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SetDefaultShippingAddressReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SetDefaultShippingAddressResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SelectAllShippingAddressReq,
      com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SelectAllShippingAddressResp> METHOD_SELECT_ALL_SHIPPING_ADDRESS =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SelectAllShippingAddressReq, com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SelectAllShippingAddressResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "FileAddressService", "selectAllShippingAddress"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SelectAllShippingAddressReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SelectAllShippingAddressResp.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FileAddressServiceStub newStub(io.grpc.Channel channel) {
    return new FileAddressServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FileAddressServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new FileAddressServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FileAddressServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new FileAddressServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class FileAddressServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1 新增文件地址
     * </pre>
     */
    public void insertFileAddress(com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.InsertFileAddressReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.InsertFileAddressResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_INSERT_FILE_ADDRESS, responseObserver);
    }

    /**
     * <pre>
     *2 获取默认地址
     * </pre>
     */
    public void getDefaultFileAddress(com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.GetDefaultFileAddressReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.GetDefaultFileAddressResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_DEFAULT_FILE_ADDRESS, responseObserver);
    }

    /**
     * <pre>
     *3. 设置默认取件地址
     * </pre>
     */
    public void setDefaultShippingAddress(com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SetDefaultShippingAddressReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SetDefaultShippingAddressResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SET_DEFAULT_SHIPPING_ADDRESS, responseObserver);
    }

    /**
     * <pre>
     *4. 获取所有地址
     * </pre>
     */
    public void selectAllShippingAddress(com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SelectAllShippingAddressReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SelectAllShippingAddressResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_ALL_SHIPPING_ADDRESS, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_INSERT_FILE_ADDRESS,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.InsertFileAddressReq,
                com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.InsertFileAddressResp>(
                  this, METHODID_INSERT_FILE_ADDRESS)))
          .addMethod(
            METHOD_GET_DEFAULT_FILE_ADDRESS,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.GetDefaultFileAddressReq,
                com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.GetDefaultFileAddressResp>(
                  this, METHODID_GET_DEFAULT_FILE_ADDRESS)))
          .addMethod(
            METHOD_SET_DEFAULT_SHIPPING_ADDRESS,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SetDefaultShippingAddressReq,
                com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SetDefaultShippingAddressResp>(
                  this, METHODID_SET_DEFAULT_SHIPPING_ADDRESS)))
          .addMethod(
            METHOD_SELECT_ALL_SHIPPING_ADDRESS,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SelectAllShippingAddressReq,
                com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SelectAllShippingAddressResp>(
                  this, METHODID_SELECT_ALL_SHIPPING_ADDRESS)))
          .build();
    }
  }

  /**
   */
  public static final class FileAddressServiceStub extends io.grpc.stub.AbstractStub<FileAddressServiceStub> {
    private FileAddressServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FileAddressServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileAddressServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FileAddressServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 新增文件地址
     * </pre>
     */
    public void insertFileAddress(com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.InsertFileAddressReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.InsertFileAddressResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_INSERT_FILE_ADDRESS, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *2 获取默认地址
     * </pre>
     */
    public void getDefaultFileAddress(com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.GetDefaultFileAddressReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.GetDefaultFileAddressResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_DEFAULT_FILE_ADDRESS, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *3. 设置默认取件地址
     * </pre>
     */
    public void setDefaultShippingAddress(com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SetDefaultShippingAddressReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SetDefaultShippingAddressResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SET_DEFAULT_SHIPPING_ADDRESS, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *4. 获取所有地址
     * </pre>
     */
    public void selectAllShippingAddress(com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SelectAllShippingAddressReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SelectAllShippingAddressResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_ALL_SHIPPING_ADDRESS, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class FileAddressServiceBlockingStub extends io.grpc.stub.AbstractStub<FileAddressServiceBlockingStub> {
    private FileAddressServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FileAddressServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileAddressServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FileAddressServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 新增文件地址
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.InsertFileAddressResp insertFileAddress(com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.InsertFileAddressReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_INSERT_FILE_ADDRESS, getCallOptions(), request);
    }

    /**
     * <pre>
     *2 获取默认地址
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.GetDefaultFileAddressResp getDefaultFileAddress(com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.GetDefaultFileAddressReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_DEFAULT_FILE_ADDRESS, getCallOptions(), request);
    }

    /**
     * <pre>
     *3. 设置默认取件地址
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SetDefaultShippingAddressResp setDefaultShippingAddress(com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SetDefaultShippingAddressReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SET_DEFAULT_SHIPPING_ADDRESS, getCallOptions(), request);
    }

    /**
     * <pre>
     *4. 获取所有地址
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SelectAllShippingAddressResp selectAllShippingAddress(com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SelectAllShippingAddressReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_ALL_SHIPPING_ADDRESS, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class FileAddressServiceFutureStub extends io.grpc.stub.AbstractStub<FileAddressServiceFutureStub> {
    private FileAddressServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FileAddressServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileAddressServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FileAddressServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 新增文件地址
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.InsertFileAddressResp> insertFileAddress(
        com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.InsertFileAddressReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_INSERT_FILE_ADDRESS, getCallOptions()), request);
    }

    /**
     * <pre>
     *2 获取默认地址
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.GetDefaultFileAddressResp> getDefaultFileAddress(
        com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.GetDefaultFileAddressReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_DEFAULT_FILE_ADDRESS, getCallOptions()), request);
    }

    /**
     * <pre>
     *3. 设置默认取件地址
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SetDefaultShippingAddressResp> setDefaultShippingAddress(
        com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SetDefaultShippingAddressReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SET_DEFAULT_SHIPPING_ADDRESS, getCallOptions()), request);
    }

    /**
     * <pre>
     *4. 获取所有地址
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SelectAllShippingAddressResp> selectAllShippingAddress(
        com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SelectAllShippingAddressReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_ALL_SHIPPING_ADDRESS, getCallOptions()), request);
    }
  }

  private static final int METHODID_INSERT_FILE_ADDRESS = 0;
  private static final int METHODID_GET_DEFAULT_FILE_ADDRESS = 1;
  private static final int METHODID_SET_DEFAULT_SHIPPING_ADDRESS = 2;
  private static final int METHODID_SELECT_ALL_SHIPPING_ADDRESS = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final FileAddressServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(FileAddressServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_INSERT_FILE_ADDRESS:
          serviceImpl.insertFileAddress((com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.InsertFileAddressReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.InsertFileAddressResp>) responseObserver);
          break;
        case METHODID_GET_DEFAULT_FILE_ADDRESS:
          serviceImpl.getDefaultFileAddress((com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.GetDefaultFileAddressReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.GetDefaultFileAddressResp>) responseObserver);
          break;
        case METHODID_SET_DEFAULT_SHIPPING_ADDRESS:
          serviceImpl.setDefaultShippingAddress((com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SetDefaultShippingAddressReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SetDefaultShippingAddressResp>) responseObserver);
          break;
        case METHODID_SELECT_ALL_SHIPPING_ADDRESS:
          serviceImpl.selectAllShippingAddress((com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SelectAllShippingAddressReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.SelectAllShippingAddressResp>) responseObserver);
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

  private static final class FileAddressServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.foundation.area.microservice.FileAddressStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (FileAddressServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FileAddressServiceDescriptorSupplier())
              .addMethod(METHOD_INSERT_FILE_ADDRESS)
              .addMethod(METHOD_GET_DEFAULT_FILE_ADDRESS)
              .addMethod(METHOD_SET_DEFAULT_SHIPPING_ADDRESS)
              .addMethod(METHOD_SELECT_ALL_SHIPPING_ADDRESS)
              .build();
        }
      }
    }
    return result;
  }
}
