package com.yhglobal.gongxiao.warehousemanager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.util.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.warehouse.service.AllocationService;
import com.yhglobal.gongxiao.warehousemanagement.bo.AllocationOrderShowModel;
import com.yhglobal.gongxiao.warehousemanagement.model.AllocationOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.AllocationOrderItem;
import com.yhglobal.gongxiao.warehousemanagement.model.CreateAllocationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 调拨controller
 * @author liukai
 */
@Controller
@RequestMapping("/allocte")
public class AllocationController {

    private static Logger logger = LoggerFactory.getLogger(AllocationController.class);

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

    @Reference
    AllocationService allocationService;


    /**
     * 查询调拨单
     * @param projectId  项目id
     * @param allocateNo  调拨单号
     * @param gongxiaoOutboundOrderNo  调拨出库单号
     * @param gongxiaoInboundOrderNo   调拨入库单号
     * @param warehouseOut     调拨出库仓
     * @param warehouseEnter    调拨入库仓
     * @param status            调拨单状态
     * @param createBeginTime   创建起始时间
     * @param createEndTime     创建结束时间
     * @param pageNumber        页码
     * @param pageSize          页数
     * @param request
     * @return
     */
    @RequestMapping("/selectAllocateOrder")
    @ResponseBody
    public GongxiaoResult selectAllocateOrder(String projectId, String allocateNo, String gongxiaoOutboundOrderNo, String gongxiaoInboundOrderNo,
                                              String warehouseOut, String warehouseEnter, String status, String createBeginTime, String createEndTime,int pageNumber, int pageSize, HttpServletRequest request){
        String traceId = null;
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),request.getServletPath());
            logger.info("#traceId={}# [IN][selectAllocateOrder] params: projectId={},allocateNo={},gongxiaoOutboundOrderNo={},gongxiaoInboundOrderNo={},warehouseOut={},warehouseEnter={},status={},createBeginTime={},createEndTime={}" , traceId,projectId,allocateNo,gongxiaoOutboundOrderNo,gongxiaoInboundOrderNo,warehouseOut,warehouseEnter,status,createBeginTime,createEndTime);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            PageInfo<AllocationOrderShowModel> resultList = allocationService.getAllRecordByCondition(rpcHeader,pageNumber,pageSize,projectId,allocateNo,gongxiaoOutboundOrderNo,gongxiaoInboundOrderNo,warehouseOut,warehouseEnter,status,createBeginTime,createEndTime);

            gongxiaoResult.setReturnCode(0);
            gongxiaoResult.setMessage(ErrorCode.SUCCESS.getMessage());
            gongxiaoResult.setData(resultList);
            logger.info("#traceId={}# [OUT] get selectAllocateOrder success: resultList.size()={}", traceId, resultList.getTotal());
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据项目ID和调拨单号查询调拨单
     * @param projectId
     * @param allocateNo
     * @param request
     * @return
     */
    @RequestMapping("/selectByAllocateNo")
    @ResponseBody
    public GongxiaoResult selectByAllocateNo(String projectId,String allocateNo, HttpServletRequest request){
        String traceId = null;
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),request.getServletPath());
            logger.info("#traceId={}# [IN][selectByAllocateNo] params: projectId={},allocateNo={}" , traceId,projectId,allocateNo);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            AllocationOrder result = allocationService.selectInfoByAllocateNo(rpcHeader,projectId,allocateNo);
            gongxiaoResult.setReturnCode(0);
            gongxiaoResult.setMessage(ErrorCode.SUCCESS.getMessage());
            gongxiaoResult.setData(result);
            logger.info("#traceId={}# [OUT] get selectByAllocateNo success", traceId);
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据项目id和调拨单号查询调拨单明细
     * @param projectId
     * @param allocateNo
     * @param request
     * @return
     */
    @RequestMapping("/selectItemByAllocateNo")
    @ResponseBody
    public GongxiaoResult selectItemByAllocateNo(String projectId,String allocateNo, HttpServletRequest request){
        String traceId = null;
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),request.getServletPath());
            logger.info("#traceId={}# [IN][selectItemByAllocateNo] params: projectId={},allocateNo={}" , traceId,projectId,allocateNo);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            List<AllocationOrderItem> resultList = allocationService.getAllocationOrderItemInfos(rpcHeader,projectId,allocateNo);
            gongxiaoResult.setReturnCode(0);
            gongxiaoResult.setMessage(ErrorCode.SUCCESS.getMessage());
            gongxiaoResult.setData(resultList);
            logger.info("#traceId={}# [OUT] get selectItemByAllocateNo success: resultList.size()={}", traceId,resultList.size());
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 新增调拨单
     * @param companyout
     * @param companyentry
     * @param projectoutId
     * @param projectEntryId
     * @param warehouseOutId
     * @param warehouseEntryId
     * @param deliveraddress
     * @param receiveaddress
     * @param allocateway
     * @param yhbusiness_date
     * @param ask_date
     * @param dead_date
     * @param remask
     * @param itemsInfo
     * @param request
     * @return
     */
    @RequestMapping("/insertAllocateOrder")
    @ResponseBody
    public GongxiaoResult insertAllocateOrder(String companyout,String companyentry,String projectoutId,String projectEntryId,String warehouseOutId,
                                              String warehouseEntryId,String deliveraddress,String receiveaddress,String allocateway,String yhbusiness_date,
                                              String ask_date,String dead_date,String remask,String itemsInfo, HttpServletRequest request){
        String traceId = null;
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),request.getServletPath());
            logger.info("#traceId={}# [IN][insertAllocateOrder] params: companyout={},companyentry={},projectoutId={},projectEntryId={},warehouseOutId={},warehouseEntryId={},deliveraddress={},receiveaddress={},allocateway={},yhbusiness_date={},ask_date={},dead_date={},remask={},itemsInfo={}" ,
                    traceId,companyout,companyentry,projectoutId,projectEntryId,warehouseOutId,warehouseEntryId,
                    deliveraddress,receiveaddress,allocateway,yhbusiness_date,ask_date,dead_date,remask,itemsInfo);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            List<CreateAllocationInfo> createAllocationInfoList  = JSON.parseObject(itemsInfo,new TypeReference<ArrayList<CreateAllocationInfo>>() {});
            AllocationOrder allocationOrder = new AllocationOrder();
