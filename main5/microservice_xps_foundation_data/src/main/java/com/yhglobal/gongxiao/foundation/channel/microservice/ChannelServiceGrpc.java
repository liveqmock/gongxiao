package com.yhglobal.gongxiao.foundation.channel.microservice;

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
    comments = "Source: channel/channel.proto")
public final class ChannelServiceGrpc {

  private ChannelServiceGrpc() {}

  public static final String SERVICE_NAME = "ChannelService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure.GetChannelByChannelIdReq,
      com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure.GetChannelByChannelIdResp> METHOD_GET_CHANNEL_BY_CHANNEL_ID =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure.GetChannelByChannelIdReq, com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure.GetChannelByChannelIdResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ChannelService", "getChannelByChannelId"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure.GetChannelByChannelIdReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure.GetChannelByChannelIdResp.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ChannelServiceStub newStub(io.grpc.Channel channel) {
    return new ChannelServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ChannelServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ChannelServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ChannelServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ChannelServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class ChannelServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1 通过currencyCode查询货币
     * </pre>
     */
    public void getChannelByChannelId(com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure.GetChannelByChannelIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure.GetChannelByChannelIdResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_CHANNEL_BY_CHANNEL_ID, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_CHANNEL_BY_CHANNEL_ID,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure.GetChannelByChannelIdReq,
                com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure.GetChannelByChannelIdResp>(
                  this, METHODID_GET_CHANNEL_BY_CHANNEL_ID)))
          .build();
    }
  }

  /**
   */
  public static final class ChannelServiceStub extends io.grpc.stub.AbstractStub<ChannelServiceStub> {
    private ChannelServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ChannelServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChannelServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ChannelServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 通过currencyCode查询货币
     * </pre>
     */
    public void getChannelByChannelId(com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure.GetChannelByChannelIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure.GetChannelByChannelIdResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_CHANNEL_BY_CHANNEL_ID, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ChannelServiceBlockingStub extends io.grpc.stub.AbstractStub<ChannelServiceBlockingStub> {
    private ChannelServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ChannelServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChannelServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ChannelServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 通过currencyCode查询货币
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelId(com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure.GetChannelByChannelIdReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_CHANNEL_BY_CHANNEL_ID, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ChannelServiceFutureStub extends io.grpc.stub.AbstractStub<ChannelServiceFutureStub> {
    private ChannelServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ChannelServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChannelServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ChannelServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 通过currencyCode查询货币
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure.GetChannelByChannelIdResp> getChannelByChannelId(
        com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure.GetChannelByChannelIdReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_CHANNEL_BY_CHANNEL_ID, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_CHANNEL_BY_CHANNEL_ID = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ChannelServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ChannelServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_CHANNEL_BY_CHANNEL_ID:
          serviceImpl.getChannelByChannelId((com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure.GetChannelByChannelIdReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure.GetChannelByChannelIdResp>) responseObserver);
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

  private static final class ChannelServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ChannelServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ChannelServiceDescriptorSupplier())
              .addMethod(METHOD_GET_CHANNEL_BY_CHANNEL_ID)
              .build();
        }
      }
    }
    return result;
  }
}
