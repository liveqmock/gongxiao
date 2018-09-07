package com.yhglobal.gongxiao.warehouse.util;

import com.yhglobal.gongxiao.common.RpcHeader;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public class test {
    public static void main(String[] args) throws Exception {
        String STOCK_IN_ORDER_NOTIFY = "http://apigateway.dev.yhglobal.cn/api/v1/MasterData/GetProjects";
        RpcHeader rpcHeader = new RpcHeader();
        rpcHeader.setTraceId("123");
        String wmsInStockNotifyRequest = "{}";
        String result = HttpRequestUtil.sendPost(STOCK_IN_ORDER_NOTIFY, wmsInStockNotifyRequest, "utf-8");

    }
}
