package com.yhglobal.gongxiao.phjd.foundation.commodityCenter;

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
import com.yhglobal.gongxiao.phjd.bean.ProductBean;
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

import static com.yhglobal.gongxiao.constant.ErrorCode.ARGUMENTS_INVALID;

/**
 * 商品中心:货品管理
 * @author yuping.lin
 */
@Controller
@RequestMapping("/product")
public class ProductController {
    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

    /**
     * 1:根据条件查询商品
     * @param request
     * @param response
     * @param model         型号
     * @param goodsName     货品名称
     * @param materialCoding  物料编码
     * @param searchStatus     状态
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveCommodityCenter")
    public GongxiaoResult saveCommodityCenter(HttpServletRequest request, HttpServletResponse response,
                                              @RequestParam(defaultValue = "") String model,
                                              @RequestParam(defaultValue = "") String goodsName,
                                              @RequestParam(defaultValue = "") String materialCoding,
                                              @RequestParam(defaultValue = "1") Integer searchStatus,
                                              @RequestParam(defaultValue = "1") Integer pageNumber,
                                              @RequestParam(defaultValue = "60") Integer pageSize) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][saveCommodityCenter] params: model = {}, goodsName={}, materialCoding = {}, searchStatus = {}, pageNumber = {}, pageSize = {}",
                    rpcHeader.getTraceId(), model, goodsName, materialCoding, searchStatus, pageNumber, pageSize);

            ProductStructure.SelectProductByConditionReq req = ProductStructure.SelectProductByConditionReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(portalUserInfo.getProjectId())
                    .setProductCode(model)
                    .setProductName(goodsName)
                    .setProductEasCode(materialCoding)
                    .setStatus(searchStatus)
                    .setPageNumber(pageNumber)
                    .setPageSize(pageSize)
                    .build();
            ProductServiceGrpc.ProductServiceBlockingStub stub = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
            ProductStructure.SelectProductByConditionResp resp = stub.selectProductByCondition(req);
            List<ProductBean> productBeanList = new ArrayList<>();
            for (ProductStructure.ProductBusiness list : resp.getProductBusinessList()) {
                ProductBean pb = new ProductBean();
                pb.setProductModel(list.getProductModel());
                pb.setProductName(list.getProductName());
                pb.setEasCode(list.getEasCode());
                pb.setWmsCode(list.getWmsCode());
                pb.setRecordStatus(list.getRecordStatus());
                pb.setPackageNumber(list.getPackageNumber());
                productBeanList.add(pb);
            }
            PageInfo<ProductBean> productVoPageInfo = new PageInfo<>();
            productVoPageInfo.setList(productBeanList);
            productVoPageInfo.setTotal(resp.getTotal());
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), productVoPageInfo);
            logger.info("#traceId={}# [OUT][saveCommodityCenter] success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 2:货品新增(目前该功能没有)
     * @param request
     * @param response
     * @param goodsName 货品名称
     * @param specificationMode  规格型号
     * @param EASCode  EAS物料编码
     * @param WMSCoding WMS编码
     * @param customerSKUCoding   客户SKU编码
     * @param purchasingGuidancePrice     采购指导价
     * @param taxRate       税率
     * @param taxonomyTaxRate      税率分类编码
     * @param transportlong   运输单元长
     * @param transportwide   运输单元 宽
     * @param transporthigh  运输单元高
     * @param transportweight  运输单元  重量
     * @param transportBoxNumber  运输单元 箱装数
     * @return
     */
//    @ResponseBody
//    @RequestMapping("/insertProductInfo")
//    public GongxiaoResult insertProductInfo(HttpServletRequest request, HttpServletResponse response,
//                                            @RequestParam(defaultValue = "") String goodsName,
//                                            @RequestParam(defaultValue = "") String specificationMode,
//                                            @RequestParam(defaultValue = "") String EASCode,
//                                            @RequestParam(defaultValue = "") String WMSCoding,
//                                            @RequestParam(defaultValue = "") String customerSKUCoding,
//                                            @RequestParam(defaultValue = "0") int purchasingGuidancePrice,
//                                            @RequestParam(defaultValue = "0") int taxRate,
//                                            @RequestParam(defaultValue = "") String taxonomyTaxRate,
//                                            @RequestParam(defaultValue = "0") int transportlong,
//                                            @RequestParam(defaultValue = "0") int transportwide,
//                                            @RequestParam(defaultValue = "0") int transporthigh,
//                                            @RequestParam(defaultValue = "0") int transportweight,
//                                            @RequestParam(defaultValue = "0") int transportBoxNumber) {
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        String traceId = null;
//        GongxiaoResult gongxiaoResult = null;
//        try {
//            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
//            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
//            logger.info("#traceId={}# [IN][insertProductInfo] params: goodsName = {}, specificationMode={}, EASCode = {}, WMSCoding = {}, customerSKUCoding = {}, purchasingGuidancePrice = {}, taxRate = {}, taxonomyTaxRate = {}, " +
//                            "transportlong = {}, transportwide = {}, transporthigh = {}, transportweight = {}, transportBoxNumber = {}",
//                    rpcHeader.getTraceId(), goodsName, specificationMode, EASCode, WMSCoding, customerSKUCoding, purchasingGuidancePrice, taxRate, taxonomyTaxRate, transportlong, transportwide, transporthigh, transportweight, transportBoxNumber);
//
//            ProductStructure.InsertProductInfoReq.Builder builder = ProductStructure.InsertProductInfoReq.newBuilder();
//            ProductStructure.ProductBasicDetail.Builder pd = ProductStructure.ProductBasicDetail.newBuilder();
//            pd.setProjectId(Long.toString(portalUserInfo.getProjectId()));
//            pd.setProductName(goodsName);
//            pd.setProductModel(specificationMode);
//            pd.setEasCode(EASCode);
//            pd.setWmsCode(WMSCoding);
//            pd.setCustomerSKUCode(customerSKUCoding);
//            pd.setPurchaseGuidePrice(purchasingGuidancePrice);
//            pd.setTaxRate(taxRate);
//            pd.setTaxCode(taxonomyTaxRate);
//            pd.setBoxLength(transportlong);
//            pd.setBoxWidth(transportwide);
//            pd.setBoxHeight(transporthigh);
//            pd.setBoxWeight(transportweight);
//            pd.setPackageNumber(transportBoxNumber);
//            builder.setRpcHeader(rpcHeader);
//            builder.setProductBasicDetail(pd);
//            ProductStructure.InsertProductInfoReq req = builder.build();
//            ProductServiceGrpc.ProductServiceBlockingStub stub = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
//            ProductStructure.InsertProductInfoResp resp = stub.insertProductInfo(req);
//            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), resp);
//            logger.info("#traceId={}# [OUT][insertProductInfo] success", rpcHeader.getTraceId());
//        } catch (Exception e) {
//            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
//            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
//        }
//        return gongxiaoResult;
//    }

