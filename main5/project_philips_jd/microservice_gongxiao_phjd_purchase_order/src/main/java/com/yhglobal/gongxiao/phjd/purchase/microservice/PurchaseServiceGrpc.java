package com.yhglobal.gongxiao.phjd.purchase.microservice;

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
    comments = "Source: Purchase.proto")
public final class PurchaseServiceGrpc {

  private PurchaseServiceGrpc() {}

  public static final String SERVICE_NAME = "PurchaseService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseDetailReq,
      com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseDetailResp> METHOD_GET_PURCHASE_DETAIL =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseDetailReq, com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseDetailResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PurchaseService", "getPurchaseDetail"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseDetailReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseDetailResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetItemListReq,
      com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetItemListResp> METHOD_GET_ITEM_LIST =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetItemListReq, com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetItemListResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PurchaseService", "getItemList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetItemListReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetItemListResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseOrderListReq,
      com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseOrderListResp> METHOD_GET_PURCHASE_ORDER_LIST =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseOrderListReq, com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseOrderListResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PurchaseService", "getPurchaseOrderList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseOrderListReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseOrderListResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetInboundItemListReq,
      com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetInboundItemListResp> METHOD_GET_INBOUND_ITEM_LIST =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetInboundItemListReq, com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetInboundItemListResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PurchaseService", "getInboundItemList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetInboundItemListReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetInboundItemListResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GeneratePurchaseOrderReq,
      com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GeneratePurchaseOrderResp> METHOD_GENERATE_PURCHASE_ORDER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GeneratePurchaseOrderReq, com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GeneratePurchaseOrderResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PurchaseService", "generatePurchaseOrder"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GeneratePurchaseOrderReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GeneratePurchaseOrderResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.PlanInboundReq,
      com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.PlanInboundResp> METHOD_PLAN_INBOUND =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.PlanInboundReq, com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.PlanInboundResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PurchaseService", "planInbound"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.PlanInboundReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.PlanInboundResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseEditDetailReq,
      com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseEditDetailResp> METHOD_GET_PURCHASE_EDIT_DETAIL =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseEditDetailReq, com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseEditDetailResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PurchaseService", "getPurchaseEditDetail"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseEditDetailReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseEditDetailResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.UpdatePurchaseOrderReq,
      com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.UpdatePurchaseOrderResp> METHOD_UPDATE_PURCHASE_ORDER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.UpdatePurchaseOrderReq, com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.UpdatePurchaseOrderResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PurchaseService", "updatePurchaseOrder"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.UpdatePurchaseOrderReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.UpdatePurchaseOrderResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.AddFileInfoReq,
      com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.AddFileInfoResp> METHOD_ADD_FILE_INFO =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.AddFileInfoReq, com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.AddFileInfoResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PurchaseService", "addFileInfo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.AddFileInfoReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.AddFileInfoResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CancelPurchaseOrderReq,
      com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CancelPurchaseOrderResp> METHOD_CANCEL_PURCHASE_ORDER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CancelPurchaseOrderReq, com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CancelPurchaseOrderResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PurchaseService", "cancelPurchaseOrder"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CancelPurchaseOrderReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CancelPurchaseOrderResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CreatePurchaseOrderReq,
      com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CreatePurchaseOrderResp> METHOD_CREATE_PURCHASE_ORDER =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CreatePurchaseOrderReq, com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CreatePurchaseOrderResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "PurchaseService", "createPurchaseOrder"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CreatePurchaseOrderReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CreatePurchaseOrderResp.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PurchaseServiceStub newStub(io.grpc.Channel channel) {
    return new PurchaseServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PurchaseServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new PurchaseServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PurchaseServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new PurchaseServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class PurchaseServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1. 通过采购单号获取采购详情
     * </pre>
     */
    public void getPurchaseDetail(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseDetailReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseDetailResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_PURCHASE_DETAIL, responseObserver);
    }

