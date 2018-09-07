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
    comments = "Source: plansale/PlanSale.proto")
public final class PlanSaleServiceGrpc {

  private PlanSaleServiceGrpc() {}

  public static final String SERVICE_NAME = "PlanSaleService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CreateSalePlanReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CreateSalePlanResp> METHOD_CREATE_SALE_PLAN =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CreateSalePlanReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CreateSalePlanResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleService", "createSalePlan"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CreateSalePlanReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CreateSalePlanResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.SelectSalePlanListByProjectReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.SelectSalePlanListByProjectResp> METHOD_SELECT_SALE_PLAN_LIST_BY_PROJECT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.SelectSalePlanListByProjectReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.SelectSalePlanListByProjectResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleService", "selectSalePlanListByProject"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.SelectSalePlanListByProjectReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.SelectSalePlanListByProjectResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdatePlanQuantityReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdatePlanQuantityResp> METHOD_UPDATE_PLAN_QUANTITY =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdatePlanQuantityReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdatePlanQuantityResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleService", "updatePlanQuantity"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdatePlanQuantityReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdatePlanQuantityResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetUnHandleQuantityReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetUnHandleQuantityResp> METHOD_GET_UN_HANDLE_QUANTITY =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetUnHandleQuantityReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetUnHandleQuantityResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleService", "getUnHandleQuantity"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetUnHandleQuantityReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetUnHandleQuantityResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanInfoReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanInfoResp> METHOD_GET_SALE_PLAN_INFO =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanInfoReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanInfoResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleService", "getSalePlanInfo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanInfoReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanInfoResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.EditSalePlanReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.EditSalePlanResp> METHOD_EDIT_SALE_PLAN =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.EditSalePlanReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.EditSalePlanResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleService", "editSalePlan"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.EditSalePlanReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.EditSalePlanResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanDetailReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanDetailResp> METHOD_GET_SALE_PLAN_DETAIL =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanDetailReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanDetailResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleService", "getSalePlanDetail"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanDetailReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanDetailResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.IsProductRepeatReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.IsProductRepeatResp> METHOD_IS_PRODUCT_REPEAT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.IsProductRepeatReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.IsProductRepeatResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleService", "isProductRepeat"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.IsProductRepeatReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.IsProductRepeatResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CancelPlanSaleOrderReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CancelPlanSaleOrderResp> METHOD_CANCEL_PLAN_SALE_ORDER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CancelPlanSaleOrderReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CancelPlanSaleOrderResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleService", "cancelPlanSaleOrder"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CancelPlanSaleOrderReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CancelPlanSaleOrderResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdateReturnSaleQuantityReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdateReturnSaleQuantityResp> METHOD_UPDATE_RETURN_SALE_QUANTITY =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdateReturnSaleQuantityReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdateReturnSaleQuantityResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleService", "updateReturnSaleQuantity"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdateReturnSaleQuantityReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdateReturnSaleQuantityResp.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PlanSaleServiceStub newStub(io.grpc.Channel channel) {
    return new PlanSaleServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PlanSaleServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new PlanSaleServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PlanSaleServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new PlanSaleServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class PlanSaleServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1. 插入货品销售计划列表
     * </pre>
     */
    public void createSalePlan(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CreateSalePlanReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CreateSalePlanResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CREATE_SALE_PLAN, responseObserver);
    }

