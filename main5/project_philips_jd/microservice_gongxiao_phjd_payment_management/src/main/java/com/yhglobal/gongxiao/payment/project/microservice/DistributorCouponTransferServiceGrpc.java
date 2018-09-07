package com.yhglobal.gongxiao.payment.project.microservice;

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
    comments = "Source: distributorcoupontransfer.proto")
public final class DistributorCouponTransferServiceGrpc {

  private DistributorCouponTransferServiceGrpc() {}

  public static final String SERVICE_NAME = "DistributorCouponTransferService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedReq,
      com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedResp> METHOD_SINGLE_DISTRIBUTOR_COUPON_TRANSFER_RECEIVED =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedReq, com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorCouponTransferService", "singleDistributorCouponTransferReceived"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowHasPageReq,
      com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> METHOD_SELECT_COUPON_FLOW_HAS_PAGE =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowHasPageReq, com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorCouponTransferService", "selectCouponFlowHasPage"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowHasPageReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageReq,
      com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageResp> METHOD_SELECT_COUPON_FLOW_NO_PAGE =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageReq, com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorCouponTransferService", "SelectCouponFlowNoPage"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.GetSubtotalReq,
      com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.GetSubtotalResp> METHOD_GET_SUBTOTAL =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.GetSubtotalReq, com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.GetSubtotalResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorCouponTransferService", "getSubtotal"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.GetSubtotalReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.GetSubtotalResp.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DistributorCouponTransferServiceStub newStub(io.grpc.Channel channel) {
    return new DistributorCouponTransferServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DistributorCouponTransferServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new DistributorCouponTransferServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DistributorCouponTransferServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new DistributorCouponTransferServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class DistributorCouponTransferServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 转账接口
     * </pre>
     */
    public void singleDistributorCouponTransferReceived(com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SINGLE_DISTRIBUTOR_COUPON_TRANSFER_RECEIVED, responseObserver);
    }

    /**
     * <pre>
     * 查询流水分页
     * </pre>
     */
    public void selectCouponFlowHasPage(com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowHasPageReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_COUPON_FLOW_HAS_PAGE, responseObserver);
    }

    /**
     * <pre>
     * 查询流水不分页
     * </pre>
     */
    public void selectCouponFlowNoPage(com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_COUPON_FLOW_NO_PAGE, responseObserver);
    }

    /**
     * <pre>
     * 获取流水统计金额数据的方法
     * </pre>
     */
    public void getSubtotal(com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.GetSubtotalReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.GetSubtotalResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_SUBTOTAL, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_SINGLE_DISTRIBUTOR_COUPON_TRANSFER_RECEIVED,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedReq,
                com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedResp>(
                  this, METHODID_SINGLE_DISTRIBUTOR_COUPON_TRANSFER_RECEIVED)))
          .addMethod(
            METHOD_SELECT_COUPON_FLOW_HAS_PAGE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowHasPageReq,
                com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>(
                  this, METHODID_SELECT_COUPON_FLOW_HAS_PAGE)))
          .addMethod(
            METHOD_SELECT_COUPON_FLOW_NO_PAGE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageReq,
                com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageResp>(
                  this, METHODID_SELECT_COUPON_FLOW_NO_PAGE)))
          .addMethod(
            METHOD_GET_SUBTOTAL,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.GetSubtotalReq,
                com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.GetSubtotalResp>(
                  this, METHODID_GET_SUBTOTAL)))
          .build();
    }
  }

  /**
   */
  public static final class DistributorCouponTransferServiceStub extends io.grpc.stub.AbstractStub<DistributorCouponTransferServiceStub> {
    private DistributorCouponTransferServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DistributorCouponTransferServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DistributorCouponTransferServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DistributorCouponTransferServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 转账接口
     * </pre>
     */
    public void singleDistributorCouponTransferReceived(com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SINGLE_DISTRIBUTOR_COUPON_TRANSFER_RECEIVED, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 查询流水分页
     * </pre>
     */
    public void selectCouponFlowHasPage(com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowHasPageReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_COUPON_FLOW_HAS_PAGE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 查询流水不分页
     * </pre>
     */
    public void selectCouponFlowNoPage(com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_COUPON_FLOW_NO_PAGE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 获取流水统计金额数据的方法
     * </pre>
     */
    public void getSubtotal(com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.GetSubtotalReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.GetSubtotalResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_SUBTOTAL, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class DistributorCouponTransferServiceBlockingStub extends io.grpc.stub.AbstractStub<DistributorCouponTransferServiceBlockingStub> {
    private DistributorCouponTransferServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DistributorCouponTransferServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DistributorCouponTransferServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DistributorCouponTransferServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 转账接口
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedResp singleDistributorCouponTransferReceived(com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SINGLE_DISTRIBUTOR_COUPON_TRANSFER_RECEIVED, getCallOptions(), request);
    }

    /**
     * <pre>
     * 查询流水分页
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo selectCouponFlowHasPage(com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowHasPageReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_COUPON_FLOW_HAS_PAGE, getCallOptions(), request);
    }

    /**
     * <pre>
     * 查询流水不分页
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageResp selectCouponFlowNoPage(com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_COUPON_FLOW_NO_PAGE, getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取流水统计金额数据的方法
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.GetSubtotalResp getSubtotal(com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.GetSubtotalReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_SUBTOTAL, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class DistributorCouponTransferServiceFutureStub extends io.grpc.stub.AbstractStub<DistributorCouponTransferServiceFutureStub> {
    private DistributorCouponTransferServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DistributorCouponTransferServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DistributorCouponTransferServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DistributorCouponTransferServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 转账接口
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedResp> singleDistributorCouponTransferReceived(
        com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SINGLE_DISTRIBUTOR_COUPON_TRANSFER_RECEIVED, getCallOptions()), request);
    }

    /**
     * <pre>
     * 查询流水分页
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> selectCouponFlowHasPage(
        com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowHasPageReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_COUPON_FLOW_HAS_PAGE, getCallOptions()), request);
    }

    /**
     * <pre>
     * 查询流水不分页
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageResp> selectCouponFlowNoPage(
        com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_COUPON_FLOW_NO_PAGE, getCallOptions()), request);
    }

    /**
     * <pre>
     * 获取流水统计金额数据的方法
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.GetSubtotalResp> getSubtotal(
        com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.GetSubtotalReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_SUBTOTAL, getCallOptions()), request);
    }
  }

  private static final int METHODID_SINGLE_DISTRIBUTOR_COUPON_TRANSFER_RECEIVED = 0;
  private static final int METHODID_SELECT_COUPON_FLOW_HAS_PAGE = 1;
  private static final int METHODID_SELECT_COUPON_FLOW_NO_PAGE = 2;
  private static final int METHODID_GET_SUBTOTAL = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DistributorCouponTransferServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DistributorCouponTransferServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SINGLE_DISTRIBUTOR_COUPON_TRANSFER_RECEIVED:
          serviceImpl.singleDistributorCouponTransferReceived((com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedResp>) responseObserver);
          break;
        case METHODID_SELECT_COUPON_FLOW_HAS_PAGE:
          serviceImpl.selectCouponFlowHasPage((com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowHasPageReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>) responseObserver);
          break;
        case METHODID_SELECT_COUPON_FLOW_NO_PAGE:
          serviceImpl.selectCouponFlowNoPage((com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageResp>) responseObserver);
          break;
        case METHODID_GET_SUBTOTAL:
          serviceImpl.getSubtotal((com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.GetSubtotalReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.GetSubtotalResp>) responseObserver);
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

  private static final class DistributorCouponTransferServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.payment.project.microservice.DistributorCouponTransferServiceStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (DistributorCouponTransferServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DistributorCouponTransferServiceDescriptorSupplier())
              .addMethod(METHOD_SINGLE_DISTRIBUTOR_COUPON_TRANSFER_RECEIVED)
              .addMethod(METHOD_SELECT_COUPON_FLOW_HAS_PAGE)
              .addMethod(METHOD_SELECT_COUPON_FLOW_NO_PAGE)
              .addMethod(METHOD_GET_SUBTOTAL)
              .build();
        }
      }
    }
    return result;
  }
}
