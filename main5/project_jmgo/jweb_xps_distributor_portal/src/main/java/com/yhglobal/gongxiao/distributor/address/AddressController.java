package com.yhglobal.gongxiao.distributor.address;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ListenableFuture;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.distributor.address.vo.DistributorShippingAddress;
import com.yhglobal.gongxiao.distributor.util.DistributorPortalTraceIdGenerator;
import com.yhglobal.gongxiao.foundation.area.microservice.AreaServiceGrpc;
import com.yhglobal.gongxiao.foundation.area.microservice.AreaStructure;
import com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressServiceGrpc;
import com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.status.FoundationNormalStatus;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.ValidationUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yhglobal.gongxiao.vo.AddressVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 地址管理
 *
 * @author: 陈浩
 **/
@Controller
@RequestMapping("/address")
public class AddressController {

    private static Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;  //处于登陆状态时 用户信息会注入到这个对象

    @ResponseBody
    @RequestMapping("/getProvinceCityDistrict")
    public GongxiaoResult getProvinceCityDistrict(HttpServletRequest request,HttpServletResponse httpServletResponse){
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult ;
        String traceId = null;
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getProvinceCityDistrict] params:  ", traceId);

            AreaServiceGrpc.AreaServiceBlockingStub areaRpcStub = RpcStubStore.getRpcStub(AreaServiceGrpc.AreaServiceBlockingStub.class);

            AreaStructure.SelectProvinceRequest selectProvinceListRequest = AreaStructure.SelectProvinceRequest.newBuilder().setRpcHeader(rpcHeader).build();
            AreaStructure.SelectAllCityRequest selectAllCityRequest = AreaStructure.SelectAllCityRequest.newBuilder().setRpcHeader(rpcHeader).build();
            AreaStructure.SelectAllDistrictRequest selectAllDistrictRequest = AreaStructure.SelectAllDistrictRequest.newBuilder().setRpcHeader(rpcHeader).build();

            AreaStructure.SelectProvinceResponse selectProvinceResponse = areaRpcStub.selectProvinceList(selectProvinceListRequest);
            AreaStructure.SelectAllCityResponse selectAllCityResponse = areaRpcStub.selectAllCity(selectAllCityRequest);
            AreaStructure.SelectAllDistrictResponse selectAllDistrictResponse = areaRpcStub.selectAllDistrict(selectAllDistrictRequest);

            List<AreaStructure.Province> provinceList = selectProvinceResponse.getProvinceList();
            List<AreaStructure.City> cityList = selectAllCityResponse.getCityList();
            List<AreaStructure.District> districtList = selectAllDistrictResponse.getDistrictList();

            Map<Integer,Object> addressMap = new HashMap<>();

            Map <String ,List<AddressVo>> provinceMap = new HashMap<>();
            List<AddressVo> addressList = new ArrayList<>();
            List<AddressVo> aGList = new ArrayList<>();
            aGList.add( new AddressVo(340000,"安徽省"));
            aGList.add( new AddressVo(110000,"北京市"));
            aGList.add( new AddressVo(500000,"重庆市"));
            aGList.add( new AddressVo(350000,"福建省"));
            aGList.add( new AddressVo(620000,"甘肃省"));
            aGList.add( new AddressVo(440000,"广东省"));
            aGList.add( new AddressVo(450000,"广西壮族自治区"));
            aGList.add( new AddressVo(520000,"贵州省"));
            addressList.addAll(aGList);
            provinceMap.put("A-G",aGList);

            List<AddressVo> hKList = new ArrayList<>();
            hKList.add( new AddressVo(460000,"海南省"));
            hKList.add( new AddressVo(130000,"河北省"));
            hKList.add( new AddressVo(230000,"黑龙江省"));
            hKList.add( new AddressVo(410000,"河南省"));
            hKList.add( new AddressVo(420000,"湖北省"));
            hKList.add( new AddressVo(430000,"湖南省"));
            hKList.add( new AddressVo(320000,"江苏省"));
            hKList.add( new AddressVo(360000,"江西省"));
            hKList.add( new AddressVo(220000,"吉林省"));
            addressList.addAll(hKList);
            provinceMap.put("H-K",hKList);

