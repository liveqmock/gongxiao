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
    comments = "Source: notifyInbound.proto")
public final class NotifiInboundServiceGrpc {

  private NotifiInboundServiceGrpc() {}

  public static final String SERVICE_NAME = "NotifiInboundService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferReceivedNotificationReq,
      com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferReceivedNotificationResp> METHOD_TRANSFER_RECEIVED_NOTIFICATION =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferReceivedNotificationReq, com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferReceivedNotificationResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "NotifiInboundService", "transferReceivedNotification"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferReceivedNotificationReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferReceivedNotificationResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.NotifyPurchaseInboundReq,
      com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.NotifyPurchaseInboundResp> METHOD_NOTIFY_PURCHASE_INBOUND =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.NotifyPurchaseInboundReq, com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.NotifyPurchaseInboundResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "NotifiInboundService", "notifyPurchaseInbound"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.NotifyPurchaseInboundReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.NotifyPurchaseInboundResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferClosedNotificationReq,
      com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferClosedNotificationResp> METHOD_TRANSFER_CLOSED_NOTIFICATION =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferClosedNotificationReq, com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferClosedNotificationResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "NotifiInboundService", "transferClosedNotification"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferClosedNotificationReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferClosedNotificationResp.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static NotifiInboundServiceStub newStub(io.grpc.Channel channel) {
    return new NotifiInboundServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static NotifiInboundServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new NotifiInboundServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static NotifiInboundServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new NotifiInboundServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class NotifiInboundServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1. 通过采购单号获取采购详情
     * </pre>
     */
    public void transferReceivedNotification(com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferReceivedNotificationReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferReceivedNotificationResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_TRANSFER_RECEIVED_NOTIFICATION, responseObserver);
    }

    /**
     * <pre>
     *2. 通过采购单号获取采购详情
     * </pre>
     */
    public void notifyPurchaseInbound(com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.NotifyPurchaseInboundReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.NotifyPurchaseInboundResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_NOTIFY_PURCHASE_INBOUND, responseObserver);
    }

    /**
     * <pre>
     *3. 通过采购单号获取采购详情
     * </pre>
     */
    public void transferClosedNotification(com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferClosedNotificationReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferClosedNotificationResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_TRANSFER_CLOSED_NOTIFICATION, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_TRANSFER_RECEIVED_NOTIFICATION,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferReceivedNotificationReq,
                com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferReceivedNotificationResp>(
                  this, METHODID_TRANSFER_RECEIVED_NOTIFICATION)))
          .addMethod(
            METHOD_NOTIFY_PURCHASE_INBOUND,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.NotifyPurchaseInboundReq,
                com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.NotifyPurchaseInboundResp>(
                  this, METHODID_NOTIFY_PURCHASE_INBOUND)))
          .addMethod(
            METHOD_TRANSFER_CLOSED_NOTIFICATION,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferClosedNotificationReq,
                com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferClosedNotificationResp>(
                  this, METHODID_TRANSFER_CLOSED_NOTIFICATION)))
          .build();
    }
  }

  /**
   */
  public static final class NotifiInboundServiceStub extends io.grpc.stub.AbstractStub<NotifiInboundServiceStub> {
    private NotifiInboundServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private NotifiInboundServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NotifiInboundServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new NotifiInboundServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1. 通过采购单号获取采购详情
     * </pre>
     */
    public void transferReceivedNotification(com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferReceivedNotificationReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferReceivedNotificationResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_TRANSFER_RECEIVED_NOTIFICATION, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *2. 通过采购单号获取采购详情
     * </pre>
     */
    public void notifyPurchaseInbound(com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.NotifyPurchaseInboundReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.NotifyPurchaseInboundResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_NOTIFY_PURCHASE_INBOUND, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *3. 通过采购单号获取采购详情
     * </pre>
     */
    public void transferClosedNotification(com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferClosedNotificationReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferClosedNotificationResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_TRANSFER_CLOSED_NOTIFICATION, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class NotifiInboundServiceBlockingStub extends io.grpc.stub.AbstractStub<NotifiInboundServiceBlockingStub> {
    private NotifiInboundServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private NotifiInboundServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NotifiInboundServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new NotifiInboundServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1. 通过采购单号获取采购详情
     * </pre>
     */
    public com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferReceivedNotificationResp transferReceivedNotification(com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferReceivedNotificationReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_TRANSFER_RECEIVED_NOTIFICATION, getCallOptions(), request);
    }

    /**
     * <pre>
     *2. 通过采购单号获取采购详情
     * </pre>
     */
    public com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.NotifyPurchaseInboundResp notifyPurchaseInbound(com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.NotifyPurchaseInboundReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_NOTIFY_PURCHASE_INBOUND, getCallOptions(), request);
    }

    /**
     * <pre>
     *3. 通过采购单号获取采购详情
     * </pre>
     */
    public com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferClosedNotificationResp transferClosedNotification(com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferClosedNotificationReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_TRANSFER_CLOSED_NOTIFICATION, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class NotifiInboundServiceFutureStub extends io.grpc.stub.AbstractStub<NotifiInboundServiceFutureStub> {
    private NotifiInboundServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private NotifiInboundServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NotifiInboundServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new NotifiInboundServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1. 通过采购单号获取采购详情
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferReceivedNotificationResp> transferReceivedNotification(
        com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferReceivedNotificationReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_TRANSFER_RECEIVED_NOTIFICATION, getCallOptions()), request);
    }

    /**
     * <pre>
     *2. 通过采购单号获取采购详情
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.NotifyPurchaseInboundResp> notifyPurchaseInbound(
        com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.NotifyPurchaseInboundReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_NOTIFY_PURCHASE_INBOUND, getCallOptions()), request);
    }

    /**
     * <pre>
     *3. 通过采购单号获取采购详情
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferClosedNotificationResp> transferClosedNotification(
        com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferClosedNotificationReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_TRANSFER_CLOSED_NOTIFICATION, getCallOptions()), request);
    }
  }

  private static final int METHODID_TRANSFER_RECEIVED_NOTIFICATION = 0;
  private static final int METHODID_NOTIFY_PURCHASE_INBOUND = 1;
  private static final int METHODID_TRANSFER_CLOSED_NOTIFICATION = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final NotifiInboundServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(NotifiInboundServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_TRANSFER_RECEIVED_NOTIFICATION:
          serviceImpl.transferReceivedNotification((com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferReceivedNotificationReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferReceivedNotificationResp>) responseObserver);
          break;
        case METHODID_NOTIFY_PURCHASE_INBOUND:
          serviceImpl.notifyPurchaseInbound((com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.NotifyPurchaseInboundReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.NotifyPurchaseInboundResp>) responseObserver);
          break;
        case METHODID_TRANSFER_CLOSED_NOTIFICATION:
          serviceImpl.transferClosedNotification((com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferClosedNotificationReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.TransferClosedNotificationResp>) responseObserver);
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

  private static final class NotifiInboundServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (NotifiInboundServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new NotifiInboundServiceDescriptorSupplier())
              .addMethod(METHOD_TRANSFER_RECEIVED_NOTIFICATION)
              .addMethod(METHOD_NOTIFY_PURCHASE_INBOUND)
              .addMethod(METHOD_TRANSFER_CLOSED_NOTIFICATION)
              .build();
        }
      }
    }
    return result;
  }
}
