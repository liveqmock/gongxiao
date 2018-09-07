package com.yhglobal.gongxiao.warehouse.service;


import com.yhglobal.gongxiao.warehouse.model.dto.wms.inventory.WmsInventoryReq;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.inventory.WmsInventoryResp;

/**
 * 查询WMS库存接口
 * @author liukai
 */
public interface WmsQuerryInventoryService {

    /**
     * 查询wms库存
     * @Param  WmsInventoryReq
     * @return
     */
    WmsInventoryResp getWmsInventory(WmsInventoryReq wmsInventoryReq, String wmsIp);
}
