package com.yhglobal.gongxiao.plansales.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorServiceGrpc;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemServiceGrpc;
import com.yhglobal.gongxiao.plansale.microservice.PlanSaleItemStructure;
import com.yhglobal.gongxiao.plansales.dao.SalePlanDao;
import com.yhglobal.gongxiao.plansales.dao.SalePlanItemDao;
import com.yhglobal.gongxiao.sales.model.plan.dto.SalePlanItemBo;
import com.yhglobal.gongxiao.sales.model.plan.model.SalesPlan;
import com.yhglobal.gongxiao.sales.model.plan.model.SalesPlanItem;
import com.yhglobal.gongxiao.util.CommonUtil;
import com.yhglobal.gongxiao.util.DoubleScale;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import com.yhglobal.gongxiao.utils.DateUtil;
import com.yhglobal.gongxiao.utils.TablePrefixUtil;
import io.grpc.internal.GrpcUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 预售明细
 *
 * @author: 陈浩
 **/
@Service
public class PlanSaleItemServiceImpl extends PlanSaleItemServiceGrpc.PlanSaleItemServiceImplBase {

    private static Logger logger = LoggerFactory.getLogger(PlanSaleItemServiceImpl.class);

    @Autowired
    SalePlanDao salePlanDao;    //销售计划

    @Autowired
    SalePlanItemDao salePlanItemDao; //销售计划明细

    /**
     * 创建销售计划明细
     *
     * rpcHeader       rpc调用的header
     * salePlanInfoIns 销售计划列表
     *
     * @return
     */
    @Override
    public void createSalePlanItem(PlanSaleItemStructure.CreateSalePlanItemReq req,
                                   StreamObserver<PlanSaleItemStructure.CreateSalePlanItemResp> responseObserver) {

        PlanSaleItemStructure.CreateSalePlanItemResp.Builder respBuilder = PlanSaleItemStructure.CreateSalePlanItemResp.newBuilder();

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        long projectId = req.getProjectId();
        List<PlanSaleItemStructure.SalePlanItemInfo> salePlanInfoIns = req.getSalePlanInfoInsList();
        logger.info("#traceId={}# [IN][insertSalePlanItemInfo] params: projectId={}", rpcHeader.getTraceId(), projectId);
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);

            String salesPlanNo1 = salePlanInfoIns.get(0).getSalesPlanNo(); //明细对应的预售计划单
            SalesPlan salePlan = salePlanDao.getSalePlanNo(tablePrefix,salesPlanNo1); //获取计划单汇总信息

            int i = 0;
            for (PlanSaleItemStructure.SalePlanItemInfo salePlanItemInfo : salePlanInfoIns) {
                SalesPlanItem salesPlanItem = new SalesPlanItem();
                salesPlanItem.setItemStatus((byte) 0);
                salesPlanItem.setSalesPlanNo(salePlanItemInfo.getSalesPlanNo());

                salesPlanItem.setProjectId(Long.parseLong(salePlanItemInfo.getProjectId()));
                salesPlanItem.setProjectName(salePlanItemInfo.getProjectName());

                salesPlanItem.setProductId(Long.parseLong(salePlanItemInfo.getProductId()));
                salesPlanItem.setProductCode(salePlanItemInfo.getProductModel());
                salesPlanItem.setProductName(salePlanItemInfo.getProductName());
                salesPlanItem.setGrossProfitValue(salePlan.getGrossProfitValue());

                salesPlanItem.setOnSaleQuantity(salePlanItemInfo.getOnSaleQuantity()); //总分配数量
//            salesPlanItem.setSaleOccupationQuantity(salePlanItemInfo.getSaleOccupationQuantity()); //销售占用
//            salesPlanItem.setSoldQuantity(salePlanItemInfo.getSoldQuantity()); //已售数量
                salesPlanItem.setUnallocatedQuantity(salePlanItemInfo.getOnSaleQuantity()); //待分配数量=总分配数量

                salesPlanItem.setCurrencyCode("CNY");
                salesPlanItem.setCurrencyName("RMB");
                salesPlanItem.setCustomerId(salePlanItemInfo.getCustomerId());
                salesPlanItem.setCustomerName(salePlanItemInfo.getCustomerName());
                salesPlanItem.setDistributorId(Long.parseLong(salePlanItemInfo.getCustomerId()));
                salesPlanItem.setDistributorName(salePlanItemInfo.getCustomerName());
                salesPlanItem.setBrandPrepaidUnit(DoubleScale.multipleMillion(salePlanItemInfo.getBrandPrepaidUnit()));
                salesPlanItem.setBrandPrepaidDiscount(DoubleScale.multipleMillion(salePlanItemInfo.getBrandPrepaidDiscount()));
                salesPlanItem.setYhPrepaidUnit(DoubleScale.multipleMillion(salePlanItemInfo.getYhPrepaidUnit()));
                salesPlanItem.setYhPrepaidDiscount(DoubleScale.multipleMillion(salePlanItemInfo.getYhPrepaidDiscount()));

                ProductStructure.GetByProductIdReq getByProductIdReq = ProductStructure.GetByProductIdReq.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setProductId(Long.parseLong(salePlanItemInfo.getProductId()))
                        .setProjectId(projectId)
                        .build();
                ProductStructure.GetByProductIdResp getByProductIdResp = null;
                try {
                    ProductServiceGrpc.ProductServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
                    getByProductIdResp = rpcStub.getByProductId(getByProductIdReq);
                } catch (Exception e) {
                    logger.error("#traceId={}# [insertSalePlanItemInfo] 调用货品信息失败: ", rpcHeader.getTraceId());
                    responseObserver.onNext(respBuilder.build());
                    responseObserver.onCompleted();
                    return;
                }
                ProductStructure.ProductBusiness productBusiness = getByProductIdResp.getProductBusiness();
                if (productBusiness == null) {
                    logger.error("#traceId={}# [insertSalePlanItemInfo] 调用货品信息失败: ", rpcHeader.getTraceId());
                    responseObserver.onNext(respBuilder.build());
                    responseObserver.onCompleted();
                    return;
                }
                long saleGuidePrice = productBusiness.getSaleGuidePrice(); //乘1000后的值
                salesPlanItem.setGuidePrice(productBusiness.getPurchaseGuidePrice());
                int onSaleQuantity = salePlanItemInfo.getOnSaleQuantity();

                double brandPrepaidDiscount = salePlanItemInfo.getBrandPrepaidDiscount();
                double yhPrepaidDiscount = salePlanItemInfo.getYhPrepaidDiscount();

                long salePrice = (long) (saleGuidePrice * (1 - brandPrepaidDiscount - yhPrepaidDiscount));
                salesPlanItem.setWholesalePrice(salePrice);
                salesPlanItem.setSaleGuidePrice(productBusiness.getSaleGuidePrice());

                String format = "yyyy-MM-dd";
                String startDate = salePlanItemInfo.getStartTime();
                String endDate = salePlanItemInfo.getEndTime();
                Date startDates = startDate == null || "".equals(startDate) ? null : CommonUtil.StringToDateWithFormat(startDate, format);
                Date endDates = endDate == null || "".equals(endDate) ? null : CommonUtil.StringToDateWithFormat(endDate, format);
                salesPlanItem.setStartTime(startDates);
                salesPlanItem.setEndTime(endDates);
                salesPlanItem.setBrandId(salePlan.getBrandId() + "");
                salesPlanItem.setBrandName(salePlan.getBrandName());
                salesPlanItem.setTablePrefix(tablePrefix);
                int num = salePlanItemDao.insertSalePlanItem(salesPlanItem); //写入明细表
                //每处理完一个AD则更新一下销售计划单 可售数量/已分配数量/未分配数量
                String salesPlanNo = salePlanItemInfo.getSalesPlanNo();
                int onSaleQuantityOrder = salePlan.getOnSaleQuantity();
                int allocatedQuantityOrder = salePlan.getAllocatedQuantity() + onSaleQuantity;
                int unallocatedQuantityOrder = salePlan.getUnallocatedQuantity() - onSaleQuantity;
                salePlanDao.updateProductQuantity(tablePrefix,
                        salesPlanNo,
                        onSaleQuantityOrder,
                        allocatedQuantityOrder,
                        unallocatedQuantityOrder);
                i += num;
            }
            logger.info("#traceId={}# [OUT] insertSalePlanItemInfo success: ", rpcHeader.getTraceId());
            respBuilder.setNumber(1);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 销售管理 > 预售管理 点击 "维护"， 在维护页上 会拉取该接口 获得预售明细列表
     *
     * <p>
     * rpcHeader
     * salesPlanNo  必填       预售单号
     * customerId   非必填      客户ID
     * customerName 非必填      客户名称
     *
     * @return
     */
    @Override
    public void selectSalePlanItemList(PlanSaleItemStructure.SelectSalePlanItemListReq req,
                                       StreamObserver<PlanSaleItemStructure.SelectSalePlanItemListResp> responseObserver) {

        PlanSaleItemStructure.SelectSalePlanItemListResp.Builder respBuilder = PlanSaleItemStructure.SelectSalePlanItemListResp.newBuilder();

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String salesPlanNo = req.getSalesPlanNo();
        String customerId = req.getCustomerId();
        String customerName = req.getCustomerName();
        int pageNumber = req.getPageNumber();
        int pageSize = req.getPageSize();
        long projectId = req.getProjectId();

        logger.info("#traceId={}# [IN][getSalePlanItemList] params: salesPlanNo={}, customerId={}, customerName={}, pageNumber={},  ",
                rpcHeader.getTraceId(), salesPlanNo, customerId, customerName, pageNumber, pageSize);
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);

