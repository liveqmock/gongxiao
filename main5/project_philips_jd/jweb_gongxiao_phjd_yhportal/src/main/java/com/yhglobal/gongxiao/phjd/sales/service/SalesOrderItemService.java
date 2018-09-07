package com.yhglobal.gongxiao.phjd.sales.service;

import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.phjd.sales.model.vo.SalesOrderItemVO;

/**
 * @author weizecheng
 * @time 2018/8/21 15:42
 */
public interface SalesOrderItemService {

    /**
     * 根据下列参数获取单个销售订单商品
     *
     * @author weizecheng
     * @date 2018/8/21 15:55
     * @param rpcHeader 请求的基本表头信息
     * @param salesOrderNo 销售订单号
     * @param productCode 商品编号
     * @param projectId 项目Id
     * @return com.yhglobal.gongxiao.phjd.sales.vo.SalesOrderItemVO
     * @throws Exception 一般为调用RPC异常
     */
    SalesOrderItemVO getSalesOrderItemByOrderNoAndProductCode(GongxiaoRpc.RpcHeader rpcHeader,
                                                              String salesOrderNo,
                                                              String productCode,
                                                              long projectId) throws Exception;
}