    /**
     * 3:商品详情
     */
    @ResponseBody
    @RequestMapping("/getProductDetailByModel")
    public GongxiaoResult getProductDetailByModel(HttpServletRequest request, HttpServletResponse response,
                                                      @RequestParam(defaultValue = "") String productModel) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ProductStructure.GetProductDetailByModelReq.Builder builder = ProductStructure.GetProductDetailByModelReq.newBuilder();
            ProductStructure.ProductBasicDetail.Builder pd = ProductStructure.ProductBasicDetail.newBuilder();
            pd.setProductName(productModel);
            builder.setRpcHeader(rpcHeader);
            builder.setProductModel(productModel);
            ProductStructure.GetProductDetailByModelReq req = builder.build();
            ProductServiceGrpc.ProductServiceBlockingStub stub = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
            ProductStructure.GetProductDetailByModelResp resp = stub.getProductDetailByModel(req);

            ProductBean pb = new ProductBean();
            pb.setGoodsName(resp.getProductBasicDetail().getProductName());
            pb.setSpecificationMode(resp.getProductBasicDetail().getProductModel());
            pd.setEasCode(resp.getProductBasicDetail().getEasCode());
            pd.setWmsCode(resp.getProductBasicDetail().getWmsCode());
            pb.setCustomerSKUCoding(resp.getProductBasicDetail().getCustomerSKUCode());
            pb.setCustomerCommodityCoding(resp.getProductBasicDetail().getCustomerProductCode());
            pb.setPurchasingGuidancePrice(resp.getProductBasicDetail().getPurchaseGuidePrice());
            pb.setTaxRate(resp.getProductBasicDetail().getTaxRate());
            pb.setTaxonomyTaxRate(resp.getProductBasicDetail().getTaxCode());
            pb.setSalesGuidancePrice(resp.getProductBasicDetail().getSaleGuidePrice());
            pb.setRealSalesRebate(resp.getProductBasicDetail().getActualSaleReturn());
            pb.setPinBackPoint(resp.getProductBasicDetail().getShouldSaleReturn());
            pb.setFactorySendRebatePoint(resp.getProductBasicDetail().getFactorySendReturn());
            pb.setCostPrice(resp.getProductBasicDetail().getCostPrice());
            pb.setExportPrice(resp.getProductBasicDetail().getOutPrice());
//            pb.setSalesSupportDiscounts(resp.getProductBasicDetail().get); ////销售支持折扣 salesSupportDiscounts
            pb.setGeneratingRebatesPurchasePrice(resp.getProductBasicDetail().getGenerateCoupon());
            pb.setSalelong(resp.getProductBasicDetail().getProductLength());
            pb.setSalewide(resp.getProductBasicDetail().getProductWidth());
            pb.setSalehigh(resp.getProductBasicDetail().getProductHeight());
            pb.setSaleweight(resp.getProductBasicDetail().getProductWeight());
            pb.setTransportlong(resp.getProductBasicDetail().getBoxLength());
            pb.setTransportwide(resp.getProductBasicDetail().getBoxWidth());
            pb.setTransporthigh(resp.getProductBasicDetail().getBoxHeight());
            pb.setTransportweight(resp.getProductBasicDetail().getBoxWeight());
            //日志信息(没有)
            /*List<PurchaseLog> plList = new ArrayList<>();
            for (){
                PurchaseLog pl = new PurchaseLog();
                pl.setDescribe();              //描述
                pl.getOperationTime();         //操作时间
                pl.setOperator();              //操作人
            }*/

            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), pb);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 4:编辑商品
     */
    @ResponseBody
    @RequestMapping("/editProduct")
    public GongxiaoResult editProduct(HttpServletRequest request, HttpServletResponse response,
                                      //1 未提交,未生效货品编辑  2.未提交,已生效货品编辑 3  已提交,未生效货品编辑
                                      @RequestParam(defaultValue = "0") int editType,
                                      @RequestParam(defaultValue = "") String goodsName,
                                      @RequestParam(defaultValue = "") String specificationMode,
                                      @RequestParam(defaultValue = "") String EASCode,
                                      @RequestParam(defaultValue = "") String WMSCoding,
                                      @RequestParam(defaultValue = "") String customerSKUCoding,
                                      @RequestParam(defaultValue = "") String customerCommodityCoding,
                                      @RequestParam(defaultValue = "0") int purchasingGuidancePrice,
                                      @RequestParam(defaultValue = "0") int taxRate,
                                      @RequestParam(defaultValue = "") String taxonomyTaxRate,
                                      @RequestParam(defaultValue = "0") int salesGuidancePrice,
                                      @RequestParam(defaultValue = "0") int realSalesRebate,
                                      @RequestParam(defaultValue = "0") int pinBackPoint,
                                      @RequestParam(defaultValue = "0") int factorySendRebatePoint,
                                      @RequestParam(defaultValue = "0") int costPrice,
                                      @RequestParam(defaultValue = "0") int exportPrice,
                                      @RequestParam(defaultValue = "") String salesSupportDiscounts,
                                      @RequestParam(defaultValue = "0") int generatingRebatesPurchasePrice,
                                      @RequestParam(defaultValue = "0") int salelong,
                                      @RequestParam(defaultValue = "0") int salewide,
                                      @RequestParam(defaultValue = "0") int salehigh,
                                      @RequestParam(defaultValue = "0") int saleweight,
                                      @RequestParam(defaultValue = "0") int transportlong,
                                      @RequestParam(defaultValue = "0") int transportwide,
                                      @RequestParam(defaultValue = "0") int transporthigh,
                                      @RequestParam(defaultValue = "0") int transportweight,
                                      @RequestParam(defaultValue = "0") int transportBoxNumber) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ProductStructure.EditProductReq.Builder builder = ProductStructure.EditProductReq.newBuilder();
            ProductStructure.ProductBasicDetail.Builder pd = ProductStructure.ProductBasicDetail.newBuilder();
            pd.setProductName(goodsName);
            pd.setProductModel(specificationMode);
            pd.setEasCode(EASCode);
            pd.setWmsCode(WMSCoding);
            pd.setCustomerSKUCode(customerSKUCoding);
            pd.setCustomerProductCode(customerCommodityCoding);
            pd.setPurchaseGuidePrice(purchasingGuidancePrice);
            pd.setTaxRate(taxRate);
            pd.setTaxCode(taxonomyTaxRate);
            pd.setSaleGuidePrice(salesGuidancePrice);
            pd.setActualSaleReturn(realSalesRebate);
            pd.setShouldSaleReturn(pinBackPoint);
            pd.setFactorySendReturn(factorySendRebatePoint);
            pd.setCostPrice(costPrice);
            pd.setOutPrice(exportPrice);
