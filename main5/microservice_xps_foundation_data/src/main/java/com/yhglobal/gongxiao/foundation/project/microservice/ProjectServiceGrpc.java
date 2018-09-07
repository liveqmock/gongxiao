package com.yhglobal.gongxiao.foundation.project.microservice;

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
    comments = "Source: project/Project.proto")
public final class ProjectServiceGrpc {

  private ProjectServiceGrpc() {}

  public static final String SERVICE_NAME = "ProjectService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectListReq,
      com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectListResp> METHOD_SELECT_PROJECT_LIST =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectListReq, com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectListResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ProjectService", "selectProjectList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectListReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectListResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.GetByProjectIdReq,
      com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.GetByProjectIdResp> METHOD_GET_BY_PROJECT_ID =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.GetByProjectIdReq, com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.GetByProjectIdResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ProjectService", "getByProjectId"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.GetByProjectIdReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.GetByProjectIdResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.UpdateProjectReq,
      com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.UpdateProjectResp> METHOD_UPDATE_PROJECT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.UpdateProjectReq, com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.UpdateProjectResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ProjectService", "updateProject"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.UpdateProjectReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.UpdateProjectResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectByConditionReq,
      com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectByConditionResp> METHOD_SELECT_PROJECT_BY_CONDITION =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectByConditionReq, com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectByConditionResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ProjectService", "selectProjectByCondition"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectByConditionReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectByConditionResp.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ProjectServiceStub newStub(io.grpc.Channel channel) {
    return new ProjectServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ProjectServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ProjectServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ProjectServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ProjectServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class ProjectServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1 查询所有的项目
     * </pre>
     */
    public void selectProjectList(com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectListReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectListResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_PROJECT_LIST, responseObserver);
    }

    /**
     * <pre>
     *2 通过项目id获取该项目的信息
     * </pre>
     */
    public void getByProjectId(com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.GetByProjectIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.GetByProjectIdResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_BY_PROJECT_ID, responseObserver);
    }

    /**
     * <pre>
     *3 更新项目信息
     * </pre>
     */
    public void updateProject(com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.UpdateProjectReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.UpdateProjectResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_PROJECT, responseObserver);
    }

    /**
     * <pre>
     *4 项目信息多条件查询
     * </pre>
     */
    public void selectProjectByCondition(com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectByConditionReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectByConditionResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_PROJECT_BY_CONDITION, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_SELECT_PROJECT_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectListReq,
                com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectListResp>(
                  this, METHODID_SELECT_PROJECT_LIST)))
          .addMethod(
            METHOD_GET_BY_PROJECT_ID,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.GetByProjectIdReq,
                com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.GetByProjectIdResp>(
                  this, METHODID_GET_BY_PROJECT_ID)))
          .addMethod(
            METHOD_UPDATE_PROJECT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.UpdateProjectReq,
                com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.UpdateProjectResp>(
                  this, METHODID_UPDATE_PROJECT)))
          .addMethod(
            METHOD_SELECT_PROJECT_BY_CONDITION,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectByConditionReq,
                com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectByConditionResp>(
                  this, METHODID_SELECT_PROJECT_BY_CONDITION)))
          .build();
    }
  }

  /**
   */
  public static final class ProjectServiceStub extends io.grpc.stub.AbstractStub<ProjectServiceStub> {
    private ProjectServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ProjectServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProjectServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ProjectServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 查询所有的项目
     * </pre>
     */
    public void selectProjectList(com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectListReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectListResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_PROJECT_LIST, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *2 通过项目id获取该项目的信息
     * </pre>
     */
    public void getByProjectId(com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.GetByProjectIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.GetByProjectIdResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_BY_PROJECT_ID, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *3 更新项目信息
     * </pre>
     */
    public void updateProject(com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.UpdateProjectReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.UpdateProjectResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_PROJECT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *4 项目信息多条件查询
     * </pre>
     */
    public void selectProjectByCondition(com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectByConditionReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectByConditionResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_PROJECT_BY_CONDITION, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ProjectServiceBlockingStub extends io.grpc.stub.AbstractStub<ProjectServiceBlockingStub> {
    private ProjectServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ProjectServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProjectServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ProjectServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 查询所有的项目
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectListResp selectProjectList(com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectListReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_PROJECT_LIST, getCallOptions(), request);
    }

    /**
     * <pre>
     *2 通过项目id获取该项目的信息
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.GetByProjectIdResp getByProjectId(com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.GetByProjectIdReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_BY_PROJECT_ID, getCallOptions(), request);
    }

    /**
     * <pre>
     *3 更新项目信息
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.UpdateProjectResp updateProject(com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.UpdateProjectReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_PROJECT, getCallOptions(), request);
    }

    /**
     * <pre>
     *4 项目信息多条件查询
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectByConditionResp selectProjectByCondition(com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectByConditionReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_PROJECT_BY_CONDITION, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ProjectServiceFutureStub extends io.grpc.stub.AbstractStub<ProjectServiceFutureStub> {
    private ProjectServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ProjectServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProjectServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ProjectServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1 查询所有的项目
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectListResp> selectProjectList(
        com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectListReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_PROJECT_LIST, getCallOptions()), request);
    }

    /**
     * <pre>
     *2 通过项目id获取该项目的信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.GetByProjectIdResp> getByProjectId(
        com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.GetByProjectIdReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_BY_PROJECT_ID, getCallOptions()), request);
    }

    /**
     * <pre>
     *3 更新项目信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.UpdateProjectResp> updateProject(
        com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.UpdateProjectReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_PROJECT, getCallOptions()), request);
    }

    /**
     * <pre>
     *4 项目信息多条件查询
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectByConditionResp> selectProjectByCondition(
        com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectByConditionReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_PROJECT_BY_CONDITION, getCallOptions()), request);
    }
  }

  private static final int METHODID_SELECT_PROJECT_LIST = 0;
  private static final int METHODID_GET_BY_PROJECT_ID = 1;
  private static final int METHODID_UPDATE_PROJECT = 2;
  private static final int METHODID_SELECT_PROJECT_BY_CONDITION = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ProjectServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ProjectServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SELECT_PROJECT_LIST:
          serviceImpl.selectProjectList((com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectListReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectListResp>) responseObserver);
          break;
        case METHODID_GET_BY_PROJECT_ID:
          serviceImpl.getByProjectId((com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.GetByProjectIdReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.GetByProjectIdResp>) responseObserver);
          break;
        case METHODID_UPDATE_PROJECT:
          serviceImpl.updateProject((com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.UpdateProjectReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.UpdateProjectResp>) responseObserver);
          break;
        case METHODID_SELECT_PROJECT_BY_CONDITION:
          serviceImpl.selectProjectByCondition((com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectByConditionReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.SelectProjectByConditionResp>) responseObserver);
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

  private static final class ProjectServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ProjectServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ProjectServiceDescriptorSupplier())
              .addMethod(METHOD_SELECT_PROJECT_LIST)
              .addMethod(METHOD_GET_BY_PROJECT_ID)
              .addMethod(METHOD_UPDATE_PROJECT)
              .addMethod(METHOD_SELECT_PROJECT_BY_CONDITION)
              .build();
        }
      }
    }
    return result;
  }
}
