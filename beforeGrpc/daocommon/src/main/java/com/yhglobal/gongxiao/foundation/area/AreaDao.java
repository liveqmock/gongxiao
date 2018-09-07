package com.yhglobal.gongxiao.foundation.area;

import com.yhglobal.gongxiao.area.City;
import com.yhglobal.gongxiao.area.District;
import com.yhglobal.gongxiao.area.Province;
import com.yhglobal.gongxiao.foundation.area.mapping.CityMapper;
import com.yhglobal.gongxiao.foundation.area.mapping.DistrictMapper;
import com.yhglobal.gongxiao.foundation.area.mapping.ProvinceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Repository
public class AreaDao {

    @Autowired
    CityMapper cityMapper;

    @Autowired
    DistrictMapper districtMapper;

    @Autowired
    ProvinceMapper provinceMapper;

    /**
     *  获得省列表
     * @return
     */
    public List<Province> selectProvinceList(){
        return provinceMapper.selectProvinceList();
    }

    /**
     * 获取所有当前province的所有city
     * @param provinceCode
     * @return
     */
    public List<City> selectCitysByProvince(String provinceCode){
        return cityMapper.selectCitysByProvince(provinceCode);
    }

    /**
     * 获取所有的城市
     * @return
     */
    public List<City> selectAllCity(){
        return cityMapper.selectAllCity();
    }

    /**
     * 获取所有县
     * @param cityCode
     * @return
     */
    public List<District> selectDstrictsByCity(String cityCode){
      return districtMapper.selectDistrictsByCity(cityCode);
    }

    /**
     * 获取所有区列表
     * @return
     */
    public  List<District> selectAllDistricts(){
        return districtMapper.selectAllDistrict();
    }

}
