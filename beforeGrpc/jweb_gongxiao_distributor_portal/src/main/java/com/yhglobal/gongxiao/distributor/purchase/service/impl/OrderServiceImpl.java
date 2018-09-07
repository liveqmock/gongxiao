package com.yhglobal.gongxiao.distributor.purchase.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.distributor.DistributorAccountForOrder;
import com.yhglobal.gongxiao.distributor.purchase.service.OrderService;
import com.yhglobal.gongxiao.foundation.distributor.dao.DistributorPortalUserDao;
import com.yhglobal.gongxiao.foundation.distributor.model.DistributorCouponSetting;
import com.yhglobal.gongxiao.foundation.distributor.service.DistributorCouponSettingService;
import com.yhglobal.gongxiao.foundation.distributor.service.DistributorPortalUserService;
import com.yhglobal.gongxiao.foundation.portal.user.model.DistributorPortalUser;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.foundation.product.dto.ProductDto;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.service.DistributorAccountService;
import com.yhglobal.gongxiao.sales.bo.SalesOrderItemInfo;
import com.yhglobal.gongxiao.sales.model.plan.model.SalesPlanItem;
import com.yhglobal.gongxiao.sales.service.PlanSaleItemService;
import com.yhglobal.gongxiao.sales.service.SalesOrderService;
import com.yhglobal.gongxiao.util.DoubleScale;
import com.yhglobal.gongxiao.util.NumberFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yhglobal.gongxiao.sales.model.SalesMarketingChannelEnum;
import com.yhglobal.gongxiao.sales.model.SalesPaymentChannelEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 订单
 *
 * @Description:
 * @author: LUOYI
 * @Date: Created in 10:46 2018/4/13
 */
@Service
public class OrderServiceImpl implements OrderService {
    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Reference
    private DistributorPortalUserService distributorPortalUserService;
    @Reference
    private SalesOrderService salesOrderService;
    @Reference
    private DistributorCouponSettingService distributorCouponSettingService;
    @Reference
    private ProductService productService;
    @Autowired
    private DistributorPortalUserDao distributorPortalUserDao;
    @Reference
    private PlanSaleItemService planSaleItemService;
    @Reference
    private DistributorAccountService distributorAccountService;

    @Override
    public int createOrder(RpcHeader rpcHeader, String shippingAddress, String provinceId, String provinceName, String cityId, String cityName, String districtId,
                           String districtName, String recipientName, String recipientMobile, String currencyId, String currencyCode, double cashAmount, double totalOrderAmount, double totalPaidAmount, String salesOrderItem) {
        logger.info("#traceId={}# [IN][updateRecipientInfo] params:  shippingAddress={}, provinceId={}, " +
                        "provinceName={},cityId={},cityName={},districtId={},  districtName={}, recipientName={}, recipientMobile={}, currencyId={} ," +
                        "currencyCode={}, cashAmount={}, c totalOrderAmount={}, totalPaidAmount={}, totalPaidAmount={}, salesOrderItem={} ",
                rpcHeader.traceId, shippingAddress, provinceId, provinceName, cityId, cityName, districtId, districtName, recipientName, recipientMobile, currencyId,
                currencyCode, cashAmount, totalOrderAmount, totalPaidAmount, totalPaidAmount, totalPaidAmount, totalPaidAmount, salesOrderItem);
        try {
            //查询帐户信息
            DistributorPortalUser distributorPortalUser = distributorPortalUserService.getPortalUserById(rpcHeader, rpcHeader.getUid());
            String salesOrderNo = DateTimeIdGenerator.nextId(BizNumberType.SALES_ORDER_NO);
            ;//销售单号
            String salesContactNo = "";//合同号
            Integer marketingChannel = SalesMarketingChannelEnum.WEB.getStatus();//销售渠道
            Integer paymentChannel = SalesPaymentChannelEnum.OFFLINE_BANK.getStatus();//支付渠道
            Long projectId = distributorPortalUser.getProjectId();//项目ID
            String projectName = distributorPortalUser.getProjectName();//项目名称
            Long distributorId = distributorPortalUser.getDistributorId();//经销商ID
            String distributorName = distributorPortalUser.getDistributorName();//经销商名称
            List<SalesOrderItemInfo> itemList
                    = JSON.parseObject(salesOrderItem, new TypeReference<List<SalesOrderItemInfo>>() {
            }); //明细
            RpcResult rpcResult = salesOrderService.createSalesOrder(rpcHeader, salesOrderNo, salesContactNo, marketingChannel,
                    paymentChannel, projectId, projectName, distributorId, distributorName, shippingAddress, provinceId, provinceName,
                    cityId, cityName, districtId, districtName, recipientName, recipientMobile, currencyId, currencyCode,
                    cashAmount, 0, 0, totalOrderAmount, totalPaidAmount, new Date(), itemList);

            if (rpcResult.getSuccess()) {
                logger.info("#traceId={}# [OUT] createOrder success", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] fail to createOrder", rpcHeader.traceId);
            }
            //todo RpcResult
            return 1;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("save order error", e);
        }
    }