    /**
     * <pre>
     *2. 获取采购单货品列表信息
     * </pre>
     */
    public void getItemList(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetItemListReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetItemListResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_ITEM_LIST, responseObserver);
    }

    /**
     * <pre>
     *3. 获取采购订单信息
     * </pre>
     */
    public void getPurchaseOrderList(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseOrderListReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseOrderListResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_PURCHASE_ORDER_LIST, responseObserver);
    }

    /**
     * <pre>
     *4. 获取入库货品列表
     * </pre>
     */
    public void getInboundItemList(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetInboundItemListReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetInboundItemListResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_INBOUND_ITEM_LIST, responseObserver);
    }

    /**
     * <pre>
     *5. 生成采购单
     * </pre>
     */
    public void generatePurchaseOrder(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GeneratePurchaseOrderReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GeneratePurchaseOrderResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GENERATE_PURCHASE_ORDER, responseObserver);
    }

    /**
     * <pre>
     *6. 生成预约入库单
     * </pre>
     */
    public void planInbound(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.PlanInboundReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.PlanInboundResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_PLAN_INBOUND, responseObserver);
    }

    /**
     * <pre>
     *7. 获取采购详情
     * </pre>
     */
    public void getPurchaseEditDetail(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseEditDetailReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseEditDetailResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_PURCHASE_EDIT_DETAIL, responseObserver);
    }

    /**
     * <pre>
     *8. 更新采购单信息
     * </pre>
     */
    public void updatePurchaseOrder(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.UpdatePurchaseOrderReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.UpdatePurchaseOrderResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_PURCHASE_ORDER, responseObserver);
    }

    /**
     * <pre>
     *9. 添加文件信息到数据库
     * </pre>
     */
    public void addFileInfo(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.AddFileInfoReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.AddFileInfoResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_ADD_FILE_INFO, responseObserver);
    }

    /**
     * <pre>
     *10. 取消采购单
     * </pre>
     */
    public void cancelPurchaseOrder(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CancelPurchaseOrderReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CancelPurchaseOrderResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CANCEL_PURCHASE_ORDER, responseObserver);
    }

    /**
     * <pre>
     *11. 新增采购单
     * </pre>
     */
    public void createPurchaseOrder(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CreatePurchaseOrderReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CreatePurchaseOrderResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CREATE_PURCHASE_ORDER, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_PURCHASE_DETAIL,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseDetailReq,
                com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseDetailResp>(
                  this, METHODID_GET_PURCHASE_DETAIL)))
          .addMethod(
            METHOD_GET_ITEM_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetItemListReq,
                com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetItemListResp>(
                  this, METHODID_GET_ITEM_LIST)))
          .addMethod(
            METHOD_GET_PURCHASE_ORDER_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseOrderListReq,
                com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseOrderListResp>(
                  this, METHODID_GET_PURCHASE_ORDER_LIST)))
          .addMethod(
            METHOD_GET_INBOUND_ITEM_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetInboundItemListReq,
                com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetInboundItemListResp>(
                  this, METHODID_GET_INBOUND_ITEM_LIST)))
          .addMethod(
            METHOD_GENERATE_PURCHASE_ORDER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GeneratePurchaseOrderReq,
                com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GeneratePurchaseOrderResp>(
                  this, METHODID_GENERATE_PURCHASE_ORDER)))
          .addMethod(
            METHOD_PLAN_INBOUND,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.PlanInboundReq,
                com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.PlanInboundResp>(
                  this, METHODID_PLAN_INBOUND)))
          .addMethod(
            METHOD_GET_PURCHASE_EDIT_DETAIL,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseEditDetailReq,
                com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseEditDetailResp>(
                  this, METHODID_GET_PURCHASE_EDIT_DETAIL)))
          .addMethod(
            METHOD_UPDATE_PURCHASE_ORDER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.UpdatePurchaseOrderReq,
                com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.UpdatePurchaseOrderResp>(
                  this, METHODID_UPDATE_PURCHASE_ORDER)))
          .addMethod(
            METHOD_ADD_FILE_INFO,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.AddFileInfoReq,
                com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.AddFileInfoResp>(
                  this, METHODID_ADD_FILE_INFO)))
          .addMethod(
            METHOD_CANCEL_PURCHASE_ORDER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CancelPurchaseOrderReq,
                com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CancelPurchaseOrderResp>(
                  this, METHODID_CANCEL_PURCHASE_ORDER)))
          .addMethod(
            METHOD_CREATE_PURCHASE_ORDER,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CreatePurchaseOrderReq,
                com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CreatePurchaseOrderResp>(
                  this, METHODID_CREATE_PURCHASE_ORDER)))
          .build();
    }
  }

  /**
   */
  public static final class PurchaseServiceStub extends io.grpc.stub.AbstractStub<PurchaseServiceStub> {
    private PurchaseServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PurchaseServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PurchaseServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PurchaseServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1. 通过采购单号获取采购详情
     * </pre>
     */
    public void getPurchaseDetail(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseDetailReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseDetailResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_PURCHASE_DETAIL, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *2. 获取采购单货品列表信息
     * </pre>
     */
    public void getItemList(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetItemListReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetItemListResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_ITEM_LIST, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *3. 获取采购订单信息
     * </pre>
     */
    public void getPurchaseOrderList(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseOrderListReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseOrderListResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_PURCHASE_ORDER_LIST, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *4. 获取入库货品列表
     * </pre>
     */
    public void getInboundItemList(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetInboundItemListReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetInboundItemListResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_INBOUND_ITEM_LIST, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *5. 生成采购单
     * </pre>
     */
    public void generatePurchaseOrder(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GeneratePurchaseOrderReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GeneratePurchaseOrderResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GENERATE_PURCHASE_ORDER, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *6. 生成预约入库单
     * </pre>
     */
    public void planInbound(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.PlanInboundReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.PlanInboundResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_PLAN_INBOUND, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *7. 获取采购详情
     * </pre>
     */
    public void getPurchaseEditDetail(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseEditDetailReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseEditDetailResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_PURCHASE_EDIT_DETAIL, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *8. 更新采购单信息
     * </pre>
     */
    public void updatePurchaseOrder(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.UpdatePurchaseOrderReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.UpdatePurchaseOrderResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_PURCHASE_ORDER, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *9. 添加文件信息到数据库
     * </pre>
     */
    public void addFileInfo(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.AddFileInfoReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.AddFileInfoResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_ADD_FILE_INFO, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *10. 取消采购单
     * </pre>
     */
    public void cancelPurchaseOrder(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CancelPurchaseOrderReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CancelPurchaseOrderResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CANCEL_PURCHASE_ORDER, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *11. 新增采购单
     * </pre>
     */
    public void createPurchaseOrder(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CreatePurchaseOrderReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CreatePurchaseOrderResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CREATE_PURCHASE_ORDER, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PurchaseServiceBlockingStub extends io.grpc.stub.AbstractStub<PurchaseServiceBlockingStub> {
    private PurchaseServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PurchaseServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PurchaseServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PurchaseServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1. 通过采购单号获取采购详情
     * </pre>
     */
    public com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseDetailResp getPurchaseDetail(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseDetailReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_PURCHASE_DETAIL, getCallOptions(), request);
    }

    /**
     * <pre>
     *2. 获取采购单货品列表信息
     * </pre>
     */
    public com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetItemListResp getItemList(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetItemListReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_ITEM_LIST, getCallOptions(), request);
    }

    /**
     * <pre>
     *3. 获取采购订单信息
     * </pre>
     */
    public com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseOrderListResp getPurchaseOrderList(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseOrderListReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_PURCHASE_ORDER_LIST, getCallOptions(), request);
    }

    /**
     * <pre>
     *4. 获取入库货品列表
     * </pre>
     */
    public com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetInboundItemListResp getInboundItemList(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetInboundItemListReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_INBOUND_ITEM_LIST, getCallOptions(), request);
    }

    /**
     * <pre>
     *5. 生成采购单
     * </pre>
     */
    public com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GeneratePurchaseOrderResp generatePurchaseOrder(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GeneratePurchaseOrderReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GENERATE_PURCHASE_ORDER, getCallOptions(), request);
    }

    /**
     * <pre>
     *6. 生成预约入库单
     * </pre>
     */
    public com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.PlanInboundResp planInbound(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.PlanInboundReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_PLAN_INBOUND, getCallOptions(), request);
    }

    /**
     * <pre>
     *7. 获取采购详情
     * </pre>
     */
    public com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseEditDetailResp getPurchaseEditDetail(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseEditDetailReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_PURCHASE_EDIT_DETAIL, getCallOptions(), request);
    }

    /**
     * <pre>
     *8. 更新采购单信息
     * </pre>
     */
    public com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.UpdatePurchaseOrderResp updatePurchaseOrder(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.UpdatePurchaseOrderReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_PURCHASE_ORDER, getCallOptions(), request);
    }

    /**
     * <pre>
     *9. 添加文件信息到数据库
     * </pre>
     */
    public com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.AddFileInfoResp addFileInfo(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.AddFileInfoReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_ADD_FILE_INFO, getCallOptions(), request);
    }

    /**
     * <pre>
     *10. 取消采购单
     * </pre>
     */
    public com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CancelPurchaseOrderResp cancelPurchaseOrder(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CancelPurchaseOrderReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CANCEL_PURCHASE_ORDER, getCallOptions(), request);
    }

    /**
     * <pre>
     *11. 新增采购单
     * </pre>
     */
    public com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CreatePurchaseOrderResp createPurchaseOrder(com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CreatePurchaseOrderReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CREATE_PURCHASE_ORDER, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PurchaseServiceFutureStub extends io.grpc.stub.AbstractStub<PurchaseServiceFutureStub> {
    private PurchaseServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PurchaseServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PurchaseServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PurchaseServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1. 通过采购单号获取采购详情
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseDetailResp> getPurchaseDetail(
        com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseDetailReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_PURCHASE_DETAIL, getCallOptions()), request);
    }

    /**
     * <pre>
     *2. 获取采购单货品列表信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetItemListResp> getItemList(
        com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetItemListReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_ITEM_LIST, getCallOptions()), request);
    }

    /**
     * <pre>
     *3. 获取采购订单信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseOrderListResp> getPurchaseOrderList(
        com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseOrderListReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_PURCHASE_ORDER_LIST, getCallOptions()), request);
    }

    /**
     * <pre>
     *4. 获取入库货品列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetInboundItemListResp> getInboundItemList(
        com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetInboundItemListReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_INBOUND_ITEM_LIST, getCallOptions()), request);
    }

    /**
     * <pre>
     *5. 生成采购单
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GeneratePurchaseOrderResp> generatePurchaseOrder(
        com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GeneratePurchaseOrderReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GENERATE_PURCHASE_ORDER, getCallOptions()), request);
    }

    /**
     * <pre>
     *6. 生成预约入库单
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.PlanInboundResp> planInbound(
        com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.PlanInboundReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_PLAN_INBOUND, getCallOptions()), request);
    }

    /**
     * <pre>
     *7. 获取采购详情
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseEditDetailResp> getPurchaseEditDetail(
        com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseEditDetailReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_PURCHASE_EDIT_DETAIL, getCallOptions()), request);
    }

    /**
     * <pre>
     *8. 更新采购单信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.UpdatePurchaseOrderResp> updatePurchaseOrder(
        com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.UpdatePurchaseOrderReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_PURCHASE_ORDER, getCallOptions()), request);
    }

    /**
     * <pre>
     *9. 添加文件信息到数据库
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.AddFileInfoResp> addFileInfo(
        com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.AddFileInfoReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_ADD_FILE_INFO, getCallOptions()), request);
    }

    /**
     * <pre>
     *10. 取消采购单
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CancelPurchaseOrderResp> cancelPurchaseOrder(
        com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CancelPurchaseOrderReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CANCEL_PURCHASE_ORDER, getCallOptions()), request);
    }

    /**
     * <pre>
     *11. 新增采购单
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CreatePurchaseOrderResp> createPurchaseOrder(
        com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CreatePurchaseOrderReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CREATE_PURCHASE_ORDER, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_PURCHASE_DETAIL = 0;
  private static final int METHODID_GET_ITEM_LIST = 1;
  private static final int METHODID_GET_PURCHASE_ORDER_LIST = 2;
  private static final int METHODID_GET_INBOUND_ITEM_LIST = 3;
  private static final int METHODID_GENERATE_PURCHASE_ORDER = 4;
  private static final int METHODID_PLAN_INBOUND = 5;
  private static final int METHODID_GET_PURCHASE_EDIT_DETAIL = 6;
  private static final int METHODID_UPDATE_PURCHASE_ORDER = 7;
  private static final int METHODID_ADD_FILE_INFO = 8;
  private static final int METHODID_CANCEL_PURCHASE_ORDER = 9;
  private static final int METHODID_CREATE_PURCHASE_ORDER = 10;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PurchaseServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PurchaseServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_PURCHASE_DETAIL:
          serviceImpl.getPurchaseDetail((com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseDetailReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseDetailResp>) responseObserver);
          break;
        case METHODID_GET_ITEM_LIST:
          serviceImpl.getItemList((com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetItemListReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetItemListResp>) responseObserver);
          break;
        case METHODID_GET_PURCHASE_ORDER_LIST:
          serviceImpl.getPurchaseOrderList((com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseOrderListReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseOrderListResp>) responseObserver);
          break;
        case METHODID_GET_INBOUND_ITEM_LIST:
          serviceImpl.getInboundItemList((com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetInboundItemListReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetInboundItemListResp>) responseObserver);
          break;
        case METHODID_GENERATE_PURCHASE_ORDER:
          serviceImpl.generatePurchaseOrder((com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GeneratePurchaseOrderReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GeneratePurchaseOrderResp>) responseObserver);
          break;
        case METHODID_PLAN_INBOUND:
          serviceImpl.planInbound((com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.PlanInboundReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.PlanInboundResp>) responseObserver);
          break;
        case METHODID_GET_PURCHASE_EDIT_DETAIL:
          serviceImpl.getPurchaseEditDetail((com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseEditDetailReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.GetPurchaseEditDetailResp>) responseObserver);
          break;
        case METHODID_UPDATE_PURCHASE_ORDER:
          serviceImpl.updatePurchaseOrder((com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.UpdatePurchaseOrderReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.UpdatePurchaseOrderResp>) responseObserver);
          break;
        case METHODID_ADD_FILE_INFO:
          serviceImpl.addFileInfo((com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.AddFileInfoReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.AddFileInfoResp>) responseObserver);
          break;
        case METHODID_CANCEL_PURCHASE_ORDER:
          serviceImpl.cancelPurchaseOrder((com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CancelPurchaseOrderReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CancelPurchaseOrderResp>) responseObserver);
          break;
        case METHODID_CREATE_PURCHASE_ORDER:
          serviceImpl.createPurchaseOrder((com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CreatePurchaseOrderReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.CreatePurchaseOrderResp>) responseObserver);
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

  private static final class PurchaseServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PurchaseServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PurchaseServiceDescriptorSupplier())
              .addMethod(METHOD_GET_PURCHASE_DETAIL)
              .addMethod(METHOD_GET_ITEM_LIST)
              .addMethod(METHOD_GET_PURCHASE_ORDER_LIST)
              .addMethod(METHOD_GET_INBOUND_ITEM_LIST)
              .addMethod(METHOD_GENERATE_PURCHASE_ORDER)
              .addMethod(METHOD_PLAN_INBOUND)
              .addMethod(METHOD_GET_PURCHASE_EDIT_DETAIL)
              .addMethod(METHOD_UPDATE_PURCHASE_ORDER)
              .addMethod(METHOD_ADD_FILE_INFO)
              .addMethod(METHOD_CANCEL_PURCHASE_ORDER)
              .addMethod(METHOD_CREATE_PURCHASE_ORDER)
              .build();
        }
      }
    }
    return result;
  }
}
