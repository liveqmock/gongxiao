package com.yhglobal.gongxiao.plansale.microservice;

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
    comments = "Source: plansale/PlanSaleCustomer.proto")
public final class PlanSaleCustomerServiceGrpc {

  private PlanSaleCustomerServiceGrpc() {}

  public static final String SERVICE_NAME = "PlanSaleCustomerService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanListReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanListResp> METHOD_SELECT_CUSTOMER_PLAN_LIST =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanListReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanListResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleCustomerService", "selectCustomerPlanList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanListReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanListResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanItemListReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanItemListResp> METHOD_SELECT_CUSTOMER_PLAN_ITEM_LIST =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanItemListReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanItemListResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleCustomerService", "selectCustomerPlanItemList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanItemListReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanItemListResp.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PlanSaleCustomerServiceStub newStub(io.grpc.Channel channel) {
    return new PlanSaleCustomerServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PlanSaleCustomerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new PlanSaleCustomerServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PlanSaleCustomerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new PlanSaleCustomerServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class PlanSaleCustomerServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1. 查询客户预售单计划列表
     * </pre>
     */
    public void selectCustomerPlanList(com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanListReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanListResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_CUSTOMER_PLAN_LIST, responseObserver);
    }

    /**
     * <pre>
     *2. 查询客户预售单明细列表
     * </pre>
     */
    public void selectCustomerPlanItemList(com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanItemListReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanItemListResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_CUSTOMER_PLAN_ITEM_LIST, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_SELECT_CUSTOMER_PLAN_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanListReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanListResp>(
                  this, METHODID_SELECT_CUSTOMER_PLAN_LIST)))
          .addMethod(
            METHOD_SELECT_CUSTOMER_PLAN_ITEM_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanItemListReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanItemListResp>(
                  this, METHODID_SELECT_CUSTOMER_PLAN_ITEM_LIST)))
          .build();
    }
  }

  /**
   */
  public static final class PlanSaleCustomerServiceStub extends io.grpc.stub.AbstractStub<PlanSaleCustomerServiceStub> {
    private PlanSaleCustomerServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PlanSaleCustomerServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PlanSaleCustomerServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PlanSaleCustomerServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1. 查询客户预售单计划列表
     * </pre>
     */
    public void selectCustomerPlanList(com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanListReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanListResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_CUSTOMER_PLAN_LIST, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *2. 查询客户预售单明细列表
     * </pre>
     */
    public void selectCustomerPlanItemList(com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanItemListReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanItemListResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_CUSTOMER_PLAN_ITEM_LIST, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PlanSaleCustomerServiceBlockingStub extends io.grpc.stub.AbstractStub<PlanSaleCustomerServiceBlockingStub> {
    private PlanSaleCustomerServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PlanSaleCustomerServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PlanSaleCustomerServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PlanSaleCustomerServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1. 查询客户预售单计划列表
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanListResp selectCustomerPlanList(com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanListReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_CUSTOMER_PLAN_LIST, getCallOptions(), request);
    }

    /**
     * <pre>
     *2. 查询客户预售单明细列表
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanItemListResp selectCustomerPlanItemList(com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanItemListReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_CUSTOMER_PLAN_ITEM_LIST, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PlanSaleCustomerServiceFutureStub extends io.grpc.stub.AbstractStub<PlanSaleCustomerServiceFutureStub> {
    private PlanSaleCustomerServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PlanSaleCustomerServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PlanSaleCustomerServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PlanSaleCustomerServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1. 查询客户预售单计划列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanListResp> selectCustomerPlanList(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanListReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_CUSTOMER_PLAN_LIST, getCallOptions()), request);
    }

    /**
     * <pre>
     *2. 查询客户预售单明细列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanItemListResp> selectCustomerPlanItemList(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanItemListReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_CUSTOMER_PLAN_ITEM_LIST, getCallOptions()), request);
    }
  }

  private static final int METHODID_SELECT_CUSTOMER_PLAN_LIST = 0;
  private static final int METHODID_SELECT_CUSTOMER_PLAN_ITEM_LIST = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PlanSaleCustomerServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PlanSaleCustomerServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SELECT_CUSTOMER_PLAN_LIST:
          serviceImpl.selectCustomerPlanList((com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanListReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanListResp>) responseObserver);
          break;
        case METHODID_SELECT_CUSTOMER_PLAN_ITEM_LIST:
          serviceImpl.selectCustomerPlanItemList((com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanItemListReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.SelectCustomerPlanItemListResp>) responseObserver);
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

  private static final class PlanSaleCustomerServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PlanSaleCustomerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PlanSaleCustomerServiceDescriptorSupplier())
              .addMethod(METHOD_SELECT_CUSTOMER_PLAN_LIST)
              .addMethod(METHOD_SELECT_CUSTOMER_PLAN_ITEM_LIST)
              .build();
        }
      }
    }
    return result;
  }
}
