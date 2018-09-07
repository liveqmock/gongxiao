package com.yhglobal.gongxiao.foundation.area.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.area.model.FoundationDistrict;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface FoundationDistrictMapper extends BaseMapper {
    @Delete({
        "delete from foundation_district",
        "where districtId = #{districtId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer districtId);

    @Insert({
        "insert into foundation_district (districtId, districtCode, ",
        "districtName, cityCode)",
        "values (#{districtId,jdbcType=INTEGER}, #{districtCode}, ",
        "#{districtName,jdbcType=VARCHAR}, #{cityCode,jdbcType=INTEGER})"
    })
    int insert(FoundationDistrict record);

    @Select({
        "select",
        "districtId, districtCode, districtName, cityCode",
        "from foundation_district",
        "where cityCode = #{cityCode}"
    })
    List<FoundationDistrict> selectDistrictsByCity(String  cityCode);

    @Select({
            "select",
            "districtId, districtCode, districtName, cityCode",
            "from foundation_district",
    })
    List<FoundationDistrict> selectAllDistrict();

    @Select({
            "select",
            "districtId, districtCode, districtName, cityCode",
            "from foundation_district",
            "where districtId = #{districtId,jdbcType=INTEGER}"
    })
    FoundationDistrict selectByPrimaryKey(Integer districtId);

    @Update({
        "update foundation_district",
        "set districtCode = #{districtCode},",
          "districtName = #{districtName,jdbcType=VARCHAR},",
          "cityCode = #{cityCode,jdbcType=INTEGER}",
        "where districtId = #{districtId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(FoundationDistrict record);
}