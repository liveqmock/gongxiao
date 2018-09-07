package com.yhglobal.gongxiao.plansale.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.protal.PortalConfig;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.plansale.controller.vo.ProductInfo;
import com.yhglobal.gongxiao.plansale.controller.vo.SaleEditInfo;
import com.yhglobal.gongxiao.plansale.controller.vo.SalePlanInfoIn;
import com.yhglobal.gongxiao.plansale.controller.vo.SalePlanInfoOut;
import com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemServiceGrpc;
import com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure;
import com.yhglobal.gongxiao.plansale.microservice.PlanSaleServiceGrpc;
import com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.DoubleScale;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.ValidationUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.utils.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.warehouseapi.XpsWarehouseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Controller
@RequestMapping("/planSale")
public class PlanSaleController {

    @Autowired
    private PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;  //处于登陆状态时 用户信息会注入到这个对象

    private static Logger logger = LoggerFactory.getLogger(PlanSaleController.class);

    /**
     * 获取当前项目的货品信息以及实际库存
     *
     * @param productCode 产品CODE
     * @param productName 产品名称
     * @param request
     * @return
     */
    @RequestMapping(value = "/getProductList", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getProductList(String productCode,
                                         String productName,
                                         int pageNumber,
                                         int pageSize,
                                         HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        try {
            long projectId = portalUserInfo.getProjectId();
            logger.info("#traceId={}# [IN][getProductList] params: projectId:{}, productCode:{}, productName:{}, pageNumber={}, pageSize={} ",
                    projectId, productCode, productName, pageNumber, pageSize);
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();

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

            List<ProductInfo> productInfoList = new ArrayList<>();
            for (ProductStructure.ProductBusiness productBasic : productBusinessList) {
                // todo: 改成一个接口
                GongxiaoResult gongxiaoResult = XpsWarehouseManager.getproductQuqntity(portalConfig.getWarehouseUrl(),
                        portalConfig.getXpsChannelId(),
                        xpsChannelSecret,
                        projectId + "",
                        productBasic.getProductModel());
                ProductInfo productInfo = new ProductInfo();
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
            PageInfo<ProductInfo> productInfoPageInfo = new PageInfo<ProductInfo>();
            productInfoPageInfo.setList(productInfoList);
            productInfoPageInfo.setTotal(total);
            logger.info("#traceId={}# [OUT] getProductList success: ", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), productInfoPageInfo);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 插入销售计划信息
     *
     * @param salePlanJson 销售计划json
     * @param request
     * @return
     */
    @RequestMapping(value = "/insertSalePlanInfo", method = RequestMethod.POST)
    @ResponseBody
    public GongxiaoResult insertSalePlanInfo(String salePlanJson,
                                             HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        try {
            long projectId = portalUserInfo.getProjectId();
            logger.info("#traceId={}# [IN][insertSalePlanInfo] params: salePlanJson:{} ", rpcHeader.getTraceId(), salePlanJson);
            //将货品列表json数组的字符串转换为List
            ArrayList<SalePlanInfoIn> salePlanInfoInArrayList
                    = JSON.parseObject(salePlanJson, new TypeReference<ArrayList<SalePlanInfoIn>>() {
            });
            for (SalePlanInfoIn salePlanInfoIn : salePlanInfoInArrayList) {
                String startTime = salePlanInfoIn.getStartTime();
                String endTime = salePlanInfoIn.getEndTime();

                boolean notEmpty = ValidationUtil.isNotEmpty(startTime, endTime);
                if (!notEmpty) {
                    return new GongxiaoResult(ErrorCode.DATE_NOT_NULL.getErrorCode(), ErrorCode.DATE_NOT_NULL.getMessage());
                }
                Date startDate = DateUtil.stringToDate(startTime, "yyyy-MM-dd");
                Date endDate = DateUtil.stringToDate(endTime, "yyyy-MM-dd");
                if (startDate.after(endDate)) {
                    return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.NOT_REPEAT_START_END_DATE);
                }
                Long productId = salePlanInfoIn.getProductId();
                //判定是否重复
                PlanSaleStructure.IsProductRepeatReq isProductRepeatReq = PlanSaleStructure.IsProductRepeatReq.newBuilder()
                        .setProjectId(projectId + "")
                        .setProductId(productId + "")
                        .setStartTime(DateUtil.getTime(startDate))
                        .setEndTime(DateUtil.getTime(endDate))
                        .build();
                PlanSaleServiceGrpc.PlanSaleServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(PlanSaleServiceGrpc.PlanSaleServiceBlockingStub.class);
                PlanSaleStructure.IsProductRepeatResp resp = null;

                resp = rpcStub.isProductRepeat(isProductRepeatReq);
                boolean productRepeat = resp.getIsRepeated();

                if (productRepeat) {
                    return new GongxiaoResult(ErrorCode.PRODUCT_REPEAT.getErrorCode(), "货品编码为" + salePlanInfoIn.getProductCode() + ErrorCode.PRODUCT_REPEAT.getMessage());
                }
            }

            PlanSaleStructure.CreateSalePlanResp resp = null;
            PlanSaleStructure.CreateSalePlanReq.Builder creatPlanReq = PlanSaleStructure.CreateSalePlanReq.newBuilder();
            for (SalePlanInfoIn salePlanInfoIn : salePlanInfoInArrayList) {
                PlanSaleStructure.SalePlanInfoIn salePlanInfoIn1 = PlanSaleStructure.SalePlanInfoIn.newBuilder()
                        .setProjectId(projectId)
                        .setProductId(salePlanInfoIn.getProductId())
                        .setProductModle(salePlanInfoIn.getProductCode())
                        .setOnSaleQuantity(salePlanInfoIn.getOnSaleQuantity())
                        .setStartTime(salePlanInfoIn.getStartTime())
                        .setEndTime(salePlanInfoIn.getEndTime())
                        .build();
                creatPlanReq.addSalePlanInfoOuts(salePlanInfoIn1);
            }
            creatPlanReq.setRpcHeader(rpcHeader);
            creatPlanReq.setProjectId(projectId);
            PlanSaleServiceGrpc.PlanSaleServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(PlanSaleServiceGrpc.PlanSaleServiceBlockingStub.class);
            resp = rpcStub.createSalePlan(creatPlanReq.build());

            logger.info("#traceId={}# [OUT] insertSalePlanInfo success: ", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), resp.getNumber());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 获取当前项目的销售计划列表
     *
     * @param productCode 产品编码
     * @param productName 产品名称
     * @param createTime  创建时间
     * @param request
     * @return
     */
    @RequestMapping(value = "/getSalePlanList", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getSalePlanList(String productCode,
                                          String productName,
                                          String createTime,
                                          int pageNumber,
                                          int pageSize,
                                          HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        try {
            long projectId = portalUserInfo.getProjectId();

            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();

            logger.info("#traceId={}# [IN][getSalePlanList] params: projectId:{}, productCode:{}, productName:{}, productName:{}, createTime={}, pageNumber={}, pageSize = {} ",
                    rpcHeader.getTraceId(), projectId, productCode, productName, createTime, pageNumber, pageSize);

            PlanSaleServiceGrpc.PlanSaleServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(PlanSaleServiceGrpc.PlanSaleServiceBlockingStub.class);
            PlanSaleStructure.SelectSalePlanListByProjectReq selectSalePlanListByProjectReq = PlanSaleStructure.SelectSalePlanListByProjectReq.newBuilder()
                    .setProjectId(projectId + "")
                    .setProductModle(productCode)
                    .setProductName(productName)
                    .setCreateTime(createTime == null ? "" : createTime)
                    .setPageNumber(pageNumber)
                    .setPageSize(pageSize)
                    .build();
            PlanSaleStructure.SelectSalePlanListByProjectResp resp = null;
            resp = rpcStub.selectSalePlanListByProject(selectSalePlanListByProjectReq);
            List<PlanSaleStructure.SalePlanInfoOut> salePlanInfoOutListList = resp.getSalePlanInfoOutListList();

            PageInfo<SalePlanInfoOut> salePlanInfoOutPageInfo = new PageInfo<SalePlanInfoOut>();
            List<SalePlanInfoOut> salePlanInfoOuts = new ArrayList<>();
            salePlanInfoOutPageInfo.setTotal(resp.getTotal());
            for (PlanSaleStructure.SalePlanInfoOut salePlanInfoOut : salePlanInfoOutListList) {
                SalePlanInfoOut salePlanInfoOut1 = new SalePlanInfoOut();
                salePlanInfoOut1.setOrderStatus(salePlanInfoOut.getOrderStatus());
                salePlanInfoOut1.setSalesPlanNo(salePlanInfoOut.getSalesPlanNo());
                salePlanInfoOut1.setProjectId(salePlanInfoOut.getProjectId());
                salePlanInfoOut1.setProjectName(salePlanInfoOut.getProjectName());
                salePlanInfoOut1.setProductId(salePlanInfoOut.getProductId());
                salePlanInfoOut1.setProductCode(salePlanInfoOut.getProductModle());
                salePlanInfoOut1.setProductName(salePlanInfoOut.getProductName());
                salePlanInfoOut1.setGuidePrice(salePlanInfoOut.getGuidePrice());
                salePlanInfoOut1.setSaleGuidePrice(salePlanInfoOut.getSaleGuidePrice());
                salePlanInfoOut1.setOnSaleQuantity(salePlanInfoOut.getOnSaleQuantity());
                salePlanInfoOut1.setAllocatedQuantity(salePlanInfoOut.getAllocatedQuantity());
                salePlanInfoOut1.setUnallocatedQuantity(salePlanInfoOut.getUnallocatedQuantity());
                GongxiaoResult gongxiaoResult = XpsWarehouseManager.getproductQuqntity(portalConfig.getWarehouseUrl(),
                        portalConfig.getXpsChannelId(),
                        xpsChannelSecret,
                        projectId + "",
                        salePlanInfoOut.getProductModle());
                if (gongxiaoResult != null && gongxiaoResult.getReturnCode() == ErrorCode.SUCCESS.getErrorCode()) {
                    int number = (int) gongxiaoResult.getData();
                    salePlanInfoOut1.setProductActualQuantity(number);
                }
                salePlanInfoOut1.setStartTime(salePlanInfoOut.getStartTime());
                salePlanInfoOut1.setEndTime(salePlanInfoOut.getEndTime());
                salePlanInfoOut1.setCreateTime(salePlanInfoOut.getCreateTime());
                salePlanInfoOuts.add(salePlanInfoOut1);
            }
            salePlanInfoOutPageInfo.setList(salePlanInfoOuts);
            logger.info("#traceId={}# [OUT] getSalePlanList success: ", rpcHeader.getTraceId());

            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), salePlanInfoOutPageInfo);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

    }

    @RequestMapping(value = "/getSalePlanDetail", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getSalePlanDetail(String salesPlanNo,
                                            HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        logger.info("#traceId={}# [IN][getPurchaseReturnDetail] params: salesPlanNo:{} ", salesPlanNo);
        if (!ValidationUtil.isNotEmpty(salesPlanNo)) {
            logger.error("#traceId={}# [OUT] getSalePlanDetail success: ", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        try {
            long projectId = portalUserInfo.getProjectId();

            PlanSaleServiceGrpc.PlanSaleServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(PlanSaleServiceGrpc.PlanSaleServiceBlockingStub.class);
            PlanSaleStructure.GetSalePlanDetailReq.Builder getSalePlanDetailReq
                    = PlanSaleStructure.GetSalePlanDetailReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setSalesPlanNo(salesPlanNo);
            PlanSaleStructure.GetSalePlanDetailResp resp = null;
            resp = rpcStub.getSalePlanDetail(getSalePlanDetailReq.build());
            PlanSaleStructure.SalePlanInfoOut salePlanInfoOut = resp.getSalePlanInfoOut();

            SalePlanInfoOut salePlanInfoOut1 = new SalePlanInfoOut();
            salePlanInfoOut1.setOrderStatus(salePlanInfoOut.getOrderStatus());
            salePlanInfoOut1.setSalesPlanNo(salePlanInfoOut.getSalesPlanNo());
            salePlanInfoOut1.setProjectId(salePlanInfoOut.getProjectId());
            salePlanInfoOut1.setProjectName(salePlanInfoOut.getProjectName());
            salePlanInfoOut1.setProductId(salePlanInfoOut.getProductId());
            salePlanInfoOut1.setProductCode(salePlanInfoOut.getProductModle());
            salePlanInfoOut1.setProductName(salePlanInfoOut.getProductName());
            salePlanInfoOut1.setGuidePrice(salePlanInfoOut.getGuidePrice());
            salePlanInfoOut1.setOnSaleQuantity(salePlanInfoOut.getOnSaleQuantity());
            salePlanInfoOut1.setAllocatedQuantity(salePlanInfoOut.getAllocatedQuantity());
            salePlanInfoOut1.setUnallocatedQuantity(salePlanInfoOut.getUnallocatedQuantity());
            salePlanInfoOut1.setProductActualQuantity(salePlanInfoOut.getProductActualQuantity());
            salePlanInfoOut1.setStartTime(salePlanInfoOut.getStartTime());
            salePlanInfoOut1.setEndTime(salePlanInfoOut.getEndTime());
            salePlanInfoOut1.setCreateTime(salePlanInfoOut.getCreateTime());

            logger.info("#traceId={}# [OUT] getSalePlanDetail success: ", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), salePlanInfoOut1);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 通过销售计划单号拿到该条销售计划信息
     *
     * @param salesPlanNo
     * @param request
     * @return
     */
    @RequestMapping(value = "/getPlanSaleInfo", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getPlanSaleInfo(String salesPlanNo,
                                          HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        logger.info("#traceId={}# [IN][getPlanSaleInfo] params: salesPlanNo:{} ", salesPlanNo);
        if ("".equals(salesPlanNo)) {
            logger.error("#traceId={}# [OUT] getPlanSaleInfo success: ", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        try {
            long projectId = portalUserInfo.getProjectId();

            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(portalConfig.getXpsChannelId() + "").build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
            String xpsChannelSecret = sourceChannel.getXpsChannelSecret();

            PlanSaleServiceGrpc.PlanSaleServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(PlanSaleServiceGrpc.PlanSaleServiceBlockingStub.class);
            PlanSaleStructure.GetSalePlanInfoReq.Builder getPlanInfoReq
                    = PlanSaleStructure.GetSalePlanInfoReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setSalesPlanNo(salesPlanNo);
            PlanSaleStructure.GetSalePlanInfoResp resp = null;
            resp = rpcStub.getSalePlanInfo(getPlanInfoReq.build());
            PlanSaleStructure.SaleEditInfo saleEditInfo1 = resp.getSaleEditInfo();
            SaleEditInfo saleEditInfo = new SaleEditInfo();
            saleEditInfo.setSalePlanNo(saleEditInfo1.getSalePlanNo());
            saleEditInfo.setProductId(saleEditInfo1.getProductId());
            saleEditInfo.setProductCode(saleEditInfo1.getProductModle());
            saleEditInfo.setProductName(saleEditInfo1.getProductName());
            saleEditInfo.setOnSaleQuantity(saleEditInfo1.getOnSaleQuantity());

            GongxiaoResult gongxiaoResult = XpsWarehouseManager.getproductQuqntity(portalConfig.getWarehouseUrl(),
                    portalConfig.getXpsChannelId(),
                    xpsChannelSecret,
                    projectId + "",
                    saleEditInfo1.getProductModle());
            if (gongxiaoResult != null && gongxiaoResult.getReturnCode() == ErrorCode.SUCCESS.getErrorCode()) {
                int number = (int) gongxiaoResult.getData();
                saleEditInfo.setInStockQuantity(number);
            }
            saleEditInfo.setAllocatedQuantity(saleEditInfo1.getAllocatedQuantity());
            saleEditInfo.setStartTime(DateUtil.dateToString(DateUtil.long2Date(saleEditInfo1.getStartTime())));
            saleEditInfo.setEndTime(DateUtil.dateToString(DateUtil.long2Date(saleEditInfo1.getEndTime())));

            logger.info("#traceId={}# [OUT] getPlanSaleInfo success: ", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), saleEditInfo);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 修改销售计划
     *
     * @param salesPlanNo    销售计划单号
     * @param onSaleQuantity 可售数量
     * @param startDate      开始日期
     * @param endDate        结束日期
     * @param request
     * @return
     */
    @RequestMapping(value = "/editPlanQuantity", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult editPlanQuantity(String salesPlanNo,
                                           int onSaleQuantity,
                                           String startDate,
                                           String endDate,
                                           HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        try {
            long projectId = portalUserInfo.getProjectId();

            logger.info("#traceId={}# [IN][editPlanQuantity] params: salesPlanNo:{}, onSaleQuantity:{}, startDate:{}, endDate:{} ",
                    rpcHeader.getTraceId(), salesPlanNo, onSaleQuantity, startDate, endDate);
            //1)数据校验
            PlanSaleServiceGrpc.PlanSaleServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(PlanSaleServiceGrpc.PlanSaleServiceBlockingStub.class);
            PlanSaleStructure.GetSalePlanDetailReq.Builder getPlanInfoReq
                    = PlanSaleStructure.GetSalePlanDetailReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setSalesPlanNo(salesPlanNo);
            PlanSaleStructure.GetSalePlanDetailResp resp = null;
            resp = rpcStub.getSalePlanDetail(getPlanInfoReq.build());
            PlanSaleStructure.SalePlanInfoOut salePlanInfoOut = resp.getSalePlanInfoOut();

            SalePlanInfoOut salePlanInfoOut1 = new SalePlanInfoOut();
            salePlanInfoOut1.setOrderStatus(salePlanInfoOut.getOrderStatus());
            salePlanInfoOut1.setSalesPlanNo(salePlanInfoOut.getSalesPlanNo());
            salePlanInfoOut1.setProjectId(salePlanInfoOut.getProjectId());
            salePlanInfoOut1.setProjectName(salePlanInfoOut.getProjectName());
            salePlanInfoOut1.setProductId(salePlanInfoOut.getProductId());
            salePlanInfoOut1.setProductCode(salePlanInfoOut.getProductModle());
            salePlanInfoOut1.setProductName(salePlanInfoOut.getProductName());
            salePlanInfoOut1.setGuidePrice(salePlanInfoOut.getGuidePrice());
            salePlanInfoOut1.setOnSaleQuantity(salePlanInfoOut.getOnSaleQuantity());
            salePlanInfoOut1.setAllocatedQuantity(salePlanInfoOut.getAllocatedQuantity());
            salePlanInfoOut1.setUnallocatedQuantity(salePlanInfoOut.getUnallocatedQuantity());
            salePlanInfoOut1.setProductActualQuantity(salePlanInfoOut.getProductActualQuantity());
            salePlanInfoOut1.setStartTime(salePlanInfoOut.getStartTime());
            salePlanInfoOut1.setEndTime(salePlanInfoOut.getEndTime());
            salePlanInfoOut1.setCreateTime(salePlanInfoOut.getCreateTime());

            int allocatedQuantity = salePlanInfoOut1.getAllocatedQuantity();
            if (onSaleQuantity == 0) {
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.AT_LEAST_ONE);
            }
            if (onSaleQuantity < allocatedQuantity) {
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.QUANTITY_VALIDATE);
            }
            boolean startDateNotNull = ValidationUtil.isNotEmpty(startDate, endDate);
            if (!startDateNotNull) {
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.DATE_NOT_NULL);
            }
            Date startTime = DateUtil.stringToDate(startDate, "yyyy-MM-dd");
            Date endTime = DateUtil.stringToDate(endDate, "yyyy-MM-dd");
            if (startTime.after(endTime)) {
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.NOT_REPEAT_START_END_DATE);
            }

            PlanSaleStructure.EditSalePlanReq editSalePlanReq = PlanSaleStructure.EditSalePlanReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setSalesPlanNo(salesPlanNo)
                    .setOnSaleQuantity(onSaleQuantity)
                    .setStartDate(startDate)
                    .setEndDate(endDate)
                    .build();
            PlanSaleStructure.EditSalePlanResp resp1 = null;
            resp1 = rpcStub.editSalePlan(editSalePlanReq);
            GongxiaoRpc.RpcResult rpcResult = resp1.getRpcResult();
            return GongxiaoResult.newResult(rpcResult.getReturnCode(), rpcResult.getMsg());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 更新销售计划数据
     *
     * @param salesPlanNo         销售计划单号
     * @param onSaleQuantity      可售数量
     * @param allocatedQuantity   已分配数量
     * @param unallocatedQuantity 未分配数量
     * @param request
     * @return
     */
    @RequestMapping(value = "/updatePlanQuantity", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult updatePlanQuantity(String salesPlanNo,
                                             int onSaleQuantity,
                                             int allocatedQuantity,
                                             int unallocatedQuantity,
                                             HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        try {
            long projectId = portalUserInfo.getProjectId();

            logger.info("#traceId={}# [IN][updatePlanQuantity] params: salesPlanNo:{}, onSaleQuantity:{}, allocatedQuantity:{}, unallocatedQuantity:{} ",
                    rpcHeader.getTraceId(), salesPlanNo, onSaleQuantity, allocatedQuantity, unallocatedQuantity);
            PlanSaleServiceGrpc.PlanSaleServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(PlanSaleServiceGrpc.PlanSaleServiceBlockingStub.class);
            PlanSaleStructure.UpdatePlanQuantityReq updatePlanQuantityReq = PlanSaleStructure.UpdatePlanQuantityReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setSalesPlanNo(salesPlanNo)
                    .setOnSaleQuantity(onSaleQuantity)
                    .setAllocatedQuantity(allocatedQuantity)
                    .setUnallocatedQuantity(unallocatedQuantity)
                    .setProjectId(projectId)
                    .build();
            PlanSaleStructure.UpdatePlanQuantityResp updatePlanQuantityResp = null;
            updatePlanQuantityResp = rpcStub.updatePlanQuantity(updatePlanQuantityReq);
            int number = updatePlanQuantityResp.getNumber();

            logger.info("#traceId={}# [OUT] updatePlanQuantity success: ", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), number);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 作废预售单
     *
     * @param planSaleOrderListString 须作废的预售单列表
     * @param request
     * @return
     */
    @RequestMapping("/cancelPlanOrder")
    @ResponseBody
    public GongxiaoResult cancelPlanOrder(String planSaleOrderListString,
                                          HttpServletRequest request) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        try {
            long projectId = portalUserInfo.getProjectId();

            boolean notEmpty = ValidationUtil.isNotEmpty(planSaleOrderListString);
            logger.info("#traceId={}# [IN][cancelPlanOrder] params: planSaleOrderListString={}",
                    rpcHeader.getTraceId(), planSaleOrderListString);
            if (!notEmpty) {
                gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.NOT_SELECT_DATA);
                return gongxiaoResult;
            }
            String[] planOrderArray = planSaleOrderListString.split(",");
            for (String planOrderNo : planOrderArray) {
                //取消预售单
                PlanSaleItemServiceGrpc.PlanSaleItemServiceBlockingStub rpcStub1 = RpcStubStore.getRpcStub(PlanSaleItemServiceGrpc.PlanSaleItemServiceBlockingStub.class);
                PlanSaleItemStructure.CancelPlanReq cancelPlanReq = PlanSaleItemStructure.CancelPlanReq.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setProjectId(projectId)
                        .setPlanSaleNo(planOrderNo)
                        .setProjectId(projectId)
                        .build();
                PlanSaleItemStructure.CancelPlanResp resp = null;
                resp = rpcStub1.cancelPlan(cancelPlanReq);
                //取消预售明细
                PlanSaleServiceGrpc.PlanSaleServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(PlanSaleServiceGrpc.PlanSaleServiceBlockingStub.class);
                PlanSaleStructure.CancelPlanSaleOrderReq cancelPlanSaleOrderReq = PlanSaleStructure.CancelPlanSaleOrderReq.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setProjectId(projectId)
                        .setSalesPlanNo(planOrderNo)
                        .build();
                PlanSaleStructure.CancelPlanSaleOrderResp resp1 = null;
                resp1 = rpcStub.cancelPlanSaleOrder(cancelPlanSaleOrderReq);
            }

            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.SUCCESS);

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;

    }

}
