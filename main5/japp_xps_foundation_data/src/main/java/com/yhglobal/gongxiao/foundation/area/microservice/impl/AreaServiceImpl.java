package com.yhglobal.gongxiao.foundation.area.microservice.impl;

import com.yhglobal.gongxiao.foundation.area.dao.FoundationAreaDao;
import com.yhglobal.gongxiao.foundation.area.microservice.AreaServiceGrpc;
import com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure;
import com.yhglobal.gongxiao.foundation.area.model.FoundationCity;
import com.yhglobal.gongxiao.foundation.area.model.FoundationDistrict;
import com.yhglobal.gongxiao.foundation.area.model.FoundationProvince;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AreaServiceImpl extends AreaServiceGrpc.AreaServiceImplBase{

    private static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);

    @Autowired
    FoundationAreaDao foundationAreaDao;

    //1获取所有省列表
    @Override
    public void selectProvinceList(AreaStructure.SelectProvinceRequest request,
                                   StreamObserver<AreaStructure.SelectProvinceResponse> responseObserver) {

        AreaStructure.SelectProvinceResponse res; //保存返回的值
        AreaStructure.SelectProvinceResponse.Builder respBuilder = AreaStructure.SelectProvinceResponse.newBuilder(); //每个proto对象都需要从builder构建出来

        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();

        logger.info("#traceId={}# [IN][selectProvinceList] params: ", rpcHeader.getTraceId());

        try {
            List<FoundationProvince> provinces = foundationAreaDao.selectProvinceList();
            if (provinces.size() == 0) {
                logger.info("#traceId={}# [OUT] fail to selectProvinceList:  provinces=NULL", rpcHeader.getTraceId());
            } else {
                logger.info("#traceId={}# [OUT] selectProvinceList success: provinces.size={}", rpcHeader.getTraceId(), provinces.size());
            }
            for (FoundationProvince province:provinces){
                AreaStructure.Province province1 = AreaStructure.Province.newBuilder()
                        .setProvinceId(province.getProvinceId())
                        .setProvinceCode(province.getProvinceCode())
                        .setProvinceName(province.getProvinceName())
                        .build();
                respBuilder.addProvince(province1);
            }
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    //2 获取某省下的所有城市
    @Override
    public void selectCityByProvince(AreaStructure.SelectCityByProvinceRequest request,
                                     StreamObserver<AreaStructure.SelectCityByProvinceResponse> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String provinceCode = request.getProvinceCode();

        AreaStructure.SelectCityByProvinceResponse response; //保存返回的值
        AreaStructure.SelectCityByProvinceResponse.Builder respBuilder
                = AreaStructure.SelectCityByProvinceResponse.newBuilder(); //每个proto对象都需要从builder构建出来

        logger.info("#traceId={}# [IN][selectCityByProvince] params: provinceCode={}", rpcHeader.getTraceId(), provinceCode);
        try {
            List<FoundationCity> cities = foundationAreaDao.selectCitysByProvince(provinceCode);
            if (cities.size() == 0) {
                logger.info("#traceId={}# [OUT] fail to selectCityByProvince:  provinces=NULL", rpcHeader.getTraceId());
            } else {
                logger.info("#traceId={}# [OUT] selectCityByProvince success: cities.size={}", rpcHeader.getTraceId(), cities.size());
            }
            for (FoundationCity city:cities){
                AreaStructure.City city1 = AreaStructure.City.newBuilder()
                        .setCityCode(city.getCityCode())
                        .setCityId(city.getCityId())
                        .setCityName(city.getCityName())
                        .setProvinceCode(city.getProvinceCode())
                        .build();
                respBuilder.addCity(city1);
            }
            response = respBuilder.build(); //通过build()方法来生成最终的getProjectByIdResp
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    //获取所有的city列表
    @Override
    public void selectAllCity(AreaStructure.SelectAllCityRequest request,
                              StreamObserver<AreaStructure.SelectAllCityResponse> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();

        AreaStructure.SelectAllCityResponse response; //保存返回的值
        AreaStructure.SelectAllCityResponse.Builder respBuilder
                = AreaStructure.SelectAllCityResponse.newBuilder(); //每个proto对象都需要从builder构建出来

        logger.info("#traceId={}# [IN][selectAllCity] params: ", rpcHeader.getTraceId());
        try {
            List<FoundationCity> cities = foundationAreaDao.selectAllCity();
            if (cities.size() == 0) {
                logger.info("#traceId={}# [OUT] fail to selectAllCity:  provinces=NULL", rpcHeader.getTraceId());
            } else {
                logger.info("#traceId={}# [OUT] selectAllCity success: cities.size={}", rpcHeader.getTraceId(), cities.size());
            }
            for (FoundationCity city:cities){
                AreaStructure.City city1 = AreaStructure.City.newBuilder()
                        .setCityCode(city.getCityCode())
                        .setCityId(city.getCityId())
                        .setCityName(city.getCityName())
                        .setProvinceCode(city.getProvinceCode())
                        .build();
                respBuilder.addCity(city1);
            }
            response = respBuilder.build(); //通过build()方法来生成最终的getProjectByIdResp
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    //获取某市下的所有区域
    @Override
    public void selectDistrictsByCity(AreaStructure.SelectDistrictsByCityRequest request,
                                      StreamObserver<AreaStructure.SelectDistrictsByCityResponse> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String cityCode = request.getCityCode();

        AreaStructure.SelectDistrictsByCityResponse response; //保存返回的值
        AreaStructure.SelectDistrictsByCityResponse.Builder respBuilder
                = AreaStructure.SelectDistrictsByCityResponse.newBuilder(); //每个proto对象都需要从builder构建出来
        logger.info("#traceId={}# [IN][selectDistrictsByCity] params: provinceCode={}", rpcHeader.getTraceId(), cityCode);
        try {
            List<FoundationDistrict> districts = foundationAreaDao.selectDstrictsByCity(cityCode);
            if (districts.size() == 0) {
                logger.info("#traceId={}# [OUT] fail to selectDistrictsByCity:  provinces=NULL", rpcHeader.getTraceId());
            } else {
                logger.info("#traceId={}# [OUT] selectDistrictsByCity success: districts.size={}", rpcHeader.getTraceId(), districts.size());
            }
            for (FoundationDistrict district:districts){
                AreaStructure.District district1 = AreaStructure.District.newBuilder()
                        .setDistrictId(district.getDistrictId())
                        .setDistrictCode(district.getDistrictCode())
                        .setDistrictName(district.getDistrictName())
                        .setCityCode(district.getCityCode())
                        .build();
                respBuilder.addDistrictList(district1);
            }
            response = respBuilder.build(); //通过build()方法来生成最终的getProjectByIdResp
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    //5 获取所有的区域
    @Override
    public void selectAllDistrict(AreaStructure.SelectAllDistrictRequest request,
                                  StreamObserver<AreaStructure.SelectAllDistrictResponse> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();

        AreaStructure.SelectAllDistrictResponse response; //保存返回的值
        AreaStructure.SelectAllDistrictResponse.Builder respBuilder
                = AreaStructure.SelectAllDistrictResponse.newBuilder(); //每个proto对象都需要从builder构建出来
        logger.info("#traceId={}# [IN][selectDistrictsByCity] params:", rpcHeader.getTraceId());
        try {
            List<FoundationDistrict> districts = foundationAreaDao.selectAllDistricts();
            if (districts.size() == 0) {
                logger.info("#traceId={}# [OUT] fail to selectDistrictsByCity:  provinces=NULL", rpcHeader.getTraceId());
            } else {
                logger.info("#traceId={}# [OUT] selectDistrictsByCity success: districts.size={}", rpcHeader.getTraceId(), districts.size());
            }
            for (FoundationDistrict district:districts){
                AreaStructure.District district1 = AreaStructure.District.newBuilder()
                        .setDistrictId(district.getDistrictId())
                        .setDistrictCode(district.getDistrictCode())
                        .setDistrictName(district.getDistrictName())
                        .setCityCode(district.getCityCode())
                        .build();
                respBuilder.addDistrict(district1);
            }
        response = respBuilder.build(); //通过build()方法来生成最终的getProjectByIdResp
        responseObserver.onNext(response);
        responseObserver.onCompleted();        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}
