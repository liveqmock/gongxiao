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
    comments = "Source: yhglobaltopaymoney.proto")
public final class YhglobalToPayMoneyServiceGrpc {

  private YhglobalToPayMoneyServiceGrpc() {}

  public static final String SERVICE_NAME = "YhglobalToPayMoneyService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.InsertNewReq,
      com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.InsertNewResp> METHOD_INSERT_NEW =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.InsertNewReq, com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.InsertNewResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalToPayMoneyService", "insertNew"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.InsertNewReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.InsertNewResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.UpdateDataReq,
      com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.UpdateDataResp> METHOD_UPDATE_DATA =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.UpdateDataReq, com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.UpdateDataResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalToPayMoneyService", "updateData"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.UpdateDataReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.UpdateDataResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.QueryReq,
      com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.QueryResp> METHOD_QUERY_TO_PAY_MONEY =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.QueryReq, com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.QueryResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "YhglobalToPayMoneyService", "queryToPayMoney"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.QueryReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.QueryResp.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static YhglobalToPayMoneyServiceStub newStub(io.grpc.Channel channel) {
    return new YhglobalToPayMoneyServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static YhglobalToPayMoneyServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new YhglobalToPayMoneyServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static YhglobalToPayMoneyServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new YhglobalToPayMoneyServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class YhglobalToPayMoneyServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 插入新的一条数据
     * </pre>
     */
    public void insertNew(com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.InsertNewReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.InsertNewResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_INSERT_NEW, responseObserver);
    }

    /**
     * <pre>
     * 更新旧的数据
     * </pre>
     */
    public void updateData(com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.UpdateDataReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.UpdateDataResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_DATA, responseObserver);
    }

    /**
     * <pre>
     * 条件查询付款申请单
     * </pre>
     */
    public void queryToPayMoney(com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.QueryReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.QueryResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_QUERY_TO_PAY_MONEY, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_INSERT_NEW,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.InsertNewReq,
                com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.InsertNewResp>(
                  this, METHODID_INSERT_NEW)))
          .addMethod(
            METHOD_UPDATE_DATA,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.UpdateDataReq,
                com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.UpdateDataResp>(
                  this, METHODID_UPDATE_DATA)))
          .addMethod(
            METHOD_QUERY_TO_PAY_MONEY,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.QueryReq,
                com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.QueryResp>(
                  this, METHODID_QUERY_TO_PAY_MONEY)))
          .build();
    }
  }

  /**
   */
  public static final class YhglobalToPayMoneyServiceStub extends io.grpc.stub.AbstractStub<YhglobalToPayMoneyServiceStub> {
    private YhglobalToPayMoneyServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private YhglobalToPayMoneyServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected YhglobalToPayMoneyServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new YhglobalToPayMoneyServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 插入新的一条数据
     * </pre>
     */
    public void insertNew(com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.InsertNewReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.InsertNewResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_INSERT_NEW, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 更新旧的数据
     * </pre>
     */
    public void updateData(com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.UpdateDataReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.UpdateDataResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_DATA, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 条件查询付款申请单
     * </pre>
     */
    public void queryToPayMoney(com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.QueryReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.QueryResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_QUERY_TO_PAY_MONEY, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class YhglobalToPayMoneyServiceBlockingStub extends io.grpc.stub.AbstractStub<YhglobalToPayMoneyServiceBlockingStub> {
    private YhglobalToPayMoneyServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private YhglobalToPayMoneyServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected YhglobalToPayMoneyServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new YhglobalToPayMoneyServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 插入新的一条数据
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.InsertNewResp insertNew(com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.InsertNewReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_INSERT_NEW, getCallOptions(), request);
    }

    /**
     * <pre>
     * 更新旧的数据
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.UpdateDataResp updateData(com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.UpdateDataReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_DATA, getCallOptions(), request);
    }

    /**
     * <pre>
     * 条件查询付款申请单
     * </pre>
     */
    public com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.QueryResp queryToPayMoney(com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.QueryReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_QUERY_TO_PAY_MONEY, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class YhglobalToPayMoneyServiceFutureStub extends io.grpc.stub.AbstractStub<YhglobalToPayMoneyServiceFutureStub> {
    private YhglobalToPayMoneyServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private YhglobalToPayMoneyServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected YhglobalToPayMoneyServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new YhglobalToPayMoneyServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 插入新的一条数据
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.InsertNewResp> insertNew(
        com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.InsertNewReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_INSERT_NEW, getCallOptions()), request);
    }

    /**
     * <pre>
     * 更新旧的数据
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.UpdateDataResp> updateData(
        com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.UpdateDataReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_DATA, getCallOptions()), request);
    }

    /**
     * <pre>
     * 条件查询付款申请单
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.QueryResp> queryToPayMoney(
        com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.QueryReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_QUERY_TO_PAY_MONEY, getCallOptions()), request);
    }
  }

  private static final int METHODID_INSERT_NEW = 0;
  private static final int METHODID_UPDATE_DATA = 1;
  private static final int METHODID_QUERY_TO_PAY_MONEY = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final YhglobalToPayMoneyServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(YhglobalToPayMoneyServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_INSERT_NEW:
          serviceImpl.insertNew((com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.InsertNewReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.InsertNewResp>) responseObserver);
          break;
        case METHODID_UPDATE_DATA:
          serviceImpl.updateData((com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.UpdateDataReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.UpdateDataResp>) responseObserver);
          break;
        case METHODID_QUERY_TO_PAY_MONEY:
          serviceImpl.queryToPayMoney((com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.QueryReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.QueryResp>) responseObserver);
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

  private static final class YhglobalToPayMoneyServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (YhglobalToPayMoneyServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new YhglobalToPayMoneyServiceDescriptorSupplier())
              .addMethod(METHOD_INSERT_NEW)
              .addMethod(METHOD_UPDATE_DATA)
              .addMethod(METHOD_QUERY_TO_PAY_MONEY)
              .build();
        }
      }
    }
    return result;
  }
}
