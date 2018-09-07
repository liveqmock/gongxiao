package com.yhglobal.gongxiao.grpc.client.foundation;

import com.yhglobal.gongxiao.accountmanage.microservice.SupplierCouponPostingServiceGrpc;
import com.yhglobal.gongxiao.accountmanage.microservice.SupplierCouponPostingServiceStructure;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierServiceGrpc;
import com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseServiceGrpc;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;

/**
 * 调试时调用FoundationData服务
 */
public class FoundationDataServiceClient {

    public static void main(String[] args) {
        getProjectById("1");
//        selectAllDistributorAddress();
        // getProjectById(146798161);
        // GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        // generateFrontFlowSubtotal(306,"CNY",1L,146798161L,0L,9999999999999L);
//        generateFrontFlowSubtotal(-1, "CNY", 1L, 146798161L, 0L, 9999999999999L);
//        getProductInfo();
//        getdistributorCoupon();
//        getDistributorByDistributorId();
//        getByProductCode();
//        getSupplierByCode();
    }

    static void getWarehouseById(){
        GongxiaoRpc.RpcHeader rpcHeader = GongxiaoRpc.RpcHeader.newBuilder().setUid("1").setTraceId("11111").build();

        WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder()
                .setRpcHeader(rpcHeader)
                .setWarehouseId("1")
                .build();

        WarehouseStructure.GetWarehouseByIdResp getWarehouseByIdResp=null;

        try {
            WarehouseServiceGrpc.WarehouseServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
            getWarehouseByIdResp = rpcStub.getWarehouseById(getWarehouseByIdReq);

        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(getWarehouseByIdResp.toString());
    }

    static void getSupplierByCode(){
        GongxiaoRpc.RpcHeader rpcHeader = GongxiaoRpc.RpcHeader.newBuilder().setUid("1").setTraceId("11111").build();
        SupplierStructure.GetSupplierByCodeReq getSupplierByCodeReq = SupplierStructure.GetSupplierByCodeReq.newBuilder()
                .setRpcHeader(rpcHeader)
                .setSupplierCode("123456")
                .build();
        SupplierStructure.GetSupplierByCodeResp getSupplierByCodeResp=null;
        try {
            SupplierServiceGrpc.SupplierServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SupplierServiceGrpc.SupplierServiceBlockingStub.class);
            getSupplierByCodeResp = rpcStub.getSupplierByCode(getSupplierByCodeReq);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(getSupplierByCodeResp.toString());
    }

    static void getByProductCode(){
        GongxiaoRpc.RpcHeader rpcHeader = GongxiaoRpc.RpcHeader.newBuilder().setUid("1").setTraceId("11111").build();
        ProductStructure.GetByProductModelReq getByProductModelReq = ProductStructure.GetByProductModelReq.newBuilder()
                .setRpcHeader(rpcHeader)
                .setProductModel("111")
                .build();
        ProductStructure.GetByProductModelResp getByProductCodeResp=null;
        try {
            ProductServiceGrpc.ProductServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
            getByProductCodeResp = rpcStub.getByProductModel(getByProductModelReq);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(getByProductCodeResp.toString());
    }


    //ProjectService
    static void getProjectById(String projectId) {

        //1. 构建GetProjectByIdReq
        ProjectStructure.GetByProjectIdReq req = ProjectStructure.GetByProjectIdReq.newBuilder()
                .setProjectId(projectId)
                .build();

        //2. 获取该gRpc对应的stub
        ProjectServiceGrpc.ProjectServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
        ProjectStructure.GetByProjectIdResp resp;
        try {
            //3. 进行gRpc调用
            resp = blockingStub.getByProjectId(req);
            System.out.println("[RPC][response]: " + resp.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void generateFrontFlowSubtotal(
            Integer moneyFlow,
            String currencyCode,
            Long supplierId,
            Long projectId,
            Long beginDateLong,
            Long endDateLong) {
        SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalReq.Builder reqBuilder
                = SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalReq.newBuilder();


        reqBuilder.setMoneyFlow(moneyFlow);
        reqBuilder.setCurrencyCode(currencyCode);
        reqBuilder.setSupplierId(supplierId);
        reqBuilder.setProjectId(projectId);
        reqBuilder.setBeginDate(beginDateLong);
        reqBuilder.setEndDate(endDateLong);

        SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalReq req = reqBuilder.build();
        //2. 获取该gRpc对应的stub
        // ProjectServiceGrpc.ProjectServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
        SupplierCouponPostingServiceGrpc.SupplierCouponPostingServiceBlockingStub blockingStub
                = RpcStubStore.getRpcStub(SupplierCouponPostingServiceGrpc.SupplierCouponPostingServiceBlockingStub.class);

        // ProjectServiceStructure.GetProjectByIdResp resp;
        SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalResp resp;
        try {
            //3. 进行gRpc调用
            resp = blockingStub.generateFrontFlowSubtotal(req);
            System.out.println("[RPC][response]: " + resp.toString());
            System.out.println("调用成功了");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    static void getProductInfo(){

        ProductStructure.GetByProductIdReq productIdReq = ProductStructure.GetByProductIdReq.newBuilder()
                .setProductId(108)
                .setRpcHeader(GongxiaoRpc.RpcHeader.newBuilder()
                        .setTraceId("1111")
                        .setUid("123")
                        .setUsername("ch")).build();

        ProductServiceGrpc.ProductServiceBlockingStub rpcStub
                = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);

        ProductStructure.GetByProductIdResp productIdResp = rpcStub.getByProductId(productIdReq);
        String productCode = productIdResp.getProductBusiness().getCustomerProductCode();
        System.out.println(productCode);
    }

}
