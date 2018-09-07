package com.yhglobal.gongxiao.grpc.client.foundation;

import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorServiceGrpc;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemServiceGrpc;
import com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure;
import com.yhglobal.gongxiao.util.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public class PlanSaleClient {
    static  GongxiaoRpc.RpcHeader rpcHeader=null;
    static {
        rpcHeader = GongxiaoRpc.RpcHeader.newBuilder().setUid("1").setTraceId("11111").build();
    }

    public static void main(String[] args) {
        getDistributorInfo();
    }

    public static void selectSalePlanItemList() {
        searchProductByCustomer();
    }

    public static void getDistributorInfo(){
        DistributorServiceGrpc.DistributorServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(DistributorServiceGrpc.DistributorServiceBlockingStub.class);
        DistributorStructure.GetDistributorBusinessByIdReq getDistributorBusinessByIdReq = DistributorStructure.GetDistributorBusinessByIdReq.newBuilder()
                .setRpcHeader(rpcHeader)
                .setDistributorBusinessId(1)
                .build();
        DistributorStructure.GetDistributorBusinessByIdResp resp = rpcStub.getDistributorBusinessById(getDistributorBusinessByIdReq);
        DistributorStructure.DistributorBusiness distributorBusiness = resp.getDistributorBusiness();
        System.out.println(distributorBusiness.toString());
    }

    public static void getCustomerPlanInfo(){
        PlanSaleItemServiceGrpc.PlanSaleItemServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(PlanSaleItemServiceGrpc.PlanSaleItemServiceBlockingStub.class);
        PlanSaleItemStructure.GetCustomerPlanInfoReq getCustomerPlanInfoReq = PlanSaleItemStructure.GetCustomerPlanInfoReq.newBuilder()
                .setProductId("1")
                .setProjectId("146798161")
                .setBusinessDate(DateUtil.getTime(new Date()))
                .setDistributorId("1")
                .build();
        PlanSaleItemStructure.GetCustomerPlanInfoResp customerPlanInfo = rpcStub.getCustomerPlanInfo(getCustomerPlanInfoReq);
        System.out.println(customerPlanInfo.toString());
    }

    public static void getDistributorUser(){
        DistributorServiceGrpc.DistributorServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(DistributorServiceGrpc.DistributorServiceBlockingStub.class);
        DistributorStructure.GetDistributorAccountByIdReq getDistributorAccountByIdReq = DistributorStructure.GetDistributorAccountByIdReq.newBuilder()
                .setRpcHeader(rpcHeader)
                .setDistributorUserId(1)
                .build();
        DistributorStructure.GetDistributorAccountByIdResp getDistributorAccountByIdResp = rpcStub.getDistributorAccountById(getDistributorAccountByIdReq);
        DistributorStructure.DistributorUser distributorUser = getDistributorAccountByIdResp.getDistributorUser();
        System.out.println(distributorUser.toString());
    }

    public static void searchProductByCustomer(){
        PlanSaleItemServiceGrpc.PlanSaleItemServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(PlanSaleItemServiceGrpc.PlanSaleItemServiceBlockingStub.class);
        PlanSaleItemStructure.SearchProductByCustomerReq searchProductByCustomerReq = PlanSaleItemStructure.SearchProductByCustomerReq.newBuilder()
                .setRpcHeader(rpcHeader)
                .setQueryStr("")
                .build();
        PlanSaleItemStructure.SearchProductByCustomerResp searchProductByCustomerResp = rpcStub.searchProductByCustomer(searchProductByCustomerReq);
        List<PlanSaleItemStructure.SalesPlanItem> salesPlanItemListList = searchProductByCustomerResp.getSalesPlanItemListList();
        for (PlanSaleItemStructure.SalesPlanItem salesPlanItem:salesPlanItemListList){
            System.out.println(salesPlanItem.toString());
        }
    }

}
