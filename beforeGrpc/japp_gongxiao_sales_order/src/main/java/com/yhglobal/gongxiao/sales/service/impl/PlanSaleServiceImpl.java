package com.yhglobal.gongxiao.sales.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.foundation.product.price.model.ProductPrice;
import com.yhglobal.gongxiao.foundation.product.price.service.ProductPriceService;
import com.yhglobal.gongxiao.foundation.project.model.Project;
import com.yhglobal.gongxiao.foundation.project.service.ProjectService;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.inventory.service.ProductInventoryService;
import com.yhglobal.gongxiao.sales.dao.SalePlanDao;
import com.yhglobal.gongxiao.sales.model.plan.model.SalesPlan;
import com.yhglobal.gongxiao.sales.model.plan.dto.SaleEditInfo;
import com.yhglobal.gongxiao.sales.model.plan.dto.SalePlanInfoIn;
import com.yhglobal.gongxiao.sales.model.plan.dto.SalePlanInfoOut;
import com.yhglobal.gongxiao.sales.service.PlanSaleService;
import com.yhglobal.gongxiao.sales.status.SalePlanStatus;
import com.yhglobal.gongxiao.util.CommonUtil;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.DoubleScale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 销售计划
 *
 * @author: 陈浩
 * @create: 2018-03-08 19:58
 **/
@Service(timeout = 20000)
public class PlanSaleServiceImpl implements PlanSaleService {
    private static Logger logger = LoggerFactory.getLogger(PlanSaleServiceImpl.class);
    @Reference
    ProductInventoryService productInventoryService;
    @Reference
    ProjectService projectService;
    @Reference
    ProductService productService;


    @Autowired
    SalePlanDao salePlanDao;

