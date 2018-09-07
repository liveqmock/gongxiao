package com.yhglobal.gongxiao.sales.microservice;

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
    comments = "Source: sales.order.address.proto")
public final class SalesOrderAddressServiceGrpc {

  private SalesOrderAddressServiceGrpc() {}

  public static final String SERVICE_NAME = "SalesOrderAddressService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.sales.microservice.SalesOrderAddressServiceStructure.UpdateShippingAddressRequest,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_UPDATE_RECIPIENT_INFO =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.sales.microservice.SalesOrderAddressServiceStructure.UpdateShippingAddressRequest, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SalesOrderAddressService", "updateRecipientInfo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.sales.microservice.SalesOrderAddressServiceStructure.UpdateShippingAddressRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SalesOrderAddressServiceStub newStub(io.grpc.Channel channel) {
    return new SalesOrderAddressServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SalesOrderAddressServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SalesOrderAddressServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SalesOrderAddressServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SalesOrderAddressServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SalesOrderAddressServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void updateRecipientInfo(com.yhglobal.gongxiao.sales.microservice.SalesOrderAddressServiceStructure.UpdateShippingAddressRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_RECIPIENT_INFO, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_UPDATE_RECIPIENT_INFO,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.sales.microservice.SalesOrderAddressServiceStructure.UpdateShippingAddressRequest,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_UPDATE_RECIPIENT_INFO)))
          .build();
    }
  }

  /**
   */
  public static final class SalesOrderAddressServiceStub extends io.grpc.stub.AbstractStub<SalesOrderAddressServiceStub> {
    private SalesOrderAddressServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SalesOrderAddressServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SalesOrderAddressServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SalesOrderAddressServiceStub(channel, callOptions);
    }

    /**
     */
    public void updateRecipientInfo(com.yhglobal.gongxiao.sales.microservice.SalesOrderAddressServiceStructure.UpdateShippingAddressRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_RECIPIENT_INFO, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SalesOrderAddressServiceBlockingStub extends io.grpc.stub.AbstractStub<SalesOrderAddressServiceBlockingStub> {
    private SalesOrderAddressServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SalesOrderAddressServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SalesOrderAddressServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SalesOrderAddressServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult updateRecipientInfo(com.yhglobal.gongxiao.sales.microservice.SalesOrderAddressServiceStructure.UpdateShippingAddressRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_RECIPIENT_INFO, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SalesOrderAddressServiceFutureStub extends io.grpc.stub.AbstractStub<SalesOrderAddressServiceFutureStub> {
    private SalesOrderAddressServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SalesOrderAddressServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SalesOrderAddressServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SalesOrderAddressServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> updateRecipientInfo(
        com.yhglobal.gongxiao.sales.microservice.SalesOrderAddressServiceStructure.UpdateShippingAddressRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_RECIPIENT_INFO, getCallOptions()), request);
    }
  }

  private static final int METHODID_UPDATE_RECIPIENT_INFO = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SalesOrderAddressServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SalesOrderAddressServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_UPDATE_RECIPIENT_INFO:
          serviceImpl.updateRecipientInfo((com.yhglobal.gongxiao.sales.microservice.SalesOrderAddressServiceStructure.UpdateShippingAddressRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
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

  private static final class SalesOrderAddressServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.sales.microservice.SalesOrderAddressServiceStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SalesOrderAddressServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SalesOrderAddressServiceDescriptorSupplier())
              .addMethod(METHOD_UPDATE_RECIPIENT_INFO)
              .build();
        }
      }
    }
    return result;
  }
}
