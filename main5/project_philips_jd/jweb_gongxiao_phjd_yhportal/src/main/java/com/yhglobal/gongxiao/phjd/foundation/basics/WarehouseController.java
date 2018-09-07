package com.yhglobal.gongxiao.phjd.foundation.basics;

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
 * 仓库管理
 * @author yuping.lin
 */
@Controller
@RequestMapping("/base/warehouse")
public class WarehouseController {
    private static Logger logger = LoggerFactory.getLogger(WarehouseController.class);

    @Autowired
    PortalConfig portalConfig; //property注入类

    @Autowired
    PortalUserInfo portalUserInfo; //用户信息注入类

    /**
     * 导出
     */
    @ResponseBody
    @RequestMapping("/exportExcel")
    public GongxiaoResult exportExcel(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            WarehouseStructure.SelectAllWarehouseReq req = WarehouseStructure.SelectAllWarehouseReq.newBuilder().setRpcHeader(rpcHeader).build();
            WarehouseServiceGrpc.WarehouseServiceBlockingStub stub = RpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
            WarehouseStructure.SelectAllWarehouseResp resp = stub.selectAllWarehouse(req);
            //TODO 数据写入到Excel中(未实现)
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), resp);
            logger.info("#traceId={}# [newWarehouse][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 修改仓库信息
     */
    @RequestMapping("/updateWarehouse")
    @ResponseBody
    public GongxiaoResult updateWarehouse(HttpServletRequest request, HttpServletResponse response,
                                          @RequestParam(defaultValue = "0") String easWarehouseCode,
                                          @RequestParam(defaultValue = "") String warehouseName,
                                          @RequestParam(defaultValue = "0") String warehouseType,
                                          @RequestParam(defaultValue = "0") Integer sendNotitionToWarehouse,
                                          @RequestParam(defaultValue = "") String generalContactName,
                                          @RequestParam(defaultValue = "0") String generalphoneNumber,
                                          @RequestParam(defaultValue = "") String library,
                                          @RequestParam(defaultValue = "0") String countryCode,
                                          @RequestParam(defaultValue = "") String countryName,
                                          @RequestParam(defaultValue = "0") Integer provinceId,
                                          @RequestParam(defaultValue = "") String provinceName,
                                          @RequestParam(defaultValue = "0") Integer cityId,
                                          @RequestParam(defaultValue = "") String cityName,
                                          @RequestParam(defaultValue = "0") Integer districtId,
                                          @RequestParam(defaultValue = "") String districtName,
                                          @RequestParam(defaultValue = "") String streetAddress,
                                          @RequestParam(defaultValue = "") String shortAddress,
                                          @RequestParam(defaultValue = "0") String easWarehouseName,
                                          @RequestParam(defaultValue = "") String wmsWarehouseName) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            WarehouseStructure.EditWarehouseInfoReq.Builder builder = WarehouseStructure.EditWarehouseInfoReq.newBuilder();
            WarehouseStructure.Warehouse.Builder warehouse = WarehouseStructure.Warehouse.newBuilder();
            warehouse.setEasWarehouseCode(easWarehouseCode);
            warehouse.setWarehouseName(warehouseName);
//            warehouse.setwarehouseType; //仓库类型
            warehouse.setSendNotitionToWarehouse(sendNotitionToWarehouse);
            warehouse.setGeneralContactName(generalContactName);
            warehouse.setGeneralphoneNumber(generalphoneNumber);
//            warehouse.setLocationNumber(library);  //库位
            warehouse.setCountryCode(countryCode);
            warehouse.setCountryName(countryName);
            warehouse.setProvinceId(provinceId);
            warehouse.setProvinceName(provinceName);
            warehouse.setCityId(cityId);
            warehouse.setCityName(cityName);
            warehouse.setDistrictId(districtId);
            warehouse.setDistrictName(districtName);
            warehouse.setStreetAddress(streetAddress);
            warehouse.setShortAddress(shortAddress);
            warehouse.setEasWarehouseName(easWarehouseName);
            warehouse.setWmsWarehouseName(wmsWarehouseName);
            builder.setWarehouse(warehouse);
            WarehouseStructure.EditWarehouseInfoReq req = builder.build();
            WarehouseServiceGrpc.WarehouseServiceBlockingStub stub = RpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
            WarehouseStructure.EditWarehouseInfoResp resp = stub.editWarehouseInfo(req);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), resp);
            logger.info("#traceId={}# [newWarehouse][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据Id获取仓库信息 getWarehouseById
     */
    @RequestMapping("/getWarehouseById")
    @ResponseBody
    public GongxiaoResult getWarehouseById(HttpServletRequest request, HttpServletResponse response,
                                           @RequestParam(defaultValue = "0") String easWarehouseCode) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][supplierCouponPosting] params: easWarehouseCode:{}",
                    traceId, easWarehouseCode);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            WarehouseStructure.GetWarehouseByIdReq.Builder builder = WarehouseStructure.GetWarehouseByIdReq.newBuilder();
            builder.setRpcHeader(rpcHeader);
            builder.setWarehouseId(easWarehouseCode);
            WarehouseStructure.GetWarehouseByIdReq req = builder.build();
            WarehouseServiceGrpc.WarehouseServiceBlockingStub stub = RpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
            WarehouseStructure.GetWarehouseByIdResp resp = stub.getWarehouseById(req);
            WarehouseBean warehouseBean = new WarehouseBean();
            warehouseBean.setEasWarehouseCode(resp.getWarehouse().getEasWarehouseCode());
            warehouseBean.setWarehouseName(resp.getWarehouse().getWarehouseName());
