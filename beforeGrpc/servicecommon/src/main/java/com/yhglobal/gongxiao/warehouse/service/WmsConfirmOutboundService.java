package com.yhglobal.gongxiao.warehouse.service;

import com.yhglobal.gongxiao.warehousemanagement.dto.wms.outstockconfirm.Data;

/**
 * WMS系统出库单确认接口
 * @author liukai
 */
public interface WmsConfirmOutboundService {

    /**
     * WMS系统出库单确认
     * @param outStockConfirmRequest  出库确认信息
     * @return
     */
    void confirmWmsOutboundInfo(Data outStockConfirmRequest);

}
