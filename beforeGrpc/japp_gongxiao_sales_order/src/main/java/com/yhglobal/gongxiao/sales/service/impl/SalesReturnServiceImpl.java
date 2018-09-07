package com.yhglobal.gongxiao.sales.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.common.TraceLog;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.constant.sales.SalesConstant;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.payment.prepaid.service.YhglobalPrepaidService;
import com.yhglobal.gongxiao.payment.service.DistributorAccountService;
import com.yhglobal.gongxiao.sales.bo.SalesReturnClassificationCount;
import com.yhglobal.gongxiao.sales.constant.SalesReturnStatus;
import com.yhglobal.gongxiao.sales.dao.SalesOrderDao;
import com.yhglobal.gongxiao.sales.dao.SalesOrderItemDao;
import com.yhglobal.gongxiao.sales.dao.SalesReturnOrderDao;
import com.yhglobal.gongxiao.sales.dao.SalesReturnOrderItemDao;
import com.yhglobal.gongxiao.sales.model.SalesOrder;
import com.yhglobal.gongxiao.sales.model.SalesOrderItem;
import com.yhglobal.gongxiao.sales.model.SalesOrderStatusEnum;
import com.yhglobal.gongxiao.sales.model.cancel.dto.OutBound;
import com.yhglobal.gongxiao.sales.model.cancel.dto.OutBoundItem;
import com.yhglobal.gongxiao.sales.model.cancel.dto.SalesReturn;
import com.yhglobal.gongxiao.sales.model.cancel.dto.SalesReturnItem;
import com.yhglobal.gongxiao.sales.model.cancel.model.SalesReturnOrder;
import com.yhglobal.gongxiao.sales.model.cancel.model.SalesReturnOrderItem;
import com.yhglobal.gongxiao.sales.service.SalesReturnService;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.warehouse.service.InboundService;
import com.yhglobal.gongxiao.warehouse.service.OutboundShowService;
import com.yhglobal.gongxiao.warehouse.type.WmsInventoryType;
import com.yhglobal.gongxiao.warehouse.type.WmsOrderType;
import com.yhglobal.gongxiao.warehouse.type.WmsSourceChannel;
import com.yhglobal.gongxiao.warehousemanagement.dto.PlanInboundOrderItem;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Description:
 * @Author: LUOYI
 * @Date: Created in 10:47 2018/3/21
 */
@Service(timeout = 5000)
public class SalesReturnServiceImpl implements SalesReturnService {

    private Logger logger = LoggerFactory.getLogger(SalesReturnServiceImpl.class);

    @Autowired
    private SalesReturnOrderDao salesReturnOrderDao;

    @Autowired
    private SalesReturnOrderItemDao salesReturnOrderItemDao;