//            warehouseBean.setWarehouseType(resp.getWarehouse().getWarehouseType()); //仓库类型
//            warehouseBean.setSendNotitionToWarehouse(resp.getWarehouse().getSendNotitionToWarehouse());
            warehouseBean.setGeneralContactName(resp.getWarehouse().getGeneralContactName());
            warehouseBean.setGeneralphoneNumber(resp.getWarehouse().getGeneralphoneNumber());
//            warehouseBean.setLibrary(resp.getWarehouse().getLocationNumber()); //库位
            warehouseBean.setCityId(resp.getWarehouse().getCityId());
            warehouseBean.setCityName(resp.getWarehouse().getCityName());
            warehouseBean.setDistrictId(resp.getWarehouse().getDistrictId());
            warehouseBean.setDistrictName(resp.getWarehouse().getDistrictName());
            warehouseBean.setStreetAddress(resp.getWarehouse().getStreetAddress());
            warehouseBean.setShortAddress(resp.getWarehouse().getShortAddress());
            warehouseBean.setProvinceId(resp.getWarehouse().getProvinceId());
            warehouseBean.setProvinceName(resp.getWarehouse().getProvinceName());
            warehouseBean.setCountryCode(resp.getWarehouse().getCountryCode());
            warehouseBean.setCountryName(resp.getWarehouse().getCountryName());