            List<AddressVo> lsList = new ArrayList<>();
            lsList.add( new AddressVo(210000,"辽宁省"));
            lsList.add( new AddressVo(150000,"内蒙古自治区"));
            lsList.add( new AddressVo(640000,"宁夏回族自治区"));
            lsList.add( new AddressVo(630000,"青海省"));
            lsList.add( new AddressVo(370000,"山东省"));
            lsList.add( new AddressVo(310000,"上海市"));
            lsList.add( new AddressVo(140000,"山西省"));
            lsList.add( new AddressVo(610000,"陕西省"));
            lsList.add( new AddressVo(510000,"四川省"));
            addressList.addAll(lsList);
            provinceMap.put("L-S",lsList);

            List<AddressVo> tzList = new ArrayList<>();
            tzList.add( new AddressVo(120000,"天津市"));
            tzList.add( new AddressVo(650000,"新疆维吾尔自治区"));
            tzList.add( new AddressVo(540000,"西藏自治区"));
            tzList.add( new AddressVo(530000,"云南省"));
            tzList.add( new AddressVo(330000,"浙江省"));
            addressList.addAll(tzList);
            provinceMap.put("T-Z",tzList);
            addressMap.put(86,provinceMap);

            //使用map保存所有市
            //1. 获取所有的省编码  2.获取所有市,与省配对 3.配对成功丢到市map里 4.将省map丢到地址map里
            for (AreaStructure.Province province:provinceList){
                Map<Integer ,String > cityMap = new HashMap<>();
                int provinceCode = province.getProvinceCode();
                for (AreaStructure.City city:cityList){
                    int parentCode = city.getProvinceCode();
                    int cityCode = city.getCityCode();
                    String cityName = city.getCityName();
                    if (provinceCode==parentCode){
                        cityMap.put(cityCode,cityName);
                    }
                }
                addressMap.put(provinceCode,cityMap);
            }
            //1. 获取所有的市编码  2.获取所有区,与市配对 3.配对成功丢到区map里 4.将区map丢到地址map里
            for (AreaStructure.City city:cityList){
                Map<Integer ,String > districtMap = new HashMap<>();
                int cityCode = city.getCityCode();
                for (AreaStructure.District district:districtList){
                    int cityCode1 = district.getCityCode();
                    int districtCode = district.getDistrictCode();
                    String districtName = district.getDistrictName();
                    if (cityCode == cityCode1){
                        districtMap.put(districtCode,districtName);
                    }
                }
                addressMap.put(cityCode,districtMap);
            }
            String addressJson = JSON.toJSONString(addressMap);
            System.out.println(addressJson);
            logger.info("#traceId={}# [getProvinceCityDistrict][OUT] success addressJson={}",traceId,addressJson);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), addressJson);
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    @RequestMapping("/selectValidAddress")
    @ResponseBody
    public GongxiaoResult selectValidAddress(HttpServletRequest request){
        GongxiaoResult gongxiaoResult ;
        long distributorId = portalUserInfo.getDistributorId();
        String traceId = null;
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][selectValidAddress] params:  distributorId={} ", traceId, distributorId);
            ShippingAddressServiceGrpc.ShippingAddressServiceBlockingStub shippingAddressStub = RpcStubStore.getRpcStub(ShippingAddressServiceGrpc.ShippingAddressServiceBlockingStub.class);
            ShippingAddressStructure.SelectAuditedAddressByDistributorIdReq selectAuditedAddressByDistributorIdReq = ShippingAddressStructure.SelectAuditedAddressByDistributorIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setDistributorAddressId(distributorId)
                    .build();
            ShippingAddressStructure.SelectAuditedAddressByDistributorIResp resp = shippingAddressStub.selectAuditedAddressByDistributorId(selectAuditedAddressByDistributorIdReq);
            List<ShippingAddressStructure.DistributorShippingAddress> distributorShippingAddressList = resp.getDistributorShippingAddressList();

            List<DistributorShippingAddress> shippingAddressList = new ArrayList<>();
            for (ShippingAddressStructure.DistributorShippingAddress distributorShippingAddress:distributorShippingAddressList){
                DistributorShippingAddress distributorShippingAddress1 = new DistributorShippingAddress();
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
                distributorShippingAddress1.setAuditStatus(distributorShippingAddress.getAuditStatus()+"");
                distributorShippingAddress1.setIsDefaultAddress((byte) distributorShippingAddress.getIsDefaultAddress());
                distributorShippingAddress1.setCreateTime(DateUtil.long2Date(distributorShippingAddress.getCreateTime()));
                distributorShippingAddress1.setLastUpdateTime(DateUtil.long2Date(distributorShippingAddress.getLastUpdateTime()));
                distributorShippingAddress1.setTracelog(distributorShippingAddress.getTracelog());
                shippingAddressList.add(distributorShippingAddress1);
           }
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), shippingAddressList);
            logger.info("#traceId={}# [selectValidAddress][OUT] success");
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    @RequestMapping("/selectAllAddress")
    @ResponseBody
    public GongxiaoResult selectAllAddress(HttpServletRequest request){
        GongxiaoResult gongxiaoResult ;
        long distributorId = portalUserInfo.getDistributorId();
        String traceId = null;
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getProductList] params:  distributorId={} ", traceId, distributorId);


            ShippingAddressServiceGrpc.ShippingAddressServiceBlockingStub shippingAddressStub = RpcStubStore.getRpcStub(ShippingAddressServiceGrpc.ShippingAddressServiceBlockingStub.class);
            ShippingAddressStructure.SelectAllAddressByDistributorIdReq selectAllAddressByDistributorIdReq
                    = ShippingAddressStructure.SelectAllAddressByDistributorIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setDistributorId(distributorId)
                    .build();
            ShippingAddressStructure.SelectAllAddressByDistributorIdResp resp=shippingAddressStub.selectAllAddressByDistributorId(selectAllAddressByDistributorIdReq);
            List<DistributorShippingAddress> distributorShippingAddressList = new ArrayList<>();
            List<ShippingAddressStructure.DistributorShippingAddress> distributorShippingAddressList1 = resp.getDistributorShippingAddressList();
            for (ShippingAddressStructure.DistributorShippingAddress distributorShippingAddress:distributorShippingAddressList1){
                DistributorShippingAddress distributorShippingAddress1 = new DistributorShippingAddress();
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
                distributorShippingAddress1.setAuditStatus(distributorShippingAddress.getAuditStatus()+"");
                distributorShippingAddress1.setIsDefaultAddress((byte) distributorShippingAddress.getIsDefaultAddress());
                distributorShippingAddress1.setCreateTime(DateUtil.long2Date(distributorShippingAddress.getCreateTime()));
                distributorShippingAddress1.setLastUpdateTime(DateUtil.long2Date(distributorShippingAddress.getLastUpdateTime()));
                distributorShippingAddress1.setTracelog(distributorShippingAddress.getTracelog());
                distributorShippingAddressList.add(distributorShippingAddress1);
            }

            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), distributorShippingAddressList);
            logger.info("#traceId={}# [selectAllAddress][OUT] success");
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    @RequestMapping("/insertAddress")
    @ResponseBody
    public GongxiaoResult insertAddress(HttpServletRequest request,
                                        String receiver,
                                        String provinceId,
                                        String provinceName,
                                        String cityId,
                                        String cityName,
                                        String districtId,
                                        String districtName,
                                        String streetAddress,
                                        String phoneNumber,
                                        String postCode,
                                        boolean isDefalutAddress,
                                        HttpServletResponse httpServletResponse){
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult ;
        long distributorId = portalUserInfo.getDistributorId();
        String distributorName=portalUserInfo.getDistributorName();
        boolean isMobile = ValidationUtil.isMobile(phoneNumber);
        if (!isMobile){
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.PHONE_FORMAT_FALSE);
        }
        String traceId = null;
        try {
            int defaultAddress=0;
            if (isDefalutAddress){
                defaultAddress=1;
            }
            ShippingAddressStructure.DistributorShippingAddress distributorShippingAddress1 = ShippingAddressStructure.DistributorShippingAddress.newBuilder()
                    .setDistributorId(distributorId)
                    .setDistributorName(distributorName)
                    .setReceiver(receiver)
                    .setProvinceId(Integer.parseInt(provinceId))
                    .setProvinceName(provinceName)
                    .setCityId(Integer.parseInt(cityId))
                    .setCityName(cityName)
                    .setDistrictId(Integer.parseInt(districtId))
                    .setDistrictName(districtName)
                    .setStreetAddress(streetAddress)
                    .setPhoneNumber(phoneNumber)
                    .setPostCode(postCode)
                    .setAuditStatus(FoundationNormalStatus.COMMITTED.getStatus())
                    .setIsDefaultAddress(defaultAddress)
                    .setCreateTime(DateUtil.getTime(new Date()))
                    .setLastUpdateTime(DateUtil.getTime(new Date()))
                    .build();
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getProductList] params:  distributorId={} ", traceId, distributorId);
            ShippingAddressServiceGrpc.ShippingAddressServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(ShippingAddressServiceGrpc.ShippingAddressServiceBlockingStub.class);
            ShippingAddressStructure.InsertShippingAddressReq insertShippingAddressReq = ShippingAddressStructure.InsertShippingAddressReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setDistributorShippingAddress(distributorShippingAddress1)
                    .build();
            ShippingAddressStructure.InsertShippingAddressResp insertShippingAddressResp = rpcStub.insertShippingAddress(insertShippingAddressReq);
            int number = insertShippingAddressResp.getNumber();
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), number);
            logger.info("#traceId={}# [selectAllAddress][OUT] success");
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    @RequestMapping("/deleteAddress")
    @ResponseBody
    public GongxiaoResult deleteAddress(HttpServletRequest request,String distributorId,int addressId,HttpServletResponse httpServletResponse){
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult ;
        String traceId = null;
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][deleteAddress] params:  distributorId={} ", traceId, distributorId);

            ShippingAddressServiceGrpc.ShippingAddressServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(ShippingAddressServiceGrpc.ShippingAddressServiceBlockingStub.class);
            ShippingAddressStructure.DeleteShippingAddressReq deleteShippingAddressReq = ShippingAddressStructure.DeleteShippingAddressReq.newBuilder()
                    .setAddressId(addressId)
                    .setRpcHeader(rpcHeader)
                    .build();
            ShippingAddressStructure.DeleteShippingAddressResp deleteShippingAddressResp = rpcStub.deleteShippingAddress(deleteShippingAddressReq);
            int number = deleteShippingAddressResp.getNumber();
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), number);
            logger.info("#traceId={}# [deleteAddress][OUT] success");
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    @RequestMapping(value = "/setDefaultAddress",method = RequestMethod.POST)
    @ResponseBody
    public GongxiaoResult setDefaultAddress(HttpServletRequest request,int addressId){
        GongxiaoResult gongxiaoResult ;
        String traceId = null;
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][setDefaultAddress] params:  distributorId={} ", traceId, rpcHeader.getUid());
            ShippingAddressServiceGrpc.ShippingAddressServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(ShippingAddressServiceGrpc.ShippingAddressServiceBlockingStub.class);
            ShippingAddressStructure.SetDefaultAddressReq setDefaultAddressReq = ShippingAddressStructure.SetDefaultAddressReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setDistributorAddressId(addressId)
                    .build();
            ShippingAddressStructure.SetDefaultAddressResp setDefaultAddressResp = rpcStub.setDefaultAddress(setDefaultAddressReq);
            int number = setDefaultAddressResp.getNumber();
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), number);
            logger.info("#traceId={}# [setDefaultAddress][OUT] success");
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

}
