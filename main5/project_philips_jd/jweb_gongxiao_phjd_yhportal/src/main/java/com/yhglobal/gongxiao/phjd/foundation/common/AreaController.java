package com.yhglobal.gongxiao.phjd.foundation.common;

import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.area.microservice.AreaServiceGrpc;
import com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.phjd.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.phjd.base.protal.PortalConfig;
import com.yhglobal.gongxiao.phjd.bean.AreaBean;
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
 * 获取省市区地址信息
 * @author yuping.lin
 */
@Controller
@RequestMapping("/area")
public class AreaController {
    private static Logger logger = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    PortalConfig portalConfig; //property注入类

    @Autowired
    PortalUserInfo portalUserInfo; //用户信息注入类

    //1获取所有省列表
    @RequestMapping("/selectProvinceList")
    @ResponseBody
    public GongxiaoResult selectProvinceList(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][supplierCouponPosting]", traceId);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            AreaStructure.SelectProvinceRequest req = AreaStructure.SelectProvinceRequest.newBuilder().setRpcHeader(rpcHeader).build();
            AreaServiceGrpc.AreaServiceBlockingStub stub = RpcStubStore.getRpcStub(AreaServiceGrpc.AreaServiceBlockingStub.class);
            AreaStructure.SelectProvinceResponse resp = stub.selectProvinceList(req);
            List<AreaBean> areaBeanList = new ArrayList<>();
            for (AreaStructure.Province list : resp.getProvinceList()) {
                AreaBean areaBean = new AreaBean();
                areaBean.setProvinceId(list.getProvinceId());
                areaBean.setProvinceCode(list.getProvinceCode());
                areaBean.setProvinceName(list.getProvinceName());
                areaBeanList.add(areaBean);
            }
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), areaBeanList);
            logger.info("#traceId={}# [selectProvinceList][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    //2 获取某省下的所有城市
    @RequestMapping("/selectCityByProvince")
    @ResponseBody
    public GongxiaoResult selectCityByProvince(HttpServletRequest request, HttpServletResponse response,
                                               @RequestParam(defaultValue = "0") String provinceCode) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectCityByProvince]", traceId);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            AreaStructure.SelectCityByProvinceRequest req = AreaStructure.SelectCityByProvinceRequest.newBuilder().setRpcHeader(rpcHeader).setProvinceCode(provinceCode).build();
            AreaServiceGrpc.AreaServiceBlockingStub stub = RpcStubStore.getRpcStub(AreaServiceGrpc.AreaServiceBlockingStub.class);
            AreaStructure.SelectCityByProvinceResponse resp = stub.selectCityByProvince(req);
            List<AreaBean> areaBeanList = new ArrayList<>();
            for (AreaStructure.City list : resp.getCityList()) {
                AreaBean areaBean = new AreaBean();
                areaBean.setCityId(list.getCityId());
                areaBean.setCityCode(list.getCityCode());
                areaBean.setCityName(list.getCityName());
                areaBean.setProvinceCode(list.getProvinceCode());
                areaBeanList.add(areaBean);
            }
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), areaBeanList);
            logger.info("#traceId={}# [selectCityByProvince][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    //3 获取某市下的所有区域
    @RequestMapping("/selectDistrictsByCity")
    @ResponseBody
    public GongxiaoResult selectDistrictsByCity(HttpServletRequest request, HttpServletResponse response,
                                                @RequestParam(defaultValue = "0") String cityCode) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            AreaStructure.SelectDistrictsByCityRequest req = AreaStructure.SelectDistrictsByCityRequest.newBuilder().setRpcHeader(rpcHeader).setCityCode(cityCode).build();
            AreaServiceGrpc.AreaServiceBlockingStub stub = RpcStubStore.getRpcStub(AreaServiceGrpc.AreaServiceBlockingStub.class);
            AreaStructure.SelectDistrictsByCityResponse resp = stub.selectDistrictsByCity(req);
            List<AreaBean> areaBeanList = new ArrayList<>();
            for (AreaStructure.District list : resp.getDistrictListList()) {
                AreaBean areaBean = new AreaBean();
                areaBean.setDistrictId(list.getDistrictId());
                areaBean.setDistrictCode(list.getDistrictCode());
                areaBean.setDistrictName(list.getDistrictName());
                areaBean.setCityCode(list.getCityCode());
                areaBeanList.add(areaBean);
            }
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), areaBeanList);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


//    //5 获取所有的区域
////    rpc selectAllDistrict (SelectAllDistrictRequest) returns (SelectAllDistrictResponse) {}
//    @RequestMapping("/selectAllDistrict")
//    @ResponseBody
//    public GongxiaoResult selectAllDistrict(HttpServletRequest request, HttpServletResponse response,
//                                                @RequestParam(defaultValue = "0") String districtId) {
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        String traceId = null;
//        GongxiaoResult gongxiaoResult = null;
//        try {
//            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
//            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, (int) portalUserInfo.getProjectId(), portalUserInfo.getUserId(), portalUserInfo.getUserName());
//            AreaStructure.SelectAllDistrictRequest req = AreaStructure.SelectAllDistrictRequest.newBuilder().setRpcHeader(rpcHeader).set(cityCode).build();
//            AreaServiceGrpc.AreaServiceBlockingStub stub = RpcStubStore.getRpcStub(AreaServiceGrpc.AreaServiceBlockingStub.class);
//            AreaStructure.SelectDistrictsByCityResponse resp = stub.selectDistrictsByCity(req);
//            List<AreaBean> areaBeanList = new ArrayList<>();
//            for (AreaStructure.District list : resp.getDistrictListList()) {
//                AreaBean areaBean = new AreaBean();
//                areaBean.setDistrictId(list.getDistrictId());
//                areaBean.setDistrictCode(list.getDistrictCode());
//                areaBean.setDistrictName(list.getDistrictName());
//                areaBean.setCityCode(list.getCityCode());
//                areaBeanList.add(areaBean);
//            }
//            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), areaBeanList);
//        } catch (Exception e) {
//            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
//            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
//        }
//        return gongxiaoResult;
//    }
}