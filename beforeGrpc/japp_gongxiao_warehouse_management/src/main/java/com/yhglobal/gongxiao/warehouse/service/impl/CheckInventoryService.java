package com.yhglobal.gongxiao.warehouse.service.impl;

import com.yhglobal.gongxiao.foundation.channel.Channel;
import com.yhglobal.gongxiao.foundation.channel.ChannelDao;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.productBasic.dao.ProductBasicDao;
import com.yhglobal.gongxiao.foundation.project.dao.ProjectDao;
import com.yhglobal.gongxiao.foundation.project.model.Project;
import com.yhglobal.gongxiao.foundation.warehouse.dao.ProductInventoryDao;
import com.yhglobal.gongxiao.foundation.warehouse.dao.WarehouseDao;
import com.yhglobal.gongxiao.foundation.warehouse.model.Warehouse;
import com.yhglobal.gongxiao.inventory.model.ProductInventory;
import com.yhglobal.gongxiao.warehouse.bootstrap.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.service.WmsQuerryInventoryService;
import com.yhglobal.gongxiao.warehouse.type.WmsInventoryType;
import com.yhglobal.gongxiao.warehouse.type.WmsSourceChannel;
import com.yhglobal.gongxiao.warehousemanagement.bo.InventoryCheckModel;
import com.yhglobal.gongxiao.warehousemanagement.dao.CheckInventoryDao;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.inventory.InventoryQueryDetailDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.inventory.InventoryResultDetailDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.inventory.WmsInventoryReq;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.inventory.WmsInventoryResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 核对库存
 *
 * @author liukai
 */
@Service
public class CheckInventoryService {
    private static Logger logger = LoggerFactory.getLogger(CheckInventoryService.class);

    @Autowired
    WmsQuerryInventoryService wmsQuerryInventoryService;

    @Autowired
    ProjectDao projectDao;

    @Autowired
    WarehouseDao warehouseDao;

    @Autowired
    ProductBasicDao productBasicDao;

    @Autowired
    WarehouseConfig warehouseConfig;

    @Autowired
    ProductInventoryDao productInventoryDao;

    @Autowired
    ChannelDao channelDao;

    @Autowired
    CheckInventoryDao checkInventoryDao;