//            allocationOrder.setAllocateNo();
            allocationOrder.setProjectIdOut(projectoutId);
            allocationOrder.setProjectIdEnter(projectEntryId);
            allocationOrder.setWarehouseOutId(warehouseOutId);
//            allocationOrder.setWarehouseOut();
            allocationOrder.setWarehouseEnterId(warehouseEntryId);
//            allocationOrder.setWarehouseEnter();
            allocationOrder.setCompanyNameOut(companyout);
            allocationOrder.setCompanyNameEnter(companyentry);
            allocationOrder.setDeliverAddress(deliveraddress);
            allocationOrder.setReceiveAddress(receiveaddress);
            allocationOrder.setAlloteWay(Integer.parseInt(allocateway));
            allocationOrder.setStatus(1);
            allocationOrder.setRequireTime(ask_date);
            allocationOrder.setDeadline(dead_date);
            allocationOrder.setNote(remask);
            allocationOrder.setCreateTime(yhbusiness_date);
            List<AllocationOrderItem> allocationOrderItemList = new ArrayList<>();
            for (CreateAllocationInfo record : createAllocationInfoList){
                AllocationOrderItem allocationOrderItem = new AllocationOrderItem();
                allocationOrderItem.setBatchNo(record.getBatchNo());
                allocationOrderItem.setProjectIdOut(projectoutId);
                allocationOrderItem.setProjectIdEnter(projectEntryId);
                allocationOrderItem.setWarehouseOutId(warehouseOutId);
//                allocationOrderItem.setWarehouseOut();
                allocationOrderItem.setWarehouseEnterId(warehouseEntryId);
//                allocationOrderItem.setWarehouseEnter();
                allocationOrderItem.setProductName(record.getProductName());
                allocationOrderItem.setProductCode(record.getProductCode());
                allocationOrderItem.setProductUnit("个");
//                allocationOrderItem.setInventoryQuantity(record.getQuantity());
                allocationOrderItem.setAlloteQuantity(record.getQuantity());
                allocationOrderItem.setInventoryStatus(1);
                allocationOrderItem.setStatus(1);
                allocationOrderItem.setGuidPrice(record.getGuidePrice());
                allocationOrderItem.setPuchasePrice(record.getPurchasePrice());
                allocationOrderItem.setCostPrice(record.getCostPrice());
                allocationOrderItemList.add(allocationOrderItem);

            }

            allocationService.createAllocationRecord(rpcHeader,allocationOrder,allocationOrderItemList);

            gongxiaoResult.setReturnCode(0);
            gongxiaoResult.setMessage(ErrorCode.SUCCESS.getMessage());
            logger.info("#traceId={}# [OUT] get insertAllocateOrder success", traceId);
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 修改调拨单
     * @param projectId
     * @param allocateNo
     * @param request
     * @return
     */
    @RequestMapping("/updateByAllocateNo")
    @ResponseBody
    public GongxiaoResult updateByAllocateNo(String projectId,String allocateNo, HttpServletRequest request){
        String traceId = null;
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),request.getServletPath());
            logger.info("#traceId={}# [IN][selectByAllocateNo] params: projectId={},allocateNo={}" , traceId,projectId,allocateNo);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            allocationService.updateByAllocateNo(rpcHeader,projectId,allocateNo);
            gongxiaoResult.setReturnCode(0);
            gongxiaoResult.setMessage(ErrorCode.SUCCESS.getMessage());
            logger.info("#traceId={}# [OUT] get selectByAllocateNo success", traceId);
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
}
