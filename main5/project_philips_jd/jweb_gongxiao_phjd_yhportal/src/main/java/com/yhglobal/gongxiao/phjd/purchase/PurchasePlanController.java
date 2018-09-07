//package com.yhglobal.gongxiao.phjd.purchase;
//
//import com.github.pagehelper.PageInfo;
//
//import com.yhglobal.gongxiao.common.WebResult;
//import com.yhglobal.gongxiao.constant.TerminalCode;
//
//import com.yhglobal.gongxiao.constants.purchase.PurchasePlanStatus;
//import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
//import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
//import com.yhglobal.gongxiao.phjd.base.model.PortalUserInfo;
//import com.yhglobal.gongxiao.phjd.base.protal.PortalConfig;
//import com.yhglobal.gongxiao.phjd.bean.PurchasePlanBean;
//import com.yhglobal.gongxiao.phjd.purchase.microservice.PurchasePlanServiceGrpc;
//import com.yhglobal.gongxiao.phjd.purchase.microservice.PurchasePlanServiceStructure;
//import com.yhglobal.gongxiao.util.RpcHeaderUtil;
//import com.yhglobal.gongxiao.phjd.utils.YhPortalTraceIdGenerator;
//import com.yhglobal.gongxiao.utils.DateUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Description:
// * @author: LUOYI
// * @Date: Created in 18:09 2018/6/27
// */
//@Controller
//@RequestMapping("/purchase/plan")
//public class PurchasePlanController {
//
//    private static Logger logger = LoggerFactory.getLogger(PurchasePlanController.class);
//
//    @Autowired
//    PortalConfig portalConfig; //property注入类
//
//    @Autowired
//    PortalUserInfo portalUserInfo; //用户信息注入类
//
//    @RequestMapping("/getsPurchasePlanStatus")
//    @ResponseBody
//    public WebResult<List<Map<String,String>>> getsPurchasePlanStatus(HttpServletRequest request, HttpServletResponse response){
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        return WebResult.newSuccessResult(PurchasePlanStatus.getsPurchasePlanStatus());
//    }
//
//
//    @RequestMapping("/gets")
//    @ResponseBody
//    public WebResult<PageInfo<PurchasePlanBean>> gets(HttpServletRequest request, HttpServletResponse response,
//                                                      Integer projectId,
//                                                      @RequestParam(defaultValue="") String purchasePlanNo,
//                                                      @RequestParam(defaultValue="") String supplierName,
//                                                      @RequestParam(defaultValue="") String productInfo,
//                                                      @RequestParam(defaultValue="0") Integer status,
//                                                      @RequestParam(defaultValue="") String dateStart,
//                                                      @RequestParam(defaultValue="") String dateEnd,
//                                                      @RequestParam(required=true,defaultValue="1") Integer pageNumber,
//                                                      @RequestParam(required=false,defaultValue="60") Integer pageSize){
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        String traceId = null;
//        PageInfo<PurchasePlanBean> pageInfo = null;
//        try{
//            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
//            logger.info("#traceId={}# [IN][getsByProjectId] params: projectId={}, purchasePlanNo={}, supplierName={}, productInfo={},status={}, dateStart={},  dateEnd={}, pageNumber={}, pageSize={}"
//                    , traceId,projectId,purchasePlanNo,supplierName,productInfo,status,dateStart,dateEnd,pageNumber,pageSize);
//            portalUserInfo.setUserId("1");
//            portalUserInfo.setUserName("gecan");
//            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
//
//            PurchasePlanServiceStructure.SearchPurchasePlanReq.Builder reqBuilder = PurchasePlanServiceStructure.SearchPurchasePlanReq.newBuilder();
//            //请求参数封装
//            reqBuilder.setProjectId(projectId);
//            reqBuilder.setPurchasePlanNo(purchasePlanNo);
//            reqBuilder.setSupplierName(supplierName);
//            reqBuilder.setProductInfo(productInfo);
//            reqBuilder.setStatus(status);
//            reqBuilder.setDateStart(dateStart);
//            reqBuilder.setDateEnd(dateEnd);
//            reqBuilder.setPageNumber(pageNumber);
//            reqBuilder.setPageSize(pageSize);
//            PurchasePlanServiceStructure.SearchPurchasePlanReq searchPurchasePlanReq = reqBuilder.build();
//
//            PurchasePlanServiceGrpc.PurchasePlanServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(PurchasePlanServiceGrpc.PurchasePlanServiceBlockingStub.class);
//            PurchasePlanServiceStructure.PurchasePlanPageInfoResp  purchasePlanPageInfoResp;
//            purchasePlanPageInfoResp = blockingStub.getsPurchasePlan(searchPurchasePlanReq);
//            if(purchasePlanPageInfoResp.getReturnCode() != WebResult.RETURN_SUCCESS){
//                return WebResult.newFailureResult(purchasePlanPageInfoResp.getReturnCode(),purchasePlanPageInfoResp.getMsg());
//            }
//            pageInfo = purchasePlanInfoRespToJava(purchasePlanPageInfoResp);
//            logger.info("#traceId={}# [OUT] get getsByProjectId success:pageInfo={}", traceId,pageInfo);
//        }catch(Exception e) {
//            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
//            return WebResult.newFailureResult();
//        }
//        return  WebResult.newSuccessResult(pageInfo);
//    }
//    private PageInfo<PurchasePlanBean> purchasePlanInfoRespToJava(PurchasePlanServiceStructure.PurchasePlanPageInfoResp purchasePlanPageInfoResp){
//        PageInfo<PurchasePlanBean> pageInfo = new PageInfo();
//        pageInfo.setPageNum(purchasePlanPageInfoResp.getPageNum());
//        pageInfo.setPageSize(purchasePlanPageInfoResp.getPageSize());
//        pageInfo.setSize(purchasePlanPageInfoResp.getSize());
//        pageInfo.setSize(purchasePlanPageInfoResp.getSize());
//        pageInfo.setEndRow(purchasePlanPageInfoResp.getEndRow());
//        pageInfo.setTotal(purchasePlanPageInfoResp.getTotal());
//        pageInfo.setPages(purchasePlanPageInfoResp.getPages());
//        List<PurchasePlanBean> purchasePlanBeanList = new ArrayList<>();
//        //组装List
//        for (PurchasePlanServiceStructure.PurchasePlan purchasePlan : purchasePlanPageInfoResp.getListList()) {
//            PurchasePlanBean purchasePlanBean = new PurchasePlanBean();
//            purchasePlanBean.setPurchasePlanId(purchasePlan.getPurchasePlanId());
//            purchasePlanBean.setPurchasePlanNo(purchasePlan.getPurchasePlanNo());
//            purchasePlanBean.setSupplierId(purchasePlan.getSupplierId());
//            purchasePlanBean.setSupplierName(purchasePlan.getSupplierName());
//            purchasePlanBean.setExpectedPayAmount(purchasePlan.getExpectedPayAmount());
//            purchasePlanBean.setApplyPayAmount(purchasePlan.getApplyPayAmount());
//            purchasePlanBean.setRealPayAmount(purchasePlan.getRealPayAmount());
//            purchasePlanBean.setOrderAmount(purchasePlan.getOrderAmount());
//            purchasePlanBean.setOrderCashAmount(purchasePlan.getOrderCashAmount());
//            purchasePlanBean.setOrderPurchaseReserveAmount(purchasePlan.getOrderPurchaseReserveAmount());
//            purchasePlanBean.setOrderCouponAmount(purchasePlan.getOrderCouponAmount());
//            purchasePlanBean.setOrderPrepaidAmount(purchasePlan.getOrderPrepaidAmount());
//            purchasePlanBean.setCashSurplusAmount(purchasePlan.getCashSurplusAmount());
//            purchasePlanBean.setStatus(purchasePlan.getStatus());
//            purchasePlanBean.setCreateTime(DateUtil.stringToDate(purchasePlan.getCreateTime(),DateUtil.DATE_TIME_FORMAT));
//            purchasePlanBean.setCreatedByName(purchasePlan.getCreatedByName());
//            purchasePlanBeanList.add(purchasePlanBean);
//        }
//        pageInfo.setList(purchasePlanBeanList);
//        return pageInfo;
//    }
//}