    @Scheduled(cron = "0 0 0 * * ?")
    public void checkInventory() {
        logger.info("[IN][checkInventory] params: start to check WMS and FenXiao inventory");

        try {
            List<Project> projectList = projectDao.selectAll();
            List<Warehouse> warehouseList = warehouseDao.selectAll();
            String wmsIp = warehouseConfig.getWmsUrl();
            Channel channel = channelDao.selectByChannelId(Integer.parseInt(WmsSourceChannel.CHANNEL_YHGLOBAL.getChannelId()));
            String ownerCode = channel.getCustCode();
            List<InventoryCheckModel> resultList = new ArrayList<>();


            for (Project project : projectList) {
                for (Warehouse warehouse : warehouseList) {
                    List<InventoryQueryDetailDto> inventoryList = new ArrayList<>();
                    InventoryQueryDetailDto inventoryQueryDetailDto = new InventoryQueryDetailDto(warehouse.getWmsWarehouseCode(), ownerCode, project.getEasProjectCode(), null);
                    inventoryList.add(inventoryQueryDetailDto);
                    WmsInventoryReq wmsInventoryReq = new WmsInventoryReq();
                    wmsInventoryReq.setInventoryList(inventoryList);
                    int maxRetriesTime = 10;
                    WmsInventoryResp resp = new WmsInventoryResp();
                    while (maxRetriesTime-- > 0) {
                        resp = wmsQuerryInventoryService.getWmsInventory(wmsInventoryReq, wmsIp);
                        if (resp.isSuccess()) {
                            break;
                        }
                    }
                    if (maxRetriesTime == 0) {    //如果重试了10次还是不行，打印错误日志
                        logger.error("# errorMessage:查询WMS库存的时候超时了");
                    }

                    for (InventoryResultDetailDto wmsRecord : resp.getResult().getInventoryResults()) {
                        InventoryCheckModel record = new InventoryCheckModel();
                        record.setDateTime(new Date());
                        record.setProjectId(String.valueOf(project.getProjectId()));
                        record.setProjectName(project.getProjectName());
                        ProductBasic productBasic = productBasicDao.getByWmsProductCode(wmsRecord.getItemCode());
                        if (productBasic == null){    //对于wms仓库有的商品，分销没有的
                            logger.info("wms仓库有的商品，分销没有的商品,projectId={},inventoryType={},productCode={},warehouseId={}",project.getProjectId(), wmsRecord.getInventoryType(), wmsRecord.getItemCode(), warehouse.getWarehouseId());
                            record.setProductCode(wmsRecord.getItemCode());
                            record.setProductId("");
                            record.setProductName("");
                            record.setWarehouseId(String.valueOf(warehouse.getWarehouseId()));
                            record.setWarehouseName(warehouse.getWarehouseName());
                            if (WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType() == Integer.parseInt(wmsRecord.getInventoryType())) {
                                record.setWmsPerfectQuantity(wmsRecord.getQuantity());
                                record.setFenxiaoPerfectQuantity(0);
                                record.setPerfectDifferent(wmsRecord.getQuantity());
                            } else if (WmsInventoryType.DEFECTIVE.getInventoryType() == Integer.parseInt(wmsRecord.getInventoryType())) {
                                record.setWmsImperfectQuantity(wmsRecord.getQuantity());
                                record.setFenxiaoImperfectQuantity(0);
                                record.setImPerfectDifferent(wmsRecord.getQuantity());
                            } else if (WmsInventoryType.ENGINE_DAMAGE.getInventoryType() == Integer.parseInt(wmsRecord.getInventoryType())) {
                                record.setWmsEngineDamage(wmsRecord.getQuantity());
                                record.setFenxiaoEngineDamage(0);
                                record.setEngineDamageDifferent(wmsRecord.getQuantity());
                            } else if (WmsInventoryType.BOX_DAMAGE.getInventoryType() == Integer.parseInt(wmsRecord.getInventoryType())) {
                                record.setWmsBoxDamage(wmsRecord.getQuantity());
                                record.setFenxiaoBoxDamage(0);
                                record.setBoxDamageDifferent(wmsRecord.getQuantity());
                            } else {
                                record.setWmsFrozenStock(wmsRecord.getQuantity());
                                record.setFenxiaoFrozenStock(0);
                                record.setFrozenStockDifferent(wmsRecord.getQuantity());
                            }


                        }else {        //对于wms仓库有的商品，分销也有的
                            record.setWarehouseId(String.valueOf(warehouse.getWarehouseId()));
                            record.setWarehouseName(warehouse.getWarehouseName());
                            logger.info("wms仓库有的商品，分销也有的,projectId={},inventoryType={},productCode={},warehouseId={}",project.getProjectId(), wmsRecord.getInventoryType(), productBasic.getProductCode(), warehouse.getWarehouseId());
                            List<ProductInventory> productInventoryList = productInventoryDao.getByProjectIdAndProductCodeAndStatus(String.valueOf(project.getProjectId()), wmsRecord.getInventoryType(), productBasic.getProductCode(), String.valueOf(warehouse.getWarehouseId()));
                            int quantity = 0;
                            if (productInventoryList != null && productInventoryList.size() > 0){
                                for (ProductInventory productInventory : productInventoryList) {
                                    quantity += productInventory.getOnHandQuantity() + productInventory.getOnSalesOrderQuantity();
                                }
                            }

                            record.setProductId(String.valueOf(productBasic.getId()));
                            record.setProductCode(productBasic.getProductCode());
                            record.setProductName(productBasic.getProductName());

                            if (WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType() == Integer.parseInt(wmsRecord.getInventoryType())) {
                                record.setWmsPerfectQuantity(wmsRecord.getQuantity());
                                record.setFenxiaoPerfectQuantity(quantity);
                                record.setPerfectDifferent(wmsRecord.getQuantity()-quantity);
                            } else if (WmsInventoryType.DEFECTIVE.getInventoryType() == Integer.parseInt(wmsRecord.getInventoryType())) {
                                record.setWmsImperfectQuantity(wmsRecord.getQuantity());
                                record.setFenxiaoImperfectQuantity(quantity);
                                record.setImPerfectDifferent(wmsRecord.getQuantity()-quantity);
                            } else if (WmsInventoryType.ENGINE_DAMAGE.getInventoryType() == Integer.parseInt(wmsRecord.getInventoryType())) {
                                record.setWmsEngineDamage(wmsRecord.getQuantity());
                                record.setFenxiaoEngineDamage(quantity);
                                record.setEngineDamageDifferent(wmsRecord.getQuantity()-quantity);
                            } else if (WmsInventoryType.BOX_DAMAGE.getInventoryType() == Integer.parseInt(wmsRecord.getInventoryType())) {
                                record.setWmsBoxDamage(wmsRecord.getQuantity());
                                record.setFenxiaoBoxDamage(quantity);
                                record.setBoxDamageDifferent(wmsRecord.getQuantity()-quantity);
                            } else {
                                record.setWmsFrozenStock(wmsRecord.getQuantity());
                                record.setFenxiaoFrozenStock(quantity);
                                record.setFrozenStockDifferent(wmsRecord.getQuantity()-quantity);
                            }
                        }
                        resultList.add(record);
                    }

                }
            }

            //存到DB
            checkInventoryDao.insertRecords(resultList);

            logger.info("[OUT][checkInventory] get checkInventory success");
        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

}
