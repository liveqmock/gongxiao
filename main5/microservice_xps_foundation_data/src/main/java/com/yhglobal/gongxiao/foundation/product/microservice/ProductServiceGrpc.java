package com.yhglobal.gongxiao.foundation.product.microservice;

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
    comments = "Source: product/Product.proto")
public final class ProductServiceGrpc {

  private ProductServiceGrpc() {}

  public static final String SERVICE_NAME = "ProductService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.InsertProductInfoReq,
      com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.InsertProductInfoResp> METHOD_INSERT_PRODUCT_INFO =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.InsertProductInfoReq, com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.InsertProductInfoResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ProductService", "insertProductInfo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.InsertProductInfoReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.InsertProductInfoResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.CommitProductBusinessInfoReq,
      com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.CommitProductBusinessInfoResp> METHOD_COMMIT_PRODUCT_BUSINESS_INFO =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.CommitProductBusinessInfoReq, com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.CommitProductBusinessInfoResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ProductService", "commitProductBusinessInfo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.CommitProductBusinessInfoReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.CommitProductBusinessInfoResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.AuditProductReq,
      com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.AuditProductResp> METHOD_AUDIT_PRODUCT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.AuditProductReq, com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.AuditProductResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ProductService", "auditProduct"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.AuditProductReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.AuditProductResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.EditProductReq,
      com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.EditProductResp> METHOD_EDIT_PRODUCT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.EditProductReq, com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.EditProductResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ProductService", "editProduct"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.EditProductReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.EditProductResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductInfoReq,
      com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductInfoResp> METHOD_SELECT_PRODUCT_INFO =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductInfoReq, com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductInfoResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ProductService", "selectProductInfo"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductInfoReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductInfoResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductIdReq,
      com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductIdResp> METHOD_GET_BY_PRODUCT_ID =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductIdReq, com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductIdResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ProductService", "getByProductId"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductIdReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductIdResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductModelReq,
      com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductModelResp> METHOD_GET_BY_PRODUCT_MODEL =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductModelReq, com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductModelResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ProductService", "getByProductModel"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductModelReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductModelResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByProjectIdReq,
      com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByProjectIdResp> METHOD_SELECT_PRODUCT_BY_PROJECT_ID =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByProjectIdReq, com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByProjectIdResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ProductService", "selectProductByProjectId"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByProjectIdReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByProjectIdResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByModelReq,
      com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByModelResp> METHOD_GET_PRODUCT_DETAIL_BY_MODEL =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByModelReq, com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByModelResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ProductService", "getProductDetailByModel"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByModelReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByModelResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByWmsProductCodeReq,
      com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByWmsProductCodeResp> METHOD_GET_BY_WMS_PRODUCT_CODE =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByWmsProductCodeReq, com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByWmsProductCodeResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ProductService", "getByWmsProductCode"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByWmsProductCodeReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByWmsProductCodeResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByConditionReq,
      com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByConditionResp> METHOD_SELECT_PRODUCT_BY_CONDITION =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByConditionReq, com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByConditionResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ProductService", "selectProductByCondition"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByConditionReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByConditionResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByIdReq,
      com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByIdResp> METHOD_GET_PRODUCT_DETAIL_BY_ID =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByIdReq, com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByIdResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ProductService", "getProductDetailById"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByIdReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByIdResp.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectAllProductReq,
      com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectAllProductResp> METHOD_SELECT_ALL_PRODUCT =
      io.grpc.MethodDescriptor.<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectAllProductReq, com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectAllProductResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ProductService", "selectAllProduct"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectAllProductReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectAllProductResp.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ProductServiceStub newStub(io.grpc.Channel channel) {
    return new ProductServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ProductServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ProductServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ProductServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ProductServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class ProductServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *1. 新增货品信息
     * </pre>
     */
    public void insertProductInfo(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.InsertProductInfoReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.InsertProductInfoResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_INSERT_PRODUCT_INFO, responseObserver);
    }

    /**
     * <pre>
     *2. 提交货品业务信息(提交新的关系,通过businessID判断是否之前有编辑)
     * </pre>
     */
    public void commitProductBusinessInfo(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.CommitProductBusinessInfoReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.CommitProductBusinessInfoResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_COMMIT_PRODUCT_BUSINESS_INFO, responseObserver);
    }

    /**
     * <pre>
     *3. 审核货品信息(审核通过/拒绝)
     * </pre>
     */
    public void auditProduct(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.AuditProductReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.AuditProductResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_AUDIT_PRODUCT, responseObserver);
    }

    /**
     * <pre>
     *4. 编辑货品信息(未曾编辑/曾编辑的区分是所传业务单号是否为空;区分是否已生效,判断basic状态)
     * </pre>
     */
    public void editProduct(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.EditProductReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.EditProductResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_EDIT_PRODUCT, responseObserver);
    }

    /**
     * <pre>
     *5. 查询货品信息(型号,货品名称,物料号,状态)
     * </pre>
     */
    public void selectProductInfo(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductInfoReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductInfoResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_PRODUCT_INFO, responseObserver);
    }

    /**
     * <pre>
     *6. 通过货品ID获取当前货品信息(不包含基础信息)
     * </pre>
     */
    public void getByProductId(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductIdResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_BY_PRODUCT_ID, responseObserver);
    }

    /**
     * <pre>
     *7. 获取货品信息详情(不包含基础信息)
     * </pre>
     */
    public void getByProductModel(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductModelReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductModelResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_BY_PRODUCT_MODEL, responseObserver);
    }

    /**
     * <pre>
     *9. 通过项目ID获取货品列表
     * </pre>
     */
    public void selectProductByProjectId(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByProjectIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByProjectIdResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_PRODUCT_BY_PROJECT_ID, responseObserver);
    }

    /**
     * <pre>
     *10通过型号找商品详情
     * </pre>
     */
    public void getProductDetailByModel(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByModelReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByModelResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_PRODUCT_DETAIL_BY_MODEL, responseObserver);
    }

    /**
     * <pre>
     *11通过wms商品编码获取当前货品信息
     * </pre>
     */
    public void getByWmsProductCode(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByWmsProductCodeReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByWmsProductCodeResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_BY_WMS_PRODUCT_CODE, responseObserver);
    }

    /**
     * <pre>
     *12 货品查询
     * </pre>
     */
    public void selectProductByCondition(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByConditionReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByConditionResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_PRODUCT_BY_CONDITION, responseObserver);
    }

    /**
     * <pre>
     *13. 获取货品信息详情(不包含基础信息)
     * </pre>
     */
    public void getProductDetailById(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByIdResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_PRODUCT_DETAIL_BY_ID, responseObserver);
    }

    /**
     * <pre>
     *13. 获取所有货品信息
     * </pre>
     */
    public void selectAllProduct(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectAllProductReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectAllProductResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_ALL_PRODUCT, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_INSERT_PRODUCT_INFO,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.InsertProductInfoReq,
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.InsertProductInfoResp>(
                  this, METHODID_INSERT_PRODUCT_INFO)))
          .addMethod(
            METHOD_COMMIT_PRODUCT_BUSINESS_INFO,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.CommitProductBusinessInfoReq,
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.CommitProductBusinessInfoResp>(
                  this, METHODID_COMMIT_PRODUCT_BUSINESS_INFO)))
          .addMethod(
            METHOD_AUDIT_PRODUCT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.AuditProductReq,
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.AuditProductResp>(
                  this, METHODID_AUDIT_PRODUCT)))
          .addMethod(
            METHOD_EDIT_PRODUCT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.EditProductReq,
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.EditProductResp>(
                  this, METHODID_EDIT_PRODUCT)))
          .addMethod(
            METHOD_SELECT_PRODUCT_INFO,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductInfoReq,
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductInfoResp>(
                  this, METHODID_SELECT_PRODUCT_INFO)))
          .addMethod(
            METHOD_GET_BY_PRODUCT_ID,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductIdReq,
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductIdResp>(
                  this, METHODID_GET_BY_PRODUCT_ID)))
          .addMethod(
            METHOD_GET_BY_PRODUCT_MODEL,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductModelReq,
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductModelResp>(
                  this, METHODID_GET_BY_PRODUCT_MODEL)))
          .addMethod(
            METHOD_SELECT_PRODUCT_BY_PROJECT_ID,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByProjectIdReq,
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByProjectIdResp>(
                  this, METHODID_SELECT_PRODUCT_BY_PROJECT_ID)))
          .addMethod(
            METHOD_GET_PRODUCT_DETAIL_BY_MODEL,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByModelReq,
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByModelResp>(
                  this, METHODID_GET_PRODUCT_DETAIL_BY_MODEL)))
          .addMethod(
            METHOD_GET_BY_WMS_PRODUCT_CODE,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByWmsProductCodeReq,
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByWmsProductCodeResp>(
                  this, METHODID_GET_BY_WMS_PRODUCT_CODE)))
          .addMethod(
            METHOD_SELECT_PRODUCT_BY_CONDITION,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByConditionReq,
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByConditionResp>(
                  this, METHODID_SELECT_PRODUCT_BY_CONDITION)))
          .addMethod(
            METHOD_GET_PRODUCT_DETAIL_BY_ID,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByIdReq,
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByIdResp>(
                  this, METHODID_GET_PRODUCT_DETAIL_BY_ID)))
          .addMethod(
            METHOD_SELECT_ALL_PRODUCT,
            asyncUnaryCall(
              new MethodHandlers<
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectAllProductReq,
                com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectAllProductResp>(
                  this, METHODID_SELECT_ALL_PRODUCT)))
          .build();
    }
  }

  /**
   */
  public static final class ProductServiceStub extends io.grpc.stub.AbstractStub<ProductServiceStub> {
    private ProductServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ProductServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ProductServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *1. 新增货品信息
     * </pre>
     */
    public void insertProductInfo(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.InsertProductInfoReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.InsertProductInfoResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_INSERT_PRODUCT_INFO, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *2. 提交货品业务信息(提交新的关系,通过businessID判断是否之前有编辑)
     * </pre>
     */
    public void commitProductBusinessInfo(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.CommitProductBusinessInfoReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.CommitProductBusinessInfoResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_COMMIT_PRODUCT_BUSINESS_INFO, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *3. 审核货品信息(审核通过/拒绝)
     * </pre>
     */
    public void auditProduct(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.AuditProductReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.AuditProductResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_AUDIT_PRODUCT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *4. 编辑货品信息(未曾编辑/曾编辑的区分是所传业务单号是否为空;区分是否已生效,判断basic状态)
     * </pre>
     */
    public void editProduct(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.EditProductReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.EditProductResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_EDIT_PRODUCT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *5. 查询货品信息(型号,货品名称,物料号,状态)
     * </pre>
     */
    public void selectProductInfo(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductInfoReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductInfoResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_PRODUCT_INFO, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *6. 通过货品ID获取当前货品信息(不包含基础信息)
     * </pre>
     */
    public void getByProductId(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductIdResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_BY_PRODUCT_ID, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *7. 获取货品信息详情(不包含基础信息)
     * </pre>
     */
    public void getByProductModel(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductModelReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductModelResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_BY_PRODUCT_MODEL, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *9. 通过项目ID获取货品列表
     * </pre>
     */
    public void selectProductByProjectId(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByProjectIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByProjectIdResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_PRODUCT_BY_PROJECT_ID, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *10通过型号找商品详情
     * </pre>
     */
    public void getProductDetailByModel(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByModelReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByModelResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_PRODUCT_DETAIL_BY_MODEL, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *11通过wms商品编码获取当前货品信息
     * </pre>
     */
    public void getByWmsProductCode(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByWmsProductCodeReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByWmsProductCodeResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_BY_WMS_PRODUCT_CODE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *12 货品查询
     * </pre>
     */
    public void selectProductByCondition(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByConditionReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByConditionResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_PRODUCT_BY_CONDITION, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *13. 获取货品信息详情(不包含基础信息)
     * </pre>
     */
    public void getProductDetailById(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByIdReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByIdResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_PRODUCT_DETAIL_BY_ID, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *13. 获取所有货品信息
     * </pre>
     */
    public void selectAllProduct(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectAllProductReq request,
        io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectAllProductResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_ALL_PRODUCT, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ProductServiceBlockingStub extends io.grpc.stub.AbstractStub<ProductServiceBlockingStub> {
    private ProductServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ProductServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ProductServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *1. 新增货品信息
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.InsertProductInfoResp insertProductInfo(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.InsertProductInfoReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_INSERT_PRODUCT_INFO, getCallOptions(), request);
    }

    /**
     * <pre>
     *2. 提交货品业务信息(提交新的关系,通过businessID判断是否之前有编辑)
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.CommitProductBusinessInfoResp commitProductBusinessInfo(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.CommitProductBusinessInfoReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_COMMIT_PRODUCT_BUSINESS_INFO, getCallOptions(), request);
    }

    /**
     * <pre>
     *3. 审核货品信息(审核通过/拒绝)
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.AuditProductResp auditProduct(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.AuditProductReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_AUDIT_PRODUCT, getCallOptions(), request);
    }

    /**
     * <pre>
     *4. 编辑货品信息(未曾编辑/曾编辑的区分是所传业务单号是否为空;区分是否已生效,判断basic状态)
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.EditProductResp editProduct(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.EditProductReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_EDIT_PRODUCT, getCallOptions(), request);
    }

    /**
     * <pre>
     *5. 查询货品信息(型号,货品名称,物料号,状态)
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductInfoResp selectProductInfo(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductInfoReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_PRODUCT_INFO, getCallOptions(), request);
    }

    /**
     * <pre>
     *6. 通过货品ID获取当前货品信息(不包含基础信息)
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductIdResp getByProductId(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductIdReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_BY_PRODUCT_ID, getCallOptions(), request);
    }

    /**
     * <pre>
     *7. 获取货品信息详情(不包含基础信息)
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductModelResp getByProductModel(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductModelReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_BY_PRODUCT_MODEL, getCallOptions(), request);
    }

    /**
     * <pre>
     *9. 通过项目ID获取货品列表
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByProjectIdResp selectProductByProjectId(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByProjectIdReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_PRODUCT_BY_PROJECT_ID, getCallOptions(), request);
    }

    /**
     * <pre>
     *10通过型号找商品详情
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByModelResp getProductDetailByModel(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByModelReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_PRODUCT_DETAIL_BY_MODEL, getCallOptions(), request);
    }

    /**
     * <pre>
     *11通过wms商品编码获取当前货品信息
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByWmsProductCodeResp getByWmsProductCode(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByWmsProductCodeReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_BY_WMS_PRODUCT_CODE, getCallOptions(), request);
    }

    /**
     * <pre>
     *12 货品查询
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByConditionResp selectProductByCondition(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByConditionReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_PRODUCT_BY_CONDITION, getCallOptions(), request);
    }

    /**
     * <pre>
     *13. 获取货品信息详情(不包含基础信息)
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByIdResp getProductDetailById(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByIdReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_PRODUCT_DETAIL_BY_ID, getCallOptions(), request);
    }

    /**
     * <pre>
     *13. 获取所有货品信息
     * </pre>
     */
    public com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectAllProductResp selectAllProduct(com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectAllProductReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_ALL_PRODUCT, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ProductServiceFutureStub extends io.grpc.stub.AbstractStub<ProductServiceFutureStub> {
    private ProductServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ProductServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ProductServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *1. 新增货品信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.InsertProductInfoResp> insertProductInfo(
        com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.InsertProductInfoReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_INSERT_PRODUCT_INFO, getCallOptions()), request);
    }

    /**
     * <pre>
     *2. 提交货品业务信息(提交新的关系,通过businessID判断是否之前有编辑)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.CommitProductBusinessInfoResp> commitProductBusinessInfo(
        com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.CommitProductBusinessInfoReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_COMMIT_PRODUCT_BUSINESS_INFO, getCallOptions()), request);
    }

    /**
     * <pre>
     *3. 审核货品信息(审核通过/拒绝)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.AuditProductResp> auditProduct(
        com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.AuditProductReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_AUDIT_PRODUCT, getCallOptions()), request);
    }

    /**
     * <pre>
     *4. 编辑货品信息(未曾编辑/曾编辑的区分是所传业务单号是否为空;区分是否已生效,判断basic状态)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.EditProductResp> editProduct(
        com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.EditProductReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_EDIT_PRODUCT, getCallOptions()), request);
    }

    /**
     * <pre>
     *5. 查询货品信息(型号,货品名称,物料号,状态)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductInfoResp> selectProductInfo(
        com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductInfoReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_PRODUCT_INFO, getCallOptions()), request);
    }

    /**
     * <pre>
     *6. 通过货品ID获取当前货品信息(不包含基础信息)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductIdResp> getByProductId(
        com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductIdReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_BY_PRODUCT_ID, getCallOptions()), request);
    }

    /**
     * <pre>
     *7. 获取货品信息详情(不包含基础信息)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductModelResp> getByProductModel(
        com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductModelReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_BY_PRODUCT_MODEL, getCallOptions()), request);
    }

    /**
     * <pre>
     *9. 通过项目ID获取货品列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByProjectIdResp> selectProductByProjectId(
        com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByProjectIdReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_PRODUCT_BY_PROJECT_ID, getCallOptions()), request);
    }

    /**
     * <pre>
     *10通过型号找商品详情
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByModelResp> getProductDetailByModel(
        com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByModelReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_PRODUCT_DETAIL_BY_MODEL, getCallOptions()), request);
    }

    /**
     * <pre>
     *11通过wms商品编码获取当前货品信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByWmsProductCodeResp> getByWmsProductCode(
        com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByWmsProductCodeReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_BY_WMS_PRODUCT_CODE, getCallOptions()), request);
    }

    /**
     * <pre>
     *12 货品查询
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByConditionResp> selectProductByCondition(
        com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByConditionReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_PRODUCT_BY_CONDITION, getCallOptions()), request);
    }

    /**
     * <pre>
     *13. 获取货品信息详情(不包含基础信息)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByIdResp> getProductDetailById(
        com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByIdReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_PRODUCT_DETAIL_BY_ID, getCallOptions()), request);
    }

    /**
     * <pre>
     *13. 获取所有货品信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectAllProductResp> selectAllProduct(
        com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectAllProductReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_ALL_PRODUCT, getCallOptions()), request);
    }
  }

  private static final int METHODID_INSERT_PRODUCT_INFO = 0;
  private static final int METHODID_COMMIT_PRODUCT_BUSINESS_INFO = 1;
  private static final int METHODID_AUDIT_PRODUCT = 2;
  private static final int METHODID_EDIT_PRODUCT = 3;
  private static final int METHODID_SELECT_PRODUCT_INFO = 4;
  private static final int METHODID_GET_BY_PRODUCT_ID = 5;
  private static final int METHODID_GET_BY_PRODUCT_MODEL = 6;
  private static final int METHODID_SELECT_PRODUCT_BY_PROJECT_ID = 7;
  private static final int METHODID_GET_PRODUCT_DETAIL_BY_MODEL = 8;
  private static final int METHODID_GET_BY_WMS_PRODUCT_CODE = 9;
  private static final int METHODID_SELECT_PRODUCT_BY_CONDITION = 10;
  private static final int METHODID_GET_PRODUCT_DETAIL_BY_ID = 11;
  private static final int METHODID_SELECT_ALL_PRODUCT = 12;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ProductServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ProductServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_INSERT_PRODUCT_INFO:
          serviceImpl.insertProductInfo((com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.InsertProductInfoReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.InsertProductInfoResp>) responseObserver);
          break;
        case METHODID_COMMIT_PRODUCT_BUSINESS_INFO:
          serviceImpl.commitProductBusinessInfo((com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.CommitProductBusinessInfoReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.CommitProductBusinessInfoResp>) responseObserver);
          break;
        case METHODID_AUDIT_PRODUCT:
          serviceImpl.auditProduct((com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.AuditProductReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.AuditProductResp>) responseObserver);
          break;
        case METHODID_EDIT_PRODUCT:
          serviceImpl.editProduct((com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.EditProductReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.EditProductResp>) responseObserver);
          break;
        case METHODID_SELECT_PRODUCT_INFO:
          serviceImpl.selectProductInfo((com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductInfoReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductInfoResp>) responseObserver);
          break;
        case METHODID_GET_BY_PRODUCT_ID:
          serviceImpl.getByProductId((com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductIdReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductIdResp>) responseObserver);
          break;
        case METHODID_GET_BY_PRODUCT_MODEL:
          serviceImpl.getByProductModel((com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductModelReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByProductModelResp>) responseObserver);
          break;
        case METHODID_SELECT_PRODUCT_BY_PROJECT_ID:
          serviceImpl.selectProductByProjectId((com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByProjectIdReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByProjectIdResp>) responseObserver);
          break;
        case METHODID_GET_PRODUCT_DETAIL_BY_MODEL:
          serviceImpl.getProductDetailByModel((com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByModelReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByModelResp>) responseObserver);
          break;
        case METHODID_GET_BY_WMS_PRODUCT_CODE:
          serviceImpl.getByWmsProductCode((com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByWmsProductCodeReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetByWmsProductCodeResp>) responseObserver);
          break;
        case METHODID_SELECT_PRODUCT_BY_CONDITION:
          serviceImpl.selectProductByCondition((com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByConditionReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectProductByConditionResp>) responseObserver);
          break;
        case METHODID_GET_PRODUCT_DETAIL_BY_ID:
          serviceImpl.getProductDetailById((com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByIdReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.GetProductDetailByIdResp>) responseObserver);
          break;
        case METHODID_SELECT_ALL_PRODUCT:
          serviceImpl.selectAllProduct((com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectAllProductReq) request,
              (io.grpc.stub.StreamObserver<com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.SelectAllProductResp>) responseObserver);
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

  private static final class ProductServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ProductServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ProductServiceDescriptorSupplier())
              .addMethod(METHOD_INSERT_PRODUCT_INFO)
              .addMethod(METHOD_COMMIT_PRODUCT_BUSINESS_INFO)
              .addMethod(METHOD_AUDIT_PRODUCT)
              .addMethod(METHOD_EDIT_PRODUCT)
              .addMethod(METHOD_SELECT_PRODUCT_INFO)
              .addMethod(METHOD_GET_BY_PRODUCT_ID)
              .addMethod(METHOD_GET_BY_PRODUCT_MODEL)
              .addMethod(METHOD_SELECT_PRODUCT_BY_PROJECT_ID)
              .addMethod(METHOD_GET_PRODUCT_DETAIL_BY_MODEL)
              .addMethod(METHOD_GET_BY_WMS_PRODUCT_CODE)
              .addMethod(METHOD_SELECT_PRODUCT_BY_CONDITION)
              .addMethod(METHOD_GET_PRODUCT_DETAIL_BY_ID)
              .addMethod(METHOD_SELECT_ALL_PRODUCT)
              .build();
        }
      }
    }
    return result;
  }
}
