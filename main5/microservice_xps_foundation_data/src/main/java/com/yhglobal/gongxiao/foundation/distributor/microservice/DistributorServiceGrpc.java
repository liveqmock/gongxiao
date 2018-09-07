package com.yhglobal.gongxiao.foundation.distributor.microservice;

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
    comments = "Source: distributor/Distributor.proto")
public final class DistributorServiceGrpc {

  private DistributorServiceGrpc() {}

  public static final String SERVICE_NAME = "DistributorService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorInfoReq,
      com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorInfoResp> METHOD_INSERT_DISTRIBUTOR_INFO =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorInfoReq, com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorInfoResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorService", "insertDistributorInfo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorInfoReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorInfoResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorBusinessReq,
      com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorBusinessResp> METHOD_INSERT_DISTRIBUTOR_BUSINESS =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorBusinessReq, com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorBusinessResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorService", "insertDistributorBusiness"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorBusinessReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorBusinessResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorAccountReq,
      com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorAccountResp> METHOD_INSERT_DISTRIBUTOR_ACCOUNT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorAccountReq, com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorAccountResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorService", "insertDistributorAccount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorAccountReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorAccountResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorReq,
      com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorResp> METHOD_AUDIT_ADD_DISTRIBUTOR =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorReq, com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorService", "auditAddDistributor"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorBusinessReq,
      com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorBusinessResp> METHOD_AUDIT_ADD_DISTRIBUTOR_BUSINESS =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorBusinessReq, com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorBusinessResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorService", "auditAddDistributorBusiness"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorBusinessReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorBusinessResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorEasInfoReq,
      com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorEasInfoResp> METHOD_UPDATE_DISTRIBUTOR_EAS_INFO =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorEasInfoReq, com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorEasInfoResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorService", "updateDistributorEasInfo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorEasInfoReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorEasInfoResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorAccountPwdReq,
      com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorAccountPwdResp> METHOD_UPDATE_DISTRIBUTOR_ACCOUNT_PWD =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorAccountPwdReq, com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorAccountPwdResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorService", "updateDistributorAccountPwd"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorAccountPwdReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorAccountPwdResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAllDistributorBasicListReq,
      com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAllDistributorBasicListResp> METHOD_SELECT_ALL_DISTRIBUTOR_BASIC_LIST =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAllDistributorBasicListReq, com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAllDistributorBasicListResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorService", "selectAllDistributorBasicList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAllDistributorBasicListReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAllDistributorBasicListResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectBusinessListByProjectReq,
      com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectBusinessListByProjectResp> METHOD_SELECT_BUSINESS_LIST_BY_PROJECT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectBusinessListByProjectReq, com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectBusinessListByProjectResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorService", "selectBusinessListByProject"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectBusinessListByProjectReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectBusinessListByProjectResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAccountListByBusinessIdReq,
      com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAccountListByBusinessIdResp> METHOD_SELECT_ACCOUNT_LIST_BY_BUSINESS_ID =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAccountListByBusinessIdReq, com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAccountListByBusinessIdResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorService", "selectAccountListByBusinessId"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAccountListByBusinessIdReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAccountListByBusinessIdResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBasicByIdReq,
      com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBasicByIdResp> METHOD_GET_DISTRIBUTOR_BASIC_BY_ID =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBasicByIdReq, com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBasicByIdResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorService", "getDistributorBasicById"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBasicByIdReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBasicByIdResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBusinessByIdReq,
      com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBusinessByIdResp> METHOD_GET_DISTRIBUTOR_BUSINESS_BY_ID =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBusinessByIdReq, com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBusinessByIdResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorService", "getDistributorBusinessById"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBusinessByIdReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBusinessByIdResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorAccountByIdReq,
      com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorAccountByIdResp> METHOD_GET_DISTRIBUTOR_ACCOUNT_BY_ID =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorAccountByIdReq, com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorAccountByIdResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorService", "getDistributorAccountById"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorAccountByIdReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorAccountByIdResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorAccountByConditionReq,
      com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorAccountByConditionResp> METHOD_SELECT_DISTRIBUTOR_ACCOUNT_BY_CONDITION =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorAccountByConditionReq, com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorAccountByConditionResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorService", "selectDistributorAccountByCondition"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorAccountByConditionReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorAccountByConditionResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectUserByConditionReq,
      com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectUserByConditionResp> METHOD_SELECT_USER_BY_CONDITION =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectUserByConditionReq, com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectUserByConditionResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorService", "selectUserByCondition"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectUserByConditionReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectUserByConditionResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.EditDistributorReq,
      com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.EditDistributorResp> METHOD_EDIT_DISTRIBUTOR =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.EditDistributorReq, com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.EditDistributorResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorService", "editDistributor"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.EditDistributorReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.EditDistributorResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorDetailReq,
      com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorDetailResp> METHOD_GET_DISTRIBUTOR_DETAIL =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorDetailReq, com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorDetailResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorService", "getDistributorDetail"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorDetailReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorDetailResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorBusinessByConditionReq,
      com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorBusinessByConditionResp> METHOD_SELECT_DISTRIBUTOR_BUSINESS_BY_CONDITION =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorBusinessByConditionReq, com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorBusinessByConditionResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorService", "selectDistributorBusinessByCondition"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorBusinessByConditionReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorBusinessByConditionResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistiruborAccountByNameReq,
      com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistiruborAccountByNameResp> METHOD_GET_DISTIRUBOR_ACCOUNT_BY_NAME =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistiruborAccountByNameReq, com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistiruborAccountByNameResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorService", "getDistiruborAccountByName"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistiruborAccountByNameReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistiruborAccountByNameResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetAccountListByaccountReq,
      com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetAccountListByaccountResp> METHOD_GET_ACCOUNT_LIST_BYACCOUNT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetAccountListByaccountReq, com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetAccountListByaccountResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "DistributorService", "getAccountListByaccount"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetAccountListByaccountReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetAccountListByaccountResp.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DistributorServiceStub newStub(io.grpc.Channel channel) {
    return new DistributorServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DistributorServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new DistributorServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DistributorServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new DistributorServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class DistributorServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1. 新增经销商信息
     * </pre>
     */
    public void insertDistributorInfo(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorInfoReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorInfoResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_INSERT_DISTRIBUTOR_INFO, responseObserver);
    }

