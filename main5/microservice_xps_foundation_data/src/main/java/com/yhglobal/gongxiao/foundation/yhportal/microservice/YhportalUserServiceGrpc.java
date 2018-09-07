package com.yhglobal.gongxiao.foundation.yhportal.microservice;

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
    comments = "Source: yhportal/yhportalUser.proto")
public final class YhportalUserServiceGrpc {

  private YhportalUserServiceGrpc() {}

  public static final String SERVICE_NAME = "YhportalUserService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.CheckAccountReq,
      com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.CheckAccountResp> METHOD_CHECK_ACCOUNT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.CheckAccountReq, com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.CheckAccountResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhportalUserService", "checkAccount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.CheckAccountReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.CheckAccountResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserByUserNameReq,
      com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserByUserNameResp> METHOD_GET_USER_BY_USER_NAME =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserByUserNameReq, com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserByUserNameResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhportalUserService", "getUserByUserName"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserByUserNameReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserByUserNameResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserListByAccountReq,
      com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserListByAccountResp> METHOD_GET_USER_LIST_BY_ACCOUNT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserListByAccountReq, com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserListByAccountResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhportalUserService", "getUserListByAccount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserListByAccountReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserListByAccountResp.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static YhportalUserServiceStub newStub(io.grpc.Channel channel) {
    return new YhportalUserServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static YhportalUserServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new YhportalUserServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static YhportalUserServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new YhportalUserServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class YhportalUserServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1 核对账号妈咪是否争取
     * </pre>
     */
    public void checkAccount(com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.CheckAccountReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.CheckAccountResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CHECK_ACCOUNT, responseObserver);
    }

    /**
     * <pre>
     *2 通过用户名获取用户信息
     * </pre>
     */
    public void getUserByUserName(com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserByUserNameReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserByUserNameResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_USER_BY_USER_NAME, responseObserver);
    }

    /**
     * <pre>
     *3
     * </pre>
     */
    public void getUserListByAccount(com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserListByAccountReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserListByAccountResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_USER_LIST_BY_ACCOUNT, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_CHECK_ACCOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.CheckAccountReq,
                com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.CheckAccountResp>(
                  this, METHODID_CHECK_ACCOUNT)))
          .addMethod(
            METHOD_GET_USER_BY_USER_NAME,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserByUserNameReq,
                com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserByUserNameResp>(
                  this, METHODID_GET_USER_BY_USER_NAME)))
          .addMethod(
            METHOD_GET_USER_LIST_BY_ACCOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserListByAccountReq,
                com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserListByAccountResp>(
                  this, METHODID_GET_USER_LIST_BY_ACCOUNT)))
          .build();
    }
  }

  /**
   */
  public static final class YhportalUserServiceStub extends io.grpc.stub.AbstractStub<YhportalUserServiceStub> {
    private YhportalUserServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private YhportalUserServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected YhportalUserServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new YhportalUserServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 核对账号妈咪是否争取
     * </pre>
     */
    public void checkAccount(com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.CheckAccountReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.CheckAccountResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CHECK_ACCOUNT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *2 通过用户名获取用户信息
     * </pre>
     */
    public void getUserByUserName(com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserByUserNameReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserByUserNameResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_USER_BY_USER_NAME, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *3
     * </pre>
     */
    public void getUserListByAccount(com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserListByAccountReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserListByAccountResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_USER_LIST_BY_ACCOUNT, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class YhportalUserServiceBlockingStub extends io.grpc.stub.AbstractStub<YhportalUserServiceBlockingStub> {
    private YhportalUserServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private YhportalUserServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected YhportalUserServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new YhportalUserServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 核对账号妈咪是否争取
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.CheckAccountResp checkAccount(com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.CheckAccountReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CHECK_ACCOUNT, getCallOptions(), request);
    }

    /**
     * <pre>
     *2 通过用户名获取用户信息
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserByUserNameResp getUserByUserName(com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserByUserNameReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_USER_BY_USER_NAME, getCallOptions(), request);
    }

    /**
     * <pre>
     *3
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserListByAccountResp getUserListByAccount(com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserListByAccountReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_USER_LIST_BY_ACCOUNT, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class YhportalUserServiceFutureStub extends io.grpc.stub.AbstractStub<YhportalUserServiceFutureStub> {
    private YhportalUserServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private YhportalUserServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected YhportalUserServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new YhportalUserServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 核对账号妈咪是否争取
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.CheckAccountResp> checkAccount(
        com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.CheckAccountReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CHECK_ACCOUNT, getCallOptions()), request);
    }

    /**
     * <pre>
     *2 通过用户名获取用户信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserByUserNameResp> getUserByUserName(
        com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserByUserNameReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_USER_BY_USER_NAME, getCallOptions()), request);
    }

    /**
     * <pre>
     *3
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserListByAccountResp> getUserListByAccount(
        com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserListByAccountReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_USER_LIST_BY_ACCOUNT, getCallOptions()), request);
    }
  }

  private static final int METHODID_CHECK_ACCOUNT = 0;
  private static final int METHODID_GET_USER_BY_USER_NAME = 1;
  private static final int METHODID_GET_USER_LIST_BY_ACCOUNT = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final YhportalUserServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(YhportalUserServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CHECK_ACCOUNT:
          serviceImpl.checkAccount((com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.CheckAccountReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.CheckAccountResp>) responseObserver);
          break;
        case METHODID_GET_USER_BY_USER_NAME:
          serviceImpl.getUserByUserName((com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserByUserNameReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserByUserNameResp>) responseObserver);
          break;
        case METHODID_GET_USER_LIST_BY_ACCOUNT:
          serviceImpl.getUserListByAccount((com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserListByAccountReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.GetUserListByAccountResp>) responseObserver);
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

  private static final class YhportalUserServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (YhportalUserServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new YhportalUserServiceDescriptorSupplier())
              .addMethod(METHOD_CHECK_ACCOUNT)
              .addMethod(METHOD_GET_USER_BY_USER_NAME)
              .addMethod(METHOD_GET_USER_LIST_BY_ACCOUNT)
              .build();
        }
      }
    }
    return result;
  }
}
