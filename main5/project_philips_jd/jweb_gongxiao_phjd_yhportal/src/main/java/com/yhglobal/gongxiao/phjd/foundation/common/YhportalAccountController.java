///*
//package com.yhglobal.gongxiao.phjd.foundation.common;
//
//import com.yhglobal.gongxiao.common.GongxiaoResult;
//import com.yhglobal.gongxiao.constant.ErrorCode;
//import com.yhglobal.gongxiao.constant.TerminalCode;
//import com.yhglobal.gongxiao.foundation.area.microservice.AreaServiceGrpc;
//import com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure;
//import com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserServiceGrpc;
//import com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserStructure;
//import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
//import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
//import com.yhglobal.gongxiao.phjd.base.model.PortalUserInfo;
//import com.yhglobal.gongxiao.phjd.base.protal.PortalConfig;
//import com.yhglobal.gongxiao.phjd.bean.AreaBean;
//import com.yhglobal.gongxiao.phjd.purchase.SupplierCouponPostingController;
//import com.yhglobal.gongxiao.phjd.utils.YhPortalTraceIdGenerator;
//import com.yhglobal.gongxiao.status.FoundationNormalStatus;
//import com.yhglobal.gongxiao.util.RpcHeaderUtil;
//import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.ArrayList;
//import java.util.List;
//
//*/
///**
// * 获取员工有哪些项目
// *//*
//
//@Controller
//@RequestMapping("/account/project")
//public class YhportalAccountController {
//    private static Logger logger = LoggerFactory.getLogger(SupplierCouponPostingController.class);
//
//    @Autowired
//    PortalConfig portalConfig; //property注入类
//
//    @Autowired
//    PortalUserInfo portalUserInfo; //用户信息注入类
//
//    @RequestMapping("/selectProjectList")
//    @ResponseBody
//    public GongxiaoResult selectProjectList(HttpServletRequest request, HttpServletResponse response) {
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        String traceId = null;
//        GongxiaoResult gongxiaoResult = null;
//        try {
//            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
//            logger.info("#traceId={}# [IN][selectProjectList]", traceId);
//            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
//            YhportalUserStructure.GetUserListByAccountReq req = YhportalUserStructure.GetUserListByAccountReq.newBuilder().setRpcHeader(rpcHeader).setUserName(portalUserInfo.getUserName()).build();
//            YhportalUserServiceGrpc.YhportalUserServiceBlockingStub stub = RpcStubStore.getRpcStub(YhportalUserServiceGrpc.YhportalUserServiceBlockingStub.class);
//            YhportalUserStructure.GetUserListByAccountResp resp = stub.getUserListByAccount(req);
//
//
//            List<AreaBean> areaBeanList = new ArrayList<>();
////            for (AreaStructure.Province list : resp.getProvinceList()) {
////                AreaBean areaBean = new AreaBean();
////                areaBean.setProvinceId(list.getProvinceId());
////                areaBean.setProvinceCode(list.getProvinceCode());
////                areaBean.setProvinceName(list.getProvinceName());
////                areaBeanList.add(areaBean);
////            }
//            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), areaBeanList);
//            logger.info("#traceId={}# [selectProjectList][OUT]");
//        } catch (Exception e) {
//            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
//            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
//        }
//        return gongxiaoResult;
//    }
//
//}
//*/
