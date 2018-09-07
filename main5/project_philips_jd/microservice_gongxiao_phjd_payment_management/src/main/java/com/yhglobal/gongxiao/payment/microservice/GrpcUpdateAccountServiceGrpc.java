package com.yhglobal.gongxiao.payment.microservice;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.4.0)",
    comments = "Source: grpc.update.account.proto")
public final class GrpcUpdateAccountServiceGrpc {

  private GrpcUpdateAccountServiceGrpc() {}

  public static final String SERVICE_NAME = "GrpcUpdateAccountService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<GrpcUpdateAccountStructure.UpdateRequest,
      GrpcUpdateAccountStructure.UpdateResponse> METHOD_UPDATE_SUPPLIER_UNIT_PRICE_DISCOUNT_FROZEN_ACCOUNT =
      io.grpc.MethodDescriptor.<GrpcUpdateAccountStructure.UpdateRequest, GrpcUpdateAccountStructure.UpdateResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "GrpcUpdateAccountService", "updateSupplierUnitPriceDiscountFrozenAccount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              GrpcUpdateAccountStructure.UpdateRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              GrpcUpdateAccountStructure.UpdateResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<GrpcUpdateAccountStructure.UpdateRequest,
      GrpcUpdateAccountStructure.UpdateResponse> METHOD_UPDATE_SUPPLIER_PURCHASE_RESERVED_FROZEN_ACCOUNT =
      io.grpc.MethodDescriptor.<GrpcUpdateAccountStructure.UpdateRequest, GrpcUpdateAccountStructure.UpdateResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "GrpcUpdateAccountService", "updateSupplierPurchaseReservedFrozenAccount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              GrpcUpdateAccountStructure.UpdateRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              GrpcUpdateAccountStructure.UpdateResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<GrpcUpdateAccountStructure.UpdateRequest,
      GrpcUpdateAccountStructure.UpdateResponse> METHOD_UPDATE_SALES_DIFFERENCE_ACCOUNT =
      io.grpc.MethodDescriptor.<GrpcUpdateAccountStructure.UpdateRequest, GrpcUpdateAccountStructure.UpdateResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "GrpcUpdateAccountService", "updateSalesDifferenceAccount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              GrpcUpdateAccountStructure.UpdateRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              GrpcUpdateAccountStructure.UpdateResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<GrpcUpdateAccountStructure.UpdateRequest,
      GrpcUpdateAccountStructure.UpdateResponse> METHOD_UPDATE_YHGLOBAL_RECEIVED_COUPON_ACCOUNT =
      io.grpc.MethodDescriptor.<GrpcUpdateAccountStructure.UpdateRequest, GrpcUpdateAccountStructure.UpdateResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "GrpcUpdateAccountService", "updateYhglobalReceivedCouponAccount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              GrpcUpdateAccountStructure.UpdateRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              GrpcUpdateAccountStructure.UpdateResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<GrpcUpdateAccountStructure.UpdateRequest,
      GrpcUpdateAccountStructure.UpdateResponse> METHOD_UPDATE_YHGLOBAL_RECEIVED_PREPAID_ACCOUNT =
      io.grpc.MethodDescriptor.<GrpcUpdateAccountStructure.UpdateRequest, GrpcUpdateAccountStructure.UpdateResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "GrpcUpdateAccountService", "updateYhglobalReceivedPrepaidAccount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              GrpcUpdateAccountStructure.UpdateRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              GrpcUpdateAccountStructure.UpdateResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static GrpcUpdateAccountServiceStub newStub(io.grpc.Channel channel) {
    return new GrpcUpdateAccountServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static GrpcUpdateAccountServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new GrpcUpdateAccountServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static GrpcUpdateAccountServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new GrpcUpdateAccountServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class GrpcUpdateAccountServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 更新供应商单价折扣冻结账户
     * </pre>
     */
    public void updateSupplierUnitPriceDiscountFrozenAccount(GrpcUpdateAccountStructure.UpdateRequest request,
        io.grpc.stub.StreamObserver<GrpcUpdateAccountStructure.UpdateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_SUPPLIER_UNIT_PRICE_DISCOUNT_FROZEN_ACCOUNT, responseObserver);
    }

    /**
     * <pre>
     * 更新供应商采购预留冻结账户
     * </pre>
     */
    public void updateSupplierPurchaseReservedFrozenAccount(GrpcUpdateAccountStructure.UpdateRequest request,
        io.grpc.stub.StreamObserver<GrpcUpdateAccountStructure.UpdateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_SUPPLIER_PURCHASE_RESERVED_FROZEN_ACCOUNT, responseObserver);
    }

    /**
     * <pre>
     * 更新供应商采购预留冻结账户
     * </pre>
     */
    public void updateSalesDifferenceAccount(GrpcUpdateAccountStructure.UpdateRequest request,
        io.grpc.stub.StreamObserver<GrpcUpdateAccountStructure.UpdateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_SALES_DIFFERENCE_ACCOUNT, responseObserver);
    }

    /**
     * <pre>
     * 更新越海返利实收账户
     * </pre>
     */
    public void updateYhglobalReceivedCouponAccount(GrpcUpdateAccountStructure.UpdateRequest request,
        io.grpc.stub.StreamObserver<GrpcUpdateAccountStructure.UpdateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_YHGLOBAL_RECEIVED_COUPON_ACCOUNT, responseObserver);
    }

    /**
     * <pre>
     * 更新越海代垫实收账户
     * </pre>
     */
    public void updateYhglobalReceivedPrepaidAccount(GrpcUpdateAccountStructure.UpdateRequest request,
        io.grpc.stub.StreamObserver<GrpcUpdateAccountStructure.UpdateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_YHGLOBAL_RECEIVED_PREPAID_ACCOUNT, responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_UPDATE_SUPPLIER_UNIT_PRICE_DISCOUNT_FROZEN_ACCOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                GrpcUpdateAccountStructure.UpdateRequest,
                GrpcUpdateAccountStructure.UpdateResponse>(
                  this, METHODID_UPDATE_SUPPLIER_UNIT_PRICE_DISCOUNT_FROZEN_ACCOUNT)))
          .addMethod(
            METHOD_UPDATE_SUPPLIER_PURCHASE_RESERVED_FROZEN_ACCOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                GrpcUpdateAccountStructure.UpdateRequest,
                GrpcUpdateAccountStructure.UpdateResponse>(
                  this, METHODID_UPDATE_SUPPLIER_PURCHASE_RESERVED_FROZEN_ACCOUNT)))
          .addMethod(
            METHOD_UPDATE_SALES_DIFFERENCE_ACCOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                GrpcUpdateAccountStructure.UpdateRequest,
                GrpcUpdateAccountStructure.UpdateResponse>(
                  this, METHODID_UPDATE_SALES_DIFFERENCE_ACCOUNT)))
          .addMethod(
            METHOD_UPDATE_YHGLOBAL_RECEIVED_COUPON_ACCOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                GrpcUpdateAccountStructure.UpdateRequest,
                GrpcUpdateAccountStructure.UpdateResponse>(
                  this, METHODID_UPDATE_YHGLOBAL_RECEIVED_COUPON_ACCOUNT)))
          .addMethod(
            METHOD_UPDATE_YHGLOBAL_RECEIVED_PREPAID_ACCOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                GrpcUpdateAccountStructure.UpdateRequest,
                GrpcUpdateAccountStructure.UpdateResponse>(
                  this, METHODID_UPDATE_YHGLOBAL_RECEIVED_PREPAID_ACCOUNT)))
          .build();
    }
  }

  /**
   */
  public static final class GrpcUpdateAccountServiceStub extends io.grpc.stub.AbstractStub<GrpcUpdateAccountServiceStub> {
    private GrpcUpdateAccountServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GrpcUpdateAccountServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected GrpcUpdateAccountServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GrpcUpdateAccountServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 更新供应商单价折扣冻结账户
     * </pre>
     */
    public void updateSupplierUnitPriceDiscountFrozenAccount(GrpcUpdateAccountStructure.UpdateRequest request,
        io.grpc.stub.StreamObserver<GrpcUpdateAccountStructure.UpdateResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_SUPPLIER_UNIT_PRICE_DISCOUNT_FROZEN_ACCOUNT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 更新供应商采购预留冻结账户
     * </pre>
     */
    public void updateSupplierPurchaseReservedFrozenAccount(GrpcUpdateAccountStructure.UpdateRequest request,
        io.grpc.stub.StreamObserver<GrpcUpdateAccountStructure.UpdateResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_SUPPLIER_PURCHASE_RESERVED_FROZEN_ACCOUNT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 更新供应商采购预留冻结账户
     * </pre>
     */
    public void updateSalesDifferenceAccount(GrpcUpdateAccountStructure.UpdateRequest request,
        io.grpc.stub.StreamObserver<GrpcUpdateAccountStructure.UpdateResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_SALES_DIFFERENCE_ACCOUNT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 更新越海返利实收账户
     * </pre>
     */
    public void updateYhglobalReceivedCouponAccount(GrpcUpdateAccountStructure.UpdateRequest request,
        io.grpc.stub.StreamObserver<GrpcUpdateAccountStructure.UpdateResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_YHGLOBAL_RECEIVED_COUPON_ACCOUNT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 更新越海代垫实收账户
     * </pre>
     */
    public void updateYhglobalReceivedPrepaidAccount(GrpcUpdateAccountStructure.UpdateRequest request,
        io.grpc.stub.StreamObserver<GrpcUpdateAccountStructure.UpdateResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_YHGLOBAL_RECEIVED_PREPAID_ACCOUNT, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class GrpcUpdateAccountServiceBlockingStub extends io.grpc.stub.AbstractStub<GrpcUpdateAccountServiceBlockingStub> {
    private GrpcUpdateAccountServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GrpcUpdateAccountServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected GrpcUpdateAccountServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GrpcUpdateAccountServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 更新供应商单价折扣冻结账户
     * </pre>
     */
    public GrpcUpdateAccountStructure.UpdateResponse updateSupplierUnitPriceDiscountFrozenAccount(GrpcUpdateAccountStructure.UpdateRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_SUPPLIER_UNIT_PRICE_DISCOUNT_FROZEN_ACCOUNT, getCallOptions(), request);
    }

    /**
     * <pre>
     * 更新供应商采购预留冻结账户
     * </pre>
     */
    public GrpcUpdateAccountStructure.UpdateResponse updateSupplierPurchaseReservedFrozenAccount(GrpcUpdateAccountStructure.UpdateRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_SUPPLIER_PURCHASE_RESERVED_FROZEN_ACCOUNT, getCallOptions(), request);
    }

    /**
     * <pre>
     * 更新供应商采购预留冻结账户
     * </pre>
     */
    public GrpcUpdateAccountStructure.UpdateResponse updateSalesDifferenceAccount(GrpcUpdateAccountStructure.UpdateRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_SALES_DIFFERENCE_ACCOUNT, getCallOptions(), request);
    }

    /**
     * <pre>
     * 更新越海返利实收账户
     * </pre>
     */
    public GrpcUpdateAccountStructure.UpdateResponse updateYhglobalReceivedCouponAccount(GrpcUpdateAccountStructure.UpdateRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_YHGLOBAL_RECEIVED_COUPON_ACCOUNT, getCallOptions(), request);
    }

    /**
     * <pre>
     * 更新越海代垫实收账户
     * </pre>
     */
    public GrpcUpdateAccountStructure.UpdateResponse updateYhglobalReceivedPrepaidAccount(GrpcUpdateAccountStructure.UpdateRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_YHGLOBAL_RECEIVED_PREPAID_ACCOUNT, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class GrpcUpdateAccountServiceFutureStub extends io.grpc.stub.AbstractStub<GrpcUpdateAccountServiceFutureStub> {
    private GrpcUpdateAccountServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GrpcUpdateAccountServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected GrpcUpdateAccountServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GrpcUpdateAccountServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 更新供应商单价折扣冻结账户
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcUpdateAccountStructure.UpdateResponse> updateSupplierUnitPriceDiscountFrozenAccount(
        GrpcUpdateAccountStructure.UpdateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_SUPPLIER_UNIT_PRICE_DISCOUNT_FROZEN_ACCOUNT, getCallOptions()), request);
    }

    /**
     * <pre>
     * 更新供应商采购预留冻结账户
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcUpdateAccountStructure.UpdateResponse> updateSupplierPurchaseReservedFrozenAccount(
        GrpcUpdateAccountStructure.UpdateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_SUPPLIER_PURCHASE_RESERVED_FROZEN_ACCOUNT, getCallOptions()), request);
    }

    /**
     * <pre>
     * 更新供应商采购预留冻结账户
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcUpdateAccountStructure.UpdateResponse> updateSalesDifferenceAccount(
        GrpcUpdateAccountStructure.UpdateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_SALES_DIFFERENCE_ACCOUNT, getCallOptions()), request);
    }

    /**
     * <pre>
     * 更新越海返利实收账户
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcUpdateAccountStructure.UpdateResponse> updateYhglobalReceivedCouponAccount(
        GrpcUpdateAccountStructure.UpdateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_YHGLOBAL_RECEIVED_COUPON_ACCOUNT, getCallOptions()), request);
    }

    /**
     * <pre>
     * 更新越海代垫实收账户
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcUpdateAccountStructure.UpdateResponse> updateYhglobalReceivedPrepaidAccount(
        GrpcUpdateAccountStructure.UpdateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_YHGLOBAL_RECEIVED_PREPAID_ACCOUNT, getCallOptions()), request);
    }
  }

  private static final int METHODID_UPDATE_SUPPLIER_UNIT_PRICE_DISCOUNT_FROZEN_ACCOUNT = 0;
  private static final int METHODID_UPDATE_SUPPLIER_PURCHASE_RESERVED_FROZEN_ACCOUNT = 1;
  private static final int METHODID_UPDATE_SALES_DIFFERENCE_ACCOUNT = 2;
  private static final int METHODID_UPDATE_YHGLOBAL_RECEIVED_COUPON_ACCOUNT = 3;
  private static final int METHODID_UPDATE_YHGLOBAL_RECEIVED_PREPAID_ACCOUNT = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final GrpcUpdateAccountServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(GrpcUpdateAccountServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_UPDATE_SUPPLIER_UNIT_PRICE_DISCOUNT_FROZEN_ACCOUNT:
          serviceImpl.updateSupplierUnitPriceDiscountFrozenAccount((GrpcUpdateAccountStructure.UpdateRequest) request,
              (io.grpc.stub.StreamObserver<GrpcUpdateAccountStructure.UpdateResponse>) responseObserver);
          break;
        case METHODID_UPDATE_SUPPLIER_PURCHASE_RESERVED_FROZEN_ACCOUNT:
          serviceImpl.updateSupplierPurchaseReservedFrozenAccount((GrpcUpdateAccountStructure.UpdateRequest) request,
              (io.grpc.stub.StreamObserver<GrpcUpdateAccountStructure.UpdateResponse>) responseObserver);
          break;
        case METHODID_UPDATE_SALES_DIFFERENCE_ACCOUNT:
          serviceImpl.updateSalesDifferenceAccount((GrpcUpdateAccountStructure.UpdateRequest) request,
              (io.grpc.stub.StreamObserver<GrpcUpdateAccountStructure.UpdateResponse>) responseObserver);
          break;
        case METHODID_UPDATE_YHGLOBAL_RECEIVED_COUPON_ACCOUNT:
          serviceImpl.updateYhglobalReceivedCouponAccount((GrpcUpdateAccountStructure.UpdateRequest) request,
              (io.grpc.stub.StreamObserver<GrpcUpdateAccountStructure.UpdateResponse>) responseObserver);
          break;
        case METHODID_UPDATE_YHGLOBAL_RECEIVED_PREPAID_ACCOUNT:
          serviceImpl.updateYhglobalReceivedPrepaidAccount((GrpcUpdateAccountStructure.UpdateRequest) request,
              (io.grpc.stub.StreamObserver<GrpcUpdateAccountStructure.UpdateResponse>) responseObserver);
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

  private static final class GrpcUpdateAccountServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return GrpcUpdateAccountStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (GrpcUpdateAccountServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new GrpcUpdateAccountServiceDescriptorSupplier())
              .addMethod(METHOD_UPDATE_SUPPLIER_UNIT_PRICE_DISCOUNT_FROZEN_ACCOUNT)
              .addMethod(METHOD_UPDATE_SUPPLIER_PURCHASE_RESERVED_FROZEN_ACCOUNT)
              .addMethod(METHOD_UPDATE_SALES_DIFFERENCE_ACCOUNT)
              .addMethod(METHOD_UPDATE_YHGLOBAL_RECEIVED_COUPON_ACCOUNT)
              .addMethod(METHOD_UPDATE_YHGLOBAL_RECEIVED_PREPAID_ACCOUNT)
              .build();
        }
      }
    }
    return result;
  }
}
