package com.yhglobal.gongxiao.accountmanage.microservice;

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
    comments = "Source: yhglobalcouponwriteoff.proto")
public final class YhglobalCouponWriteoffServiceGrpc {

  private YhglobalCouponWriteoffServiceGrpc() {}

  public static final String SERVICE_NAME = "YhglobalCouponWriteoffService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffReq,
      YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp> METHOD_YH_COUPON_WRITEROFF =
      io.grpc.MethodDescriptor.<YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffReq, YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalCouponWriteoffService", "yhCouponWriteroff"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageReq,
      YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageResp> METHOD_SELECT_BY_MANY_CONDIITONS_HAS_PAGE =
      io.grpc.MethodDescriptor.<YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageReq, YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalCouponWriteoffService", "selectByManyCondiitonsHasPage"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageReq,
      YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageResp> METHOD_SELECT_BY_MANY_CONDIITONS_NO_PAGE =
      io.grpc.MethodDescriptor.<YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageReq, YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalCouponWriteoffService", "selectByManyCondiitonsNoPage"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchReq,
      YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchResp> METHOD_COUPON_RECEIVE_CANCEL_CONFIRM_BATCH =
      io.grpc.MethodDescriptor.<YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchReq, YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalCouponWriteoffService", "couponReceiveCancelConfirmBatch"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdReq,
      YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdResp> METHOD_SELECT_YHGLOBAL_COUPON_LEDGER_BY_PURCHASE_COUPON_LEDGER_ID =
      io.grpc.MethodDescriptor.<YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdReq, YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalCouponWriteoffService", "selectYhglobalCouponLedgerByPurchaseCouponLedgerId"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmReq,
      YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmResp> METHOD_SEARCH_COUPON_CONFIRM =
      io.grpc.MethodDescriptor.<YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmReq, YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalCouponWriteoffService", "searchCouponConfirm"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmResp.getDefaultInstance()))
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
    public void yhCouponWriteroff(YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffReq request,
        io.grpc.stub.StreamObserver<YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_YH_COUPON_WRITEROFF, responseObserver);
    }

    /**
     * <pre>
     * 多条件分页查询返利流水
     * </pre>
     */
    public void selectByManyCondiitonsHasPage(YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageReq request,
        io.grpc.stub.StreamObserver<YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_BY_MANY_CONDIITONS_HAS_PAGE, responseObserver);
    }

    /**
     * <pre>
     * 多条件无分页查询返利流水
     * </pre>
     */
    public void selectByManyCondiitonsNoPage(YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageReq request,
        io.grpc.stub.StreamObserver<YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_BY_MANY_CONDIITONS_NO_PAGE, responseObserver);
    }

    /**
     * <pre>
     * 批量取消确认
     * </pre>
     */
    public void couponReceiveCancelConfirmBatch(YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchReq request,
        io.grpc.stub.StreamObserver<YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_COUPON_RECEIVE_CANCEL_CONFIRM_BATCH, responseObserver);
    }

    /**
     * <pre>
     * 根据选中的返利ID查询应收返利
     * </pre>
     */
    public void selectYhglobalCouponLedgerByPurchaseCouponLedgerId(YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdReq request,
        io.grpc.stub.StreamObserver<YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_YHGLOBAL_COUPON_LEDGER_BY_PURCHASE_COUPON_LEDGER_ID, responseObserver);
    }

    /**
     * <pre>
     * 分页查询返利确认记录
     * </pre>
     */
    public void searchCouponConfirm(YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmReq request,
        io.grpc.stub.StreamObserver<YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SEARCH_COUPON_CONFIRM, responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_YH_COUPON_WRITEROFF,
            asyncUnaryCall(
              new MethodHandlers<
                YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffReq,
                YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp>(
                  this, METHODID_YH_COUPON_WRITEROFF)))
          .addMethod(
            METHOD_SELECT_BY_MANY_CONDIITONS_HAS_PAGE,
            asyncUnaryCall(
              new MethodHandlers<
                YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageReq,
                YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageResp>(
                  this, METHODID_SELECT_BY_MANY_CONDIITONS_HAS_PAGE)))
          .addMethod(
            METHOD_SELECT_BY_MANY_CONDIITONS_NO_PAGE,
            asyncUnaryCall(
              new MethodHandlers<
                YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageReq,
                YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageResp>(
                  this, METHODID_SELECT_BY_MANY_CONDIITONS_NO_PAGE)))
          .addMethod(
            METHOD_COUPON_RECEIVE_CANCEL_CONFIRM_BATCH,
            asyncUnaryCall(
              new MethodHandlers<
                YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchReq,
                YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchResp>(
                  this, METHODID_COUPON_RECEIVE_CANCEL_CONFIRM_BATCH)))
          .addMethod(
            METHOD_SELECT_YHGLOBAL_COUPON_LEDGER_BY_PURCHASE_COUPON_LEDGER_ID,
            asyncUnaryCall(
              new MethodHandlers<
                YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdReq,
                YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdResp>(
                  this, METHODID_SELECT_YHGLOBAL_COUPON_LEDGER_BY_PURCHASE_COUPON_LEDGER_ID)))
          .addMethod(
            METHOD_SEARCH_COUPON_CONFIRM,
            asyncUnaryCall(
              new MethodHandlers<
                YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmReq,
                YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmResp>(
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

    @Override
    protected YhglobalCouponWriteoffServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new YhglobalCouponWriteoffServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 返利确认
     * </pre>
     */
    public void yhCouponWriteroff(YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffReq request,
        io.grpc.stub.StreamObserver<YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_YH_COUPON_WRITEROFF, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 多条件分页查询返利流水
     * </pre>
     */
    public void selectByManyCondiitonsHasPage(YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageReq request,
        io.grpc.stub.StreamObserver<YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_BY_MANY_CONDIITONS_HAS_PAGE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 多条件无分页查询返利流水
     * </pre>
     */
    public void selectByManyCondiitonsNoPage(YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageReq request,
        io.grpc.stub.StreamObserver<YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_BY_MANY_CONDIITONS_NO_PAGE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 批量取消确认
     * </pre>
     */
    public void couponReceiveCancelConfirmBatch(YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchReq request,
        io.grpc.stub.StreamObserver<YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_COUPON_RECEIVE_CANCEL_CONFIRM_BATCH, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 根据选中的返利ID查询应收返利
     * </pre>
     */
    public void selectYhglobalCouponLedgerByPurchaseCouponLedgerId(YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdReq request,
        io.grpc.stub.StreamObserver<YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_YHGLOBAL_COUPON_LEDGER_BY_PURCHASE_COUPON_LEDGER_ID, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 分页查询返利确认记录
     * </pre>
     */
    public void searchCouponConfirm(YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmReq request,
        io.grpc.stub.StreamObserver<YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmResp> responseObserver) {
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

    @Override
    protected YhglobalCouponWriteoffServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new YhglobalCouponWriteoffServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 返利确认
     * </pre>
     */
    public YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp yhCouponWriteroff(YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_YH_COUPON_WRITEROFF, getCallOptions(), request);
    }

    /**
     * <pre>
     * 多条件分页查询返利流水
     * </pre>
     */
    public YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageResp selectByManyCondiitonsHasPage(YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_BY_MANY_CONDIITONS_HAS_PAGE, getCallOptions(), request);
    }

    /**
     * <pre>
     * 多条件无分页查询返利流水
     * </pre>
     */
    public YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageResp selectByManyCondiitonsNoPage(YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_BY_MANY_CONDIITONS_NO_PAGE, getCallOptions(), request);
    }

    /**
     * <pre>
     * 批量取消确认
     * </pre>
     */
    public YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchResp couponReceiveCancelConfirmBatch(YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_COUPON_RECEIVE_CANCEL_CONFIRM_BATCH, getCallOptions(), request);
    }

    /**
     * <pre>
     * 根据选中的返利ID查询应收返利
     * </pre>
     */
    public YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdResp selectYhglobalCouponLedgerByPurchaseCouponLedgerId(YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_YHGLOBAL_COUPON_LEDGER_BY_PURCHASE_COUPON_LEDGER_ID, getCallOptions(), request);
    }

    /**
     * <pre>
     * 分页查询返利确认记录
     * </pre>
     */
    public YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmResp searchCouponConfirm(YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmReq request) {
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

    @Override
    protected YhglobalCouponWriteoffServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new YhglobalCouponWriteoffServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 返利确认
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp> yhCouponWriteroff(
        YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_YH_COUPON_WRITEROFF, getCallOptions()), request);
    }

    /**
     * <pre>
     * 多条件分页查询返利流水
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageResp> selectByManyCondiitonsHasPage(
        YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_BY_MANY_CONDIITONS_HAS_PAGE, getCallOptions()), request);
    }

    /**
     * <pre>
     * 多条件无分页查询返利流水
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageResp> selectByManyCondiitonsNoPage(
        YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_BY_MANY_CONDIITONS_NO_PAGE, getCallOptions()), request);
    }

    /**
     * <pre>
     * 批量取消确认
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchResp> couponReceiveCancelConfirmBatch(
        YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_COUPON_RECEIVE_CANCEL_CONFIRM_BATCH, getCallOptions()), request);
    }

    /**
     * <pre>
     * 根据选中的返利ID查询应收返利
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdResp> selectYhglobalCouponLedgerByPurchaseCouponLedgerId(
        YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_YHGLOBAL_COUPON_LEDGER_BY_PURCHASE_COUPON_LEDGER_ID, getCallOptions()), request);
    }

    /**
     * <pre>
     * 分页查询返利确认记录
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmResp> searchCouponConfirm(
        YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmReq request) {
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

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_YH_COUPON_WRITEROFF:
          serviceImpl.yhCouponWriteroff((YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffReq) request,
              (io.grpc.stub.StreamObserver<YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp>) responseObserver);
          break;
        case METHODID_SELECT_BY_MANY_CONDIITONS_HAS_PAGE:
          serviceImpl.selectByManyCondiitonsHasPage((YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageReq) request,
              (io.grpc.stub.StreamObserver<YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageResp>) responseObserver);
          break;
        case METHODID_SELECT_BY_MANY_CONDIITONS_NO_PAGE:
          serviceImpl.selectByManyCondiitonsNoPage((YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageReq) request,
              (io.grpc.stub.StreamObserver<YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageResp>) responseObserver);
          break;
        case METHODID_COUPON_RECEIVE_CANCEL_CONFIRM_BATCH:
          serviceImpl.couponReceiveCancelConfirmBatch((YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchReq) request,
              (io.grpc.stub.StreamObserver<YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchResp>) responseObserver);
          break;
        case METHODID_SELECT_YHGLOBAL_COUPON_LEDGER_BY_PURCHASE_COUPON_LEDGER_ID:
          serviceImpl.selectYhglobalCouponLedgerByPurchaseCouponLedgerId((YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdReq) request,
              (io.grpc.stub.StreamObserver<YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdResp>) responseObserver);
          break;
        case METHODID_SEARCH_COUPON_CONFIRM:
          serviceImpl.searchCouponConfirm((YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmReq) request,
              (io.grpc.stub.StreamObserver<YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmResp>) responseObserver);
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

  private static final class YhglobalCouponWriteoffServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return YhglobalCouponWriteoffServiceStructure.getDescriptor();
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
