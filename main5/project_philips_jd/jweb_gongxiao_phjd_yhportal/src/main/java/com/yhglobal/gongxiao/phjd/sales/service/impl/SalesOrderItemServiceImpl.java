package com.yhglobal.gongxiao.phjd.sales.service.impl;

import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.phjd.sales.model.vo.SalesOrderItemVO;
import com.yhglobal.gongxiao.phjd.sales.service.SalesOrderItemService;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceGrpc;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure;
import org.springframework.stereotype.Service;

/**
 * @author weizecheng
 * @time 2018/8/21 15:42
 */
@Service
public class SalesOrderItemServiceImpl implements SalesOrderItemService {

    @Override
    public SalesOrderItemVO getSalesOrderItemByOrderNoAndProductCode(GongxiaoRpc.RpcHeader rpcHeader, String salesOrderNo, String productCode, long projectId) throws Exception{
            SalesOrderServiceStructure.GetOrderItemRequest rpcRequest = SalesOrderServiceStructure.GetOrderItemRequest.newBuilder()
                    .setProjectId(projectId)
                    .setRpcHeader(rpcHeader)
                    .setProductCode(productCode)
                    .setSalesOrderNo(salesOrderNo).build();
            SalesOrderServiceGrpc.SalesOrderServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SalesOrderServiceGrpc.SalesOrderServiceBlockingStub.class);
            SalesOrderServiceStructure.GetOrderItemResponse rpcResponse = rpcStub.getOrderDetailBySalesOrderNoAndProjectCode(rpcRequest);
            SalesOrderItemVO salesOrderItemVO = null;
            if(ErrorCode.TARGET_DATA_NOT_EXIST.getErrorCode() != rpcResponse.getReturnCode()){
                salesOrderItemVO = new SalesOrderItemVO();
                salesOrderItemVO.setCurrencyCode(rpcResponse.getCurrencyCode());
                salesOrderItemVO.setCurrencyName(rpcResponse.getCurrencyName());
                salesOrderItemVO.setSalesGuidePrice(rpcResponse.getSalesGuidePrice());
                salesOrderItemVO.setWholesalePrice(rpcResponse.getWholesalePrice());
            }
            return salesOrderItemVO;
    }
}
