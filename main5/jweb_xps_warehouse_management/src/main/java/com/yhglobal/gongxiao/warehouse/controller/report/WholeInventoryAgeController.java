package com.yhglobal.gongxiao.warehouse.controller.report;

import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.grpc.client.WarehouseRpcStubStore;
import com.yhglobal.gongxiao.inventory.wholeinventoryage.microservice.WholeInventoryAgeServiceGrpc;
import com.yhglobal.gongxiao.inventory.wholeinventoryage.microservice.WholeInventoryAgeStructure;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.warehouse.model.WholeInventoryAge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 报表整体库龄情况
 */
@Controller
@RequestMapping("/wholeinventoryAge")
public class WholeInventoryAgeController {

    private static Logger logger = LoggerFactory.getLogger(WholeInventoryAgeController.class);

    @RequestMapping(value = "/getWholeinventoryAge", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getInventoryMonthAmount(String projectId, HttpServletRequest request, HttpServletResponse response) {
        logger.info("#traceId={}# [IN][getInventory180Age] params: projectId = {} ", projectId);
        String traceId = "15827545";
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
        try {
            WholeInventoryAgeServiceGrpc.WholeInventoryAgeServiceBlockingStub wholeInventoryAgeService = WarehouseRpcStubStore.getRpcStub(WholeInventoryAgeServiceGrpc.WholeInventoryAgeServiceBlockingStub.class);
            WholeInventoryAgeStructure.GetWholeInventoryAgeRequest wholeInventoryAgeRequest = WholeInventoryAgeStructure.GetWholeInventoryAgeRequest.newBuilder()
                    .setProjectId(Long.parseLong(projectId))
                    .setRpcHeader(rpcHeader)
                    .build();
            WholeInventoryAgeStructure.GetWholeInventoryAgeRespon getWholeInventoryAgeRespon = wholeInventoryAgeService.getWholeInventoryAge(wholeInventoryAgeRequest);

            WholeInventoryAge wholeInventoryAge = new WholeInventoryAge();
            String[] stage = wholeInventoryAge.getStage();
            double[] sumOfmoney = wholeInventoryAge.getSumOfmoney();
            double[] proportion = wholeInventoryAge.getProportion();
            int j = 0;
            for (WholeInventoryAgeStructure.WholeInventoryAge record : getWholeInventoryAgeRespon.getListList()) {
                if (record.getStage() == 1) {
                    stage[j] = "60天内";
                } else if (record.getStage() == 2) {
                    stage[j] = "60天—90天";
                } else if (record.getStage() == 3) {
                    stage[j] = "90天—180天";
                } else if (record.getStage() == 4) {
                    stage[j] = "180天—360天";
                } else if (record.getStage() == 5) {
                    stage[j] = "大于360天";
                }
                sumOfmoney[j] = record.getSumOfmoney() / 1000000.0;
                proportion[j] = record.getProportion();

                j++;

            }

            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), wholeInventoryAge);
        } catch(Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

    }

}
