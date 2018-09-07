package com.yhglobal.gongxiao.area.service;

import com.yhglobal.gongxiao.area.City;
import com.yhglobal.gongxiao.area.District;
import com.yhglobal.gongxiao.area.Province;
import com.yhglobal.gongxiao.common.RpcHeader;

import java.util.List;

/**
 * 省市区服务类
 *
 * @author: 陈浩
 **/
public interface AreaService {
    /**
     * 获取所有省列表
     * @return
     */
    List<Province> selectProvinceList(RpcHeader rpcHeader);

    /**
     * 获取某省下的所有城市
     * @param provinceCode
     * @return
     */
    List<City> selectCityByProvince(RpcHeader rpcHeader,String provinceCode);

    /**
     * 获取所有的city列表
     * @param rpcHeader
     * @return
     */
    List<City> selectAllCity(RpcHeader rpcHeader);

    /**
     * 获取某市下的所有区域
     * @param cityCode
     * @return
     */
    List<District> selectDistrictsByCity(RpcHeader rpcHeader,String cityCode);

    /**
     * 获取所有的区域
     * @param rpcHeader
     * @return
     */
    List<District> selectAllDistrict(RpcHeader rpcHeader);

}
