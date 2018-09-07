package com.yhglobal.gongxiao.inventory.inventorycheck;

import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseServiceGrpc;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.inventory.config.WarehouseConfig;
import com.yhglobal.gongxiao.inventory.dao.CheckInventoryDao;
import com.yhglobal.gongxiao.inventory.dao.ProductInventoryDao;
import com.yhglobal.gongxiao.inventory.model.ProductInventory;
import com.yhglobal.gongxiao.inventory.model.bo.InventoryCheckModel;
import com.yhglobal.gongxiao.inventory.model.inventory.InventoryQueryDetailDto;
import com.yhglobal.gongxiao.inventory.model.inventory.InventoryResultDetailDto;
import com.yhglobal.gongxiao.inventory.model.inventory.WmsInventoryReq;
import com.yhglobal.gongxiao.inventory.model.inventory.WmsInventoryResp;
import com.yhglobal.gongxiao.inventory.util.TablePrefixUtil;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.type.WmsInventoryType;
import com.yhglobal.gongxiao.type.WmsSourceChannel;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
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
    WarehouseConfig warehouseConfig;

    @Autowired
    ProductInventoryDao productInventoryDao;

    @Autowired
    CheckInventoryDao checkInventoryDao;

    @Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(cron = "0 53 9 ? * *")
    public void checkInventory() {
        logger.info("[IN][checkInventory] params: start to check WMS and FenXiao inventory");
        Date date = new Date();
        String traceId = String.valueOf(date.getTime());
        String uid = "fenxiao";
        String userName = "fenxiao";
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId,uid,userName);

        try {

            //调用基础模块的项目的grpc查询项目信息
            ProjectServiceGrpc.ProjectServiceBlockingStub projectService = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.SelectProjectListReq selectProjectListReq = ProjectStructure.SelectProjectListReq.newBuilder().setRpcHeader(rpcHeader).build();
            ProjectStructure.SelectProjectListResp rpcResponse = projectService.selectProjectList(selectProjectListReq);
            List<ProjectStructure.Project> projectListList = rpcResponse.getProjectListList();

            //查询仓库列表
            //调用基础模块的仓库grpc服务查仓库编码
            WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseService = GlobalRpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
            WarehouseStructure.SelectAllWarehouseReq selectAllWarehouseReq = WarehouseStructure.SelectAllWarehouseReq.newBuilder().setRpcHeader(rpcHeader).build();
            WarehouseStructure.SelectAllWarehouseResp warehouseRpcResp = warehouseService.selectAllWarehouse(selectAllWarehouseReq);
            List<WarehouseStructure.Warehouse> warehouseListList = warehouseRpcResp.getWarehouseListList();

            String wmsIp = warehouseConfig.getWmsUrl();

            //调用基础模块的SourceChannel服务
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder().setRpcHeader(rpcHeader).setXpsChannelId(WmsSourceChannel.CHANNEL_SHAVER.getChannelId()).build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();

            String ownerCode = sourceChannel.getWmsCustCode();

            for (ProjectStructure.Project project : projectListList) {    //遍历项目
                List<InventoryCheckModel> resultList = new ArrayList<>();
                String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(project.getProjectId());    //获取项目前缀

                for (WarehouseStructure.Warehouse warehouse : warehouseListList) {   //遍历项目下的仓库
//                    WarehouseStructure.Warehouse warehouse = WarehouseStructure.Warehouse.newBuilder().setWarehouseId(1).setWarehouseName("武汉普通仓").setWmsWarehouseCode("18").build();
                    List<InventoryQueryDetailDto> inventoryList = new ArrayList<>();
                    InventoryQueryDetailDto inventoryQueryDetailDto = new InventoryQueryDetailDto(warehouse.getWmsWarehouseCode(), ownerCode, project.getEasProjectCode(), null);
                    inventoryList.add(inventoryQueryDetailDto);
                    WmsInventoryReq wmsInventoryReq = new WmsInventoryReq();
                    wmsInventoryReq.setInventoryList(inventoryList);
                    int maxRetriesTime = 5;
                    WmsInventoryResp resp = new WmsInventoryResp();

                    while (maxRetriesTime-- > 0) {  //调用查询wms库存的接口，尝试10次

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

                        //调用基础模块的商品的grpc查询商品信息
                        ProductServiceGrpc.ProductServiceBlockingStub productService = GlobalRpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
                        ProductStructure.GetByWmsProductCodeReq getByWmsProductCodeReq = ProductStructure.GetByWmsProductCodeReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(project.getProjectId()).setProductWmsCode(wmsRecord.getItemCode()).build();
                        ProductStructure.GetByWmsProductCodeResp response = productService.getByWmsProductCode(getByWmsProductCodeReq);
                        ProductStructure.ProductBusiness productBusiness = response.getProductBusiness();

                        if (productBusiness == null){    //对于wms仓库有的商品，分销没有的
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
                            logger.info("wms仓库有的商品，分销也有的,projectId={},inventoryType={},productCode={},warehouseId={}",project.getProjectId(), wmsRecord.getInventoryType(), productBusiness.getProductModel(), warehouse.getWarehouseId());
                            List<ProductInventory> productInventoryList = productInventoryDao.getByProjectIdAndProductCodeAndStatus(String.valueOf(project.getProjectId()), wmsRecord.getInventoryType(), productBusiness.getProductModel(), String.valueOf(warehouse.getWarehouseId()),projectPrefix);
                            int quantity = 0;
                            if (productInventoryList.size() > 0){
                                for (ProductInventory productInventory : productInventoryList) {
                                    quantity += productInventory.getOnHandQuantity() + productInventory.getOnSalesOrderQuantity();
                                }
                            }

                            record.setProductId(String.valueOf(productBusiness.getProductBasicId()));
                            record.setProductCode(wmsRecord.getItemCode());
//                            record.setProductCode(productBusiness.getProductModel());
                            record.setProductName(productBusiness.getProductName());

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
                        //存到DB中

                    }

                }
                checkInventoryDao.insertRecords(resultList,projectPrefix);
            }



            logger.info("[OUT][checkInventory] get checkInventory success");
        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

}
