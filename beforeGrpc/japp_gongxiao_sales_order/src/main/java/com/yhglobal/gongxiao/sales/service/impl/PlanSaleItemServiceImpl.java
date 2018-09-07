package com.yhglobal.gongxiao.sales.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.foundation.distributor.service.DistributorPortalUserService;
import com.yhglobal.gongxiao.foundation.portal.user.model.DistributorPortalUser;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.foundation.product.price.model.ProductPrice;
import com.yhglobal.gongxiao.foundation.product.price.service.ProductPriceService;
import com.yhglobal.gongxiao.payment.flow.DistributorInoutFlow;
import com.yhglobal.gongxiao.sales.dao.SalePlanDao;
import com.yhglobal.gongxiao.sales.dao.SalePlanItemDao;
import com.yhglobal.gongxiao.sales.model.plan.SalePlanItemInfo;
import com.yhglobal.gongxiao.sales.model.plan.dto.SaleItemEditInfo;
import com.yhglobal.gongxiao.sales.model.plan.dto.SalePlanItemBo;
import com.yhglobal.gongxiao.sales.model.plan.model.SalesPlan;
import com.yhglobal.gongxiao.sales.model.plan.model.SalesPlanItem;
import com.yhglobal.gongxiao.sales.service.PlanSaleItemService;

import com.yhglobal.gongxiao.sales.status.SalePlanItemStatus;
import com.yhglobal.gongxiao.util.CommonUtil;
import com.yhglobal.gongxiao.util.DoubleScale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 客户销售计划实现类
 *
 * @author: 陈浩
 * @create: 2018-03-09 11:33
 **/
@Service(timeout = 2000)
public class PlanSaleItemServiceImpl implements PlanSaleItemService {
    private static Logger logger = LoggerFactory.getLogger(PlanSaleServiceImpl.class);

    @Reference
    ProductService productService; //产品服务类

    @Autowired
    SalePlanDao salePlanDao;    //销售计划

    @Autowired
    SalePlanItemDao salePlanItemDao; //销售计划明细

    @Reference
    DistributorPortalUserService distributorPortalUserService;

