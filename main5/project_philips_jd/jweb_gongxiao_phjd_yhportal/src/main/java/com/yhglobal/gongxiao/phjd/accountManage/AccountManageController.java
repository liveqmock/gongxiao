package com.yhglobal.gongxiao.phjd.accountManage;

import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.microservice.GrpcUpdateAccountServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.GrpcUpdateAccountStructure;
import com.yhglobal.gongxiao.phjd.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.phjd.base.protal.PortalConfig;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 越海返利核销的controller  京东
 * @author 王帅
 */
@Controller
@RequestMapping("/accountManage")
public class AccountManageController {

    private static Logger logger = LoggerFactory.getLogger(AccountManageController.class);

    @Autowired
    PortalConfig portalConfig; //property注入类

    @Autowired
    PortalUserInfo portalUserInfo; //用户信息注入类

    @RequestMapping(value = "/salesDiference")
    @ResponseBody
    public GongxiaoResult yhglobalCouponWriteroff(HttpServletRequest request,long supplierId, String supplierName,
                                                  @RequestParam(defaultValue = "CNY")String currencyCode, String remark,
                                                  Double postingAmount, Long projectId ) {
        if (projectId == null) {
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage() + ": projectId is null");
        }
        GongxiaoResult gongxiaoResult = null;
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader("look","123","simon");
        // 测试grpc更新销售差价账户
//        GrpcUpdateAccountStructure.UpdateRequest.Builder reqBuilder = GrpcUpdateAccountStructure.UpdateRequest.newBuilder();
//        reqBuilder.setAmount(postingAmount);
//        reqBuilder.setProjectId(projectId);
//        reqBuilder.setRpcHeader(rpcHeader);
//        reqBuilder.setSupplierId(supplierId);
//        reqBuilder.setTransactionTime(new Date().getTime());
//        reqBuilder.setPrefix("PHJD");
//        reqBuilder.setRemark("test");
//
//        GrpcUpdateAccountServiceGrpc.GrpcUpdateAccountServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(GrpcUpdateAccountServiceGrpc.GrpcUpdateAccountServiceBlockingStub.class);
//        GrpcUpdateAccountStructure.UpdateResponse updateResponse = blockingStub.updateSalesDifferenceAccount(reqBuilder.build());
//        gongxiaoResult = new GongxiaoResult(updateResponse.getCode(), updateResponse.getMsg());


        return gongxiaoResult;
    }
}
