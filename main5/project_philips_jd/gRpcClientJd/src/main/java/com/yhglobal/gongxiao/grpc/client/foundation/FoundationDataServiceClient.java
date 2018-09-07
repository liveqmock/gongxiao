package com.yhglobal.gongxiao.grpc.client.foundation;

import java.util.Date;

/**
 * 调试时调用FoundationData服务
 */
public class FoundationDataServiceClient {

    //ProjectService
    static void getProjectById(int projectId) {
        //1. 构建GetProjectByIdReq
//        ProjectServiceStructure.GetProjectByIdReq req = ProjectServiceStructure.GetProjectByIdReq.newBuilder()
//                .setProjectId(projectId)
//                .build();
//
//        //2. 获取该gRpc对应的stub
//        ProjectServiceGrpc.ProjectServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
//        ProjectServiceStructure.GetProjectByIdResp resp;
//        try {
//            //3. 进行gRpc调用
//            resp = blockingStub.getProjectById(req);
//            System.out.println("[RPC][response]: " + resp.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //测试付款单生成接口
//        YhglobalToPayMoneyServiceStructure.ToPayMoneyApply.Builder yhglobalToPayMoney = YhglobalToPayMoneyServiceStructure.ToPayMoneyApply.newBuilder();
//        yhglobalToPayMoney.setPaymentId(22l);
//        yhglobalToPayMoney.setConfirmStatus("1");
//        yhglobalToPayMoney.setStatus("2");
//        yhglobalToPayMoney.setPaymentApplyNo("444");
//        yhglobalToPayMoney.setCreateTime(new Date().getTime());
//        yhglobalToPayMoney.setEstimatedPaymentTime(new Date().getTime());
//        yhglobalToPayMoney.setSupplierId(22l);
//        yhglobalToPayMoney.setSupplierName("qwq");
//        yhglobalToPayMoney.setSettlementType("2");
//        yhglobalToPayMoney.setCreditPaymentDays(23);
//        yhglobalToPayMoney.setPaymentType("1");
//        yhglobalToPayMoney.setBankAcceptancePeriod(3);
//        yhglobalToPayMoney.setPurchasePlanNo("4asq2");
//        yhglobalToPayMoney.setOrderTime(new Date().getTime());
//        yhglobalToPayMoney.setCurrencyCode("CNY");
//        yhglobalToPayMoney.setToPayAmount(3333.0);
//        yhglobalToPayMoney.setToBePayAmount(3333.0);
//        yhglobalToPayMoney.setConfirmAmount(0.0);
//        yhglobalToPayMoney.setReceiptAmount(0.0);
//        yhglobalToPayMoney.setReceiverName("aaa");
//        yhglobalToPayMoney.setProjectId(334l);
//        yhglobalToPayMoney.setProjectName("rere");
//        yhglobalToPayMoney.setDataVersion(1l);
//        yhglobalToPayMoney.setPlanOrderAmount(333333.0);
//
//        YhglobalToPayMoneyServiceStructure.InsertNewReq.Builder req = YhglobalToPayMoneyServiceStructure.InsertNewReq.newBuilder();
//        req.setToPayMoneyApply(yhglobalToPayMoney);
//        YhglobalToPayMoneyServiceGrpc.YhglobalToPayMoneyServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(YhglobalToPayMoneyServiceGrpc.YhglobalToPayMoneyServiceBlockingStub.class);
//        YhglobalToPayMoneyServiceStructure.InsertNewResp resp = blockingStub.insertNew(req.build());
//        System.out.println(resp.getReturnCode()+"----"+resp.getMsg());

    }

    public static void main(String[] args) {
        getProjectById(1);
        //getProjectById(146798161);

    }

}