    /**
     * <pre>
     *2. 添加经销商跟项目的关系
     * </pre>
     */
    public void insertDistributorBusiness(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorBusinessReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorBusinessResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_INSERT_DISTRIBUTOR_BUSINESS, responseObserver);
    }

    /**
     * <pre>
     *3. 新增一个登陆账号(新增后,账号不可变)
     * </pre>
     */
    public void insertDistributorAccount(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorAccountReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorAccountResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_INSERT_DISTRIBUTOR_ACCOUNT, responseObserver);
    }

    /**
     * <pre>
     *4. 审核经销商新增(审核,驳回共用一个接口)
     * </pre>
     */
    public void auditAddDistributor(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_AUDIT_ADD_DISTRIBUTOR, responseObserver);
    }

    /**
     * <pre>
     *5. 审核经销商项目关系新增(审核,驳回共用一个接口)
     * </pre>
     */
    public void auditAddDistributorBusiness(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorBusinessReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorBusinessResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_AUDIT_ADD_DISTRIBUTOR_BUSINESS, responseObserver);
    }

    /**
     * <pre>
     *6. 变更经销商eascode,
     * </pre>
     */
    public void updateDistributorEasInfo(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorEasInfoReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorEasInfoResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_DISTRIBUTOR_EAS_INFO, responseObserver);
    }

    /**
     * <pre>
     *7. 变更经销商密码
     * </pre>
     */
    public void updateDistributorAccountPwd(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorAccountPwdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorAccountPwdResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_DISTRIBUTOR_ACCOUNT_PWD, responseObserver);
    }

    /**
     * <pre>
     *8. 获取所有经销商基础信息列表
     * </pre>
     */
    public void selectAllDistributorBasicList(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAllDistributorBasicListReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAllDistributorBasicListResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_ALL_DISTRIBUTOR_BASIC_LIST, responseObserver);
    }

    /**
     * <pre>
     *9. 获取某个项目下经销商列表
     * </pre>
     */
    public void selectBusinessListByProject(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectBusinessListByProjectReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectBusinessListByProjectResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_BUSINESS_LIST_BY_PROJECT, responseObserver);
    }

    /**
     * <pre>
     *10. 获取某项目,某经销商的登录账号列表
     * </pre>
     */
    public void selectAccountListByBusinessId(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAccountListByBusinessIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAccountListByBusinessIdResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_ACCOUNT_LIST_BY_BUSINESS_ID, responseObserver);
    }

    /**
     * <pre>
     *11. 通过经销商基础信息ID获取经销商基础信息详情
     * </pre>
     */
    public void getDistributorBasicById(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBasicByIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBasicByIdResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_DISTRIBUTOR_BASIC_BY_ID, responseObserver);
    }

    /**
     * <pre>
     *12. 通过经销商项目关系id,获取经销商项目关系详情
     * </pre>
     */
    public void getDistributorBusinessById(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBusinessByIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBusinessByIdResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_DISTRIBUTOR_BUSINESS_BY_ID, responseObserver);
    }

    /**
     * <pre>
     *13. 通过经销商登录账号id,获取经销商登录账号详情
     * </pre>
     */
    public void getDistributorAccountById(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorAccountByIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorAccountByIdResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_DISTRIBUTOR_ACCOUNT_BY_ID, responseObserver);
    }

    /**
     * <pre>
     *14. 通过经销商account,name 获取经销商账号信息
     * </pre>
     */
    public void selectDistributorAccountByCondition(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorAccountByConditionReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorAccountByConditionResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_DISTRIBUTOR_ACCOUNT_BY_CONDITION, responseObserver);
    }

    /**
     * <pre>
     *15. 条件查询接口
     * </pre>
     */
    public void selectUserByCondition(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectUserByConditionReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectUserByConditionResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_USER_BY_CONDITION, responseObserver);
    }

    /**
     * <pre>
     *16 编辑接口
     * </pre>
     */
    public void editDistributor(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.EditDistributorReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.EditDistributorResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_EDIT_DISTRIBUTOR, responseObserver);
    }

    /**
     * <pre>
     *17 查看详情
     * </pre>
     */
    public void getDistributorDetail(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorDetailReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorDetailResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_DISTRIBUTOR_DETAIL, responseObserver);
    }

    /**
     * <pre>
     *18
     * </pre>
     */
    public void selectDistributorBusinessByCondition(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorBusinessByConditionReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorBusinessByConditionResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_DISTRIBUTOR_BUSINESS_BY_CONDITION, responseObserver);
    }

    /**
     * <pre>
     *19
     * </pre>
     */
    public void getDistiruborAccountByName(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistiruborAccountByNameReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistiruborAccountByNameResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_DISTIRUBOR_ACCOUNT_BY_NAME, responseObserver);
    }

    /**
     * <pre>
     *20 通过账号获取账号列表
     * </pre>
     */
    public void getAccountListByaccount(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetAccountListByaccountReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetAccountListByaccountResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_ACCOUNT_LIST_BYACCOUNT, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_INSERT_DISTRIBUTOR_INFO,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorInfoReq,
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorInfoResp>(
                  this, METHODID_INSERT_DISTRIBUTOR_INFO)))
          .addMethod(
            METHOD_INSERT_DISTRIBUTOR_BUSINESS,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorBusinessReq,
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorBusinessResp>(
                  this, METHODID_INSERT_DISTRIBUTOR_BUSINESS)))
          .addMethod(
            METHOD_INSERT_DISTRIBUTOR_ACCOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorAccountReq,
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorAccountResp>(
                  this, METHODID_INSERT_DISTRIBUTOR_ACCOUNT)))
          .addMethod(
            METHOD_AUDIT_ADD_DISTRIBUTOR,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorReq,
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorResp>(
                  this, METHODID_AUDIT_ADD_DISTRIBUTOR)))
          .addMethod(
            METHOD_AUDIT_ADD_DISTRIBUTOR_BUSINESS,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorBusinessReq,
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorBusinessResp>(
                  this, METHODID_AUDIT_ADD_DISTRIBUTOR_BUSINESS)))
          .addMethod(
            METHOD_UPDATE_DISTRIBUTOR_EAS_INFO,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorEasInfoReq,
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorEasInfoResp>(
                  this, METHODID_UPDATE_DISTRIBUTOR_EAS_INFO)))
          .addMethod(
            METHOD_UPDATE_DISTRIBUTOR_ACCOUNT_PWD,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorAccountPwdReq,
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorAccountPwdResp>(
                  this, METHODID_UPDATE_DISTRIBUTOR_ACCOUNT_PWD)))
          .addMethod(
            METHOD_SELECT_ALL_DISTRIBUTOR_BASIC_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAllDistributorBasicListReq,
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAllDistributorBasicListResp>(
                  this, METHODID_SELECT_ALL_DISTRIBUTOR_BASIC_LIST)))
          .addMethod(
            METHOD_SELECT_BUSINESS_LIST_BY_PROJECT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectBusinessListByProjectReq,
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectBusinessListByProjectResp>(
                  this, METHODID_SELECT_BUSINESS_LIST_BY_PROJECT)))
          .addMethod(
            METHOD_SELECT_ACCOUNT_LIST_BY_BUSINESS_ID,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAccountListByBusinessIdReq,
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAccountListByBusinessIdResp>(
                  this, METHODID_SELECT_ACCOUNT_LIST_BY_BUSINESS_ID)))
          .addMethod(
            METHOD_GET_DISTRIBUTOR_BASIC_BY_ID,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBasicByIdReq,
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBasicByIdResp>(
                  this, METHODID_GET_DISTRIBUTOR_BASIC_BY_ID)))
          .addMethod(
            METHOD_GET_DISTRIBUTOR_BUSINESS_BY_ID,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBusinessByIdReq,
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBusinessByIdResp>(
                  this, METHODID_GET_DISTRIBUTOR_BUSINESS_BY_ID)))
          .addMethod(
            METHOD_GET_DISTRIBUTOR_ACCOUNT_BY_ID,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorAccountByIdReq,
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorAccountByIdResp>(
                  this, METHODID_GET_DISTRIBUTOR_ACCOUNT_BY_ID)))
          .addMethod(
            METHOD_SELECT_DISTRIBUTOR_ACCOUNT_BY_CONDITION,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorAccountByConditionReq,
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorAccountByConditionResp>(
                  this, METHODID_SELECT_DISTRIBUTOR_ACCOUNT_BY_CONDITION)))
          .addMethod(
            METHOD_SELECT_USER_BY_CONDITION,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectUserByConditionReq,
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectUserByConditionResp>(
                  this, METHODID_SELECT_USER_BY_CONDITION)))
          .addMethod(
            METHOD_EDIT_DISTRIBUTOR,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.EditDistributorReq,
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.EditDistributorResp>(
                  this, METHODID_EDIT_DISTRIBUTOR)))
          .addMethod(
            METHOD_GET_DISTRIBUTOR_DETAIL,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorDetailReq,
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorDetailResp>(
                  this, METHODID_GET_DISTRIBUTOR_DETAIL)))
          .addMethod(
            METHOD_SELECT_DISTRIBUTOR_BUSINESS_BY_CONDITION,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorBusinessByConditionReq,
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorBusinessByConditionResp>(
                  this, METHODID_SELECT_DISTRIBUTOR_BUSINESS_BY_CONDITION)))
          .addMethod(
            METHOD_GET_DISTIRUBOR_ACCOUNT_BY_NAME,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistiruborAccountByNameReq,
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistiruborAccountByNameResp>(
                  this, METHODID_GET_DISTIRUBOR_ACCOUNT_BY_NAME)))
          .addMethod(
            METHOD_GET_ACCOUNT_LIST_BYACCOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetAccountListByaccountReq,
                com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetAccountListByaccountResp>(
                  this, METHODID_GET_ACCOUNT_LIST_BYACCOUNT)))
          .build();
    }
  }

  /**
   */
  public static final class DistributorServiceStub extends io.grpc.stub.AbstractStub<DistributorServiceStub> {
    private DistributorServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DistributorServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DistributorServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DistributorServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1. 新增经销商信息
     * </pre>
     */
    public void insertDistributorInfo(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorInfoReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorInfoResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_INSERT_DISTRIBUTOR_INFO, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *2. 添加经销商跟项目的关系
     * </pre>
     */
    public void insertDistributorBusiness(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorBusinessReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorBusinessResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_INSERT_DISTRIBUTOR_BUSINESS, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *3. 新增一个登陆账号(新增后,账号不可变)
     * </pre>
     */
    public void insertDistributorAccount(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorAccountReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorAccountResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_INSERT_DISTRIBUTOR_ACCOUNT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *4. 审核经销商新增(审核,驳回共用一个接口)
     * </pre>
     */
    public void auditAddDistributor(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_AUDIT_ADD_DISTRIBUTOR, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *5. 审核经销商项目关系新增(审核,驳回共用一个接口)
     * </pre>
     */
    public void auditAddDistributorBusiness(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorBusinessReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorBusinessResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_AUDIT_ADD_DISTRIBUTOR_BUSINESS, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *6. 变更经销商eascode,
     * </pre>
     */
    public void updateDistributorEasInfo(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorEasInfoReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorEasInfoResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_DISTRIBUTOR_EAS_INFO, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *7. 变更经销商密码
     * </pre>
     */
    public void updateDistributorAccountPwd(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorAccountPwdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorAccountPwdResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_DISTRIBUTOR_ACCOUNT_PWD, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *8. 获取所有经销商基础信息列表
     * </pre>
     */
    public void selectAllDistributorBasicList(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAllDistributorBasicListReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAllDistributorBasicListResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_ALL_DISTRIBUTOR_BASIC_LIST, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *9. 获取某个项目下经销商列表
     * </pre>
     */
    public void selectBusinessListByProject(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectBusinessListByProjectReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectBusinessListByProjectResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_BUSINESS_LIST_BY_PROJECT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *10. 获取某项目,某经销商的登录账号列表
     * </pre>
     */
    public void selectAccountListByBusinessId(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAccountListByBusinessIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAccountListByBusinessIdResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_ACCOUNT_LIST_BY_BUSINESS_ID, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *11. 通过经销商基础信息ID获取经销商基础信息详情
     * </pre>
     */
    public void getDistributorBasicById(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBasicByIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBasicByIdResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_DISTRIBUTOR_BASIC_BY_ID, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *12. 通过经销商项目关系id,获取经销商项目关系详情
     * </pre>
     */
    public void getDistributorBusinessById(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBusinessByIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBusinessByIdResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_DISTRIBUTOR_BUSINESS_BY_ID, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *13. 通过经销商登录账号id,获取经销商登录账号详情
     * </pre>
     */
    public void getDistributorAccountById(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorAccountByIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorAccountByIdResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_DISTRIBUTOR_ACCOUNT_BY_ID, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *14. 通过经销商account,name 获取经销商账号信息
     * </pre>
     */
    public void selectDistributorAccountByCondition(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorAccountByConditionReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorAccountByConditionResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_DISTRIBUTOR_ACCOUNT_BY_CONDITION, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *15. 条件查询接口
     * </pre>
     */
    public void selectUserByCondition(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectUserByConditionReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectUserByConditionResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_USER_BY_CONDITION, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *16 编辑接口
     * </pre>
     */
    public void editDistributor(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.EditDistributorReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.EditDistributorResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_EDIT_DISTRIBUTOR, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *17 查看详情
     * </pre>
     */
    public void getDistributorDetail(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorDetailReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorDetailResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_DISTRIBUTOR_DETAIL, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *18
     * </pre>
     */
    public void selectDistributorBusinessByCondition(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorBusinessByConditionReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorBusinessByConditionResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_DISTRIBUTOR_BUSINESS_BY_CONDITION, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *19
     * </pre>
     */
    public void getDistiruborAccountByName(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistiruborAccountByNameReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistiruborAccountByNameResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_DISTIRUBOR_ACCOUNT_BY_NAME, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *20 通过账号获取账号列表
     * </pre>
     */
    public void getAccountListByaccount(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetAccountListByaccountReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetAccountListByaccountResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_ACCOUNT_LIST_BYACCOUNT, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class DistributorServiceBlockingStub extends io.grpc.stub.AbstractStub<DistributorServiceBlockingStub> {
    private DistributorServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DistributorServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DistributorServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DistributorServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1. 新增经销商信息
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorInfoResp insertDistributorInfo(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorInfoReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_INSERT_DISTRIBUTOR_INFO, getCallOptions(), request);
    }

    /**
     * <pre>
     *2. 添加经销商跟项目的关系
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorBusinessResp insertDistributorBusiness(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorBusinessReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_INSERT_DISTRIBUTOR_BUSINESS, getCallOptions(), request);
    }

    /**
     * <pre>
     *3. 新增一个登陆账号(新增后,账号不可变)
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorAccountResp insertDistributorAccount(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorAccountReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_INSERT_DISTRIBUTOR_ACCOUNT, getCallOptions(), request);
    }

    /**
     * <pre>
     *4. 审核经销商新增(审核,驳回共用一个接口)
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorResp auditAddDistributor(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_AUDIT_ADD_DISTRIBUTOR, getCallOptions(), request);
    }

    /**
     * <pre>
     *5. 审核经销商项目关系新增(审核,驳回共用一个接口)
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorBusinessResp auditAddDistributorBusiness(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorBusinessReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_AUDIT_ADD_DISTRIBUTOR_BUSINESS, getCallOptions(), request);
    }

    /**
     * <pre>
     *6. 变更经销商eascode,
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorEasInfoResp updateDistributorEasInfo(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorEasInfoReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_DISTRIBUTOR_EAS_INFO, getCallOptions(), request);
    }

    /**
     * <pre>
     *7. 变更经销商密码
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorAccountPwdResp updateDistributorAccountPwd(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorAccountPwdReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_DISTRIBUTOR_ACCOUNT_PWD, getCallOptions(), request);
    }

    /**
     * <pre>
     *8. 获取所有经销商基础信息列表
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAllDistributorBasicListResp selectAllDistributorBasicList(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAllDistributorBasicListReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_ALL_DISTRIBUTOR_BASIC_LIST, getCallOptions(), request);
    }

    /**
     * <pre>
     *9. 获取某个项目下经销商列表
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectBusinessListByProjectResp selectBusinessListByProject(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectBusinessListByProjectReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_BUSINESS_LIST_BY_PROJECT, getCallOptions(), request);
    }

    /**
     * <pre>
     *10. 获取某项目,某经销商的登录账号列表
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAccountListByBusinessIdResp selectAccountListByBusinessId(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAccountListByBusinessIdReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_ACCOUNT_LIST_BY_BUSINESS_ID, getCallOptions(), request);
    }

    /**
     * <pre>
     *11. 通过经销商基础信息ID获取经销商基础信息详情
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBasicByIdResp getDistributorBasicById(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBasicByIdReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_DISTRIBUTOR_BASIC_BY_ID, getCallOptions(), request);
    }

    /**
     * <pre>
     *12. 通过经销商项目关系id,获取经销商项目关系详情
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBusinessByIdResp getDistributorBusinessById(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBusinessByIdReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_DISTRIBUTOR_BUSINESS_BY_ID, getCallOptions(), request);
    }

    /**
     * <pre>
     *13. 通过经销商登录账号id,获取经销商登录账号详情
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorAccountByIdResp getDistributorAccountById(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorAccountByIdReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_DISTRIBUTOR_ACCOUNT_BY_ID, getCallOptions(), request);
    }

    /**
     * <pre>
     *14. 通过经销商account,name 获取经销商账号信息
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorAccountByConditionResp selectDistributorAccountByCondition(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorAccountByConditionReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_DISTRIBUTOR_ACCOUNT_BY_CONDITION, getCallOptions(), request);
    }

    /**
     * <pre>
     *15. 条件查询接口
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectUserByConditionResp selectUserByCondition(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectUserByConditionReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_USER_BY_CONDITION, getCallOptions(), request);
    }

    /**
     * <pre>
     *16 编辑接口
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.EditDistributorResp editDistributor(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.EditDistributorReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_EDIT_DISTRIBUTOR, getCallOptions(), request);
    }

    /**
     * <pre>
     *17 查看详情
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorDetailResp getDistributorDetail(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorDetailReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_DISTRIBUTOR_DETAIL, getCallOptions(), request);
    }

    /**
     * <pre>
     *18
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorBusinessByConditionResp selectDistributorBusinessByCondition(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorBusinessByConditionReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_DISTRIBUTOR_BUSINESS_BY_CONDITION, getCallOptions(), request);
    }

    /**
     * <pre>
     *19
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistiruborAccountByNameResp getDistiruborAccountByName(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistiruborAccountByNameReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_DISTIRUBOR_ACCOUNT_BY_NAME, getCallOptions(), request);
    }

    /**
     * <pre>
     *20 通过账号获取账号列表
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetAccountListByaccountResp getAccountListByaccount(com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetAccountListByaccountReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_ACCOUNT_LIST_BYACCOUNT, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class DistributorServiceFutureStub extends io.grpc.stub.AbstractStub<DistributorServiceFutureStub> {
    private DistributorServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DistributorServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DistributorServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DistributorServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1. 新增经销商信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorInfoResp> insertDistributorInfo(
        com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorInfoReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_INSERT_DISTRIBUTOR_INFO, getCallOptions()), request);
    }

    /**
     * <pre>
     *2. 添加经销商跟项目的关系
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorBusinessResp> insertDistributorBusiness(
        com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorBusinessReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_INSERT_DISTRIBUTOR_BUSINESS, getCallOptions()), request);
    }

    /**
     * <pre>
     *3. 新增一个登陆账号(新增后,账号不可变)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorAccountResp> insertDistributorAccount(
        com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorAccountReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_INSERT_DISTRIBUTOR_ACCOUNT, getCallOptions()), request);
    }

    /**
     * <pre>
     *4. 审核经销商新增(审核,驳回共用一个接口)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorResp> auditAddDistributor(
        com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_AUDIT_ADD_DISTRIBUTOR, getCallOptions()), request);
    }

    /**
     * <pre>
     *5. 审核经销商项目关系新增(审核,驳回共用一个接口)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorBusinessResp> auditAddDistributorBusiness(
        com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorBusinessReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_AUDIT_ADD_DISTRIBUTOR_BUSINESS, getCallOptions()), request);
    }

    /**
     * <pre>
     *6. 变更经销商eascode,
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorEasInfoResp> updateDistributorEasInfo(
        com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorEasInfoReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_DISTRIBUTOR_EAS_INFO, getCallOptions()), request);
    }

    /**
     * <pre>
     *7. 变更经销商密码
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorAccountPwdResp> updateDistributorAccountPwd(
        com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorAccountPwdReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_DISTRIBUTOR_ACCOUNT_PWD, getCallOptions()), request);
    }

    /**
     * <pre>
     *8. 获取所有经销商基础信息列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAllDistributorBasicListResp> selectAllDistributorBasicList(
        com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAllDistributorBasicListReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_ALL_DISTRIBUTOR_BASIC_LIST, getCallOptions()), request);
    }

    /**
     * <pre>
     *9. 获取某个项目下经销商列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectBusinessListByProjectResp> selectBusinessListByProject(
        com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectBusinessListByProjectReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_BUSINESS_LIST_BY_PROJECT, getCallOptions()), request);
    }

    /**
     * <pre>
     *10. 获取某项目,某经销商的登录账号列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAccountListByBusinessIdResp> selectAccountListByBusinessId(
        com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAccountListByBusinessIdReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_ACCOUNT_LIST_BY_BUSINESS_ID, getCallOptions()), request);
    }

    /**
     * <pre>
     *11. 通过经销商基础信息ID获取经销商基础信息详情
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBasicByIdResp> getDistributorBasicById(
        com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBasicByIdReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_DISTRIBUTOR_BASIC_BY_ID, getCallOptions()), request);
    }

    /**
     * <pre>
     *12. 通过经销商项目关系id,获取经销商项目关系详情
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBusinessByIdResp> getDistributorBusinessById(
        com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBusinessByIdReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_DISTRIBUTOR_BUSINESS_BY_ID, getCallOptions()), request);
    }

    /**
     * <pre>
     *13. 通过经销商登录账号id,获取经销商登录账号详情
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorAccountByIdResp> getDistributorAccountById(
        com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorAccountByIdReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_DISTRIBUTOR_ACCOUNT_BY_ID, getCallOptions()), request);
    }

    /**
     * <pre>
     *14. 通过经销商account,name 获取经销商账号信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorAccountByConditionResp> selectDistributorAccountByCondition(
        com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorAccountByConditionReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_DISTRIBUTOR_ACCOUNT_BY_CONDITION, getCallOptions()), request);
    }

    /**
     * <pre>
     *15. 条件查询接口
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectUserByConditionResp> selectUserByCondition(
        com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectUserByConditionReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_USER_BY_CONDITION, getCallOptions()), request);
    }

    /**
     * <pre>
     *16 编辑接口
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.EditDistributorResp> editDistributor(
        com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.EditDistributorReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_EDIT_DISTRIBUTOR, getCallOptions()), request);
    }

    /**
     * <pre>
     *17 查看详情
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorDetailResp> getDistributorDetail(
        com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorDetailReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_DISTRIBUTOR_DETAIL, getCallOptions()), request);
    }

    /**
     * <pre>
     *18
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorBusinessByConditionResp> selectDistributorBusinessByCondition(
        com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorBusinessByConditionReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_DISTRIBUTOR_BUSINESS_BY_CONDITION, getCallOptions()), request);
    }

    /**
     * <pre>
     *19
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistiruborAccountByNameResp> getDistiruborAccountByName(
        com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistiruborAccountByNameReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_DISTIRUBOR_ACCOUNT_BY_NAME, getCallOptions()), request);
    }

    /**
     * <pre>
     *20 通过账号获取账号列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetAccountListByaccountResp> getAccountListByaccount(
        com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetAccountListByaccountReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_ACCOUNT_LIST_BYACCOUNT, getCallOptions()), request);
    }
  }

  private static final int METHODID_INSERT_DISTRIBUTOR_INFO = 0;
  private static final int METHODID_INSERT_DISTRIBUTOR_BUSINESS = 1;
  private static final int METHODID_INSERT_DISTRIBUTOR_ACCOUNT = 2;
  private static final int METHODID_AUDIT_ADD_DISTRIBUTOR = 3;
  private static final int METHODID_AUDIT_ADD_DISTRIBUTOR_BUSINESS = 4;
  private static final int METHODID_UPDATE_DISTRIBUTOR_EAS_INFO = 5;
  private static final int METHODID_UPDATE_DISTRIBUTOR_ACCOUNT_PWD = 6;
  private static final int METHODID_SELECT_ALL_DISTRIBUTOR_BASIC_LIST = 7;
  private static final int METHODID_SELECT_BUSINESS_LIST_BY_PROJECT = 8;
  private static final int METHODID_SELECT_ACCOUNT_LIST_BY_BUSINESS_ID = 9;
  private static final int METHODID_GET_DISTRIBUTOR_BASIC_BY_ID = 10;
  private static final int METHODID_GET_DISTRIBUTOR_BUSINESS_BY_ID = 11;
  private static final int METHODID_GET_DISTRIBUTOR_ACCOUNT_BY_ID = 12;
  private static final int METHODID_SELECT_DISTRIBUTOR_ACCOUNT_BY_CONDITION = 13;
  private static final int METHODID_SELECT_USER_BY_CONDITION = 14;
  private static final int METHODID_EDIT_DISTRIBUTOR = 15;
  private static final int METHODID_GET_DISTRIBUTOR_DETAIL = 16;
  private static final int METHODID_SELECT_DISTRIBUTOR_BUSINESS_BY_CONDITION = 17;
  private static final int METHODID_GET_DISTIRUBOR_ACCOUNT_BY_NAME = 18;
  private static final int METHODID_GET_ACCOUNT_LIST_BYACCOUNT = 19;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DistributorServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DistributorServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_INSERT_DISTRIBUTOR_INFO:
          serviceImpl.insertDistributorInfo((com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorInfoReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorInfoResp>) responseObserver);
          break;
        case METHODID_INSERT_DISTRIBUTOR_BUSINESS:
          serviceImpl.insertDistributorBusiness((com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorBusinessReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorBusinessResp>) responseObserver);
          break;
        case METHODID_INSERT_DISTRIBUTOR_ACCOUNT:
          serviceImpl.insertDistributorAccount((com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorAccountReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.InsertDistributorAccountResp>) responseObserver);
          break;
        case METHODID_AUDIT_ADD_DISTRIBUTOR:
          serviceImpl.auditAddDistributor((com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorResp>) responseObserver);
          break;
        case METHODID_AUDIT_ADD_DISTRIBUTOR_BUSINESS:
          serviceImpl.auditAddDistributorBusiness((com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorBusinessReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.AuditAddDistributorBusinessResp>) responseObserver);
          break;
        case METHODID_UPDATE_DISTRIBUTOR_EAS_INFO:
          serviceImpl.updateDistributorEasInfo((com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorEasInfoReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorEasInfoResp>) responseObserver);
          break;
        case METHODID_UPDATE_DISTRIBUTOR_ACCOUNT_PWD:
          serviceImpl.updateDistributorAccountPwd((com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorAccountPwdReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.UpdateDistributorAccountPwdResp>) responseObserver);
          break;
        case METHODID_SELECT_ALL_DISTRIBUTOR_BASIC_LIST:
          serviceImpl.selectAllDistributorBasicList((com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAllDistributorBasicListReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAllDistributorBasicListResp>) responseObserver);
          break;
        case METHODID_SELECT_BUSINESS_LIST_BY_PROJECT:
          serviceImpl.selectBusinessListByProject((com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectBusinessListByProjectReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectBusinessListByProjectResp>) responseObserver);
          break;
        case METHODID_SELECT_ACCOUNT_LIST_BY_BUSINESS_ID:
          serviceImpl.selectAccountListByBusinessId((com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAccountListByBusinessIdReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectAccountListByBusinessIdResp>) responseObserver);
          break;
        case METHODID_GET_DISTRIBUTOR_BASIC_BY_ID:
          serviceImpl.getDistributorBasicById((com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBasicByIdReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBasicByIdResp>) responseObserver);
          break;
        case METHODID_GET_DISTRIBUTOR_BUSINESS_BY_ID:
          serviceImpl.getDistributorBusinessById((com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBusinessByIdReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorBusinessByIdResp>) responseObserver);
          break;
        case METHODID_GET_DISTRIBUTOR_ACCOUNT_BY_ID:
          serviceImpl.getDistributorAccountById((com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorAccountByIdReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorAccountByIdResp>) responseObserver);
          break;
        case METHODID_SELECT_DISTRIBUTOR_ACCOUNT_BY_CONDITION:
          serviceImpl.selectDistributorAccountByCondition((com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorAccountByConditionReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorAccountByConditionResp>) responseObserver);
          break;
        case METHODID_SELECT_USER_BY_CONDITION:
          serviceImpl.selectUserByCondition((com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectUserByConditionReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectUserByConditionResp>) responseObserver);
          break;
        case METHODID_EDIT_DISTRIBUTOR:
          serviceImpl.editDistributor((com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.EditDistributorReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.EditDistributorResp>) responseObserver);
          break;
        case METHODID_GET_DISTRIBUTOR_DETAIL:
          serviceImpl.getDistributorDetail((com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorDetailReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistributorDetailResp>) responseObserver);
          break;
        case METHODID_SELECT_DISTRIBUTOR_BUSINESS_BY_CONDITION:
          serviceImpl.selectDistributorBusinessByCondition((com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorBusinessByConditionReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.SelectDistributorBusinessByConditionResp>) responseObserver);
          break;
        case METHODID_GET_DISTIRUBOR_ACCOUNT_BY_NAME:
          serviceImpl.getDistiruborAccountByName((com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistiruborAccountByNameReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetDistiruborAccountByNameResp>) responseObserver);
          break;
        case METHODID_GET_ACCOUNT_LIST_BYACCOUNT:
          serviceImpl.getAccountListByaccount((com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetAccountListByaccountReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.GetAccountListByaccountResp>) responseObserver);
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

  private static final class DistributorServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (DistributorServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DistributorServiceDescriptorSupplier())
              .addMethod(METHOD_INSERT_DISTRIBUTOR_INFO)
              .addMethod(METHOD_INSERT_DISTRIBUTOR_BUSINESS)
              .addMethod(METHOD_INSERT_DISTRIBUTOR_ACCOUNT)
              .addMethod(METHOD_AUDIT_ADD_DISTRIBUTOR)
              .addMethod(METHOD_AUDIT_ADD_DISTRIBUTOR_BUSINESS)
              .addMethod(METHOD_UPDATE_DISTRIBUTOR_EAS_INFO)
              .addMethod(METHOD_UPDATE_DISTRIBUTOR_ACCOUNT_PWD)
              .addMethod(METHOD_SELECT_ALL_DISTRIBUTOR_BASIC_LIST)
              .addMethod(METHOD_SELECT_BUSINESS_LIST_BY_PROJECT)
              .addMethod(METHOD_SELECT_ACCOUNT_LIST_BY_BUSINESS_ID)
              .addMethod(METHOD_GET_DISTRIBUTOR_BASIC_BY_ID)
              .addMethod(METHOD_GET_DISTRIBUTOR_BUSINESS_BY_ID)
              .addMethod(METHOD_GET_DISTRIBUTOR_ACCOUNT_BY_ID)
              .addMethod(METHOD_SELECT_DISTRIBUTOR_ACCOUNT_BY_CONDITION)
              .addMethod(METHOD_SELECT_USER_BY_CONDITION)
              .addMethod(METHOD_EDIT_DISTRIBUTOR)
              .addMethod(METHOD_GET_DISTRIBUTOR_DETAIL)
              .addMethod(METHOD_SELECT_DISTRIBUTOR_BUSINESS_BY_CONDITION)
              .addMethod(METHOD_GET_DISTIRUBOR_ACCOUNT_BY_NAME)
              .addMethod(METHOD_GET_ACCOUNT_LIST_BYACCOUNT)
              .build();
        }
      }
    }
    return result;
  }
}
