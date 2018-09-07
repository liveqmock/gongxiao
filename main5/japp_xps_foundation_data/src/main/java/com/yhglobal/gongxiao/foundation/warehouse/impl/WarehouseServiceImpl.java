package com.yhglobal.gongxiao.foundation.warehouse.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.foundation.project.model.FoundationProject;
import com.yhglobal.gongxiao.foundation.warehouse.dao.FoundationWarehouseDao;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseServiceGrpc;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure;
import com.yhglobal.gongxiao.foundation.warehouse.model.FoundationWarehouse;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.status.FoundationNormalStatus;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WarehouseServiceImpl extends WarehouseServiceGrpc.WarehouseServiceImplBase {

    private static Logger logger = LoggerFactory.getLogger(WarehouseServiceImpl.class);

    @Autowired
    private FoundationWarehouseDao warehouseDao;

    /**
     * 通过仓库ID获取仓库信息
     * <p>
     * rpcHeader rpc调用的header
     * warehouseId
     *
     * @return
     */
    @Override
    public void getWarehouseById(WarehouseStructure.GetWarehouseByIdReq request,
                                 StreamObserver<WarehouseStructure.GetWarehouseByIdResp> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String warehouseId = request.getWarehouseId();
        WarehouseStructure.GetWarehouseByIdResp.Builder respBuilder = WarehouseStructure.GetWarehouseByIdResp.newBuilder(); //每个proto对象都需要从builder构建出来

        logger.info("#traceId={}# [IN][getWarehouseById] params: warehouseId={}", rpcHeader.getTraceId(), warehouseId);
        try {
            FoundationWarehouse warehouse = warehouseDao.getWarehouseById(Long.parseLong(warehouseId));
            if (warehouse == null) {
                logger.info("[getWarehouseById] 没有拿到仓库数据 #traceId={}", rpcHeader.getTraceId());
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            WarehouseStructure.Warehouse warehouse1 = WarehouseStructure.Warehouse.newBuilder()
                    .setWarehouseId(warehouse.getWarehouseId())
                    .setWarehouseCode(warehouse.getWarehouseCode())
                    .setWarehouseName(warehouse.getWarehouseName())
                    .setEasWarehouseCode(warehouse.getEasWarehouseCode())
                    .setEasWarehouseName(warehouse.getEasWarehouseName())
                    .setWmsWarehouseCode(warehouse.getWmsWarehouseCode())
                    .setWmsWarehouseName(warehouse.getWmsWarehouseName())
                    .setRecordStatus(warehouse.getRecordStatus())
                    .setLocationNumber(GrpcCommonUtil.getProtoParam(warehouse.getLocationNumber()))
                    .setCountryName(GrpcCommonUtil.getProtoParam(warehouse.getCountryName()))
                    .setCityName(GrpcCommonUtil.getProtoParam(warehouse.getCityName()))
                    .setProvinceId(GrpcCommonUtil.getProtoParam(warehouse.getProvinceId()))
                    .setProvinceName(GrpcCommonUtil.getProtoParam(warehouse.getProvinceName()))
                    .setStreetAddress(GrpcCommonUtil.getProtoParam(warehouse.getStreetAddress()))
                    .setShortAddress(GrpcCommonUtil.getProtoParam(warehouse.getShortAddress()))
                    .setGeneralContactName(GrpcCommonUtil.getProtoParam(warehouse.getGeneralContactName()))
                    .setGeneralMobile(GrpcCommonUtil.getProtoParam(warehouse.getGeneralMobile()))
                    .setCreatedById(GrpcCommonUtil.getProtoParam(warehouse.getCreatedById()))
                    .setCreatedByName(GrpcCommonUtil.getProtoParam(warehouse.getCreatedByName()))
                    .setCreateTime(DateUtil.getTime(warehouse.getCreateTime()))
                    .setTracelog(GrpcCommonUtil.getProtoParam(warehouse.getTracelog()))
                    .build();
            logger.info("#traceId={}# [OUT][selectAllWarehouse] success ", rpcHeader.getTraceId());

            respBuilder.setWarehouse(warehouse1);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 获取所有仓库信息
     * <p>
     * rpcHeader rpc调用的header
     *
     * @return
     */
    @Override
    public void selectAllWarehouse(WarehouseStructure.SelectAllWarehouseReq request,
                                   StreamObserver<WarehouseStructure.SelectAllWarehouseResp> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();

        WarehouseStructure.SelectAllWarehouseResp.Builder respBuilder = WarehouseStructure.SelectAllWarehouseResp.newBuilder(); //每个proto对象都需要从builder构建出来

        logger.info("#traceId={}# [IN][insertProject] params: ", rpcHeader.getTraceId());
        try {
            List<FoundationWarehouse> warehouses = warehouseDao.selectAllWarehouseList();
            if (warehouses.size() == 0) {
                logger.info("[selectAllWarehouse] 没有拿到仓库数据 #traceId={}", rpcHeader.getTraceId());
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            for (FoundationWarehouse warehouse : warehouses) {
                WarehouseStructure.Warehouse warehouse1 = WarehouseStructure.Warehouse.newBuilder()
                        .setWarehouseId(warehouse.getWarehouseId())
                        .setWarehouseCode(warehouse.getWarehouseCode())
                        .setWarehouseName(warehouse.getWarehouseName())
                        .setEasWarehouseCode(warehouse.getEasWarehouseCode())
                        .setEasWarehouseName(warehouse.getEasWarehouseName())
                        .setWmsWarehouseCode(warehouse.getWmsWarehouseCode())
                        .setWmsWarehouseName(warehouse.getWarehouseName())
                        .setRecordStatus(warehouse.getRecordStatus())
                        .setLocationNumber(GrpcCommonUtil.getProtoParam(warehouse.getLocationNumber()))
                        .setCountryName(GrpcCommonUtil.getProtoParam(warehouse.getCountryName()))
                        .setCityName(GrpcCommonUtil.getProtoParam(warehouse.getCountryName()))
                        .setProvinceId(GrpcCommonUtil.getProtoParam(warehouse.getProvinceId()))
                        .setProvinceName(GrpcCommonUtil.getProtoParam(warehouse.getProvinceName()))
                        .setStreetAddress(GrpcCommonUtil.getProtoParam(warehouse.getStreetAddress()))
                        .setShortAddress(GrpcCommonUtil.getProtoParam(warehouse.getShortAddress()))
                        .setGeneralContactName(GrpcCommonUtil.getProtoParam(warehouse.getGeneralContactName()))
                        .setGeneralMobile(GrpcCommonUtil.getProtoParam(warehouse.getGeneralMobile()))
                        .setCreatedById(GrpcCommonUtil.getProtoParam(warehouse.getCreatedById()))
                        .setCreatedByName(GrpcCommonUtil.getProtoParam(warehouse.getCreatedByName()))
                        .setCreateTime(DateUtil.getTime(warehouse.getCreateTime()))
                        .setTracelog(GrpcCommonUtil.getProtoParam(warehouse.getTracelog()))
                        .build();
                respBuilder.addWarehouseList(warehouse1);
            }
            logger.info("#traceId={}# [OUT][selectAllWarehouse] success foundationWarehouses.size", rpcHeader.getTraceId(), warehouses.size());
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    public void selectWarehouseCondition(WarehouseStructure.SelectWarehouseConditionReq request,
                                         StreamObserver<WarehouseStructure.SelectWarehouseConditionResp> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        WarehouseStructure.SelectWarehouseConditionResp.Builder respBuilder = WarehouseStructure.SelectWarehouseConditionResp.newBuilder(); //每个proto对象都需要从builder构建出来
        long warehouseId = request.getWarehouseId();
        String warehouseName = request.getWarehouseName();
        long projectId = 0;
        int pageNumber = request.getPageNumber();
        int pageSize = request.getPageSize();
        logger.info("#traceId={}# [IN][SelectWarehouseCondition] params: ", rpcHeader.getTraceId());
        try {
            PageHelper.startPage(pageNumber, pageSize);
            List<FoundationWarehouse> foundationWarehouses = warehouseDao.selectWarehouseListByCondition(projectId, warehouseId, warehouseName);
            if (foundationWarehouses.size() == 0) {
                logger.info("[SelectWarehouseCondition] 没有拿到仓库数据 #traceId={}", rpcHeader.getTraceId());
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            PageInfo<FoundationWarehouse> foundationWarehousePageInfo = new PageInfo<>(foundationWarehouses);
            for (FoundationWarehouse warehouse : foundationWarehouses) {
                WarehouseStructure.Warehouse warehouse1 = WarehouseStructure.Warehouse.newBuilder()
                        .setWarehouseId(warehouse.getWarehouseId())
                        .setWarehouseCode(warehouse.getWarehouseCode())
                        .setWarehouseName(warehouse.getWarehouseName())
                        .setEasWarehouseCode(warehouse.getEasWarehouseCode())
                        .setEasWarehouseName(warehouse.getEasWarehouseName())
                        .setWmsWarehouseCode(warehouse.getWmsWarehouseCode())
                        .setWmsWarehouseName(warehouse.getWarehouseName())
                        .setRecordStatus(warehouse.getRecordStatus())
                        .setLocationNumber(GrpcCommonUtil.getProtoParam(warehouse.getLocationNumber()))
                        .setCountryName(GrpcCommonUtil.getProtoParam(warehouse.getCountryName()))
                        .setCityName(GrpcCommonUtil.getProtoParam(warehouse.getCountryName()))
                        .setProvinceId(GrpcCommonUtil.getProtoParam(warehouse.getProvinceId()))
                        .setProvinceName(GrpcCommonUtil.getProtoParam(warehouse.getProvinceName()))
                        .setStreetAddress(GrpcCommonUtil.getProtoParam(warehouse.getStreetAddress()))
                        .setShortAddress(GrpcCommonUtil.getProtoParam(warehouse.getShortAddress()))
                        .setGeneralContactName(GrpcCommonUtil.getProtoParam(warehouse.getGeneralContactName()))
                        .setGeneralMobile(GrpcCommonUtil.getProtoParam(warehouse.getGeneralMobile()))
                        .setCreatedById(GrpcCommonUtil.getProtoParam(warehouse.getCreatedById()))
                        .setCreatedByName(GrpcCommonUtil.getProtoParam(warehouse.getCreatedByName()))
                        .setCreateTime(DateUtil.getTime(warehouse.getCreateTime()))
                        .setTracelog(GrpcCommonUtil.getProtoParam(warehouse.getTracelog()))
                        .build();
                respBuilder.addWarehouse(warehouse1);
            }
            logger.info("#traceId={}# [OUT][SelectWarehouseCondition] success foundationWarehouses.size", rpcHeader.getTraceId(), foundationWarehouses.size());
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    //TODO: 暂时没有用到 通过页面添加的时候才有使用场景
    public void insertWarehouseInfo(WarehouseStructure.InsertWarehouseInfoReq request,
                                    StreamObserver<WarehouseStructure.InsertWarehouseInfoResp> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        WarehouseStructure.InsertWarehouseInfoResp.Builder respBuilder = WarehouseStructure.InsertWarehouseInfoResp.newBuilder(); //每个proto对象都需要从builder构建出来
        WarehouseStructure.Warehouse warehouse = request.getWarehouse();
        logger.info("#traceId={}# [IN][insertWarehouseInfo] params: ", rpcHeader.getTraceId());
        try {
            FoundationWarehouse foundationWarehouse = new FoundationWarehouse();
            foundationWarehouse.setWarehouseId(warehouse.getWarehouseId());
            foundationWarehouse.setWarehouseCode(warehouse.getWarehouseCode());
            foundationWarehouse.setWarehouseName(warehouse.getWarehouseName());
            foundationWarehouse.setEasWarehouseCode(warehouse.getEasWarehouseCode());
            foundationWarehouse.setEasWarehouseName(warehouse.getEasWarehouseName());
            foundationWarehouse.setWmsWarehouseCode(warehouse.getWmsWarehouseCode());
            foundationWarehouse.setWmsWarehouseName(warehouse.getWmsWarehouseName());
            foundationWarehouse.setSendNotitionToWarehouse((byte)warehouse.getSendNotitionToWarehouse());
            foundationWarehouse.setRecordStatus(FoundationNormalStatus.COMMITTED.getStatus());
            foundationWarehouse.setLocationNumber(warehouse.getLocationNumber());
            foundationWarehouse.setCountryCode(warehouse.getCountryCode());
            foundationWarehouse.setCountryName(warehouse.getCountryName());
            foundationWarehouse.setProvinceId(warehouse.getProvinceId());
            foundationWarehouse.setProvinceName(warehouse.getProvinceName());
            foundationWarehouse.setCityId(warehouse.getCityId());
            foundationWarehouse.setCityName(warehouse.getCityName());
            foundationWarehouse.setDistrictId(warehouse.getDistrictId());
            foundationWarehouse.setDistrictName(warehouse.getDistrictName());
            foundationWarehouse.setStreetAddress(warehouse.getStreetAddress());
            foundationWarehouse.setShortAddress(warehouse.getShortAddress());
            foundationWarehouse.setGeneralContactName(warehouse.getGeneralContactName());
            foundationWarehouse.setGeneralphoneNumber(warehouse.getGeneralphoneNumber());
            foundationWarehouse.setGeneralMobile(warehouse.getGeneralMobile());
            foundationWarehouse.setCreatedById(Long.parseLong(rpcHeader.getUid()));
            foundationWarehouse.setCreatedByName(rpcHeader.getUsername());
            foundationWarehouse.setCreateTime(new Date());
            foundationWarehouse.setLastUpdateTime(new Date());
            foundationWarehouse.setTracelog("");
            warehouseDao.insert(foundationWarehouse);
            logger.info("#traceId={}# [OUT][insertWarehouseInfo] params: ", rpcHeader.getTraceId());
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    //TODO: 暂时没有用到 通过页面添加的时候才有使用场景
    public void editWarehouseInfo(WarehouseStructure.EditWarehouseInfoReq req,
                                  StreamObserver<WarehouseStructure.EditWarehouseInfoResp> responseObserver){

        WarehouseStructure.Warehouse warehouse = req.getWarehouse();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        logger.info("#traceId={}# [IN][editWarehouseInfo] params: ", rpcHeader.getTraceId());
        try {
            FoundationWarehouse foundationWarehouse = new FoundationWarehouse();
            foundationWarehouse.setWarehouseId(warehouse.getWarehouseId());
            foundationWarehouse.setWarehouseCode(warehouse.getWarehouseCode());
            foundationWarehouse.setWarehouseName(warehouse.getWarehouseName());
            foundationWarehouse.setEasWarehouseCode(warehouse.getEasWarehouseCode());
            foundationWarehouse.setEasWarehouseName(warehouse.getEasWarehouseName());
            foundationWarehouse.setWmsWarehouseCode(warehouse.getWmsWarehouseCode());
            foundationWarehouse.setWmsWarehouseName(warehouse.getWmsWarehouseName());
            foundationWarehouse.setSendNotitionToWarehouse((byte)warehouse.getSendNotitionToWarehouse());
            foundationWarehouse.setRecordStatus(FoundationNormalStatus.COMMITTED.getStatus());
            foundationWarehouse.setLocationNumber(warehouse.getLocationNumber());
            foundationWarehouse.setCountryCode(warehouse.getCountryCode());
            foundationWarehouse.setCountryName(warehouse.getCountryName());
            foundationWarehouse.setProvinceId(warehouse.getProvinceId());
            foundationWarehouse.setProvinceName(warehouse.getProvinceName());
            foundationWarehouse.setCityId(warehouse.getCityId());
            foundationWarehouse.setCityName(warehouse.getCityName());
            foundationWarehouse.setDistrictId(warehouse.getDistrictId());
            foundationWarehouse.setDistrictName(warehouse.getDistrictName());
            foundationWarehouse.setStreetAddress(warehouse.getStreetAddress());
            foundationWarehouse.setShortAddress(warehouse.getShortAddress());
            foundationWarehouse.setGeneralContactName(warehouse.getGeneralContactName());
            foundationWarehouse.setGeneralphoneNumber(warehouse.getGeneralphoneNumber());
            foundationWarehouse.setGeneralMobile(warehouse.getGeneralMobile());
            foundationWarehouse.setCreatedById(Long.parseLong(rpcHeader.getUid()));
            foundationWarehouse.setCreatedByName(rpcHeader.getUsername());
            foundationWarehouse.setCreateTime(new Date());
            foundationWarehouse.setLastUpdateTime(new Date());
            foundationWarehouse.setTracelog("");
            warehouseDao.updateWarehouse(foundationWarehouse);
            logger.info("#traceId={}# [OUT][editWarehouseInfo] params: ", rpcHeader.getTraceId());
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }
}
