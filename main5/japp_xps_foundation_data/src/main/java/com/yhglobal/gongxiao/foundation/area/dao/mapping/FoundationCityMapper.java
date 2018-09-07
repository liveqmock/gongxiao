package com.yhglobal.gongxiao.foundation.area.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.area.model.FoundationCity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface FoundationCityMapper extends BaseMapper {
    @Delete({
        "delete from foundation_city",
        "where cityId = #{cityId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer cityId);

    @Insert({
        "insert into foundation_city (cityId, cityCode, ",
        "cityName, provinceCode)",
        "values (#{cityId,jdbcType=INTEGER}, #{cityCode}, ",
        "#{cityName,jdbcType=VARCHAR}, #{provinceCode,jdbcType=INTEGER})"
    })
    int insert(FoundationCity record);

    @Select({
        "select",
        "cityId, cityCode, cityName, provinceCode",
        "from foundation_city",
        "where cityId = #{cityId,jdbcType=INTEGER}"
    })
    FoundationCity selectByPrimaryKey(Integer cityId);


    @Select({
            "select",
            "cityId, cityCode, cityName, provinceCode",
            "from foundation_city",
            "where provinceCode = #{provinceCode}"
    })
    List<FoundationCity> selectCityListByProvince(String  provinceCode);

    @Select({
            "select",
            "cityId, cityCode, cityName, provinceCode",
            "from foundation_city",
    })
    List<FoundationCity> selectAllCity();

    @Update({
        "update foundation_city",
        "set cityCode = #{cityCode},",
          "cityName = #{cityName,jdbcType=VARCHAR},",
          "provinceCode = #{provinceCode,jdbcType=INTEGER}",
        "where cityId = #{cityId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(FoundationCity record);
}