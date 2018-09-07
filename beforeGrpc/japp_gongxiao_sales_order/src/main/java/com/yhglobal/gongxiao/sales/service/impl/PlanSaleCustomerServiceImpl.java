package com.yhglobal.gongxiao.sales.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.sales.dao.SalePlanDao;
import com.yhglobal.gongxiao.sales.dao.SalePlanItemDao;
import com.yhglobal.gongxiao.sales.model.plan.model.SalesPlanItem;
import com.yhglobal.gongxiao.sales.model.plan.vo.CustomerPlanInfo;
import com.yhglobal.gongxiao.sales.model.plan.vo.CustomerPlanItemInfo;
import com.yhglobal.gongxiao.sales.service.PlanSaleCustomerService;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.DoubleScale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
    public class PlanSaleCustomerServiceImpl implements PlanSaleCustomerService {

    private static Logger logger = LoggerFactory.getLogger(PlanSaleCustomerServiceImpl.class);


    @Autowired
    SalePlanDao salePlanDao;    //销售计划

    @Autowired
    SalePlanItemDao salePlanItemDao; //销售计划明细

    @Override
    public PageInfo<CustomerPlanInfo> selectCustomerPlanList(RpcHeader rpcHeader,
                                                             String projectId,
                                                             String distributorId,
                                                             String distributorName,
                                                             int pageNumber,
                                                             int pageSize) {
        logger.info("#traceId={}# [IN][selectCustomerPlanList] params: projectId={}, distributorId={}, distributorName={} ",
                rpcHeader.traceId, projectId, distributorId, distributorName);

        try {
            PageHelper.startPage(pageNumber, pageSize);
            //1)获取经销商列表
            List<String> distributorIdList = salePlanItemDao.selectCustomerPlanItemList(projectId, distributorId, distributorName);
            PageInfo<String> distributorIdListPage = new PageInfo<String>(distributorIdList);

            List<CustomerPlanInfo> customerPlanInfoList = new ArrayList<>();
            //Map<产品型号 ,数量>
            Map<String, Integer> map = new HashMap<>();
            for (String distributorIdDb : distributorIdListPage.getList()) {
                CustomerPlanInfo customerPlanInfo = new CustomerPlanInfo();
                int onSaleQuantity = 0;
                int saleOccupationQuantity = 0;
                int soldQuantity = 0;
                //2) 获取该客户的所有预售信息
                List<SalesPlanItem> salesPlanItems = salePlanItemDao.selectCustomerSalePlanItemList(projectId, distributorIdDb);
                if (salesPlanItems.size()==0){
                    continue;
                }
                //3) 整合该客户的所有预售信息
                for (SalesPlanItem salesPlanItem : salesPlanItems) {
                    //设置基础信息 会重复设置
                    customerPlanInfo.setProjectId(salesPlanItem.getProjectId() + "");
                    customerPlanInfo.setProjectName(salesPlanItem.getProjectName());
                    customerPlanInfo.setBrandId(salesPlanItem.getBrandId());
                    customerPlanInfo.setBrandName(salesPlanItem.getBrandName());
                    customerPlanInfo.setDistributorId(salesPlanItem.getDistributorId() + "");
                    customerPlanInfo.setDistributorName(salesPlanItem.getDistributorName());

                    String productCode = salesPlanItem.getProductCode();
                    Integer number = map.get(productCode);
                    if (number != null) {
                        map.put(productCode, number + 1);
                    } else {
                        map.put(productCode, 1);
                    }
                    onSaleQuantity += salesPlanItem.getOnSaleQuantity() ;
                    saleOccupationQuantity += salesPlanItem.getSaleOccupationQuantity() ;
                    soldQuantity += salesPlanItem.getSoldQuantity() ;
                }
                customerPlanInfo.setOnSaleQuantity(onSaleQuantity);
                customerPlanInfo.setSaleOccupationQuantity(saleOccupationQuantity);
                customerPlanInfo.setSoldQuantity(soldQuantity);
                customerPlanInfo.setOnSaleCategory(map.size());
                customerPlanInfo.setRemainQuantity(onSaleQuantity-saleOccupationQuantity-soldQuantity);
                customerPlanInfoList.add(customerPlanInfo);
            }
            PageInfo<CustomerPlanInfo> customerPlanInfoPageInfo = new PageInfo<CustomerPlanInfo>(customerPlanInfoList);
            customerPlanInfoPageInfo.setTotal(distributorIdListPage.getTotal());
            logger.info("#traceId={}# [OUT] selectCustomerPlanList success: ", rpcHeader.traceId);
            return customerPlanInfoPageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public PageInfo<CustomerPlanItemInfo> selectCustomerPlanItemList(RpcHeader rpcHeader,
                                                                     String projectId,
                                                                     String distributorId,
                                                                     String salePlanNo,
                                                                     String productCode,
                                                                     String productName,
                                                                     int orderStatus,
                                                                     Date startDate,
                                                                     Date endDate,
                                                                     int pageNumber,
                                                                     int pageSize) {
        logger.info("#traceId={}# [IN][selectCustomerPlanItemList] params: projectId={}, distributorId={} ",
                rpcHeader.traceId, projectId, distributorId);
        try {
            PageHelper.startPage(pageNumber, pageSize);
            List<SalesPlanItem> salesPlanItems = salePlanItemDao.selectCustomerSalePlanList(projectId,
                    distributorId,
                    salePlanNo,
                    productCode,
                    productName,
                    orderStatus,
                    startDate,
                    endDate);
            PageInfo<SalesPlanItem> distributorIdListPage = new PageInfo<SalesPlanItem>(salesPlanItems);

            List<CustomerPlanItemInfo> customerPlanItemInfoList = new ArrayList<>();
            for (SalesPlanItem salesPlanItem : distributorIdListPage.getList()) {
                CustomerPlanItemInfo customerPlanItemInfo = new CustomerPlanItemInfo();
                customerPlanItemInfo.setProductCode(salesPlanItem.getProductCode());
                customerPlanItemInfo.setProductName(salesPlanItem.getProductName());
                customerPlanItemInfo.setAllocatedQuantity(salesPlanItem.getOnSaleQuantity() );
                customerPlanItemInfo.setSaleOccupationQuantity(salesPlanItem.getSaleOccupationQuantity() );
                customerPlanItemInfo.setSoldQuantity(salesPlanItem.getSoldQuantity());
                int couldSoldNumber = salesPlanItem.getOnSaleQuantity() - salesPlanItem.getSaleOccupationQuantity() - salesPlanItem.getSoldQuantity();
                customerPlanItemInfo.setCouldBuyNumber(couldSoldNumber);
                customerPlanItemInfo.setCurrencyCode(salesPlanItem.getCurrencyCode());
                customerPlanItemInfo.setGuidePrice(DoubleScale.keepSixBits(salesPlanItem.getGuidePrice()));
                customerPlanItemInfo.setBrandPrepaidDiscount(DoubleScale.keepSixBits(salesPlanItem.getBrandPrepaidDiscount()) );
                customerPlanItemInfo.setYhPrepaidDiscount(DoubleScale.keepSixBits(salesPlanItem.getYhPrepaidDiscount()));
                customerPlanItemInfo.setWholesalePrice(DoubleScale.keepSixBits(salesPlanItem.getWholesalePrice()));

                int itemStatus = salesPlanItem.getItemStatus();
                if (itemStatus==91){
                    customerPlanItemInfo.setRecordStatus("作废");
                }else if (itemStatus==92){
                    customerPlanItemInfo.setRecordStatus("过期");
                }else {
                    customerPlanItemInfo.setRecordStatus("正常");
                }
                customerPlanItemInfo.setSalePlaneNo(salesPlanItem.getSalesPlanNo());
                customerPlanItemInfo.setStartDate(DateUtil.shortDataToFXString(salesPlanItem.getStartTime()));
                customerPlanItemInfo.setEndDate(DateUtil.shortDataToFXString(salesPlanItem.getEndTime()));
                customerPlanItemInfoList.add(customerPlanItemInfo);
            }

            PageInfo<CustomerPlanItemInfo> customerPlanItemInfoPageInfo = new PageInfo<>(customerPlanItemInfoList);
            customerPlanItemInfoPageInfo.setTotal(distributorIdListPage.getTotal());
            logger.info("#traceId={}# [OUT] selectCustomerPlanItemList success: ", rpcHeader.traceId);
            return customerPlanItemInfoPageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }
}

