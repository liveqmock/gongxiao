package com.yhglobal.gongxiao.foundation.controller;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.protal.PortalConfig;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressServiceGrpc;
import com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure;
import com.yhglobal.gongxiao.foundation.viewobject.DistributorShippingAddress;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.status.FoundationNormalStatus;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.DateUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.utils.YhPortalTraceIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Controller
@RequestMapping("/shippingAddress")
public class DistributorShippingAddressController {

    private static Logger logger = LoggerFactory.getLogger(DistributorShippingAddressController.class);

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

    /**
     * 获取需审核地址列表
     * @param request
     * @param distributorAccount 经销商账号
     * @param distributorName 经销商名称
     * @param auditStatus 审核状态
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping("/selectNeedAuditAddressList")
    @ResponseBody
    public GongxiaoResult selectNeedAuditAddressList(HttpServletRequest request,
                                                     String distributorAccount,
                                                     String distributorName,
                                                     int auditStatus,
                                                     int pageNumber,
                                                     int pageSize) {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());

        try {
            logger.info("#traceId={}# [IN][selectNeedAuditAddressList] params:  projectId={}, distributorAccount={}, distributorName={}",
                    traceId, distributorAccount, distributorName);
            //2. 接口查询
            ShippingAddressStructure.SelectAddressListByStatusReq selectNeedAuditAddressListReq = ShippingAddressStructure.SelectAddressListByStatusReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setDistributorName(distributorName)
                    .setRecordStatus(auditStatus)
                    .setPageNumber(pageNumber)
                    .setPageSize(pageSize)
                    .build();
            ShippingAddressServiceGrpc.ShippingAddressServiceBlockingStub rpcStub
                    = RpcStubStore.getRpcStub(ShippingAddressServiceGrpc.ShippingAddressServiceBlockingStub.class);
            ShippingAddressStructure.SelectAddressListByStatusResp resp = null;
            try {
                resp = rpcStub.selectAddressListByStatus(selectNeedAuditAddressListReq);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ShippingAddressStructure.ShippingAddressPage shippingAddressPage = resp.getShippingAddressPage();
            PageInfo<DistributorShippingAddress> distributorShippingAddressPageInfo = new PageInfo<>();
            distributorShippingAddressPageInfo.setTotal(shippingAddressPage.getTotal());
            List<DistributorShippingAddress> distributorShippingAddressList = new ArrayList<>();
            for (ShippingAddressStructure.DistributorShippingAddress distributorShippingAddress : shippingAddressPage.getDistributorShippingAddressList()) {
                DistributorShippingAddress distributorShippingAddress1 = new DistributorShippingAddress();
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
            logger.info("#traceId={}# [selectNeedAuditAddressList][OUT]");
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), distributorShippingAddressPageInfo);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return  GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 地址审核通过接口
     * @param request
     * @param addressId
     * @return
     */
    @RequestMapping("/auditAddress")
    @ResponseBody
    public GongxiaoResult auditAddress(HttpServletRequest request, long addressId){
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        try {
            logger.info("#traceId={}# [IN][auditAddress] params:  projectId={},  distributorAddressId={} " ,
                    rpcHeader.getTraceId(),  addressId);
            //2. 调用接口
            ShippingAddressStructure.AuditAddressReq auditAddressReq = ShippingAddressStructure.AuditAddressReq.newBuilder()
                    .setDistributorAddressId(addressId)
                    .setRpcHeader(rpcHeader)
                    .setAuditStatus(1)
                    .build();
            ShippingAddressServiceGrpc.ShippingAddressServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(ShippingAddressServiceGrpc.ShippingAddressServiceBlockingStub.class);
            ShippingAddressStructure.AuditAddressResp resp=null;
            try{
                resp=rpcStub.auditAddress(auditAddressReq);
            }catch (Exception e){
                e.printStackTrace();
            }
            logger.info("#traceId={}# [auditAddress][OUT]");
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), resp.getNumber());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 地址审核不通过接口
     * @param request
     * @param distributorAddressId
     * @return
     */
    @RequestMapping("/rejectAddress")
    @ResponseBody
    public GongxiaoResult rejectAddress(HttpServletRequest request, long distributorAddressId){
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        try {
            logger.info("#traceId={}# [IN][rejectAddress] params:  projectId={},  distributorAddressId={} " ,
                    rpcHeader.getTraceId(),  distributorAddressId);
            //2. 调用接口
            ShippingAddressStructure.AuditAddressReq auditAddressReq = ShippingAddressStructure.AuditAddressReq.newBuilder()
                    .setDistributorAddressId(distributorAddressId)
                    .setRpcHeader(rpcHeader)
                    .setAuditStatus(2)
                    .build();
            ShippingAddressServiceGrpc.ShippingAddressServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(ShippingAddressServiceGrpc.ShippingAddressServiceBlockingStub.class);
            ShippingAddressStructure.AuditAddressResp resp=null;
            try{
                resp=rpcStub.auditAddress(auditAddressReq);
            }catch (Exception e){
                e.printStackTrace();
            }
            logger.info("#traceId={}# [rejectAddress][OUT]");
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), resp.getNumber());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }



}
