package com.yhglobal.gongxiao.sales.service.impl;

import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.RpcResult;
import com.yhglobal.gongxiao.plansales.dao.SalePlanItemDao;
import com.yhglobal.gongxiao.sales.model.plan.model.SalesPlanItem;
import com.yhglobal.gongxiao.sales.service.PlanSaleItemLocalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 葛灿
 */
@Service
public class PlanSaleItemLocalServiceImpl implements PlanSaleItemLocalService {

    private static final Logger logger = LoggerFactory.getLogger(PlanSaleItemLocalServiceImpl.class);

    @Autowired
    private SalePlanItemDao salePlanItemDao;

    @Override
    public RpcResult updateSaleOccupation(String prefix, GongxiaoRpc.RpcHeader rpcHeader, String projectId, String distributorId, String productId, Date businessDate, int saleOccupationQuantity) {
        logger.info("#traceId={}# [IN][updateSaleOccupation] params: projectId={}, distributorId={} ,productId={}, businessDate={}, saleOccupationQuantity={}",
                rpcHeader.getTraceId(), distributorId, productId, businessDate, saleOccupationQuantity);
        try {
            //找到对应的预售记录
            SalesPlanItem salesPlanItem = salePlanItemDao.getCustomerProductList(prefix, distributorId, productId, businessDate);
            if (salesPlanItem == null) {
                logger.info("#traceId={}# [OUT] fail to getCustomerPlanInfo", rpcHeader.getTraceId());
                return new RpcResult(ErrorCode.NOT_VALID_PLAN_SALE_ORDER.getErrorCode());
            }
            //数据校验
            Long itemId = salesPlanItem.getItemId();
            int unallocatedQuantity = salesPlanItem.getUnallocatedQuantity();
            int saleOccupationQuantityAlready = salesPlanItem.getSaleOccupationQuantity();
            unallocatedQuantity -= saleOccupationQuantity;
            if (unallocatedQuantity < 0) {
                return new RpcResult(ErrorCode.OVERTAKE_MAX_ALLOCATE_NUMBER.getErrorCode());
            }
            //更新销售占用
            saleOccupationQuantityAlready += saleOccupationQuantity;
            salePlanItemDao.updateSaleOccupationQuantity(prefix, itemId, unallocatedQuantity, saleOccupationQuantityAlready);
            logger.info("#traceId={}# [OUT] updateSaleOccupation success: ", rpcHeader.getTraceId());
            return new RpcResult();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public RpcResult updateSoldQuantity(String prefix, GongxiaoRpc.RpcHeader rpcHeader, String projectId, String distributorId, String productCode, Date businessDate, int quantity) {
        logger.info("#traceId={}# [IN][updateSoldQuantity] params: projectId={}, distributorId={} ,productId={}, businessDate={}, quantity={}",
                rpcHeader.getTraceId(), projectId, distributorId, productCode, businessDate, quantity);
        try {
            //找到对应的预售记录
            SalesPlanItem salesPlanItem = salePlanItemDao.getCustomerProductList(prefix, distributorId, productCode, businessDate);
            if (salesPlanItem == null) {
                logger.info("#traceId={}# [OUT] fail to getCustomerPlanInfo", rpcHeader.getTraceId());
                return new RpcResult(ErrorCode.NOT_VALID_PLAN_SALE_ORDER.getErrorCode());
            }
            //数据校验
            int saleOccupationQuantity = salesPlanItem.getSaleOccupationQuantity();
            saleOccupationQuantity -= quantity;
            if (saleOccupationQuantity < 0) {
                return new RpcResult(ErrorCode.OVERTAKE_MAX_ALLOCATE_NUMBER.getErrorCode());
            }
            //更新已售数量
            Long itemId = salesPlanItem.getItemId();
            int soldQuantityAlready = salesPlanItem.getSoldQuantity();
            soldQuantityAlready += quantity;
            salePlanItemDao.updateSoldQuantity(prefix, itemId, saleOccupationQuantity, soldQuantityAlready);
            logger.info("#traceId={}# [OUT] updateSoldQuantity success: ", rpcHeader.getTraceId());
            return new RpcResult();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public RpcResult cancelAuditSaleOrder(String prefix,
                                          GongxiaoRpc.RpcHeader rpcHeader,
                                          String projectId,
                                          String distributorId,
                                          String productCode,
                                          Date businessDate,
                                          int changeNumber) {
        try {
            logger.info("#traceId={}# [IN][cancelAuditSaleOrder] params: projectId={}, distributorId={} ,productId={}, businessDate={}, quantity={}",
                    rpcHeader.getTraceId(), projectId, distributorId, productCode, businessDate, changeNumber);
            //找到对应的预售记录
            SalesPlanItem salesPlanItem = salePlanItemDao.getCustomerProductList(prefix, distributorId, productCode, businessDate);
            if (salesPlanItem == null) {
                logger.info("#traceId={}# [OUT] fail to getCustomerPlanInfo", rpcHeader.getTraceId());
                return new RpcResult(ErrorCode.NOT_VALID_PLAN_SALE_ORDER.getErrorCode());
            }
            //数据校验
            int unallocatedQuantity = salesPlanItem.getUnallocatedQuantity();
            int soldQuantity = salesPlanItem.getSoldQuantity();
            unallocatedQuantity += changeNumber;
            soldQuantity -= changeNumber;
            if (soldQuantity < 0) {
                return new RpcResult(ErrorCode.OVERTAKE_MAX_ALLOCATE_NUMBER.getErrorCode());
            }
            //更新数量
            salePlanItemDao.cancelSaleOrder(prefix, salesPlanItem.getItemId(), unallocatedQuantity, soldQuantity );

            logger.info("#traceId={}# [OUT] cancelAuditSaleOrder success: ", rpcHeader.getTraceId());
            return new RpcResult();

        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }


}
