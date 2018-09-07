package com.yhglobal.gongxiao.phjd.foundation.basics;

import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorServiceGrpc;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.phjd.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.phjd.base.protal.PortalConfig;
import com.yhglobal.gongxiao.phjd.bean.CustomerBean;
import com.yhglobal.gongxiao.phjd.utils.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户信息
 * @author yuping.lin
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {
    private static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    PortalConfig portalConfig; //property注入类

    @Autowired
    PortalUserInfo portalUserInfo; //用户信息注入类

    /**
     * 1:根据条件获取客户列表
     */
    @RequestMapping("/selectUserByCondition")
    @ResponseBody
    public GongxiaoResult selectUserByCondition(HttpServletRequest request, HttpServletResponse response,
                                                @RequestParam(defaultValue = "0") String easCode,
                                                @RequestParam(defaultValue = "0") String distributorName,
                                                    @RequestParam(defaultValue = "0") Integer status,
                                                @RequestParam(defaultValue = "0") String pageSize,
                                                @RequestParam(defaultValue = "0") Integer pageNumber) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            DistributorStructure.SelectUserByConditionReq req = DistributorStructure.SelectUserByConditionReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setEasCode(easCode)
                    .setDistributorName(distributorName)
                    .setStatus(status)
                    .setPageSize(pageNumber)
                    .setPageNumber(pageNumber)
                    .build();
            DistributorServiceGrpc.DistributorServiceBlockingStub stub = RpcStubStore.getRpcStub(DistributorServiceGrpc.DistributorServiceBlockingStub.class);
            DistributorStructure.SelectUserByConditionResp resp = stub.selectUserByCondition(req);
            List<CustomerBean> customerBeanList = new ArrayList<>();
            for (DistributorStructure.DistributorUser list : resp.getDistributorUserList()) {
                CustomerBean customerBean = new CustomerBean();
                customerBean.setEasDistributorCode(list.getEasDistributorCode());
                customerBean.setEasDistributorName(list.getEasDistributorName());
                customerBean.setAccount(list.getAccount());
                customerBean.setPassword(list.getPassword());
                customerBean.setCreatedById(list.getCreatedById());
                customerBean.setCreatedByName(list.getCreatedByName());
                customerBean.setEasStatus(list.getEasStatus());
                customerBeanList.add(customerBean);
            }
            Map map = new HashMap();
            map.put("totle", resp.getTotal());
            map.put("customerBeanList", customerBeanList);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), map);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 2.客户新增
     */
    @RequestMapping("/newCustomer")
    @ResponseBody
    public GongxiaoResult addCustomer(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam(defaultValue = "") String EASCoding,
                                      @RequestParam(defaultValue = "") String customerName,
                                      @RequestParam(defaultValue = "") String userName,
                                      @RequestParam(defaultValue = "") String psd,
                                      @RequestParam(defaultValue = "0") int settlementMethod,
                                      @RequestParam(defaultValue = "") String customerCode,
                                      @RequestParam(defaultValue = "0") int accountPeriod,
                                      @RequestParam(defaultValue = "") String mailbox,
                                      @RequestParam(defaultValue = "") String dutyParagraph,
                                      @RequestParam(defaultValue = "") String contactInformation,
                                      @RequestParam(defaultValue = "") String businessAddress,
//                                      collectGoods
                                      @RequestParam(defaultValue = "") String collectGoodsConsignee,
                                      @RequestParam(defaultValue = "") String collectGoodsAddress,
                                      @RequestParam(defaultValue = "") String collectGoodsDetailedAddress,
                                      @RequestParam(defaultValue = "") String collectGoodsPhone,
                                      @RequestParam(defaultValue = "") String collectGoodsZipCode,
//                                      collection
                                      @RequestParam(defaultValue = "") String collectionConsignee,
                                      @RequestParam(defaultValue = "") String collectionAddress,
                                      @RequestParam(defaultValue = "") String collectionDetailedAddress,
                                      @RequestParam(defaultValue = "") String collectionPhone,
                                      @RequestParam(defaultValue = "") String collectionZipCode) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            DistributorStructure.InsertDistributorInfoReq.Builder builder = DistributorStructure.InsertDistributorInfoReq.newBuilder();
            DistributorStructure.DistributorDetail.Builder disd = DistributorStructure.DistributorDetail.newBuilder();
            disd.setEasDistributorCode(EASCoding);
            disd.setEasDistributorName(customerName);
            disd.setAccount(userName);
            disd.setPassword(psd);
            disd.setSettlementType(settlementMethod);
            disd.setDistributorCode(customerCode);
            disd.setAccountPeriod(accountPeriod);
            disd.setEmail(mailbox);
            disd.setTaxNumber(dutyParagraph);
            disd.setContactNumber(contactInformation);
            disd.setBusinessAddress(businessAddress);
            builder.setRpcHeader(rpcHeader);
            builder.setDestributorDetail(disd);
            DistributorStructure.InsertDistributorInfoReq req = builder.build();
            DistributorServiceGrpc.DistributorServiceBlockingStub stub = RpcStubStore.getRpcStub(DistributorServiceGrpc.DistributorServiceBlockingStub.class);
            DistributorStructure.InsertDistributorInfoResp resp = stub.insertDistributorInfo(req);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), resp);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 3.客户编辑
     */
    @RequestMapping("/editDistributor")
    @ResponseBody
    public GongxiaoResult editDistributor(HttpServletRequest request, HttpServletResponse response,
                                          @RequestParam(defaultValue = "") String EASCoding,
                                          @RequestParam(defaultValue = "") String customerName,
                                          @RequestParam(defaultValue = "") String userName,
                                          @RequestParam(defaultValue = "") String psd,
                                          @RequestParam(defaultValue = "0") int settlementMethod,
                                          @RequestParam(defaultValue = "") String customerCode,
                                          @RequestParam(defaultValue = "0") int accountPeriod,
                                          @RequestParam(defaultValue = "") String mailbox,
                                          @RequestParam(defaultValue = "") String dutyParagraph,
                                          @RequestParam(defaultValue = "") String contactInformation,
                                          @RequestParam(defaultValue = "") String businessAddress,
//                                      collectGoods
                                          @RequestParam(defaultValue = "") String collectGoodsConsignee,
                                          @RequestParam(defaultValue = "") String collectGoodsAddress,
                                          @RequestParam(defaultValue = "") String collectGoodsDetailedAddress,
                                          @RequestParam(defaultValue = "") String collectGoodsPhone,
                                          @RequestParam(defaultValue = "") String collectGoodsZipCode,
//                                      collection
                                          @RequestParam(defaultValue = "") String collectionConsignee,
                                          @RequestParam(defaultValue = "") String collectionAddress,
                                          @RequestParam(defaultValue = "") String collectionDetailedAddress,
                                          @RequestParam(defaultValue = "") String collectionPhone,
                                          @RequestParam(defaultValue = "") String collectionZipCode) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            DistributorStructure.EditDistributorReq.Builder builder = DistributorStructure.EditDistributorReq.newBuilder();
            DistributorStructure.DistributorDetail.Builder disd = DistributorStructure.DistributorDetail.newBuilder();
            disd.setEasDistributorCode(EASCoding);
            disd.setEasDistributorName(customerName);
            disd.setAccount(userName);
            disd.setPassword(psd);
            disd.setSettlementType(settlementMethod);
            disd.setDistributorCode(customerCode);
            disd.setAccountPeriod(accountPeriod);
            disd.setEmail(mailbox);
            disd.setTaxNumber(dutyParagraph);
            disd.setContactNumber(contactInformation);
            disd.setBusinessAddress(businessAddress);
            builder.setRpcHeader(rpcHeader);
            builder.setDistributorDetail(disd);
            DistributorStructure.EditDistributorReq req = builder.build();
            DistributorServiceGrpc.DistributorServiceBlockingStub stub = RpcStubStore.getRpcStub(DistributorServiceGrpc.DistributorServiceBlockingStub.class);
            DistributorStructure.EditDistributorResp resp = stub.editDistributor(req);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), resp);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 4.客户详情
     */
    @RequestMapping("/getDistributorDetail")
    @ResponseBody
    public GongxiaoResult getDistributorDetail(HttpServletRequest request, HttpServletResponse response,
                                               @RequestParam(defaultValue = "") String distributorBusinessId) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            DistributorStructure.GetDistributorDetailReq req = DistributorStructure.GetDistributorDetailReq.newBuilder()
                    .setDistributorBusinessId(distributorBusinessId)
                    .setRpcHeader(rpcHeader)
                    .build();
            DistributorServiceGrpc.DistributorServiceBlockingStub stub = RpcStubStore.getRpcStub(DistributorServiceGrpc.DistributorServiceBlockingStub.class);
            DistributorStructure.GetDistributorDetailResp resp = stub.getDistributorDetail(req);
            CustomerBean cb = new CustomerBean();
            cb.setEasDistributorCode(resp.getDistributorDetail().getEasDistributorCode());
            cb.setEasDistributorName(resp.getDistributorDetail().getEasDistributorName());
            cb.setAccount(resp.getDistributorDetail().getAccount());
            cb.setPassword(resp.getDistributorDetail().getPassword());
            cb.setSettlementMethod(resp.getDistributorDetail().getSettlementType());
            cb.setCustomerCode(resp.getDistributorDetail().getDistributorCode());
            cb.setDutyParagraph(resp.getDistributorDetail().getTaxNumber());
            cb.setMailbox(resp.getDistributorDetail().getEmail());
            cb.setContactInformation(resp.getDistributorDetail().getContactNumber());
            cb.setBusinessAddress(resp.getDistributorDetail().getBusinessAddress());
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), cb);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 5.收货地址
     */
    @RequestMapping("/selectAllAddressByDistributorId")
    @ResponseBody
    public GongxiaoResult selectAllAddressByDistributorId(HttpServletRequest request, HttpServletResponse response,
                                                      @RequestParam(defaultValue = "0") int distributorBusinessId,
                                                      @RequestParam(defaultValue = "") String account,
                                                      @RequestParam(defaultValue = "") String pwd) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            DistributorStructure.UpdateDistributorAccountPwdReq req = DistributorStructure.UpdateDistributorAccountPwdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setDistributorUserId(distributorBusinessId)
                    .setAccount(account)
                    .setPwd(pwd)
                    .build();
            DistributorServiceGrpc.DistributorServiceBlockingStub stub = RpcStubStore.getRpcStub(DistributorServiceGrpc.DistributorServiceBlockingStub.class);
            DistributorStructure.UpdateDistributorAccountPwdResp resp = stub.updateDistributorAccountPwd(req);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), resp);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 6.收件地址
     */

    /**
     * 修改密码
     */
    @RequestMapping("/updateDistributorAccountPwd")
    @ResponseBody
    public GongxiaoResult updateDistributorAccountPwd(HttpServletRequest request, HttpServletResponse response,
                                                      @RequestParam(defaultValue = "0") int distributorBusinessId,
                                                      @RequestParam(defaultValue = "") String account,
                                                      @RequestParam(defaultValue = "") String pwd) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            DistributorStructure.UpdateDistributorAccountPwdReq req = DistributorStructure.UpdateDistributorAccountPwdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setDistributorUserId(distributorBusinessId)
                    .setAccount(account)
                    .setPwd(pwd)
                    .build();
            DistributorServiceGrpc.DistributorServiceBlockingStub stub = RpcStubStore.getRpcStub(DistributorServiceGrpc.DistributorServiceBlockingStub.class);
            DistributorStructure.UpdateDistributorAccountPwdResp resp = stub.updateDistributorAccountPwd(req);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), resp);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    //TODO 没有接口
    /**
     * 6.收件地址
     * 客户审核(运维)
     * 客户地址审批
     */
}
