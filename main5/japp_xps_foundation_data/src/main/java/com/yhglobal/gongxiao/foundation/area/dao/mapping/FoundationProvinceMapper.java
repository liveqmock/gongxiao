package com.yhglobal.gongxiao.foundation.area.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.area.model.FoundationProvince;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface FoundationProvinceMapper extends BaseMapper {
    @Delete({
        "delete from foundation_province",
        "where provinceId = #{provinceId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer provinceId);

    @Insert({
        "insert into foundation_province (provinceId, provinceCode, ",
        "provinceName)",
        "values (#{provinceId,jdbcType=INTEGER}, #{provinceCode}, ",
        "#{provinceName,jdbcType=VARCHAR})"
    })
    int insert(FoundationProvince record);

    @Select({
        "select",
        "provinceId, provinceCode, provinceName",
        "from foundation_province",
        "where provinceId = #{provinceId,jdbcType=INTEGER}"
    })
    FoundationProvince selectByPrimaryKey(Integer provinceId);

    @Select({
            "select",
            "provinceId, provinceCode, provinceName",
            "from foundation_province",
    })
    List<FoundationProvince> selectAllProvinceList();

    @Update({
        "update foundation_province",
        "set provinceCode = #{provinceCode},",
          "provinceName = #{provinceName,jdbcType=VARCHAR}",
        "where provinceId = #{provinceId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(FoundationProvince record);
}