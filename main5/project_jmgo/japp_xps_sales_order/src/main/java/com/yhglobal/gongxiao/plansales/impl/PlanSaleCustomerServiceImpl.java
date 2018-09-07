package com.yhglobal.gongxiao.plansales.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerServiceGrpc;
import com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure;
import com.yhglobal.gongxiao.plansales.dao.SalePlanDao;
import com.yhglobal.gongxiao.plansales.dao.SalePlanItemDao;
import com.yhglobal.gongxiao.sales.model.plan.model.SalesPlanItem;
import com.yhglobal.gongxiao.sales.model.plan.vo.CustomerPlanInfo;
import com.yhglobal.gongxiao.sales.model.plan.vo.CustomerPlanItemInfo;
import com.yhglobal.gongxiao.util.DoubleScale;
import com.yhglobal.gongxiao.utils.DateUtil;
import com.yhglobal.gongxiao.utils.TablePrefixUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Service
public class PlanSaleCustomerServiceImpl extends PlanSaleCustomerServiceGrpc.PlanSaleCustomerServiceImplBase {

    private static Logger logger = LoggerFactory.getLogger(PlanSaleCustomerServiceImpl.class);

    @Autowired
    SalePlanDao salePlanDao;    //销售计划

    @Autowired
    SalePlanItemDao salePlanItemDao; //销售计划明细

