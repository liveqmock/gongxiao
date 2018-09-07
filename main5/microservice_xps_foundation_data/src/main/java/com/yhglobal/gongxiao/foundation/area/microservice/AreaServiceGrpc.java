package com.yhglobal.gongxiao.foundation.area.microservice;

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
    comments = "Source: area/area.proto")
public final class AreaServiceGrpc {

  private AreaServiceGrpc() {}

  public static final String SERVICE_NAME = "AreaService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectProvinceRequest,
      com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectProvinceResponse> METHOD_SELECT_PROVINCE_LIST =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectProvinceRequest, com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectProvinceResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "AreaService", "selectProvinceList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectProvinceRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectProvinceResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectCityByProvinceRequest,
      com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectCityByProvinceResponse> METHOD_SELECT_CITY_BY_PROVINCE =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectCityByProvinceRequest, com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectCityByProvinceResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "AreaService", "selectCityByProvince"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectCityByProvinceRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectCityByProvinceResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllCityRequest,
      com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllCityResponse> METHOD_SELECT_ALL_CITY =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllCityRequest, com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllCityResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "AreaService", "selectAllCity"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllCityRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllCityResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectDistrictsByCityRequest,
      com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectDistrictsByCityResponse> METHOD_SELECT_DISTRICTS_BY_CITY =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectDistrictsByCityRequest, com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectDistrictsByCityResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "AreaService", "selectDistrictsByCity"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectDistrictsByCityRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectDistrictsByCityResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllDistrictRequest,
      com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllDistrictResponse> METHOD_SELECT_ALL_DISTRICT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllDistrictRequest, com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllDistrictResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "AreaService", "selectAllDistrict"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllDistrictRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllDistrictResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AreaServiceStub newStub(io.grpc.Channel channel) {
    return new AreaServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AreaServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new AreaServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AreaServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new AreaServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class AreaServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1获取所有省列表
     * </pre>
     */
    public void selectProvinceList(com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectProvinceRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectProvinceResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_PROVINCE_LIST, responseObserver);
    }

