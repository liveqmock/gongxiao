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
    comments = "Source: plansale/PlanSaleItem.proto")
public final class PlanSaleItemServiceGrpc {

  private PlanSaleItemServiceGrpc() {}

  public static final String SERVICE_NAME = "PlanSaleItemService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CreateSalePlanItemReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CreateSalePlanItemResp> METHOD_CREATE_SALE_PLAN_ITEM =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CreateSalePlanItemReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CreateSalePlanItemResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleItemService", "createSalePlanItem"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CreateSalePlanItemReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CreateSalePlanItemResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListResp> METHOD_SELECT_SALE_PLAN_ITEM_LIST =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleItemService", "selectSalePlanItemList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetEditItemInfoReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetEditItemInfoResp> METHOD_GET_EDIT_ITEM_INFO =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetEditItemInfoReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetEditItemInfoResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleItemService", "getEditItemInfo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetEditItemInfoReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetEditItemInfoResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.EditSaleItemReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.EditSaleItemResp> METHOD_EDIT_SALE_ITEM =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.EditSaleItemReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.EditSaleItemResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleItemService", "editSaleItem"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.EditSaleItemReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.EditSaleItemResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanItemReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanItemResp> METHOD_CANCEL_PLAN_ITEM =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanItemReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanItemResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleItemService", "cancelPlanItem"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanItemReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanItemResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanResp> METHOD_CANCEL_PLAN =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleItemService", "cancelPlan"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetCustomerPlanInfoReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetCustomerPlanInfoResp> METHOD_GET_CUSTOMER_PLAN_INFO =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetCustomerPlanInfoReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetCustomerPlanInfoResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleItemService", "getCustomerPlanInfo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetCustomerPlanInfoReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetCustomerPlanInfoResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetProductCustomerReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetProductCustomerResp> METHOD_GET_PRODUCT_CUSTOMER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetProductCustomerReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetProductCustomerResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleItemService", "getProductCustomer"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetProductCustomerReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetProductCustomerResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SearchProductByCustomerReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SearchProductByCustomerResp> METHOD_SEARCH_PRODUCT_BY_CUSTOMER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SearchProductByCustomerReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SearchProductByCustomerResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleItemService", "searchProductByCustomer"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SearchProductByCustomerReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SearchProductByCustomerResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSaleOccupationReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSaleOccupationResp> METHOD_UPDATE_SALE_OCCUPATION =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSaleOccupationReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSaleOccupationResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleItemService", "updateSaleOccupation"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSaleOccupationReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSaleOccupationResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSoldQuantityReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSoldQuantityResp> METHOD_UPDATE_SOLD_QUANTITY =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSoldQuantityReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSoldQuantityResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleItemService", "updateSoldQuantity"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSoldQuantityReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSoldQuantityResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListConditionsReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListConditionsResp> METHOD_SELECT_SALE_PLAN_ITEM_LIST_CONDITIONS =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListConditionsReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListConditionsResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleItemService", "selectSalePlanItemListConditions"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListConditionsReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListConditionsResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetOnSaleQuantityReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetOnSaleQuantityResp> METHOD_GET_ON_SALE_QUANTITY =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetOnSaleQuantityReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetOnSaleQuantityResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleItemService", "getOnSaleQuantity"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetOnSaleQuantityReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetOnSaleQuantityResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelCustomerOrderReq,
      com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelCustomerOrderResp> METHOD_CANCEL_CUSTOMER_ORDER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelCustomerOrderReq, com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelCustomerOrderResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PlanSaleItemService", "cancelCustomerOrder"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelCustomerOrderReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelCustomerOrderResp.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PlanSaleItemServiceStub newStub(io.grpc.Channel channel) {
    return new PlanSaleItemServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PlanSaleItemServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new PlanSaleItemServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PlanSaleItemServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new PlanSaleItemServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class PlanSaleItemServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1. 插入销售计划列表
     * </pre>
     */
    public void createSalePlanItem(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CreateSalePlanItemReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CreateSalePlanItemResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CREATE_SALE_PLAN_ITEM, responseObserver);
    }

    /**
     * <pre>
     *2. 获取预销客户列表
     * </pre>
     */
    public void selectSalePlanItemList(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_SALE_PLAN_ITEM_LIST, responseObserver);
    }

    /**
     * <pre>
     *3. 获取可编辑的信息
     * </pre>
     */
    public void getEditItemInfo(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetEditItemInfoReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetEditItemInfoResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_EDIT_ITEM_INFO, responseObserver);
    }

    /**
     * <pre>
     *4. 更新客户销售计划信息
     * </pre>
     */
    public void editSaleItem(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.EditSaleItemReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.EditSaleItemResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_EDIT_SALE_ITEM, responseObserver);
    }

    /**
     * <pre>
     *5. 取消客户计划销售单
     * </pre>
     */
    public void cancelPlanItem(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanItemReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanItemResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CANCEL_PLAN_ITEM, responseObserver);
    }

    /**
     * <pre>
     *6. 通过预售单取消预售详情
     * </pre>
     */
    public void cancelPlan(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CANCEL_PLAN, responseObserver);
    }

    /**
     * <pre>
     *7. 获取客户某个有效的货品信息
     * </pre>
     */
    public void getCustomerPlanInfo(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetCustomerPlanInfoReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetCustomerPlanInfoResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_CUSTOMER_PLAN_INFO, responseObserver);
    }

    /**
     * <pre>
     *8. 获取该货品分配给客户的所有记录数
     * </pre>
     */
    public void getProductCustomer(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetProductCustomerReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetProductCustomerResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_PRODUCT_CUSTOMER, responseObserver);
    }

    /**
     * <pre>
     *9. 获取客户有效的货品信息列表
     * </pre>
     */
    public void searchProductByCustomer(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SearchProductByCustomerReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SearchProductByCustomerResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SEARCH_PRODUCT_BY_CUSTOMER, responseObserver);
    }

    /**
     * <pre>
     *10. 更新销售占用信息
     * </pre>
     */
    public void updateSaleOccupation(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSaleOccupationReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSaleOccupationResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_SALE_OCCUPATION, responseObserver);
    }

    /**
     * <pre>
     *11. 更新已售数量信息
     * </pre>
     */
    public void updateSoldQuantity(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSoldQuantityReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSoldQuantityResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_SOLD_QUANTITY, responseObserver);
    }

    /**
     * <pre>
     *12. 获取有效客户列表
     * </pre>
     */
    public void selectSalePlanItemListConditions(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListConditionsReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListConditionsResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_SALE_PLAN_ITEM_LIST_CONDITIONS, responseObserver);
    }

    /**
     * <pre>
     *13. 通过itemid;获取可售信息
     * </pre>
     */
    public void getOnSaleQuantity(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetOnSaleQuantityReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetOnSaleQuantityResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_ON_SALE_QUANTITY, responseObserver);
    }

    /**
     * <pre>
     *14. 通过itemid;获取可售信息
     * </pre>
     */
    public void cancelCustomerOrder(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelCustomerOrderReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelCustomerOrderResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CANCEL_CUSTOMER_ORDER, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_CREATE_SALE_PLAN_ITEM,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CreateSalePlanItemReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CreateSalePlanItemResp>(
                  this, METHODID_CREATE_SALE_PLAN_ITEM)))
          .addMethod(
            METHOD_SELECT_SALE_PLAN_ITEM_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListResp>(
                  this, METHODID_SELECT_SALE_PLAN_ITEM_LIST)))
          .addMethod(
            METHOD_GET_EDIT_ITEM_INFO,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetEditItemInfoReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetEditItemInfoResp>(
                  this, METHODID_GET_EDIT_ITEM_INFO)))
          .addMethod(
            METHOD_EDIT_SALE_ITEM,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.EditSaleItemReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.EditSaleItemResp>(
                  this, METHODID_EDIT_SALE_ITEM)))
          .addMethod(
            METHOD_CANCEL_PLAN_ITEM,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanItemReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanItemResp>(
                  this, METHODID_CANCEL_PLAN_ITEM)))
          .addMethod(
            METHOD_CANCEL_PLAN,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanResp>(
                  this, METHODID_CANCEL_PLAN)))
          .addMethod(
            METHOD_GET_CUSTOMER_PLAN_INFO,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetCustomerPlanInfoReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetCustomerPlanInfoResp>(
                  this, METHODID_GET_CUSTOMER_PLAN_INFO)))
          .addMethod(
            METHOD_GET_PRODUCT_CUSTOMER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetProductCustomerReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetProductCustomerResp>(
                  this, METHODID_GET_PRODUCT_CUSTOMER)))
          .addMethod(
            METHOD_SEARCH_PRODUCT_BY_CUSTOMER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SearchProductByCustomerReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SearchProductByCustomerResp>(
                  this, METHODID_SEARCH_PRODUCT_BY_CUSTOMER)))
          .addMethod(
            METHOD_UPDATE_SALE_OCCUPATION,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSaleOccupationReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSaleOccupationResp>(
                  this, METHODID_UPDATE_SALE_OCCUPATION)))
          .addMethod(
            METHOD_UPDATE_SOLD_QUANTITY,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSoldQuantityReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSoldQuantityResp>(
                  this, METHODID_UPDATE_SOLD_QUANTITY)))
          .addMethod(
            METHOD_SELECT_SALE_PLAN_ITEM_LIST_CONDITIONS,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListConditionsReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListConditionsResp>(
                  this, METHODID_SELECT_SALE_PLAN_ITEM_LIST_CONDITIONS)))
          .addMethod(
            METHOD_GET_ON_SALE_QUANTITY,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetOnSaleQuantityReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetOnSaleQuantityResp>(
                  this, METHODID_GET_ON_SALE_QUANTITY)))
          .addMethod(
            METHOD_CANCEL_CUSTOMER_ORDER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelCustomerOrderReq,
                com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelCustomerOrderResp>(
                  this, METHODID_CANCEL_CUSTOMER_ORDER)))
          .build();
    }
  }

  /**
   */
  public static final class PlanSaleItemServiceStub extends io.grpc.stub.AbstractStub<PlanSaleItemServiceStub> {
    private PlanSaleItemServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PlanSaleItemServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PlanSaleItemServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PlanSaleItemServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1. 插入销售计划列表
     * </pre>
     */
    public void createSalePlanItem(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CreateSalePlanItemReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CreateSalePlanItemResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CREATE_SALE_PLAN_ITEM, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *2. 获取预销客户列表
     * </pre>
     */
    public void selectSalePlanItemList(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_SALE_PLAN_ITEM_LIST, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *3. 获取可编辑的信息
     * </pre>
     */
    public void getEditItemInfo(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetEditItemInfoReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetEditItemInfoResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_EDIT_ITEM_INFO, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *4. 更新客户销售计划信息
     * </pre>
     */
    public void editSaleItem(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.EditSaleItemReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.EditSaleItemResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_EDIT_SALE_ITEM, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *5. 取消客户计划销售单
     * </pre>
     */
    public void cancelPlanItem(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanItemReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanItemResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CANCEL_PLAN_ITEM, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *6. 通过预售单取消预售详情
     * </pre>
     */
    public void cancelPlan(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CANCEL_PLAN, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *7. 获取客户某个有效的货品信息
     * </pre>
     */
    public void getCustomerPlanInfo(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetCustomerPlanInfoReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetCustomerPlanInfoResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_CUSTOMER_PLAN_INFO, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *8. 获取该货品分配给客户的所有记录数
     * </pre>
     */
    public void getProductCustomer(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetProductCustomerReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetProductCustomerResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_PRODUCT_CUSTOMER, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *9. 获取客户有效的货品信息列表
     * </pre>
     */
    public void searchProductByCustomer(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SearchProductByCustomerReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SearchProductByCustomerResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SEARCH_PRODUCT_BY_CUSTOMER, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *10. 更新销售占用信息
     * </pre>
     */
    public void updateSaleOccupation(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSaleOccupationReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSaleOccupationResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_SALE_OCCUPATION, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *11. 更新已售数量信息
     * </pre>
     */
    public void updateSoldQuantity(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSoldQuantityReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSoldQuantityResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_SOLD_QUANTITY, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *12. 获取有效客户列表
     * </pre>
     */
    public void selectSalePlanItemListConditions(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListConditionsReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListConditionsResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_SALE_PLAN_ITEM_LIST_CONDITIONS, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *13. 通过itemid;获取可售信息
     * </pre>
     */
    public void getOnSaleQuantity(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetOnSaleQuantityReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetOnSaleQuantityResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_ON_SALE_QUANTITY, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *14. 通过itemid;获取可售信息
     * </pre>
     */
    public void cancelCustomerOrder(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelCustomerOrderReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelCustomerOrderResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CANCEL_CUSTOMER_ORDER, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PlanSaleItemServiceBlockingStub extends io.grpc.stub.AbstractStub<PlanSaleItemServiceBlockingStub> {
    private PlanSaleItemServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PlanSaleItemServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PlanSaleItemServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PlanSaleItemServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1. 插入销售计划列表
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CreateSalePlanItemResp createSalePlanItem(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CreateSalePlanItemReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CREATE_SALE_PLAN_ITEM, getCallOptions(), request);
    }

    /**
     * <pre>
     *2. 获取预销客户列表
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListResp selectSalePlanItemList(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_SALE_PLAN_ITEM_LIST, getCallOptions(), request);
    }

    /**
     * <pre>
     *3. 获取可编辑的信息
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetEditItemInfoResp getEditItemInfo(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetEditItemInfoReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_EDIT_ITEM_INFO, getCallOptions(), request);
    }

    /**
     * <pre>
     *4. 更新客户销售计划信息
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.EditSaleItemResp editSaleItem(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.EditSaleItemReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_EDIT_SALE_ITEM, getCallOptions(), request);
    }

    /**
     * <pre>
     *5. 取消客户计划销售单
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanItemResp cancelPlanItem(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanItemReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CANCEL_PLAN_ITEM, getCallOptions(), request);
    }

    /**
     * <pre>
     *6. 通过预售单取消预售详情
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanResp cancelPlan(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CANCEL_PLAN, getCallOptions(), request);
    }

    /**
     * <pre>
     *7. 获取客户某个有效的货品信息
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetCustomerPlanInfoResp getCustomerPlanInfo(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetCustomerPlanInfoReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_CUSTOMER_PLAN_INFO, getCallOptions(), request);
    }

    /**
     * <pre>
     *8. 获取该货品分配给客户的所有记录数
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetProductCustomerResp getProductCustomer(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetProductCustomerReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_PRODUCT_CUSTOMER, getCallOptions(), request);
    }

    /**
     * <pre>
     *9. 获取客户有效的货品信息列表
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SearchProductByCustomerResp searchProductByCustomer(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SearchProductByCustomerReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SEARCH_PRODUCT_BY_CUSTOMER, getCallOptions(), request);
    }

    /**
     * <pre>
     *10. 更新销售占用信息
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSaleOccupationResp updateSaleOccupation(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSaleOccupationReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_SALE_OCCUPATION, getCallOptions(), request);
    }

    /**
     * <pre>
     *11. 更新已售数量信息
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSoldQuantityResp updateSoldQuantity(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSoldQuantityReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_SOLD_QUANTITY, getCallOptions(), request);
    }

    /**
     * <pre>
     *12. 获取有效客户列表
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListConditionsResp selectSalePlanItemListConditions(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListConditionsReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_SALE_PLAN_ITEM_LIST_CONDITIONS, getCallOptions(), request);
    }

    /**
     * <pre>
     *13. 通过itemid;获取可售信息
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetOnSaleQuantityResp getOnSaleQuantity(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetOnSaleQuantityReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_ON_SALE_QUANTITY, getCallOptions(), request);
    }

    /**
     * <pre>
     *14. 通过itemid;获取可售信息
     * </pre>
     */
    public com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelCustomerOrderResp cancelCustomerOrder(com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelCustomerOrderReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CANCEL_CUSTOMER_ORDER, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PlanSaleItemServiceFutureStub extends io.grpc.stub.AbstractStub<PlanSaleItemServiceFutureStub> {
    private PlanSaleItemServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PlanSaleItemServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PlanSaleItemServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PlanSaleItemServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1. 插入销售计划列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CreateSalePlanItemResp> createSalePlanItem(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CreateSalePlanItemReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CREATE_SALE_PLAN_ITEM, getCallOptions()), request);
    }

    /**
     * <pre>
     *2. 获取预销客户列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListResp> selectSalePlanItemList(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_SALE_PLAN_ITEM_LIST, getCallOptions()), request);
    }

    /**
     * <pre>
     *3. 获取可编辑的信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetEditItemInfoResp> getEditItemInfo(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetEditItemInfoReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_EDIT_ITEM_INFO, getCallOptions()), request);
    }

    /**
     * <pre>
     *4. 更新客户销售计划信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.EditSaleItemResp> editSaleItem(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.EditSaleItemReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_EDIT_SALE_ITEM, getCallOptions()), request);
    }

    /**
     * <pre>
     *5. 取消客户计划销售单
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanItemResp> cancelPlanItem(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanItemReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CANCEL_PLAN_ITEM, getCallOptions()), request);
    }

    /**
     * <pre>
     *6. 通过预售单取消预售详情
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanResp> cancelPlan(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CANCEL_PLAN, getCallOptions()), request);
    }

    /**
     * <pre>
     *7. 获取客户某个有效的货品信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetCustomerPlanInfoResp> getCustomerPlanInfo(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetCustomerPlanInfoReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_CUSTOMER_PLAN_INFO, getCallOptions()), request);
    }

    /**
     * <pre>
     *8. 获取该货品分配给客户的所有记录数
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetProductCustomerResp> getProductCustomer(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetProductCustomerReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_PRODUCT_CUSTOMER, getCallOptions()), request);
    }

    /**
     * <pre>
     *9. 获取客户有效的货品信息列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SearchProductByCustomerResp> searchProductByCustomer(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SearchProductByCustomerReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SEARCH_PRODUCT_BY_CUSTOMER, getCallOptions()), request);
    }

    /**
     * <pre>
     *10. 更新销售占用信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSaleOccupationResp> updateSaleOccupation(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSaleOccupationReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_SALE_OCCUPATION, getCallOptions()), request);
    }

    /**
     * <pre>
     *11. 更新已售数量信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSoldQuantityResp> updateSoldQuantity(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSoldQuantityReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_SOLD_QUANTITY, getCallOptions()), request);
    }

    /**
     * <pre>
     *12. 获取有效客户列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListConditionsResp> selectSalePlanItemListConditions(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListConditionsReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_SALE_PLAN_ITEM_LIST_CONDITIONS, getCallOptions()), request);
    }

    /**
     * <pre>
     *13. 通过itemid;获取可售信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetOnSaleQuantityResp> getOnSaleQuantity(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetOnSaleQuantityReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_ON_SALE_QUANTITY, getCallOptions()), request);
    }

    /**
     * <pre>
     *14. 通过itemid;获取可售信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelCustomerOrderResp> cancelCustomerOrder(
        com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelCustomerOrderReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CANCEL_CUSTOMER_ORDER, getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_SALE_PLAN_ITEM = 0;
  private static final int METHODID_SELECT_SALE_PLAN_ITEM_LIST = 1;
  private static final int METHODID_GET_EDIT_ITEM_INFO = 2;
  private static final int METHODID_EDIT_SALE_ITEM = 3;
  private static final int METHODID_CANCEL_PLAN_ITEM = 4;
  private static final int METHODID_CANCEL_PLAN = 5;
  private static final int METHODID_GET_CUSTOMER_PLAN_INFO = 6;
  private static final int METHODID_GET_PRODUCT_CUSTOMER = 7;
  private static final int METHODID_SEARCH_PRODUCT_BY_CUSTOMER = 8;
  private static final int METHODID_UPDATE_SALE_OCCUPATION = 9;
  private static final int METHODID_UPDATE_SOLD_QUANTITY = 10;
  private static final int METHODID_SELECT_SALE_PLAN_ITEM_LIST_CONDITIONS = 11;
  private static final int METHODID_GET_ON_SALE_QUANTITY = 12;
  private static final int METHODID_CANCEL_CUSTOMER_ORDER = 13;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PlanSaleItemServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PlanSaleItemServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_SALE_PLAN_ITEM:
          serviceImpl.createSalePlanItem((com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CreateSalePlanItemReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CreateSalePlanItemResp>) responseObserver);
          break;
        case METHODID_SELECT_SALE_PLAN_ITEM_LIST:
          serviceImpl.selectSalePlanItemList((com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListResp>) responseObserver);
          break;
        case METHODID_GET_EDIT_ITEM_INFO:
          serviceImpl.getEditItemInfo((com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetEditItemInfoReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetEditItemInfoResp>) responseObserver);
          break;
        case METHODID_EDIT_SALE_ITEM:
          serviceImpl.editSaleItem((com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.EditSaleItemReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.EditSaleItemResp>) responseObserver);
          break;
        case METHODID_CANCEL_PLAN_ITEM:
          serviceImpl.cancelPlanItem((com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanItemReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanItemResp>) responseObserver);
          break;
        case METHODID_CANCEL_PLAN:
          serviceImpl.cancelPlan((com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelPlanResp>) responseObserver);
          break;
        case METHODID_GET_CUSTOMER_PLAN_INFO:
          serviceImpl.getCustomerPlanInfo((com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetCustomerPlanInfoReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetCustomerPlanInfoResp>) responseObserver);
          break;
        case METHODID_GET_PRODUCT_CUSTOMER:
          serviceImpl.getProductCustomer((com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetProductCustomerReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetProductCustomerResp>) responseObserver);
          break;
        case METHODID_SEARCH_PRODUCT_BY_CUSTOMER:
          serviceImpl.searchProductByCustomer((com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SearchProductByCustomerReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SearchProductByCustomerResp>) responseObserver);
          break;
        case METHODID_UPDATE_SALE_OCCUPATION:
          serviceImpl.updateSaleOccupation((com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSaleOccupationReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSaleOccupationResp>) responseObserver);
          break;
        case METHODID_UPDATE_SOLD_QUANTITY:
          serviceImpl.updateSoldQuantity((com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSoldQuantityReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.UpdateSoldQuantityResp>) responseObserver);
          break;
        case METHODID_SELECT_SALE_PLAN_ITEM_LIST_CONDITIONS:
          serviceImpl.selectSalePlanItemListConditions((com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListConditionsReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.SelectSalePlanItemListConditionsResp>) responseObserver);
          break;
        case METHODID_GET_ON_SALE_QUANTITY:
          serviceImpl.getOnSaleQuantity((com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetOnSaleQuantityReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.GetOnSaleQuantityResp>) responseObserver);
          break;
        case METHODID_CANCEL_CUSTOMER_ORDER:
          serviceImpl.cancelCustomerOrder((com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelCustomerOrderReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.CancelCustomerOrderResp>) responseObserver);
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

  private static final class PlanSaleItemServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PlanSaleItemServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PlanSaleItemServiceDescriptorSupplier())
              .addMethod(METHOD_CREATE_SALE_PLAN_ITEM)
              .addMethod(METHOD_SELECT_SALE_PLAN_ITEM_LIST)
              .addMethod(METHOD_GET_EDIT_ITEM_INFO)
              .addMethod(METHOD_EDIT_SALE_ITEM)
              .addMethod(METHOD_CANCEL_PLAN_ITEM)
              .addMethod(METHOD_CANCEL_PLAN)
              .addMethod(METHOD_GET_CUSTOMER_PLAN_INFO)
              .addMethod(METHOD_GET_PRODUCT_CUSTOMER)
              .addMethod(METHOD_SEARCH_PRODUCT_BY_CUSTOMER)
              .addMethod(METHOD_UPDATE_SALE_OCCUPATION)
              .addMethod(METHOD_UPDATE_SOLD_QUANTITY)
              .addMethod(METHOD_SELECT_SALE_PLAN_ITEM_LIST_CONDITIONS)
              .addMethod(METHOD_GET_ON_SALE_QUANTITY)
              .addMethod(METHOD_CANCEL_CUSTOMER_ORDER)
              .build();
        }
      }
    }
    return result;
  }
}
