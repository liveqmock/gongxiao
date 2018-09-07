package com.yhglobal.gongxiao.purchase.service;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;

/**
 * 采购文件服务类
 *
 * @author  陈浩
 */
public interface PurchaseFileService {

    /**
     * 采购单解析
     * @param rpcHeader
     * @param filePath 文件地址
     * @return
     */
    RpcResult parsePurchaseOrderList(RpcHeader rpcHeader,
                                     String filePath,
                                     String projectId,
                                     String projectName);

}