    /**
     * <pre>
     *2. 获取当前项目的销售计划列表
     * </pre>
     */
    public void selectSalePlanListByProject(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.SelectSalePlanListByProjectReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.SelectSalePlanListByProjectResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_SALE_PLAN_LIST_BY_PROJECT, responseObserver);
    }

    /**
     * <pre>
     *3. 变更销售计划单可售数量
     * </pre>
     */
    public void updatePlanQuantity(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdatePlanQuantityReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdatePlanQuantityResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_PLAN_QUANTITY, responseObserver);
    }

    /**
     * <pre>
     *4. 通过销售计划单号获取预约销售单未处理的数量
     * </pre>
     */
    public void getUnHandleQuantity(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetUnHandleQuantityReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetUnHandleQuantityResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_UN_HANDLE_QUANTITY, responseObserver);
    }

    /**
     * <pre>
     *5. 通过销售计划单号获取销售计划信息
     * </pre>
     */
    public void getSalePlanInfo(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanInfoReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanInfoResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_SALE_PLAN_INFO, responseObserver);
    }

    /**
     * <pre>
     *6. 变更销售计划信息;主要市变更可售数量
     * </pre>
     */
    public void editSalePlan(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.EditSalePlanReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.EditSalePlanResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_EDIT_SALE_PLAN, responseObserver);
    }

    /**
     * <pre>
     *7. 获取销售计划单详情
     * </pre>
     */
    public void getSalePlanDetail(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanDetailReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanDetailResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_SALE_PLAN_DETAIL, responseObserver);
    }

    /**
     * <pre>
     *8. 判定货品是否重复
     * </pre>
     */
    public void isProductRepeat(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.IsProductRepeatReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.IsProductRepeatResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_IS_PRODUCT_REPEAT, responseObserver);
    }

    /**
     * <pre>
     *9. 作废预售单
     * </pre>
     */
    public void cancelPlanSaleOrder(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CancelPlanSaleOrderReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CancelPlanSaleOrderResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CANCEL_PLAN_SALE_ORDER, responseObserver);
    }

    /**
     * <pre>
     *10. 自动或者手动失效;返还订单的可售数量
     * </pre>
     */
    public void updateReturnSaleQuantity(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdateReturnSaleQuantityReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdateReturnSaleQuantityResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_RETURN_SALE_QUANTITY, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_CREATE_SALE_PLAN,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CreateSalePlanReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CreateSalePlanResp>(
                  this, METHODID_CREATE_SALE_PLAN)))
          .addMethod(
            METHOD_SELECT_SALE_PLAN_LIST_BY_PROJECT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.SelectSalePlanListByProjectReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.SelectSalePlanListByProjectResp>(
                  this, METHODID_SELECT_SALE_PLAN_LIST_BY_PROJECT)))
          .addMethod(
            METHOD_UPDATE_PLAN_QUANTITY,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdatePlanQuantityReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdatePlanQuantityResp>(
                  this, METHODID_UPDATE_PLAN_QUANTITY)))
          .addMethod(
            METHOD_GET_UN_HANDLE_QUANTITY,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetUnHandleQuantityReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetUnHandleQuantityResp>(
                  this, METHODID_GET_UN_HANDLE_QUANTITY)))
          .addMethod(
            METHOD_GET_SALE_PLAN_INFO,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanInfoReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanInfoResp>(
                  this, METHODID_GET_SALE_PLAN_INFO)))
          .addMethod(
            METHOD_EDIT_SALE_PLAN,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.EditSalePlanReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.EditSalePlanResp>(
                  this, METHODID_EDIT_SALE_PLAN)))
          .addMethod(
            METHOD_GET_SALE_PLAN_DETAIL,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanDetailReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanDetailResp>(
                  this, METHODID_GET_SALE_PLAN_DETAIL)))
          .addMethod(
            METHOD_IS_PRODUCT_REPEAT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.IsProductRepeatReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.IsProductRepeatResp>(
                  this, METHODID_IS_PRODUCT_REPEAT)))
          .addMethod(
            METHOD_CANCEL_PLAN_SALE_ORDER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CancelPlanSaleOrderReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CancelPlanSaleOrderResp>(
                  this, METHODID_CANCEL_PLAN_SALE_ORDER)))
          .addMethod(
            METHOD_UPDATE_RETURN_SALE_QUANTITY,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdateReturnSaleQuantityReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdateReturnSaleQuantityResp>(
                  this, METHODID_UPDATE_RETURN_SALE_QUANTITY)))
          .build();
    }
  }

  /**
   */
  public static final class PlanSaleServiceStub extends io.grpc.stub.AbstractStub<PlanSaleServiceStub> {
    private PlanSaleServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PlanSaleServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PlanSaleServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PlanSaleServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1. 插入货品销售计划列表
     * </pre>
     */
    public void createSalePlan(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CreateSalePlanReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CreateSalePlanResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CREATE_SALE_PLAN, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *2. 获取当前项目的销售计划列表
     * </pre>
     */
    public void selectSalePlanListByProject(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.SelectSalePlanListByProjectReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.SelectSalePlanListByProjectResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_SALE_PLAN_LIST_BY_PROJECT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *3. 变更销售计划单可售数量
     * </pre>
     */
    public void updatePlanQuantity(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdatePlanQuantityReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdatePlanQuantityResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_PLAN_QUANTITY, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *4. 通过销售计划单号获取预约销售单未处理的数量
     * </pre>
     */
    public void getUnHandleQuantity(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetUnHandleQuantityReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetUnHandleQuantityResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_UN_HANDLE_QUANTITY, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *5. 通过销售计划单号获取销售计划信息
     * </pre>
     */
    public void getSalePlanInfo(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanInfoReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanInfoResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_SALE_PLAN_INFO, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *6. 变更销售计划信息;主要市变更可售数量
     * </pre>
     */
    public void editSalePlan(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.EditSalePlanReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.EditSalePlanResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_EDIT_SALE_PLAN, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *7. 获取销售计划单详情
     * </pre>
     */
    public void getSalePlanDetail(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanDetailReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanDetailResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_SALE_PLAN_DETAIL, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *8. 判定货品是否重复
     * </pre>
     */
    public void isProductRepeat(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.IsProductRepeatReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.IsProductRepeatResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_IS_PRODUCT_REPEAT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *9. 作废预售单
     * </pre>
     */
    public void cancelPlanSaleOrder(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CancelPlanSaleOrderReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CancelPlanSaleOrderResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CANCEL_PLAN_SALE_ORDER, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *10. 自动或者手动失效;返还订单的可售数量
     * </pre>
     */
    public void updateReturnSaleQuantity(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdateReturnSaleQuantityReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdateReturnSaleQuantityResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_RETURN_SALE_QUANTITY, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PlanSaleServiceBlockingStub extends io.grpc.stub.AbstractStub<PlanSaleServiceBlockingStub> {
    private PlanSaleServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PlanSaleServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PlanSaleServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PlanSaleServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1. 插入货品销售计划列表
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CreateSalePlanResp createSalePlan(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CreateSalePlanReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CREATE_SALE_PLAN, getCallOptions(), request);
    }

    /**
     * <pre>
     *2. 获取当前项目的销售计划列表
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.SelectSalePlanListByProjectResp selectSalePlanListByProject(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.SelectSalePlanListByProjectReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_SALE_PLAN_LIST_BY_PROJECT, getCallOptions(), request);
    }

    /**
     * <pre>
     *3. 变更销售计划单可售数量
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdatePlanQuantityResp updatePlanQuantity(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdatePlanQuantityReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_PLAN_QUANTITY, getCallOptions(), request);
    }

    /**
     * <pre>
     *4. 通过销售计划单号获取预约销售单未处理的数量
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetUnHandleQuantityResp getUnHandleQuantity(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetUnHandleQuantityReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_UN_HANDLE_QUANTITY, getCallOptions(), request);
    }

    /**
     * <pre>
     *5. 通过销售计划单号获取销售计划信息
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanInfoResp getSalePlanInfo(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanInfoReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_SALE_PLAN_INFO, getCallOptions(), request);
    }

    /**
     * <pre>
     *6. 变更销售计划信息;主要市变更可售数量
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.EditSalePlanResp editSalePlan(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.EditSalePlanReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_EDIT_SALE_PLAN, getCallOptions(), request);
    }

    /**
     * <pre>
     *7. 获取销售计划单详情
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanDetailResp getSalePlanDetail(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanDetailReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_SALE_PLAN_DETAIL, getCallOptions(), request);
    }

    /**
     * <pre>
     *8. 判定货品是否重复
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.IsProductRepeatResp isProductRepeat(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.IsProductRepeatReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_IS_PRODUCT_REPEAT, getCallOptions(), request);
    }

    /**
     * <pre>
     *9. 作废预售单
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CancelPlanSaleOrderResp cancelPlanSaleOrder(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CancelPlanSaleOrderReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CANCEL_PLAN_SALE_ORDER, getCallOptions(), request);
    }

    /**
     * <pre>
     *10. 自动或者手动失效;返还订单的可售数量
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdateReturnSaleQuantityResp updateReturnSaleQuantity(com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdateReturnSaleQuantityReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_RETURN_SALE_QUANTITY, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PlanSaleServiceFutureStub extends io.grpc.stub.AbstractStub<PlanSaleServiceFutureStub> {
    private PlanSaleServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PlanSaleServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PlanSaleServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PlanSaleServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1. 插入货品销售计划列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CreateSalePlanResp> createSalePlan(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CreateSalePlanReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CREATE_SALE_PLAN, getCallOptions()), request);
    }

    /**
     * <pre>
     *2. 获取当前项目的销售计划列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.SelectSalePlanListByProjectResp> selectSalePlanListByProject(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.SelectSalePlanListByProjectReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_SALE_PLAN_LIST_BY_PROJECT, getCallOptions()), request);
    }

    /**
     * <pre>
     *3. 变更销售计划单可售数量
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdatePlanQuantityResp> updatePlanQuantity(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdatePlanQuantityReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_PLAN_QUANTITY, getCallOptions()), request);
    }

    /**
     * <pre>
     *4. 通过销售计划单号获取预约销售单未处理的数量
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetUnHandleQuantityResp> getUnHandleQuantity(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetUnHandleQuantityReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_UN_HANDLE_QUANTITY, getCallOptions()), request);
    }

    /**
     * <pre>
     *5. 通过销售计划单号获取销售计划信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanInfoResp> getSalePlanInfo(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanInfoReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_SALE_PLAN_INFO, getCallOptions()), request);
    }

    /**
     * <pre>
     *6. 变更销售计划信息;主要市变更可售数量
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.EditSalePlanResp> editSalePlan(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.EditSalePlanReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_EDIT_SALE_PLAN, getCallOptions()), request);
    }

    /**
     * <pre>
     *7. 获取销售计划单详情
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanDetailResp> getSalePlanDetail(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanDetailReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_SALE_PLAN_DETAIL, getCallOptions()), request);
    }

    /**
     * <pre>
     *8. 判定货品是否重复
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.IsProductRepeatResp> isProductRepeat(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.IsProductRepeatReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_IS_PRODUCT_REPEAT, getCallOptions()), request);
    }

    /**
     * <pre>
     *9. 作废预售单
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CancelPlanSaleOrderResp> cancelPlanSaleOrder(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CancelPlanSaleOrderReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CANCEL_PLAN_SALE_ORDER, getCallOptions()), request);
    }

    /**
     * <pre>
     *10. 自动或者手动失效;返还订单的可售数量
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdateReturnSaleQuantityResp> updateReturnSaleQuantity(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdateReturnSaleQuantityReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_RETURN_SALE_QUANTITY, getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_SALE_PLAN = 0;
  private static final int METHODID_SELECT_SALE_PLAN_LIST_BY_PROJECT = 1;
  private static final int METHODID_UPDATE_PLAN_QUANTITY = 2;
  private static final int METHODID_GET_UN_HANDLE_QUANTITY = 3;
  private static final int METHODID_GET_SALE_PLAN_INFO = 4;
  private static final int METHODID_EDIT_SALE_PLAN = 5;
  private static final int METHODID_GET_SALE_PLAN_DETAIL = 6;
  private static final int METHODID_IS_PRODUCT_REPEAT = 7;
  private static final int METHODID_CANCEL_PLAN_SALE_ORDER = 8;
  private static final int METHODID_UPDATE_RETURN_SALE_QUANTITY = 9;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PlanSaleServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PlanSaleServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_SALE_PLAN:
          serviceImpl.createSalePlan((com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CreateSalePlanReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CreateSalePlanResp>) responseObserver);
          break;
        case METHODID_SELECT_SALE_PLAN_LIST_BY_PROJECT:
          serviceImpl.selectSalePlanListByProject((com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.SelectSalePlanListByProjectReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.SelectSalePlanListByProjectResp>) responseObserver);
          break;
        case METHODID_UPDATE_PLAN_QUANTITY:
          serviceImpl.updatePlanQuantity((com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdatePlanQuantityReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdatePlanQuantityResp>) responseObserver);
          break;
        case METHODID_GET_UN_HANDLE_QUANTITY:
          serviceImpl.getUnHandleQuantity((com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetUnHandleQuantityReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetUnHandleQuantityResp>) responseObserver);
          break;
        case METHODID_GET_SALE_PLAN_INFO:
          serviceImpl.getSalePlanInfo((com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanInfoReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanInfoResp>) responseObserver);
          break;
        case METHODID_EDIT_SALE_PLAN:
          serviceImpl.editSalePlan((com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.EditSalePlanReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.EditSalePlanResp>) responseObserver);
          break;
        case METHODID_GET_SALE_PLAN_DETAIL:
          serviceImpl.getSalePlanDetail((com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanDetailReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.GetSalePlanDetailResp>) responseObserver);
          break;
        case METHODID_IS_PRODUCT_REPEAT:
          serviceImpl.isProductRepeat((com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.IsProductRepeatReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.IsProductRepeatResp>) responseObserver);
          break;
        case METHODID_CANCEL_PLAN_SALE_ORDER:
          serviceImpl.cancelPlanSaleOrder((com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CancelPlanSaleOrderReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.CancelPlanSaleOrderResp>) responseObserver);
          break;
        case METHODID_UPDATE_RETURN_SALE_QUANTITY:
          serviceImpl.updateReturnSaleQuantity((com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdateReturnSaleQuantityReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.UpdateReturnSaleQuantityResp>) responseObserver);
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

  private static final class PlanSaleServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PlanSaleServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PlanSaleServiceDescriptorSupplier())
              .addMethod(METHOD_CREATE_SALE_PLAN)
              .addMethod(METHOD_SELECT_SALE_PLAN_LIST_BY_PROJECT)
              .addMethod(METHOD_UPDATE_PLAN_QUANTITY)
              .addMethod(METHOD_GET_UN_HANDLE_QUANTITY)
              .addMethod(METHOD_GET_SALE_PLAN_INFO)
              .addMethod(METHOD_EDIT_SALE_PLAN)
              .addMethod(METHOD_GET_SALE_PLAN_DETAIL)
              .addMethod(METHOD_IS_PRODUCT_REPEAT)
              .addMethod(METHOD_CANCEL_PLAN_SALE_ORDER)
              .addMethod(METHOD_UPDATE_RETURN_SALE_QUANTITY)
              .build();
        }
      }
    }
    return result;
  }
}
