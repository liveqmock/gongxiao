package com.yhglobal.gongxiao.wmsconfirm;

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
    comments = "Source: wmsConfirm.proto")
public final class WmsConfirmServiceGrpc {

  private WmsConfirmServiceGrpc() {}

  public static final String SERVICE_NAME = "WmsConfirmService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<WmsConfirmStructure.InStockConfirmRequest,
      WmsConfirmStructure.ConfirmResult> METHOD_CONFIRM_WMS_INBOUND_INFO =
      io.grpc.MethodDescriptor.<WmsConfirmStructure.InStockConfirmRequest, WmsConfirmStructure.ConfirmResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "WmsConfirmService", "confirmWmsInboundInfo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              WmsConfirmStructure.InStockConfirmRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              WmsConfirmStructure.ConfirmResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<WmsConfirmStructure.OutStockConfirmRequest,
      WmsConfirmStructure.ConfirmResult> METHOD_CONFIRM_WMS_OUTBOUND_INFO =
      io.grpc.MethodDescriptor.<WmsConfirmStructure.OutStockConfirmRequest, WmsConfirmStructure.ConfirmResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "WmsConfirmService", "confirmWmsOutboundInfo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              WmsConfirmStructure.OutStockConfirmRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              WmsConfirmStructure.ConfirmResult.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static WmsConfirmServiceStub newStub(io.grpc.Channel channel) {
    return new WmsConfirmServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static WmsConfirmServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new WmsConfirmServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static WmsConfirmServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new WmsConfirmServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class WmsConfirmServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1 wms确认入库
     * </pre>
     */
    public void confirmWmsInboundInfo(WmsConfirmStructure.InStockConfirmRequest request,
        io.grpc.stub.StreamObserver<WmsConfirmStructure.ConfirmResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CONFIRM_WMS_INBOUND_INFO, responseObserver);
    }

    /**
     * <pre>
     *2 wms确认出库
     * </pre>
     */
    public void confirmWmsOutboundInfo(WmsConfirmStructure.OutStockConfirmRequest request,
        io.grpc.stub.StreamObserver<WmsConfirmStructure.ConfirmResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CONFIRM_WMS_OUTBOUND_INFO, responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_CONFIRM_WMS_INBOUND_INFO,
            asyncUnaryCall(
              new MethodHandlers<
                WmsConfirmStructure.InStockConfirmRequest,
                WmsConfirmStructure.ConfirmResult>(
                  this, METHODID_CONFIRM_WMS_INBOUND_INFO)))
          .addMethod(
            METHOD_CONFIRM_WMS_OUTBOUND_INFO,
            asyncUnaryCall(
              new MethodHandlers<
                WmsConfirmStructure.OutStockConfirmRequest,
                WmsConfirmStructure.ConfirmResult>(
                  this, METHODID_CONFIRM_WMS_OUTBOUND_INFO)))
          .build();
    }
  }

  /**
   */
  public static final class WmsConfirmServiceStub extends io.grpc.stub.AbstractStub<WmsConfirmServiceStub> {
    private WmsConfirmServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WmsConfirmServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected WmsConfirmServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WmsConfirmServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 wms确认入库
     * </pre>
     */
    public void confirmWmsInboundInfo(WmsConfirmStructure.InStockConfirmRequest request,
        io.grpc.stub.StreamObserver<WmsConfirmStructure.ConfirmResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CONFIRM_WMS_INBOUND_INFO, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *2 wms确认出库
     * </pre>
     */
    public void confirmWmsOutboundInfo(WmsConfirmStructure.OutStockConfirmRequest request,
        io.grpc.stub.StreamObserver<WmsConfirmStructure.ConfirmResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CONFIRM_WMS_OUTBOUND_INFO, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class WmsConfirmServiceBlockingStub extends io.grpc.stub.AbstractStub<WmsConfirmServiceBlockingStub> {
    private WmsConfirmServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WmsConfirmServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected WmsConfirmServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WmsConfirmServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 wms确认入库
     * </pre>
     */
    public WmsConfirmStructure.ConfirmResult confirmWmsInboundInfo(WmsConfirmStructure.InStockConfirmRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CONFIRM_WMS_INBOUND_INFO, getCallOptions(), request);
    }

    /**
     * <pre>
     *2 wms确认出库
     * </pre>
     */
    public WmsConfirmStructure.ConfirmResult confirmWmsOutboundInfo(WmsConfirmStructure.OutStockConfirmRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CONFIRM_WMS_OUTBOUND_INFO, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class WmsConfirmServiceFutureStub extends io.grpc.stub.AbstractStub<WmsConfirmServiceFutureStub> {
    private WmsConfirmServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WmsConfirmServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected WmsConfirmServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WmsConfirmServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 wms确认入库
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<WmsConfirmStructure.ConfirmResult> confirmWmsInboundInfo(
        WmsConfirmStructure.InStockConfirmRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CONFIRM_WMS_INBOUND_INFO, getCallOptions()), request);
    }

    /**
     * <pre>
     *2 wms确认出库
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<WmsConfirmStructure.ConfirmResult> confirmWmsOutboundInfo(
        WmsConfirmStructure.OutStockConfirmRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CONFIRM_WMS_OUTBOUND_INFO, getCallOptions()), request);
    }
  }

  private static final int METHODID_CONFIRM_WMS_INBOUND_INFO = 0;
  private static final int METHODID_CONFIRM_WMS_OUTBOUND_INFO = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final WmsConfirmServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(WmsConfirmServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CONFIRM_WMS_INBOUND_INFO:
          serviceImpl.confirmWmsInboundInfo((WmsConfirmStructure.InStockConfirmRequest) request,
              (io.grpc.stub.StreamObserver<WmsConfirmStructure.ConfirmResult>) responseObserver);
          break;
        case METHODID_CONFIRM_WMS_OUTBOUND_INFO:
          serviceImpl.confirmWmsOutboundInfo((WmsConfirmStructure.OutStockConfirmRequest) request,
              (io.grpc.stub.StreamObserver<WmsConfirmStructure.ConfirmResult>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class WmsConfirmServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return WmsConfirmStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (WmsConfirmServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new WmsConfirmServiceDescriptorSupplier())
              .addMethod(METHOD_CONFIRM_WMS_INBOUND_INFO)
              .addMethod(METHOD_CONFIRM_WMS_OUTBOUND_INFO)
              .build();
        }
      }
    }
    return result;
  }
}
