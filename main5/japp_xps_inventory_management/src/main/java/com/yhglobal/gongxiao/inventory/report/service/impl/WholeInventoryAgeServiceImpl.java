package com.yhglobal.gongxiao.inventory.report.service.impl;


import com.yhglobal.gongxiao.inventory.dao.ProductInventoryDao;
import com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeStructure;
import com.yhglobal.gongxiao.inventory.model.ProductInventory;
import com.yhglobal.gongxiao.inventory.report.model.WholeInventoryAge;
import com.yhglobal.gongxiao.inventory.util.TablePrefixUtil;
import com.yhglobal.gongxiao.inventory.wholeinventoryage.microservice.WholeInventoryAgeServiceGrpc;
import com.yhglobal.gongxiao.inventory.wholeinventoryage.microservice.WholeInventoryAgeStructure;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
@Service
public class WholeInventoryAgeServiceImpl extends WholeInventoryAgeServiceGrpc.WholeInventoryAgeServiceImplBase {

    private Logger logger = LoggerFactory.getLogger(WholeInventoryAgeServiceImpl.class);

    @Autowired
    ProductInventoryDao productInventoryDao;

    @Override
    public void getWholeInventoryAge(WholeInventoryAgeStructure.GetWholeInventoryAgeRequest request, StreamObserver<WholeInventoryAgeStructure.GetWholeInventoryAgeRespon> responseObserver) {
        WholeInventoryAgeStructure.GetWholeInventoryAgeRespon response;
        WholeInventoryAgeStructure.GetWholeInventoryAgeRespon.Builder respBuilder = WholeInventoryAgeStructure.GetWholeInventoryAgeRespon.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        long projectId = request.getProjectId();
        String prefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);
        try {
            logger.info("#traceId={}# [IN][conditionalSelectInventoryFlow] params: projectId = {}", rpcHeader.getTraceId(),projectId);
            long totalInventoryAmount = productInventoryDao.getTotalInventoryAmount(prefix);

            Date nowDate = getDateByRequest(0);
            Date startDate60 = getDateByRequest(60);
            createRecord(respBuilder, startDate60, nowDate,1,totalInventoryAmount, prefix);  //查询库龄在60天内的批次库存

            Date startDate90 = getDateByRequest(90);
            Date endDate90 = getDateByRequest(60);
            createRecord(respBuilder, startDate90, endDate90, 2,totalInventoryAmount, prefix);  //查询库龄在60-90天内的批次库存

            Date startDate180 = getDateByRequest(180);
            Date endDate180 = getDateByRequest(90);
            createRecord(respBuilder, startDate180, endDate180, 3,totalInventoryAmount, prefix); //查询库龄在90-180天内的批次库存

            Date startDate360 = getDateByRequest(360);
            Date endDate360 = getDateByRequest(180);
            createRecord(respBuilder, startDate360, endDate360, 4,totalInventoryAmount, prefix);        //查询库龄在180-360天内的批次库存

            Date startDate361 = getDateByRequest(360);
            createRecordThanYear(respBuilder, startDate361, 5,totalInventoryAmount, prefix);        //查询库龄在360天以上的批次库存

            response = respBuilder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] get getInventoryAge success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }

    }

    private void createRecord(WholeInventoryAgeStructure.GetWholeInventoryAgeRespon.Builder respBuilder, Date startDate, Date endDate,int stage, long totalInventoryAmount, String prefix) {
        long sumOfmoney = productInventoryDao.getSumOfmoney(startDate, endDate, prefix);       //查询库龄在60天内的批次库存金额
        WholeInventoryAgeStructure.WholeInventoryAge wholeInventoryAge = WholeInventoryAgeStructure.WholeInventoryAge.newBuilder()
                .setStage(stage)
                .setSumOfmoney(sumOfmoney)
                .setProportion(sumOfmoney / totalInventoryAmount)
                .build();

        respBuilder.addList(wholeInventoryAge);
    }

    private void createRecordThanYear(WholeInventoryAgeStructure.GetWholeInventoryAgeRespon.Builder respBuilder, Date startDate, int stage,long totalInventoryAmount, String prefix) {
        long sumOfmoney = productInventoryDao.getSumOfmoneyThanYear(startDate, prefix);       //查询库龄大于360天的批次库存金额
        WholeInventoryAgeStructure.WholeInventoryAge wholeInventoryAge = WholeInventoryAgeStructure.WholeInventoryAge.newBuilder()
                .setStage(stage)
                .setSumOfmoney(sumOfmoney)
                .setProportion(sumOfmoney / totalInventoryAmount)
                .build();

        respBuilder.addList(wholeInventoryAge);
    }

    public static Date getDateByRequest(int i) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now();
        localDate = localDate.minusDays(i);
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }
}
