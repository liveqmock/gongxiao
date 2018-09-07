package com.yhglobal.gongxiao.foundation.supplier.impl;

import com.yhglobal.gongxiao.foundation.project.dao.FoundationProjectDao;
import com.yhglobal.gongxiao.foundation.supplier.dao.FoundationSupplierDao;
import com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierServiceGrpc;
import com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure;
import com.yhglobal.gongxiao.foundation.supplier.model.FoundationSupplier;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoundationSupplierImpl extends SupplierServiceGrpc.SupplierServiceImplBase {

    private static Logger logger = LoggerFactory.getLogger(FoundationSupplierImpl.class);

    @Autowired
    private FoundationSupplierDao supplierDao;

    @Autowired
    private FoundationProjectDao projectDao;

    /**
     * 通过供应商编码获取供应商信息
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void getSupplierByCode(SupplierStructure.GetSupplierByCodeReq request,
                                  StreamObserver<SupplierStructure.GetSupplierByCodeResp> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String supplierCode = request.getSupplierCode();

        SupplierStructure.GetSupplierByCodeResp response; //保存返回的值
        SupplierStructure.GetSupplierByCodeResp.Builder respBuilder = SupplierStructure.GetSupplierByCodeResp.newBuilder(); //每个proto对象都需要从builder构建出来
        logger.info("#traceId={}# [IN][getSupplierByCode] ", rpcHeader.getTraceId());
        try {
            FoundationSupplier supplier = supplierDao.getSupplierByCode(supplierCode);
            if (supplier==null){
                logger.info("[getSupplierByCode] 没有拿到供应商数据 #traceId={}, supplierCode={}",rpcHeader.getTraceId(),supplierCode);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            SupplierStructure.Supplier supplier1 = SupplierStructure.Supplier.newBuilder()
                    .setSupplierId(supplier.getSupplierId())
                    .setSupplierName(GrpcCommonUtil.getProtoParam(supplier.getSupplierName()))
                    .setSupplierCode(GrpcCommonUtil.getProtoParam(supplier.getSupplierCode()))
                    .setEasSupplierCode(GrpcCommonUtil.getProtoParam(supplier.getEasSupplierCode()))
                    .setEasSupplierName(GrpcCommonUtil.getProtoParam(supplier.getEasSupplierName()))
                    .setRecordStatus(supplier.getRecordStatus())
                    .setAddress(supplier.getAddress())
                    .setEmail(GrpcCommonUtil.getProtoParam(supplier.getEmail()))
                    .setContactName(GrpcCommonUtil.getProtoParam(supplier.getContactName()))
                    .setCountryCode(supplier.getCountryCode())
                    .setPhoneNumber(GrpcCommonUtil.getProtoParam(supplier.getPhoneNumber()))
                    .setCurrentProjectInfo(GrpcCommonUtil.getProtoParam(supplier.getCurrentProjectInfo()))
                    .setHistoryProjectInfo(GrpcCommonUtil.getProtoParam(supplier.getHistoryProjectInfo()))
                    .setCreatedById(GrpcCommonUtil.getProtoParam(supplier.getCreatedById()))
                    .setCreatedByName(supplier.getCreatedByName())
                    .setCreateTime(DateUtil.getTime(supplier.getCreateTime()))
                    .setLastUpdateTime(DateUtil.getTime(supplier.getLastUpdateTime()))
                    .setTracelog(GrpcCommonUtil.getProtoParam(supplier.getTracelog()))
                    .build();
            respBuilder.setSupplier(supplier1);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT][getSupplierByCode] ", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    /**
     * 获取供应商列表
     * <p>
     * rpcHeader rpc调用的header
     *
     * @return
     */
    @Override
    public void selectAll(SupplierStructure.SelectAllReq request,
                          StreamObserver<SupplierStructure.SelectAllResp> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();

        SupplierStructure.SelectAllResp response; //保存返回的值
        SupplierStructure.SelectAllResp.Builder respBuilder = SupplierStructure.SelectAllResp.newBuilder(); //每个proto对象都需要从builder构建出来

        logger.info("#traceId={}# [IN][selectAll] ", rpcHeader.getTraceId());
        try {
            List<FoundationSupplier> suppliers = supplierDao.selectAll();
            if (suppliers.size()==0){
                logger.info("[selectAll] 没有拿到供应商数据 #traceId={}",rpcHeader.getTraceId());
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            for (FoundationSupplier supplier : suppliers) {
                SupplierStructure.Supplier supplier1 = SupplierStructure.Supplier.newBuilder()
                        .setId(supplier.getSupplierId())
                        .setSupplierName(GrpcCommonUtil.getProtoParam(supplier.getSupplierName()))
                        .setSupplierCode(GrpcCommonUtil.getProtoParam(supplier.getSupplierCode()))
                        .setEasSupplierCode(GrpcCommonUtil.getProtoParam(supplier.getEasSupplierCode()))
                        .setEasSupplierName(GrpcCommonUtil.getProtoParam(supplier.getEasSupplierName()))
                        .setRecordStatus(supplier.getRecordStatus())
                        .setAddress(supplier.getAddress())
                        .setEmail(GrpcCommonUtil.getProtoParam(supplier.getEmail()))
                        .setContactName(GrpcCommonUtil.getProtoParam(supplier.getContactName()))
                        .setCountryCode(supplier.getCountryCode())
                        .setPhoneNumber(GrpcCommonUtil.getProtoParam(supplier.getPhoneNumber()))
                        .setCurrentProjectInfo(GrpcCommonUtil.getProtoParam(supplier.getCurrentProjectInfo()))
                        .setHistoryProjectInfo(GrpcCommonUtil.getProtoParam(supplier.getHistoryProjectInfo()))
                        .setCreatedById(GrpcCommonUtil.getProtoParam(supplier.getCreatedById()))
                        .setCreatedByName(supplier.getCreatedByName())
                        .setCreateTime(DateUtil.getTime(supplier.getCreateTime()))
                        .setLastUpdateTime(DateUtil.getTime(supplier.getLastUpdateTime()))
                        .setTracelog(GrpcCommonUtil.getProtoParam(supplier.getTracelog()))
                        .build();
                respBuilder.addSupplierList(supplier1);
            }
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    public void getSupplierById(SupplierStructure.GetSupplierByIdReq request,
                          StreamObserver<SupplierStructure.GetSupplierByIdResp> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String supplierId = request.getSupplierId();

        SupplierStructure.GetSupplierByIdResp.Builder respBuilder = SupplierStructure.GetSupplierByIdResp.newBuilder(); //每个proto对象都需要从builder构建出来

        try {
            FoundationSupplier supplier = supplierDao.getBySupplierId(Long.parseLong(supplierId));
            if (supplier==null){
                logger.info("[selectAll] 没有拿到供应商数据 #traceId={}",rpcHeader.getTraceId());
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            SupplierStructure.Supplier supplier1 = SupplierStructure.Supplier.newBuilder()
                    .setId(supplier.getSupplierId())
                    .setSupplierName(GrpcCommonUtil.getProtoParam(supplier.getSupplierName()))
                    .setSupplierCode(GrpcCommonUtil.getProtoParam(supplier.getSupplierCode()))
                    .setEasSupplierCode(GrpcCommonUtil.getProtoParam(supplier.getEasSupplierCode()))
                    .setEasSupplierName(GrpcCommonUtil.getProtoParam(supplier.getEasSupplierName()))
                    .setRecordStatus(supplier.getRecordStatus())
                    .setAddress(supplier.getAddress())
                    .setEmail(GrpcCommonUtil.getProtoParam(supplier.getEmail()))
                    .setContactName(GrpcCommonUtil.getProtoParam(supplier.getContactName()))
                    .setCountryCode(supplier.getCountryCode())
                    .setPhoneNumber(GrpcCommonUtil.getProtoParam(supplier.getPhoneNumber()))
                    .setCurrentProjectInfo(GrpcCommonUtil.getProtoParam(supplier.getCurrentProjectInfo()))
                    .setHistoryProjectInfo(GrpcCommonUtil.getProtoParam(supplier.getHistoryProjectInfo()))
                    .setCreatedById(GrpcCommonUtil.getProtoParam(supplier.getCreatedById()))
                    .setCreatedByName(supplier.getCreatedByName())
                    .setCreateTime(DateUtil.getTime(supplier.getCreateTime()))
                    .setLastUpdateTime(DateUtil.getTime(supplier.getLastUpdateTime()))
                    .setTracelog(GrpcCommonUtil.getProtoParam(supplier.getTracelog()))
                    .build();
            respBuilder.setSupplier(supplier1);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();

        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }
}
