package com.yhglobal.gongxiao.inventory.inventorycheck;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.inventory.model.inventory.InventoryResultDetailDto;
import com.yhglobal.gongxiao.inventory.model.inventory.WmsInventoryReq;
import com.yhglobal.gongxiao.inventory.model.inventory.WmsInventoryResp;
import com.yhglobal.gongxiao.inventory.util.HttpRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class WmsQuerryInventoryServiceImpl implements WmsQuerryInventoryService {
    private static Logger logger = LoggerFactory.getLogger(WmsQuerryInventoryServiceImpl.class);

    public static final String WMS_INVENTORY_QUERRY = "/api/services/app/order/InventoryQuery";
    @Override
    public WmsInventoryResp getWmsInventory(WmsInventoryReq wmsInventoryReq, String wmsIp) {
        logger.info("[IN][getWmsInventory]: wmsInventoryReq={},wmsIp={}",  JSON.toJSONString(wmsInventoryReq));
        String querryInventory = wmsIp + WMS_INVENTORY_QUERRY;
        WmsInventoryResp resp = null;
        List<InventoryResultDetailDto> inventoryResultDetailDtoList = new ArrayList<>();
        try {
            String httpResultJson = HttpRequestUtil.sendPost(querryInventory, JSON.toJSONString(wmsInventoryReq),"utf-8");
            logger.info("[OUT] get getWmsInventory success: httpResult={}", httpResultJson);
            resp = JSON.parseObject(httpResultJson,WmsInventoryResp.class);

            if (resp.getResult() != null){
                inventoryResultDetailDtoList = resp.getResult().getInventoryResults();
                logger.info("wms inventory result={}",JSON.toJSONString(inventoryResultDetailDtoList));
            }
            return resp;
        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);
            return new WmsInventoryResp(null,false);
        }
    }
}