    @Override
    public int createSalePlan(RpcHeader rpcHeader, ArrayList<SalePlanInfoIn> salePlanInfoOuts) {
        logger.info("#traceId={}# [IN][insertSalePlanInfo] params: ", rpcHeader.traceId);

        try {
            String format = "yyyy-MM-dd";
            int category = salePlanInfoOuts.size();
            int i = 0;
            for (SalePlanInfoIn salePlanInfoIn : salePlanInfoOuts) {
                Project project = projectService.getByProjectId(rpcHeader, salePlanInfoIn.getProjectId() + "");
                ProductBasic productBasic = productService.getByProductId(rpcHeader, salePlanInfoIn.getProductId() + "");
                SalesPlan salesPlan = new SalesPlan();
                String oderNumber = DateTimeIdGenerator.nextId(BizNumberType.SALE_PLANE_ORDER_NO);
                salesPlan.setSalesPlanNo(oderNumber);
                salesPlan.setSalesPlanStatus((byte) SalePlanStatus.INIT.getStatus());
                salesPlan.setProjectId(salePlanInfoIn.getProjectId());
                salesPlan.setProjectName(project.getProjectName());
                salesPlan.setProductId(productBasic.getId());
                salesPlan.setProductCode(productBasic.getProductCode());
                salesPlan.setProductName(productBasic.getProductName());
                salesPlan.setGuidePrice( productBasic.getGuidePrice());
                salesPlan.setSaleGuidePrice(productBasic.getSaleGuidePrice());
                salesPlan.setOnSaleQuantity(salePlanInfoIn.getOnSaleQuantity());
                salesPlan.setAllocatedQuantity(0);
                salesPlan.setUnallocatedQuantity(salePlanInfoIn.getOnSaleQuantity());
                salesPlan.setProductCategory((byte) category);
                Date startDate = salePlanInfoIn.getStartTime() != null ? CommonUtil.StringToDateWithFormat(salePlanInfoIn.getStartTime(), format) : null;
                Date endDate = salePlanInfoIn.getEndTime() != null ? CommonUtil.StringToDateWithFormat(salePlanInfoIn.getEndTime(), format) : null;
                salesPlan.setStartTime(startDate);
                salesPlan.setEndTime(endDate);
                salesPlan.setCreatedById(Long.parseLong(rpcHeader.getUid()));
                salesPlan.setCreatedByName(rpcHeader.getUsername());
                salesPlan.setCreateTime(new Date());
                int i1 = salePlanDao.insertSalePlane(salesPlan);
                i += i1;
            }
            logger.info("#traceId={}# [OUT] insertSalePlanInfo success: ", rpcHeader.traceId);
            return i;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public PageInfo<SalePlanInfoOut> selectSalePlanListByProject(RpcHeader rpcHeader,
                                                                 String projectId,
                                                                 String productCode,
                                                                 String productName,
                                                                 String createTime,
                                                                 int pageNumber,
                                                                 int pageSize) {
        logger.info("#traceId={}# [IN][selectSalePlanListByProject] params: #projectId={} #productCode={} #productName={} #createTime={} ",
                rpcHeader.traceId, projectId, productCode, productName, createTime);
        try {

            PageHelper.startPage(pageNumber, pageSize);
            List<SalesPlan> salesPlans = salePlanDao.selectSalePlanListByProject(projectId, productCode, productName, createTime);
            PageInfo<SalesPlan> ProductPriceVoPage = new PageInfo<SalesPlan>(salesPlans);

            List<SalePlanInfoOut> salePlanInfoOuts = new ArrayList<>();
            //:todo 获取实时库存应只做一次接口调用
            for (SalesPlan salesPlan : salesPlans) {
                int actualQuantity = productInventoryService.selectQuantityByProjectIdAndProductCode(rpcHeader, salesPlan.getProjectId() + "", salesPlan.getProductCode());
                SalePlanInfoOut salePlanInfoOut = new SalePlanInfoOut();
                salePlanInfoOut.setSalesPlanNo(salesPlan.getSalesPlanNo());
                salePlanInfoOut.setOrderStatus(salesPlan.getSalesPlanStatus());
                salePlanInfoOut.setProjectId(salesPlan.getProjectId());
                salePlanInfoOut.setProjectName(salesPlan.getProjectName());
                salePlanInfoOut.setProductId(salesPlan.getProductId());
                salePlanInfoOut.setProductCode(salesPlan.getProductCode());
                salePlanInfoOut.setProductName(salesPlan.getProductName());
                salePlanInfoOut.setGuidePrice(DoubleScale.keepSixBits(salesPlan.getGuidePrice()));
                salePlanInfoOut.setSaleGuidePrice(DoubleScale.keepSixBits(salesPlan.getSaleGuidePrice()));
                salePlanInfoOut.setOnSaleQuantity(salesPlan.getOnSaleQuantity());
                salePlanInfoOut.setAllocatedQuantity(salesPlan.getAllocatedQuantity());
                salePlanInfoOut.setUnallocatedQuantity(salesPlan.getUnallocatedQuantity());
                salePlanInfoOut.setProductActualQuantity(actualQuantity);
                salePlanInfoOut.setStartTime(DateUtil.shortDataToFXString(salesPlan.getStartTime()));
                salePlanInfoOut.setEndTime(DateUtil.shortDataToFXString(salesPlan.getEndTime()));
                salePlanInfoOut.setCreateTime(DateUtil.longDateToFXString(salesPlan.getCreateTime()));
                salePlanInfoOuts.add(salePlanInfoOut);
            }
            PageInfo<SalePlanInfoOut> salePlanInfoOutPageInfo = new PageInfo<SalePlanInfoOut>();
            salePlanInfoOutPageInfo.setList(salePlanInfoOuts);
            salePlanInfoOutPageInfo.setTotal(ProductPriceVoPage.getTotal());
            logger.info("#traceId={}# [OUT] selectSalePlanListByProject success: ", rpcHeader.traceId, salePlanInfoOuts.size());
            return salePlanInfoOutPageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    public int updatePlanQuantity(RpcHeader rpcHeader,
                                  String salesPlanNo,
                                  int onSaleQuantity,
                                  int allocatedQuantity,
                                  int unallocatedQuantity) {
        logger.info("#traceId={}# [IN][updatePlanQuantity] params: salesPlanNo={}, #onSaleQuantity={}, #allocatedQuantity={}, #unallocatedQuantity={} ",
                rpcHeader.traceId, salesPlanNo, onSaleQuantity, allocatedQuantity, unallocatedQuantity);
        try {
            int updateQuantity = salePlanDao.updateProductQuantity(salesPlanNo, onSaleQuantity, allocatedQuantity, unallocatedQuantity);
            logger.info("#traceId={}# [OUT] updatePlanQuantity success: ", rpcHeader.traceId);
            return updateQuantity;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int getUnHandleQuantity(RpcHeader rpcHeader, String salesPlanNo) {
        logger.info("#traceId={}# [IN][getSaleQuantity] params: salesPlanNo={}  ", rpcHeader.traceId, salesPlanNo);
        try {
            SalesPlan salePlan = salePlanDao.getSalePlanNo(salesPlanNo);
            int onSaleQuantity = salePlan.getUnallocatedQuantity();
            logger.info("#traceId={}# [OUT] getSaleQuantity success: ", rpcHeader.traceId);
            return onSaleQuantity;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    public SaleEditInfo getSalePlanInfo(RpcHeader rpcHeader, String salesPlanNo) {
        logger.info("#traceId={}# [IN][getSalePlanInfo] params: salesPlanNo={}  ", rpcHeader.traceId, salesPlanNo);
        try {
            SalesPlan salePlan = salePlanDao.getSalePlanNo(salesPlanNo);
            SaleEditInfo saleEditInfo = new SaleEditInfo();
            saleEditInfo.setSalePlanNo(salePlan.getSalesPlanNo());
            saleEditInfo.setProductId(salePlan.getProductId() + "");
            saleEditInfo.setProductCode(salePlan.getProductCode());
            saleEditInfo.setProductName(salePlan.getProductName());
            saleEditInfo.setOnSaleQuantity(salePlan.getOnSaleQuantity());
            int actualQuantity = productInventoryService.selectQuantityByProjectIdAndProductCode(rpcHeader, salePlan.getProjectId() + "", salePlan.getProductCode());
            saleEditInfo.setInStockQuantity(actualQuantity);
            saleEditInfo.setAllocatedQuantity(salePlan.getAllocatedQuantity());
            saleEditInfo.setStartTime(DateUtil.dateToString(salePlan.getStartTime(),"yyyy-MM-dd"));
            saleEditInfo.setEndTime(DateUtil.dateToString(salePlan.getEndTime(),"yyyy-MM-dd"));
            logger.info("#traceId={}# [OUT] getSalePlanInfo success: ", rpcHeader.traceId);
            return saleEditInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public GongxiaoResult editSalePlan(RpcHeader rpcHeader, String salesPlanNo, int onSaleQuantity, String startDate, String endDate) {
        logger.info("#traceId={}# [IN][editSalePlan] params: salesPlanNo={}, onSaleQuantity={}, onSaleQuantity={}, onSaleQuantity={}, onSaleQuantity={}",
                rpcHeader.traceId, salesPlanNo, onSaleQuantity, onSaleQuantity, salesPlanNo, endDate);
        SalesPlan salePlan = salePlanDao.getSalePlanNo(salesPlanNo);
        int allocatedQuantity = salePlan.getAllocatedQuantity();
        int onSaleQuantityDb = salePlan.getOnSaleQuantity();
        int changeNumber = onSaleQuantity - onSaleQuantityDb;
        int unallocatedQuantity = salePlan.getUnallocatedQuantity();

        if (onSaleQuantity < allocatedQuantity) {
            logger.info("[OUT] #traceId={} validate={} ", rpcHeader.traceId, ErrorCode.NUMBER_EXCEPTION.getMessage());
            return new GongxiaoResult(ErrorCode.NUMBER_EXCEPTION.getErrorCode(), ErrorCode.NUMBER_EXCEPTION.getMessage());
        }
        try {
            int editNumber = salePlanDao.editSalePlan(salesPlanNo, onSaleQuantity, unallocatedQuantity + changeNumber, startDate, endDate);
            logger.info("#traceId={}# [OUT] editSalePlan success: ", rpcHeader.traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), editNumber);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    public SalePlanInfoOut getSalePlanDetail(RpcHeader rpcHeader, String salesPlanNo) {
        logger.info("#traceId={}# [IN][getSalePlanDetail] params: salesPlanNo={}  ", rpcHeader.traceId, salesPlanNo);
        try {
            SalesPlan salesPlan = salePlanDao.getSalePlanNo(salesPlanNo);
            int actualQuantity = productInventoryService.selectQuantityByProjectIdAndProductCode(rpcHeader, salesPlan.getProjectId() + "", salesPlan.getProductCode());
            SalePlanInfoOut salePlanInfoOut = new SalePlanInfoOut();
            salePlanInfoOut.setSalesPlanNo(salesPlan.getSalesPlanNo());
            salePlanInfoOut.setOrderStatus(salesPlan.getSalesPlanStatus());
            salePlanInfoOut.setProjectId(salesPlan.getProjectId());
            salePlanInfoOut.setProjectName(salesPlan.getProjectName());
            salePlanInfoOut.setProductId(salesPlan.getProductId());
            salePlanInfoOut.setProductCode(salesPlan.getProductCode());
            salePlanInfoOut.setProductName(salesPlan.getProductName());
            salePlanInfoOut.setGuidePrice(DoubleScale.keepSixBits(salesPlan.getGuidePrice() ));
            salePlanInfoOut.setOnSaleQuantity(salesPlan.getOnSaleQuantity());
            salePlanInfoOut.setAllocatedQuantity(salesPlan.getAllocatedQuantity());
            salePlanInfoOut.setUnallocatedQuantity(salesPlan.getUnallocatedQuantity());
            salePlanInfoOut.setProductActualQuantity(actualQuantity);
            salePlanInfoOut.setStartTime(CommonUtil.DateToString(salesPlan.getStartTime(), "yyyy-MM-dd"));
            salePlanInfoOut.setEndTime(CommonUtil.DateToString(salesPlan.getEndTime(), "yyyy-MM-dd"));
            salePlanInfoOut.setCreateTime(CommonUtil.DateToString(salesPlan.getCreateTime(), "yyyy-MM-dd"));
            logger.info("#traceId={}# [OUT] getSalePlanDetail success: ", rpcHeader.traceId);
            return salePlanInfoOut;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public boolean isProductRepeat(RpcHeader rpcHeader,
                                   String projectId,
                                   String productId,
                                   Date startTime,
                                   Date endTime) {
        logger.info("#traceId={}# [IN][isProductRepeat] params: projectId={} productId={} ", rpcHeader.traceId, projectId, productId);
        try {
            int i = salePlanDao.countRepeatRecord(projectId, productId,startTime,endTime);
            if (i > 0) {
                return true;
            }
            logger.info("#traceId={}# [OUT] isProductRepeat success: ", rpcHeader.traceId);
            return false;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public int cancelPlanSaleOrder(RpcHeader rpcHeader, String salesPlanNo) {
        logger.info("#traceId={}# [IN][cancelPlanSaleOrder] params:  salesPlanNo={} ", rpcHeader.traceId, salesPlanNo);
        try {
            int i = salePlanDao.cancleSalePlanOrder(salesPlanNo);
            logger.info("#traceId={}# [OUT] cancelPlanSaleOrder success: salesPlanNo={}", rpcHeader.traceId, salesPlanNo);
            return i;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int updateReturnSaleQuantity(RpcHeader rpcHeader, String salePlanNo, int changeQuantity) {
        logger.info("#traceId={}# [IN][updateReturnSaleQuantity] params:  salesPlanNo={} ,changeQuantity={} ", rpcHeader.traceId, salePlanNo, changeQuantity);
        try {
            int i = salePlanDao.updateReturnSaleQuantity(salePlanNo, changeQuantity);
            logger.info("#traceId={}# [OUT] updateReturnSaleQuantity success: salesPlanNo={}", rpcHeader.traceId, salePlanNo);
            return i;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

}