//            warehouseBean.setEas_system(resp.getWarehouse().getEasWarehouseName()); //EAS系统
//            warehouseBean.setWms_system(resp.getWarehouse().getWmsWarehouseName()); //WMS系统
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), warehouseBean);
            logger.info("#traceId={}# [getWarehouseById][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 新增仓库信息
     */
    @RequestMapping("/newWarehouse")
    @ResponseBody
    public GongxiaoResult newWarehouse(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(defaultValue = "") String easWarehouseCode,
                                       @RequestParam(defaultValue = "") String warehouseName,
                                       @RequestParam(defaultValue = "0") Integer warehouseType,
                                       @RequestParam(defaultValue = "0") Integer sendNotitionToWarehouse,
                                       @RequestParam(defaultValue = "") String generalContactName,
                                       @RequestParam(defaultValue = "") String generalphoneNumber,
                                       @RequestParam(defaultValue = "") String library,
                                       @RequestParam(defaultValue = "") String countryCode,
                                       @RequestParam(defaultValue = "") String countryName,
                                       @RequestParam(defaultValue = "") Integer provinceId,
                                       @RequestParam(defaultValue = "") String provinceName,
                                       @RequestParam(defaultValue = "0") Integer cityId,
                                       @RequestParam(defaultValue = "") String cityName,
                                       @RequestParam(defaultValue = "0") Integer districtId,
                                       @RequestParam(defaultValue = "") String districtName,
                                       @RequestParam(defaultValue = "") String streetAddress,
                                       @RequestParam(defaultValue = "") String shortAddress,
                                       @RequestParam(defaultValue = "") String easWarehouseName,
                                       @RequestParam(defaultValue = "") String wmsWarehouseName) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            WarehouseStructure.InsertWarehouseInfoReq.Builder builder = WarehouseStructure.InsertWarehouseInfoReq.newBuilder();
            WarehouseStructure.Warehouse.Builder warehouse = WarehouseStructure.Warehouse.newBuilder();
            warehouse.setEasWarehouseCode(easWarehouseCode);
            warehouse.setWarehouseName(warehouseName);
            warehouse.setWarehouseType(warehouseType); //仓库类型
            warehouse.setSendNotitionToWarehouse(sendNotitionToWarehouse);
            warehouse.setGeneralContactName(generalContactName);
            warehouse.setGeneralphoneNumber(generalphoneNumber);
            warehouse.setLocationNumber(library);  //库位
            warehouse.setCountryCode(countryCode);
            warehouse.setCountryName(countryName);
            warehouse.setProvinceId(provinceId);
            warehouse.setProvinceName(provinceName);
            warehouse.setCityId(cityId);
            warehouse.setCityName(cityName);
            warehouse.setDistrictId(districtId);
            warehouse.setDistrictName(districtName);
            warehouse.setStreetAddress(streetAddress);
            warehouse.setShortAddress(shortAddress);
            warehouse.setEasWarehouseName(easWarehouseName);
            warehouse.setWmsWarehouseName(wmsWarehouseName);
            builder.setWarehouse(warehouse);
            WarehouseStructure.InsertWarehouseInfoReq req = builder.build();
            WarehouseServiceGrpc.WarehouseServiceBlockingStub stub = RpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
            WarehouseStructure.InsertWarehouseInfoResp resp = stub.insertWarehouseInfo(req);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), resp);
            logger.info("#traceId={}# [newWarehouse][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据多条件查询仓库信息列表
     */
    @RequestMapping("/selectWarehouse")
    @ResponseBody
    public GongxiaoResult selectWarehouse(HttpServletRequest request, HttpServletResponse response,
                                          @RequestParam(defaultValue = "0") String warehouseID,
                                          @RequestParam(defaultValue = "") String warehouseName) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][supplierCouponPosting] params: warehouseID:{}, warehouseName:{}",
                    traceId, warehouseID, warehouseName);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            WarehouseStructure.SelectWarehouseConditionReq.Builder builder = WarehouseStructure.SelectWarehouseConditionReq.newBuilder();
            builder.setRpcHeader(rpcHeader);
            builder.setWarehouseId(Integer.parseInt(warehouseID));
            builder.setWarehouseName(warehouseName);
            WarehouseStructure.SelectWarehouseConditionReq req = builder.build();
            WarehouseServiceGrpc.WarehouseServiceBlockingStub stub = RpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
            WarehouseStructure.SelectWarehouseConditionResp resp = stub.selectWarehouseCondition(req);
            List<WarehouseBean> warehouseBeanList = new ArrayList<>();
            for (WarehouseStructure.Warehouse list : resp.getWarehouseList()) {
                WarehouseBean warehouseBean = new WarehouseBean();
                warehouseBean.setWarehouseId(list.getWarehouseId());
                warehouseBean.setWarehouseName(list.getWarehouseName());
                warehouseBean.setGeneralContactName(list.getGeneralContactName());  //负责人
                warehouseBean.setGeneralphoneNumber(list.getGeneralphoneNumber());
                warehouseBean.setCountryCode(list.getCountryCode());
                warehouseBean.setCountryName(list.getCountryName());
                warehouseBean.setProvinceId(list.getProvinceId());
                warehouseBean.setProvinceName(list.getProvinceName());
                warehouseBean.setCityId(list.getCityId());
                warehouseBean.setCityName(list.getCityName());
                warehouseBean.setDistrictId(list.getDistrictId());
                warehouseBean.setDistrictName(list.getDistrictName());
                warehouseBean.setStreetAddress(list.getStreetAddress());
                warehouseBean.setShortAddress(list.getShortAddress());
//                warehouseBean.setLastUpdateTime(list.getLastUpdateTime());
//                warehouseBean.setOperator();  //操作人
                warehouseBeanList.add(warehouseBean);
            }
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), warehouseBeanList);
            logger.info("#traceId={}# [selectWarehouse][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
}
