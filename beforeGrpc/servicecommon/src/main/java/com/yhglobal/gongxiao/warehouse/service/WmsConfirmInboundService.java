package com.yhglobal.gongxiao.warehouse.service;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.instockconfirm.Data;

/**
 * WMS系统入库单确认接口
 *
 * @author liukai
 */
public interface WmsConfirmInboundService {

    /**
     * WMS系统入库单确认接
     * @param inStockConfirmRequest  入库确认信息
     * @return
     */
    void confirmWmsInboundInfo(RpcHeader rpcHeader, Data inStockConfirmRequest);

}
