package com.yhglobal.gongxiao.phjd.foundation.basics;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.phjd.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.phjd.base.protal.PortalConfig;
import com.yhglobal.gongxiao.phjd.bean.JDWarehouseBean;
import com.yhglobal.gongxiao.phjd.foundation.service.JDWarehouseAddressService;
import com.yhglobal.gongxiao.phjd.utils.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 京东仓库地址管理
 *
 * @author yuping.lin
 */
@Controller
@RequestMapping("/jdwarehouse/address")
public class JDWarehouseAddressController {
    private static Logger logger = LoggerFactory.getLogger(JDWarehouseAddressController.class);

    @Autowired
    PortalConfig portalConfig; //property注入类

    @Autowired
    PortalUserInfo portalUserInfo; //用户信息注入类

    @Autowired
    JDWarehouseAddressService jdWarehouseAddressService;

    /**
     * 京东仓库地址管理查询
     *
     * @param request
     * @param response
     * @param warehouseID   仓库编码
     * @param warehouse 仓库名称
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectAllWarehouseAddress")
    public GongxiaoResult selectAllWarehouseAddress(HttpServletRequest request, HttpServletResponse response,
                                           @RequestParam(defaultValue = "") String warehouseID,
                                           @RequestParam(defaultValue = "") String warehouse,
                                           @RequestParam(defaultValue = "1") int pageNumber,
                                           @RequestParam(defaultValue = "60") int pageSize) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectAllWarehouseAddress] params: warehouseId = {}, warehouseName = {}", warehouseID, warehouse, pageNumber, pageSize);
            PageInfo<JDWarehouseBean> pageInfo = jdWarehouseAddressService.selectAllWarehouseAddress(warehouseID, warehouse, pageNumber, pageSize);
            logger.info("#traceId={}# [selectAllWarehouseAddress][OUT]");
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), pageInfo);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

}
