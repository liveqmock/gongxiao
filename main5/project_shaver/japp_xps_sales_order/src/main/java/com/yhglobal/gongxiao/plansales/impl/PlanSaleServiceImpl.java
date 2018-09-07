package com.yhglobal.gongxiao.plansales.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.plansale.microservice.PlanSaleServiceGrpc;
import com.yhglobal.gongxiao.plansale.microservice.PlanSaleStructure;
import com.yhglobal.gongxiao.plansales.dao.SalePlanDao;
import com.yhglobal.gongxiao.sales.model.plan.dto.SalePlanInfoOut;
import com.yhglobal.gongxiao.sales.model.plan.model.SalesPlan;
import com.yhglobal.gongxiao.util.CommonUtil;
import com.yhglobal.gongxiao.util.DoubleScale;
import com.yhglobal.gongxiao.utils.DateUtil;
import com.yhglobal.gongxiao.utils.TablePrefixUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 销售计划
 *
 * @author: 陈浩
 **/
@Service
public class PlanSaleServiceImpl extends PlanSaleServiceGrpc.PlanSaleServiceImplBase {

    private static Logger logger = LoggerFactory.getLogger(PlanSaleServiceImpl.class);

    @Autowired
    SalePlanDao salePlanDao;
    /**
     * 创建新的货品销售计划  注：一次性可计划多个货品
     *
     * rpcHeader rpc调用的header
     * salePlanInfoOuts 销售计划列表
     * @return
     */
    @Override
    public void createSalePlan(PlanSaleStructure.CreateSalePlanReq req,
                               StreamObserver<PlanSaleStructure.CreateSalePlanResp> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        List<PlanSaleStructure.SalePlanInfoIn> salePlanInfoOuts = req.getSalePlanInfoOutsList();
        long projectId = req.getProjectId();

        PlanSaleStructure.CreateSalePlanResp.Builder respBuilder = PlanSaleStructure.CreateSalePlanResp.newBuilder();

        logger.info("#traceId={}# [IN][createSalePlan] params: ", rpcHeader.getTraceId());

        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);

