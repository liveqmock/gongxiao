package com.yhglobal.gongxiao.foundation.area.mapping;

import com.yhglobal.gongxiao.area.City;
import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CityMapper extends BaseMapper{
    @Delete({
        "delete from t_city",
        "where cityId = #{cityId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer cityId);

    @Insert({
        "insert into t_city (cityId, cityCode, ",
        "cityName, provinceCode)",
        "values (#{cityId,jdbcType=INTEGER}, #{cityCode,jdbcType=VARCHAR}, ",
        "#{cityName,jdbcType=VARCHAR}, #{provinceCode,jdbcType=VARCHAR})"
    })
    int insert(City record);

    @Select({
        "select",
        "cityId, cityCode, cityName, provinceCode",
        "from t_city",
        "where cityId = #{cityId,jdbcType=INTEGER}"
    })
    City selectByPrimaryKey(Integer cityId);

    @Select({
            "select",
            "cityId, cityCode, cityName, provinceCode",
            "from t_city",
            "where provinceCode = #{provinceCode}"
    })
    List<City> selectCitysByProvince(String provinceCode);

    @Select({
            "select",
            "cityId, cityCode, cityName, provinceCode",
            "from t_city",
    })
    List<City> selectAllCity();

    @Update({
        "update t_city",
        "set cityCode = #{cityCode,jdbcType=VARCHAR},",
          "cityName = #{cityName,jdbcType=VARCHAR},",
          "provinceCode = #{provinceCode,jdbcType=VARCHAR}",
        "where cityId = #{cityId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(City record);
}