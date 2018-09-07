package com.yhglobal.gongxiao.phjd.foundation.common;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.phjd.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.phjd.base.protal.PortalConfig;
import com.yhglobal.gongxiao.phjd.bean.ProductPriceVo;
import com.yhglobal.gongxiao.phjd.utils.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.util.DoubleScale;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.warehouseapi.XpsWarehouseManager;
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
 * 获取货品信息
 * @author yuping.lin
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    private static Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

    /**
     * 获取当前项目的货品信息以及实际库存
     *
     * @param productCode 产品CODE
     * @param productName 产品名称
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getProductList")
    public GongxiaoResult getProductList(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam(defaultValue = "") String productCode,
                                         @RequestParam(defaultValue = "") String productName,
                                         @RequestParam(defaultValue = "1") int pageNumber,
                                         @RequestParam(defaultValue = "60") int pageSize) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            long projectId = portalUserInfo.getProjectId();
            logger.info("#traceId={}# [IN][getProductList] params: projectId:{}, productCode:{}, productName:{} ",
                    projectId, productCode, productName);

            ProductStructure.SelectProductByConditionReq selectProductByConditionReq = ProductStructure.SelectProductByConditionReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setProductCode(productCode)
                    .setProductName(productName)
                    .setProductEasCode("")
                    .setPageNumber(pageNumber)
                    .setPageSize(pageSize)
                    .build();

            ProductServiceGrpc.ProductServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
            ProductStructure.SelectProductByConditionResp selectProductByConditionResp = rpcStub.selectProductByCondition(selectProductByConditionReq);
            List<ProductStructure.ProductBusiness> productBusinessList = selectProductByConditionResp.getProductBusinessList();
            long total = selectProductByConditionResp.getTotal();
            List<ProductPriceVo> productInfoList = new ArrayList<>();
            for (ProductStructure.ProductBusiness productBasic : productBusinessList) {
                gongxiaoResult = XpsWarehouseManager.getproductQuqntity(portalConfig.getWarehouseUrl(),
                        portalConfig.getXpsChannelId(), "",
                        projectId + "",
                        productBasic.getProductModel());
                ProductPriceVo productInfo = new ProductPriceVo();
                productInfo.setProductCode(productBasic.getProductModel());
                productInfo.setProductName(productBasic.getProductName());
                productInfo.setGuidePrice(DoubleScale.keepSixBits(productBasic.getPurchaseGuidePrice()));
                if (gongxiaoResult != null && gongxiaoResult.getReturnCode() == ErrorCode.SUCCESS.getErrorCode()) {
                    int number = (int) gongxiaoResult.getData();
                    productInfo.setInStockQuantity(number);
                }
                productInfo.setProductId(productBasic.getProductBusinessId() + "");
                productInfo.setSaleGuidePrice(DoubleScale.keepSixBits(productBasic.getSaleGuidePrice()));
                productInfoList.add(productInfo);
            }
            PageInfo<ProductPriceVo> productInfoPageInfo = new PageInfo<ProductPriceVo>();
            productInfoPageInfo.setList(productInfoList);
            productInfoPageInfo.setTotal(total);
            logger.info("#traceId={}# [OUT] getProductList success: ", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), productInfoPageInfo);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }
}
