package com.yhglobal.gongxiao.accountmanage.microservice;

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
 * <pre>
 *供采购模块生成返利用 注：目前实现类放在支付模块2018718
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.4.0)",
    comments = "Source: yhglobalcoupon.proto")
public final class YhglobalCouponServiceGrpc {

  private YhglobalCouponServiceGrpc() {}

  public static final String SERVICE_NAME = "YhglobalCouponService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<YhglobalCouponServiceStructure.GenerateYhglobalCouponLedgerReq,
      YhglobalCouponServiceStructure.GenerateYhglobalCouponLedgerResp> METHOD_GENERATE_YHGLOBAL_COUPON_LEDGER =
      io.grpc.MethodDescriptor.<YhglobalCouponServiceStructure.GenerateYhglobalCouponLedgerReq, YhglobalCouponServiceStructure.GenerateYhglobalCouponLedgerResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalCouponService", "generateYhglobalCouponLedger"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              YhglobalCouponServiceStructure.GenerateYhglobalCouponLedgerReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              YhglobalCouponServiceStructure.GenerateYhglobalCouponLedgerResp.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static YhglobalCouponServiceStub newStub(io.grpc.Channel channel) {
    return new YhglobalCouponServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static YhglobalCouponServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new YhglobalCouponServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static YhglobalCouponServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new YhglobalCouponServiceFutureStub(channel);
  }

  /**
   * <pre>
   *供采购模块生成返利用 注：目前实现类放在支付模块2018718
   * </pre>
   */
  public static abstract class YhglobalCouponServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *根据采购单生成应收返利：月度/季度/年度/现金后收
     * </pre>
     */
    public void generateYhglobalCouponLedger(YhglobalCouponServiceStructure.GenerateYhglobalCouponLedgerReq request,
        io.grpc.stub.StreamObserver<YhglobalCouponServiceStructure.GenerateYhglobalCouponLedgerResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GENERATE_YHGLOBAL_COUPON_LEDGER, responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GENERATE_YHGLOBAL_COUPON_LEDGER,
            asyncUnaryCall(
              new MethodHandlers<
                YhglobalCouponServiceStructure.GenerateYhglobalCouponLedgerReq,
                YhglobalCouponServiceStructure.GenerateYhglobalCouponLedgerResp>(
                  this, METHODID_GENERATE_YHGLOBAL_COUPON_LEDGER)))
          .build();
    }
  }

  /**
   * <pre>
   *供采购模块生成返利用 注：目前实现类放在支付模块2018718
   * </pre>
   */
  public static final class YhglobalCouponServiceStub extends io.grpc.stub.AbstractStub<YhglobalCouponServiceStub> {
    private YhglobalCouponServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private YhglobalCouponServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected YhglobalCouponServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new YhglobalCouponServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *根据采购单生成应收返利：月度/季度/年度/现金后收
     * </pre>
     */
    public void generateYhglobalCouponLedger(YhglobalCouponServiceStructure.GenerateYhglobalCouponLedgerReq request,
        io.grpc.stub.StreamObserver<YhglobalCouponServiceStructure.GenerateYhglobalCouponLedgerResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GENERATE_YHGLOBAL_COUPON_LEDGER, getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   *供采购模块生成返利用 注：目前实现类放在支付模块2018718
   * </pre>
   */
  public static final class YhglobalCouponServiceBlockingStub extends io.grpc.stub.AbstractStub<YhglobalCouponServiceBlockingStub> {
    private YhglobalCouponServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private YhglobalCouponServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected YhglobalCouponServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new YhglobalCouponServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *根据采购单生成应收返利：月度/季度/年度/现金后收
     * </pre>
     */
    public YhglobalCouponServiceStructure.GenerateYhglobalCouponLedgerResp generateYhglobalCouponLedger(YhglobalCouponServiceStructure.GenerateYhglobalCouponLedgerReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GENERATE_YHGLOBAL_COUPON_LEDGER, getCallOptions(), request);
    }
  }

  /**
   * <pre>
   *供采购模块生成返利用 注：目前实现类放在支付模块2018718
   * </pre>
   */
  public static final class YhglobalCouponServiceFutureStub extends io.grpc.stub.AbstractStub<YhglobalCouponServiceFutureStub> {
    private YhglobalCouponServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private YhglobalCouponServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected YhglobalCouponServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new YhglobalCouponServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *根据采购单生成应收返利：月度/季度/年度/现金后收
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<YhglobalCouponServiceStructure.GenerateYhglobalCouponLedgerResp> generateYhglobalCouponLedger(
        YhglobalCouponServiceStructure.GenerateYhglobalCouponLedgerReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GENERATE_YHGLOBAL_COUPON_LEDGER, getCallOptions()), request);
    }
  }

  private static final int METHODID_GENERATE_YHGLOBAL_COUPON_LEDGER = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final YhglobalCouponServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(YhglobalCouponServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GENERATE_YHGLOBAL_COUPON_LEDGER:
          serviceImpl.generateYhglobalCouponLedger((YhglobalCouponServiceStructure.GenerateYhglobalCouponLedgerReq) request,
              (io.grpc.stub.StreamObserver<YhglobalCouponServiceStructure.GenerateYhglobalCouponLedgerResp>) responseObserver);
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

  private static final class YhglobalCouponServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return YhglobalCouponServiceStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (YhglobalCouponServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new YhglobalCouponServiceDescriptorSupplier())
              .addMethod(METHOD_GENERATE_YHGLOBAL_COUPON_LEDGER)
              .build();
        }
      }
    }
    return result;
  }
}
