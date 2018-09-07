package com.yhglobal.gongxiao.inventory.inventorycheck;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.inventory.dao.CheckInventoryDao;
import com.yhglobal.gongxiao.inventory.inventorycheck.microservice.InventoryCheckServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventorycheck.microservice.InventorycheckStructure;
import com.yhglobal.gongxiao.inventory.model.bo.InventoryCheckModel;
import com.yhglobal.gongxiao.inventory.util.TablePrefixUtil;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class InventoryCheckServiceImpl extends InventoryCheckServiceGrpc.InventoryCheckServiceImplBase {

    private static Logger logger = LoggerFactory.getLogger(InventoryCheckServiceImpl.class);

    @Autowired
    CheckInventoryDao checkInventoryDao;

    @Override
    public void getInventoryCheck(InventorycheckStructure.GetInventoryCheckRequest request, StreamObserver<InventorycheckStructure.InventoryCheckPageInfo> responseObserver) {

        InventorycheckStructure.InventoryCheckPageInfo response;
        InventorycheckStructure.InventoryCheckPageInfo.Builder respBuilder = InventorycheckStructure.InventoryCheckPageInfo.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String projectId = request.getProjectId();
        String warehouseId = request.getWarehouseId();
        String productCode = request.getProductCode();
        String productName = request.getProductName();
        int pageNumber = request.getPageNumber();
        int pageSize = request.getPageSize();
        try {
            logger.info("#traceId={}# [IN][getInventoryCheck] params: projectId={}, warehouseId={}, productCode={}, productName={}, pageNumber={}, pageSize={}", rpcHeader.getTraceId(), projectId, warehouseId, productCode, productName, pageNumber, pageSize);
            String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
            //启动分页
            PageHelper.startPage(pageNumber, pageSize);
            //查询列表
            List<InventoryCheckModel> resultList = checkInventoryDao.selectRecords(projectId, warehouseId, productCode, productName, projectPrefix);
            PageInfo<InventoryCheckModel> inventoryLedgerPageInfo = new PageInfo<>(resultList);
            long total = inventoryLedgerPageInfo.getTotal();

            for (InventoryCheckModel record : resultList) {
                InventorycheckStructure.InventoryCheckModel.Builder innerModel = InventorycheckStructure.InventoryCheckModel.newBuilder()
                        .setDateTime(record.getDateTime().getTime())
                        .setProjectId(record.getProjectId())
                        .setProjectName(record.getProjectName())
                        .setWarehouseId(record.getWarehouseId())
                        .setWarehouseName(record.getWarehouseName())
                        .setProductId(record.getProductId())
                        .setProductCode(record.getProductCode())
                        .setProductName(record.getProductName())
                        .setFenxiaoPerfectQuantity(record.getFenxiaoPerfectQuantity())
                        .setWmsPerfectQuantity(record.getWmsPerfectQuantity())
                        .setPerfectDifferent(record.getPerfectDifferent())
                        .setFenxiaoImperfectQuantity(record.getFenxiaoImperfectQuantity())
                        .setWmsImperfectQuantity(record.getWmsImperfectQuantity())
                        .setImPerfectDifferent(record.getImPerfectDifferent())
                        .setFenxiaoEngineDamage(record.getFenxiaoEngineDamage())
                        .setWmsEngineDamage(record.getWmsEngineDamage())
                        .setEngineDamageDifferent(record.getEngineDamageDifferent())
                        .setFenxiaoBoxDamage(record.getFenxiaoBoxDamage())
                        .setWmsBoxDamage(record.getWmsBoxDamage())
                        .setBoxDamageDifferent(record.getBoxDamageDifferent())
                        .setFenxiaoFrozenStock(record.getFenxiaoFrozenStock())
                        .setWmsFrozenStock(record.getWmsFrozenStock())
                        .setFrozenStockDifferent(record.getFrozenStockDifferent());

                respBuilder.addList(innerModel);
            }

            respBuilder.setPageNum(pageNumber);
            respBuilder.setPageSize(pageSize);
            respBuilder.setTotal(total);
            response = respBuilder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] get getInventoryCheck success: result.size():{}", rpcHeader.getTraceId(), resultList.size());
            }catch (Exception e){
                logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
                throw e;
        }

    }
}
