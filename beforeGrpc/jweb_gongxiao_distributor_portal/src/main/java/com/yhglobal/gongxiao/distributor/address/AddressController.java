package com.yhglobal.gongxiao.distributor.address;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.area.City;
import com.yhglobal.gongxiao.area.District;
import com.yhglobal.gongxiao.area.Province;
import com.yhglobal.gongxiao.area.service.AreaService;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.distributor.util.DistributorPortalTraceIdGenerator;
import com.yhglobal.gongxiao.foundation.distributor.model.DistributorShippingAddress;
import com.yhglobal.gongxiao.foundation.distributor.service.DistributorAddressService;
import com.yhglobal.gongxiao.util.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.ValidationUtil;
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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 地址管理
 *
 * @author: 陈浩
 **/
@Controller
@RequestMapping("/address")
public class AddressController {

    private static Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Reference
    DistributorAddressService distributorAddressService;

    @Reference
    AreaService areaService;

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
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getProvinceCityDistrict] params:  ", traceId);

            List<Province> provinces = areaService.selectProvinceList(rpcHeader);
            List<City> cities = areaService.selectAllCity(rpcHeader);
            List<District> districts = areaService.selectAllDistrict(rpcHeader);

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
            for (Province province:provinces){
                Map<Integer ,String > cityMap = new HashMap<>();
                int provinceCode = province.getProvinceCode();
                for (City city:cities){
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
            for (City city:cities){
                Map<Integer ,String > districtMap = new HashMap<>();
                int cityCode = city.getCityCode();
                for (District district:districts){
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
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), addressJson);
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
        String distributorId = portalUserInfo.getDistributorId()+"";
        String traceId = null;
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][selectValidAddress] params:  distributorId={} ", traceId, distributorId);
            List<DistributorShippingAddress> distributorShippingAddressList
                    = distributorAddressService.selectValidAddress(rpcHeader, distributorId);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), distributorShippingAddressList);
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
        String distributorId = portalUserInfo.getDistributorId()+"";
        String traceId = null;
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getProductList] params:  distributorId={} ", traceId, distributorId);
            List<DistributorShippingAddress> distributorShippingAddressList
                    = distributorAddressService.selectDistributorList(rpcHeader, distributorId);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), distributorShippingAddressList);
            logger.info("#traceId={}# [selectAllAddress][OUT] success");
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    @RequestMapping("/selectPreferAddress")
    @ResponseBody
    public GongxiaoResult selectPreferAddress(HttpServletRequest request,HttpServletResponse httpServletResponse){
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult ;
        String traceId = null;
        try {
            String distributorId = portalUserInfo.getDistributorId()+"";
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getProductList] params:  distributorId={} ", traceId, distributorId);
            List<DistributorShippingAddress> distributorShippingAddressList
                    = distributorAddressService.selectADPreferenceList(rpcHeader, distributorId);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), distributorShippingAddressList);
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
        String distributorId =portalUserInfo.getDistributorId()+"";
        String distributorName=portalUserInfo.getDistributorName();
        boolean isMobile = ValidationUtil.isMobile(phoneNumber);
        if (!isMobile){
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.PHONE_FORMAT_FALSE);
        }
        String traceId = null;
        try {
            DistributorShippingAddress distributorShippingAddress = new DistributorShippingAddress();
            distributorShippingAddress.setDistributorId(Long.parseLong(distributorId));
            distributorShippingAddress.setDistributorName(distributorName);
            distributorShippingAddress.setReceiver(receiver);
            distributorShippingAddress.setProvinceId(Integer.parseInt(provinceId));
            distributorShippingAddress.setProvinceName(provinceName);
            distributorShippingAddress.setCityId(Integer.parseInt(cityId));
            distributorShippingAddress.setCityName(cityName);
            distributorShippingAddress.setDistrictId(Integer.parseInt(districtId));
            distributorShippingAddress.setDistrictName(districtName);
            distributorShippingAddress.setStreetAddress(streetAddress);
            distributorShippingAddress.setPhoneNumber(phoneNumber);
            distributorShippingAddress.setPostCode(postCode);
            distributorShippingAddress.setAuditStatus(1+"");
            if (isDefalutAddress){
                distributorShippingAddress.setIsDefaultAddress((byte) 1);
            }
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getProductList] params:  distributorId={} ", traceId, distributorId);
            int num = distributorAddressService.insertAddress(rpcHeader,isDefalutAddress, distributorShippingAddress);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), num);
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
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][deleteAddress] params:  distributorId={} ", traceId, distributorId);
            distributorAddressService.deleteAddress(rpcHeader, distributorId,addressId);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), 1);
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
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());

            logger.info("#traceId={}# [IN][setDefaultAddress] params:  distributorId={} ", traceId, rpcHeader.getUid());
            distributorAddressService.setDefaultAddress(rpcHeader,  portalUserInfo.getDistributorId()+"",addressId);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), 1);
            logger.info("#traceId={}# [setDefaultAddress][OUT] success");
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

}
