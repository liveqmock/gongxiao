package com.yhglobal.gongxiao.inventory.account.microservice;

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
    comments = "Source: account.proto")
public final class InventoryLedgerServiceGrpc {

  private InventoryLedgerServiceGrpc() {}

  public static final String SERVICE_NAME = "InventoryLedgerService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.inventory.account.microservice.AccountStructure.GetInventoryLedgerRequest,
      com.yhglobal.gongxiao.inventory.account.microservice.AccountStructure.AccountPageInfo> METHOD_GET_INVENTORY_LEDGER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.inventory.account.microservice.AccountStructure.GetInventoryLedgerRequest, com.yhglobal.gongxiao.inventory.account.microservice.AccountStructure.AccountPageInfo>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "InventoryLedgerService", "getInventoryLedger"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.account.microservice.AccountStructure.GetInventoryLedgerRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.account.microservice.AccountStructure.AccountPageInfo.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static InventoryLedgerServiceStub newStub(io.grpc.Channel channel) {
    return new InventoryLedgerServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static InventoryLedgerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new InventoryLedgerServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static InventoryLedgerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new InventoryLedgerServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class InventoryLedgerServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1 多条件查询进销存台账
     * </pre>
     */
    public void getInventoryLedger(com.yhglobal.gongxiao.inventory.account.microservice.AccountStructure.GetInventoryLedgerRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.account.microservice.AccountStructure.AccountPageInfo> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_INVENTORY_LEDGER, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_INVENTORY_LEDGER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.inventory.account.microservice.AccountStructure.GetInventoryLedgerRequest,
                com.yhglobal.gongxiao.inventory.account.microservice.AccountStructure.AccountPageInfo>(
                  this, METHODID_GET_INVENTORY_LEDGER)))
          .build();
    }
  }

  /**
   */
  public static final class InventoryLedgerServiceStub extends io.grpc.stub.AbstractStub<InventoryLedgerServiceStub> {
    private InventoryLedgerServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventoryLedgerServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryLedgerServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventoryLedgerServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 多条件查询进销存台账
     * </pre>
     */
    public void getInventoryLedger(com.yhglobal.gongxiao.inventory.account.microservice.AccountStructure.GetInventoryLedgerRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.account.microservice.AccountStructure.AccountPageInfo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_INVENTORY_LEDGER, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class InventoryLedgerServiceBlockingStub extends io.grpc.stub.AbstractStub<InventoryLedgerServiceBlockingStub> {
    private InventoryLedgerServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventoryLedgerServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryLedgerServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventoryLedgerServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 多条件查询进销存台账
     * </pre>
     */
    public com.yhglobal.gongxiao.inventory.account.microservice.AccountStructure.AccountPageInfo getInventoryLedger(com.yhglobal.gongxiao.inventory.account.microservice.AccountStructure.GetInventoryLedgerRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_INVENTORY_LEDGER, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class InventoryLedgerServiceFutureStub extends io.grpc.stub.AbstractStub<InventoryLedgerServiceFutureStub> {
    private InventoryLedgerServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private InventoryLedgerServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryLedgerServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new InventoryLedgerServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 多条件查询进销存台账
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.inventory.account.microservice.AccountStructure.AccountPageInfo> getInventoryLedger(
        com.yhglobal.gongxiao.inventory.account.microservice.AccountStructure.GetInventoryLedgerRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_INVENTORY_LEDGER, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_INVENTORY_LEDGER = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final InventoryLedgerServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(InventoryLedgerServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_INVENTORY_LEDGER:
          serviceImpl.getInventoryLedger((com.yhglobal.gongxiao.inventory.account.microservice.AccountStructure.GetInventoryLedgerRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.account.microservice.AccountStructure.AccountPageInfo>) responseObserver);
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

  private static final class InventoryLedgerServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.inventory.account.microservice.AccountStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (InventoryLedgerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new InventoryLedgerServiceDescriptorSupplier())
              .addMethod(METHOD_GET_INVENTORY_LEDGER)
              .build();
        }
      }
    }
    return result;
  }
}
