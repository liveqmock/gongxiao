package com.yhglobal.gongxiao.inventory.report.service.impl;

import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.inventory.dao.ProductInventoryDao;
import com.yhglobal.gongxiao.inventory.productinventory180age.microservice.ProductInventory180AgeServiceGrpc;
import com.yhglobal.gongxiao.inventory.productinventory180age.microservice.ProductInventory180AgeStructure;
import com.yhglobal.gongxiao.inventory.util.TablePrefixUtil;
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
import java.util.List;

@Service
public class ProductInventory180AgeServiceImpl extends ProductInventory180AgeServiceGrpc.ProductInventory180AgeServiceImplBase {

    private static Logger logger = LoggerFactory.getLogger(ProductInventory180AgeServiceImpl.class);

    @Autowired
    ProductInventoryDao productInventoryDao;

    @Override
    public void getProductInventory180Age(ProductInventory180AgeStructure.GetProductInventory180AgeRequest request, StreamObserver<ProductInventory180AgeStructure.GetProductInventory180AgeRespon> responseObserver) {
        ProductInventory180AgeStructure.GetProductInventory180AgeRespon response;
        ProductInventory180AgeStructure.GetProductInventory180AgeRespon.Builder respBuilder = ProductInventory180AgeStructure.GetProductInventory180AgeRespon.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        long projectId = request.getProjectId();
        String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);

        try {
            logger.info("#traceId={}# [IN][getProductInventory180Age] params: projectId = {}", rpcHeader.getTraceId(), projectId);

            //调用基础模块的商品的grpc查询商品信息
            ProductServiceGrpc.ProductServiceBlockingStub productService = GlobalRpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
            ProductStructure.SelectAllProductReq selectAllProductReq = ProductStructure.SelectAllProductReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(projectId).build();
            ProductStructure.SelectAllProductResp productResp = productService.selectAllProduct(selectAllProductReq);
            List<ProductStructure.ProductBusiness> productBusinessList = productResp.getProductBusinessList();

            Date day180ago = getDateByRequest(180);
            for (ProductStructure.ProductBusiness record : productBusinessList) {
                //商品型号的总金额
                long totalProductInventoryAmount = productInventoryDao.getTotalProductInventoryAmount(record.getProductModel(), projectPrefix);
                //库龄大于180天的商品型号占用该型号的金额
                long sumOfmoney = productInventoryDao.getProductInventoryAmount(record.getProductModel(), day180ago, projectPrefix);

                if (totalProductInventoryAmount == 0 || sumOfmoney == 0){   //如果商品没有库存或者商品没有大于180天库龄的库存，跳过
                    continue;
                }
                ProductInventory180AgeStructure.ProductInventory180Age productInventory180Age = ProductInventory180AgeStructure.ProductInventory180Age.newBuilder()
                        .setProductCode(record.getProductName())
                        .setSumOfmoney(sumOfmoney)
                        .setProportion((1.0*sumOfmoney / totalProductInventoryAmount))
                        .build();

                respBuilder.addList(productInventory180Age);
            }

            response = respBuilder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] get getProductInventory180Age success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }

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