    @Override
    public DistributorAccountForOrder getAvailableBalance(RpcHeader rpcHeader) {
        logger.info("#traceId={}# [IN][updateRecipientInfo] params:  ", rpcHeader.traceId);
        try {
            DistributorAccountForOrder distributorAccountForOrder = new DistributorAccountForOrder();
            //查询用户信息
            DistributorPortalUser distributorPortalUser = distributorPortalUserService.getPortalUserById(rpcHeader, rpcHeader.getUid());
            //查询现金余额
            AccountAmount account = distributorAccountService.getDistributorAccountAmount(rpcHeader, "CNY", distributorPortalUser.getProjectId(), distributorPortalUser.getDistributorId());
            distributorAccountForOrder.setCashAmount(account.getCashAmount() / FXConstant.HUNDRED_DOUBLE);
            logger.info("#traceId={}# [OUT] getAvailableBalance success", rpcHeader.traceId);
            return distributorAccountForOrder;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("DistributorAccountForOrder error", e);
        }
    }

    @Override
    public ProductDto getProductByAd(RpcHeader rpcHeader, Long productId) {
        logger.info("#traceId={}# [IN][getProductByAd] params: productId={} ",
                rpcHeader.traceId, productId);
        ProductDto product = new ProductDto();
        try {
            DistributorPortalUser distributorPortalUser = distributorPortalUserDao.getDistributorPortalUserById(rpcHeader.getUid());

            //查询预售
            SalesPlanItem salesPlanItem = planSaleItemService.getCustomerPlanInfo(rpcHeader,
                    distributorPortalUser.getProjectId().toString(),
                    distributorPortalUser.getDistributorId().toString(),
                    productId.toString(), new
                            Date());
            product.setProductName(salesPlanItem.getProductName());
            product.setProductCode(salesPlanItem.getProductCode());
            product.setCanBePurchasedPieces(salesPlanItem.getOnSaleQuantity() - salesPlanItem.getSaleOccupationQuantity() - salesPlanItem.getSoldQuantity());
            product.setDiscount(salesPlanItem.getYhPrepaidDiscount() / FXConstant.MILLION_DOUBLE + salesPlanItem.getBrandPrepaidDiscount() / FXConstant.MILLION_DOUBLE);//折扣点
            double buyingPrice = DoubleScale.getRoundUpValue(6, salesPlanItem.getWholesalePrice() / FXConstant.MILLION_DOUBLE); // 出货价
            product.setBuyingPrice(buyingPrice);
            product.setGuidePrice(DoubleScale.getRoundUpValue(6, salesPlanItem.getSaleGuidePrice() / FXConstant.MILLION_DOUBLE));
            product.setEndTime(salesPlanItem.getEndTime());
            product.setBuyingPriceStr(NumberFormat.format(product.getBuyingPrice(),"#,##0.000000"));
            product.setGuidePriceStr(NumberFormat.format(product.getGuidePrice(),"#,##0.000000"));
            product.setDiscountStr(NumberFormat.format(product.getDiscount()*100,"#,##0.0000")+"%");
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw new RuntimeException("Search ProductBasic error", e);
        }
        logger.info("#traceId={}# [OUT] getProductByAd success: result={} ", rpcHeader.traceId, product);
        return product;
    }
}