    /**
     * 查询客户预售单计划列表
     * rpcHeader
     * distributorId 经销商ID
     * distributorName 经销商名称
     * pageNumber 页数
     * pageSize 单页数据量
     *
     * @return
     */
    @Override
    public void selectCustomerPlanList(PlanSaleCustomerStructure.SelectCustomerPlanListReq req,
                                       StreamObserver<PlanSaleCustomerStructure.SelectCustomerPlanListResp> responseObserver) {

        PlanSaleCustomerStructure.SelectCustomerPlanListResp.Builder respBuilder = PlanSaleCustomerStructure.SelectCustomerPlanListResp.newBuilder();

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String projectId = req.getProjectId();
        String distributorId = req.getDistributorId();
        String distributorName = req.getDistributorName();
        int pageNumber = req.getPageNumber();
        int pageSize = req.getPageSize();

        logger.info("#traceId={}# [IN][selectCustomerPlanList] params: projectId={}, distributorId={}, distributorName={}, pageNumber={}, pageSize={} ",
                rpcHeader.getTraceId(), projectId, distributorId, distributorName, pageNumber, pageSize);

        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
            PageHelper.startPage(pageNumber, pageSize);
            //1)获取经销商列表
            List<String> distributorIdList = salePlanItemDao.selectCustomerPlanItemList(tablePrefix,
                    distributorId,
                    distributorName);
            PageInfo<String> distributorIdListPage = new PageInfo<String>(distributorIdList);

            List<CustomerPlanInfo> customerPlanInfoList = new ArrayList<>();
            //Map<产品型号 ,数量>
            for (String distributorIdDb : distributorIdListPage.getList()) {
                Map<String, Integer> map = new HashMap<>();
                PlanSaleCustomerStructure.CustomerPlanInfo.Builder customerPlanInfo = PlanSaleCustomerStructure.CustomerPlanInfo.newBuilder();
                int onSaleQuantity = 0;
                int saleOccupationQuantity = 0;
                int soldQuantity = 0;
                //2) 获取该客户的所有预售信息
                List<SalesPlanItem> salesPlanItems = salePlanItemDao.selectCustomerSalePlanItemList(tablePrefix, distributorIdDb);
                if (salesPlanItems.size() == 0) {
                    continue;
                }
                //3) 整合该客户的所有预售信息
                for (SalesPlanItem salesPlanItem : salesPlanItems) {
                    //设置基础信息 会重复设置
                    customerPlanInfo.setProjectId(salesPlanItem.getProjectId() + "");
                    customerPlanInfo.setProjectName(salesPlanItem.getProjectName());
                    customerPlanInfo.setDistributorId(salesPlanItem.getDistributorId() + "");
                    customerPlanInfo.setDistributorName(salesPlanItem.getDistributorName());

                    String productModel = salesPlanItem.getProductCode();
                    Integer number = map.get(productModel);
                    if (number != null) {
                        map.put(productModel, number + 1);
                    } else {
                        map.put(productModel, 1);
                    }
                    onSaleQuantity += salesPlanItem.getOnSaleQuantity();
                    saleOccupationQuantity += salesPlanItem.getSaleOccupationQuantity();
                    soldQuantity += salesPlanItem.getSoldQuantity();
                }
                customerPlanInfo.setOnSaleQuantity(onSaleQuantity);
                customerPlanInfo.setSaleOccupationQuantity(saleOccupationQuantity);
                customerPlanInfo.setSoldQuantity(soldQuantity);
                customerPlanInfo.setOnSaleCategory(map.size());
                customerPlanInfo.setRemainQuantity(onSaleQuantity - saleOccupationQuantity - soldQuantity);

                respBuilder.addCustomerPlanInfoList(customerPlanInfo.build());
            }
            respBuilder.setTotal((int) distributorIdListPage.getTotal());
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] selectCustomerPlanList success: ", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 查询客户预售单明细列表
     * rpcHeader
     * distributorId 经销商ID
     * pageNumber 页数
     * pageSize 单页数据量
     *
     * @return
     */
    @Override
    public void selectCustomerPlanItemList(PlanSaleCustomerStructure.SelectCustomerPlanItemListReq req,
                                           StreamObserver<PlanSaleCustomerStructure.SelectCustomerPlanItemListResp> responseObserver) {

        PlanSaleCustomerStructure.SelectCustomerPlanItemListResp.Builder respBuilder = PlanSaleCustomerStructure.SelectCustomerPlanItemListResp.newBuilder();

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String projectId = req.getProjectId();
        String distributorId = req.getDistributorId();
        String salePlanNo = req.getSalePlanNo();
        String productModle = req.getProductModle();
        String productName = req.getProductName();
        int orderStatus = req.getOrderStatus();
        long startDate = req.getStartDate();
        long endDate = req.getEndDate();
        int pageNumber = req.getPageNumber();
        int pageSize = req.getPageSize();

        logger.info("#traceId={}# [IN][selectCustomerPlanItemList] params: projectId={}, distributorId={} ,salePlaneNo={}, productModel={}, " +
                        "productName={}, orderStatus={}, startDate={}, endDate={}, pageNumber={}, pageSize={} ",
                rpcHeader.getTraceId(), projectId, distributorId, salePlanNo, productModle, productName, orderStatus, startDate, endDate, pageNumber, pageSize);
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
            PageHelper.startPage(pageNumber, pageSize);
            List<SalesPlanItem> salesPlanItems = salePlanItemDao.selectCustomerSalePlanList(tablePrefix,
                    distributorId,
                    salePlanNo,
                    productModle,
                    productName,
                    orderStatus,
                    DateUtil.long2Date(startDate),
                    DateUtil.long2Date(endDate));
            PageInfo<SalesPlanItem> distributorIdListPage = new PageInfo<SalesPlanItem>(salesPlanItems);

            List<CustomerPlanItemInfo> customerPlanItemInfoList = new ArrayList<>();
            for (SalesPlanItem salesPlanItem : distributorIdListPage.getList()) {
                PlanSaleCustomerStructure.CustomerPlanItemInfo.Builder customerPlanItemInfo = PlanSaleCustomerStructure.CustomerPlanItemInfo.newBuilder();
                customerPlanItemInfo.setProductModle(salesPlanItem.getProductCode());
                customerPlanItemInfo.setProductName(salesPlanItem.getProductName());
                customerPlanItemInfo.setAllocatedQuantity(salesPlanItem.getOnSaleQuantity());
                customerPlanItemInfo.setSaleOccupationQuantity(salesPlanItem.getSaleOccupationQuantity());
                customerPlanItemInfo.setSoldQuantity(salesPlanItem.getSoldQuantity());
                int couldSoldNumber = salesPlanItem.getOnSaleQuantity() - salesPlanItem.getSaleOccupationQuantity() - salesPlanItem.getSoldQuantity();
                customerPlanItemInfo.setCouldBuyNumber(couldSoldNumber);
                customerPlanItemInfo.setCurrencyCode(salesPlanItem.getCurrencyCode());
                customerPlanItemInfo.setGuidePrice(DoubleScale.keepSixBits(salesPlanItem.getGuidePrice()));
                customerPlanItemInfo.setBrandPrepaidDiscount(DoubleScale.keepSixBits(salesPlanItem.getBrandPrepaidDiscount()));
                customerPlanItemInfo.setYhPrepaidDiscount(DoubleScale.keepSixBits(salesPlanItem.getYhPrepaidDiscount()));
                customerPlanItemInfo.setWholesalePrice(DoubleScale.keepSixBits(salesPlanItem.getWholesalePrice()));

                int itemStatus = salesPlanItem.getItemStatus();
                if (itemStatus == 91) {
                    customerPlanItemInfo.setRecordStatus("作废");
                } else if (itemStatus == 92) {
                    customerPlanItemInfo.setRecordStatus("过期");
                } else {
                    customerPlanItemInfo.setRecordStatus("正常");
                }
                customerPlanItemInfo.setSalePlaneNo(salesPlanItem.getSalesPlanNo());
                customerPlanItemInfo.setStartDate(DateUtil.shortDataToFXString(salesPlanItem.getStartTime()));
                customerPlanItemInfo.setEndDate(DateUtil.shortDataToFXString(salesPlanItem.getEndTime()));
                respBuilder.addCustomerPlanItemInfoList(customerPlanItemInfo);
            }
            respBuilder.setTotal((int) distributorIdListPage.getTotal());

            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] selectCustomerPlanItemList success: ", rpcHeader.getTraceId());
            return;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }
}
