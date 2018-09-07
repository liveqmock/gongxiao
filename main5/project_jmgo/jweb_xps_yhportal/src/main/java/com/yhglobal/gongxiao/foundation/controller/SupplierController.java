package com.yhglobal.gongxiao.foundation.controller;

import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.protal.PortalConfig;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierServiceGrpc;
import com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure;
import com.yhglobal.gongxiao.foundation.viewobject.FoundationSupplier;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
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
@RequestMapping("/supplier")
public class SupplierController  {

    private static Logger logger = LoggerFactory.getLogger(SupplierController.class);


    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

    @ResponseBody
    @RequestMapping("/selectAllSupplier")
    public GongxiaoResult selectAllSupplier(HttpServletRequest request){
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        try {
            logger.info("#traceId={}# [IN][getSupplierList] params: ", rpcHeader.getTraceId());
            SupplierStructure.SelectAllReq selectAllReq = SupplierStructure.SelectAllReq.newBuilder().setRpcHeader(rpcHeader).build();
            SupplierServiceGrpc.SupplierServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SupplierServiceGrpc.SupplierServiceBlockingStub.class);
            SupplierStructure.SelectAllResp resp = null;
            try {
                resp = rpcStub.selectAll(selectAllReq);
            }catch (Exception e){
                e.printStackTrace();
            }
            List<SupplierStructure.Supplier> supplierListList = resp.getSupplierListList();
            List<FoundationSupplier> foundationSupplierList = new ArrayList<>();
            for (SupplierStructure.Supplier supplier:supplierListList){
                FoundationSupplier foundationSupplier = new FoundationSupplier();
                foundationSupplier.setSupplierId(supplier.getSupplierId());
                foundationSupplier.setSupplierCode(supplier.getSupplierCode());
                foundationSupplier.setSupplierName(supplier.getSupplierName());
                foundationSupplier.setEasSupplierCode(supplier.getEasSupplierCode());
                foundationSupplier.setEasSupplierName(supplier.getEasSupplierName());
                foundationSupplier.setRecordStatus((byte)supplier.getRecordStatus());
                foundationSupplier.setAddress(supplier.getAddress());
                foundationSupplier.setEmail(supplier.getEmail());
                foundationSupplier.setContactName(supplier.getContactName());
                foundationSupplier.setCountryCode(supplier.getCountryCode());
                foundationSupplier.setPhoneNumber(supplier.getPhoneNumber());
                foundationSupplier.setCurrentProjectInfo(supplier.getCurrentProjectInfo());
                foundationSupplier.setHistoryProjectInfo(supplier.getHistoryProjectInfo());
                foundationSupplier.setCreatedById(supplier.getCreatedById());
                foundationSupplier.setCreatedByName(supplier.getCreatedByName());
                foundationSupplier.setCreateTime(DateUtil.long2Date(supplier.getCreateTime()));
                foundationSupplier.setLastUpdateTime(DateUtil.long2Date(supplier.getLastUpdateTime()));
                foundationSupplier.setTracelog(supplier.getTracelog());
                foundationSupplierList.add(foundationSupplier);
            }
            logger.info("#traceId={}# [OUT] get getSupplierList success: brandList.size()={}", rpcHeader.getTraceId(), foundationSupplierList.size());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), foundationSupplierList);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

}