            String format = "yyyy-MM-dd";
            int category = salePlanInfoOuts.size();
            for (PlanSaleStructure.SalePlanInfoIn salePlanInfoIn : salePlanInfoOuts) {
                //A获取项目信息
                ProjectStructure.GetByProjectIdReq getByProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setProjectId(salePlanInfoIn.getProjectId() + "").build();
                ProjectStructure.GetByProjectIdResp getByProjectIdResp = null;
                try {
                    ProjectServiceGrpc.ProjectServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
                    getByProjectIdResp = rpcStub.getByProjectId(getByProjectIdReq);
                } catch (Exception e) {
                    logger.error("#traceId={}# [createSalePlan] 获取项目失败", rpcHeader.getTraceId());
                }
                ProjectStructure.Project project = getByProjectIdResp.getProject();
                //B获取货品信息
                ProductStructure.GetByProductIdReq getProductReq = ProductStructure.GetByProductIdReq.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setProductId(salePlanInfoIn.getProductId() )
                        .setProjectId(projectId)
                        .build();
                ProductStructure.GetByProductIdResp getByProductIdResp = null;
                try {
                    ProductServiceGrpc.ProductServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
                    getByProductIdResp = rpcStub.getByProductId(getProductReq);
                } catch (Exception e) {
                    logger.error("#traceId={}# [createSalePlan] 获取货品失败", rpcHeader.getTraceId());
                }
                ProductStructure.ProductBusiness productBusiness = getByProductIdResp.getProductBusiness();
                SalesPlan salesPlan = new SalesPlan(); //待写入db的销售计划
                String oderNumber = DateTimeIdGenerator.nextId(tablePrefix,BizNumberType.SALE_PLANE_ORDER_NO);
                salesPlan.setSalesPlanNo(oderNumber);
                salesPlan.setSalesPlanStatus((byte) 0);
                salesPlan.setProjectId(salePlanInfoIn.getProjectId());
                salesPlan.setProjectName(project.getProjectName());
                salesPlan.setProductId(productBusiness.getProductBusinessId());
                salesPlan.setProductCode(productBusiness.getProductModel());
                salesPlan.setProductName(productBusiness.getProductName());
                salesPlan.setGuidePrice( productBusiness.getPurchaseGuidePrice());
                salesPlan.setSaleGuidePrice(productBusiness.getSaleGuidePrice());
                salesPlan.setOnSaleQuantity(salePlanInfoIn.getOnSaleQuantity());
                salesPlan.setGrossProfitValue( productBusiness.getGrossProfitValue());
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
                salesPlan.setTablePrefix(tablePrefix);
                int i1 = salePlanDao.insertSalePlane(salesPlan);
            }
            logger.info("#traceId={}# [OUT] createSalePlan success: ", rpcHeader.getTraceId());
            respBuilder.setNumber(1);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 获取 销售管理 > 预售管理 页面的销售计划表格
     *
     * rpcHeader     必填
     * projectId     必填 项目ID
     * productCode   选填  货品编码
     * productName   选填  货品名称
     * createTime    选填  创建时间
     * pageNumber    选填  页号
     * pageSize      选填  页数
     * @return
     */
    @Override
    public void selectSalePlanListByProject(PlanSaleStructure.SelectSalePlanListByProjectReq req,
                                            StreamObserver<PlanSaleStructure.SelectSalePlanListByProjectResp> responseObserver) {

        PlanSaleStructure.SelectSalePlanListByProjectResp.Builder respBuilder = PlanSaleStructure.SelectSalePlanListByProjectResp.newBuilder();

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String projectId = req.getProjectId();
        String productModle = req.getProductModle();
        String productName = req.getProductName();
        String createTime = req.getCreateTime();
        int pageNumber = req.getPageNumber();
        int pageSize = req.getPageSize();

        logger.info("#traceId={}# [IN][selectSalePlanListByProject] params: #projectId={} #productModle={} #productName={} #createTime={} ",
                rpcHeader.getTraceId(), projectId, productModle, productName, createTime);
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));

            PageHelper.startPage(pageNumber, pageSize);
            List<SalesPlan> salesPlans = salePlanDao.selectSalePlanListByProject(tablePrefix,
                    productModle,
                    productName,
                    createTime);

            //组装前端的对象
            PageInfo<SalesPlan> ProductPriceVoPage = new PageInfo<SalesPlan>(salesPlans);

            for (SalesPlan salesPlan : salesPlans) {
                //A 调用库存接口
//                int actualQuantity = productInventoryService.selectQuantityByProjectIdAndProductCode(rpcHeader, salesPlan.getProjectId() + "", salesPlan.getProductCode());
                PlanSaleStructure.SalePlanInfoOut salePlanInfoOut = PlanSaleStructure.SalePlanInfoOut.newBuilder()
                        .setSalesPlanNo(salesPlan.getSalesPlanNo())
                        .setOrderStatus(salesPlan.getSalesPlanStatus())
                        .setProjectId(salesPlan.getProjectId())
                        .setProjectName(salesPlan.getProjectName())
                        .setProductId(salesPlan.getProductId())
                        .setProductModle(salesPlan.getProductCode())
                        .setProductName(salesPlan.getProductName())
                        .setGuidePrice(DoubleScale.keepSixBits(salesPlan.getGuidePrice()))
                        .setSaleGuidePrice(DoubleScale.keepSixBits(salesPlan.getSaleGuidePrice()))
                        .setOnSaleQuantity(salesPlan.getOnSaleQuantity())
                        .setAllocatedQuantity(salesPlan.getAllocatedQuantity())
                        .setUnallocatedQuantity(salesPlan.getUnallocatedQuantity())
                        .setProductActualQuantity(0)
                        .setStartTime(DateUtil.shortDataToFXString(salesPlan.getStartTime()))
                        .setEndTime(DateUtil.shortDataToFXString(salesPlan.getEndTime()))
                        .setCreateTime(DateUtil.longDateToFXString(salesPlan.getCreateTime())).build();
                respBuilder.addSalePlanInfoOutList(salePlanInfoOut);
            }
            respBuilder.setTotal(ProductPriceVoPage.getTotal());
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] selectSalePlanListByProject success: ", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 变更销售计划单可售数量
     *
     * rpcHeader 必填  rpc调用的header
     * salesPlanNo 必填    销售计划单号
     * onSaleQuantity  必填    可售总数
     * allocatedQuantity 必填  已分配数量
     * unallocatedQuantity 必填    未分配额数量
     * @return
     */
    public void updatePlanQuantity(PlanSaleStructure.UpdatePlanQuantityReq req,
                                   StreamObserver<PlanSaleStructure.UpdatePlanQuantityResp> responseObserver) {

        PlanSaleStructure.UpdatePlanQuantityResp.Builder respBuilder = PlanSaleStructure.UpdatePlanQuantityResp.newBuilder();

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String salesPlanNo = req.getSalesPlanNo();
        int onSaleQuantity = req.getOnSaleQuantity();
        int allocatedQuantity = req.getAllocatedQuantity();
        int unallocatedQuantity = req.getUnallocatedQuantity();
        long projectId = req.getProjectId();

        logger.info("#traceId={}# [IN][updatePlanQuantity] params: salesPlanNo={}, #onSaleQuantity={}, #allocatedQuantity={}, #unallocatedQuantity={} ",
                rpcHeader.getTraceId(), salesPlanNo, onSaleQuantity, allocatedQuantity, unallocatedQuantity);
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);

            int updateQuantity = salePlanDao.updateProductQuantity(tablePrefix,
                    salesPlanNo,
                    onSaleQuantity,
                    allocatedQuantity,
                    unallocatedQuantity);
            logger.info("#traceId={}# [OUT] updatePlanQuantity success: ", rpcHeader.getTraceId());
            respBuilder.setNumber(updateQuantity);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }


    /**
     * 通过销售计划单号获取预约销售单未处理的数量
     *
     * rpcHeader     必填   rpc调用的header
     * salesPlanNo   必填  销售单号
     * @return
     */
    @Override
    public void getUnHandleQuantity(PlanSaleStructure.GetUnHandleQuantityReq req,
                                    StreamObserver<PlanSaleStructure.GetUnHandleQuantityResp> responseObserver) {

        PlanSaleStructure.GetUnHandleQuantityResp.Builder respBuilder = PlanSaleStructure.GetUnHandleQuantityResp.newBuilder();

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String salesPlanNo = req.getSalesPlanNo();
        long projectId = req.getProjectId();

        logger.info("#traceId={}# [IN][getSaleQuantity] params: salesPlanNo={}  ", rpcHeader.getTraceId(), salesPlanNo);
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);

            SalesPlan salePlan = salePlanDao.getSalePlanNo(tablePrefix,salesPlanNo);
            int onSaleQuantity = salePlan.getUnallocatedQuantity();
            logger.info("#traceId={}# [OUT] getSaleQuantity success: ", rpcHeader.getTraceId());
            respBuilder.setNumber(onSaleQuantity);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    /**
     * 通过销售计划单号获取销售计划信息
     *
     * rpcHeader rpc调用的header
     * salesPlanNo 必填    销售单号
     * @return
     */
    public void getSalePlanInfo(PlanSaleStructure.GetSalePlanInfoReq req,
                                StreamObserver<PlanSaleStructure.GetSalePlanInfoResp> responseObserver) {

        PlanSaleStructure.GetSalePlanInfoResp.Builder respBuilder = PlanSaleStructure.GetSalePlanInfoResp.newBuilder();

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String salesPlanNo = req.getSalesPlanNo();
        long projectId = req.getProjectId();

        logger.info("#traceId={}# [IN][getSalePlanInfo] params: salesPlanNo={}  ", rpcHeader.getTraceId(), salesPlanNo);
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);

            SalesPlan salePlan = salePlanDao.getSalePlanNo(tablePrefix,salesPlanNo);
            Date startTime = salePlan.getStartTime();
            long statTimeLong = 0;
            if (startTime != null) {
                statTimeLong = startTime.getTime();
            }
            Date endTime = salePlan.getEndTime();
            long endTimeLong = 0;
            if (endTime != null) {
                endTimeLong = endTime.getTime();
            }
            PlanSaleStructure.SaleEditInfo saleEditInfo = PlanSaleStructure.SaleEditInfo.newBuilder()
                    .setSalePlanNo(salePlan.getSalesPlanNo())
                    .setProductId(salePlan.getProductId() + "")
                    .setProductModle(salePlan.getProductCode())
                    .setProductName(salePlan.getProductName())
                    .setOnSaleQuantity(salePlan.getOnSaleQuantity())