            PageHelper.startPage(pageNumber, pageSize);
            List<SalesPlanItem> salesPlanItems = salePlanItemDao.selectBySalePlanNo(tablePrefix,salesPlanNo, customerId, customerName);
            PageInfo<SalesPlanItem> salesPlanItemPageInfo = new PageInfo<SalesPlanItem>(salesPlanItems);

            List<SalePlanItemBo> salePlanItemBos = new ArrayList<>();
            for (SalesPlanItem salesPlanItem : salesPlanItems) {
                PlanSaleItemStructure.SalePlanItemBo.Builder salePlanItemBo = PlanSaleItemStructure.SalePlanItemBo.newBuilder()
                        .setItemId(salesPlanItem.getItemId())
                        .setSalesPlanNo(salesPlanItem.getSalesPlanNo())
                        .setCustomerId(salesPlanItem.getCustomerId())
                        .setCustomerName(salesPlanItem.getCustomerName())
                        .setOnSaleQuantity(salesPlanItem.getOnSaleQuantity())
                        .setSaleOccupationQuantity(salesPlanItem.getSaleOccupationQuantity())
                        .setSoldQuantity(salesPlanItem.getSoldQuantity())
                        .setUnallocatedQuantity(salesPlanItem.getUnallocatedQuantity())
                        .setCurrencyCode(salesPlanItem.getCurrencyCode())
                        .setCurrencyName(salesPlanItem.getCurrencyName())
                        .setGuidePrice(DoubleScale.keepSixBits(salesPlanItem.getGuidePrice()))
                        .setBrandPrepaidDiscount(DoubleScale.keepSixBits(salesPlanItem.getBrandPrepaidDiscount()))
                        .setYhPrepaidDiscount(DoubleScale.keepSixBits(salesPlanItem.getYhPrepaidDiscount()))
                        .setWholesalePrice(DoubleScale.keepSixBits(salesPlanItem.getWholesalePrice()))
                        .setStartTime(DateUtil.getTime(salesPlanItem.getStartTime()))
                        .setEndTime(DateUtil.getTime(salesPlanItem.getEndTime()))
                        .setLastUpdateTime(DateUtil.getTime(salesPlanItem.getLastUpdateTime()));

                respBuilder.addSalePlanItemBoList(salePlanItemBo);
            }
            respBuilder.setTotal(salesPlanItemPageInfo.getTotal());
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] getSalePlanItemList success: salePlanItemBos.size={}", rpcHeader.getTraceId(), salePlanItemBos.size());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    /**
     * 销售管理 > 预售管理 点击 "维护"， 在维护页上，点击"编辑"进入弹框，则会进入该接口来拉取该条明细的信息
     *
     * <p>
     * rpcHeader rpc调用的header
     * itemId    预售明细ID
     *
     * @return
     */
    @Override
    public void getEditItemInfo(PlanSaleItemStructure.GetEditItemInfoReq req,
                                StreamObserver<PlanSaleItemStructure.GetEditItemInfoResp> responseObserver) {

        PlanSaleItemStructure.GetEditItemInfoResp.Builder respBuilder = PlanSaleItemStructure.GetEditItemInfoResp.newBuilder();

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String itemId = req.getItemId();
        long projectId = req.getProjectId();

        logger.info("#traceId={}# [IN][getEditItemInfo] params: itemId={}, projectId={}  ", rpcHeader.getTraceId(), itemId, projectId);
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);

            SalesPlanItem salesPlanItem = salePlanItemDao.getByItemId(tablePrefix,itemId);
            String salesPlanNo = salesPlanItem.getSalesPlanNo();
            SalesPlan salePlanNo = salePlanDao.getSalePlanNo(tablePrefix,salesPlanNo);

            PlanSaleItemStructure.SaleItemEditInfo.Builder saleItemEditInfo = PlanSaleItemStructure.SaleItemEditInfo.newBuilder()
                    .setCustomerId(salesPlanItem.getCustomerId())
                    .setCustomerName(salesPlanItem.getCustomerName());

            int onSaleQuantity = salesPlanItem.getOnSaleQuantity(); //获取可售总数
            int saleOccupationQuantity = salesPlanItem.getSaleOccupationQuantity(); //获取销售占用
            int soldQuantity = salesPlanItem.getSoldQuantity(); //获取已售数量
            int remianSaleQuantity = onSaleQuantity - saleOccupationQuantity - soldQuantity; //剩余销售总数
            saleItemEditInfo.setRemainSaleAmount(remianSaleQuantity);

            saleItemEditInfo.setUnallocatedQuantity(salePlanNo.getUnallocatedQuantity()); //客户可下单数量
            saleItemEditInfo.setGuidePrice(DoubleScale.keepSixBits(salesPlanItem.getGuidePrice()));
            saleItemEditInfo.setSaleGuidePrice(DoubleScale.keepSixBits(salesPlanItem.getSaleGuidePrice()));
            saleItemEditInfo.setBrandPrepaidUnit(DoubleScale.keepSixBits(salesPlanItem.getBrandPrepaidUnit()));
            saleItemEditInfo.setBrandPrepaidDiscount(DoubleScale.keepSixBits(salesPlanItem.getBrandPrepaidDiscount()));
            saleItemEditInfo.setYhPrepaidUnit(DoubleScale.keepSixBits(salesPlanItem.getYhPrepaidUnit()));
            saleItemEditInfo.setYhPrepaidDiscount(DoubleScale.keepSixBits(salesPlanItem.getYhPrepaidDiscount()));
            saleItemEditInfo.setWholesalePrice(DoubleScale.keepSixBits(salesPlanItem.getWholesalePrice()));
            saleItemEditInfo.setStartTime(DateUtil.getTime(salesPlanItem.getStartTime()));
            saleItemEditInfo.setEndTime(DateUtil.getTime(salesPlanItem.getEndTime()));
            saleItemEditInfo.setItemId(salesPlanItem.getItemId());
            logger.info("#traceId={}# [OUT] getEditItemInfo success: ", rpcHeader.getTraceId());
            respBuilder.setSaleItemEditInfo(saleItemEditInfo);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 销售管理 > 预售管理 点击 "维护"， 在维护页上，点击"编辑"进入弹框页，进行修改后，点击提交 则会进入该接口来更新该预售明细
     * <p>
     * rpcHeader            rpc调用的header
     * itemId               客户计划货销售单号
     * adjustNumber         变更数量
     * brandPrepaidUnit     品牌商单位代垫
     * brandPrepaidDiscount 品牌商代垫折扣
     * yhPrepaidUnit        越海单位代垫
     * yhPrepaidDiscount    越海代垫折扣
     * startDate            开始日期
     * endDate              结束日期
     *
     * @return
     */
    @Override
    public void editSaleItem(PlanSaleItemStructure.EditSaleItemReq req,
                             StreamObserver<PlanSaleItemStructure.EditSaleItemResp> responseObserver) {

        PlanSaleItemStructure.EditSaleItemResp.Builder respBuilder = PlanSaleItemStructure.EditSaleItemResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String itemId = req.getItemId();
        int adjustNumber = req.getAdjustNumber();
        double brandPrepaidUnitTemp = req.getBrandPrepaidUnit();
        double brandPrepaidDiscountTemp = req.getBrandPrepaidDiscount();
        double yhPrepaidUnitTemp = req.getYhPrepaidUnit();
        double yhPrepaidDiscountTemp = req.getYhPrepaidDiscount();
        long startDate = req.getStartDate();
        long endDate = req.getEndDate();
        long projectId = req.getProjectId();

        logger.info("#traceId={}# [IN][editSaleItem] params: itemId={}, #adjustNumber={}, #brandPrepaidUnitTemp={}, #brandPrepaidDiscountTemp={}," +
                        "yhPrepaidUnitTemp={}, yhPrepaidDiscountTemp={}, startDate={}, endDate={}, projectId={} ",
                rpcHeader.getTraceId(), itemId, adjustNumber, brandPrepaidUnitTemp, brandPrepaidDiscountTemp, yhPrepaidUnitTemp, yhPrepaidDiscountTemp, startDate, endDate, projectId);
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);
            SalesPlanItem salesPlanItem = salePlanItemDao.getByItemId(tablePrefix,itemId);
            long saleGuidePrice = salesPlanItem.getSaleGuidePrice();
            int brandPrepaidUnit = (int) (brandPrepaidUnitTemp *  saleGuidePrice);
            int brandPrepaidDiscount = (int) (brandPrepaidDiscountTemp * FXConstant.MILLION);
            int yhPrepaidUnit = (int) (yhPrepaidDiscountTemp * saleGuidePrice);
            int yhPrepaidDiscount = (int) (yhPrepaidDiscountTemp * FXConstant.MILLION);

            //1 由 调整数量 计算新的 可售数量 和 未处理数量
            int onSaleQuantity = salesPlanItem.getOnSaleQuantity();
            onSaleQuantity += adjustNumber;
            int unallocatedQuantity1 = salesPlanItem.getUnallocatedQuantity();
            unallocatedQuantity1 += adjustNumber;
            //更新计划单明细表
            //todo: 参数后台校验
            salePlanItemDao.update(
                    tablePrefix,
                    itemId,
                    onSaleQuantity,
                    unallocatedQuantity1,
                    brandPrepaidUnit,
                    brandPrepaidDiscount,
                    yhPrepaidUnit,
                    yhPrepaidDiscount,
                    DateUtil.long2Date(startDate),
                    DateUtil.long2Date(endDate));

            SalesPlan salePlanInfo = salePlanDao.getSalePlanNo(tablePrefix,salesPlanItem.getSalesPlanNo());

            int allocatedQuantity = salePlanInfo.getAllocatedQuantity();
            int unallocatedQuantity = salePlanInfo.getUnallocatedQuantity();

            allocatedQuantity = allocatedQuantity + adjustNumber;
            unallocatedQuantity = unallocatedQuantity - adjustNumber;
            //更新计划单汇总
            salePlanDao.updateHandleQuantity(tablePrefix,
                    salePlanInfo.getSalesPlanNo(),
                    allocatedQuantity,
                    unallocatedQuantity);
            logger.info("#traceId={}# [OUT] editSaleItem success: ", rpcHeader.getTraceId());
            respBuilder.setNumber(1);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 销售管理 > 预售管理 点击 "维护"， 在维护页上，选中一条或多条预售明细后，点击作废 则会进入该接口作废该明细
     *
     * 注1: 这个接口不是作废整个计划单，只是作废某一条明细
     * 注2：这里只会更改明细的状态  释放可售数量等逻辑由另外接口负责
     *
     * <p>
     * rpcHeader rpc调用的header
     * itemId    被取消的计划销售单号
     *
     * @return
     */
    @Override
    public void cancelPlanItem(PlanSaleItemStructure.CancelPlanItemReq req,
                               StreamObserver<PlanSaleItemStructure.CancelPlanItemResp> responseObserver) {

        PlanSaleItemStructure.CancelPlanItemResp.Builder respBuilder = PlanSaleItemStructure.CancelPlanItemResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        long projectId = req.getProjectId();
        String itemId = req.getItemId();
        logger.info("#traceId={}# [IN][cancelPlanItem] params: projectId={}", rpcHeader.getTraceId(), projectId);
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);

            int i = salePlanItemDao.updatePlanItemStatus(tablePrefix,itemId, 91);
            logger.info("#traceId={}# [OUT] cancelPlanItem success: ", rpcHeader.getTraceId());
            respBuilder.setNumber(1);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 销售管理 > 预售管理 点击作废 则会进入该接口作废整个销售计划单
     *
     * 注1: 这个接口是作废整个计划单，不是作废某一条明细
     * 注2：这里只会更改明细的状态  释放可售数量等逻辑由另外接口负责
     * <p>
     * rpcHeader
     * planSaleNo
     *
     * @return
     */
    @Override
    public void cancelPlan(PlanSaleItemStructure.CancelPlanReq req,
                           StreamObserver<PlanSaleItemStructure.CancelPlanResp> responseObserver) {

        PlanSaleItemStructure.CancelPlanResp.Builder respBuilder = PlanSaleItemStructure.CancelPlanResp.newBuilder();

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String planSaleNo = req.getPlanSaleNo();
        long projectId = req.getProjectId();
        logger.info("#traceId={}# [IN][cancelPlan] params: planSaleNo={}, projectId={} ", rpcHeader.getTraceId(), planSaleNo, projectId);
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);
            int i = salePlanItemDao.cancelSalePlanItemBySalePlanNo(tablePrefix,planSaleNo, 91);
            logger.info("#traceId={}# [OUT] cancelPlan success: 变更条数={}", rpcHeader.getTraceId(), i);
            respBuilder.setNumber(1);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }


    /**
     *
     * AD登录后，通过该接口查询可下单的货品列表
     *
     * <p>
     * rpcHeader     rpc调用的header
     * projectId     项目ID
     * distributorId 经销商ID
     * productId     货品ID
     * businessDate  有效日期
     *
     * @return
     */
    @Override
    public void getCustomerPlanInfo(PlanSaleItemStructure.GetCustomerPlanInfoReq req,
                                    StreamObserver<PlanSaleItemStructure.GetCustomerPlanInfoResp> responseObserver) {

        PlanSaleItemStructure.GetCustomerPlanInfoResp.Builder respBuilder = PlanSaleItemStructure.GetCustomerPlanInfoResp.newBuilder();

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String distributorId = req.getDistributorId();
        String productId = req.getProductId();
        String projectId = req.getProjectId();
        long businessDate = req.getBusinessDate();

        logger.info("#traceId={}# [IN][getCustomerPlanInfo] params: projectId={}, distributorId={} ,productId={}, projectId={}",
                rpcHeader.getTraceId(), distributorId, productId, projectId);
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
            SalesPlanItem salesPlanItem = salePlanItemDao.getCustomerProduct(tablePrefix,
                    distributorId,
                    productId,
                    DateUtil.long2Date(businessDate));
            if (salesPlanItem == null) {
                logger.info("#traceId={}# [OUT] fail to getCustomerPlanInfo", rpcHeader.getTraceId());
                return;
            } else {
                logger.info("#traceId={}# [OUT] getCustomerPlanInfo success: salesPlanItem={}", rpcHeader.getTraceId(), salesPlanItem.toString());
            }
            PlanSaleItemStructure.SalesPlanItem salesPlanItem1 = PlanSaleItemStructure.SalesPlanItem.newBuilder()
                    .setItemId(salesPlanItem.getItemId())
                    .setItemStatus(salesPlanItem.getItemStatus())
                    .setSalesPlanNo(salesPlanItem.getSalesPlanNo())
                    .setCustomerId(salesPlanItem.getCustomerId())
                    .setCustomerName(salesPlanItem.getCustomerName())
                    .setProjectId(salesPlanItem.getProjectId())
                    .setProjectName(salesPlanItem.getProjectName())
                    .setProductId(salesPlanItem.getProductId())
                    .setProductModle(salesPlanItem.getProductCode())
                    .setProductName(salesPlanItem.getProductName())
                    .setDistributorId(salesPlanItem.getDistributorId())
                    .setDistributorName(salesPlanItem.getDistributorName())
                    .setOnSaleQuantity(salesPlanItem.getOnSaleQuantity())
                    .setSaleOccupationQuantity(salesPlanItem.getSaleOccupationQuantity())
                    .setSoldQuantity(salesPlanItem.getSoldQuantity())
                    .setFreezedQuantity(salesPlanItem.getFreezedQuantity())
                    .setUnallocatedQuantity(salesPlanItem.getUnallocatedQuantity())
                    .setGuidePrice(salesPlanItem.getGuidePrice())
                    .setSaleGuidePrice(salesPlanItem.getSaleGuidePrice())
                    .setGuidePriceDouble(salesPlanItem.getGuidePrice())
                    .setCurrencyCode(salesPlanItem.getCurrencyCode())
                    .setCurrencyName(salesPlanItem.getCurrencyName())
                    .setBrandPrepaidDiscount(salesPlanItem.getBrandPrepaidDiscount())
                    .setBrandPrepaidUnit(salesPlanItem.getBrandPrepaidUnit())
                    .setYhPrepaidUnit(salesPlanItem.getYhPrepaidUnit())
                    .setYhPrepaidDiscount(salesPlanItem.getYhPrepaidDiscount())
                    .setTotalDiscountDouble(salesPlanItem.getTotalDiscountDouble())
                    .setWholesalePrice(salesPlanItem.getWholesalePrice())
                    .setWholesalePriceDouble(salesPlanItem.getWholesalePriceDouble())
                    .setStartTime(DateUtil.getTime(salesPlanItem.getStartTime()))
                    .setEndTime(DateUtil.getTime(salesPlanItem.getEndTime()))
                    .setCreateTime(DateUtil.getTime(salesPlanItem.getCreateTime()))
                    .setLastUpdateTime(DateUtil.getTime(salesPlanItem.getLastUpdateTime()))
                    .setTracelog(GrpcCommonUtil.getProtoParam(salesPlanItem.getTracelog()))
                    .setCustomerDiscountAmount(salesPlanItem.getCustomerDiscountAmount())
                    .setCustomerDiscountAmountDouble(salesPlanItem.getCustomerDiscountAmountDouble())
                    .setSubtotalDouble(GrpcCommonUtil.getProtoParam(salesPlanItem.getSubtotalDouble()))
                    .build();
            respBuilder.setSalesPlanItem(salesPlanItem1);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 校验同货品在指定时间指定AD 所有的销售计划记录，以便校验是否有多条
     * 获取该货品分配给客户的所有记录
     * <p>
     * rpcHeader
     * projectId
     * distributorId
     * productId
     *
     * @return
     */
    @Override
    public void getProductCustomer(PlanSaleItemStructure.GetProductCustomerReq req,
                                   StreamObserver<PlanSaleItemStructure.GetProductCustomerResp> responseObserver) {

        PlanSaleItemStructure.GetProductCustomerResp.Builder respBuilder = PlanSaleItemStructure.GetProductCustomerResp.newBuilder();

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String projectId = req.getProjectId();
        String distributorId = req.getDistributorId();
        String productId = req.getProductId();
        long startDate = req.getStartDate();
        long endDate = req.getEndDate();

        logger.info("#traceId={}# [IN][getProductCustomer] params: projectId={}, distributorId={}, productId={}, startDate={}, endDate={}",
                rpcHeader.getTraceId(), projectId, distributorId, productId, startDate, endDate);
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
            List<SalesPlanItem> salesPlanItems = salePlanItemDao.selectProductCustomer(tablePrefix,
                    distributorId,
                    productId,
                    DateUtil.long2Date(startDate),
                    DateUtil.long2Date(endDate));
            for (SalesPlanItem salesPlanItem : salesPlanItems) {
                PlanSaleItemStructure.SalesPlanItem salesPlanItem1 = PlanSaleItemStructure.SalesPlanItem.newBuilder()
                        .setItemId(salesPlanItem.getItemId())
                        .setItemStatus(salesPlanItem.getItemStatus())
                        .setSalesPlanNo(salesPlanItem.getSalesPlanNo())
                        .setCustomerId(salesPlanItem.getCustomerId())
                        .setCustomerName(salesPlanItem.getCustomerName())
                        .setProjectId(salesPlanItem.getProjectId())
                        .setProjectName(salesPlanItem.getProjectName())
                        .setProductId(salesPlanItem.getProductId())
                        .setProductModle(salesPlanItem.getProductCode())
                        .setProductName(salesPlanItem.getProductName())
                        .setDistributorId(salesPlanItem.getDistributorId())
                        .setDistributorName(salesPlanItem.getDistributorName())
                        .setOnSaleQuantity(salesPlanItem.getOnSaleQuantity())
                        .setSaleOccupationQuantity(salesPlanItem.getSaleOccupationQuantity())
                        .setSoldQuantity(salesPlanItem.getSoldQuantity())
                        .setFreezedQuantity(salesPlanItem.getFreezedQuantity())
                        .setUnallocatedQuantity(salesPlanItem.getUnallocatedQuantity())
                        .setGuidePrice(salesPlanItem.getGuidePrice())
                        .setGuidePriceDouble(salesPlanItem.getGuidePrice())
                        .setCurrencyCode(salesPlanItem.getCurrencyCode())
                        .setCurrencyName(salesPlanItem.getCurrencyName())
                        .setBrandPrepaidDiscount(salesPlanItem.getBrandPrepaidDiscount())
                        .setBrandPrepaidUnit(salesPlanItem.getBrandPrepaidUnit())
                        .setYhPrepaidUnit(salesPlanItem.getYhPrepaidUnit())
                        .setYhPrepaidDiscount(salesPlanItem.getYhPrepaidDiscount())
                        .setTotalDiscountDouble(salesPlanItem.getTotalDiscountDouble())
                        .setWholesalePrice(salesPlanItem.getWholesalePrice())
                        .setWholesalePriceDouble(salesPlanItem.getWholesalePriceDouble())
                        .setStartTime(DateUtil.getTime(salesPlanItem.getStartTime()))
                        .setEndTime(DateUtil.getTime(salesPlanItem.getEndTime()))
                        .setCreateTime(DateUtil.getTime(salesPlanItem.getCreateTime()))
                        .setLastUpdateTime(DateUtil.getTime(salesPlanItem.getLastUpdateTime()))
                        .setTracelog(GrpcCommonUtil.getProtoParam(salesPlanItem.getTracelog()))
                        .setCustomerDiscountAmount(salesPlanItem.getCustomerDiscountAmount())
                        .setCustomerDiscountAmountDouble(salesPlanItem.getCustomerDiscountAmountDouble())
                        .setSubtotalDouble(GrpcCommonUtil.getProtoParam(salesPlanItem.getSubtotalDouble()))
                        .build();
                respBuilder.addSalesPlanItemList(salesPlanItem1);
            }
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] getProductCustomer success: salesPlanItem.size={}", rpcHeader.getTraceId(), salesPlanItems.size());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }


    /**
     * 更新销售占用
     * 预计场景：AD下单审核通过前为销售占用 或这 取消订单时会释放销售占用
     *
     * @param req
     * @param responseObserver
     */
    @Override
    public void updateSaleOccupation(PlanSaleItemStructure.UpdateSaleOccupationReq req,
                                     StreamObserver<PlanSaleItemStructure.UpdateSaleOccupationResp> responseObserver) {

        PlanSaleItemStructure.UpdateSaleOccupationResp.Builder respBuilder = PlanSaleItemStructure.UpdateSaleOccupationResp.newBuilder();

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String distributorId = req.getDistributorId();
        String projectId = req.getProjectId();
        String productId = req.getProductId();
        long businessDate = req.getBusinessDate();
        int saleOccupationQuantity = req.getSaleOccupationQuantity();
        logger.info("#traceId={}# [IN][updateSaleOccupation] params: projectId={}, distributorId={} ,productId={}, " +
                        "businessDate={}, saleOccupationQuantity={}",
                rpcHeader.getTraceId(), distributorId, productId, businessDate, saleOccupationQuantity);
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));

            //找到对应的预售记录
            SalesPlanItem salesPlanItem = salePlanItemDao.getCustomerProductList(tablePrefix,
                    distributorId,
                    productId,
                    DateUtil.long2Date(businessDate));
            if (salesPlanItem == null) {
                logger.info("#traceId={}# [OUT] fail to getCustomerPlanInfo", rpcHeader.getTraceId());
                GongxiaoRpc.RpcResult rpcResult = GongxiaoRpc.RpcResult.newBuilder()
                        .setReturnCode(ErrorCode.NOT_VALID_PLAN_SALE_ORDER.getErrorCode())
                        .setMsg(ErrorCode.NOT_VALID_PLAN_SALE_ORDER.getMessage()).build();
                respBuilder.setRpcResult(rpcResult);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                logger.info("#traceId={}# [OUT] updateSaleOccupation fail: ", rpcHeader.getTraceId());
                return;
            }
            //数据校验
            Long itemId = salesPlanItem.getItemId();
            int unallocatedQuantity = salesPlanItem.getUnallocatedQuantity();
            int saleOccupationQuantityAlready = salesPlanItem.getSaleOccupationQuantity();
            unallocatedQuantity -= saleOccupationQuantity;
            if (unallocatedQuantity < 0) {
                GongxiaoRpc.RpcResult rpcResult = GongxiaoRpc.RpcResult.newBuilder()
                        .setReturnCode(ErrorCode.OVERTAKE_MAX_ALLOCATE_NUMBER.getErrorCode())
                        .setMsg(ErrorCode.OVERTAKE_MAX_ALLOCATE_NUMBER.getMessage()).build();
                respBuilder.setRpcResult(rpcResult);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                logger.info("#traceId={}# [OUT] updateSaleOccupation fail: ", rpcHeader.getTraceId());
                return;
            }
            //更新销售占用
            saleOccupationQuantityAlready += saleOccupationQuantity;
            salePlanItemDao.updateSaleOccupationQuantity(tablePrefix,
                    itemId,
                    unallocatedQuantity,
                    saleOccupationQuantityAlready);
            logger.info("#traceId={}# [OUT] updateSaleOccupation success: ", rpcHeader.getTraceId());
            GongxiaoRpc.RpcResult rpcResult = GongxiaoRpc.RpcResult.newBuilder()
                    .setReturnCode(ErrorCode.SUCCESS.getErrorCode())
                    .setMsg(ErrorCode.SUCCESS.getMessage()).build();
            respBuilder.setRpcResult(rpcResult);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] updateSaleOccupation success: ", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 更新已售数量信息
     *
     * 预计场景：AD订单被商务审核通过后，会更新已售数量
     * <p>
     * rpcHeader
     * projectId     必填  项目ID
     * distributorId 必填  经销商ID
     * productCode   必填  商品ID
     * businessDate  必填  业务发生时间
     * soldQuantity  必填  已售数量
     *
     * @return
     */
    @Override
    public void updateSoldQuantity(PlanSaleItemStructure.UpdateSoldQuantityReq req,
                                   StreamObserver<PlanSaleItemStructure.UpdateSoldQuantityResp> responseObserver) {

        PlanSaleItemStructure.UpdateSoldQuantityResp.Builder respBuilder = PlanSaleItemStructure.UpdateSoldQuantityResp.newBuilder();

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String projectId = req.getProjectId();
        String distributorId = req.getDistributorId();
        String productModle = req.getProductModle();
        long businessDate = req.getBusinessDate();
        int soldQuantity = req.getSoldQuantity();
        logger.info("#traceId={}# [IN][updateSoldQuantity] params: projectId={}, distributorId={} ,productModle={}, " +
                        "businessDate={}, soldQuantity={}",
                rpcHeader.getTraceId(), projectId, distributorId, productModle, businessDate, soldQuantity);
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
            //找到对应的预售记录
            SalesPlanItem salesPlanItem = salePlanItemDao.getCustomerProductList(tablePrefix,
                    distributorId,
                    productModle,
                    DateUtil.long2Date(businessDate));
            if (salesPlanItem == null) {
                logger.info("#traceId={}# [OUT] fail to getCustomerPlanInfo", rpcHeader.getTraceId());
                respBuilder.setRpcResult(GongxiaoRpc.RpcResult.newBuilder()
                        .setReturnCode(ErrorCode.NOT_VALID_PLAN_SALE_ORDER.getErrorCode())
                        .setMsg(ErrorCode.NOT_VALID_PLAN_SALE_ORDER.getMessage()));
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            //数据校验
            int unallocatedQuantity = salesPlanItem.getUnallocatedQuantity();
            unallocatedQuantity -= soldQuantity;
            if (unallocatedQuantity < 0) {
                respBuilder.setRpcResult(GongxiaoRpc.RpcResult.newBuilder()
                        .setReturnCode(ErrorCode.OVERTAKE_MAX_ALLOCATE_NUMBER.getErrorCode())
                        .setMsg(ErrorCode.OVERTAKE_MAX_ALLOCATE_NUMBER.getMessage()));
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            //更新已售数量
            Long itemId = salesPlanItem.getItemId();
            int soldQuantityAlready = salesPlanItem.getSoldQuantity();
            soldQuantityAlready += soldQuantity;
            salePlanItemDao.updateSoldQuantity(tablePrefix,
                    itemId,
                    unallocatedQuantity,
                    soldQuantityAlready);
            respBuilder.setRpcResult(GongxiaoRpc.RpcResult.newBuilder()
                    .setReturnCode(ErrorCode.SUCCESS.getErrorCode())
                    .setMsg(ErrorCode.SUCCESS.getMessage()));
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] updateSoldQuantity success: ", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 获取有效客户列表 TODO: 确认是否有使用
     * <p>
     * rpcHeader
     * projectId     必填  项目ID
     * distributorId 必填  经销商ID
     * productCode   必填  货品ID
     * businessDate  必填  业务发生时间
     *
     * @return
     */
    @Override
    public void selectSalePlanItemListConditions(PlanSaleItemStructure.SelectSalePlanItemListConditionsReq req,
                                                 StreamObserver<PlanSaleItemStructure.SelectSalePlanItemListConditionsResp> responseObserver) {

        PlanSaleItemStructure.SelectSalePlanItemListConditionsResp.Builder respBuilder = PlanSaleItemStructure.SelectSalePlanItemListConditionsResp.newBuilder();

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String projectId = req.getProjectId();
        String distributorId = req.getDistributorId();
        String productModle = req.getProductModle();
        long businessDate = req.getBusinessDate();
        int pageNum = req.getPageNum();
        int pageSize = req.getPageSize();
        logger.info("#traceId={}# [IN][selectSalePlanItemListConditions] params: projectId={}, distributorId={} ,productModel={}, " +
                        "businessDate={}, pageNumber={}, pageSize={}",
                rpcHeader.getTraceId(), projectId, distributorId, productModle, businessDate, pageNum, pageSize);
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
            //启动分页
            PageHelper.startPage(pageNum, pageSize);
            List<SalesPlanItem> salesPlanItemList = salePlanItemDao.selectItemListByCondition(tablePrefix, productModle, distributorId, DateUtil.long2Date(businessDate));
            for (SalesPlanItem salesPlanItem : salesPlanItemList) {
                salesPlanItem.setGuidePriceDouble(salesPlanItem.getGuidePrice() / FXConstant.MILLION);
                salesPlanItem.setWholesalePriceDouble(salesPlanItem.getWholesalePrice() / FXConstant.MILLION);
                double totalDiscountDouble =
                        (salesPlanItem.getYhPrepaidDiscount() + salesPlanItem.getBrandPrepaidDiscount()) / FXConstant.MILLION;
                salesPlanItem.setTotalDiscountDouble(totalDiscountDouble);
            }
            PageInfo<SalesPlanItem> pageResult = new PageInfo<SalesPlanItem>(salesPlanItemList);
            if (salesPlanItemList.size() == 0) {
                logger.info("#traceId={}# [OUT] fail to selectSalePlanItemList", rpcHeader.getTraceId());
                responseObserver.onCompleted();
                return;
            }
            List<SalesPlanItem> list = pageResult.getList();
            for (SalesPlanItem salesPlanItem : list) {
                PlanSaleItemStructure.SalesPlanItem salesPlanItem1 = PlanSaleItemStructure.SalesPlanItem.newBuilder()
                        .setItemId(salesPlanItem.getItemId())
                        .setItemStatus(salesPlanItem.getItemStatus())
                        .setSalesPlanNo(salesPlanItem.getSalesPlanNo())
                        .setCustomerId(salesPlanItem.getCustomerId())
                        .setCustomerName(salesPlanItem.getCustomerName())
                        .setProjectId(salesPlanItem.getProjectId())
                        .setProjectName(salesPlanItem.getProjectName())
                        .setProductId(salesPlanItem.getProductId())
                        .setProductModle(salesPlanItem.getProductCode())
                        .setProductName(salesPlanItem.getProductName())
                        .setDistributorId(salesPlanItem.getDistributorId())
                        .setDistributorName(salesPlanItem.getDistributorName())
                        .setOnSaleQuantity(salesPlanItem.getOnSaleQuantity())
                        .setSaleOccupationQuantity(salesPlanItem.getSaleOccupationQuantity())
                        .setSoldQuantity(salesPlanItem.getSoldQuantity())
                        .setFreezedQuantity(salesPlanItem.getFreezedQuantity())
                        .setUnallocatedQuantity(salesPlanItem.getUnallocatedQuantity())
                        .setGuidePrice(salesPlanItem.getGuidePrice())
                        .setGuidePriceDouble(salesPlanItem.getGuidePrice())
                        .setCurrencyCode(salesPlanItem.getCurrencyCode())
                        .setCurrencyName(salesPlanItem.getCurrencyName())
                        .setBrandPrepaidDiscount(salesPlanItem.getBrandPrepaidDiscount())
                        .setBrandPrepaidUnit(salesPlanItem.getBrandPrepaidUnit())
                        .setYhPrepaidUnit(salesPlanItem.getYhPrepaidUnit())
                        .setYhPrepaidDiscount(salesPlanItem.getYhPrepaidDiscount())
                        .setTotalDiscountDouble(salesPlanItem.getTotalDiscountDouble())
                        .setWholesalePrice(salesPlanItem.getWholesalePrice())
                        .setWholesalePriceDouble(salesPlanItem.getWholesalePriceDouble())
                        .setStartTime(DateUtil.getTime(salesPlanItem.getStartTime()))
                        .setEndTime(DateUtil.getTime(salesPlanItem.getEndTime()))
                        .setCreateTime(DateUtil.getTime(salesPlanItem.getCreateTime()))
                        .setLastUpdateTime(DateUtil.getTime(salesPlanItem.getLastUpdateTime()))
                        .setTracelog(GrpcCommonUtil.getProtoParam(salesPlanItem.getTracelog()))
                        .setCustomerDiscountAmount(salesPlanItem.getCustomerDiscountAmount())
                        .setCustomerDiscountAmountDouble(salesPlanItem.getCustomerDiscountAmountDouble())
                        .setSubtotalDouble(GrpcCommonUtil.getProtoParam(salesPlanItem.getSubtotalDouble()))
                        .build();
                respBuilder.addSalesPlanItemList(salesPlanItem1);
            }
            respBuilder.setTotal(pageResult.getTotal());
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] selectSalePlanItemList success: salesPlanItemList.size()={}", rpcHeader.getTraceId(), salesPlanItemList.size());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 获取客户有效的货品信息列表
     *
     * 查询AD下面有哪些有效货品
     *
     * <p>
     * rpcHeader rpc调用的header
     * queryStr  查询
     *
     * @return
     */
    @Override
    public void searchProductByCustomer(PlanSaleItemStructure.SearchProductByCustomerReq req,
                                        StreamObserver<PlanSaleItemStructure.SearchProductByCustomerResp> responseObserver) {

        PlanSaleItemStructure.SearchProductByCustomerResp.Builder respBuilder = PlanSaleItemStructure.SearchProductByCustomerResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String queryStr = req.getQueryStr();
        long projectId = req.getProjectId();
        long distributorId = req.getDistributorId();
        logger.info("#traceId={}# [IN][searchProductByCustomer] params: queryStr={} ", rpcHeader.getTraceId(), queryStr);
        List<SalesPlanItem> resultList;
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);

            //1. 获取AD的用户信息
            DistributorStructure.GetDistributorAccountByIdReq distributorBusinessByIdReq = DistributorStructure.GetDistributorAccountByIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setDistributorUserId(distributorId)
                    .build();
            DistributorStructure.GetDistributorAccountByIdResp resp = null;
            try {
                DistributorServiceGrpc.DistributorServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(DistributorServiceGrpc.DistributorServiceBlockingStub.class);
                resp = rpcStub.getDistributorAccountById(distributorBusinessByIdReq);
            } catch (Exception e) {
                logger.error("#traceId={}# [IN][searchProductByCustomer]  获取用户信息失败", rpcHeader.getTraceId());
            }
            DistributorStructure.DistributorUser distributorUser = resp.getDistributorUser();
            //2. 获取AD用户业务信息（项目维度）
            DistributorStructure.GetDistributorBusinessByIdReq getDistributorBusinessByIdReq = DistributorStructure.GetDistributorBusinessByIdReq.newBuilder()
                    .setDistributorBusinessId(distributorUser.getDistributorBusinessId())
                    .setRpcHeader(rpcHeader)
                    .build();
            DistributorStructure.GetDistributorBusinessByIdResp resp1 = null;
            try {
                DistributorServiceGrpc.DistributorServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(DistributorServiceGrpc.DistributorServiceBlockingStub.class);
                resp1 = rpcStub.getDistributorBusinessById(getDistributorBusinessByIdReq);
            } catch (Exception e) {
                e.printStackTrace();
            }
            DistributorStructure.DistributorBusiness distributorBusiness = resp1.getDistributorBusiness();
            resultList = salePlanItemDao.searchProductByCustomer(tablePrefix,
                    distributorBusiness.getDistributorBusinessId() + "",
                    queryStr,
                    new Date());
            for (SalesPlanItem salesPlanItem : resultList) {
                PlanSaleItemStructure.SalesPlanItem salesPlanItem1 = PlanSaleItemStructure.SalesPlanItem.newBuilder()
                        .setItemId(salesPlanItem.getItemId())
                        .setItemStatus(salesPlanItem.getItemStatus())
                        .setSalesPlanNo(salesPlanItem.getSalesPlanNo())
                        .setCustomerId(salesPlanItem.getCustomerId())
                        .setCustomerName(salesPlanItem.getCustomerName())
                        .setProjectId(salesPlanItem.getProjectId())
                        .setProjectName(salesPlanItem.getProjectName())
                        .setProductId(salesPlanItem.getProductId())
                        .setProductModle(salesPlanItem.getProductCode())
                        .setProductName(salesPlanItem.getProductName())
                        .setDistributorId(salesPlanItem.getDistributorId())
                        .setDistributorName(salesPlanItem.getDistributorName())
                        .setOnSaleQuantity(salesPlanItem.getOnSaleQuantity())
                        .setSaleOccupationQuantity(salesPlanItem.getSaleOccupationQuantity())
                        .setSoldQuantity(salesPlanItem.getSoldQuantity())
                        .setFreezedQuantity(salesPlanItem.getFreezedQuantity())
                        .setUnallocatedQuantity(salesPlanItem.getUnallocatedQuantity())
                        .setGuidePrice(salesPlanItem.getGuidePrice())
                        .setGuidePriceDouble(salesPlanItem.getGuidePrice())
                        .setCurrencyCode(salesPlanItem.getCurrencyCode())
                        .setCurrencyName(salesPlanItem.getCurrencyName())
                        .setBrandPrepaidDiscount(salesPlanItem.getBrandPrepaidDiscount())
                        .setBrandPrepaidUnit(salesPlanItem.getBrandPrepaidUnit())
                        .setYhPrepaidUnit(salesPlanItem.getYhPrepaidUnit())
                        .setYhPrepaidDiscount(salesPlanItem.getYhPrepaidDiscount())
                        .setTotalDiscountDouble(salesPlanItem.getTotalDiscountDouble())
                        .setWholesalePrice(salesPlanItem.getWholesalePrice())
                        .setWholesalePriceDouble(salesPlanItem.getWholesalePriceDouble())
                        .setStartTime(DateUtil.getTime(salesPlanItem.getStartTime()))
                        .setEndTime(DateUtil.getTime(salesPlanItem.getEndTime()))
                        .setCreateTime(DateUtil.getTime(salesPlanItem.getCreateTime()))
                        .setLastUpdateTime(DateUtil.getTime(salesPlanItem.getLastUpdateTime()))
                        .setTracelog(GrpcCommonUtil.getProtoParam(salesPlanItem.getTracelog()))
                        .setCustomerDiscountAmount(salesPlanItem.getCustomerDiscountAmount())
                        .setCustomerDiscountAmountDouble(salesPlanItem.getCustomerDiscountAmountDouble())
                        .setSubtotalDouble(GrpcCommonUtil.getProtoParam(salesPlanItem.getSubtotalDouble()))
                        .build();
                respBuilder.addSalesPlanItemList(salesPlanItem1);

            }
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
        logger.info("#traceId={}# [OUT] searchProductByCustomer success: resultList.size={} ", rpcHeader.getTraceId(), resultList.size());
    }

    /**
     * 通过itemid, 获取可售信息
     *
     * TODO：添加使用场景
     * <p>
     * rpcHeader
     * itemId
     *
     * @return
     */
    @Override
    public void getOnSaleQuantity(PlanSaleItemStructure.GetOnSaleQuantityReq req,
                                  StreamObserver<PlanSaleItemStructure.GetOnSaleQuantityResp> responseObserver) {

        PlanSaleItemStructure.GetOnSaleQuantityResp.Builder respBuilder = PlanSaleItemStructure.GetOnSaleQuantityResp.newBuilder();

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String itemId = req.getItemId();
        long projectId = req.getProjectId();
        logger.info("#traceId={}# [IN][searchProductByCustomer] params: queryStr={} projectId={}, projectId={}",
                rpcHeader.getTraceId(), itemId, projectId);
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);

            SalesPlanItem salesPlanItem = salePlanItemDao.getByItemId(tablePrefix,itemId);
            PlanSaleItemStructure.SalesPlanItem salesPlanItem1 = PlanSaleItemStructure.SalesPlanItem.newBuilder()
                    .setItemId(salesPlanItem.getItemId())
                    .setItemStatus(salesPlanItem.getItemStatus())
                    .setSalesPlanNo(salesPlanItem.getSalesPlanNo())
                    .setCustomerId(salesPlanItem.getCustomerId())
                    .setCustomerName(salesPlanItem.getCustomerName())
                    .setProjectId(salesPlanItem.getProjectId())
                    .setProjectName(salesPlanItem.getProjectName())
                    .setProductId(salesPlanItem.getProductId())
                    .setProductModle(salesPlanItem.getProductCode())
                    .setProductName(salesPlanItem.getProductName())
                    .setDistributorId(salesPlanItem.getDistributorId())
                    .setDistributorName(salesPlanItem.getDistributorName())
                    .setOnSaleQuantity(salesPlanItem.getOnSaleQuantity())
                    .setSaleOccupationQuantity(salesPlanItem.getSaleOccupationQuantity())
                    .setSoldQuantity(salesPlanItem.getSoldQuantity())
                    .setFreezedQuantity(salesPlanItem.getFreezedQuantity())
                    .setUnallocatedQuantity(salesPlanItem.getUnallocatedQuantity())
                    .setGuidePrice(salesPlanItem.getGuidePrice())
                    .setGuidePriceDouble(salesPlanItem.getGuidePrice())
                    .setCurrencyCode(salesPlanItem.getCurrencyCode())
                    .setCurrencyName(salesPlanItem.getCurrencyName())
                    .setBrandPrepaidDiscount(salesPlanItem.getBrandPrepaidDiscount())
                    .setBrandPrepaidUnit(salesPlanItem.getBrandPrepaidUnit())
                    .setYhPrepaidUnit(salesPlanItem.getYhPrepaidUnit())
                    .setYhPrepaidDiscount(salesPlanItem.getYhPrepaidDiscount())
                    .setTotalDiscountDouble(salesPlanItem.getTotalDiscountDouble())
                    .setWholesalePrice(salesPlanItem.getWholesalePrice())
                    .setWholesalePriceDouble(salesPlanItem.getWholesalePriceDouble())
                    .setStartTime(DateUtil.getTime(salesPlanItem.getStartTime()))
                    .setEndTime(DateUtil.getTime(salesPlanItem.getEndTime()))
                    .setCreateTime(DateUtil.getTime(salesPlanItem.getCreateTime()))
                    .setLastUpdateTime(DateUtil.getTime(salesPlanItem.getLastUpdateTime()))
                    .setTracelog(GrpcCommonUtil.getProtoParam(salesPlanItem.getTracelog()))
                    .setCustomerDiscountAmount(salesPlanItem.getCustomerDiscountAmount())
                    .setCustomerDiscountAmountDouble(salesPlanItem.getCustomerDiscountAmountDouble())
                    .setSubtotalDouble(GrpcCommonUtil.getProtoParam(salesPlanItem.getSubtotalDouble()))
                    .build();
            respBuilder.setSalesPlanItem(salesPlanItem1);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [IN][searchProductByCustomer] success", rpcHeader.getTraceId());

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * AD取消订单
     *
     * rpcHeader
     * projectId 项目ID
     * distributorId 经销商id
     * productCode 货品CODE
     * businessDate 业务日期
     * saleOccupationQuantity 销售占用
     * soldQuantity  已售数量
     *
     * @return
     */
    public void cancelCustomerOrder(PlanSaleItemStructure.CancelCustomerOrderReq req,
                                    StreamObserver<PlanSaleItemStructure.CancelCustomerOrderResp> responseObserver) {

        PlanSaleItemStructure.CancelCustomerOrderResp.Builder respBuilder = PlanSaleItemStructure.CancelCustomerOrderResp.newBuilder();

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String projectId = req.getProjectId();
        String distributorId = req.getDistributorId();
        String productModle = req.getProductModle();
        long businessDate = req.getBusinessDate();
        int saleOccupationQuantity = req.getSaleOccupationQuantity();
        int soldQuantity = req.getSoldQuantity();
        logger.info("#traceId={}# [IN][cancelCustomerOrder] params: projectId={} projectId={}, distributorId={}, productModle={},businessDate{},saleOccupationQuantity={}, soldQuantity={}"
                , rpcHeader.getTraceId(), projectId, distributorId, productModle, businessDate, saleOccupationQuantity, soldQuantity);
        try {

            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));

            //作废明细
            int i = salePlanItemDao.cancelCustomerOrder(tablePrefix,
                    distributorId,
                    productModle,
                    DateUtil.long2Date(businessDate),
                    saleOccupationQuantity,
                    soldQuantity);
            //还原预售单数量 TODO：还原汇总表的 可售数量/...

            respBuilder.setNumber(i);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }
}
