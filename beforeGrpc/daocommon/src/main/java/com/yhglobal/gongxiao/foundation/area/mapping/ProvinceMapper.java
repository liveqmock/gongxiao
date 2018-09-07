package com.yhglobal.gongxiao.foundation.area.mapping;

import com.yhglobal.gongxiao.area.Province;
import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ProvinceMapper extends BaseMapper {
    @Delete({
        "delete from t_province",
        "where provinceId = #{provinceId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer provinceId);

    @Insert({
        "insert into t_province (provinceId, provinceCode, ",
        "provinceName)",
        "values (#{provinceId,jdbcType=INTEGER}, #{provinceCode,jdbcType=VARCHAR}, ",
        "#{provinceName,jdbcType=VARCHAR})"
    })
    int insert(Province record);

    @Select({
        "select",
        "provinceId, provinceCode, provinceName",
        "from t_province",
        "where provinceId = #{provinceId,jdbcType=INTEGER}"
    })
    Province selectByPrimaryKey(Integer provinceId);

    @Select({
            "select",
            "provinceId, provinceCode, provinceName",
            "from t_province",
    })
    List<Province> selectProvinceList();

    @Update({
        "update t_province",
        "set provinceCode = #{provinceCode,jdbcType=VARCHAR},",
          "provinceName = #{provinceName,jdbcType=VARCHAR}",
        "where provinceId = #{provinceId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Province record);
}