    @Autowired
    private SalesOrderItemDao salesOrderItemDao;
    @Autowired
    private SalesOrderDao salesOrderDao;
    @Reference
    private InboundService inboundService;
    @Reference
    private OutboundShowService outboundShowService;
    @Reference
    private DistributorAccountService distributorAccountService;
    @Reference
    private YhglobalPrepaidService yhglobalPrepaidService;
    /**
     * 根据项目查询退货单列表
     *
     * @param projectId
     * @param returnedType
     * @param salesReturnedOrderNo
     * @param warehouseId
     * @param timeStart
     * @param timeEnd
     * @return List<SalesReturnOrder>
     */
    @Override
    public PageInfo<SalesReturnOrder> getsSalesReturnOrderByProject(RpcHeader rpcHeader, Integer projectId,
                                                                    Integer returnedType,
                                                                    String salesReturnedOrderNo,
                                                                    String warehouseId,
                                                                    String timeStart,
                                                                    String timeEnd,
                                                                    Integer returnedOrderStatus,
                                                                    Integer page,
                                                                    Integer pageSize) {
        logger.info("#traceId={}# [IN][getsSalesReturnOrderByProject] params: projectId={}, returnedType={}, salesReturnedOrderNo={}, warehouseId={}, timeStart={}, timeEnd={}, returnedOrderStatus={} "
                , rpcHeader.traceId, projectId, returnedType, salesReturnedOrderNo, warehouseId, timeStart, timeEnd, returnedOrderStatus);
        List<SalesReturnOrder> returnList = null;
        PageInfo<SalesReturnOrder> pageResult = null;
        try {
            PageHelper.startPage(page, pageSize);//启动分页
            returnList = salesReturnOrderDao.selectSalesReturnedOrderByProject(
                    projectId, returnedType, salesReturnedOrderNo, warehouseId, timeStart, timeEnd, returnedOrderStatus);
            pageResult = new PageInfo<SalesReturnOrder>(returnList);//包装分页对象
            logger.info("#traceId={}# [OUT] get getsSalesReturnOrderByProject success: resultList.size()={}", rpcHeader.getTraceId(), returnList.size());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
        return pageResult;
    }

    @Override
    public List<SalesReturnClassificationCount> selectClassificationCount(RpcHeader rpcHeader, Integer projectId, Integer returnedType, String salesReturnedOrderNo, String warehouseId, String timeStart, String timeEnd) {
        logger.info("#traceId={}# [IN][getsSalesReturnOrderByProject] params: projectId={}, returnedType={}, salesReturnedOrderNo={}, warehouseId={}, timeStart={}, timeEnd={} "
                , rpcHeader.traceId, projectId, returnedType, salesReturnedOrderNo, warehouseId, timeStart, timeEnd);
        Integer count = 0;
        List<SalesReturnClassificationCount> returnList = null;
        List<SalesReturnClassificationCount> result = null;
        try {
            count = salesReturnOrderDao.selectSalesReturnedOrderCount(projectId, returnedType, salesReturnedOrderNo, warehouseId, timeStart, timeEnd);
            result = salesReturnOrderDao.selectClassificationCount(projectId, returnedType, salesReturnedOrderNo, warehouseId, timeStart, timeEnd);
            returnList = new ArrayList<>();
            for (SalesReturnStatus salesReturnStatus : SalesReturnStatus.values()) {
                SalesReturnClassificationCount salesCount = new SalesReturnClassificationCount();
                salesCount.setStatus(salesReturnStatus.getCode());
                if (SalesReturnStatus.RETURN_ORDER_STATUS_ALL.getCode() == salesReturnStatus.getCode()) {
                    salesCount.setCount(count);
                } else {
                    salesCount.setCount(0);
                    for (SalesReturnClassificationCount salesReturnCount : result) {
                        if (salesReturnStatus.getCode() == salesReturnCount.getStatus())
                            salesCount.setCount(salesReturnCount.getCount());
                    }
                }
                returnList.add(salesCount);
            }
            logger.info("#traceId={}# [OUT] get selectClassificationCount success: result.zize={}", rpcHeader.getTraceId(), result.size());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
        return returnList;
    }

    /**
     * y
     * 保存销售退货单
     *
     * @param salesReturnOrder 销售退货实体
     * @return
     */
    @Override
    public RpcResult<Integer> addSalesReturnOrder(RpcHeader rpcHeader, SalesReturnOrder salesReturnOrder) {
        logger.info("#traceId={}# [IN][addSalesReturnOrder] params: salesReturnOrder={} ", rpcHeader.traceId, salesReturnOrder);
        int result = 0;
        //对销售退货单数据封装
        salesReturnOrder.setReturnedType(SalesConstant.RETURN_ORDER_TYPE_SALES);//退货单类型 销售退货
        salesReturnOrder.setReturnedOrderStatus(SalesReturnStatus.RETURN_ORDER_STATUS_NEW.getCode());//退货单状态  新建待审
        salesReturnOrder.setSalesReturnedOrderNo(DateTimeIdGenerator.nextId(BizNumberType.SALES_RETURN_ORDER_NO));//退货单号
        //封装创建人信息
        salesReturnOrder.setCreatedById(Long.parseLong(rpcHeader.getUid()));
        salesReturnOrder.setCreatedByName(rpcHeader.getUsername());
        salesReturnOrder.setCreateTime(new Date());
        salesReturnOrder.setDataVersion(0l);
        //封装退货单明细
        List<SalesReturnOrderItem> salesReturnOrderItemList
                = JSON.parseObject(salesReturnOrder.getSalesReturnOrderItemListJson(), new TypeReference<List<SalesReturnOrderItem>>() {
        });
        int totalReturnedQuantity = 0;
        //校验退货数量
        for(SalesReturnOrderItem salesReturnOrderItem : salesReturnOrderItemList){
            OutboundOrderItem outboundOrderItem = outboundShowService.selectRecordBySalseNoAndProduct(rpcHeader,String.valueOf(salesReturnOrder.getProjectId()),salesReturnOrder.getOriginalSalesOrderNo(),salesReturnOrderItem.getProductCode());
            int CanReturnQuantity = outboundOrderItem.getOutStockQuantity() - outboundOrderItem.getCanceledQuantity();
            if(salesReturnOrderItem.getTotalReturnedQuantity()>CanReturnQuantity){
                return RpcResult.newFailureResult(ErrorCode.CAN_RETURN_QUANTITY_SCANTY.getErrorCode(),ErrorCode.CAN_RETURN_QUANTITY_SCANTY.getMessage());
            }
        }
        //逐条处理明细数据并保存
        for (SalesReturnOrderItem salesReturnOrderItem : salesReturnOrderItemList) {
            //计算退货数量总和
            totalReturnedQuantity += salesReturnOrderItem.getTotalReturnedQuantity();
            //封装创建人信息
            salesReturnOrderItem.setCreatedById(Long.parseLong(rpcHeader.getUid()));
            salesReturnOrderItem.setCreatedByName(rpcHeader.getUsername());
            salesReturnOrderItem.setCreateTime(new Date());
            salesReturnOrderItem.setSalesReturnedOrderNo(salesReturnOrder.getSalesReturnedOrderNo());//销售退货单号
            salesReturnOrderItem.setDataVersion(0l);
            try {
                //保存退货单明细
                salesReturnOrderItemDao.addSalesReturnOrderItem(salesReturnOrderItem);
            } catch (Exception e) {
                logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
                throw e;
            }
        }
        salesReturnOrder.setTotalReturnedQuantity(totalReturnedQuantity);
        try {
            //查询销售单信息
            SalesOrder salesOrder = salesOrderDao.getByOrderNo(salesReturnOrder.getOriginalSalesOrderNo());
            //品牌ID
            salesReturnOrder.setBrandId(salesOrder.getBrandId());
            //品牌名称
            salesReturnOrder.setBrandName(salesOrder.getBrandName());
            //供应商Id
            salesReturnOrder.setSupplierId(salesOrder.getSupplierId());
            //供应商名称
            salesReturnOrder.setSupplierName(salesOrder.getSupplierName());
            //经销商Id
            salesReturnOrder.setDistributorId(salesOrder.getDistributorId());
            //经销商名称
            salesReturnOrder.setDistributorName(salesOrder.getDistributorName());
            salesReturnOrder.setFreight(salesReturnOrder.getFreightDouble()!=null?Math.round(salesReturnOrder.getFreightDouble()*FXConstant.HUNDRED_DOUBLE):0L);
            //salesReturnOrder.setOriginalOutboundWarehouseId(salesOrder.getWarehouseId()+"");//发货时仓库ID
            //salesReturnOrder.setOriginalOutboundWarehouseName(salesOrder.getWarehouse());//发货时仓库名称

            TraceLog traceLog = new TraceLog(new Date().getTime(), rpcHeader.getUid(), rpcHeader.getUsername(),SalesReturnStatus.RETURN_ORDER_STATUS_NEW.getCode(), SalesReturnStatus.RETURN_ORDER_STATUS_NEW.getMessage());
            salesReturnOrder.setTracelog(TraceLogUtil.appendTraceLog(null, traceLog));
            //保存退货单
            result = salesReturnOrderDao.addSalesReturnOrder(salesReturnOrder);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
        logger.info("#traceId={}# [OUT] get addSalesReturnOrder success: result={}", rpcHeader.getTraceId(), result);
        return RpcResult.newSuccessResult(result);
    }

    @Override
    public int checkSalesReturnOrder(RpcHeader rpcHeader, SalesReturnOrder[] salesReturnOrder) throws Exception {
        logger.info("#traceId={}# [IN][checkSalesReturnOrder] params: salesReturnOrder={} ", rpcHeader.traceId, salesReturnOrder);
        int result = 0;
        for (int i = 0; i < salesReturnOrder.length; i++) {
            SalesReturnOrder returnOrder = salesReturnOrder[i];
            result += checkSalesReturnOrder(rpcHeader, returnOrder);
        }
        logger.info("#traceId={}# [OUT] get checkSalesReturnOrder success: result={}", rpcHeader.getTraceId(), result);
        return result;
    }

    /**
     * 单个处理退货审核
     *
     * @param rpcHeader
     * @param returnOrder
     * @return
     */
    private int checkSalesReturnOrder(RpcHeader rpcHeader, SalesReturnOrder returnOrder) {
        logger.info("#traceId={}# [IN][checkSalesReturnOrder] params: salesReturnOrder={} ", rpcHeader.traceId, returnOrder);
        int result = 0;
        long dateVersion = returnOrder.getDataVersion();
        //逐条更新状态
        result = checkSalesReturnOrder(rpcHeader, returnOrder.getSalesReturnedOrderId(), dateVersion);
        if (result == 0) {
            return result;
        }
        //查询退货单
        returnOrder = salesReturnOrderDao.selectById(returnOrder.getSalesReturnedOrderId());
        //查询退货单明细列表
        List<SalesReturnOrderItem> itemList = salesReturnOrderItemDao.selectBySalesReturnedOrderNo(returnOrder.getSalesReturnedOrderNo());
        //调用仓储模块预约入库
        GongxiaoResult gongxiaoResult = null;
        try {
            gongxiaoResult = createInboundOrder(rpcHeader, returnOrder, itemList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //更新入库单号
        if (0 == gongxiaoResult.getReturnCode()) {
            int count = salesReturnOrderDao.updateInboundOrderNo((String) gongxiaoResult.getData(), returnOrder.getSalesReturnedOrderId(), returnOrder.getDataVersion());
            //更新退货明细入库单号
            for (SalesReturnOrderItem item : itemList) {
                salesReturnOrderItemDao.updateInboundOrderNo((String) gongxiaoResult.getData(), item.getRowId(), item.getDataVersion());
            }
        }

        logger.info("#traceId={}# [OUT] get checkSalesReturnOrder success: result={}", rpcHeader.getTraceId(), result);
        return result;
    }

    /**
     * @param returnOrder
     * @param itemList
     * @return
     */
    private GongxiaoResult createInboundOrder(RpcHeader rpcHeader, SalesReturnOrder returnOrder, List<SalesReturnOrderItem> itemList) throws Exception {
        logger.info("#traceId={}# [IN][createInboundOrder] params: returnOrder={} ,itemList={}", rpcHeader.traceId, returnOrder, itemList);
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String sourceChannelId = WmsSourceChannel.CHANNEL_YHGLOBAL.getChannelId();//发起入库通知单的渠道
        String projectId = String.valueOf(returnOrder.getProjectId());           //项目id
        String traceNo = returnOrder.getSalesReturnedOrderNo();              //调用的发起方携带的标签
        String senderName = returnOrder.getSenderName();// 发件人名字
        String senderPhoneNo = returnOrder.getSenderMobile();        //发件人联系电话
        String warehouseId = returnOrder.getTargetWarehouseId();//        入库目标仓库id
        String warehouseName = returnOrder.getTargetWarehouseName();//      入库目标仓库名称
        String deliverAddress = returnOrder.getProvinceName() + returnOrder.getCityName() + returnOrder.getDistrictName() + returnOrder.getSenderAddressItem();//    发货地址
        Integer shippingMode = returnOrder.getLogisticsType();//物流方式
        String logisticsCompanyName = returnOrder.getLogisticsCompany(); //物流公司的名字
        String logisticsNo = returnOrder.getLogisticsOrderNo();//         物流号
        String note = "";//                备注
        Integer totalQuantity = returnOrder.getTotalReturnedQuantity();//        各种类商品本次入库数量总和
        String signature = "";//           签名
        List<PlanInboundOrderItem> inboundItemList = new ArrayList<>(); //货品明细
        //遍历退货明细生成预约入库明细
        for (SalesReturnOrderItem item : itemList) {
            PlanInboundOrderItem inboundItem = new PlanInboundOrderItem();
            inboundItem.setProjectId(String.valueOf(returnOrder.getProjectId()));                //项目id
            inboundItem.setPurchaseOrderNo(returnOrder.getOriginalSalesOrderNo());           //采购单号|销售单号
            inboundItem.setWarehouseId(returnOrder.getTargetWarehouseId());              //仓库ID
            inboundItem.setWarehouseName(returnOrder.getTargetWarehouseName());            //仓库名称
            inboundItem.setBrandId(String.valueOf(returnOrder.getBrandId()));                  //品牌id
            inboundItem.setBrandName(returnOrder.getBrandName());                //品牌商
            inboundItem.setProjectId(item.getProductId());
            ;                //商品id
            inboundItem.setProductCode(item.getProductCode());             //商品编码
            inboundItem.setProductName(item.getProductName());             //商品名称
            inboundItem.setProductUnit(item.getProductUnit());             //商品单位
            inboundItem.setTotalQuantity(item.getTotalReturnedQuantity());              //预约入库数量
            inboundItem.setInventoryType(WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType());//库存类型
            inboundItemList.add(inboundItem);
        }
        //调用仓储模块
        try {

            String saleUniqueNo =DateTimeIdGenerator.nextId(BizNumberType.SALE_UNIQUE_NO);

            gongxiaoResult = inboundService.createInboundOrder2(rpcHeader,
                    returnOrder.getSalesReturnedOrderNo(),
                    sourceChannelId,
                    WmsOrderType.INBOUND_FOR_RETURNING_PRODUCT.getInboundOrderCode(),
                    projectId,
                    traceNo,
                    senderName,
                    senderPhoneNo,
                    warehouseId,
                    warehouseName,
                    deliverAddress,
                    shippingMode,
                    logisticsCompanyName,
                    logisticsNo,
                    note,
                    totalQuantity,
                    inboundItemList,
                    signature,
                    saleUniqueNo);

        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("#traceId={}# [OUT] get createInboundOrder success: result={}", rpcHeader.getTraceId());
        return gongxiaoResult;
    }

    /**
     * 更新销售退货单状态为等待退货入仓
     *
     * @param salesReturnedOrderId
     * @param dataVersion
     * @return
     */
    private int checkSalesReturnOrder(RpcHeader rpcHeader, Long salesReturnedOrderId, Long dataVersion) {
        logger.info("#traceId={}# [IN][checkSalesReturnOrder] params: salesReturnedOrderId={} ,dataVersion={}", rpcHeader.traceId, salesReturnedOrderId, dataVersion);
        SalesReturnOrder salesReturnOrder = salesReturnOrderDao.selectById(salesReturnedOrderId);
        TraceLog traceLog = new TraceLog(new Date().getTime(), rpcHeader.getUid(), rpcHeader.getUsername(),SalesReturnStatus.RETURN_ORDER_STATUS_NOT_IN_STORAGE.getCode(), SalesReturnStatus.RETURN_ORDER_STATUS_NOT_IN_STORAGE.getMessage());
        String traceLogs = TraceLogUtil.appendTraceLog(salesReturnOrder.getTracelog(), traceLog);
        int result = salesReturnOrderDao.updateStatus(salesReturnedOrderId, SalesReturnStatus.RETURN_ORDER_STATUS_NOT_IN_STORAGE.getCode(), dataVersion, traceLogs);
        logger.info("#traceId={}# [OUT] get checkSalesReturnOrder success: result={}", rpcHeader.getTraceId(), result);
        return result;
    }

    @Override
    public boolean salesReturnInbound(RpcHeader rpcHeader,
                                      String inboundOrderNo,
                                      String traceNo,
                                      String productCode,
                                      String productName,
                                      String productUnit,
                                      int inStockQuantity) {
        logger.info("#traceId={}# [IN][salesReturnInbound] params: inboundOrderNo={}, traceNo={}, productCode={}, productName={}, inStockQuantity={} ",
                rpcHeader.traceId, inboundOrderNo, traceNo, productCode, productName, productUnit, inStockQuantity);
        try {
            //按入库单号查询退货单
            SalesReturnOrder salesReturnOrder = salesReturnOrderDao.selectSalesReturnOrderByInBoundOrderNo(inboundOrderNo);
            //按入库单号&产品编码查询退货单详情
            SalesReturnOrderItem salesReturnOrderItem = salesReturnOrderItemDao.selectSalesReturnOrderItemByInBoundOrderNo(inboundOrderNo, productCode);
            //更新退货单详情（退货入库数量&wms入库记录）
            salesReturnOrderItem.setTotalInStockQuantity(salesReturnOrderItem.getTotalInStockQuantity()+inStockQuantity);
            salesReturnOrderItemDao.updateInboundRecord(salesReturnOrderItem.getTotalInStockQuantity(),inboundOrderNo, salesReturnOrderItem.getRowId(), salesReturnOrderItem.getDataVersion());
            //查询销售单
            SalesOrder salesOrder = salesOrderDao.getByOrderNo(salesReturnOrder.getOriginalSalesOrderNo());
            //查询销售单明细
            SalesOrderItem salesOrderItem = salesOrderItemDao.getSalesOrderItemBySalesOrderNoAndProductCode(salesReturnOrder.getOriginalSalesOrderNo(), productCode);
            //计算本次退货总金额  总金额/总数量*入库数量
            Long totalAmount = salesOrderItem.getTotalOrderAmount() / salesOrderItem.getTotalQuantity() * inStockQuantity;
            //计算本次退货总金额在销售单中占比
            Double inboundAmountScale = totalAmount / salesOrder.getTotalOrderAmount() * 0.00;
            //入库应归还返利
            Long returnedCoupon = Math.round(salesOrder.getCouponAmount() * inboundAmountScale);
            //入库应归还代垫
            Long returnedPrepaid = Math.round(salesOrder.getPrepaidAmount() * inboundAmountScale);
            //入库应归还预存现金 现金+预存
            Long returnedCash = Math.round(salesOrder.getCashAmount() * inboundAmountScale) + Math.round(salesOrder.getPrestoredAmount() * inboundAmountScale);
            //已退还返利
            Long returnedCouponAmount = salesReturnOrder.getReturnedCouponAmount() + returnedCoupon;
            //已退还代垫
            Long returnedPrepaidAmount = salesReturnOrder.getReturnedPrepaidAmount() + returnedPrepaid;
            //已退还现金
            Long returnedCashAmount = salesReturnOrder.getReturnedCashAmount() + returnedCash;
            TraceLog traceLog = new TraceLog(new Date().getTime(), rpcHeader.getUid(), rpcHeader.getUsername(), "退货到仓");
            String traceLogs = TraceLogUtil.appendTraceLog(salesReturnOrder.getTracelog(), traceLog);
            salesReturnOrder.setInStockQuantity(salesReturnOrder.getInStockQuantity()+inStockQuantity);
            if(salesReturnOrder.getInStockQuantity()>=salesReturnOrder.getTotalReturnedQuantity()){
                traceLog = new TraceLog(new Date().getTime(), rpcHeader.getUid(), rpcHeader.getUsername(),SalesReturnStatus.RETURN_ORDER_STATUS_FINISH.getCode(), SalesReturnStatus.RETURN_ORDER_STATUS_FINISH.getMessage());
                traceLogs = TraceLogUtil.appendTraceLog(traceLogs, traceLog);
            }
            //更新退货单（归还返利&代垫&现金）
            salesReturnOrderDao.updateInboundRecord(returnedCouponAmount, returnedPrepaidAmount, returnedCashAmount, salesReturnOrder.getInStockQuantity(), salesReturnOrder.getSalesReturnedOrderId(),traceLogs, salesReturnOrder.getDataVersion());
            //更新现金，返利代垫帐户
            distributorAccountService.returnForSalesReturnOrder(rpcHeader,"CNY",salesReturnOrder.getDistributorId(),salesReturnOrder.getProjectId(),returnedCouponAmount,returnedPrepaidAmount,returnedCashAmount,salesReturnOrder.getSalesReturnedOrderNo(),new Date());
            //更新销售单退货数量
            salesOrder.setReturnedQuantity(salesOrder.getReturnedQuantity()+ inStockQuantity);
            salesOrderItem.setReturnedQuantity(salesOrderItem.getReturnedQuantity()+ inStockQuantity);
            salesOrderDao.update(salesOrder);
            salesOrderItemDao.update(salesOrderItem);
            // 越海应收代垫扣减  生成金额为负的应收代垫
            SalesOrderItem orderItem = salesOrderItemDao.getSalesOrderItemBySalesOrderNoAndProductCode(salesReturnOrder.getOriginalSalesOrderNo(),productCode);
            Long backPrepaid =  orderItem.getGeneratedPrepaid() * inStockQuantity / 10000;
            yhglobalPrepaidService.addReceive(rpcHeader,
                    null,
                    salesReturnOrder.getProjectId(),
                    salesReturnOrder.getProjectName(),
                    (int)salesReturnOrder.getSupplierId(),
                    salesReturnOrder.getSupplierName(),
                    null,
                    -backPrepaid,
                    "CNY");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("WMS callback error!", e);
        }
        return true;
    }

    @Override
    public SalesReturn getSalesReturn(RpcHeader rpcHeader, Long salesReturnedOrderId) {
        logger.info("#traceId={}# [IN][SalesReturnOrder] params: salesReturnedOrderId={} ", rpcHeader.traceId, salesReturnedOrderId);
        SalesReturnOrder salesReturnOrder = null;
        SalesReturn salesReturn = new SalesReturn();
        try {
            salesReturnOrder = salesReturnOrderDao.selectById(salesReturnedOrderId);
            if (salesReturnOrder == null) {
                logger.info("#traceId={}# [OUT] fail to getSalesReturn: SalesReturnOrder ={}", salesReturnOrder);
                throw new RuntimeException("No data can be found", new Throwable());
            }
            List<SalesReturnOrderItem> itemList = salesReturnOrderItemDao.selectBySalesReturnedOrderNo(salesReturnOrder.getSalesReturnedOrderNo());

            List<SalesReturnItem> salesReturnItems = new ArrayList<>();
            salesReturn.setSalesReturnedOrderNo(salesReturnOrder.getSalesReturnedOrderNo());//退货订单号
            salesReturn.setReturnedOrderStatus(salesReturnOrder.getReturnedOrderStatus());//状态
            salesReturn.setCreateTime(salesReturnOrder.getCreateTime());//退单创建时间
            salesReturn.setSenderName(salesReturnOrder.getSenderName());//退货人
            salesReturn.setProvinceId(salesReturnOrder.getProvinceId());//退货人地址
            salesReturn.setProvinceName(salesReturnOrder.getProvinceName());
            salesReturn.setCityId(salesReturnOrder.getCityId());
            salesReturn.setCityName(salesReturnOrder.getCityName());
            salesReturn.setDistrictId(salesReturnOrder.getDistrictId());
            salesReturn.setDistrictName(salesReturnOrder.getDistrictName());
            salesReturn.setSenderAddressItem(salesReturnOrder.getSenderAddressItem());//详细地址
            salesReturn.setSenderMobile(salesReturnOrder.getSenderMobile());//手机
            salesReturn.setDistributorName(salesReturnOrder.getDistributorName());//退货公司名称
            salesReturn.setLogisticsType(salesReturnOrder.getLogisticsType());//运输方式
            salesReturn.setLogisticsCompany(salesReturnOrder.getLogisticsCompany());//退回物流公司
            salesReturn.setLogisticsOrderNo(salesReturnOrder.getLogisticsOrderNo());//物流运单号
            salesReturn.setTargetWarehouseName(salesReturnOrder.getTargetWarehouseName());//退回仓库名称
            salesReturn.setFreightPaidBy(salesReturnOrder.getFreightPaidBy());//运费承担方
            salesReturn.setFreight(salesReturnOrder.getFreight() / FXConstant.HUNDRED_DOUBLE);//运费
            salesReturn.setOriginalGongxiaoOutboundOrderNo(salesReturnOrder.getOriginalGongxiaoOutboundOrderNo());//发货单编号
            salesReturn.setProjectName(salesReturnOrder.getProjectName());//项目名称
            salesReturn.setReturnedType(salesReturnOrder.getReturnedType());//退货单类型
            salesReturn.setCreatedByName(salesReturnOrder.getCreatedByName());//退货单创建人
            salesReturn.setLastUpdateTime(salesReturnOrder.getLastUpdateTime());//状态更新时间
            salesReturn.setTracelog(salesReturnOrder.getTracelog());//操作日志
            //退货单明细
            for (SalesReturnOrderItem returnOrderItem : itemList) {
                SalesReturnItem returnItem = new SalesReturnItem();
                //查询销售详情
                SalesOrderItem salesOrderItem = salesOrderItemDao.getSalesOrderItemBySalesOrderNoAndProductCode(salesReturnOrder.getOriginalSalesOrderNo(), returnOrderItem.getProductCode());
                returnItem.setProductName(returnOrderItem.getProductName());//货品名称
                returnItem.setProductCode(returnOrderItem.getProductCode());//货品编码
                returnItem.setTotalReturnedQuantity(returnOrderItem.getTotalReturnedQuantity()); //退货数量
                returnItem.setTotalInStockQuantity(returnOrderItem.getTotalInStockQuantity());//仓库实收
                returnItem.setReturnCause(returnOrderItem.getReturnCause());//退货原因
                if (salesOrderItem != null) {
                    returnItem.setCurrencyCode(salesOrderItem.getCurrencyCode());//货币编码
                    returnItem.setCurrencyName(salesOrderItem.getCurrencyName());//货币名称
                    returnItem.setWholesalePrice(Double.valueOf(salesOrderItem.getWholesalePrice() / FXConstant.MILLION_DOUBLE));//出货价
                    returnItem.setTotalQuantity(salesOrderItem.getTotalQuantity());//原始数量
                    returnItem.setReturnAmount(Double.valueOf(salesOrderItem.getWholesalePrice() * returnOrderItem.getTotalReturnedQuantity() / FXConstant.HUNDRED_DOUBLE));//商品退款金额=出货价*数量
                }
                salesReturnItems.add(returnItem);
            }
            salesReturn.setItemList(salesReturnItems);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
        logger.info("#traceId={}# [OUT] get getSalesReturn success: result={}", rpcHeader.getTraceId(), salesReturnOrder);
        return salesReturn;
    }

    @Override
    public List<OutBound> getOutBound(RpcHeader rpcHeader, String projectId, String salesNo) {
        logger.info("[IN]# [getOutBound] #rpcHeader={} #projectId={} #salesNo={}", rpcHeader.traceId, projectId, salesNo);
        List<OutBound> outBoundList = new ArrayList<>();
        try {

            List<OutboundOrder> list = outboundShowService.selectRecordBySalesNo(rpcHeader, projectId, salesNo);
            SalesOrder salesOrder = salesOrderDao.getByOrderNo(salesNo);
            for (OutboundOrder outboundOrder : list) {
                OutBound outBound = new OutBound();
                outBound.setGongxiaoOutboundOrderNo(outboundOrder.getGongxiaoOutboundOrderNo());         //GX出库单号
                outBound.setOrderStatus(outboundOrder.getOrderStatus());                        //出库单状态
                if (0 == outboundOrder.getOrderStatus()) {
                    outBound.setOrderStatusStr("等待收货");
                } else if (1 == outboundOrder.getOrderStatus()) {
                    outBound.setOrderStatusStr("部分出仓");
                } else {
                    outBound.setOrderStatusStr("收货完成");
                }

                outBound.setContactsPeople(outboundOrder.getContactsPeople());                   //收件人名字
                outBound.setPhoneNum(outboundOrder.getPhoneNum());                 //收件人手机号
                outBound.setSalesOrderNo(outboundOrder.getSalesOrderNo());
                outBound.setLastUpdateTime(outboundOrder.getLastUpdateTime());
                outBound.setShippingAddress(salesOrder.getShippingAddress());
                outBound.setProvinceId(salesOrder.getProvinceId());
                outBound.setProvinceName(salesOrder.getProvinceName());
                outBound.setCityId(salesOrder.getCityId());
                outBound.setCityName(salesOrder.getCityName());
                outBound.setDistrictId(salesOrder.getDistrictId());
                outBound.setDistrictName(salesOrder.getDistrictName());
                outBoundList.add(outBound);
            }
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
        return outBoundList;
    }

    @Override
    public List<OutBoundItem> getOutBoundItem(RpcHeader rpcHeader, String projectId, String salesOrderNo, String gongxiaoOutboundOrderNo) {
        logger.info("[IN]# [getOutBoundItem] #rpcHeader={} #projectId={} #salesOrderNo={} #gongxiaoOutboundOrderNo={} ", rpcHeader.traceId, projectId, salesOrderNo, gongxiaoOutboundOrderNo);
        List<OutboundOrderItem> outboundOrderItemList = outboundShowService.selectRecordItemByOutboundOrderNo(rpcHeader, projectId, gongxiaoOutboundOrderNo);
        List<OutBoundItem> result = new ArrayList<>();
        try {
            for (OutboundOrderItem outboundOrderItem : outboundOrderItemList) {
                OutBoundItem outBoundItem = new OutBoundItem();
                outBoundItem.setCanReturnQuantity(outboundOrderItem.getOutStockQuantity() - outboundOrderItem.getCanceledQuantity());
                outBoundItem.setProductId(outboundOrderItem.getProductId());
                outBoundItem.setProductCode(outboundOrderItem.getProductCode());
                outBoundItem.setProductName(outboundOrderItem.getProductName());
                outBoundItem.setOutStockQuantity(outboundOrderItem.getOutStockQuantity());
                outBoundItem.setProductUnit(outboundOrderItem.getProductUnit());
                SalesOrderItem salesOrderItem = salesOrderItemDao.getSalesOrderItemBySalesOrderNoAndProductCode(outboundOrderItem.getSalesOrderNo(), outboundOrderItem.getProductCode());
                if (salesOrderItem != null) {
                    outBoundItem.setCurrencyCode(salesOrderItem.getCurrencyCode());
                    outBoundItem.setCurrencyName(salesOrderItem.getCurrencyName());
                    outBoundItem.setGuidePrice(Double.valueOf(salesOrderItem.getSalesGuidePrice() / FXConstant.MILLION_DOUBLE));
                    outBoundItem.setWholesalePrice(Double.valueOf(salesOrderItem.getWholesalePrice() / FXConstant.MILLION_DOUBLE));
                }
                result.add(outBoundItem);
            }
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
        }
        logger.info("[OUT] #traceId={} :  result={}", rpcHeader.traceId, result);
        return result;
    }
}
