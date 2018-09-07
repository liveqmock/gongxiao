package com.yhglobal.gongxiao.phjd.foundation.commodityCenter;

import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.phjd.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.phjd.base.protal.PortalConfig;
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

/**
 * 商品中心:货品审核管理
 * @author yuping.lin
 */
@Controller
@RequestMapping("/productAudit")
public class ProductAuditController {

    private static Logger logger = LoggerFactory.getLogger(ProductAuditController.class);

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;
    /**
     * 1:条件查询接口
     */
    @ResponseBody
    @RequestMapping("/selectProductByCondition")
    public GongxiaoResult selectProductByCondition(HttpServletRequest request, HttpServletResponse response,
                                                   @RequestParam(defaultValue = "") String model,
                                                   @RequestParam(defaultValue = "") String goodsName,
                                                   @RequestParam(defaultValue = "") String materialCoding,
                                                   @RequestParam(defaultValue = "0") Integer searchStatus,
                                                   @RequestParam(defaultValue = "0") Integer pageNumber,
                                                   @RequestParam(defaultValue = "0") Integer pageSize){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            //TODO 目前接口没有实现
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
    /**
     * 2:审核接口
     */
    /**
     * 3:驳回接口

     */

    /**
     * 4:导出接口
     */

    /**
     * 5:根据id查找详情
     */

}