    @Override
    public int createSalePlanItem(RpcHeader rpcHeader, ArrayList<SalePlanItemInfo> salePlanInfoIns) {
        logger.info("#traceId={}# [IN][insertSalePlanItemInfo] params: ", rpcHeader.traceId);
        try {
            SalesPlanItem salesPlanItem = new SalesPlanItem();
            String salesPlanNo1 = salePlanInfoIns.get(0).getSalesPlanNo();
            SalesPlan salePlan = salePlanDao.getSalePlanNo(salesPlanNo1);

            int i = 0;
            for (SalePlanItemInfo salePlanItemInfo : salePlanInfoIns) {
                salesPlanItem.setItemStatus(SalePlanItemStatus.INIT.getStatus());
                salesPlanItem.setSalesPlanNo(salePlanItemInfo.getSalesPlanNo());

                salesPlanItem.setProjectId(Long.parseLong(salePlanItemInfo.getProjectId()));
                salesPlanItem.setProjectName(salePlanItemInfo.getProjectName());

                salesPlanItem.setProductId(Long.parseLong(salePlanItemInfo.getProductId()));
                salesPlanItem.setProductCode(salePlanItemInfo.getProductCode());
                salesPlanItem.setProductName(salePlanItemInfo.getProductName());

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

                ProductBasic productBasic = productService.getByProductId(rpcHeader, salePlanItemInfo.getProductId());
                long guidePrice =productBasic.getGuidePrice(); //乘1000后的值

                long saleGuidePrice = productBasic.getSaleGuidePrice();
                double saleGuidePriceDouble =(double) saleGuidePrice/FXConstant.MILLION;

                salesPlanItem.setGuidePrice(guidePrice);
                salesPlanItem.setSaleGuidePrice(productBasic.getSaleGuidePrice());
                int onSaleQuantity = salePlanItemInfo.getOnSaleQuantity();

                double brandPrepaidDiscount = salePlanItemInfo.getBrandPrepaidDiscount();
                double yhPrepaidDiscount = salePlanItemInfo.getYhPrepaidDiscount();

                long salePrice = DoubleScale.multipleMillion(saleGuidePriceDouble * (1 - brandPrepaidDiscount-yhPrepaidDiscount)) ;
                salesPlanItem.setWholesalePrice(salePrice);

                String format = "yyyy-MM-dd";
                String startDate = salePlanItemInfo.getStartTime();
                String endDate = salePlanItemInfo.getEndTime();
                Date startDates = startDate == null || "".equals(startDate) ? null : CommonUtil.StringToDateWithFormat(startDate, format);
                Date endDates = endDate == null || "".equals(endDate) ? null : CommonUtil.StringToDateWithFormat(endDate, format);
                salesPlanItem.setStartTime(startDates);
                salesPlanItem.setEndTime(endDates);
                salesPlanItem.setBrandId(salePlan.getBrandId() + "");
                salesPlanItem.setBrandName(salePlan.getBrandName());
                salesPlanItem.setCreateTime(new Date());
                int num = salePlanItemDao.insertSalePlanItem(salesPlanItem);
                //更新销售单
                String salesPlanNo = salePlanItemInfo.getSalesPlanNo();
                int onSaleQuantityOrder = salePlan.getOnSaleQuantity();
                int allocatedQuantityOrder = salePlan.getAllocatedQuantity() + onSaleQuantity;
                int unallocatedQuantityOrder = salePlan.getUnallocatedQuantity() - onSaleQuantity;
                salePlanDao.updateProductQuantity(salesPlanNo, onSaleQuantityOrder, allocatedQuantityOrder, unallocatedQuantityOrder);
                i += num;
            }
            logger.info("#traceId={}# [OUT] insertSalePlanItemInfo success: ", rpcHeader.traceId);
            return i;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public PageInfo<SalePlanItemBo> selectSalePlanItemList(RpcHeader rpcHeader,
                                                           String salesPlanNo,
                                                           String customerId,
                                                           String customerName,
                                                           int pageNumber,
                                                           int pageSize) {
        logger.info("#traceId={}# [IN][getSalePlanItemList] params: salesPlanNo={}, customerId={}, customerName={} ",
                rpcHeader.traceId, salesPlanNo, customerId, customerName);
        try {
            PageHelper.startPage(pageNumber, pageSize);
            List<SalesPlanItem> salesPlanItems = salePlanItemDao.selectBySalePlanNo(salesPlanNo, customerId, customerName);
            PageInfo<SalesPlanItem> salesPlanItemPageInfo = new PageInfo<SalesPlanItem>(salesPlanItems);

            List<SalePlanItemBo> salePlanItemBos = new ArrayList<>();
            for (SalesPlanItem salesPlanItem : salesPlanItems) {
                SalePlanItemBo salePlanItemBo = new SalePlanItemBo();
                salePlanItemBo.setItemId(salesPlanItem.getItemId());
                salePlanItemBo.setSalesPlanNo(salesPlanItem.getSalesPlanNo());
                salePlanItemBo.setCustomerId(salesPlanItem.getCustomerId());
                salePlanItemBo.setCustomerName(salesPlanItem.getCustomerName());
                salePlanItemBo.setOnSaleQuantity(salesPlanItem.getOnSaleQuantity());
                salePlanItemBo.setSaleOccupationQuantity(salesPlanItem.getSaleOccupationQuantity());
                salePlanItemBo.setSoldQuantity(salesPlanItem.getSoldQuantity());
                salePlanItemBo.setUnallocatedQuantity(salesPlanItem.getUnallocatedQuantity());
                salePlanItemBo.setCurrencyCode(salesPlanItem.getCurrencyCode());
                salePlanItemBo.setCurrencyName(salesPlanItem.getCurrencyName());
                salePlanItemBo.setGuidePrice(DoubleScale.keepSixBits(salesPlanItem.getGuidePrice()));
                salePlanItemBo.setBrandPrepaidDiscount(DoubleScale.keepSixBits(salesPlanItem.getBrandPrepaidDiscount()));
                salePlanItemBo.setYhPrepaidDiscount(DoubleScale.keepSixBits(salesPlanItem.getYhPrepaidDiscount()));
                salePlanItemBo.setWholesalePrice(DoubleScale.keepSixBits(salesPlanItem.getWholesalePrice()));
                salePlanItemBo.setStartTime(salesPlanItem.getStartTime());
                salePlanItemBo.setEndTime(salesPlanItem.getEndTime());
                salePlanItemBo.setLastUpdateTime(salesPlanItem.getLastUpdateTime());
                salePlanItemBos.add(salePlanItemBo);
            }
            PageInfo<SalePlanItemBo> salePlanItemBoPageInfo = new PageInfo<SalePlanItemBo>(salePlanItemBos);
            salePlanItemBoPageInfo.setTotal(salesPlanItemPageInfo.getTotal());
            logger.info("#traceId={}# [OUT] getSalePlanItemList success: salePlanItemBos.size={}", rpcHeader.traceId, salePlanItemBos.size());
            return salePlanItemBoPageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public SaleItemEditInfo getEditItemInfo(RpcHeader rpcHeader, String itemId) {
        logger.info("#traceId={}# [IN][getEditItemInfo] params: itemId={}  ", rpcHeader.traceId, itemId);
        try {
            SalesPlanItem salesPlanItem = salePlanItemDao.getByItemId(itemId);
            String salesPlanNo = salesPlanItem.getSalesPlanNo();
            SalesPlan salePlanNo = salePlanDao.getSalePlanNo(salesPlanNo);

            SaleItemEditInfo saleItemEditInfo = new SaleItemEditInfo();
            saleItemEditInfo.setCustomerId(salesPlanItem.getCustomerId());
            saleItemEditInfo.setCustomerName(salesPlanItem.getCustomerName());

            int onSaleQuantity = salesPlanItem.getOnSaleQuantity(); //获取可售总数
            int saleOccupationQuantity = salesPlanItem.getSaleOccupationQuantity(); //获取销售占用
            int soldQuantity = salesPlanItem.getSoldQuantity(); //获取已售数量
            int remianSaleQuantity = onSaleQuantity - saleOccupationQuantity - soldQuantity; //剩余销售总数
            saleItemEditInfo.setRemainSaleAmount(remianSaleQuantity);

            saleItemEditInfo.setUnallocatedQuantity(salePlanNo.getUnallocatedQuantity()); //剩余销售总数

            Long productId = salesPlanItem.getProductId();
            ProductBasic productBasic = productService.getByProductId(rpcHeader, productId + "");
            saleItemEditInfo.setGuidePrice(DoubleScale.keepSixBits(salesPlanItem.getGuidePrice()));
            saleItemEditInfo.setSaleGuidePrice(DoubleScale.keepSixBits(salesPlanItem.getSaleGuidePrice()));
            saleItemEditInfo.setBrandPrepaidUnit(DoubleScale.keepSixBits(salesPlanItem.getBrandPrepaidUnit()));
            saleItemEditInfo.setBrandPrepaidDiscount(DoubleScale.keepSixBits(salesPlanItem.getBrandPrepaidDiscount()));
            saleItemEditInfo.setYhPrepaidUnit(DoubleScale.keepSixBits(salesPlanItem.getYhPrepaidUnit()));
            saleItemEditInfo.setYhPrepaidDiscount(DoubleScale.keepSixBits(salesPlanItem.getYhPrepaidDiscount()));
            saleItemEditInfo.setWholesalePrice(DoubleScale.keepSixBits(salesPlanItem.getWholesalePrice()));
            saleItemEditInfo.setStartTime(salesPlanItem.getStartTime());
            saleItemEditInfo.setEndTime(salesPlanItem.getEndTime());
            saleItemEditInfo.setItemId(salesPlanItem.getItemId());
            logger.info("#traceId={}# [OUT] getEditItemInfo success: ", rpcHeader.traceId);
            return saleItemEditInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public int editSaleItem(RpcHeader rpcHeader,
                            String itemId,
                            int adjustNumber,
                            double brandPrepaidUnitTemp,
                            double brandPrepaidDiscountTemp,
                            double yhPrepaidUnitTemp,
                            double yhPrepaidDiscountTemp,
                            Date startDate,
                            Date endDate
    ) {
        logger.info("#traceId={}# [IN][editSaleItem] params: salesPlanNo={}, #customerId={}, #customerName={}, #traceId={},  " +
                        "salesPlanNo={}, customerId={}, customerName={}, customerName={} ",
                rpcHeader.traceId, itemId, adjustNumber, brandPrepaidUnitTemp, brandPrepaidDiscountTemp, yhPrepaidUnitTemp, yhPrepaidDiscountTemp, startDate, endDate);
        try {
            int brandPrepaidUnit = (int) (brandPrepaidUnitTemp * FXConstant.MILLION);
            int brandPrepaidDiscount = (int) (brandPrepaidDiscountTemp * FXConstant.MILLION);
            int yhPrepaidUnit = (int) (yhPrepaidUnitTemp * FXConstant.MILLION);
            int yhPrepaidDiscount = (int) (yhPrepaidDiscountTemp * FXConstant.MILLION);

            SalesPlanItem salesPlanItem = salePlanItemDao.getByItemId(itemId);
            //1 变数量
            int onSaleQuantity = salesPlanItem.getOnSaleQuantity();
            onSaleQuantity += adjustNumber;
            int unallocatedQuantity1 = salesPlanItem.getUnallocatedQuantity();
            unallocatedQuantity1+=adjustNumber;


            //todo: 参数后台校验
            salePlanItemDao.update(itemId,
                    onSaleQuantity,
                    unallocatedQuantity1,
                    brandPrepaidUnit,
                    brandPrepaidDiscount,
                    yhPrepaidUnit,
                    yhPrepaidDiscount,
                    startDate,
                    endDate);

            SalesPlan salePlanInfo = salePlanDao.getSalePlanNo(salesPlanItem.getSalesPlanNo());

            int allocatedQuantity = salePlanInfo.getAllocatedQuantity();
            int unallocatedQuantity = salePlanInfo.getUnallocatedQuantity();

            allocatedQuantity = allocatedQuantity + adjustNumber;
            unallocatedQuantity = unallocatedQuantity - adjustNumber;
            salePlanDao.updateHandleQuantity(salePlanInfo.getSalesPlanNo(), allocatedQuantity, unallocatedQuantity);
            logger.info("#traceId={}# [OUT] editSaleItem success: ", rpcHeader.traceId);
            return 1;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int cancelPlanItem(RpcHeader rpcHeader, String itemId) {
        logger.info("#traceId={}# [IN][canclePlanItemStatus] params: ", rpcHeader.traceId);
        try {
            int i = salePlanItemDao.updatePlanItemStatus(itemId, SalePlanItemStatus.CANCEL.getStatus());
            logger.info("#traceId={}# [OUT] canclePlanItemStatus success: ", rpcHeader.traceId);
            return i;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int cancelPlan(RpcHeader rpcHeader, String planSaleNo) {
        logger.info("#traceId={}# [IN][cancelPlan] params: planSaleNo={} ", rpcHeader.traceId, planSaleNo);
        try {
            int i = salePlanItemDao.cancelSalePlanItemBySalePlanNo(planSaleNo, SalePlanItemStatus.CANCEL.getStatus());
            logger.info("#traceId={}# [OUT] canclePlanItemStatus success: 变更条数={}", rpcHeader.traceId,i);
            return i;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public SalesPlanItem getCustomerPlanInfo(RpcHeader rpcHeader, String projectId, String distributorId, String productId, Date businessDate) {
        logger.info("#traceId={}# [IN][getCustomerPlanInfo] params: projectId={}, distributorId={} ,productId={}",
                rpcHeader.traceId, distributorId, productId);
        try {
            SalesPlanItem salesPlanItem = salePlanItemDao.getCustomerProduct(projectId, distributorId, productId, businessDate);
            if (salesPlanItem == null) {
                logger.info("#traceId={}# [OUT] fail to getCustomerPlanInfo", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] getCustomerPlanInfo success: salesPlanItem={}", rpcHeader.traceId, salesPlanItem.toString());
            }
            return salesPlanItem;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<SalesPlanItem> getProductCustomer(RpcHeader rpcHeader, String projectId, String distributorId, String productId,Date startDate, Date endDate) {
        logger.info("#traceId={}# [IN][getProductCustomer] params: projectId={}, distributorId={}, productId={}", rpcHeader.traceId);
        try {
            List<SalesPlanItem> salesPlanItems = salePlanItemDao.selectProductCustomer(projectId, distributorId, productId,startDate,endDate);
            logger.info("#traceId={}# [OUT] getProductCustomer success: salesPlanItem={}", rpcHeader.traceId, salesPlanItems.size());
            return salesPlanItems;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public RpcResult updateSaleOccupation(RpcHeader rpcHeader,
                                          String projectId,
                                          String distributorId,
                                          String productId,
                                          Date businessDate,
                                          int changeQuantity) {
        logger.info("#traceId={}# [IN][updateSaleOccupation] params: projectId={}, distributorId={} ,productId={}, businessDate={}, changeQuantity={}",
                rpcHeader.traceId, distributorId, productId, businessDate, changeQuantity);
        try {
            //找到对应的预售记录
            SalesPlanItem salesPlanItem = salePlanItemDao.getCustomerProduct(projectId, distributorId, productId, businessDate);
            if (salesPlanItem == null) {
                logger.info("#traceId={}# [OUT] fail to getCustomerPlanInfo", rpcHeader.traceId);
                return new RpcResult(ErrorCode.NOT_VALID_PLAN_SALE_ORDER.getErrorCode());
            }
            //数据校验
            Long itemId = salesPlanItem.getItemId();
            int unallocatedQuantity = salesPlanItem.getUnallocatedQuantity();
            int saleOccupationQuantityAlready = salesPlanItem.getSaleOccupationQuantity();
            unallocatedQuantity -=changeQuantity;
             if (unallocatedQuantity<0){
                 return new RpcResult(ErrorCode.OVERTAKE_MAX_ALLOCATE_NUMBER.getErrorCode());
             }
             //更新销售占用
            saleOccupationQuantityAlready+=changeQuantity;
            salePlanItemDao.updateSaleOccupationQuantity(itemId,unallocatedQuantity, saleOccupationQuantityAlready);
            logger.info("#traceId={}# [OUT] updateSaleOccupation success: ", rpcHeader.traceId);
            return new RpcResult();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public RpcResult updateSoldQuantity(RpcHeader rpcHeader,
                                  String projectId,
                                  String distributorId,
                                  String productCode,
                                  Date businessDate,
                                  int changeQuantity) {
        logger.info("#traceId={}# [IN][updateSoldQuantity] params: projectId={}, distributorId={} ,productId={}, businessDate={}, changeQuantity={}",
                rpcHeader.traceId, projectId, distributorId, productCode, businessDate, changeQuantity);
        try {
            //找到对应的预售记录
            SalesPlanItem salesPlanItem = salePlanItemDao.getCustomerProductList(projectId, distributorId, productCode, businessDate);
            if (salesPlanItem == null) {
                logger.debug("#traceId={}# [OUT] fail to getCustomerPlanInfo", rpcHeader.traceId);
                return new RpcResult(ErrorCode.NOT_VALID_PLAN_SALE_ORDER.getErrorCode());
            }
            //数据校验
            int unallocatedQuantity = salesPlanItem.getUnallocatedQuantity();
            int saleOccupationQuantity = salesPlanItem.getSaleOccupationQuantity();
            int soldQuantityDb = salesPlanItem.getSoldQuantity();
            //如果销售占用的数量不够扣减,返回错误
            if (saleOccupationQuantity-changeQuantity<0){
                return new RpcResult(ErrorCode.OVERTAKE_SALE_OCCUPATION_NUMBER.getErrorCode());
            }
            int soldQuantity = soldQuantityDb+changeQuantity;
            int saleOccupationQuantity2=saleOccupationQuantity-changeQuantity;
            //更新已售数量
            Long itemId = salesPlanItem.getItemId();
            salePlanItemDao.updateSoldQuantity(itemId,saleOccupationQuantity2, soldQuantity);
            logger.info("#traceId={}# [OUT] updateSoldQuantity success: ", rpcHeader.traceId);
            return new RpcResult();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public PageInfo<SalesPlanItem> selectSalePlanItemListConditions(RpcHeader rpcHeader, String projectId, String distributorId, String productCode, Date businessDate, int pageNum, int pageSize) {
        logger.info("#traceId={}# [IN][selectSalePlanItemList] params: projectId={}, distributorId={} ,productCode={}, businessDate={}",
                rpcHeader.traceId, projectId, distributorId, productCode, businessDate);
        try {
            //启动分页
            PageHelper.startPage(pageNum, pageSize);
            List<SalesPlanItem> salesPlanItemList = salePlanItemDao.selectItemListByCondition(projectId, productCode, distributorId, businessDate);
            for (SalesPlanItem salesPlanItem : salesPlanItemList) {
                salesPlanItem.setGuidePriceDouble(salesPlanItem.getGuidePrice() / FXConstant.MILLION);
                salesPlanItem.setWholesalePriceDouble(salesPlanItem.getWholesalePrice() / FXConstant.MILLION);
                double totalDiscountDouble =
                        (salesPlanItem.getYhPrepaidDiscount() + salesPlanItem.getBrandPrepaidDiscount()) / FXConstant.MILLION;
                salesPlanItem.setTotalDiscountDouble(totalDiscountDouble);
            }
            PageInfo<SalesPlanItem> pageResult = new PageInfo<SalesPlanItem>(salesPlanItemList);
            if (salesPlanItemList.size() == 0) {
                logger.info("#traceId={}# [OUT] fail to selectSalePlanItemList", rpcHeader.traceId);
                return null;
            } else {
                logger.info("#traceId={}# [OUT] selectSalePlanItemList success: salesPlanItemList.size()={}", rpcHeader.traceId, salesPlanItemList.size());
            }
            return pageResult;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }



    @Override
    public List<SalesPlanItem> searchProductByCustomer(RpcHeader rpcHeader, String queryStr) {
        logger.info("#traceId={}# [IN][searchProductByCustomer] params: queryStr={} ",
                rpcHeader.traceId, queryStr);
        List<SalesPlanItem> resultList;
        try {
            DistributorPortalUser distributorPortalUser = distributorPortalUserService.getPortalUserById(rpcHeader, rpcHeader.getUid());
            resultList = salePlanItemDao.searchProductByCustomer(distributorPortalUser.getProjectId().toString(), distributorPortalUser.getDistributorId().toString(), queryStr, new Date());
            //resultList = salePlanItemDao.getCustomerProductList(distributorPortalUser.getProjectId().toString(), distributorPortalUser.getDistributorId().toString(), queryStr, new Date());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw new RuntimeException("searchProductByCustomer error", e);
        }
        logger.info("#traceId={}# [OUT] searchProductByCustomer success: resultList.size={} ", rpcHeader.traceId, resultList.size());
        return resultList;
    }


    @Override
    public SalesPlanItem getOnSaleQuantity(RpcHeader rpcHeader,String itemId) {
        logger.info("#traceId={}# [IN][searchProductByCustomer] params: queryStr={} projectId={}, distributorId={}",
                rpcHeader.traceId, itemId);
        try{
            SalesPlanItem salesPlanItem = salePlanItemDao.getByItemId(itemId);
            return salesPlanItem;
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw new RuntimeException("searchProductByCustomer error", e);
        }
    }

    @Override
    public RpcResult cancelCustomerOrder(RpcHeader rpcHeader,
                                         String projectId,
                                         String distributorId,
                                         String productCode,
                                         Date businessDate,
                                         int saleOccupationQuantity,
                                         int changeQuantity) {
        logger.info("#traceId={}# [IN][cancelCustomerOrder] params: projectId={} projectId={}, distributorId={}, productCode={},businessDate{},saleOccupationQuantity={}, soldQuantity={}"
                ,rpcHeader.getTraceId(),projectId,distributorId,productCode,businessDate,saleOccupationQuantity,changeQuantity);
        try {
            //找到对应的预售记录
            SalesPlanItem salesPlanItem = salePlanItemDao.getCustomerProductList(projectId, distributorId, productCode, businessDate);
            if (salesPlanItem == null) {
                logger.debug("#traceId={}# [OUT] fail to getCustomerPlanInfo", rpcHeader.traceId);
                return new RpcResult(ErrorCode.NOT_VALID_PLAN_SALE_ORDER.getErrorCode());
            }
            //数据校验
            int soldQuantityDb = salesPlanItem.getSoldQuantity();
            int unallocatedQuantity = salesPlanItem.getUnallocatedQuantity();

            //如果销售占用的数量不够扣减,返回错误
            int soldQuantity = soldQuantityDb-changeQuantity;
            unallocatedQuantity+=changeQuantity;
            //更新已售数量
            Long itemId = salesPlanItem.getItemId();
            salePlanItemDao.cancelAudit(itemId,unallocatedQuantity, soldQuantity);
            logger.info("#traceId={}# [OUT] updateSoldQuantity success: ", rpcHeader.traceId);
            return new RpcResult();
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw new RuntimeException("cancelCustomerOrder error", e);
        }
    }

}
