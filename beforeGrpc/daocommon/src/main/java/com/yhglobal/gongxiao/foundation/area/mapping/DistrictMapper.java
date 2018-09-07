package com.yhglobal.gongxiao.foundation.area.mapping;

import com.yhglobal.gongxiao.area.District;
import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface DistrictMapper extends BaseMapper {
    @Delete({
        "delete from t_district",
        "where districtId = #{districtId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer districtId);

    @Insert({
        "insert into t_district (districtId, districtCode, ",
        "districtName, cityCode)",
        "values (#{districtId,jdbcType=INTEGER}, #{districtCode,jdbcType=VARCHAR}, ",
        "#{districtName,jdbcType=VARCHAR}, #{cityCode,jdbcType=VARCHAR})"
    })
    int insert(District record);

    @Select({
        "select",
        "districtId, districtCode, districtName, cityCode",
        "from t_district",
        "where districtId = #{districtId,jdbcType=INTEGER}"
    })
    District selectByPrimaryKey(Integer districtId);

    @Select({
            "select",
            "districtId, districtCode, districtName, cityCode",
            "from t_district",
            "where districtId = #{districtId,jdbcType=INTEGER}"
    })
    List<District> selectDistrictsByCity(String cityCode);

    @Select({
            "select",
            "districtId, districtCode, districtName, cityCode",
            "from t_district",
    })
    List<District> selectAllDistrict();

    @Update({
        "update t_district",
        "set districtCode = #{districtCode,jdbcType=VARCHAR},",
          "districtName = #{districtName,jdbcType=VARCHAR},",
          "cityCode = #{cityCode,jdbcType=VARCHAR}",
        "where districtId = #{districtId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(District record);
}