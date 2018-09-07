package com.yhglobal.gongxiao.warehouse.controller.warehouse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.util.CommonUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.warehouse.model.CreateOutboundItemInfo;
import com.yhglobal.gongxiao.warehouse.model.ManualOutboundOrder;
import com.yhglobal.gongxiao.warehouse.service.ManualOutboundService;
import com.yhglobal.gongxiao.warehouse.service.OutboundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 其它出库单
 * @author liukai
 */
@Controller
@RequestMapping("/manualOutbound")
public class ManualOutboundController {

    private static Logger logger = LoggerFactory.getLogger(ManualInboundController.class);

//    @Autowired
//    ProductService productService;

    @Autowired
    ManualOutboundService manualOutboundService;

//    @Autowired
//    InventoryBatchService inventoryBatchService;

    @Autowired
    OutboundService outboundService;


    /**
     * 查询所有的其他出库单
     * @param request
     * @return
     */
    @RequestMapping("/getManualOutboundList")
    @ResponseBody
    public GongxiaoResult getManualOutboundListList(HttpServletRequest request){
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = "";
            logger.info("#traceId={}# [IN][getManualOutboundListList] ", traceId);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId,"", "");
            List<ManualOutboundOrder> resultList = manualOutboundService.getManualOutboundList(rpcHeader);
            gongxiaoResult.setReturnCode(0);
            gongxiaoResult.setData(resultList);logger.info("#traceId={}# [OUT] get getManualOutboundListList success: resultList.size()={}", traceId, resultList.size());
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 新增其他出库单
     * @param projectId             项目id
     * @param warehouseId           仓库id
     * @param warehouseName         仓库名称
     * @param delieverAddress       发货地址
     * @param supplierName          供应商
     * @param businessDate          业务日期
     * @param outboundType          出库类型
     * @param remark                备注
     * @param itemsInfo             出库商品明细
     * @param request
     * @return
     */
    @RequestMapping("/insertManualOutboundOrder")
    @ResponseBody
    public GongxiaoResult insertManualOutboundOrder(String projectId,
                                                    String warehouseId,
                                                    String warehouseName,
                                                    String delieverAddress,
                                                    String supplierName,
                                                    String businessDate,
                                                    int outboundType,
                                                    String remark,
                                                    String itemsInfo, HttpServletRequest request){
        String traceId = null;
        try {
            traceId = "";
            logger.info("#traceId={}# [IN][insertManualOutboundOrder] params: projectId={},warehouseId={},warehouseName={},delieverAddress={},supplierName={},businessDate={},outboundType={},remark={},itemsInfo={} ",traceId, projectId,warehouseId,warehouseName,delieverAddress,supplierName,businessDate,outboundType,remark,itemsInfo);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
            //将货品列表json数组的字符串转换为List

            ArrayList<CreateOutboundItemInfo> createOutboundItemInfos = JSON.parseObject(itemsInfo, new TypeReference<ArrayList<CreateOutboundItemInfo>>() {});

            ManualOutboundOrder manualOutboundOrder = new ManualOutboundOrder();
            manualOutboundOrder.setOutboundType(outboundType);
            manualOutboundOrder.setStatus(0);
            manualOutboundOrder.setCreatedByName(rpcHeader.getUsername());
            manualOutboundOrder.setCreatedById(rpcHeader.getUid());
            String format = "yyyy-MM-dd";
            manualOutboundOrder.setCreateTime(CommonUtil.StringToDateWithFormat(businessDate, format));
            manualOutboundOrder.setNote(remark);
            manualOutboundOrder.setProjectId(projectId);
            manualOutboundOrder.setDeliverAddress(delieverAddress);
            manualOutboundOrder.setWarehouseId(warehouseId);
            manualOutboundOrder.setWarehouseName(warehouseName);


            int insertNumber = manualOutboundService.createManualOutbound(rpcHeader,manualOutboundOrder,createOutboundItemInfos);
            logger.info("#traceId={}# [OUT] insertManualOutboundOrder success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), insertNumber);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 修改其他出库单
     * @param manualJson
     * @param request
     * @return
     */
    @RequestMapping("/updateOutbound")
    @ResponseBody
    public GongxiaoResult updateManualOutboundOrder(@RequestBody String manualJson, HttpServletRequest request){
        String traceId = null;
        try {
            traceId = "";
            logger.info("#traceId={}# [IN][updateManualOutboundOrder] params: manualJson={} ",traceId, manualJson);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
            //将货品列表json数组的字符串转换为List
            ManualOutboundOrder manualOutboundOrder = JSON.parseObject(manualJson,ManualOutboundOrder.class);
            int updateNumber = manualOutboundService.updateManualOutboundOrder(rpcHeader, manualOutboundOrder);
            logger.info("#traceId={}# [OUT] updateManualOutboundOrder success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), updateNumber);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 获取商品信息
     * @param pageNo
     * @param pageSize
     * @param request
     * @return
     */
    @RequestMapping("/getProductList")
    @ResponseBody
    public GongxiaoResult getProductList(int pageNo, int pageSize, HttpServletRequest request){
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = "";
            logger.info("#traceId={}# [IN][getProductList] ", traceId);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
//            List<ProductBasic>  resultList = productService.selectAll(rpcHeader,pageNo,pageSize);
            gongxiaoResult.setReturnCode(0);
//            gongxiaoResult.setData(resultList);logger.info("#traceId={}# [OUT] get getProductList success: resultList.size()={}", traceId, resultList.size());
        }catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 其他出库批次查询
     *
     * @param projectId   项目id
     * @param productCode 货品编码
     * @param warehouseId 货品编码
     * @return
     */
    @RequestMapping("/batch")
    @ResponseBody
    public GongxiaoResult detail(HttpServletRequest request, HttpServletResponse response, int pageNumber, int pageSize, String projectId, String productCode, String warehouseId) {

        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = "";
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
            logger.info("#traceId={}# [IN][getSalesOrderList][IN] params:  salesOrderNo={}", traceId);
//            PageInfo<InventoryBatch> list = inventoryBatchService.getBatchDetailByWarehouse(rpcHeader, projectId, productCode, warehouseId, pageNumber, pageSize);
//            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), list);
            logger.info("#traceId={}# [getSalesOrderList][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


}