//            int actualQuantity = productInventoryService.selectQuantityByProjectIdAndProductCode(rpcHeader, salePlan.getProjectId() + "", salePlan.getProductCode());
                    .setInStockQuantity(0)
                    .setAllocatedQuantity(salePlan.getAllocatedQuantity())
                    .setStartTime(statTimeLong)
                    .setEndTime(endTimeLong).build();
            logger.info("#traceId={}# [OUT] getSalePlanInfo success: ", rpcHeader.getTraceId());
            respBuilder.setSaleEditInfo(saleEditInfo);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }


    /**
     * 销售管理 > 预售管理页 在某一条销售计划后 点击"修改", 在弹框中修改数量后 点提交则调用该接口
     * 变更销售计划信息,主要市变更可售数量
     *
     * rpcHeader      必填
     * salesPlanNo    必填  销售计划单号
     * onSaleQuantity 必填  可售数量
     * startDate      必填  有效期起
     * endDate        必填  有效期止
     * @return
     */
    @Override
    public void editSalePlan(PlanSaleStructure.EditSalePlanReq req,
                             StreamObserver<PlanSaleStructure.EditSalePlanResp> responseObserver) {

        PlanSaleStructure.EditSalePlanResp.Builder respBuilder = PlanSaleStructure.EditSalePlanResp.newBuilder();

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String salesPlanNo = req.getSalesPlanNo();
        int onSaleQuantity = req.getOnSaleQuantity();
        String endDate = req.getEndDate();
        String startDate = req.getStartDate();
        long projectId = req.getProjectId();

        String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);


        logger.info("#traceId={}# [IN][editSalePlan] params: salesPlanNo={}, onSaleQuantity={}, onSaleQuantity={}, onSaleQuantity={}, onSaleQuantity={}",
                rpcHeader.getTraceId(), salesPlanNo, onSaleQuantity, onSaleQuantity, salesPlanNo, endDate);
        SalesPlan salePlan = salePlanDao.getSalePlanNo(tablePrefix,salesPlanNo);
        int allocatedQuantity = salePlan.getAllocatedQuantity();
        int onSaleQuantityDb = salePlan.getOnSaleQuantity();
        int changeNumber = onSaleQuantity - onSaleQuantityDb;
        int unallocatedQuantity = salePlan.getUnallocatedQuantity();

        //数据校验
        if (onSaleQuantity < allocatedQuantity) {
            logger.info("[OUT] #traceId={} validate={} ", rpcHeader.getTraceId(), ErrorCode.NUMBER_EXCEPTION.getMessage());
            GongxiaoRpc.RpcResult rpcResult = GongxiaoRpc.RpcResult.newBuilder()
                    .setReturnCode(ErrorCode.NUMBER_EXCEPTION.getErrorCode())
                    .setMsg(ErrorCode.NUMBER_EXCEPTION.getMessage()).build();
            respBuilder.setRpcResult(rpcResult);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            return;
        }
        try {
            int editNumber = salePlanDao.editSalePlan(tablePrefix,
                    salesPlanNo,
                    onSaleQuantity,
                    unallocatedQuantity + changeNumber,
                    startDate, endDate);
            logger.info("#traceId={}# [OUT] editSalePlan success: ", rpcHeader.getTraceId());
            GongxiaoRpc.RpcResult rpcResult = GongxiaoRpc.RpcResult.newBuilder()
                    .setReturnCode(ErrorCode.SUCCESS.getErrorCode())
                    .setMsg(ErrorCode.SUCCESS.getMessage()).build();
            respBuilder.setRpcResult(rpcResult);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            return;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }


    /**
     *
     * 获取销售计划单详情
     * rpcHeader
     * salesPlanNo   销售计划单号
     * @return
     */
    public void getSalePlanDetail(PlanSaleStructure.GetSalePlanDetailReq req,
                                  StreamObserver<PlanSaleStructure.GetSalePlanDetailResp> responseObserver) {

        PlanSaleStructure.GetSalePlanDetailResp.Builder respBuilder = PlanSaleStructure.GetSalePlanDetailResp.newBuilder();

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String salesPlanNo = req.getSalesPlanNo();
        long projectId = req.getProjectId();

        logger.info("#traceId={}# [IN][getSalePlanDetail] params: salesPlanNo={}  ", rpcHeader.getTraceId(), salesPlanNo);
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);

            SalesPlan salesPlan = salePlanDao.getSalePlanNo(tablePrefix,salesPlanNo);
