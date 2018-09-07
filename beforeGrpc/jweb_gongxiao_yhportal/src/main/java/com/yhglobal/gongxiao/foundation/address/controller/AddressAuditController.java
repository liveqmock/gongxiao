package com.yhglobal.gongxiao.foundation.address.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.distributor.model.DistributorShippingAddress;
import com.yhglobal.gongxiao.foundation.distributor.service.DistributorAddressService;
import com.yhglobal.gongxiao.util.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.YhPortalTraceIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/addressAudit")
public class AddressAuditController {

    private static Logger logger = LoggerFactory.getLogger(AddressAuditController.class);

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

    @Reference
    DistributorAddressService distributorAddressService;

    @RequestMapping("/selectNeedAuditAddressList")
    @ResponseBody
    public GongxiaoResult selectNeedAuditAddressList(HttpServletRequest request,
                                                     String projectId,
                                                     String distributorAccount,
                                                     String distributorName,
                                                     int auditStatus,
                                                     int pageNumber,
                                                     int pageSize){
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][selectNeedAuditAddressList] params:  projectId={}, projectId={}, distributorAccount={}, distributorName={}",
                    traceId,  projectId, distributorAccount, distributorName);
            PageInfo<DistributorShippingAddress> distributorShippingAddressList =
                    distributorAddressService.selectNeedAuditAddressList(rpcHeader,
                            projectId,
                            distributorAccount,
                            distributorName,
                            auditStatus,
                            pageNumber,
                            pageSize);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), distributorShippingAddressList);
            logger.info("#traceId={}# [selectNeedAuditAddressList][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    @RequestMapping("/auditAddress")
    @ResponseBody
    public GongxiaoResult auditAddress(HttpServletRequest request, String projectId, int addressId){
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][auditAddress] params:  projectId={}, projectId={}, addressId={} " ,
                    traceId,  projectId, addressId);
            int num = distributorAddressService.auditAddress(rpcHeader, projectId, addressId);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), num);
            logger.info("#traceId={}# [auditAddress][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    @RequestMapping("/rejectAddress")
    @ResponseBody
    public GongxiaoResult rejectAddress(HttpServletRequest request, String projectId, int addressId){
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][rejectAddress] params:  projectId={}, projectId={}, addressId={} " ,
                    traceId,  projectId, addressId);
            int num = distributorAddressService.rejectAddress(rpcHeader, projectId, addressId);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), num);
            logger.info("#traceId={}# [rejectAddress][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

}
