package com.yhglobal.gongxiao.inventory.productinventory180age.microservice;

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
    comments = "Source: productinventory180age.proto")
public final class ProductInventory180AgeServiceGrpc {

  private ProductInventory180AgeServiceGrpc() {}

  public static final String SERVICE_NAME = "ProductInventory180AgeService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.inventory.productinventory180age.microservice.ProductInventory180AgeStructure.GetProductInventory180AgeRequest,
      com.yhglobal.gongxiao.inventory.productinventory180age.microservice.ProductInventory180AgeStructure.GetProductInventory180AgeRespon> METHOD_GET_PRODUCT_INVENTORY180AGE =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.inventory.productinventory180age.microservice.ProductInventory180AgeStructure.GetProductInventory180AgeRequest, com.yhglobal.gongxiao.inventory.productinventory180age.microservice.ProductInventory180AgeStructure.GetProductInventory180AgeRespon>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ProductInventory180AgeService", "getProductInventory180Age"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.productinventory180age.microservice.ProductInventory180AgeStructure.GetProductInventory180AgeRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.inventory.productinventory180age.microservice.ProductInventory180AgeStructure.GetProductInventory180AgeRespon.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ProductInventory180AgeServiceStub newStub(io.grpc.Channel channel) {
    return new ProductInventory180AgeServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ProductInventory180AgeServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ProductInventory180AgeServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ProductInventory180AgeServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ProductInventory180AgeServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class ProductInventory180AgeServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1 查询整体库龄
     * </pre>
     */
    public void getProductInventory180Age(com.yhglobal.gongxiao.inventory.productinventory180age.microservice.ProductInventory180AgeStructure.GetProductInventory180AgeRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.productinventory180age.microservice.ProductInventory180AgeStructure.GetProductInventory180AgeRespon> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_PRODUCT_INVENTORY180AGE, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_PRODUCT_INVENTORY180AGE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.inventory.productinventory180age.microservice.ProductInventory180AgeStructure.GetProductInventory180AgeRequest,
                com.yhglobal.gongxiao.inventory.productinventory180age.microservice.ProductInventory180AgeStructure.GetProductInventory180AgeRespon>(
                  this, METHODID_GET_PRODUCT_INVENTORY180AGE)))
          .build();
    }
  }

  /**
   */
  public static final class ProductInventory180AgeServiceStub extends io.grpc.stub.AbstractStub<ProductInventory180AgeServiceStub> {
    private ProductInventory180AgeServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ProductInventory180AgeServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductInventory180AgeServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ProductInventory180AgeServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 查询整体库龄
     * </pre>
     */
    public void getProductInventory180Age(com.yhglobal.gongxiao.inventory.productinventory180age.microservice.ProductInventory180AgeStructure.GetProductInventory180AgeRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.productinventory180age.microservice.ProductInventory180AgeStructure.GetProductInventory180AgeRespon> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_PRODUCT_INVENTORY180AGE, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ProductInventory180AgeServiceBlockingStub extends io.grpc.stub.AbstractStub<ProductInventory180AgeServiceBlockingStub> {
    private ProductInventory180AgeServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ProductInventory180AgeServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductInventory180AgeServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ProductInventory180AgeServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 查询整体库龄
     * </pre>
     */
    public com.yhglobal.gongxiao.inventory.productinventory180age.microservice.ProductInventory180AgeStructure.GetProductInventory180AgeRespon getProductInventory180Age(com.yhglobal.gongxiao.inventory.productinventory180age.microservice.ProductInventory180AgeStructure.GetProductInventory180AgeRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_PRODUCT_INVENTORY180AGE, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ProductInventory180AgeServiceFutureStub extends io.grpc.stub.AbstractStub<ProductInventory180AgeServiceFutureStub> {
    private ProductInventory180AgeServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ProductInventory180AgeServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductInventory180AgeServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ProductInventory180AgeServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 查询整体库龄
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.inventory.productinventory180age.microservice.ProductInventory180AgeStructure.GetProductInventory180AgeRespon> getProductInventory180Age(
        com.yhglobal.gongxiao.inventory.productinventory180age.microservice.ProductInventory180AgeStructure.GetProductInventory180AgeRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_PRODUCT_INVENTORY180AGE, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_PRODUCT_INVENTORY180AGE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ProductInventory180AgeServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ProductInventory180AgeServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_PRODUCT_INVENTORY180AGE:
          serviceImpl.getProductInventory180Age((com.yhglobal.gongxiao.inventory.productinventory180age.microservice.ProductInventory180AgeStructure.GetProductInventory180AgeRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.inventory.productinventory180age.microservice.ProductInventory180AgeStructure.GetProductInventory180AgeRespon>) responseObserver);
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

  private static final class ProductInventory180AgeServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.inventory.productinventory180age.microservice.ProductInventory180AgeStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ProductInventory180AgeServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ProductInventory180AgeServiceDescriptorSupplier())
              .addMethod(METHOD_GET_PRODUCT_INVENTORY180AGE)
              .build();
        }
      }
    }
    return result;
  }
}
