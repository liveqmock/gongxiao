package com.yhglobal.gongxiao.payment.microservice;

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
    comments = "Source: yhglobal.prepaid.proto")
public final class YhglobalPrepaidServiceGrpc {

  private YhglobalPrepaidServiceGrpc() {}

  public static final String SERVICE_NAME = "YhglobalPrepaidService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.AddReceiveReq,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_ADD_RECEIVE =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.AddReceiveReq, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalPrepaidService", "addReceive"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.AddReceiveReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveConfirmReq,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_RECEIVE_CONFIRM =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveConfirmReq, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalPrepaidService", "receiveConfirm"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveConfirmReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveCancelConfirmBatchReq,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_RECEIVE_CANCEL_CONFIRM_BATCH =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveCancelConfirmBatchReq, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalPrepaidService", "receiveCancelConfirmBatch"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveCancelConfirmBatchReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectByProjectIdReq,
      com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceivePageInfoResp> METHOD_SELECT_BY_PROJECT_ID =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectByProjectIdReq, com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceivePageInfoResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalPrepaidService", "selectByProjectId"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectByProjectIdReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceivePageInfoResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectByIdsReq,
      com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveListResp> METHOD_SELECT_BY_IDS =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectByIdsReq, com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveListResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalPrepaidService", "selectByIds"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectByIdsReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveListResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectWriteoffRecordByReceiveIdReq,
      com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.WriteoffRecordResp> METHOD_SELECT_WRITEOFF_RECORD_BY_RECEIVE_ID =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectWriteoffRecordByReceiveIdReq, com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.WriteoffRecordResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalPrepaidService", "selectWriteoffRecordByReceiveId"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectWriteoffRecordByReceiveIdReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.WriteoffRecordResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectReceiveAndRecordByProjectIdReq,
      com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceivePageInfoResp> METHOD_SELECT_RECEIVE_AND_RECORD_BY_PROJECT_ID =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectReceiveAndRecordByProjectIdReq, com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceivePageInfoResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalPrepaidService", "selectReceiveAndRecordByProjectId"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectReceiveAndRecordByProjectIdReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceivePageInfoResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectReceiveAndRecordCountReq,
      com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.YhglobalToReceivePrepaidCountResp> METHOD_SELECT_RECEIVE_AND_RECORD_COUNT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectReceiveAndRecordCountReq, com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.YhglobalToReceivePrepaidCountResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalPrepaidService", "selectReceiveAndRecordCount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectReceiveAndRecordCountReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.YhglobalToReceivePrepaidCountResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.AddPrepaidInfoReq,
      com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> METHOD_ADD_PREPAID_INFO =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.AddPrepaidInfoReq, com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalPrepaidService", "addPrepaidInfo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.AddPrepaidInfoReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.GetsPrepaidInfoListReq,
      com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.PrepaidPageInfoResp> METHOD_GETS_PREPAID_INFO_LIST =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.GetsPrepaidInfoListReq, com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.PrepaidPageInfoResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalPrepaidService", "getsPrepaidInfoList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.GetsPrepaidInfoListReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.PrepaidPageInfoResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.GetPrepaidInfoByIdReq,
      com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.PrepaidInfoResp> METHOD_GET_PREPAID_INFO_BY_ID =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.GetPrepaidInfoByIdReq, com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.PrepaidInfoResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalPrepaidService", "getPrepaidInfoById"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.GetPrepaidInfoByIdReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.PrepaidInfoResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SearchPrepaidConfirmReq,
      com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.WriteoffRecordPageInfoResp> METHOD_SEARCH_PREPAID_CONFIRM =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SearchPrepaidConfirmReq, com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.WriteoffRecordPageInfoResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalPrepaidService", "searchPrepaidConfirm"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SearchPrepaidConfirmReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.WriteoffRecordPageInfoResp.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static YhglobalPrepaidServiceStub newStub(io.grpc.Channel channel) {
    return new YhglobalPrepaidServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static YhglobalPrepaidServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new YhglobalPrepaidServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static YhglobalPrepaidServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new YhglobalPrepaidServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class YhglobalPrepaidServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void addReceive(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.AddReceiveReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_ADD_RECEIVE, responseObserver);
    }

    /**
     */
    public void receiveConfirm(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveConfirmReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_RECEIVE_CONFIRM, responseObserver);
    }

    /**
     */
    public void receiveCancelConfirmBatch(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveCancelConfirmBatchReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_RECEIVE_CANCEL_CONFIRM_BATCH, responseObserver);
    }

    /**
     */
    public void selectByProjectId(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectByProjectIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceivePageInfoResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_BY_PROJECT_ID, responseObserver);
    }

    /**
     */
    public void selectByIds(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectByIdsReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveListResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_BY_IDS, responseObserver);
    }

    /**
     */
    public void selectWriteoffRecordByReceiveId(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectWriteoffRecordByReceiveIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.WriteoffRecordResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_WRITEOFF_RECORD_BY_RECEIVE_ID, responseObserver);
    }

    /**
     */
    public void selectReceiveAndRecordByProjectId(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectReceiveAndRecordByProjectIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceivePageInfoResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_RECEIVE_AND_RECORD_BY_PROJECT_ID, responseObserver);
    }

    /**
     */
    public void selectReceiveAndRecordCount(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectReceiveAndRecordCountReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.YhglobalToReceivePrepaidCountResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_RECEIVE_AND_RECORD_COUNT, responseObserver);
    }

    /**
     */
    public void addPrepaidInfo(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.AddPrepaidInfoReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_ADD_PREPAID_INFO, responseObserver);
    }

    /**
     */
    public void getsPrepaidInfoList(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.GetsPrepaidInfoListReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.PrepaidPageInfoResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GETS_PREPAID_INFO_LIST, responseObserver);
    }

    /**
     */
    public void getPrepaidInfoById(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.GetPrepaidInfoByIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.PrepaidInfoResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_PREPAID_INFO_BY_ID, responseObserver);
    }

    /**
     */
    public void searchPrepaidConfirm(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SearchPrepaidConfirmReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.WriteoffRecordPageInfoResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SEARCH_PREPAID_CONFIRM, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_ADD_RECEIVE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.AddReceiveReq,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_ADD_RECEIVE)))
          .addMethod(
            METHOD_RECEIVE_CONFIRM,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveConfirmReq,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_RECEIVE_CONFIRM)))
          .addMethod(
            METHOD_RECEIVE_CANCEL_CONFIRM_BATCH,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveCancelConfirmBatchReq,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_RECEIVE_CANCEL_CONFIRM_BATCH)))
          .addMethod(
            METHOD_SELECT_BY_PROJECT_ID,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectByProjectIdReq,
                com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceivePageInfoResp>(
                  this, METHODID_SELECT_BY_PROJECT_ID)))
          .addMethod(
            METHOD_SELECT_BY_IDS,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectByIdsReq,
                com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveListResp>(
                  this, METHODID_SELECT_BY_IDS)))
          .addMethod(
            METHOD_SELECT_WRITEOFF_RECORD_BY_RECEIVE_ID,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectWriteoffRecordByReceiveIdReq,
                com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.WriteoffRecordResp>(
                  this, METHODID_SELECT_WRITEOFF_RECORD_BY_RECEIVE_ID)))
          .addMethod(
            METHOD_SELECT_RECEIVE_AND_RECORD_BY_PROJECT_ID,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectReceiveAndRecordByProjectIdReq,
                com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceivePageInfoResp>(
                  this, METHODID_SELECT_RECEIVE_AND_RECORD_BY_PROJECT_ID)))
          .addMethod(
            METHOD_SELECT_RECEIVE_AND_RECORD_COUNT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectReceiveAndRecordCountReq,
                com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.YhglobalToReceivePrepaidCountResp>(
                  this, METHODID_SELECT_RECEIVE_AND_RECORD_COUNT)))
          .addMethod(
            METHOD_ADD_PREPAID_INFO,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.AddPrepaidInfoReq,
                com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>(
                  this, METHODID_ADD_PREPAID_INFO)))
          .addMethod(
            METHOD_GETS_PREPAID_INFO_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.GetsPrepaidInfoListReq,
                com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.PrepaidPageInfoResp>(
                  this, METHODID_GETS_PREPAID_INFO_LIST)))
          .addMethod(
            METHOD_GET_PREPAID_INFO_BY_ID,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.GetPrepaidInfoByIdReq,
                com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.PrepaidInfoResp>(
                  this, METHODID_GET_PREPAID_INFO_BY_ID)))
          .addMethod(
            METHOD_SEARCH_PREPAID_CONFIRM,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SearchPrepaidConfirmReq,
                com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.WriteoffRecordPageInfoResp>(
                  this, METHODID_SEARCH_PREPAID_CONFIRM)))
          .build();
    }
  }

  /**
   */
  public static final class YhglobalPrepaidServiceStub extends io.grpc.stub.AbstractStub<YhglobalPrepaidServiceStub> {
    private YhglobalPrepaidServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private YhglobalPrepaidServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected YhglobalPrepaidServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new YhglobalPrepaidServiceStub(channel, callOptions);
    }

    /**
     */
    public void addReceive(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.AddReceiveReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_ADD_RECEIVE, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void receiveConfirm(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveConfirmReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_RECEIVE_CONFIRM, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void receiveCancelConfirmBatch(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveCancelConfirmBatchReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_RECEIVE_CANCEL_CONFIRM_BATCH, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void selectByProjectId(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectByProjectIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceivePageInfoResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_BY_PROJECT_ID, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void selectByIds(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectByIdsReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveListResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_BY_IDS, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void selectWriteoffRecordByReceiveId(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectWriteoffRecordByReceiveIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.WriteoffRecordResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_WRITEOFF_RECORD_BY_RECEIVE_ID, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void selectReceiveAndRecordByProjectId(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectReceiveAndRecordByProjectIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceivePageInfoResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_RECEIVE_AND_RECORD_BY_PROJECT_ID, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void selectReceiveAndRecordCount(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectReceiveAndRecordCountReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.YhglobalToReceivePrepaidCountResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_RECEIVE_AND_RECORD_COUNT, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void addPrepaidInfo(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.AddPrepaidInfoReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_ADD_PREPAID_INFO, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getsPrepaidInfoList(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.GetsPrepaidInfoListReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.PrepaidPageInfoResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GETS_PREPAID_INFO_LIST, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getPrepaidInfoById(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.GetPrepaidInfoByIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.PrepaidInfoResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_PREPAID_INFO_BY_ID, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void searchPrepaidConfirm(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SearchPrepaidConfirmReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.WriteoffRecordPageInfoResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SEARCH_PREPAID_CONFIRM, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class YhglobalPrepaidServiceBlockingStub extends io.grpc.stub.AbstractStub<YhglobalPrepaidServiceBlockingStub> {
    private YhglobalPrepaidServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private YhglobalPrepaidServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected YhglobalPrepaidServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new YhglobalPrepaidServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult addReceive(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.AddReceiveReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_ADD_RECEIVE, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult receiveConfirm(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveConfirmReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_RECEIVE_CONFIRM, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult receiveCancelConfirmBatch(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveCancelConfirmBatchReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_RECEIVE_CANCEL_CONFIRM_BATCH, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceivePageInfoResp selectByProjectId(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectByProjectIdReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_BY_PROJECT_ID, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveListResp selectByIds(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectByIdsReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_BY_IDS, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.WriteoffRecordResp selectWriteoffRecordByReceiveId(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectWriteoffRecordByReceiveIdReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_WRITEOFF_RECORD_BY_RECEIVE_ID, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceivePageInfoResp selectReceiveAndRecordByProjectId(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectReceiveAndRecordByProjectIdReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_RECEIVE_AND_RECORD_BY_PROJECT_ID, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.YhglobalToReceivePrepaidCountResp selectReceiveAndRecordCount(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectReceiveAndRecordCountReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_RECEIVE_AND_RECORD_COUNT, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult addPrepaidInfo(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.AddPrepaidInfoReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_ADD_PREPAID_INFO, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.PrepaidPageInfoResp getsPrepaidInfoList(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.GetsPrepaidInfoListReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GETS_PREPAID_INFO_LIST, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.PrepaidInfoResp getPrepaidInfoById(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.GetPrepaidInfoByIdReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_PREPAID_INFO_BY_ID, getCallOptions(), request);
    }

    /**
     */
    public com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.WriteoffRecordPageInfoResp searchPrepaidConfirm(com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SearchPrepaidConfirmReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SEARCH_PREPAID_CONFIRM, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class YhglobalPrepaidServiceFutureStub extends io.grpc.stub.AbstractStub<YhglobalPrepaidServiceFutureStub> {
    private YhglobalPrepaidServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private YhglobalPrepaidServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected YhglobalPrepaidServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new YhglobalPrepaidServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> addReceive(
        com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.AddReceiveReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_ADD_RECEIVE, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> receiveConfirm(
        com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveConfirmReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_RECEIVE_CONFIRM, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> receiveCancelConfirmBatch(
        com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveCancelConfirmBatchReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_RECEIVE_CANCEL_CONFIRM_BATCH, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceivePageInfoResp> selectByProjectId(
        com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectByProjectIdReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_BY_PROJECT_ID, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveListResp> selectByIds(
        com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectByIdsReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_BY_IDS, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.WriteoffRecordResp> selectWriteoffRecordByReceiveId(
        com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectWriteoffRecordByReceiveIdReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_WRITEOFF_RECORD_BY_RECEIVE_ID, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceivePageInfoResp> selectReceiveAndRecordByProjectId(
        com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectReceiveAndRecordByProjectIdReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_RECEIVE_AND_RECORD_BY_PROJECT_ID, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.YhglobalToReceivePrepaidCountResp> selectReceiveAndRecordCount(
        com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectReceiveAndRecordCountReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_RECEIVE_AND_RECORD_COUNT, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult> addPrepaidInfo(
        com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.AddPrepaidInfoReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_ADD_PREPAID_INFO, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.PrepaidPageInfoResp> getsPrepaidInfoList(
        com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.GetsPrepaidInfoListReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GETS_PREPAID_INFO_LIST, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.PrepaidInfoResp> getPrepaidInfoById(
        com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.GetPrepaidInfoByIdReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_PREPAID_INFO_BY_ID, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.WriteoffRecordPageInfoResp> searchPrepaidConfirm(
        com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SearchPrepaidConfirmReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SEARCH_PREPAID_CONFIRM, getCallOptions()), request);
    }
  }

  private static final int METHODID_ADD_RECEIVE = 0;
  private static final int METHODID_RECEIVE_CONFIRM = 1;
  private static final int METHODID_RECEIVE_CANCEL_CONFIRM_BATCH = 2;
  private static final int METHODID_SELECT_BY_PROJECT_ID = 3;
  private static final int METHODID_SELECT_BY_IDS = 4;
  private static final int METHODID_SELECT_WRITEOFF_RECORD_BY_RECEIVE_ID = 5;
  private static final int METHODID_SELECT_RECEIVE_AND_RECORD_BY_PROJECT_ID = 6;
  private static final int METHODID_SELECT_RECEIVE_AND_RECORD_COUNT = 7;
  private static final int METHODID_ADD_PREPAID_INFO = 8;
  private static final int METHODID_GETS_PREPAID_INFO_LIST = 9;
  private static final int METHODID_GET_PREPAID_INFO_BY_ID = 10;
  private static final int METHODID_SEARCH_PREPAID_CONFIRM = 11;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final YhglobalPrepaidServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(YhglobalPrepaidServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ADD_RECEIVE:
          serviceImpl.addReceive((com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.AddReceiveReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_RECEIVE_CONFIRM:
          serviceImpl.receiveConfirm((com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveConfirmReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_RECEIVE_CANCEL_CONFIRM_BATCH:
          serviceImpl.receiveCancelConfirmBatch((com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveCancelConfirmBatchReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_SELECT_BY_PROJECT_ID:
          serviceImpl.selectByProjectId((com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectByProjectIdReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceivePageInfoResp>) responseObserver);
          break;
        case METHODID_SELECT_BY_IDS:
          serviceImpl.selectByIds((com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectByIdsReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceiveListResp>) responseObserver);
          break;
        case METHODID_SELECT_WRITEOFF_RECORD_BY_RECEIVE_ID:
          serviceImpl.selectWriteoffRecordByReceiveId((com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectWriteoffRecordByReceiveIdReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.WriteoffRecordResp>) responseObserver);
          break;
        case METHODID_SELECT_RECEIVE_AND_RECORD_BY_PROJECT_ID:
          serviceImpl.selectReceiveAndRecordByProjectId((com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectReceiveAndRecordByProjectIdReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.ReceivePageInfoResp>) responseObserver);
          break;
        case METHODID_SELECT_RECEIVE_AND_RECORD_COUNT:
          serviceImpl.selectReceiveAndRecordCount((com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SelectReceiveAndRecordCountReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.YhglobalToReceivePrepaidCountResp>) responseObserver);
          break;
        case METHODID_ADD_PREPAID_INFO:
          serviceImpl.addPrepaidInfo((com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.AddPrepaidInfoReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.microservice.GongxiaoRpc.RpcResult>) responseObserver);
          break;
        case METHODID_GETS_PREPAID_INFO_LIST:
          serviceImpl.getsPrepaidInfoList((com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.GetsPrepaidInfoListReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.PrepaidPageInfoResp>) responseObserver);
          break;
        case METHODID_GET_PREPAID_INFO_BY_ID:
          serviceImpl.getPrepaidInfoById((com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.GetPrepaidInfoByIdReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.PrepaidInfoResp>) responseObserver);
          break;
        case METHODID_SEARCH_PREPAID_CONFIRM:
          serviceImpl.searchPrepaidConfirm((com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.SearchPrepaidConfirmReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.WriteoffRecordPageInfoResp>) responseObserver);
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

  private static final class YhglobalPrepaidServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.payment.microservice.YhglobalPrepaidServiceStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (YhglobalPrepaidServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new YhglobalPrepaidServiceDescriptorSupplier())
              .addMethod(METHOD_ADD_RECEIVE)
              .addMethod(METHOD_RECEIVE_CONFIRM)
              .addMethod(METHOD_RECEIVE_CANCEL_CONFIRM_BATCH)
              .addMethod(METHOD_SELECT_BY_PROJECT_ID)
              .addMethod(METHOD_SELECT_BY_IDS)
              .addMethod(METHOD_SELECT_WRITEOFF_RECORD_BY_RECEIVE_ID)
              .addMethod(METHOD_SELECT_RECEIVE_AND_RECORD_BY_PROJECT_ID)
              .addMethod(METHOD_SELECT_RECEIVE_AND_RECORD_COUNT)
              .addMethod(METHOD_ADD_PREPAID_INFO)
              .addMethod(METHOD_GETS_PREPAID_INFO_LIST)
              .addMethod(METHOD_GET_PREPAID_INFO_BY_ID)
              .addMethod(METHOD_SEARCH_PREPAID_CONFIRM)
              .build();
        }
      }
    }
    return result;
  }
}
