package com.yhglobal.gongxiao.warehouse.controller.inventory;

import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.grpc.client.WarehouseRpcStubStore;
import com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryServiceGrpc;
import com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure;
import com.yhglobal.gongxiao.type.WmsSourceChannel;
import com.yhglobal.gongxiao.warehouseapi.XpsWarehouseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 插入库存数据的工具类controller
 * @author liukai
 */
@RequestMapping("/inventory")
@Controller
public class InSertInventoryController {

    private static Logger logger = LoggerFactory.getLogger(InSertInventoryController.class);

    @RequestMapping(value = "/insertInventory",method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult insertInventory(String projectId, HttpServletRequest request){
        logger.info("[IN] [insertInventory]");
        GongxiaoResult result = new GongxiaoResult();
        try {
            //插入eas即时库存grpc服务
            InsertInventoryServiceGrpc.InsertInventoryServiceBlockingStub insertInventoryService = WarehouseRpcStubStore.getRpcStub(InsertInventoryServiceGrpc.InsertInventoryServiceBlockingStub.class);
            InsertInventoryStructure.GetInsertInventoryRequest getInsertInventoryRequest = InsertInventoryStructure.GetInsertInventoryRequest.newBuilder().setProjectId(Long.parseLong(projectId)).build();
            InsertInventoryStructure.GetInsertInventoryResponse rpcResponse = insertInventoryService.insertInventory(getInsertInventoryRequest);
            int i = rpcResponse.getErrorCode();
            result.setReturnCode(i);
            logger.info("[OUT] get insertInventory success");
            return result;
        }catch (Exception e){
            logger.error("#errorMessage：{}",e.getMessage());
            return new GongxiaoResult(1, ErrorCode.UNKNOWN_ERROR);
        }

    }

    @RequestMapping("/checkEasInventory")
    @ResponseBody
    public GongxiaoResult checkEasInventory(String projectId,HttpServletRequest request){

        logger.info("[IN] [checkEasInventory] params: projectId={}", projectId);
        GongxiaoResult result = new GongxiaoResult();
        try {
            String channelTocken = "437834345";
            //核对eas即时库存grpc服务
            InsertInventoryServiceGrpc.InsertInventoryServiceBlockingStub insertInventoryService = WarehouseRpcStubStore.getRpcStub(InsertInventoryServiceGrpc.InsertInventoryServiceBlockingStub.class);
            InsertInventoryStructure.GetCheckEasInventoryRequest getInsertInventoryRequest = InsertInventoryStructure.GetCheckEasInventoryRequest.newBuilder().setProjectId(Long.parseLong(projectId)).build();
            InsertInventoryStructure.GetCheckEasInventoryResponse rpcResponse = insertInventoryService.checkEasInventory(getInsertInventoryRequest);
            int i = rpcResponse.getErrorCode();
            logger.info("[OUT] get checkEasInventory success");
            return result;
        }catch (Exception e){
            logger.error("#errorMessage：{}",e.getMessage());
            return new GongxiaoResult(ErrorCode.UNKNOWN_ERROR.getErrorCode(), ErrorCode.UNKNOWN_ERROR);
        }

    }

    @RequestMapping("/selectEasInventoryCheck")
    @ResponseBody
    public GongxiaoResult selectEasInventoryCheck(String projectId,HttpServletRequest request){
        logger.info("[IN] [selectEasInventoryCheck] params: projectId={}", projectId);
        GongxiaoResult result = new GongxiaoResult();
        try {
            String channelTocken = "437834345";
            //核对eas即时库存grpc服务
            InsertInventoryServiceGrpc.InsertInventoryServiceBlockingStub insertInventoryService = WarehouseRpcStubStore.getRpcStub(InsertInventoryServiceGrpc.InsertInventoryServiceBlockingStub.class);
            InsertInventoryStructure.GetCheckEasInventoryRequest getInsertInventoryRequest = InsertInventoryStructure.GetCheckEasInventoryRequest.newBuilder().setProjectId(Long.parseLong(projectId)).build();
            InsertInventoryStructure.GetCheckEasInventoryResponse rpcResponse = insertInventoryService.checkEasInventory(getInsertInventoryRequest);
            int i = rpcResponse.getErrorCode();
            logger.info("[OUT] get selectEasInventoryCheck success");
            return result;
        }catch (Exception e){
            logger.error("#errorMessage：{}",e.getMessage());
            return new GongxiaoResult(ErrorCode.UNKNOWN_ERROR.getErrorCode(), ErrorCode.UNKNOWN_ERROR);
        }

    }
}
