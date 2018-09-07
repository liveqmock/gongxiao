package com.yhglobal.gongxiao.inventory.inventoryage;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.inventory.dao.ProductInventoryDao;
import com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeStructure;
import com.yhglobal.gongxiao.inventory.model.ProductInventory;
import com.yhglobal.gongxiao.inventory.util.TablePrefixUtil;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@Service
public class InventoryAgeServiceImpl extends InventoryAgeServiceGrpc.InventoryAgeServiceImplBase {

    private static Logger logger = LoggerFactory.getLogger(InventoryAgeServiceImpl.class);

    @Autowired
    ProductInventoryDao productInventoryDao;

    private static final int OPEN = 1;

    private static final int CLOSE = 2;

    @Override
    public void getInventoryAge(InventoryAgeStructure.GetInventoryAgeRequest request, StreamObserver<InventoryAgeStructure.InventoryAgePageInfo> responseObserver) {
        InventoryAgeStructure.InventoryAgePageInfo response;
        InventoryAgeStructure.InventoryAgePageInfo.Builder respBuilder = InventoryAgeStructure.InventoryAgePageInfo.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String projectId = request.getProjectId();
        String batchNo = request.getBatchNo();
        String inboundOrderNo = request.getInboundOrderNo();
        String outboundOrderNo = request.getOutboundOrderNo();
        String startDate = request.getStartDate();
        String endDate = request.getEndDate();
        int pageNumber = request.getPageNumber();
        int pageSize = request.getPageSize();

        try {
            logger.info("#traceId={}# [IN][getInventoryAge]: projectId={}, batchNo={},inboundOrderNo={},outboundOrderNo={},startDate={},endDate={}, pageNumber={}, pageSize={}", rpcHeader.getTraceId(), projectId, batchNo, inboundOrderNo, outboundOrderNo, startDate, endDate, pageNumber, pageSize);
            String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
            //启动分页
            PageHelper.startPage(pageNumber, pageSize);
            //查询列表
            List<ProductInventory> productInventoryList = productInventoryDao.getAllBatchRecord(projectId, batchNo, inboundOrderNo, outboundOrderNo, startDate, endDate, projectPrefix);
            PageInfo<ProductInventory> productInventoryPageInfo = new PageInfo<>(productInventoryList);
            long total = productInventoryPageInfo.getTotal();

            Date dateTime = new Date();

            //遍历
            for (ProductInventory record : productInventoryList) {

                InventoryAgeStructure.InventoryAge.Builder innerModel = InventoryAgeStructure.InventoryAge.newBuilder()
                        .setStatus(((record.getOnHandQuantity())) > 0 ? OPEN : CLOSE)               // 默认为1:正常  2:关闭
                        .setBatchNo(record.getBatchNo())
                        .setProductCode(record.getProductCode())
                        .setProductName(record.getProductName())
                        .setWarehouseName(record.getWarehouseName())
                        .setInboundQuantity(record.getOnHandQuantity())
                        .setPurchaseOrderNo(record.getPurchaseOrderNo())
                        .setInboundOrderNo(record.getInboundOrderNo())
                        .setCreateTime(record.getCreateTime().getTime())
                        .setReceiveFinishTime(dateTime.getTime())            //入库完成时间没有
                        .setPurchaseGuidPrice((long) record.getPurchaseGuidPrice())
                        .setPurchasePrice((long) record.getPurchasePrice())
                        .setCostPrice((long) record.getCostPrice())
                        .setBatchInboundAmount((long) (record.getOnHandQuantity() * record.getCostPrice()))
                        .setInventoryAge(getDays(dateTime.getTime(), record.getCreateTime().getTime()))
                        .setRestAmount(record.getOnHandQuantity())
                        .setSalesOrderNo("")
                        .setOutboundOrderNo("")
                        .setSalesGuidPrice(0)
                        .setOutboundPrice(0)
                        .setOutboundQuantity(0)
                        .setBatchOutboundAmount(0)
                        .setCustomerUseCoupon(0)
                        .setCustomerUsePrepaid(0)
                        .setOutboundDate(0)
                        .setSightTime(0);
                respBuilder.addList(innerModel);
            }

            respBuilder.setPageNum(pageNumber);
            respBuilder.setPageSize(pageSize);
            respBuilder.setTotal(total);
            response = respBuilder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] get getInventoryAge success", rpcHeader.getTraceId());

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    private int getDays(long nowDate, long createTime) {
        int days = (int) (nowDate - createTime) / (1000 * 3600 * 24);
        return days+1;
    }
}