//            int actualQuantity = productInventoryService.selectQuantityByProjectIdAndProductCode(rpcHeader, salesPlan.getProjectId() + "", salesPlan.getProductCode());
            PlanSaleStructure.SalePlanInfoOut.Builder salePlanInfoOut = PlanSaleStructure.SalePlanInfoOut.newBuilder()
                    .setSalesPlanNo(salesPlan.getSalesPlanNo())
                    .setOrderStatus(salesPlan.getSalesPlanStatus())
                    .setProjectId(salesPlan.getProjectId())
                    .setProjectName(salesPlan.getProjectName())
                    .setProductId(salesPlan.getProductId())
                    .setProductModle(salesPlan.getProductCode())
                    .setProductName(salesPlan.getProductName())
                    .setGuidePrice(DoubleScale.keepSixBits(salesPlan.getGuidePrice()))
                    .setOnSaleQuantity(salesPlan.getOnSaleQuantity())
                    .setAllocatedQuantity(salesPlan.getAllocatedQuantity())
                    .setUnallocatedQuantity(salesPlan.getUnallocatedQuantity())
                    .setProductActualQuantity(0)
                    .setStartTime(CommonUtil.DateToString(salesPlan.getStartTime(), "yyyy-MM-dd"))
                    .setEndTime(CommonUtil.DateToString(salesPlan.getEndTime(), "yyyy-MM-dd"))
                    .setCreateTime(CommonUtil.DateToString(salesPlan.getCreateTime(), "yyyy-MM-dd"));
            logger.info("#traceId={}# [OUT] getSalePlanDetail success: ", rpcHeader.getTraceId());
            respBuilder.setSalePlanInfoOut(salePlanInfoOut);

            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }
    /**
     * 同一货品在同个有效期内 只能有一条预售记录  该接口为判定货品是否重复
     *
     * rpcHeader
     * projectId 项目ID
     * productId 项目名称
     * @return
     */
    @Override
    public void isProductRepeat(PlanSaleStructure.IsProductRepeatReq req,
                                StreamObserver<PlanSaleStructure.IsProductRepeatResp> responseObserver) {

        PlanSaleStructure.IsProductRepeatResp.Builder respBuilder = PlanSaleStructure.IsProductRepeatResp.newBuilder();

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String projectId = req.getProjectId();
        String productId = req.getProductId();
        long startTime = req.getStartTime();
        long endTime = req.getEndTime();

        logger.info("#traceId={}# [IN][isProductRepeat] params: projectId={} productId={} ", rpcHeader.getTraceId(), projectId, productId);
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));

            int i = salePlanDao.countRepeatRecord(tablePrefix,
                    productId,
                    DateUtil.long2Date(startTime),
                    DateUtil.long2Date(endTime));

            if (i > 0) { //若该时间段内已存在预售
                respBuilder.setIsRepeated(true);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            logger.info("#traceId={}# [OUT] isProductRepeat success: ", rpcHeader.getTraceId());
            respBuilder.setIsRepeated(false);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            return;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }


    /**
     * 作废预售单
     * rpcHeader
     * salesPlanNo 预售单号
     * @return
     */
    @Override
    public void cancelPlanSaleOrder(PlanSaleStructure.CancelPlanSaleOrderReq req,
                                    StreamObserver<PlanSaleStructure.CancelPlanSaleOrderResp> responseObserver) {

        PlanSaleStructure.CancelPlanSaleOrderResp.Builder respBuilder = PlanSaleStructure.CancelPlanSaleOrderResp.newBuilder();

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String salesPlanNo = req.getSalesPlanNo();
        long projectId = req.getProjectId();

        logger.info("#traceId={}# [IN][cancelPlanSaleOrder] params:  salesPlanNo={} ", rpcHeader.getTraceId(), salesPlanNo);
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);

            int i = salePlanDao.cancleSalePlanOrder(tablePrefix,salesPlanNo);
            logger.info("#traceId={}# [OUT] cancelPlanSaleOrder success: salesPlanNo={}", rpcHeader.getTraceId(), salesPlanNo);
            respBuilder.setNumber(1);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }


    /**
     * 预售明细作废后,返还订单的未处理数量
     *
     * rpcHeader
     * changeQuantity 返还数量
     * @return
     */
    @Override
    public void updateReturnSaleQuantity(PlanSaleStructure.UpdateReturnSaleQuantityReq req,
                                         StreamObserver<PlanSaleStructure.UpdateReturnSaleQuantityResp> responseObserver) {

        PlanSaleStructure.UpdateReturnSaleQuantityResp.Builder respBuilder = PlanSaleStructure.UpdateReturnSaleQuantityResp.newBuilder();

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String salePlanNo = req.getSalePlanNo();
        int changeQuantity = req.getChangeQuantity();
        long projectId = req.getProjectId();

        logger.info("#traceId={}# [IN][updateReturnSaleQuantity] params:  salesPlanNo={} ,changeQuantity={} ", rpcHeader.getTraceId(), salePlanNo, changeQuantity);
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);

            int i = salePlanDao.updateReturnSaleQuantity(tablePrefix,salePlanNo, changeQuantity);
            logger.info("#traceId={}# [OUT] updateReturnSaleQuantity success: salesPlanNo={}", rpcHeader.getTraceId(), salePlanNo);
            respBuilder.setNumber(1);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }
}

