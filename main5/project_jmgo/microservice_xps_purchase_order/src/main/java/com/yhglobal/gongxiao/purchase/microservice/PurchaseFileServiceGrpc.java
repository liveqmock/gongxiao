package com.yhglobal.gongxiao.purchase.microservice;

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
    comments = "Source: PurchaseFile.proto")
public final class PurchaseFileServiceGrpc {

  private PurchaseFileServiceGrpc() {}

  public static final String SERVICE_NAME = "PurchaseFileService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.purchase.microservice.PurchaseFileStructure.PurchaseFileReq,
      com.yhglobal.gongxiao.purchase.microservice.PurchaseFileStructure.PurchaseFileResp> METHOD_PARSE_PURCHASE_ORDER_LIST =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.purchase.microservice.PurchaseFileStructure.PurchaseFileReq, com.yhglobal.gongxiao.purchase.microservice.PurchaseFileStructure.PurchaseFileResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PurchaseFileService", "parsePurchaseOrderList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.purchase.microservice.PurchaseFileStructure.PurchaseFileReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.purchase.microservice.PurchaseFileStructure.PurchaseFileResp.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PurchaseFileServiceStub newStub(io.grpc.Channel channel) {
    return new PurchaseFileServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PurchaseFileServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new PurchaseFileServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PurchaseFileServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new PurchaseFileServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class PurchaseFileServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1 采购单解析
     * </pre>
     */
    public void parsePurchaseOrderList(com.yhglobal.gongxiao.purchase.microservice.PurchaseFileStructure.PurchaseFileReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.purchase.microservice.PurchaseFileStructure.PurchaseFileResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_PARSE_PURCHASE_ORDER_LIST, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_PARSE_PURCHASE_ORDER_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.purchase.microservice.PurchaseFileStructure.PurchaseFileReq,
                com.yhglobal.gongxiao.purchase.microservice.PurchaseFileStructure.PurchaseFileResp>(
                  this, METHODID_PARSE_PURCHASE_ORDER_LIST)))
          .build();
    }
  }

  /**
   */
  public static final class PurchaseFileServiceStub extends io.grpc.stub.AbstractStub<PurchaseFileServiceStub> {
    private PurchaseFileServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PurchaseFileServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PurchaseFileServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PurchaseFileServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 采购单解析
     * </pre>
     */
    public void parsePurchaseOrderList(com.yhglobal.gongxiao.purchase.microservice.PurchaseFileStructure.PurchaseFileReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.purchase.microservice.PurchaseFileStructure.PurchaseFileResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_PARSE_PURCHASE_ORDER_LIST, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PurchaseFileServiceBlockingStub extends io.grpc.stub.AbstractStub<PurchaseFileServiceBlockingStub> {
    private PurchaseFileServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PurchaseFileServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PurchaseFileServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PurchaseFileServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 采购单解析
     * </pre>
     */
    public com.yhglobal.gongxiao.purchase.microservice.PurchaseFileStructure.PurchaseFileResp parsePurchaseOrderList(com.yhglobal.gongxiao.purchase.microservice.PurchaseFileStructure.PurchaseFileReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_PARSE_PURCHASE_ORDER_LIST, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PurchaseFileServiceFutureStub extends io.grpc.stub.AbstractStub<PurchaseFileServiceFutureStub> {
    private PurchaseFileServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PurchaseFileServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PurchaseFileServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PurchaseFileServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 采购单解析
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.purchase.microservice.PurchaseFileStructure.PurchaseFileResp> parsePurchaseOrderList(
        com.yhglobal.gongxiao.purchase.microservice.PurchaseFileStructure.PurchaseFileReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_PARSE_PURCHASE_ORDER_LIST, getCallOptions()), request);
    }
  }

  private static final int METHODID_PARSE_PURCHASE_ORDER_LIST = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PurchaseFileServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PurchaseFileServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PARSE_PURCHASE_ORDER_LIST:
          serviceImpl.parsePurchaseOrderList((com.yhglobal.gongxiao.purchase.microservice.PurchaseFileStructure.PurchaseFileReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.purchase.microservice.PurchaseFileStructure.PurchaseFileResp>) responseObserver);
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

  private static final class PurchaseFileServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.purchase.microservice.PurchaseFileStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PurchaseFileServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PurchaseFileServiceDescriptorSupplier())
              .addMethod(METHOD_PARSE_PURCHASE_ORDER_LIST)
              .build();
        }
      }
    }
    return result;
  }
}