    /**
     * <pre>
     *2 获取某省下的所有城市
     * </pre>
     */
    public void selectCityByProvince(com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectCityByProvinceRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectCityByProvinceResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_CITY_BY_PROVINCE, responseObserver);
    }

    /**
     * <pre>
     *3 获取所有的city列表
     * </pre>
     */
    public void selectAllCity(com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllCityRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllCityResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_ALL_CITY, responseObserver);
    }

    /**
     * <pre>
     *4 获取某市下的所有区域
     * </pre>
     */
    public void selectDistrictsByCity(com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectDistrictsByCityRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectDistrictsByCityResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_DISTRICTS_BY_CITY, responseObserver);
    }

    /**
     * <pre>
     *5 获取所有的区域
     * </pre>
     */
    public void selectAllDistrict(com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllDistrictRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllDistrictResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_ALL_DISTRICT, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_SELECT_PROVINCE_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectProvinceRequest,
                com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectProvinceResponse>(
                  this, METHODID_SELECT_PROVINCE_LIST)))
          .addMethod(
            METHOD_SELECT_CITY_BY_PROVINCE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectCityByProvinceRequest,
                com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectCityByProvinceResponse>(
                  this, METHODID_SELECT_CITY_BY_PROVINCE)))
          .addMethod(
            METHOD_SELECT_ALL_CITY,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllCityRequest,
                com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllCityResponse>(
                  this, METHODID_SELECT_ALL_CITY)))
          .addMethod(
            METHOD_SELECT_DISTRICTS_BY_CITY,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectDistrictsByCityRequest,
                com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectDistrictsByCityResponse>(
                  this, METHODID_SELECT_DISTRICTS_BY_CITY)))
          .addMethod(
            METHOD_SELECT_ALL_DISTRICT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllDistrictRequest,
                com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllDistrictResponse>(
                  this, METHODID_SELECT_ALL_DISTRICT)))
          .build();
    }
  }

  /**
   */
  public static final class AreaServiceStub extends io.grpc.stub.AbstractStub<AreaServiceStub> {
    private AreaServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AreaServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AreaServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AreaServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1获取所有省列表
     * </pre>
     */
    public void selectProvinceList(com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectProvinceRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectProvinceResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_PROVINCE_LIST, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *2 获取某省下的所有城市
     * </pre>
     */
    public void selectCityByProvince(com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectCityByProvinceRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectCityByProvinceResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_CITY_BY_PROVINCE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *3 获取所有的city列表
     * </pre>
     */
    public void selectAllCity(com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllCityRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllCityResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_ALL_CITY, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *4 获取某市下的所有区域
     * </pre>
     */
    public void selectDistrictsByCity(com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectDistrictsByCityRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectDistrictsByCityResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_DISTRICTS_BY_CITY, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *5 获取所有的区域
     * </pre>
     */
    public void selectAllDistrict(com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllDistrictRequest request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllDistrictResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_ALL_DISTRICT, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class AreaServiceBlockingStub extends io.grpc.stub.AbstractStub<AreaServiceBlockingStub> {
    private AreaServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AreaServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AreaServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AreaServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1获取所有省列表
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectProvinceResponse selectProvinceList(com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectProvinceRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_PROVINCE_LIST, getCallOptions(), request);
    }

    /**
     * <pre>
     *2 获取某省下的所有城市
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectCityByProvinceResponse selectCityByProvince(com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectCityByProvinceRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_CITY_BY_PROVINCE, getCallOptions(), request);
    }

    /**
     * <pre>
     *3 获取所有的city列表
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllCityResponse selectAllCity(com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllCityRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_ALL_CITY, getCallOptions(), request);
    }

    /**
     * <pre>
     *4 获取某市下的所有区域
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectDistrictsByCityResponse selectDistrictsByCity(com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectDistrictsByCityRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_DISTRICTS_BY_CITY, getCallOptions(), request);
    }

    /**
     * <pre>
     *5 获取所有的区域
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllDistrictResponse selectAllDistrict(com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllDistrictRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_ALL_DISTRICT, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class AreaServiceFutureStub extends io.grpc.stub.AbstractStub<AreaServiceFutureStub> {
    private AreaServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AreaServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AreaServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AreaServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1获取所有省列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectProvinceResponse> selectProvinceList(
        com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectProvinceRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_PROVINCE_LIST, getCallOptions()), request);
    }

    /**
     * <pre>
     *2 获取某省下的所有城市
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectCityByProvinceResponse> selectCityByProvince(
        com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectCityByProvinceRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_CITY_BY_PROVINCE, getCallOptions()), request);
    }

    /**
     * <pre>
     *3 获取所有的city列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllCityResponse> selectAllCity(
        com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllCityRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_ALL_CITY, getCallOptions()), request);
    }

    /**
     * <pre>
     *4 获取某市下的所有区域
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectDistrictsByCityResponse> selectDistrictsByCity(
        com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectDistrictsByCityRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_DISTRICTS_BY_CITY, getCallOptions()), request);
    }

    /**
     * <pre>
     *5 获取所有的区域
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllDistrictResponse> selectAllDistrict(
        com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllDistrictRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_ALL_DISTRICT, getCallOptions()), request);
    }
  }

  private static final int METHODID_SELECT_PROVINCE_LIST = 0;
  private static final int METHODID_SELECT_CITY_BY_PROVINCE = 1;
  private static final int METHODID_SELECT_ALL_CITY = 2;
  private static final int METHODID_SELECT_DISTRICTS_BY_CITY = 3;
  private static final int METHODID_SELECT_ALL_DISTRICT = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AreaServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AreaServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SELECT_PROVINCE_LIST:
          serviceImpl.selectProvinceList((com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectProvinceRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectProvinceResponse>) responseObserver);
          break;
        case METHODID_SELECT_CITY_BY_PROVINCE:
          serviceImpl.selectCityByProvince((com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectCityByProvinceRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectCityByProvinceResponse>) responseObserver);
          break;
        case METHODID_SELECT_ALL_CITY:
          serviceImpl.selectAllCity((com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllCityRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllCityResponse>) responseObserver);
          break;
        case METHODID_SELECT_DISTRICTS_BY_CITY:
          serviceImpl.selectDistrictsByCity((com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectDistrictsByCityRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectDistrictsByCityResponse>) responseObserver);
          break;
        case METHODID_SELECT_ALL_DISTRICT:
          serviceImpl.selectAllDistrict((com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllDistrictRequest) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.SelectAllDistrictResponse>) responseObserver);
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

  private static final class AreaServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (AreaServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AreaServiceDescriptorSupplier())
              .addMethod(METHOD_SELECT_PROVINCE_LIST)
              .addMethod(METHOD_SELECT_CITY_BY_PROVINCE)
              .addMethod(METHOD_SELECT_ALL_CITY)
              .addMethod(METHOD_SELECT_DISTRICTS_BY_CITY)
              .addMethod(METHOD_SELECT_ALL_DISTRICT)
              .build();
        }
      }
    }
    return result;
  }
}
