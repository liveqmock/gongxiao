package com.yhglobal.gongxiao.plansale.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.protal.PortalConfig;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorServiceGrpc;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.plansale.controller.vo.Customer;
import com.yhglobal.gongxiao.plansale.controller.vo.SaleItemEditInfo;
import com.yhglobal.gongxiao.plansale.controller.vo.SalePlanItemBo;
import com.yhglobal.gongxiao.plansale.controller.vo.SalePlanItemInfo;
import com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemServiceGrpc;
import com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure;
import com.yhglobal.gongxiao.plansale.microservice.PlanSaleServiceGrpc;
import com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.ValidationUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.utils.YhPortalTraceIdGenerator;
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
 * 销售管理 > 预售管理 在某一条计划后面点击 "维护"页面的相关接口
 *
 * @author: 陈浩
 **/
@Controller
@RequestMapping("/planSaleItem")
public class PlanSaleItemController {
    private static Logger logger = LoggerFactory.getLogger(PlanSaleItemController.class);

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;


    /**
     *
     * 销售管理 > 预售管理 点击 "维护"， 在维护页面点击"新增" 则会进入该接口拉取 该项目客户列表
     *
     * @param customerId   客户ID
     * @param customerName 客户名称
     * @param request
     * @return
     */
    @RequestMapping(value = "/getCustomerList", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getCustomerList(String customerId,
                                          String customerName,
                                          int pageNumber,
                                          int pageSize,
                                          HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        long projectId = portalUserInfo.getProjectId();
        logger.info("#traceId={}# [IN][getCustomerList] params: projectId:{}, customerId:{}, customerName:{}  ", rpcHeader.getTraceId(), projectId, customerId, customerName);

        DistributorServiceGrpc.DistributorServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(DistributorServiceGrpc.DistributorServiceBlockingStub.class);
        DistributorStructure.SelectDistributorBusinessByConditionReq selectDistributorBusinessByConditionReq = DistributorStructure.SelectDistributorBusinessByConditionReq.newBuilder()
                .setProjectId(projectId)
                .setDistributorBusinessId(customerId != null && !"".equals(customerId) ? Long.parseLong(customerId) : 0)
                .setDistributorName(customerName)
                .setPageNumber(pageNumber)
                .setPageSize(pageSize)
                .build();
        DistributorStructure.SelectDistributorBusinessByConditionResp selectDistributorBusinessByConditionResp = rpcStub.selectDistributorBusinessByCondition(selectDistributorBusinessByConditionReq);
        List<DistributorStructure.DistributorBusiness> distributorBusinessList = selectDistributorBusinessByConditionResp.getDistributorBusinessList();
        List<Customer> customerList = new ArrayList<>();
        for ( DistributorStructure.DistributorBusiness distributorBusiness : distributorBusinessList) {
            Customer customer = new Customer();
            customer.setCustomerId(distributorBusiness.getDistributorBusinessId() + "");
            customer.setCustomerName(distributorBusiness.getDistributorName());
            customerList.add(customer);
        }
        PageInfo<Customer> customerPageInfo =  new PageInfo<>(customerList);
        customerPageInfo.setTotal(selectDistributorBusinessByConditionResp.getTotal()); //设置总的AD数量

        logger.info("#traceId={}# [OUT] getCustomerList success: ", rpcHeader.getTraceId());
        return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), customerPageInfo);
    }

    /**
     * 销售管理 > 预售管理 点击 "维护"， 在维护页上 会拉取该接口 获得预售明细列表
     *
     * 通过预销单号,客户ID,客户名称获取预销列表
     *
     * @param salesPlanNo  销售计划单号
     * @param customerId   客户ID
     * @param customerName 客户名称
     * @param request
     * @return
     */
    @RequestMapping(value = "/getPlanSaleItemList", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getPlanSaleItemList(String salesPlanNo,
                                              String customerId,
                                              String customerName,
                                              int pageNumber,
                                              int pageSize,
                                              HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        long projectId = portalUserInfo.getProjectId();
        logger.info("#traceId={}# [IN][getPlanSaleItemList] params: salesPlanNo:{}, customerId:{}, customerName:{}  ", salesPlanNo, customerId, customerName);
        if ("".equals(salesPlanNo)) {
            logger.error("#traceId={}# [OUT] fail to getPlanSaleItemList:", traceId);
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        try {
            PlanSaleItemStructure.SelectSalePlanItemListReq selectSalePlanItemListReq = PlanSaleItemStructure.SelectSalePlanItemListReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setSalesPlanNo(salesPlanNo)
                    .setCustomerId(customerId)
                    .setCustomerName(customerName)
                    .setPageNumber(pageNumber)
                    .setPageSize(pageSize)
                    .setProjectId(projectId)
                    .build();
            PlanSaleItemStructure.SelectSalePlanItemListResp resp = null;
            PlanSaleItemServiceGrpc.PlanSaleItemServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(PlanSaleItemServiceGrpc.PlanSaleItemServiceBlockingStub.class);
            resp=rpcStub.selectSalePlanItemList(selectSalePlanItemListReq);

            List<PlanSaleItemStructure.SalePlanItemBo> salePlanItemBoListList = resp.getSalePlanItemBoListList();
            PageInfo<SalePlanItemBo> salePlanItemList = new PageInfo<>();
            List<SalePlanItemBo> salePlanItemBoList = new ArrayList<>();
            for (PlanSaleItemStructure.SalePlanItemBo salePlanItemBo:salePlanItemBoListList){
                SalePlanItemBo salePlanItemBo1 = new SalePlanItemBo();
                salePlanItemBo1.setItemId(salePlanItemBo.getItemId());
                salePlanItemBo1.setSalesPlanNo(salePlanItemBo.getSalesPlanNo());
                salePlanItemBo1.setCustomerId(salePlanItemBo.getCustomerId());
                salePlanItemBo1.setCustomerName(salePlanItemBo.getCustomerName());
                salePlanItemBo1.setOnSaleQuantity(salePlanItemBo.getOnSaleQuantity());
                salePlanItemBo1.setSaleOccupationQuantity(salePlanItemBo.getSaleOccupationQuantity());
                salePlanItemBo1.setSoldQuantity(salePlanItemBo.getSoldQuantity());
                salePlanItemBo1.setUnallocatedQuantity(salePlanItemBo.getUnallocatedQuantity());
                salePlanItemBo1.setCurrencyCode(salePlanItemBo.getCurrencyCode());
                salePlanItemBo1.setCustomerName(salePlanItemBo.getCustomerName());
                salePlanItemBo1.setGuidePrice(salePlanItemBo.getGuidePrice());
                salePlanItemBo1.setBrandPrepaidDiscount(salePlanItemBo.getBrandPrepaidDiscount());
                salePlanItemBo1.setYhPrepaidDiscount(salePlanItemBo.getYhPrepaidDiscount());
                salePlanItemBo1.setWholesalePrice(salePlanItemBo.getWholesalePrice());
                salePlanItemBo1.setStartTime(DateUtil.long2Date(salePlanItemBo.getStartTime()));
                salePlanItemBo1.setEndTime(DateUtil.long2Date(salePlanItemBo.getEndTime()));
                salePlanItemBo1.setLastUpdateTime(DateUtil.long2Date(salePlanItemBo.getLastUpdateTime()));
                salePlanItemBo1.setItemStatus(salePlanItemBo.getItemStatus());
                salePlanItemBoList.add(salePlanItemBo1);
            }
            salePlanItemList.setList(salePlanItemBoList);
            salePlanItemList.setTotal(resp.getTotal());
            logger.info("#traceId={}# [OUT] getPlanSaleItemList success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), salePlanItemList);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

    }

    /**
     * TODO：待确定使用场景
     *
     * 获取某一销售计划单的剩余可售总数
     *
     * @param salesPlanNo 销售计划单号
     * @param request
     * @return
     */
    @RequestMapping(value = "/getOnSaleQuantity", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getOnSaleQuantity(String salesPlanNo,
                                            HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        long projectId = portalUserInfo.getProjectId();

        try {
            logger.info("#traceId={}# [IN][getOnSaleQuantity] params: salesPlanNo:{}   ", salesPlanNo);
            PlanSaleServiceGrpc.PlanSaleServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(PlanSaleServiceGrpc.PlanSaleServiceBlockingStub.class);
            PlanSaleStructure.GetUnHandleQuantityReq getUnHandleQuantityReq = PlanSaleStructure.GetUnHandleQuantityReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setSalesPlanNo(salesPlanNo)
                    .build();
            PlanSaleStructure.GetUnHandleQuantityResp resp = null;
            resp = rpcStub.getUnHandleQuantity(getUnHandleQuantityReq);
            int couldAllocatedTotalQuantity = resp.getNumber();
            logger.info("#traceId={}# [OUT] getOnSaleQuantity success: ", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), couldAllocatedTotalQuantity);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }


    /**
     * 销售管理 > 预售管理 点击 "维护"， 在维护页上点击"新增，添加客户后提交 会进入该接口来添加计划销售明细信息
     *
     * 注：会依赖原销售计划单
     *
     * @param salePlanItemJson 客户可售计划json
     * @param request
     * @return
     */
    @RequestMapping(value = "/putCustomerSalePlan", method = RequestMethod.POST)
    @ResponseBody
    public GongxiaoResult putCustomerSalePlan(String salePlanItemJson,
                                              HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        try {
            long projectId = portalUserInfo.getProjectId();
            logger.info("#traceId={}# [IN][putCustomerSalePlan] params: #salePlanItemJson:{}   ", salePlanItemJson);
            //将货品列表json数组的字符串转换为List
            ArrayList<SalePlanItemInfo> purchaseItemInfoList
                    = JSON.parseObject(salePlanItemJson, new TypeReference<ArrayList<SalePlanItemInfo>>() {});
            //数据校验 start
            PlanSaleServiceGrpc.PlanSaleServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(PlanSaleServiceGrpc.PlanSaleServiceBlockingStub.class);
            PlanSaleItemServiceGrpc.PlanSaleItemServiceBlockingStub rpcStub1 = RpcStubStore.getRpcStub(PlanSaleItemServiceGrpc.PlanSaleItemServiceBlockingStub.class);

            PlanSaleStructure.GetSalePlanDetailReq getSalePlanDetailReq = PlanSaleStructure.GetSalePlanDetailReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setSalesPlanNo(purchaseItemInfoList.get(0).getSalesPlanNo())
                    .build();
            PlanSaleStructure.GetSalePlanDetailResp resp = null;
            resp = rpcStub.getSalePlanDetail(getSalePlanDetailReq);
            PlanSaleStructure.SalePlanInfoOut salePlanInfoOut = resp.getSalePlanInfoOut();

            String orderStartTime = salePlanInfoOut.getStartTime();
            String orderEndTime = salePlanInfoOut.getEndTime();
            Date orderStartDate = DateUtil.stringToDate(orderStartTime, "yyyy-MM-dd");
            Date orderEndDate = DateUtil.stringToDate(orderEndTime, "yyyy-MM-dd");
            int totalQuantity = 0;//各个客户累计的"分配数量"
            for (SalePlanItemInfo salePlanItemInfo : purchaseItemInfoList) {
                totalQuantity += salePlanItemInfo.getOnSaleQuantity();
                //1. 校验时间是否为空
                String startTime = salePlanItemInfo.getStartTime();
                String endTime = salePlanItemInfo.getEndTime();
                boolean notEmpty = ValidationUtil.isNotEmpty(startTime, endTime);
                if (!notEmpty) {
                    return new GongxiaoResult(ErrorCode.DATE_NOT_NULL.getErrorCode(), ErrorCode.DATE_NOT_NULL.getMessage());
                }
                Date startDate = DateUtil.stringToDate(startTime, "yyyy-MM-dd");
                Date endDate = DateUtil.stringToDate(endTime, "yyyy-MM-dd");
                if (startDate.after(endDate)) {
                    return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.NOT_REPEAT_START_END_DATE);
                }
                //2. 校验客户预售单的有效期是否超出货品有效期

                if (startDate.before(orderStartDate) || endDate.after(orderEndDate)) {
                    return new GongxiaoResult(ErrorCode.DATE_OUT_EXPIRE.getErrorCode(), ErrorCode.DATE_OUT_EXPIRE.getMessage());
                }
                //3. 校验同货品在同一有效时间是否分配给客户多次
                PlanSaleItemStructure.GetProductCustomerReq getProductCustomerReq = PlanSaleItemStructure.GetProductCustomerReq.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setProjectId(salePlanItemInfo.getProjectId())
                        .setDistributorId(salePlanItemInfo.getCustomerId())
                        .setProductId(salePlanItemInfo.getProductId())
                        .setStartDate(DateUtil.getTime(salePlanItemInfo.getEndTime(),"yyyy-MM-dd"))
                        .setEndDate(DateUtil.getTime(salePlanItemInfo.getEndTime(),"yyyy-MM-dd"))
                        .build();
                PlanSaleItemStructure.GetProductCustomerResp resp1 =rpcStub1.getProductCustomer(getProductCustomerReq);
                List<PlanSaleItemStructure.SalesPlanItem> salesPlanItemListList = resp1.getSalesPlanItemListList();

                for (PlanSaleItemStructure.SalesPlanItem salesPlanItem : salesPlanItemListList) {
                    Date startTimeCustomer = DateUtil.long2Date(salesPlanItem.getStartTime());
                    Date endTimeCustomer = DateUtil.long2Date(salesPlanItem.getEndTime());
                    if (startDate.getTime() >= startTimeCustomer.getTime() && startDate.getTime() <= endTimeCustomer.getTime()
                            || endDate.getTime() >= startTimeCustomer.getTime() && endDate.getTime() <= endTimeCustomer.getTime()) {
                        return new GongxiaoResult(ErrorCode.EXPIRE_DATE_CUSTOMER_REPEAT.getErrorCode(), ErrorCode.EXPIRE_DATE_CUSTOMER_REPEAT.getMessage());

                    }
                }
                //4. 校验是否超出可下单货品可下单数量
                int unallocatedQuantity = salePlanInfoOut.getUnallocatedQuantity();
                if ((unallocatedQuantity < totalQuantity) || totalQuantity < 0) {
                    return new GongxiaoResult(ErrorCode.OVERTAKE_MAX_ALLOCATE_NUMBER.getErrorCode(), ErrorCode.OVERTAKE_MAX_ALLOCATE_NUMBER.getMessage());
                }
            }
            //数据校验结束
            PlanSaleItemStructure.CreateSalePlanItemReq.Builder createSalePlanItemReq = PlanSaleItemStructure.CreateSalePlanItemReq.newBuilder();
            for (SalePlanItemInfo salePlanItemInfo:purchaseItemInfoList){
                PlanSaleItemStructure.SalePlanItemInfo salePlanItemInfo1 = PlanSaleItemStructure.SalePlanItemInfo.newBuilder()
                        .setProjectId(salePlanItemInfo.getProjectId())
                        .setProjectName(salePlanItemInfo.getProjectName())
                        .setProductId(salePlanItemInfo.getProductId())
                        .setProductModel(salePlanItemInfo.getProductCode())
                        .setProductName(salePlanItemInfo.getProductName())
                        .setSalesPlanNo(salePlanItemInfo.getSalesPlanNo())
                        .setOnSaleQuantity(salePlanItemInfo.getOnSaleQuantity())
                        .setCustomerId(salePlanItemInfo.getCustomerId())
                        .setCustomerName(salePlanItemInfo.getCustomerName())
                        .setSaleOccupationQuantity(salePlanItemInfo.getSaleOccupationQuantity())
                        .setSoldQuantity(salePlanItemInfo.getSoldQuantity())
                        .setUnallocatedQuantity(salePlanItemInfo.getUnallocatedQuantity())
                        .setBrandPrepaidUnit(salePlanItemInfo.getBrandPrepaidUnit())
                        .setBrandPrepaidDiscount(salePlanItemInfo.getBrandPrepaidDiscount())
                        .setYhPrepaidUnit(salePlanItemInfo.getYhPrepaidUnit())
                        .setYhPrepaidDiscount(salePlanItemInfo.getYhPrepaidDiscount())
                        .setStartTime(salePlanItemInfo.getStartTime())
                        .setEndTime(salePlanItemInfo.getEndTime())
                        .build();
                createSalePlanItemReq.addSalePlanInfoIns(salePlanItemInfo1);
            }
            createSalePlanItemReq.setRpcHeader(rpcHeader);
            createSalePlanItemReq.setProjectId(projectId);
            PlanSaleItemStructure.CreateSalePlanItemResp salePlanItem = rpcStub1.createSalePlanItem(createSalePlanItemReq.build()); //新增AD配额
            int number = salePlanItem.getNumber();
            logger.info("#traceId={}# [OUT] putCustomerSalePlan success: ", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), number);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     *
     * 销售管理 > 预售管理 点击 "维护"， 在维护页上，点击"编辑"进入弹框，则会进入该接口来拉取该条明细的信息
     * 编辑获取明细信息
     *
     * @param itemId  明细ID
     * @param request
     * @return
     */
    @RequestMapping(value = "/getEditItemInfo", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getEditItemInfo(String itemId,
                                          HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        PlanSaleItemServiceGrpc.PlanSaleItemServiceBlockingStub rpcStub1 = RpcStubStore.getRpcStub(PlanSaleItemServiceGrpc.PlanSaleItemServiceBlockingStub.class);
        long projectId = portalUserInfo.getProjectId();

        logger.info("#traceId={}# [IN][getEditItemInfo] params: itemId:{}   ", itemId);
        if ("".equals(itemId)) {
            logger.error("#traceId={}# [OUT] fail to getEditItemInfo:", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        try {
            PlanSaleItemStructure.GetEditItemInfoReq getEditItemInfoReq = PlanSaleItemStructure.GetEditItemInfoReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setItemId(itemId)
                    .build();
            PlanSaleItemStructure.GetEditItemInfoResp editItemInfo1 = rpcStub1.getEditItemInfo(getEditItemInfoReq);
            SaleItemEditInfo editItemInfo = new SaleItemEditInfo();
            PlanSaleItemStructure.SaleItemEditInfo saleItemEditInfo = editItemInfo1.getSaleItemEditInfo();
            editItemInfo.setItemId(saleItemEditInfo.getItemId());
            editItemInfo.setCustomerId(saleItemEditInfo.getCustomerId());
            editItemInfo.setCustomerName(saleItemEditInfo.getCustomerName());
            editItemInfo.setRemainSaleAmount(saleItemEditInfo.getRemainSaleAmount());
            editItemInfo.setUnallocatedQuantity(saleItemEditInfo.getUnallocatedQuantity());
            editItemInfo.setAdjustNumber(saleItemEditInfo.getAdjustNumber());
            editItemInfo.setGuidePrice(saleItemEditInfo.getGuidePrice());
            editItemInfo.setSaleGuidePrice(saleItemEditInfo.getSaleGuidePrice());
            editItemInfo.setBrandPrepaidUnit(saleItemEditInfo.getBrandPrepaidUnit());
            editItemInfo.setBrandPrepaidDiscount(saleItemEditInfo.getBrandPrepaidDiscount());
            editItemInfo.setYhPrepaidDiscount(saleItemEditInfo.getYhPrepaidDiscount());
            editItemInfo.setYhPrepaidUnit(saleItemEditInfo.getYhPrepaidUnit());
            editItemInfo.setWholesalePrice(saleItemEditInfo.getWholesalePrice());
            editItemInfo.setStartTime(DateUtil.long2Date(saleItemEditInfo.getStartTime()));
            editItemInfo.setEndTime(DateUtil.long2Date(saleItemEditInfo.getEndTime()));

            logger.info("#traceId={}# [OUT] getEditItemInfo success: ", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), editItemInfo);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 销售管理 > 预售管理 点击 "维护"， 在维护页上，点击"编辑"进入弹框页，进行修改后，点击提交 则会进入该接口来更新该预售明细
     *
     * 编辑明细信息
     *
     * @param adjustNumber         调整数量
     * @param itemId               客户计划货销售单号
     * @param brandPrepaidUnit     品牌商单位代垫
     * @param brandPrepaidDiscount 品牌商代垫折扣
     * @param yhPrepaidUnit        越海单位代垫
     * @param yhPrepaidDiscount    越海代垫折扣
     * @param startDate            开始日期
     * @param endDate              结束日期
     * @return
     * @aram adjustNumber 调整数量
     */
    @RequestMapping(value = "/editItemInfo", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult editItemInfo(String itemId,
                                       int adjustNumber,
                                       double brandPrepaidUnit,
                                       double brandPrepaidDiscount,
                                       double yhPrepaidUnit,
                                       double yhPrepaidDiscount,
                                       String startDate,
                                       String endDate,
                                       HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        try {
            long projectId = portalUserInfo.getProjectId();

            logger.info("#traceId={}# [IN][editItemInfo] params: itemId:{}, adjustNumber:{}, brandPrepaidUnit:{}, brandPrepaidDiscount:{} " +
                            "#yhPrepaidUnit:{}, yhPrepaidDiscount:{}, startDate:{}, endDate:{} ",
                    itemId, adjustNumber, brandPrepaidUnit, brandPrepaidDiscount, yhPrepaidUnit, yhPrepaidDiscount, startDate, endDate);
            boolean dateNotNull = ValidationUtil.isNotEmpty(startDate, endDate);
            if (!dateNotNull) {
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.DATE_NOT_NULL);
            }
            Date startTime = DateUtil.stringToDate(startDate, "yyyy-MM-dd");
            Date endTime = DateUtil.stringToDate(endDate, "yyyy-MM-dd");
            if (startTime.after(endTime)) {
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.NOT_REPEAT_START_END_DATE);
            }
            PlanSaleItemStructure.GetEditItemInfoReq getEditItemInfoReq = PlanSaleItemStructure.GetEditItemInfoReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setItemId(itemId)
                    .build();
            PlanSaleItemServiceGrpc.PlanSaleItemServiceBlockingStub rpcStub1 = RpcStubStore.getRpcStub(PlanSaleItemServiceGrpc.PlanSaleItemServiceBlockingStub.class);
            PlanSaleItemStructure.GetEditItemInfoResp editItemInfo1 = rpcStub1.getEditItemInfo(getEditItemInfoReq);
            PlanSaleItemStructure.SaleItemEditInfo editItemInfo = editItemInfo1.getSaleItemEditInfo();
            int itemAmount = editItemInfo.getRemainSaleAmount();//客户可销售总数
            int orderQuantity = editItemInfo.getUnallocatedQuantity();//货品剩余销售总数
            if (adjustNumber > orderQuantity) { //上调超过未处理货品总数
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.SALE_ITEM_UP_ADJUST);
            }
            if (-adjustNumber > itemAmount) { //下调则前端会传递负值
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.SALE_ITEM_DOWN_ADJUST);
            }
            PlanSaleItemStructure.EditSaleItemReq editSaleItemReq = PlanSaleItemStructure.EditSaleItemReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setItemId(itemId)
                    .setAdjustNumber(adjustNumber)
                    .setBrandPrepaidDiscount(brandPrepaidDiscount)
                    .setBrandPrepaidUnit(brandPrepaidDiscount)
                    .setYhPrepaidDiscount(yhPrepaidDiscount)
                    .setYhPrepaidUnit(yhPrepaidUnit)
                    .setStartDate(DateUtil.getTime(startTime))
                    .setEndDate(DateUtil.getTime(endTime))
                    .build();
            PlanSaleItemStructure.EditSaleItemResp editSaleItemResp = rpcStub1.editSaleItem(editSaleItemReq);
            int number = editSaleItemResp.getNumber();
            logger.info("#traceId={}# [OUT] editItemInfo success: ", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), number);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

    }

    /**
     * 销售管理 > 预售管理 点击 "维护"， 在维护页上，选中一条或多条预售明细后，点击作废 则会进入该接口作废该明细
     *
     * 注：在作废的时候，该预售可能已有销售了部分的数量，此时应释放剩余为销售的配额
     *
     * @param itemIdJson 客户销售计划ID串
     * @param request
     * @return
     */
    @RequestMapping(value = "/cancelPlanSaleItemOrder", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult cancelPlanSaleItemOrder(String itemIdJson, HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        try {
            long projectId = portalUserInfo.getProjectId();

            logger.info("#traceId={}# [IN][cancelPlanSaleItemOrder] params: #itemId:{}  ", itemIdJson);
            PlanSaleItemServiceGrpc.PlanSaleItemServiceBlockingStub rpcStub1 = RpcStubStore.getRpcStub(PlanSaleItemServiceGrpc.PlanSaleItemServiceBlockingStub.class);
            boolean notEmpty = ValidationUtil.isNotEmpty(itemIdJson);
            if (!notEmpty) {
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.NOT_SELECT_DATA);
            }
            String[] itemIdArray = itemIdJson.split(",");
            int cancleQuantity = 0;
            String salePlanNo = "";
            //变更客户预售明细
            for (String itemId : itemIdArray) {
                PlanSaleItemStructure.CancelPlanItemReq cancelPlanItemReq = PlanSaleItemStructure.CancelPlanItemReq.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setProjectId(projectId)
                        .setItemId(itemId)
                        .build();
                PlanSaleItemStructure.CancelPlanItemResp cancelPlanItemResp = rpcStub1.cancelPlanItem(cancelPlanItemReq);

                PlanSaleItemStructure.GetOnSaleQuantityReq getOnSaleQuantityReq = PlanSaleItemStructure.GetOnSaleQuantityReq.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setProjectId(projectId)
                        .setItemId(itemId)
                        .build();
                PlanSaleItemStructure.GetOnSaleQuantityResp getOnSaleQuantityResp = rpcStub1.getOnSaleQuantity(getOnSaleQuantityReq);
                PlanSaleItemStructure.SalesPlanItem salesPlanItem = getOnSaleQuantityResp.getSalesPlanItem();
                int unallocatedQuantity = salesPlanItem.getUnallocatedQuantity();//获得未分配数量的额度 并累加
                cancleQuantity += (unallocatedQuantity);
                salePlanNo = salesPlanItem.getSalesPlanNo();
            }
            //变更订单预售明细
            PlanSaleServiceGrpc.PlanSaleServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(PlanSaleServiceGrpc.PlanSaleServiceBlockingStub.class);
            PlanSaleStructure.UpdateReturnSaleQuantityReq updateReturnSaleQuantityReq = PlanSaleStructure.UpdateReturnSaleQuantityReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setSalePlanNo(salePlanNo)
                    .setChangeQuantity(cancleQuantity)
                    .build();
            PlanSaleStructure.UpdateReturnSaleQuantityResp updateReturnSaleQuantityResp = rpcStub.updateReturnSaleQuantity(updateReturnSaleQuantityReq); //释放未分配的数量
            logger.info("#traceId={}# [OUT] cancelPlanSaleItemOrder success: ", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), "");
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

    }


}
