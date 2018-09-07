package com.yhglobal.gongxiao.inventory.inventorycheck;


import com.yhglobal.gongxiao.inventory.model.inventory.WmsInventoryReq;
import com.yhglobal.gongxiao.inventory.model.inventory.WmsInventoryResp;

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
