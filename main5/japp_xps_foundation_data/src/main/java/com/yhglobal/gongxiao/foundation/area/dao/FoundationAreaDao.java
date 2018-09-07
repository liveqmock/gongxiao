package com.yhglobal.gongxiao.foundation.area.dao;

import com.yhglobal.gongxiao.foundation.area.dao.mapping.FoundationCityMapper;
import com.yhglobal.gongxiao.foundation.area.dao.mapping.FoundationDistrictMapper;
import com.yhglobal.gongxiao.foundation.area.dao.mapping.FoundationProvinceMapper;
import com.yhglobal.gongxiao.foundation.area.model.FoundationCity;
import com.yhglobal.gongxiao.foundation.area.model.FoundationDistrict;
import com.yhglobal.gongxiao.foundation.area.model.FoundationProvince;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Repository
public class FoundationAreaDao {

    @Autowired
    FoundationProvinceMapper provinceMapper;

    @Autowired
    private FoundationCityMapper cityMapper;

    @Autowired
    FoundationDistrictMapper districtMapper;

    /**
     *  获得省列表
     * @return
     */
    public List<FoundationProvince> selectProvinceList(){
        return provinceMapper.selectAllProvinceList();
    }

    /**
     * 获取所有当前province的所有city
     * @param provinceCode
     * @return
     */
    public List<FoundationCity> selectCitysByProvince(String provinceCode){
        return cityMapper.selectCityListByProvince(provinceCode);
    }

    /**
     * 获取所有的城市
     * @return
     */
    public List<FoundationCity> selectAllCity(){
        return cityMapper.selectAllCity();
    }

    /**
     * 获取所有县
     * @param cityCode
     * @return
     */
    public List<FoundationDistrict> selectDstrictsByCity(String cityCode){
      return districtMapper.selectDistrictsByCity(cityCode);
    }

    /**
     * 获取所有区列表
     * @return
     */
    public  List<FoundationDistrict> selectAllDistricts(){
        return districtMapper.selectAllDistrict();
    }

}
