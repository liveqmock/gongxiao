package com.yhglobal.gongxiao.foundation.currency.microservice;

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
    comments = "Source: currency/currency.proto")
public final class CurrencyServiceGrpc {

  private CurrencyServiceGrpc() {}

  public static final String SERVICE_NAME = "CurrencyService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByCodeRequest,
      com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByCodeResponse> METHOD_GET_CURRENCY_BY_CODE =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByCodeRequest, com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByCodeResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "CurrencyService", "getCurrencyByCode"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByCodeRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByCodeResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByNameRequest,
      com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByNameResponse> METHOD_GET_CURRENCY_BY_NAME =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByNameRequest, com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByNameResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "CurrencyService", "getCurrencyByName"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByNameRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByNameResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByIdRequest,
      com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByIdResponse> METHOD_GET_CURRENCY_BY_ID =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByIdRequest, com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByIdResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "CurrencyService", "getCurrencyById"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByIdRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByIdResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.SelectAllCurrencyRequest,
      com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.SelectAllCurrencyResponse> METHOD_SELECT_ALL_CURRENCY =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.SelectAllCurrencyRequest, com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.SelectAllCurrencyResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "CurrencyService", "selectAllCurrency"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.SelectAllCurrencyRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.SelectAllCurrencyResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CurrencyServiceStub newStub(io.grpc.Channel channel) {
    return new CurrencyServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CurrencyServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new CurrencyServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CurrencyServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new CurrencyServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class CurrencyServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1 通过currencyCode查询货币
     * </pre>
     */
    public void getCurrencyByCode(com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByCodeRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByCodeResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_CURRENCY_BY_CODE, responseObserver);
    }

    /**
     * <pre>
     *2 通过currencyName查询货币
     * </pre>
     */
    public void getCurrencyByName(com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByNameRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByNameResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_CURRENCY_BY_NAME, responseObserver);
    }

    /**
     * <pre>
     *3 通过currencyId查询货币
     * </pre>
     */
    public void getCurrencyById(com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByIdRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByIdResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_CURRENCY_BY_ID, responseObserver);
    }

    /**
     * <pre>
     *4 获取所有货币
     * </pre>
     */
    public void selectAllCurrency(com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.SelectAllCurrencyRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.SelectAllCurrencyResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_ALL_CURRENCY, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_CURRENCY_BY_CODE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByCodeRequest,
                com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByCodeResponse>(
                  this, METHODID_GET_CURRENCY_BY_CODE)))
          .addMethod(
            METHOD_GET_CURRENCY_BY_NAME,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByNameRequest,
                com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByNameResponse>(
                  this, METHODID_GET_CURRENCY_BY_NAME)))
          .addMethod(
            METHOD_GET_CURRENCY_BY_ID,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByIdRequest,
                com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByIdResponse>(
                  this, METHODID_GET_CURRENCY_BY_ID)))
          .addMethod(
            METHOD_SELECT_ALL_CURRENCY,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.SelectAllCurrencyRequest,
                com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.SelectAllCurrencyResponse>(
                  this, METHODID_SELECT_ALL_CURRENCY)))
          .build();
    }
  }

  /**
   */
  public static final class CurrencyServiceStub extends io.grpc.stub.AbstractStub<CurrencyServiceStub> {
    private CurrencyServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CurrencyServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CurrencyServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CurrencyServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 通过currencyCode查询货币
     * </pre>
     */
    public void getCurrencyByCode(com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByCodeRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByCodeResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_CURRENCY_BY_CODE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *2 通过currencyName查询货币
     * </pre>
     */
    public void getCurrencyByName(com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByNameRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByNameResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_CURRENCY_BY_NAME, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *3 通过currencyId查询货币
     * </pre>
     */
    public void getCurrencyById(com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByIdRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByIdResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_CURRENCY_BY_ID, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *4 获取所有货币
     * </pre>
     */
    public void selectAllCurrency(com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.SelectAllCurrencyRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.SelectAllCurrencyResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_ALL_CURRENCY, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CurrencyServiceBlockingStub extends io.grpc.stub.AbstractStub<CurrencyServiceBlockingStub> {
    private CurrencyServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CurrencyServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CurrencyServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CurrencyServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 通过currencyCode查询货币
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByCodeResponse getCurrencyByCode(com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByCodeRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_CURRENCY_BY_CODE, getCallOptions(), request);
    }

    /**
     * <pre>
     *2 通过currencyName查询货币
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByNameResponse getCurrencyByName(com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByNameRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_CURRENCY_BY_NAME, getCallOptions(), request);
    }

    /**
     * <pre>
     *3 通过currencyId查询货币
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByIdResponse getCurrencyById(com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByIdRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_CURRENCY_BY_ID, getCallOptions(), request);
    }

    /**
     * <pre>
     *4 获取所有货币
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.SelectAllCurrencyResponse selectAllCurrency(com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.SelectAllCurrencyRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_ALL_CURRENCY, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CurrencyServiceFutureStub extends io.grpc.stub.AbstractStub<CurrencyServiceFutureStub> {
    private CurrencyServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CurrencyServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CurrencyServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CurrencyServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 通过currencyCode查询货币
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByCodeResponse> getCurrencyByCode(
        com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByCodeRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_CURRENCY_BY_CODE, getCallOptions()), request);
    }

    /**
     * <pre>
     *2 通过currencyName查询货币
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByNameResponse> getCurrencyByName(
        com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByNameRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_CURRENCY_BY_NAME, getCallOptions()), request);
    }

    /**
     * <pre>
     *3 通过currencyId查询货币
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByIdResponse> getCurrencyById(
        com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByIdRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_CURRENCY_BY_ID, getCallOptions()), request);
    }

    /**
     * <pre>
     *4 获取所有货币
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.SelectAllCurrencyResponse> selectAllCurrency(
        com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.SelectAllCurrencyRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_ALL_CURRENCY, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_CURRENCY_BY_CODE = 0;
  private static final int METHODID_GET_CURRENCY_BY_NAME = 1;
  private static final int METHODID_GET_CURRENCY_BY_ID = 2;
  private static final int METHODID_SELECT_ALL_CURRENCY = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CurrencyServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CurrencyServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_CURRENCY_BY_CODE:
          serviceImpl.getCurrencyByCode((com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByCodeRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByCodeResponse>) responseObserver);
          break;
        case METHODID_GET_CURRENCY_BY_NAME:
          serviceImpl.getCurrencyByName((com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByNameRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByNameResponse>) responseObserver);
          break;
        case METHODID_GET_CURRENCY_BY_ID:
          serviceImpl.getCurrencyById((com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByIdRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.GetCurrencyByIdResponse>) responseObserver);
          break;
        case METHODID_SELECT_ALL_CURRENCY:
          serviceImpl.selectAllCurrency((com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.SelectAllCurrencyRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.SelectAllCurrencyResponse>) responseObserver);
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

  private static final class CurrencyServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CurrencyServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CurrencyServiceDescriptorSupplier())
              .addMethod(METHOD_GET_CURRENCY_BY_CODE)
              .addMethod(METHOD_GET_CURRENCY_BY_NAME)
              .addMethod(METHOD_GET_CURRENCY_BY_ID)
              .addMethod(METHOD_SELECT_ALL_CURRENCY)
              .build();
        }
      }
    }
    return result;
  }
}
