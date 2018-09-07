package com.yhglobal.gongxiao.foundation.area.microservice.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.foundation.area.dao.FoundationDistributorShippingAddressDao;
import com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressServiceGrpc;
import com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure;
import com.yhglobal.gongxiao.foundation.area.model.FoundationDistributorShippingAddress;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.status.FoundationNormalStatus;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Service
public class ShippingAddressImpl extends ShippingAddressServiceGrpc.ShippingAddressServiceImplBase {

    private static Logger logger = LoggerFactory.getLogger(ShippingAddressImpl.class);

    @Autowired
    FoundationDistributorShippingAddressDao foundationDistributorShippingAddressDao;

    /**
     * 获取经销商默认收货地址
     *
     * 注：默认地址会放在拉取地址列表的第一个，暂时不会单独来拉取该接口
     *
     * @param req
     * @param responseObserver
     */
    public void getDefaultShippingAddress(ShippingAddressStructure.GetDefaultShippingAddressReq req,
                                          StreamObserver<ShippingAddressStructure.GetDefaultShippingAddressResp> responseObserver) {
        ShippingAddressStructure.GetDefaultShippingAddressResp.Builder respBuilder = ShippingAddressStructure.GetDefaultShippingAddressResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        long distributorId = req.getDistributorId();
        logger.info("#traceId={}# [IN][getDefaultShippingAddress] params: distributorId={} ", rpcHeader.getTraceId(), distributorId);
        try {
            FoundationDistributorShippingAddress distributorShippingAddress = foundationDistributorShippingAddressDao.getDefaultAddress(distributorId);
            if (distributorShippingAddress==null){
                logger.info("没有拿到地址数据 #traceId={}, distributorId={}",rpcHeader.getTraceId(), distributorId);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            ShippingAddressStructure.DistributorShippingAddress distributorShippingAddress1 = ShippingAddressStructure.DistributorShippingAddress.newBuilder()
                    .setAddressId(GrpcCommonUtil.getProtoParam(distributorShippingAddress.getAddressId()))
                    .setDistributorId(GrpcCommonUtil.getProtoParam(distributorShippingAddress.getDistributorId()))
                    .setDistributorName(GrpcCommonUtil.getProtoParam(distributorShippingAddress.getDistributorName()))
                    .setReceiver(GrpcCommonUtil.getProtoParam(distributorShippingAddress.getReceiver()))
                    .setProvinceId(distributorShippingAddress.getProvinceId())
                    .setProvinceName(distributorShippingAddress.getProvinceName())
                    .setCityId(distributorShippingAddress.getCityId())
                    .setCityName(distributorShippingAddress.getCityName())
                    .setDistrictId(distributorShippingAddress.getDistrictId())
                    .setDistrictName(distributorShippingAddress.getDistrictName())
                    .setStreetAddress(distributorShippingAddress.getStreetAddress()!=null?distributorShippingAddress.getStreetAddress():"")
                    .setPhoneNumber(distributorShippingAddress.getPhoneNumber()!=null?distributorShippingAddress.getPhoneNumber():"")
                    .setPostCode(distributorShippingAddress.getPostCode()!=null?distributorShippingAddress.getPostCode():"")
                    .setAuditStatus(distributorShippingAddress.getAuditStatus())
                    .setIsDefaultAddress(distributorShippingAddress.getIsDefaultAddress())
                    .setCreateTime(DateUtil.getTime(distributorShippingAddress.getCreateTime()))
                    .setLastUpdateTime(DateUtil.getTime(distributorShippingAddress.getLastUpdateTime()))
                    .setTracelog(distributorShippingAddress.getTracelog()!=null?distributorShippingAddress.getTracelog():"")
                    .build();
            respBuilder.setDistributorShippingAddress(distributorShippingAddress1);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT][getDefaultShippingAddress] SUCCESS params: distributorId={} ", rpcHeader.getTraceId(), distributorId);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 通过状态获取各经销商地址列表
     * 前端状态 1未提交 2,已提交 3,已审核 4.已驳回 5.已作废
     * 数据库状态: 1初始化 2已提交 3已审核 4已驳回 9已废弃
     *
     * @param req
     * @param responseObserver
     */
    public void selectAddressListByStatus(ShippingAddressStructure.SelectAddressListByStatusReq req,
                                          StreamObserver<ShippingAddressStructure.SelectAddressListByStatusResp> responseObserver) {
        ShippingAddressStructure.SelectAddressListByStatusResp.Builder respBuilder = ShippingAddressStructure.SelectAddressListByStatusResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        int recordStatus = req.getRecordStatus();
        long distributorId = req.getDistributorId();
        int pageNumber = req.getPageNumber();
        int pageSize = req.getPageSize();
        String distributorName = req.getDistributorName();
        Map<Integer, Byte> map = new HashMap<>(); //映射前端的地址状态
        map.put(1, FoundationNormalStatus.INIT.getStatus());
        map.put(2, FoundationNormalStatus.COMMITTED.getStatus());
        map.put(3, FoundationNormalStatus.AUDITED.getStatus());
        map.put(4, FoundationNormalStatus.REJECTED.getStatus());
        map.put(5, FoundationNormalStatus.CANCEL.getStatus());
        logger.info("#traceId={}# [IN][selectAddressListByStatus] params: distributorId={},distributorName={},recordStatus={} ",
                rpcHeader.getTraceId(), distributorId, distributorId, recordStatus);
        try {
            PageHelper.startPage(pageNumber, pageSize);
            List<FoundationDistributorShippingAddress> foundationDistributorShippingAddresses
                    = foundationDistributorShippingAddressDao.selectShippingAddressListByStatus( distributorId, distributorName, map.get(recordStatus));
            if (foundationDistributorShippingAddresses.size()==0){
                logger.info("[selectAddressListByStatus] 没有拿到地址数据 #traceId={}, distributorId={}",rpcHeader.getTraceId(), distributorId);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            PageInfo<FoundationDistributorShippingAddress> purchaseOrderPageInfo = new PageInfo<>(foundationDistributorShippingAddresses);
            ShippingAddressStructure.ShippingAddressPage.Builder addressPage = ShippingAddressStructure.ShippingAddressPage.newBuilder();

            for (FoundationDistributorShippingAddress distributorShippingAddress : purchaseOrderPageInfo.getList()) {
                ShippingAddressStructure.DistributorShippingAddress distributorShippingAddress1 = ShippingAddressStructure.DistributorShippingAddress.newBuilder()
                        .setAddressId(distributorShippingAddress.getAddressId())
                        .setDistributorId(distributorShippingAddress.getDistributorId())
                        .setDistributorName(distributorShippingAddress.getDistributorName())
                        .setReceiver(distributorShippingAddress.getReceiver()!=null?distributorShippingAddress.getReceiver():"")
                        .setProvinceId(distributorShippingAddress.getProvinceId())
                        .setProvinceName(distributorShippingAddress.getProvinceName())
                        .setCityId(distributorShippingAddress.getCityId())
                        .setCityName(distributorShippingAddress.getCityName())
                        .setDistrictId(distributorShippingAddress.getDistrictId())
                        .setDistrictName(distributorShippingAddress.getDistrictName())
                        .setStreetAddress(distributorShippingAddress.getStreetAddress()!=null?distributorShippingAddress.getStreetAddress():"")
                        .setPhoneNumber(distributorShippingAddress.getPhoneNumber()!=null?distributorShippingAddress.getPhoneNumber():"")
                        .setPostCode(distributorShippingAddress.getPostCode()!=null?distributorShippingAddress.getPostCode():"")
                        .setAuditStatus(distributorShippingAddress.getAuditStatus())
                        .setIsDefaultAddress(distributorShippingAddress.getIsDefaultAddress())
                        .setCreateTime(DateUtil.getTime(distributorShippingAddress.getCreateTime()))
                        .setLastUpdateTime(DateUtil.getTime(distributorShippingAddress.getLastUpdateTime()))
                        .setTracelog(distributorShippingAddress.getTracelog()!=null?distributorShippingAddress.getTracelog():"")
                        .build();
                addressPage.addDistributorShippingAddress(distributorShippingAddress1);
            }
            addressPage.setTotal((int)purchaseOrderPageInfo.getTotal());
            respBuilder.setShippingAddressPage(addressPage);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT][selectAddressListByStatus] SUCCESS params: distributorId={},distributorName={},recordStatus={},size={} ",
                    rpcHeader.getTraceId(), distributorId, distributorId, recordStatus, foundationDistributorShippingAddresses.size());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }

    }

    /**
     * 审核/驳回地址 1审核通过 2驳回
     * 数据库状态: 1初始化 2已提交 3已审核 4已驳回 9已废弃
     *
     * @param req
     * @param responseObserver
     */
    public void auditAddress(ShippingAddressStructure.AuditAddressReq req,
                             StreamObserver<ShippingAddressStructure.AuditAddressResp> responseObserver) {
        ShippingAddressStructure.AuditAddressResp.Builder respBuilder = ShippingAddressStructure.AuditAddressResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        long distributorAddressId = req.getDistributorAddressId();
        int auditStatus = req.getAuditStatus();
        Map<Integer, Byte> map = new HashMap<>();
        map.put(1, FoundationNormalStatus.AUDITED.getStatus());
        map.put(2, FoundationNormalStatus.REJECTED.getStatus());
        logger.info("#traceId={}# [IN][auditAddress] params: distributorId={},auditStatus={} ", rpcHeader.getTraceId(), distributorAddressId, auditStatus);

        try {
            int i = foundationDistributorShippingAddressDao.updateShippingAddressStatus(distributorAddressId, map.get(auditStatus));
            respBuilder.setNumber(i);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [IN][auditAddress] SUCCESS params: distributorId={},auditStatus={} ", rpcHeader.getTraceId(), distributorAddressId, auditStatus);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 获取所有有效收货地址列表
     *
     * @param req              经销商ID
     * @param responseObserver
     */
    public void selectAuditedAddressByDistributorId(ShippingAddressStructure.SelectAuditedAddressByDistributorIdReq req,
                                                    StreamObserver<ShippingAddressStructure.SelectAuditedAddressByDistributorIResp> responseObserver) {
        ShippingAddressStructure.SelectAuditedAddressByDistributorIResp.Builder respBuilder = ShippingAddressStructure.SelectAuditedAddressByDistributorIResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        long distributorAddressId = req.getDistributorAddressId();
        logger.info("#traceId={}# [IN][selectAuditedAddressByDistributorId] params: distributorId={} ", rpcHeader.getTraceId(), distributorAddressId);
        try {
            List<FoundationDistributorShippingAddress> foundationDistributorShippingAddresses
                    = foundationDistributorShippingAddressDao.selectAuditedAddressListByDistributor(distributorAddressId);
            for (FoundationDistributorShippingAddress distributorShippingAddress : foundationDistributorShippingAddresses) {
                ShippingAddressStructure.DistributorShippingAddress distributorShippingAddress1 = ShippingAddressStructure.DistributorShippingAddress.newBuilder()
                        .setAddressId(distributorShippingAddress.getAddressId())
                        .setDistributorId(distributorShippingAddress.getDistributorId())
                        .setDistributorName(distributorShippingAddress.getDistributorName())
                        .setReceiver(distributorShippingAddress.getReceiver()!=null?distributorShippingAddress.getReceiver():"")
                        .setProvinceId(distributorShippingAddress.getProvinceId())
                        .setProvinceName(distributorShippingAddress.getProvinceName())
                        .setCityId(distributorShippingAddress.getCityId())
                        .setCityName(distributorShippingAddress.getCityName())
                        .setDistrictId(distributorShippingAddress.getDistrictId())
                        .setDistrictName(distributorShippingAddress.getDistrictName())
                        .setStreetAddress(distributorShippingAddress.getStreetAddress()!=null?distributorShippingAddress.getStreetAddress():"")
                        .setPhoneNumber(distributorShippingAddress.getPhoneNumber()!=null?distributorShippingAddress.getPhoneNumber():"")
                        .setPostCode(distributorShippingAddress.getPostCode()!=null?distributorShippingAddress.getPostCode():"")
                        .setAuditStatus(distributorShippingAddress.getAuditStatus())
                        .setIsDefaultAddress(distributorShippingAddress.getIsDefaultAddress())
                        .setCreateTime(DateUtil.getTime(distributorShippingAddress.getCreateTime()))
                        .setLastUpdateTime(DateUtil.getTime(distributorShippingAddress.getLastUpdateTime()))
                        .setTracelog(distributorShippingAddress.getTracelog()!=null?distributorShippingAddress.getTracelog():"")
                        .build();
                respBuilder.addDistributorShippingAddress(distributorShippingAddress1);
            }
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT][selectAuditedAddressByDistributorId] SUCCESS params: distributorId={} ", rpcHeader.getTraceId(), distributorAddressId);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    //设置AD默认地址
    public void setDefaultAddress(ShippingAddressStructure.SetDefaultAddressReq req,
                                  StreamObserver<ShippingAddressStructure.SetDefaultAddressResp> responseObserver) {
        ShippingAddressStructure.SetDefaultAddressResp.Builder respBuilder = ShippingAddressStructure.SetDefaultAddressResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        long addressId = req.getDistributorAddressId();
        logger.info("#traceId={}# [IN][setDefaultAddress] params: addressId={} ", rpcHeader.getTraceId(), addressId);

        try {
            //通过地址id,获取该条地址数据
            FoundationDistributorShippingAddress shippingAddress = foundationDistributorShippingAddressDao.getAddressByAddressId(addressId);
            Long distributorId = shippingAddress.getDistributorId();
            //获取经销商的默认地址
            FoundationDistributorShippingAddress distributorShippingAddress = foundationDistributorShippingAddressDao.getDefaultAddress(distributorId);
            if (distributorShippingAddress == null) { //之前没有默认地址
                foundationDistributorShippingAddressDao.setDefaultAddress(addressId, (byte) 1);
                respBuilder.setNumber(1);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                logger.info("#traceId={}# [OUT][setDefaultAddress] SUCCESS params: addressId={} ", rpcHeader.getTraceId(), addressId);
                return;
            } else if (addressId == distributorShippingAddress.getAddressId()) { //本次设置的默认地址跟之前设置的默认地址一致,直接返回成功
                respBuilder.setNumber(1);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                logger.info("#traceId={}# [OUT][setDefaultAddress] SUCCESS params: addressId={} ", rpcHeader.getTraceId(), addressId);
                return;
            } else if (addressId != distributorShippingAddress.getAddressId()) { //之前设置的默认地址跟本次设置的默认地址不一致
                foundationDistributorShippingAddressDao.setDefaultAddress(addressId, (byte) 1); //1表示是默认
                foundationDistributorShippingAddressDao.setDefaultAddress(distributorShippingAddress.getAddressId(), (byte) 0); //把以前的默认地址标记为0 表非默认
                respBuilder.setNumber(1);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                logger.info("#traceId={}# [OUT][setDefaultAddress] SUCCESS params: addressId={} ", rpcHeader.getTraceId(), addressId);
                return;
            }
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    //通过经销商id获取已审核的地址列表
    public void selectAllAddressByDistributorId(ShippingAddressStructure.SelectAllAddressByDistributorIdReq req,
                                                StreamObserver<ShippingAddressStructure.SelectAllAddressByDistributorIdResp> responseObserver) {
        ShippingAddressStructure.SelectAllAddressByDistributorIdResp.Builder respBuilder = ShippingAddressStructure.SelectAllAddressByDistributorIdResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        long distributorId = req.getDistributorId();
        logger.info("#traceId={}# [IN][selectAllAddressByDistributorId] params: distributorId={} ", rpcHeader.getTraceId(), distributorId);
        try {
            List<FoundationDistributorShippingAddress> foundationDistributorShippingAddresses = foundationDistributorShippingAddressDao.selectAllShippingAddressListByDistributorId(distributorId);
            for (FoundationDistributorShippingAddress distributorShippingAddress : foundationDistributorShippingAddresses) {
                ShippingAddressStructure.DistributorShippingAddress distributorShippingAddress1 = ShippingAddressStructure.DistributorShippingAddress.newBuilder()
                        .setAddressId(distributorShippingAddress.getAddressId())
                        .setDistributorId(distributorShippingAddress.getDistributorId())
                        .setDistributorName(distributorShippingAddress.getDistributorName())
                        .setReceiver(distributorShippingAddress.getReceiver()!=null?distributorShippingAddress.getReceiver():"")
                        .setProvinceId(distributorShippingAddress.getProvinceId())
                        .setProvinceName(distributorShippingAddress.getProvinceName())
                        .setCityId(distributorShippingAddress.getCityId())
                        .setCityName(distributorShippingAddress.getCityName())
                        .setDistrictId(distributorShippingAddress.getDistrictId())
                        .setDistrictName(distributorShippingAddress.getDistrictName())
                        .setStreetAddress(distributorShippingAddress.getStreetAddress()!=null?distributorShippingAddress.getStreetAddress():"")
                        .setPhoneNumber(distributorShippingAddress.getPhoneNumber()!=null?distributorShippingAddress.getPhoneNumber():"")
                        .setPostCode(distributorShippingAddress.getPostCode()!=null?distributorShippingAddress.getPostCode():"")
                        .setAuditStatus(distributorShippingAddress.getAuditStatus())
                        .setIsDefaultAddress(distributorShippingAddress.getIsDefaultAddress())
                        .setCreateTime(DateUtil.getTime(distributorShippingAddress.getCreateTime()))
                        .setLastUpdateTime(DateUtil.getTime(distributorShippingAddress.getLastUpdateTime()))
                        .setTracelog(distributorShippingAddress.getTracelog()!=null?distributorShippingAddress.getTracelog():"")
                        .build();
                respBuilder.addDistributorShippingAddress(distributorShippingAddress1);
            }
            logger.info("#traceId={}# [OUT][selectAllAddressByDistributorId] SUCCESS params: foundationDistributorShippingAddresses.size={} ", rpcHeader.getTraceId(), foundationDistributorShippingAddresses.size());

            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    //插入新地址
    public void insertShippingAddress(ShippingAddressStructure.InsertShippingAddressReq req,
                                      StreamObserver<ShippingAddressStructure.InsertShippingAddressResp> responseObserver) {
        ShippingAddressStructure.InsertShippingAddressResp.Builder respBuilder = ShippingAddressStructure.InsertShippingAddressResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        ShippingAddressStructure.DistributorShippingAddress distributorShippingAddress = req.getDistributorShippingAddress();

        logger.info("#traceId={}# [IN][selectAllAddressByDistributorId] params: distributorId={} ", rpcHeader.getTraceId(), distributorShippingAddress.getDistributorId());
        try {
            int isDefaultAddress = distributorShippingAddress.getIsDefaultAddress();
            long distributorId = distributorShippingAddress.getDistributorId();
            //如果新地址是默认地址,将之前的地址改为非默认地址
            if (isDefaultAddress == 1) {
                FoundationDistributorShippingAddress defaultAddress = foundationDistributorShippingAddressDao.getDefaultAddress(distributorId);
                if (defaultAddress != null) {
                    foundationDistributorShippingAddressDao.setDefaultAddress(distributorId, (byte)0);
                }
            }
            FoundationDistributorShippingAddress distributorShippingAddress1 = new FoundationDistributorShippingAddress();
            distributorShippingAddress1.setAddressId(distributorShippingAddress.getAddressId());
            distributorShippingAddress1.setDistributorId(distributorShippingAddress.getDistributorId());
            distributorShippingAddress1.setDistributorName(distributorShippingAddress.getDistributorName());
            distributorShippingAddress1.setReceiver(distributorShippingAddress.getReceiver());
            distributorShippingAddress1.setProvinceId(distributorShippingAddress.getProvinceId());
            distributorShippingAddress1.setProvinceName(distributorShippingAddress.getProvinceName());
            distributorShippingAddress1.setCityId(distributorShippingAddress.getCityId());
            distributorShippingAddress1.setCityName(distributorShippingAddress.getCityName());
            distributorShippingAddress1.setDistrictId(distributorShippingAddress.getDistrictId());
            distributorShippingAddress1.setDistrictName(distributorShippingAddress.getDistrictName());
            distributorShippingAddress1.setStreetAddress(distributorShippingAddress.getStreetAddress());
            distributorShippingAddress1.setPhoneNumber(distributorShippingAddress.getPhoneNumber());
            distributorShippingAddress1.setPostCode(distributorShippingAddress.getPostCode());
            distributorShippingAddress1.setAuditStatus((byte) distributorShippingAddress.getAuditStatus());
            distributorShippingAddress1.setIsDefaultAddress((byte) distributorShippingAddress.getIsDefaultAddress());
            distributorShippingAddress1.setCreateTime(DateUtil.long2Date(distributorShippingAddress.getCreateTime()));
            distributorShippingAddress1.setLastUpdateTime(DateUtil.long2Date(distributorShippingAddress.getLastUpdateTime()));
            distributorShippingAddress1.setTracelog(distributorShippingAddress.getTracelog()!=null?distributorShippingAddress.getTracelog():"");
            int i = foundationDistributorShippingAddressDao.insertShippingAddress(distributorShippingAddress1);
            respBuilder.setNumber(i);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT][insertShippingAddress] SUCCESS params:  ", rpcHeader.getTraceId());

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    //删除新地址
    public void deleteShippingAddress(ShippingAddressStructure.DeleteShippingAddressReq req,
                                      StreamObserver<ShippingAddressStructure.DeleteShippingAddressResp> responseObserver) {
        ShippingAddressStructure.DeleteShippingAddressResp.Builder respBuilder = ShippingAddressStructure.DeleteShippingAddressResp.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        long addressId = req.getAddressId();
        try {
            foundationDistributorShippingAddressDao.updateShippingAddressStatus(addressId, (byte) FoundationNormalStatus.CANCEL.getStatus());
            logger.info("#traceId={}# [OUT][deleteShippingAddress] SUCCESS params:  ", rpcHeader.getTraceId());
        }
        catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

}
