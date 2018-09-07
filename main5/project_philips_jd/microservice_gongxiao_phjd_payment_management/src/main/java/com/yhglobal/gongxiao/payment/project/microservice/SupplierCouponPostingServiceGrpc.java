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
    comments = "Source: suppliercouponposting.project.proto")
public final class SupplierCouponPostingServiceGrpc {

  private SupplierCouponPostingServiceGrpc() {}

  public static final String SERVICE_NAME = "SupplierCouponPostingService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalReq,
      com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalResp> METHOD_GENERATE_FRONT_FLOW_SUBTOTAL =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalReq, com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierCouponPostingService", "generateFrontFlowSubtotal"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageReq,
      com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageResp> METHOD_SELECT_ALL_BY_SUPPLIER_ID_NO_PAGE =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageReq, com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierCouponPostingService", "selectAllBySupplierIdNoPage"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdHasPageReq,
      com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> METHOD_SELECT_ALL_BY_SUPPLIER_ID_HAS_PAGE =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdHasPageReq, com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "SupplierCouponPostingService", "selectAllBySupplierIdHasPage"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdHasPageReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SupplierCouponPostingServiceStub newStub(io.grpc.Channel channel) {
    return new SupplierCouponPostingServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SupplierCouponPostingServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SupplierCouponPostingServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SupplierCouponPostingServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SupplierCouponPostingServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SupplierCouponPostingServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *根据projectId获取项目信息
     * rpc getProjectById(GetProjectByIdReq) returns (GetProjectByIdResp) {}
     * 获取流水统计金额数据的方法
     * </pre>
     */
    public void generateFrontFlowSubtotal(com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GENERATE_FRONT_FLOW_SUBTOTAL, responseObserver);
    }

    /**
     * <pre>
     * 查询流水不分页
     * </pre>
     */
    public void selectAllBySupplierIdNoPage(com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_ALL_BY_SUPPLIER_ID_NO_PAGE, responseObserver);
    }

    /**
     * <pre>
     * 查询流水分页
     * </pre>
     */
    public void selectAllBySupplierIdHasPage(com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdHasPageReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_ALL_BY_SUPPLIER_ID_HAS_PAGE, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GENERATE_FRONT_FLOW_SUBTOTAL,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalReq,
                com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalResp>(
                  this, METHODID_GENERATE_FRONT_FLOW_SUBTOTAL)))
          .addMethod(
            METHOD_SELECT_ALL_BY_SUPPLIER_ID_NO_PAGE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageReq,
                com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageResp>(
                  this, METHODID_SELECT_ALL_BY_SUPPLIER_ID_NO_PAGE)))
          .addMethod(
            METHOD_SELECT_ALL_BY_SUPPLIER_ID_HAS_PAGE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdHasPageReq,
                com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>(
                  this, METHODID_SELECT_ALL_BY_SUPPLIER_ID_HAS_PAGE)))
          .build();
    }
  }

  /**
   */
  public static final class SupplierCouponPostingServiceStub extends io.grpc.stub.AbstractStub<SupplierCouponPostingServiceStub> {
    private SupplierCouponPostingServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SupplierCouponPostingServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SupplierCouponPostingServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SupplierCouponPostingServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *根据projectId获取项目信息
     * rpc getProjectById(GetProjectByIdReq) returns (GetProjectByIdResp) {}
     * 获取流水统计金额数据的方法
     * </pre>
     */
    public void generateFrontFlowSubtotal(com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GENERATE_FRONT_FLOW_SUBTOTAL, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 查询流水不分页
     * </pre>
     */
    public void selectAllBySupplierIdNoPage(com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_ALL_BY_SUPPLIER_ID_NO_PAGE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 查询流水分页
     * </pre>
     */
    public void selectAllBySupplierIdHasPage(com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdHasPageReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_ALL_BY_SUPPLIER_ID_HAS_PAGE, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SupplierCouponPostingServiceBlockingStub extends io.grpc.stub.AbstractStub<SupplierCouponPostingServiceBlockingStub> {
    private SupplierCouponPostingServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SupplierCouponPostingServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SupplierCouponPostingServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SupplierCouponPostingServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *根据projectId获取项目信息
     * rpc getProjectById(GetProjectByIdReq) returns (GetProjectByIdResp) {}
     * 获取流水统计金额数据的方法
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalResp generateFrontFlowSubtotal(com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GENERATE_FRONT_FLOW_SUBTOTAL, getCallOptions(), request);
    }

    /**
     * <pre>
     * 查询流水不分页
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageResp selectAllBySupplierIdNoPage(com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_ALL_BY_SUPPLIER_ID_NO_PAGE, getCallOptions(), request);
    }

    /**
     * <pre>
     * 查询流水分页
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo selectAllBySupplierIdHasPage(com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdHasPageReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_ALL_BY_SUPPLIER_ID_HAS_PAGE, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SupplierCouponPostingServiceFutureStub extends io.grpc.stub.AbstractStub<SupplierCouponPostingServiceFutureStub> {
    private SupplierCouponPostingServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SupplierCouponPostingServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SupplierCouponPostingServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SupplierCouponPostingServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *根据projectId获取项目信息
     * rpc getProjectById(GetProjectByIdReq) returns (GetProjectByIdResp) {}
     * 获取流水统计金额数据的方法
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalResp> generateFrontFlowSubtotal(
        com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GENERATE_FRONT_FLOW_SUBTOTAL, getCallOptions()), request);
    }

    /**
     * <pre>
     * 查询流水不分页
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageResp> selectAllBySupplierIdNoPage(
        com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_ALL_BY_SUPPLIER_ID_NO_PAGE, getCallOptions()), request);
    }

    /**
     * <pre>
     * 查询流水分页
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo> selectAllBySupplierIdHasPage(
        com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdHasPageReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_ALL_BY_SUPPLIER_ID_HAS_PAGE, getCallOptions()), request);
    }
  }

  private static final int METHODID_GENERATE_FRONT_FLOW_SUBTOTAL = 0;
  private static final int METHODID_SELECT_ALL_BY_SUPPLIER_ID_NO_PAGE = 1;
  private static final int METHODID_SELECT_ALL_BY_SUPPLIER_ID_HAS_PAGE = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SupplierCouponPostingServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SupplierCouponPostingServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GENERATE_FRONT_FLOW_SUBTOTAL:
          serviceImpl.generateFrontFlowSubtotal((com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalResp>) responseObserver);
          break;
        case METHODID_SELECT_ALL_BY_SUPPLIER_ID_NO_PAGE:
          serviceImpl.selectAllBySupplierIdNoPage((com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageResp>) responseObserver);
          break;
        case METHODID_SELECT_ALL_BY_SUPPLIER_ID_HAS_PAGE:
          serviceImpl.selectAllBySupplierIdHasPage((com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.SelectAllBySupplierIdHasPageReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo>) responseObserver);
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

  private static final class SupplierCouponPostingServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.payment.project.microservice.SupplierCouponPostingServiceStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SupplierCouponPostingServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SupplierCouponPostingServiceDescriptorSupplier())
              .addMethod(METHOD_GENERATE_FRONT_FLOW_SUBTOTAL)
              .addMethod(METHOD_SELECT_ALL_BY_SUPPLIER_ID_NO_PAGE)
              .addMethod(METHOD_SELECT_ALL_BY_SUPPLIER_ID_HAS_PAGE)
              .build();
        }
      }
    }
    return result;
  }
}
