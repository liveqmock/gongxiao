package com.yhglobal.gongxiao.phjd.foundation.common;

import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseServiceGrpc;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.phjd.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.phjd.base.protal.PortalConfig;
import com.yhglobal.gongxiao.phjd.bean.WarehouseBean;
import com.yhglobal.gongxiao.phjd.utils.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.ValidationUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取所有仓库信息
 * @author yuping.lin
 */
@Controller
@RequestMapping("/warehouse")
public class WarehouseInfoController {
    private static Logger logger = LoggerFactory.getLogger(WarehouseInfoController.class);

    @Autowired
    PortalConfig portalConfig; //property注入类

    @Autowired
    PortalUserInfo portalUserInfo; //用户信息注入类

    /**
     * 获取所以仓库信息
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectAllWarehouse")
    public GongxiaoResult selectAllWarehouse(HttpServletRequest request, HttpServletResponse response) {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        String projectId = String.valueOf(portalUserInfo.getProjectId());
        boolean projectNotEmpty = ValidationUtil.isNotEmpty(projectId);
        if (!projectNotEmpty){
            projectId="0";
        }
        try {
            logger.info("#traceId={}# [IN][selectAllWarehouse] params: ", rpcHeader.getTraceId());
            WarehouseStructure.SelectAllWarehouseReq selectAllWarehouseReq = WarehouseStructure.SelectAllWarehouseReq.newBuilder().setRpcHeader(rpcHeader).build();
            WarehouseServiceGrpc.WarehouseServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
            WarehouseStructure.SelectAllWarehouseResp resp=null;
            try{
                resp=rpcStub.selectAllWarehouse(selectAllWarehouseReq);
            }catch (Exception e){
                e.printStackTrace();
            }
            List<WarehouseBean> warehouseList =new ArrayList<>();
            List<WarehouseStructure.Warehouse> warehouseListList = resp.getWarehouseListList();
            for (WarehouseStructure.Warehouse warehouse:warehouseListList){
                WarehouseBean warehouse1 = new WarehouseBean();
                warehouse1.setWarehouseId(warehouse.getWarehouseId());
                warehouse1.setWarehouseId(warehouse.getWarehouseId());
                warehouse1.setWarehouseCode(warehouse.getWarehouseCode());
                warehouse1.setWarehouseName(warehouse.getWarehouseName());
                warehouse1.setEasWarehouseCode(warehouse.getEasWarehouseCode());
                warehouse1.setEasWarehouseName(warehouse1.getEasWarehouseName());
                warehouse1.setRecordStatus((byte)warehouse.getRecordStatus());
                warehouse1.setLocationNumber(warehouse.getLocationNumber());
                warehouse1.setCountryCode(warehouse.getCountryCode());
                warehouse1.setCountryName(warehouse.getCountryName());
                warehouse1.setCityId(warehouse.getCityId());
                warehouse1.setCityName(warehouse.getCityName());
                warehouse1.setProvinceId(warehouse.getProvinceId());
                warehouse1.setProvinceName(warehouse.getProvinceName());
                warehouse1.setDistrictName(warehouse.getDistrictName());
                warehouse1.setStreetAddress(warehouse.getStreetAddress());
                warehouse1.setShortAddress(warehouse.getShortAddress());
                warehouse1.setGeneralContactName(warehouse.getGeneralContactName());
                warehouse1.setGeneralphoneNumber(warehouse.getGeneralphoneNumber());
                warehouse1.setGeneralMobile(warehouse.getGeneralMobile());
                warehouse1.setCreatedById(warehouse.getCreatedById());
                warehouse1.setCreatedByName(warehouse.getCreatedByName());
                warehouse1.setLastUpdateTime(DateUtil.long2Date(warehouse.getLastUpdateTime()));
                warehouse1.setCreateTime(DateUtil.long2Date(warehouse.getCreateTime()));
                warehouse1.setTracelog(warehouse.getTracelog());
                warehouseList.add(warehouse1);
            }
            logger.info("#traceId={}# [OUT] get warehouseList success: warehouseList.size()={}", rpcHeader.getTraceId(), warehouseList.size());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), warehouseList);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId()+ "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }
}
