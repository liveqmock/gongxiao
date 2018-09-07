package com.yhglobal.gongxiao.phjd.foundation.basics;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressServiceGrpc;
import com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.phjd.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.phjd.base.protal.PortalConfig;
import com.yhglobal.gongxiao.phjd.bean.DistributorShippingAddressBean;
import com.yhglobal.gongxiao.phjd.utils.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.status.FoundationNormalStatus;
import com.yhglobal.gongxiao.util.DateUtil;
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
import java.util.List;

/**
 * 客户地址审批
 *
 * @author yuping.lin
 */
@Controller
@RequestMapping("customer/address")
public class AddressApprovalController {
    private static Logger logger = LoggerFactory.getLogger(AddressApprovalController.class);

    @Autowired
    PortalConfig portalConfig; //property注入类

    @Autowired
    PortalUserInfo portalUserInfo; //用户信息注入类

    /**
     * 条件查询客户地址审批列表
     *
     * @param request
     * @param response
     * @param distributorAccount 客户帐号
     * @param distributorName    客户名称
     * @param auditStatus        状态审核(1初始化 2 已提交,3 已审核,4 已驳回 9已废弃')
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectCustomerAddress")
    public GongxiaoResult selectCustomerAddress(HttpServletRequest request, HttpServletResponse response,
                                                @RequestParam(defaultValue = "") String distributorAccount,
                                                @RequestParam(defaultValue = "") String distributorName,
                                                @RequestParam(defaultValue = "3") int auditStatus,
                                                @RequestParam(defaultValue = "1") int pageNumber,
                                                @RequestParam(defaultValue = "60") int pageSize) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        int projectId = 0;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectProductInventoryInfo] params: distributorAccount = {}, distributorName={}, auditStatus = {},pageNumber = {}, pageSize = {}",
                    distributorAccount, distributorName, auditStatus, pageNumber, pageSize);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ShippingAddressStructure.SelectAddressListByStatusReq req = ShippingAddressStructure.SelectAddressListByStatusReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setDistributorName(distributorName)
                    .setRecordStatus(auditStatus)
                    .setPageNumber(pageNumber)
                    .setPageSize(pageSize)
                    .build();
            ShippingAddressServiceGrpc.ShippingAddressServiceBlockingStub stub = RpcStubStore.getRpcStub(ShippingAddressServiceGrpc.ShippingAddressServiceBlockingStub.class);
            ShippingAddressStructure.SelectAddressListByStatusResp resp = stub.selectAddressListByStatus(req);
            ShippingAddressStructure.ShippingAddressPage shippingAddressPage = resp.getShippingAddressPage();
            PageInfo<DistributorShippingAddressBean> distributorShippingAddressPageInfo = new PageInfo<>();
            distributorShippingAddressPageInfo.setTotal(shippingAddressPage.getTotal());
            List<DistributorShippingAddressBean> distributorShippingAddressList = new ArrayList<>();
            for (ShippingAddressStructure.DistributorShippingAddress distributorShippingAddress : shippingAddressPage.getDistributorShippingAddressList()) {
                DistributorShippingAddressBean distributorShippingAddress1 = new DistributorShippingAddressBean();
                distributorShippingAddress1.setAddressId(distributorShippingAddress.getAddressId());
                distributorShippingAddress1.setDistrictId(distributorShippingAddress.getDistrictId());
                distributorShippingAddress1.setDistrictName(distributorShippingAddress.getDistrictName());
                distributorShippingAddress1.setReceiver(distributorShippingAddress.getReceiver());
                distributorShippingAddress1.setProvinceId(distributorShippingAddress.getProvinceId());
                distributorShippingAddress1.setProvinceName(distributorShippingAddress.getProvinceName());
                distributorShippingAddress1.setDistributorId(distributorShippingAddress.getDistributorId());
                distributorShippingAddress1.setDistributorName(distributorShippingAddress.getDistributorName());
                distributorShippingAddress1.setCityId(distributorShippingAddress.getCityId());
                distributorShippingAddress1.setCityName(distributorShippingAddress.getCityName());
                distributorShippingAddress1.setStreetAddress(distributorShippingAddress.getStreetAddress());
                distributorShippingAddress1.setPhoneNumber(distributorShippingAddress.getPhoneNumber());
                distributorShippingAddress1.setPostCode(distributorShippingAddress.getPostCode());
                int auditStatusResp = distributorShippingAddress.getAuditStatus();
                distributorShippingAddress1.setAuditStatus(FoundationNormalStatus.getStatusByCode((byte) auditStatusResp).getMessage());
                distributorShippingAddress1.setIsDefaultAddress((byte) distributorShippingAddress.getIsDefaultAddress());
                distributorShippingAddress1.setCreateTime(DateUtil.long2Date(distributorShippingAddress.getCreateTime()));
                distributorShippingAddress1.setLastUpdateTime(DateUtil.long2Date(distributorShippingAddress.getCreateTime()));
                distributorShippingAddress1.setTracelog(distributorShippingAddress.getTracelog());
                distributorShippingAddressList.add(distributorShippingAddress1);
            }
            distributorShippingAddressPageInfo.setList(distributorShippingAddressList);
            logger.info("#traceId={}# [selectCustomerAddress][OUT]");
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), distributorShippingAddressPageInfo);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 审批通过(只对审批状态为1初始化)
     *
     * @param request
     * @param response
     * @param addressId 经销商用户Id
     * @return
     */
    @ResponseBody
    @RequestMapping("/auditAddress")
    public GongxiaoResult auditAddress(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(defaultValue = "0") int addressId) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        int projectId = 0;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][auditAddress] params: addressId = {}", addressId);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ShippingAddressStructure.AuditAddressReq auditAddressReq = ShippingAddressStructure.AuditAddressReq.newBuilder()
                    .setDistributorAddressId(addressId)
                    .setRpcHeader(rpcHeader)
                    .setAuditStatus(1)
                    .build();
            ShippingAddressServiceGrpc.ShippingAddressServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(ShippingAddressServiceGrpc.ShippingAddressServiceBlockingStub.class);
            ShippingAddressStructure.AuditAddressResp resp = rpcStub.auditAddress(auditAddressReq);
            ;
            logger.info("#traceId={}# [auditAddress][OUT]");
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), resp.getNumber());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 审批驳回(只对审批状态为2 已提交)
     *
     * @param request
     * @param response
     * @param addressId 经销商用户Id
     * @return
     */
    @ResponseBody
    @RequestMapping("/rejectAddress")
    public GongxiaoResult rejectAddress(HttpServletRequest request, HttpServletResponse response,
                                        @RequestParam(defaultValue = "0") int addressId) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        int projectId = 0;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][rejectAddress] params: addressId = {}", addressId);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ShippingAddressStructure.AuditAddressReq auditAddressReq = ShippingAddressStructure.AuditAddressReq.newBuilder()
                    .setDistributorAddressId(addressId)
                    .setRpcHeader(rpcHeader)
                    .setAuditStatus(2)
                    .build();
            ShippingAddressServiceGrpc.ShippingAddressServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(ShippingAddressServiceGrpc.ShippingAddressServiceBlockingStub.class);
            ShippingAddressStructure.AuditAddressResp resp = rpcStub.auditAddress(auditAddressReq);
            logger.info("#traceId={}# [rejectAddress][OUT]");
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), resp.getNumber());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
}
