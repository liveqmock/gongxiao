package com.yhglobal.gongxiao.warehouse.controller.warehouse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.warehouse.model.AllocationOrder;
import com.yhglobal.gongxiao.warehouse.model.AllocationOrderItem;
import com.yhglobal.gongxiao.warehouse.model.CreateAllocationInfo;
import com.yhglobal.gongxiao.warehouse.model.bo.AllocationOrderShowModel;
import com.yhglobal.gongxiao.warehouse.service.AllocationService;
import com.yhglobal.gongxiao.warehouseapi.model.CreateAllocateOrderRequest;
import com.yhglobal.gongxiao.warehouseapi.model.OutboundOrderItem;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 调拨controller
 *
 * @author liukai
 */
@Controller
@RequestMapping("/allocte")
public class AllocationController {

    private static Logger logger = LoggerFactory.getLogger(AllocationController.class);

    @Autowired
    AllocationService allocationService;


    /**
     * 查询调拨单
     *
     * @param projectId               项目id
     * @param allocateNo              调拨单号
     * @param gongxiaoOutboundOrderNo 调拨出库单号
     * @param gongxiaoInboundOrderNo  调拨入库单号
     * @param warehouseOut            调拨出库仓
     * @param warehouseEnter          调拨入库仓
     * @param status                  调拨单状态
     * @param createBeginTime         创建起始时间
     * @param createEndTime           创建结束时间
     * @param pageNumber              页码
     * @param pageSize                页数
     * @param request
     * @return
     */
    @RequestMapping("/selectAllocateOrder")
    @ResponseBody
    public GongxiaoResult selectAllocateOrder(String projectId, String allocateNo, String gongxiaoOutboundOrderNo, String gongxiaoInboundOrderNo,
                                              String warehouseOut, String warehouseEnter, String status, String createBeginTime, String createEndTime, int pageNumber, int pageSize, HttpServletRequest request) {
        String traceId = null;
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        try {
            traceId = "45723455";
            logger.info("#traceId={}# [IN][selectAllocateOrder] params: projectId={},allocateNo={},gongxiaoOutboundOrderNo={},gongxiaoInboundOrderNo={},warehouseOut={},warehouseEnter={},status={},createBeginTime={},createEndTime={}", traceId, projectId, allocateNo, gongxiaoOutboundOrderNo, gongxiaoInboundOrderNo, warehouseOut, warehouseEnter, status, createBeginTime, createEndTime);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "6547", "liukai");
            PageInfo<AllocationOrderShowModel> resultList = allocationService.getAllRecordByCondition(rpcHeader, pageNumber, pageSize, projectId, allocateNo, gongxiaoOutboundOrderNo, gongxiaoInboundOrderNo, warehouseOut, warehouseEnter, createBeginTime, createEndTime);
            gongxiaoResult.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
            gongxiaoResult.setMessage(ErrorCode.SUCCESS.getMessage());
            gongxiaoResult.setData(resultList);
            logger.info("#traceId={}# [OUT] get selectAllocateOrder success", traceId);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据项目ID和调拨单号查询调拨单
     *
     * @param projectId
     * @param allocateNo
     * @param request
     * @return
     */
    @RequestMapping("/selectByAllocateNo")
    @ResponseBody
    public GongxiaoResult selectByAllocateNo(String projectId, String allocateNo, HttpServletRequest request) {
        String traceId = null;
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        try {
            traceId = "";
            logger.info("#traceId={}# [IN][selectByAllocateNo] params: projectId={},allocateNo={}", traceId, projectId, allocateNo);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "465345", "liukai");
            AllocationOrder result = allocationService.selectInfoByAllocateNo(rpcHeader, projectId, allocateNo);
            gongxiaoResult.setReturnCode(0);
            gongxiaoResult.setMessage(ErrorCode.SUCCESS.getMessage());
            gongxiaoResult.setData(result);
            logger.info("#traceId={}# [OUT] get selectByAllocateNo success", traceId);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据项目id和调拨单号查询调拨单明细
     *
     * @param projectId
     * @param allocateNo
     * @param request
     * @return
     */
    @RequestMapping("/selectItemByAllocateNo")
    @ResponseBody
    public GongxiaoResult selectItemByAllocateNo(String projectId, String allocateNo, HttpServletRequest request) {
        String traceId = null;
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        try {
            traceId = "";
            logger.info("#traceId={}# [IN][selectItemByAllocateNo] params: projectId={},allocateNo={}", traceId, projectId, allocateNo);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "546654", "liukai");
            List<AllocationOrderItem> resultList = allocationService.getAllocationOrderItemInfos(rpcHeader, projectId, allocateNo);
            gongxiaoResult.setReturnCode(0);
            gongxiaoResult.setMessage(ErrorCode.SUCCESS.getMessage());
            gongxiaoResult.setData(resultList);
            logger.info("#traceId={}# [OUT] get selectItemByAllocateNo success: resultList.size()={}", traceId, resultList.size());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 新增调拨单
     *
     * @param createAllocateOrderRequest
     * @param request
     * @return
     */
    @RequestMapping("/insertAllocateOrder")
    @ResponseBody
    public GongxiaoResult insertAllocateOrder(@RequestBody String createAllocateOrderRequest, HttpServletRequest request) {
        String traceId = null;
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        try {
            traceId = "";
            logger.info("#traceId={}# [IN][insertAllocateOrder] params: createAllocateOrderRequest={}", traceId, createAllocateOrderRequest);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "345745867", "liukai");
            CreateAllocateOrderRequest allocateRequest = JSON.parseObject(createAllocateOrderRequest, CreateAllocateOrderRequest.class);
            if (allocateRequest != null) {
                AllocationOrder allocationOrder = new AllocationOrder();
//            allocationOrder.setAllocateNo();
                allocationOrder.setProjectIdOut(allocateRequest.getProjectoutId());
                allocationOrder.setProjectIdEnter(allocateRequest.getProjectEntryId());
                allocationOrder.setWarehouseOutId(allocateRequest.getWarehouseOutId());
//            allocationOrder.setWarehouseOut();
                allocationOrder.setWarehouseEnterId(allocateRequest.getWarehouseEntryId());
//            allocationOrder.setWarehouseEnter();
                allocationOrder.setCompanyNameOut(allocateRequest.getCompanyout());
                allocationOrder.setCompanyNameEnter(allocateRequest.getCompanyentry());
                allocationOrder.setDeliverAddress(allocateRequest.getDeliveraddress());
                allocationOrder.setReceiveAddress(allocateRequest.getReceiveaddress());
                allocationOrder.setAlloteWay(Integer.parseInt(allocateRequest.getAllocateway()));
                allocationOrder.setStatus(1);
                allocationOrder.setRequireTime(allocateRequest.getAsk_date());
                allocationOrder.setDeadline(allocateRequest.getDead_date());
                allocationOrder.setNote(allocateRequest.getRemask());
                allocationOrder.setCreateTime(allocateRequest.getYhbusiness_date());
                List<AllocationOrderItem> allocationOrderItemList = new ArrayList<>();

                List<CreateAllocationInfo> createAllocationInfoList = JSON.parseObject(allocateRequest.getItemsInfo(), new TypeReference<List<CreateAllocationInfo>>() {
                });
                int totalQuantity = 0;
                if (createAllocationInfoList != null) {
                    for (CreateAllocationInfo record : createAllocationInfoList) {
                        AllocationOrderItem allocationOrderItem = new AllocationOrderItem();
                        allocationOrderItem.setPurchaseType(record.getPurchaseType());
                        allocationOrderItem.setBatchNo(record.getBatchNo());
                        allocationOrderItem.setProjectIdOut(allocateRequest.getProjectoutId());
                        allocationOrderItem.setProjectIdEnter(allocateRequest.getProjectEntryId());
                        allocationOrderItem.setWarehouseOutId(allocateRequest.getWarehouseOutId());
//                allocationOrderItem.setWarehouseOut();
                        allocationOrderItem.setWarehouseEnterId(allocateRequest.getWarehouseEntryId());
//                allocationOrderItem.setWarehouseEnter();
                        allocationOrderItem.setProductId(record.getProductId());
                        allocationOrderItem.setProductName(record.getProductName());
                        allocationOrderItem.setProductCode(record.getProductCode());
                        allocationOrderItem.setProductUnit("GE");
//                allocationOrderItem.setInventoryQuantity(record.getQuantity());
                        allocationOrderItem.setAlloteQuantity(record.getQuantity());
                        allocationOrderItem.setInventoryStatus(record.getInventoryStatus());
                        allocationOrderItem.setStatus(1);     //状态为“未完成”
                        allocationOrderItem.setGuidPrice((long)(record.getGuidePrice()*1000000));
                        allocationOrderItem.setPuchasePrice((long)(record.getPurchasePrice()*1000000));
                        allocationOrderItem.setCostPrice((long)(record.getCostPrice()*1000000));
                        allocationOrderItem.setCreateTime(new Date());
                        allocationOrderItemList.add(allocationOrderItem);

                        totalQuantity += record.getQuantity();
                    }
                }
                allocationOrder.setTotalQuantity(totalQuantity);

                allocationService.createAllocationRecord(rpcHeader, allocationOrder, allocationOrderItemList);
            }


            gongxiaoResult.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
            gongxiaoResult.setMessage(ErrorCode.SUCCESS.getMessage());
            logger.info("#traceId={}# [OUT] get insertAllocateOrder success", traceId);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

}
