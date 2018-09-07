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
    comments = "Source: yhglobalcouponwriteoff.proto")
public final class YhglobalCouponWriteoffServiceGrpc {

  private YhglobalCouponWriteoffServiceGrpc() {}

  public static final String SERVICE_NAME = "YhglobalCouponWriteoffService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffReq,
      com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp> METHOD_YH_COUPON_WRITEROFF =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffReq, com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalCouponWriteoffService", "yhCouponWriteroff"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageReq,
      com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageResp> METHOD_SELECT_BY_MANY_CONDIITONS_HAS_PAGE =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageReq, com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalCouponWriteoffService", "selectByManyCondiitonsHasPage"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageReq,
      com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageResp> METHOD_SELECT_BY_MANY_CONDIITONS_NO_PAGE =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageReq, com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalCouponWriteoffService", "selectByManyCondiitonsNoPage"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchReq,
      com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchResp> METHOD_COUPON_RECEIVE_CANCEL_CONFIRM_BATCH =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchReq, com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalCouponWriteoffService", "couponReceiveCancelConfirmBatch"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdReq,
      com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdResp> METHOD_SELECT_YHGLOBAL_COUPON_LEDGER_BY_PURCHASE_COUPON_LEDGER_ID =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdReq, com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalCouponWriteoffService", "selectYhglobalCouponLedgerByPurchaseCouponLedgerId"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmReq,
      com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmResp> METHOD_SEARCH_COUPON_CONFIRM =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmReq, com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalCouponWriteoffService", "searchCouponConfirm"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmResp.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static YhglobalCouponWriteoffServiceStub newStub(io.grpc.Channel channel) {
    return new YhglobalCouponWriteoffServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static YhglobalCouponWriteoffServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new YhglobalCouponWriteoffServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static YhglobalCouponWriteoffServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new YhglobalCouponWriteoffServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class YhglobalCouponWriteoffServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 返利确认
     * </pre>
     */
    public void yhCouponWriteroff(com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_YH_COUPON_WRITEROFF, responseObserver);
    }

    /**
     * <pre>
     * 多条件分页查询返利流水
     * </pre>
     */
    public void selectByManyCondiitonsHasPage(com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_BY_MANY_CONDIITONS_HAS_PAGE, responseObserver);
    }

    /**
     * <pre>
     * 多条件无分页查询返利流水
     * </pre>
     */
    public void selectByManyCondiitonsNoPage(com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_BY_MANY_CONDIITONS_NO_PAGE, responseObserver);
    }

    /**
     * <pre>
     * 批量取消确认
     * </pre>
     */
    public void couponReceiveCancelConfirmBatch(com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_COUPON_RECEIVE_CANCEL_CONFIRM_BATCH, responseObserver);
    }

    /**
     * <pre>
     * 根据选中的返利ID查询应收返利
     * </pre>
     */
    public void selectYhglobalCouponLedgerByPurchaseCouponLedgerId(com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_YHGLOBAL_COUPON_LEDGER_BY_PURCHASE_COUPON_LEDGER_ID, responseObserver);
    }

    /**
     * <pre>
     * 分页查询返利确认记录
     * </pre>
     */
    public void searchCouponConfirm(com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SEARCH_COUPON_CONFIRM, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_YH_COUPON_WRITEROFF,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffReq,
                com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp>(
                  this, METHODID_YH_COUPON_WRITEROFF)))
          .addMethod(
            METHOD_SELECT_BY_MANY_CONDIITONS_HAS_PAGE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageReq,
                com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageResp>(
                  this, METHODID_SELECT_BY_MANY_CONDIITONS_HAS_PAGE)))
          .addMethod(
            METHOD_SELECT_BY_MANY_CONDIITONS_NO_PAGE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageReq,
                com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageResp>(
                  this, METHODID_SELECT_BY_MANY_CONDIITONS_NO_PAGE)))
          .addMethod(
            METHOD_COUPON_RECEIVE_CANCEL_CONFIRM_BATCH,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchReq,
                com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchResp>(
                  this, METHODID_COUPON_RECEIVE_CANCEL_CONFIRM_BATCH)))
          .addMethod(
            METHOD_SELECT_YHGLOBAL_COUPON_LEDGER_BY_PURCHASE_COUPON_LEDGER_ID,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdReq,
                com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdResp>(
                  this, METHODID_SELECT_YHGLOBAL_COUPON_LEDGER_BY_PURCHASE_COUPON_LEDGER_ID)))
          .addMethod(
            METHOD_SEARCH_COUPON_CONFIRM,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmReq,
                com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmResp>(
                  this, METHODID_SEARCH_COUPON_CONFIRM)))
          .build();
    }
  }

  /**
   */
  public static final class YhglobalCouponWriteoffServiceStub extends io.grpc.stub.AbstractStub<YhglobalCouponWriteoffServiceStub> {
    private YhglobalCouponWriteoffServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private YhglobalCouponWriteoffServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected YhglobalCouponWriteoffServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new YhglobalCouponWriteoffServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 返利确认
     * </pre>
     */
    public void yhCouponWriteroff(com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_YH_COUPON_WRITEROFF, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 多条件分页查询返利流水
     * </pre>
     */
    public void selectByManyCondiitonsHasPage(com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_BY_MANY_CONDIITONS_HAS_PAGE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 多条件无分页查询返利流水
     * </pre>
     */
    public void selectByManyCondiitonsNoPage(com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_BY_MANY_CONDIITONS_NO_PAGE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 批量取消确认
     * </pre>
     */
    public void couponReceiveCancelConfirmBatch(com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_COUPON_RECEIVE_CANCEL_CONFIRM_BATCH, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 根据选中的返利ID查询应收返利
     * </pre>
     */
    public void selectYhglobalCouponLedgerByPurchaseCouponLedgerId(com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_YHGLOBAL_COUPON_LEDGER_BY_PURCHASE_COUPON_LEDGER_ID, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 分页查询返利确认记录
     * </pre>
     */
    public void searchCouponConfirm(com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SEARCH_COUPON_CONFIRM, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class YhglobalCouponWriteoffServiceBlockingStub extends io.grpc.stub.AbstractStub<YhglobalCouponWriteoffServiceBlockingStub> {
    private YhglobalCouponWriteoffServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private YhglobalCouponWriteoffServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected YhglobalCouponWriteoffServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new YhglobalCouponWriteoffServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 返利确认
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp yhCouponWriteroff(com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_YH_COUPON_WRITEROFF, getCallOptions(), request);
    }

    /**
     * <pre>
     * 多条件分页查询返利流水
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageResp selectByManyCondiitonsHasPage(com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_BY_MANY_CONDIITONS_HAS_PAGE, getCallOptions(), request);
    }

    /**
     * <pre>
     * 多条件无分页查询返利流水
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageResp selectByManyCondiitonsNoPage(com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_BY_MANY_CONDIITONS_NO_PAGE, getCallOptions(), request);
    }

    /**
     * <pre>
     * 批量取消确认
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchResp couponReceiveCancelConfirmBatch(com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_COUPON_RECEIVE_CANCEL_CONFIRM_BATCH, getCallOptions(), request);
    }

    /**
     * <pre>
     * 根据选中的返利ID查询应收返利
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdResp selectYhglobalCouponLedgerByPurchaseCouponLedgerId(com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_YHGLOBAL_COUPON_LEDGER_BY_PURCHASE_COUPON_LEDGER_ID, getCallOptions(), request);
    }

    /**
     * <pre>
     * 分页查询返利确认记录
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmResp searchCouponConfirm(com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SEARCH_COUPON_CONFIRM, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class YhglobalCouponWriteoffServiceFutureStub extends io.grpc.stub.AbstractStub<YhglobalCouponWriteoffServiceFutureStub> {
    private YhglobalCouponWriteoffServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private YhglobalCouponWriteoffServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected YhglobalCouponWriteoffServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new YhglobalCouponWriteoffServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 返利确认
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp> yhCouponWriteroff(
        com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_YH_COUPON_WRITEROFF, getCallOptions()), request);
    }

    /**
     * <pre>
     * 多条件分页查询返利流水
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageResp> selectByManyCondiitonsHasPage(
        com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_BY_MANY_CONDIITONS_HAS_PAGE, getCallOptions()), request);
    }

    /**
     * <pre>
     * 多条件无分页查询返利流水
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageResp> selectByManyCondiitonsNoPage(
        com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_BY_MANY_CONDIITONS_NO_PAGE, getCallOptions()), request);
    }

    /**
     * <pre>
     * 批量取消确认
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchResp> couponReceiveCancelConfirmBatch(
        com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_COUPON_RECEIVE_CANCEL_CONFIRM_BATCH, getCallOptions()), request);
    }

    /**
     * <pre>
     * 根据选中的返利ID查询应收返利
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdResp> selectYhglobalCouponLedgerByPurchaseCouponLedgerId(
        com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_YHGLOBAL_COUPON_LEDGER_BY_PURCHASE_COUPON_LEDGER_ID, getCallOptions()), request);
    }

    /**
     * <pre>
     * 分页查询返利确认记录
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmResp> searchCouponConfirm(
        com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SEARCH_COUPON_CONFIRM, getCallOptions()), request);
    }
  }

  private static final int METHODID_YH_COUPON_WRITEROFF = 0;
  private static final int METHODID_SELECT_BY_MANY_CONDIITONS_HAS_PAGE = 1;
  private static final int METHODID_SELECT_BY_MANY_CONDIITONS_NO_PAGE = 2;
  private static final int METHODID_COUPON_RECEIVE_CANCEL_CONFIRM_BATCH = 3;
  private static final int METHODID_SELECT_YHGLOBAL_COUPON_LEDGER_BY_PURCHASE_COUPON_LEDGER_ID = 4;
  private static final int METHODID_SEARCH_COUPON_CONFIRM = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final YhglobalCouponWriteoffServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(YhglobalCouponWriteoffServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_YH_COUPON_WRITEROFF:
          serviceImpl.yhCouponWriteroff((com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp>) responseObserver);
          break;
        case METHODID_SELECT_BY_MANY_CONDIITONS_HAS_PAGE:
          serviceImpl.selectByManyCondiitonsHasPage((com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageResp>) responseObserver);
          break;
        case METHODID_SELECT_BY_MANY_CONDIITONS_NO_PAGE:
          serviceImpl.selectByManyCondiitonsNoPage((com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageResp>) responseObserver);
          break;
        case METHODID_COUPON_RECEIVE_CANCEL_CONFIRM_BATCH:
          serviceImpl.couponReceiveCancelConfirmBatch((com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchResp>) responseObserver);
          break;
        case METHODID_SELECT_YHGLOBAL_COUPON_LEDGER_BY_PURCHASE_COUPON_LEDGER_ID:
          serviceImpl.selectYhglobalCouponLedgerByPurchaseCouponLedgerId((com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdResp>) responseObserver);
          break;
        case METHODID_SEARCH_COUPON_CONFIRM:
          serviceImpl.searchCouponConfirm((com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmResp>) responseObserver);
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

  private static final class YhglobalCouponWriteoffServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.payment.project.microservice.YhglobalCouponWriteoffServiceStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (YhglobalCouponWriteoffServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new YhglobalCouponWriteoffServiceDescriptorSupplier())
              .addMethod(METHOD_YH_COUPON_WRITEROFF)
              .addMethod(METHOD_SELECT_BY_MANY_CONDIITONS_HAS_PAGE)
              .addMethod(METHOD_SELECT_BY_MANY_CONDIITONS_NO_PAGE)
              .addMethod(METHOD_COUPON_RECEIVE_CANCEL_CONFIRM_BATCH)
              .addMethod(METHOD_SELECT_YHGLOBAL_COUPON_LEDGER_BY_PURCHASE_COUPON_LEDGER_ID)
              .addMethod(METHOD_SEARCH_COUPON_CONFIRM)
              .build();
        }
      }
    }
    return result;
  }
}