//            pd.set;                         //销售支持折扣 salesSupportDiscounts
            pd.setGenerateCoupon(generatingRebatesPurchasePrice);
            pd.setProductLength(salelong);
            pd.setProductWidth(salewide);
            pd.setProductHeight(salehigh);
            pd.setProductWeight(saleweight);
            pd.setBoxLength(transportlong);
            pd.setBoxWidth(transportwide);
            pd.setBoxHeight(transporthigh);
            pd.setBoxWeight(transportweight);
            pd.setPackageNumber(transportBoxNumber);
            builder.setRpcHeader(rpcHeader);
            builder.setEditType(editType);
            builder.setProductBasicDetail(pd);
            ProductStructure.EditProductReq req = builder.build();
            ProductServiceGrpc.ProductServiceBlockingStub stub = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
            ProductStructure.EditProductResp resp = stub.editProduct(req);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), resp);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 5:提交
     */
    @ResponseBody
    @RequestMapping("/commitProductBusinessInfo")
    public GongxiaoResult commitProductBusinessInfo(HttpServletRequest request, HttpServletResponse response,
                                                    @RequestParam(defaultValue = "0") int productBusinessId) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ProductStructure.CommitProductBusinessInfoReq req = ProductStructure.CommitProductBusinessInfoReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProductBusinessId(productBusinessId)
                    .setProjectId(portalUserInfo.getProjectId())
                    .build();

            ProductServiceGrpc.ProductServiceBlockingStub stub = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
            ProductStructure.CommitProductBusinessInfoResp resp = stub.commitProductBusinessInfo(req);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), resp);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 货品审核管理(审核,驳回)
     */
    @ResponseBody
    @RequestMapping("/auditProduct")
    public GongxiaoResult auditProduct(HttpServletRequest request, HttpServletResponse response,
                                                    @RequestParam(defaultValue = "0") int productBusinessId) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ProductStructure.AuditProductReq req = ProductStructure.AuditProductReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProductBusinessId(productBusinessId)
                    .build();
            ProductServiceGrpc.ProductServiceBlockingStub stub = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
            ProductStructure.AuditProductResp resp = stub.auditProduct(req);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), resp);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

}
